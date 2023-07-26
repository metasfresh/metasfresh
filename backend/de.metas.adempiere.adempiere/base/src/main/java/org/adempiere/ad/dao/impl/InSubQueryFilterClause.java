package org.adempiere.ad.dao.impl;

import java.util.function.Consumer;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IInSubQueryFilterClause;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;

import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@ToString
public class InSubQueryFilterClause<ModelType, ParentType> implements IInSubQueryFilterClause<ModelType, ParentType>
{
	static <ModelType> IInSubQueryFilterClause<ModelType, ICompositeQueryFilter<ModelType>> of(final String tableName, @NonNull final ICompositeQueryFilter<ModelType> filters)
	{
		return new InSubQueryFilterClause<>(tableName, filters, filters::addFilter);
	}

	private final ParentType parent;
	private final Consumer<InSubQueryFilter<ModelType>> finisher;
	private final InSubQueryFilter.Builder<ModelType> inSubQueryBuilder;
	private boolean destroyed = false;

	InSubQueryFilterClause(@NonNull final String tableName, @NonNull final ParentType parent, @NonNull final Consumer<InSubQueryFilter<ModelType>> finisher)
	{
		this.parent = parent;
		this.finisher = finisher;
		inSubQueryBuilder = InSubQueryFilter.<ModelType> builder()
				.tableName(tableName);
	}

	private void asserNotDestroyed()
	{
		if (destroyed)
		{
			throw new AdempiereException("already destroyed: " + this);
		}
	}

	private void markAsDestroyed()
	{
		asserNotDestroyed();
		destroyed = true;
	}

	@Override
	public ParentType end()
	{
		markAsDestroyed();

		final InSubQueryFilter<ModelType> inSubQueryFilter = inSubQueryBuilder.build();
		finisher.accept(inSubQueryFilter);
		return parent;
	}

	@Override
	public IInSubQueryFilterClause<ModelType, ParentType> subQuery(final IQuery<?> subQuery)
	{
		asserNotDestroyed();

		inSubQueryBuilder.subQuery(subQuery);
		return this;
	}

	@Override
	public IInSubQueryFilterClause<ModelType, ParentType> matchingColumnNames(final String columnName, final String subQueryColumnName, final IQueryFilterModifier modifier)
	{
		asserNotDestroyed();

		inSubQueryBuilder.matchingColumnNames(columnName, subQueryColumnName, modifier);
		return this;
	}

	@Override
	public IInSubQueryFilterClause<ModelType, ParentType> matchingColumnNames(final String columnName, final String subQueryColumnName)
	{
		asserNotDestroyed();

		inSubQueryBuilder.matchingColumnNames(columnName, subQueryColumnName);
		return this;
	}
}
