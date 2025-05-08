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

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteraction.InteractionMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import plus.dragons.quicksand.config.QuicksandConfig;

public class QuicksandCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<QuicksandCauldronBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            propertiesCodec(),
            CauldronInteraction.CODEC.fieldOf("interactions").forGetter(block -> block.interactions),
            ResourceKey.codec(Registries.ITEM).fieldOf("bucket").<Supplier<? extends Item>>xmap(
                    key -> () -> BuiltInRegistries.ITEM.get(key),
                    bucket -> BuiltInRegistries.ITEM.getResourceKey(bucket.get()).orElseThrow()).forGetter(block -> block.bucket))
            .apply(instance, QuicksandCauldronBlock::new));
    protected final Supplier<? extends Item> bucket;

    protected QuicksandCauldronBlock(Properties properties, InteractionMap interactions, Supplier<? extends Item> bucket) {
        super(properties, interactions);
        this.bucket = bucket;
    }

    public QuicksandCauldronBlock(Properties properties, ResourceLocation location, Supplier<? extends Item> bucket) {
        this(properties, CauldronInteraction.newInteractionMap(location.toString()), bucket);
    }

    public void registerDefaultInteractions(ItemLike sand) {
        this.getInteractions().map().put(Items.BUCKET, this::fillBucket);
        CauldronInteraction.EMPTY.map().put(bucket.get(), this::emptyBucket);
        if (QuicksandConfig.COMMON.quicksandCraftable.get()) {
            CauldronInteraction.WATER.map().put(sand.asItem(), this::craftFromSand);
        }
    }

    public InteractionMap getInteractions() {
        return interactions;
    }

    public ItemStack getBucket() {
        return this.bucket.get().getDefaultInstance();
    }

    @Override
    protected MapCodec<? extends QuicksandCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return 0.9375D;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return 3;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide &&
                QuicksandConfig.SERVER.quicksandExtinguishesFire.get() &&
                entity.isOnFire() &&
                this.isEntityInsideContent(state, pos, entity)) {
            entity.clearFire();
        }
    }

    public ItemInteractionResult fillBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        return CauldronInteraction.fillBucket(state, level, pos, player, hand, stack,
                this.bucket.get().getDefaultInstance(),
                it -> it.is(this),
                SoundEvents.SAND_BREAK);
    }

    public ItemInteractionResult emptyBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        return CauldronInteraction.emptyBucket(level, pos, player, hand, stack,
                this.defaultBlockState(),
                SoundEvents.SAND_PLACE);
    }

    public ItemInteractionResult craftFromSand(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if (blockState.hasProperty(LayeredCauldronBlock.LEVEL) && blockState.getValue(LayeredCauldronBlock.LEVEL) == 1) {
            if (!level.isClientSide) {
                Item item = stack.getItem();
                if (!player.hasInfiniteMaterials())
                    stack.shrink(1);
                player.awardStat(Stats.FILL_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(item));
                level.setBlockAndUpdate(pos, this.defaultBlockState());
                level.playSound(null, pos, SoundEvents.SAND_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
