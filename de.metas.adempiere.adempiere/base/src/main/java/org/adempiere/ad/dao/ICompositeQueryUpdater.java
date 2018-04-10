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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.ad.dao.impl.ModelColumnNameValue;


/**
 * Use {@link IQueryBL#createCompositeQueryUpdater(Class)} to get an instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <T> model class of the table to be updated.
 */
public interface ICompositeQueryUpdater<T> extends ISqlQueryUpdater<T>
{

	ICompositeQueryUpdater<T> addQueryUpdater(IQueryUpdater<T> updater);

	ICompositeQueryUpdater<T> addSetColumnValue(String columnName, Object value);

	ICompositeQueryUpdater<T> addSetColumnFromColumn(String columnName, ModelColumnNameValue<T> fromColumnName);

	ICompositeQueryUpdater<T> addAddValueToColumn(String columnName, BigDecimal valueToAdd);

	ICompositeQueryUpdater<T> addAddValueToColumn(String columnName, BigDecimal valueToAdd, IQueryFilter<T> onlyWhenFilter);
}
