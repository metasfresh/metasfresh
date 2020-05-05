package net.sf.memoranda.ui.htmleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.memoranda.ui.htmleditor.util.Local;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ElementDialog extends JDialog {
  JPanel areaPanel = new JPanel(new GridBagLayout());
  JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
  JButton cancelB = new JButton();
  JButton okB = new JButton();
  JLabel lblClass = new JLabel();
  public JTextField classField = new JTextField();
  JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
  public JLabel header = new JLabel();
  public boolean CANCELLED = false;
  JLabel lblID = new JLabel();
  public JTextField idField = new JTextField();
  JLabel lblStyle = new JLabel();
  public JTextField styleField = new JTextField();
  GridBagConstraints gbc;

  public ElementDialog(Frame frame) {
    super(frame, Local.getString("Object properties"), true);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ElementDialog() {
    this(null);
  }


  void jbInit() throws Exception {
	this.setResizable(false);
	headerPanel.setBackground(Color.WHITE);
	header.setFont(new java.awt.Font("Dialog", 0, 20));
	header.setForeground(new Color(0, 0, 124));
	header.setText(Local.getString("Object properties"));
	header.setIcon(new ImageIcon(
		net.sf.memoranda.ui.htmleditor.ElementDialog.class.getResource(
		"resources/icons/textbig.png")));
    headerPanel.add(header);
    this.getContentPane().add(headerPanel, BorderLayout.NORTH);
    
    areaPanel.setBorder(BorderFactory.createEtchedBorder(Color.white,
    	new Color(142, 142, 142)));
	lblID.setText(Local.getString("ID"));
	gbc = new GridBagConstraints();
	gbc.gridx = 0; gbc.gridy = 0;
	gbc.anchor = GridBagConstraints.WEST;
	gbc.insets = new Insets(10, 10, 5, 5);
    areaPanel.add(lblID, gbc);
	idField.setPreferredSize(new Dimension(300, 25));
	gbc = new GridBagConstraints();
	gbc.gridx = 1; gbc.gridy = 0;
	gbc.anchor = GridBagConstraints.WEST;
	gbc.insets = new Insets(10, 5, 5, 10);
	areaPanel.add(idField, gbc);
	lblClass.setText(Local.getString("Class"));
	gbc = new GridBagConstraints();
	gbc.gridx = 0; gbc.gridy = 1;
	gbc.anchor = GridBagConstraints.WEST;
	gbc.insets = new Insets(5, 10, 5, 5);
	areaPanel.add(lblClass, gbc);		
	classField.setPreferredSize(new Dimension(300, 25));
	gbc = new GridBagConstraints();
	gbc.gridx = 1; gbc.gridy = 1;
	gbc.anchor = GridBagConstraints.WEST;
	gbc.insets = new Insets(5, 5, 5, 10);
	areaPanel.add(classField, gbc);
	lblStyle.setText(Local.getString("Style"));
	gbc = new GridBagConstraints();
	gbc.gridx = 0; gbc.gridy = 2;
	gbc.anchor = GridBagConstraints.WEST;
	gbc.insets = new Insets(5, 10, 10, 5);
	areaPanel.add(lblStyle, gbc);
	styleField.setPreferredSize(new Dimension(300, 25));
	gbc = new GridBagConstraints();
	gbc.gridx = 1; gbc.gridy = 2;
	gbc.anchor = GridBagConstraints.WEST;
	gbc.insets = new Insets(5, 5, 10, 10);
	areaPanel.add(styleField, gbc);
	this.getContentPane().add(areaPanel, BorderLayout.CENTER);				
		
    cancelB.setMaximumSize(new Dimension(100, 26));
    cancelB.setMinimumSize(new Dimension(100, 26));
    cancelB.setPreferredSize(new Dimension(100, 26));
    cancelB.setText(Local.getString("Cancel"));
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });
    okB.setMaximumSize(new Dimension(100, 26));
    okB.setMinimumSize(new Dimension(100, 26));
    okB.setPreferredSize(new Dimension(100, 26));
    okB.setText(Local.getString("Ok"));
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    buttonsPanel.add(okB);
    buttonsPanel.add(cancelB);
    this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

  }

  void okB_actionPerformed(ActionEvent e) {
    this.dispose();
  }

  void cancelB_actionPerformed(ActionEvent e) {
    CANCELLED = true;
    this.dispose();
  }

}