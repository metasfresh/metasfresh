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
package org.compiere.util;

import java.io.Serializable;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  Name Pair Interface
 *
 *  @author     Jorg Janke
 *  @version    $Id: NamePair.java,v 1.3 2006/07/30 00:52:23 jjanke Exp $
 */
public abstract class NamePair implements Comparator<Object>, Serializable, Comparable<Object>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8951176533385087242L;

	/**
	 *  Protected Constructor
	 *  @param   name    (Display) Name of the Pair
	 */
	protected NamePair(final String name)
	{
		super();
		if (name == null)
		{
			m_name = "";
		}
		else
		{
			m_name = name;
		}
	}   //  NamePair

	/** The Name        */
	private final String m_name;

	/**
	 *  Returns display value
	 *  @return name
	 */
	@JsonProperty("n")
	public final String getName()
	{
		return m_name;
	}   //  getName

	/**
	 *  Returns Key or Value as String
	 *  @return String or null
	 */
	public abstract String getID();

	/**
	 *	Comparator Interface (based on toString value)
	 *  @param o1 Object 1
	 *  @param o2 Object 2
	 *  @return compareTo value
	 */
	@Override
	public final int compare(Object o1, Object o2)
	{
		String s1 = o1 == null ? "" : o1.toString();
		String s2 = o2 == null ? "" : o2.toString();
		return s1.compareTo (s2);    //  sort order ??
	}	//	compare

	/**
	 *	Comparator Interface (based on toString value)
	 *  @param o1 Object 1
	 *  @param o2 Object 2
	 *  @return compareTo value
	 */
	public final int compare(NamePair o1, NamePair o2)
	{
		String s1 = o1 == null ? "" : o1.toString();
		String s2 = o2 == null ? "" : o2.toString();
		return s1.compareTo (s2);    //  sort order ??
	}	//	compare

	/**
	 * 	Comparable Interface (based on toString value)
	 *  @param   o the Object to be compared.
	 *  @return  a negative integer, zero, or a positive integer as this object
	 *		is less than, equal to, or greater than the specified object.
	 */
	@Override
	public final int compareTo(Object o)
	{
		return compare (this, o);
	}	//	compareTo

	/**
	 * 	Comparable Interface (based on toString value)
	 *  @param   o the Object to be compared.
	 *  @return  a negative integer, zero, or a positive integer as this object
	 *		is less than, equal to, or greater than the specified object.
	 */
	public final int compareTo(NamePair o)
	{
		return compare (this, o);
	}	//	compareTo

	/**
	 *	To String - returns name
	 *  @return Name
	 */
	@Override
	public String toString()
	{
		return m_name;
	}	//	toString

	/**
	 *	To String - detail
	 *  @return String in format ID=Name
	 */
	public final String toStringX()
	{
		StringBuilder sb = new StringBuilder(getID());
		sb.append("=").append(m_name);
		return sb.toString();
	}	//	toStringX

}	//	NamePair
