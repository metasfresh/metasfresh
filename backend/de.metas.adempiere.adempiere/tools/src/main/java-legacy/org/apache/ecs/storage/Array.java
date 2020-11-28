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

import java.io.Serializable;

public class Array implements java.util.Enumeration, Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7392079695735049505L;
	private int current = 0;
    private int size = 10;
    private int grow = 2;
    private int place = 0;
    private Object[] elements = null;
    private Object[] tmpElements = null;

    public Array()
    {
        init();
    }

    public Array(int size)
    {
        setSize(size);
        init();
    }

    public Array(int size,int grow)
    {
        setSize(size);
        setGrow(grow);
        init();
    }

    private void init()
    {
        elements = new Object[size];
    }

    public Object nextElement() throws java.util.NoSuchElementException
    {
        if ( elements[place] != null && place != current)
        {
            place++;
            return elements[place - 1];
        }
        else
        {
            place = 0;
            throw new java.util.NoSuchElementException();
        }
    }

    public boolean hasMoreElements()
    {
        if( place < elements.length && current != place )
            return true;
        return false;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getCurrentSize()
    {
        return current;
    }

    public void rehash()
    {
        tmpElements = new Object[size];
        int count = 0;
        for ( int x = 0; x < elements.length; x++ )
        {
            if( elements[x] != null )
            {
                tmpElements[count] = elements[x];
                count++;
            }
        }
        elements = (Object[])tmpElements.clone();
        tmpElements = null;
        current = count;
    }

    public void setGrow(int grow)
    {
        this.grow = grow;
    }

    public void grow()
    {
        size = size+=(size/grow);
        rehash();
    }

    public void add(Object o)
    {
        if( current == elements.length )
            grow();

        try
        {
            elements[current] = o;
            current++;
        }
        catch(java.lang.ArrayStoreException ase)
        {
        }
    }

    public void add(int location,Object o)
    {
        try
        {
            elements[location] = o;
        }
        catch(java.lang.ArrayStoreException ase)
        {
        }
    }

    public void remove(int location)
    {
        elements[location] = null;
    }

    public int location(Object o) throws NoSuchObjectException
    {
        int loc = -1;
        for ( int x = 0; x < elements.length; x++ )
        {
            if((elements[x] != null && elements[x] == o )||
               (elements[x] != null && elements[x].equals(o)))
            {
                loc = x;
                break;
            }
        }
        if( loc == -1 )
            throw new NoSuchObjectException();
        return(loc);
    }

    public Object get(int location)
    {
        return elements[location];
    }

    public java.util.Enumeration elements()
    {
        return this;
    }
}
