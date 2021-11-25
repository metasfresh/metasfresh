/*
 * #%L
 * metasfresh-material-event
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

package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

/**
 * Is a response to a {@link SupplyRequiredEvent} when the material-dispo advises to create a manufacturing order in order to fullfill the requirement.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PPOrderCandidateAdvisedEvent extends AbstractPPOrderCandidateEvent
{
	public static PPOrderCandidateAdvisedEvent cast(@NonNull final AbstractPPOrderCandidateEvent ppOrderCandidateEvent)
	{
		return (PPOrderCandidateAdvisedEvent)ppOrderCandidateEvent;
	}

	public static final String TYPE = "PPOrderCandidateAdvisedEvent";

	/**
	 * If {@code true}, then this event advises the recipient to directly request an actual PP_Order to be created from the candidate.
	 */
	@Getter
	private final boolean directlyCreatePPOrder;

	@Getter
	private final boolean directlyPickIfFeasible;

	/**
	 * If {@code false}, then this event advises the recipient to not attempt to identify and update an existing supply candidate, but create a new one.
	 */
	@Getter
	private final boolean tryUpdateExistingCandidate;

	@JsonCreator
	@Builder
	public PPOrderCandidateAdvisedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("ppOrderCandidate") @NonNull final PPOrderCandidate ppOrderCandidate,
			@JsonProperty("supplyRequiredDescriptor") @Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@JsonProperty("directlyCreatePPOrder") final boolean directlyCreatePPOrder,
			@JsonProperty("directlyPickIfFeasible") final boolean directlyPickIfFeasible,
			@JsonProperty("tryUpdateExistingCandidate") final boolean tryUpdateExistingCandidate)
	{
		super(eventDescriptor, ppOrderCandidate, supplyRequiredDescriptor);

		this.directlyCreatePPOrder = directlyCreatePPOrder;
		this.directlyPickIfFeasible = directlyPickIfFeasible;
		this.tryUpdateExistingCandidate = tryUpdateExistingCandidate;
	}

	public void validate()
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = getSupplyRequiredDescriptor();
		Check.errorIf(supplyRequiredDescriptor == null, "The given ppOrderCandidateAdvisedEvent has no supplyRequiredDescriptor");

		final PPOrderCandidate ppOrderCandidate = getPpOrderCandidate();

		Check.errorIf(ppOrderCandidate.getPpOrderCandidateId() > 0,
					  "The given ppOrderCandidateAdvisedEvent's ppOrderCandidate may not yet have an ID; ppOrderCandidate={}", ppOrderCandidate);

		final int productPlanningId = ppOrderCandidate.getPpOrderData().getProductPlanningId();
		Check.errorIf(productPlanningId <= 0,
					  "The given ppOrderCandidateAdvisedEvent event needs to have a ppOrderCandidate with a product planning Id; productPlanningId={}", productPlanningId);
	}
}
