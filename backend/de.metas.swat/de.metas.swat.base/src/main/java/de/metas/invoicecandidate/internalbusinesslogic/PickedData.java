/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.invoicecandidate.internalbusinesslogic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.UOMConversionContext;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Value
public class PickedData
{
	ProductId productId;

	Quantity qtyPicked;

	Quantity qtyPickedInUOM;

	Quantity qtyCatch;

	@NonNull
	public static PickedData of(@NonNull final StockQtyAndUOMQty stockQtyAndUOMQty)
	{
		return PickedData.builder()
				.productId(stockQtyAndUOMQty.getProductId())
				.qtyPicked(stockQtyAndUOMQty.getStockQty())
				.qtyPickedInUOM(stockQtyAndUOMQty.getUOMQtyNotNull())
				.build();
	}

	@Builder
	@JsonCreator
	private PickedData(
			@JsonProperty("productId") @NonNull final ProductId productId,
			@JsonProperty("qtyPicked") @NonNull final Quantity qtyPicked,
			@JsonProperty("qtyCatch") @Nullable final Quantity qtyCatch,
			@JsonProperty("qtyPickedInUOM") @NonNull final Quantity qtyPickedInUOM)
	{
		this.productId = productId;
		this.qtyPicked = qtyPicked;
		this.qtyPickedInUOM = qtyPickedInUOM;
		this.qtyCatch = qtyCatch;
	}

	public StockQtyAndUOMQty computeInvoicableQtyPicked(@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn)
	{
		final Quantity pickedInUOM;
		switch (invoicableQtyBasedOn)
		{
			case CatchWeight:
				pickedInUOM = coalesce(getCatchWeightInIcUOM(), getQtyPickedInUOM());
				break;
			case NominalWeight:
				pickedInUOM = getQtyPickedInUOM();
				break;
			default:
				throw new AdempiereException("Unexpected InvoicableQtyBasedOn=" + invoicableQtyBasedOn);
		}

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(qtyPicked)
				.uomQty(pickedInUOM).build();
	}

	@Nullable
	private Quantity getCatchWeightInIcUOM()
	{
		if (qtyCatch == null)
		{
			return null;
		}

		return Quantitys.of(qtyCatch, UOMConversionContext.of(productId), qtyPickedInUOM.getUomId());
	}
}
