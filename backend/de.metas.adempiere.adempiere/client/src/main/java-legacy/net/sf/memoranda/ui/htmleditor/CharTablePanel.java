package net.sf.memoranda.ui.htmleditor;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CharTablePanel extends JPanel {

    JEditorPane editor;
    Border border1;
    FlowLayout flowLayout1 = new FlowLayout();
    
    String[] chars =
        {
            "\u00A9",
            "\u00AE",
            "\u2122",
            "\u00AB\u00BB",
            "\u201C\u201D",
            "\u2018\u2019",
            "\u2013",
            "\u2014",
            "\u2020",
            "\u2021",
            "\u00A7",
            "\u2116",
            "\u20AC",
            "\u00A2",
            "\u00A3",
            "\u00A4",
            "\u00A5",
            "\u00B7",
            "\u2022",
            "\u25E6",
            "\u25AA",
            "\u25AB",
            "\u25CF",
            "\u25CB",
            "\u25A0",
            "\u25A1",
            "\u263A",
            "\u00A0" };

    Vector buttons = new Vector();

    public CharTablePanel(JEditorPane ed) {
        try {
            editor = ed;
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void jbInit() throws Exception {
        

        //this.setSize(200, 50);        
        this.setFocusable(false);        
        //this.setBackground();
        
        this.setPreferredSize(new Dimension(200, 45));
        this.setToolTipText("");
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        this.setLayout(flowLayout1);
        //this.getContentPane().add(cal,  BorderLayout.CENTER);
        createButtons();

    }

    void createButtons() {
        for (int i = 0; i < chars.length; i++) {
            JButton button = new JButton(new CharAction(chars[i]));
            button.setMaximumSize(new Dimension(50, 22));
            //button.setMinimumSize(new Dimension(22, 22));
            button.setPreferredSize(new Dimension(30, 22));
            button.setRequestFocusEnabled(false);
            button.setFocusable(false);
            button.setBorderPainted(false);
            button.setOpaque(false);
            button.setMargin(new Insets(0,0,0,0));
            button.setFont(new Font("serif", 0, 14));
            if (i == chars.length-1) {
                button.setText("nbsp");
                button.setFont(new Font("Dialog",0,10));
                button.setMargin(new Insets(0,0,0,0));
            }
            this.add(button, null);
        }
    }

    class CharAction extends AbstractAction {
        CharAction(String name) {
            super(name);
            //putValue(Action.SHORT_DESCRIPTION, name);
        }

        public void actionPerformed(ActionEvent e) {
            String s = this.getValue(Action.NAME).toString();
            editor.replaceSelection(s);
            if (s.length() == 2)
                editor.setCaretPosition(editor.getCaretPosition()-1);
        }
    }

}