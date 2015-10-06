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
package org.compiere.plaf;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Window;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.adempiere.plaf.AdempiereLookAndFeel;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.util.KeyNamePair;
import org.compiere.util.ValueNamePair;

/**
 *  Adempiere Background Color
 *
 *  @author     Jorg Janke
 *  @version    $Id: AdempiereColor.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CompiereColor implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 756220720952973781L;
	/** Background type Flat            */
	public static final String  TYPE_FLAT = "F";
	/** Background type Gradient        */
	public static final String  TYPE_GRADIENT = "G";
	/** Background type Lines           */
	public static final String  TYPE_LINES = "L";
	/** Background type Texture         */
	public static final String  TYPE_TEXTURE = "T";

	/** Names   */
	private static final ResourceBundle s_res = PlafRes.getBundle();

	/** Type Values     */
	public static final String[]    TYPE_VALUES = new String[] {
		TYPE_FLAT, TYPE_GRADIENT, TYPE_LINES, TYPE_TEXTURE
	};
	/** Type Names      */
	public static final String[]    TYPE_NAMES = new String[] {
		s_res.getString("BackColType_Flat"),
		s_res.getString("BackColType_Gradient"),
		s_res.getString("BackColType_Lines"),
		s_res.getString("BackColType_Texture")
	};
	/** Types           */
	public static final ValueNamePair[] TYPES = new ValueNamePair[] {
		new ValueNamePair(TYPE_VALUES[0], TYPE_NAMES[0]),
		new ValueNamePair(TYPE_VALUES[1], TYPE_NAMES[1]),
		new ValueNamePair(TYPE_VALUES[2], TYPE_NAMES[2]),
		new ValueNamePair(TYPE_VALUES[3], TYPE_NAMES[3])
	};

	/** Gradient Starting Values    */
	public static final int[]       GRADIENT_SP_VALUES = new int[] {
		SwingConstants.NORTH, SwingConstants.NORTH_EAST,
		SwingConstants.EAST, SwingConstants.SOUTH_EAST,
		SwingConstants.SOUTH, SwingConstants.SOUTH_WEST,
		SwingConstants.WEST, SwingConstants.NORTH_WEST
	};
	/** Gradient Starting Names     */
	public static final String[]    GRADIENT_SP_NAMES = new String[] {
		"North",    "North-East",
		"East",     "South-East",
		"South",    "South-West",
		"West",     "North-West"
	};
	/** Gradient Starting Point    */
	public static final KeyNamePair[]   GRADIENT_SP = new KeyNamePair[] {
		new KeyNamePair(GRADIENT_SP_VALUES[0], GRADIENT_SP_NAMES[0]),
		new KeyNamePair(GRADIENT_SP_VALUES[1], GRADIENT_SP_NAMES[1]),
		new KeyNamePair(GRADIENT_SP_VALUES[2], GRADIENT_SP_NAMES[2]),
		new KeyNamePair(GRADIENT_SP_VALUES[3], GRADIENT_SP_NAMES[3]),
		new KeyNamePair(GRADIENT_SP_VALUES[4], GRADIENT_SP_NAMES[4]),
		new KeyNamePair(GRADIENT_SP_VALUES[5], GRADIENT_SP_NAMES[5]),
		new KeyNamePair(GRADIENT_SP_VALUES[6], GRADIENT_SP_NAMES[6]),
		new KeyNamePair(GRADIENT_SP_VALUES[7], GRADIENT_SP_NAMES[7])
	};
	/** Exception text              */
	private static final String     EXCEPTION_TEXT = "Arguments cannot be NULL";

	
	/**************************************************************************
	 *  Set Background of Component to default color
	 *  @param c component
	 */
	public static void setBackground (JComponent c)
	{
		setBackground (c, new CompiereColor(AdempierePLAF.getFormBackground()));
	}   //  setBackground

	/**
	 *  Set Background of Component
	 *  @param c Component
	 *  @param cc Color
	 */
	public static void setBackground (JComponent c, CompiereColor cc)
	{
		c.putClientProperty(AdempiereLookAndFeel.BACKGROUND, cc);
	}   //  setBackground

	/**
	 *  Get Background Color of component
	 *  @param c JComponent
	 *  @return Color
	 */
	public static CompiereColor getBackground (JComponent c)
	{
		CompiereColor bg = null;
		try
		{
			bg = (CompiereColor)c.getClientProperty(AdempiereLookAndFeel.BACKGROUND);
		}
		catch (Exception e)
		{
			log.severe("ClientProperty: " + e.getMessage());
		}
		return bg;
	}   //  getBackground

	/**
	 *  Set Background of Window Content Pane to default color
	 *  @param win window
	 */
	public static void setBackground (Window win)
	{
		setBackground (win, new CompiereColor(AdempierePLAF.getFormBackground()));
	}   //  setBackground

	/**
	 *  Set Background of Window Content Pane
	 *  @param win window
	 *  @param cc adempiere color
	 */
	public static void setBackground (Window win, CompiereColor cc)
	{
		if (win instanceof JDialog)
		{
			((JPanel)((JDialog)win).getContentPane()).putClientProperty(AdempiereLookAndFeel.BACKGROUND, cc);
		//	((JPanel)((JDialog)win).getContentPane()).setName("contentPane");
		}
		else if (win instanceof JFrame)
		{
			((JPanel)((JFrame)win).getContentPane()).putClientProperty(AdempiereLookAndFeel.BACKGROUND, cc);
		//	((JPanel)((JFrame)win).getContentPane()).setName("contentPane");
		}
		else if (win instanceof JWindow)
		{
			((JPanel)((JWindow)win).getContentPane()).putClientProperty(AdempiereLookAndFeel.BACKGROUND, cc);
		//	((JPanel)((JWindow)win).getContentPane()).setName("contentPane");
		}
	}   //  setBackground

	/**
	 *  Get Default Background
	 *  @return Background
	 *  @see CompierePanelUI#getDefaultBackground
	 */
	public static CompiereColor getDefaultBackground()
	{
		return new CompiereColor(AdempierePLAF.getFormBackground());
	}   //  getDefaultBackground

	/*************************************************************************
	 *  Create Gradient Background Color (Window System Color - White)
	 */
	public CompiereColor()
	{
		this (TYPE_GRADIENT);
	}   //  AdempiereColor

	/**
	 *  Create Default Background Colors of Type
	 *  @param type Background type (see constants TYPE_*)
	 */
	public CompiereColor (String type)
	{
		if (type == null)
			throw new java.lang.IllegalArgumentException (EXCEPTION_TEXT);
		if (type.equals(TYPE_FLAT) || type.equals(TYPE_GRADIENT)
			|| type.equals(TYPE_TEXTURE) || type.equals(TYPE_LINES))
		{
			m_type = type;
		}
		else
			throw new java.lang.IllegalArgumentException ("Invalid Type");
	}	//  AdempiereColor

	/**
	 *  Create Flat Background Color
	 *  @param bg background
	 */
	public CompiereColor (Color bg)
	{
		this (bg, true);
	}   //  AdempiereColor

	/**
	 *  Create Background Color
	 *  @param bg Color
	 *  @param flat if true create Flat color otherwise Gradient color with white lower color
	 */
	public CompiereColor (Color bg, boolean flat)
	{
		if (bg == null)
			throw new java.lang.IllegalArgumentException (EXCEPTION_TEXT);
		m_type = flat ? TYPE_FLAT : TYPE_GRADIENT;
		m_primaryColor = bg;
	}   //  AdempiereColor

	/**
	 *  Set Background to Gradient colors
	 *  @param upperColor upper Color
	 *  @param lowerColor lower Color
	 *  @param startPoint   Starting point - e.g. SOUTH_WEST see SwingConstants, default NORTH_WEST
	 *  @param repeatDistance   X/Y Distance to repeat gradient in points - 0 no repeats
	 */
	public CompiereColor (Color upperColor, Color lowerColor, int startPoint, int repeatDistance)
	{
		if (upperColor == null || lowerColor == null)
			throw new java.lang.IllegalArgumentException (EXCEPTION_TEXT);
		m_type = TYPE_GRADIENT;
		m_primaryColor = upperColor;
		m_secondaryColor = lowerColor;
		m_startPoint = startPoint;
		m_repeatDistance = repeatDistance;
	}   //  AdempiereColor

	/**
	 *  Set Background to Gradient colors.
	 *  Starting in the north, repeat after 100 pt
	 *  @param upperColor upper color
	 *  @param lowerColor lower color
	 */
	public CompiereColor (Color upperColor, Color lowerColor)
	{
		this (upperColor, lowerColor, SwingConstants.NORTH_WEST, 100);
	}   //  AdempiereColor

	/**
	 *  Set Background to Texture
	 *
	 *  @param textureURL URL to a *.gif or *.jpg graphic file
	 *  @param taint  Color to taint the texture (use white for not tainting it)
	 *  @param compositeAlpha Value from 0(no) to 1(full) tainting
	 */
	public CompiereColor (URL textureURL, Color taint, float compositeAlpha)
	{
		if (textureURL == null || taint == null)
			throw new java.lang.IllegalArgumentException (EXCEPTION_TEXT);
		m_type = TYPE_TEXTURE;
		m_textureURL = textureURL;
		m_primaryColor = taint;
		m_compositeAlpha = compositeAlpha;
	}   //  AdempiereColor

	/**
	 *  Set Background to Texture
	 *
	 *  @param textureURL URL to a *.gif or *.jpg graphic file
	 *  @param taint  Color to taint the texture (use white for not tainting it)
	 *  @param compositeAlpha Tainting value from 0 (no - FullGraph) to 1 (full - NoGraph)
	 */
	public CompiereColor (String textureURL, Color taint, float compositeAlpha)
	{
		if (textureURL == null || taint == null)
			throw new java.lang.IllegalArgumentException (EXCEPTION_TEXT);
		m_type = TYPE_TEXTURE;
		setTextureURL(textureURL);
		m_primaryColor = taint;
		m_compositeAlpha = compositeAlpha;
	}   //  AdempiereColor

	/**
	 *  Set Background to Lines
	 *
	 *  @param lineColor line color
	 *  @param backColor background color
	 *  @param lineWidth Stroke width in point
	 *  @param lineDistance Distance between lines in points
	 */
	public CompiereColor (Color lineColor, Color backColor, float lineWidth, int lineDistance)
	{
		if (lineColor == null || backColor == null)
			throw new java.lang.IllegalArgumentException (EXCEPTION_TEXT);
		m_type = TYPE_LINES;
		m_primaryColor = backColor;
		m_secondaryColor = lineColor;
		m_lineWidth = lineWidth;
		m_lineDistance = lineDistance;
	}   //  AdempiereColor

	/**
	 *  Copy Color
	 *  @param cc color
	 */
	public CompiereColor (CompiereColor cc)
	{
		if (cc == null)
			return;
		setColor(cc);
	}   //  AdempiereColor

	/*************************************************************************/

	/** Type - Default: Gradient    */
	private String  		m_type = TYPE_GRADIENT;

	/** Primary Color - Default Panel back  */
	private Color   		m_primaryColor = UIManager.getColor("Panel.background");
	/** Secondary Color - Default: gray-white    */
	private Color   		m_secondaryColor = new Color (245, 245, 245);

	/** Texture Graph URL           */
	private URL     		m_textureURL = null;
	/** Texture Alpha - Default: 0.7 */
	private float   		m_compositeAlpha = 0.7f;

	/** Line Width - Default: 1     */
	private float   		m_lineWidth = 1.0f;
	/** Line Distance - Default: 5  */
	private int     		m_lineDistance = 5;

	/** Gradient Starting point - Default: NORTH_WEST   */
	private int     		m_startPoint = SwingConstants.NORTH_WEST;
	/** Gradient repeat distance in points - Default: 100   */
	private int     		m_repeatDistance = 100;

	/** Dirty marker for re-paining Background    */
	private boolean m_dirty = true;
	/**	Logger			*/
	private static Logger 	log = Logger.getLogger(CompiereColor.class.getName());

	/**************************************************************************
	 *  Get BackgroundType (Flat, Gradient, Lines, Texture)
	 *  @return Background Type (see TYPE_* constants)
	 */
	public String getType()
	{
		return m_type;
	}   //  getType

	/**
	 *  Flat Background Type (default)
	 *  @return true if Flat background
	 */
	public boolean isFlat()
	{
		return TYPE_FLAT.equals(getType());
	}   // isFlat

	/**
	 *  Gradient Background Type
	 *  @return true if Gradient background
	 */
	public boolean isGradient()
	{
		return TYPE_GRADIENT.equals(getType());
	}   // isGradient

	/**
	 *  Line Background Type
	 *  @return true if Line background
	 */
	public boolean isLine()
	{
		return TYPE_LINES.equals(getType());
	}   // isLine

	/**
	 *  Texture Background Type
	 *  @return true if Texture background
	 */
	public boolean isTexture()
	{
		return TYPE_TEXTURE.equals(getType());
	}   // isTexture


	/************************
	 *  Get Flat Color
	 *  @return Primary Color
	 */
	public Color getFlatColor()
	{
		return m_primaryColor;
	}   //  getFlatColor

	/**
	 *  Set Flat Color
	 *  @param color flat color
	 */
	public void setFlatColor(Color color)
	{
		if (!isFlat() || color == null)
			return;
		m_primaryColor = color;
		m_dirty = true;
	}   //  getFlatColor


	/************************
	 *  Gradient Upper Color
	 *  @return Color or null
	 */
	public Color getGradientUpperColor()
	{
		if (!isGradient())
			return null;
		return m_primaryColor;
	}   //  getGradientUpperColor

	/**
	 *  Gradient Upper Color
	 *  @param color upper color
	 */
	public void setGradientUpperColor(Color color)
	{
		if (!isGradient() || color == null)
			return;
		m_primaryColor = color;
		m_dirty = true;
	}   //  getGradientUpperColor

	/**
	 *  Gradient Lower Color
	 *  @return Color or null
	 */
	public Color getGradientLowerColor()
	{
		if (!isGradient())
			return null;
		return m_secondaryColor;
	}   //  getGradientLowerColor

	/**
	 *  Gradient Lower Color
	 *  @param color lower color
	 */
	public void setGradientLowerColor(Color color)
	{
		if (!isGradient() || color == null)
			return;
		m_secondaryColor = color;
		m_dirty = true;
	}   //  setGradientLowerColor

	/**
	 *  Gradient Starting Point
	 *  @return starting point - e.g. NORTH - or 0
	 *  @see SwingConstants
	 */
	public int getGradientStartPoint ()
	{
		if (!isGradient())
			return 0;
		return m_startPoint;
	}   //  getGradientStartPoint

	/**
	 *  Gradient Starting Point
	 *  @param startPoint starting point - e.g. NORTH
	 *  @see SwingConstants
	 */
	public void setGradientStartPoint (int startPoint)
	{
		if (!isGradient())
			return;
		m_startPoint = startPoint;
		m_dirty = true;
	}   //  setGradientStartPoint

	/**
	 *  Gradient Repeat Distance in point
	 *  @return Repeat Distance - or 0
	 */
	public int getGradientRepeatDistance ()
	{
		if (!isGradient())
			return 0;
		return m_repeatDistance;
	}   //  getGradientRepeatDistance

	/**
	 *  Gradient Repeat Distance.
	 *  Zero stands for no repeats
	 *  @param repeatDistance repeat gradient after point x+repeat / y+repeat (depending on direction)
	 */
	public void setGradientRepeatDistance (int repeatDistance)
	{
		if (!isGradient())
			return;
		m_repeatDistance = repeatDistance;
		m_dirty = true;
	}   //  setGradientRepeatDistance

	/**
	 *  Gradient Repeat Distance.
	 *  Zero stands for no repeats
	 *  @param repeatDistanceString repeat gradient after point x+repeat / y+repeat (depending on direction)
	 */
	public void setGradientRepeatDistance (String repeatDistanceString)
	{
		if (!isGradient())
			return;
		try
		{
			setGradientRepeatDistance(Integer.parseInt(repeatDistanceString));
		}
		catch (Exception e)
		{
			log.severe("Parsing="
				+ repeatDistanceString  + " - " + e.getMessage());
		}
	}   //  setGradientRepeatDistance


	/************************
	 *  Texture Url
	 *  @return URL (if not found, org.compiere.plaf.background.jpg is used)
	 */
	public URL getTextureURL()
	{
		if (!isTexture())
			return null;
		if (m_textureURL == null)
			m_textureURL = CompiereColor.class.getResource("Adempiere00x100.gif");
		return m_textureURL;
	}   //  getTextureURL

	/**
	 *  Texture Url
	 *  @param url URL to graphic file (jpg)
	 */
	public void setTextureURL(URL url)
	{
		if (!isTexture() || url == null)
			return;
		m_textureURL = url;
		m_dirty = true;
	}   //  setTextureURL

	/**
	 *  Texture Url
	 *  @param urlString URL to graphic file (jpg)
	 */
	public void setTextureURL(String urlString)
	{
		if (!isTexture() || urlString == null)
			return;
		try
		{
			setTextureURL (new URL(urlString));
		}
		catch (Exception e)
		{
			log.severe("Parsing URL="
				+ urlString + " - " + e.getMessage());
		}
	}   //  setTextureURL

	/**
	 *  Texture Taint Color
	 *  @return Color or null
	 */
	public Color getTextureTaintColor()
	{
		if (!isTexture())
			return null;
		return m_primaryColor;
	}   //  getTextureTaintColor

	/**
	 *  Texture Taint Color
	 *  @param color taint color
	 */
	public void setTextureTaintColor(Color color)
	{
		if (!isTexture() || color == null)
			return;
		m_primaryColor = color;
		m_dirty = true;
	}   //  setTextureTaintColor

	/**
	 *  Texture Composite Alpha
	 *  @return Composite Ampha or 0f
	 */
	public float getTextureCompositeAlpha()
	{
		if (!isTexture())
			return 0f;
		return m_compositeAlpha;
	}   //  getTextureCompositeAlpha

	/**
	 *  Texture Composite Alpha
	 *  @param alpha alpha value
	 */
	public void setTextureCompositeAlpha(float alpha)
	{
		if (!isTexture())
			return;
		m_compositeAlpha = alpha;
		m_dirty = true;
	}   //  setTextureCompositeAlpha

	/**
	 *  Texture Composite Alpha
	 *  @param alphaString String to be parsed
	 */
	public void setTextureCompositeAlpha(String alphaString)
	{
		if (!isTexture() || alphaString == null)
			return;
		try
		{
			setTextureCompositeAlpha(Float.parseFloat(alphaString));
		}
		catch (Exception e)
		{
			log.severe("Parsing="
				+ alphaString  + " - " + e.getMessage());
		}
	}   //  setTextureCompositeAlpha


	/************************
	 *  Line Color
	 *  @return Color or null
	 */
	public Color getLineColor()
	{
		if (!isLine())
			return null;
		return m_secondaryColor;
	}   //  getLineColor

	/**
	 *  Line Color
	 *  @param color line color
	 */
	public void setLineColor(Color color)
	{
		if (!isLine() || color == null)
			return;
		m_secondaryColor = color;
		m_dirty = true;
	}   //  setLineColor

	/**
	 *  Line Background Color
	 *  @return Color or null
	 */
	public Color getLineBackColor()
	{
		if (!isLine())
			return null;
		return m_primaryColor;
	}   //  getLineBackColor

	/**
	 *  Line Background Color
	 *  @param color background color
	 */
	public void setLineBackColor(Color color)
	{
		if (!isLine() || color == null)
			return;
		m_primaryColor = color;
		m_dirty = true;
	}   //  setLineBackColor

	/**
	 *  Background Line Width
	 *  @return width or 0f
	 */
	public float getLineWidth()
	{
		if (!isLine())
			return 0f;
		return m_lineWidth;
	}   //  getLineWidth

	/**
	 *  Background Line Width
	 *  @param width line width
	 */
	public void setLineWidth(float width)
	{
		if (!isLine())
			return;
		m_lineWidth = width;
		m_dirty = true;
	}   //  setLineWidth

	/**
	 *  Background Line Width
	 *  @param widthString line width
	 */
	public void setLineWidth(String widthString)
	{
		if (!isLine() || widthString == null)
			return;
		try
		{
			setLineWidth(Float.parseFloat(widthString));
		}
		catch (Exception e)
		{
			log.severe("Parsing="
				+ widthString  + " - " + e.getMessage());
		}
	}   //  setLineWidth

	/**
	 *  Background Line distance in pt
	 *  @return distance or 0
	 */
	public int getLineDistance()
	{
		if (!isLine())
			return 0;
		return m_lineDistance;
	}   //  getLineDistance

	/**
	 *  Background Line distance in pt
	 *  @param distance line distance
	 */
	public void setLineDistance(int distance)
	{
		if (!isLine())
			return;
		m_lineDistance = distance;
		m_dirty = true;
	}   //  setLineDistance

	/**
	 *  Background Line distance in pt
	 *  @param distanceString line distance
	 */
	public void setLineDistance(String distanceString)
	{
		if (!isLine())
			return;
		try
		{
			setLineDistance(Integer.parseInt(distanceString));
		}
		catch (Exception e)
		{
			log.severe("Parsing="
				+ distanceString  + " - " + e.getMessage());
		}
	}   //  setLineDistance

	/**
	 *  Set Prinary Color
	 *  @param color primary color
	 */
	protected void setPrimaryColor (Color color)
	{
		if (color != null)
			m_primaryColor = color;
	}   //  setPrimaryColor

	/**
	 *  Set AdempiereColor from AdempiereColor
	 *  @param cc AdempiereColor
	 */
	public void setColor (CompiereColor cc)
	{
		if (cc == null)
			return;
		m_type = cc.getType();
		//
		if (cc.isFlat())
			m_primaryColor = cc.getFlatColor();
		else if (cc.isGradient())
		{
			m_primaryColor = cc.getGradientUpperColor();
			m_secondaryColor = cc.getGradientLowerColor();
			m_startPoint = cc.getGradientStartPoint();
			m_repeatDistance = cc.getGradientRepeatDistance();
		}
		else if (cc.isTexture())
		{
			setTextureURL(cc.getTextureURL());
			m_primaryColor = cc.getTextureTaintColor();
			m_compositeAlpha = cc.getTextureCompositeAlpha();
		}
		else if (cc.isLine())
		{
			m_primaryColor = cc.getLineBackColor();
			m_secondaryColor = cc.getLineColor();
			m_lineWidth = cc.getLineWidth();
			m_lineDistance = cc.getLineDistance();
		}
		else
			log.severe("Invalid Color");
		//
		m_dirty = true;
	}   //  setColor

	/**************************************************************************
	 *  String representation
	 *  @return string representation
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("AdempiereColor[");
		if (isFlat())
			sb.append("Flat")
				.append(" ").append(getColorAsString(getFlatColor()));
		else if (isGradient())
			sb.append("Gradient")
				.append(" Upper=").append(getColorAsString(getGradientUpperColor()))
				.append(",Lower=").append(getColorAsString(getGradientLowerColor()))
				.append(",Start=").append(getGradientStartPoint())
				.append(",RDistance=").append(getGradientRepeatDistance());
		else if (isLine())
			sb.append("Line")
				.append(" Color=").append(getColorAsString(getLineColor()))
				.append(",BackColor=").append(getColorAsString(getLineBackColor()))
				.append(",Width=").append(getLineWidth())
				.append(",Distance=").append(getLineDistance());
		else if (isTexture())
			sb.append("Texture")
				.append(" GraphURL=").append(getTextureURL())
				.append(",Taint=").append(getColorAsString(getTextureTaintColor()))
				.append(",Alpha=").append(getTextureCompositeAlpha());
		sb.append("]");
		return sb.toString();
	}   //  toString
	
	/**
	 *  Parses Color into String representation.
	 *  Required as SystemColors and Alpha Colors have different formats
	 *  @param c Color
	 *  @return [r=102,g=102,b=153,a=255]
	 */
	private static final String getColorAsString (Color c)
	{
		if (c == null)
			c = SystemColor.control;
		StringBuilder sb = new StringBuilder("[r=").append(c.getRed())
			.append(",g=").append(c.getGreen())
			.append(",b=").append(c.getBlue())
			.append(",a=").append(c.getAlpha())
			.append("]");
	//	System.out.println(sb.toString());
		return sb.toString();
	}   //  getColorAsString


	/**
	 *  Does the background needs to be redone
	 *  @return true if there were changes
	 */
	boolean isDirty()
	{
		return m_dirty;
	}   //  isDirty

	/**
	 *  Set Dirty
	 *  @param dirty if true, the background will be re-painted
	 */
	void setDirty (boolean dirty)
	{
		m_dirty = dirty;
	}   //  setDirty
}   //  AdempiereColor
