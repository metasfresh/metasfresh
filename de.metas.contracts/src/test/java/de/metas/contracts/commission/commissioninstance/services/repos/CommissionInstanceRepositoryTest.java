package de.metas.contracts.commission.commissioninstance.services.repos;

import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static de.metas.contracts.commission.model.X_C_Commission_Fact.*;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstanceId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionState;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory;
import de.metas.contracts.commission.commissioninstance.services.CommissionHierarchyFactory;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionInstanceRepository;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionRecordStagingService;
import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.commission.testhelpers.CommissionFactTestRecord;
import de.metas.contracts.commission.testhelpers.CommissionInstanceTestRecord;
import de.metas.contracts.commission.testhelpers.CommissionShareTestRecord;
import de.metas.contracts.commission.testhelpers.ConfigTestRecord;
import de.metas.contracts.commission.testhelpers.ContractTestRecord;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.lang.Percent;
import io.github.jsonSnapshot.SnapshotMatcher;

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

class CommissionInstanceRepositoryTest
{
	private static final long START_TIMESTAMP = 1568720955000L; // Tuesday, September 17, 2019 11:49:15 AM

	private long currentTimestamp = START_TIMESTAMP;

	private static final InvoiceCandidateId C_INVOICE_CANDIDATE_ID = InvoiceCandidateId.ofRepoId(10);

	private static final BPartnerId C_BPartner_SalesRep_1_ID = BPartnerId.ofRepoId(20);
	private static final BPartnerId C_BPartner_SalesRep_2_ID = BPartnerId.ofRepoId(21);

	private static final BigDecimal ELEVEN = new BigDecimal("11");
	private static final BigDecimal TWELVE = new BigDecimal("12");

	private CommissionInstanceRepository commissionInstanceRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		commissionInstanceRepository = new CommissionInstanceRepository(
				new CommissionConfigFactory(new CommissionHierarchyFactory()),
				new CommissionRecordStagingService());
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
	void getForInvoiceCandidateId()
	{
		createCommissionData();

		// invoke the method under test

		final ImmutableList<CommissionInstance> result = commissionInstanceRepository.getForInvoiceCandidateId(C_INVOICE_CANDIDATE_ID);
		assertThat(result).hasSize(1);

		SnapshotMatcher.expect(result.get(0)).toMatchSnapshot();
	}

