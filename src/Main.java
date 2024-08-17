
import static game.util.Constant.bgm1;

import javax.swing.SwingUtilities;

import game.Frame;
import game.gui.MainPane;
import game.util.SoundManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame window = new Frame();
            window.changePanel(new MainPane());
            window.sound = new SoundManager(bgm1);
            window.sound.play(true);
            window.setVisible(true);
        });
    }
}
