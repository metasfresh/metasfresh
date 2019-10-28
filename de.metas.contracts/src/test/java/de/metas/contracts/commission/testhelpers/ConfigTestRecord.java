package de.metas.contracts.commission.testhelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.model.I_C_Flatrate_Term;
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
public class ConfigTestRecord
{
	@Default
	int pointsPrecision = 2;

	@NonNull
	Boolean subtractLowerLevelCommissionFromBase;

	@Singular
	List<ContractTestRecord> contractTestRecords;

	@Singular
	List<ConfigLineTestRecord> configLineTestRecords;

	public ImmutableMap<BPartnerId, FlatrateTermId> createConfigData()
	{
		final I_C_HierarchyCommissionSettings settingsRecord = newInstance(I_C_HierarchyCommissionSettings.class);
		settingsRecord.setPointsPrecision(pointsPrecision);
		settingsRecord.setIsSubtractLowerLevelCommissionFromBase(subtractLowerLevelCommissionFromBase);
		saveRecord(settingsRecord);

		final I_C_Flatrate_Conditions conditionsRecord = newInstance(I_C_Flatrate_Conditions.class);
		conditionsRecord.setC_HierarchyCommissionSettings_ID(settingsRecord.getC_HierarchyCommissionSettings_ID());
		saveRecord(conditionsRecord);

		for (final ConfigLineTestRecord configLineTestRecord : configLineTestRecords)
		{
			configLineTestRecord.createConfigLineData(settingsRecord.getC_HierarchyCommissionSettings_ID());
		}

		final ImmutableMap.Builder<BPartnerId, FlatrateTermId> bpartnerId2flatrateTermId = ImmutableMap.builder();
		for (final ContractTestRecord contractTestRecord : contractTestRecords)
		{
			final I_C_Flatrate_Term termRecord = contractTestRecord.createContractData(conditionsRecord.getC_Flatrate_Conditions_ID());
			bpartnerId2flatrateTermId.put(
					BPartnerId.ofRepoId(termRecord.getBill_BPartner_ID()),
					FlatrateTermId.ofRepoId(termRecord.getC_Flatrate_Term_ID()));
		}
		return bpartnerId2flatrateTermId.build();
	}
}
