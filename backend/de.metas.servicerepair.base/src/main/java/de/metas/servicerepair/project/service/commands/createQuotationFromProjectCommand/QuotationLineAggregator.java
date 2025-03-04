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
import de.metas.order.OrderFactory;
import de.metas.order.OrderLineBuilder;
import de.metas.order.OrderLineDetailCreateRequest;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

class QuotationLineAggregator
{
	private final ProjectQuotationPriceCalculator priceCalculator;

	@NonNull private final QuotationLineKey key;
	@NonNull private Quantity qty;
	@Nullable private Boolean zeroPrice;
	@Nullable private String description;
	private final ArrayList<OrderLineDetailCreateRequest> details = new ArrayList<>();
	private final HashSet<ServiceRepairProjectCostCollectorId> costCollectorIds = new HashSet<>();

	private OrderLineBuilder orderLineBuilderUsed;

	@Builder
	private QuotationLineAggregator(
			@NonNull final ProjectQuotationPriceCalculator priceCalculator,
			@NonNull final QuotationLineKey key)
	{
		this.priceCalculator = priceCalculator;
		this.key = key;
		this.qty = Quantitys.zero(key.getUomId());
	}

	public static QuotationLineKey extractKey(@NonNull final ServiceRepairProjectCostCollector costCollector)
	{
		return QuotationLineKey.builder()
				.groupKey(QuotationLinesGroupAggregator.extractKey(costCollector))
				.type(costCollector.getType())
				.productId(costCollector.getProductId())
				.uomId(costCollector.getUomId())
				.asiId(costCollector.getAsiId())
				.build();
	}

	public QuotationLineAggregator add(@NonNull final ServiceRepairProjectCostCollector costCollector)
	{
		Check.assumeEquals(extractKey(costCollector), key, "key does not match for {}. Expected: {}", costCollector, key);

		qty = qty.add(costCollector.getQtyReservedOrConsumed());
		costCollectorIds.add(costCollector.getId());

		return this;
	}

	public void createOrderLines(@NonNull final OrderFactory orderFactory)
	{
		if (this.orderLineBuilderUsed != null)
		{
			throw new AdempiereException("Order line was already created for " + this); // shall not happen
		}

		this.orderLineBuilderUsed = orderFactory.newOrderLine()
				.productId(key.getProductId())
				.asiId(key.getAsiId())
				.qty(qty)
				.manualPrice(isZeroPrice() ? Money.zero(priceCalculator.getCurrencyId()) : null)
				.description(description)
				.hideWhenPrinting(isHideWhenPrinting())
				.details(details);
	}

	private boolean isZeroPrice()
	{
		return zeroPrice != null ? zeroPrice : key.isZeroPrice();
	}

	private boolean isHideWhenPrinting()
	{
		return key.getType() == ServiceRepairProjectCostCollectorType.SparePartsOwnedByCustomer;
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

	public QuotationLineAggregator description(@Nullable final String description)
	{
		this.description = description;
		return this;
	}
}
