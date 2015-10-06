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
package org.adempiere.apps.graph;

import java.awt.Color;
import java.util.ArrayList;

import org.compiere.util.CLogger;

/**
 * 	Graphic Utilities
 *	
 *  @author Jorg Janke
 *  @version $Id: GraphUtil.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class GraphUtil
{
	/**	Logger	*/
	private static CLogger log = CLogger.getCLogger (GraphUtil.class);
	
	/**
	 * 	Get Foreground for back
	 *	@param background back
	 *	@return while or black
	 */
	public static Color getForeground (Color background)
	{
		if (background != null && isDark(background))
			return Color.white;
		return Color.black;
	}	//	getForeground

	/**
	 * 	Get Column Background
	 *	@param index index
	 *	@return random color
	 */
	public static Color getBackground (int index)
	{
		while (s_colors.size() <= index)
		{
			int rr = (index+1) * 47;
			int gg = 100;
			while (rr > 255)
			{
				rr -= 255;
				gg += 50;
			}
			while (gg > 255)
				gg -= 255;
			s_colors.add(new Color(255-rr, gg, rr));
		}
		return s_colors.get(index);
	}	//	getBackGround

	/** List of Colors		*/
	private static ArrayList<Color> s_colors = new ArrayList<Color>();
	
	
	/**
	 *	Is the Color dark?
	 *	@param color color
	 *	@return true if dark
	 */
	public static boolean isDark (Color color)
	{
		float r = color.getRed()   / 255.0f;
		float g = color.getGreen() / 255.0f;
		float b = color.getBlue()  / 255.0f;
		double whiteDistance = colorDistance (r, g, b, 1.0, 1.0, 1.0);
		double blackDistance = colorDistance (r, g, b, 0.0, 0.0, 0.0);
		boolean dark = blackDistance < whiteDistance;
		if (r+g+b == 1.0)
			dark = false;
	//	log.info("r=" + r + ",g=" + g + ",b=" + b + " - black=" + blackDistance
	//		+ (dark ? " <dark " : " light> ") + "white=" + whiteDistance 
	//		+ " - Alpha=" + color.getAlpha() + ", Trans=" + color.getTransparency());
		return dark;
	}	//	isDark
	
	/**
	 * 	Is Color more white or black?
	 *	@param r red
	 *	@param g green
	 *	@param b blue
	 *	@return true if dark
	 */
	public static boolean isDark (double r, double g, double b)
	{
		double whiteDistance = colorDistance (r, g, b, 1.0, 1.0, 1.0);
		double blackDistance = colorDistance (r, g, b, 0.0, 0.0, 0.0);
		boolean dark = blackDistance < whiteDistance;
	//	log.finest("r=" + r + ",g=" + g + ",b=" + b + " - white=" + whiteDistance + ",black=" + blackDistance);
		return dark;
	}	//	isDark

	/**
	 * 	Simple Color Distance.
	 * 	(3d point distance)
	 *	@param r1 first red
	 *	@param g1 first green
	 *	@param b1 first blue
	 *	@param r2 second red
	 *	@param g2 second green
	 *	@param b2 second blue
	 *	@return 3d distance for relative comparison
	 */
	public static double colorDistance (double r1, double g1, double b1,
          double r2, double g2, double b2)
	{
		double a = (r2 - r1) + 0.1;
		double b = (g2 - g1) + 0.1;
		double c = (b2 - b1) + 0.1;
		return Math.sqrt (a*a + b*b + c*c);
	}	//	colorDistance
	

	/**
	 * 	Get darker color
	 *	@param color color
	 *	@param factor factor 0..1 (AWT 0.7) the smaller, the darker 
	 *	@return darker color
	 */
	public static Color darker(Color color, double factor) 
	{
		if (factor < 0.0)
			factor = 0.7;
		else if (factor > 1.0)
			factor = 0.7;
    	return new Color(
    		Math.max((int)(color.getRed() * factor), 0),
    		Math.max((int)(color.getGreen() * factor), 0),
    		Math.max((int)(color.getBlue() * factor), 0));
	}	//	darker

	/**
	 * 	Get brighter color
	 *	@param color color
	 *	@param factor factor 0..1 (AWT 0.7) the smaller, the lighter 
	 *	@return brighter color
	 */
	public static Color brighter (Color color, double factor) 
	{
		if (factor < 0.0)
			factor = 0.7;
		else if (factor > 1.0)
			factor = 0.7;
		
    	return new Color(
    		Math.min((int)(color.getRed() / factor), 255),
    		Math.min((int)(color.getGreen() / factor), 255),
    		Math.min((int)(color.getBlue() / factor), 255));
	}	//	brighter
	
}	//	GraphUtil
