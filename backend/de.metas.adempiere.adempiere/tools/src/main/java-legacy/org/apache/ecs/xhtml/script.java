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
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;

/**
 * This class creates a &lt;script&gt; tag.
 * <P>
 * Note that XHTML script tag doesn't hide the script text withing comments like
 * its HTML counterpart does. This difference is caused by the fact that XHTML
 * is XML and XML parsers can throw the comments out. Use this tag with browsers
 * that support scripting language(s).
 * 
 * @version $Id: script.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy </a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens </a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver </a>
 */
public class script extends MultiPartElement
	implements Printable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -117144945976065646L;

	/**
	 * Private initialization routine.
	 */
	{
		setElementType ("script");
		setCase (LOWERCASE);
		setAttributeQuote (true);
		setLanguage ("JavaScript1.2");
		setType("text/javascript");
	}

	/**
	 * Basic constructor.
	 */
	public script ()
	{
	}

	/**
	 * Basic constructor.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public script (Element element)
	{
		addElement (element);
	}

	/**
	 * Basic constructor.
	 * 
	 * @param element
	 *            Optionally adds an Element to the element.
	 * @param src
	 *            sets the src="" attribute
	 */
	public script (Element element, String src)
	{
		if (element != null)
			addElement (element);
		setSrc (src);
	}

	/**
	 * Basic constructor.
	 * 
	 * @param element
	 *            Optionally adds an Element to the element.
	 * @param src
	 *            sets the src="" attribute
	 * @param type
	 *            sets the type="" attribute
	 */
	public script (Element element, String src, String type)
	{
		if (element != null)
			addElement (element);
		setSrc (src);
		setType (type);
	}

	/**
	 * Basic constructor.
	 * 
	 * @param element
	 *            Optionally adds an Element to the element.
	 * @param src
	 *            sets the src="" attribute
	 * @param type
	 *            sets the type="" attribute
	 * @param lang
	 *            sets the language="" attribute
	 */
	public script (Element element, String src, String type, String lang)
	{
		if (element != null)
			addElement (element);
		setSrc (src);
		setType (type);
		setLanguage (lang);
	}

	/**
	 * Basic constructor.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 */
	public script (String element)
	{
		addElement (element);
	}

	/**
	 * Basic constructor.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 * @param src
	 *            sets the src="" attribute
	 */
	public script (String element, String src)
	{
		addElement (element);
		setSrc (src);
	}

	/**
	 * Basic constructor.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 * @param src
	 *            sets the src="" attribute
	 * @param type
	 *            sets the type="" attribute
	 */
	public script (String element, String src, String type)
	{
		addElement (element);
		setSrc (src);
		setType (type);
	}

	/**
	 * Basic constructor.
	 * 
	 * @param element
	 *            Adds an Element to the element.
	 * @param src
	 *            sets the src="" attribute
	 * @param type
	 *            sets the type="" attribute
	 * @param lang
	 *            sets the language="" attribute
	 */
	public script (String element, String src, String type, String lang)
	{
		addElement (element);
		setSrc (src);
		setType (type);
		setLanguage (lang);
	}

	/**
	 * Sets the src="" attribute
	 * 
	 * @param src
	 *            the src="" attribute
	 */
	public script setSrc (String src)
	{
		addAttribute ("src", src);
		return this;
	}

	/**
	 * Sets the type="" attribute
	 * 
	 * @param type
	 *            the type="" attribute
	 */
	public script setType (String type)
	{
		addAttribute ("type", type);
		return this;
	}

	/**
	 * Sets the language="" attribute
	 * 
	 * @param language
	 *            the language="" attribute
	 */
	public script setLanguage (String language)
	{
		addAttribute ("language", language);
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
	public script addElement (String hashcode, Element element)
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
	public script addElement (String hashcode, String element)
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
	public script addElement (Element element)
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
	public script addElement (String element)
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
	public script removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}
}
