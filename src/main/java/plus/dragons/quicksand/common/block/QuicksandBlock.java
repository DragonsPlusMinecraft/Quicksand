/*
 * Copyright (C) 2025 Shnupbups, LambdAurora and DragonsPlus
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package plus.dragons.quicksand.common.block;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import plus.dragons.quicksand.common.registry.QuicksandParticles;
import plus.dragons.quicksand.common.registry.data.QuicksandDamageTypes;

public class QuicksandBlock extends ColoredFallingBlock implements BucketPickup {
    protected static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0, 0.0, 0.0, 1.0, 0.9f, 1.0);
    protected final Supplier<? extends Block> sand;
    protected final Supplier<? extends Item> bucket;

    public QuicksandBlock(BlockBehaviour.Properties settings, ColorRGBA color, Supplier<? extends Block> sand, Supplier<? extends Item> bucket) {
        super(color, settings);
        this.sand = sand;
        this.bucket = bucket;
    }

    public BlockState convertToSand(BlockState state, BlockGetter level, BlockPos pos) {
        return sand.get().defaultBlockState();
    }

    public ItemStack getBucket() {
        return bucket.get().getDefaultInstance();
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!(fallDistance < 4.0) && entity instanceof LivingEntity livingEntity) {
            LivingEntity.Fallsounds fallSounds = livingEntity.getFallSounds();
            SoundEvent soundevent = fallDistance < 7.0 ? fallSounds.small() : fallSounds.big();
            entity.playSound(soundevent, 1.0F, 1.0F);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getInBlockState().is(this)) {
            entity.makeStuckInBlock(state, new Vec3(0.6F, 1.5, 0.6F));
            if (entity.xOld != entity.getX() || entity.zOld != entity.getZ()) {
                if (level.isClientSide) {
                    RandomSource random = level.getRandom();
                    if (random.nextBoolean()) {
                        int color = FastColor.ARGB32.opaque(this.getDustColor(state, level, pos));
                        level.addParticle(
                                ColorParticleOption.create(QuicksandParticles.QUICKSAND.get(), color),
                                entity.getX(),
                                entity.getY() + 1,
                                entity.getZ(),
                                Mth.randomBetween(random, 1F, -1F) * 1 / 12F,
                                0.05,
                                Mth.randomBetween(random, 1F, -1F) * 1 / 12F);
                    }
                } else if (entity instanceof LivingEntity livingEntity && !livingEntity.isQuicksandImmune()) {
                    double dx = Math.abs(entity.getX() - entity.xOld);
                    double dz = Math.abs(entity.getZ() - entity.zOld);
                    if (dx >= 0.003F || dz >= 0.003F) {
                        livingEntity.hurt(livingEntity.damageSources().source(QuicksandDamageTypes.QUICKSAND), 1F);
                    }
                }
            }
        }
        entity.setIsInQuicksand(true);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();
            if (entity != null && entity.fallDistance > 2.5f)
                return FALLING_COLLISION_SHAPE;
            if (entity instanceof FallingBlockEntity || entity instanceof LivingEntity livingEntity && livingEntity.canWalkOnQuicksand() && context.isAbove(Shapes.block(), pos, false) && !context.isDescending())
                return super.getCollisionShape(state, level, pos, context);
            return Shapes.empty();
        }
        return Shapes.block();
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.is(this) || super.skipRendering(state, stateFrom, direction);
    }

    @Override
    protected void falling(FallingBlockEntity entity) {
        entity.dropItem = false;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return true;
    }

    @Override
    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_IMMEDIATE | Block.UPDATE_ALL);
        if (!level.isClientSide())
            level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
        return getBucket();
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.SAND_BREAK);
    }
}
