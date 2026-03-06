/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.dispo.service.candidatechange.handler;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateQtyDetailsRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(AdempiereTestWatcher.class)
public class StockUpCandiateHandlerTest
{
	private StockUpCandiateHandler handler;
	private AvailableToPromiseRepository stockRepository;
	private PostMaterialEventService postMaterialEventService;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final DimensionService dimensionService = new DimensionService(ImmutableList.of(
				new MDCandidateDimensionFactory()));

		stockRepository = Mockito.mock(AvailableToPromiseRepository.class);
		postMaterialEventService = Mockito.mock(PostMaterialEventService.class);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();
		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final CandidateQtyDetailsRepository candidateQtyDetailsRepository = new CandidateQtyDetailsRepository();
		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService(
				dimensionService, stockChangeDetailRepo, candidateRepository, candidateQtyDetailsRepository);

		handler = new StockUpCandiateHandler(candidateRepositoryWriteService, postMaterialEventService, stockRepository);
	}

	/**
	 * Forecast qty = 10, no existing stock (ATP = 0).
	 * Expect: STOCK_UP candidate persisted, SupplyRequiredEvent fired with qty = 10.
	 */
	@Test
	public void testNewCandidateNoProjectedStock()
	{
		final Candidate candidate = createStockUpCandidate("10");
		setupStockForCandidate(candidate, "0");

		handler.onCandidateNewOrChange(candidate, CandidateHandler.OnNewOrChangeAdvise.DEFAULT);

		final List<I_MD_Candidate> records = DispoTestUtils.filter(CandidateType.STOCK_UP);
		assertThat(records).hasSize(1);
		assertThat(records.get(0).getQty()).isEqualByComparingTo("10");

		assertSupplyRequiredEventPostedWithQty("10");
	}

	/**
	 * Forecast qty = 10, existing stock = 15 (sufficient).
	 * Expect: STOCK_UP candidate persisted, NO SupplyRequiredEvent fired.
	 */
	@Test
	public void testNewCandidateWithSufficientStock()
	{
		final Candidate candidate = createStockUpCandidate("10");
		setupStockForCandidate(candidate, "15");

		handler.onCandidateNewOrChange(candidate, CandidateHandler.OnNewOrChangeAdvise.DEFAULT);

		final List<I_MD_Candidate> records = DispoTestUtils.filter(CandidateType.STOCK_UP);
		assertThat(records).hasSize(1);
		assertThat(records.get(0).getQty()).isEqualByComparingTo("10");

		verify(postMaterialEventService, never()).enqueueEventAfterNextCommit(any());
	}

	/**
	 * Forecast qty = 10, existing stock = 3 (partial).
	 * Expect: STOCK_UP candidate persisted, SupplyRequiredEvent fired with qty = 7 (the gap).
	 */
	@Test
	public void testNewCandidateWithPartialStock()
	{
		final Candidate candidate = createStockUpCandidate("10");
		setupStockForCandidate(candidate, "3");

		handler.onCandidateNewOrChange(candidate, CandidateHandler.OnNewOrChangeAdvise.DEFAULT);

		final List<I_MD_Candidate> records = DispoTestUtils.filter(CandidateType.STOCK_UP);
		assertThat(records).hasSize(1);
		assertThat(records.get(0).getQty()).isEqualByComparingTo("10");

		assertSupplyRequiredEventPostedWithQty("7");
	}

	/**
	 * Call onCandidateNewOrChange twice with the same candidate.
	 * Second call should update the existing record (not create a duplicate).
	 */
	@Test
	public void testUpdateCandidateIdempotent()
	{
		final Candidate candidate = createStockUpCandidate("10");
		setupStockForCandidate(candidate, "0");

		handler.onCandidateNewOrChange(candidate, CandidateHandler.OnNewOrChangeAdvise.DEFAULT);
		handler.onCandidateNewOrChange(candidate, CandidateHandler.OnNewOrChangeAdvise.DEFAULT);

		final List<I_MD_Candidate> records = DispoTestUtils.filter(CandidateType.STOCK_UP);
		assertThat(records).hasSize(1);
		assertThat(records.get(0).getQty()).isEqualByComparingTo("10");
	}

	/**
	 * Verify that the handler only handles STOCK_UP candidate type.
	 */
	@Test
	public void testHandledTypes()
	{
		assertThat(handler.getHandeledTypes())
				.containsExactly(CandidateType.STOCK_UP);
	}

	private Candidate createStockUpCandidate(@NonNull final String qty)
	{
		return Candidate.builder()
				.type(CandidateType.STOCK_UP)
				.businessCase(CandidateBusinessCase.FORECAST)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.warehouseId(WAREHOUSE_ID)
						.quantity(new BigDecimal(qty))
						.date(NOW)
						.build())
				.build();
	}

	private void setupStockForCandidate(@NonNull final Candidate candidate, @NonNull final String stockQty)
	{
		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery
				.forDescriptorAndAllPossibleBPartnerIds(candidate.getMaterialDescriptor());
		when(stockRepository.retrieveAvailableStockQtySum(query)).thenReturn(new BigDecimal(stockQty));
	}

	private void assertSupplyRequiredEventPostedWithQty(@NonNull final String expectedQty)
	{
		final ArgumentCaptor<MaterialEvent> eventCaptor = ArgumentCaptor.forClass(MaterialEvent.class);
		verify(postMaterialEventService).enqueueEventAfterNextCommit(eventCaptor.capture());

		final SupplyRequiredEvent event = (SupplyRequiredEvent)eventCaptor.getValue();
		assertThat(event.getSupplyRequiredDescriptor().getQtyToSupplyBD())
				.as("Supply required qty")
				.isEqualByComparingTo(expectedQty);
	}
}
