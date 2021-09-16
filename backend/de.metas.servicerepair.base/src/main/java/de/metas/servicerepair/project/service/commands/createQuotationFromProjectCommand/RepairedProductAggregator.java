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

import com.google.common.collect.ImmutableList;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderFactory;
import de.metas.order.OrderLineBuilder;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.product.ProductId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Stream;

class RepairedProductAggregator implements QuotationLinesGroupAggregator
{
	// Services
	private final ProjectQuotationPriceCalculator priceCalculator;

	// Parameters
	@NonNull private final QuotationLinesGroupKey key;
	@NonNull private final String groupCaption;
	@Nullable private final String repairOrderSummary;
	@Nullable private final ProductId repairServicePerformedId;
	private final ArrayList<ServiceRepairProjectCostCollector> costCollectorsToAggregate = new ArrayList<>();

	// Fields populated after createOrderLines is called:
	private ServiceRepairProjectCostCollector repairedProductToReturnCostCollector;
	private OrderLineBuilder repairedProductToReturnLineBuilder;
	private OrderLineBuilder servicePerformedLineBuilder;

	@Builder
	private RepairedProductAggregator(
			@NonNull final ProjectQuotationPriceCalculator priceCalculator,
			@NonNull final QuotationLinesGroupKey key,
			@NonNull final String groupCaption,
			@Nullable final String repairOrderSummary,
			@Nullable final ProductId repairServicePerformedId)
	{
		if (key.getType() != QuotationLinesGroupKey.Type.REPAIRED_PRODUCT)
		{
			throw new AdempiereException("Invalid key type: " + key);
		}

		this.priceCalculator = priceCalculator;
		this.key = key;
		this.groupCaption = groupCaption;
		this.repairOrderSummary = repairOrderSummary;
		this.repairServicePerformedId = repairServicePerformedId;
	}

	@Override
	public void add(@NonNull final ServiceRepairProjectCostCollector costCollector)
	{
		Check.assumeEquals(QuotationLinesGroupAggregator.extractKey(costCollector), key, "keys does not match");
		costCollectorsToAggregate.add(costCollector);
	}

	@Override
	public void createOrderLines(final OrderFactory orderFactory)
	{
		if (costCollectorsToAggregate.isEmpty())
		{
			throw new AdempiereException("No cost collectors to aggregate");
		}

		//
		// Repaired product
		repairedProductToReturnCostCollector = removeSingleCostCollectorOfType(ServiceRepairProjectCostCollectorType.RepairedProductToReturn);
		repairedProductToReturnLineBuilder = orderFactory.newOrderLine()
				.productId(repairedProductToReturnCostCollector.getProductId())
				.asiId(repairedProductToReturnCostCollector.getAsiId())
				.qty(repairedProductToReturnCostCollector.getQtyReservedOrConsumed())
				.manualPrice(Money.zero(priceCalculator.getCurrencyId()))
				.description(repairOrderSummary)
				.details(
						removeCostCollectorsOfType(ServiceRepairProjectCostCollectorType.RepairingConsumption)
								.stream()
								.map(priceCalculator::computeOrderLineDetailCreateRequest)
								.collect(ImmutableList.toImmutableList()));

		//
		// Service performed while repairing
		if (repairServicePerformedId != null)
		{
			servicePerformedLineBuilder = orderFactory.newOrderLine()
					.productId(repairServicePerformedId)
					.qty(BigDecimal.ONE);

			// NOTE: we create the line with regular price.
			// In case it's a warranty case we will add a 100% discount compensation line (see below).
		}

		// Other cost collectors
		// => FAIL because they are not handled
		if (!costCollectorsToAggregate.isEmpty())
		{
			throw new AdempiereException("Following cost collectors cannot be handled: " + costCollectorsToAggregate);
		}
	}

	private ServiceRepairProjectCostCollector removeSingleCostCollectorOfType(
			@NonNull final ServiceRepairProjectCostCollectorType type)
	{
		final ImmutableList<ServiceRepairProjectCostCollector> result = removeCostCollectorsOfType(type);
		if (result.size() == 1)
		{
			return result.get(0);
		}
		else
		{
			throw new AdempiereException("Expected one and only one cost collector of type " + type + " but got " + result);
		}
	}

	private ImmutableList<ServiceRepairProjectCostCollector> removeCostCollectorsOfType(@NonNull final ServiceRepairProjectCostCollectorType type)
	{
		final ImmutableList.Builder<ServiceRepairProjectCostCollector> result = ImmutableList.builder();
		for (final Iterator<ServiceRepairProjectCostCollector> it = costCollectorsToAggregate.iterator(); it.hasNext(); )
		{
			final ServiceRepairProjectCostCollector costCollector = it.next();
			if (costCollector.getType() == type)
			{
				result.add(costCollector);
				it.remove();
			}
		}

		return result.build();
	}

	@Override
	public Stream<Map.Entry<ServiceRepairProjectCostCollectorId, OrderAndLineId>> streamQuotationLineIdsIndexedByCostCollectorId()
	{
		if (repairedProductToReturnCostCollector == null || repairedProductToReturnLineBuilder == null)
		{
			throw new AdempiereException("Order/quotation line for repaired product was not created yet");
		}

		final LinkedHashSet<Map.Entry<ServiceRepairProjectCostCollectorId, OrderAndLineId>> orderAndLineIds = new LinkedHashSet<>();

		orderAndLineIds.add(GuavaCollectors.entry(repairedProductToReturnCostCollector.getId(), repairedProductToReturnLineBuilder.getCreatedOrderAndLineId()));

		if (servicePerformedLineBuilder != null)
		{
			orderAndLineIds.add(GuavaCollectors.entry(repairedProductToReturnCostCollector.getId(), servicePerformedLineBuilder.getCreatedOrderAndLineId()));
		}

		return orderAndLineIds.stream();
	}

	@Override
	public GroupTemplate getGroupTemplate()
	{
		final GroupTemplate.GroupTemplateBuilder builder = GroupTemplate.builder()
				.name(groupCaption)
				.isNamePrinted(false);

		if (repairedProductToReturnCostCollector.getWarrantyCase().isYes()
				&& repairServicePerformedId != null)
		{
			builder.compensationLine(GroupTemplateCompensationLine.builder()
					.productId(repairServicePerformedId)
					.compensationType(GroupCompensationType.Discount)
					.percentage(Percent.ONE_HUNDRED)
					.build());
		}
		
		builder.regularLinesToAdd(ImmutableList.of());

		return builder.build();
	}
}
