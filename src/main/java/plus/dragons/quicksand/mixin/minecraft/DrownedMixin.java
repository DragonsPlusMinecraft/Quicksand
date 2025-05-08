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

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import plus.dragons.quicksand.common.registry.QuicksandSoundEvents;
import plus.dragons.quicksand.config.QuicksandConfig;

@Mixin(Drowned.class)
public abstract class DrownedMixin extends ZombieMixin {
    protected DrownedMixin(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean canConvertInQuicksand() {
        return QuicksandConfig.SERVER.quicksandConvertsDrowned.get();
    }

    @Override
    public void doQuicksandConversion() {
        if (EventHooks.canLivingConvert(this, EntityType.ZOMBIE, this::setQuicksandConversionTime)) {
            this.convertToZombieType(EntityType.ZOMBIE);
            this.playSound(QuicksandSoundEvents.DROWNED_CONVERTED_TO_ZOMBIE.get(), 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
        }
    }
}
