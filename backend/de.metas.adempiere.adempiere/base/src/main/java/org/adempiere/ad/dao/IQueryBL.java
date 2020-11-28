package org.adempiere.ad.dao;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.util.Env;

import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.util.ISingletonService;

import javax.annotation.Nullable;

public interface IQueryBL extends ISingletonService
{
	<T> IQueryBuilder<T> createQueryBuilder(Class<T> modelClass, Properties ctx, @Nullable String trxName);

	/**
	 * @param next identifier of the next page, as taken from the previous page's {@link QueryResultPage#getNextPageDescriptor()}.
	 * @see IQuery#paginate(Class, int)
	 */
	<T> QueryResultPage<T> retrieveNextPage(Class<T> clazz, String next);

	<T> IQueryBuilder<T> createQueryBuilder(Class<T> modelClass, Object contextProvider);

	IQueryBuilder<Object> createQueryBuilder(String modelTableName, Properties ctx, String trxName);

	IQueryBuilder<Object> createQueryBuilder(String modelTableName, Object contextProvider);

	/**
	 * @return query builder using current context and thread inherited transaction
	 */
	default IQueryBuilder<Object> createQueryBuilder(String modelTableName)
	{
		return createQueryBuilder(modelTableName, Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * @return query builder using current context and thread inherited transaction
	 */
	default <T> IQueryBuilder<T> createQueryBuilder(Class<T> modelClass)
	{
		return createQueryBuilder(modelClass, Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * @return query builder using current context and out of transaction
	 */
	default <T> IQueryBuilder<T> createQueryBuilderOutOfTrx(Class<T> modelClass)
	{
		return createQueryBuilder(modelClass, Env.getCtx(), ITrx.TRXNAME_None);
	}

	/**
	 * @return query builder using current context and out of transaction
	 */
	default IQueryBuilder<Object> createQueryBuilderOutOfTrx(String modelTableName)
	{
		return createQueryBuilder(modelTableName, Env.getCtx(), ITrx.TRXNAME_None);
	}

	/**
	 * Create a query builder to query for a class like <code>IProductAware</code> (but also regular model interfaces like I_C_Order are supported), for which the framework can't deduct the table name.
	 *
	 * @param tableName name of the table in question, which can't be deducted from the given <code>modelClass</code>. In case this is null, the tableName will be fetched from <code>modelClass</code>.
	 */
	<T> IQueryBuilder<T> createQueryBuilder(Class<T> modelClass, String tableName, Object contextProvider);

	/**
	 * @deprecated Please use {@link #createQueryOrderByBuilder(Class)}
	 */
	@Deprecated
	<T> IQueryOrderByBuilder<T> createQueryOrderByBuilder();

	<T> IQueryOrderByBuilder<T> createQueryOrderByBuilder(Class<T> modelClass);

	IQueryOrderBy createSqlQueryOrderBy(String orderBy);

	/**
	 * @param modelClass the model class. Assumes that the table name can be obtained from the model class via {@link InterfaceWrapperHelper#getTableName(Class)}.
	 */
	<T> ICompositeQueryFilter<T> createCompositeQueryFilter(Class<T> modelClass);

	/**
	 * @param tableName name of the table in question. <b>Can</b> be null
 */
	<T> ICompositeQueryFilter<T> createCompositeQueryFilter(String tableName);

	<T> ICompositeQueryUpdater<T> createCompositeQueryUpdater(Class<T> modelClass);

	/**
	 * Applies the given <code>filter</code> to the given <code>model</code> and builds a detailed info string (with each particular filter and if it accepted the model or not).
	 *
	 * @return detailed debug info string
	 */
	<T> String debugAccept(IQueryFilter<T> filter, T model);
}
