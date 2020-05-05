package de.metas.ui.web.document.filter.provider.userQuery;

import java.util.Optional;

import org.compiere.apps.search.IUserQueryField;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
@ToString(of = "columnName")
final class UserQueryField implements IUserQueryField
{
	public static final UserQueryField cast(final IUserQueryField userQueryField)
	{
		return (UserQueryField)userQueryField;
	}

	private final String columnName;
	private final ITranslatableString displayName;
	private final DocumentFieldWidgetType widgetType;
	private final Optional<LookupDescriptor> lookupDescriptor;

	@Override
	public String getColumnSQL()
	{
		return null; // shall not be needed
	}

	@Override
	public String getValueDisplay(final Object value)
	{
		return null; // not needed
	}

	@Override
	public Object convertValueToFieldType(final Object valueObj)
	{
		return valueObj;
	}
}
