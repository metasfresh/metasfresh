package de.metas.ui.web.window.model.lookup;

import java.util.LinkedHashMap;
import java.util.Map;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.service.impl.LookupDAO.SQLNamePairIterator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;

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

public class GenericSqlLookupDataSourceFetcher implements LookupDataSourceFetcher
{
	public static final GenericSqlLookupDataSourceFetcher of(final LookupDescriptor lookupDescriptor)
	{
		return new GenericSqlLookupDataSourceFetcher(lookupDescriptor);
	}

	private static final Logger logger = LogManager.getLogger(GenericSqlLookupDataSourceFetcher.class);

	private final String lookupTableName;
	private final boolean numericKey;
	private final int entityTypeIndex;

	private final IStringExpression sqlForFetchingExpression;
	private final IStringExpression sqlForFetchingDisplayNameByIdExpression;
	private final INamePairPredicate postQueryPredicate;

	private GenericSqlLookupDataSourceFetcher(final LookupDescriptor lookupDescriptor)
	{
		super();
		
		// NOTE: don't store a reference to our descriptor, just extract what we need!

		Preconditions.checkNotNull(lookupDescriptor);
		final SqlLookupDescriptor sqlLookupDescriptor = lookupDescriptor.cast(SqlLookupDescriptor.class);
		lookupTableName = sqlLookupDescriptor.getTableName();
		numericKey = sqlLookupDescriptor.isNumericKey();
		entityTypeIndex = sqlLookupDescriptor.getEntityTypeIndex();
		sqlForFetchingExpression = sqlLookupDescriptor.getSqlForFetchingExpression();
		sqlForFetchingDisplayNameByIdExpression = sqlLookupDescriptor.getSqlForFetchingDisplayNameByIdExpression();
		postQueryPredicate = sqlLookupDescriptor.getPostQueryPredicate();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("lookupTableName", lookupTableName)
				.add("sqlForFetchingExpression", sqlForFetchingExpression)
				.add("postQueryPredicate", postQueryPredicate)
				.toString();
	}

	@Override
	public String getCachePrefix()
	{
		// NOTE: it's very important to have the lookupTableName as cache name prefix because we want the cache invalidation to happen for this table
		return lookupTableName;
	}

	@Override
	public final LookupDataSourceContext.Builder newContextForFetchingById(final Object id)
	{
		return LookupDataSourceContext.builder(lookupTableName)
				.putFilterByIdParameterName("?")
				.putFilterById(id)
				.setRequiredParameters(sqlForFetchingDisplayNameByIdExpression.getParameters());
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(lookupTableName)
				.putPostQueryPredicate(postQueryPredicate)
				.setRequiredParameters(sqlForFetchingExpression.getParameters());
	}

	@Override
	public final boolean isNumericKey()
	{
		return numericKey;
	}

	/**
	 *
	 * @param evalCtx
	 * @return lookup values list
	 * @see #getRetrieveEntriesParameters()
	 */
	@Override
	public LookupValuesList retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		final String sqlForFetching = sqlForFetchingExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		try (final SQLNamePairIterator data = new SQLNamePairIterator(sqlForFetching, numericKey, entityTypeIndex))
		{
			Map<String, String> debugProperties = null;
			if (WindowConstants.isProtocolDebugging())
			{
				debugProperties = new LinkedHashMap<>();
				debugProperties.put("debug-sql", sqlForFetching);
				debugProperties.put("debug-params", evalCtx.toString());
			}
			
			LookupValuesList values = data.fetchAll()
					.stream()
					.filter(evalCtx::acceptItem)
					.map(namePair -> LookupValue.fromNamePair(namePair))
					.collect(LookupValuesList.collect(debugProperties));

			logger.trace("Returning values={} (executed sql: {})", values, sqlForFetching);
			return values;
		}
	}

	@Override
	public final LookupValue retrieveLookupValueById(final LookupDataSourceContext evalCtx)
	{
		final Object id = evalCtx.getIdToFilter();
		if (id == null)
		{
			throw new IllegalStateException("No ID provided in " + evalCtx);
		}

		final String sqlDisplayName = sqlForFetchingDisplayNameByIdExpression.evaluate(evalCtx, OnVariableNotFound.Fail);
		final String displayName = DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited, sqlDisplayName, id);
		if (displayName == null)
		{
			return LOOKUPVALUE_NULL;
		}

		//
		//
		if (id instanceof Integer)
		{
			final Integer idInt = (Integer)id;
			return IntegerLookupValue.of(idInt, displayName);
		}
		else
		{
			final String idString = id.toString();
			return StringLookupValue.of(idString, displayName);
		}
	}
}
