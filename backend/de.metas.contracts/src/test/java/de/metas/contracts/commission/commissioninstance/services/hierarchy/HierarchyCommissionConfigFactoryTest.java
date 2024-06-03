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

package de.metas.contracts.commission.commissioninstance.services.hierarchy;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigProvider;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigStagingDataService;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfig;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfig.ConfigData;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfigLine;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestHierarchyCommissionContract;
import de.metas.contracts.commission.model.I_C_CommissionSettingsLine;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;

import static de.metas.contracts.commission.CommissionConstants.FLATRATE_CONDITION_0_COMMISSION_ID;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SnapshotExtension.class)
public class HierarchyCommissionConfigFactoryTest
{
	private CommissionHierarchyFactory commissionHierarchyFactory;
	private HierarchyCommissionConfigFactory hierarchyCommissionConfigFactory;

	private OrgId orgId;
	private ProductId salesProductId;
	private ProductCategoryId saleProductCategoryId;
	private LocalDate date;
	private BPartnerId endCustomerId;
	private BPGroupId customerBPGroudId;

	private ProductId commissionProduct1Id;
	private ProductId commissionProduct2Id;
	private I_C_Flatrate_Conditions noContractFlatrateConditions;

	private CommissionTriggerType commissionTriggerType;

	private Expect expect;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new ModularContractLogDAO());
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsDAO());

		orgId = AdempiereTestHelper.createOrgWithTimeZone();

		final I_M_Product commissionProduct1Record = newInstance(I_M_Product.class);
		saveRecord(commissionProduct1Record);
		commissionProduct1Id = ProductId.ofRepoId(commissionProduct1Record.getM_Product_ID());

		final I_M_Product commissionProduct2Record = newInstance(I_M_Product.class);
		saveRecord(commissionProduct2Record);
		commissionProduct2Id = ProductId.ofRepoId(commissionProduct2Record.getM_Product_ID());

		commissionHierarchyFactory = new CommissionHierarchyFactory();

		final CommissionConfigStagingDataService commissionConfigStagingDataService = new CommissionConfigStagingDataService();
		hierarchyCommissionConfigFactory = new HierarchyCommissionConfigFactory(commissionConfigStagingDataService, new CommissionProductService());

		final I_C_BP_Group customerBPGroupRecord = newInstance(I_C_BP_Group.class);
		saveRecord(customerBPGroupRecord);
		customerBPGroudId = BPGroupId.ofRepoId(customerBPGroupRecord.getC_BP_Group_ID());

		final I_C_BPartner bpartnerRecord_EndCustomer = newInstance(I_C_BPartner.class);
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

		noContractFlatrateConditions = flatrateConditionsBuilder()
				.name("Default for `Missing commission contract`")
				.isSubtractLowerLevelCommissionFromBase(true)
				.noContractCommissionProductId(1)
				.percentageOfBasePoint(BigDecimal.ZERO)
				.flatrateConditionsId(FLATRATE_CONDITION_0_COMMISSION_ID.getRepoId())
				.docStatus("CO")
				.isActive(false)
				.typeConditions(TypeConditions.COMMISSION)
				.build();

		commissionTriggerType = CommissionTriggerType.InvoiceCandidate;
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
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		final BPartnerId salesRepLvl0Id = configData.getName2BPartnerId().get("salesRep");
		final BPartnerId salesRepLvl1Id = configData.getName2BPartnerId().get("salesSupervisor");
		final BPartnerId salesRepLvl2Id = configData.getName2BPartnerId().get("headOfSales");

		setSalesRepOfEndCustomerTo(salesRepLvl0Id);

		// when
		final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.orgId(configData.getOrgId())
				.commissionHierarchy(commissionHierarchyFactory.createForCustomer(endCustomerId, salesRepLvl0Id))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.commissionTriggerType(commissionTriggerType)
				.salesProductId(salesProductId)
				.commissionDate(date).build();
		final ImmutableList<CommissionConfig> configs = hierarchyCommissionConfigFactory.createForNewCommissionInstances(contractRequest);

		// then
		assertThat(configs).hasSize(1);
		final CommissionConfig config = configs.get(0);
		assertThat(config.getCommissionType()).isEqualTo(CommissionType.HIERARCHY_COMMISSION);

		final HierarchyConfig hierarchyConfig = HierarchyConfig.cast(config);
		assertThat(hierarchyConfig.isSubtractLowerLevelCommissionFromBase()).isTrue();

		final CommissionContract contractLvl0 = hierarchyConfig.getContractFor(salesRepLvl0Id);
		final HierarchyContract hierarchyContractLvl0 = HierarchyContract.cast(contractLvl0);
		assertThat(hierarchyContractLvl0.getCommissionPercent().toBigDecimal()).isEqualTo("20");
		assertThat(hierarchyContractLvl0.getPointsPrecision()).isEqualTo(3);

		final CommissionContract contractLvl1 = hierarchyConfig.getContractFor(salesRepLvl1Id);
		final HierarchyContract hierarchyContractLvl1 = HierarchyContract.cast(contractLvl1);
		assertThat(hierarchyContractLvl1.getCommissionPercent().toBigDecimal()).isEqualTo("20");
		assertThat(hierarchyContractLvl1.getPointsPrecision()).isEqualTo(3);

		final CommissionContract contractLvl2 = hierarchyConfig.getContractFor(salesRepLvl2Id);
		final HierarchyContract hierarchyContractLvl2 = HierarchyContract.cast(contractLvl2);
		assertThat(hierarchyContractLvl2.getCommissionPercent().toBigDecimal()).isEqualTo("20");
		assertThat(hierarchyContractLvl2.getPointsPrecision()).isEqualTo(3);

		expect.serializer("orderedJson").toMatchSnapshot(config);
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
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(2); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(3); // guard

		TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct2Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(3); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(6); // guard

		final BPartnerId salesRepLvl0Id = configData1.getName2BPartnerId().get("salesRep");

		setSalesRepOfEndCustomerTo(salesRepLvl0Id);

		// when
		final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.orgId(orgId)
				.commissionHierarchy(commissionHierarchyFactory.createForCustomer(endCustomerId,salesRepLvl0Id))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.commissionTriggerType(commissionTriggerType)
				.salesProductId(salesProductId)
				.commissionDate(date).build();
		final ImmutableList<CommissionConfig> configs = hierarchyCommissionConfigFactory.createForNewCommissionInstances(contractRequest);

		// then
		assertThat(configs).hasSize(2);
		expect.serializer("orderedJson").toMatchSnapshot(configs);
	}

	@Test
	void createForNewCommissionInstances_no_contract()
	{
		//given
		noContractFlatrateConditions.setIsActive(true);
		saveRecord(noContractFlatrateConditions);

		final ConfigData configData1 = TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct1Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").date(date).build())
				.build()
				.createConfigData();

		final BPartnerId salesRepLvl0Id = configData1.getName2BPartnerId().get("salesRep");
		final BPartnerId salesRepLvl2Id = configData1.getName2BPartnerId().get("salesSupervisor");

		final I_C_BPartner salesRepLvl1 = bPartnerBuilder()
				.salesRepId(salesRepLvl2Id)
				.name("noContractSalesRep")
				.isVendor(true)
				.build();

		final BPartnerId salesRepLvl1Id = BPartnerId.ofRepoId(salesRepLvl1.getC_BPartner_ID());

		final I_C_BPartner salesRepLvl0 = InterfaceWrapperHelper.load(salesRepLvl0Id, I_C_BPartner.class);
		salesRepLvl0.setC_BPartner_SalesRep_ID(salesRepLvl1.getC_BPartner_ID());

		InterfaceWrapperHelper.saveRecord(salesRepLvl0);

		setSalesRepOfEndCustomerTo(salesRepLvl0Id);

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(2); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(2); // guard

		final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.orgId(orgId)
				.commissionHierarchy(commissionHierarchyFactory.createForCustomer(endCustomerId, salesRepLvl0Id))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.commissionTriggerType(commissionTriggerType)
				.salesProductId(salesProductId)
				.commissionDate(date)
				.build();

		// when
		final ImmutableList<CommissionConfig> configs = hierarchyCommissionConfigFactory.createForNewCommissionInstances(contractRequest);

		// then
		assertThat(configs).hasSize(2);
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(3);

		final CommissionConfig noContractConfig = configs.stream()
				.map(HierarchyConfig::cast)
				.filter(config -> config.getId().getRepoId() == noContractFlatrateConditions.getC_HierarchyCommissionSettings_ID())
				.findFirst()
				.orElse(null);

		assertThat(noContractConfig).isNotNull();

		assertThat(noContractConfig.getCommissionType()).isEqualTo(CommissionType.HIERARCHY_COMMISSION);

		final HierarchyConfig hierarchyConfig = HierarchyConfig.cast(noContractConfig);

		assertThat(hierarchyConfig.getCommissionProductId().getRepoId()).isEqualTo(noContractFlatrateConditions.getM_Product_Flatrate_ID());

		final HierarchyContract zeroPercentHierarchyContract = HierarchyContract.cast(hierarchyConfig.getContractFor(salesRepLvl1Id));

		assertThat(zeroPercentHierarchyContract).isNotNull();
		assertThat(zeroPercentHierarchyContract.getCommissionPercent()).isEqualTo(Percent.ZERO);
	}

	@Test
	void createForNewCommissionInstances_no_contract_inactive_GenericCommissionTerm()
	{
		// given
		final ConfigData configData1 = TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct1Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").date(date).build())
				.build()
				.createConfigData();

		final BPartnerId salesRepLvl0Id = configData1.getName2BPartnerId().get("salesRep");
		final BPartnerId salesRepLvl2Id = configData1.getName2BPartnerId().get("salesSupervisor");

		final I_C_BPartner salesRepLvl1 = bPartnerBuilder()
				.salesRepId(salesRepLvl2Id)
				.name("noContractSalesRep")
				.isVendor(true)
				.build();

		final BPartnerId salesRepLvl1Id = BPartnerId.ofRepoId(salesRepLvl1.getC_BPartner_ID());

		final I_C_BPartner salesRepLvl0 = InterfaceWrapperHelper.load(salesRepLvl0Id, I_C_BPartner.class);
		salesRepLvl0.setC_BPartner_SalesRep_ID(salesRepLvl1.getC_BPartner_ID());

		InterfaceWrapperHelper.saveRecord(salesRepLvl0);

		setSalesRepOfEndCustomerTo(salesRepLvl0Id);

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(2); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(2); // guard

		final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.orgId(orgId)
				.commissionHierarchy(commissionHierarchyFactory.createForCustomer(endCustomerId, salesRepLvl0Id))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.commissionTriggerType(commissionTriggerType)
				.salesProductId(salesProductId)
				.commissionDate(date)
				.build();
		// when
		final ImmutableList<CommissionConfig> configs = hierarchyCommissionConfigFactory.createForNewCommissionInstances(contractRequest);

		// then
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(2); // guard
		assertThat(configs).hasSize(1);

		final CommissionConfig config = configs.get(0);

		assertThat(config.getCommissionType()).isEqualTo(CommissionType.HIERARCHY_COMMISSION);

		final HierarchyConfig hierarchyConfig = HierarchyConfig.cast(config);

		final CommissionContract contractLvl1 = hierarchyConfig.getContractFor(salesRepLvl1Id);
		assertThat(contractLvl1).isNull();
	}

	@Test
	void createForNewCommissionInstances_no_contract_drafted_GenericCommissionTerm()
	{
		// given
		noContractFlatrateConditions.setIsActive(true);
		noContractFlatrateConditions.setDocStatus("DR");
		saveRecord(noContractFlatrateConditions);

		final ConfigData configData1 = TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct1Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").date(date).build())
				.build()
				.createConfigData();

		final BPartnerId salesRepLvl0Id = configData1.getName2BPartnerId().get("salesRep");
		final BPartnerId salesRepLvl2Id = configData1.getName2BPartnerId().get("salesSupervisor");

		final I_C_BPartner salesRepLvl1 = bPartnerBuilder()
				.salesRepId(salesRepLvl2Id)
				.name("noContractSalesRep")
				.isVendor(true)
				.build();

		final BPartnerId salesRepLvl1Id = BPartnerId.ofRepoId(salesRepLvl1.getC_BPartner_ID());

		final I_C_BPartner salesRepLvl0 = InterfaceWrapperHelper.load(salesRepLvl0Id, I_C_BPartner.class);
		salesRepLvl0.setC_BPartner_SalesRep_ID(salesRepLvl1.getC_BPartner_ID());

		InterfaceWrapperHelper.saveRecord(salesRepLvl0);

		setSalesRepOfEndCustomerTo(salesRepLvl0Id);

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(2); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(2); // guard

		final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.orgId(orgId)
				.commissionHierarchy(commissionHierarchyFactory.createForCustomer(endCustomerId, salesRepLvl0Id))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.commissionTriggerType(commissionTriggerType)
				.salesProductId(salesProductId)
				.commissionDate(date)
				.build();
		// when
		final ImmutableList<CommissionConfig> configs = hierarchyCommissionConfigFactory.createForNewCommissionInstances(contractRequest);

		// then
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(2); // guard

		assertThat(configs).hasSize(1);

		final CommissionConfig config = configs.get(0);

		assertThat(config.getCommissionType()).isEqualTo(CommissionType.HIERARCHY_COMMISSION);

		final HierarchyConfig hierarchyConfig = HierarchyConfig.cast(config);

		final CommissionContract contractLvl1 = hierarchyConfig.getContractFor(salesRepLvl1Id);
		assertThat(contractLvl1).isNull();
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
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(2); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(3); // guard

		TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct2Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(3); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(6); // guard

		final BPartnerId salesRepLvl0Id = configData1.getName2BPartnerId().get("salesRep");
		final BPartnerId salesRepLvl1Id = configData1.getName2BPartnerId().get("salesSupervisor");

		// when
		final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.orgId(orgId)
				.commissionHierarchy(commissionHierarchyFactory.createForCustomer(salesRepLvl0Id,salesRepLvl1Id))
				.customerBPartnerId(salesRepLvl0Id)
				.salesRepBPartnerId(salesRepLvl1Id)
				.commissionTriggerType(commissionTriggerType)
				.salesProductId(salesProductId)
				.commissionDate(date).build();
		final ImmutableList<CommissionConfig> configs = hierarchyCommissionConfigFactory.createForNewCommissionInstances(contractRequest);

		// then
		assertThat(configs).hasSize(2);
		expect.serializer("orderedJson").toMatchSnapshot(configs);
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
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("headOfSales").date(date).build())
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
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertNoConfigCreated(configData);
	}

	private void assertNoConfigCreated(@NonNull final ConfigData configData)
	{
		final BPartnerId salesRepLvl0Id = configData.getName2BPartnerId().get("salesRep");

		final CommissionConfigProvider.ConfigRequestForNewInstance contractRequest = CommissionConfigProvider.ConfigRequestForNewInstance.builder()
				.orgId(orgId)
				.commissionHierarchy(commissionHierarchyFactory.createForCustomer(endCustomerId, salesRepLvl0Id))
				.customerBPartnerId(endCustomerId)
				.salesRepBPartnerId(salesRepLvl0Id)
				.commissionTriggerType(commissionTriggerType)
				.salesProductId(salesProductId)
				.commissionDate(date).build();

		// invoke method under test
		final ImmutableList<CommissionConfig> configs = hierarchyCommissionConfigFactory.createForNewCommissionInstances(contractRequest);

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
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(2); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(3); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_BPartner.class)).hasSize(4); // guard - 3 new sales reps

		ConfigData configData2 = TestCommissionConfig.builder()
				.orgId(orgId)
				.commissionProductId(commissionProduct2Id)
				.pointsPrecision(3)
				.subtractLowerLevelCommissionFromBase(true)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("2ndConfigLine").seqNo(20).salesProductCategoryId(saleProductCategoryId).percentOfBasePoints("10").build())
				.configLineTestRecord(TestCommissionConfigLine.builder().name("1stConfigLine").seqNo(10).customerBGroupId(customerBPGroudId).percentOfBasePoints("20").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesRep").parentSalesRepName("salesSupervisor").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("salesSupervisor").parentSalesRepName("headOfSales").date(date).build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("headOfSales").date(date).build())
				.build()
				.createConfigData();

		assertThat(POJOLookupMap.get().getRecords(I_C_HierarchyCommissionSettings.class)).hasSize(3); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class)).hasSize(6); // guard
		assertThat(POJOLookupMap.get().getRecords(I_C_BPartner.class)).hasSize(4); // guard - no additional new sales reps

		final ImmutableCollection<FlatrateTermId> configData1ContractIds = configData1.getBpartnerId2FlatrateTermId().values();
		final ImmutableCollection<FlatrateTermId> configData2ContractIds = configData2.getBpartnerId2FlatrateTermId().values();

		final ImmutableList<FlatrateTermId> allSixContractIds = POJOLookupMap.get().getRecords(I_C_Flatrate_Term.class).stream()
				.map(record -> FlatrateTermId.ofRepoId(record.getC_Flatrate_Term_ID()))
				.collect(ImmutableList.toImmutableList());

		final CommissionConfigProvider.ConfigRequestForExistingInstance commissionConfigRequest = CommissionConfigProvider.ConfigRequestForExistingInstance.builder()
				.contractIds(allSixContractIds)
				.customerBPartnerId(endCustomerId)
				.salesProductId(salesProductId)
				.build();

		// invoke method under test
		final ImmutableMap<FlatrateTermId, CommissionConfig> result = hierarchyCommissionConfigFactory.createForExistingInstance(commissionConfigRequest);
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

		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@Builder(builderMethodName = "flatrateConditionsBuilder", builderClassName = "FlatrateConditionsBuilder")
	public static I_C_Flatrate_Conditions buildCommissionContractConditions(
			final boolean isSubtractLowerLevelCommissionFromBase,
			final int noContractCommissionProductId,
			final int flatrateConditionsId,
			final boolean isActive,
			final String name,
			final BigDecimal percentageOfBasePoint,
			final String docStatus,
			@NonNull final TypeConditions typeConditions
	)
	{
		final I_M_Product noContractCommissionProduct = newInstance(I_M_Product.class);
		noContractCommissionProduct.setM_Product_ID(noContractCommissionProductId);
		noContractCommissionProduct.setM_Product_Category_ID(1);
		saveRecord(noContractCommissionProduct);

		final I_C_HierarchyCommissionSettings noContractHierarchySettings = newInstance(I_C_HierarchyCommissionSettings.class);
		noContractHierarchySettings.setName(name);
		noContractHierarchySettings.setIsSubtractLowerLevelCommissionFromBase(isSubtractLowerLevelCommissionFromBase);
		noContractHierarchySettings.setPointsPrecision(2);
		noContractHierarchySettings.setCommission_Product_ID(noContractCommissionProductId);
		saveRecord(noContractHierarchySettings);

		final I_C_CommissionSettingsLine commissionSettingsLine = newInstance(I_C_CommissionSettingsLine.class);
		commissionSettingsLine.setC_HierarchyCommissionSettings(noContractHierarchySettings);
		commissionSettingsLine.setSeqNo(10);
		commissionSettingsLine.setAD_Org_ID(0);
		commissionSettingsLine.setPercentOfBasePoints(percentageOfBasePoint);
		saveRecord(commissionSettingsLine);

		final I_C_Flatrate_Conditions flatrateConditions = newInstance(I_C_Flatrate_Conditions.class);
		flatrateConditions.setC_Flatrate_Conditions_ID(flatrateConditionsId);
		flatrateConditions.setDocStatus(docStatus);
		flatrateConditions.setIsActive(isActive);
		flatrateConditions.setM_Product_Flatrate_ID(noContractCommissionProductId);

		if (typeConditions.equals(TypeConditions.COMMISSION))
		{
			flatrateConditions.setC_HierarchyCommissionSettings_ID(noContractHierarchySettings.getC_HierarchyCommissionSettings_ID());
			flatrateConditions.setType_Conditions(TypeConditions.COMMISSION.getCode());
		}

		saveRecord(flatrateConditions);

		return flatrateConditions;
	}

	@Builder(builderMethodName = "bPartnerBuilder", builderClassName = "BPartnerBuilder")
	private I_C_BPartner createBPartner(
			final BPartnerId salesRepId,
			final String name,
			final boolean isVendor)
	{
		final I_C_BPartner bPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bPartner.setIsVendor(isVendor);
		bPartner.setC_BPartner_SalesRep_ID(salesRepId.getRepoId());
		bPartner.setName(name);
		InterfaceWrapperHelper.saveRecord(bPartner);

		final I_C_Location location = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		location.setC_Country_ID(1);
		InterfaceWrapperHelper.saveRecord(location);

		final I_C_BPartner_Location bPartnerLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		bPartnerLocation.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		bPartnerLocation.setIsBillTo(true);
		bPartnerLocation.setC_Location_ID(location.getC_Location_ID());

		InterfaceWrapperHelper.saveRecord(bPartnerLocation);

		return bPartner;
	}
}
