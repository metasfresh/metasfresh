package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.service.impl.LookupDAO.SQLNamePairIterator;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.vaadin.window.descriptor.DataFieldLookupDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.editor.LookupValue;

/*
 * #%L
 * metasfresh-webui
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

public class SqlLazyLookupDataSource implements LookupDataSource
{
	private static final Logger logger = LogManager.getLogger(SqlLazyLookupDataSource.class);

	private final DataFieldLookupDescriptor sqlLookupDescriptor;
	
	private ImmutableList<LookupValue> _lastPage = ImmutableList.of();

	public SqlLazyLookupDataSource(final DataFieldLookupDescriptor sqlLookupDescriptor)
	{
		super();
		this.sqlLookupDescriptor = sqlLookupDescriptor;
	}
	
	@Override
	public int size(final String filter)
	{
		if (!isValidFilter(filter))
		{
			final int size = _lastPage.size();
			logger.trace("Returning size={} (used last page)", size);
			return size;
		}

		final int sqlFetchOffset = 0; // does not matter
		final int sqlFetchLimit = Integer.MAX_VALUE; // does not matter
		final Evaluatee evalCtx = createEvaluationContext(filter, sqlFetchOffset, sqlFetchLimit);
		final IStringExpression sqlForCountingExpression = sqlLookupDescriptor.getSqlForCountingExpression();
		final String sqlForCounting = sqlForCountingExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		final int count = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sqlForCounting);
		logger.trace("Returning size={} (executed sql: {})", count, sqlForCounting);
		return count;
	}

	@Override
	public List<LookupValue> findEntities(final String filter, final int firstRow, final int pageLength)
	{
		if (!isValidFilter(filter))
		{
			final ImmutableList<LookupValue> lastPage = _lastPage;
			logger.trace("Returning values={} (used last page)", lastPage);
			return lastPage;
		}

		final Evaluatee evalCtx = createEvaluationContext(filter, firstRow, pageLength);
		final IStringExpression sqlForFetchingExpression = sqlLookupDescriptor.getSqlForFetchingExpression();
		final String sqlForFetching = sqlForFetchingExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		final boolean numericKey = sqlLookupDescriptor.isNumericKey();
		final int entityTypeIndex = sqlLookupDescriptor.getEntityTypeIndex();
		try (final SQLNamePairIterator data = new SQLNamePairIterator(sqlForFetching, numericKey, entityTypeIndex))
		{
			final List<LookupValue> values = data.fetchAll()
					.stream()
					.map(namePair -> toLookupValue(namePair))
					.collect(Collectors.toList());
			_lastPage = ImmutableList.copyOf(values);
			
			logger.trace("Returning values={} (executed sql: {})", values, sqlForFetching);
			return values;
		}
	}

	@Override
	public LookupValue findById(final Object id)
	{
		if(id == null)
		{
			return null;
		}
		final String sql = sqlLookupDescriptor.getSqlForFetchingDisplayNameById("?");
		final String displayName = DB.getSQLValueString(ITrx.TRXNAME_ThreadInherited, sql, id);
		if(displayName == null)
		{
			return null;
		}
		
		return LookupValue.of(id, displayName);
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
			return LookupValue.of(vnp.getValue(), vnp.getName());
		}
		else if (namePair instanceof KeyNamePair)
		{
			final KeyNamePair knp = (KeyNamePair)namePair;
			return LookupValue.of(knp.getKey(), knp.getName());
		}
		else
		{
			// shall not happen
			throw new IllegalArgumentException("Unknown namePair: " + namePair + " (" + namePair.getClass() + ")");
		}
	}

	@Override
	public boolean isValidFilter(final String filter)
	{
		if (Check.isEmpty(filter, true))
		{
			return false;
		}

		return true;
	}
	
	private Evaluatee createEvaluationContext(final String filter, final int sqlFetchOffset, final int sqlFetchLimit)
	{
		final String sqlValidationRule = "1=1"; // TODO
		
		final Map<String, Object> map = new HashMap<>();
		map.put(DataFieldLookupDescriptor.SQL_PARAM_FilterSql.getName(), convertFilterToSql(filter));
		map.put(DataFieldLookupDescriptor.SQL_PARAM_Offset.getName(), sqlFetchOffset);
		map.put(DataFieldLookupDescriptor.SQL_PARAM_Limit.getName(), sqlFetchLimit);
		map.put(DataFieldLookupDescriptor.SQL_PARAM_ValidationRuleSql.getName(), sqlValidationRule);

		return Evaluatees.ofMap(map);
	}

	private String convertFilterToSql(final String filter)
	{
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
}
