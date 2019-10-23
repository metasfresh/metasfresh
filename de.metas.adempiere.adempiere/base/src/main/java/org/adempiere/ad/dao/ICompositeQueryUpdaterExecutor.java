package org.adempiere.ad.dao;

import java.math.BigDecimal;

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


/**
 * Extension of {@link ICompositeQueryUpdater} which is also capable of executing the update (see {@link #execute()}).
 * 
 * @author tsa
 *
 * @param <T>
 */
public interface ICompositeQueryUpdaterExecutor<T> extends ICompositeQueryUpdater<T>
{
	/**
	 * Execute this update
	 * 
	 * @return how many rows were updated
	 */
	int execute();

	/**
	 * Sets if the update shall be directly on underlying database or the records shall be updated one by one using the persistence engine API.
	 * 
	 * @param executeDirectly
	 * @return this
	 */
	ICompositeQueryUpdaterExecutor<T> setExecuteDirectly(final boolean executeDirectly);

	@Override
	ICompositeQueryUpdaterExecutor<T> addQueryUpdater(IQueryUpdater<T> updater);

	@Override
	ICompositeQueryUpdaterExecutor<T> addSetColumnValue(String columnName, Object value);

	@Override
	ICompositeQueryUpdaterExecutor<T> addAddValueToColumn(String columnName, BigDecimal valueToAdd);
	
	@Override
	ICompositeQueryUpdaterExecutor<T> addAddValueToColumn(String columnName, BigDecimal valueToAdd, IQueryFilter<T> onlyWhenFilter);
}
