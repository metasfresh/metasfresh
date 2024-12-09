package de.metas.material.event.shipmentschedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.material.event.MaterialEvent;
<<<<<<< HEAD
=======
import de.metas.material.event.commons.DocumentLineDescriptor;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
<<<<<<< HEAD
=======
import lombok.NonNull;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import lombok.ToString;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

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

	@JsonInclude(NON_NULL)
	private final MinMaxDescriptor minMaxDescriptor;

<<<<<<< HEAD
	private final BigDecimal reservedQuantity;

	private final int shipmentScheduleId;

=======
	private final ShipmentScheduleDetail shipmentScheduleDetail;

	private final int shipmentScheduleId;

	private final DocumentLineDescriptor documentLineDescriptor;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public AbstractShipmentScheduleEvent(
			final EventDescriptor eventDescriptor,
			final MaterialDescriptor materialDescriptor,
			@Nullable final MinMaxDescriptor minMaxDescriptor,
<<<<<<< HEAD
			final BigDecimal reservedQuantity,
			final int shipmentScheduleId)
=======
			@NonNull final ShipmentScheduleDetail shipmentScheduleDetail,
			final int shipmentScheduleId,
			@Nullable final DocumentLineDescriptor documentLineDescriptor)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		this.shipmentScheduleId = shipmentScheduleId;
		this.eventDescriptor = eventDescriptor;
		this.materialDescriptor = materialDescriptor;
		this.minMaxDescriptor = minMaxDescriptor;
<<<<<<< HEAD
		this.reservedQuantity = reservedQuantity;
	}

	public abstract BigDecimal getOrderedQuantityDelta();

	public abstract BigDecimal getReservedQuantityDelta();
=======
		this.shipmentScheduleDetail = shipmentScheduleDetail;
		this.documentLineDescriptor = documentLineDescriptor;
	}

	@NonNull
	public abstract BigDecimal getReservedQuantityDelta();

	@NonNull
	public abstract BigDecimal getOrderedQuantityDelta();

	@NonNull
	public BigDecimal getOrderedQuantity()
	{
		return getShipmentScheduleDetail().getOrderedQuantity();
	}

	@NonNull
	public BigDecimal getReservedQuantity()
	{
		return getShipmentScheduleDetail().getReservedQuantity();
	}

	@Nullable
	public OldShipmentScheduleData getOldShipmentScheduleData()
	{
		return getShipmentScheduleDetail().getOldShipmentScheduleData();
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@OverridingMethodsMustInvokeSuper
	public void validate()
	{
		checkIdGreaterThanZero("shipmentScheduleId", shipmentScheduleId);

		Check.errorIf(eventDescriptor == null, "eventDescriptor may not be null");
		Check.errorIf(materialDescriptor == null, "materialDescriptor may not be null");
<<<<<<< HEAD
		Check.errorIf(reservedQuantity == null, "reservedQuantity may not be null");
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
