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
package org.apache.ecs.filter;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.apache.ecs.Filter;

/**
    Stupid implementation of Filter interface to demonstrate how easy  <br>
    it is to create your own filters. <b>This should NOT be used</b> in/for<br>
    anything real. Anyone want to implement a regex filter?

    @version $Id: StringFilter.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public class StringFilter extends java.util.Hashtable<String, Object> implements Filter
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8241976653327997713L;

	public StringFilter()
    {
		super(4);
    }

    /** Returns the name of the filter */
    public String getInfo()
    {
        return "StringFilter";
    }

    /**
        this method actually performs the filtering.
    */
    public String process(String to_process)
    {   System.out.println("\nString to Process in StringFilter = "+to_process);
        String[] value = split(to_process);
        StringBuffer new_value = new StringBuffer();
        for(int x = 0; x < value.length; x++)
        {
            if(hasAttribute(value[x]))
                new_value.append((String)get(value[x]));
            else
                new_value.append(value[x]);
            if(x != value.length - 1)
                new_value.append(" ");
        }
        return(new_value.toString());
    }

    /**
        Put a filter somewhere we can get to it.
    */
    public Filter addAttribute(String attribute,Object entity)
    {
        put(attribute,entity);
        return(this);
    }

    /**
        Get rid of a current filter.
    */
    public Filter removeAttribute(String attribute)
    {
        try
        {
            remove(attribute);
        }
        catch(NullPointerException exc)
        { // don't really care if this throws a null pointer exception
        }
        return(this);
    }

    /**
        Does the filter filter this?
    */
    public boolean hasAttribute(String attribute)
    {
        return(containsKey(attribute));
    }

    /**
        Need a way to parse the stream so we can do string comparisons instead
        of character comparisons.
    */
    private String[] split(String to_split)
    {

        if ( to_split == null || to_split.length() == 0 )
        {
            String[] array = new String[0];
            return array;
        }

        StringBuffer sb = new StringBuffer(to_split.length()+50);
        StringCharacterIterator sci = new StringCharacterIterator(to_split);
        int length = 0;

        for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next())
        {
            if(String.valueOf(c).equals(" "))
                length++;
            else if(sci.getEndIndex()-1 == sci.getIndex())
                length++;
        }

        String[] array = new String[length];
        length = 0;
        String tmp = new String();
        for (char c = sci.first(); c!= CharacterIterator.DONE; c = sci.next())
        {
            if(String.valueOf(c).equals(" "))
            {
                array[length] = tmp;
                tmp = new String();
                length++;
            }
            else if(sci.getEndIndex()-1 == sci.getIndex())
            {
                tmp = tmp+String.valueOf(sci.last());
                array[length] = tmp;
                tmp = new String();
                length++;
            }
            else
                tmp += String.valueOf(c);
        }
        return(array);
    }
}
