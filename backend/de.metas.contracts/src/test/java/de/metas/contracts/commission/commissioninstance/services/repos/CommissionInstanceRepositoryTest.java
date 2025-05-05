package de.metas.contracts.commission.commissioninstance.services.repos;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Payer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstanceId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionState;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate.SalesInvoiceCandidateDocumentId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline.SalesInvoiceLineDocumentId;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigProvider;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigStagingDataService;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.contracts.commission.commissioninstance.services.hierarchy.HierarchyCommissionConfigFactory;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfig;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfig.ConfigData;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfigLine;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionFact;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionInstance;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionShare;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestHierarchyCommissionContract;
import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static de.metas.contracts.commission.model.X_C_Commission_Fact.COMMISSION_FACT_STATE_FORECASTED;
import static de.metas.contracts.commission.model.X_C_Commission_Fact.COMMISSION_FACT_STATE_INVOICEABLE;
import static de.metas.contracts.commission.model.X_C_Commission_Fact.COMMISSION_FACT_STATE_INVOICED;
import static de.metas.contracts.commission.model.X_C_Commission_Fact.COMMISSION_FACT_STATE_SETTLED;
import static de.metas.contracts.commission.model.X_C_Commission_Fact.COMMISSION_FACT_STATE_TO_SETTLE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

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

@ExtendWith(SnapshotExtension.class)
class CommissionInstanceRepositoryTest
{
	private static final long START_TIMESTAMP = 1568720955000L; // Tuesday, September 17, 2019 11:49:15 AM

	private long currentTimestamp = START_TIMESTAMP;

	private static final InvoiceCandidateId C_INVOICE_CANDIDATE_ID = InvoiceCandidateId.ofRepoId(10);

	private static final BigDecimal ELEVEN = new BigDecimal("11");
	private static final BigDecimal TWELVE = new BigDecimal("12");

	private CommissionInstanceRepository commissionInstanceRepository;

	private OrgId orgId = OrgId.ofRepoId(20);
	private I_C_UOM uom;
	private ProductId commissionProductId;
	private BPartnerId payerId;

	private Expect expect;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId = AdempiereTestHelper.createOrgWithTimeZone();

		final I_M_Product commissionProductRecord = newInstance(I_M_Product.class);
		commissionProductRecord.setAD_Org_ID(0); /* set org to * */
		saveRecord(commissionProductRecord);
		commissionProductId = ProductId.ofRepoId(commissionProductRecord.getM_Product_ID());

