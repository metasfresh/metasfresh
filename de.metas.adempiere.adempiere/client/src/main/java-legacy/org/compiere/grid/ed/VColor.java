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
package org.compiere.grid.ed;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.net.URL;

import javax.swing.JFrame;

import org.adempiere.plaf.AdempiereLookAndFeel;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MImage;
import org.compiere.swing.CButton;
import org.compiere.swing.ColorEditor;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.MFColorType;
import de.metas.util.MFColor;

/**
 *  Color Editor.
 *  The editor stores/gets the attributes from the tab.
 *
 *  @author     Jorg Janke
 *  @version    $Id: VColor.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VColor extends CButton
	implements VEditor, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3881508174949938138L;

	/**
	 *  Constructor
	 *  @param mTab	Tab
	 *  @param mandatory mandatory
	 *  @param isReadOnly read only
	 */
	public VColor (GridTab mTab, boolean mandatory, boolean isReadOnly)
	{
		m_mTab = mTab;
		setMandatory(mandatory);
		setReadWrite(!isReadOnly);
		addActionListener(this);
	}   //  VColor

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
		m_mTab = null;
	}   //  dispose

	private GridTab            m_mTab;
	private boolean         m_mandatory;
	private MFColor   color = null;
	private Object          m_value;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VColor.class);

	/**
	 *  Set Mandatory
	 *  @param mandatory mandatory
	 */
	@Override
	public void setMandatory (boolean mandatory)
	{
		m_mandatory = mandatory;
	}   //  setMandatory

	/**
	 *  Is Mandatory
	 *  @return true if Mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return m_mandatory;
	}   //  isMandatory

	/**
	 *  Set Background (nop)
	 *  @param error error
	 */
	@Override
	public void setBackground (boolean error)
	{
	}   //  setBackground

	/**
	 *  Set Value
	 *  @param value value
	 */
	@Override
	public void setValue (Object value)
	{
		log.info("Value=" + value);
		m_value = value;
		color = getAdempiereColor();

		//  Display It
		setText(getDisplay());
		if (color != null)
			setBackgroundColor(color);
		else
		{
			setOpaque(false);
			putClientProperty(AdempiereLookAndFeel.BACKGROUND, null);
		}
		repaint();
	}   //  setValue

	/**
	 *  GetValue
	 *  @return value
	 */
	@Override
	public Object getValue()
	{
		return m_value;
	}   //  getValue

	/**
	 *  Get Displayed Value
	 *  @return String representation
	 */
	@Override
	public String getDisplay()
	{
		if (color == null)
			return "-/-";
		return " ";
	}   //  getDisplay

	/**
	 *  Property Change Listener
	 *  @param evt event
	 */
	@Override
	public void propertyChange (PropertyChangeEvent evt)
	{
	//	log.info( "VColor.propertyChange", evt);
		if (evt.getPropertyName().equals(org.compiere.model.GridField.PROPERTY))
		{
			setValue(evt.getNewValue());
			setBackground(false);
		}
	}   //  propertyChange

	//	Field for Value Preference
	private GridField          m_mField = null;
	/**
	 *  Set Field/WindowNo for ValuePreference
	 *  @param mField field
	 */
	@Override
	public void setField (GridField mField)
	{
		mField.setValueNoFire(false);  //  fire every time
		m_mField = mField;
	}   //  setField

	@Override
	public GridField getField() {
		return m_mField;
	}
	
	private MFColor getAdempiereColor()
	{
		final Integer adColorId = (Integer)m_mTab.getValue("AD_Color_ID");
		log.debug("AD_Color_ID=" + adColorId);
		MFColor cc = null;

		//  Color Type
		final String colorTypeCode = (String)m_mTab.getValue("ColorType");
		if (colorTypeCode == null)
		{
			log.debug("No ColorType");
			return null;
		}
		//
		final MFColorType colorType = MFColorType.ofCode(colorTypeCode);
		if (colorType == MFColorType.FLAT)
		{
			cc = MFColor.ofFlatColor(getColor(true));
		}
		else if (colorType == MFColorType.GRADIENT)
		{
			Integer repeatDistanceObj = (Integer)m_mTab.getValue("RepeatDistance");
			int repeatDistance = repeatDistanceObj == null ? 0 : repeatDistanceObj.intValue();
			String startPointStr = (String)m_mTab.getValue("StartPoint");
			int startPoint = startPointStr == null ? 0 : Integer.parseInt(startPointStr);
			cc = MFColor.ofGradientColor(getColor(true), getColor(false), startPoint, repeatDistance);
		}
		else if (colorType == MFColorType.LINES)
		{
			BigDecimal LineWidth = (BigDecimal)m_mTab.getValue("LineWidth");
			BigDecimal LineDistance = (BigDecimal)m_mTab.getValue("LineDistance");
			int lineWidth = LineWidth == null ? 0 : LineWidth.intValue();
			int lineDistance = LineDistance == null ? 0 : LineDistance.intValue();
			cc = MFColor.ofLinesColor(getColor(false), getColor(true), lineWidth, lineDistance);
		}
		else if (colorType == MFColorType.TEXTURE)
		{
			final Integer adImageId = (Integer)m_mTab.getValue("AD_Image_ID");
			final URL url = getURL(adImageId);
			if (url == null)
				return null;
			final BigDecimal ImageAlpha = (BigDecimal)m_mTab.getValue("ImageAlpha");
			float compositeAlpha = ImageAlpha == null ? 0.7f : ImageAlpha.floatValue();
			cc = MFColor.ofTextureColor(url, getColor(true), compositeAlpha);
		}
		else
		{
			return null;
		}

		log.debug("AdempiereColor={}", cc);
		return cc;
	}   //  getAdempiereColor

	/**
	 *  Get Color from Tab
	 *  @param primary true if primary false if secondary
	 *  @return Color
	 */
	private Color getColor (boolean primary)
	{
		String add = primary ? "" : "_1";
		//	is either BD or Int
		Integer Red = (Integer)m_mTab.getValue("Red" + add);
		Integer Green = (Integer)m_mTab.getValue("Green" + add);
		Integer Blue = (Integer)m_mTab.getValue("Blue" + add);
		//
		int red = Red == null ? 0 : Red.intValue();
		int green = Green == null ? 0 : Green.intValue();
		int blue = Blue == null ? 0 : Blue.intValue();
		//
		return new Color (red, green, blue);
	}   //  getColor

	/**
	 *  Get URL from Image
	 *  @param AD_Image_ID image
	 *  @return URL as String or null
	 */
	private URL getURL(final Integer AD_Image_ID)
	{
		if (AD_Image_ID == null || AD_Image_ID.intValue() <= 0)
			return null;
		
		return MImage.getURLOrNull(AD_Image_ID);
	}   //  getURL

	/*************************************************************************/

	/**
	 *  Action Listener - Open Dialog
	 *  @param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		//  Show Dialog
		MFColor cc = ColorEditor.showDialog((JFrame)Env.getParent(this), color);
		if (cc == null)
		{
			log.info( "VColor.actionPerformed - no color");
			return;
		}
		setBackgroundColor(cc);		//	set Button
		repaint();

		//  Update Values
		m_mTab.setValue("ColorType", cc.getType().getCode());
		if (cc.isFlat())
		{
			setColor (cc.getFlatColor(), true);
		}
		else if (cc.isGradient())
		{
			setColor (cc.getGradientUpperColor(), true);
			setColor (cc.getGradientLowerColor(), false);
			m_mTab.setValue("RepeatDistance",   new BigDecimal(cc.getGradientRepeatDistance()));
			m_mTab.setValue("StartPoint",       String.valueOf(cc.getGradientStartPoint()));
		}
		else if (cc.isLine())
		{
			setColor (cc.getLineBackColor(), true);
			setColor (cc.getLineColor(), false);
			m_mTab.getValue("LineWidth");
			m_mTab.getValue("LineDistance");
		}
		else if (cc.isTexture())
		{
			setColor (cc.getTextureTaintColor(), true);
		//	URL url = cc.getTextureURL();
		//	m_mTab.setValue("AD_Image_ID");
			m_mTab.setValue("ImageAlpha", new BigDecimal(cc.getTextureCompositeAlpha()));
		}
		color = cc;
	}   //  actionPerformed

	/**
	 *  Set Color in Tab
	 *  @param c Color
	 *  @param primary true if primary false if secondary
	 */
	private void setColor (Color c, boolean primary)
	{
		String add = primary ? "" : "_1";
		m_mTab.setValue("Red" + add,    new BigDecimal(c.getRed()));
		m_mTab.setValue("Green" + add,  new BigDecimal(c.getGreen()));
		m_mTab.setValue("Blue" + add,   new BigDecimal(c.getBlue()));
	}   //  setColor

	// metas: Ticket#2011062310000013 
	@Override
	public boolean isAutoCommit()
	{
		return false;
	}

	
}   //  VColor
