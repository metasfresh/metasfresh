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
package org.compiere.print;

import java.awt.Color;
import java.util.Properties;

import org.compiere.model.X_AD_PrintColor;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *	AD_PrintColor Print Color Model
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: MPrintColor.java,v 1.3 2006/07/30 00:53:02 jjanke Exp $
 */
public class MPrintColor extends X_AD_PrintColor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8352503254165120016L;

	/**************************************************************************
	 * 	Create Color in Database and save
	 * 	@param color color
	 *  @param name name
	 * 	@return MPrintColor
	 */
	static MPrintColor create (Color color, String name)
	{
		MPrintColor pc = new MPrintColor (Env.getCtx(), 0, null);
		pc.setName(name);
		pc.setColor(color);
		pc.save();
		return pc;
	}	//	create

	/*************************************************************************/

	/** Dark Green			*/
	public static final Color	darkGreen = new Color (0, 128, 0);
	/** Black Green			*/
	public static final Color	blackGreen = new Color (0, 64, 0);
	/** Dark Blue			*/
	public static final Color	darkBlue = new Color (0, 0, 128);
	/** Black Blue			*/
	public static final Color	blackBlue = new Color (0, 0, 64);
	/** White Gray			*/
	public static final Color	whiteGray = new Color (224, 224, 224);
	/** Brown				*/
	public static final Color	brown = new Color (153, 102, 51);
	/** Dark Brown			*/
	public static final Color	darkBrown = new Color (102, 51, 0);

	/*************************************************************************/

	/** Cached Colors						*/
	static private CCache<Integer,MPrintColor> 	s_colors = new CCache<>("AD_PrintColor", 20);
	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(MPrintColor.class);

	/**
	 * 	Get Color.
	 * 	if id = 0, it returns a new color (black) - but do not modify/save as cached
	 * 	@param ctx context
	 * 	@param AD_PrintColor_ID id
	 * 	@return Color
	 */
	static public MPrintColor get (Properties ctx, int AD_PrintColor_ID)
	{
	//	if (AD_PrintColor_ID == 0)
	//		return new MPrintColor (ctx, 0);
		Integer key = new Integer(AD_PrintColor_ID);
		MPrintColor pc = s_colors.get(key);
		if (pc == null)
		{
			pc = new MPrintColor (ctx, AD_PrintColor_ID, null);
			s_colors.put(key, pc);
		}
		return pc;
	}	//	get

	/**
	 * 	Get Color
	 * 	@param ctx context
	 * 	@param AD_PrintColor_ID id
	 * 	@return Color or null
	 */
	static public MPrintColor get (Properties ctx, String AD_PrintColor_ID)
	{
		if (AD_PrintColor_ID == null || AD_PrintColor_ID.length() == 0)
			return null;
		try
		{
			int id = Integer.parseInt(AD_PrintColor_ID);
			return get(ctx, id);
		}
		catch (Exception e)
		{
			s_log.error("AD_PrintColor_ID=" + AD_PrintColor_ID
				+ " - " + e.toString());
		}
		return null;
	}	//	get
	
	
	/**************************************************************************
	 *	Constructor
	 *  @param ctx context
	 *  @param AD_PrintColor_ID ID
	 *  @param trxName transaction
	 */
	public MPrintColor(Properties ctx, int AD_PrintColor_ID, String trxName)
	{
		super (ctx, AD_PrintColor_ID, trxName);
		if (AD_PrintColor_ID == 0)
			setIsDefault(false);
	}	//	MPrintColor

	/**	Color cached				*/
	private Color	m_cacheColor = null;

	/**
	 * 	Get Color
	 * 	@return Color
	 */
	public Color getColor()
	{
		if (m_cacheColor != null)
			return m_cacheColor;
		String code = getCode();
		if (code == null || code.equals("."))
			m_cacheColor = Color.black;
		try
		{
			if (code != null && !code.equals("."))
			{
				int rgba = Integer.parseInt(code);
				m_cacheColor = new Color(rgba, false);
			}
		}
		catch (Exception e)
		{
			log.error("MPrintColor.getColor", e);
		}
		if (code == null)
			m_cacheColor = Color.black;
	//	log.debug( "MPrintColor.getColor " + code, m_cacheColor);
		return m_cacheColor;
	}	//	getColor

	/**
	 * 	Set Color
	 * 	@param color Color
	 */
	public void setColor (Color color)
	{
		int rgba = color.getRGB();
		super.setCode(String.valueOf(rgba));
	}	//	setColor

	/**
	 * 	Get Color as RRGGBB hex string for HTML font tag
	 *	@return	rgb hex value
	 */
	public String getRRGGBB()
	{
		Color color = getColor();
		StringBuffer sb = new StringBuffer();
		sb.append(Util.toHex((byte)color.getRed()))
			.append(Util.toHex((byte)color.getGreen()))
			.append(Util.toHex((byte)color.getBlue()));
		return sb.toString();
	}	//	getRRGGBB
	
	/**
	 * 	String Representation
	 * 	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MPrintColor[");
		sb.append("ID=").append(get_ID())
			.append(",Name=").append(getName())
			.append(",RGB=").append(getCode())
			.append(",").append(getColor())
			.append("]");
		return sb.toString();
	}	//	toString
}	//	MPrintColor
