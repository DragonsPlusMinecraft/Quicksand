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

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import plus.dragons.quicksand.common.block.QuicksandBlock;
import plus.dragons.quicksand.integration.wthit.QuicksandWthitCommonPlugin.Config;

public enum QuicksandOverride implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public @Nullable BlockState getOverride(IBlockAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(Config.OVERRIDE_QUICKSAND) && accessor.getBlock() instanceof QuicksandBlock quicksand)
            return quicksand.convertToSand(accessor.getBlockState(), accessor.getWorld(), accessor.getPosition());
        return null;
    }
}
