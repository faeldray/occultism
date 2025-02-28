/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.common.ritual.pentacle;

import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.github.klikli_dev.occultism.registry.OccultismRituals;
import com.github.klikli_dev.occultism.registry.OccultismTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Pentacle extends ForgeRegistryEntry<Pentacle> {
    //region Fields

    protected PatchouliAPI.IPatchouliAPI api = PatchouliAPI.instance;
    protected IMultiblock blockMatcher;
    protected List<Object> mapping = new ArrayList<>();
    protected String translationKey;
    //endregion Fields

    //region Initialization
    public Pentacle() {
    }
    //endregion Initialization

    //region Getter / Setter

    /**
     * Gets the multiblock block matcher for the pentacle.
     *
     * @return the multiblack.
     */
    public IMultiblock getBlockMatcher() {
        return this.blockMatcher;
    }
    //endregion Getter / Setter

    //region Methods

    protected String getDefaultTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("pentacle", OccultismRituals.PENTACLE_REGISTRY.getKey(this));
        }

        return this.translationKey;
    }

    /**
     * Returns the unlocalized name of this item.
     */
    public String getTranslationKey() {
        return this.getDefaultTranslationKey();
    }

    /**
     * registers the multiblock with patchouli_books.
     */
    public void registerMultiblock(ResourceLocation id) {
        this.setupMapping();
        this.blockMatcher = this.api.registerMultiblock(id, this.setupMultiblock());
    }

    protected void setupMapping() {
        this.mapping.addAll(Arrays.asList(
                '0', this.api.looseBlockMatcher(OccultismBlocks.GOLDEN_SACRIFICIAL_BOWL.get()),
                'W', this.api.looseBlockMatcher(OccultismBlocks.CHALK_GLYPH_WHITE.get()),
                'G', this.api.looseBlockMatcher(OccultismBlocks.CHALK_GLYPH_GOLD.get()),
                'P', this.api.looseBlockMatcher(OccultismBlocks.CHALK_GLYPH_PURPLE.get()),
                'R', this.api.looseBlockMatcher(OccultismBlocks.CHALK_GLYPH_RED.get()),
                'C', this.api.predicateMatcher(OccultismBlocks.CANDLE_WHITE.get(),
                        b -> OccultismTags.CANDLES.contains(b.getBlock())),
                ' ', this.api.anyMatcher())
        );
    }

    /**
     * set up the multi block in this method.
     * Example at
     * https://github.com/Vazkii/Patchouli/blob/1.14-final/src/main/java/vazkii/patchouli/common/multiblock/MultiblockRegistry.java
     *
     * @return the finished multiblock.
     */
    protected abstract IMultiblock setupMultiblock();
    //endregion Methods
}