		final CommissionConfigStagingDataService commissionConfigStagingDataService = new CommissionConfigStagingDataService();
		final HierarchyCommissionConfigFactory hierarchyCommissionConfigFactory = new HierarchyCommissionConfigFactory(commissionConfigStagingDataService, new CommissionProductService());
		final CommissionRecordStagingService commissionInstanceRecordStagingService = new CommissionRecordStagingService();
		final CommissionConfigProvider commissionConfigProvider = new CommissionConfigProvider(ImmutableList.of(hierarchyCommissionConfigFactory));
		commissionInstanceRepository = new CommissionInstanceRepository(commissionInstanceRecordStagingService, commissionConfigProvider);
		payerId = BPartnerId.ofRepoId(1001);
		uom = BusinessTestHelper.createUOM("uom");
	}

	@Test
	void getByDocumentId_InvoiceCandidateId()
	{
		createCommissionData_InvoiceCandidateId(orgId);

		// invoke the method under test
		final Optional<CommissionInstance> result = commissionInstanceRepository.getByDocumentId(new SalesInvoiceCandidateDocumentId(C_INVOICE_CANDIDATE_ID));

		assertThat(result).isPresent();
		expect.serializer("orderedJson").toMatchSnapshot(result.get());
	}

	private void createCommissionData_InvoiceCandidateId(@NonNull final OrgId orgId)
	{
		final ConfigData configData = TestCommissionConfig.builder()
				.orgId(orgId)
				.subtractLowerLevelCommissionFromBase(true)
				.pointsPrecision(2)
				.commissionProductId(commissionProductId)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("singleConfigLine").seqNo(10).percentOfBasePoints("10").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("C_BPartner_SalesRep_1_ID").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("C_BPartner_SalesRep_2_ID").build())
				.build()
				.createConfigData();

		assertThat(configData.getName2BPartnerId()).hasSize(2); // guard
		final BPartnerId C_BPartner_SalesRep_1_ID = configData.getName2BPartnerId().get("C_BPartner_SalesRep_1_ID");
		final BPartnerId C_BPartner_SalesRep_2_ID = configData.getName2BPartnerId().get("C_BPartner_SalesRep_2_ID");

		TestCommissionInstance.builder()
				.orgId(orgId)
				.invoiceCandidateId(C_INVOICE_CANDIDATE_ID)
				.triggerType(CommissionTriggerType.InvoiceCandidate)
				.triggerDocumentDate(TimeUtil.parseTimestamp("2020-03-21"))
				.pointsBase_Forecasted("10")
				.pointsBase_Invoiceable("11")
				.pointsBase_Invoiced("12")
				.commissionShareTestRecord(TestCommissionShare.builder()
												   .commissionProductId(commissionProductId)
												   .salesRepBPartnerId(C_BPartner_SalesRep_1_ID)
						.payerBPartnerId(payerId)
						.isSOTrx(false)
												   .flatrateTermId(configData.getBpartnerId2FlatrateTermId().get(C_BPartner_SalesRep_1_ID))
												   .levelHierarchy(10)
												   .pointsSum_Forecasted("1")
												   .pointsSum_Invoiceable("1.1")
												   .pointsSum_Invoiced("1.2")
												   .commissionFactTestRecord(TestCommissionFact.builder()
																					 .state(COMMISSION_FACT_STATE_FORECASTED)
																					 .commissionPoints("10")
																					 .timestamp(incAndGetTimestamp()).build())
												   .commissionFactTestRecord(TestCommissionFact.builder()
																					 .state(COMMISSION_FACT_STATE_FORECASTED)
																					 .commissionPoints("-9")
																					 .timestamp(incAndGetTimestamp()).build())
												   .commissionFactTestRecord(TestCommissionFact.builder()
																					 .state(COMMISSION_FACT_STATE_INVOICEABLE)
																					 .commissionPoints("1.1")
																					 .timestamp(incAndGetTimestamp()).build())
												   .commissionFactTestRecord(TestCommissionFact.builder()
																					 .state(COMMISSION_FACT_STATE_INVOICED)
																					 .commissionPoints("1.2")
																					 .timestamp(incAndGetTimestamp()).build())
												   // the last two are irrelevant for sales-commission-share
												   .commissionFactTestRecord(TestCommissionFact.builder()
																					 .state(COMMISSION_FACT_STATE_TO_SETTLE)
																					 .commissionPoints("4.2")
																					 .timestamp(incAndGetTimestamp()).build())
												   .commissionFactTestRecord(TestCommissionFact.builder()
																					 .state(COMMISSION_FACT_STATE_SETTLED)
																					 .commissionPoints("3.1")
																					 .timestamp(incAndGetTimestamp()).build())
												   .build())
				.commissionShareTestRecord(TestCommissionShare.builder()
												   .commissionProductId(commissionProductId)
												   .salesRepBPartnerId(C_BPartner_SalesRep_2_ID)
						.payerBPartnerId(payerId)
						.isSOTrx(false)
												   .flatrateTermId(configData.getBpartnerId2FlatrateTermId().get(C_BPartner_SalesRep_2_ID))
												   .levelHierarchy(20)
												   .pointsSum_Forecasted("2")
												   .pointsSum_Invoiceable("2.1")
												   .pointsSum_Invoiced("2.2")
												   .commissionFactTestRecord(TestCommissionFact.builder()
																					 .state(COMMISSION_FACT_STATE_FORECASTED)
																					 .commissionPoints("2")
																					 .timestamp(incAndGetTimestamp()).build())
												   .commissionFactTestRecord(TestCommissionFact.builder()
																					 .state(COMMISSION_FACT_STATE_INVOICEABLE)
																					 .commissionPoints("2.1")
																					 .timestamp(incAndGetTimestamp()).build())
												   .commissionFactTestRecord(TestCommissionFact.builder()
																					 .state(COMMISSION_FACT_STATE_INVOICED)
																					 .commissionPoints("10")
																					 .timestamp(incAndGetTimestamp()).build())
												   .commissionFactTestRecord(TestCommissionFact.builder()
																					 .state(COMMISSION_FACT_STATE_INVOICED)
																					 .commissionPoints("-7.8")
																					 .timestamp(incAndGetTimestamp()).build())
												   .build())
				.mostRecentTriggerTimestamp(currentTimestamp)
				.currencyId(CurrencyId.ofRepoId(1))
				.invoicedQty(Quantity.of(TEN, uom))
				.build()
				.createCommissionData();
	}

	/**
	 * Simpler than {@link #getByDocumentId_InvoiceCandidateId()}. Supposed to make sure that it's also working with an invoice line id.
	 */
	@Test
	void getByDocumentId_InvoiceLineId()
	{
		final InvoiceAndLineId invoiceAndLineId = InvoiceAndLineId.ofRepoId(10, 15);

		final ConfigData configData = TestCommissionConfig.builder()
				.orgId(orgId)
				.subtractLowerLevelCommissionFromBase(true)
				.pointsPrecision(2)
				.commissionProductId(commissionProductId)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("singleConfigLine").seqNo(10).percentOfBasePoints("10").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("C_BPartner_SalesRep_1_ID").build())
				.build()
				.createConfigData();

		assertThat(configData.getName2BPartnerId()).hasSize(1); // guard
		final BPartnerId C_BPartner_SalesRep_1_ID = configData.getName2BPartnerId().get("C_BPartner_SalesRep_1_ID");

		TestCommissionInstance.builder()
				.orgId(orgId)
				.invoiceAndLineId(invoiceAndLineId)
				.pointsBase_Forecasted("0")
				.pointsBase_Invoiceable("0")
				.pointsBase_Invoiced("100")
				.mostRecentTriggerTimestamp(123L)
				.triggerDocumentDate(TimeUtil.parseTimestamp("2020-03-21"))
				.triggerType(CommissionTriggerType.SalesInvoice)
				.commissionShareTestRecord(TestCommissionShare.builder()
												   .levelHierarchy(10)
												   .flatrateTermId(configData.getBpartnerId2FlatrateTermId().get(C_BPartner_SalesRep_1_ID))
												   .commissionProductId(commissionProductId)
												   .salesRepBPartnerId(C_BPartner_SalesRep_1_ID)
						.payerBPartnerId(payerId)
						.isSOTrx(false)
												   .build())
				.currencyId(CurrencyId.ofRepoId(1))
				.invoicedQty(Quantity.of(TEN, uom))
				.build()
				.createCommissionData();

		// invoke the method under test
		final Optional<CommissionInstance> result = commissionInstanceRepository.getByDocumentId(new SalesInvoiceLineDocumentId(invoiceAndLineId));

		assertThat(result).isPresent();
		expect.serializer("orderedJson").toMatchSnapshot(result.get());
	}

	private long incAndGetTimestamp()
	{
		currentTimestamp += 10000;
		return currentTimestamp;
	}

	@Test
	void save()
	{
		// the actual contract data is not saved
		final ConfigData configData = TestCommissionConfig.builder()
				.orgId(orgId)
				.subtractLowerLevelCommissionFromBase(true)
				.commissionProductId(commissionProductId)
				.pointsPrecision(2)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("singleLine").seqNo(10).percentOfBasePoints("10").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("C_BPartner_SalesRep_1_ID").build())
				.contractTestRecord(TestHierarchyCommissionContract.builder().salesRepName("C_BPartner_SalesRep_2_ID").build())
				.build()
				.createConfigData();

		assertThat(configData.getName2BPartnerId()).hasSize(2); // guard
		final BPartnerId C_BPartner_SalesRep_1_ID = configData.getName2BPartnerId().get("C_BPartner_SalesRep_1_ID");
		final BPartnerId C_BPartner_SalesRep_2_ID = configData.getName2BPartnerId().get("C_BPartner_SalesRep_2_ID");

		final I_M_Product salesProduct = newInstance(I_M_Product.class);
		salesProduct.setAD_Org_ID(OrgId.toRepoId(orgId));
		salesProduct.setM_Product_Category_ID(20);
		saveRecord(salesProduct);

		final I_C_BPartner customer = newInstance(I_C_BPartner.class);
		customer.setAD_Org_ID(OrgId.toRepoId(orgId));
		customer.setC_BP_Group_ID(30);
		saveRecord(customer);

		final I_C_Invoice_Candidate salesInvoiceCandidate = newInstance(I_C_Invoice_Candidate.class);
		salesInvoiceCandidate.setAD_Org_ID(OrgId.toRepoId(orgId));
		salesInvoiceCandidate.setBill_BPartner_ID(customer.getC_BPartner_ID());
		salesInvoiceCandidate.setM_Product_ID(salesProduct.getM_Product_ID());
		saveRecord(salesInvoiceCandidate);

		final Beneficiary beneficiary1 = Beneficiary.of(C_BPartner_SalesRep_1_ID);
		final Beneficiary beneficiary2 = Beneficiary.of(C_BPartner_SalesRep_2_ID);

		final HierarchyConfig config = HierarchyConfig.builder()
				.id(configData.getHierarchyConfigId())
				.commissionProductId(commissionProductId)
				.subtractLowerLevelCommissionFromBase(true)
				.beneficiary2HierarchyContract(
						beneficiary1,
						HierarchyContract.builder().id(configData.getBpartnerId2FlatrateTermId().get(C_BPartner_SalesRep_1_ID))
								.commissionSettingsLineId(configData.getName2CommissionSettingsLineId().get("singleLine"))
								.commissionPercent(Percent.of("10"))
								.pointsPrecision(2))
				.beneficiary2HierarchyContract(
						beneficiary2,
						HierarchyContract.builder().id(configData.getBpartnerId2FlatrateTermId().get(C_BPartner_SalesRep_2_ID))
								.commissionSettingsLineId(configData.getName2CommissionSettingsLineId().get("singleLine"))
								.commissionPercent(Percent.of("10"))
								.pointsPrecision(2))
				.build();

		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("uom");

		final CommissionInstance commissionInstance = CommissionInstance.builder()
				.id(null) // not yet persisted
				.currentTriggerData(CommissionTriggerData.builder()
											.orgId(orgId)
											.triggerType(CommissionTriggerType.InvoiceCandidate)
											.triggerDocumentId(new SalesInvoiceCandidateDocumentId(InvoiceCandidateId.ofRepoId(salesInvoiceCandidate.getC_Invoice_Candidate_ID())))
											.triggerDocumentDate(LocalDate.of(2020, 03, 21))
											.timestamp(Instant.parse("2019-09-17T11:50:35Z"))
											.forecastedBasePoints(CommissionPoints.of("10"))
											.invoiceableBasePoints(CommissionPoints.of("11"))
											.invoicedBasePoints(CommissionPoints.of("12"))
											.productId(commissionProductId)
											.totalQtyInvolved(Quantity.of(BigDecimal.TEN, uomRecord))
											.documentCurrencyId(CurrencyId.ofRepoId(1))
											.build())
				.share(CommissionShare.builder()
							   .config(config)
						.soTrx(SOTrx.PURCHASE)
						.payer(Payer.of(payerId))
							   .beneficiary(beneficiary1)
							   .level(HierarchyLevel.of(10))
						.fact(CommissionFact.builder()
											 .points(CommissionPoints.of("10"))
								.state(CommissionState.FORECASTED)
											 .timestamp(Instant.parse("2019-09-17T11:49:25Z")).build())
						.fact(CommissionFact.builder()
								.state(CommissionState.FORECASTED)
											 .points(CommissionPoints.of("-9"))
											 .timestamp(Instant.parse("2019-09-17T11:49:35Z")).build())
						.fact(CommissionFact.builder()
								.state(CommissionState.INVOICEABLE)
											 .points(CommissionPoints.of("1.1"))
											 .timestamp(Instant.parse("2019-09-17T11:49:45Z")).build())
						.fact(CommissionFact.builder()
											 .points(CommissionPoints.of("1.2"))
								.state(CommissionState.INVOICED)
											 .timestamp(Instant.parse("2019-09-17T11:49:55Z")).build())
							   .build())
				.share(CommissionShare.builder()
							   .config(config)
							   .beneficiary(beneficiary2)
							   .level(HierarchyLevel.of(20))
						.payer(Payer.of(payerId))
						.soTrx(SOTrx.PURCHASE)
						.fact(CommissionFact.builder()
											 .points(CommissionPoints.of("2"))
								.state(CommissionState.FORECASTED)
											 .timestamp(Instant.parse("2019-09-17T11:50:05Z")).build())
						.fact(CommissionFact.builder()
								.state(CommissionState.INVOICEABLE)
											 .points(CommissionPoints.of("2.1"))
											 .timestamp(Instant.parse("2019-09-17T11:50:15Z")).build())
						.fact(CommissionFact.builder()
								.state(CommissionState.INVOICED)
											 .points(CommissionPoints.of("10"))
											 .timestamp(Instant.parse("2019-09-17T11:50:25Z")).build())
						.fact(CommissionFact.builder()
								.state(CommissionState.INVOICED)
											 .points(CommissionPoints.of("-7.8"))
											 .timestamp(Instant.parse("2019-09-17T11:50:35Z")).build())
							   .build())
				.build();

		// invoke the method under test
		final CommissionInstanceId result = commissionInstanceRepository.save(commissionInstance);

		// verify the records that were stored
		final List<I_C_Commission_Instance> instanceRecords = POJOLookupMap.get().getRecords(I_C_Commission_Instance.class);
		assertThat(instanceRecords).hasSize(1)
				.extracting("C_Commission_Instance_ID", "PointsBase_Forecasted", "PointsBase_Invoiceable", "PointsBase_Invoiced", "MostRecentTriggerTimestamp.time")
				.contains(tuple(result.getRepoId(), TEN, ELEVEN, TWELVE, 1568721035000L/* "2019-09-17T11:50:35Z" */));

		final List<I_C_Commission_Share> shareRecords = POJOLookupMap.get().getRecords(I_C_Commission_Share.class);
		assertThat(shareRecords).hasSize(2)
				.extracting("C_Commission_Instance_ID", "LevelHierarchy", "C_BPartner_SalesRep_ID", "Commission_Product_ID", "PointsSum_Forecasted", "PointsSum_Invoiceable", "PointsSum_Invoiced")
				.contains(
						tuple(result.getRepoId(), 10, C_BPartner_SalesRep_1_ID.getRepoId(), commissionProductId.getRepoId(), new BigDecimal("1"), new BigDecimal("1.1"), new BigDecimal("1.2")),
						tuple(result.getRepoId(), 20, C_BPartner_SalesRep_2_ID.getRepoId(), commissionProductId.getRepoId(), new BigDecimal("2"), new BigDecimal("2.1"), new BigDecimal("2.2")));

		final I_C_Commission_Share shareRecord1 = shareRecords.get(0);
		assertThat(shareRecord1.getLevelHierarchy()).isEqualTo(10); // guard
		final List<I_C_Commission_Fact> factRecords1 = POJOLookupMap.get().getRecords(I_C_Commission_Fact.class, r -> r.getC_Commission_Share_ID() == shareRecord1.getC_Commission_Share_ID());
		assertThat(factRecords1).hasSize(4)
				.extracting("CommissionFactTimestamp", "Commission_Fact_State", "CommissionPoints")
				.contains(
						tuple("1568720965.0", "FORECASTED", new BigDecimal("10")),
						tuple("1568720975.0", "FORECASTED", new BigDecimal("-9")),
						tuple("1568720985.0", "INVOICEABLE", new BigDecimal("1.1")),
						tuple("1568720995.0", "INVOICED", new BigDecimal("1.2")));

		final I_C_Commission_Share shareRecord2 = shareRecords.get(1);
		assertThat(shareRecord2.getLevelHierarchy()).isEqualTo(20); // guard
		final List<I_C_Commission_Fact> factRecords2 = POJOLookupMap.get().getRecords(I_C_Commission_Fact.class, r -> r.getC_Commission_Share_ID() == shareRecord2.getC_Commission_Share_ID());
		assertThat(factRecords2).hasSize(4)
				.extracting("CommissionFactTimestamp", "Commission_Fact_State", "CommissionPoints")
				.contains(
						tuple("1568721005.0", "FORECASTED", new BigDecimal("2")),
						tuple("1568721015.0", "INVOICEABLE", new BigDecimal("2.1")),
						tuple("1568721025.0", "INVOICED", new BigDecimal("10")),
						tuple("1568721035.0", "INVOICED", new BigDecimal("-7.8")));

		// final check; load the CommissionInstance from the records we just created and verify that it's equal to the one we got from json
		final CommissionInstance reloadedInstance = commissionInstanceRepository.getById(result);
		expect.serializer("orderedJson").toMatchSnapshot(reloadedInstance);
	}
}
