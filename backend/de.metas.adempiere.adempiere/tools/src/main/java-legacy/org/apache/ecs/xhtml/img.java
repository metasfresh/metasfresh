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
import org.apache.ecs.Printable;
import org.apache.ecs.SinglePartElement;

/**
    This class creates an &lt;img&gt; tag.

    @version $Id: img.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>
*/
public class img extends SinglePartElement implements Printable, MouseEvents, KeyEvents
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8168015457971219546L;

	/**
	  Private initialization routine.
    */

    {
        setElementType("img");
        setCase(LOWERCASE);
        setAttributeQuote(true);
        setBeginEndModifier('/');
    }


    /**
        Basic constructor. Use the set* methods to set the attibutes.
    */
    public img()
    {
    }

    /**
        Creates an img tag
        @param   src  the src="" attribute
    */
	public img(String src)
    {
        setSrc(src);
    }

    /**
        Creates an img tag
        @param   src  the src="" attribute
        @param   border  the border="" attribute
    */
    public img(String src, int border)
    {
        setSrc(src);
        setBorder(border);
    }

    /**
        Creates an img tag
        @param   src  the src="" attribute
        @param   name  the name="" attribute
    */
    public img(String src, String name)
    {
        setSrc(src);
        setName(name);
    }

    /**
        Creates an img tag
        @param   src  the src="" attribute
        @param   name  the name="" attribute
        @param   border  the border="" attribute
    */

    public img(String src, String name, int border)
    {
        setSrc(src);
        setName(name);
        setBorder(border);
    }

    /**
        Sets the src="" attribute
        @param   src  the src="" attribute
    */
    public img setSrc(String src)
    {
        addAttribute("src",src);
        return this;
    }

    /**
        Sets the border="" attribute
        @param   border  the border="" attribute
    */
    public img setBorder(int border)
    {
        addAttribute("border",Integer.toString(border));
        return this;
    }

    /**
        Sets the name="" attribute
        @param   name  the name="" attribute
    */
    public img setName(String name)
    {
        addAttribute("name",name);
        return this;
    }

    /**
        Sets the height="" attribute
        @param   height  the height="" attribute
    */
    public img setHeight(String height)
    {
        addAttribute("height",height);
        return this;
    }

    /**
        Sets the height="" attribute
        @param   height  the height="" attribute
    */
    public img setHeight(int height)
    {
        addAttribute("height",Integer.toString(height));
        return this;
    }

    /**
        Sets the width="" attribute
        @param   width  the width="" attribute
    */
    public img setWidth(String width)
    {
        addAttribute("width",width);
        return this;
    }

    /**
        Sets the width="" attribute
        @param   width  the width="" attribute

    */
    public img setWidth(int width)
    {
        addAttribute("width",Integer.toString(width));
        return this;
    }

    /**
        Sets the alt="" attribute
        @param   alt  the alt="" attribute
    */
    public img setAlt(String alt)
    {
        addAttribute("alt",alt);
        return this;
    }

    /**
        Sets the ismap attribute
        @param   ismap  the ismap attribute
    */
    public img setIsMap(boolean ismap)
    {
        if (ismap == true)
            addAttribute("ismap", "ismap");
        else
            removeAttribute("ismap");
            
        return this;
    }

    /**
        Sets the usmap="" attribute
        @param   usemap  the usmap="" attribute
    */
    public img setUseMap(String usemap)
    {
        addAttribute("usemap",usemap);
        return this;
    }

    /**
        Sets the align="" attribute
        @param   align  the align="" attribute
    */
    public img setAlign(String align)
    {
        addAttribute("align",align);
        return this;
    }

    /**
        Sets the hspace="" attribute
        @param   hspace  the hspace="" attribute
    */
    public img setHspace(String hspace)
    {
        addAttribute("hspace",hspace);
        return this;
    }

    /**
        Sets the hspace="" attribute
        @param   hspace  the hspace="" attribute
    */
    public img setHspace(int hspace)
    {
        addAttribute("hspace",Integer.toString(hspace));
        return this;
    }

    /**
        Sets the vspace="" attribute
        @param   vspace  the vspace="" attribute
    */
    public img setVspace(String vspace)
    {
        addAttribute("vspace",vspace);
        return this;
    }

    /**
        Sets the vspace="" attribute
        @param   vspace  the vspace="" attribute
    */
    public img setVspace(int vspace)
    {
        addAttribute("vspace",Integer.toString(vspace));
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
    public img addElement(String hashcode,Element element)
    {
        addElementToRegistry(hashcode,element);
        return(this);
    }

    /**
        Adds an Element to the element.
        @param  hashcode name of element for hash table
        @param  element Adds an Element to the element.
     */
    public img addElement(String hashcode,String element)
    {
        addElementToRegistry(hashcode,element);
        return(this);
    }

    /**
        Adds an Element to the element.
        @param  element Adds an Element to the element.
     */
    public img addElement(Element element)
    {
        addElementToRegistry(element);
        return(this);
    }

    /**
        Adds an Element to the element.
        @param  element Adds an Element to the element.
     */
    public img addElement(String element)
    {
        addElementToRegistry(element);
        return(this);
    }

    /**
        Removes an Element from the element.
        @param hashcode the name of the element to be removed.
    */
    public img removeElement(String hashcode)
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

