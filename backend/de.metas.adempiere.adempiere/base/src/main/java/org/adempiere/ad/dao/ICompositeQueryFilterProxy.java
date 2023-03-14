package org.adempiere.ad.dao;

import com.google.common.collect.Range;
import de.metas.util.InSetPredicate;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.BetweenQueryFilter;
import org.adempiere.ad.dao.impl.CoalesceEqualsQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.CompositeQueryFilter;
import org.adempiere.ad.dao.impl.ContextClientQueryFilter;
import org.adempiere.ad.dao.impl.DateIntervalIntersectionQueryFilter;
import org.adempiere.ad.dao.impl.EndsWithQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.dao.impl.InArrayQueryFilter;
import org.adempiere.ad.dao.impl.InSubQueryFilter;
import org.adempiere.ad.dao.impl.InSubQueryFilterClause;
import org.adempiere.ad.dao.impl.InstantRangeQueryFilter;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.adempiere.ad.dao.impl.NotEqualsQueryFilter;
import org.adempiere.ad.dao.impl.NotQueryFilter;
import org.adempiere.ad.dao.impl.StringLikeFilter;
import org.adempiere.ad.dao.impl.ValidFromToMatchesQueryFilter;
import org.adempiere.model.ModelColumn;
import org.compiere.model.IQuery;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

@SuppressWarnings("UnusedReturnValue")
public interface ICompositeQueryFilterProxy<T, RT>
{
	RT self();

	ICompositeQueryFilterBase<T> compositeFiltersBase();

	String getModelTableName();

	default RT addFilter(@NonNull final IQueryFilter<T> filter)
	{
		compositeFiltersBase().addFilter0(filter);
		return self();
	}

	/**
	 * Unboxes and adds the filters contained in the <code>compositeFilter</code>.
	 * If it could not be unboxed (e.g. because JOIN method does not match) the composite filter is added as is.
	 * Note that by "unboxing" we mean getting the filters included in the given {@code compositeFilter} and adding them to this instance directly, rather than adding the given {@code compositeFilter} itself.
	 */
	default RT addFiltersUnboxed(final ICompositeQueryFilter<T> compositeFilter)
	{
		compositeFiltersBase().addFiltersUnboxed0(compositeFilter);
		return self();
	}

	default ICompositeQueryFilter<T> addCompositeQueryFilter()
	{
		final ICompositeQueryFilter<T> filter = new CompositeQueryFilter<>(getModelTableName());
		addFilter(filter);
		return filter;
	}

	default RT addEqualsFilter(final String columnName, @Nullable final Object value)
	{
		final EqualsQueryFilter<T> filter = new EqualsQueryFilter<>(columnName, value);
		return addFilter(filter);
	}

	default RT addEqualsFilter(final ModelColumn<T, ?> column, @Nullable final Object value)
	{
		final EqualsQueryFilter<T> filter = new EqualsQueryFilter<>(column.getColumnName(), value);
		return addFilter(filter);
	}

	default RT addEqualsFilter(final String columnName, final Object value, final IQueryFilterModifier modifier)
	{
		final EqualsQueryFilter<T> filter = new EqualsQueryFilter<>(columnName, value, modifier);
		return addFilter(filter);
	}

	default RT addEqualsFilter(final ModelColumn<T, ?> column, final Object value, final IQueryFilterModifier modifier)
	{
		final String columnName = column.getColumnName();
		return addEqualsFilter(columnName, value, modifier);
	}

	/**
	 * Adds a filter for substrings. That filter creates SQL such as <code>columnName LIKE '%substring%'</code>.<br>
	 * The string to filter by may contain {@code _} and {@code %}. Starting and trailing '%' are supplemented if missing.
	 * <p>
	 * Note: if you don't want the starting and trailing '%' to be supplemented, check out {@link #addCompareFilter(String, CompareQueryFilter.Operator, Object)}
	 *
	 * @param ignoreCase if <code>true</code> the filter will use <code>ILIKE</code> instead of <code>LIKE</code>
	 */
	default RT addStringLikeFilter(final ModelColumn<T, ?> column, final String substring, final boolean ignoreCase)
	{
		final String columnName = column.getColumnName();
		return addStringLikeFilter(columnName, substring, ignoreCase);
	}

