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
package org.apache.ecs;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;


/**
    This class is used to create a String element in ECS. A StringElement 
    has no tags wrapped around it, it is an Element without tags.
    
    @version $Id: StringElement.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public class StringElement extends ConcreteElement implements Printable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1589031493456017832L;
	/**
        Basic constructor
    */
    public StringElement()
    {
    }
    
    /** 
        Basic constructor
    */
    public StringElement(String string)
    {
	if (string != null)
	    setTagText(string);
	else
	    setTagText("");
    }

    /** 
        Basic constructor
    */
    public StringElement(Element element)
    {
        addElement(element);
    }

    private StringElement append(String string)
    {
        setTagText(getTagText()+string);
        return this;
    }

    /** 
        Resets the internal string to be empty.
    */
    public StringElement reset()
    {
        setTagText("");
        return this;
    }

	/**
	 * 	Set Tag Text
	 *	@param text text
	 *	@return Element
	 */
	public Element setTagText (String text)
	{
		if (text != null && text.length() > 0)
		{
	        StringCharacterIterator sci = new StringCharacterIterator(text);
	        for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next())
	        {
				int ii = c;
				if (ii > 255)
				{
					setFilterState(true);
					break;
				}
	        }
		}
		return super.setTagText (text);
	}	//	setTagText
	
	/**
	 * 	Set Filter State - don't allow reset
	 *	@param filter_state state
	 *	@return this
	 */
	public Element setFilterState (boolean filter_state)
	{
		if (!getFilterState())
			return super.setFilterState (filter_state);
		return this;
	}	//	setFilterState
	
    /**
        Adds an Element to the element.
        @param  hashcode name of element for hash table
        @param  element Adds an Element to the element.
     */
    public StringElement addElement(String hashcode,Element element)
    {
        addElementToRegistry(hashcode,element);
        return(this);
    }

    /**
        Adds an Element to the element.
        @param  hashcode name of element for hash table
        @param  element Adds an Element to the element.
     */
    public StringElement addElement(String hashcode,String element)
    {
        // We do it this way so that filtering will work.
        // 1. create a new StringElement(element) - this is the only way that setTextTag will get called
        // 2. copy the filter state of this string element to this child.
        // 3. copy the filter for this string element to this child.

        StringElement se = new StringElement(element);
		se.setFilterState(getFilterState());
		se.setFilter(getFilter());
        addElementToRegistry(hashcode,se);
        return(this);
    }

    /**
        Adds an Element to the element.
        @param  element Adds an Element to the element.
     */
    public StringElement addElement(String element)
    {
        addElement(Integer.toString(element.hashCode()),element);
        return(this);
    }
    
    /**
        Adds an Element to the element.
        @param  element Adds an Element to the element.
     */
    public StringElement addElement(Element element)
    {
        addElementToRegistry(element);
        return(this);
    }
    
    /**
        Removes an Element from the element.
        @param hashcode the name of the element to be removed.
    */
    public StringElement removeElement(String hashcode)
    {
        removeElementFromRegistry(hashcode);
        return(this);
    }
    
    protected String createStartTag()
    {
        return("");
    }
    protected String createEndTag()
    {
        return("");
    }
}
