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


import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Evaluatee;

/**
 * Factory class used to create {@link IValidationRule} instances
 * 
 * @author tsa
 * 
 * @task http://dewiki908/mediawiki/index.php/03271:_Extend_the_ValidationRule_feature_%282012091210000027%29
 */
public interface IValidationRuleFactory extends ISingletonService
{

	/**
	 * Create {@link IValidationRule} for given AD_ValRule_ID
	 * 
	 * @param ctx
	 * @param tableName
	 * @param adValRuleId
	 * @return
	 */
	IValidationRule create(String tableName, int adValRuleId);

	/**
	 * Create SQL {@link IValidationRule} for given whereClause
	 * 
	 * @param whereClause
	 * @return
	 */
	IValidationRule createSQLValidationRule(String whereClause);

	/**
	 * Registers a table-wide validation rule.
	 * 
	 * Each time we retrieve the validation rules for a column, if there are some table validation rules registered for target table, those rules will be composed too.
	 * 
	 * E.g. If we register a validation rule for table "C_BPartner_Location" and we try to retrieve the validation rules for C_Order.Bill_Location_ID, our registered rule will be composed too.
	 * 
	 * @param tableName
	 * @param rule
	 */
	void registerTableValidationRule(String tableName, IValidationRule rule);

	IValidationContext createValidationContext(Properties ctx, int windowNo, int tabNo, String tableName);

	IValidationContext createValidationContext(Properties ctx, String tableName, GridTab gridTab, int rowIndex);

	/**
	 * Creates validation context for given <code>gridField</code>.
	 * 
	 * If gridField is not a lookup then {@link IValidationContext#NULL} is returned.
	 * 
	 * @param gridField
	 * @return validation context or {@link IValidationContext#NULL}
	 */
	IValidationContext createValidationContext(GridField gridField);

	IValidationContext createValidationContext(GridField gridField, int rowIndex);

	IValidationContext createValidationContext(Evaluatee evaluatee);
}
