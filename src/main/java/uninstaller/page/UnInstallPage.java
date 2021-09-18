package uninstaller.page;

import me.wincho.choui.rendersystem.CPage;
import me.wincho.choui.rendersystem.CWindow;
import me.wincho.choui.rendersystem.RenderSystem;
import me.wincho.choui.utils.FontUtils;
import me.wincho.choui.widget.AnimatedIconWidget;
import uninstaller.Main;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class UnInstallPage extends CPage {
    public UnInstallPage(CWindow window) {
        super(window);
        AnimatedIconWidget icon = null;
        try {
            icon = new AnimatedIconWidget(250, 100, new AnimatedIconWidget.AnimatedIcon(new URL("https://icons8.com/vue-static/landings/animated-icons/icons/uninstalling-updates/uninstalling-updates_200.gif")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        addWidget(icon);

        CompletableFuture.runAsync(() -> {
            Path path = Paths.get(Main.data.get("target").getAsString());
            delete(path);
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            window.setPage(new CompletePage(window));
        });
    }

    private void delete(Path dir) {
        if (dir.toFile().exists()) {
            File[] deleteFolderList = dir.toFile().listFiles();

            for (File file : deleteFolderList) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    delete(file.toPath());
                }
                file.delete();
            }
            dir.toFile().delete();
        }
    }

    @Override
    public void render() {
        super.render();
        RenderSystem.drawString("Uninstalling..", 30, 40, Color.BLACK, FontUtils.DEFAULT_FONT);
    }
}
