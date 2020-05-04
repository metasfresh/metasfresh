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
import org.apache.ecs.xhtml.frameset;
import org.apache.ecs.xhtml.head;
import org.apache.ecs.xhtml.html;
import org.apache.ecs.xhtml.noframes;
import org.apache.ecs.xhtml.title;



/**

    This class creates a XhtmlFrameSetDocument container, for convience.



    @version $Id: XhtmlFrameSetDocument.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $

    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>

    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>

    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>

*/

public class XhtmlFrameSetDocument implements Serializable,Cloneable

{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2548492591611250782L;

	/** @serial html html */

    private html html; // this is the actual container for head and body

    /** @serial head head */

    private head head;

    /** @serial body body */

    private body body;

    /** @serial title title */

    private title title;

    /** @serial frameset frameset */

    private frameset frameset;

    /** @serial noframes frameset */

    private noframes noframes;



    /** @serial codeset codeset */

    private String codeset = null;

    

    {

        html = new html();

        head = new head();

        title = new title();

        frameset = new frameset();

        noframes = new noframes();

        body = new body();

        

        head.addElement(title);

        html.addElement(head);

        html.addElement(frameset);

        html.addElement(noframes);

        noframes.addElement(body);        

    }

    

    /**

        Basic constructor.

    */

    public XhtmlFrameSetDocument()

    {

    }



    /**

        Basic constructor. Sets the codeset for the page output.

    */

    public XhtmlFrameSetDocument(String codeset)

    {

        setCodeset(codeset);

    }



    /**

        Get the html element for this document container.

    */

    public html getHtml()

    {

        return(html);

    }

    

    /**

        Set the html element for this XhtmlFrameSetDocument container.

    */

    public XhtmlFrameSetDocument setHtml(html set_html)

    {

        this.html = set_html;

        return(this);

    }

    

    /**

        Get the head element for this XhtmlFrameSetDocument container.

    */

    public head getHead()

    {

        return(head);

    }



    /**

        Set the head element for this XhtmlFrameSetDocument container.

    */

    public XhtmlFrameSetDocument setHead(head set_head)

    {

        this.head = set_head;

        return(this);

    }



    /**

        Append to the head element for this XhtmlFrameSetDocument container.

        @param value adds to the value between the head tags

    */

    public XhtmlFrameSetDocument appendHead(Element value)

    {

        head.addElement(value);

        return(this);

    }



    /**

        Append to the head element for this XhtmlFrameSetDocument container.

        @param value adds to the value between the head tags

    */

    public XhtmlFrameSetDocument appendHead(String value)

    {

        head.addElement(value);

        return(this);

    }



    /**

        Get the frameset element for this XhtmlFrameSetDocument container.

    */

    public frameset getFrameSet()

    {

        return(frameset);

    }



    /**

        Set the frameset element for this XhtmlFrameSetDocument container.

    */

    public XhtmlFrameSetDocument setHead(frameset set_frameset)

    {

        this.frameset = set_frameset;

        return(this);

    }



    /**

        Append to the head element for this FrameSetDocument container.

        @param value adds to the value between the head tags

    */

    public XhtmlFrameSetDocument appendFrameSet(Element value)

    {

        frameset.addElement(value);

        return(this);

    }



    /**

        Append to the head element for this XhtmlFrameSetDocument container.

        @param value adds to the value between the head tags

    */

    public XhtmlFrameSetDocument appendFrameSet(String value)

    {

        frameset.addElement(value);

        return(this);

    }

    /**

        Get the body element for this XhtmlFrameSetDocument container.

    */

    public body getBody()

    {

        return(body);

    }



    /**

        Set the body element for this XhtmlFrameSetDocument container.

    */

    public XhtmlFrameSetDocument setBody(body set_body)

    {

        this.body = set_body;

        return(this);

    }

    

    /**

        Append to the body element for this XhtmlFrameSetDocument container.

        @param value adds to the value between the body tags

    */

    public XhtmlFrameSetDocument appendBody(Element value)

    {

        body.addElement(value);

        return(this);

    }



    /**

        Append to the body element for this XhtmlFrameSetDocument container.

        @param value adds to the value between the body tags

    */

    public XhtmlFrameSetDocument appendBody(String value)

    {

        body.addElement(value);

        return(this);

    }



    /**

        Get the title element for this XhtmlFrameSetDocument container.

    */

    public title getTitle()

    {

        return(title);

    }



    /**

        Set the title element for this XhtmlFrameSetDocument container.

    */

    public XhtmlFrameSetDocument setTitle(title set_title)

    {

        this.title = set_title;

        return(this);

    }

    

    /**

        Append to the title element for this XhtmlFrameSetDocument container.

        @param value adds to the value between the title tags

    */

    public XhtmlFrameSetDocument appendTitle(Element value)

    {

        title.addElement(value);

        return(this);

    }



    /**

        Append to the title element for this XhtmlFrameSetDocument container.

        @param value adds to the value between the title tags

    */

    public XhtmlFrameSetDocument appendTitle(String value)

    {

        title.addElement(value);

        return(this);

    }



    /**

     * Sets the codeset for this XhtmlFrameSetDocument

     */

    public void setCodeset ( String codeset )

    {

        this.codeset = codeset;

    }



    /**

     * Gets the codeset for this XhtmlFrameSetDocument

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

        // XhtmlFrameSetDocument is just a convient wrapper for html call html.output

        html.output(out);

    }



    /**

        Write the container to the PrinteWriter

    */

    public void output(PrintWriter out)

    {

        // XhtmlFrameSetDocument is just a convient wrapper for html call html.output

        html.output(out);

    }



    /**

        Override the toString() method so that it prints something meaningful.

    */

    public final String toString()

    {

        if ( getCodeset() != null )

            return (html.toString(getCodeset()));

        else

            return(html.toString());

    }



    /**

        Override the toString() method so that it prints something meaningful.

    */

    public final String toString(String codeset)

    {

        return(html.toString(codeset));

    }

    /**

        Allows the XhtmlFrameSetDocument to be cloned.  Doesn't return an instanceof XhtmlFrameSetDocument returns instance of html.



    */



    public Object clone()

    {

        return(html.clone());

    }

}