	/**
	 * Filters using the given string as a <b>substring</b>.
	 * If this "substring" behavior is too opinionated for your case, consider using e.g. {@link #addCompareFilter(String, CompareQueryFilter.Operator, Object)}.
	 *
	 * @param substring  will be complemented with {@code %} at both the string's start and end, if the given string doesn't have them yet.
	 * @param ignoreCase if {@code true}, then {@code ILIKE} is used as operator instead of {@code LIKE}
	 */
	default RT addStringLikeFilter(final String columnName, final String substring, final boolean ignoreCase)
	{
		final StringLikeFilter<T> filter = new StringLikeFilter<>(columnName, substring, ignoreCase);
		return addFilter(filter);
	}

	default RT addStringNotLikeFilter(final ModelColumn<T, ?> column, final String substring, final boolean ignoreCase)
	{
		final String columnName = column.getColumnName();
		final StringLikeFilter<T> filter = new StringLikeFilter<>(columnName, substring, ignoreCase);
		return addFilter(NotQueryFilter.of(filter));
	}

	default RT addCoalesceEqualsFilter(final Object value, final String... columnNames)
	{
		final CoalesceEqualsQueryFilter<T> filter = new CoalesceEqualsQueryFilter<>(value, columnNames);
		return addFilter(filter);
	}

	default RT addNotEqualsFilter(final String columnName, @Nullable final Object value)
	{
		final NotEqualsQueryFilter<T> filter = NotEqualsQueryFilter.of(columnName, value);
		return addFilter(filter);
	}

	default RT addNotEqualsFilter(final ModelColumn<T, ?> column, @Nullable final Object value)
	{
		final String columnName = column.getColumnName();
		return addNotEqualsFilter(columnName, value);
	}

	default RT addIsNull(final String columnName)
	{
		return addEqualsFilter(columnName, null);
	}

	default RT addNotNull(final String columnName)
	{
		return addNotEqualsFilter(columnName, null);
	}

	default RT addNotNull(final ModelColumn<T, ?> column)
	{
		final String columnName = column.getColumnName();
		return addNotEqualsFilter(columnName, null);
	}

	default RT addCompareFilter(final String columnName, final CompareQueryFilter.Operator operator, final @Nullable Object value)
	{
		final CompareQueryFilter<T> filter = new CompareQueryFilter<>(columnName, operator, value);
		return addFilter(filter);
	}

	default RT addCompareFilter(final ModelColumn<T, ?> column, final CompareQueryFilter.Operator operator, final @Nullable Object value)
	{
		final CompareQueryFilter<T> filter = new CompareQueryFilter<>(column.getColumnName(), operator, value);
		return addFilter(filter);
	}

	default RT addCompareFilter(final String columnName, final CompareQueryFilter.Operator operator, final Object value, final IQueryFilterModifier modifier)
	{
		final CompareQueryFilter<T> filter = new CompareQueryFilter<>(columnName, operator, value, modifier);
		return addFilter(filter);
	}

	default RT addCompareFilter(final ModelColumn<T, ?> column, final CompareQueryFilter.Operator operator, final Object value, final IQueryFilterModifier modifier)
	{
		return addCompareFilter(column.getColumnName(), operator, value, modifier);
	}

	default RT addOnlyActiveRecordsFilter()
	{
		final IQueryFilter<T> filter = ActiveRecordQueryFilter.getInstance();
		return addFilter(filter);
	}

	default RT addOnlyContextClient(final Properties ctx)
	{
		final IQueryFilter<T> filter = new ContextClientQueryFilter<>(ctx);
		return addFilter(filter);
	}

