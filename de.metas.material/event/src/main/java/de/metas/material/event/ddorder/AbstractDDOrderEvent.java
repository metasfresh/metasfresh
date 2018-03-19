package de.metas.material.event.ddorder;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.eevolution.model.I_PP_Order;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventUtils;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
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
/**
 * Send by the material planner when it came up with a brilliant distribution plan that could be turned into an {@link I_PP_Order}<br>
 * <b>or</or> if a ddOrder was actually created.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public abstract class AbstractDDOrderEvent implements MaterialEvent
{
	@NonNull
	private final EventDescriptor eventDescriptor;

	@NonNull
	private final DDOrder ddOrder;

	/**
	 * Note: this field is a bit redundant because the {@link #getPpOrder()}'s lines contain a network distribution line with this info.<br>
	 * However, the material-dispo code doesn't know or care about how to get to that information.
	 */
	private final int fromWarehouseId;

	/**
	 * Also check the note about {@link #getFromWarehouseId()}.
	 */
	private final int toWarehouseId;

	/**
	 * Set to not-null mainly if this event is about and "advise" that was created due to a {@link SupplyRequiredEvent}, but also<br>
	 * if this event is about a "wild" DDOrder that was somehow created and has a sales order line ID
	 */
	private final SupplyRequiredDescriptor supplyRequiredDescriptor;

	public AbstractDDOrderEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final DDOrder ddOrder,
			final int fromWarehouseId,
			final int toWarehouseId,
			@Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		this.eventDescriptor = eventDescriptor;
		this.ddOrder = ddOrder;
		this.fromWarehouseId = fromWarehouseId;
		this.toWarehouseId = toWarehouseId;
		this.supplyRequiredDescriptor = supplyRequiredDescriptor;
	}

	@OverridingMethodsMustInvokeSuper
	public void validate()
	{
		MaterialEventUtils.checkIdGreaterThanZero("fromWarehouseId", fromWarehouseId);
		MaterialEventUtils.checkIdGreaterThanZero("toWarehouseId", toWarehouseId);
	}
}
