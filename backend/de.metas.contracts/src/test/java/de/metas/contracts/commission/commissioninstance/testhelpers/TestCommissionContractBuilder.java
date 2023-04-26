/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.commissioninstance.testhelpers;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsId;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsId;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.experimental.UtilityClass;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@UtilityClass
public class TestCommissionContractBuilder
{
	@Builder(builderMethodName = "commissionContractBuilder")
	private I_C_Flatrate_Term createCommissionContract(
			final BPartnerId contractBPartnerId,
			final ProductId commissionProductId,
			final OrgId orgId,
			final TypeConditions typeConditions,
			final CustomerTradeMarginId marginConfigId,
			final MediatedCommissionSettingsId mediatedCommissionSettingsId,
			final LicenseFeeSettingsId licenseFeeSettingsId)
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setAD_Org_ID(orgId.getRepoId());
		conditions.setIsActive(true);

		if (marginConfigId != null)
		{
			conditions.setC_Customer_Trade_Margin_ID(marginConfigId.getRepoId());
		}
		else if (mediatedCommissionSettingsId != null)
		{
			conditions.setC_MediatedCommissionSettings_ID(mediatedCommissionSettingsId.getRepoId());
		}
		else if (licenseFeeSettingsId != null)
		{
			conditions.setC_LicenseFeeSettings_ID(LicenseFeeSettingsId.toRepoId(licenseFeeSettingsId));
		}

		saveRecord(conditions);

		final I_C_Flatrate_Term contract = newInstance(I_C_Flatrate_Term.class);
		contract.setBill_BPartner_ID(contractBPartnerId.getRepoId());
		contract.setAD_Org_ID(orgId.getRepoId());
		contract.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		contract.setType_Conditions(typeConditions.getCode());
		contract.setM_Product_ID(commissionProductId.getRepoId());
		contract.setIsActive(true);
		saveRecord(contract);

		return contract;
	}
}
