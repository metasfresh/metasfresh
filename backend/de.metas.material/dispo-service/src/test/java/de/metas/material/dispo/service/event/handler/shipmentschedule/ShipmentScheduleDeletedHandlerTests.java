package de.metas.material.dispo.service.event.handler.shipmentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDeletedEvent;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ShipmentScheduleDeletedHandlerTests
{
	private AvailableToPromiseRepository atpRepository;
	private ShipmentScheduleCreatedHandler shipmentScheduleCreatedHandler;

	private ShipmentScheduleDeletedHandler shipmentScheduleDeletedHandler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();

		final CandidateRepositoryWriteService candidateRepositoryCommands = new CandidateRepositoryWriteService();

		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);

		atpRepository = Mockito.spy(AvailableToPromiseRepository.class);

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands);

		final SupplyCandidateHandler supplyCandidateHandler = new SupplyCandidateHandler(candidateRepositoryCommands, stockCandidateService);

		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(ImmutableList.of(
				new DemandCandiateHandler(
						candidateRepositoryRetrieval,
						candidateRepositoryCommands,
						postMaterialEventService,
						atpRepository,
						stockCandidateService,
						supplyCandidateHandler)));

		shipmentScheduleCreatedHandler = new ShipmentScheduleCreatedHandler(
				candidateChangeHandler,
				candidateRepositoryRetrieval);

		shipmentScheduleDeletedHandler = new ShipmentScheduleDeletedHandler(candidateChangeHandler, candidateRepositoryRetrieval);
	}

	@Test
	public void handleEvent()
	{
		final int shipmentScheduleId = ShipmentScheduleCreatedHandlerTests.performTest(shipmentScheduleCreatedHandler, atpRepository);

		final EventDescriptor eventDescriptor = EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID);
		final ShipmentScheduleDeletedEvent shipmentScheduleDeletedEvent = ShipmentScheduleDeletedEvent
				.builder()
				.eventDescriptor(eventDescriptor)
				.shipmentScheduleId(shipmentScheduleId)
				.build();

		shipmentScheduleDeletedHandler.handleEvent(shipmentScheduleDeletedEvent);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(1);

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);

		assertThat(demandRecord.getQty()).isEqualByComparingTo(ZERO);
		assertThat(stockRecord.getQty()).isEqualByComparingTo(ZERO);
	}
}
