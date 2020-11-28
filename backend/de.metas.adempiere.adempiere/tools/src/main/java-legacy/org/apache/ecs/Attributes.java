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

/**
    This interface describes the attributes within an element. It is 
    implemented by ElementAttributes.

    @version $Id: Attributes.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public interface Attributes
{
    /**
        Does this element attribute value need a =""?
    */
    public static final String NO_ATTRIBUTE_VALUE = "ECS_NO_ATTRIBUTE_VALUE";

    /**
        Set the state of the attribute filter.
        @param filter_attribute_state do we need to filter attributes?
    */
    public Element setAttributeFilterState(boolean filter_attribute_state);

    /**
        Set the AttributeFilter that should be used.
        @param attribute_filter set the attribute filter to be used.
    */
    public Element setAttributeFilter(Filter attribute_filter);

    /**
        Get the AttributeFilter that is in use.
    */
    public Filter getAttributeFilter();

    /**
        Add an attribute to the Element.
        @param name name of the attribute
        @param element value of the attribute.
    */
    public Element addAttribute(String name,Object element);

    /**
        Add an attribute to the Element.
        @param name name of the attribute
        @param element value of the attribute.
    */
    public Element addAttribute(String name, int element);

    /**
        Add an attribute to the Element.
        @param name name of the attribute
        @param element value of the attribute.
    */
    public Element addAttribute(String name, String element);

    /**
        Add an attribute to the Element.
        @param name name of the attribute
        @param element value of the attribute.
    */
    public Element addAttribute(String name, Integer element);
    
    /**
        Remove an attribute from the element.
        @param name remove the attribute of this name
    */
    public Element removeAttribute(String name);

    /**
        Does the element have an attribute.
        @param name of the attribute to ask the element for.
    */
    public boolean hasAttribute(String name);

    /**
        Set the character used to quote attributes.
        @param  quote_char character used to quote attributes
    */
    public Element setAttributeQuoteChar(char quote_char);

    /**
        Get the character used to quote attributes.
    */
    public char getAttributeQuoteChar();

    /**
        Set the equality sign for an attribute.
        @param equality_sign The equality sign used for attributes.
    */
    public Element setAttributeEqualitySign(char equality_sign);

    /**
        Get the equality sign for an attribute.
    */
    public char getAttributeEqualitySign();

    /**
        Do we surround attributes with qoutes?
    */
    public boolean getAttributeQuote();

    /**
        Set wether or not we surround the attributes with quotes.
    */
    public Element setAttributeQuote(boolean attribute_quote);
} 
