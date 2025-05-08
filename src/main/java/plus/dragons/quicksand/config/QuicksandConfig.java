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

package plus.dragons.quicksand.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class QuicksandConfig {
    public static final Common COMMON;
    public static final Client CLIENT;
    public static final Server SERVER;
    public static final ModConfigSpec COMMON_SPEC;
    public static final ModConfigSpec CLIENT_SPEC;
    public static final ModConfigSpec SERVER_SPEC;

    public static void register(ModContainer modContainer) {
        modContainer.registerConfig(Type.CLIENT, CLIENT_SPEC);
        modContainer.registerConfig(Type.SERVER, SERVER_SPEC);
        modContainer.registerConfig(Type.COMMON, COMMON_SPEC);
    }

    public static class Common {
        public final ModConfigSpec.ConfigValue<Boolean> quicksandCraftable;

        public Common(ModConfigSpec.Builder builder) {
            builder.push("gameplay");
            quicksandCraftable = builder
                    .comment("When enabled, Quicksand Cauldron can be crafted by adding Sand to Water Cauldron (level 1).")
                    .define("quicksandCraftable", true);
            builder.pop();
        }
    }

    public static class Client {
        public final ModConfigSpec.ConfigValue<Boolean> quicksandRenderFog;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("render");
            quicksandRenderFog = builder
                    .comment("When enabled, renders fog when submerged in Quicksand.")
                    .define("renderFog", true);
            builder.pop();
        }
    }

    public static class Server {
        public final ModConfigSpec.ConfigValue<Boolean> quicksandDrownsEntities;
        public final ModConfigSpec.ConfigValue<Boolean> quicksandExtinguishesFire;
        public final ModConfigSpec.ConfigValue<Boolean> quicksandConvertsZombie;
        public final ModConfigSpec.ConfigValue<Boolean> quicksandConvertsDrowned;

        public Server(ModConfigSpec.Builder builder) {
            builder.push("gameplay");
            quicksandDrownsEntities = builder
                    .comment("When enabled, Quicksand makes submerged entities drown.")
                    .define("quicksandDrownsEntities", true);
            quicksandExtinguishesFire = builder
                    .comment("When enabled, Quicksand and Quicksand Cauldron can extinguish burning entities.")
                    .define("quicksandExtinguishesFire", true);
            quicksandConvertsZombie = builder
                    .comment("When enabled, Quicksand can convert submerged Zombie into Husk.")
                    .define("quicksandConvertsZombie", true);
            quicksandConvertsDrowned = builder
                    .comment("When enabled, Quicksand can convert submerged Drowned into Zombie.")
                    .define("quicksandConvertsDrowned", true);
            builder.pop();
        }
    }

    static {
        Pair<Common, ModConfigSpec> common = new ModConfigSpec.Builder().configure(Common::new);
        Pair<Client, ModConfigSpec> client = new ModConfigSpec.Builder().configure(Client::new);
        Pair<Server, ModConfigSpec> server = new ModConfigSpec.Builder().configure(Server::new);
        COMMON = common.getKey();
        CLIENT = client.getKey();
        SERVER = server.getKey();
        COMMON_SPEC = common.getValue();
        CLIENT_SPEC = client.getValue();
        SERVER_SPEC = server.getValue();
    }
}
