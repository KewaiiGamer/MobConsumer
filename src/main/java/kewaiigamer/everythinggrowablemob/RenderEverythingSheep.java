package kewaiigamer.everythinggrowablemob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderEverythingSheep extends RenderLiving<EntityEverythingSheep> {

    private ResourceLocation mobTexture = new ResourceLocation("everythinggrowablemob:textures/entity/EverythingSheep.png");
    public static final Factory FACTORY = new Factory();

    public RenderEverythingSheep(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCow(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityEverythingSheep entity) {
        return mobTexture;
    }
    public static class Factory implements IRenderFactory<EntityEverythingSheep> {

        @Override
        public Render<? super EntityEverythingSheep> createRenderFor(RenderManager manager) {
            return new RenderEverythingSheep(manager);
        }

    }

}
