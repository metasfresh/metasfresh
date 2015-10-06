package net.sf.memoranda.ui.htmleditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.memoranda.ui.htmleditor.util.Local;

import org.compiere.grid.ed.HTMLEditor;

public class ContinueSearchDialog extends JPanel {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton cancelB = new JButton();
  JButton continueB = new JButton();
  JPanel buttonsPanel = new JPanel();
  JLabel jLabel1 = new JLabel();
  JTextField textF = new JTextField();
  String text;
  
  Thread thread;

  public boolean cont = false;
  public boolean cancel = false;

  public ContinueSearchDialog(Thread t, String txt) {    
    try {
      text = txt;   
      thread = t;
      jbInit();      
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  
  void jbInit() throws Exception {
  
    this.setLayout(borderLayout1);
    textF.setEditable(false);
    textF.setText(text);
    cancelB.setMaximumSize(new Dimension(120, 26));
    cancelB.setMinimumSize(new Dimension(80, 26));
    cancelB.setPreferredSize(new Dimension(120, 26));
    cancelB.setText(Local.getString("Cancel"));
    cancelB.setFocusable(false);
    cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
    continueB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                continueB_actionPerformed(e);
            }
        });
    continueB.setText(Local.getString("Find next"));
    continueB.setPreferredSize(new Dimension(120, 26));
    continueB.setMinimumSize(new Dimension(80, 26));
    continueB.setMaximumSize(new Dimension(120, 26));
    continueB.setFocusable(false);    
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    buttonsPanel.setLayout(flowLayout1);
    
    jLabel1.setText(" "+Local.getString("Search for")+":  ");
    jLabel1.setIcon(new ImageIcon(HTMLEditor.class.getResource("resources/icons/findbig.png"))) ;   
    this.add(jLabel1, BorderLayout.WEST);
    this.add(textF,BorderLayout.CENTER);    
    buttonsPanel.add(continueB, null);
    buttonsPanel.add(cancelB, null);
    this.add(buttonsPanel,  BorderLayout.EAST);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    cont = true;
    cancel = true;    
    thread.resume();
  }

  void continueB_actionPerformed(ActionEvent e) {
     cont = true;     
     thread.resume();
  }
}