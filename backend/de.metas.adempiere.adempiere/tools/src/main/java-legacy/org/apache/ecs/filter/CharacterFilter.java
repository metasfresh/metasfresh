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

import org.apache.ecs.Entities;
import org.apache.ecs.Filter;
/**
    This class creates a Filter object.  The default characters filtered are:<br>
    " ' & < >
    <p>
    For example:

    <pre><code>
    Filter filter = new CharacterFilter();
    filter.addAttribute("$","dollar");
    filter.addAttribute("#",Entities.POUND);

    P p = new P();
    p.setFilter(filter);

    Document doc = new Document();
    doc.getBody().addElement(p);
    </pre></code>

    The filter is applied when the addElement() method is called.

    @version $Id: CharacterFilter.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public class CharacterFilter extends java.util.Hashtable<String, Object> implements Filter
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1425231653286948494L;

	/**
        Private initializer. 
        " ' & < > are the default filters.
    */
    {
        addAttribute("\"",Entities.QUOT);
        addAttribute("'",Entities.LSQUO);
        addAttribute("&",Entities.AMP);
        addAttribute("<",Entities.LT);
        addAttribute(">",Entities.GT);
    }

    public CharacterFilter()
    {
		super(4);
    }

    /** Returns the name of the filter */
    public String getInfo()
    {
        return "CharacterFilter";
    }

    /**
        Register things to be filtered.
    */
    public Filter addAttribute(String name,Object attribute)
    {
        this.put(name,attribute);
        return this;
    }

    /**
        Remove things to be filtered.
    */
    public Filter removeAttribute(String name)
    {
        try
        {
            this.remove(name);
        }
        catch ( Exception e )
        {
        }
        return this;
    }

    /**
        Check to see if something is going to be filtered.
    */
    public boolean hasAttribute(String key)
    {
        return(this.containsKey(key));
    }

    /**
        Perform the filtering operation.
    */
    public String process(String to_process)
    {
        if ( to_process == null || to_process.length() == 0 )
            return "";

        StringBuffer bs = new StringBuffer(to_process.length() + 50);
        StringCharacterIterator sci = new StringCharacterIterator(to_process);
        String tmp = null;

        for (char c = sci.first(); c != CharacterIterator.DONE; c = sci.next())
        {
            tmp = String.valueOf(c);

            if (hasAttribute(tmp))
                tmp = (String) this.get(tmp);
			
			int ii = c;
			if (ii > 255)
				tmp = "&#" + ii + ";";

            bs.append(tmp);
        }
        return(bs.toString());
    }
}
