package de.metas.contracts.commission.testhelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import lombok.Builder;
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

	@NonNull
	String percentOfBasePoints;

	@NonNull
	Boolean subtractLowerLevelCommissionFromBase;

	@Singular
	List<ContractTestRecord> contractTestRecords;

	public ImmutableMap<BPartnerId, FlatrateTermId> createConfigData()
	{
		final I_C_HierarchyCommissionSettings settingsRecord = newInstance(I_C_HierarchyCommissionSettings.class);
		settingsRecord.setPercentOfBasePoints(new BigDecimal(percentOfBasePoints));
		settingsRecord.setIsSubtractLowerLevelCommissionFromBase(subtractLowerLevelCommissionFromBase);
		saveRecord(settingsRecord);

		final I_C_Flatrate_Conditions conditionsRecord = newInstance(I_C_Flatrate_Conditions.class);
		conditionsRecord.setC_HierarchyCommissionSettings_ID(settingsRecord.getC_HierarchyCommissionSettings_ID());
		saveRecord(conditionsRecord);

		final ImmutableMap.Builder<BPartnerId, FlatrateTermId> bpartnerId2flatrateTermId = ImmutableMap.builder();

		for (final ContractTestRecord contractTestRecord : contractTestRecords)
		{
			final FlatrateTermId flatrateTermId = contractTestRecord.createContractData(conditionsRecord.getC_Flatrate_Conditions_ID());
			bpartnerId2flatrateTermId.put(
					contractTestRecord.getC_BPartner_SalesRep_ID(),
					flatrateTermId);
		}
		return bpartnerId2flatrateTermId.build();
	}
}
