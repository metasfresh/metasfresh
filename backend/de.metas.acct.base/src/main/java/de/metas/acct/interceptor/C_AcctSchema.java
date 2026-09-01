package de.metas.acct.interceptor;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostType;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_AcctSchema;
import org.compiere.util.Env;
import org.slf4j.Logger;

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
	private static final Logger logger = LogManager.getLogger(C_AcctSchema.class);
	
	private final ICostElementRepository costElementRepo;
	private final ICurrentCostsRepository currentCostsRepository;
	private final ICostDetailService costDetailService;

	private final static AdMessageKey MSG_ACCT_SCHEMA_HAS_ASSOCIATED_COSTS = AdMessageKey.of("de.metas.acct.AcctSchema.hasCosts");
	private final static AdMessageKey MSG_ACCT_SCHEMA_COSTING_METHOD_NOT_SEEDED = AdMessageKey.of("ERR_ACCTSCHEMA_COSTING_METHOD_NOT_SEEDED");
	
	public static final ModelDynAttributeAccessor<I_C_AcctSchema, Boolean> DISABLE_CHECK_CURRENCY = new ModelDynAttributeAccessor<>("DISABLE_CHECK_CURRENCY", Boolean.class);

	/**
	 * Opt-out for {@link #assertTargetCostingMethodIsSeeded(I_C_AcctSchema)}, for callers that set the
	 * costing method as pure SETUP - i.e. while the schema has no cost history to lose yet, so there is
	 * nothing the guard could protect. Same shape and purpose as {@link #DISABLE_CHECK_CURRENCY}.
	 * Never set it on the interactive path: that is the path the guard exists for.
	 */
	public static final ModelDynAttributeAccessor<I_C_AcctSchema, Boolean> DISABLE_CHECK_COSTING_METHOD_SEEDED = new ModelDynAttributeAccessor<>("DISABLE_CHECK_COSTING_METHOD_SEEDED", Boolean.class);

	public C_AcctSchema(
			@NonNull final ICostElementRepository costElementRepo,
			@NonNull final ICurrentCostsRepository currentCostsRepository,
			@NonNull final ICostDetailService costDetailService)
	{
		this.costElementRepo = costElementRepo;
		this.currentCostsRepository = currentCostsRepository;
		this.costDetailService = costDetailService;
	}

	/**
	 * Refuses a CostingMethod switch while the target method has zero {@code M_CostDetail} rows in this schema;
	 * {@link #MSG_ACCT_SCHEMA_COSTING_METHOD_NOT_SEEDED} carries the remediation. Does not check per-product coverage.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_AcctSchema.COLUMNNAME_CostingMethod)
	public void assertTargetCostingMethodIsSeeded(@NonNull final I_C_AcctSchema acctSchema)
	{
		if (DISABLE_CHECK_COSTING_METHOD_SEEDED.getValue(acctSchema, Boolean.FALSE))
		{
			logger.debug("Skip costing-method seeding check for {} because of dynamic attribute {}", acctSchema, DISABLE_CHECK_COSTING_METHOD_SEEDED);
			return;
		}

		final CostingMethod targetCostingMethod = CostingMethod.ofNullableCode(acctSchema.getCostingMethod());
		if (targetCostingMethod == null)
		{
			return;
		}

		final ClientId clientId = ClientId.ofRepoId(acctSchema.getAD_Client_ID());
		final CostElementId targetCostElementId = costElementRepo.getActiveMaterialCostingElements(clientId)
				.stream()
				.filter(costElement -> costElement.isMaterialCostingMethod(targetCostingMethod))
				.findFirst()
				.map(CostElement::getId)
				.orElse(null);

		final boolean seeded = targetCostElementId != null
				&& costDetailService.hasCostDetails(CostDetailQuery.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(acctSchema.getC_AcctSchema_ID()))
				.costElementId(targetCostElementId)
				.build());

		if (!seeded)
		{
			throw new AdempiereException(MSG_ACCT_SCHEMA_COSTING_METHOD_NOT_SEEDED, targetCostingMethod.getCode());
		}
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
		if(DISABLE_CHECK_CURRENCY.getValue(acctSchema, Boolean.FALSE))
		{
			logger.debug("Skip currency check for {} because of dynamic attribute {}", acctSchema, DISABLE_CHECK_CURRENCY);
			return;
		}
		
		final PO po = InterfaceWrapperHelper.getPO(acctSchema);

		final CurrencyId previousCurrencyId = CurrencyId.ofRepoIdOrNull(po.get_ValueOldAsInt(I_C_AcctSchema.COLUMNNAME_C_Currency_ID));

		if (previousCurrencyId != null && currentCostsRepository.hasCostsInCurrency(AcctSchemaId.ofRepoId(acctSchema.getC_AcctSchema_ID()), previousCurrencyId))
		{
			throw new AdempiereException(MSG_ACCT_SCHEMA_HAS_ASSOCIATED_COSTS);
		}
	}
}
