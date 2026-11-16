package com.arthou.tpa;

import com.arthou.tpa.client.TpaModListDescription;
import com.arthou.tpa.command.TpaCommands;
import com.arthou.tpa.runtime.TeleportRuntime;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(TpaForgeMod.MOD_ID)
public final class TpaForgeMod {
    public static final String MOD_ID = "tpa";

    public TpaForgeMod() {
        MinecraftForge.EVENT_BUS.addListener(TpaCommands::register);
        MinecraftForge.EVENT_BUS.addListener(TeleportRuntime::onPlayerLoggedOut);
        MinecraftForge.EVENT_BUS.addListener(TeleportRuntime::onPlayerRespawn);
        MinecraftForge.EVENT_BUS.addListener(TeleportRuntime::onPlayerDeath);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            TpaModListDescription.register();
        }
    }
}
