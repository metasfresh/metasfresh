package org.adempiere.ad.validationRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Abstract Java Validation Rule be the extension point for all Java Based Validation Rules.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03271:_Extend_the_ValidationRule_feature_%282012091210000027%29
 */
public abstract class AbstractJavaValidationRule implements IValidationRule, INamePairPredicate
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	private static List<ValueNamePair> exceptionTableAndColumns = new ArrayList<>();

	/**
	 * A Java Validation rule shall never provide a pre-filter where clause.
	 * (design decision)
	 */
	@Override
	public final IStringExpression getPrefilterWhereClause()
	{
		return IStringExpression.NULL;
	}

	@Override
	public final Set<String> getAllParameters()
	{
		return getPostQueryFilter().getParameters();
	}

	@Override
	public final INamePairPredicate getPostQueryFilter()
	{
		return this;
	}

	@Override
	public List<ValueNamePair> getExceptionTableAndColumns()
	{

		return exceptionTableAndColumns;
	}

	@Override
	public void registerException(
			@NonNull final String tableName,
			@NonNull final String columnName,
			@Nullable final String description)
	{
		final ValueNamePair exception = new ValueNamePair(tableName, columnName, description);

		exceptionTableAndColumns.add(exception);
	}

	// NOTE: better enforce developer to define this method
	@Override
	public abstract Set<String> getParameters();

	@Override
	public abstract boolean accept(final IValidationContext evalCtx, final NamePair item);
}
