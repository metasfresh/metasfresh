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

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.commons.CandidatesQuery;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryCommands;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.demandWasFound.SupplyRequiredEvent;
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

public class DemandCandiateCangeHandlerTest
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@Mocked
	private MaterialEventService materialEventService;

	private DemandCandiateHandler demandCandiateHandler;

	@Mocked
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryCommands candidateRepositoryCommands = new CandidateRepositoryCommands();

		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepositoryRetrieval, candidateRepositoryCommands);

		demandCandiateHandler = new DemandCandiateHandler(candidateRepositoryRetrieval, candidateRepositoryCommands, materialEventService, stockCandidateService);
	}

	@Test
	public void testOnDemandCandidateCandidateNewOrChange_no_stock()
	{
		final Candidate candidate = createDemandCandidateWithQuantity("23");
		setupRetrieveLatestMatchOrNullAlwaysReturnsNull();
		setupRepositorReturnsQuantityForMaterial("0", candidate.getMaterialDescriptor());

		demandCandiateHandler.onCandidateNewOrChange(candidate);

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
	public void testOnDemandCandidateCandidateNewOrChange_unsufficient_stock()
	{
		final Candidate candidate = createDemandCandidateWithQuantity("23");
		setupRepositorReturnsQuantityForMaterial("10", candidate.getMaterialDescriptor());
		setupRetrieveLatestMatchOrNullAlwaysReturnsNull();

		demandCandiateHandler.onCandidateNewOrChange(candidate);

		assertDemandEventWasFiredWithQuantity("13");

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);

		assertThat(demandRecord.getQty()).isEqualByComparingTo("23");
		assertThat(demandRecord.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);

		assertThat(stockRecord.getQty()).isEqualByComparingTo("-23");
		assertThat(stockRecord.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
		assertThat(stockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());

		assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo() + 1); // when we sort by SeqNo, the demand needs to be first and thus have the smaller value
	}

	@Test
	public void testOnDemandCandidateCandidateNewOrChange_unsufficient_stock_2()
	{
		final Candidate candidate = createDemandCandidateWithQuantity("23");
		setupRepositorReturnsQuantityForMaterial("0", candidate.getMaterialDescriptor());

		final Candidate preexistingStockCandidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.materialDescriptor(candidate.getMaterialDescriptor().withQuantity(BigDecimal.TEN))
				.build();

		// @formatter:off
		new Expectations()
		{{
			candidateRepositoryRetrieval.retrieveLatestMatchOrNull(candidate.createStockQueryBuilder().build());
			times = 1;
			result = preexistingStockCandidate;
		}}; // @formatter:on

		demandCandiateHandler.onCandidateNewOrChange(candidate);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2);

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);

		final I_MD_Candidate newStockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);

		assertThat(newStockRecord.getQty()).isEqualByComparingTo("-13"); // preexistingStock's quantity minus 23
		assertThat(newStockRecord.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
		assertThat(newStockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());
	}

	public void setupRepositorReturnsQuantityForMaterial(final String quantity, final MaterialDescriptor materialDescriptor)
	{
		// @formatter:off
		new Expectations()
		{{
			candidateRepositoryRetrieval.retrieveAvailableStock(materialDescriptor);
			times = 1;
			result = new BigDecimal(quantity);
		}}; // @formatter:on
	}

	public void setupRetrieveLatestMatchOrNullAlwaysReturnsNull()
	{
		// @formatter:off
		new Expectations()
		{{
			candidateRepositoryRetrieval.retrieveLatestMatchOrNull((CandidatesQuery)any);
			result = null;
		}}; // @formatter:on
	}

	public Candidate createDemandCandidateWithQuantity(@NonNull final String quantity)
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.complete(true)
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

	public void assertDemandEventWasFiredWithQuantity(@NonNull final String expectedQty)
	{
		// @formatter:off
		new Verifications()
		{{
			MaterialEvent event;
			materialEventService.fireEvent(event = withCapture());

			assertThat(event).isInstanceOf(SupplyRequiredEvent.class);
			final SupplyRequiredEvent materialDemandEvent = (SupplyRequiredEvent)event;
			final SupplyRequiredDescriptor materialDemandDescriptor = materialDemandEvent.getMaterialDemandDescriptor();
			assertThat(materialDemandDescriptor).isNotNull();

			final MaterialDescriptor materialDescriptorOfEvent = materialDemandDescriptor.getMaterialDescriptor();
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

		demandCandiateHandler.onCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		final I_MD_Candidate unrelatedTransactionCandidate = allRecords.get(0);
		assertThat(unrelatedTransactionCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_UNRELATED_DECREASE);
		assertThat(unrelatedTransactionCandidate.getQty()).isEqualByComparingTo("10");

		final I_MD_Candidate stockCandidate = allRecords.get(1);
		assertThat(stockCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK);
		assertThat(stockCandidate.getQty()).isEqualByComparingTo("-10");
		assertThat(stockCandidate.getMD_Candidate_Parent_ID()).isEqualTo(unrelatedTransactionCandidate.getMD_Candidate_ID());

		// @formatter:off verify that *no* event was fired
		new Verifications()
		{{
			materialEventService.fireEvent((MaterialEvent)any); times = 0;
			materialEventService.fireEventAfterNextCommit((MaterialEvent)any, anyString); times = 0;
		}}; // @formatter:on
	}

	private static Candidate createCandidateWithType(@NonNull final CandidateType type)
	{
		final Candidate candidate = Candidate.builder()
				.type(type)
				.materialDescriptor(MaterialDescriptor.builder()
						.complete(true)
						.productDescriptor(createProductDescriptor())
						.date(SystemTime.asTimestamp())
						.warehouseId(WAREHOUSE_ID)
						.quantity(BigDecimal.TEN)
						.build())
				.build();
		return candidate;
	}
}
