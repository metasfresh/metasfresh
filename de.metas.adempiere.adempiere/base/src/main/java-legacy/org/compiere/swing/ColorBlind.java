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
import java.util.logging.Logger;

import javax.swing.plaf.ColorUIResource;

/**
 *  Color Blind Utilities.
 *  These utilities help to show how color blind/challenged see colors.
 *
 *  Created by Thomas Wolfmaier.
 *  Copyright (C) 1999-2005 HCIRN.
 *  All rights reserved.
 *  @see <a href="http://www.hcirn.com">HCIRN</a>
 *
 *  @author     Thomas Wolfmaier
 *  @version    $Id: ColorBlind.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class ColorBlind
{
	/** Color Blindness Type    - 0=none    */
	public static final String[] COLORBLIND_TYPE =
		{"", "Protanopia", "Deuteranopia", "Tritanopia"};

	public static final int NORMAL = 0;
	public static final int PROTANOPIA = 1;
	public static final int DEUTERANOPIA = 2;
	public static final int TRITANOPIA = 3;

	/*************************************************************************/

	private static final double[][] confusionPoints =
	{   {0.735,  0.265},
		{1.140, -0.140},
		{0.171, -0.003}
	};
	private static final double[][][] colorAxes =
	{   {{0.115807, 0.073581}, {0.471899, 0.527051}},
		{{0.102776, 0.102864}, {0.505845, 0.493211}},
		{{0.045391, 0.294976}, {0.665764, 0.334011}}
	};
	private static final double[][] RGBtoXYZMatrix =
	{   {0.430574, 0.341550, 0.178325},
		{0.222015, 0.706655, 0.071330},
		{0.020183, 0.129553, 0.939180}
	};
	private static final double[][] XYZtoRGBMatrix =
	{   { 3.063218, -1.393325, -0.475802},
		{-0.969243,  1.875966,  0.041555},
		{ 0.067871, -0.228834,  1.069251}
	};
	private static final double[] whitePoint =
	{   0.312713, 0.329016, 0.358271
	};
	private static final double gamma = 2.2;

	/** Color Blind Type        */
	private static int          s_colorType = NORMAL;
	
	/**	Logger			*/
	private static Logger log = Logger.getLogger(ColorBlind.class.getName());


	/**
	 *  Set Color Type for Color Blind view
	 *  @param colorType (0 = none)
	 */
	public static void setColorType (int colorType)
	{
		if (colorType > 0 && colorType < 4)
			s_colorType = colorType;
		else
			s_colorType = 0;
		if (s_colorType != 0)
			log.config(COLORBLIND_TYPE[colorType]);
	}   //  setColorType

	/**
	 *  Get Color Type for Color Blind view
	 *  @return colorType (0 = none)
	 */
	public static int getColorType ()
	{
		return s_colorType;
	}   //  getColorType

	
	/**************************************************************************
	 *  Convert "normal" color to Dichromat Color based on set color type
	 *  @param color Java Color object containing values for RGB
	 *  @return Dichromat Color
	 */
	public static ColorUIResource getDichromatColorUIResource (ColorUIResource color)
	{
		if (s_colorType == NORMAL)
			return color;
		return new ColorUIResource(getDichromatColorUIResource (color, s_colorType));
	}   //  convertToDichromatColorUIResource

	/**
	 *  Convert "normal" color to Dichromat Color
	 *  @param color Java Color object containing values for RGB
	 *  @param colorType  PROTANOPIA = 1, DEUTERANOPIA = 2 or TRITANOPIA = 3 as declared above
	 *  @return Dichromat Color
	 */
	public static Color getDichromatColorUIResource (ColorUIResource color, int colorType)
	{
		if (s_colorType == NORMAL)
			return color;
		return new ColorUIResource(getDichromatColor (color, s_colorType));
	}   //  convertToDichromatColorUIResource

	/**
	 *  Convert "normal" color to Dichromat Color based on set color type
	 *  @param color Java Color object containing values for RGB
	 *  @return Dichromat Color
	 */
	public static Color getDichromatColor (Color color)
	{
		if (s_colorType == NORMAL)
			return color;
		return getDichromatColor (color, s_colorType);
	}   //  convertToDichromatColor

	/**
	 *  Convert "normal" color to Dichromat Color
	 *  @param color Java Color object containing values for RGB
	 *  @param colorType  PROTANOPIA = 1, DEUTERANOPIA = 2 or TRITANOPIA = 3 as declared above
	 *  @return Dichromat Color
	 */
	public static Color getDichromatColor (Color color, int colorType)
	{
		//  check type & return if not valid
		int type = 0;
		if (colorType > 0 && colorType < 4)
			type = colorType;
		//  No conversion or no color
		if (type == 0 || color == null)
			return color;
		type--;     //  correct to zero based

		//  Return white & black - not converted
		if (color.equals(Color.black) || color.equals(Color.white))
			return color;

		double red   = color.getRed();
		double green = color.getGreen();
		double blue  = color.getBlue();

	//  System.out.println("Red: " + red + " Green: " + green + " Blue: " + blue);

		double X =  RGBtoXYZMatrix[0][0] * Math.pow(red   / 255.0, gamma) +
					RGBtoXYZMatrix[0][1] * Math.pow(green / 255.0, gamma) +
					RGBtoXYZMatrix[0][2] * Math.pow(blue  / 255.0, gamma);
		double Y =  RGBtoXYZMatrix[1][0] * Math.pow(red   / 255.0, gamma) +
					RGBtoXYZMatrix[1][1] * Math.pow(green / 255.0, gamma) +
					RGBtoXYZMatrix[1][2] * Math.pow(blue  / 255.0, gamma);
		double Z =  RGBtoXYZMatrix[2][0] * Math.pow(red   / 255.0, gamma) +
					RGBtoXYZMatrix[2][1] * Math.pow(green / 255.0, gamma) +
					RGBtoXYZMatrix[2][2] * Math.pow(blue  / 255.0, gamma);

	//  System.out.println("X: " + X + " Y: " + Y + " Z: " + Z);

		double x = 0.0;
		double y = 0.0;

		if ((X + Y + Z) != 0.0)
		{
			x = X / (X + Y + Z);
			y = Y / (X + Y + Z);
		}

	//  System.out.println("x: " + x + " y: " + y + " Y: " + Y);

		double Yn = Y;
		double Xn = (whitePoint[0] * Yn) / whitePoint[1];
		double Zn = (whitePoint[2] * Yn) / whitePoint[1];

		double xc = confusionPoints[type][0];
		double yc = confusionPoints[type][1];

		double x1 = colorAxes[type][0][0];
		double y1 = colorAxes[type][0][1];
		double x2 = colorAxes[type][1][0];
		double y2 = colorAxes[type][1][1];

		double ap = (y2 - y1) / (x2 - x1);
		double bp = y1 - (x1 * ap);

	//  System.out.println("ap: " + ap + " bp: " + bp);

		double a;
		if (x < xc)
			a = (yc - y) / (xc - x);
		else
			a = (y - yc) / (x - xc);

		double b = y - (x * a);

	//  System.out.println("a: " + a + " b: " + b);

		x = (bp - b) / (a - ap);
		y = a * x + b;

	//  System.out.println("x: " + x + " y: " + y);

		X = 0.0;
		Z = 0.0;
		if (y != 0)
		{
			X = x * (Y / y);
			Z = (1 - x  - y) * (Y / y);
		}

	//  System.out.println("X: " + X + " Y: " + Y + " Z: " + Z);

		red   = XYZtoRGBMatrix[0][0] * X +
				XYZtoRGBMatrix[0][1] * Y +
				XYZtoRGBMatrix[0][2] * Z;
		green = XYZtoRGBMatrix[1][0] * X +
				XYZtoRGBMatrix[1][1] * Y +
				XYZtoRGBMatrix[1][2] * Z;
		blue  = XYZtoRGBMatrix[2][0] * X +
				XYZtoRGBMatrix[2][1] * Y +
				XYZtoRGBMatrix[2][2] * Z;

	//  System.out.println("Red: " + red + " Green: " + green + " Blue: " + blue);

		double reddiff =    XYZtoRGBMatrix[0][0] * (Xn - X) +
							XYZtoRGBMatrix[0][1] * (Yn - Y) +
							XYZtoRGBMatrix[0][2] * (Zn - Z);
		double greendiff =  XYZtoRGBMatrix[1][0] * (Xn - X) +
							XYZtoRGBMatrix[1][1] * (Yn - Y) +
							XYZtoRGBMatrix[1][2] * (Zn - Z);
		double bluediff =   XYZtoRGBMatrix[2][0] * (Xn - X) +
							XYZtoRGBMatrix[2][1] * (Yn - Y) +
							XYZtoRGBMatrix[2][2] * (Zn - Z);

		double cr = ((red   < 0.0 ? 0.0 : 1.0) - red)   / reddiff;
		double cg = ((green < 0.0 ? 0.0 : 1.0) - green) / greendiff;
		double cb = ((blue  < 0.0 ? 0.0 : 1.0) - blue)  / bluediff;

	//  System.out.println("cr: " + cr + " cg: " + cg + " cb: " + cb);

		double c1 = (cr < 0 || cr > 1) ? 0 : cr;
		double c2 = (cg < 0 || cg > 1) ? 0 : cg;
		double c3 = (cb < 0 || cb > 1) ? 0 : cb;
		double c = Math.max(c1, Math.max(c2, c3));

	//  System.out.println("c: " + c);

		red   = red   + c * reddiff;
		green = green + c * greendiff;
		blue  = blue  + c * bluediff;

	//  System.out.println("Red: " + red + " Green: " + green + " Blue: " + blue);

		red   = Math.pow(red,   1.0 / gamma);
		green = Math.pow(green, 1.0 / gamma);
		blue  = Math.pow(blue,  1.0 / gamma);

	//  System.out.println("Red: " + red + " Green: " + green + " Blue: " + blue);
	//  System.out.println("Red: " + red * 255.0 + " Green: " + green * 255.0 + " Blue: " + blue * 255.0);

		Color retValue = new Color((float)red, (float)green, (float)blue);
		log.fine("Color " + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue()
			+ " -> " + retValue.getRed() + "-" + retValue.getGreen() + "-" + retValue.getBlue() + " <- " + COLORBLIND_TYPE[colorType]);

		return retValue;
	}   //  convertToDichromatColor

}   //  ColorBlind
