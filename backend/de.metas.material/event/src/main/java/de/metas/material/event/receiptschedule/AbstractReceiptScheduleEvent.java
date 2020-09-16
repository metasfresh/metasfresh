package de.metas.material.event.receiptschedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
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
public abstract class AbstractReceiptScheduleEvent implements MaterialEvent
{

	private final EventDescriptor eventDescriptor;

	private final MaterialDescriptor materialDescriptor;

	private final BigDecimal reservedQuantity;

	@JsonInclude(NON_NULL)
	private final MinMaxDescriptor minMaxDescriptor;

	private final int receiptScheduleId;

	public AbstractReceiptScheduleEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final MaterialDescriptor materialDescriptor,
			@Nullable final MinMaxDescriptor minMaxDescriptor,
			final BigDecimal reservedQuantity,
			final int receiptScheduleId)
	{
		this.minMaxDescriptor = minMaxDescriptor;
		this.receiptScheduleId = receiptScheduleId;
		this.eventDescriptor = eventDescriptor;
		this.materialDescriptor = materialDescriptor;
		this.reservedQuantity = reservedQuantity;
	}

	public abstract BigDecimal getOrderedQuantityDelta();

	public abstract BigDecimal getReservedQuantityDelta();

	@OverridingMethodsMustInvokeSuper
	public AbstractReceiptScheduleEvent validate()
	{
		checkIdGreaterThanZero("receiptScheduleId", receiptScheduleId);

		Check.errorIf(reservedQuantity == null, "reservedQuantity may not be null");

		return this;
	}

}
