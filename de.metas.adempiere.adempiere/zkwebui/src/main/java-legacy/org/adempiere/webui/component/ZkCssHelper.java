/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.component;

import java.awt.Color;

import org.zkoss.zk.ui.HtmlBasedComponent;

/**
 * Utility function to support ZK functions.
 *
 * Provides functionsfor manipulating the CSS style for
 * ZK components.
 *
 * @author Andrew Kimball
 *
 */
public final class ZkCssHelper
{

    /** Left text alignment CSS style property and value. */
    public static final String STYLE_TEXT_ALIGN_LEFT = "text-align:left";
    /** Right text alignment CSS style property and value. */
    public static final String STYLE_TEXT_ALIGN_RIGHT = "text-align:right";
    /** Centre text alignment CSS style property and value. */
    public static final String STYLE_TEXT_ALIGN_CENTER = "text-align:center";
    /** CSS style property for color. */
    public static final String STYLE_COLOR = "color:#";
    /** CSS style property for background color. */
    public static final String STYLE_BACKGROUND_COLOR = "background-color:#";

    /**
     * Private default constructor.
     * This exists purely for conformance and should not be used.
     */
    private ZkCssHelper()
    {

    }

	/**
	 * Obtains the string description of the RGB components of a <code>color</code>.
	 * The returned string is suitable for using in CSS styles.
	 * The red, green and blue components are formatted as hexadecimal characters.
	 * Each component is in the range 00 to FF.
	 * The entire string is therefore
	 * a 6 character string ranging from "000000" to "FFFFFF".
	 *
	 * @param color The color for which the string is to be created
	 * @return The string representation of the colour's RGB components.
	 */
	public static String createHexColorString(Color color)
	{
		String colorString = String.format("%02X%02X%02X",
				color.getRed(),
				color.getGreen(),
				color.getBlue());

		return colorString;
	}


    /**
     * Adds a new CSS style to <code>component</code>.
     * The ";" prefix is not required.
     *
     * @param component	the HTML based ZK component whose CSS style is to be modified
     * @param style		CSS style string to append to current style
     *
     * @see #setStyle(String)
     */
    public static void appendStyle(HtmlBasedComponent component, String style)
    {
    	String oldStyle = "";

		if (component.getStyle() != null)
		{
			oldStyle = component.getStyle();
		}
		component.setStyle(oldStyle
						+ "; " + style);

		return;
    }

    /**
     * Adds a CSS color style to <code>component</code>.
     *
     * The current style of the component is retained.
     *
     * @param component the HTML based ZK component whose CSS style is to be modified
     * @param color     the color to be set
     * @see #appendStyleBackgroundColor(HtmlBasedComponent, Color)
     * @see #setStyleColor(HtmlBasedComponent, Color)
     */
    public static void appendStyleColor(HtmlBasedComponent component, Color color)
    {
        String colorString = createHexColorString(color);
        String colorStyleString = STYLE_COLOR + colorString;
        appendStyle(component, colorStyleString);

        return;
    }


    /**
     * Sets CSS color style for <code>component</code>.
     *
     * Previous styles are removed.
     *
     * @param component the HTML based ZK component whose CSS style is to be modified
     * @param color     the color to be set
     * @see #setStyleBackgroundColor(HtmlBasedComponent, Color)
     * @see #appendStyleColor(HtmlBasedComponent, Color)
     */
    public static void setStyleColor(HtmlBasedComponent component, Color color)
    {
        String colorString = createHexColorString(color);
        String colorStyleString = STYLE_COLOR + colorString;
        component.setStyle(colorStyleString);

        return;
    }

    /**
     * Adds a CSS background color style to <code>component</code>.
     *
     * The current style of the component is retained.
     *
     * @param component the HTML based ZK component whose CSS style is to be modified
     * @param color     the color to be set
     * @see #appendStyleBackColor(HtmlBasedComponent, Color)
     * @see #setStyleBackgroundColor(HtmlBasedComponent, Color)
     */
    public static void appendStyleBackgroundColor(HtmlBasedComponent component, Color color)
    {
        String colorString = createHexColorString(color);
        String colorStyleString = STYLE_BACKGROUND_COLOR + colorString;
        appendStyle(component, colorStyleString);

        return;
    }

    /**
     * Sets CSS background color style for <code>component</code>.
     *
     * Previous styles are removed.
     *
     * @param component the HTML based ZK component whose CSS style is to be modified
     * @param color     the color to be set
     * @see #appendStyleBackgroundColor(HtmlBasedComponent, Color)
     * @see #setStyleColor(HtmlBasedComponent, Color)
     */
    public static void setStyleBackgroundColor(HtmlBasedComponent component, Color color)
    {
        String colorString = createHexColorString(color);
        String colorStyleString = STYLE_BACKGROUND_COLOR + colorString;
        component.setStyle(colorStyleString);

        return;
    }
}
