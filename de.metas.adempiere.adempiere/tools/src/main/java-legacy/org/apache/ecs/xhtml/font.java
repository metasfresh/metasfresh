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
import org.apache.ecs.HtmlColor;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;



/**

    This class creates a &lt;font&gt; object.

    @version $Id: font.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $

    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>

    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>

    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>

*/

public class font extends MultiPartElement implements Printable

{



    /**
	 * 
	 */
	private static final long serialVersionUID = 8438524129542982400L;

	/**

        Private initializer.

    */

    {

        setElementType("font");

        setCase(LOWERCASE);

        setAttributeQuote(true);

    }



    /**

        Basic Constructor. use set* methods.

    */

    public font()

    {

    }



    /**

        Basic constructor.

        @param face create new font object with this face.

    */

    public font(String face)

    {

        setFace(face);

    }



    /**

        Basic constructor

        @param  face

        @param  color

        Create a new font object with the face abd color already set. Convience colors are defined in HtmlColor interface.

    */

    public font(String face,String color)

    {

        setFace(face);

        setColor(color);

    }



    /**

        Basic constructor

        @param  face

        @param  color

        @param  size

        Create a new font object with the face,color and size already set. Convience colors are defined in HtmlColor interface.

    */

    public font(String face,String color,int size)

    {

        setFace(face);

        setColor(color);

        setSize(size);

    }



    /**

        Basic constructor

        @param  size

        Create a new font object with the size already set.

    */

    public font(int size)

    {

        setSize(size);

    }



    /**

        Basic constructor

        @param  size

        @param  face

        Create a new font object with the size and face already set.

    */

    public font(int size,String face)

    {

        setSize(size);

        setFace(face);

    }



    /**

        Basic constructor

        @param  color

        @param  size

        Create a new font object with the size and color already set.

    */

    public font(String color,int size)

    {

        setSize(size);

        setColor(color);

    }

    

    /**

        sets the face="" attribute.

        @param  face sets the face="" attribute.

    */

    public font setFace(String face)

    {

        addAttribute("face",face);

        return(this);

    }



    /**

        sets the color="" attribute.

        @param  color sets the color="" attribute. Convience colors are defined in the HtmlColors interface.

    */

    public font setColor(String color)

    {

        addAttribute("color",HtmlColor.convertColor(color));

        return(this);

    }

    

    /**

        sets the size="" attribute.

        @param    size sets the size="" attribute.

    */

    public font setSize(int size)

    {

        addAttribute("size",Integer.toString(size));

        return(this);

    }



    /**

        sets the size="" attribute.

        @param    size sets the size="" attribute.

    */

    public font setSize(String size)

    {

        addAttribute("size",size);

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

    public font addElement(String hashcode,Element element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  hashcode name of element for hash table

        @param  element Adds an Element to the element.

     */

    public font addElement(String hashcode,String element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the Element.

        @param     element adds and Element to the Element.

    */

    public font addElement(Element element)

    {

        addElementToRegistry(element);

        return(this);

    }



    /**

        Adds an Element to the Element.

        @param     element adds and Element to the Element.

    */

    public font addElement(String element)

    {

        addElementToRegistry(element);

        return(this);

    }

    /**

        Removes an Element from the element.

        @param hashcode the name of the element to be removed.

    */

    public font removeElement(String hashcode)

    {

        removeElementFromRegistry(hashcode);

        return(this);

    }

} 

