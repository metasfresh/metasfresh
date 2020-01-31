package de.metas.contracts.commission.commissioninstance.services;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory.ConfigRequestForNewInstance;
import de.metas.contracts.commission.testhelpers.ConfigLineTestRecord;
import de.metas.contracts.commission.testhelpers.ConfigTestRecord;
import de.metas.contracts.commission.testhelpers.ConfigTestRecord.ConfigData;
import de.metas.contracts.commission.testhelpers.ContractTestRecord;
import de.metas.product.ProductCategoryId;
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
	private CommissionHierarchyFactory commissionHierarchyFactory;
	private CommissionConfigFactory commissionConfigFactory;
	private I_C_BPartner bpartnerRecord_EndCustomer;

	private ProductId salesProductId;
	private ProductCategoryId saleProductCategoryId;
	private LocalDate date;
	private BPartnerId endCustomerId;
	private BPGroupId customerBPGroudId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		commissionHierarchyFactory = new CommissionHierarchyFactory();
		final CommissionConfigStagingDataService commissionConfigStagingDataService = new CommissionConfigStagingDataService();
		commissionConfigFactory = new CommissionConfigFactory(commissionConfigStagingDataService);

		final I_C_BP_Group customerBPGroupRecord = newInstance(I_C_BP_Group.class);
		saveRecord(customerBPGroupRecord);
		customerBPGroudId = BPGroupId.ofRepoId(customerBPGroupRecord.getC_BP_Group_ID());

		bpartnerRecord_EndCustomer = newInstance(I_C_BPartner.class);
		bpartnerRecord_EndCustomer.setC_BP_Group_ID(customerBPGroupRecord.getC_BP_Group_ID());
		bpartnerRecord_EndCustomer.setName("bpartnerRecord_EndCustomer");
		saveRecord(bpartnerRecord_EndCustomer);

		endCustomerId = BPartnerId.ofRepoId(bpartnerRecord_EndCustomer.getC_BPartner_ID());

		saleProductCategoryId = ProductCategoryId.ofRepoId(33);

		final I_M_Product salesProductRecord = newInstance(I_M_Product.class);
		salesProductRecord.setM_Product_Category_ID(saleProductCategoryId.getRepoId());
		saveRecord(salesProductRecord);
		salesProductId = ProductId.ofRepoId(salesProductRecord.getM_Product_ID());

		date = LocalDate.now();

	}

	@Test
	void createFor()
	{
		final ConfigData configData = ConfigTestRecord.builder()
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(ConfigLineTestRecord.builder().seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(ConfigLineTestRecord.builder().seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(ContractTestRecord.builder().name("salesRep").parentName("salesSupervisor").date(date).build())
				.contractTestRecord(ContractTestRecord.builder().name("salesSupervisor").parentName("headOfSales").date(date).build())
				.contractTestRecord(ContractTestRecord.builder().name("headOfSales").date(date).build())
				.build()
				.createConfigData();

		final BPartnerId salesRepLvl0Id = configData.getName2BPartnerId().get("salesRep");
		final BPartnerId salesRepLvl1Id = configData.getName2BPartnerId().get("salesSupervisor");
		final BPartnerId salesRepLvl2Id = configData.getName2BPartnerId().get("headOfSales");

		// invoke method under test
		final ConfigRequestForNewInstance contractRequest = ConfigRequestForNewInstance.builder()
				.commissionHierarchy(commissionHierarchyFactory.createFor(salesRepLvl0Id))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.salesProductId(salesProductId)
				.date(date).build();
		final ImmutableList<CommissionConfig> configs = commissionConfigFactory.createForNewCommissionInstances(contractRequest);

		assertThat(configs).hasSize(1);
		final CommissionConfig config = configs.get(0);
		assertThat(config.getCommissionType()).isEqualTo(CommissionType.HIERARCHY_COMMISSION);

		final HierarchyConfig hierarchyConfig = HierarchyConfig.cast(config);
		assertThat(hierarchyConfig.isSubtractLowerLevelCommissionFromBase()).isTrue();

		final CommissionContract contractLvl0 = hierarchyConfig.getContractFor(Beneficiary.of(salesRepLvl0Id));
		final HierarchyContract hierarchyContractLvl0 = HierarchyContract.cast(contractLvl0);
		assertThat(hierarchyContractLvl0.getCommissionPercent().toBigDecimal()).isEqualTo("20");
		assertThat(hierarchyContractLvl0.getPointsPrecision()).isEqualTo(3);

		final CommissionContract contractLvl1 = hierarchyConfig.getContractFor(Beneficiary.of(salesRepLvl1Id));
		final HierarchyContract hierarchyContractLvl1 = HierarchyContract.cast(contractLvl1);
		assertThat(hierarchyContractLvl1.getCommissionPercent().toBigDecimal()).isEqualTo("20");
		assertThat(hierarchyContractLvl1.getPointsPrecision()).isEqualTo(3);

		final CommissionContract contractLvl2 = hierarchyConfig.getContractFor(Beneficiary.of(salesRepLvl2Id));
		final HierarchyContract hierarchyContractLvl2 = HierarchyContract.cast(contractLvl2);
		assertThat(hierarchyContractLvl2.getCommissionPercent().toBigDecimal()).isEqualTo("20");
		assertThat(hierarchyContractLvl2.getPointsPrecision()).isEqualTo(3);
	}

	@Test
	void createForNewCommissionInstances_no_configLines()
	{
		final ProductCategoryId someOtherProductCategoryId = ProductCategoryId.ofRepoId(34);

		final ConfigData configData = ConfigTestRecord.builder()
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				// note: we create an unrelated configLine
				.configLineTestRecord(ConfigLineTestRecord.builder().seqNo(10).salesProductCategoryId(someOtherProductCategoryId).percentOfBasePoints("20").build())
				.contractTestRecord(ContractTestRecord.builder().name("salesRep").parentName("salesSupervisor").date(date).build())
				.contractTestRecord(ContractTestRecord.builder().name("salesSupervisor").parentName("headOfSales").date(date).build())
				.contractTestRecord(ContractTestRecord.builder().name("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertNoConfigCreated(configData);
	}

	@Test
	void createForNewCommissionInstances_no_matching_configLines()
	{
		final ConfigData configData = ConfigTestRecord.builder()
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				// note: we create no configLines
				.contractTestRecord(ContractTestRecord.builder().name("salesRep").parentName("salesSupervisor").date(date).build())
				.contractTestRecord(ContractTestRecord.builder().name("salesSupervisor").parentName("headOfSales").date(date).build())
				.contractTestRecord(ContractTestRecord.builder().name("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertNoConfigCreated(configData);
	}

	private void assertNoConfigCreated(@NonNull final ConfigData configData)
	{
		final BPartnerId salesRepLvl0Id = configData.getName2BPartnerId().get("salesRep");

		final ConfigRequestForNewInstance contractRequest = ConfigRequestForNewInstance.builder()
				.commissionHierarchy(commissionHierarchyFactory.createFor(salesRepLvl0Id))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.salesProductId(salesProductId)
				.date(date).build();

		// invoke method under test
		final ImmutableList<CommissionConfig> configs = commissionConfigFactory.createForNewCommissionInstances(contractRequest);

		assertThat(configs).isEmpty();
	}
}
