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
package org.apache.ecs.storage;

import java.util.Enumeration;

public class Hash implements java.io.Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7635193444611108511L;
	private Array keys = new Array();
    private Array elements = new Array();

    public Hash()
    {
    }

    public void setSize(int newSize)
    {
        keys.setSize(newSize);
        elements.setSize(newSize);
    }

    public void setGrow(int growBy)
    {
        keys.setGrow(growBy);
        elements.setGrow(growBy);
    }

    public synchronized void put(String key,Object element)
    {
        try
        {
            if( containsKey(key) )
            {
                elements.add(keys.location(key),element);
            }
            else
            {
                keys.add( key );
                elements.add(element);
            }
        }
        catch(org.apache.ecs.storage.NoSuchObjectException nsoe)
        {
        }
    }

    public synchronized void remove(String key)
    {
        try
        {
            if(containsKey(key))
            {
                elements.remove(keys.location(key));
                elements.remove(elements.location(key));
            }
        }
        catch(org.apache.ecs.storage.NoSuchObjectException nsoe)
        {
        }
    }

    public int size()
    {
        return keys.getCurrentSize();
    }

    public boolean contains(Object element)
    {
        try
        {
            elements.location(element);
            return(true);
        }
        catch(org.apache.ecs.storage.NoSuchObjectException noSuchObject)
        {
            return false;
        }
    }

    public Enumeration keys()
    {
        return keys;
    }

    public boolean containsKey(String key)
    {
        try
        {
            keys.location(key);
        }
        catch(org.apache.ecs.storage.NoSuchObjectException noSuchObject)
        {
            return false;
        }
        return(true);
    }

    public Enumeration elements()
    {
        return elements;
    }

    public Object get(String key)
    {
        try
        {
            if( containsKey(key) )
                return(elements.get(keys.location(key)));
        }
        catch(org.apache.ecs.storage.NoSuchObjectException nsoe)
        {
        }
        return null;
    }
}
