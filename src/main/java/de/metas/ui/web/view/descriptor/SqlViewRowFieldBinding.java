package de.metas.ui.web.view.descriptor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.util.Check;

import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor.DocumentFieldValueLoader;
import de.metas.ui.web.window.descriptor.sql.SqlEntityFieldBinding;

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

public class SqlViewRowFieldBinding implements SqlEntityFieldBinding
{
	static final SqlViewRowFieldBinding of(final SqlDocumentFieldDataBindingDescriptor fieldDataBinding, final boolean isDisplayColumnAvailable)
	{
		return new SqlViewRowFieldBinding(fieldDataBinding, isDisplayColumnAvailable);
	}
	
	/**
	 * Retrieves a particular field from given {@link ResultSet}.
	 */
	@FunctionalInterface
	public static interface SqlViewRowFieldLoader
	{
		Object retrieveValueAsJson(ResultSet rs) throws SQLException;
	}

	private final String fieldName;
	private final String columnName;
	private final String columnSql;
	private final boolean keyColumn;
	private final DocumentFieldWidgetType widgetType;
	
	private final Class<?> sqlValueClass;
	private final String sqlSelectValue;
	private final IStringExpression sqlSelectDisplayValue;
	private final boolean usingDisplayColumn;

	private final IExpression<String> sqlOrderBy;
	private final IStringExpression sqlOrderByAsc;
	private final IStringExpression sqlOrderByDesc;

	private final SqlViewRowFieldLoader fieldLoader;

	private SqlViewRowFieldBinding(final SqlDocumentFieldDataBindingDescriptor fieldDataBinding, final boolean isDisplayColumnAvailable)
	{
		fieldName = fieldDataBinding.getFieldName();
		columnName = fieldDataBinding.getColumnName();
		columnSql = fieldDataBinding.getColumnSql();
		keyColumn = fieldDataBinding.isKeyColumn();
		widgetType = fieldDataBinding.getWidgetType();

		sqlValueClass = fieldDataBinding.getSqlValueClass();
		sqlSelectValue = fieldDataBinding.getSqlSelectValue();
		usingDisplayColumn = fieldDataBinding.isUsingDisplayColumn(); // TODO: shall we use displayColumnAvailable instead?
		sqlSelectDisplayValue = fieldDataBinding.getSqlSelectDisplayValue();

		sqlOrderBy = fieldDataBinding.getSqlFullOrderBy();
		sqlOrderByAsc = fieldDataBinding.buildSqlFullOrderBy(true);
		sqlOrderByDesc = fieldDataBinding.buildSqlFullOrderBy(false);

		final DocumentFieldValueLoader fieldValueLoader = fieldDataBinding.getDocumentFieldValueLoader();
		fieldLoader = createRowFieldLoader(fieldValueLoader, isDisplayColumnAvailable);
	}

	/**
	 * NOTE to developer: keep this method static and provide only primitive or lambda parameters
	 *
	 * @param fieldValueLoader
	 * @param isDisplayColumnAvailable
	 */
	private static SqlViewRowFieldLoader createRowFieldLoader(final DocumentFieldValueLoader fieldValueLoader, final boolean isDisplayColumnAvailable)
	{
		Check.assumeNotNull(fieldValueLoader, "Parameter fieldValueLoader is not null");
		return rs -> {
			final Object fieldValue = fieldValueLoader.retrieveFieldValue(rs, isDisplayColumnAvailable);
			return Values.valueToJsonObject(fieldValue);
		};
	}

	public String getFieldName()
	{
		return fieldName;
	}

	@Override
	public String getColumnName()
	{
		return columnName;
	}

	public boolean isKeyColumn()
	{
		return keyColumn;
	}
	
	@Override
	public String getColumnSql()
	{
		return columnSql;
	}
	
	@Override
	public DocumentFieldWidgetType getWidgetType()
	{
		return widgetType;
	}
	
	@Override
	public Class<?> getSqlValueClass()
	{
		return sqlValueClass;
	}

	public String getSqlSelectValue()
	{
		return sqlSelectValue;
	}

	public boolean isUsingDisplayColumn()
	{
		return usingDisplayColumn;
	}

	public IStringExpression getSqlSelectDisplayValue()
	{
		return sqlSelectDisplayValue;
	}

	public SqlViewRowFieldLoader getFieldLoader()
	{
		return fieldLoader;
	}

	public IExpression<String> getSqlOrderBy()
	{
		return sqlOrderBy;
	}

	public IStringExpression getSqlOrderBy(final boolean ascending)
	{
		return ascending ? sqlOrderByAsc : sqlOrderByDesc;
	}
}
