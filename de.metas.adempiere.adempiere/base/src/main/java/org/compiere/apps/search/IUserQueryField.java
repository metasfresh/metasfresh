package org.compiere.apps.search;

import java.util.Objects;

import de.metas.i18n.ITranslatableString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IUserQueryField
{
	static IUserQueryField castToUserQueryField(final Object value)
	{
		return (IUserQueryField)value;
	}

	String getColumnName();

	/**
	 * @return display name; never null
	 */
	ITranslatableString getDisplayName();

	String getColumnSQL();

	default boolean matchesColumnName(String columnName)
	{
		return Objects.equals(getColumnName(), columnName);
	}

	Object convertValueToFieldType(Object valueObj);

	String getValueDisplay(Object value);
}
