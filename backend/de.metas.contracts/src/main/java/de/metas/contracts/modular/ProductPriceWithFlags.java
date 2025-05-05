/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular;

import de.metas.product.ProductPrice;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ProductPriceWithFlags
{
	@NonNull ProductPrice price;
	boolean isCost;
	boolean isSubtractedValue;

	@NonNull
	public static ProductPriceWithFlags ofZero(@NonNull final ProductPrice productPrice)
	{
		Check.assume(productPrice.toMoney().isZero(), "Expecting product price to be 0! {0}", productPrice);

		return ProductPriceWithFlags.builder()
				.price(productPrice)
				.build();
	}

	@NonNull
	public ProductPrice toProductPrice()
	{
		return price.negateIf(isCost).negateIf(isSubtractedValue);
	}
}
