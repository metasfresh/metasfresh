package org.adempiere.ad.validationRule;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.ValueNamePair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import lombok.NonNull;

/**
 * Lookup Validation Rule Model
 *
 * NOTE to developer: all implementations shall be <b>stateless</b>.
 *
 * @author tsa
 * Task http://dewiki908/mediawiki/index.php/03271:_Extend_the_ValidationRule_feature_%282012091210000027%29
 *
 */
public interface IValidationRule
{
	int AD_Val_Rule_ID_Null = -1;

	/**
	 * Indicates that the validation rule always returns the same result when given the same argument values; that is, it does not do database lookups or otherwise use information not directly present
	 * in its argument list.
	 *
	 * @return true if the validation rule is immutable
	 */
	default boolean isImmutable()
	{
		return getPrefilterWhereClause().getParameterNames().isEmpty()
				&& getPostQueryFilter().getParameters().isEmpty();
	}

	/**
	 * Returns a set of parameters on which this validation rule depends.
	 *
	 * Actually it's a concatenation of:
	 * <ul>
	 * <li>{@link #getPrefilterWhereClause()}'s parameters
	 * <li>{@link #getPostQueryFilter()}'s parameters
	 * </ul>
	 *
	 * It is assumed that the parameters set is static and not change over time.
	 *
	 * @return set of parameter names
	 */
	Set<String> getAllParameters();

	/**
	 * @return SQL to be used for prefiltering data
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
	 *
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
