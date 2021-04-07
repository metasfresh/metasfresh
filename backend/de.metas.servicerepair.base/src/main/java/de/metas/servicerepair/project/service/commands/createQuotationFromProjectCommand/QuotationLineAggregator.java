/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.project.service.commands.createQuotationFromProjectCommand;

import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineBuilder;
import de.metas.order.OrderLineDetailCreateRequest;
import de.metas.pricing.IPricingResult;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

class QuotationLineAggregator
{
	private final ProjectQuotationPriceCalculator priceCalculator;

	@NonNull private final QuotationLineKey key;

	@Getter
	@NonNull private Quantity qty;

	@Getter
	@Nullable private Money manualPrice;

	@Getter
	private final ArrayList<OrderLineDetailCreateRequest> details = new ArrayList<>();

	private final HashSet<ServiceRepairProjectCostCollectorId> costCollectorIds = new HashSet<>();

	@Setter
	@Getter
	private OrderLineBuilder orderLineBuilderUsed;

	@Builder
	private QuotationLineAggregator(
			@NonNull final ProjectQuotationPriceCalculator priceCalculator,
			@NonNull final QuotationLineKey key)
	{
		this.priceCalculator = priceCalculator;
		this.key = key;

		this.qty = Quantitys.createZero(key.getUomId());
		this.manualPrice = null;
	}

	public ProductId getProductId()
	{
		return key.getProductId();
	}

	public AttributeSetInstanceId getAsiId()
	{
		return key.getAsiId();
	}

	public void collectMatchingItem(@NonNull final ServiceRepairProjectCostCollector costCollector)
	{
		final ServiceRepairProjectCostCollectorType type = key.getType();
		if (type == ServiceRepairProjectCostCollectorType.SparePartsToBeInvoiced)
		{
			qty = qty.add(costCollector.getQtyReservedOrConsumed());
			manualPrice = null;
		}
		else if (type == ServiceRepairProjectCostCollectorType.SparePartsOwnedByCustomer)
		{
			qty = qty.add(costCollector.getQtyReservedOrConsumed());
			manualPrice = Money.zero(priceCalculator.getCurrencyId());
		}
		else if (type == ServiceRepairProjectCostCollectorType.RepairedProductToReturn)
		{
			qty = qty.add(costCollector.getQtyReservedOrConsumed());
			manualPrice = Money.zero(priceCalculator.getCurrencyId());
		}
		else if (type == ServiceRepairProjectCostCollectorType.RepairingConsumption)
		{
			// NOTE: the quantity shall be the amount of repaired products to return
			// qty = qty.toOne();

			final OrderLineDetailCreateRequest detail = computeOrderLineDetailCreateRequest(costCollector);
			details.add(detail);

			manualPrice = costCollector.getWarrantyCase().isYes()
					? Money.zero(priceCalculator.getCurrencyId())
					: null; // NOTE: use the price from price list
		}
		else
		{
			throw new AdempiereException("Unknown type: " + type);
		}

		costCollectorIds.add(costCollector.getId());
	}

	public void collectNotMatchingItem(@NonNull final ServiceRepairProjectCostCollector costCollector)
	{
		final ServiceRepairProjectCostCollectorType type = key.getType();
		if (type == ServiceRepairProjectCostCollectorType.RepairingConsumption)
		{
			if (costCollector.getType() == ServiceRepairProjectCostCollectorType.RepairedProductToReturn)
			{
				// NOTE: usually we increment with ONE.
				// We are adding as BigDecimal to avoid cases when the product to return and the service product have different UOMs.
				qty = qty.add(costCollector.getQtyReservedOrConsumed().toBigDecimal());
			}
		}
	}

	private OrderLineDetailCreateRequest computeOrderLineDetailCreateRequest(final ServiceRepairProjectCostCollector costCollector)
	{
		final IPricingResult pricingResult = priceCalculator.calculatePrice(costCollector);

		final Quantity qty = costCollector.getQtyReservedOrConsumed();
		final Money price = pricingResult.getPriceStdAsMoney();
		final Money amount = price
				.multiply(qty.toBigDecimal())
				.round(pricingResult.getPrecision());

		return OrderLineDetailCreateRequest.builder()
				.productId(costCollector.getProductId())
				.qty(qty)
				.price(price)
				.amount(amount)
				.build();
	}

	public Stream<Map.Entry<ServiceRepairProjectCostCollectorId, OrderAndLineId>> streamQuotationLineIdsIndexedByCostCollectorId()
	{
		if (orderLineBuilderUsed == null)
		{
			return Stream.empty();
		}
		else
		{
			final OrderAndLineId quotationLineId = orderLineBuilderUsed.getCreatedOrderAndLineId();
			return costCollectorIds.stream().map(costCollectorId -> GuavaCollectors.entry(costCollectorId, quotationLineId));
		}
	}
}
