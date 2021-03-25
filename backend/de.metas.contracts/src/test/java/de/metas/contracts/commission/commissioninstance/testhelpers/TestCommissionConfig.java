package de.metas.contracts.commission.commissioninstance.testhelpers;

import static de.metas.util.Check.isEmpty;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.HashMap;
import java.util.List;

import de.metas.organization.OrgId;
import org.adempiere.util.lang.IPair;
import org.compiere.model.I_C_BPartner;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionSettingsLineId;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfigId;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
public class TestCommissionConfig
{
	@NonNull
	OrgId orgId;
	
	@Default
	int pointsPrecision = 2;

	@NonNull
	ProductId commissionProductId;

	@NonNull
	Boolean subtractLowerLevelCommissionFromBase;

	@NonNull
	@Default
	Boolean createShareForOwnRevenue = false;

	@Singular
	List<TestCommissionContract> contractTestRecords;

	@Singular
	List<TestCommissionConfigLine> configLineTestRecords;

	public ConfigData createConfigData()
	{
		final I_C_HierarchyCommissionSettings settingsRecord = newInstance(I_C_HierarchyCommissionSettings.class);
		settingsRecord.setAD_Org_ID(OrgId.toRepoId(orgId));
		settingsRecord.setPointsPrecision(pointsPrecision);
		settingsRecord.setCommission_Product_ID(commissionProductId.getRepoId());
		settingsRecord.setIsSubtractLowerLevelCommissionFromBase(subtractLowerLevelCommissionFromBase);
		settingsRecord.setIsCreateShareForOwnRevenue(createShareForOwnRevenue);
		saveRecord(settingsRecord);

		final I_C_Flatrate_Conditions conditionsRecord = newInstance(I_C_Flatrate_Conditions.class);
		conditionsRecord.setAD_Org_ID(OrgId.toRepoId(orgId));
		conditionsRecord.setC_HierarchyCommissionSettings_ID(settingsRecord.getC_HierarchyCommissionSettings_ID());
		conditionsRecord.setDocStatus(IDocument.STATUS_Completed);
		saveRecord(conditionsRecord);

		final ImmutableMap.Builder<String, CommissionSettingsLineId> name2CommissionSettingsLineId = ImmutableMap.builder();
		for (final TestCommissionConfigLine configLineTestRecord : configLineTestRecords)
		{
			final IPair<String, CommissionSettingsLineId> configLineResult = configLineTestRecord.createConfigLineData(orgId, settingsRecord.getC_HierarchyCommissionSettings_ID());
			name2CommissionSettingsLineId.put(configLineResult.getLeft(), configLineResult.getRight());
		}

		// create sales res and contracts
		final ImmutableMap.Builder<BPartnerId, FlatrateTermId> bpartnerId2flatrateTermId = ImmutableMap.builder();
		final ImmutableMap.Builder<String, BPartnerId> name2bpartnerId = ImmutableMap.builder();

		final HashMap<String, I_C_BPartner> name2bpartnerRecord = new HashMap<>(); // used just locally in this method
		for (final TestCommissionContract contractTestRecord : contractTestRecords)
		{
			final I_C_Flatrate_Term termRecord = contractTestRecord.createContractData(
					orgId,
					conditionsRecord.getC_Flatrate_Conditions_ID(),
					commissionProductId);
			bpartnerId2flatrateTermId.put(
					BPartnerId.ofRepoId(termRecord.getBill_BPartner_ID()),
					FlatrateTermId.ofRepoId(termRecord.getC_Flatrate_Term_ID()));

			name2bpartnerRecord.put(contractTestRecord.getSalesRepName(), termRecord.getBill_BPartner());
			name2bpartnerId.put(contractTestRecord.getSalesRepName(), BPartnerId.ofRepoId(termRecord.getBill_BPartner_ID()));
		}

		// link sales reps into their hierarchy
		for (final TestCommissionContract contractTestRecord : contractTestRecords)
		{
			if (!isEmpty(contractTestRecord.getParentSalesRepName(), true))
			{
				final I_C_BPartner child = name2bpartnerRecord.get(contractTestRecord.getSalesRepName());
				final I_C_BPartner parent = name2bpartnerRecord.get(contractTestRecord.getParentSalesRepName());
				child.setC_BPartner_SalesRep_ID(parent.getC_BPartner_ID());
				saveRecord(child);
			}
		}

		return new ConfigData(
				orgId,
				HierarchyConfigId.ofRepoId(settingsRecord.getC_HierarchyCommissionSettings_ID()),
				bpartnerId2flatrateTermId.build(),
				name2bpartnerId.build(),
				name2CommissionSettingsLineId.build());
	}

	@Value
	public static class ConfigData
	{
		@NonNull OrgId orgId;
		HierarchyConfigId hierarchyConfigId;
		ImmutableMap<BPartnerId, FlatrateTermId> bpartnerId2FlatrateTermId;
		ImmutableMap<String, BPartnerId> name2BPartnerId;
		ImmutableMap<String, CommissionSettingsLineId> name2CommissionSettingsLineId;
	}
}
