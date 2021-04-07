package de.metas.contracts.commission.commissioninstance.services;

import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import de.metas.organization.OrgId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory.ConfigRequestForExistingInstance;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory.ConfigRequestForNewInstance;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfigLine;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfig;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionContract;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfig.ConfigData;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import io.github.jsonSnapshot.SnapshotMatcher;
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

	private OrgId orgId;
	private ProductId salesProductId;
	private ProductCategoryId saleProductCategoryId;
	private LocalDate date;
	private BPartnerId endCustomerId;
	private BPGroupId customerBPGroudId;

	private ProductId commissionProduct1Id;
	private ProductId commissionProduct2Id;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId = AdempiereTestHelper.createOrgWithTimeZone();

		final I_M_Product commissionProduct1Record = newInstance(I_M_Product.class);
		saveRecord(commissionProduct1Record);
		commissionProduct1Id = ProductId.ofRepoId(commissionProduct1Record.getM_Product_ID());

		final I_M_Product commissionProduct2Record = newInstance(I_M_Product.class);
		saveRecord(commissionProduct2Record);
		commissionProduct2Id = ProductId.ofRepoId(commissionProduct2Record.getM_Product_ID());

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

	@BeforeAll
	static void beforeAll()
	{
		SnapshotMatcher.start(
				AdempiereTestHelper.SNAPSHOT_CONFIG,
				AdempiereTestHelper.createSnapshotJsonFunction());
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	void createFor()
	{
		// given
		final ConfigData configData = TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct1Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		final BPartnerId salesRepLvl0Id = configData.getName2BPartnerId().get("salesRep");
		final BPartnerId salesRepLvl1Id = configData.getName2BPartnerId().get("salesSupervisor");
		final BPartnerId salesRepLvl2Id = configData.getName2BPartnerId().get("headOfSales");

		setSalesRepOfEndCustomerTo(salesRepLvl0Id);

		// when
		final ConfigRequestForNewInstance contractRequest = ConfigRequestForNewInstance.builder()
				.orgId(configData.getOrgId())
				.commissionHierarchy(commissionHierarchyFactory.createFor(endCustomerId))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.salesProductId(salesProductId)
				.commissionDate(date).build();
		final ImmutableList<CommissionConfig> configs = commissionConfigFactory.createForNewCommissionInstances(contractRequest);

		// then
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

		SnapshotMatcher.expect(config).toMatchSnapshot();
	}

	private void setSalesRepOfEndCustomerTo(final BPartnerId salesRepLvl0Id)
	{
		final I_C_BPartner endcustomerRecord = load(endCustomerId, I_C_BPartner.class);
		endcustomerRecord.setC_BPartner_SalesRep_ID(salesRepLvl0Id.getRepoId());
		saveRecord(endcustomerRecord);
	}

	@Test
	void createForNewCommissionInstances_multiple_contracts()
	{
		final ConfigData configData1 = TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct1Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(1); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(3); // guard

		TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct2Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(2); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(6); // guard

		final BPartnerId salesRepLvl0Id = configData1.getName2BPartnerId().get("salesRep");

		setSalesRepOfEndCustomerTo(salesRepLvl0Id);
		
		// when
		final ConfigRequestForNewInstance contractRequest = ConfigRequestForNewInstance.builder()
				.orgId(orgId)
				.commissionHierarchy(commissionHierarchyFactory.createFor(endCustomerId))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.salesProductId(salesProductId)
				.commissionDate(date).build();
		final ImmutableList<CommissionConfig> configs = commissionConfigFactory.createForNewCommissionInstances(contractRequest);

		// then
		assertThat(configs).hasSize(2);
		SnapshotMatcher.expect(configs).toMatchSnapshot();
	}

	@Test
	void createForNewCommissionInstances_multiple_contracts_shareOnOwnRevenue()
	{
		final ConfigData configData1 = TestCommissionConfig.builder()
				.orgId(orgId)
				.createShareForOwnRevenue(true)
				.commissionProductId(commissionProduct1Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(1); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(3); // guard

		TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct2Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(2); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(6); // guard

		final BPartnerId salesRepLvl0Id = configData1.getName2BPartnerId().get("salesRep");
		final BPartnerId salesRepLvl1Id = configData1.getName2BPartnerId().get("salesSupervisor");


		// when
		final ConfigRequestForNewInstance contractRequest = ConfigRequestForNewInstance.builder()
				.orgId(orgId)
				.commissionHierarchy(commissionHierarchyFactory.createFor(salesRepLvl0Id))
				.customerBPartnerId(salesRepLvl0Id)
				.salesRepBPartnerId(salesRepLvl1Id)
				.salesProductId(salesProductId)
				.commissionDate(date).build();
		final ImmutableList<CommissionConfig> configs = commissionConfigFactory.createForNewCommissionInstances(contractRequest);

		// then
		assertThat(configs).hasSize(2);
		SnapshotMatcher.expect(configs).toMatchSnapshot();
	}
	
	@Test
	void createForNewCommissionInstances_no_configLines()
	{
		final ProductCategoryId someOtherProductCategoryId = ProductCategoryId.ofRepoId(34);

		final ConfigData configData = TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct1Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				// note: we create an unrelated configLine
				.configLineTestRecord(TestCommissionConfigLine.builder().name("singleConfigLine").seqNo(10).salesProductCategoryId(someOtherProductCategoryId).percentOfBasePoints("20").build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertNoConfigCreated(configData);
	}

	@Test
	void createForNewCommissionInstances_no_matching_configLines()
	{
		final ConfigData configData = TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct1Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				// note: we create no configLines
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertNoConfigCreated(configData);
	}

	private void assertNoConfigCreated(@NonNull final ConfigData configData)
	{
		final BPartnerId salesRepLvl0Id = configData.getName2BPartnerId().get("salesRep");

		final ConfigRequestForNewInstance contractRequest = ConfigRequestForNewInstance.builder()
				.orgId(orgId)
				.commissionHierarchy(commissionHierarchyFactory.createFor(endCustomerId))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.salesProductId(salesProductId)
				.commissionDate(date).build();

		// invoke method under test
		final ImmutableList<CommissionConfig> configs = commissionConfigFactory.createForNewCommissionInstances(contractRequest);

		assertThat(configs).isEmpty();
	}

	@Test
	void createForExisingInstance()
	{
		assertThat(POJOLookupMap.get().getRecords(I_C_BPartner.class)).hasSize(1); // guard

		ConfigData configData1 = TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct1Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(1); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(3); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_BPartner.class)).hasSize(4); // guard - 3 new sales reps

		ConfigData configData2 = TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct2Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(2); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(6); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_BPartner.class)).hasSize(4); // guard - no additional new sales reps

		final ImmutableCollection<FlatrateTermId> configData1ContractIds = configData1.getBpartnerId2FlatrateTermId().values();
		final ImmutableCollection<FlatrateTermId> configData2ContractIds = configData2.getBpartnerId2FlatrateTermId().values();

		final ImmutableList<FlatrateTermId> allSixContractIds = POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class).stream()
				.map(record -> FlatrateTermId.ofRepoId(record.getC_Flatrate_Term_ID()))
				.collect(ImmutableList.toImmutableList());

		final ConfigRequestForExistingInstance commissionConfigRequest = ConfigRequestForExistingInstance.builder()
				.contractIds(allSixContractIds)
				.customerBPartnerId(endCustomerId)
				.salesProductId(salesProductId)
				.build();

		// invoke method under test
		final ImmutableMap<FlatrateTermId, CommissionConfig> result = commissionConfigFactory.createForExisingInstance(commissionConfigRequest);
		assertThat(result).hasSize(6);
		assertThat(configData1ContractIds)
				.as("check that all contracts of configData1 are mapped to to the config created from configData1")
				.allSatisfy(contractId -> {
					final CommissionConfig config = result.get(contractId);
					assertThat(config).isInstanceOf(HierarchyConfig.class);
					assertThat(HierarchyConfig.cast(config).getId()).isEqualTo(configData1.getHierarchyConfigId());
				});
		assertThat(configData2ContractIds)
				.as("check that all contracts of configData2 are mapped to to the config created from configData2")
				.allSatisfy(contractId -> {
					final CommissionConfig config = result.get(contractId);
					assertThat(config).isInstanceOf(HierarchyConfig.class);
					assertThat(HierarchyConfig.cast(config).getId()).isEqualTo(configData2.getHierarchyConfigId());
				});

		SnapshotMatcher.expect(result).toMatchSnapshot();
	}

}
