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
import org.apache.ecs.FocusEvents;
import org.apache.ecs.KeyEvents;
import org.apache.ecs.MouseEvents;
import org.apache.ecs.MultiPartElement;
import org.apache.ecs.Printable;



/**

    This class creates a &lt;map&gt; tag.



    @version $Id: map.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $

    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>

    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>

    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>

*/

public class map extends MultiPartElement implements Printable, FocusEvents, MouseEvents, KeyEvents

{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5245261398203047570L;



	/**

        Private initialization routine.

    */

    {

        setElementType("map");

        setCase(LOWERCASE);

        setAttributeQuote(true);

    }

    /**

        Basic constructor.

    */

    public map()

    {

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

    */

    public map(Element element)

    {

        addElement(element);

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

    */

    public map(String element)

    {

        addElement(element);

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

        @param  name sets the name="" attribute

    */

    public map(Element element, String name)

    {

        addElement(element);

        setName ( name );

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

        @param  name sets the name="" attribute

    */

    public map(String element, String name)

    {

        addElement(element);

        setName ( name );

    }



    /**

        Sets the name="" attribute

        @param   name  the name="" attribute

    */

    public map setName(String name)

    {

        addAttribute("name",name);

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

    public map addElement(String hashcode,Element element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  hashcode name of element for hash table

        @param  element Adds an Element to the element.

     */

    public map addElement(String hashcode,String element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public map addElement(Element element)

    {

        addElementToRegistry(element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public map addElement(String element)

    {

        addElementToRegistry(element);

        return(this);

    }

    /**

        Removes an Element from the element.

        @param hashcode the name of the element to be removed.

    */

    public map removeElement(String hashcode)

    {

        removeElementFromRegistry(hashcode);

        return(this);

    }



    /**

        The onfocus event occurs when an element receives focus either by the

        pointing device or by tabbing navigation. This attribute may be used

        with the following elements: LABEL, INPUT, SELECT, TEXTAREA, and

        BUTTON.

        

        @param The script

    */

    public void setOnFocus(String script)

    {

        addAttribute ( "onfocus", script );

    }



    /**

        The onblur event occurs when an element loses focus either by the

        pointing device or by tabbing navigation. It may be used with the same

        elements as onfocus.

        

        @param The script

    */

    public void setOnBlur(String script)

    {

        addAttribute ( "onblur", script );

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

