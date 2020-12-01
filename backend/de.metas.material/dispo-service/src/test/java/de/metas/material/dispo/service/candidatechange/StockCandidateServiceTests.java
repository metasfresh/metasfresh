package de.metas.material.dispo.service.candidatechange;

import de.metas.bpartner.BPartnerId;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService.SaveResult;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.testsupport.MetasfreshAssertions.assertThatModel;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * metasfresh-manufacturing-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ExtendWith(AdempiereTestWatcher.class)
public class StockCandidateServiceTests
{
	private final Instant t1 = Instant.parse("2017-11-22T00:00:00.00Z");
	private final Instant t2 = t1.plus(10, ChronoUnit.MINUTES);
	private final Instant t3 = t1.plus(20, ChronoUnit.MINUTES);
	private final Instant t4 = t1.plus(30, ChronoUnit.MINUTES);
	private final Instant t5 = t1.plus(40, ChronoUnit.MINUTES);
	private final Instant t6 = t1.plus(50, ChronoUnit.MINUTES);

	private StockCandidateService stockCandidateService;
	private CandidateRepositoryWriteService candidateRepositoryWriteService;

	private int parentIdSequence;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		parentIdSequence = 1;

		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval();

		candidateRepositoryWriteService = new CandidateRepositoryWriteService();
		stockCandidateService = new StockCandidateService(
				candidateRepository,
				candidateRepositoryWriteService);
	}

	private SaveResult createStockRecordAtTimeNOW(@Nullable final BPartnerId customerId)
	{
		final boolean reserved = customerId != null; // in stock candidates,reserved coesn't really matter, but we set it for consistency
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.customerId(customerId)
				.reservedForCustomer(reserved)
				.quantity(new BigDecimal("10"))
				.date(NOW)
				.build();

		final Candidate stockCandidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescr)
				.build();
		return candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(stockCandidate);
	}

	/**
	 * creates a stock candidate at a certain time and then adds an earlier one
	 */
	@Test
	void createStockCandidate_2ndWithout_1stWithout_customer()
	{
		final SaveResult nowCandidateResult = createStockRecordAtTimeNOW(null);
		assertThat(nowCandidateResult.getCandidate().getQuantity()).isEqualByComparingTo("10"); // guard

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(BEFORE_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final SaveResult result = invokeCreateStockCandidate(materialDescr);

		assertThat(result.getCandidate().getQuantity()).isEqualByComparingTo(/* 0+1= */ONE);
		assertThat(result.getPreviousQty()).isNull();
	}

	private SaveResult invokeCreateStockCandidate(final MaterialDescriptor materialDescr)
	{
		final Candidate candidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescr)
				.build();

		// invoke the the method under test
		return stockCandidateService.createStockCandidate(candidate);
	}

	@Test
	void createStockCandidate_2ndWith_1stWithout_customer()
	{
		final SaveResult candidate2Result = createStockRecordAtTimeNOW(BPartnerId.ofRepoId(10));
		assertThat(candidate2Result.getCandidate().getQuantity()).isEqualByComparingTo("10"); // guard

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(BEFORE_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final SaveResult candidate1Result = invokeCreateStockCandidate(materialDescr);

		assertThat(candidate1Result.getCandidate().getQuantity()).isEqualByComparingTo("1"); // the first candidate has its 1
		assertThat(candidate1Result.getPreviousQty()).isNull();

		final I_MD_Candidate nowCandidateRecord = load(candidate2Result.getCandidate().getId(), I_MD_Candidate.class);
		assertThat(nowCandidateRecord.getQty()).isEqualByComparingTo("10");  // the original candidate that is now 2nd candidate still has its 10
	}

	/**
	 * Creates a stock candidate without a customerId at a certain time and then adds a later one, also without customerId
	 */
	@Test
	void createStockCandidate_1stWithout_2ndWithout_customer()
	{
		final SaveResult nowCandidateResult = createStockRecordAtTimeNOW(null);
		assertThat(nowCandidateResult.getCandidate().getQuantity()).isEqualByComparingTo("10"); // guard

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(AFTER_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final SaveResult result = invokeCreateStockCandidate(materialDescr);

		assertThat(result.getCandidate().getQuantity()).isEqualByComparingTo(/* 10+1= */"11");
		assertThat(result.getPreviousQty()).isEqualByComparingTo(TEN);

		final I_MD_Candidate nowCandidateRecord = load(nowCandidateResult.getCandidate().getId(), I_MD_Candidate.class);
		assertThat(nowCandidateRecord.getQty()).isEqualByComparingTo("10");  // the original candidate still has its 10
	}

	/**
	 * Creates a stock candidate without a customerId at a certain time and then adds a later one which has a customerId
	 */
	@Test
	void createStockCandidate_1stWithout_2ndWith_customer()
	{
		final SaveResult candidate1Result = createStockRecordAtTimeNOW(null);
		assertThat(candidate1Result.getCandidate().getQuantity()).isEqualByComparingTo("10"); // guard

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.customerId(BPartnerId.ofRepoId(10))
				.warehouseId(WAREHOUSE_ID)
				.date(AFTER_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final SaveResult candidate2Result = invokeCreateStockCandidate(materialDescr);

		assertThat(candidate2Result.getCandidate().getQuantity()).isEqualByComparingTo(/* 10+1= */"11");
		assertThat(candidate2Result.getPreviousQty()).isEqualByComparingTo(TEN);

		final I_MD_Candidate candidate1Record = load(candidate1Result.getCandidate().getId(), I_MD_Candidate.class);
		assertThat(candidate1Record.getQty()).isEqualByComparingTo("10");  // the original candidate still has its 10
	}


	@Test
	void updateQuantity_error_if_missing_candidate_record()
	{
		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.type(CandidateType.DEMAND)
				.clearTransactionDetails()
				.materialDescriptor(createMaterialDescriptor())
				.id(CandidateId.ofRepoId(23))
				.build();

		assertThatThrownBy(() -> stockCandidateService.updateQtyAndDate(candidate))
				.isInstanceOf(RuntimeException.class);
	}

	@Test
	void updateQuantity()
	{
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		candidateRecord.setQty(TEN);
		candidateRecord.setDateProjected(TimeUtil.asTimestamp(t1));
		save(candidateRecord);

		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.type(CandidateType.DEMAND)
				.materialDescriptor(createMaterialDescriptor().withQuantity(BigDecimal.ONE))
				.id(CandidateId.ofRepoId(candidateRecord.getMD_Candidate_ID()))
				.build();

		final SaveResult result = stockCandidateService.updateQtyAndDate(candidate);

		assertThat(result.getCandidate().getQuantity()).isEqualByComparingTo("1");
		assertThat(result.getCandidate().getDate()).isEqualTo(candidate.getDate());

		assertThat(result.getQtyDelta()).isEqualByComparingTo("-9"); // new qty of 1 minus old qty of 10

		assertThat(result.getPreviousQty()).isEqualByComparingTo(TEN);
		assertThat(result.getPreviousTime()).isNotNull();
		assertThat(result.getPreviousTime().getDate()).isEqualTo(t1);
	}

	@Test
	void addOrUpdateStock_simple_case()
	{
		invokeStockCandidateService(t1, "10"); // (t1 => 10)
		invokeStockCandidateService(t2, "-4"); // (t1 => 10), (t2 => 6)
		invokeStockCandidateService(t3, "-3"); // (t1 => 10), (t2 => 6), (t3 => 3)
		invokeStockCandidateService(t4, "2");  // (t1 => 10), (t2 => 6), (t3 => 3), (t4 => 5)

		final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
		assertThat(records).hasSize(4);
		assertDateAndQty(records.get(0), t1, "10");
		assertDateAndQty(records.get(1), t2, "6");
		assertDateAndQty(records.get(2), t3, "3");
		assertDateAndQty(records.get(3), t4, "5");

		// all these stock records need to have the same group-ID
		final int groupId = records.get(0).getMD_Candidate_GroupId();
		assertThat(groupId).isGreaterThan(0);
		records.forEach(r -> assertThat(r.getMD_Candidate_GroupId()).isEqualTo(groupId));
	}

	@Test
	void addOrUpdateStock_with_non_chronological_updates()
	{
		invokeStockCandidateService(t1, "10"); // (t1 => 10)
		{
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(1);
			assertDateAndQty(records.get(0), t1, "10");
		}

		invokeStockCandidateService(t4, "2");  // (t1 => 10), (t4 => 12)
		{
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(2);
			assertDateAndQty(records.get(0), t1, "10");
			assertDateAndQty(records.get(1), t4, "12");
		}

		invokeStockCandidateService(t3, "-3"); // (t1 => 10), (t3 => 7), (t4 => 9)
		{
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(3);
			assertDateAndQty(records.get(0), t1, "10");
			assertDateAndQty(records.get(1), t3, "7");
			assertDateAndQty(records.get(2), t4, "9");
		}

		invokeStockCandidateService(t2, "-4"); // (t1 => 10), (t2 => 6), (t3 => 3), (t4 => 5)
		{
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(4);
			assertDateAndQty(records.get(0), t1, "10");
			assertDateAndQty(records.get(1), t2, "6");
			assertDateAndQty(records.get(2), t3, "3");
			assertDateAndQty(records.get(3), t4, "5");
		}

		// all these stock records need to have the same group-ID
		final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
		final int groupId = records.get(0).getMD_Candidate_GroupId();
		assertThat(groupId).isGreaterThan(0);
		records.forEach(r -> assertThat(r.getMD_Candidate_GroupId()).isEqualTo(groupId));
	}

	/**
	 * Similar to {@link #addOrUpdateStock_simple_case()}, but two invocations have the same timestamp.
	 */
	@Test
	// @Ignore("stockCandidateService can't do this thing alone as of now. It needs to be driven my demandCandidateHandler and supplyCandidateHandler")
	// TODO 3034 refactor&fix
	void addOrUpdateStock_with_overlapping_time()
	{
		{
			invokeStockCandidateService(t1, "10");
			// (t1 => 10)

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(1);
			assertDateAndQty(records.get(0), t1, "10");
		}

		{
			invokeStockCandidateService(t4, "2");
			// (t1 => 10), (t4 => 12)

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(2);
			assertDateAndQty(records.get(0), t1, "10");
			assertDateAndQty(records.get(1), t4, "12");
		}

		{
			invokeStockCandidateService(t3, 10, "-3");
			// (t1 => 10), (t3_1 => 7), (t4 => 9)

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(3);
			assertDateAndQty(records.get(0), t1, "10");
			assertDateAndQty(records.get(1), t3, "7");
			assertDateAndQty(records.get(2), t4, "9");
		}

		{
			invokeStockCandidateService(t3, 20, "-4"); // same time again!
			// (t1 => 10), (t3_1 => 7), (t3_2 => 3), (t4 => 5)

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(4);
			assertDateAndQty(records.get(0), t1, "10");
			assertDateAndQty(records.get(1), t3, "7");
			assertDateAndQty(records.get(2), t3, "3");
			assertDateAndQty(records.get(3), t4, "5");
		}

		// all these stock records need to have the same group-ID
		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThatModel(records.get(0)).hasValueGreaterThanZero(I_MD_Candidate.COLUMN_MD_Candidate_GroupId);

		final int groupId = records.get(0).getMD_Candidate_GroupId();
		assertThat(records).allSatisfy(r -> assertThatModel(r).hasNonNullValue(I_MD_Candidate.COLUMN_MD_Candidate_GroupId, groupId));
	}

	@Test
	void addOrUpdateStock_move_backwards()
	{
		invokeStockCandidateService(t1, "10"); // (t1 => 10)
		invokeStockCandidateService(t3, "-3"); // (t1 => 10), (t3 => 7)

		invokeStockCandidateService(t4, "2");  // (t1 => 10), (t3 => 7), (t4 => 8)
		final SaveResult t5SaveResult = //
				invokeStockCandidateService(t5, "-3"); // (t1 => 10), (t3 => 7), (t4 => 8), (t5 => 5)
		invokeStockCandidateService(t6, "5");  // (t1 => 10), (t3 => 7), (t4 => 9), (t5 => 6), (t6 => 11)

		{ // guard
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.filter(CandidateType.STOCK));
			assertThat(records).hasSize(5);
			assertDateAndQty(records.get(0), t1, "10");
			assertDateAndQty(records.get(1), t3, "7");
			assertDateAndQty(records.get(2), t4, "9");
			assertDateAndQty(records.get(3), t5, "6");
			assertDateAndQty(records.get(4), t6, "11");
		}

		// now "move" t5 => t2
		final Candidate t5ToT2Candidate = t5SaveResult.getCandidate().withDate(t2).withQuantity(new BigDecimal("7"));
		final SaveResult t5ToT2SaveResult = stockCandidateService.updateQtyAndDate(t5ToT2Candidate);

		final Candidate persistentStockCandidateWithDelta = t5ToT2SaveResult.getCandidate().withQuantity(new BigDecimal("-3"));

		final SaveResult appliedSaveResult = SaveResult
				.builder()
				.previousTime(DateAndSeqNo.atTimeNoSeqNo(t5))
				.previousQty(new BigDecimal("-3"))
				.candidate(persistentStockCandidateWithDelta)
				.build();

		// invoke the method under test
		stockCandidateService
				.applyDeltaToMatchingLaterStockCandidates(appliedSaveResult);

		// expecting (t1 => 10), (t2 => 7), (t3 => 4), (t4 => 6), (t6 => 11)
		final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.filter(CandidateType.STOCK));
		assertThat(records).hasSize(5);
		assertDateAndQty(records.get(0), t1, "10");
		assertDateAndQty(records.get(1), t2, "7");
		assertDateAndQty(records.get(2), t3, "4");
		assertDateAndQty(records.get(3), t4, "6");
		assertDateAndQty(records.get(4), t6, "11");
	}

	@Test
	void addOrUpdateStock_move_forwards()
	{
		invokeStockCandidateService(t1, "10"); // (t1 => 10)
		final SaveResult t2SaveResult = //
				invokeStockCandidateService(t2, "-3"); // (t1 => 10), (t2 => 7)
		invokeStockCandidateService(t3, "-3"); // (t1 => 10), (t2 => 7), (t3 => 4)
		invokeStockCandidateService(t4, "2");  // (t1 => 10), (t2 => 7), (t3 => 4), (t4 => 6)
		invokeStockCandidateService(t6, "5");  // (t1 => 10), (t2 => 7), (t3 => 4), (t4 => 6), (t6 => 11)

		{ // guard
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.filter(CandidateType.STOCK));
			assertThat(records).hasSize(5);
			assertDateAndQty(records.get(0), t1, "10");
			assertDateAndQty(records.get(1), t2, "7");
			assertDateAndQty(records.get(2), t3, "4");
			assertDateAndQty(records.get(3), t4, "6");
			assertDateAndQty(records.get(4), t6, "11");
		}

		// now "move" t2 => t5
		final Candidate t2ToT5Candidate = t2SaveResult.getCandidate()
				.withQuantity(new BigDecimal("6"))
				.withDate(t5);
		final SaveResult t2ToT5SaveResult = stockCandidateService.updateQtyAndDate(t2ToT5Candidate);

		final Candidate persistentStockCandidateWithDelta = t2ToT5SaveResult.getCandidate().withQuantity(new BigDecimal("-3"));

		final SaveResult appliedSaveResult = SaveResult
				.builder()
				.previousTime(DateAndSeqNo.atTimeNoSeqNo(t2))
				.previousQty(new BigDecimal("-3"))
				.candidate(persistentStockCandidateWithDelta)
				.build();

		// invoke the method under test
		stockCandidateService
				.applyDeltaToMatchingLaterStockCandidates(appliedSaveResult);

		// expecting (t1 => 10), (t3 => 7), (t4 => 9), (t5 => 6), (t6 => 11)
		final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.filter(CandidateType.STOCK));
		assertThat(records).hasSize(5);
		assertDateAndQty(records.get(0), t1, "10");
		assertDateAndQty(records.get(1), t3, "7");
		assertDateAndQty(records.get(2), t4, "9");
		assertDateAndQty(records.get(3), t5, "6");
		assertDateAndQty(records.get(4), t6, "11");
	}

	private void assertDateAndQty(
			@NonNull final I_MD_Candidate candidateRecord,
			@NonNull final Instant t,
			@NonNull final String qty)
	{
		assertThat(TimeUtil.asInstant(candidateRecord.getDateProjected())).isEqualTo(t);
		assertThat(candidateRecord.getQty()).isEqualByComparingTo(qty);

	}

	private SaveResult invokeStockCandidateService(
			@NonNull final Instant date,
			@NonNull final String qty)
	{
		return invokeStockCandidateService(date, -1, qty);
	}

	/**
	 * @param qty qty to be "injected into the stock. System needs to create a new stock record or update an exiting one
	 * @return the parameter that this method passed to {@link StockCandidateService#applyDeltaToMatchingLaterStockCandidates(SaveResult)}
	 */
	private SaveResult invokeStockCandidateService(
			@NonNull final Instant date,
			final int seqNo,
			@NonNull final String qty)
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal(qty))
				.date(date)
				.build();

		final CandidateBuilder candidateBuilder = Candidate.builder();
		if (seqNo > 0)
		{
			candidateBuilder.seqNo(seqNo);
		}
		final Candidate stockCandidate = candidateBuilder
				.type(CandidateType.SUPPLY) // doesn't really matter, but it's important to note that stockCandidateService will create a stock candidate *for* this candidate
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescr)
				.parentId(CandidateId.ofRepoId(parentIdSequence++)) // don't update stock candidates, but add new ones.
				.build();

		final Candidate stockCandidateToPersist = stockCandidateService.createStockCandidate(stockCandidate).getCandidate();

		final Candidate persistendStockCandidate = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(stockCandidateToPersist)
				.getCandidate();

		final Candidate persistentStockCandidateWithDelta = persistendStockCandidate.withQuantity(new BigDecimal(qty));

		final SaveResult appliedSaveResult = SaveResult
				.builder()
				.candidate(persistentStockCandidateWithDelta)
				.build();
		stockCandidateService
				.applyDeltaToMatchingLaterStockCandidates(appliedSaveResult);
		return appliedSaveResult;
	}
}
