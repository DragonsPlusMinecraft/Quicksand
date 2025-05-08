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

package plus.dragons.quicksand.common;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.living.LivingDrownEvent;
import plus.dragons.quicksand.common.registry.data.QuicksandDamageTypes;
import plus.dragons.quicksand.config.QuicksandConfig;

@EventBusSubscriber
public class QuicksandCommonEvents {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingBreathe(LivingBreatheEvent event) {
        if (QuicksandConfig.SERVER.quicksandDrownsEntities.get()) {
            var entity = event.getEntity();
            if (entity.isSubmergedInQuicksand() && entity.drownsInQuicksand())
                event.setCanBreathe(false);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDrown(LivingDrownEvent event) {
        if (QuicksandConfig.SERVER.quicksandDrownsEntities.get()) {
            var entity = event.getEntity();
            if (entity.isSubmergedInQuicksand() && !entity.isQuicksandImmune()) {
                entity.setAirSupply(0);
                if (event.getDamageAmount() > 0)
                    entity.hurt(entity.damageSources().source(QuicksandDamageTypes.QUICKSAND), event.getDamageAmount());
                event.setCanceled(true);
            }
        }
    }
}
