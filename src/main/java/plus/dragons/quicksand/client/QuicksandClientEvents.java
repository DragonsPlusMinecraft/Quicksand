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

import net.minecraft.core.BlockPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import plus.dragons.quicksand.common.block.QuicksandBlock;
import plus.dragons.quicksand.config.QuicksandConfig;

@EventBusSubscriber(Dist.CLIENT)
public class QuicksandClientEvents {
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        if (QuicksandConfig.CLIENT.quicksandRenderFog.get()) {
            var camera = event.getCamera();
            var entity = camera.getEntity();
            var level = entity.level();
            var pos = BlockPos.containing(camera.getPosition());
            if (level.getBlockState(pos).getBlock() instanceof QuicksandBlock) {
                if (camera.getEntity().isSpectator()) {
                    event.setNearPlaneDistance(-8.0F);
                    event.scaleFarPlaneDistance(0.5F);
                } else {
                    event.setNearPlaneDistance(0.0F);
                    event.setFarPlaneDistance(2.0F);
                }
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        if (QuicksandConfig.CLIENT.quicksandRenderFog.get()) {
            var camera = event.getCamera();
            var entity = camera.getEntity();
            var level = entity.level();
            var pos = BlockPos.containing(camera.getPosition());
            var state = level.getBlockState(pos);
            if (state.getBlock() instanceof QuicksandBlock quicksand) {
                int color = quicksand.getDustColor(state, level, pos);
                event.setRed((color >> 16 & 0xFF) / 255.0F);
                event.setGreen((color >> 8 & 0xFF) / 255.0F);
                event.setBlue((color & 0xFF) / 255.0F);
            }
        }
    }
}
