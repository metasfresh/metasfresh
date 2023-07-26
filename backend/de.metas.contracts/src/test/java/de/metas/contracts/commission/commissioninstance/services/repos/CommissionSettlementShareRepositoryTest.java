package de.metas.contracts.commission.commissioninstance.services.repos;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShareId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementState;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfig;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfig.ConfigData;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionConfigLine;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionContract;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionFact;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionInstance;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionInstance.CreateCommissionInstanceResult;
import de.metas.contracts.commission.commissioninstance.testhelpers.TestCommissionShare;
import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.collections.CollectionUtils;
import io.github.jsonSnapshot.SnapshotMatcher;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map.Entry;

import static de.metas.contracts.commission.model.X_C_Commission_Fact.COMMISSION_FACT_STATE_INVOICED;
import static de.metas.contracts.commission.model.X_C_Commission_Fact.COMMISSION_FACT_STATE_SETTLED;
import static de.metas.contracts.commission.model.X_C_Commission_Fact.COMMISSION_FACT_STATE_TO_SETTLE;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
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

class CommissionSettlementShareRepositoryTest
{
	private CommissionSettlementShareRepository commissionSettlementShareRepository;

	private static final long START_TIMESTAMP = 1568720955000L; // Tuesday, September 17, 2019 11:49:15 AM

	private long currentTimestamp = START_TIMESTAMP;

	private ProductId commissionProductId;
	private BPartnerId payerId;
	private OrgId orgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId = AdempiereTestHelper.createOrgWithTimeZone();

		final I_M_Product commissionProductRecord = newInstance(I_M_Product.class);
		commissionProductRecord.setAD_Org_ID(0); /* set it to org * */
		saveRecord(commissionProductRecord);
		commissionProductId = ProductId.ofRepoId(commissionProductRecord.getM_Product_ID());
		payerId = BPartnerId.ofRepoId(1001);

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
		settlementICRecord.setAD_Org_ID(OrgId.toRepoId(orgId));
		saveRecord(settlementICRecord);

