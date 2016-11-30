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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ISingletonService;

public interface IQueryBL extends ISingletonService
{
	<T> IQueryBuilder<T> createQueryBuilder(Class<T> modelClass, Properties ctx, String trxName);

	<T> IQueryBuilder<T> createQueryBuilder(Class<T> modelClass, Object contextProvider);

	IQueryBuilder<Object> createQueryBuilder(String modelTableName, Properties ctx, String trxName);

	IQueryBuilder<Object> createQueryBuilder(String modelTableName, Object contextProvider);

	/**
	 * Create a query builder to quirey for a class like <code>IProductAware</code>, for which the framework can't deduct the table name.
	 *
	 * @param modelClass
	 * @param tableName name of the table in question, which can't be deducted from the given <code>modelClass</code>.
	 * @param contextProvider
	 * @return
	 */
	<T> IQueryBuilder<T> createQueryBuilder(Class<T> modelClass, String tableName, Object contextProvider);

	/**
	 *
	 * @return
	 * @deprecated Please use {@link #createQueryOrderByBuilder(Class)}
	 */
	@Deprecated
	<T> IQueryOrderByBuilder<T> createQueryOrderByBuilder();

	<T> IQueryOrderByBuilder<T> createQueryOrderByBuilder(Class<T> modelClass);

	IQueryOrderBy createSqlQueryOrderBy(String orderBy);

	/**
	 *
	 * @param modelClass the model class. Assumes that the table name can be obtained from the model class via {@link InterfaceWrapperHelper#getTableName(Class)}.
	 * @return
	 */
	<T> ICompositeQueryFilter<T> createCompositeQueryFilter(Class<T> modelClass);

	/**
	 *
	 * @param tableName name of the table in question.
	 * @return
	 */
	<T> ICompositeQueryFilter<T> createCompositeQueryFilter(String tableName);

	<T> ICompositeQueryUpdater<T> createCompositeQueryUpdater(Class<T> modelClass);

	/**
	 * Applies the given <code>filter</code> to the given <code>model</code> and builds a detailed info string (with each particular filter and if it accepted the model or not).
	 *
	 * @param filter
	 * @param model
	 * @return detailed debug info string
	 */
	<T> String debugAccept(IQueryFilter<T> filter, T model);
}
