package de.metas.costing.interceptors;

import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingLevel;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Cost;
import org.compiere.model.ModelValidator;

import java.math.BigDecimal;

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

@Interceptor(I_M_Cost.class)
class M_Cost
{
	private final ICostElementRepository costElementRepository;
	private final IProductCostingBL productCostingBL;

	public M_Cost(
			@NonNull final ICostElementRepository costElementRepository,
			@NonNull final IProductCostingBL productCostingBL)
	{
		this.costElementRepository = costElementRepository;
		this.productCostingBL = productCostingBL;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_M_Cost costRecord)
	{
		final CostElementId costElementId = CostElementId.ofRepoId(costRecord.getM_CostElement_ID());
		final CostElement costElement = costElementRepository.getByIdIfExists(costElementId).orElse(null);
		final boolean userEntry = InterfaceWrapperHelper.isUIAction(costRecord);

		// Check if data entry makes sense
		if (userEntry)
		{
			final ProductId productId = ProductId.ofRepoId(costRecord.getM_Product_ID());
			final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(costRecord.getC_AcctSchema_ID());
			final CostingLevel costingLevel = productCostingBL.getCostingLevel(productId, acctSchemaId);
			final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(costRecord.getM_AttributeSetInstance_ID());
			if (CostingLevel.Client.equals(costingLevel))
			{
				final OrgId orgId = OrgId.ofRepoIdOrAny(costRecord.getAD_Org_ID());
				if (orgId.isRegular() || asiId.isRegular())
				{
					throw new AdempiereException("@CostingLevelClient@");
				}
			}
			else if (CostingLevel.BatchLot.equals(costingLevel))
			{
				if (asiId.isNone() && costElement != null && costElement.isMaterial())
				{
					throw new FillMandatoryException(I_M_Cost.COLUMNNAME_M_AttributeSetInstance_ID);
				}
				costRecord.setAD_Org_ID(OrgId.ANY.getRepoId());
			}
		}

		// Cannot enter calculated
		if (userEntry && costElement != null && !costElement.isAllowUserChangingCurrentCosts())
		{
			throw new AdempiereException("@IsCalculated@");
		}

		// Percentage
		if (costElement != null
				&& (!costElement.isAllowUserChangingCurrentCosts() || costElement.isMaterial())
				&& costRecord.getPercent() != 0)
		{
			costRecord.setPercent(0);
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
