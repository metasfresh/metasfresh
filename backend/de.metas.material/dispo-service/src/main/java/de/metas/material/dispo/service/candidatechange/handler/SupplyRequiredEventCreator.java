package de.metas.material.dispo.service.candidatechange.handler;

import com.google.common.base.Preconditions;
import de.metas.common.util.IdConstants;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor.SupplyRequiredDescriptorBuilder;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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
	@NonNull
	public static SupplyRequiredEvent createSupplyRequiredEvent(
			@NonNull final Candidate demandCandidate,
			@NonNull final BigDecimal requiredAdditionalQty,
			@Nullable final CandidateId supplyCandidateId)
	{
		verifyCandidateType(demandCandidate);

		final SupplyRequiredDescriptor descriptor = createSupplyRequiredDescriptor(
				demandCandidate,
				requiredAdditionalQty,
				supplyCandidateId);

		return SupplyRequiredEvent.of(descriptor);
	}

	private static void verifyCandidateType(final Candidate demandCandidate)
	{
		final CandidateType candidateType = demandCandidate.getType();
		Preconditions.checkArgument(candidateType == CandidateType.DEMAND || candidateType == CandidateType.STOCK_UP,
				"Given parameter demandCandidate needs to have DEMAND or STOCK_UP as type; demandCandidate=%s", demandCandidate);
	}

	@NonNull
	private static SupplyRequiredDescriptor createSupplyRequiredDescriptor(
			@NonNull final Candidate demandCandidate,
			@NonNull final BigDecimal requiredAdditionalQty,
			@Nullable final CandidateId supplyCandidateId)
	{
		final SupplyRequiredDescriptorBuilder descriptorBuilder = createAndInitSupplyRequiredDescriptor(
				demandCandidate, requiredAdditionalQty);

		if (supplyCandidateId != null)
		{
			descriptorBuilder.supplyCandidateId(supplyCandidateId.getRepoId());
		}

		if (demandCandidate.getDemandDetail() != null)
		{
			final DemandDetail demandDetail = demandCandidate.getDemandDetail();
			descriptorBuilder
					.shipmentScheduleId(IdConstants.toRepoId(demandDetail.getShipmentScheduleId()))
					.forecastId(IdConstants.toRepoId(demandDetail.getForecastId()))
					.forecastLineId(IdConstants.toRepoId(demandDetail.getForecastLineId()))
					.orderId(IdConstants.toRepoId(demandDetail.getOrderId()))
					.orderLineId(IdConstants.toRepoId(demandDetail.getOrderLineId()))
					.subscriptionProgressId(IdConstants.toRepoId(demandDetail.getSubscriptionProgressId()));
		}
		return descriptorBuilder.build();
	}

	@NonNull
	private static SupplyRequiredDescriptorBuilder createAndInitSupplyRequiredDescriptor(
			@NonNull final Candidate candidate,
			@NonNull final BigDecimal qty)
	{
		final PPOrderRef ppOrderRef = candidate.getBusinessCaseDetail(ProductionDetail.class)
				.map(ProductionDetail::getPpOrderRef)
				.orElse(null);

		return SupplyRequiredDescriptor.builder()
				.demandCandidateId(candidate.getId().getRepoId())
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(candidate.getClientAndOrgId(), candidate.getTraceId()))
				.materialDescriptor(candidate.getMaterialDescriptor().withQuantity(qty))
				.fullDemandQty(candidate.getQuantity())
				.ppOrderRef(ppOrderRef)
				.simulated(candidate.isSimulated());
	}

}
