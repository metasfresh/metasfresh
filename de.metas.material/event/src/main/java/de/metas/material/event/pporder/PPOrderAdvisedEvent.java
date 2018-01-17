package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PPOrderAdvisedEvent extends AbstractPPOrderEvent
{
	public static final String TYPE = "PPOrderAdvisedEvent";

	/**
	 * If {@code true}, then this event advises the recipient to directly request an actual PP_Order to be created.
	 */
	@Getter
	private final boolean directlyCreatePPOrder;

	@Getter
	private final boolean directlyPickSupply;

	@JsonCreator
	@Builder
	public PPOrderAdvisedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("ppOrder") final PPOrder ppOrder,
			@JsonProperty("supplyRequiredDescriptor") final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@JsonProperty("directlyCreatePPOrder") final boolean directlyCreatePPOrder,
			@JsonProperty("directlyPickSupply") final boolean directlyPickSupply)
	{
		super(eventDescriptor, ppOrder, supplyRequiredDescriptor);

		this.directlyCreatePPOrder = directlyCreatePPOrder;
		this.directlyPickSupply = directlyPickSupply;

	}
}
