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

package plus.dragons.quicksand.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.quicksand.common.block.QuicksandBlock;
import plus.dragons.quicksand.common.extension.QuicksandEntityExtension;
import plus.dragons.quicksand.config.QuicksandConfig;

@Mixin(value = Entity.class)
public abstract class EntityMixin implements QuicksandEntityExtension {
    @Shadow
    private Level level;
    @Unique
    private boolean quicksand$isInQuicksand;
    @Unique
    private boolean quicksand$wasInQuicksand;
    @Unique
    private boolean quicksand$isSubmergedInQuicksand;
    @Unique
    private boolean quicksand$wasSubmergedInQuicksand;

    @Shadow
    public abstract Vec3 getEyePosition();

    @Shadow
    public abstract BlockState getInBlockState();

    @Shadow
    public abstract EntityType<?> getType();

    @Override
    public boolean isInQuicksand() {
        return quicksand$isInQuicksand;
    }

    @Override
    public boolean wasInQuicksand() {
        return quicksand$wasInQuicksand;
    }

    @Override
    public void setIsInQuicksand(boolean isInQuicksand) {
        this.quicksand$isInQuicksand = isInQuicksand;
    }

    @Override
    public boolean isSubmergedInQuicksand() {
        return this.quicksand$isSubmergedInQuicksand;
    }

    @Override
    public boolean wasSubmergedInQuicksand() {
        return this.quicksand$wasSubmergedInQuicksand;
    }

    @Inject(method = "baseTick", at = @At(value = "FIELD", ordinal = 0, target = "Lnet/minecraft/world/entity/Entity;isInPowderSnow:Z"))
    private void baseTick$updateInQuicksand(CallbackInfo ci) {
        this.quicksand$wasInQuicksand = this.quicksand$isInQuicksand;
        this.quicksand$isInQuicksand = false;
    }

    @Inject(method = "updateFluidOnEyes", at = @At("HEAD"))
    private void updateFluidOnEyes$updateSubmergedInQuicksand(CallbackInfo ci) {
        this.quicksand$wasSubmergedInQuicksand = this.quicksand$isSubmergedInQuicksand;
        this.quicksand$isSubmergedInQuicksand = this.level.getBlockState(BlockPos.containing(this.getEyePosition())).getBlock() instanceof QuicksandBlock;
    }

    @ModifyExpressionValue(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/Entity;isInPowderSnow:Z"))
    private boolean move$extinguishWhenSubmergedInQuicksand(boolean original) {
        return original || (QuicksandConfig.SERVER.quicksandExtinguishesFire.get() && this.quicksand$isSubmergedInQuicksand);
    }

    @ModifyReturnValue(method = "isStateClimbable", at = @At("RETURN"))
    private boolean isStateClimbable$checkQuicksand(boolean original, BlockState state) {
        return original || state.getBlock() instanceof QuicksandBlock;
    }
}
