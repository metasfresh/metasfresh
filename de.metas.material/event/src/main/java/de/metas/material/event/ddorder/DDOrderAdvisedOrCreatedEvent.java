package de.metas.material.event.ddorder;

import org.eevolution.model.I_DD_Order;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
 * Send by the material planner when it came up with a distribution plan that could be turned into an {@link I_DD_Order}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@Builder
public class DDOrderAdvisedOrCreatedEvent implements MaterialEvent
{
	public static final String TYPE = "DDOrderAdvisedOrCreatedEvent";

	@NonNull
	EventDescriptor eventDescriptor;

	@NonNull
	DDOrder ddOrder;

	/**
	 * Note: this field is a bit redundant because the {@link #getPpOrder()}'s lines contain a network distribution line with this info.<br>
	 * However, the material-dispo code doesn't know or care about how to get to that information.
	 */
	@NonNull
	Integer fromWarehouseId;

	/**
	 * Also check the note about {@link #getFromWarehouseId()}.
	 */
	@NonNull
	Integer toWarehouseId;

	/**
	 * Set to > 0 if this event is about a "real" DDOrder, and not just the advise to create one
	 */
	@JsonProperty
	int groupId;

	/**
	 * Set to not-null mainly if this event is about and "advise" that was created due to a {@link SupplyRequiredEvent}, but also<br>
	 * if this event is about a "wild" PPOrder that was somehow created and has a sale order line ID
	 */
	SupplyRequiredDescriptor supplyRequiredDescriptor;

	/**
	 * If {@code true}, then this event advises the recipient to directly request an actual DD_Order to be created.
	 */
	boolean advisedToCreateDDrder;

	boolean pickIfFeasible;
}
