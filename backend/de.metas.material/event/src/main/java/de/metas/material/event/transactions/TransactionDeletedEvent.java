package de.metas.material.event.transactions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.inout.InOutAndLineId;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import lombok.Builder;
import lombok.Singular;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class TransactionDeletedEvent extends AbstractTransactionEvent
{
	public static final String TYPE = "TransactionDeletedEvent";

	@JsonCreator
	@Builder
	public TransactionDeletedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("materialDescriptor") final MaterialDescriptor materialDescriptor,
			@JsonProperty("minMaxDescriptor") @Nullable final MinMaxDescriptor minMaxDescriptor,
			@JsonProperty("shipmentScheduleIds2Qtys") @Singular final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys,
			@JsonProperty("receiptScheduleIdsQtys") @Singular final Map<Integer, BigDecimal> receiptScheduleIdsQtys,
			@JsonProperty("receiptId") final InOutAndLineId receiptId,
			@JsonProperty("shipmentId") final InOutAndLineId shipmentId,
			@JsonProperty("ppOrderId") final int ppOrderId,
			@JsonProperty("ppOrderLineId") final int ppOrderLineId,
			@JsonProperty("ddOrderId") final int ddOrderId,
			@JsonProperty("ddOrderLineId") final int ddOrderLineId,
			@JsonProperty("inventoryId") final int inventoryId,
			@JsonProperty("inventoryLineId") final int inventoryLineId,
			@JsonProperty("transactionId") final int transactionId,
			@JsonProperty("directMovementWarehouse") final boolean directMovementWarehouse,
			@JsonProperty("huOnHandQtyChangeDescriptor") final Collection<HUDescriptor> huOnHandQtyChangeDescriptors)
	{
		super(eventDescriptor,
				materialDescriptor,
				minMaxDescriptor,
				shipmentScheduleIds2Qtys,
				receiptScheduleIdsQtys,
				receiptId,
				shipmentId,
				ppOrderId,
				ppOrderLineId,
				ddOrderId,
				ddOrderLineId,
				inventoryId,
				inventoryLineId,
				transactionId,
				directMovementWarehouse,
				huOnHandQtyChangeDescriptors);
	}

	/**
	 * @return zero.
	 */
	@Override
	public BigDecimal getQuantity()
	{
		return BigDecimal.ZERO;
	}

	/**
	 * @return our material descriptor's <b>negated</b> quantity, i.e. the negated {@code MovementQty} of the underlying {@code M_Transaction}.
	 */
	@Override
	public BigDecimal getQuantityDelta()
	{
		return getMaterialDescriptor().getQuantity().negate();
	}
}
