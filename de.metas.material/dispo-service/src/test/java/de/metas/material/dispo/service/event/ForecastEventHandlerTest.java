package de.metas.material.dispo.service.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
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
import de.metas.material.dispo.service.CandidateChangeHandler;
import de.metas.material.dispo.service.StockCandidateFactory;
import de.metas.material.event.EventDescr;
import de.metas.material.event.ForecastEvent;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastLine;

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
	private CandidateRepository candidateRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		candidateRepository = new CandidateRepository();

		forecastEventHandler = new ForecastEventHandler(
				new CandidateChangeHandler(
						candidateRepository,
						new StockCandidateFactory(candidateRepository),
						MaterialEventService.createLocalServiceThatIsReadyToUse()));
	}

	@Test
	public void test()
	{
		final ForecastLine forecastLine = ForecastLine.builder()
				.forecastLineId(300)
				.forecastLineDeleted(false)
				.materialDescriptor(MaterialDescriptor.builder()
						.productId(PRODUCT_ID)
						.warehouseId(WAREHOUSE_ID)
						.qty(new BigDecimal("8"))
						.date(DATE)
						.build())
				.reference(TableRecordReference.of("someTable", 300))
				.build();

		final Forecast forecast = Forecast.builder().forecastId(200)
				.docStatus("docStatus")
				.forecastLine(forecastLine)
				.build();

		createAndHandleEvent(forecast);
		final List<I_MD_Candidate> result = DispoTestUtils.retrieveAllRecords().stream().sorted(Comparator.comparing(I_MD_Candidate::getSeqNo)).collect(Collectors.toList());

		assertThat(result).hasSize(4);

		final I_MD_Candidate supplyStockCandidate = result.get(0);
		assertThat(supplyStockCandidate.getMD_Candidate_Type()).isEqualTo(Type.STOCK.toString());
		assertThat(supplyStockCandidate.getMD_Candidate_Parent_ID()).isLessThanOrEqualTo(0);
		assertThat(supplyStockCandidate.getQty())
				.as("The suply record's plus 8 needs to be neutralized by the demand's minus 8")
				.isZero();

		final I_MD_Candidate supplyCandidate = result.get(1);
		assertThat(supplyCandidate.getMD_Candidate_Type()).isEqualTo(Type.SUPPLY.toString());
		assertThat(supplyCandidate.getQty()).isEqualByComparingTo("8");
		assertThat(supplyCandidate.getMD_Candidate_Parent_ID()).isEqualTo(supplyStockCandidate.getMD_Candidate_ID());

		final I_MD_Candidate demandCandidate = result.get(2);
		assertThat(demandCandidate.getMD_Candidate_Type()).isEqualTo(Type.DEMAND.toString());
		assertThat(demandCandidate.getQty()).isEqualByComparingTo("8");
		assertThat(demandCandidate.getMD_Candidate_GroupId()).isEqualTo(supplyCandidate.getMD_Candidate_GroupId());

		final I_MD_Candidate demandStockCandidate = result.get(3);
		assertThat(demandStockCandidate.getMD_Candidate_Type()).isEqualTo(Type.STOCK.toString());
		assertThat(demandStockCandidate.getQty()).isEqualByComparingTo("-8");
		assertThat(demandStockCandidate.getMD_Candidate_Parent_ID())
				.as("demandStockCandidate needs to have demandCandidate as its parent, but has MD_Candidate_Parent_ID=%s; demandStockCandidate=%s",
						demandStockCandidate.getMD_Candidate_Parent_ID(), demandStockCandidate)
				.isEqualTo(demandCandidate.getMD_Candidate_ID());
	}

	private void createAndHandleEvent(final Forecast forecast)
	{
		final ForecastEvent forecastEvent = ForecastEvent.builder()
				.eventDescr(new EventDescr(1, 2))
				.forecast(forecast)
				.build();

		forecastEventHandler.handleForecastEvent(forecastEvent);
	}
}
