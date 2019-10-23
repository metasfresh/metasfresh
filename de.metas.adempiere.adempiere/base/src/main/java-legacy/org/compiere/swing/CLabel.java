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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

/**
 *  Label with Mnemonics interpretation
 *
 *  @author     Jorg Janke
 *  @version    $Id: CLabel.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CLabel extends JLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3836688834452881595L;
	public static int   DEFAULT_ALIGNMENT = JLabel.TRAILING;

	/**
	 * Creates a <code>Label</code> instance with the specified
	 * text, image, and horizontal alignment.
	 * The label is centered vertically in its display area.
	 * The text is on the trailing edge of the image.
	 *
	 * @param text  The text to be displayed by the label.
	 * @param icon  The image to be displayed by the label.
	 * @param horizontalAlignment  One of the following constants
	 *           defined in <code>SwingConstants</code>:
	 *           <code>LEFT</code>,
	 *           <code>CENTER</code>,
	 *           <code>RIGHT</code>,
	 *           <code>LEADING</code> or
	 *           <code>TRAILING</code>.
	 */
	public CLabel (String text, Icon icon, int horizontalAlignment)
	{
		super (text, icon, horizontalAlignment);
		init();
	}

	/**
	 * Creates a <code>Label</code> instance with the specified
	 * text and horizontal alignment.
	 * The label is centered vertically in its display area.
	 *
	 * @param text  The text to be displayed by the label.
	 * @param horizontalAlignment  One of the following constants
	 *           defined in <code>SwingConstants</code>:
	 *           <code>LEFT</code>,
	 *           <code>CENTER</code>,
	 *           <code>RIGHT</code>,
	 *           <code>LEADING</code> or
	 *           <code>TRAILING</code>.
	 */
	public CLabel (String text, int horizontalAlignment)
	{
		super(text, horizontalAlignment);
		init();
	}

	/**
	 * Creates a <code>Label</code> instance with the specified text.
	 * The label is aligned against the leading edge of its display area,
	 * and centered vertically.
	 *
	 * @param text  The text to be displayed by the label.
	 */
	public CLabel (String text)
	{
		super(text, DEFAULT_ALIGNMENT);
		init();
	}

	/**
	 * Creates a <code>Label</code> instance with the specified
	 * image and horizontal alignment.
	 * The label is centered vertically in its display area.
	 *
	 * @param image  The image to be displayed by the label.
	 * @param horizontalAlignment  One of the following constants
	 *           defined in <code>SwingConstants</code>:
	 *           <code>LEFT</code>,
	 *           <code>CENTER</code>,
	 *           <code>RIGHT</code>,
	 *           <code>LEADING</code> or
	 *           <code>TRAILING</code>.
	 */
	public CLabel (Icon image, int horizontalAlignment)
	{
		super (image, horizontalAlignment);
		init();
	}

	/**
	 * Creates a <code>Label</code> instance with the specified image.
	 * The label is centered vertically and horizontally
	 * in its display area.
	 *
	 * @param image  The image to be displayed by the label.
	 */
	public CLabel (Icon image)
	{
		super (image, DEFAULT_ALIGNMENT);
		init();
	}

	/**
	 * Creates a <code>JLabel</code> instance with
	 * no image and with an empty string for the title.
	 * The label is centered vertically
	 * in its display area.
	 * The label's contents, once set, will be displayed on the leading edge
	 * of the label's display area.
	 */
	public CLabel ()
	{
		super("", DEFAULT_ALIGNMENT);
		init();
	}

	/**
	 * Creates a <code>Label</code> instance with the specified text.
	 * The label is aligned against the leading edge of its display area,
	 * and centered vertically.
	 *
	 * @param label  The text to be displayed by the label.
	 * @param toolTip   The optional Tooltip text
	 */
	public CLabel (String label, String toolTip)
	{
		super (label, DEFAULT_ALIGNMENT);
		if (toolTip != null && toolTip.length() > 0)
			super.setToolTipText(toolTip);
		init();
	}   //  CLabel

	/**
	 * 	Trailing Label for Field
	 *	@param label label
	 *	@param field field
	 */
	public CLabel (String label, Component field)
	{
		this (label, TRAILING);
		setLabelFor(field);
	}	//	CLabel
	
	
	/** Mnemonic saved			*/
	private char	m_savedMnemonic = 0;
	
	/**
	 *  Common init
	 */
	private void init()
	{
		setFocusable (false);
		setOpaque(false);
		if (getToolTipText() == null)	//	force Tool Tip
			setToolTipText(getText());
	}   //  init


	/**
	 *  Set Background
	 *  @param bg background
	 */
	@Override
	public void setBackground (Color bg)
	{
		if (bg.equals(getBackground()))
			return;
		super.setBackground(bg);
	}   //  setBackground

	/**
	 * 	Set Font to Bold
	 *	@param bold true bold false normal
	 */
	public void setFontBold (boolean bold)
	{
		Font font = getFont();
		if (bold != font.isBold())
		{
			font = new Font (font.getName(), bold ? 
				Font.BOLD : Font.PLAIN,
				font.getSize());
			setFont (font);
		}
	}	//	setFontBold
	
	/**************************************************************************
	 *  Set label text - if it includes &, the next character is the Mnemonic
	 *  @param mnemonicLabel Label containing Mnemonic
	 */
	@Override
	public void setText (String mnemonicLabel)
	{
		String text = createMnemonic (mnemonicLabel);
		super.setText (text);
		if (text != null && getName() == null)
			setName(text);
		//workaround for focus accelerator issue
		if (getLabelFor() != null && getLabelFor() instanceof JTextComponent)
		{
			if ( m_savedMnemonic > 0)
				((JTextComponent)getLabelFor()).setFocusAccelerator(m_savedMnemonic);
			else
				((JTextComponent)getLabelFor()).setFocusAccelerator('\0');
		}
	}   //  setText

	/**
	 *  Create Mnemonics of text containing "&".
	 *	Based on MS notation of &Help => H is Mnemonics
	 *  @param text test with Mnemonics
	 *  @return text w/o &
	 *  @see JLabel#setLabelFor(java.awt.Component)
	 */
	private String createMnemonic(String text)
	{
		m_savedMnemonic = 0;
		if (text == null)
			return text;
		int pos = text.indexOf('&');
		if (pos != -1)					//	We have a nemonic
		{
			char ch = text.charAt(pos+1);
			if (ch != ' ')              //  &_ - is the & character
			{
				setDisplayedMnemonic(ch);
				setSavedMnemonic(ch);
				return text.substring(0, pos) + text.substring(pos+1);
			}
		}
		return text;
	}   //  createMnemonic

	/**
	 *  Set ReadWrite
	 *  @param rw enabled
	 */
	public void setReadWrite (boolean rw)
	{
		this.setEnabled(rw);
	}   //  setReadWrite

	/**
	 * 	Set Label For
	 *	@param c component
	 */
	@Override
	public void setLabelFor (Component c)
	{
		//reset old if any
		if (getLabelFor() != null && getLabelFor() instanceof JTextComponent)
		{
			((JTextComponent)getLabelFor()).setFocusAccelerator('\0');
		}
		super.setLabelFor(c);
		if (c.getName() == null)
			c.setName(getName());
		//workaround for focus accelerator issue
		if (c instanceof JTextComponent)
		{
			if (m_savedMnemonic > 0) 
			{
				((JTextComponent)c).setFocusAccelerator(m_savedMnemonic);
			}
		}
	}	//	setLabelFor

	
	/**
	 * @return Returns the savedMnemonic.
	 */
	public char getSavedMnemonic ()
	{
		return m_savedMnemonic;
	}	//	getSavedMnemonic
	
	/**
	 * @param savedMnemonic The savedMnemonic to set.
	 */
	public void setSavedMnemonic (char savedMnemonic)
	{
		m_savedMnemonic = savedMnemonic;
	}	//	getSavedMnemonic
	
}   //  CLabel
