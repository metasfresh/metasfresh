package de.metas.material.event.transactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.inout.InOutAndLineId;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

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
public abstract class AbstractTransactionEvent implements MaterialEvent
{
	private final EventDescriptor eventDescriptor;

	private final MaterialDescriptor materialDescriptor;

	@JsonInclude(NON_NULL)
	private final MinMaxDescriptor minMaxDescriptor;

	/** note: one shipment-inoutLine might be an aggregation of multiple shipment schedules */
	private final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys;

	private final Map<Integer, BigDecimal> receiptScheduleIds2Qtys;

	private final InOutAndLineId receiptId;
	private final InOutAndLineId shipmentId;

	private final int transactionId;

	private final boolean directMovementWarehouse;

	private final int ppOrderId;

	private final int ppOrderLineId;

	private final int ddOrderId;

	private final int ddOrderLineId;

	private final Collection<HUDescriptor> huOnHandQtyChangeDescriptors;

	public AbstractTransactionEvent(
			final EventDescriptor eventDescriptor,
			final MaterialDescriptor materialDescriptor,
			@Nullable final MinMaxDescriptor minMaxDescriptor,
			final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys,
			final Map<Integer, BigDecimal> receiptScheduleIds2Qtys,
			final InOutAndLineId receiptId,
			final InOutAndLineId shipmentId,
			final int ppOrderId,
			final int ppOrderLineId,
			final int ddOrderId,
			final int ddOrderLineId,
			final int transactionId,
			final boolean directMovementWarehouse,
			final Collection<HUDescriptor> huOnHandQtyChangeDescriptors)
	{
		this.transactionId = checkIdGreaterThanZero("transactionId", transactionId);

		this.eventDescriptor = eventDescriptor;

		this.materialDescriptor = materialDescriptor;
		this.minMaxDescriptor = minMaxDescriptor;
		this.huOnHandQtyChangeDescriptors = huOnHandQtyChangeDescriptors;

		this.shipmentScheduleIds2Qtys = shipmentScheduleIds2Qtys;
		this.receiptScheduleIds2Qtys = receiptScheduleIds2Qtys;

		this.receiptId = receiptId;
		this.shipmentId = shipmentId;

		this.ddOrderLineId = ddOrderLineId;
		this.ddOrderId = ddOrderId;

		this.ppOrderLineId = ppOrderLineId;
		this.ppOrderId = ppOrderId;

		this.directMovementWarehouse = directMovementWarehouse;
	}

	/** Never return {@code null}. */
	public abstract BigDecimal getQuantity();

	/** Never return {@code null}. */
	public abstract BigDecimal getQuantityDelta();

	@OverridingMethodsMustInvokeSuper
	public void assertValid()
	{
		Check.errorIf(eventDescriptor == null, "eventDescriptor may not be null");

		Check.errorIf(materialDescriptor == null, "materialDescriptor may not be null");
		materialDescriptor.asssertMaterialDescriptorComplete();
	}
}
