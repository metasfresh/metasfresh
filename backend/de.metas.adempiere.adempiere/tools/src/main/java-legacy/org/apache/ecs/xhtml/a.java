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
import org.apache.ecs.KeyEvents;
import org.apache.ecs.MouseEvents;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;

/**
 * This class creates an &lt;a&gt; tag.
 * <P>
 * Please refer to the TestBed.java file for example code usage.
 * 
 * @version $Id: a.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy </a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens </a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver </a>
 */
public class a extends MultiPartElement
	implements Printable, FocusEvents, MouseEvents, KeyEvents
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8003836277522682517L;

	/**
	 * Private initialization routine.
	 */
	{
		setElementType ("a");
		setCase (LOWERCASE); // XHTML specific
		setAttributeQuote (true); // XHTML specific
		setPrettyPrint(false);
	}

	public static final String 	TARGET_BLANK 	= "_blank";
	public static final String 	TARGET_PARENT 	= "_parent";
	public static final String 	TARGET_SELF 	= "_self";
	public static final String 	TARGET_TOP 		= "_top";
	
	/**
	 * Basic constructor. You need to set the attributes using the set* methods.
	 */
	public a ()
	{
	}

	/**
	 * This constructor creates ah &lt;a&gt; tag.
	 * 
	 * @param href
	 *            the URI that goes between double quotes
	 */
	public a (String href)
	{
		setHref (href);
	}

	/**
	 * This constructor creates an &lt;a&gt; tag.
	 * 
	 * @param href
	 *            the URI that goes between double quotes
	 * @param value
	 *            what goes between &lt;start_tag&gt; &lt;end_tag&gt;
	 */
	public a (String href, String value)
	{
		setHref (href);
		addElement (value);
	}

	/**
	 * This constructor creates an &lt;a&gt; tag.
	 * 
	 * @param href
	 *            the URI that goes between double quotes
	 * @param value
	 *            what goes between &lt;start_tag&gt; &lt;end_tag&gt;
	 */
	public a (String href, Element value)
	{
		setHref (href);
		addElement (value);
	}

	/**
	 * This constructor creates an &lt;a&gt; tag.
	 * 
	 * @param href
	 *            the URI that goes between double quotes
	 * @param name
	 *            the name="" attribute
	 * @param value
	 *            what goes between &lt;start_tag&gt; &lt;end_tag&gt;
	 */
	public a (String href, String name, String value)
	{
		setHref (href);
		setName (name);
		addElement (value);
	}

	/**
	 * This constructor creates an &lt;a&gt; tag.
	 * 
	 * @param href
	 *            the URI that goes between double quotes
	 * @param name
	 *            the name="" attribute
	 * @param value
	 *            what goes between &lt;start_tag&gt; &lt;end_tag&gt;
	 */
	public a (String href, String name, Element value)
	{
		setHref (href);
		setName (name);
		addElement (value);
	}

	/**
	 * This constructor creates an &lt;a&gt; tag.
	 * 
	 * @param href
	 *            the URI that goes between double quotes
	 * @param name
	 *            the optional name="" attribute
	 * @param target
	 *            the target="" attribute
	 * @param value
	 *            the value that goes between &lt;start_tag&gt; &lt;end_tag&gt;
	 */
	public a (String href, String name, String target, Element value)
	{
		setHref (href);
		if (name != null)
			setName (name);
		setTarget (target);
		addElement (value);
	}

	/**
	 * This constructor creates an &lt;a&gt; tag.
	 * 
	 * @param href
	 *            the URI that goes between double quotes
	 * @param name
	 *            the optional name="" attribute
	 * @param target
	 *            the optional target="" attribute
	 * @param value
	 *            the value that goes between &lt;start_tag&gt; &lt;end_tag&gt;
	 */
	public a (String href, String name, String target, String value)
	{
		setHref (href);
		if (name != null)
			setName (name);
		if (target != null)
			setTarget (target);
		addElement (value);
	}

	/**
	 * This constructor creates an &lt;a&gt; tag.
	 * 
	 * @param href
	 *            the URI that goes between double quotes
	 * @param name
	 *            the optional name="" attribute
	 * @param target
	 *            the target="" attribute
	 * @param lang
	 *            the lang="" and xml:lang="" attributes
	 * @param value
	 *            the value that goes between &lt;start_tag&gt; &lt;end_tag&gt;
	 */
	public a (String href, String name, String target, String lang, String value)
	{
		setHref (href);
		if (name != null)
			setName (name);
		setTarget (target);
		setLang (lang);
		addElement (value);
	}

	/**
	 * This constructor creates an &lt;a&gt; tag.
	 * 
	 * @param href
	 *            the URI that goes between double quotes
	 * @param name
	 *            the optional name="" attribute
	 * @param target
	 *            the target="" attribute
	 * @param lang
	 *            the lang="" and xml:lang="" attributes
	 * @param value
	 *            the value that goes between &lt;start_tag&gt; &lt;end_tag&gt;
	 */
	public a (String href, String name, String target, String lang,	Element value)
	{
		setHref (href);
		if (name != null)
			setName (name);
		setTarget (target);
		setLang (lang);
		addElement (value);
	}

	/**
	 * Sets the href="" attribute
	 * 
	 * @param href
	 *            the href="" attribute
	 */
	public a setHref (String href)
	{
		addAttribute ("href", href);
		return this;
	}

	/**
	 * Sets the name="" attribute
	 * 
	 * @param name
	 *            the name="" attribute
	 */
	public a setName (String name)
	{
		addAttribute ("name", name);
		return this;
	}

	/**
	 * Sets the target="" attribute
	 * 
	 * @param target
	 *            the target="" attribute
	 */
	public a setTarget (String target)
	{
		addAttribute ("target", target);
		return this;
	}

	/**
	 * Sets the rel="" attribute
	 * 
	 * @param rel
	 *            the rel="" attribute
	 */
	public a setRel (String rel)
	{
		addAttribute ("rel", rel);
		return this;
	}

	/**
	 * Sets the rev="" attribute
	 * 
	 * @param rev
	 *            the rev="" attribute
	 */
	public a setRev (String rev)
	{
		addAttribute ("rev", rev);
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
	 * @param element
	 *            Adds an Element to the element.
	 */
	public a addElement (Element element)
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
	public a addElement (String element)
	{
		addElementToRegistry (element);
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
	public a addElement (String hashcode, Element element)
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
	public a addElement (String hashcode, String element)
	{
		addElementToRegistry (hashcode, element);
		return (this);
	}

	/**
	 * Removes an Element from the element.
	 * 
	 * @param hashcode
	 *            the name of the element to be removed.
	 */
	public a removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}

	/**
	 * The onfocus event occurs when an element receives focus either by the
	 * pointing device or by tabbing navigation. This attribute may be used with
	 * the following elements: label, input, select, textarea, and button.
	 * 
	 * @param script script
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
	 * @param script script
	 */
	public void setOnBlur (String script)
	{
		addAttribute ("onblur", script);
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
		addAttribute ("onnlouseup", script);
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
			if (obj instanceof img)
				i++;
		}
		if (i == j)
			return false;
		return true;
	}
}