	private void createCommissionData()
	{
		final ImmutableMap<BPartnerId, FlatrateTermId> bpartnerId2flatrateTermId = ConfigTestRecord.builder()
				.percentOfBasePoints("10")
				.subtractLowerLevelCommissionFromBase(true)
				.contractTestRecord(ContractTestRecord.builder().C_BPartner_SalesRep_ID(C_BPartner_SalesRep_1_ID).build())
				.contractTestRecord(ContractTestRecord.builder().C_BPartner_SalesRep_ID(C_BPartner_SalesRep_2_ID).build())
				.build()
				.createConfigData();

		CommissionInstanceTestRecord.builder()
				.C_INVOICE_CANDIDATE_ID(C_INVOICE_CANDIDATE_ID)
				.pointsBase_Forecasted("10")
				.pointsBase_Invoiceable("11")
				.pointsBase_Invoiced("12")
				.commissionShareTestRecord(CommissionShareTestRecord.builder()
						.C_BPartner_SalesRep_ID(C_BPartner_SalesRep_1_ID)
						.flatrateTermId(bpartnerId2flatrateTermId.get(C_BPartner_SalesRep_1_ID))
						.levelHierarchy(10)
						.pointsSum_Forecasted("1")
						.pointsSum_Invoiceable("1.1")
						.pointsSum_Invoiced("1.2")
						.commissionFactTestRecord(CommissionFactTestRecord.builder()
								.state(COMMISSION_FACT_STATE_FORECASTED)
								.commissionPoints("10")
								.timestamp(incAndGetTimestamp()).build())
						.commissionFactTestRecord(CommissionFactTestRecord.builder()
								.state(COMMISSION_FACT_STATE_FORECASTED)
								.commissionPoints("-9")
								.timestamp(incAndGetTimestamp()).build())
						.commissionFactTestRecord(CommissionFactTestRecord.builder()
								.state(COMMISSION_FACT_STATE_INVOICEABLE)
								.commissionPoints("1.1")
								.timestamp(incAndGetTimestamp()).build())
						.commissionFactTestRecord(CommissionFactTestRecord.builder()
								.state(COMMISSION_FACT_STATE_INVOICED)
								.commissionPoints("1.2")
								.timestamp(incAndGetTimestamp()).build())
						// the last two are irrelevant for sales-commission-share
						.commissionFactTestRecord(CommissionFactTestRecord.builder()
								.state(COMMISSION_FACT_STATE_TO_SETTLE)
								.commissionPoints("4.2")
								.timestamp(incAndGetTimestamp()).build())
						.commissionFactTestRecord(CommissionFactTestRecord.builder()
								.state(COMMISSION_FACT_STATE_SETTLED)
								.commissionPoints("3.1")
								.timestamp(incAndGetTimestamp()).build())
						.build())
				.commissionShareTestRecord(CommissionShareTestRecord.builder()
						.C_BPartner_SalesRep_ID(C_BPartner_SalesRep_2_ID)
						.flatrateTermId(bpartnerId2flatrateTermId.get(C_BPartner_SalesRep_2_ID))
						.levelHierarchy(20)
						.pointsSum_Forecasted("2")
						.pointsSum_Invoiceable("2.1")
						.pointsSum_Invoiced("2.2")
						.commissionFactTestRecord(CommissionFactTestRecord.builder()
								.state(COMMISSION_FACT_STATE_FORECASTED)
								.commissionPoints("2")
								.timestamp(incAndGetTimestamp()).build())
						.commissionFactTestRecord(CommissionFactTestRecord.builder()
								.state(COMMISSION_FACT_STATE_INVOICEABLE)
								.commissionPoints("2.1")
								.timestamp(incAndGetTimestamp()).build())
						.commissionFactTestRecord(CommissionFactTestRecord.builder()
								.state(COMMISSION_FACT_STATE_INVOICED)
								.commissionPoints("10")
								.timestamp(incAndGetTimestamp()).build())
						.commissionFactTestRecord(CommissionFactTestRecord.builder()
								.state(COMMISSION_FACT_STATE_INVOICED)
								.commissionPoints("-7.8")
								.timestamp(incAndGetTimestamp()).build())
						.build())
				.mostRecentTriggerTimestamp(currentTimestamp)
				.build()
				.createCommissionData();
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
		final ImmutableMap<BPartnerId, FlatrateTermId> bpartnerId2flatrateTermId = ConfigTestRecord.builder()
				.percentOfBasePoints("10")
				.subtractLowerLevelCommissionFromBase(true)
				.contractTestRecord(ContractTestRecord.builder().C_BPartner_SalesRep_ID(C_BPartner_SalesRep_1_ID).build())
				.contractTestRecord(ContractTestRecord.builder().C_BPartner_SalesRep_ID(C_BPartner_SalesRep_2_ID).build())
				.build()
				.createConfigData();

		final I_C_Invoice_Candidate salesInvoiceCandidate = newInstance(I_C_Invoice_Candidate.class);
		salesInvoiceCandidate.setBill_BPartner_ID(10);
		saveRecord(salesInvoiceCandidate);

		final Beneficiary beneficiary1 = Beneficiary.of(C_BPartner_SalesRep_1_ID);
		final Beneficiary beneficiary2 = Beneficiary.of(C_BPartner_SalesRep_2_ID);

		final HierarchyConfig config = HierarchyConfig.builder()
				.subtractLowerLevelCommissionFromBase(true)
				.beneficiary2HierarchyContract(
						beneficiary1,
						HierarchyContract.builder().id(bpartnerId2flatrateTermId.get(C_BPartner_SalesRep_1_ID)).commissionPercent(Percent.of("10")).pointsPrecision(2))
				.beneficiary2HierarchyContract(
						beneficiary2,
						HierarchyContract.builder().id(bpartnerId2flatrateTermId.get(C_BPartner_SalesRep_2_ID)).commissionPercent(Percent.of("10")).pointsPrecision(2))
				.build();

		final CommissionInstance commissionInstance = CommissionInstance.builder()
				.config(config)
				.id(null) // not yet persisted
				.currentTriggerData(CommissionTriggerData.builder()
						.invoiceCandidateId(InvoiceCandidateId.ofRepoId(salesInvoiceCandidate.getC_Invoice_Candidate_ID()))
						.timestamp(Instant.parse("2019-09-17T11:50:35Z"))
						.forecastedPoints(CommissionPoints.of("10"))
						.invoiceablePoints(CommissionPoints.of("11"))
						.invoicedPoints(CommissionPoints.of("12"))
						.build())
				.share(SalesCommissionShare.builder()
						.beneficiary(beneficiary1)
						.level(HierarchyLevel.of(10))
						.fact(SalesCommissionFact.builder()
								.points(CommissionPoints.of("10"))
								.state(SalesCommissionState.FORECASTED)
								.timestamp(Instant.parse("2019-09-17T11:49:25Z")).build())
						.fact(SalesCommissionFact.builder()
								.state(SalesCommissionState.FORECASTED)
								.points(CommissionPoints.of("-9"))
								.timestamp(Instant.parse("2019-09-17T11:49:35Z")).build())
						.fact(SalesCommissionFact.builder()
								.state(SalesCommissionState.INVOICEABLE)
								.points(CommissionPoints.of("1.1"))
								.timestamp(Instant.parse("2019-09-17T11:49:45Z")).build())
						.fact(SalesCommissionFact.builder()
								.points(CommissionPoints.of("1.2"))
								.state(SalesCommissionState.INVOICED)
								.timestamp(Instant.parse("2019-09-17T11:49:55Z")).build())
						.build())
				.share(SalesCommissionShare.builder()
						.beneficiary(beneficiary2)
						.level(HierarchyLevel.of(20))
						.fact(SalesCommissionFact.builder()
								.points(CommissionPoints.of("2"))
								.state(SalesCommissionState.FORECASTED)
								.timestamp(Instant.parse("2019-09-17T11:50:05Z")).build())
						.fact(SalesCommissionFact.builder()
								.state(SalesCommissionState.INVOICEABLE)
								.points(CommissionPoints.of("2.1"))
								.timestamp(Instant.parse("2019-09-17T11:50:15Z")).build())
						.fact(SalesCommissionFact.builder()
								.state(SalesCommissionState.INVOICED)
								.points(CommissionPoints.of("10"))
								.timestamp(Instant.parse("2019-09-17T11:50:25Z")).build())
						.fact(SalesCommissionFact.builder()
								.state(SalesCommissionState.INVOICED)
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
				.extracting("C_Commission_Instance_ID", "LevelHierarchy", "C_BPartner_SalesRep_ID", "PointsSum_Forecasted", "PointsSum_Invoiceable", "PointsSum_Invoiced")
				.contains(
						tuple(result.getRepoId(), 10, 20, new BigDecimal("1"), new BigDecimal("1.1"), new BigDecimal("1.2")),
						tuple(result.getRepoId(), 20, 21, new BigDecimal("2"), new BigDecimal("2.1"), new BigDecimal("2.2")));

		final I_C_Commission_Share shareRecord1 = shareRecords.get(0);
		assertThat(shareRecord1.getLevelHierarchy()).isEqualTo(10); // guard
		final List<I_C_Commission_Fact> factRecords1 = POJOLookupMap.get().getRecords(I_C_Commission_Fact.class, r -> r.getC_Commission_Share_ID() == shareRecord1.getC_Commission_Share_ID());
		assertThat(factRecords1).hasSize(4)
				.extracting("CommissionFactTimestamp", "Commission_Fact_State", "CommissionPoints")
				.contains(
						tuple("2019-09-17T11:49:25Z", "FORECASTED", new BigDecimal("10")),
						tuple("2019-09-17T11:49:35Z", "FORECASTED", new BigDecimal("-9")),
						tuple("2019-09-17T11:49:45Z", "INVOICEABLE", new BigDecimal("1.1")),
						tuple("2019-09-17T11:49:55Z", "INVOICED", new BigDecimal("1.2")));

		final I_C_Commission_Share shareRecord2 = shareRecords.get(1);
		assertThat(shareRecord2.getLevelHierarchy()).isEqualTo(20); // guard
		final List<I_C_Commission_Fact> factRecords2 = POJOLookupMap.get().getRecords(I_C_Commission_Fact.class, r -> r.getC_Commission_Share_ID() == shareRecord2.getC_Commission_Share_ID());
		assertThat(factRecords2).hasSize(4)
				.extracting("CommissionFactTimestamp", "Commission_Fact_State", "CommissionPoints")
				.contains(
						tuple("2019-09-17T11:50:05Z", "FORECASTED", new BigDecimal("2")),
						tuple("2019-09-17T11:50:15Z", "INVOICEABLE", new BigDecimal("2.1")),
						tuple("2019-09-17T11:50:25Z", "INVOICED", new BigDecimal("10")),
						tuple("2019-09-17T11:50:35Z", "INVOICED", new BigDecimal("-7.8")));

		// final check; load the CommissionInstance from the records we jsut created and verify that it's equal to the one we got from json
		final CommissionInstance reloadedInstance = commissionInstanceRepository.getForCommissionInstanceId(result);
		assertThat(reloadedInstance).isEqualTo(commissionInstance.toBuilder().id(result).build());
	}
}
