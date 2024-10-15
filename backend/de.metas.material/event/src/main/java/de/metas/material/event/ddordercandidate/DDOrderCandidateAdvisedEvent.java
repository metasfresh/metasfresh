package de.metas.material.event.ddordercandidate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrderRef;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-event
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

/**
 * Send by the material planner when it came up with a distribution plan that could be turned into an DD Order Candidate.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class DDOrderCandidateAdvisedEvent extends AbstractDDOrderCandidateEvent
{
	public static final String TYPE = "DDOrderCandidateAdvisedEvent";

	/**
	 * If {@code true}, then this event advises the recipient to directly request an actual DD_Order_Candidate to be created.
	 */
	private final boolean advisedToCreateDDOrder;
	private final boolean pickIfFeasible;

	@JsonCreator
	@Builder
	public DDOrderCandidateAdvisedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("ddOrderCandidate") final DDOrderCandidateData ddOrderCandidate,
			@JsonProperty("supplyRequiredDescriptor") final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@JsonProperty("advisedToCreateDDOrderCandidate") final boolean advisedToCreateDDOrder,
			@JsonProperty("pickIfFeasible") final boolean pickIfFeasible)
	{
		super(eventDescriptor, ddOrderCandidate, supplyRequiredDescriptor);

		this.advisedToCreateDDOrder = advisedToCreateDDOrder;
		this.pickIfFeasible = pickIfFeasible;
	}

	public static DDOrderCandidateAdvisedEvent cast(@NonNull final AbstractDDOrderCandidateEvent event)
	{
		return (DDOrderCandidateAdvisedEvent)event;
	}

	public DDOrderCandidateAdvisedEvent withPPOrderId(@Nullable final PPOrderId newPPOrderId)
	{
		if (PPOrderId.equals(getPPOrderId(), newPPOrderId))
		{
			return this;
		}

		return new DDOrderCandidateAdvisedEvent(
				getEventDescriptor(),
				getDdOrderCandidate().withPPOrderId(newPPOrderId),
				getSupplyRequiredDescriptorNotNull(),
				advisedToCreateDDOrder,
				pickIfFeasible
		);
	}

	@Nullable
	private PPOrderId getPPOrderId()
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = getSupplyRequiredDescriptor();
		if (supplyRequiredDescriptor == null)
		{
			return null;
		}

		final PPOrderRef ppOrderRef = supplyRequiredDescriptor.getPpOrderRef();
		if (ppOrderRef == null)
		{
			return null;
		}

		return ppOrderRef.getPpOrderId();
	}
}