	default RT addOnlyContextClientOrSystem(final Properties ctx)
	{
		final boolean includeSystemClient = true;
		final IQueryFilter<T> filter = new ContextClientQueryFilter<>(ctx, includeSystemClient);
		return addFilter(filter);
	}

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is accepted.
	 */
	@SuppressWarnings({ "unchecked", "ConfusingArgumentToVarargsMethod" })
	default <V> RT addInArrayOrAllFilter(final String columnName, final V... values)
	{
		final IQueryFilter<T> filter = new InArrayQueryFilter<T>(columnName, values)
				.setDefaultReturnWhenEmpty(true);
		return addFilter(filter);
	}

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is rejected.
	 * <p>
	 * Note that "InArray*Filters" also support {@link RepoIdAware} and {@link de.metas.util.lang.ReferenceListAwareEnum}
	 */
	@SuppressWarnings({ "unchecked", "ConfusingArgumentToVarargsMethod" })
	default <V> RT addInArrayFilter(final String columnName, final V... values)
	{
		final IQueryFilter<T> filter = new InArrayQueryFilter<T>(columnName, values)
				.setDefaultReturnWhenEmpty(false);
		return addFilter(filter);
	}

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is accepted.
	 */
	@SuppressWarnings({ "unchecked", "ConfusingArgumentToVarargsMethod" })
	default <V> RT addInArrayOrAllFilter(final ModelColumn<T, ?> column, final V... values)
	{
		final IQueryFilter<T> filter = new InArrayQueryFilter<T>(column.getColumnName(), values)
				.setDefaultReturnWhenEmpty(true);
		return addFilter(filter);
	}

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is rejected.
	 *
	 * @param values the values to check again also supports {@code null} value among them.
	 */
	@SuppressWarnings({ "unchecked", "ConfusingArgumentToVarargsMethod" })
	default <V> RT addInArrayFilter(final ModelColumn<T, ?> column, final V... values)
	{
		final IQueryFilter<T> filter = new InArrayQueryFilter<T>(column.getColumnName(), values)
				.setDefaultReturnWhenEmpty(false);
		return addFilter(filter);
	}

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is accepted.
	 */
	default <V> RT addInArrayOrAllFilter(final String columnName, final Collection<V> values)
	{
		final IQueryFilter<T> filter = new InArrayQueryFilter<T>(columnName, values)
				.setDefaultReturnWhenEmpty(true);
		return addFilter(filter);
	}

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is rejected.
	 * Note: also works with {@link RepoIdAware} values.
	 */
	default <V> RT addInArrayFilter(final String columnName, final Collection<V> values)
	{
		final IQueryFilter<T> filter = new InArrayQueryFilter<T>(columnName, values)
				.setDefaultReturnWhenEmpty(false);
		return addFilter(filter);
	}

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is accepted.
	 * Note: also works with {@link RepoIdAware} values.
	 */
	default <V> RT addInArrayOrAllFilter(final ModelColumn<T, ?> column, final Collection<V> values)
	{
		final IQueryFilter<T> filter = new InArrayQueryFilter<T>(column.getColumnName(), values)
				.setDefaultReturnWhenEmpty(true);
		return addFilter(filter);
	}

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is rejected.
	 * Note: also works with {@link RepoIdAware} values.
	 */
	default <V> RT addInArrayFilter(final ModelColumn<T, ?> column, final Collection<V> values)
	{
		final IQueryFilter<T> filter = new InArrayQueryFilter<T>(column.getColumnName(), values)
				.setDefaultReturnWhenEmpty(false);
		return addFilter(filter);
	}

	/**
	 * Notes:
	 * <li>This filter <b>will not</b> match {@code null} column values.</li>
	 * <li>If {@code values} is empty, then this filter will return {@code true} (as intuitively expected).</li>
	 * <li>Also works with {@link RepoIdAware} values.</li>
	 */
	default <V> RT addNotInArrayFilter(final ModelColumn<T, ?> column, final Collection<V> values)
	{
		return addNotInArrayFilter(column.getColumnName(), values);
	}

	/**
	 * NOTE: in case <code>values</code> collection is empty this filter will return <code>true</code> (as intuitively expected).
	 */
	default <V> RT addNotInArrayFilter(final String columnName, final Collection<V> values)
	{
		final InArrayQueryFilter<T> filter = new InArrayQueryFilter<>(columnName, values);

		// NOTE: in case the values collection is empty then
		// InArrayQueryFilter shall return false,
		// so negativing the expression it will "true",
		// which actually is the intuitive result
		// i.e. when there are no values then "not in array" shall return "true".
		filter.setDefaultReturnWhenEmpty(false);
		final IQueryFilter<T> notFilter = NotQueryFilter.of(filter);
		return addFilter(notFilter);
	}

	default <V> RT addInArrayFilter(@NonNull final String columnName, @NonNull final InSetPredicate<V> values)
	{
		addFilter(InArrayQueryFilter.ofInSetPredicate(columnName, values));
		return self();
	}

	default RT addBetweenFilter(final ModelColumn<T, ?> column, final Object valueFrom, final Object valueTo, final IQueryFilterModifier modifier)
	{
		final BetweenQueryFilter<T> filter = new BetweenQueryFilter<>(column, valueFrom, valueTo, modifier);
		return addFilter(filter);
	}

	default RT addBetweenFilter(final String columnName, final Object valueFrom, final Object valueTo, final IQueryFilterModifier modifier)
	{
		final BetweenQueryFilter<T> filter = new BetweenQueryFilter<>(getModelTableName(), columnName, valueFrom, valueTo, modifier);
		return addFilter(filter);
	}

	default RT addBetweenFilter(final ModelColumn<T, ?> column, final Object valueFrom, final Object valueTo)
	{
		final BetweenQueryFilter<T> filter = new BetweenQueryFilter<>(column, valueFrom, valueTo);
		return addFilter(filter);
	}

