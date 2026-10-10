package com.nullclient.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends Screen {
    private final List<ModuleToggleButton> buttons = new ArrayList<>();

    public ClickGuiScreen() {
        super(Text.literal("NullClient"));
        buttons.add(new ModuleToggleButton("X-Ray", 24, 32, 160, 20, true));
        buttons.add(new ModuleToggleButton("Fullbright", 24, 60, 160, 20, false));
        buttons.add(new ModuleToggleButton("NoClip", 24, 88, 160, 20, false));
        buttons.add(new ModuleToggleButton("ESP", 24, 116, 160, 20, true));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        final int bg = 0xFF181825;
        final int border = 0xFF89B4FA;
        final int text = 0xFFE6E6F2;
        final int active = 0xFF89D185;
        final int inactive = 0xFF3A3A4A;

        final int panelX = 16;
        final int panelY = 16;
        final int panelW = 200;
        final int panelH = 180;

        context.fill(panelX, panelY, panelX + panelW, panelY + panelH, bg);
        context.drawBorder(panelX, panelY, panelW, panelH, border);
        context.drawText(this.textRenderer, "NullClient", panelX + 12, panelY + 10, text, false);

        for (ModuleToggleButton button : buttons) {
            button.render(context, mouseX, mouseY, text, active, inactive, border);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        for (ModuleToggleButton moduleButton : buttons) {
            if (moduleButton.isInside(mouseX, mouseY)) {
                moduleButton.toggle();
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private static final class ModuleToggleButton {
        private final String name;
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private boolean enabled;

        private ModuleToggleButton(String name, int x, int y, int width, int height, boolean enabled) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.enabled = enabled;
        }

        private boolean isInside(double mouseX, double mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }

        private void toggle() {
            this.enabled = !this.enabled;
        }

        private void render(DrawContext context, int mouseX, int mouseY, int textColor, int activeColor, int inactiveColor, int borderColor) {
            int bgColor = enabled ? activeColor : inactiveColor;
            context.fill(x, y, x + width, y + height, bgColor);
            context.drawBorder(x, y, width, height, borderColor);

            String label = name + ": " + (enabled ? "ON" : "OFF");
            context.drawText(context.getClient().textRenderer, label, x + 10, y + (height - 8) / 2, textColor, false);
        }
    }
}
