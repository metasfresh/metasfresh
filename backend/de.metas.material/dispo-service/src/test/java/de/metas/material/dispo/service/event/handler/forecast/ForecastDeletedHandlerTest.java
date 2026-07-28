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

package de.metas.material.dispo.service.event.handler.forecast;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.ForecastLineDimensionFactory;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateQtyDetailsRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.handler.StockUpCandiateHandler;
import de.metas.material.dispo.service.event.handler.foreacast.ForecastCreatedHandler;
import de.metas.material.dispo.service.event.handler.foreacast.ForecastDeletedHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.forecast.ForecastDeletedEvent;
import de.metas.material.event.forecast.ForecastLine;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.impl.WarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptorWithProductId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(AdempiereTestWatcher.class)
public class ForecastDeletedHandlerTest
{
	private ForecastCreatedHandler forecastCreatedHandler;
	private ForecastDeletedHandler forecastDeletedHandler;
	private AvailableToPromiseRepository stockRepository;

	private int forecastLineId1;
	private int forecastLineId2;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(IWarehouseBL.class, new WarehouseBL());
		ForecastCreatedHandlerTest.ensureWarehouseExists(WAREHOUSE_ID);

		final DimensionService dimensionService = new DimensionService(ImmutableList.of(
				new MDCandidateDimensionFactory(),
				new ForecastLineDimensionFactory()));

