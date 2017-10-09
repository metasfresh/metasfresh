package de.metas.material.dispo.service.event.handler;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDemandEvent;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastEvent;
import de.metas.material.event.forecast.ForecastLine;
import lombok.NonNull;
import mockit.Delegate;
import mockit.Expectations;
import mockit.Mocked;

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

public class ForecastEventHandlerTest
{
	@Rule
	public final AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private static final Date DATE = SystemTime.asDate();
	private static final Integer PRODUCT_ID = 31;
	private static final Integer WAREHOUSE_ID = 41;
	private ForecastEventHandler forecastEventHandler;

	@Mocked
	private MaterialEventService materialEventService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepository candidateRepository = new CandidateRepository();

		forecastEventHandler = new ForecastEventHandler(
				new CandidateChangeService(
						candidateRepository,
						new StockCandidateService(candidateRepository),
						materialEventService));
	}

	/**
	 * We create a forecast event with quantity = 8, and there is no projected stock quantity.<br>
	 * We assert that the method under test fires a MaterialDemandEvent with a quantity of 8 - 0 = 8.
	 */
	@Test
	public void testWithoutProjectedQty()
	{
		final ForecastEvent forecastEvent = createForecastWithQtyOfEight();

		// @formatter:off
		new Expectations()
		{{
				materialEventService.fireEvent(with(eventQuantity("8"))); times = 1;
		}};
		// @formatter:on

		forecastEventHandler.handleForecastEvent(forecastEvent);
		final List<I_MD_Candidate> result = DispoTestUtils.retrieveAllRecords().stream().sorted(Comparator.comparing(I_MD_Candidate::getSeqNo)).collect(Collectors.toList());

		assertThat(result).hasSize(1);

		final I_MD_Candidate demandCandidate = result.get(0);
		assertThat(demandCandidate.getMD_Candidate_Type()).isEqualTo(Type.STOCK_UP.toString());
		assertThat(demandCandidate.getQty()).isEqualByComparingTo("8");
	}

	/**
	 * We create a forecast event with quantity = 8, and there is already a projected stock quantity of 3.<br>
	 * We assert that the method under test fires a MaterialDemandEvent with a quantity of 8 - 3 = 5.
	 */
	@Test
	public void testWithProjectedQty()
	{
		final ForecastEvent forecastEvent = createForecastWithQtyOfEight();

		final I_MD_Candidate stockCandidate = newInstance(I_MD_Candidate.class);
		stockCandidate.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK);
		stockCandidate.setM_Product_ID(PRODUCT_ID);
		stockCandidate.setM_Warehouse_ID(WAREHOUSE_ID);
		stockCandidate.setDateProjected(new Timestamp(DATE.getTime()));
		stockCandidate.setQty(new BigDecimal("3"));
		save(stockCandidate);

		// @formatter:off
		new Expectations()
		{{
				materialEventService.fireEvent(with(eventQuantity("5"))); times = 1;
		}};
		// @formatter:on

		forecastEventHandler.handleForecastEvent(forecastEvent);
		final List<I_MD_Candidate> result = DispoTestUtils.retrieveAllRecords().stream().sorted(Comparator.comparing(I_MD_Candidate::getSeqNo)).collect(Collectors.toList());

		assertThat(result).hasSize(2);

		assertThat(result)
				.as("one of the two candidates has to be the stock candidate we created in the setup phase")
				.anySatisfy(r -> assertThat(r.getMD_Candidate_Type()).isEqualTo(Type.STOCK.toString()));

		assertThat(result).anySatisfy(demandCandidate -> {
			assertThat(demandCandidate.getMD_Candidate_Type()).isEqualTo(Type.STOCK_UP.toString());
			assertThat(demandCandidate.getQty()).isEqualByComparingTo("8");
		});
	}

	private ForecastEvent createForecastWithQtyOfEight()
	{
		final ForecastLine forecastLine = ForecastLine.builder()
				.forecastLineId(300)
				.materialDescriptor(MaterialDescriptor.builder()
						.productId(PRODUCT_ID)
						.warehouseId(WAREHOUSE_ID)
						.quantity(new BigDecimal("8"))
						.date(DATE)
						.build())
				.reference(TableRecordReference.of("someTable", 300))
				.build();

		final Forecast forecast = Forecast.builder().forecastId(200)
				.docStatus("docStatus")
				.forecastLine(forecastLine)
				.build();

		return ForecastEvent.builder()
				.eventDescr(new EventDescr(1, 2))
				.forecast(forecast)
				.build();
	}

	private Delegate<MaterialDemandEvent> eventQuantity(@NonNull final String expectedEventQty)
	{
		return new Delegate<MaterialDemandEvent>()
		{
			@SuppressWarnings("unused")
			public boolean verifyQty(@NonNull final MaterialDemandEvent event)
			{
				return event.getMaterialDemandDescr().getMaterialDescriptor().getQuantity().compareTo(new BigDecimal(expectedEventQty)) == 0;
			}
		};
	}

}
