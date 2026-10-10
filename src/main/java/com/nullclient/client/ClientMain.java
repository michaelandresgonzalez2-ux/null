package com.nullclient.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class ClientMain implements ClientModInitializer {
    public static final KeyBinding OPEN_GUI_KEY = new KeyBinding(
            "Open NullClient GUI",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "NullClient"
    );

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(OPEN_GUI_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!OPEN_GUI_KEY.wasPressed()) {
                return;
            }

            if (client.currentScreen == null || !(client.currentScreen instanceof com.nullclient.client.gui.ClickGuiScreen)) {
                client.setScreen(new com.nullclient.client.gui.ClickGuiScreen());
            } else {
                client.setScreen(null);
            }
        });
    }
}