		final InvoiceCandidateId settlementInvoiceCandidateId = InvoiceCandidateId.ofRepoId(settlementICRecord.getC_Invoice_Candidate_ID());
		final ConfigData configData = TestCommissionConfig.builder()
				.orgId(orgId)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("singleConfigLine").seqNo(10).percentOfBasePoints("10").build())
				.subtractLowerLevelCommissionFromBase(true)
				.commissionProductId(commissionProductId)
				.contractTestRecord(TestCommissionContract.builder().salesRepName("C_BPartner_SalesRep_1_ID").build())
				.build()
				.createConfigData();
		assertThat(configData.getBpartnerId2FlatrateTermId().entrySet()).hasSize(1); // guard
		final Entry<BPartnerId, FlatrateTermId> bpartnerIdAndFlatrateTermId = CollectionUtils.singleElement(configData.getBpartnerId2FlatrateTermId().entrySet());

		final CreateCommissionInstanceResult commissionInstanceResult = TestCommissionInstance.builder()
				.orgId(orgId)
				.invoiceCandidateId(InvoiceCandidateId.ofRepoId(10))
				.triggerType(CommissionTriggerType.InvoiceCandidate)
				.triggerDocumentDate(TimeUtil.parseTimestamp("2020-03-21"))
				.pointsBase_Forecasted("999")
				.pointsBase_Invoiceable("999")
				.pointsBase_Invoiced("999")
				.commissionShareTestRecord(
						TestCommissionShare.builder()
								.commissionProductId(commissionProductId)
								.salesRepBPartnerId(bpartnerIdAndFlatrateTermId.getKey())
								.payerBPartnerId(payerId)
								.isSOTrx(true)
								.flatrateTermId(bpartnerIdAndFlatrateTermId.getValue())
								.levelHierarchy(10)
								.pointsSum_ToSettle("10") // shall be overridden by the facts' sum
								.pointsSum_Settled("11") // shall be overridden by the facts' sum
								.commissionFactTestRecord(TestCommissionFact.builder()
										.state(COMMISSION_FACT_STATE_TO_SETTLE)
										.commissionPoints("20")
										.C_Invoice_Candidate_Commission_ID(settlementInvoiceCandidateId)
										.timestamp(incAndGetTimestamp()).build())
								.commissionFactTestRecord(TestCommissionFact.builder()
										.state(COMMISSION_FACT_STATE_TO_SETTLE)
										.commissionPoints("-3")
										.C_Invoice_Candidate_Commission_ID(settlementInvoiceCandidateId)
										.timestamp(incAndGetTimestamp()).build())
								.commissionFactTestRecord(TestCommissionFact.builder()
										.state(COMMISSION_FACT_STATE_INVOICED) // shall not be relevant here
										.commissionPoints("21")
										.C_Invoice_Candidate_Commission_ID(settlementInvoiceCandidateId)
										.timestamp(incAndGetTimestamp()).build())
								.commissionFactTestRecord(TestCommissionFact.builder()
										.state(COMMISSION_FACT_STATE_SETTLED) // shall not be relevant here
										.commissionPoints("18")
										.C_Invoice_Candidate_Commission_ID(settlementInvoiceCandidateId)
										.timestamp(incAndGetTimestamp()).build())
								.build())
				.mostRecentTriggerTimestamp(23L)
				.build()
				.createCommissionData();

		settlementICRecord.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_Commission_Share.class));
		settlementICRecord.setRecord_ID(commissionInstanceResult.getBpartnerId2commissionShareId().get(bpartnerIdAndFlatrateTermId.getKey()));
		saveRecord(settlementICRecord);

		// invoke the method under test
		final CommissionSettlementShare result = commissionSettlementShareRepository.getByInvoiceCandidateId(settlementInvoiceCandidateId);

		SnapshotMatcher.expect(result).toMatchSnapshot();
	}

	@Test
	void save()
	{
		final I_C_Invoice_Candidate settlementICRecord = newInstance(I_C_Invoice_Candidate.class);
		settlementICRecord.setAD_Org_ID(OrgId.toRepoId(orgId));
		saveRecord(settlementICRecord);
		final InvoiceCandidateId settlementInvoiceCandidateId = InvoiceCandidateId.ofRepoId(settlementICRecord.getC_Invoice_Candidate_ID());

		final ConfigData configData = TestCommissionConfig.builder()
				.orgId(orgId)
				.configLineTestRecord(TestCommissionConfigLine.builder().name("singleConfigLine").seqNo(10).percentOfBasePoints("10").build())
				.subtractLowerLevelCommissionFromBase(true)
				.contractTestRecord(TestCommissionContract.builder().salesRepName("C_BPartner_SalesRep_1_ID").build())
				.commissionProductId(commissionProductId)
				.build()
				.createConfigData();
		assertThat(configData.getBpartnerId2FlatrateTermId().entrySet()).hasSize(1); // guard
		final Entry<BPartnerId, FlatrateTermId> bpartnerIdAndFlatrateTermId = CollectionUtils.singleElement(configData.getBpartnerId2FlatrateTermId().entrySet());

		final CreateCommissionInstanceResult commissionInstanceResult = TestCommissionInstance.builder()
				.orgId(orgId)
				.invoiceCandidateId(InvoiceCandidateId.ofRepoId(10))
				.triggerType(CommissionTriggerType.InvoiceCandidate)
				.triggerDocumentDate(TimeUtil.parseTimestamp("2020-03-21"))
				.pointsBase_Forecasted("999")
				.pointsBase_Invoiceable("999")
				.pointsBase_Invoiced("999")
				.commissionShareTestRecord(
						TestCommissionShare.builder()
								.commissionProductId(commissionProductId)
								.salesRepBPartnerId(bpartnerIdAndFlatrateTermId.getKey())
								.payerBPartnerId(payerId)
								.flatrateTermId(bpartnerIdAndFlatrateTermId.getValue())
								.levelHierarchy(10)
								.isSOTrx(true)
								.build())
				.mostRecentTriggerTimestamp(23L)
				.build()
				.createCommissionData();

		final CommissionShareId salesCommissionShareId = CommissionShareId.ofRepoId(commissionInstanceResult.getBpartnerId2commissionShareId().get(bpartnerIdAndFlatrateTermId.getKey()));
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
						tuple(salesCommissionShareId.getRepoId(), "1568720965.0", "SETTLED", new BigDecimal("22")),
						tuple(salesCommissionShareId.getRepoId(), "1568720975.0", "TO_SETTLE", new BigDecimal("11")));
	}

	private long incAndGetTimestamp()
	{
		currentTimestamp += 10000;
		return currentTimestamp;
	}
}
