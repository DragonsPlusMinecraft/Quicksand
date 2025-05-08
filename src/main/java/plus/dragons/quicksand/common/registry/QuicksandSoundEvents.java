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

package plus.dragons.quicksand.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.quicksand.common.QuicksandCommon;

public class QuicksandSoundEvents {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
            .create(Registries.SOUND_EVENT, QuicksandCommon.ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> ZOMBIE_CONVERTED_TO_HUSK = SOUND_EVENTS
            .register("entity.zombie.converted_to_husk", SoundEvent::createVariableRangeEvent);
    public static final DeferredHolder<SoundEvent, SoundEvent> DROWNED_CONVERTED_TO_ZOMBIE = SOUND_EVENTS
            .register("entity.drowned.converted_to_zombie", SoundEvent::createVariableRangeEvent);

    public static void register(IEventBus modBus) {
        SOUND_EVENTS.register(modBus);
    }
}
