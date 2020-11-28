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

import org.apache.ecs.Element;
import org.apache.ecs.HtmlColor;
import org.apache.ecs.KeyEvents;
import org.apache.ecs.MouseEvents;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;

/**
 * This class creates a &lt;table&gt; object.
 * 
 * @version $Id: table.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy </a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens </a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver </a>
 */
public class table extends MultiPartElement
	implements Printable, MouseEvents, KeyEvents
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6841603851523601317L;

	/**
	 * Private iniitialization routine
	 */
	{
		setElementType ("table");
		setCase (LOWERCASE);
		setAttributeQuote (true);
	}

	public table ()
	{
	}

	/**
	 * Allows one to set the border size.
	 * 
	 * @param border
	 *            sets the border="" attribute.
	 */
	public table (int border)
	{
		setBorder (border);
	}

	/**
	 * Allows one to set the border size.
	 * 
	 * @param border
	 *            sets the border="" attribute.
	 * @param cellSpacing
	 *            sets the cellSpacing="" attribute.
	 * @param cellPadding
	 *            sets the cellSPadding="" attribute.
	 * @param width
	 *            optionally sets the width="" attribute.
	 * @param className
	 *            optionally sets the class="" attribute.
	 */
	public table (String border, String cellSpacing, String cellPadding, 
		String width, String className)
	{
		setBorder (border);
		setCellSpacing(cellSpacing);
		setCellPadding(cellPadding);
		if (width != null)
			setWidth(width);
		if (className != null)
			setClass(className);
	}

	/**
	 * Allows one to set the border size.
	 * 
	 * @param border
	 *            sets the border="" attribute.
	 */
	public table (String border)
	{
		setBorder (border);
	}
	
	

	/**
	 * Set the summary="" attribue.
	 * 
	 * @param summary
	 *            sets the summary="" attribute.
	 */
	public table setSummary (String summary)
	{
		addAttribute ("summary", summary);
		return (this);
	}

	/**
	 * Sets the align="" attribute.
	 * 
	 * @param align
	 *            sets the align="" attribute. You can use the AlignType.*
	 *            variables for convience.
	 */
	public table setAlign (String align)
	{
		addAttribute ("align", align);
		return (this);
	}

	/**
	 * Sets the width="" attribute.
	 * 
	 * @param width
	 *            sets the width="" attribute.
	 */
	public table setWidth (String width)
	{
		addAttribute ("width", width);
		return (this);
	}

	/**
	 * Sets the height="" attribute.
	 * 
	 * @param width
	 *            sets the height="" attribute.
	 */
	public table setHeight (String height)
	{
		addAttribute ("height", height);
		return (this);
	}

	/**
	 * Sets the width="" attribute.
	 * 
	 * @param width
	 *            sets the width="" attribute.
	 */
	public table setWidth (int width)
	{
		addAttribute ("width", Integer.toString (width));
		return (this);
	}

	/**
	 * Sets the height="" attribute.
	 * 
	 * @param width
	 *            sets the height="" attribute.
	 */
	public table setHeight (int height)
	{
		addAttribute ("height", Integer.toString (height));
		return (this);
	}

	/**
	 * Sets the cols="" attribute.
	 * 
	 * @param width
	 *            sets the cols="" attribute.
	 */
	public table setCols (int cols)
	{
		addAttribute ("cols", Integer.toString (cols));
		return (this);
	}

	/**
	 * Sets the cols="" attribute.
	 * 
	 * @param width
	 *            sets the cols="" attribute.
	 */
	public table setCols (String cols)
	{
		addAttribute ("cols", cols);
		return (this);
	}

	/**
	 * Sets the cellpading="" attribute.
	 * 
	 * @param cellpadding
	 *            sets the cellpading="" attribute.
	 */
	public table setCellPadding (int cellpadding)
	{
		addAttribute ("cellpadding", Integer.toString (cellpadding));
		return (this);
	}

	/**
	 * Sets the cellspacing="" attribute.
	 * 
	 * @param spacing
	 *            sets the cellspacing="" attribute.
	 */
	public table setCellSpacing (int cellspacing)
	{
		addAttribute ("cellspacing", Integer.toString (cellspacing));
		return (this);
	}

	/**
	 * Sets the cellpading="" attribute.
	 * 
	 * @param cellpadding
	 *            sets the cellpading="" attribute.
	 */
	public table setCellPadding (String cellpadding)
	{
		addAttribute ("cellpadding", cellpadding);
		return (this);
	}

	/**
	 * Sets the cellspacing="" attribute.
	 * 
	 * @param spacing
	 *            sets the cellspacing="" attribute.
	 */
	public table setCellSpacing (String cellspacing)
	{
		addAttribute ("cellspacing", cellspacing);
		return (this);
	}

	/**
	 * Sets the border="" attribute.
	 * 
	 * @param border
	 *            sets the border="" attribute.
	 */
	public table setBorder (int border)
	{
		addAttribute ("border", Integer.toString (border));
		return (this);
	}

	/**
	 * Sets the border="" attribute.
	 * 
	 * @param border
	 *            sets the border="" attribute.
	 */
	public table setBorder (String border)
	{
		addAttribute ("border", border);
		return (this);
	}

	/**
	 * Sets the frame="" attribute.
	 * 
	 * @param frame
	 *            sets the frame="" attribute.
	 */
	public table setFrame (String frame)
	{
		addAttribute ("frame", frame);
		return (this);
	}

	/**
	 * Sets the rules="" attribute.
	 * 
	 * @param rules
	 *            sets the rules="" attribute.
	 */
	public table setRules (String rules)
	{
		addAttribute ("rules", rules);
		return (this);
	}

	/**
	 * Sets the bordercolor="" attribute
	 * 
	 * @param color
	 *            the bgcolor="" attribute
	 */
	public table setBorderColor (String color)
	{
		addAttribute ("bordercolor", HtmlColor.convertColor (color));
		return this;
	}
	
	/**
	 * Sets the bgcolor="" attribute
	 * 
	 * @param color
	 *            the bgcolor="" attribute
	 */
	public table setBgColor (String color)
	{
		addAttribute ("bgcolor", HtmlColor.convertColor (color));
		return this;
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
	public table addElement (String hashcode, Element element)
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
	public table addElement (String hashcode, String element)
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
	public table addElement (Element element)
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
	public table addElement (String element)
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
	public table removeElement (String hashcode)
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
}
