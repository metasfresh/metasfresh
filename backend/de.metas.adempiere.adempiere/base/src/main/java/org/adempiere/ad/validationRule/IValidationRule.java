package org.adempiere.ad.validationRule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.ValueNamePair;

import java.util.List;
import java.util.Set;

/**
 * Lookup Validation Rule Model
 *
 * @implSpec NOTE to developer: all implementations shall be <b>stateless</b>.
 */
public interface IValidationRule
{
	/**
	 * Indicates that the validation rule always returns the same result when given the same argument values; that is, it does not do database lookups or otherwise use information not directly present
	 * in its argument list.
	 *
	 * @return true if the validation rule is immutable
	 */
	default boolean isImmutable()
	{
		return getPrefilterWhereClause().getParameterNames().isEmpty()
				&& getPostQueryFilter().getParameters(null).isEmpty();
	}

	/**
	 * Returns a set of parameters on which this validation rule depends.
	 * <p>
	 * Actually it's a concatenation of:
	 * <ul>
	 * <li>{@link #getPrefilterWhereClause()}'s parameters
	 * <li>{@link #getPostQueryFilter()}'s parameters
	 * </ul>
	 * <p>
	 * It is assumed that the parameters set is static and not change over time.
	 *
	 * @return set of parameter names
	 */
	Set<String> getAllParameters();

	/**
	 * @return SQL to be used for pre-filtering data
	 */
	IStringExpression getPrefilterWhereClause();

	/**
	 * @return filter to be applied after query; or {@link NamePairPredicates#ACCEPT_ALL}
	 */
	default INamePairPredicate getPostQueryFilter()
	{
		return NamePairPredicates.ACCEPT_ALL;
	}

	/**
	 * Specify for which table and column pair this validation rule shall not apply
	 *
	 * @param description may be null
	 */
	default void registerException(@NonNull final String tableName, @NonNull final String columnName, final String description)
	{
		throw new UnsupportedOperationException("Class " + getClass().getName() + " does not support registering exceptions");
	}

	/**
	 * @return a list containing all the table+column pairs for which this validation rule shall not be applied
	 */
	default List<ValueNamePair> getExceptionTableAndColumns()
	{
		return ImmutableList.of();
	}

	default Set<String> getDependsOnTableNames()
	{
		return ImmutableSet.of();
	}
}
