package de.metas.costing.interceptors;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostType;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
@Interceptor(I_C_AcctSchema.class)
public class C_AcctSchema
{
	/**
	 * Check Costing Setup.
	 * Make sure that there is a Cost Type and Cost Element
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void checkCosting(final I_C_AcctSchema acctSchema)
	{
		// Create Cost Type
		if (acctSchema.getM_CostType_ID() <= 0)
		{
			final I_M_CostType costType = InterfaceWrapperHelper.newInstance(I_M_CostType.class);
			costType.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);
			costType.setName(acctSchema.getName());
			InterfaceWrapperHelper.save(costType);
			acctSchema.setM_CostType_ID(costType.getM_CostType_ID());
		}

		// Create Cost Elements
		Services.get(ICostElementRepository.class).getOrCreateMaterialCostElement(acctSchema.getAD_Client_ID(), CostingMethod.ofNullableCode(acctSchema.getCostingMethod()));

		// Default Costing Level
		if (acctSchema.getCostingLevel() == null)
			acctSchema.setCostingLevel(CostingLevel.Client.getCode());
		if (acctSchema.getCostingMethod() == null)
			acctSchema.setCostingMethod(CostingMethod.StandardCosting.getCode());
	}	// checkCosting

}
