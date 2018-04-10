package de.metas.material.dispo.service.candidatechange.handler;

import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
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
import de.metas.material.dispo.commons.RepositoryTestHelper;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import lombok.NonNull;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

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

public class DemandCandiateHandlerTest
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@Mocked
	private PostMaterialEventService postMaterialEventService;

	private DemandCandiateHandler demandCandidateHandler;

	@Mocked
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@Mocked
	private AvailableToPromiseRepository stockRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryWriteService candidateRepositoryCommands = new CandidateRepositoryWriteService();

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands);

		demandCandidateHandler = new DemandCandiateHandler(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands,
				postMaterialEventService,
				stockRepository,
				stockCandidateService);
	}

	@Test
	public void onCandidateNewOrChange_no_stock()
	{
		final Candidate candidate = createDemandCandidateWithQuantity("23");
		setupRetrieveLatestMatchOrNullAlwaysReturnsNull();
		setupRepositoryReturnsQuantityForMaterial("-23", candidate.getMaterialDescriptor());

		demandCandidateHandler.onCandidateNewOrChange(candidate);

		assertDemandEventWasFiredWithQuantity("23");

		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThat(records).hasSize(2);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);

		assertThat(demandRecord.getQty()).isEqualByComparingTo("23");
		assertThat(stockRecord.getQty()).isEqualByComparingTo("-23"); // ..because there was no older record, the "delta" we provided is the current quantity
		assertThat(stockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());

		assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo() + 1); // when we sort by SeqNo, the demand needs to be first and thus have the smaller value
	}

	@Test
	public void onCandidateNewOrChange_unsufficient_stock()
	{
		final Candidate candidate = createDemandCandidateWithQuantity("23");
		setupRepositoryReturnsQuantityForMaterial("-13", candidate.getMaterialDescriptor());
		setupRetrieveLatestMatchOrNullAlwaysReturnsNull();

		demandCandidateHandler.onCandidateNewOrChange(candidate);

		assertDemandEventWasFiredWithQuantity("13");

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);

		assertThat(demandRecord.getQty()).isEqualByComparingTo("23");
		assertThat(demandRecord.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY.getAsString());

		assertThat(stockRecord.getQty()).isEqualByComparingTo("-23");
		assertThat(stockRecord.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY.getAsString());
		assertThat(stockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());

		assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo() + 1); // when we sort by SeqNo, the demand needs to be first and thus have the smaller value
	}


	private void setupRepositoryReturnsQuantityForMaterial(
			@NonNull final String quantity,
			@NonNull final MaterialDescriptor materialDescriptor)
	{
		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.forDescriptorAndAllPossibleBPartnerIds(materialDescriptor);

		// @formatter:off
		new Expectations()
		{{
			stockRepository.retrieveAvailableStockQtySum(query);
			times = 1;
			result = new BigDecimal(quantity);
		}}; // @formatter:on
	}

	private void setupRetrieveLatestMatchOrNullAlwaysReturnsNull()
	{
		// @formatter:off
		new Expectations()
		{{
			candidateRepositoryRetrieval.retrieveLatestMatchOrNull((CandidatesQuery)any);
			result = null;
		}}; // @formatter:on
	}

	private Candidate createDemandCandidateWithQuantity(@NonNull final String quantity)
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal(quantity))
				.date(NOW)
				.build();
		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		return candidate;
	}

	private void assertDemandEventWasFiredWithQuantity(@NonNull final String expectedQty)
	{
		// @formatter:off
		new Verifications()
		{{
			MaterialEvent event;
			postMaterialEventService.postEventAfterNextCommit(event = withCapture());

			assertThat(event).isInstanceOf(SupplyRequiredEvent.class);
			final SupplyRequiredEvent materialDemandEvent = (SupplyRequiredEvent)event;
			final SupplyRequiredDescriptor supplyRequiredDescriptor = materialDemandEvent.getSupplyRequiredDescriptor();
			assertThat(supplyRequiredDescriptor).isNotNull();

			final MaterialDescriptor materialDescriptorOfEvent = supplyRequiredDescriptor.getMaterialDescriptor();
			assertThat(materialDescriptorOfEvent.getProductId()).isEqualTo(PRODUCT_ID);
			assertThat(materialDescriptorOfEvent.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
			assertThat(materialDescriptorOfEvent.getQuantity()).isEqualByComparingTo(expectedQty);
		}}; // @formatter:on
	}

	@Test
	public void decrease_stock()
	{
		setupRetrieveLatestMatchOrNullAlwaysReturnsNull();
		final Candidate candidate = createCandidateWithType(CandidateType.UNRELATED_DECREASE);

		demandCandidateHandler.onCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		final I_MD_Candidate unrelatedTransactionCandidate = allRecords.get(0);
		assertThat(unrelatedTransactionCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_UNRELATED_DECREASE);
		assertThat(unrelatedTransactionCandidate.getQty()).isEqualByComparingTo("10");

		final I_MD_Candidate stockCandidate = allRecords.get(1);
		assertThat(stockCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK);
		assertThat(stockCandidate.getQty()).isEqualByComparingTo("-10");
		assertThat(stockCandidate.getMD_Candidate_Parent_ID()).isEqualTo(unrelatedTransactionCandidate.getMD_Candidate_ID());

		// @formatter:off verify that no event was fired
		new Verifications()
		{{
			postMaterialEventService.postEventNow((MaterialEvent)any); times = 0;
		}}; // @formatter:on
	}

	private static Candidate createCandidateWithType(@NonNull final CandidateType type)
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

	/**
	 * Like {@link #testOnDemandCandidateCandidateNewOrChange_noOlderRecords()},
	 * but the method under test is called two times. We expect the code to recognize this and not count the 2nd invocation.
	 */
	@Test
	public void onCandidateNewOrChange_noOlderRecords_invokeTwiceWithSame()
	{
		final BigDecimal qty = new BigDecimal("23");

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(NOW)
				.build();

		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(
				stockRepository,
				materialDescriptor,
				"0");

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		final Consumer<Candidate> doTest = candidateUnderTest -> {

			demandCandidateHandler.onCandidateNewOrChange(candidateUnderTest);

			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records).hasSize(2);
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);

			assertThat(demandRecord.getQty()).isEqualByComparingTo(qty);
			assertThat(stockRecord.getQty()).isEqualByComparingTo(qty.negate()); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(stockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());

			assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo() + 1); // when we sort by SeqNo, the demand needs to be first and thus have a smaller value
		};

		doTest.accept(candidate); // first invocation
		doTest.accept(candidate); // second invocation
	}

	/**
	 * like {@link #testOnDemandCandidateCandidateNewOrChange_noOlderRecords_invokeTwiceWitDifferent()},
	 * but on the 2nd invocation, a different demand-quantity is used.
	 */
	@Test
	public void onCandidateNewOrChange_noOlderRecords_invokeTwice_withDifferent_qty()
	{
		final BigDecimal qty = new BigDecimal("23");

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(NOW)
				.build();
		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(
				stockRepository,
				materialDescriptor,
				"0");

		final BiConsumer<Candidate, BigDecimal> doTest = (candidateUnderTest, expectedQty) -> {
			demandCandidateHandler.onCandidateNewOrChange(candidateUnderTest);

			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records).hasSize(2);
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);

			assertThat(demandRecord.getQty()).isEqualByComparingTo(expectedQty);
			assertThat(stockRecord.getQty()).isEqualByComparingTo(expectedQty.negate());
			assertThat(stockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());

			assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo() + 1); // when we sort by SeqNo, the demand needs to be first and thus have the smaller number
		};

		// first invocation
		doTest.accept(candidate, qty);

		// second invocation with different quantity
		final Candidate secondInvocationCanddiate = candidate.withQuantity(qty.add(BigDecimal.ONE));
		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(
				stockRepository,
				secondInvocationCanddiate.getMaterialDescriptor(),
				"0");

		final BigDecimal secondInvocationExpectedQty = qty.add(BigDecimal.ONE);
		doTest.accept(secondInvocationCanddiate, secondInvocationExpectedQty);
	}
}
