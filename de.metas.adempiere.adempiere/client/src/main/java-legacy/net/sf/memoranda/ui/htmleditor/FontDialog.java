package net.sf.memoranda.ui.htmleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import net.sf.memoranda.ui.htmleditor.util.Local;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class FontDialog extends JDialog {
    JPanel areaPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc;
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    JButton cancelB = new JButton();
    JButton okB = new JButton();
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    public JLabel header = new JLabel();
    public boolean CANCELLED = false;
    public JComboBox fontSizeCB = new JComboBox(new Object[] 
    	{"", "1","2","3","4","5","6","7"});
    public JComboBox fontFamilyCB;
    public JLabel sample = new JLabel();
    JPanel samplePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    public JTextField colorField = new JTextField();
    JLabel lblTextColor = new JLabel();
    JButton colorB = new JButton();

    public FontDialog(Frame frame) {
        super(frame, Local.getString("Text properties"), true);
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public FontDialog() {
        this(null);
    }

    void jbInit() throws Exception {
	this.setResizable(false);
        GraphicsEnvironment gEnv = 
        	GraphicsEnvironment.getLocalGraphicsEnvironment();
        String envfonts[] = gEnv.getAvailableFontFamilyNames();
        Vector fonts = new Vector();
        fonts.add("");
        fonts.add("serif");
        fonts.add("sans-serif");
        fonts.add("monospaced");
        for (int i = 0; i < envfonts.length; i++)
            fonts.add(envfonts[i]);
        fontFamilyCB = new JComboBox(fonts);
        
		headerPanel.setBackground(Color.WHITE);
		header.setFont(new java.awt.Font("Dialog", 0, 20));
		header.setForeground(new Color(0, 0, 124));
		header.setText(Local.getString("Text properties"));
		header.setIcon(new ImageIcon(
			net.sf.memoranda.ui.htmleditor.ImageDialog.class.getResource(
			"resources/icons/fontbig.png")));
		headerPanel.add(header);
		this.getContentPane().add(headerPanel, BorderLayout.NORTH);
		        
        areaPanel.setBorder(BorderFactory.createEtchedBorder(
        	Color.white, new Color(142, 142, 142)));
		fontFamilyCB.setMaximumRowCount(9);
		fontFamilyCB.setBorder(new TitledBorder(
			BorderFactory.createEmptyBorder(), 
			Local.getString("Font family")));
		fontFamilyCB.setPreferredSize(new Dimension(200, 50));
		fontFamilyCB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontChanged(e);
			}
		});
        gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 5, 5);
        areaPanel.add(fontFamilyCB, gbc);
		fontSizeCB.setEditable(true);
		fontSizeCB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fontChanged(e);
			}
		});
		fontSizeCB.setBorder(new TitledBorder(
			BorderFactory.createEmptyBorder(), Local.getString("Font size")));
		fontSizeCB.setPreferredSize(new Dimension(60, 50));
		gbc = new GridBagConstraints();
		gbc.gridx = 2; gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 5, 10);
		areaPanel.add(fontSizeCB, gbc);
		lblTextColor.setText(Local.getString("Font color"));		
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 20, 5, 5);
		areaPanel.add(lblTextColor, gbc);
		colorField.setPreferredSize(new Dimension(60, 25));
		gbc = new GridBagConstraints();
		gbc.gridx = 1; gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		areaPanel.add(colorField, gbc);
		colorB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorB_actionPerformed(e);
			}
		});
		colorB.setIcon(new ImageIcon(
			net.sf.memoranda.ui.htmleditor.FontDialog.class.getResource(
			"resources/icons/color.png")));
		colorB.setPreferredSize(new Dimension(25, 25));
		gbc = new GridBagConstraints();
		gbc.gridx = 2; gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 5, 5, 5);
		areaPanel.add(colorB, gbc);
		samplePanel.setBackground(Color.white);
		samplePanel.setBorder(BorderFactory.createTitledBorder(
			Local.getString("Sample")));
		sample.setText(Local.getString("AaBbCcDd"));
		sample.setHorizontalAlignment(SwingConstants.CENTER);
		sample.setVerticalAlignment(SwingConstants.CENTER);
		sample.setPreferredSize(new Dimension(250, 50));
		samplePanel.add(sample);		
		gbc = new GridBagConstraints();
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 10, 10, 10);
		areaPanel.add(samplePanel, gbc);
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
        this.getRootPane().setDefaultButton(okB);
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);
        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    }

    void okB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        CANCELLED = true;
        this.dispose();
    }

    void fontChanged(ActionEvent e) {
        int[] sizes = {8,10,13,16,18,24,32};
        int size = 16;
        String face;
        Font font = sample.getFont();
        if (fontSizeCB.getSelectedIndex() > 0)
            size = sizes[fontSizeCB.getSelectedIndex()-1];
        if (fontFamilyCB.getSelectedIndex() >0)
             face = (String)fontFamilyCB.getSelectedItem();
        else face = font.getName();        
        sample.setFont(new Font(face,Font.PLAIN, size));
    }
   
    void colorB_actionPerformed(ActionEvent e) {
		// Fix until Sun's JVM supports more locales...
		UIManager.put(
			"ColorChooser.swatchesNameText",
			Local.getString("Swatches"));
		UIManager.put("ColorChooser.hsbNameText", Local.getString("HSB"));
		UIManager.put("ColorChooser.rgbNameText", Local.getString("RGB"));
		UIManager.put(
			"ColorChooser.swatchesRecentText",
			Local.getString("Recent:"));
		UIManager.put("ColorChooser.previewText", Local.getString("Preview"));
		UIManager.put(
			"ColorChooser.sampleText",
			Local.getString("Sample Text")
				+ " "
				+ Local.getString("Sample Text"));
		UIManager.put("ColorChooser.okText", Local.getString("OK"));
		UIManager.put("ColorChooser.cancelText", Local.getString("Cancel"));
		UIManager.put("ColorChooser.resetText", Local.getString("Reset"));
		UIManager.put("ColorChooser.hsbHueText", Local.getString("H"));
		UIManager.put("ColorChooser.hsbSaturationText", Local.getString("S"));
		UIManager.put("ColorChooser.hsbBrightnessText", Local.getString("B"));
		UIManager.put("ColorChooser.hsbRedText", Local.getString("R"));
		UIManager.put("ColorChooser.hsbGreenText", Local.getString("G"));
		UIManager.put("ColorChooser.hsbBlueText", Local.getString("B2"));
		UIManager.put("ColorChooser.rgbRedText", Local.getString("Red"));
		UIManager.put("ColorChooser.rgbGreenText", Local.getString("Green"));
		UIManager.put("ColorChooser.rgbBlueText", Local.getString("Blue"));        
        Color c = JColorChooser.showDialog(this, Local.getString("Font color"), 
        	Util.decodeColor(colorField.getText()));
        if (c == null) return;
        colorField.setText(Util.encodeColor(c));
        Util.setColorField(colorField);
        sample.setForeground(c);
    }

}