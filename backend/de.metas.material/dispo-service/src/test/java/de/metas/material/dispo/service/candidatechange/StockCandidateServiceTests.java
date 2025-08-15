package de.metas.material.dispo.service.candidatechange;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateQtyDetailsRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.CandidateSaveResult;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
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
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.material.event.EventTestHelper.newMaterialDescriptor;
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
	private final Instant t00 = Instant.parse("2017-11-22T00:00:00.00Z");
	private final Instant t10 = t00.plus(10, ChronoUnit.MINUTES);
	private final Instant t20 = t00.plus(20, ChronoUnit.MINUTES);
	private final Instant t30 = t00.plus(30, ChronoUnit.MINUTES);
	private final Instant t40 = t00.plus(40, ChronoUnit.MINUTES);
	private final Instant t50 = t00.plus(50, ChronoUnit.MINUTES);

	private StockCandidateService stockCandidateService;
	private CandidateRepositoryWriteService candidateRepositoryWriteService;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		final DimensionService dimensionService = new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory()));
		//SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final CandidateQtyDetailsRepository candidateQtyDetailsRepository = new CandidateQtyDetailsRepository();
		candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo, candidateRepository, candidateQtyDetailsRepository);
		stockCandidateService = new StockCandidateService(
				candidateRepository,
				candidateRepositoryWriteService);
	}

	private CandidateSaveResult createStockRecordAtTimeNOW(@Nullable final BPartnerId customerId)
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
		final CandidateSaveResult stockCandidateSaveResult = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(stockCandidate);
		createSupplyCandidateForStockMaterialDescriptor(materialDescr, stockCandidateSaveResult);
		return stockCandidateSaveResult;
	}

	private void createSupplyCandidateForStockMaterialDescriptor(final MaterialDescriptor materialDescr, final CandidateSaveResult stockCandidateSaveResult)
	{
		final Candidate mainCandidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescr.withQuantity(BigDecimal.valueOf(9)))
				.parentId(stockCandidateSaveResult.getCandidate().getId())
				.build();
		candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(mainCandidate);
	}

	/**
	 * creates a stock candidate at a certain time and then adds an earlier one
	 */
	@Test
	void createStockCandidate_2ndWithout_1stWithout_customer()
	{
		final CandidateSaveResult nowCandidateResult = createStockRecordAtTimeNOW(null);
		assertThat(nowCandidateResult.getCandidate().getQuantity()).isEqualByComparingTo("10"); // guard

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(BEFORE_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final CandidateSaveResult result = invokeCreateStockCandidate(materialDescr);

		assertThat(result.getCandidate().getQuantity()).isEqualByComparingTo(/* 0+1= */ONE);
		assertThat(result.getPreviousQty()).isNull();
	}

	private CandidateSaveResult invokeCreateStockCandidate(final MaterialDescriptor materialDescr)
	{
		final Candidate candidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescr)
				.build();

		// invoke the method under test
		return stockCandidateService.createStockCandidate(candidate);
	}

	@Test
	void createStockCandidate_2ndWith_1stWithout_customer()
	{
		final CandidateSaveResult candidate2Result = createStockRecordAtTimeNOW(BPartnerId.ofRepoId(10));
		assertThat(candidate2Result.getCandidate().getQuantity()).isEqualByComparingTo("10"); // guard

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(BEFORE_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final CandidateSaveResult candidate1Result = invokeCreateStockCandidate(materialDescr);

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
		final CandidateSaveResult nowCandidateResult = createStockRecordAtTimeNOW(null);
		assertThat(nowCandidateResult.getCandidate().getQuantity()).isEqualByComparingTo("10"); // guard

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(AFTER_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final CandidateSaveResult result = invokeCreateStockCandidate(materialDescr);

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
		final CandidateSaveResult candidate1Result = createStockRecordAtTimeNOW(null);
		assertThat(candidate1Result.getCandidate().getQuantity()).isEqualByComparingTo("10"); // guard

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.customerId(BPartnerId.ofRepoId(10))
				.warehouseId(WAREHOUSE_ID)
				.date(AFTER_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final CandidateSaveResult candidate2Result = invokeCreateStockCandidate(materialDescr);

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
				.materialDescriptor(newMaterialDescriptor())
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
		candidateRecord.setDateProjected(TimeUtil.asTimestamp(t00));
		save(candidateRecord);

		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.type(CandidateType.DEMAND)
				.materialDescriptor(newMaterialDescriptor().withQuantity(BigDecimal.ONE))
				.id(CandidateId.ofRepoId(candidateRecord.getMD_Candidate_ID()))
				.build();

		final CandidateSaveResult result = stockCandidateService.updateQtyAndDate(candidate);

		assertThat(result.getCandidate().getQuantity()).isEqualByComparingTo("1");
		assertThat(result.getCandidate().getDate()).isEqualTo(candidate.getDate());

		assertThat(result.getQtyDelta()).isEqualByComparingTo("-9"); // new qty of 1 minus old qty of 10

		assertThat(result.getPreviousQty()).isEqualByComparingTo(TEN);
		assertThat(result.getPreviousTime()).isNotNull();
		assertThat(result.getPreviousTime().getDate()).isEqualTo(t00);
	}

	@Test
	void addOrUpdateStock_simple_case()
	{
		invokeStockCandidateService(t00, "10"); // (t1 => 10)
		invokeStockCandidateService(t10, "-4"); // (t1 => 10), (t2 => 6)
		invokeStockCandidateService(t20, "-3"); // (t1 => 10), (t2 => 6), (t3 => 3)
		invokeStockCandidateService(t30, "2");  // (t1 => 10), (t2 => 6), (t3 => 3), (t4 => 5)

		final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
		assertThat(records).hasSize(4);
		assertDateAndQty(records.getFirst(), t00, "10");
		assertDateAndQty(records.get(1), t10, "6");
		assertDateAndQty(records.get(2), t20, "3");
		assertDateAndQty(records.get(3), t30, "5");

		// all these stock records need to have the same group-ID
		final int groupId = records.getFirst().getMD_Candidate_GroupId();
		assertThat(groupId).isGreaterThan(0);
		records.forEach(r -> assertThat(r.getMD_Candidate_GroupId()).isEqualTo(groupId));
	}

	@Test
	void addOrUpdateStock_with_non_chronological_updates()
	{
		invokeStockCandidateService(t00, "10"); // (t1 => 10)
		{
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(1);
			assertDateAndQty(records.getFirst(), t00, "10");
		}

		invokeStockCandidateService(t30, "2");  // (t1 => 10), (t4 => 12)
		{
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(2);
			assertDateAndQty(records.getFirst(), t00, "10");
			assertDateAndQty(records.get(1), t30, "12");
		}

		invokeStockCandidateService(t20, "-3"); // (t1 => 10), (t3 => 7), (t4 => 9)
		{
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(3);
			assertDateAndQty(records.getFirst(), t00, "10");
			assertDateAndQty(records.get(1), t20, "7");
			assertDateAndQty(records.get(2), t30, "9");
		}

		invokeStockCandidateService(t10, "-4"); // (t1 => 10), (t2 => 6), (t3 => 3), (t4 => 5)
		{
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(4);
			assertDateAndQty(records.getFirst(), t00, "10");
			assertDateAndQty(records.get(1), t10, "6");
			assertDateAndQty(records.get(2), t20, "3");
			assertDateAndQty(records.get(3), t30, "5");
		}

		// all these stock records need to have the same group-ID
		final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
		final int groupId = records.getFirst().getMD_Candidate_GroupId();
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
			invokeStockCandidateService(t00, "10");
			// (t1 => 10)

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(1);
			assertDateAndQty(records.getFirst(), t00, "10");
		}

		{
			invokeStockCandidateService(t30, "2");
			// (t1 => 10), (t4 => 12)

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(2);
			assertDateAndQty(records.getFirst(), t00, "10");
			assertDateAndQty(records.get(1), t30, "12");
		}

		{
			invokeStockCandidateService(t20, 10, "-3");
			// (t1 => 10), (t3_1 => 7), (t4 => 9)

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(3);
			assertDateAndQty(records.getFirst(), t00, "10");
			assertDateAndQty(records.get(1), t20, "7");
			assertDateAndQty(records.get(2), t30, "9");
		}

		{
			invokeStockCandidateService(t20, 20, "-4"); // same time again!
			// (t1 => 10), (t3_1 => 7), (t3_2 => 3), (t4 => 5)

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(4);
			assertDateAndQty(records.getFirst(), t00, "10");
			assertDateAndQty(records.get(1), t20, "7");
			assertDateAndQty(records.get(2), t20, "3");
			assertDateAndQty(records.get(3), t30, "5");
		}

		// all these stock records need to have the same group-ID
		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllStockRecords();
		assertThatModel(records.getFirst()).hasValueGreaterThanZero(I_MD_Candidate.COLUMN_MD_Candidate_GroupId);

		final int groupId = records.getFirst().getMD_Candidate_GroupId();
		assertThat(records).allSatisfy(r -> assertThatModel(r).hasNonNullValue(I_MD_Candidate.COLUMN_MD_Candidate_GroupId, groupId));
	}

	@Test
	void addOrUpdateStock_move_backwards()
	{
		invokeStockCandidateService(t00, "10"); // (t00 => 10)
		invokeStockCandidateService(t20, "-3"); // (t20 => 10), (t2 => 7)

		invokeStockCandidateService(t30, "2");  // (t20 => 10), (t2 => 7), (t3 => 9)
		final MainAndStockCandidateSaveResult t4SaveResult = //
				invokeStockCandidateService(t40, "-3"); // (t20 => 10), (t2 => 7), (t3 => 9), (t4 => 6)
		invokeStockCandidateService(t50, "5");  // (t20 => 10), (t2 => 7), (t3 => 9), (t4 => 6), (t5 => 11)

		{ // guard
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(5);
			assertDateAndQty(records.getFirst(), t00, "10");
			assertDateAndQty(records.get(1), t20, "7");
			assertDateAndQty(records.get(2), t30, "9");
			assertDateAndQty(records.get(3), t40, "6");
			assertDateAndQty(records.get(4), t50, "11");
		}

		// now "move" t40 => t10 and increase the qty from -3 to 7
		final Candidate t40ToT10MainCandidate = t4SaveResult.getMainSaveResult().getCandidate().withDate(t10).withQuantity(new BigDecimal("7"));
		candidateRepositoryWriteService.updateCandidateById(t40ToT10MainCandidate);
		// this would be propagated by SupplyCandidateHandler#onCandidateNewOrChange
		final Candidate t40ToT10StockCandidate = t4SaveResult.getStockSaveResult().getCandidate().withDate(t10).withQuantity(new BigDecimal("7"));
		final CandidateSaveResult t4ToT1SaveResult = stockCandidateService.updateQtyAndDate(t40ToT10StockCandidate);

		stockCandidateService
				.applyDeltaToMatchingLaterStockCandidates(t4ToT1SaveResult);

		{ // check intermediate result
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(5);
			assertDateAndQty(records.get(0), t00, "10");
			assertDateAndQty(records.get(1), t10, "17");
			assertDateAndQty(records.get(2), t20, "14");
			assertDateAndQty(records.get(3), t30, "16");
			assertDateAndQty(records.get(4), t50, "21");
		}

		// now "move" t10 => t40 and decrease the qty from 7 to -3

		final CandidateSaveResult backToNegativeMainCandidateSaveResult = candidateRepositoryWriteService.updateCandidateById(t40ToT10MainCandidate.withDate(t40).withQuantity(new BigDecimal("-3")));
		// this would be propagated by SupplyCandidateHandler#onCandidateNewOrChange
		stockCandidateService.updateQtyAndDate(t40ToT10StockCandidate.withDate(t40).withQuantity(new BigDecimal("-3")));

		// invoke the method under test
		stockCandidateService
				.applyDeltaToMatchingLaterStockCandidates(backToNegativeMainCandidateSaveResult);

		// expecting (t20=> 10), (t2 => 7), (t3 => 4), (t4 => 6), (t6 => 11)
		final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
		assertThat(records).hasSize(5);
		assertDateAndQty(records.getFirst(), t00, "10");
		assertDateAndQty(records.get(1), t20, "7");
		assertDateAndQty(records.get(2), t30, "9");
		assertDateAndQty(records.get(3), t40, "6");
		assertDateAndQty(records.get(4), t50, "11");
	}

	@Test
	void addOrUpdateStock_move_forwards()
	{
		invokeStockCandidateService(t00, "10"); // (t1 => 10)
		final MainAndStockCandidateSaveResult t10SaveResult = //
				invokeStockCandidateService(t10, "-3"); // (t1 => 10), (t2 => 7)
		invokeStockCandidateService(t20, "-3"); // (t1 => 10), (t2 => 7), (t3 => 4)
		invokeStockCandidateService(t30, "2");  // (t1 => 10), (t2 => 7), (t3 => 4), (t4 => 6)
		invokeStockCandidateService(t50, "5");  // (t1 => 10), (t2 => 7), (t3 => 4), (t4 => 6), (t6 => 11)

		{ // guard
			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllStockRecords());
			assertThat(records).hasSize(5);
			assertDateAndQty(records.getFirst(), t00, "10");
			assertDateAndQty(records.get(1), t10, "7"); // 10 - 3
			assertDateAndQty(records.get(2), t20, "4"); //  7 - 3
			assertDateAndQty(records.get(3), t30, "6"); //  4 + 2
			assertDateAndQty(records.get(4), t50, "11");//  6 + 5
		}

		// now "move" t10 => t40
		final Candidate t10ToT40Candidate = t10SaveResult.getMainSaveResult().getCandidate().withDate(t40);
		candidateRepositoryWriteService.updateCandidateById(t10ToT40Candidate);
		// this would be propagated by SupplyCandidateHandler#onCandidateNewOrChange
		final Candidate t40ToT10StockCandidate = t10SaveResult.getStockSaveResult().getCandidate().withDate(t40);
		final CandidateSaveResult t10ToT40SaveResult = stockCandidateService.updateQtyAndDate(t40ToT10StockCandidate);

		// invoke the method under test
		stockCandidateService
				.applyDeltaToMatchingLaterStockCandidates(t10ToT40SaveResult);

		// expecting (t1 => 10), (t3 => 7), (t4 => 9), (t5 => 6), (t6 => 11)
		final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.filter(CandidateType.STOCK));
		assertThat(records).hasSize(5);
		assertDateAndQty(records.getFirst(), t00, "10");
		assertDateAndQty(records.get(1), t20, "7"); // 10 - 3 (the t3's "-3")
		assertDateAndQty(records.get(2), t30, "9"); //  7 + 2
		assertDateAndQty(records.get(3), t40, "6"); //  9 - 3 (the -3 of the previous ts that is now t5)
		assertDateAndQty(records.get(4), t50, "11"); // 6 + 5 (as before)
	}

	private void assertDateAndQty(
			@NonNull final I_MD_Candidate candidateRecord,
			@NonNull final Instant t,
			@NonNull final String qty)
	{
		assertThat(TimeUtil.asInstant(candidateRecord.getDateProjected())).isEqualTo(t);
		assertThat(candidateRecord.getQty()).isEqualByComparingTo(qty);

	}

	private MainAndStockCandidateSaveResult invokeStockCandidateService(
			@NonNull final Instant date,
			@NonNull final String qty)
	{
		return invokeStockCandidateService(date, -1, qty);
	}

	/**
	 * @param qty qty to be "injected into the stock. System needs to create a new stock record or update an exiting one
	 * @return the parameter that this method passed to {@link StockCandidateService#applyDeltaToMatchingLaterStockCandidates(CandidateSaveResult)}
	 */
	private MainAndStockCandidateSaveResult invokeStockCandidateService(
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
		final Candidate mainCandidate = candidateBuilder
				.type(CandidateType.SUPPLY) // doesn't really matter, but it's important to note that stockCandidateService will create a stock candidate *for* this candidate
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescr)
				.build();
		final CandidateSaveResult mainCandidateSaveResult = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(mainCandidate);

		final Candidate stockCandidateToPersist = stockCandidateService.createStockCandidate(mainCandidate).getCandidate();

		final Candidate persistedStockCandidate = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(stockCandidateToPersist)
				.getCandidate();

		candidateRepositoryWriteService.updateCandidateById(
				mainCandidateSaveResult.getCandidate()
						.withParentId(persistedStockCandidate.getId()));

		final Candidate persistentStockCandidateWithDelta = persistedStockCandidate.withQuantity(new BigDecimal(qty));

		final CandidateSaveResult appliedSaveResult = CandidateSaveResult
				.builder()
				.candidate(persistentStockCandidateWithDelta)
				.build();
		stockCandidateService
				.applyDeltaToMatchingLaterStockCandidates(appliedSaveResult);
		return MainAndStockCandidateSaveResult.builder()
				.mainSaveResult(mainCandidateSaveResult)
				.stockSaveResult(appliedSaveResult)
				.build();
	}

	@Value
	@Builder
	private static class MainAndStockCandidateSaveResult
	{
		CandidateSaveResult mainSaveResult;
		CandidateSaveResult stockSaveResult;
	}
}
