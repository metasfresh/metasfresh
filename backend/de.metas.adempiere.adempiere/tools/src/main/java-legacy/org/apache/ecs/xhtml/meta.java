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
import org.apache.ecs.Printable;
import org.apache.ecs.SinglePartElement;

/**
 * This class creates a &lt;meta&gt; tag.
 * 
 * @version $Id: meta.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
 * @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>
 */
public class meta extends SinglePartElement
	implements Printable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7943556377713623573L;

	/**
	 * Private initialization routine.
	 */
	{
		setElementType ("meta");
		setCase (LOWERCASE);
		setAttributeQuote (true);
		setBeginEndModifier ('/');
	}

	/**
	 * This method creates a &lt;meta&gt; tag.
	 */
	public meta ()
	{
	}

	/**
	 * Sets the content="" attribute.
	 * 
	 * @param content
	 *            the value that should go into the content attribute
	 */
	public meta setContent (String content)
	{
		addAttribute ("content", content);
		return this;
	}

	/**
	 * Sets the name="" attribute.
	 * 
	 * @param content
	 *            the value that should go into the name attribute
	 */
	public meta setName (String name)
	{
		addAttribute ("name", name);
		return this;
	}

	/**
	 * Sets the name="" attribute.
	 * 
	 * @param content
	 *            the value that should go into the name attribute
	 */
	public meta setName (String name, String content)
	{
		addAttribute ("name", name);
		addAttribute ("content", content);
		return this;
	}

	/**
	 * Sets the scheme="" attribute.
	 * 
	 * @param content
	 *            the value that should go into the scheme attribute
	 */
	public meta setScheme (String scheme)
	{
		addAttribute ("scheme", scheme);
		return this;
	}

	/**
	 * Sets the http-equiv="" attribute.
	 * 
	 * @param content
	 *            the value that should go into the http-equiv attribute
	 */
	public meta setHttpEquiv (String http_equiv)
	{
		addAttribute ("http-equiv", http_equiv);
		return this;
	}

	/**
	 * Sets the http-equiv="" attribute.
	 * 
	 * @param content
	 *            the value that should go into the http-equiv attribute
	 */
	public meta setHttpEquiv (String http_equiv, String content)
	{
		addAttribute ("content", content);
		addAttribute ("http-equiv", http_equiv);
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
	public meta addElement (String hashcode, Element element)
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
	public meta addElement (String hashcode, String element)
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
	public meta addElement (Element element)
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
	public meta addElement (String element)
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
	public meta removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}
}
