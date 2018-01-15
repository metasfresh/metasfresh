package de.metas.material.dispo.service.candidatechange.handler;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.commons.MaterialDescriptor;

/*
 * #%L
 * metasfresh-material-dispo-service
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

public class SupplyCandiateCangeHandlerTest
{

	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private SupplyCandiateHandler supplyCandiateHandler;

	private CandidateRepositoryWriteService candidateRepositoryWriteService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval();
		candidateRepositoryWriteService = new CandidateRepositoryWriteService();

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepository,
				candidateRepositoryWriteService);

		supplyCandiateHandler = new SupplyCandiateHandler(candidateRepository, candidateRepositoryWriteService, stockCandidateService);
	}

	@Test
	public void testOnSupplyCandidateNewOrChange_noOlderRecords()
	{
		final BigDecimal qty = new BigDecimal("23");

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(NOW)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();
		supplyCandiateHandler.onCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThat(records).hasSize(2);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
		final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);

		assertThat(supplyRecord.getQty()).isEqualByComparingTo(qty);
		assertThat(stockRecord.getQty()).isEqualByComparingTo(qty); // ..because there was no older record, the "delta" we provided is the current quantity
		assertThat(supplyRecord.getMD_Candidate_Parent_ID()).isEqualTo(stockRecord.getMD_Candidate_ID());
		assertThat(supplyRecord.getSeqNo() - 1).isEqualTo(stockRecord.getSeqNo()); // when we sort by SeqNo, the stock needs to be first
	}

	@Test
	public void testOnSupplyCandidateNewOrChange_noOlderRecords_invokeTwiceWithSame()
	{
		final BigDecimal qty = new BigDecimal("23");

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(NOW)
				.build();

		final Candidate candidatee = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		final Consumer<Candidate> doTest = candidate -> {

			supplyCandiateHandler.onCandidateNewOrChange(candidate);

			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records).hasSize(2);
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);

			assertThat(supplyRecord.getQty()).isEqualByComparingTo(qty);
			assertThat(stockRecord.getQty()).isEqualByComparingTo(qty); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(supplyRecord.getMD_Candidate_Parent_ID()).isEqualTo(stockRecord.getMD_Candidate_ID());

			assertThat(supplyRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo() + 1); // when we sort by SeqNo, the stock needs to be first and thus have the smaller value
		};

		doTest.accept(candidatee); // 1st invocation
		doTest.accept(candidatee); // 2nd invocation, same candidate
	}

	@Test
	public void onCandidateNewOrChange_noOlderRecords_invokeTwice_withDifferentQuantites()
	{
		final BigDecimal qty = new BigDecimal("23");

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(NOW)
				.build();

		final Candidate candidatee = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		final BiConsumer<Candidate, BigDecimal> doTest = (candidate, exptectedQty) -> {

			supplyCandiateHandler.onCandidateNewOrChange(candidate);

			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records).hasSize(2);
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);

			assertThat(supplyRecord.getQty()).isEqualByComparingTo(exptectedQty);
			assertThat(stockRecord.getQty()).isEqualByComparingTo(exptectedQty); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(supplyRecord.getMD_Candidate_Parent_ID()).isEqualTo(stockRecord.getMD_Candidate_ID());

			assertThat(supplyRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo() + 1); // when we sort by SeqNo, the stock needs to be first and thus have the smaller value
		};

		doTest.accept(candidatee, qty); // 1st invocation
		doTest.accept(candidatee.withQuantity(qty.add(BigDecimal.ONE)), qty.add(BigDecimal.ONE)); // 2nd invocation, same candidate
	}

	/**
	 * If this test fails, please first verify whether {@link #testOnStockCandidateNewOrChanged()} and {@link #testOnSupplyCandidateNewOrChange_noOlderRecords()} work.
	 */
	@Test
	public void onCandidateNewOrChange()
	{
		final BigDecimal olderStockQty = new BigDecimal("11");

		final MaterialDescriptor olderMaterialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(olderStockQty)
				.date(NOW)
				.build();

		final Candidate olderStockCandidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(olderMaterialDescriptor)
				.build();
		candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(olderStockCandidate);

		final BigDecimal supplyQty = new BigDecimal("23");

		final MaterialDescriptor materialDescriptoriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(supplyQty)
				.date(AFTER_NOW)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescriptoriptor)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.build();
		supplyCandiateHandler.onCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThat(records).hasSize(3);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK, AFTER_NOW).get(0);
		final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);

		assertThat(supplyRecord.getQty()).isEqualByComparingTo(supplyQty);
		assertThat(supplyRecord.getMD_Candidate_BusinessCase()).isEqualTo(CandidateBusinessCase.PRODUCTION.toString());
		assertThat(stockRecord.getQty()).isEqualByComparingTo(new BigDecimal("34"));

		assertThat(supplyRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo() + 1); // when we sort by SeqNo, the stock needs to be first and thus have the smaller value
	}

	@Test
	public void increase_stock()
	{
		final Candidate candidate = createCandidateWithType(CandidateType.UNRELATED_INCREASE);

		supplyCandiateHandler.onCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		final I_MD_Candidate unrelatedTransactionCandidate = allRecords.get(0);
		assertThat(unrelatedTransactionCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_UNRELATED_INCREASE);
		assertThat(unrelatedTransactionCandidate.getQty()).isEqualByComparingTo("10");

		final I_MD_Candidate stockCandidate = allRecords.get(1);
		assertThat(stockCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK);
		assertThat(stockCandidate.getQty()).isEqualByComparingTo("10");
		assertThat(unrelatedTransactionCandidate.getMD_Candidate_Parent_ID()).isEqualTo(stockCandidate.getMD_Candidate_ID());
	}

	private Candidate createCandidateWithType(final CandidateType type)
	{
		final Candidate candidate = Candidate.builder()
				.type(type)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.date(SystemTime.asTimestamp())
						.warehouseId(WAREHOUSE_ID)
						.quantity(BigDecimal.TEN)
						.build())
				.build();
		return candidate;
	}
}
