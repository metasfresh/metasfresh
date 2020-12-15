package de.metas.material.dispo.service.event.handler;

import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
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

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		candidateRepository = Mockito.mock(CandidateRepositoryRetrieval.class);
		stockRepository = Mockito.mock(AvailableToPromiseRepository.class);
		postMaterialEventService = Mockito.mock(PostMaterialEventService.class);

		final CandidateRepositoryWriteService candidateRepositoryCommands = new CandidateRepositoryWriteService();
		forecastCreatedHandler = new ForecastCreatedHandler(
				new CandidateChangeService(ImmutableList.of(
						new StockUpCandiateHandler(
								candidateRepositoryCommands,
								postMaterialEventService,
								stockRepository))));
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
				.forecastLineId(300)
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
				.postEventNow(eventCaptor.capture());

		final SupplyRequiredEvent event = (SupplyRequiredEvent)eventCaptor.getValue();

		final BigDecimal eventQty = event.getSupplyRequiredDescriptor()
				.getMaterialDescriptor()
				.getQuantity();

		assertThat(eventQty)
				.as("eventQty of " + event)
				.isEqualByComparingTo(expectedEventQty);
	}
}
