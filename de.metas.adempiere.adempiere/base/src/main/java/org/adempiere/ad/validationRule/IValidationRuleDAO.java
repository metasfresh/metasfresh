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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Set;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Val_Rule;

/**
 * DAO methods for retrieving {@link I_AD_Val_Rule}s
 * 
 * @author tsa
 * 
 */
public interface IValidationRuleDAO extends ISingletonService
{
	I_AD_Val_Rule retriveValRule(int adValRuleId);

	int retrieveValRuleIdByColumnName(String tableName, String columnName);

	/**
	 * Retrieve child/included validation rules
	 * 
	 * @param parentValRuleId
	 * @return included validation rules
	 */
	List<I_AD_Val_Rule> retrieveChildValRules(int parentValRuleId);

	Set<String> retrieveValRuleDependsOnTableNames(int valRuleId);

}
