package de.metas.material.dispo.service.candidatechange.handler;

import de.metas.common.util.time.SystemTime;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RepositoryTestHelper;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
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
public class DemandCandiateHandlerTest
{
	private PostMaterialEventService postMaterialEventService;
	private DemandCandiateHandler demandCandidateHandler;
	private AvailableToPromiseRepository availableToPromiseRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService();
		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();

		postMaterialEventService = Mockito.mock(PostMaterialEventService.class);
		availableToPromiseRepository = Mockito.spy(AvailableToPromiseRepository.class);

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryWriteService);

		final SupplyCandidateHandler supplyCandidateHandler = new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService);

		demandCandidateHandler = new DemandCandiateHandler(
				candidateRepositoryRetrieval,
				candidateRepositoryWriteService,
				postMaterialEventService,
				availableToPromiseRepository,
				stockCandidateService,
				supplyCandidateHandler);
	}

	@Test
	public void onCandidateNewOrChange_no_stock()
	{
		final Candidate candidate = createDemandCandidateWithQuantity("23");
		setupRepositoryReturnsQuantityForMaterial("-23", candidate.getMaterialDescriptor());

		demandCandidateHandler.onCandidateNewOrChange(candidate);

		assertDemandEventWasFiredWithQuantity("23");

		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThat(records).hasSize(4);
		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
		final I_MD_Candidate demandStockRecord = DispoTestUtils.retrieveStockCandidate(demandRecord);

		assertThat(demandRecord.getQty()).isEqualByComparingTo("23");
		assertThat(demandStockRecord.getQty()).isEqualByComparingTo("-23"); // ..because there was no older record, the "delta" we provided is the current quantity
		assertThat(demandStockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());
		assertThat(demandStockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo());

		final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);
		assertThat(supplyRecord.getQty()).isEqualByComparingTo("23");
		final I_MD_Candidate supplyStockRecord = DispoTestUtils.retrieveStockCandidate(supplyRecord);
		assertThat(supplyStockRecord.getQty()).isEqualByComparingTo("0"); // ..because there was no older record, the "delta" we provided is the current quantity
		assertThat(supplyStockRecord.getMD_Candidate_ID()).isEqualTo(supplyRecord.getMD_Candidate_Parent_ID());
		assertThat(supplyStockRecord.getSeqNo()).isEqualTo(supplyRecord.getSeqNo());
	}

	/**
	 * demand: 23
	 * available after demand: -13
	 * => min and max=0, so we expect a demand event with 13
	 */
	@Test
	public void onCandidateNewOrChange_unsufficient_stock()
	{
		final Candidate candidate = createDemandCandidateWithQuantity("23");
		setupRepositoryReturnsQuantityForMaterial("-13", candidate.getMaterialDescriptor());

		// when
		demandCandidateHandler.onCandidateNewOrChange(candidate);

		// then
		assertDemandEventWasFiredWithQuantity("13");

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(4);

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
		assertThat(demandRecord).extracting("qty.toString", "StorageAttributesKey")
				.containsExactly("23", STORAGE_ATTRIBUTES_KEY.getAsString());

		final I_MD_Candidate demandStockRecord = DispoTestUtils.retrieveStockCandidate(demandRecord);
		assertThat(demandStockRecord).extracting(
				"MD_Candidate_Parent_ID",
				"Qty.toString",
				"StorageAttributesKey",
				"SeqNo")
				.containsExactly(demandRecord.getMD_Candidate_ID(), "-23", STORAGE_ATTRIBUTES_KEY.getAsString(), demandRecord.getSeqNo());

		final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);
		assertThat(supplyRecord).extracting("qty.toString", "StorageAttributesKey")
				.containsExactly("13", STORAGE_ATTRIBUTES_KEY.getAsString());

		final I_MD_Candidate supplyStockRecord = DispoTestUtils.retrieveStockCandidate(supplyRecord);
		// the stock record's qty is -10 because we were at -23, and 13 were added
		assertThat(supplyStockRecord).extracting("MD_Candidate_ID", "Qty.toString", "StorageAttributesKey")
				.containsExactly(supplyRecord.getMD_Candidate_Parent_ID(), "-10", STORAGE_ATTRIBUTES_KEY.getAsString());
	}

	private void setupRepositoryReturnsQuantityForMaterial(
			@NonNull final String quantity,
			@NonNull final MaterialDescriptor materialDescriptor)
	{
		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.forDescriptorAndAllPossibleBPartnerIds(materialDescriptor);

		Mockito.doReturn(new BigDecimal(quantity))
				.when(availableToPromiseRepository)
				.retrieveAvailableStockQtySum(query);
	}

	private void assertDemandEventWasFiredWithQuantity(@NonNull final String expectedQty)
	{
		final ArgumentCaptor<MaterialEvent> eventCaptor = ArgumentCaptor.forClass(MaterialEvent.class);
		Mockito.verify(postMaterialEventService)
				.postEventAfterNextCommit(eventCaptor.capture());

		final MaterialEvent event = eventCaptor.getValue();

		assertThat(event).isInstanceOf(SupplyRequiredEvent.class);
		final SupplyRequiredEvent materialDemandEvent = (SupplyRequiredEvent)event;
		final SupplyRequiredDescriptor supplyRequiredDescriptor = materialDemandEvent.getSupplyRequiredDescriptor();
		assertThat(supplyRequiredDescriptor).isNotNull();

		final MaterialDescriptor materialDescriptorOfEvent = supplyRequiredDescriptor.getMaterialDescriptor();
		assertThat(materialDescriptorOfEvent.getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(materialDescriptorOfEvent.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(materialDescriptorOfEvent.getQuantity()).isEqualByComparingTo(expectedQty);
	}

	@Test
	public void decrease_stock()
	{
		final Candidate candidate = createCandidateWithType(CandidateType.UNEXPECTED_DECREASE);

		demandCandidateHandler.onCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		final I_MD_Candidate unrelatedTransactionCandidate = allRecords.get(0);
		assertThat(unrelatedTransactionCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_UNEXPECTED_DECREASE);
		assertThat(unrelatedTransactionCandidate.getQty()).isEqualByComparingTo("10");

		final I_MD_Candidate stockCandidate = allRecords.get(1);
		assertThat(stockCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK);
		assertThat(stockCandidate.getQty()).isEqualByComparingTo("-10");
		assertThat(stockCandidate.getMD_Candidate_Parent_ID()).isEqualTo(unrelatedTransactionCandidate.getMD_Candidate_ID());

		Mockito.verify(postMaterialEventService, Mockito.times(0))
				.postEventNow(Mockito.any());
	}

	private static Candidate createCandidateWithType(@NonNull final CandidateType type)
	{
		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
				.type(type)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.date(SystemTime.asInstant())
						.warehouseId(WAREHOUSE_ID)
						.quantity(BigDecimal.TEN)
						.build())
				.build();
		return candidate;
	}

	/**
	 * Like {@link #onCandidateNewOrChange_noOlderRecords_invokeTwice_withDifferent_qty()},
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
				availableToPromiseRepository,
				materialDescriptor,
				"0");

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
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

			assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo());
		};

		doTest.accept(candidate); // first invocation
		doTest.accept(candidate); // second invocation
	}

	/**
	 * like {@link #onCandidateNewOrChange_noOlderRecords_invokeTwiceWithSame()},
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
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(
				availableToPromiseRepository,
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

			assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo());
		};

		// first invocation
		doTest.accept(candidate, qty);

		// second invocation with different quantity
		final Candidate secondInvocationCanddiate = candidate.withQuantity(qty.add(BigDecimal.ONE));
		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(
				availableToPromiseRepository,
				secondInvocationCanddiate.getMaterialDescriptor(),
				"0");

		final BigDecimal secondInvocationExpectedQty = qty.add(BigDecimal.ONE);
		doTest.accept(secondInvocationCanddiate, secondInvocationExpectedQty);
	}

	/**
	 * given: existing candidate
	 * <p>
	 * change its date and invoke the handler under test
	 * <p>
	 * verify that
	 * <li>the candidate's stock-candidate's date also changed</li>
	 * <li>
	 */
	@Test
	public void onCandidateNewOrChange_dateChange()
	{
		final Candidate demandCandidate = createDemandCandidateWithQuantity("30");

		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(
				availableToPromiseRepository,
				demandCandidate.getMaterialDescriptor(),
				"0");

		assertThat(demandCandidate.getMaterialDescriptor().getDate()).isEqualTo(NOW); // guard
		demandCandidateHandler.onCandidateNewOrChange(demandCandidate);
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
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		return candidate;
	}

	@ParameterizedTest
	@CsvSource({
			"0,0,0,0",
			"0,0,-10,10",
			"0,5,-10,15",
			"5,5,5,0",
			"5,6,5,0",
			"5,5,6,0",
			"5,7,6,0",
			"5,5,3,2",
			"5,7,3,4",
			"5,7,-10,17",
	})
	void computeRequiredQty(String givenMin, String givenMax, String when, String then)
	{
		// given
		final MinMaxDescriptor minMaxDescriptor = MinMaxDescriptor.builder()
				.min(new BigDecimal(givenMin))
				.max(new BigDecimal(givenMax)).build();

		// when
		final BigDecimal actual = DemandCandiateHandler.computeRequiredQty(new BigDecimal(when), minMaxDescriptor);

		// then
		assertThat(actual).isEqualByComparingTo(then);
	}
}
