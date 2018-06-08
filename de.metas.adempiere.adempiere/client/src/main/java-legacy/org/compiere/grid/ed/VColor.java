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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.JFrame;

import org.adempiere.plaf.AdempiereLookAndFeel;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.plaf.CompiereColor;
import org.compiere.swing.CButton;
import org.compiere.swing.ColorEditor;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.Env;

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
//	private int             m_AD_Color_ID = 0;
	private CompiereColor   m_cc = null;
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
		m_cc = getAdempiereColor();

		//  Display It
		setText(getDisplay());
		if (m_cc != null)
			setBackgroundColor(m_cc);
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
		if (m_cc == null)
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
	
	/*************************************************************************/

	/**
	 *  Load Color from Tab
	 *  @return true if loaded
	 *  @see org.compiere.model.MColor#getAdempiereColor
	 */
	private CompiereColor getAdempiereColor()
	{
		Integer AD_Color_ID = (Integer)m_mTab.getValue("AD_Color_ID");
		log.debug("AD_Color_ID=" + AD_Color_ID);
		CompiereColor cc = null;

		//  Color Type
		String ColorType = (String)m_mTab.getValue("ColorType");
		if (ColorType == null)
		{
			log.debug("No ColorType");
			return null;
		}
		//
		if (ColorType.equals(CompiereColor.TYPE_FLAT))
		{
			cc = new CompiereColor(getColor(true), true);
		}
		else if (ColorType.equals(CompiereColor.TYPE_GRADIENT))
		{
			Integer RepeatDistance = (Integer)m_mTab.getValue("RepeatDistance");
			String StartPoint = (String)m_mTab.getValue("StartPoint");
			int repeatDistance = RepeatDistance == null ? 0 : RepeatDistance.intValue();
			int startPoint = StartPoint == null ? 0 : Integer.parseInt(StartPoint);
			cc = new CompiereColor(getColor(true), getColor(false), startPoint, repeatDistance);
		}
		else if (ColorType.equals(CompiereColor.TYPE_LINES))
		{
			BigDecimal LineWidth = (BigDecimal)m_mTab.getValue("LineWidth");
			BigDecimal LineDistance = (BigDecimal)m_mTab.getValue("LineDistance");
			int lineWidth = LineWidth == null ? 0 : LineWidth.intValue();
			int lineDistance = LineDistance == null ? 0 : LineDistance.intValue();
			cc = new CompiereColor(getColor(false), getColor(true), lineWidth, lineDistance);
		}
		else if (ColorType.equals(CompiereColor.TYPE_TEXTURE))
		{
			Integer AD_Image_ID = (Integer)m_mTab.getValue("AD_Image_ID");
			String url = getURL(AD_Image_ID);
			if (url == null)
				return null;
			BigDecimal ImageAlpha = (BigDecimal)m_mTab.getValue("ImageAlpha");
			float compositeAlpha = ImageAlpha == null ? 0.7f : ImageAlpha.floatValue();
			cc = new CompiereColor(url, getColor(true), compositeAlpha);
		}
		else
			return null;

		log.debug("AdempiereColor=" + cc);
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
	private String getURL (Integer AD_Image_ID)
	{
		if (AD_Image_ID == null || AD_Image_ID.intValue() == 0)
			return null;
		//
		String retValue = null;
		String sql = "SELECT ImageURL FROM AD_Image WHERE AD_Image_ID=?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt (1, AD_Image_ID.intValue());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getString(1);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}
		return retValue;
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
		CompiereColor cc = ColorEditor.showDialog((JFrame)Env.getParent(this), m_cc);
		if (cc == null)
		{
			log.info( "VColor.actionPerformed - no color");
			return;
		}
		setBackgroundColor(cc);		//	set Button
		repaint();

		//  Update Values
		m_mTab.setValue("ColorType", cc.getType());
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
		m_cc = cc;
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
