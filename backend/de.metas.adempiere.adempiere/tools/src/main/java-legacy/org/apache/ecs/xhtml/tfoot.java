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
import org.apache.ecs.KeyEvents;
import org.apache.ecs.MouseEvents;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;



/**

    This class creates a &lt;tfoot&gt; object.

    @version $Id: tfoot.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $

    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>

    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>

    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>

*/

public class tfoot extends MultiPartElement implements Printable, MouseEvents, KeyEvents

{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1663886800759150608L;



	/**

        private initializer.

    */

    {

        setElementType("tfoot");

        setCase(LOWERCASE);

        setAttributeQuote(true);

    }

    public tfoot()

    {

    }



    /**

        Sets the span="" attribute.

        @param span    sets the span="" attribute.

    */

    public tfoot setSpan(String span)

    {

        addAttribute("span",span);

        return(this);

    }



    /**

        Sets the span="" attribute.

        @param span    sets the span="" attribute.

    */

    public tfoot setSpan(int span)

    {

        addAttribute("span",Integer.toString(span));

        return(this);

    }



    /**

        Supplies user agents with a recommended cell width.  (Pixel Values)

        @param width    how many pixels to make cell

    */

    public tfoot setWidth(int width)

    {

        addAttribute("width",Integer.toString(width));

        return(this);

    }

    

    /**

        Supplies user agents with a recommended cell width.  (Pixel Values)

        @param width    how many pixels to make cell

    */

    public tfoot setWidth(String width)

    {

        addAttribute("width",width);

        return(this);

    }



    /**

        Sets the align="" attribute convience variables are provided in the AlignType interface

        @param  align   Sets the align="" attribute

    */

    public tfoot setAlign(String align)

    {

        addAttribute("align",align);

        return(this);

    }



    /**

        Sets the valign="" attribute convience variables are provided in the AlignType interface

        @param  valign   Sets the valign="" attribute

    */

    public tfoot setVAlign(String valign)

    {

        addAttribute("valign",valign);

        return(this);

    }



    /**

        Sets the char="" attribute.

        @param character    the character to use for alignment.

    */

    public tfoot setChar(String character)

    {

        addAttribute("char",character);

        return(this);

    }



    /**

        Sets the charoff="" attribute.

        @param char_off When present this attribute specifies the offset

        of the first occurrence of the alignment character on each line.

    */

    public tfoot setCharOff(int char_off)

    {

        addAttribute("charoff",Integer.toString(char_off));

        return(this);

    }



    /**

        Sets the charoff="" attribute.

        @param char_off When present this attribute specifies the offset

        of the first occurrence of the alignment character on each line.

    */

    public tfoot setCharOff(String char_off)

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

    public tfoot addElement(String hashcode,Element element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  hashcode name of element for hash table

        @param  element Adds an Element to the element.

     */

    public tfoot addElement(String hashcode,String element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public tfoot addElement(Element element)

    {

        addElementToRegistry(element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public tfoot addElement(String element)

    {

        addElementToRegistry(element);

        return(this);

    }

    /**

        Removes an Element from the element.

        @param hashcode the name of the element to be removed.

    */

    public tfoot removeElement(String hashcode)

    {

        removeElementFromRegistry(hashcode);

        return(this);

    }



    /**

        The onclick event occurs when the pointing device button is clicked

        over an element. This attribute may be used with most elements.

        

        @param The script

    */

    public void setOnClick(String script)

    {

        addAttribute ( "onclick", script );

    }

    /**

        The ondblclick event occurs when the pointing device button is double

        clicked over an element. This attribute may be used with most elements.



        @param The script

    */

    public void setOnDblClick(String script)

    {

        addAttribute ( "ondblclick", script );

    }

    /**

        The onmousedown event occurs when the pointing device button is pressed

        over an element. This attribute may be used with most elements.



        @param The script

    */

    public void setOnMouseDown(String script)

    {

        addAttribute ( "onmousedown", script );

    }

    /**

        The onmouseup event occurs when the pointing device button is released

        over an element. This attribute may be used with most elements.



        @param The script

    */

    public void setOnMouseUp(String script)

    {

        addAttribute ( "onmouseup", script );

    }

    /**

        The onmouseover event occurs when the pointing device is moved onto an

        element. This attribute may be used with most elements.



        @param The script

    */

    public void setOnMouseOver(String script)

    {

        addAttribute ( "onmouseover", script );

    }

    /**

        The onmousemove event occurs when the pointing device is moved while it

        is over an element. This attribute may be used with most elements.



        @param The script

    */

    public void setOnMouseMove(String script)

    {

        addAttribute ( "onmousemove", script );

    }

    /**

        The onmouseout event occurs when the pointing device is moved away from

        an element. This attribute may be used with most elements.



        @param The script

    */

    public void setOnMouseOut(String script)

    {

        addAttribute ( "onmouseout", script );

    }



    /**

        The onkeypress event occurs when a key is pressed and released over an

        element. This attribute may be used with most elements.

        

        @param The script

    */

    public void setOnKeyPress(String script)

    {

        addAttribute ( "onkeypress", script );

    }



    /**

        The onkeydown event occurs when a key is pressed down over an element.

        This attribute may be used with most elements.

        

        @param The script

    */

    public void setOnKeyDown(String script)

    {

        addAttribute ( "onkeydown", script );

    }



    /**

        The onkeyup event occurs when a key is released over an element. This

        attribute may be used with most elements.

        

        @param The script

    */

    public void setOnKeyUp(String script)

    {

        addAttribute ( "onkeyup", script );

    }

}

