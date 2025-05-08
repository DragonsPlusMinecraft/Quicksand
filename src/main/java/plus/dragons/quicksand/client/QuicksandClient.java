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

package plus.dragons.quicksand.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import plus.dragons.quicksand.client.particle.QuicksandParticle;
import plus.dragons.quicksand.common.QuicksandCommon;
import plus.dragons.quicksand.common.registry.QuicksandParticles;

@Mod(value = QuicksandCommon.ID, dist = Dist.CLIENT)
public class QuicksandClient {
    public QuicksandClient(IEventBus modBus) {
        modBus.register(this);
    }

    @SubscribeEvent
    public void registerParticleProviders(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(QuicksandParticles.QUICKSAND.get(), QuicksandParticle.Provider::new);
    }
}
