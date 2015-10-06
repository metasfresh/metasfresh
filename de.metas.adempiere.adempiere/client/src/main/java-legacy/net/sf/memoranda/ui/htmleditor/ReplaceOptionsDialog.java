package net.sf.memoranda.ui.htmleditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.sf.memoranda.ui.htmleditor.util.Local;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ReplaceOptionsDialog extends JDialog {

    public static final int YES_OPTION = 0;
    public static final int YES_TO_ALL_OPTION = 1;
    public static final int NO_OPTION = 2;
    public static final int CANCEL_OPTION = 3;

    public static int showDialog(Component comp, String text) {
        ReplaceOptionsDialog dlg = new ReplaceOptionsDialog(text);
        Dimension dlgSize = new Dimension(300, 150);
        dlg.setSize(300, 150);
        Dimension frmSize = comp.getSize();
        Point loc = comp.getLocationOnScreen();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.setVisible(true);
        return dlg.option;
    }

    public int option = 0;
    JPanel panel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel areaPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    JButton cancelB = new JButton();
    JButton yesAllB = new JButton();
    FlowLayout flowLayout1 = new FlowLayout(FlowLayout.LEFT);
    Border border1;

    Border border2;

   
    BorderLayout borderLayout3 = new BorderLayout();
    JLabel textLabel = new JLabel();
    JButton yesB = new JButton();
    JButton noB = new JButton();

    public ReplaceOptionsDialog(Frame frame, String text) {
        super(frame, Local.getString("Replace"), true);
        try {
            textLabel.setText(text);
            jbInit();
            pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ReplaceOptionsDialog(String text) {
        this(null, text);
    }

    void jbInit() throws Exception {
        this.setResizable(false);
        textLabel.setIcon(new ImageIcon(net.sf.memoranda.ui.htmleditor.HTMLEditor.class.getResource("resources/icons/findbig.png"))) ;
        textLabel.setIconTextGap(10);
        border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border2 = BorderFactory.createEmptyBorder();
        
        panel1.setLayout(borderLayout1);
       
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
       // cancelB.setFocusable(false);
      
        yesAllB.setText(Local.getString("Yes to all"));
        yesAllB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                yesAllB_actionPerformed(e);
            }
        });
        //yesAllB.setFocusable(false);
        buttonsPanel.setLayout(flowLayout1);
        panel1.setBorder(border1);
        areaPanel.setLayout(borderLayout3);
        areaPanel.setBorder(border2);
        borderLayout3.setHgap(5);
        borderLayout3.setVgap(5);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        yesB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                yesB_actionPerformed(e);
            }
        });
        yesB.setText(Local.getString("Yes"));
        
        //yesB.setFocusable(false);
        this.getRootPane().setDefaultButton(yesB);
        
      
        noB.setText(Local.getString("No"));
        noB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                noB_actionPerformed(e);
            }
        });
       // noB.setFocusable(false);
        buttonsPanel.add(yesB, null);
        getContentPane().add(panel1);
        panel1.add(areaPanel, BorderLayout.CENTER);
        areaPanel.add(textLabel, BorderLayout.WEST);
        panel1.add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(yesAllB, null);
        buttonsPanel.add(noB, null);
        buttonsPanel.add(cancelB, null);
        

    }

    void yesAllB_actionPerformed(ActionEvent e) {
        option = YES_TO_ALL_OPTION;
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        option = CANCEL_OPTION;
        this.dispose();
    }
    void yesB_actionPerformed(ActionEvent e) {
        option = YES_OPTION;
        this.dispose();
    }
    void noB_actionPerformed(ActionEvent e) {
        option = NO_OPTION;
        this.dispose();
    }

}