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

    This class creates a &lt;colgroup&gt; object.



    @version $Id: colgroup.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $

    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>

    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>

    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>

*/

public class colgroup extends MultiPartElement implements Printable

{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7610169537948275866L;

	/**

        private initializer.

    */

    {

        setElementType("colgroup");

        setCase(LOWERCASE);

        setAttributeQuote(true);

    }

    public colgroup()

    {

    }



    /**

        Sets the span="" attribute.

        @param span    sets the span="" attribute.

    */

    public colgroup setSpan(String span)

    {

        addAttribute("span",span);

        return(this);

    }



    /**

        Sets the span="" attribute.

        @param span    sets the span="" attribute.

    */

    public colgroup setSpan(int span)

    {

        addAttribute("span",Integer.toString(span));

        return(this);

    }



    /**

        Supplies user agents with a recommended cell width.  (Pixel Values)

        @param width    how many pixels to make cell

    */

    public colgroup setWidth(int width)

    {

        addAttribute("width",Integer.toString(width));

        return(this);

    }

    

    /**

        Supplies user agents with a recommended cell width.  (Pixel Values)

        @param width    how many pixels to make cell

    */

    public colgroup setWidth(String width)

    {

        addAttribute("width",width);

        return(this);

    }



    /**

        Sets the align="" attribute convience variables are provided in the AlignType interface

        @param  align   Sets the align="" attribute

    */

    public colgroup setAlign(String align)

    {

        addAttribute("align",align);

        return(this);

    }



    /**

        Sets the valign="" attribute convience variables are provided in the AlignType interface

        @param  valign   Sets the valign="" attribute

    */

    public colgroup setVAlign(String valign)

    {

        addAttribute("valign",valign);

        return(this);

    }



    /**

        Sets the char="" attribute.

        @param character    the character to use for alignment.

    */

    public colgroup setChar(String character)

    {

        addAttribute("char",character);

        return(this);

    }



    /**

        Sets the charoff="" attribute.

        @param char_off When present this attribute specifies the offset

        of the first occurrence of the alignment character on each line.

    */

    public colgroup setCharOff(int char_off)

    {

        addAttribute("charoff",Integer.toString(char_off));

        return(this);

    }



    /**

        Sets the charoff="" attribute.

        @param char_off When present this attribute specifies the offset

        of the first occurrence of the alignment character on each line.

    */

    public colgroup setCharOff(String char_off)

    {

        addAttribute("charoff",char_off);

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

    public colgroup addElement(String hashcode,Element element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  hashcode name of element for hash table

        @param  element Adds an Element to the element.

     */

    public colgroup addElement(String hashcode,String element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }

    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public colgroup addElement(Element element)

    {

        addElementToRegistry(element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public colgroup addElement(String element)

    {

        addElementToRegistry(element);

        return(this);

    }

    /**

        Removes an Element from the element.

        @param hashcode the name of the element to be removed.

    */

    public colgroup removeElement(String hashcode)

    {

        removeElementFromRegistry(hashcode);

        return(this);

    }

}

