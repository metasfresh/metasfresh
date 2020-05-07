/**
 *
 */
package de.metas.adempiere.form.terminal.lookup;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.IQueryOrderByBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.db.DBConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import com.google.common.base.Optional;

import de.metas.adempiere.form.terminal.ITerminalLookup;

/**
 * @author tsa
 *
 */
public class SimpleTableLookup<T> implements ITerminalLookup
{
	//
	// Services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final Class<T> modelClass;
	private final String tableName;
	private final String keyColumnName;
	private final String displayColumnName;
	private IQueryFilter<T> filters = null;

	private List<SearchColumn> searchColumns;
	private IQueryOrderBy orderBy;

	private final class SearchColumn
	{
		private final String columnName;
		private final int displayType;

		public SearchColumn(final String columnName, final int displayType)
		{
			super();
			this.columnName = columnName;
			this.displayType = displayType;
		}

		public String getColumnName()
		{
			return columnName;
		}

		public int getDisplayType()
		{
			return displayType;
		}
	}

	public SimpleTableLookup(final Class<T> modelClass, final String keyColumnName, final String displayColumnName)
	{
		super();

		Check.assumeNotNull(modelClass, "modelClass not null");
		this.modelClass = modelClass;
		this.tableName = InterfaceWrapperHelper.getTableName(modelClass);
		this.keyColumnName = keyColumnName;
		this.displayColumnName = displayColumnName;

		init();
	}

	private void init()
	{
		this.searchColumns = new ArrayList<SearchColumn>();

		final IQueryOrderByBuilder<T> orderByBuilder = queryBL.createQueryOrderByBuilder(modelClass);
		orderByBuilder.addColumn(displayColumnName, Direction.Ascending, Nulls.Last);

		final POInfo poInfo = POInfo.getPOInfo(tableName);
		for (int i = 0; i < poInfo.getColumnCount(); i++)
		{
			final POInfoColumn column = poInfo.getColumn(i);
			if (!isSelectionColumn(column))
			{
				continue;
			}

			final String columnName = column.getColumnName();
			final int displayType = column.getDisplayType();
			final SearchColumn searchColumn = new SearchColumn(columnName, displayType);
			this.searchColumns.add(searchColumn);

			if (!displayColumnName.equals(columnName))
			{
				orderByBuilder.addColumn(columnName, Direction.Ascending, Nulls.Last);
			}
		}

		this.orderBy = orderByBuilder.createQueryOrderBy();
	}

	private boolean isSelectionColumn(final POInfoColumn column)
	{
		if (DisplayType.isID(column.getDisplayType()))
		{
			return false;
		}

		if (column.isSelectionColumn())
		{
			return true;
		}

		final String columnName = column.getColumnName();
		return "Value".equalsIgnoreCase(columnName)
				|| "Name".equals(columnName)
				|| "DocumentNo".equals(columnName);
	}

	public Properties getCtx()
	{
		return Env.getCtx();
	}

	private final IQueryFilter<T> createSearchColumnsQueryFilter(final String text)
	{
		if (text == null || text.length() == 0)
		{
			return null;
		}
		final String textToUse = text.trim();

		boolean isInteger = false;
		int valueInt = -1;
		try
		{
			valueInt = Integer.parseInt(textToUse);
			isInteger = true;
		}
		catch (final Exception e)
		{
		}
		boolean isBigDecimal = false;
		BigDecimal valueBD = null;
		try
		{
			valueBD = new BigDecimal(textToUse);
			isBigDecimal = true;
		}
		catch (final Exception e)
		{
		}

		final List<Object> params = new ArrayList<Object>();
		final StringBuilder whereClause = new StringBuilder();
		final StringBuilder orderByClause = new StringBuilder();
		for (final SearchColumn sc : searchColumns)
		{
			final String columnName = sc.getColumnName();
			final int displayType = sc.getDisplayType();

			final String clause;
			if (DisplayType.isText(displayType))
			{
				// task 07599:
				//  * make sure to append '%' so that substrings are searched for properly
				//  * unaccenting the string to also find stuff with accents, umlauts and other special letters that are unavailable on a local keyboard
				clause = "UPPER(" + DBConstants.FUNC_unaccent_string(columnName) + ") LIKE '%' || UPPER(" + DBConstants.FUNC_unaccent_string("?") + ") || '%'"; 
				params.add(getFindParameter(sc, textToUse));
			}
			else if (displayType == DisplayType.Integer && isInteger)
			{
				clause = columnName + "=?";
				params.add(valueInt);
			}
			else if (DisplayType.isNumeric(displayType) && isBigDecimal)
			{
				clause = columnName + "=?";
				params.add(valueBD);
			}
			else
			{
				clause = null;
			}

			if (clause != null)
			{
				if (whereClause.length() > 0)
				{
					whereClause.append(" OR ");
				}
				whereClause.append("(").append(clause).append(")");

				if (orderByClause.length() > 0)
				{
					orderByClause.append(",");
				}
				orderByClause.append(columnName);
			}
		}

		return TypedSqlQueryFilter.of(whereClause.toString(), params);
	}