		stockRepository = Mockito.mock(AvailableToPromiseRepository.class);
		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);

		forecastLineId1 = createDbForecastLine().getM_ForecastLine_ID();
		forecastLineId2 = createDbForecastLine().getM_ForecastLine_ID();

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();
		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final CandidateQtyDetailsRepository candidateQtyDetailsRepository = new CandidateQtyDetailsRepository();
		final CandidateRepositoryWriteService candidateRepositoryCommands = new CandidateRepositoryWriteService(
				dimensionService, stockChangeDetailRepo, candidateRepositoryRetrieval, candidateQtyDetailsRepository);

		final CandidateChangeService candidateChangeService = new CandidateChangeService(ImmutableList.of(
				new StockUpCandiateHandler(candidateRepositoryCommands, postMaterialEventService, stockRepository)));

		forecastCreatedHandler = new ForecastCreatedHandler(candidateChangeService);
		forecastDeletedHandler = new ForecastDeletedHandler(candidateRepositoryRetrieval, candidateChangeService);
	}

	private I_M_ForecastLine createDbForecastLine()
	{
		final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);
		save(forecastLine);
		return forecastLine;
	}

	/**
	 * Create a STOCK_UP candidate via forecast, then delete it via ForecastDeletedEvent.
	 * Verify the candidate is removed from the DB.
	 */
	@Test
	public void testDeleteWithExistingCandidate()
	{
		final ForecastCreatedEvent createdEvent = buildCreatedEvent(forecastLineId1, "8");
		setupZeroStock(createdEvent);
		forecastCreatedHandler.handleEvent(createdEvent);

		final List<I_MD_Candidate> recordsBefore = DispoTestUtils.retrieveAllRecords();
		assertThat(recordsBefore).hasSize(1);
		assertThat(recordsBefore.get(0).getMD_Candidate_Type()).isEqualTo(CandidateType.STOCK_UP.toString());

		final ForecastDeletedEvent deletedEvent = ForecastDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(1, 2))
				.forecast(createdEvent.getForecast())
				.build();
		forecastDeletedHandler.handleEvent(deletedEvent);

		assertThat(DispoTestUtils.retrieveAllRecords()).isEmpty();
	}

	/**
	 * Create a STOCK_UP candidate for forecastLineId1. Then send a ForecastDeletedEvent for forecastLineId2.
	 * The candidate for forecastLineId1 should remain untouched.
	 */
	@Test
	public void testDeleteWithNoMatchingCandidate()
	{
		final ForecastCreatedEvent createdEvent = buildCreatedEvent(forecastLineId1, "8");
		setupZeroStock(createdEvent);
		forecastCreatedHandler.handleEvent(createdEvent);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(1);

		final ForecastDeletedEvent deletedEvent = ForecastDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(1, 2))
				.forecast(Forecast.builder()
						.forecastId(300)
						.docStatus("VO")
						.forecastLine(buildForecastLine(forecastLineId2, "5"))
						.build())
				.build();
		forecastDeletedHandler.handleEvent(deletedEvent);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(1);
	}

	/**
	 * Create two STOCK_UP candidates (one per forecast line), then delete both via a single
	 * ForecastDeletedEvent containing both lines. Verify all candidates are removed.
	 */
	@Test
	public void testDeleteMultipleForecastLines()
	{
		final ForecastCreatedEvent createdEvent1 = buildCreatedEvent(forecastLineId1, "8");
		setupZeroStock(createdEvent1);
		forecastCreatedHandler.handleEvent(createdEvent1);

		final ForecastCreatedEvent createdEvent2 = buildCreatedEvent(forecastLineId2, "12");
		setupZeroStock(createdEvent2);
		forecastCreatedHandler.handleEvent(createdEvent2);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2);

		final ForecastDeletedEvent deletedEvent = ForecastDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(1, 2))
				.forecast(Forecast.builder()
						.forecastId(200)
						.docStatus("VO")
						.forecastLine(buildForecastLine(forecastLineId1, "8"))
						.forecastLine(buildForecastLine(forecastLineId2, "12"))
						.build())
				.build();
		forecastDeletedHandler.handleEvent(deletedEvent);

		assertThat(DispoTestUtils.retrieveAllRecords()).isEmpty();
	}

	/**
	 * Two forecast lines on different warehouses: one OK, one excluded via MRP_Exclude=Y.
	 * Create candidates for both then send a ForecastDeletedEvent for both.
	 * Only the OK line ever had a candidate; deleting the excluded line is a no-op.
	 */
	@Test
	public void handleEvent_perLineExclusion_onlyOkLineCandidateExisted()
	{
		final WarehouseId whOk = createWarehouse(null);
		final WarehouseId whExcl = createWarehouse("Y");

		final ForecastLine lineOk = ForecastLine.builder()
				.forecastLineId(forecastLineId1)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.warehouseId(whOk)
						.customerId(BPARTNER_ID)
						.quantity(new BigDecimal("5"))
						.date(NOW)
						.build())
				.build();
		final ForecastLine lineExcl = ForecastLine.builder()
				.forecastLineId(forecastLineId2)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptorWithProductId(PRODUCT_ID + 10))
						.warehouseId(whExcl)
						.customerId(BPARTNER_ID)
						.quantity(new BigDecimal("7"))
						.date(NOW)
						.build())
				.build();

		final ForecastCreatedEvent createdEvent = ForecastCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(1, 2))
				.forecast(Forecast.builder()
						.forecastId(200)
						.docStatus("CO")
						.forecastLine(lineOk)
						.forecastLine(lineExcl)
						.build())
				.build();

		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery
				.forDescriptorAndAllPossibleBPartnerIds(lineOk.getMaterialDescriptor());
		when(stockRepository.retrieveAvailableStockQtySum(query)).thenReturn(BigDecimal.ZERO);

		forecastCreatedHandler.handleEvent(createdEvent);

		// Only the OK line produces a candidate.
		final List<I_MD_Candidate> recordsAfterCreate = DispoTestUtils.retrieveAllRecords();
		assertThat(recordsAfterCreate).hasSize(1);
		assertThat(recordsAfterCreate.get(0).getM_Warehouse_ID()).isEqualTo(whOk.getRepoId());

		// Now delete both lines. The excluded line is skipped (no candidate to delete);
		// the OK line's candidate is removed.
		final ForecastDeletedEvent deletedEvent = ForecastDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(1, 2))
				.forecast(createdEvent.getForecast())
				.build();
		forecastDeletedHandler.handleEvent(deletedEvent);

		assertThat(DispoTestUtils.retrieveAllRecords()).isEmpty();
	}

	private static WarehouseId createWarehouse(@Nullable final String mrpExclude)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setMRP_Exclude(mrpExclude);
		InterfaceWrapperHelper.saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	private ForecastCreatedEvent buildCreatedEvent(final int forecastLineId, @NonNull final String qty)
	{
		return ForecastCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(1, 2))
				.forecast(Forecast.builder()
						.forecastId(200)
						.docStatus("CO")
						.forecastLine(buildForecastLine(forecastLineId, qty))
						.build())
				.build();
	}

	private ForecastLine buildForecastLine(final int forecastLineId, @NonNull final String qty)
	{
		return ForecastLine.builder()
				.forecastLineId(forecastLineId)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.warehouseId(WAREHOUSE_ID)
						.customerId(BPARTNER_ID)
						.quantity(new BigDecimal(qty))
						.date(NOW)
						.build())
				.build();
	}

	private void setupZeroStock(@NonNull final ForecastCreatedEvent event)
	{
		final MaterialDescriptor descriptor = event.getForecast().getForecastLines().get(0).getMaterialDescriptor();
		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.forDescriptorAndAllPossibleBPartnerIds(descriptor);
		when(stockRepository.retrieveAvailableStockQtySum(query)).thenReturn(BigDecimal.ZERO);
	}
}
