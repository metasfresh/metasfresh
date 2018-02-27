package de.metas.testsupport;

import static de.metas.testsupport.QueryFilterTestUtil.castOrNull;
import static org.assertj.core.error.ElementsShouldSatisfy.elementsShouldSatisfyAny;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.dao.impl.InArrayQueryFilter;
import org.adempiere.ad.dao.impl.NotQueryFilter;
import org.adempiere.model.ModelColumn;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Failures;
import org.assertj.core.internal.Iterables;
import org.springframework.util.ReflectionUtils;

import com.jgoodies.common.base.Objects;

import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
public class CompositeQueryFilterAssert extends AbstractAssert<CompositeQueryFilterAssert, ICompositeQueryFilter>
{
	public CompositeQueryFilterAssert(@Nullable final ICompositeQueryFilter actual)
	{
		super(actual, CompositeQueryFilterAssert.class);
	}

	public static CompositeQueryFilterAssert assertThat(@Nullable final ICompositeQueryFilter actual)
	{
		return new CompositeQueryFilterAssert(actual);
	}

	public CompositeQueryFilterAssert isJoinAnd()
	{
		if(!actual.isJoinAnd())
		{
			failWithMessage("Expected the ICompositeQueryFilter <%s> to have JoinAnd", actual);
		}
		return this;
	}

	public CompositeQueryFilterAssert isJoinOr()
	{
		if(!actual.isJoinOr())
		{
			failWithMessage("Expected the ICompositeQueryFilter <%s> to be JoinOr", actual);
		}
		return this;
	}

	public CompositeQueryFilterAssert hasEqualsFilter(final ModelColumn column, final Object value)
	{
		final Predicate<IQueryFilter> p = filter -> {

			final EqualsQueryFilter equalsQueryFilter = castOrNull(filter, EqualsQueryFilter.class);
			if (equalsQueryFilter == null)
			{
				return false;
			}

			final boolean hasColumnName = equalsQueryFilter.getColumnName().equals(column.getColumnName());
			if (!hasColumnName)
			{
				return false;
			}

			final boolean hasValue = Objects.equals(equalsQueryFilter.getValue(), value);
			if (!hasValue)
			{
				return false;
			}
			return true;
		};

		return anyFilterMatches(p);
	}

	public CompositeQueryFilterAssert hasInArrayFilter(final ModelColumn column, final Object ... values)
	{
		return hasInArrayFilter(column, Arrays.asList(values));
	}

	public CompositeQueryFilterAssert hasInArrayFilter(final ModelColumn column, final Collection<?> values)
	{
		final Predicate<IQueryFilter> p = filter -> {

			final InArrayQueryFilter inArrayQueryFilter = castOrNull(filter, InArrayQueryFilter.class);
			if (inArrayQueryFilter == null)
			{
				return false;
			}

			final boolean hasColumnName = inArrayQueryFilter.getColumnName().equals(column.getColumnName());
			if (!hasColumnName)
			{
				return false;
			}

			try
			{
				Iterables.instance().assertContainsOnly(info, inArrayQueryFilter.getValuesOrNull(), values.toArray());
			}
			catch (final AssertionError assertionError)
			{
				return false;
			}
			return true;
		};

		return anyFilterMatches(p);
	}

	public CompositeQueryFilterAssert hasStringLikeFilter(final ModelColumn column, final String likeExpression)
	{
		final Predicate<IQueryFilter> p = filter -> {
			try
			{
				QueryFilterAssert.assertThat(filter).isStringLikeFilter(column, likeExpression);
				return true;
			}
			catch (AssertionError e)
			{
				return false;
			}
		};
		return anyFilterMatches(p);
	}

	public CompositeQueryFilterAssert hasNotQueryFilter()
	{
		final Predicate<IQueryFilter> p = filter -> {

			final NotQueryFilter notQueryFilter = castOrNull(filter, NotQueryFilter.class);

			final boolean filterIsNotQueryFilter = notQueryFilter != null;
			return filterIsNotQueryFilter;
		};
		return anyFilterMatches(p);
	}

	public CompositeQueryFilterAssert hasCompositeOrFilter()
	{
		final Predicate<IQueryFilter> p = filter -> {

			final ICompositeQueryFilter compositeQueryFilter = castOrNull(filter, ICompositeQueryFilter.class);
			if (compositeQueryFilter == null)
			{
				return false;
			}
			return compositeQueryFilter.isJoinOr();
		};
		return anyFilterMatches(p);
	}

	public CompositeQueryFilterAssert hasCompositeAndFilter()
	{
		final Predicate<IQueryFilter> p = filter -> {

			final ICompositeQueryFilter compositeQueryFilter = castOrNull(filter, ICompositeQueryFilter.class);
			if (compositeQueryFilter == null)
			{
				return false;
			}
			return compositeQueryFilter.isJoinAnd();
		};
		return anyFilterMatches(p);
	}

	public CompositeQueryFilterAssert hasCompareFilter(final ModelColumn column, final Operator operator, final Object value)
	{
		final Predicate<IQueryFilter> p = filter -> {

			final CompareQueryFilter compareQueryFilter = castOrNull(filter, CompareQueryFilter.class);
			if (compareQueryFilter == null)
			{
				return false;
			}

			final boolean hasColumnName = compareQueryFilter.getColumnName().equals(column.getColumnName());
			if (!hasColumnName)
			{
				return false;
			}

			final boolean hasValue = Objects.equals(compareQueryFilter.getValue(), value);
			if (!hasValue)
			{
				return false;
			}

			final boolean hasOperator = Objects.equals(compareQueryFilter.getOperator(), operator);
			if (!hasOperator)
			{
				return false;
			}

			return true;
		};
		return anyFilterMatches(p);
	}

	public CompositeQueryFilterAssert hasActiveRecordQueryFilter()
	{
		final Predicate<IQueryFilter> p = filter -> {

			final ActiveRecordQueryFilter compareQueryFilter = castOrNull(filter, ActiveRecordQueryFilter.class);
			if (compareQueryFilter == null)
			{
				return false;
			}

			return true;
		};
		return anyFilterMatches(p);
	}

	private CompositeQueryFilterAssert anyFilterMatches(@NonNull final Predicate<IQueryFilter> p)
	{
		isNotNull();

		@SuppressWarnings("unchecked")
		final List<IQueryFilter> filters = actual.getFilters();
		for (final IQueryFilter filter : filters)
		{
			if (p.test(filter))
			{
				return this;
			}
		}
		throw Failures.instance().failure(info, elementsShouldSatisfyAny(actual));
	}

	public CompositeQueryFilterAssert hasNoFilterRegarding(ModelColumn column)
	{
		Predicate<IQueryFilter> p = filter -> {

			Method columnNameMethod = ReflectionUtils.findMethod(filter.getClass(), "getColumnName");
			if (columnNameMethod == null)
			{
				return false;
			}

			try
			{
				String columnName = (String)columnNameMethod.invoke(filter);
				return Objects.equals(columnName, column.getColumnName());
			}
			catch (final Exception e)
			{
				throw new RuntimeException("Unable to invoke columnNameMethod, please check the signature", e);
			}
		};

		isNotNull();

		@SuppressWarnings("unchecked")
		final List<IQueryFilter> filters = actual.getFilters();
		for (final IQueryFilter filter : filters)
		{
			if (p.test(filter))
			{
				failWithMessage("None of the filters included in actual \n<%s>\n should have columnName <%s>, but this filter does:\n<%s>",
						actual, column.getColumnName(), filter);
			}
		}
		return this;
	}
}
