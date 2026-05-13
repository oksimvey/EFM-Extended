package com.robson.efmextended.mixins;


import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.SortedMap;

@Mixin(RenderBuffers.class)
@OnlyIn(Dist.CLIENT)
public interface RenderBufferInterface {

    @Accessor("bufferSource")
    MultiBufferSource.BufferSource getBufferSource();

    @Accessor("fixedBuffers")
    SortedMap<RenderType, BufferBuilder> getFixedBuffers();

}