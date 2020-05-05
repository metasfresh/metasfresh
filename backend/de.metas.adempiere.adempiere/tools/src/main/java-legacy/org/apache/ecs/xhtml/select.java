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
import org.apache.ecs.FormEvents;
import org.apache.ecs.KeyEvents;
import org.apache.ecs.MouseEvents;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.PageEvents;
import org.apache.ecs.Printable;

/**
 * This class creates a &lt;select&gt; tag.
 * 
 * @version $Id: select.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy </a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens </a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver </a>
 */
public class select extends MultiPartElement
	implements Printable, PageEvents, FormEvents, MouseEvents, KeyEvents
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1713354093825899777L;

	/**
	 * Private initializer
	 */
	{
		setElementType ("select");
		setCase (LOWERCASE);
		setAttributeQuote (true);
	}

	/**
	 * Basic constructor. Use the set* methods.
	 */
	public select ()
	{
	}

	/**
	 * Basic Constructor.
	 * 
	 * @param name
	 *            set the NAME="" attribute
	 */
	public select (String name)
	{
		setName (name);
	}

	/**
	 * Basic Constructor.
	 * 
	 * @param name
	 *            set the name="" attribute
	 * @param size
	 *            set the size="" attribute
	 */
	public select (String name, String size)
	{
		setName (name);
		setSize (size);
	}

	/**
	 * Basic Constructor.
	 * 
	 * @param name
	 *            set the name="" attribute
	 * @param size
	 *            set the size="" attribute
	 */
	public select (String name, int size)
	{
		setName (name);
		setSize (size);
	}

	/**
	 * Basic Constructor.
	 * 
	 * @param name
	 *            set the name="" attribute
	 * @param element
	 *            provide a group of strings to be converted to options
	 *            elements.
	 */
	public select (String name, String[] element)
	{
		setName (name);
		addElement (element);
	}

	/**
	 * Basic Constructor.
	 * 
	 * @param name
	 *            set the name="" attribute
	 * @param element
	 *            provide a group of strings to be converted to options
	 *            elements.
	 */
	public select (String name, option[] element)
	{
		setName (name);
		addElement (element);
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
	public select addElement (String hashcode, Element element)
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
	public select addElement (String hashcode, String element)
	{
		addElementToRegistry (hashcode, element);
		return (this);
	}

	/**
	 * Adds an Element to the Element.
	 * 
	 * @param element
	 *            adds and Element to the Element.
	 */
	public select addElement (Element element)
	{
		addElementToRegistry (element);
		return (this);
	}

	/**
	 * Adds a group of elements to the select element.
	 * 
	 * @param element
	 *            adds a group of elements to the select element.
	 */
	public select addElement (option[] element)
	{
		for (int x = 0; x < element.length; x++)
		{
			addElementToRegistry (element[x]);
		}
		return (this);
	}

	/**
	 * Adds an Element to the Element.
	 * 
	 * @param element
	 *            adds and Element to the Element.
	 */
	public select addElement (String element)
	{
		addElementToRegistry (element);
		return (this);
	}

	/**
	 * Creates a group of option elements and adds them to this select.
	 * 
	 * @param element
	 *            adds a group of option elements to this select.
	 */
	public select addElement (String[] element)
	{
		option[] options = new option ().addElement (element);
		addElement (options);
		return (this);
	}

	/**
	 * Sets the name="" attribute
	 * 
	 * @param name
	 *            the name="" attribute
	 */
	public select setName (String name)
	{
		addAttribute ("name", name);
		return this;
	}

	/**
	 * Sets the size="" attribute
	 * 
	 * @param size
	 *            the size="" attribute
	 */
	public select setSize (String size)
	{
		addAttribute ("size", size);
		return this;
	}

	/**
	 * Sets the size="" attribute
	 * 
	 * @param size
	 *            the size="" attribute
	 */
	public select setSize (int size)
	{
		setSize (Integer.toString (size));
		return this;
	}

	/**
	 * Sets the multiple value
	 * 
	 * @param multiple
	 *            true or false
	 */
	public select setMultiple (boolean multiple)
	{
		if (multiple == true)
			addAttribute ("multiple", "multiple");
		else
			removeAttribute ("multiple");
		return (this);
	}

	/**
	 * Sets the tabindex="" attribute
	 * 
	 * @param index the tabindex="" attribute
	 */
	public select setTabindex (String index)
	{
		addAttribute ("tabindex", index);
		return this;
	}

	/**
	 * Sets the tabindex="" attribute
	 * 
	 * @param index the tabindex="" attribute
	 */
	public select setTabindex (int index)
	{
		setTabindex (Integer.toString (index));
		return this;
	}

	/**
	 * Sets the disabled value
	 * 
	 * @param disabled
	 *            true or false
	 */
	public select setDisabled (boolean disabled)
	{
		if (disabled == true)
			addAttribute ("disabled", "disabled");
		else
			removeAttribute ("disabled");
		return (this);
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 */
	public select removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}

	/**
	 * The onload event occurs when the user agent finishes loading a window or
	 * all frames within a frameset. This attribute may be used with body and
	 * frameset elements.
	 * 
     * @param script The script
	 */
	public void setOnLoad (String script)
	{
		addAttribute ("onload", script);
	}

	/**
	 * The onunload event occurs when the user agent removes a document from a
	 * window or frame. This attribute may be used with body and frameset
	 * elements.
	 * 
     * @param script The script
	 */
	public void setOnUnload (String script)
	{
		addAttribute ("onunload", script);
	}

	/**
	 * The onsubmit event occurs when a form is submitted. It only applies to
	 * the FORM element.
	 * 
     * @param script The script
	 */
	public void setOnSubmit (String script)
	{
		addAttribute ("onsubmit", script);
	}

	/**
	 * The onreset event occurs when a form is reset. It only applies to the
	 * FORM element.
	 * 
     * @param script The script
	 */
	public void setOnReset (String script)
	{
		addAttribute ("onreset", script);
	}

	/**
	 * The onselect event occurs when a user selects some text in a text field.
	 * This attribute may be used with the input and textarea elements.
	 * 
     * @param script The script
	 */
	public void setOnSelect (String script)
	{
		addAttribute ("onselect", script);
	}

	/**
	 * The onchange event occurs when a control loses the input focus and its
	 * value has been modified since gaining focus. This attribute applies to
	 * the following elements: input, select, and textarea.
	 * 
     * @param script The script
	 */
	public void setOnChange (String script)
	{
		addAttribute ("onchange", script);
	}

	/**
	 * The onclick event occurs when the pointing device button is clicked over
	 * an element. This attribute may be used with most elements.
	 * 
     * @param script The script
	 */
	public void setOnClick (String script)
	{
		addAttribute ("onclick", script);
	}

	/**
	 * The ondblclick event occurs when the pointing device button is double
	 * clicked over an element. This attribute may be used with most elements.
	 * 
     * @param script The script
	 */
	public void setOnDblClick (String script)
	{
		addAttribute ("ondblclick", script);
	}

	/**
	 * The onmousedown event occurs when the pointing device button is pressed
	 * over an element. This attribute may be used with most elements.
	 * 
     * @param script The script
	 */
	public void setOnMouseDown (String script)
	{
		addAttribute ("onmousedown", script);
	}

	/**
	 * The onmouseup event occurs when the pointing device button is released
	 * over an element. This attribute may be used with most elements.
	 * 
     * @param script The script
	 */
	public void setOnMouseUp (String script)
	{
		addAttribute ("onmouseup", script);
	}

	/**
	 * The onmouseover event occurs when the pointing device is moved onto an
	 * element. This attribute may be used with most elements.
	 * 
     * @param script The script
	 */
	public void setOnMouseOver (String script)
	{
		addAttribute ("onmouseover", script);
	}

	/**
	 * The onmousemove event occurs when the pointing device is moved while it
	 * is over an element. This attribute may be used with most elements.
	 * 
     * @param script The script
	 */
	public void setOnMouseMove (String script)
	{
		addAttribute ("onmousemove", script);
	}

	/**
	 * The onmouseout event occurs when the pointing device is moved away from
	 * an element. This attribute may be used with most elements.
	 * 
     * @param script The script
	 */
	public void setOnMouseOut (String script)
	{
		addAttribute ("onmouseout", script);
	}

	/**
	 * The onkeypress event occurs when a key is pressed and released over an
	 * element. This attribute may be used with most elements.
	 * 
     * @param script The script
	 */
	public void setOnKeyPress (String script)
	{
		addAttribute ("onkeypress", script);
	}

	/**
	 * The onkeydown event occurs when a key is pressed down over an element.
	 * This attribute may be used with most elements.
	 * 
     * @param script The script
	 */
	public void setOnKeyDown (String script)
	{
		addAttribute ("onkeydown", script);
	}

	/**
	 * The onkeyup event occurs when a key is released over an element. This
	 * attribute may be used with most elements.
	 * 
     * @param script The script
	 */
	public void setOnKeyUp (String script)
	{
		addAttribute ("onkeyup", script);
	}
}
