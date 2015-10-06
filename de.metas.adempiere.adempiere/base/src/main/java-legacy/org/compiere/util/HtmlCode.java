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
package org.compiere.util;

import org.apache.ecs.Element;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;

/**
 *  ECS Component Collection.
 *
 *  @author Jorg Janke
 *  @version $Id: HtmlCode.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class HtmlCode extends MultiPartElement
	implements Printable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4231482888802431943L;

	/**
	 * 	HtmlCode
	 */
	public HtmlCode ()
	{
		setNeedClosingTag (false);
		setTagText ("");
		setStartTagChar (' ');
		setEndTagChar (' ');
	}	//	HtmlCode

	/**
		Adds an Element to the element.
		@param  hashcode name of element for hash table
		@param  element Adds an Element to the element.
		@return this
	 */
	public HtmlCode addElement (String hashcode, Element element)
	{
		addElementToRegistry (hashcode, element);
		return (this);
	}


	/**
		Adds an Element to the element.
		@param  hashcode name of element for hash table
		@param  element Adds an Element to the element.
		@return this
	 */
	public HtmlCode addElement (String hashcode, String element)
	{
		addElementToRegistry (hashcode, element);
		return (this);
	}


	/**
		Adds an Element to the element.
		@param  element Adds an Element to the element.
		@return this
	 */
	public HtmlCode addElement (Element element)
	{
		addElementToRegistry (element);
		return (this);
	}

	/**
		Adds an Element to the element.
		@param  element Adds an Element to the element.
		@return this
	 */
	public HtmlCode addElement (String element)
	{
		addElementToRegistry (element);
		return (this);
	}

	/**
		Removes an Element from the element.
		@param hashcode the name of the element to be removed.
		@return this
	 */
	public HtmlCode removeElement (String hashcode)
	{
		removeElementFromRegistry (hashcode);
		return (this);
	}

}	//	HtmlCode