	private final IQuery<T> createSearchQuery(final String text)
	{
		final ICompositeQueryFilter<T> filters = queryBL.createCompositeQueryFilter(modelClass);

		//
		// Create Search by Text filter
		final IQueryFilter<T> searchFilter = createSearchColumnsQueryFilter(text);
		if (searchFilter == null)
		{
			return null;
		}
		filters.addFilter(searchFilter);

		//
		// Append additional filters (if any)
		final IQueryFilter<T> additionalFilter = getFilters();
		if (additionalFilter != null)
		{
			filters.addFilter(additionalFilter);
		}

		final Properties ctx = getCtx();
		final IQuery<T> query = queryBL.createQueryBuilder(modelClass, ctx, ITrx.TRXNAME_None)
				.filter(filters)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.create()
				.setApplyAccessFilterRW(false) // only those on which current user has at least read access - task #09756
		;

		if (orderBy != null)
		{
			query.setOrderBy(orderBy);
		}

		return query;
	}

	@Override
	public KeyNamePair resolve(final String text)
	{
		final IQuery<T> query = createSearchQuery(text);
		if (query == null)
		{
			return null;
		}

		final List<T> results = query.list();

		if (results.size() == 0)
		{
			return null;
		}
		else if (results.size() == 1)
		{
			return createKeyNamePair(results.get(0));
		}
		else
		{
			// more than one found
			// => could not have a precise result, so return null
			return null;
		}
	}

	@Override
	public KeyNamePair resolveById(final int id)
	{
		final String whereClause = keyColumnName + "=?";
		final T result = new TypedSqlQuery<T>(getCtx(), modelClass, whereClause, ITrx.TRXNAME_None)
				.setParameters(id)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.firstOnly();
		if (result == null)
		{
			return new KeyNamePair(id, "<" + id + ">");
		}
		return createKeyNamePair(result);
	}

	private KeyNamePair createKeyNamePair(final T model)
	{
		final Optional<Integer> id = InterfaceWrapperHelper.getValue(model, keyColumnName);
		if (!id.isPresent())
		{
			return null;
		}

		final Object name = InterfaceWrapperHelper.getValue(model, displayColumnName).orNull();
		final String nameStr = name == null ? "" : name.toString();
		return new KeyNamePair(id.get(), nameStr);
	}

	private String getFindParameter(final SearchColumn searchColumn, String query)
	{
		if (query == null)
		{
			return null;
		}
		if (query.length() == 0 || query.equals("%"))
		{
			return null;
		}
		if (!query.endsWith("%"))
		{
			query += "%";
		}
		return query;
	} // getFindParameter

	@Override
	public List<KeyNamePair> suggest(final String text, final int maxItems)
	{
		final List<KeyNamePair> result = new ArrayList<KeyNamePair>();
		final IQuery<T> query = createSearchQuery(text);
		if (query == null)
		{
			return result;
		}

		if (maxItems > 0)
		{
			query.setLimit(maxItems + 1);
		}

		final List<T> models = query.list();
		for (final T model : models)
		{
			if (maxItems > 0 && result.size() >= maxItems)
			{
				// TODO: add some "..." key name pair
				break;
			}

			final KeyNamePair knp = createKeyNamePair(model);
			result.add(knp);
		}
		return result;
	}

	public IQueryFilter<T> getFilters()
	{
		return filters;
	}

	public void setFilters(final IQueryFilter<T> filters)
	{
		this.filters = filters;
	}
}
