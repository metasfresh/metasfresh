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
package org.apache.ecs.xhtml;

import org.apache.ecs.AlignType;
import org.apache.ecs.Element;
import org.apache.ecs.HtmlColor;
import org.apache.ecs.KeyEvents;
import org.apache.ecs.MouseEvents;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;

/**
 * This class creates a &lt;td&gt; object.
 * 
 * @version $Id: td.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy </a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens </a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver </a>
 */
public class td extends MultiPartElement
	implements Printable, MouseEvents, KeyEvents
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2600951455950300475L;

	/**
	 * private initializer.
	 */
	{
		setElementType ("td");
		setCase (LOWERCASE);
		setAttributeQuote (true);
	}

	/**
	 * Basic Constructor use set* methods.
	 */
	public td ()
	{
	}

	/**
	 * 	Constructor.
	 * 	<code>
	 	new td ("popupCenter", AlignType.CENTER, AlignType.MIDDLE, true);
	 * 	</code>
	 *	@param className class name
	 *	@param hAlign horizontal - e.g. AlignType.LEFT
	 *	@param vAlign vertical  - e.g. AlignType.TOP
	 *	@param nowrap no wrap
	 */
	public td (String className, String hAlign, String vAlign, boolean nowrap)
	{
		this();
		if (className != null)
			setClass(className);
		if (hAlign != null)
			setAlign(hAlign);
		if (vAlign != null)
			setVAlign(vAlign);
		setNoWrap(nowrap);
	}	//	td

	/**
	 * 	Constructor
	 *	@param className class name
	 *	@param hAlign horizontal - e.g. AlignType.LEFT
	 *	@param vAlign vertical  - e.g. AlignType.TOP
	 * 	@param value added Element - if null nbsp is added
	 *	@param nowrap no wrap
	 */
	public td (String className, String hAlign, String vAlign, 
		boolean nowrap, Element value)
	{
		this (className, hAlign, vAlign, nowrap);
		if (value == null)
			addElement(NBSP);
		else
			addElement(value);
	}	//	td

	/**
	 *	Basic Constructor with Element use set* methods.
	 * 	@param value added Element
	 */
	public td (Element value)
	{
		addElement (value);
	}

	/**
	 *	Basic Constructor with Element use set* methods.
	 * 	@param value added Element
	 */
	public td (String value)
	{
		addElement (value);
	}
	
	/*
	 * Basic Constructor use set* methods. @param close. Print the closing tag
	 * or not. public td(boolean close) { setNeedClosingTag(close); }
	 */
	/**
	 * Sets the abbr="" attribute.
	 * 
	 * @param cdata
	 *            sets the abbr="" attribute.
	 */
	public td setAbbr (String cdata)
	{
		addAttribute ("abbr", cdata);
		return (this);
	}

	/**
	 * Sets the axis="" attribute
	 * 
	 * @param cdata
	 *            sets the axis="" attribute
	 */
	public td setAxis (String cdata)
	{
		addAttribute ("axis", cdata);
		return (this);
	}

	/**
	 * Sets the axes="" attribute
	 * 
	 * @param id_refs
	 *            list of id's for header cells
	 */
	public td setAxes (String id_refs)
	{
		addAttribute ("axes", id_refs);
		return (this);
	}

	/**
	 * Sets the rowspan="" attribute
	 * 
	 * @param rowspan
	 *            number of rows spaned by cell
	 */
	public td setRowSpan (int rowspan)
	{
		addAttribute ("rowspan", Integer.toString (rowspan));
		return (this);
	}

	/**
	 * Sets the rowspan="" attribute
	 * 
	 * @param rowspan
	 *            number of rows spaned by cell
	 */
	public td setRowSpan (String rowspan)
	{
		addAttribute ("rowspan", rowspan);
		return (this);
	}

	/**
	 * Sets the colspan="" attribute
	 * 
	 * @param colspan
	 *            number of columns spanned by cell
	 */
	public td setColSpan (int colspan)
	{
		addAttribute ("colspan", Integer.toString (colspan));
		return (this);
	}

	/**
	 * Sets the colspan="" attribute
	 * 
	 * @param colspan
	 *            number of columns spanned by cell
	 */
	public td setColSpan (String colspan)
	{
		addAttribute ("colspan", colspan);
		return (this);
	}

	/**
	 * Sets word wrap on or off.
	 * 
	 * @param wrap
	 *            turn word wrap on or off.
	 */
	public td setNoWrap (boolean wrap)
	{
		if (wrap == true)
			addAttribute ("nowrap", "nowrap");
		else
			removeAttribute ("nowrap");
		return (this);
	}

	/**
	 * Supplies user agents with a recommended cell width. (Pixel Values)
	 * 
	 * @param width
	 *            how many pixels to make cell
	 */
	public td setWidth (int width)
	{
		addAttribute ("width", Integer.toString (width));
		return (this);
	}

	/**
	 * Supplies user agents with a recommended cell width. (Pixel Values)
	 * 
	 * @param width
	 *            how many pixels to make cell
	 */
	public td setWidth (String width)
	{
		addAttribute ("width", width);
		return (this);
	}

	/**
	 * Supplies user agents with a recommended cell height. (Pixel Values)
	 * 
	 * @param height
	 *            how many pixels to make cell
	 */
	public td setHeight (int height)
	{
		addAttribute ("height", Integer.toString (height));
		return (this);
	}

	/**
	 * Supplies user agents with a recommended cell height. (Pixel Values)
	 * 
	 * @param height
	 *            how many pixels to make cell
	 */
	public td setHeight (String height)
	{
		addAttribute ("height", height);
		return (this);
	}

	/**
	 * Sets the align="" attribute convience variables are provided in the
	 * AlignType interface
	 * 
	 * @param hAlign
	 *            Sets the align="" attribute
	 */
	public td setAlign (String hAlign)
	{
		if (hAlign == null 
			|| hAlign.equals(AlignType.LEFT) 
			|| hAlign.equals(AlignType.CENTER)
			|| hAlign.equals(AlignType.RIGHT))
			addAttribute ("align", hAlign);
		else
			throw new IllegalArgumentException (hAlign 
				+ " - Illegal for Horizontal Align(left,center,right)");
		return (this);
	}

	/**
	 * Sets the valign="" attribute convience variables are provided in the
	 * AlignType interface
	 * 
	 * @param vAlign
	 *            Sets the valign="" attribute
	 */
	public td setVAlign (String vAlign)
	{
		if (vAlign == null 
			|| vAlign.equals(AlignType.TOP) 
			|| vAlign.equals(AlignType.MIDDLE)
			|| vAlign.equals(AlignType.ABSMIDDLE)
			|| vAlign.equals(AlignType.BOTTOM)
			|| vAlign.equals(AlignType.ABSBOTTOM)
			|| vAlign.equals(AlignType.BASELINE))
			addAttribute ("valign", vAlign);
		else
			throw new IllegalArgumentException (vAlign 
				+ " - Illegal for Vertical Align(top,middle,bottom..)");
		return (this);
	}

	/**
	 * Sets the char="" attribute.
	 * 
	 * @param character
	 *            the character to use for alignment.
	 */
	public td setChar (String character)
	{
		addAttribute ("char", character);
		return (this);
	}

	/**
	 * Sets the charoff="" attribute.
	 * 
	 * @param char_off
	 *            When present this attribute specifies the offset of the first
	 *            occurrence of the alignment character on each line.
	 */
	public td setCharOff (int char_off)
	{
		addAttribute ("charoff", Integer.toString (char_off));
		return (this);
	}

	/**
	 * Sets the charoff="" attribute.
	 * 
	 * @param char_off
	 *            When present this attribute specifies the offset of the first
	 *            occurrence of the alignment character on each line.
	 */
	public td setCharOff (String char_off)
	{
		addAttribute ("charoff", char_off);
		return (this);
	}

	/**
	 * Sets the bgcolor="" attribute
	 * 
	 * @param color
	 *            sets the background color of the cell.
	 */
	public td setBgColor (String color)
	{
		addAttribute ("bgcolor", HtmlColor.convertColor (color));
		return (this);
	}

	/**
	 * Sets the lang="" and xml:lang="" attributes
	 * 
	 * @param lang
	 *            the lang="" and xml:lang="" attributes
	 */
	public Element setLang (String lang)
	{
		addAttribute ("lang", lang);
		addAttribute ("xml:lang", lang);
		return this;
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param hashcode
	 *            name of element for hash table
	 * @param element
	 *            Adds an Element to the element.
	 */
	public td addElement (String hashcode, Element element)
	{
		addElementToRegistry (hashcode, element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param hashcode
	 *            name of element for hash table
	 * @param element
	 *            Adds an Element to the element.
	 */
	public td addElement (String hashcode, String element)
	{
		addElementToRegistry (hashcode, element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public td addElement (Element element)
	{
		addElementToRegistry (element);
		return (this);
	}

	/**
	 * Adds an Element to the element.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public td addElement (String element)
	{
		addElementToRegistry (element);
		return (this);
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 */
	public td removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}

	/**
	 * The onclick event occurs when the pointing device button is clicked over
	 * an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnClick (String script)
	{
		addAttribute ("onclick", script);
	}

	/**
	 * The ondblclick event occurs when the pointing device button is double
	 * clicked over an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnDblClick (String script)
	{
		addAttribute ("ondblclick", script);
	}

	/**
	 * The onmousedown event occurs when the pointing device button is pressed
	 * over an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnMouseDown (String script)
	{
		addAttribute ("onmousedown", script);
	}

	/**
	 * The onmouseup event occurs when the pointing device button is released
	 * over an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnMouseUp (String script)
	{
		addAttribute ("onmouseup", script);
	}

	/**
	 * The onmouseover event occurs when the pointing device is moved onto an
	 * element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnMouseOver (String script)
	{
		addAttribute ("onmouseover", script);
	}

	/**
	 * The onmousemove event occurs when the pointing device is moved while it
	 * is over an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnMouseMove (String script)
	{
		addAttribute ("onmousemove", script);
	}

	/**
	 * The onmouseout event occurs when the pointing device is moved away from
	 * an element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnMouseOut (String script)
	{
		addAttribute ("onmouseout", script);
	}

	/**
	 * The onkeypress event occurs when a key is pressed and released over an
	 * element. This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnKeyPress (String script)
	{
		addAttribute ("onkeypress", script);
	}

	/**
	 * The onkeydown event occurs when a key is pressed down over an element.
	 * This attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnKeyDown (String script)
	{
		addAttribute ("onkeydown", script);
	}

	/**
	 * The onkeyup event occurs when a key is released over an element. This
	 * attribute may be used with most elements.
	 * 
	 * @param The
	 *            script
	 */
	public void setOnKeyUp (String script)
	{
		addAttribute ("onkeyup", script);
	}

	/**
	 * Determine if this element needs a line break, if pretty printing.
	 */
	public boolean getNeedLineBreak ()
	{
		java.util.Enumeration en = elements ();
		int i = 0;
		int j = 0;
		while (en.hasMoreElements ())
		{
			j++;
			Object obj = en.nextElement ();
			if (obj instanceof img || obj instanceof a)
				i++;
		}
		if (i == j)
			return false;
		return true;
	}
}
