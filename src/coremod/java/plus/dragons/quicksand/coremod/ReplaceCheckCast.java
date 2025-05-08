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

package plus.dragons.quicksand.coremod;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TargetType;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import java.util.Set;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class ReplaceCheckCast implements ITransformer<MethodNode> {
    private final String original;
    private final String replacement;
    private final Set<Target<MethodNode>> targets;

    @SafeVarargs
    public ReplaceCheckCast(String original, String replacement, Target<MethodNode>... targets) {
        this.original = original;
        this.replacement = replacement;
        this.targets = Set.of(targets);
    }

    @Override
    public MethodNode transform(MethodNode methodNode, ITransformerVotingContext context) {
        for (var insn : methodNode.instructions) {
            if (insn.getOpcode() == Opcodes.CHECKCAST && insn instanceof TypeInsnNode checkCast) {
                if (checkCast.desc.equals(original))
                    checkCast.desc = replacement;
            }
        }
        return methodNode;
    }

    @Override
    public TransformerVoteResult castVote(ITransformerVotingContext context) {
        return TransformerVoteResult.YES;
    }

    @Override
    public Set<Target<MethodNode>> targets() {
        return targets;
    }

    @Override
    public TargetType<MethodNode> getTargetType() {
        return TargetType.METHOD;
    }
}
