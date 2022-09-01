package de.metas.material.dispo.service.event.handler;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.ForecastLineDimensionFactory;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.handler.StockUpCandiateHandler;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.forecast.ForecastLine;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_ForecastLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
public class ForecastCreatedHandlerTest
{
	private ForecastCreatedHandler forecastCreatedHandler;
	private CandidateRepositoryRetrieval candidateRepository;
	private AvailableToPromiseRepository stockRepository;
	private PostMaterialEventService postMaterialEventService;
	private DimensionService dimensionService;

	int forecastLineId;
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new MDCandidateDimensionFactory());
		dimensionFactories.add(new ForecastLineDimensionFactory());
		dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		candidateRepository = Mockito.mock(CandidateRepositoryRetrieval.class);
		stockRepository = Mockito.mock(AvailableToPromiseRepository.class);
		postMaterialEventService = Mockito.mock(PostMaterialEventService.class);

		forecastLineId = createForecastline().getM_ForecastLine_ID();

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		final CandidateRepositoryWriteService candidateRepositoryCommands = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo);
		forecastCreatedHandler = new ForecastCreatedHandler(
				new CandidateChangeService(ImmutableList.of(
						new StockUpCandiateHandler(
								candidateRepositoryCommands,
								postMaterialEventService,
								stockRepository))));
	}

	private I_M_ForecastLine createForecastline()
	{
		final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);

		save(forecastLine);

		return forecastLine;
	}
	/**
	 * We create a forecast event with quantity = 8, and there is no projected stock quantity.<br>
	 * We assert that the method under test fires a MaterialDemandEvent with a quantity of 8 - 0 = 8.
	 */
	@Test
	public void testWithoutProjectedQty()
	{
		final ForecastCreatedEvent forecastCreatedEvent = createForecastWithQtyOfEight();
		final MaterialDescriptor materialDescriptorOfFirstAndOnlyForecastLine = forecastCreatedEvent
				.getForecast()
				.getForecastLines()
				.get(0)
				.getMaterialDescriptor();

		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery
				.forDescriptorAndAllPossibleBPartnerIds(materialDescriptorOfFirstAndOnlyForecastLine);

		when(stockRepository.retrieveAvailableStockQtySum(query))
				.thenReturn(BigDecimal.ZERO);

		forecastCreatedHandler.handleEvent(forecastCreatedEvent);
		final List<I_MD_Candidate> result = DispoTestUtils.retrieveAllRecords().stream().sorted(Comparator.comparing(I_MD_Candidate::getSeqNo)).collect(Collectors.toList());

		assertThat(result).hasSize(1);

		final I_MD_Candidate demandCandidate = result.get(0);
		assertThat(demandCandidate.getMD_Candidate_Type()).isEqualTo(CandidateType.STOCK_UP.toString());
		assertThat(demandCandidate.getQty()).isEqualByComparingTo("8");
		assertThat(result).allSatisfy(r -> assertThat(r.getC_BPartner_Customer_ID()).isEqualTo(BPARTNER_ID.getRepoId()));

		assertEventPostedWithQty("8");
	}

	/**
	 * We create a forecast event with quantity = 8, and there is already a projected stock quantity of 3.<br>
	 * We assert that the method under test fires a MaterialDemandEvent with a quantity of 8 - 3 = 5.
	 */
	@Test
	public void testWithProjectedQty()
	{
		final ForecastCreatedEvent forecastCreatedEvent = createForecastWithQtyOfEight();
		final MaterialDescriptor materialDescriptorOfFirstAndOnlyForecastLine = forecastCreatedEvent
				.getForecast()
				.getForecastLines()
				.get(0)
				.getMaterialDescriptor();

		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery
				.forDescriptorAndAllPossibleBPartnerIds(materialDescriptorOfFirstAndOnlyForecastLine);

		when(stockRepository.retrieveAvailableStockQtySum(query))
				.thenReturn(new BigDecimal("3"));

		forecastCreatedHandler.handleEvent(forecastCreatedEvent);
		final List<I_MD_Candidate> result = DispoTestUtils.retrieveAllRecords().stream().sorted(Comparator.comparing(I_MD_Candidate::getSeqNo)).collect(Collectors.toList());

		assertThat(result).hasSize(1);

		assertThat(result.get(0).getMD_Candidate_Type()).isEqualTo(CandidateType.STOCK_UP.toString());
		assertThat(result.get(0).getQty()).isEqualByComparingTo("8");
		assertThat(result).allSatisfy(r -> assertThat(r.getC_BPartner_Customer_ID()).isEqualTo(BPARTNER_ID.getRepoId()));

		assertEventPostedWithQty("5");
	}

	private ForecastCreatedEvent createForecastWithQtyOfEight()
	{
		final ForecastLine forecastLine = ForecastLine.builder()
				.forecastLineId(forecastLineId)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.warehouseId(WAREHOUSE_ID)
						.customerId(BPARTNER_ID)
						.quantity(new BigDecimal("8"))
						.date(NOW)
						.build())
				.build();

		final Forecast forecast = Forecast.builder().forecastId(200)
				.docStatus("docStatus")
				.forecastLine(forecastLine)
				.build();

		return ForecastCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(1, 2))
				.forecast(forecast)
				.build();
	}

	private void assertEventPostedWithQty(@NonNull final String expectedEventQty)
	{
		final ArgumentCaptor<MaterialEvent> eventCaptor = ArgumentCaptor.forClass(MaterialEvent.class);
		verify(postMaterialEventService)
				.postEventNow(eventCaptor.capture(), null);

		final SupplyRequiredEvent event = (SupplyRequiredEvent)eventCaptor.getValue();

		final BigDecimal eventQty = event.getSupplyRequiredDescriptor()
				.getMaterialDescriptor()
				.getQuantity();

		assertThat(eventQty)
				.as("eventQty of " + event)
				.isEqualByComparingTo(expectedEventQty);
	}
}
