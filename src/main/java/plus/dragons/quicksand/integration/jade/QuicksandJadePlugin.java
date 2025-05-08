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

package plus.dragons.quicksand.integration.jade;

import plus.dragons.quicksand.common.block.QuicksandBlock;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IWailaConfig;
import snownee.jade.impl.WailaClientRegistration;

@WailaPlugin
public class QuicksandJadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
            if (WailaClientRegistration.instance().maybeLowVisionUser() || !IWailaConfig.get().getGeneral().getBuiltinCamouflage()) {
                return accessor;
            }
            if (accessor instanceof BlockAccessor blockAccessor) {
                if (blockAccessor.getBlock() instanceof QuicksandBlock quicksand) {
                    return registration.blockAccessor()
                            .from(blockAccessor)
                            .blockState(quicksand.convertToSand(blockAccessor.getBlockState(), blockAccessor.getLevel(), blockAccessor.getPosition()))
                            .build();
                }
            }
            return accessor;
        });
    }
}
