package de.metas.material.event.transactions;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.HUDescriptor;
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
public abstract class AbstractTransactionEvent implements MaterialEvent
{
	private final EventDescriptor eventDescriptor;

	private final MaterialDescriptor materialDescriptor;

	private final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys;

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
			final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys,
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
		this.huOnHandQtyChangeDescriptors = huOnHandQtyChangeDescriptors;

		this.shipmentScheduleIds2Qtys = shipmentScheduleIds2Qtys;

		this.ddOrderLineId = ddOrderLineId;
		this.ddOrderId = ddOrderId;

		this.ppOrderLineId = ppOrderLineId;
		this.ppOrderId = ppOrderId;

		this.directMovementWarehouse = directMovementWarehouse;
	}

	public abstract BigDecimal getQuantity();
	public abstract BigDecimal getQuantityDelta();

	@OverridingMethodsMustInvokeSuper
	public void assertValid()
	{
		Check.errorIf(eventDescriptor == null, "eventDescriptor may not be null");

		Check.errorIf(materialDescriptor == null, "materialDescriptor may not be null");
		materialDescriptor.asssertMaterialDescriptorComplete();
	}
}
