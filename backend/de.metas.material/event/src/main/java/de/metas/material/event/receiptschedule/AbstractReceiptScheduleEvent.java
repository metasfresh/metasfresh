package de.metas.material.event.receiptschedule;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import java.math.BigDecimal;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
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
public abstract class AbstractReceiptScheduleEvent implements MaterialEvent
{
	@NonNull
	private final EventDescriptor eventDescriptor;

	@NonNull
	private final MaterialDescriptor materialDescriptor;

	// needed on update and delete
	private final BigDecimal reservedQuantity;

	private final int receiptScheduleId;

	public AbstractReceiptScheduleEvent(
			final EventDescriptor eventDescriptor,
			final MaterialDescriptor materialDescriptor,
			final BigDecimal reservedQuantity,
			final int receiptScheduleId)
	{
		this.receiptScheduleId = receiptScheduleId;
		this.eventDescriptor = eventDescriptor;
		this.materialDescriptor = materialDescriptor;
		this.reservedQuantity = reservedQuantity;
	}

	public abstract BigDecimal getOrderedQuantityDelta();

	public abstract BigDecimal getReservedQuantityDelta();

	@OverridingMethodsMustInvokeSuper
	public void validate()
	{
		checkIdGreaterThanZero("receiptScheduleId", receiptScheduleId);

		Check.errorIf(eventDescriptor == null, "eventDescriptor may not be null");
		Check.errorIf(materialDescriptor == null, "materialDescriptor may not be null");
		Check.errorIf(reservedQuantity == null, "reservedQuantity may not be null");
	}

}
