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

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * (String) Value Name Pair
 *
 * @author Jorg Janke
 * @version $Id: ValueNamePair.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
@Immutable
public final class ValueNamePair extends NamePair
{
	private static final long serialVersionUID = -8315081335749462163L;

	@JsonCreator
	public static final ValueNamePair of(@JsonProperty("v") final String value, @JsonProperty("n") final String name)
	{
		if (Objects.equals(value, EMPTY.getValue()) && Objects.equals(name, EMPTY.getName()))
		{
			return EMPTY;
		}
		return new ValueNamePair(value, name);
	}

	public static final ValueNamePair EMPTY = new ValueNamePair("", "");

	/**
	 * Construct KeyValue Pair
	 *
	 * @param value value
	 * @param name string representation
	 */
	public ValueNamePair(final String value, final String name)
	{
		super(name);
		m_value = value == null ? "" : value;
	}   // ValueNamePair

	/** The Value */
	private final String m_value;

	/**
	 * Get Value
	 *
	 * @return Value
	 */
	@JsonProperty("v")
	public String getValue()
	{
		return m_value;
	}	// getValue

	/**
	 * Get ID
	 *
	 * @return Value
	 */
	@Override
	@JsonIgnore
	public String getID()
	{
		return m_value;
	}	// getID

	@Override
	public boolean equals(final Object obj)
	{
		if(obj == this)
		{
			return true;
		}
		
		if (obj instanceof ValueNamePair)
		{
			final ValueNamePair other = (ValueNamePair)obj;
			return Objects.equals(this.m_value, other.m_value)
					&& Objects.equals(this.getName(), other.getName());
		}
		return false;
	}	// equals

	/**
	 * Return Hashcode of value
	 *
	 * @return hascode
	 */
	@Override
	public int hashCode()
	{
		return m_value.hashCode();
	}   // hashCode

}	// KeyValuePair

