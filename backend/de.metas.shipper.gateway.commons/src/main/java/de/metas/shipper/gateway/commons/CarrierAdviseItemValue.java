/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.shipper.gateway.commons;

import de.metas.common.util.CoalesceUtil;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

/**
 * The monetary/quantity fields describing one shipped product line, shared by the three nShift build paths so their
 * derivation stays identical (each caller carries a "change together" note pointing here):
 * <ul>
 *     <li>HU-advise: {@code PackedHUCarrierAdviseService#buildRequestItem}</li>
 *     <li>schedule-advise: {@code CarrierAdviseCommand#getJsonDeliveryAdvisorRequestParcel}</li>
 *     <li>delivery-order (ship): {@code NShiftDraftDeliveryOrderCreator#createDeliveryOrderItem}</li>
 * </ul>
 * {@link #unitPrice} is the value of ONE {@link #shippedQuantity} unit and {@link #totalValue} the value of the full
 * quantity; both are obtained from the order-line price with a single price-UOM conversion via
 * {@link MoneyService#multiply(Quantity, ProductPrice)}, so {@code unitPrice} and {@code totalValue} always share
 * {@code shippedQuantity}'s UOM basis (i.e. {@code unitPrice * shippedQuantity == totalValue}).
 */
@Value
@Builder
public class CarrierAdviseItemValue
{
	@NonNull Money unitPrice;
	@NonNull Money totalValue;
	@NonNull Quantity shippedQuantity;

	@NonNull
	public static CarrierAdviseItemValue compute(
			@NonNull final MoneyService moneyService,
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final ProductId productId,
			@NonNull final Quantity shippedQuantity)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());
		// When no explicit price UOM is set, the price is taken to be per the shipped quantity's own UOM.
		final UomId priceUomId = CoalesceUtil.coalesceNotNull(
				UomId.ofRepoIdOrNull(orderLine.getPrice_UOM_ID()),
				shippedQuantity.getUomId());
		final ProductPrice orderLinePrice = ProductPrice.builder()
				.money(Money.of(orderLine.getPriceEntered(), currencyId))
				.uomId(priceUomId)
				.productId(productId)
				.build();
		// Price and shipped UOM may differ: value of one unit / of the full qty at the order-line price.
		// MoneyService.multiply converts the qty into the price UOM once, then multiplies (exactly one conversion).
		final Quantity oneUnit = Quantitys.of(BigDecimal.ONE, shippedQuantity.getUomId());
		return builder()
				.unitPrice(moneyService.multiply(oneUnit, orderLinePrice))
				.totalValue(moneyService.multiply(shippedQuantity, orderLinePrice))
				.shippedQuantity(shippedQuantity)
				.build();
	}
}
