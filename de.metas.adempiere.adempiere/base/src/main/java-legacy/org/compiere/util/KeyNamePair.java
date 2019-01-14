/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.util;

import java.util.Objects;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.util.lang.RepoIdAware;

/**
 * (Numeric) Key Name Pair
 *
 * @author Jorg Janke
 * @version $Id: KeyNamePair.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
@Immutable
public final class KeyNamePair extends NamePair
{

	public static final KeyNamePair of(
			@Nullable final RepoIdAware key,
			@Nullable final String name)
	{
		final int keyInt = key != null ? key.getRepoId() : EMPTY.getKey();
		return of(keyInt, name, null/* description */);
	}

	public static final KeyNamePair of(
			@Nullable final int key,
			@Nullable final String name)
	{
		return of(key, name, null/* description */);
	}

	public static final KeyNamePair of(
			@Nullable final RepoIdAware key,
			@Nullable final String name,
			@Nullable final String description)
	{
		final int keyInt = key != null ? key.getRepoId() : EMPTY.getKey();
		return of(keyInt, name, description);
	}

	@JsonCreator
	public static final KeyNamePair of(
			@JsonProperty("k") final int key,
			@JsonProperty("n") final String name,
			@JsonProperty("description") final String description)
	{
		if (key == EMPTY.getKey() && Objects.equals(name, EMPTY.getName()))
		{
			return EMPTY;
		}
		return new KeyNamePair(key, name, description);
	}

	public static final KeyNamePair of(final int key)
	{
		if (key < 0)
		{
			return EMPTY;
		}
		return new KeyNamePair(key, "<" + key + ">", null/* help */);
	}

	private static final long serialVersionUID = 6347385376010388473L;

	public static final KeyNamePair EMPTY = new KeyNamePair(-1, "", null/* description */);

	/**
	 * @deprecated please use the static creator methods instead.
	 */
	@Deprecated
	public KeyNamePair(final int key, final String name)
	{
		super(name, null/* description */);
		this.m_key = key;
	}

	/**
	 * Constructor KeyValue Pair -
	 *
	 * @param key Key (-1 is considered as null)
	 * @param name string representation
	 * @deprecated please use the static creator methods instead.
	 */
	@Deprecated
	public KeyNamePair(final int key, @Nullable final String name, @Nullable final String description)
	{
		super(name, description);
		this.m_key = key;
	}

	/** The Key */
	private final int m_key;

	/**
	 * Get Key
	 *
	 * @return key
	 */
	@JsonProperty("k")
	public int getKey()
	{
		return m_key;
	}	// getKey

	/**
	 * Get ID (key as String)
	 *
	 * @return String value of key or null if -1
	 */
	@Override
	@JsonIgnore
	public String getID()
	{
		if (m_key == -1)
			return null;
		return String.valueOf(m_key);
	}	// getID

	/**
	 * Equals
	 *
	 * @param obj object
	 * @return true if equal
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj instanceof KeyNamePair)
		{
			KeyNamePair pp = (KeyNamePair)obj;
			if (pp.getKey() == m_key
					&& pp.getName() != null
					&& pp.getName().equals(getName()))
				return true;
			return false;
		}
		return false;
	}	// equals

	@Override
	public int hashCode()
	{
		return Objects.hash(m_key);
	}
}
