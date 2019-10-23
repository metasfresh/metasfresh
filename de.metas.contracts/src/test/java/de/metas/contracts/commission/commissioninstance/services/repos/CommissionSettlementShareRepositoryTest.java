package de.metas.contracts.commission.commissioninstance.services.repos;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static de.metas.contracts.commission.model.X_C_Commission_Fact.*;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShareId;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementState;
import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.commission.testhelpers.CommissionFactTestRecord;
import de.metas.contracts.commission.testhelpers.CommissionInstanceTestRecord;
import de.metas.contracts.commission.testhelpers.CommissionInstanceTestRecord.CreateCommissionInstanceResult;
import de.metas.contracts.commission.testhelpers.CommissionShareTestRecord;
import de.metas.contracts.commission.testhelpers.ConfigTestRecord;
import de.metas.contracts.commission.testhelpers.ContractTestRecord;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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

class CommissionSettlementShareRepositoryTest
{
	private CommissionSettlementShareRepository commissionSettlementShareRepository;

	private static final long START_TIMESTAMP = 1568720955000L; // Tuesday, September 17, 2019 11:49:15 AM

	private long currentTimestamp = START_TIMESTAMP;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		commissionSettlementShareRepository = new CommissionSettlementShareRepository();
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
	void getByInvoiceCandidateId()
	{
		final I_C_Invoice_Candidate settlementICRecord = newInstance(I_C_Invoice_Candidate.class);
		saveRecord(settlementICRecord);
		final InvoiceCandidateId settlementInvoiceCandidateId = InvoiceCandidateId.ofRepoId(settlementICRecord.getC_Invoice_Candidate_ID());

		final BPartnerId salesRepId = BPartnerId.ofRepoId(10);

		final ImmutableMap<BPartnerId, FlatrateTermId> bpartnerId2flatrateTermId = ConfigTestRecord.builder()
				.percentOfBasePoints("10")
				.subtractLowerLevelCommissionFromBase(true)
				.contractTestRecord(ContractTestRecord.builder().C_BPartner_SalesRep_ID(salesRepId).build())
				.build()
				.createConfigData();

		final CreateCommissionInstanceResult commissionInstanceResult = CommissionInstanceTestRecord.builder()
				.C_INVOICE_CANDIDATE_ID(InvoiceCandidateId.ofRepoId(10))
				.pointsBase_Forecasted("999")
				.pointsBase_Invoiceable("999")
				.pointsBase_Invoiced("999")
				.commissionShareTestRecord(
						CommissionShareTestRecord.builder()
								.C_BPartner_SalesRep_ID(salesRepId)
								.flatrateTermId(bpartnerId2flatrateTermId.get(salesRepId))
								.levelHierarchy(10)
								.pointsSum_ToSettle("10") // shall be overridden by the facts' sum
								.pointsSum_Settled("11") // shall be overridden by the facts' sum
								.commissionFactTestRecord(CommissionFactTestRecord.builder()
										.state(COMMISSION_FACT_STATE_TO_SETTLE)
										.commissionPoints("20")
										.C_Invoice_Candidate_Commission_ID(settlementInvoiceCandidateId)
										.timestamp(incAndGetTimestamp()).build())
								.commissionFactTestRecord(CommissionFactTestRecord.builder()
										.state(COMMISSION_FACT_STATE_TO_SETTLE)
										.commissionPoints("-3")
										.C_Invoice_Candidate_Commission_ID(settlementInvoiceCandidateId)
										.timestamp(incAndGetTimestamp()).build())
								.commissionFactTestRecord(CommissionFactTestRecord.builder()
										.state(COMMISSION_FACT_STATE_INVOICED) // shall not be relevant here
										.commissionPoints("21")
										.C_Invoice_Candidate_Commission_ID(settlementInvoiceCandidateId)
										.timestamp(incAndGetTimestamp()).build())
								.commissionFactTestRecord(CommissionFactTestRecord.builder()
										.state(COMMISSION_FACT_STATE_SETTLED) // shall not be relevant here
										.commissionPoints("18")
										.C_Invoice_Candidate_Commission_ID(settlementInvoiceCandidateId)
										.timestamp(incAndGetTimestamp()).build())
								.build())
				.mostRecentTriggerTimestamp(23L)
				.build()
				.createCommissionData();

		settlementICRecord.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Commission_Share.class));
		settlementICRecord.setRecord_ID(commissionInstanceResult.getBpartnerId2commissionShareId().get(salesRepId));
		saveRecord(settlementICRecord);

		// invoke the method under test
		final CommissionSettlementShare result = commissionSettlementShareRepository.getByInvoiceCandidateId(settlementInvoiceCandidateId);

		SnapshotMatcher.expect(result).toMatchSnapshot();
	}

	@Test
	void save()
	{
		final I_C_Invoice_Candidate settlementICRecord = newInstance(I_C_Invoice_Candidate.class);
		saveRecord(settlementICRecord);
		final InvoiceCandidateId settlementInvoiceCandidateId = InvoiceCandidateId.ofRepoId(settlementICRecord.getC_Invoice_Candidate_ID());

		final BPartnerId salesRepId = BPartnerId.ofRepoId(10);

		final ImmutableMap<BPartnerId, FlatrateTermId> bpartnerId2flatrateTermId = ConfigTestRecord.builder()
				.percentOfBasePoints("10")
				.subtractLowerLevelCommissionFromBase(true)
				.contractTestRecord(ContractTestRecord.builder().C_BPartner_SalesRep_ID(salesRepId).build())
				.build()
				.createConfigData();

		final CreateCommissionInstanceResult commissionInstanceResult = CommissionInstanceTestRecord.builder()
				.C_INVOICE_CANDIDATE_ID(InvoiceCandidateId.ofRepoId(10))
				.pointsBase_Forecasted("999")
				.pointsBase_Invoiceable("999")
				.pointsBase_Invoiced("999")
				.commissionShareTestRecord(
						CommissionShareTestRecord.builder()
								.C_BPartner_SalesRep_ID(salesRepId)
								.flatrateTermId(bpartnerId2flatrateTermId.get(salesRepId))
								.levelHierarchy(10)
								.build())
				.mostRecentTriggerTimestamp(23L)
				.build()
				.createCommissionData();

		final SalesCommissionShareId salesCommissionShareId = SalesCommissionShareId.ofRepoId(commissionInstanceResult.getBpartnerId2commissionShareId().get(salesRepId));
		final CommissionSettlementShare share = CommissionSettlementShare.builder()
				.salesCommissionShareId(salesCommissionShareId)
				.facts(ImmutableList.of())
				.build();

		share
				.addFact(CommissionSettlementFact.builder()
						.settlementInvoiceCandidateId(settlementInvoiceCandidateId)
						.points(CommissionPoints.of("22"))
						.state(CommissionSettlementState.SETTLED)
						.timestamp(Instant.ofEpochMilli(incAndGetTimestamp()))
						.build())
				.addFact(CommissionSettlementFact.builder()
						.settlementInvoiceCandidateId(settlementInvoiceCandidateId)
						.points(CommissionPoints.of("11"))
						.state(CommissionSettlementState.TO_SETTLE)
						.timestamp(Instant.ofEpochMilli(incAndGetTimestamp()))
						.build());

		// invoke the method under test
		commissionSettlementShareRepository.save(share);

		final List<I_C_Commission_Fact> factRecords = POJOLookupMap.get().getRecords(I_C_Commission_Fact.class);
		assertThat(factRecords).hasSize(2)
				.extracting("C_Commission_Share_ID", "CommissionFactTimestamp", "Commission_Fact_State", "CommissionPoints")
				.contains(
						tuple(salesCommissionShareId.getRepoId(), "2019-09-17T11:49:25Z", "SETTLED", new BigDecimal("22")),
						tuple(salesCommissionShareId.getRepoId(), "2019-09-17T11:49:35Z", "TO_SETTLE", new BigDecimal("11")));
	}

	private long incAndGetTimestamp()
	{
		currentTimestamp += 10000;
		return currentTimestamp;
	}
}
