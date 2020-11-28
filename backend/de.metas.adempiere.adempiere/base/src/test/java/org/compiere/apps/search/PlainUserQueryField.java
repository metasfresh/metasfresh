package org.compiere.apps.search;

import org.junit.Ignore;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Ignore
@Value
@Builder
final class PlainUserQueryField implements IUserQueryField
{
	public static PlainUserQueryField ofColumnName(final String columnName)
	{
		return builder()
				.columnName(columnName)
				.displayName(TranslatableStrings.constant(columnName))
				.build();
	}

	@NonNull
	String columnName;
	ITranslatableString displayName;

	@Override
	public String getColumnSQL()
	{
		return null;
	}

	@Override
	public Object convertValueToFieldType(final Object valueObj)
	{
		return valueObj;
	}

	@Override
	public String getValueDisplay(final Object valueObj)
	{
		return valueObj != null ? valueObj.toString() : null;
	}

}
