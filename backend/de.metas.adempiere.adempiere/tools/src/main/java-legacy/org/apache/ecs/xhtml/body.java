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
import org.apache.ecs.PageEvents;
import org.apache.ecs.Printable;

/**
 * This class creates a &lt;body&gt;&lt;/body&gt; tag.
 * 
 * @version $Id: body.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy </a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens </a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver </a>
 */
public class body extends MultiPartElement
	implements Printable, PageEvents, MouseEvents, KeyEvents
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -959311472053267957L;

	/**
	 * Private initialization routine.
	 */
	{
		setElementType ("body");
		setCase (LOWERCASE);
		setAttributeQuote (true);
	}

	/**
	 * Basic constructor. Use the set* methods to set the values of the
	 * attributes.
	 */
	public body ()
	{
	}

	/**
	 * Use the set* methods to set the values of the attributes.
	 * 
	 * @param color
	 *            the bgcolor="" attribute
	 */
	public body (String color)
	{
		setBgColor (color);
	}

	/**
	 * Sets the bgcolor="" attribute
	 * 
	 * @param color
	 *            the bgcolor="" attribute
	 */
	public body setBgColor (String color)
	{
		addAttribute ("bgcolor", HtmlColor.convertColor (color));
		return this;
	}

	/**
	 * Sets the background="" attribute
	 * 
	 * @param url
	 *            the background="" attribute
	 */
	public body setBackground (String url)
	{
		addAttribute ("background", url);
		return this;
	}

	/**
	 * Sets the text="" attribute
	 * 
	 * @param text
	 *            the text="" attribute
	 */
	public body setText (String text)
	{
		addAttribute ("text", text);
		return this;
	}

	/**
	 * Sets the link="" attribute
	 * 
	 * @param color
	 *            the link="" attribute
	 */
	public body setLink (String color)
	{
		addAttribute ("link", HtmlColor.convertColor (color));
		return this;
	}

	/**
	 * Sets the vlink="" attribute
	 * 
	 * @param color
	 *            the vlink="" attribute
	 */
	public body setVlink (String color)
	{
		addAttribute ("vlink", HtmlColor.convertColor (color));
		return this;
	}

	/**
	 * Sets the alink="" attribute
	 * 
	 * @param color
	 *            the alink="" attribute
	 */
	public body setAlink (String color)
	{
		addAttribute ("alink", HtmlColor.convertColor (color));
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
	public body addElement (String hashcode, Element element)
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
	public body addElement (String hashcode, String element)
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
	public body addElement (Element element)
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
	public body addElement (String element)
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
	public body removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}

	/**
	 * The onload event occurs when the user agent finishes loading a window or
	 * all frames within a frameset. This attribute may be used with body and
	 * frameset elements.
	 * 
	 * @param script script
	 */
	public void setOnLoad (String script)
	{
		addAttribute ("onload", script);
	}

	/**
	 * On Blur
	 * 
	 * @param script script
	 */
	public void setOnBlur (String script)
	{
		addAttribute ("onblur", script);
	}

	/**
	 * The onunload event occurs when the user agent removes a document from a
	 * window or frame. This attribute may be used with body and frameset
	 * elements.
	 * 
	 * @param script script
	 */
	public void setOnUnload (String script)
	{
		addAttribute ("onunload", script);
	}

	/**
	 * The onclick event occurs when the pointing device button is clicked over
	 * an element. This attribute may be used with most elements.
	 * 
	 * @param script script
	 */
	public void setOnClick (String script)
	{
		addAttribute ("onclick", script);
	}

	/**
	 * The ondblclick event occurs when the pointing device button is double
	 * clicked over an element. This attribute may be used with most elements.
	 * 
	 * @param script script
	 */
	public void setOnDblClick (String script)
	{
		addAttribute ("ondblclick", script);
	}

	/**
	 * The onmousedown event occurs when the pointing device button is pressed
	 * over an element. This attribute may be used with most elements.
	 * 
	 * @param script script
	 */
	public void setOnMouseDown (String script)
	{
		addAttribute ("onmousedown", script);
	}

	/**
	 * The onmouseup event occurs when the pointing device button is released
	 * over an element. This attribute may be used with most elements.
	 * 
	 * @param script script
	 */
	public void setOnMouseUp (String script)
	{
		addAttribute ("onmouseup", script);
	}

	/**
	 * The onmouseover event occurs when the pointing device is moved onto an
	 * element. This attribute may be used with most elements.
	 * 
	 * @param script script
	 */
	public void setOnMouseOver (String script)
	{
		addAttribute ("onmouseover", script);
	}

	/**
	 * The onmousemove event occurs when the pointing device is moved while it
	 * is over an element. This attribute may be used with most elements.
	 * 
	 * @param script script
	 */
	public void setOnMouseMove (String script)
	{
		addAttribute ("onmousemove", script);
	}

	/**
	 * The onmouseout event occurs when the pointing device is moved away from
	 * an element. This attribute may be used with most elements.
	 * 
	 * @param script script
	 */
	public void setOnMouseOut (String script)
	{
		addAttribute ("onmouseout", script);
	}

	/**
	 * The onkeypress event occurs when a key is pressed and released over an
	 * element. This attribute may be used with most elements.
	 * 
	 * @param script script
	 */
	public void setOnKeyPress (String script)
	{
		addAttribute ("onkeypress", script);
	}

	/**
	 * The onkeydown event occurs when a key is pressed down over an element.
	 * This attribute may be used with most elements.
	 * 
	 * @param script script
	 */
	public void setOnKeyDown (String script)
	{
		addAttribute ("onkeydown", script);
	}

	/**
	 * The onkeyup event occurs when a key is released over an element. This
	 * attribute may be used with most elements.
	 * 
	 * @param script script
	 */
	public void setOnKeyUp (String script)
	{
		addAttribute ("onkeyup", script);
	}
}
