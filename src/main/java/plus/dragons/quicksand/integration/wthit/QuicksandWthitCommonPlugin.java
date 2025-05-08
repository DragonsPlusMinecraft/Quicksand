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

package plus.dragons.quicksand.integration.wthit;

import mcp.mobius.waila.api.ICommonRegistrar;
import mcp.mobius.waila.api.IWailaCommonPlugin;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.quicksand.common.QuicksandCommon;

public class QuicksandWthitCommonPlugin implements IWailaCommonPlugin {
    @Override
    public void register(ICommonRegistrar registrar) {
        registrar.featureConfig(Config.OVERRIDE_QUICKSAND, true);
    }

    public static class Config {
        public static final ResourceLocation OVERRIDE_QUICKSAND = QuicksandCommon.asResource("override.quicksand");
    }
}
