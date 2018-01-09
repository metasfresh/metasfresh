package de.metas.material.event.shipmentschedule;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import java.math.BigDecimal;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/*
 * #%L
 * metasfresh-manufacturing-event-api
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

@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public abstract class AbstractShipmentScheduleEvent implements MaterialEvent
{
	private final EventDescriptor eventDescriptor;

	private final MaterialDescriptor materialDescriptor;

	private final BigDecimal reservedQuantity;

	private final int shipmentScheduleId;

	public AbstractShipmentScheduleEvent(
			final EventDescriptor eventDescriptor,
			final MaterialDescriptor materialDescriptor,
			final BigDecimal reservedQuantity,
			final int shipmentScheduleId)
	{
		this.shipmentScheduleId = shipmentScheduleId;
		this.eventDescriptor = eventDescriptor;
		this.materialDescriptor = materialDescriptor;
		this.reservedQuantity = reservedQuantity;
	}

	public abstract BigDecimal getOrderedQuantityDelta();

	public abstract BigDecimal getReservedQuantityDelta();

	@OverridingMethodsMustInvokeSuper
	public void validate()
	{
		checkIdGreaterThanZero("shipmentScheduleId", shipmentScheduleId);

		Check.errorIf(eventDescriptor == null, "eventDescriptor may not be null");
		Check.errorIf(materialDescriptor == null, "materialDescriptor may not be null");
		Check.errorIf(reservedQuantity == null, "reservedQuantity may not be null");
	}
}
