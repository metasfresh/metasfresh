package de.metas.ui.web.window.descriptor.sql;

import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@Value
public class PlainSqlEntityFieldBinding implements SqlEntityFieldBinding
{
	public static PlainSqlEntityFieldBinding mandatoryIntField(final String columnName)
	{
		return builder()
				.columnName(columnName)
				.widgetType(DocumentFieldWidgetType.Integer)
				.mandatory(true)
				.build();
	}

	String columnName;
	SqlSelectValue sqlSelectValue;
	DocumentFieldWidgetType widgetType;
	Class<?> sqlValueClass;
	SqlOrderByValue sqlOrderBy;
	boolean virtualColumn;
	boolean mandatory;

	@Builder
	private PlainSqlEntityFieldBinding(
			@NonNull final String columnName,
			final SqlSelectValue sqlSelectValue,
			@NonNull final DocumentFieldWidgetType widgetType,
			final Class<?> sqlValueClass,
			final SqlOrderByValue sqlOrderBy,
			final boolean virtualColumn,
			final boolean mandatory)
	{
		this.columnName = columnName;
		this.sqlSelectValue = sqlSelectValue;
		this.widgetType = widgetType;
		this.sqlValueClass = sqlValueClass != null ? sqlValueClass : widgetType.getValueClass();
		this.sqlOrderBy = sqlOrderBy;
		this.virtualColumn = virtualColumn;
		this.mandatory = mandatory;
	}

	@Override
	public boolean isMandatory()
	{
		return mandatory;
	}
}
