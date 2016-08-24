package org.adempiere.ad.validationRule;

import org.compiere.util.NamePair;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Abstract Java Validation Rule be the extension point for all Java Based Validation Rules.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03271:_Extend_the_ValidationRule_feature_%282012091210000027%29
 */
public abstract class AbstractJavaValidationRule implements IValidationRule
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	/**
	 * A Java Validation rule shall never provide a pre-filter where clause.
	 * (design decision)
	 */
	@Override
	public final String getPrefilterWhereClause(final IValidationContext evalCtx)
	{
		return null;
	}

	// NOTE: better enforce developer to define this method
	// @Override
	// public List<String> getParameters()
	// {
	// return ImmutableList.of();
	// }

	@Override
	public NamePair getValidValue(final Object currentValue)
	{
		// By default, the "valid" value is null. Will be overwritten in specific rules if necessary.
		return null;
	}

}
