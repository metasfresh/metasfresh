package de.metas.material.dispo.service.candidatechange;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.testsupport.MetasfreshAssertions.assertThatModel;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.NonNull;

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

public class StockCandidateServiceTests
{
	private final Timestamp t1 = TimeUtil.parseTimestamp("2017-11-22 00:00");
	private final Timestamp t2 = TimeUtil.addMinutes(t1, 10);
	private final Timestamp t3 = TimeUtil.addMinutes(t1, 20);
	private final Timestamp t4 = TimeUtil.addMinutes(t1, 30);

	private StockCandidateService stockCandidateService;
	private CandidateRepositoryWriteService candidateRepositoryCommands;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval();

		candidateRepositoryCommands = new CandidateRepositoryWriteService();
		stockCandidateService = new StockCandidateService(
				candidateRepository,
				candidateRepositoryCommands);
	}

	private void createStockRecordAtTimeNOW()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("10"))
				.date(NOW)
				.build();

		final Candidate stockCandidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.build();
		candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(stockCandidate);
	}

	@Test
	public void createStockCandidate_before_existing()
	{
		createStockRecordAtTimeNOW();

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(BEFORE_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.build();

		final Candidate newCandidateBefore = stockCandidateService.createStockCandidate(candidate);
		assertThat(newCandidateBefore.getQuantity()).isEqualByComparingTo(/* 0+1= */"1");
	}

	@Test
	public void createStockCandidate_after_existing()
	{
		createStockRecordAtTimeNOW();

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(AFTER_NOW)
				.quantity(BigDecimal.ONE)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.build();

		final Candidate newCandidateAfter = stockCandidateService.createStockCandidate(candidate);
		assertThat(newCandidateAfter.getQuantity()).isEqualByComparingTo(/* 10+1= */"11");
	}

	@Test(expected = RuntimeException.class)
	public void updateQuantity_error_if_missing_candidate_record()
	{
		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.materialDescriptor(createMaterialDescriptor())
				.id(23)
				.build();

		stockCandidateService.updateQty(candidate);
	}

	@Test
	public void updateQuantity()
	{
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		candidateRecord.setQty(BigDecimal.TEN);
		save(candidateRecord);
		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.materialDescriptor(createMaterialDescriptor().withQuantity(BigDecimal.ONE))
				.id(candidateRecord.getMD_Candidate_ID())
				.build();

		final Candidate result = stockCandidateService.updateQty(candidate);

		assertThat(result.getQuantity()).isEqualByComparingTo("-9"); // new qty of 1 minus old qty of 10
	}

	@Test
	public void addOrUpdateStock_with_non_chronological_updates()
	{
		invokeAddOrUpdateStock(t1, "10"); // (t1 => 10)
		invokeAddOrUpdateStock(t4, "2");  // (t1 => 10),                        (t4 => 12)
		invokeAddOrUpdateStock(t3, "-3"); // (t1 => 10),            (t3 =>  7), (t4 =>  9)
		invokeAddOrUpdateStock(t2, "-4"); // (t1 => 10), (t2 => 6), (t3 =>  3), (t4 =>  5)

		final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
		assertThat(records).hasSize(4);

		assertThat(records.get(0).getDateProjected()).isEqualTo(t1);
		assertThat(records.get(0).getQty()).isEqualByComparingTo("10");

		assertThat(records.get(1).getDateProjected()).isEqualTo(t2);
		assertThat(records.get(1).getQty()).isEqualByComparingTo("6");

		assertThat(records.get(2).getDateProjected()).isEqualTo(t3);
		assertThat(records.get(2).getQty()).isEqualByComparingTo("3");

		assertThat(records.get(3).getDateProjected()).isEqualTo(t4);
		assertThat(records.get(3).getQty()).isEqualByComparingTo("5");

		// all these stock records need to have the same group-ID
		final int groupId = records.get(0).getMD_Candidate_GroupId();
		assertThat(groupId, greaterThan(0));
		records.forEach(r -> assertThat(r.getMD_Candidate_GroupId()).isEqualTo(groupId));
	}

	/**
	 * Similar to {@link #testUpdateStockDifferentTimes()}, but two invocations have the same timestamp.
	 */
	@Test
	@Ignore("stockCandidateService can't do this thing alone as of now. It needs to be driven my demandCandidateHandler and supplyCandidateHandler")
	// TODO 3034 refactor&fix
	public void addOrUpdateStock_with_overlapping_time()
	{
		{
			invokeAddOrUpdateStock(t1, "10");

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(1);
			assertThat(records.get(0).getDateProjected()).isEqualTo(t1);
			assertThat(records.get(0).getQty()).isEqualByComparingTo("10");
		}

		{
			invokeAddOrUpdateStock(t4, "2");

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(2);
			assertThat(records.get(0).getDateProjected()).isEqualTo(t1);
			assertThat(records.get(0).getQty()).isEqualByComparingTo("10");
			assertThat(records.get(1).getDateProjected()).isEqualTo(t4);
			assertThat(records.get(1).getQty()).isEqualByComparingTo("12");
		}

		{
			invokeAddOrUpdateStock(t3, "-3");

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(3);

			assertThat(records.get(0).getDateProjected()).isEqualTo(t1);
			assertThat(records.get(0).getQty()).isEqualByComparingTo("10");

			assertThat(records.get(1).getDateProjected()).isEqualTo(t3);
			assertThat(records.get(1).getQty()).isEqualByComparingTo("7");

			assertThat(records.get(2).getDateProjected()).isEqualTo(t4);
			assertThat(records.get(2).getQty()).isEqualByComparingTo("9");
		}

		{
			invokeAddOrUpdateStock(t3, "-4"); // same time again!

			final List<I_MD_Candidate> records = DispoTestUtils.sortByDateProjected(DispoTestUtils.retrieveAllRecords());
			assertThat(records).hasSize(3);

			assertThat(records.get(0).getDateProjected()).isEqualTo(t1);
			assertThat(records.get(0).getQty()).isEqualByComparingTo("10");

			assertThat(records.get(1).getDateProjected()).isEqualTo(t3);
			assertThat(records.get(1).getQty()).isEqualByComparingTo("3");

			assertThat(records.get(2).getDateProjected()).isEqualTo(t4);
			assertThat(records.get(2).getQty()).isEqualByComparingTo("5");
		}

		// all these stock records need to have the same group-ID
		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThatModel(records.get(0)).hasValueGreaterThanZero(I_MD_Candidate.COLUMN_MD_Candidate_GroupId);

		final int groupId = records.get(0).getMD_Candidate_GroupId();
		assertThat(records).allSatisfy(r -> assertThatModel(r).hasNonNullValue(I_MD_Candidate.COLUMN_MD_Candidate_GroupId, groupId));
	}

	/**
	 *
	 * @param date
	 * @param qty qty to be "injected into the stock. System needs to create a new stock record or update an exiting one
	 */
	private void invokeAddOrUpdateStock(@NonNull final Date date, @NonNull final String qty)
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal(qty))
				.date(date)
				.build();

		final Candidate stockCandidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.build();

		final Candidate stockCandidateToPersist = stockCandidateService.createStockCandidate(stockCandidate);

		final Candidate persistendStockCandidate = candidateRepositoryCommands
				.addOrUpdateOverwriteStoredSeqNo(stockCandidateToPersist);

		final Candidate persistendStockCandidateWithDelta = persistendStockCandidate.withQuantity(new BigDecimal(qty));
		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(persistendStockCandidateWithDelta);
	}
}
