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
import javax.swing.JCheckBox;
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

public class FindDialog extends JDialog {
	JPanel areaPanel = new JPanel(new GridBagLayout());
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	JButton cancelB = new JButton();
	JButton okB = new JButton();
	JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	public JLabel header = new JLabel();
	public boolean CANCELLED = false;
	JLabel lblSearch = new JLabel();
	public JTextField txtSearch = new JTextField();
	public JCheckBox chkReplace = new JCheckBox();
	public JCheckBox chkCaseSens = new JCheckBox();
	public JCheckBox chkWholeWord = new JCheckBox();
	public JCheckBox chkRegExp = new JCheckBox();
	public JTextField txtReplace = new JTextField();
	GridBagConstraints gbc;

	public FindDialog(Frame frame) {
		super(frame, Local.getString("Find & replace"), true);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public FindDialog() {
		this(null);
	}

	void jbInit() throws Exception {
		this.setResizable(false);
		// Build Header and its layout
		
		header.setFont(new java.awt.Font("Dialog", 0, 20));
		header.setForeground(new Color(0, 0, 124));
		header.setText(Local.getString("Find & replace"));
		header.setIcon(
			new ImageIcon(
				net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource(
					"resources/icons/findbig.png")));
		headerPanel.setBackground(Color.WHITE);
		headerPanel.add(header);
		this.getContentPane().add(headerPanel, BorderLayout.NORTH);

		// build areaPanel
		lblSearch.setText(Local.getString("Search for") + ":");
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 5, 0);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(lblSearch, gbc);
		txtSearch.setPreferredSize(new Dimension(300, 25));
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(txtSearch, gbc);
		chkWholeWord.setText(Local.getString("Whole words only"));
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.insets = new Insets(5, 10, 5, 25);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(chkWholeWord, gbc);
		chkRegExp.setText(Local.getString("Regular expressions"));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 2;
		gbc.insets = new Insets(5, 25, 5, 10);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(chkRegExp, gbc);
		chkCaseSens.setText(Local.getString("Case sensitive"));
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 3;
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(chkCaseSens, gbc);
		chkReplace.setText(Local.getString("Replace with") + ":");
		chkReplace.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceChB_actionPerformed(e);
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 4;
		gbc.insets = new Insets(5, 10, 5, 10);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(chkReplace, gbc);
		txtReplace.setPreferredSize(new Dimension(300, 25));
		txtReplace.setEnabled(false);
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;
		areaPanel.add(txtReplace, gbc);
		areaPanel.setBorder(BorderFactory.createEtchedBorder(
			Color.white, new Color(142, 142, 142)));
		this.getContentPane().add(areaPanel, BorderLayout.CENTER);

		// Initialize buttons
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
		okB.setText(Local.getString("Find"));
		okB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okB_actionPerformed(e);
			}
		});
		this.getRootPane().setDefaultButton(okB);
		// build button-panel
		buttonsPanel.add(okB);
		buttonsPanel.add(cancelB);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
	}

	void okB_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void cancelB_actionPerformed(ActionEvent e) {
		CANCELLED = true;
		this.dispose();
	}

	void replaceChB_actionPerformed(ActionEvent e) {
		txtReplace.setEnabled(chkReplace.isSelected());
	}

}