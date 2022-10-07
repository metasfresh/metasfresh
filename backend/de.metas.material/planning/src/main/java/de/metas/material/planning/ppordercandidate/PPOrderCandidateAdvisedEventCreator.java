/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.planning.ppordercandidate;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent.PPOrderCandidateAdvisedEventBuilder;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.event.MaterialRequest;
import de.metas.material.planning.event.SupplyRequiredHandlerUtils;
import de.metas.material.planning.pporder.PPOrderCandidateDemandMatcher;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PPOrderCandidateAdvisedEventCreator
{
	private final PPOrderCandidateDemandMatcher ppOrderCandidateDemandMatcher;

	private final PPOrderCandidatePojoSupplier ppOrderCandidatePojoSupplier;

	public PPOrderCandidateAdvisedEventCreator(
			@NonNull final PPOrderCandidateDemandMatcher ppOrderCandidateDemandMatcher,
			@NonNull final PPOrderCandidatePojoSupplier ppOrderCandidatePojoSupplier)
	{
		this.ppOrderCandidateDemandMatcher = ppOrderCandidateDemandMatcher;
		this.ppOrderCandidatePojoSupplier = ppOrderCandidatePojoSupplier;
	}

	@NonNull
	public Optional<PPOrderCandidateAdvisedEvent> createPPOrderCandidateAdvisedEvents(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final IMutableMRPContext mrpContext)
	{
		if (!ppOrderCandidateDemandMatcher.matches(mrpContext))
		{
			return Optional.empty();
		}

		final MaterialRequest completeRequest = SupplyRequiredHandlerUtils.mkRequest(supplyRequiredDescriptor, mrpContext);

		final PPOrderCandidate ppOrderCandidate = ppOrderCandidatePojoSupplier.supplyPPOrderCandidatePojoWithoutLines(completeRequest);

		final PPOrderCandidateAdvisedEventBuilder eventBuilder = PPOrderCandidateAdvisedEvent.builder()
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.eventDescriptor(EventDescriptor.ofEventDescriptor(supplyRequiredDescriptor.getEventDescriptor()))
				.ppOrderCandidate(ppOrderCandidate)
				.directlyCreatePPOrder(mrpContext.getProductPlanning().isCreatePlan())
				.tryUpdateExistingCandidate(true);

		Loggables.addLog("Created PPOrderCandidateAdvisedEvent with quantity={}", completeRequest.getQtyToSupply());

		return Optional.of(eventBuilder.build());
	}
}
