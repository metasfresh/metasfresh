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
import org.apache.ecs.FocusEvents;
import org.apache.ecs.FormEvents;
import org.apache.ecs.KeyEvents;
import org.apache.ecs.MouseEvents;
import org.apache.ecs.PageEvents;
import org.apache.ecs.Printable;
import org.apache.ecs.SinglePartElement;

/**
 * This class creates a &lt;input&gt; tag.
 * 
 * @version $Id: input.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy </a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens </a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver </a>
 */
public class input extends SinglePartElement
	implements Printable, FormEvents, PageEvents, FocusEvents, MouseEvents,
	KeyEvents
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8437101559802594863L;

	public static final String	TYPE_TEXT		= "text";

	public static final String	TYPE_PASSWORD	= "password";

	public static final String	TYPE_CHECKBOX	= "checkbox";

	public static final String	TYPE_RADIO		= "radio";

	public static final String	TYPE_FILE		= "file";

	public static final String	TYPE_BUTTON		= "button";

	public static final String	TYPE_IMAGE		= "image";

	public static final String	TYPE_HIDDEN		= "hidden";

	public static final String	TYPE_SUBMIT		= "submit";

	public static final String	TYPE_RESET		= "reset";
	
	
	/**
	 * Private initialization routine.
	 */
	{
		setElementType ("input");
		setCase (LOWERCASE);
		setAttributeQuote (true);
		setBeginEndModifier ('/');
	}

	/**
	 * Basic constructor. Use the set* methods to set the values of the
	 * attributes.
	 */
	public input ()
	{
	}

	/**
	 * Basic constructor. Use the set* methods to set the values of the
	 * attributes.
	 */
	public input (String type, String name, String value)
	{
		setType (type);
		setName (name);
		setValue (value);
	}

	/**
	 * Basic constructor. Use the set* methods to set the values of the
	 * attributes.
	 */
	public input (String type, String name, int value)
	{
		setType (type);
		setName (name);
		setValue (value);
	}

	/**
	 * Basic constructor. Use the set* methods to set the values of the
	 * attributes.
	 */
	public input (String type, String name, Integer value)
	{
		setType (type);
		setName (name);
		setValue (value);
	}

	/**
	 * Basic constructor. Use the set* methods to set the values of the
	 * attributes.
	 */
	public input (String type, String name, double value)
	{
		setType (type);
		setName (name);
		setValue (value);
	}

	/**
	 * Sets the type="" attribute
	 * 
	 * @param type
	 *            the type="" attribute
	 */
	public input setType (String type)
	{
		addAttribute ("type", type);
		return this;
	}

	/**
	 * Sets the src="" attribute
	 * 
	 * @param src
	 *            the src="" attribute
	 */
	public input setSrc (String src)
	{
		addAttribute ("src", src);
		return this;
	}

	/**
	 * Sets the border="" attribute
	 * 
	 * @param border
	 *            the border="" attribute
	 */
	public input setBorder (int border)
	{
		addAttribute ("border", Integer.toString (border));
		return this;
	}

	/**
	 * Sets the alt="" attribute
	 * 
	 * @param alt
	 *            the alt="" attribute
	 */
	public input setAlt (String alt)
	{
		addAttribute ("alt", alt);
		return this;
	}

	/**
	 * Sets the name="" attribute
	 * 
	 * @param name
	 *            the name="" attribute
	 */
	public input setName (String name)
	{
		addAttribute ("name", name);
		return this;
	}

	/**
	 * Sets the value="" attribute
	 * 
	 * @param value
	 *            the value="" attribute
	 */
	public input setValue (String value)
	{
		addAttribute ("value", value);
		return this;
	}

	/**
	 * Sets the value="" attribute
	 * 
	 * @param value
	 *            the value="" attribute
	 */
	public input setValue (int value)
	{
		addAttribute ("value", Integer.toString (value));
		return this;
	}

	/**
	 * Sets the value="" attribute
	 * 
	 * @param value
	 *            the value="" attribute
	 */
	public input setValue (Integer value)
	{
		addAttribute ("value", value.toString ());
		return this;
	}

	/**
	 * Sets the value="" attribute
	 * 
	 * @param value
	 *            the value="" attribute
	 */
	public input setValue (double value)
	{
		addAttribute ("value", Double.toString (value));
		return this;
	}

	/**
	 * Sets the accept="" attribute
	 * 
	 * @param accept
	 *            the accept="" attribute
	 */
	public input setAccept (String accept)
	{
		addAttribute ("accept", accept);
		return this;
	}

	/**
	 * Sets the size="" attribute
	 * 
	 * @param size
	 *            the size="" attribute
	 */
	public input setSize (String size)
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
	public input setSize (int size)
	{
		setSize (Integer.toString (size));
		return this;
	}

	/**
	 * Sets the maxlength="" attribute
	 * 
	 * @param maxlength
	 *            the maxlength="" attribute
	 */
	public input setMaxlength (String maxlength)
	{
		addAttribute ("maxlength", maxlength);
		return this;
	}

	/**
	 * Sets the maxlength="" attribute
	 * 
	 * @param maxlength
	 *            the maxlength="" attribute
	 */
	public input setMaxlength (int maxlength)
	{
		setMaxlength (Integer.toString (maxlength));
		return this;
	}

	/**
	 * Sets the usemap="" attribute
	 * 
	 * @param usemap
	 *            the usemap="" attribute
	 */
	public input setUsemap (String usemap)
	{
		addAttribute ("usemap", usemap);
		return this;
	}

	/**
	 * Sets the tabindex="" attribute
	 * 
	 * @param index
	 *            the tabindex="" attribute
	 */
	public input setTabindex (String index)
	{
		addAttribute ("tabindex", index);
		return this;
	}

	/**
	 * Sets the tabindex="" attribute
	 * 
	 * @param index
	 *            the tabindex="" attribute
	 */
	public input setTabindex (int index)
	{
		setTabindex (Integer.toString (index));
		return this;
	}

	/**
	 * Sets the checked value
	 * 
	 * @param checked
	 *            true or false
	 */
	public input setChecked (boolean checked)
	{
		if (checked == true)
			addAttribute ("checked", "checked");
		else
			removeAttribute ("checked");
		return (this);
	}

	/**
	 * Sets the readonly value
	 * 
	 * @param readonly
	 *            true or false
	 */
	public input setReadOnly (boolean readonly)
	{
		if (readonly == true)
			addAttribute ("readonly", "readonly");
		else
			removeAttribute ("readonly");
		return (this);
	}

	/**
	 * Sets the disabled value
	 * 
	 * @param disabled
	 *            true or false
	 */
	public input setDisabled (boolean disabled)
	{
		if (disabled == true)
			addAttribute ("disabled", "disabled");
		else
			removeAttribute ("disabled");
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
	public input addElement (String hashcode, Element element)
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
	public input addElement (String hashcode, String element)
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
	public input addElement (Element element)
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
	public input addElement (String element)
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
	public input removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}

	/**
	 * The onsubmit event occurs when a form is submitted. It only applies to
	 * the FORM element.
	 * 
	 * @param script
	 *            script
	 */
	public void setOnSubmit (String script)
	{
		addAttribute ("onsubmit", script);
	}

	/**
	 * The onreset event occurs when a form is reset. It only applies to the
	 * FORM element.
	 * 
	 * @param script
	 *            script
	 */
	public void setOnReset (String script)
	{
		addAttribute ("onreset", script);
	}

	/**
	 * The onselect event occurs when a user selects some text in a text field.
	 * This attribute may be used with the INPUT and TEXTAREA elements.
	 * 
	 * @param script
	 *            script
	 */
	public void setOnSelect (String script)
	{
		addAttribute ("onselect", script);
	}

	/**
	 * The onchange event occurs when a control loses the input focus and its
	 * value has been modified since gaining focus. This attribute applies to
	 * the following elements: INPUT, SELECT, and TEXTAREA.
	 * 
	 * @param script
	 *            script
	 */
	public void setOnChange (String script)
	{
		addAttribute ("onchange", script);
	}

	/**
	 * The onload event occurs when the user agent finishes loading a window or
	 * all frames within a FRAMESET. This attribute may be used with BODY and
	 * FRAMESET elements.
	 * 
	 * @param script
	 *            script
	 */
	public void setOnLoad (String script)
	{
		addAttribute ("onload", script);
	}

	/**
	 * The onunload event occurs when the user agent removes a document from a
	 * window or frame. This attribute may be used with BODY and FRAMESET
	 * elements.
	 * 
	 * @param script
	 *            script
	 */
	public void setOnUnload (String script)
	{
		addAttribute ("onunload", script);
	}

	/**
	 * The onfocus event occurs when an element receives focus either by the
	 * pointing device or by tabbing navigation. This attribute may be used with
	 * the following elements: LABEL, INPUT, SELECT, TEXTAREA, and BUTTON.
	 * 
	 * @param script
	 *            script
	 */
	public void setOnFocus (String script)
	{
		addAttribute ("onfocus", script);
	}

	/**
	 * The onblur event occurs when an element loses focus either by the
	 * pointing device or by tabbing navigation. It may be used with the same
	 * elements as onfocus.
	 * 
	 * @param script
	 *            script
	 */
	public void setOnBlur (String script)
	{
		addAttribute ("onblur", script);
	}

	/**
	 * The onclick event occurs when the pointing device button is clicked over
	 * an element. This attribute may be used with most elements.
	 * 
	 * @param script
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
	 * @param script
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
	 * @param script
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
	 * @param script
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
	 * @param script
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
	 * @param script
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
	 * @param script
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
	 * @param script
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
	 * @param script
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
	 * @param script
	 *            script
	 */
	public void setOnKeyUp (String script)
	{
		addAttribute ("onkeyup", script);
	}
}
