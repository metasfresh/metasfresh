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
package org.apache.ecs.xml;

import org.apache.ecs.Element;
import org.apache.ecs.Filter;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;
import org.apache.ecs.StringElement;

/**
 * This class creates a generic &lt;&gt; tag.
 * 
 * @version $Id: XML.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
 * @author <a href="mailto:snagy@servletapi.com">Stephan Nagy </a>
 * @author <a href="mailto:jon@clearink.com">Jon S. Stevens </a>
 */
public class XML extends MultiPartElement
	implements Printable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7562733665733930129L;

	/**
	 * Default constructor use set* Methods. With this name.
	 * 
	 * @param element_type
	 *            The name of this element.
	 */
	public XML (String element_type)
	{
		setElementType (element_type);
	}

	/**
	 * Construct a new XML element with this name, <br>
	 * and specify if it needs the element tag closed.
	 * 
	 * @param element_type
	 *            The name of this element.
	 * @param close
	 *            Should it have a closing tag
	 */
	public XML (String element_type, boolean close)
	{
		setElementType (element_type);
		setNeedClosingTag (close);
	}

	/**
	 * Construct a new XML element with this name, and specify a filter for it.
	 * 
	 * @param element_type
	 *            The name of this element.
	 * @param filter
	 *            a new Filter for this element override the default.
	 */
	public XML (String element_type, Filter filter)
	{
		setElementType (element_type);
		setFilter (filter);
	}

	/**
	 * Construct a new XML element with this name, and specify a filter for it.
	 * 
	 * @param element_type
	 *            The name of this element.
	 * @param close
	 *            Should it have a closing tag
	 * @param filter
	 *            Should this element be filtered?
	 */
	public XML (String element_type, boolean close, boolean filter)
	{
		setElementType (element_type);
		setNeedClosingTag (close);
		setFilterState (filter);
	}

	/**
	 * Construct a new XML element with this name, and specify a filter for it.
	 * 
	 * @param element_type
	 *            The name of this element.
	 * @param close
	 *            Should it have a closing tag
	 * @param filter
	 *            a new Filter for this element override the default.
	 */
	public XML (String element_type, boolean close, Filter filter)
	{
		setElementType (element_type);
		setNeedClosingTag (close);
		setFilter (filter);
	}

	/**
	 * Add a new attribute to this XML tag.
	 * 
	 * @param attribute
	 *            the attribute name
	 * @param attribute_value
	 *            the value of the attribute set this to <BR>
	 *            <code>"ECS_NO_ATTRIBUTE_VALUE"</code> if this attribute <BR>
	 *            doesn't take a value.
	 */
	public XML addXMLAttribute (String attribute, String attribute_value)
	{
		addAttribute (attribute, attribute_value);
		return (this);
	}

	/**
	 * Add an element to the valuie of &lt;&gt;VALUE&lt;/&gt;
	 * 
	 * @param element
	 *            the value of &lt;&gt;VALUE&lt;/&gt;
	 */
	public XML addElement (String element)
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
	public XML addElement (String hashcode, Element element)
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
	public XML addElement (String hashcode, String element)
	{
		addElementToRegistry (hashcode, element);
		return (this);
	}

	/**
	 * Add an element to the valuie of &lt;&gt;VALUE&lt;/&gt;
	 * 
	 * @param element
	 *            the value of &lt;&gt;VALUE&lt;/&gt;
	 */
	public XML addElement (Element element)
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
	public XML removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}

	public boolean getNeedLineBreak ()
	{
		boolean linebreak = true;

		java.util.Enumeration en = elements ();

		// if this tag has one child, and it's a String, then don't
		// do any linebreaks to preserve whitespace

		while (en.hasMoreElements ())
		{
			Object obj = en.nextElement ();
			if (obj instanceof StringElement)
			{
				linebreak = false;
				break;
			}

		}

		return linebreak;
	}

	public boolean getBeginEndModifierDefined ()
	{
		boolean answer = false;

		if (!this.getNeedClosingTag ())
			answer = true;

		return answer;
	}

	public char getBeginEndModifier ()
	{
		return '/';
	}
}
