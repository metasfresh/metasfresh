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
import java.io.Serializable;

import org.apache.ecs.xhtml.body;
import org.apache.ecs.xhtml.head;
import org.apache.ecs.xhtml.html;
import org.apache.ecs.xhtml.title;



/**

    This class creates an XhtmlDocument container, for convience.



    @version $Id: XhtmlDocument.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $

    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>

    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>

    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>

*/

public class XhtmlDocument implements Serializable,Cloneable

{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8143619141939879559L;

	/** @serial html html */

    private html html; // this is the actual container for head and body

    /** @serial head head */

    private head head;

    /** @serial body body */

    private body body;

    /** @serial title title */

    private title title;

    /** @serial codeset codeset */

    private String codeset = null;

    /** @serial doctype doctype */

    private Doctype doctype = null;

    

    {

        html = new html();

        head = new head();

        title = new title();

        body = new body();

        

        head.addElement("title",title);

        html.addElement("head",head);

        html.addElement("body",body);

    }

    

    /**

        Basic constructor.

    */

    public XhtmlDocument()

    {

    }



    /**

        Basic constructor. Sets the codeset for the page output.

    */

    public XhtmlDocument(String codeset)

    {

        setCodeset(codeset);

    }



    /**

        Get the doctype element for this XhtmlDocument container.

    */

    public Doctype getDoctype()

    {

        return(doctype);

    }

    

    /**

        Set the doctype element for this XhtmlDocument container.

    */

    public XhtmlDocument setDoctype(Doctype set_doctype)

    {

        this.doctype = set_doctype;

        return(this);

    }



    /**

        Get the html element for this XhtmlDocument container.

    */

    public html getHtml()

    {

        return(html);

    }

    

    /**

        Set the html element for this XhtmlDocument container.

    */

    public XhtmlDocument setHtml(html set_html)

    {

        this.html = set_html;

        return(this);

    }

    

    /**

        Get the head element for this XhtmlDocument container.

    */

    public head getHead()

    {

        return(head);

    }

    

    /**

        Set the head element for this XhtmlDocument container.

    */

    public XhtmlDocument setHead(head set_head)

    {

        html.addElement("head",set_head);

        return(this);

    }

    

    /**

        Append to the head element for this XhtmlDocument container.

        @param value adds to the value between the head tags

    */

    public XhtmlDocument appendHead(Element value)

    {

        head.addElement(value);

        return(this);

    }



    /**

        Append to the head element for this XhtmlDocument container.

        @param value adds to the value between the head tags

    */

    public XhtmlDocument appendHead(String value)

    {

        head.addElement(value);

        return(this);

    }



    /**

        Get the body element for this XhtmlDocument container.

    */

    public body getBody()

    {

        return(body);

    }



    /**

        Set the body element for this XhtmlDocument container.

    */

    public XhtmlDocument setBody(body set_body)

    {

        html.addElement("body",set_body);

        return(this);

    }

    

    /**

        Append to the body element for this XhtmlDocument container.

        @param value adds to the value between the body tags

    */

    public XhtmlDocument appendBody(Element value)

    {

        body.addElement(value);

        return(this);

    }



    /**

        Append to the body element for this XhtmlDocument container.

        @param value adds to the value between the body tags

    */

    public XhtmlDocument appendBody(String value)

    {

        body.addElement(value);

        return(this);

    }



    /**

        Get the title element for this XhtmlDocument container.

    */

    public title getTitle()

    {

        return(title);

    }



    /**

        Set the title element for this XhtmlDocument container.

    */

    public XhtmlDocument setTitle(title set_title)

    {

        head.addElement("title",set_title);

        return(this);

    }

    

    /**

        Append to the title element for this XhtmlDocument container.

        @param value adds to the value between the title tags

    */

    public XhtmlDocument appendTitle(Element value)

    {

        title.addElement(value);

        return(this);

    }



    /**

        Append to the title element for this XhtmlDocument container.

        @param value adds to the value between the title tags

    */

    public XhtmlDocument appendTitle(String value)

    {

        title.addElement(value);

        return(this);

    }



    /**

     * Sets the codeset for this XhtmlDocument

     */

    public void setCodeset ( String codeset )

    {

        this.codeset = codeset;

    }



    /**

     * Gets the codeset for this XhtmlDocument

     *

     * @return    the codeset 

     */

    public String getCodeset()

    {

        return this.codeset;

    }

    

    /**

        Write the container to the OutputStream

    */

    public void output(OutputStream out)

    {

        if (doctype != null)

        {

            doctype.output(out);

            try

            {

                out.write('\n');

            }

            catch ( Exception e)

            {}

        }

        // XhtmlDocument is just a convient wrapper for html call html.output

        html.output(out);

    }



    /**

        Write the container to the PrinteWriter

    */

    public void output(PrintWriter out)

    {

        if (doctype != null)

        {

            doctype.output(out);

            try

            {

                out.write('\n');

            }

            catch ( Exception e)

            {}

        }

        // XhtmlDocument is just a convient wrapper for html call html.output

        html.output(out);

    }



    /**

        Override the toString() method so that it prints something meaningful.

    */

    public final String toString()

    {

        StringBuffer sb = new StringBuffer();

        if ( getCodeset() != null )

        {

            if (doctype != null)

                sb.append (doctype.toString(getCodeset()));

            sb.append (html.toString(getCodeset()));

            return (sb.toString());

        }

        else

        {

            if (doctype != null)

                sb.append (doctype.toString());

            sb.append (html.toString());

            return(sb.toString());

        }

    }



    /**

        Override the toString() method so that it prints something meaningful.

    */

    public final String toString(String codeset)

    {

        StringBuffer sb = new StringBuffer();

        if (doctype != null)

            sb.append (doctype.toString(getCodeset()));

        sb.append (html.toString(getCodeset()));

        return(sb.toString());

    }

    

    /**

        Allows the document to be cloned.

        Doesn't return an instance of document returns instance of html.

        NOTE: If you have a doctype set, then it will be lost. Feel free

        to submit a patch to fix this. It isn't trivial.

    */

    public Object clone()

    {

        return(html.clone());

    }

}

