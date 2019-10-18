package de.metas.contracts.commission.commissioninstance.services.repos;

import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstanceId;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory;
import de.metas.contracts.commission.commissioninstance.services.CommissionHierarchyFactory;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionInstanceRepository;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionRecordStagingService;
import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.commission.model.X_C_Commission_Fact;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.util.JSONObjectMapper;
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

	private static long nextTimestamp = START_TIMESTAMP;

	private static final int C_INVOICE_CANDIDATE_ID = 10;
	private static final int C_BPartner_SalesRep_1_ID = 20;
	private static final int C_BPartner_SalesRep_2_ID = 21;

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
		final ImmutableList<CommissionInstance> result = commissionInstanceRepository.getForInvoiceCandidateId(InvoiceCandidateId.ofRepoId(C_INVOICE_CANDIDATE_ID));
		assertThat(result).hasSize(1);

		SnapshotMatcher.expect(result.get(0)).toMatchSnapshot();
	}

	@Test
	void getForCommissionInstanceId()
	{
		CommissionInstanceId commissionInstanceId = createCommissionData();

		// invoke the method under test
		final CommissionInstance result = commissionInstanceRepository.getForCommissionInstanceId(commissionInstanceId);

		SnapshotMatcher.expect(result).toMatchSnapshot();
	}

	private CommissionInstanceId createCommissionData()
	{
		final I_C_Commission_Instance instanceRecord = newInstance(I_C_Commission_Instance.class);
		instanceRecord.setC_Invoice_Candidate_ID(C_INVOICE_CANDIDATE_ID);
		instanceRecord.setMostRecentTriggerTimestamp(TimeUtil.asTimestamp(createNextInstant()));
		instanceRecord.setPointsBase_Forecasted(TEN);
		instanceRecord.setPointsBase_Invoiceable(ELEVEN);
		instanceRecord.setPointsBase_Invoiced(TWELVE);
		saveRecord(instanceRecord);

		final I_C_Commission_Share shareRecord1 = newInstance(I_C_Commission_Share.class);
		shareRecord1.setC_Commission_Instance_ID(instanceRecord.getC_Commission_Instance_ID());
		shareRecord1.setC_BPartner_SalesRep_ID(C_BPartner_SalesRep_1_ID);
		shareRecord1.setLevelHierarchy(10);
		shareRecord1.setPointsSum_Forecasted(new BigDecimal("1"));
		shareRecord1.setPointsSum_Invoiceable(new BigDecimal("1.1"));
		shareRecord1.setPointsSum_Invoiced(new BigDecimal("1.2"));
		saveRecord(shareRecord1);

		createFactRecord(shareRecord1, X_C_Commission_Fact.COMMISSION_FACT_STATE_FORECASTED, new BigDecimal("10"));
		createFactRecord(shareRecord1, X_C_Commission_Fact.COMMISSION_FACT_STATE_FORECASTED, new BigDecimal("-9"));
		createFactRecord(shareRecord1, X_C_Commission_Fact.COMMISSION_FACT_STATE_INVOICEABLE, new BigDecimal("1.1"));
		createFactRecord(shareRecord1, X_C_Commission_Fact.COMMISSION_FACT_STATE_INVOICED, new BigDecimal("1.2"));

		final I_C_Commission_Share shareRecord2 = newInstance(I_C_Commission_Share.class);
		shareRecord2.setC_Commission_Instance_ID(instanceRecord.getC_Commission_Instance_ID());
		shareRecord2.setC_BPartner_SalesRep_ID(C_BPartner_SalesRep_2_ID);
		shareRecord2.setLevelHierarchy(20);
		shareRecord2.setPointsSum_Forecasted(new BigDecimal("2"));
		shareRecord2.setPointsSum_Invoiceable(new BigDecimal("2.1"));
		shareRecord2.setPointsSum_Invoiced(new BigDecimal("2.2"));
		saveRecord(shareRecord2);

		createFactRecord(shareRecord2, X_C_Commission_Fact.COMMISSION_FACT_STATE_FORECASTED, new BigDecimal("2"));
		createFactRecord(shareRecord2, X_C_Commission_Fact.COMMISSION_FACT_STATE_INVOICEABLE, new BigDecimal("2.1"));
		createFactRecord(shareRecord2, X_C_Commission_Fact.COMMISSION_FACT_STATE_INVOICED, new BigDecimal("10"));
		createFactRecord(shareRecord2, X_C_Commission_Fact.COMMISSION_FACT_STATE_INVOICED, new BigDecimal("-7.8"));

		return CommissionInstanceId.ofRepoId(instanceRecord.getC_Commission_Instance_ID());
	}

	private void createFactRecord(final I_C_Commission_Share shareRecord, final String state, final BigDecimal points)
	{
		final I_C_Commission_Fact factRecord = newInstance(I_C_Commission_Fact.class);
		factRecord.setC_Commission_Share_ID(shareRecord.getC_Commission_Share_ID());
		factRecord.setCommissionFactTimestamp(createNextInstant().toString());
		factRecord.setCommission_Fact_State(state);
		factRecord.setCommissionPoints(points);
		saveRecord(factRecord);
	}

	private Instant createNextInstant()
	{
		final Instant result = Instant.ofEpochMilli(nextTimestamp);
		nextTimestamp += 10000;

		return result;
	}

	@Test
	void save()
	{
		final InputStream objectStream = getClass().getResourceAsStream("/de/metas/contracts/commission/services/repos/CommissionInstance.json");
		assertThat(objectStream).isNotNull();

		final CommissionInstance commissionInstance = JSONObjectMapper.forClass(CommissionInstance.class).readValue(objectStream);

		// invoke the method under test
		final CommissionInstanceId result = commissionInstanceRepository.save(commissionInstance);

		// verify the records that were stored
		final List<I_C_Commission_Instance> instanceRecords = POJOLookupMap.get().getRecords(I_C_Commission_Instance.class);
		assertThat(instanceRecords).hasSize(1)
				.extracting("C_Commission_Instance_ID", "PointsBase_Forecasted", "PointsBase_Invoiceable", "PointsBase_Invoiced", "MostRecentTriggerTimestamp.time")
				.contains(tuple(result.getRepoId(), TEN, ELEVEN, TWELVE, 1568720955000L/* "2019-09-17T11:49:15Z" */));

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
