package de.metas.contracts.commission.commissioninstance.services;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import  de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.CommissionConstants;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory.ContractRequest;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;

import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import de.metas.product.ProductId;
import lombok.NonNull;

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

class CommissionConfigFactoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void createFor()
	{
		final I_C_BPartner bpartnerRecord_Lvl2 = newInstance(I_C_BPartner.class);
		bpartnerRecord_Lvl2.setName("SalesRep_Lvl2");
		saveRecord(bpartnerRecord_Lvl2);

		final I_C_BPartner bpartnerRecord_Lvl1 = newInstance(I_C_BPartner.class);
		bpartnerRecord_Lvl1.setBPartner_Parent_ID(bpartnerRecord_Lvl2.getC_BPartner_ID());
		bpartnerRecord_Lvl1.setName("SalesRep_Lvl1");
		saveRecord(bpartnerRecord_Lvl1);

		final I_C_BPartner bpartnerRecord_Lvl0 = newInstance(I_C_BPartner.class);
		bpartnerRecord_Lvl0.setBPartner_Parent_ID(bpartnerRecord_Lvl1.getC_BPartner_ID());
		bpartnerRecord_Lvl0.setName("SalesRep_Lvl0");
		saveRecord(bpartnerRecord_Lvl0);

		final BPartnerId salesRepLvl0Id = BPartnerId.ofRepoId(bpartnerRecord_Lvl0.getC_BPartner_ID());
		final BPartnerId salesRepLvl1Id = BPartnerId.ofRepoId(bpartnerRecord_Lvl1.getC_BPartner_ID());
		final BPartnerId salesRepLvl2Id = BPartnerId.ofRepoId(bpartnerRecord_Lvl2.getC_BPartner_ID());

		final ProductId productId = ProductId.ofRepoId(33);
		final LocalDate date = LocalDate.now();

		final I_C_HierarchyCommissionSettings settingsRecord = newInstance(I_C_HierarchyCommissionSettings.class);
		settingsRecord.setIsSubtractLowerLevelCommissionFromBase(true);
		settingsRecord.setPercentOfBasePoints(new BigDecimal("20"));
		saveRecord(settingsRecord);

		final I_C_Flatrate_Conditions flatrateConditions = newInstance(I_C_Flatrate_Conditions.class);
		flatrateConditions.setC_HierarchyCommissionSettings_ID(settingsRecord.getC_HierarchyCommissionSettings_ID());
		flatrateConditions.setType_Conditions(CommissionConstants.TYPE_CONDITIONS_COMMISSION);
		flatrateConditions.setDocStatus(IDocument.STATUS_Completed);
		saveRecord(flatrateConditions);

		final I_C_Flatrate_Matching flatrateMatchingRecord = newInstance(I_C_Flatrate_Matching.class);
		flatrateMatchingRecord.setC_Flatrate_Conditions_ID(flatrateConditions.getC_Flatrate_Conditions_ID());
		flatrateMatchingRecord.setM_Product_ID(productId.getRepoId());
		saveRecord(flatrateMatchingRecord);


		createFlatrateTerm(salesRepLvl0Id, date, flatrateConditions);
		createFlatrateTerm(salesRepLvl1Id, date, flatrateConditions);
		createFlatrateTerm(salesRepLvl2Id, date, flatrateConditions);

		// invoke method under test
		final ContractRequest contractRequest = ContractRequest.builder()
				.bPartnerId(salesRepLvl0Id)
				.productId(productId)
				.date(date).build();
		final ImmutableList<CommissionConfig> configs = new CommissionConfigFactory(new CommissionHierarchyFactory()).createFor(contractRequest);

		assertThat(configs).hasSize(1);
		final CommissionConfig config = configs.get(0);
		assertThat(config.getCommissionType()).isEqualTo(CommissionType.HIERARCHY_COMMISSION);

		final HierarchyConfig hierarchyConfig = HierarchyConfig.cast(config);
		assertThat(hierarchyConfig.isSubtractLowerLevelCommissionFromBase()).isTrue();

		final CommissionContract contractLvl0 = hierarchyConfig.getContractFor(Beneficiary.of(salesRepLvl0Id));
		final HierarchyContract hierarchyContractLvl0 = HierarchyContract.cast(contractLvl0);
		assertThat(hierarchyContractLvl0.getCommissionPercent().toBigDecimal()).isEqualTo("20");
		assertThat(hierarchyContractLvl0.getPointsPrecision()).isEqualTo(2);

		final CommissionContract contractLvl1 = hierarchyConfig.getContractFor(Beneficiary.of(salesRepLvl1Id));
		final HierarchyContract hierarchyContractLvl1 = HierarchyContract.cast(contractLvl1);
		assertThat(hierarchyContractLvl1.getCommissionPercent().toBigDecimal()).isEqualTo("20");
		assertThat(hierarchyContractLvl1.getPointsPrecision()).isEqualTo(2);

		final CommissionContract contractLvl2 = hierarchyConfig.getContractFor(Beneficiary.of(salesRepLvl2Id));
		final HierarchyContract hierarchyContractLvl2 = HierarchyContract.cast(contractLvl2);
		assertThat(hierarchyContractLvl2.getCommissionPercent().toBigDecimal()).isEqualTo("20");
		assertThat(hierarchyContractLvl2.getPointsPrecision()).isEqualTo(2);
	}

	private void createFlatrateTerm(
			@NonNull final BPartnerId salesRepBPartnerId,
			@NonNull final LocalDate date,
			@NonNull final I_C_Flatrate_Conditions flatrateConditions)
	{
		final I_C_Flatrate_Term flatrateTermRecord = newInstance(I_C_Flatrate_Term.class);
		flatrateTermRecord.setC_Flatrate_Conditions_ID(flatrateConditions.getC_Flatrate_Conditions_ID());
		flatrateTermRecord.setBill_BPartner_ID(salesRepBPartnerId.getRepoId());
		flatrateTermRecord.setDocStatus(IDocument.STATUS_Completed);
		flatrateTermRecord.setType_Conditions(CommissionConstants.TYPE_CONDITIONS_COMMISSION);
		flatrateTermRecord.setStartDate(TimeUtil.asTimestamp(date.minusDays(10)));
		flatrateTermRecord.setEndDate(TimeUtil.asTimestamp(date.plusDays(10)));
		saveRecord(flatrateTermRecord);
	}

}
