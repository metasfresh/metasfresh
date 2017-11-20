package de.metas.material.dispo.service.candidatechange.handler;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.demandWasFound.SupplyRequiredEvent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class SupplyRequiredEventCreator
{
	public SupplyRequiredEvent createMaterialDemandEvent(
			@NonNull final Candidate demandCandidate,
			@NonNull final BigDecimal requiredAdditionalQty)
	{
		verifyCandidateType(demandCandidate);

		final int orderLineId = demandCandidate.getDemandDetail() == null ? 0
				: demandCandidate.getDemandDetail().getOrderLineId();

		final SupplyRequiredEvent materialDemandEvent = SupplyRequiredEvent
				.builder()
				.materialDemandDescriptor(createMaterialDemandDescr(demandCandidate, requiredAdditionalQty, orderLineId))
				.build();
		return materialDemandEvent;
	}

	private void verifyCandidateType(final Candidate demandCandidate)
	{
		final CandidateType candidateType = demandCandidate.getType();
		Preconditions.checkArgument(candidateType == CandidateType.DEMAND || candidateType == CandidateType.STOCK_UP,
				"Given parameter demandCandidate needs to have DEMAND or STOCK_UP as type; demandCandidate=%s", demandCandidate);
	}

	private SupplyRequiredDescriptor createMaterialDemandDescr(
			@NonNull final Candidate candidate,
			@NonNull final BigDecimal qty,
			final int orderLineId)
	{
		return SupplyRequiredDescriptor.builder()
				.demandCandidateId(candidate.getId())
				.eventDescr(new EventDescriptor(candidate.getClientId(), candidate.getOrgId()))
				.materialDescriptor(candidate.getMaterialDescriptor().withQuantity(qty))
				.orderLineId(orderLineId)
				.build();
	}

}
