package de.metas.costing.interceptors;

import java.math.BigDecimal;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Cost;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.costing.CostElement;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingLevel;
import de.metas.costing.ICostElementRepository;
import de.metas.product.IProductBL;

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
@Interceptor(I_M_Cost.class)
public class M_Cost
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_M_Cost costRecord)
	{
		final CostElement ce = Adempiere.getBean(ICostElementRepository.class).getById(costRecord.getM_CostElement_ID());
		final boolean userEntry = InterfaceWrapperHelper.isUIAction(costRecord);

		// Check if data entry makes sense
		if (userEntry)
		{
			final CostingLevel costingLevel = Services.get(IProductBL.class).getCostingLevel(costRecord.getM_Product_ID(), costRecord.getC_AcctSchema_ID());
			if (CostingLevel.Client.equals(costingLevel))
			{
				if (costRecord.getAD_Org_ID() > 0 || costRecord.getM_AttributeSetInstance_ID() > 0)
				{
					throw new AdempiereException("@CostingLevelClient@");
				}
			}
			else if (CostingLevel.BatchLot.equals(costingLevel))
			{
				if (costRecord.getM_AttributeSetInstance_ID() <= 0 && ce.isMaterialCostingMethod())
				{
					throw new FillMandatoryException(I_M_Cost.COLUMNNAME_M_AttributeSetInstance_ID);
				}
				if (costRecord.getAD_Org_ID() != 0)
				{
					costRecord.setAD_Org_ID(0);
				}
			}
		}

		// Cannot enter calculated
		if (userEntry && ce != null && ce.isCalculated())
		{
			throw new AdempiereException("@IsCalculated@");
		}

		// Percentage
		if (ce != null)
		{
			if ((ce.isCalculated() || CostElementType.Material.equals(ce.getCostElementType())) && costRecord.getPercent() != 0)
			{
				costRecord.setPercent(0);
			}
		}
		if (costRecord.getPercent() != 0)
		{
			costRecord.setCurrentCostPrice(BigDecimal.ZERO);
			costRecord.setFutureCostPrice(BigDecimal.ZERO);
			costRecord.setCumulatedAmt(BigDecimal.ZERO);
			costRecord.setCumulatedQty(BigDecimal.ZERO);
		}
	}
}
