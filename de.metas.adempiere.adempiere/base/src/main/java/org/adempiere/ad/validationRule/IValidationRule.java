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


import java.util.List;

import org.compiere.util.NamePair;

/**
 * Lookup Validation Rule Model
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03271:_Extend_the_ValidationRule_feature_%282012091210000027%29
 * 
 */
public interface IValidationRule
{
	String WHERECLAUSE_ERROR = "1=0";
	String VALIDATIONCODE_Null = "";
	int AD_Val_Rule_ID_Null = -1;

	/**
	 * Indicates that the validation rule always returns the same result when given the same argument values; that is, it does not do database lookups or otherwise use information not directly present
	 * in its argument list.
	 * 
	 * @return true if the validation rule is immutable
	 */
	boolean isImmutable();

	/**
	 * Returns a SQL where clause which is already parsed based on given context
	 * 
	 * @param evalCtx
	 * @param windowNo
	 * @return SQL where clause or {@link #WHERECLAUSE_ERROR} on error
	 */
	String getPrefilterWhereClause(IValidationContext evalCtx);

	/**
	 * Returns a list of parameters on which this validation rule depends.
	 * 
	 * It is assumed that the parameters list is static and not change over time.
	 * 
	 * @return list of parameter names
	 */
	List<String> getParameters();

	/**
	 * 
	 * @param tableName
	 * @param item
	 * @return true if the item is accepted in the lookup list
	 */
	boolean accept(IValidationContext evalCtx, NamePair item);

	/**
	 * Returns the valid value of a column. It returns null by default. If another logic is required, overwrite it in classes extending {@link AbstractJavaValidationRule}.
	 * 
	 * @param currentValue Current value of the field to be changed.
	 * @return
	 */
	NamePair getValidValue(Object currentValue);
}
