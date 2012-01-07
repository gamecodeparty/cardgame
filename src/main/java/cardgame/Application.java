/*
 * Application.java
 * 
 * 05/01/2012
 */
package cardgame;

import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.KeyEvent.VK_N;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import cardgame.engine.Engine;
import cardgame.events.Dispatcher;
import cardgame.model.ModelSampler;

public class Application extends JFrame {

  private final Engine engine;
  private final Dispatcher dispatcher =  new Dispatcher.Asynchronous();

  public Application() {
    setTitle("Jogo de Cartas");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    engine = new Engine();
    engine.setEnhancedGraphicsEnabled(true);
    engine.setPreferredSize(new Dimension(800, 600));
    add(engine, BorderLayout.CENTER);

    pack();
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width - getWidth()) >> 1;
    int y = (screen.height - getHeight()) >> 1;
    setBounds(x, y, getWidth(), getHeight());

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        engine.stop();
      }
    });

    createMenu();
  }

  public Dispatcher getDispatcher() {
    return dispatcher;
  }

  public Engine getEngine() {
    return engine;
  }

  private void createMenu() {
    JMenuBar bar = new JMenuBar();

    JMenu menu;
    JMenuItem item;

    menu = bar.add(new JMenu("Jogo"));
    menu.setMnemonic('j');

    item = menu.add(new JMenuItem("Novo Jogo"));
    item.setMnemonic('n');
    item.setAccelerator(KeyStroke.getKeyStroke(VK_N, CTRL_MASK));
    item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doNewGame();
      }
    });

    menu.add(new JSeparator());

    item = menu.add(new JMenuItem("Sair"));
    item.setMnemonic('s');
    item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    setJMenuBar(bar);
  }

  @Override
  public void setVisible(boolean value) {
    super.setVisible(value);
    if (value) {
      getEngine().start();
    } else {
      getEngine().stop();
    }
  }

  public void doNewGame() {
    getEngine().setState(ModelSampler.sampleSpiderState());
    getDispatcher().dispatchEvent(
        new ApplicationEvent(ApplicationEvent.STATE_CHANGE, this));
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) {
      // nada a fazer
    }
    new Application().setVisible(true);
  }
}
