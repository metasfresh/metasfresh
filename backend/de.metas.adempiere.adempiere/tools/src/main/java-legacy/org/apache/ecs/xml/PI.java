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
package org.apache.ecs.xml;

import org.apache.ecs.ConcreteElement;
import org.apache.ecs.Element;
import org.apache.ecs.Printable;

public class PI extends ConcreteElement implements Printable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4593598395653541273L;
	// Private initializer
    {
        setBeginStartModifier('?');
        setBeginEndModifier('?');
        setNeedClosingTag(false);
        setElementType("xml");
    }

    public PI()
    {
    }

    /**
        Set the version of this document.
        @param version the version of this document.
    */
    public PI setVersion(String version)
    {
        addAttribute("version",version);
        return(this);
    }

    /**
        Set the version of this document.
        @param version the version of this document.
    */
    public PI setVersion(float version)
    {
        setVersion(Float.toString(version));
        return(this);
    }

    /**
        Set the version of this document.
        @param version the version of this document.
    */
    public PI setVersion(double version)
    {
        setVersion(Double.toString(version));
        return(this);
    }

    /**
        Sets the target of this document.
        It is used to identigy the application
        to which the instruction is directed.
        @param target The target application.
    */
    public PI setTarget(String target)
    {
        setElementType(target);
        return(this);
    }
    
    /**
     * Add an additional instruction (which works like an XML
     *   attribute) to the PI
     *
     * @param name - Name of instruction (e.g. standalone)
     * @param value - value of instruction (e.g. "no")
     */
    public PI addInstruction(String name, String value) {
        addAttribute(name, value);
        return(this);
    }
    
    /**
        Adds an Element to the element.
        @param  hashcode name of element for hash table
        @param  element Adds an Element to the element.
     */
    public PI addElement(String hashcode,Element element)
    {
        addElementToRegistry(hashcode,element);
        return(this);
    }
    /**
        Adds an Element to the element.
        @param  hashcode name of element for hash table
        @param  element Adds an Element to the element.
     */
    public PI addElement(String hashcode,String element)
    {
        addElementToRegistry(hashcode,element);
        return(this);
    }
    /**
        Adds an Element to the element.
        @param  element Adds an Element to the element.
     */
    public PI addElement(Element element)
    {
        addElementToRegistry(element);
        return(this);
    }
    /**
        Adds an Element to the element.
        @param  element Adds an Element to the element.
     */
    public PI addElement(String element)
    {
        addElementToRegistry(element);
        return(this);
    }
    /**
        Removes an Element from the element.
        @param hashcode the name of the element to be removed.
    */
    public PI removeElement(String hashcode)
    {
        removeElementFromRegistry(hashcode);
        return(this);
    }
} 
