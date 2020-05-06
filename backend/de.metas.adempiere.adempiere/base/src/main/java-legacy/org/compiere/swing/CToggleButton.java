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

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JToggleButton;

import org.adempiere.plaf.AdempiereLookAndFeel;
import org.adempiere.plaf.AdempierePLAF;

import de.metas.util.MFColor;

/**
 *  Adempiere Color Taggle Button
 *
 *  @author     Jorg Janke
 *  @version    $Id: CToggleButton.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CToggleButton extends JToggleButton implements CEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3527812520795669992L;

	/**
	 * Creates an initially unselected toggle button
	 * without setting the text or image.
	 */
	public CToggleButton () 
	{
		this(null, null, false);
	}

	/**
	 * Creates an initially unselected toggle button
	 * with the specified image but no text.
	 *
	 * @param icon  the image that the button should display
	 */
	public CToggleButton(Icon icon) 
	{
		this(null, icon, false);
	}

	/**
	 * Creates a toggle button with the specified image
	 * and selection state, but no text.
	 *
	 * @param icon  the image that the button should display
	 * @param selected  if true, the button is initially selected;
	 *                  otherwise, the button is initially unselected
	 */
	public CToggleButton(Icon icon, boolean selected) 
	{
		this(null, icon, selected);
	}

	/**
	 * Creates an unselected toggle button with the specified text.
	 *
	 * @param text  the string displayed on the toggle button
	 */
	public CToggleButton (String text) 
	{
		this(text, null, false);
	}

	/**
	 * Creates a toggle button with the specified text
	 * and selection state.
	 *
	 * @param text  the string displayed on the toggle button
	 * @param selected  if true, the button is initially selected;
	 *                  otherwise, the button is initially unselected
	 */
	public CToggleButton (String text, boolean selected) 
	{
		this(text, null, selected);
	}

	/**
	 * Creates a toggle button where properties are taken from the
	 * Action supplied.
	 * @param a
	 */
	public CToggleButton(Action a) 
	{
		this(null, null, false);
		setAction(a);
	}

	/**
	 * Creates a toggle button that has the specified text and image,
	 * and that is initially unselected.
	 *
	 * @param text the string displayed on the button
	 * @param icon  the image that the button should display
	 */
	public CToggleButton(String text, Icon icon) 
	{
		this(text, icon, false);
	}

	/**
	 * Creates a toggle button with the specified text, image, and
	 * selection state.
	 *
	 * @param text the text of the toggle button
	 * @param icon  the image that the button should display
	 * @param selected  if true, the button is initially selected;
	 *                  otherwise, the button is initially unselected
	 */
	public CToggleButton (String text, Icon icon, boolean selected)
	{
		super(text, icon, selected);
	}

	/*************************************************************************/

	/**
	 *  Set Background - Differentiates between system & user call.
	 *  If User Call, sets Opaque & ContextAreaFilled to true
	 *  @param bg
	 */
	@Override
	public void setBackground(Color bg)
	{
		if (bg.equals(getBackground()))
			return;
		super.setBackground( bg);
	}   //  setBackground

	/**
	 *  Set Background - NOP
	 *  @param error
	 */
	@Override
	public void setBackground (boolean error)
	{
	}   //  setBackground

	/**
	 *  Set Standard Background
	 */
	public void setBackgroundColor ()
	{
		setBackgroundColor (null);
	}   //  setBackground

	/**
	 *  Set Background
	 *  @param bg AdempiereColor for Background, if null set standard background
	 */
	public void setBackgroundColor (MFColor bg)
	{
		if (bg == null)
			bg = MFColor.ofFlatColor(AdempierePLAF.getFormBackground());
		setOpaque(true);
		putClientProperty(AdempiereLookAndFeel.BACKGROUND, bg);
		super.setBackground (bg.getFlatColor());
	}   //  setBackground

	/**
	 *  Get Background
	 *  @return Color for Background
	 */
	public MFColor getBackgroundColor ()
	{
		try
		{
			return (MFColor)getClientProperty(AdempiereLookAndFeel.BACKGROUND);
		}
		catch (Exception e)
		{
			System.err.println("CButton - ClientProperty: " + e.getMessage());
		}
		return null;
	}   //  getBackgroundColor

	/** Mandatory (default false)   */
	private boolean m_mandatory = false;

	/**
	 *	Set Editor Mandatory
	 *  @param mandatory true, if you have to enter data
	 */
	@Override
	public void setMandatory (boolean mandatory)
	{
		m_mandatory = mandatory;
		setBackground(false);
	}   //  setMandatory

	/**
	 *	Is Field mandatory
	 *  @return true, if mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return m_mandatory;
	}   //  isMandatory

	/**
	 *	Enable Editor
	 *  @param rw true, if you can enter/select data
	 */
	@Override
	public void setReadWrite (boolean rw)
	{
		if (super.isEnabled() != rw)
			super.setEnabled(rw);
	}   //  setReadWrite

	/**
	 *	Is it possible to edit
	 *  @return true, if editable
	 */
	@Override
	public boolean isReadWrite()
	{
		return super.isEnabled();
	}   //  isReadWrite

	/**
	 *	Set Editor to value
	 *  @param value value of the editor
	 */
	@Override
	public void setValue (Object value)
	{
		if (value == null)
			setText("");
		else
			setText(value.toString());
	}   //  setValue

	/**
	 *	Return Editor value
	 *  @return current value
	 */
	@Override
	public Object getValue()
	{
		return getText();
	}   //  getValue

	/**
	 *  Return Display Value
	 *  @return displayed String value
	 */
	@Override
	public String getDisplay()
	{
		return getText();
	}   //  getDisplay

}   //  CToggleButton
