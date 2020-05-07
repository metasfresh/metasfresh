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
package org.apache.ecs.xhtml;



import org.apache.ecs.Element;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;



/**

    This class creates a &lt;style&gt; tag.



    @version $Id: style.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $

    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>

    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>

    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>

*/

public class style extends MultiPartElement implements Printable

{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3915246292057799666L;
	/** type=text/css */

    public static final String css = "text/css";

    

    /**

        Private initialization routine.

    */

    {

        setElementType("style");

        setCase(LOWERCASE);

        setAttributeQuote(true);

    }

    /**

        Basic constructor.

    */

    public style()

    {

    }



    /**

        Basic constructor.

        @param   type  the type="" attribute

    */

    public style(String type)

    {

        setType(type);

    }



    /**

        Basic constructor.

        @param   type  the type="" attribute

        @param  element Adds an Element to the element.

    */

    public style(String type, Element element)

    {

        setType(type);

        addElement(element);

    }



    /**

        Basic constructor.

        @param   type  the type="" attribute

        @param  element Adds an Element to the element.

    */

    public style(String type, String element)

    {

        setType(type);

        addElement(element);

    }



    /**

        Sets the type="" attribute

        @param   type  the type="" attribute

    */

    public style setType(String type)

    {

        addAttribute("type",type);

        return this;

    }



    /**

        Sets the media="" attribute

        @param   media  the media="" attribute

    */

    public style setMedia(String media)

    {

        addAttribute("media",media);

        return this;

    }



    /**

        Sets the lang="" and xml:lang="" attributes

        @param   lang  the lang="" and xml:lang="" attributes

    */

    public Element setLang(String lang)

    {

        addAttribute("lang",lang);

        addAttribute("xml:lang",lang);

        return this;

    }



    /**

        Adds an Element to the element.

        @param  hashcode name of element for hash table

        @param  element Adds an Element to the element.

     */

    public style addElement(String hashcode,Element element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  hashcode name of element for hash table

        @param  element Adds an Element to the element.

     */

    public style addElement(String hashcode,String element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public style addElement(Element element)

    {

        addElementToRegistry(element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public style addElement(String element)

    {

        addElementToRegistry(element);

        return(this);

    }

    /**

        Removes an Element from the element.

        @param hashcode the name of the element to be removed.

    */

    public style removeElement(String hashcode)

    {

        removeElementFromRegistry(hashcode);

        return(this);

    }

}

