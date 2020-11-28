package de.metas.acct.model.validator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostType;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_AcctSchema;
import org.compiere.util.Env;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.organization.OrgId;
import lombok.NonNull;

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

@Interceptor(I_C_AcctSchema.class)
public class C_AcctSchema
{
	private final IAcctSchemaDAO acctSchemaDAO;
	private final ICostElementRepository costElementRepo;

	public C_AcctSchema(
			@NonNull final IAcctSchemaDAO acctSchemaDAO,
			@NonNull final ICostElementRepository costElementRepo)
	{
		this.acctSchemaDAO = acctSchemaDAO;
		this.costElementRepo = costElementRepo;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_AcctSchema acctSchema)
	{
		acctSchema.setAD_Org_ID(OrgId.ANY.getRepoId());

		if (acctSchema.getTaxCorrectionType() == null)
		{
			final TaxCorrectionType taxCorrectionType = acctSchema.isDiscountCorrectsTax() ? TaxCorrectionType.WRITEOFF_AND_DISCOUNT : TaxCorrectionType.NONE;
			acctSchema.setTaxCorrectionType(taxCorrectionType.getCode());
		}

		if (acctSchema.getGAAP() == null)
		{
			acctSchema.setGAAP(X_C_AcctSchema.GAAP_InternationalGAAP);
		}

		// Primary accounting schema shall not restrict to a particular Organization
		if (acctSchema.getAD_OrgOnly_ID() > 0 && isPrimaryAcctSchema(acctSchema))
		{
			acctSchema.setAD_OrgOnly_ID(OrgId.ANY.getRepoId());
		}

		//
		checkCosting(acctSchema);
	}

	private boolean isPrimaryAcctSchema(final I_C_AcctSchema acctSchema)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoIdOrNull(acctSchema.getC_AcctSchema_ID());

		if (acctSchemaId == null)
		{
			// New schema is not primary
			return false;
		}

		final ClientId clientId = ClientId.ofRepoId(acctSchema.getAD_Client_ID());
		final AcctSchemaId primaryAcctSchemaId = acctSchemaDAO.getPrimaryAcctSchemaId(clientId);
		if (primaryAcctSchemaId == null)
		{
			// no primary schema defined
			return false;
		}

		return primaryAcctSchemaId.equals(acctSchemaId);
	}

	/**
	 * Check Costing Setup.
	 * Make sure that there is a Cost Type and Cost Element
	 */
	private void checkCosting(final I_C_AcctSchema acctSchema)
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
		final ClientId clientId = ClientId.ofRepoId(acctSchema.getAD_Client_ID());
		costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.ofNullableCode(acctSchema.getCostingMethod()));

		// Default Costing Level
		if (acctSchema.getCostingLevel() == null)
		{
			acctSchema.setCostingLevel(CostingLevel.Client.getCode());
		}
		if (acctSchema.getCostingMethod() == null)
		{
			acctSchema.setCostingMethod(CostingMethod.StandardCosting.getCode());
		}
	}
}
