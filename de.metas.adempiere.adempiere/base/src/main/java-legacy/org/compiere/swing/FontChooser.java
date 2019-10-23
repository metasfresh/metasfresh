/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.swing;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.plaf.PlafRes;

/**
 *  Font Chooser Dialog
 *
 *  @author     Jorg Janke
 *  @version    $Id: FontChooser.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class FontChooser extends CDialog
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -755094979517882166L;

	/**
	 *  Show Dialog with initial font and return selected font
	 *  @param owner Base window
	 *  @param title Chooser Title
	 *  @param initFont initial font
	 *  @return selected font
	 */
	public static Font showDialog (Dialog owner, String title, Font initFont)
	{
		Font retValue = initFont;
		FontChooser fc = new FontChooser(owner, title, initFont);
		retValue = fc.getFont();
		fc = null;
		return retValue;
	}   //  showDialog

	
	/**************************************************************************
	 *  Constructor
	 *
	 *  @param owner Base window
	 *  @param title Chooser Title
	 *  @param initFont Initial Font
	 */
	public FontChooser(Dialog owner, String title, Font initFont)
	{
		super(owner, title, true);
		try
		{
			jbInit();
			dynInit();
			setFont(initFont);
			AdempierePLAF.showCenterScreen(this);
		}
		catch(Exception ex)
		{
			System.err.println ("FontChooser");
			ex.printStackTrace();
		}
	}   //  FontChooser

	/**
	 *  IDE Constructor
	 */
	public FontChooser()
	{
		this (null, s_res.getString("FontChooser"), null);
	}   //  FontChooser

	private static final ResourceBundle s_res = PlafRes.getBundle();

	/** Static list of Styles       */
	public static FontStyle[] s_list = {
		new FontStyle(s_res.getString("Plain"), Font.PLAIN),
		new FontStyle(s_res.getString("Italic"), Font.ITALIC),
		new FontStyle(s_res.getString("Bold"), Font.BOLD),
		new FontStyle(s_res.getString("BoldItalic"), Font.BOLD|Font.ITALIC)};

	private Font        m_font = super.getFont();
	private Font        m_retFont = null;

	private boolean     m_setting = false;

	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel selectPanel = new CPanel();
	private CLabel nameLabel = new CLabel();
	private CComboBox<String> fontName = new CComboBox<>();
	private CLabel sizeLabel = new CLabel();
	private CLabel styleLabel = new CLabel();
	private CComboBox<FontStyle> fontStyle = new CComboBox<>();
	private CComboBox<String> fontSize = new CComboBox<>();
	private JTextArea fontTest = new JTextArea();
	private JTextArea fontInfo = new JTextArea();
	private GridBagLayout selectLayout = new GridBagLayout();
	private CPanel confirmPanel = new CPanel();
	private CButton bCancel = AdempierePLAF.getCancelButton();
	private CButton bOK = AdempierePLAF.getOKButton();
	private FlowLayout confirmLayout = new FlowLayout();

	/**
	 *  Static Layout
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		mainPanel.setLayout(mainLayout);
		nameLabel.setText(s_res.getString("Name"));
		selectPanel.setLayout(selectLayout);
		sizeLabel.setText(s_res.getString("Size"));
		styleLabel.setText(s_res.getString("Style"));
		fontTest.setText(s_res.getString("TestString"));
		fontTest.setLineWrap(true);
		fontTest.setWrapStyleWord(true);
		fontTest.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		fontTest.setBorder(BorderFactory.createLoweredBevelBorder());
		fontTest.setPreferredSize(new Dimension(220, 100));
		fontInfo.setText(s_res.getString("FontString"));
		fontInfo.setLineWrap(true);
		fontInfo.setWrapStyleWord(true);
		fontInfo.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		fontInfo.setOpaque(false);
		fontInfo.setEditable(false);
		confirmPanel.setLayout(confirmLayout);
		confirmLayout.setAlignment(FlowLayout.RIGHT);
		confirmPanel.setOpaque(false);
		selectPanel.setOpaque(false);
		getContentPane().add(mainPanel);
		mainPanel.add(selectPanel, BorderLayout.CENTER);
		selectPanel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		selectPanel.add(fontName,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		selectPanel.add(sizeLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		selectPanel.add(styleLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		selectPanel.add(fontStyle,  new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		selectPanel.add(fontSize,  new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		selectPanel.add(fontTest,  new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(20, 5, 5, 5), 0, 0));
		selectPanel.add(fontInfo, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 5, 10, 5), 0, 0));
		//
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.add(bCancel, null);
		confirmPanel.add(bOK, null);
		bCancel.addActionListener(this);
		bOK.addActionListener(this);
	}   //  jbInit

	/**
	 *  Dynamic Init
	 */
	private void dynInit()
	{
		String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		Arrays.sort(names);
		for (int i = 0; i < names.length; i++)
			fontName.addItem(names[i]);
		fontName.addActionListener(this);
		//
		for (int i = 6; i < 32; i++)
			fontSize.addItem(String.valueOf(i));
		fontSize.addActionListener(this);
		//
		for (int i = 0; i < s_list.length; i++)
			fontStyle.addItem(s_list[i]);
		fontStyle.addActionListener(this);
	}   //  dynInit

	/**
	 *  Set Font - sets font for chooser - not the component font
	 *  @param font
	 */
	@Override
	public void setFont(Font font)
	{
		if (font == null)
			return;
	//	Log.trace("FontChooser.setFont - " + font.toString());
		if (m_retFont == null)
			m_retFont = font;
		//
		fontTest.setFont(font);
		fontInfo.setFont(font);
		fontInfo.setText(font.toString());
		//
		m_setting = true;
		fontName.setSelectedItem(font.getName());
		if (!fontName.getSelectedItem().equals(font.getName()))
			System.err.println("FontChooser.setFont" + fontName.getSelectedItem().toString() + " <> " + font.getName());
		//
		fontSize.setSelectedItem(String.valueOf(font.getSize()));
		if (!fontSize.getSelectedItem().equals(String.valueOf(font.getSize())))
			System.err.println("FontChooser.setFont" + fontSize.getSelectedItem() + " <> " + font.getSize());
		//  find style
		for (int i = 0; i < s_list.length; i++)
			if (s_list[i].getID() == font.getStyle())
				fontStyle.setSelectedItem(s_list[i]);
		if (fontStyle.getSelectedItem().getID() != font.getStyle())
			System.err.println("FontChooser.setFont" + fontStyle.getSelectedItem().getID() + " <> " + font.getStyle());
		//
		m_font = font;
		this.pack();
		m_setting = false;
	}   //  setFont

	/**
	 *  Return selected font
	 *  @return font
	 */
	@Override
	public Font getFont()
	{
		return m_retFont;
	}   //  getFont

	/**
	 *  ActionListener
	 *  @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (m_setting)
			return;

		if (e.getSource() == bOK)
		{
			m_retFont = m_font;
			dispose();
		}

		else if (e.getSource() == bCancel)
			dispose();

		else if (e.getSource() == fontName)
		{
			String s = fontName.getSelectedItem().toString();
			m_font = new Font(s, m_font.getStyle(), m_font.getSize());
		}
		else if (e.getSource() == fontSize)
		{
			String s = fontSize.getSelectedItem().toString();
			m_font = new Font(m_font.getName(), m_font.getStyle(), Integer.parseInt(s));
		}
		else if (e.getSource() == fontStyle)
		{
			FontStyle fs = fontStyle.getSelectedItem();
			m_font = new Font(m_font.getName(), fs.getID(), m_font.getSize());
		}
	//	System.out.println("NewFont - " + m_font.toString());
		setFont(m_font);
	}   //  actionPerformed
}   //  FontChooser

/**
 *  Font Style Value Object
 */
class FontStyle
{
	/**
	 *  Create FontStyle
	 *  @param name
	 *  @param id
	 */
	public FontStyle(String name, int id)
	{
		m_name = name;
		m_id = id;
	}   //  FontStyle

	private String  m_name;
	private int     m_id;

	/**
	 *  Get Name
	 *  @return name
	 */
	@Override
	public String toString()
	{
		return m_name;
	}   //  getName

	/**
	 *  Get int value of Font Style
	 *  @return id
	 */
	public int getID()
	{
		return m_id;
	}   //  getID
}   //  FontStyle
