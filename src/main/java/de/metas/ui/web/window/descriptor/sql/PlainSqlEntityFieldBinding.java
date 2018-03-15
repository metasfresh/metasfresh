package de.metas.ui.web.window.descriptor.sql;

import org.adempiere.ad.expression.api.IStringExpression;

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
	public static final PlainSqlEntityFieldBinding intField(final String columnName)
	{
		return builder()
				.columnName(columnName)
				.widgetType(DocumentFieldWidgetType.Integer)
				.build();
	}

	String columnName;
	String columnSql;
	DocumentFieldWidgetType widgetType;
	Class<?> sqlValueClass;
	IStringExpression sqlOrderBy;
	boolean virtualColumn;

	@Builder
	private PlainSqlEntityFieldBinding(
			@NonNull final String columnName,
			final String columnSql,
			@NonNull final DocumentFieldWidgetType widgetType,
			final Class<?> sqlValueClass,
			final IStringExpression sqlOrderBy,
			final boolean virtualColumn)
	{
		this.columnName = columnName;
		this.columnSql = columnSql != null ? columnSql : columnName;
		this.widgetType = widgetType;
		this.sqlValueClass = sqlValueClass != null ? sqlValueClass : widgetType.getValueClass();
		this.sqlOrderBy = sqlOrderBy;
		this.virtualColumn = virtualColumn;
	}

}
