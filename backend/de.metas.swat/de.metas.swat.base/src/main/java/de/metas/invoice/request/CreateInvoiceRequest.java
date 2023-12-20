/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoice.request;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AcctSchema;
import de.metas.money.Money;
import de.metas.product.ProductPrice;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Optional;

@Value
@Builder
public class CreateInvoiceRequest
{
	@NonNull
	Boolean completeIt;

	@NonNull
	AcctSchema acctSchema;

	@NonNull
	CreateInvoiceRequestHeader header;

	@NonNull
	ImmutableMap<String, CreateInvoiceRequestLine> externalLineId2Line;

	@NonNull
	public Optional<ProductPrice> getPriceEntered(@NonNull final CreateInvoiceRequestLine line)
	{
		if (!externalLineId2Line.containsKey(line.getExternalLineId()))
		{
			throw new AdempiereException("No Line found for externalLineId=" + line.getExternalLineId());
		}

		return Optional.ofNullable(line.getPriceEntered())
				.map(priceEntered -> Money.of(priceEntered, header.getCurrencyId()))
				.map(price -> ProductPrice.builder()
						.money(price)
						.productId(line.getProductId())
						.uomId(Optional.ofNullable(line.getPriceUomId())
									   .orElseThrow(() -> new AdempiereException("PriceUOMId cannot be missing if priceEntered was provided!")))
						.build());
	}

	@NonNull
	public ImmutableList<CreateInvoiceRequestLine> getLines()
	{
		return externalLineId2Line.values().asList();
	}
}
