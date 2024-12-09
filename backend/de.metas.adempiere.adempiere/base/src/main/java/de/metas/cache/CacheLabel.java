package de.metas.cache;

import lombok.NonNull;
import lombok.Value;

<<<<<<< HEAD
=======
import javax.annotation.Nullable;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
<<<<<<< HEAD
public final class CacheLabel
{
	public static CacheLabel ofTableName(final String tableName)
	{
		return new CacheLabel(tableName);
	}

	String name;
=======
public class CacheLabel
{
	public static final String NO_TABLENAME_PREFIX = "$NoTableName$";

	@NonNull String name;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	private CacheLabel(@NonNull final String name)
	{
		this.name = name;
	}
<<<<<<< HEAD
=======

	public static CacheLabel ofTableName(@NonNull final String tableName)
	{
		return new CacheLabel(tableName);
	}

	public static CacheLabel ofString(@NonNull final String string)
	{
		return new CacheLabel(string);
	}

	@Override
	@Deprecated
	public String toString() {return getName();}

	public boolean equalsByName(@Nullable final String otherName)
	{
		return this.name.equals(otherName);
	}

	public boolean isApplicationDictionaryTableName()
	{
		return name.startsWith("AD_");
	}

	public boolean containsNoTableNameMarker()
	{
		return name.contains(NO_TABLENAME_PREFIX);
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
