package de.metas.bpartner.product.stats;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class InvoiceChangedEvent
{
	@JsonProperty("invoiceId")
	InvoiceId invoiceId;

	@JsonProperty("invoiceDate")
	LocalDate invoiceDate;

	@JsonProperty("bpartnerId")
	BPartnerId bpartnerId;

	@JsonProperty("soTrx")
	SOTrx soTrx;

	@JsonProperty("reversal")
	boolean reversal;

	@JsonProperty("productPrices")
	@Getter(AccessLevel.NONE)
	List<ProductPrice> productPrices;

	@JsonIgnore
	ImmutableMap<ProductId, ProductPrice> productPricesByProductId;

	@Builder
	@JsonCreator
	private InvoiceChangedEvent(
			@JsonProperty("invoiceId") @NonNull final InvoiceId invoiceId,
			@JsonProperty("invoiceDate") @NonNull final LocalDate invoiceDate,
			@JsonProperty("bpartnerId") @NonNull final BPartnerId bpartnerId,
			@JsonProperty("soTrx") @NonNull final SOTrx soTrx,
			@JsonProperty("reversal") final boolean reversal,
			@JsonProperty("productPrices") @NonNull @Singular final List<ProductPrice> productPrices)
	{
		Check.assumeNotEmpty(productPrices, "productPrices is not empty");

		this.invoiceId = invoiceId;
		this.invoiceDate = invoiceDate;
		this.bpartnerId = bpartnerId;
		this.soTrx = soTrx;
		this.reversal = reversal;

		this.productPrices = ImmutableList.copyOf(productPrices);
		this.productPricesByProductId = Maps.uniqueIndex(this.productPrices, ProductPrice::getProductId);
	}

	public Set<ProductId> getProductIds()
	{
		return productPricesByProductId.keySet();
	}

	public Money getProductPrice(@NonNull final ProductId productId)
	{
		final ProductPrice productPrice = productPricesByProductId.get(productId);
		if (productPrice == null)
		{
			throw new AdempiereException("No product price found for " + productId + " in " + this);
		}
		return productPrice.getPrice();
	}

	public InvoicableQtyBasedOn getInvoicableQtyBasedOn(@NonNull final ProductId productId)
	{
		final ProductPrice productPrice = productPricesByProductId.get(productId);
		if (productPrice == null)
		{
			throw new AdempiereException("No product price found for " + productId + " in " + this);
		}
		return productPrice.getInvoicableQtyBasedOn();
	}

	@Value
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	public static class ProductPrice
	{
		@JsonProperty("productId")
		ProductId productId;

		@JsonProperty("price")
		Money price;

		@JsonProperty("invoicableQtyBasedOn")
		InvoicableQtyBasedOn invoicableQtyBasedOn;

		@Builder
		@JsonCreator
		private ProductPrice(
				@JsonProperty("productId") @NonNull final ProductId productId,
				@JsonProperty("price") @NonNull final Money price,
				@JsonProperty("invoicableQtyBasedOn") @NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn)
		{
			this.productId = productId;
			this.price = price;
			this.invoicableQtyBasedOn = invoicableQtyBasedOn;
		}

	}

}