	default RT addBetweenFilter(final String columnName, final Object valueFrom, final Object valueTo)
	{
		final BetweenQueryFilter<T> filter = new BetweenQueryFilter<>(getModelTableName(), columnName, valueFrom, valueTo);
		return addFilter(filter);
	}

	default RT addEndsWithQueryFilter(final String columnName, final String endsWithString)
	{
		final EndsWithQueryFilter<T> filter = new EndsWithQueryFilter<>(columnName, endsWithString);
		return addFilter(filter);
	}

	default RT addValidFromToMatchesFilter(final ModelColumn<T, ?> validFromColumn, final ModelColumn<T, ?> validToColumn, final Date dateToMatch)
	{
		final ValidFromToMatchesQueryFilter<T> filter = new ValidFromToMatchesQueryFilter<>(validFromColumn, validToColumn, dateToMatch);
		return addFilter(filter);
	}

	default RT addInRange(@NonNull final String columnName, @NonNull final Range<Instant> range)
	{
		return addFilter(InstantRangeQueryFilter.of(columnName, range));
	}

	default IInSubQueryFilterClause<T, RT> addInSubQueryFilter()
	{
		return new InSubQueryFilterClause<>(getModelTableName(), self(), this::addFilter);
	}

	/**
	 * @param columnName         the key column from the "main" query
	 * @param subQueryColumnName the key column from the "sub" query
	 * @param subQuery           the actual sub query
	 * @return this
	 */
	default <ST> RT addInSubQueryFilter(final String columnName, final String subQueryColumnName, final IQuery<ST> subQuery)
	{
		return addFilter(InSubQueryFilter.<T>builder()
				.tableName(getModelTableName())
				.subQuery(subQuery)
				.matchingColumnNames(columnName, subQueryColumnName)
				.build());
	}

	default <ST> RT addNotInSubQueryFilter(final String columnName, final String subQueryColumnName, final IQuery<ST> subQuery)
	{
		final IQueryFilter<T> filter = InSubQueryFilter.<T>builder()
				.tableName(getModelTableName())
				.subQuery(subQuery)
				.matchingColumnNames(columnName, subQueryColumnName)
				.build();
		final IQueryFilter<T> notFilter = NotQueryFilter.of(filter);
		return addFilter(notFilter);
	}

	/**
	 * @param column         the key column from the "main" query
	 * @param subQueryColumn the key column from the "sub" query
	 * @param subQuery       the actual sub query
	 * @return this
	 */
	default <ST> RT addInSubQueryFilter(final ModelColumn<T, ?> column, final ModelColumn<ST, ?> subQueryColumn, final IQuery<ST> subQuery)
	{
		return addFilter(InSubQueryFilter.<T>builder()
				.tableName(getModelTableName())
				.subQuery(subQuery)
				.matchingColumnNames(column.getColumnName(), subQueryColumn.getColumnName())
				.build());
	}

	default <ST> RT addNotInSubQueryFilter(final ModelColumn<T, ?> column, final ModelColumn<ST, ?> subQueryColumn, final IQuery<ST> subQuery)
	{
		final IQueryFilter<T> filter = InSubQueryFilter.<T>builder()
				.tableName(getModelTableName())
				.subQuery(subQuery)
				.matchingColumnNames(column.getColumnName(), subQueryColumn.getColumnName())
				.build();

		final IQueryFilter<T> notFilter = NotQueryFilter.of(filter);
		return addFilter(notFilter);
	}

	default RT addIntervalIntersection(
			@NonNull final String lowerBoundColumnName,
			@NonNull final String upperBoundColumnName,
			@Nullable final ZonedDateTime lowerBoundValue,
			@Nullable final ZonedDateTime upperBoundValue)
	{
		addIntervalIntersection(
				lowerBoundColumnName,
				upperBoundColumnName,
				lowerBoundValue != null ? lowerBoundValue.toInstant() : null,
				upperBoundValue != null ? upperBoundValue.toInstant() : null);

		return self();
	}

	default RT addIntervalIntersection(
			@NonNull final String lowerBoundColumnName,
			@NonNull final String upperBoundColumnName,
			@Nullable final Instant lowerBoundValue,
			@Nullable final Instant upperBoundValue)
	{
		addFilter(new DateIntervalIntersectionQueryFilter<>(
				ModelColumnNameValue.forColumnName(lowerBoundColumnName),
				ModelColumnNameValue.forColumnName(upperBoundColumnName),
				lowerBoundValue,
				upperBoundValue));
		return self();
	}
}
