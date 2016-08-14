package de.metas.ui.web.window.model.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.service.impl.LookupDAO.SQLNamePairIterator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.adempiere.util.Check;
import org.compiere.util.DB;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.LookupDataSource;

/*
 * #%L
 * metasfresh-webui-api
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

public class SqlLookupDataSource implements LookupDataSource
{
	public static final SqlLookupDataSource of(final SqlLookupDescriptor sqlLookupDescriptor)
	{
		return new SqlLookupDataSource(sqlLookupDescriptor);
	}

	private static final Logger logger = LogManager.getLogger(SqlLookupDataSource.class);

	private static final String FILTER_Any = "%";
	private static final String FILTER_Any_SQL = "'%'";

	private final SqlLookupDescriptor sqlLookupDescriptor;

	private SqlLookupDataSource(final SqlLookupDescriptor sqlLookupDescriptor)
	{
		super();
		this.sqlLookupDescriptor = Preconditions.checkNotNull(sqlLookupDescriptor);
	}

	@Override
	public boolean isNumericKey()
	{
		return sqlLookupDescriptor.isNumericKey();
	}

	@Override
	public List<LookupValue> findEntities(final Document document, final String filter, final int firstRow, final int pageLength)
	{
		if (!isValidFilter(filter))
		{
			throw new IllegalArgumentException("Invalid filter: " + filter);
		}

		final DataSourceValidationContext evalCtx = createEvaluationContext(document, filter, firstRow, pageLength);
		final IStringExpression sqlForFetchingExpression = sqlLookupDescriptor.getSqlForFetchingExpression();
		final String sqlForFetching = sqlForFetchingExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		final boolean numericKey = sqlLookupDescriptor.isNumericKey();
		final int entityTypeIndex = sqlLookupDescriptor.getEntityTypeIndex();
		try (final SQLNamePairIterator data = new SQLNamePairIterator(sqlForFetching, numericKey, entityTypeIndex))
		{
			final List<LookupValue> values = data.fetchAll()
					.stream()
					.filter(evalCtx::acceptItem)
					.map(namePair -> toLookupValue(namePair))
					.collect(Collectors.toList());

			logger.trace("Returning values={} (executed sql: {})", values, sqlForFetching);
			return values;
		}
	}

	@Override
	public List<LookupValue> findEntities(final Document document, final int pageLength)
	{
		return findEntities(document, FILTER_Any, FIRST_ROW, pageLength);
	}

	@Override
	public LookupValue findById(final Object id)
	{
		if (id == null)
		{
			return null;
		}

		final boolean numericKey = sqlLookupDescriptor.isNumericKey();
		if (numericKey)
		{
			if (id instanceof Number)
			{
				final int idInt = ((Number)id).intValue();
				return findByIntegerId(idInt);
			}

			final String idStr = id.toString().trim();
			if (idStr.isEmpty())
			{
				return null;
			}

			final int idInt = Integer.parseInt(id.toString());
			if (idInt < 0)
			{
				return null;
			}

			return findByIntegerId(idInt);
		}
		// string key
		else
		{
			final String idStr = id.toString();
			return findByStringId(idStr);
		}
	}

	private IntegerLookupValue findByIntegerId(final int id)
	{
		final String sql = sqlLookupDescriptor.getSqlForFetchingDisplayNameById("?");
		final String displayName = DB.getSQLValueString(ITrx.TRXNAME_ThreadInherited, sql, id);
		if (displayName == null)
		{
			return null;
		}

		return IntegerLookupValue.of(id, displayName);
	}
	
	private StringLookupValue findByStringId(final String id)
	{
		final String sql = sqlLookupDescriptor.getSqlForFetchingDisplayNameById("?");
		final String displayName = DB.getSQLValueString(ITrx.TRXNAME_ThreadInherited, sql, id);
		if (displayName == null)
		{
			return null;
		}

		return StringLookupValue.of(id, displayName);
	}

	private static final LookupValue toLookupValue(final NamePair namePair)
	{
		if (namePair == null)
		{
			return null;
		}
		else if (namePair instanceof ValueNamePair)
		{
			final ValueNamePair vnp = (ValueNamePair)namePair;
			return StringLookupValue.of(vnp.getValue(), vnp.getName());
		}
		else if (namePair instanceof KeyNamePair)
		{
			final KeyNamePair knp = (KeyNamePair)namePair;
			return IntegerLookupValue.of(knp.getKey(), knp.getName());
		}
		else
		{
			// shall not happen
			throw new IllegalArgumentException("Unknown namePair: " + namePair + " (" + namePair.getClass() + ")");
		}
	}

	private boolean isValidFilter(final String filter)
	{
		if (Check.isEmpty(filter, true))
		{
			return false;
		}

		if (filter == FILTER_Any)
		{
			return true;
		}

		return true;
	}

	private DataSourceValidationContext createEvaluationContext(final Document document, final String filter, final int sqlFetchOffset, final int sqlFetchLimit)
	{
		final DataSourceValidationContext validationCtx = new DataSourceValidationContext(document);

		validationCtx.putValue(SqlLookupDescriptor.SQL_PARAM_FilterSql.getName(), convertFilterToSql(filter));
		validationCtx.putValue(SqlLookupDescriptor.SQL_PARAM_Offset.getName(), sqlFetchOffset);
		validationCtx.putValue(SqlLookupDescriptor.SQL_PARAM_Limit.getName(), sqlFetchLimit);

		// SQL validation rule
		{
			final IValidationRule validationRule = sqlLookupDescriptor.getValidationRule();
			validationCtx.setValidationRule(validationRule);

			String sqlValidationRule = validationRule.getPrefilterWhereClause(validationCtx);
			if (Check.isEmpty(sqlValidationRule, true))
			{
				sqlValidationRule = "1=1";
			}
			validationCtx.putValue(SqlLookupDescriptor.SQL_PARAM_ValidationRuleSql.getName(), sqlValidationRule);
		}

		return validationCtx;
	}

	private String convertFilterToSql(final String filter)
	{
		if (filter == FILTER_Any)
		{
			return FILTER_Any_SQL;
		}

		String searchSql = filter;
		if (!searchSql.startsWith("%"))
		{
			searchSql = "%" + searchSql;
		}
		if (!searchSql.endsWith("%"))
		{
			searchSql += "%";
		}

		return DB.TO_STRING(searchSql);
	}

	private final class DataSourceValidationContext implements IValidationContext
	{
		private final Document document;
		private final Map<String, Object> name2value = new HashMap<>();
		private IValidationRule validationRule = NullValidationRule.instance;

		private DataSourceValidationContext(final Document document)
		{
			super();
			this.document = document;
		}

		@Override
		public int getWindowNo()
		{
			return document.getWindowNo();
		}

		@Override
		public String getContextTableName()
		{
			final SqlDocumentEntityDataBindingDescriptor dataBinding = SqlDocumentEntityDataBindingDescriptor.cast(document.getEntityDescriptor().getDataBinding());
			return dataBinding.getSqlTableName();
		}

		@Override
		public String getTableName()
		{
			return sqlLookupDescriptor.getSqlTableName();
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			if (name2value.containsKey(variableName))
			{
				final Object valueObj = name2value.get(variableName);
				return valueObj == null ? null : valueObj.toString();
			}

			// Fallback to document evaluatee
			return document.asEvaluatee().get_ValueAsString(variableName);
		}

		public void putValue(final String variableName, final Object value)
		{
			name2value.put(variableName, value);
		}

		public void setValidationRule(final IValidationRule validationRule)
		{
			this.validationRule = validationRule;
		}

		public boolean acceptItem(final NamePair item)
		{
			return validationRule.accept(this, item);
		}
	}
}
