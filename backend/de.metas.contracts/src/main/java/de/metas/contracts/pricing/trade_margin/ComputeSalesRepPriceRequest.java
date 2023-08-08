/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.pricing.trade_margin;

import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ComputeSalesRepPriceRequest
{
	@NonNull
	BPartnerId salesRepId;

	@NonNull
	SOTrx soTrx;

	@NonNull
	ProductId productId;

	@NonNull
	Quantity qty;

	@NonNull
	CurrencyId customerCurrencyId;

	@NonNull
	LocalDate commissionDate;

	@Builder
	public ComputeSalesRepPriceRequest(
			@NonNull final BPartnerId salesRepId,
			@NonNull final SOTrx soTrx,
			@NonNull final ProductId productId,
			@NonNull final Quantity qty,
			@NonNull final CurrencyId customerCurrencyId,
			@NonNull final LocalDate commissionDate)
	{
		this.salesRepId = salesRepId;
		this.soTrx = soTrx;
		this.productId = productId;
		this.qty = qty;
		this.customerCurrencyId = customerCurrencyId;
		this.commissionDate = commissionDate;
	}
}
