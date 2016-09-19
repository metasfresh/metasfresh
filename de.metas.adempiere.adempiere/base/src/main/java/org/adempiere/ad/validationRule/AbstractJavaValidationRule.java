package org.adempiere.ad.validationRule;

import java.util.Set;

import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.NamePair;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Abstract Java Validation Rule be the extension point for all Java Based Validation Rules.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03271:_Extend_the_ValidationRule_feature_%282012091210000027%29
 */
public abstract class AbstractJavaValidationRule implements IValidationRule, INamePairPredicate
{
	protected final transient Logger logger = LogManager.getLogger(getClass());
	
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

	// NOTE: better enforce developer to define this method
	 @Override
	 public abstract Set<String> getParameters();
	

	@Override
	public abstract boolean accept(final IValidationContext evalCtx, final NamePair item);
}
