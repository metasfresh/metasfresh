package de.metas.material.dispo.service.candidatechange.handler;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateQtyDetailsRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler.OnNewOrChangeAdvise;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static de.metas.material.dispo.commons.DispoTestUtils.filter;
import static de.metas.material.dispo.commons.DispoTestUtils.retrieveAllRecords;
import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

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
@ExtendWith(AdempiereTestWatcher.class)
public class SupplyCandidateHandlerTest
{
	private SupplyCandidateHandler supplyCandidateHandler;
	private CandidateRepositoryWriteService candidateRepositoryWriteService;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final DimensionService dimensionService = new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory()));
		// SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final CandidateQtyDetailsRepository candidateQtyDetailsRepository = new CandidateQtyDetailsRepository();
		candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo, candidateRepository, candidateQtyDetailsRepository);

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepository,
				candidateRepositoryWriteService);

		supplyCandidateHandler = new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService);
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
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();
		supplyCandidateHandler.onCandidateNewOrChange(candidate, OnNewOrChangeAdvise.DEFAULT);

		final List<I_MD_Candidate> records = retrieveAllRecords();
		assertThat(records).hasSize(2);
		final I_MD_Candidate stockRecord = filter(CandidateType.STOCK).getFirst();
		final I_MD_Candidate supplyRecord = filter(CandidateType.SUPPLY).getFirst();

		assertThat(supplyRecord.getQty()).isEqualByComparingTo(qty);
		assertThat(stockRecord.getQty()).isEqualByComparingTo(qty); // ..because there was no older record, the "delta" we provided is the current quantity
		assertThat(supplyRecord.getMD_Candidate_Parent_ID()).isEqualTo(stockRecord.getMD_Candidate_ID());

		// note that now, the stock record shall have the same SeqNo as it's "actual" record
		assertThat(supplyRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo());
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
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		final Consumer<Candidate> doTest = candidate -> {

			supplyCandidateHandler.onCandidateNewOrChange(candidate, OnNewOrChangeAdvise.DEFAULT);

			final List<I_MD_Candidate> records = retrieveAllRecords();
			assertThat(records).hasSize(2);
			final I_MD_Candidate stockRecord = filter(CandidateType.STOCK).getFirst();
			final I_MD_Candidate supplyRecord = filter(CandidateType.SUPPLY).getFirst();

			assertThat(supplyRecord.getQty()).isEqualByComparingTo(qty);
			assertThat(stockRecord.getQty()).isEqualByComparingTo(qty); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(supplyRecord.getMD_Candidate_Parent_ID()).isEqualTo(stockRecord.getMD_Candidate_ID());

			// note that now, the stock record shall have the same SeqNo as it's "actual" record
			assertThat(supplyRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo());
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
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		final BiConsumer<Candidate, BigDecimal> doTest = (candidate, exptectedQty) -> {

			supplyCandidateHandler.onCandidateNewOrChange(candidate, OnNewOrChangeAdvise.DEFAULT);

			final List<I_MD_Candidate> records = retrieveAllRecords();
			assertThat(records).hasSize(2);
			final I_MD_Candidate stockRecord = filter(CandidateType.STOCK).getFirst();
			final I_MD_Candidate supplyRecord = filter(CandidateType.SUPPLY).getFirst();

			assertThat(supplyRecord.getQty()).isEqualByComparingTo(exptectedQty);
			assertThat(stockRecord.getQty()).isEqualByComparingTo(exptectedQty); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(supplyRecord.getMD_Candidate_Parent_ID()).isEqualTo(stockRecord.getMD_Candidate_ID());

			// note that now, the stock record shall have the same SeqNo as it's "actual" record
			assertThat(supplyRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo());
		};

		doTest.accept(candidatee, qty/*exptectedQty*/); // 1st invocation
		doTest.accept(candidatee.withQuantity(qty.add(ONE)), qty.add(ONE)/*exptectedQty*/); // 2nd invocation, same candidate
	}

	/**
	 * If this test fails, please first verify whether {@link #testOnSupplyCandidateNewOrChange_noOlderRecords()} works.
	 */
	@Test
	public void onCandidateNewOrChange()
	{

		final MaterialDescriptor olderMaterialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("11"))
				.date(NOW)
				.build();

		final Candidate olderStockCandidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(olderMaterialDescriptor)
				.build();
		candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(olderStockCandidate);

		final MaterialDescriptor materialDescriptoriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("23"))
				.date(AFTER_NOW)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescriptoriptor)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.build();
		supplyCandidateHandler.onCandidateNewOrChange(candidate, OnNewOrChangeAdvise.DEFAULT);

		final List<I_MD_Candidate> records = retrieveAllRecords();
		assertThat(records).hasSize(3);
		final I_MD_Candidate stockRecord = filter(CandidateType.STOCK, AFTER_NOW).getFirst();
		final I_MD_Candidate supplyRecord = filter(CandidateType.SUPPLY).getFirst();

		assertThat(supplyRecord.getQty()).isEqualByComparingTo(new BigDecimal("23"));
		assertThat(CandidateBusinessCase.ofNullableCode(supplyRecord.getMD_Candidate_BusinessCase())).isEqualTo(CandidateBusinessCase.PRODUCTION);
		assertThat(stockRecord.getQty()).isEqualByComparingTo(new BigDecimal("11").add(new BigDecimal("23")));

		// note that now, the stock record shall have the same SeqNo as it's "actual" record
		assertThat(supplyRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo());
	}

	@Test
	public void increase_stock()
	{
		final Candidate candidate = createCandidateWithType(CandidateType.UNEXPECTED_INCREASE);

		supplyCandidateHandler.onCandidateNewOrChange(candidate, OnNewOrChangeAdvise.DEFAULT);

		final List<I_MD_Candidate> allRecords = retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		final I_MD_Candidate unrelatedTransactionCandidate = allRecords.getFirst();
		assertThat(unrelatedTransactionCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_UNEXPECTED_INCREASE);
		assertThat(unrelatedTransactionCandidate.getQty()).isEqualByComparingTo("10");

		final I_MD_Candidate stockCandidate = allRecords.get(1);
		assertThat(stockCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK);
		assertThat(stockCandidate.getQty()).isEqualByComparingTo("10");
		assertThat(unrelatedTransactionCandidate.getMD_Candidate_Parent_ID()).isEqualTo(stockCandidate.getMD_Candidate_ID());
	}

	@Test
	public void onCandidateNewOrChange_split_newCandidateHasCompleteQty()
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("23"))
				.date(AFTER_NOW)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescriptor)
				.businessCase(CandidateBusinessCase.PURCHASE)
				.build();
		final Candidate persistendCandidate = supplyCandidateHandler.onCandidateNewOrChange(candidate, OnNewOrChangeAdvise.DEFAULT).getCandidate();

		assertThat(filter(CandidateType.SUPPLY)).hasSize(1); // guard
		assertThat(filter(CandidateType.STOCK)).hasSize(1); // guard

		final AttributesKey storageAttributesKey = AttributesKey.ofAttributeValueIds(10);
		final int attributeSetInstanceId = 20;
		final MaterialDescriptor alternativeAttribsMaterialDescriptor = materialDescriptor.withStorageAttributes(storageAttributesKey, attributeSetInstanceId);
		final Candidate alternativeAttribsCandidate = persistendCandidate.toBuilder()
				.id(null)
				.parentId(null)
				.materialDescriptor(alternativeAttribsMaterialDescriptor)
				.build();

		final Candidate updatedCandidate = persistendCandidate.withQuantity(ZERO);

		// invoke the method under test
		supplyCandidateHandler.onCandidateNewOrChange(alternativeAttribsCandidate, OnNewOrChangeAdvise.DEFAULT);
		supplyCandidateHandler.onCandidateNewOrChange(updatedCandidate, OnNewOrChangeAdvise.DEFAULT);

		assertThat(filter(CandidateType.SUPPLY)).hasSize(2);
		assertThat(filter(CandidateType.STOCK)).hasSize(2);
	}

	@SuppressWarnings("SameParameterValue")
	private Candidate createCandidateWithType(@NonNull final CandidateType type)
	{
		return Candidate.builder()
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.type(type)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.date(SystemTime.asInstant())
						.warehouseId(WAREHOUSE_ID)
						.quantity(BigDecimal.TEN)
						.build())
				.build();
	}
}
