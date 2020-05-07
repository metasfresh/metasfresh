package de.metas.testsupport;

import static de.metas.testsupport.QueryFilterTestUtil.castOrNull;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.StringLikeFilter;
import org.adempiere.model.ModelColumn;
import org.assertj.core.api.AbstractAssert;

import com.jgoodies.common.base.Objects;

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

@SuppressWarnings("rawtypes")
public class QueryFilterAssert extends AbstractAssert<QueryFilterAssert, IQueryFilter>
{
	public QueryFilterAssert(@Nullable final IQueryFilter actual)
	{
		super(actual, QueryFilterAssert.class);
	}

	public static QueryFilterAssert assertThat(@Nullable final IQueryFilter actual)
	{
		return new QueryFilterAssert(actual);
	}

	public QueryFilterAssert isStringLikeFilter(final ModelColumn column, final String likeExpression)
	{
		final StringLikeFilter actualStringLikeQueryFilter = castOrNull(actual, StringLikeFilter.class);
		if (actualStringLikeQueryFilter == null)
		{
			failWithMessage("Expected IQueryFilter to be instance of <%s> but was <%s>",
					StringLikeFilter.class, actual.getClass());
		}

		final boolean hasColumnName = actualStringLikeQueryFilter.getColumnName().equals(column.getColumnName());
		if (!hasColumnName)
		{
			failWithMessage("Expected StringLikeFilter to have hasColumnName <%s> but had <%s>",
					column.getColumnName(), actualStringLikeQueryFilter.getColumnName());
		}

		final boolean hasLikeExpression = Objects.equals(actualStringLikeQueryFilter.getValue(), likeExpression);
		if (!hasLikeExpression)
		{
			failWithMessage("Expected StringLikeFilter to have likeExpression <%s> but had <%s>",
					likeExpression, actualStringLikeQueryFilter.getValue());
		}
		return this;
	}
}
