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

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

/**
    This class is a Element container class. You can place elements into 
    this class and then you can place this class into other elements in order 
    to combine elements together.

<code><pre>
    P p = new P().addElement("foo");
    P p1 = new P().addElement("bar");
    ElementContainer ec = new ElementContainer(p).addElement(p1);
    System.out.println(ec.toString());
</pre></code>

    @version $Id: ElementContainer.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public class ElementContainer extends ConcreteElement implements Printable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5023697241526677706L;
	/** 
        internal use only
        @serial ec ec
    */
    private Vector<Element> ec = new Vector<Element>(2);
    
    /** 
        Basic constructor
    */
    public ElementContainer()
    {
    }

    /** 
        Basic constructor
    */
    public ElementContainer(Element element)
    {
        addElement(element);
    }

    /**
        Adds an Element to the element.
        @param  element Adds an Element to the element.
     */
    public ElementContainer addElement(Element element)
    {
        ec.addElement(element);
        return(this);
    }
    
    /**
        Adds an Element to the element.
        @param  element Adds an Element to the element.
     */
    public ElementContainer addElement(String element)
    {
        ec.addElement(new StringElement(element));
        return(this);
    }

    /**
        Implements the output method in Element
    */
    public void output(OutputStream out)
    {
        Element element = null;
        Enumeration<Element> data = ec.elements();
        while ( data.hasMoreElements() )
        {
            element = data.nextElement();
            element.output(out);
        }
    }
    
    /**
        Implements the output method in Element
    */
    public void output(PrintWriter out)
    {
        Element element = null;
        Enumeration<Element> data = ec.elements();
        while ( data.hasMoreElements() )
        {
            element = data.nextElement();
            element.output(out);
        }
    }
    /**
        returns an enumeration of the elements in this container
    */
    public Enumeration<Element> elements()
    {
        return ec.elements();
    }
}
