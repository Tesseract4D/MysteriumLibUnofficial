package net.tclproject.mysteriumlib;

import chylex.hee.render.model.ModelEndermanHeadBiped;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.tclproject.mysteriumlib.asm.annotations.Fix;
import net.tclproject.mysteriumlib.asm.common.CustomLoadingPlugin;
import net.tclproject.mysteriumlib.asm.common.FirstClassTransformer;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.Random;

@Mod(modid = ModProperties.MODID, useMetadata = true, version = ModProperties.VERSION, name = ModProperties.NAME)
public class ForgeMod extends CustomLoadingPlugin {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        makeFancyModInfo(event);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Fix(insertOnInvoke = "org/lwjgl/opengl/GL11;glScalef(FFF)V", insertOnLine = 1)
    public static void renderEquippedItems(RenderPlayer c, AbstractClientPlayer p, float f) {
        float n = 1.06F;
        GL11.glScalef(n, n, n);
    }

    @Fix(insertOnInvoke = "org/lwjgl/opengl/GL11;glScalef(FFF)V")
    public static void render(ModelEndermanHeadBiped c, Entity entity, float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float unitPixel) {
        float n = 1.1F;
        GL11.glScalef(n, n, n);
    }

    public void makeFancyModInfo(FMLPreInitializationEvent event) {
        // The following will overwrite the mcmod.info file so the info page looks good.
        // Adapted from Jabelar's Magic Beans and AstroTibs's OptionsEnforcer.

        // This will stop Forge from complaining about missing mcmod.info (just in case i forget it).
        event.getModMetadata().autogenerated = false;

        event.getModMetadata().name = ModProperties.COLORED_NAME; // Mod name
        event.getModMetadata().version = ModProperties.COLORED_VERSION; // Mod version
        event.getModMetadata().credits = ModProperties.CREDITS; // Mod credits

        // Author list
        event.getModMetadata().authorList.clear();
        Collections.addAll(event.getModMetadata().authorList, ModProperties.AUTHORS);

        event.getModMetadata().url = ModProperties.COLORED_URL; // Mod URL

        // Mod description
        event.getModMetadata().description = ModProperties.DESCRIPTION + "\n\n"
            + EnumChatFormatting.DARK_GRAY
            + EnumChatFormatting.ITALIC
            + ModProperties.SPLASH_OF_THE_DAY[(new Random()).nextInt(ModProperties.SPLASH_OF_THE_DAY.length)];

        if (ModProperties.LOGO != null) event.getModMetadata().logoFile = ModProperties.LOGO; // Mod logo
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{FirstClassTransformer.class.getName()};
    }

    @Override
    public void registerFixes() {
        registerClassWithFixes("net.tclproject.mysteriumlib.ForgeMod");
    }
}
