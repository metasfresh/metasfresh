package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.ICompositeQueryUpdaterExecutor;
import org.adempiere.ad.dao.IQueryInsertExecutor;
import org.adempiere.ad.model.util.Model2IdFunction;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;

import com.google.common.base.Function;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

/**
 * Contains common methods to be used in {@link IQuery} implementations.
 * 
 * @author tsa
 *
 * @param <T> model type
 */
public abstract class AbstractTypedQuery<T> implements IQuery<T>
{
	@Override
	public final <ET extends T> ET firstOnly(final Class<ET> clazz) throws DBException
	{
		final boolean throwExIfMoreThenOneFound = true;
		return firstOnly(clazz, throwExIfMoreThenOneFound);
	}

	@Override
	public final <ET extends T> ET firstOnlyOrNull(final Class<ET> clazz) throws DBException
	{
		final boolean throwExIfMoreThenOneFound = false;
		return firstOnly(clazz, throwExIfMoreThenOneFound);
	}

	@Override
	public final <ET extends T> ET firstOnlyNotNull(final Class<ET> clazz) throws DBException
	{
		final boolean throwExIfMoreThenOneFound = true;
		final ET model = firstOnly(clazz, throwExIfMoreThenOneFound);
		if (model == null)
		{
			throw new DBException("@NotFound@ @" + getTableName() + "@"
					+ "\n\n@Query@: " + this);
		}

		return model;
	}

	@Override
	public final <ET extends T> ET firstNotNull(final Class<ET> clazz) throws DBException
	{
		final ET model = first(clazz);
		if (model == null)
		{
			throw new DBException("@NotFound@ @" + getTableName() + "@"
					+ "\n\n@Query@: " + this);
		}

		return model;
	}

	/**
	 * 
	 * @param clazz
	 * @param throwExIfMoreThenOneFound if true and there more then one record found it will throw exception, <code>null</code> will be returned otherwise.
	 * @return model or null
	 * @throws DBException
	 */
	protected abstract <ET extends T> ET firstOnly(final Class<ET> clazz, final boolean throwExIfMoreThenOneFound) throws DBException;

	@Override
	public <ET extends T> Map<Integer, ET> mapById(final Class<ET> clazz)
	{
		final List<ET> list = list(clazz);
		final Map<Integer, ET> map = new HashMap<>(list.size());
		for (final ET item : list)
		{
			final int itemId = InterfaceWrapperHelper.getId(item);
			map.put(itemId, item);
		}

		return map;
	}
	
	
	@Override
	public final List<Map<String, Object>> listColumns(String... columnNames)
	{
		final boolean distinct = false;
		return listColumns(distinct, columnNames);
	}

	@Override
	public final List<Map<String, Object>> listDistinct(final String... columnNames)
	{
		final boolean distinct = true;
		return listColumns(distinct, columnNames);
	}

	/**
	 * Selects given columns and return the result as a list of ColumnName to Value map.
	 *
	 * @param distinct true if the value rows shall be district
	 * @param columnNames
	 * @return a list of rows, where each row is a {@link Map} having the required columns as keys.
	 */
	protected abstract List<Map<String, Object>> listColumns(final boolean distinct, final String... columnNames);

	@Override
	public <K, ET extends T> Map<K, ET> map(final Class<ET> modelClass, final Function<ET, K> keyFunction)
	{
		final List<ET> list = list(modelClass);
		return Maps.uniqueIndex(list, keyFunction);
	}

	@Override
	public <ET extends T> Map<Integer, ET> mapToId(final Class<ET> modelClass)
	{
		return map(modelClass, Model2IdFunction.<ET> getInstance());
	}

	@Override
	public <K, ET extends T> ListMultimap<K, ET> listMultimap(final Class<ET> modelClass, final Function<ET, K> keyFunction)
	{
		final ListMultimap<K, ET> map = LinkedListMultimap.create();
		final List<ET> list = list(modelClass);
		for (final ET item : list)
		{
			final K key = keyFunction.apply(item);
			map.put(key, item);
		}
		return map;
	}

	@Override
	public <K, ET extends T> Collection<List<ET>> listAndSplit(final Class<ET> modelClass, final Function<ET, K> keyFunction)
	{
		final ListMultimap<K, ET> map = listMultimap(modelClass, keyFunction);
		return Multimaps.asMap(map).values();
	}

	@Override
	public ICompositeQueryUpdaterExecutor<T> updateDirectly()
	{
		return new CompositeQueryUpdaterExecutor<T>(this);
	}

	@Override
	public <ToModelType> IQueryInsertExecutor<ToModelType, T> insertDirectlyInto(final Class<ToModelType> toModelClass)
	{
		return new QueryInsertExecutor<>(toModelClass, this);
	}

	abstract <ToModelType> int executeInsert(final QueryInsertExecutor<ToModelType, T> queryInserter);
}
