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

    This class creates a &lt;frame&gt; tag.



    @version $Id: frame.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $

    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>

    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>

    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>

*/

public class frame extends MultiPartElement implements Printable

{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5304662633349757578L;

	public final static String yes = "yes";

    public final static String no = "no";

    public final static String auto = "auto";

    

    /**

        Private initialization routine.

    */

    {

        setElementType("frame");

        setCase(LOWERCASE);

        setAttributeQuote(true);

    }

    /**

        Basic constructor.

    */

    public frame()

    {

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

    */

    public frame(Element element)

    {

        addElement(element);

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

    */

    public frame(String element)

    {

        addElement(element);

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

        @param   name  the name="" attribute

    */

    public frame(Element element, String name)

    {

        addElement(element);

        setName(name);

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

        @param   name  the name="" attribute

    */

    public frame(String element, String name)

    {

        addElement(element);

        setName(name);

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

        @param   name  the nama="" attribute

        @param   src  the src="" attribute

    */

    public frame(Element element, String name, String src)

    {

        addElement(element);

        setName(name);

        setSrc(src);

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

        @param   name  the name="" attribute

        @param   src  the src="" attribute

    */

    public frame(String element, String name, String src)

    {

        addElement(element);

        setName(name);

        setSrc(src);

    }



    /**

        Sets the longdesc="" attribute

        @param   longdesc  the longdesc="" attribute

    */

    public frame setLongDesc(String longdesc)

    {

        addAttribute("longdesc",longdesc);

        return this;

    }



    /**

        Sets the name="" attribute

        @param   name  the name="" attribute

    */

    public frame setName(String name)

    {

        addAttribute("name",name);

        return this;

    }



    /**

        Sets the src="" attribute

        @param   src  the src="" attribute

    */

    public frame setSrc(String src)

    {

        addAttribute("src",src);

        return this;

    }



    /**

        Sets the frameborder="" attribute

        @param   frameborder  the frameborder="" attribute

    */

    public frame setFrameBorder(boolean frameborder)

    {

        if (frameborder)

            addAttribute("frameborder",Integer.toString(1));

        else

            addAttribute("frameborder",Integer.toString(0));            

        return this;

    }



    /**

        Sets the marginwidth="" attribute

        @param   marginwidth  the marginwidth="" attribute

    */

    public frame setMarginWidth(int marginwidth)

    {

        setMarginWidth(Integer.toString(marginwidth));

        return this;

    }



    /**

        Sets the marginwidth="" attribute

        @param   marginwidth  the marginwidth="" attribute

    */

    public frame setMarginWidth(String marginwidth)

    {

        addAttribute("marginwidth",marginwidth);

        return this;

    }



    /**

        Sets the marginheight="" attribute

        @param   marginheight  the marginheight="" attribute

    */

    public frame setMarginHeight(int marginheight)

    {

        setMarginHeight(Integer.toString(marginheight));

        return this;

    }



    /**

        Sets the marginheight="" attribute

        @param   marginheight  the marginheight="" attribute

    */

    public frame setMarginHeight(String marginheight)

    {

        addAttribute("marginheight",marginheight);

        return this;

    }



    /**

        Sets the scrolling="" attribute

        @param   scrolling  the scrolling="" attribute

    */

    public frame setScrolling(String scrolling)

    {

        addAttribute("scrolling",scrolling);

        return this;

    }



    /**

        Sets the noresize value

        @param   noresize  true or false

    */

    public frame setNoResize(boolean noresize)

    {

        if ( noresize == true )

            addAttribute("noresize", "noresize");

        else

            removeAttribute("noresize");

            

        return(this);

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

    public frame addElement(String hashcode,Element element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  hashcode name of element for hash table

        @param  element Adds an Element to the element.

     */

    public frame addElement(String hashcode,String element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public frame addElement(Element element)

    {

        addElementToRegistry(element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public frame addElement(String element)

    {

        addElementToRegistry(element);

        return(this);

    }



    /**

        Removes an Element from the element.

        @param hashcode the name of the element to be removed.

    */

    public frame removeElement(String hashcode)

    {

        removeElementFromRegistry(hashcode);

        return(this);

    }

}

