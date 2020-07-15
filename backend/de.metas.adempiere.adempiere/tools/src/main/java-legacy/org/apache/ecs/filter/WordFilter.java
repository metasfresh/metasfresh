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

import java.util.StringTokenizer;

import org.apache.ecs.Filter;

/**
    This filter uses StringTokenizer to create "words" and allow
    you to replace on those "words". A word is defined as anything
    between two spaces. This filter should be relatively fast and
    shows how easy it is to implement your own filters.

<pre><code>
    Filter filter = new WordFilter();
    filter.addAttribute("there","where");
    filter.addAttribute("it","is");
    filter.addAttribute("goes","it");
    P p = new P();
    p.setFilter(filter);
    p.addElement("there it goes");
    System.out.println(p.toString());
</code></pre>

    Produces: &lt;p&gt;where is it

    @version $Id: WordFilter.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public class WordFilter extends java.util.Hashtable<String, Object> implements Filter
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7530768970669548477L;

	public WordFilter()
    {
		super(4);
    }

    /** Returns the name of the filter */
    public String getInfo()
    {
        return "WordFilter";
    }

    /**
        this method actually performs the filtering.
    */
    public String process(String to_process)
    {
        if ( to_process == null || to_process.length() == 0 )
            return "";

        String tmp = "";
        // the true at the end is the key to making it work
        StringTokenizer st = new StringTokenizer(to_process, " ", true);
        StringBuffer newValue = new StringBuffer(to_process.length() + 50);
        while ( st.hasMoreTokens() )
        {
            tmp = st.nextToken();
            if (hasAttribute(tmp))
                newValue.append((String)get(tmp));
            else
                newValue.append(tmp);
        }
        return newValue.toString();
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
}
