package de.metas.acct.interceptor;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostType;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_AcctSchema;
import org.compiere.util.Env;

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
	private final ICostElementRepository costElementRepo;
	private final ICurrentCostsRepository currentCostsRepository;

	private final static AdMessageKey MSG_ACCT_SCHEMA_HAS_ASSOCIATED_COSTS = AdMessageKey.of("de.metas.acct.AcctSchema.hasCosts");

	public C_AcctSchema(
			@NonNull final ICostElementRepository costElementRepo,
			@NonNull final ICurrentCostsRepository currentCostsRepository)
	{
		this.costElementRepo = costElementRepo;
		this.currentCostsRepository = currentCostsRepository;
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

		// NOTE: allow having only org restriction for primary accounting schema too.
		// if (acctSchema.getAD_OrgOnly_ID() > 0 && isPrimaryAcctSchema(acctSchema))
		// {
		// 	acctSchema.setAD_OrgOnly_ID(OrgId.ANY.getRepoId());
		// }

		//
		checkCosting(acctSchema);
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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_AcctSchema.COLUMNNAME_C_Currency_ID)
	public void checkCurrency(final I_C_AcctSchema acctSchema)
	{
		final PO po = InterfaceWrapperHelper.getPO(acctSchema);

		final CurrencyId previousCurrencyId = CurrencyId.ofRepoIdOrNull(po.get_ValueOldAsInt(I_C_AcctSchema.COLUMNNAME_C_Currency_ID));

		if (previousCurrencyId != null && currentCostsRepository.hasCostsInCurrency(AcctSchemaId.ofRepoId(acctSchema.getC_AcctSchema_ID()), previousCurrencyId))
		{
			throw new AdempiereException(MSG_ACCT_SCHEMA_HAS_ASSOCIATED_COSTS);
		}
	}
}
