package de.metas.material.dispo.service.event.handler.shipmentschedule;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.common.util.IdConstants;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDeletedEvent;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.math.BigDecimal.ZERO;

/*
 * #%L
 * metasfresh-material-dispo
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
@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class ShipmentScheduleDeletedHandler implements MaterialEventHandler<ShipmentScheduleDeletedEvent>
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleDeletedHandler.class);

	private final CandidateChangeService candidateChangeHandler;
	private final CandidateRepositoryRetrieval candidateRepository;

	public ShipmentScheduleDeletedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepository)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepository = candidateRepository;
	}

	@Override
	public Collection<Class<? extends ShipmentScheduleDeletedEvent>> getHandledEventType()
	{
		return ImmutableList.of(ShipmentScheduleDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final ShipmentScheduleDeletedEvent event)
	{
		final DemandDetailsQuery demandDetailsQuery = DemandDetailsQuery.ofShipmentScheduleId(event.getShipmentScheduleId());
		final CandidatesQuery candidatesQuery = CandidatesQuery
				.builder()
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.SHIPMENT)
				.demandDetailsQuery(demandDetailsQuery)
				.build();

		final Candidate candidate = candidateRepository.retrieveLatestMatchOrNull(candidatesQuery);
		if (candidate == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Found no records to change for shipmentScheduleId={}", event.getShipmentScheduleId());
			return;
		}

		final DemandDetail updatedDemandDetail = candidate
				.getDemandDetail()
				.toBuilder()
				.shipmentScheduleId(IdConstants.NULL_REPO_ID)
				.qty(ZERO)
				.build();
		final Candidate updatedCandidate = candidate
				.withQuantity(ZERO)
				.withBusinessCaseDetail(updatedDemandDetail);
		candidateChangeHandler.onCandidateNewOrChange(updatedCandidate);
	}
}
