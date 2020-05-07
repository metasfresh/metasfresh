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

import java.util.Enumeration;

/**
    This class provides a common set of attributes set* methods for all classes.
    It is abstract to prevent direct instantiation.
    @version $Id: ElementAttributes.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/

public abstract class ElementAttributes extends GenericElement implements Attributes
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5646571780677625947L;

	/**
        Basic Constructor.
    */
    public ElementAttributes()
    {
    }

    /**
        Filter to use to escape attribute input.
        By default the attribute filter and the element filter are the same.
        @serial attribute_filter attribute_filter
    */
    private Filter attribute_filter = getFilter();   

    /**
        Should we filter the value of the element attributes
        @serial filter_attribute_state filter_attribute_state
    */
    private boolean filter_attribute_state = ECSDefaults.getDefaultFilterAttributeState();

    /**
        What is the equality character for an attribute.
        @serial attribute_equality_sign attribute_equality_sign
    */
    private char attribute_equality_sign = ECSDefaults.getDefaultAttributeEqualitySign();

    /**
        What character should we use for quoting attributes.
        @serial attribute_quote_char attribute_quote_char
    */
    private char attribute_quote_char = ECSDefaults.getDefaultAttributeQuoteChar();

    /**
        Should we wrap quotes around an attribute?
        @serial attribute_quote attribute_quote
    */
    private boolean attribute_quote = ECSDefaults.getDefaultAttributeQuote();

    /**
        Set the character used to quote attributes.
        @param  quote_char character used to quote attributes
    */
    public Element setAttributeQuoteChar(char quote_char)
    {
        attribute_quote_char = quote_char;
        return(this);
    }

    /**
        Get the character used to quote attributes.
    */
    public char getAttributeQuoteChar()
    {
        return(attribute_quote_char);
    }

    /**
        Set the equality sign for an attribute.
        @param equality_sign The equality sign used for attributes.
    */
    public Element setAttributeEqualitySign(char equality_sign)
    {
        attribute_equality_sign = equality_sign;
        return(this);
    }

    /**
        Get the equality sign for an attribute.
    */
    public char getAttributeEqualitySign()
    {
        return(attribute_equality_sign);
    }

    /*
        Do we surround attributes with qoutes?
    */
    public boolean getAttributeQuote()
    {
        return(attribute_quote);
    }

    /**
        Set wether or not we surround the attributes with quotes.
    */
    public Element setAttributeQuote(boolean attribute_quote)
    {
        this.attribute_quote = attribute_quote;
        return(this);
    }

    /**
        Set the element id for Cascading Style Sheets.
    */
    public Element setID(String id)
    {
        addAttribute("id",id);
        return(this);
    }

    /**
        Set the element class for Cascading Style Sheets.
    */
    public Element setClass(String element_class)
    {
        addAttribute("class",element_class);
        return(this);
    }

    /**
        Sets the LANG="" attribute
        @param   lang  the LANG="" attribute
    */
    public Element setLang(String lang)
    {
        addAttribute("lang",lang);
        return(this);
    }

    /**
        Sets the STYLE="" attribute
        @param   style  the STYLE="" attribute
    */
    public Element setStyle(String style)
    {
        addAttribute("style",style);
        return(this);
    }

    /**
        Sets the DIR="" attribute
        @param   dir  the DIR="" attribute
    */
    public Element setDir(String dir)
    {
        addAttribute("dir",dir);
        return(this);
    }
    /**
        Sets the TITLE="" attribute
        @param   title  the TITLE="" attribute
    */
    public Element setTitle(String title)
    {
        addAttribute("title",title);
        return(this);
    }

    /**
        Find out if we want to filter the elements attributes or not.
    */
    protected boolean getAttributeFilterState()
    {
        return(filter_attribute_state);
    }

    /**
        Tell the element if we want to filter its attriubtes.
        @param filter_attribute_state do we want to filter the attributes of this element?
    */
    public Element setAttributeFilterState(boolean filter_attribute_state)
    {
        this.filter_attribute_state = filter_attribute_state;
        return(this);
    }

    /**
        Set up a new filter for all element attributes.
        @param attribute_filter the filter we want to use for element attributes.  By <br>
        default it is the same as is used for the value of the tag. It is assumed<br>
        that if you create a new filter you must want to use it.
    */
    public Element setAttributeFilter(Filter attribute_filter)
    {
        filter_attribute_state = true; // If your setting up a filter you must want to filter.
        this.attribute_filter = attribute_filter;
        return(this);
    }

    /**
        Get the filter for all element attributes.
        Filter the filter we want to use for element attributes.  By <br>
        default it is the same as is used for the value of the tag. It is assumed<br>
        that if you create a new filter you must want to use it.
    */
    public Filter getAttributeFilter()
    {
        return(this.attribute_filter);
    }


    /** Add an attribute to the element. */
    public Element addAttribute(String attribute_name, Object attribute_value)
    {
        getElementHashEntry().put(attribute_name, attribute_value);
        return(this);
    }

    /** Add an attribute to the element. */
    public Element addAttribute(String attribute_name, int attribute_value)
    {
        getElementHashEntry().put(attribute_name, new Integer(attribute_value));
        return(this);
    }

    /** Add an attribute to the element. */
    public Element addAttribute(String attribute_name, String attribute_value)
    {
    	if (attribute_name != null && attribute_value != null)
    		getElementHashEntry().put(attribute_name, attribute_value);
        return(this);
    }

    /** Add an attribute to the element. */
    public Element addAttribute(String attribute_name, Integer attribute_value)
    {
        getElementHashEntry().put(attribute_name, attribute_value);
        return(this);
    }

    /** remove an attribute from the element */
    public Element removeAttribute(String attribute_name)
    {
        try
        {
            getElementHashEntry().remove(attribute_name);
        }
        catch ( Exception e )
        {
        }
        return(this);
    }

    /** does the element have a particular attribute? */
    public boolean hasAttribute(String attribute)
    {
        return(getElementHashEntry().containsKey(attribute));
    }

    /** Return a list of the attributes associated with this element. */
    public Enumeration<String> attributes()
    {
        return getElementHashEntry().keys();
    }

    /**
     * Return the specified attribute.
     * @param attribute The name of the attribute to fetch
     */
    public String getAttribute(String attribute)
    {
        return (String)getElementHashEntry().get(attribute);
    }

    /**
        This method overrides createStartTag() in Generic Element.
        It provides a way to print out the attributes of an element.
    */
    protected String createStartTag()
    {
        StringBuffer out = new StringBuffer();

        out.append(getStartTagChar());

        if(getBeginStartModifierDefined())
        {
            out.append(getBeginStartModifier());
        }
        out.append(getElementType());

        Enumeration<String> en = getElementHashEntry().keys();
        String value = null; // avoid creating a new string object on each pass through the loop

        while (en.hasMoreElements())
        {
            String attr = en.nextElement();
            if(getAttributeFilterState())
            {
                value = getAttributeFilter().process(getElementHashEntry().get(attr).toString());
            }
            else
            {
                value = (String) getElementHashEntry().get(attr);
            }
            out.append(' ');
            out.append(alterCase(attr));
            if(!value.equalsIgnoreCase(NO_ATTRIBUTE_VALUE) && getAttributeQuote())
            {
                out.append(getAttributeEqualitySign());
                out.append(getAttributeQuoteChar());
                out.append(value);
                out.append(getAttributeQuoteChar());
            }
            else if( !getAttributeQuote() )
            {
                out.append(getAttributeEqualitySign());
                out.append(value);
            }
        }
        if(getBeginEndModifierDefined())
        {
            out.append(getBeginEndModifier());
        }
        out.append(getEndTagChar());

        return(out.toString());
    }
}
