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

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.quicksand.common.block.QuicksandBlock;

@Mixin(ClimbOnTopOfPowderSnowGoal.class)
public abstract class ClimbOnTopOfPowderSnowGoalMixin {
    @Shadow
    @Final
    private Mob mob;

    @Shadow
    @Final
    private Level level;

    @ModifyReturnValue(method = "canUse", at = @At("TAIL"))
    private boolean canUse$checkQuicksand(boolean original) {
        if (original)
            return true;
        boolean flag = this.mob.wasInQuicksand() || this.mob.isInQuicksand();
        if (flag && this.mob.canWalkOnQuicksand()) {
            BlockPos blockpos = this.mob.blockPosition().above();
            BlockState blockstate = this.level.getBlockState(blockpos);
            return blockstate.getBlock() instanceof QuicksandBlock || blockstate.getCollisionShape(this.level, blockpos) == Shapes.empty();
        }
        return false;
    }
}
