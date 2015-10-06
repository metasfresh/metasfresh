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
 * This class creates a &lt;head&gt;&lt;/head&gt; tag.
 * 
 * @version $Id: head.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy </a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens </a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver </a>
 */
public class head extends MultiPartElement
	implements Printable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2597905260419035106L;

	/**
	 * Private initialization routine.
	 */
	{
		setElementType ("head");
		setCase (LOWERCASE);
		setAttributeQuote (true);
	}

	/**
	 * Basic constructor. Use the set* methods to set the attibutes.
	 */
	public head ()
	{
	}

	/**
	 * This method creates a &lt;head&gt; tag and sets it value
	 * 
	 * @param value
	 *            the value that goes between &lt;start_tag&gt; &lt;end_tag&gt;
	 */
	public head (String value)
	{
		addElement (value);
	}

	/**
	 * This method creates a &lt;head&gt; tag and sets it value
	 * 
	 * @param value
	 *            the value that goes between &lt;start_tag&gt; &lt;end_tag&gt;
	 */
	public head (Element value)
	{
		addElement (value);
	}

	/**
	 * Sets the PROFILE="" attribue
	 * 
	 * @param profile
	 *            the url to one or more meta data profiles seperated by
	 *            whitespace
	 */
	public head setProfile (String profile)
	{
		addAttribute ("profile", profile);
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
	public head addElement (String hashcode, Element element)
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
	public head addElement (String hashcode, String element)
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
	public head addElement (Element element)
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
	public head addElement (String element)
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
	public head removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}
}
