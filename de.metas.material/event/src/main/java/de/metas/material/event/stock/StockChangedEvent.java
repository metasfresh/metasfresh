package de.metas.material.event.stock;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.util.Check;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class StockChangedEvent implements MaterialEvent
{
	public static final String TYPE = "StockChangedEvent";

	EventDescriptor eventDescriptor;
	ProductDescriptor productDescriptor;
	int warehouseId;

	/** may be negative if a storage attribute is changed! */
	BigDecimal qtyOnHand;

	BigDecimal qtyOnHandOld;

	/** optional; might be used by some handlers; if null then "now" is assumed. */
	Date changeDate;

	StockChangeDetails stockChangeDetails;

	@JsonCreator
	@Builder
	public StockChangedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("productDescriptor") final ProductDescriptor productDescriptor,
			@JsonProperty("warehouseId") final int warehouseId,
			@JsonProperty("qtyOnHand") final BigDecimal qtyOnHand,
			@JsonProperty("qtyOnHandOld") final BigDecimal qtyOnHandOld,
			@JsonProperty("changeDate") final Date changeDate,
			@JsonProperty("stockChangeDetails") final StockChangeDetails stockChangeDetails)
	{
		this.eventDescriptor = eventDescriptor;
		this.productDescriptor = productDescriptor;
		this.warehouseId = warehouseId;
		this.qtyOnHand = qtyOnHand;
		this.qtyOnHandOld = qtyOnHandOld;
		this.changeDate = changeDate;
		this.stockChangeDetails = stockChangeDetails;
	}

	public void validate()
	{
		Check.errorIf(eventDescriptor == null, "eventDescriptor may not be null; this={}", this);
		Check.errorIf(productDescriptor == null, "productDescriptor may not be null; this={}", this);
		Check.errorIf(qtyOnHand == null, "qtyOnHand may not be null; this={}", this);
		Check.errorIf(qtyOnHandOld == null, "qtyOnHandOld may not be null; this={}", this);
		Check.errorIf(warehouseId <= 0, "warehouseId needs to be > 0; this={}", this);
		Check.errorIf(stockChangeDetails == null, "stockChangeDetails may not be null; this={}", this);
		Check.errorIf(stockChangeDetails.getStockId() <= 0, "stockChangeDetails.stockId may not be null; this={}", this);
	}

	public int getProductId()
	{
		return productDescriptor.getProductId();
	}

	@Value
	public static class StockChangeDetails
	{
		int resetStockAdPinstanceId;

		int transactionId;

		int stockId;

		@JsonCreator
		@Builder
		public StockChangeDetails(
				@JsonProperty("resetStockAdPinstanceId") final int resetStockAdPinstanceId, 
				@JsonProperty("transactionId") final int transactionId, 
				@JsonProperty("stockId") final int stockId)
		{
			this.resetStockAdPinstanceId = resetStockAdPinstanceId;
			this.transactionId = transactionId;
			this.stockId = stockId;
		}
	}
}
