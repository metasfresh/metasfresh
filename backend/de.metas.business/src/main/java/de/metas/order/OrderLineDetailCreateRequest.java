/*
 * #%L
 * de.metas.business
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

package de.metas.order;

import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class OrderLineDetailCreateRequest
{
	@NonNull ProductId productId;
	@NonNull Quantity qty;
	@NonNull ProductPrice price;
	@NonNull Money amount;
	@NonNull String description;

	@Builder
	private OrderLineDetailCreateRequest(
			@NonNull final ProductId productId,
			@NonNull final Quantity qty,
			@NonNull final Money price,
			@NonNull final Money amount,
			@Nullable final String description)
	{
		Money.getCommonCurrencyIdOfAll(price, amount);

		this.productId = productId;
		this.qty = qty;
		this.price = ProductPrice.builder()
				.money(price)
				.productId(productId)
				.uomId(qty.getUomId())
				.build();
		this.amount = amount;
		this.description = StringUtils.trimBlankToNull(description);
	}
}
