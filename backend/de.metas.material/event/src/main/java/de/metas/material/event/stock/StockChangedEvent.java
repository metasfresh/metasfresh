package de.metas.material.event.stock;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Transaction;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class StockChangedEvent implements MaterialEvent
{
	public static final String TYPE = "StockChangedEvent";

	EventDescriptor eventDescriptor;
	ProductDescriptor productDescriptor;
	WarehouseId warehouseId;

	/** may be negative if a storage attribute is changed! */
	BigDecimal qtyOnHand;

	BigDecimal qtyOnHandOld;

	/** optional; might be used by some handlers; if null then "now" is assumed. */
	Instant changeDate;

	StockChangeDetails stockChangeDetails;

	@JsonCreator
	@Builder
	public StockChangedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("productDescriptor") final ProductDescriptor productDescriptor,
			@JsonProperty("warehouseId") final WarehouseId warehouseId,
			@JsonProperty("qtyOnHand") final BigDecimal qtyOnHand,
			@JsonProperty("qtyOnHandOld") final BigDecimal qtyOnHandOld,
			@JsonProperty("changeDate") final Instant changeDate,
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
		Check.errorIf(warehouseId == null, "warehouseId needs to set; this={}", this);
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
		ResetStockPInstanceId resetStockPInstanceId;
		int transactionId;
		int stockId;

		@JsonCreator
		@Builder
		public StockChangeDetails(
				@JsonProperty("resetStockPInstanceId") final ResetStockPInstanceId resetStockPInstanceId,
				@JsonProperty("transactionId") final int transactionId,
				@JsonProperty("stockId") final int stockId)
		{
			this.resetStockPInstanceId = resetStockPInstanceId;
			this.transactionId = transactionId;
			this.stockId = stockId;
		}
	}

	@Nullable
	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.ofNullable(I_M_Transaction.Table_Name, stockChangeDetails.getTransactionId());
	}

	@Override
	public String getEventName() {return TYPE;}
}
