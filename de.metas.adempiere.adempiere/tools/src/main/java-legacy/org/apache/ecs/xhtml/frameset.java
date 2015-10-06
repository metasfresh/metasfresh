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
import org.apache.ecs.PageEvents;
import org.apache.ecs.Printable;



/**

    This class creates a &lt;frameset&gt; tag.



    @version $Id: frameset.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $

    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>

    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>

    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>

*/

public class frameset extends MultiPartElement implements Printable, PageEvents

{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5103453601086001403L;



	/**

        Private initialization routine.

    */

    {

        setElementType("frameset");

        setCase(LOWERCASE);

        setAttributeQuote(true);

    }

    

    /**

        Basic constructor.

    */

    public frameset()

    {

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

    */

    public frameset(Element element)

    {

        addElement(element);

    }



    /**

        Basic constructor.

        @param  element Adds an Element to the element.

    */

    public frameset(String element)

    {

        addElement(element);

    }



    /**

        Basic constructor.

        @param  rows   Sets the rows="" attribute

        @param  cols   Sets the cols="" attribute

    */

    public frameset(String rows, String cols)

    {

        setRows(rows);

        setCols(cols);

    }



    /**

        Basic constructor.

        @param  rows   Sets the rows="" attribute

        @param  cols   Sets the cols="" attribute

        @param  element Adds an Element to the element.

    */

    public frameset(String rows, String cols, Element element)

    {

        addElement(element);

        setRows(rows);

        setCols(cols);

    }



    /**

        Basic constructor.

        @param  rows   Sets the rows="" attribute

        @param  cols   Sets the cols="" attribute

        @param  element Adds an Element to the element.

    */

    public frameset(String rows, String cols, String element)

    {

        addElement(element);

        setRows(rows);

        setCols(cols);

    }



    /**

        Sets the rows="" attribute

        @param  rows   Sets the rows="" attribute

    */

    public frameset setRows(int rows)

    {

        setRows(Integer.toString(rows));

        return(this);

    }



    /**

        Sets the rows="" attribute

        @param  rows   Sets the rows="" attribute

    */

    public frameset setRows(String rows)

    {

        addAttribute("rows",rows);

        return(this);

    }



    /**

        Sets the cols="" attribute

        @param  cols   Sets the cols="" attribute

    */

    public frameset setCols(int cols)

    {

        setCols(Integer.toString(cols));

        return(this);

    }



    /**

        Sets the cols="" attribute

        @param  cols   Sets the cols="" attribute

    */

    public frameset setCols(String cols)

    {

        addAttribute("cols",cols);

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

    public frameset addElement(String hashcode,Element element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  hashcode name of element for hash table

        @param  element Adds an Element to the element.

     */

    public frameset addElement(String hashcode,String element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }

    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public frameset addElement(Element element)

    {

        addElementToRegistry(element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public frameset addElement(String element)

    {

        addElementToRegistry(element);

        return(this);

    }

    /**

        Removes an Element from the element.

        @param hashcode the name of the element to be removed.

    */

    public frameset removeElement(String hashcode)

    {

        removeElementFromRegistry(hashcode);

        return(this);

    }



    /**

        The onload event occurs when the user agent finishes loading a window

        or all frames within a frameset. This attribute may be used with body

        and frameset elements.

        

        @param The script

    */

    public void setOnLoad(String script)

    {

        addAttribute ( "onload", script );

    }



    /**

        The onunload event occurs when the user agent removes a document from a

        window or frame. This attribute may be used with body and frameset

        elements.

        

        @param The script

    */

    public void setOnUnload(String script)

    {

        addAttribute ( "onunload", script );

    }

}

