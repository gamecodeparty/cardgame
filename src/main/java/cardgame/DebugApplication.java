/*
 * DebugApplication.java
 * 
 * 05/01/2012
 */
package cardgame;

import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_T;
import static java.awt.event.KeyEvent.VK_Y;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import cardgame.engine.UIRenderer;
import cardgame.events.Event;
import cardgame.events.Listener;
import cardgame.model.Model;
import cardgame.model.Pile;

public class DebugApplication extends Application {

  private final Stack<Model.Memento> undo = new Stack<Model.Memento>();
  private final Stack<Model.Memento> redo = new Stack<Model.Memento>();
  
  public DebugApplication() {
    createMenu();
    getDispatcher().listen(ApplicationEvent.STATE_CHANGE, new Listener() {
      @Override
      public void onEvent(Event event) {
        undo.clear();
        redo.clear();
      }
    });
  }
  
  private void createMenu() {
    JMenuBar bar = getJMenuBar();

    JMenu menu;
    JMenuItem item;
    
    menu = bar.add(new JMenu("Debug"));
    item = menu.add(new JMenuItem("Mova uma carta"));
    item.setAccelerator(KeyStroke.getKeyStroke(VK_T, CTRL_MASK));
    item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        movaUmaCarta();
      }
    });

    item = menu.add(new JMenuItem("Desfazer"));
    item.setAccelerator(KeyStroke.getKeyStroke(VK_Y, CTRL_MASK));
    item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        mementoTest(false);
      }
    });
    
    item = menu.add(new JMenuItem("Refazer"));
    item.setAccelerator(KeyStroke.getKeyStroke(VK_Y, SHIFT_MASK + CTRL_MASK));
    item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        mementoTest(true);
      }
    });
    
    menu.add(new JSeparator());
    
    item = menu.add(new JMenuItem("Exibir FPS"));
    item.setMnemonic('f');
    item.setAccelerator(KeyStroke.getKeyStroke(VK_F, CTRL_MASK));
    item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UIRenderer ui = getEngine().getRenderer(UIRenderer.class);
        ui.setFpsEnabled(!ui.isFpsEnabled());
      }
    });
  }
  
  private void movaUmaCarta() {
    Model model = getEngine().getState();
    
    redo.clear();
    undo.push(model.createMemento());
    
    Pile a = model.getPiles().get("tableau0");
    Pile b = model.getPiles().get("tableau1");
    if (!a.isEmpty()) {
      b.push(a.pop());
    } else {
      a.push(b.pop());
    }
    if (!a.isEmpty()) a.peek().setFacedUp(true);
    if (!b.isEmpty()) b.peek().setFacedUp(true);
  }
  
  private void mementoTest(boolean redoOperation) {
    Model model = getEngine().getState();
    if (redoOperation) {
      if (!redo.isEmpty()) {
        undo.push(model.createMemento());
        model.restoreMemento(redo.pop());
      }
    } else {
      if (!undo.isEmpty()) {
        redo.push(model.createMemento());
        model.restoreMemento(undo.pop());
      }
    }
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) {
      // nada a fazer
    }
    new DebugApplication().setVisible(true);
  }
}
