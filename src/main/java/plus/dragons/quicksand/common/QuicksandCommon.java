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

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.quicksand.common.registry.QuicksandBlocks;
import plus.dragons.quicksand.common.registry.QuicksandItems;
import plus.dragons.quicksand.common.registry.QuicksandParticles;
import plus.dragons.quicksand.common.registry.QuicksandSoundEvents;
import plus.dragons.quicksand.config.QuicksandConfig;
import plus.dragons.quicksand.integration.biomesoplenty.BOPIntegration;
import plus.dragons.quicksand.integration.biomeswevegone.BWGIntegration;

@Mod(QuicksandCommon.ID)
public class QuicksandCommon {
    public static final String ID = "quicksand";

    public QuicksandCommon(IEventBus modBus, ModContainer modContainer) {
        QuicksandBlocks.register(modBus);
        QuicksandItems.register(modBus);
        QuicksandParticles.register(modBus);
        QuicksandSoundEvents.register(modBus);
        QuicksandConfig.register(modContainer);
        modBus.register(this);
        if (ModList.get().isLoaded(BOPIntegration.ID))
            modBus.register(new BOPIntegration(modBus));
        if (ModList.get().isLoaded(BWGIntegration.ID))
            modBus.register(new BWGIntegration(modBus));
    }

    @SubscribeEvent
    public void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(QuicksandBlocks::registerCauldronInteractions);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
