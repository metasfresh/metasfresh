package org.adempiere.ad.validationRule;

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


import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.NamePair;


/**
 * Abstract Java Validation Rule be the extension point for all Java Based Validation Rules.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03271:_Extend_the_ValidationRule_feature_%282012091210000027%29
 */
public abstract class AbstractJavaValidationRule implements IValidationRule
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public boolean isValidationRequired(IValidationContext evalCtx)
	{
		return true;
	}

	/**
	 * A Java Validation rule shall never provide a pre-filter where clause.
	 * (design decision)
	 */
	@Override
	public final String getPrefilterWhereClause(IValidationContext evalCtx)
	{
		return null;
	}

	@Override
	public List<String> getParameters(IValidationContext evalCtx)
	{
		return Collections.emptyList();
	}
	
	@Override
	public NamePair getValidValue(Object currentValue)
	{
		// By default, the "valid" value is null. Will be overwritten in specific rules if necessary.
		return null;
	}
	
}
