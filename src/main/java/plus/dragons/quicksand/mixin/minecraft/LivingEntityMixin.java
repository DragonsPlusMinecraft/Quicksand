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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.quicksand.common.block.QuicksandBlock;
import plus.dragons.quicksand.common.extension.QuicksandLivingEntityExtension;
import plus.dragons.quicksand.common.registry.data.QuicksandTags;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin implements QuicksandLivingEntityExtension {
    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Override
    public boolean isQuicksandImmune() {
        return this.getType().is(QuicksandTags.QUICKSAND_IMMUNE_ENTITY_TYPES);
    }

    @Override
    public boolean drownsInQuicksand() {
        return !this.isQuicksandImmune();
    }

    @Override
    public boolean canWalkOnQuicksand() {
        return this.getType().is(QuicksandTags.QUICKSAND_WALKABLE_MOBS) ||
                this.getItemBySlot(EquipmentSlot.FEET).canWalkOnQuicksand((LivingEntity) (Object) this);
    }

    @ModifyExpressionValue(method = "handleRelativeFrictionAndCalculateMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;onClimbable()Z"))
    private boolean handleRelativeFrictionAndCalculateMovement$checkQuicksand(boolean original) {
        return original || this.getInBlockState().getBlock() instanceof QuicksandBlock && canWalkOnQuicksand();
    }
}
