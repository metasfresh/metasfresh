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
import org.apache.ecs.Printable;
import org.apache.ecs.SinglePartElement;



/**

    This class creates an param Element



    @version $Id: param.java,v 1.2 2006/07/30 00:54:02 jjanke Exp $

    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>

    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>

    @author <a href="mailto:bojan@binarix.com">Bojan Smojver</a>

*/

public class param extends SinglePartElement implements Printable

{

    // Convience variables.



    /**
	 * 
	 */
	private static final long serialVersionUID = 8523078370350242925L;

	public final static String ref = "ref";

    public final static String data = "data";

    public final static String object = "object";



    /**

        private initializer.

    */

    {

        setElementType("param");

        setCase(LOWERCASE);

        setAttributeQuote(true);

        setBeginEndModifier('/');

    }



    /**

        Default constructor. use set* methods.

    */

    public param()

    {

    }



    /**

        Sets the name of this parameter.

        @param name sets the name of this parameter.

    */

    public param setName(String name)

    {

        addAttribute("name",name);

        return(this);

    }



    /**

        Sets the value of this attribute.

        @param value sets the value attribute.

    */

    public param setValue(String value)

    {

        addAttribute("value",value);

        return(this);

    }



    /**

        Sets the valuetype of this parameter.

        @param valuetype sets the name of this parameter.<br>

        ref|data|object convience varaibles provided as param.ref,param.data,param.object

    */

    public param setValueType(String valuetype)

    {

        addAttribute("value",valuetype);

        return(this);

    }



    /**

        Sets the mime type of this object

        @param the mime type of this object

    */

    public param setType(String cdata)

    {

        addAttribute("type",cdata);

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

    public param addElement(String hashcode,Element element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  hashcode name of element for hash table

        @param  element Adds an Element to the element.

     */

    public param addElement(String hashcode,String element)

    {

        addElementToRegistry(hashcode,element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public param addElement(Element element)

    {

        addElementToRegistry(element);

        return(this);

    }



    /**

        Adds an Element to the element.

        @param  element Adds an Element to the element.

     */

    public param addElement(String element)

    {

        addElementToRegistry(element);

        return(this);

    }

    /**

        Removes an Element from the element.

        @param hashcode the name of the element to be removed.

    */

    public param removeElement(String hashcode)

    {

        removeElementFromRegistry(hashcode);

        return(this);

    }

} 

