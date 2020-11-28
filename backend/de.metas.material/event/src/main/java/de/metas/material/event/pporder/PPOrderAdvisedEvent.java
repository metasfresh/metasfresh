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
 * Is a response to a {@link SupplyRequiredEvent} when the material-dispo advises to create a manufacturing order in order to fullfill the requirement.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PPOrderAdvisedEvent extends AbstractPPOrderEvent
{
	public static PPOrderAdvisedEvent cast(@Nullable final AbstractPPOrderEvent ppOrderEvent)
	{
		return (PPOrderAdvisedEvent)ppOrderEvent;
	}

	public static final String TYPE = "PPOrderAdvisedEvent";

	/**
	 * If {@code true}, then this event advises the recipient to directly request an actual PP_Order to be created.
	 */
	@Getter
	private final boolean directlyCreatePPOrder;

	@Getter
	private final boolean directlyPickSupply;

	/**
	 * If {@code false}, then this event advises the recipient to not attempt to identify and update an existing supply candidate, but create a new one.
	 */
	@Getter
	private final boolean tryUpdateExistingCandidate;

	@JsonCreator
	@Builder
	public PPOrderAdvisedEvent(
			@JsonProperty("eventDescriptor") @NonNull final EventDescriptor eventDescriptor,
			@JsonProperty("ppOrder") @NonNull final PPOrder ppOrder,
			@JsonProperty("supplyRequiredDescriptor") @NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@JsonProperty("directlyCreatePPOrder") final boolean directlyCreatePPOrder,
			@JsonProperty("directlyPickSupply") final boolean directlyPickSupply,
			@JsonProperty("tryUpdateExistingCandidate") final boolean tryUpdateExistingCandidate)
	{
		super(eventDescriptor, ppOrder, supplyRequiredDescriptor);

		this.directlyCreatePPOrder = directlyCreatePPOrder;
		this.directlyPickSupply = directlyPickSupply;
		this.tryUpdateExistingCandidate = tryUpdateExistingCandidate;
	}

	public void validate()
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = getSupplyRequiredDescriptor();
		Check.errorIf(supplyRequiredDescriptor == null, "The given ppOrderAdvisedEvent has no supplyRequiredDescriptor");

		final PPOrder ppOrder = getPpOrder();

		Check.errorIf(ppOrder.getPpOrderId() > 0,
				"The given ppOrderAdvisedEvent's ppOrder may not yet have an ID; ppOrder={}", ppOrder);

		final int productPlanningId = ppOrder.getProductPlanningId();
		Check.errorIf(productPlanningId <= 0,
				"The given ppOrderAdvisedEvent event needs to have a ppOrder with a product planning Id; productPlanningId={}", productPlanningId);

		ppOrder.getLines().forEach(this::validateLine);
	}

	private void validateLine(final PPOrderLine ppOrderLine)
	{
		final int productBomLineId = ppOrderLine.getProductBomLineId();
		Check.errorIf(productBomLineId <= 0,
				"The given ppOrderAdvisedEvent event has a ppOrderLine with a product bom line Id; productBomLineId={}; ppOrderLine={}",
				productBomLineId, ppOrderLine);
	}
}
