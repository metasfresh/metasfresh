package de.metas.ui.web.view.descriptor;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nullable;

import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlEntityFieldBinding;
import de.metas.ui.web.window.descriptor.sql.SqlOrderByValue;
import de.metas.ui.web.window.descriptor.sql.SqlSelectDisplayValue;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class SqlViewRowFieldBinding implements SqlEntityFieldBinding
{
	/**
	 * Retrieves a particular field from given {@link ResultSet}.
	 */
	@FunctionalInterface
	public interface SqlViewRowFieldLoader
	{
		Object retrieveValue(ResultSet rs, String adLanguage) throws SQLException;
	}

	private final String fieldName;
	private final String columnName;
	private final boolean keyColumn;
	private final DocumentFieldWidgetType widgetType;
	private final boolean virtualColumn;

	private final Class<?> sqlValueClass;
	private final SqlSelectValue sqlSelectValue;
	private final SqlSelectDisplayValue sqlSelectDisplayValue;

	private final SqlOrderByValue sqlOrderBy;

	private final SqlViewRowFieldLoader fieldLoader;

	@Builder
	private SqlViewRowFieldBinding(
			@NonNull final String fieldName,
			@Nullable final String columnName,
			final boolean keyColumn,
			@NonNull final DocumentFieldWidgetType widgetType,
			final boolean virtualColumn,
			//
			@Nullable final Class<?> sqlValueClass,
			@NonNull final SqlSelectValue sqlSelectValue,
			@Nullable final SqlSelectDisplayValue sqlSelectDisplayValue,
			//
			@Nullable final SqlOrderByValue sqlOrderBy,
			@NonNull final SqlViewRowFieldLoader fieldLoader)
	{
		this.fieldName = fieldName;
		this.columnName = columnName != null ? columnName : this.fieldName;
		this.keyColumn = keyColumn;
		this.widgetType = widgetType;
		this.virtualColumn = virtualColumn;

		this.sqlValueClass = sqlValueClass != null ? sqlValueClass : widgetType.getValueClass();
		this.sqlSelectValue = sqlSelectValue;
		this.sqlSelectDisplayValue = sqlSelectDisplayValue;

		this.sqlOrderBy = sqlOrderBy != null
				? sqlOrderBy
				: SqlOrderByValue.builder().sqlSelectDisplayValue(sqlSelectDisplayValue).sqlSelectValue(sqlSelectValue).columnName(columnName).build();
		this.fieldLoader = fieldLoader;
	}
}
