package de.metas.material.dispo.service.event.handler;

import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.DemandCandidateDetail;
import de.metas.material.event.ShipmentScheduleEvent;
import lombok.NonNull;

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
public class ShipmentScheduleEventHandler
{
	private final CandidateChangeService candidateChangeHandler;

	public ShipmentScheduleEventHandler(@NonNull final CandidateChangeService candidateChangeHandler)
	{
		this.candidateChangeHandler = candidateChangeHandler;
	}

	public void handleShipmentScheduleEvent(@NonNull final ShipmentScheduleEvent event)
	{
		if (event.isShipmentScheduleDeleted())
		{
			candidateChangeHandler.onCandidateDelete(event.getReference());
			return;
		}

		final Candidate candidate = Candidate.builderForEventDescr(event.getEventDescr())
				.materialDescr(event.getMaterialDescr())
				.type(Type.DEMAND)
				.subType(SubType.SHIPMENT)
				.reference(event.getReference())
				.demandDetail(DemandCandidateDetail.forOrderLineId(event.getOrderLineId()))
				.build();
		candidateChangeHandler.onCandidateNewOrChange(candidate);
	}
}
