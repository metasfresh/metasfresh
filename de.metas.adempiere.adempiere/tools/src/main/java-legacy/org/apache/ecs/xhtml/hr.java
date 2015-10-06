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
import org.apache.ecs.KeyEvents;
import org.apache.ecs.MouseEvents;
import org.apache.ecs.Printable;
import org.apache.ecs.SinglePartElement;

/**
 * This class creates an &lt;hr&gt; tag.
 * 
 * @version $Id: hr.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy </a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens </a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver </a>
 */
public class hr extends SinglePartElement
	implements Printable, MouseEvents, KeyEvents
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1776012475110450414L;

	/**
	 * Private initialization routine.
	 */
	{
		setElementType ("hr");
		setCase (LOWERCASE);
		setAttributeQuote (true);
		setBeginEndModifier ('/');
	}

	/**
	 * methods to set the attibutes.
	 */
	public hr ()
	{
	}

	/**
	 * Basic constructor
	 * 
	 * @param width
	 *            sets the width="" attribute
	 */
	public hr (String width)
	{
		setWidth (width);
	}

	/**
	 * Basic constructor
	 * 
	 * @param width
	 *            sets the width="" attribute
	 */
	public hr (int width)
	{
		setWidth (width);
	}

	/**
	 * Basic constructor
	 * 
	 * @param width
	 *            sets the width="" attribute
	 * @param align
	 *            sets the align="" attribute
	 */
	public hr (String width, String align)
	{
		setWidth (width);
		setAlign (align);
	}

	/**
	 * Basic constructor
	 * 
	 * @param width
	 *            sets the width="" attribute
	 * @param align
	 *            sets the align="" attribute
	 */
	public hr (int width, String align)
	{
		setWidth (width);
		setAlign (align);
	}

	/**
	 * Basic constructor
	 * 
	 * @param width
	 *            sets the width="" attribute
	 * @param align
	 *            sets the align="" attribute
	 * @param size
	 *            sets the size="" attribute
	 */
	public hr (String width, String align, String size)
	{
		setWidth (width);
		setAlign (align);
		setSize (size);
	}

	/**
	 * Basic constructor
	 * 
	 * @param width
	 *            sets the width="" attribute
	 * @param align
	 *            sets the align="" attribute
	 * @param size
	 *            sets the size="" attribute
	 */
	public hr (String width, String align, int size)
	{
		setWidth (width);
		setAlign (align);
		setSize (size);
	}

	/**
	 * Basic constructor
	 * 
	 * @param width
	 *            sets the width="" attribute
	 * @param align
	 *            sets the align="" attribute
	 * @param size
	 *            sets the size="" attribute
	 */
	public hr (int width, String align, int size)
	{
		setWidth (width);
		setAlign (align);
		setSize (size);
	}

	/**
	 * Sets the width="" attribute
	 * 
	 * @param width
	 *            the width="" attribute
	 */
	public hr setWidth (String width)
	{
		addAttribute ("width", width);
		return this;
	}

	/**
	 * Sets the width="" attribute
	 * 
	 * @param width
	 *            the width="" attribute
	 */
	public hr setWidth (int width)
	{
		addAttribute ("width", Integer.toString (width));
		return this;
	}

	/**
	 * Sets the align="" attribute
	 * 
	 * @param align
	 *            the align="" attribute
	 */
	public hr setAlign (String align)
	{
		addAttribute ("align", align);
		return this;
	}

	/**
	 * Sets the size="" attribute
	 * 
	 * @param hspace
	 *            the size="" attribute
	 */
	public hr setSize (String size)
	{
		addAttribute ("size", size);
		return this;
	}

	/**
	 * Sets the size="" attribute
	 * 
	 * @param hspace
	 *            the size="" attribute
	 */
	public hr setSize (int size)
	{
		addAttribute ("size", Integer.toString (size));
		return this;
	}

	/**
	 * Sets the noshade
	 * 
	 * @param shade
	 *            true or false
	 */
	public hr setNoShade (boolean shade)
	{
		if (shade == true)
			addAttribute ("noshade", "noshade");
		else
			removeAttribute ("noshade");
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
	public hr addElement (String hashcode, Element element)
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
	public hr addElement (String hashcode, String element)
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
	public hr addElement (Element element)
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
	public hr addElement (String element)
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
	public hr removeElement (String hashcode)
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
