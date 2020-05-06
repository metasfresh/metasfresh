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
    This class creates an interface for all filters.
    <P>
    For example:
    <P>
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
    
    @version $Id: Filter.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public interface Filter
{
    public Filter addAttribute(String name,Object attribute);
    public Filter removeAttribute(String name);
    public boolean hasAttribute(String key);
    public String process(String to_process);
    public String getInfo();
}
