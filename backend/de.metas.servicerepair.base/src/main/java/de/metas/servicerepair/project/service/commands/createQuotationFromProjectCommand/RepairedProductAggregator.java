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
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderFactory;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

class RepairedProductAggregator implements QuotationLinesGroupAggregator
{
	private final ProjectQuotationPriceCalculator priceCalculator;

	private final QuotationLinesGroupKey key;
	private final String repairOrderSummary;

	private final ArrayList<ServiceRepairProjectCostCollector> costCollectorsToAggregate = new ArrayList<>();

	private QuotationLineAggregator repairedProductToReturnLine;
	private final ArrayList<QuotationLineAggregator> performedServiceLines = new ArrayList<>();

	@Builder
	private RepairedProductAggregator(
			@NonNull final ProjectQuotationPriceCalculator priceCalculator,
			@NonNull final QuotationLinesGroupKey key,
			@Nullable final String repairOrderSummary)
	{
		if (key.getType() != QuotationLinesGroupKey.Type.REPAIRED_PRODUCT)
		{
			throw new AdempiereException("Invalid key type: " + key);
		}

		this.priceCalculator = priceCalculator;
		this.key = key;
		this.repairOrderSummary = repairOrderSummary;
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
		{
			final ServiceRepairProjectCostCollector repairedProductToReturnCostCollector = removeSingleCostCollectorOfType(ServiceRepairProjectCostCollectorType.RepairedProductToReturn);

			repairedProductToReturnLine = newLineAggregator(QuotationLineAggregator.extractKey(repairedProductToReturnCostCollector))
					.description(repairOrderSummary)
					.add(repairedProductToReturnCostCollector);
		}

		//
		// Materials consumed while repairing
		repairedProductToReturnLine.addAsDetails(removeCostCollectorsOfType(ServiceRepairProjectCostCollectorType.RepairingConsumption));

		//
		// Services performed while repairing
		for (final ServiceRepairProjectCostCollector costCollector : removeCostCollectorsOfType(ServiceRepairProjectCostCollectorType.RepairingServicePerformed))
		{
			final QuotationLineKey lineKey = QuotationLineAggregator.extractKey(costCollector);
			performedServiceLines.add(newLineAggregator(lineKey).add(costCollector).zeroPrice(false));

			if (costCollector.getWarrantyCase().isYes())
			{
				performedServiceLines.add(newLineAggregator(lineKey).addNegated(costCollector).zeroPrice(false));
			}
		}

		// Other cost collectors
		// => FAIL because they are not handled
		if (!costCollectorsToAggregate.isEmpty())
		{
			throw new AdempiereException("Following cost collectors cannot be handled: " + costCollectorsToAggregate);
		}

		//
		// Actually create order lines
		repairedProductToReturnLine.createOrderLines(orderFactory);
		performedServiceLines.forEach(serviceAgg -> serviceAgg.createOrderLines(orderFactory));
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

	private QuotationLineAggregator newLineAggregator(@NonNull final QuotationLineKey lineKey)
	{
		return QuotationLineAggregator.builder()
				.priceCalculator(priceCalculator)
				.key(lineKey)
				.build();
	}

	@Override
	public Stream<Map.Entry<ServiceRepairProjectCostCollectorId, OrderAndLineId>> streamQuotationLineIdsIndexedByCostCollectorId()
	{
		return Stream.concat(
				repairedProductToReturnLine.streamQuotationLineIdsIndexedByCostCollectorId(),
				performedServiceLines.stream().flatMap(QuotationLineAggregator::streamQuotationLineIdsIndexedByCostCollectorId));
	}

	@Override
	public GroupTemplate getGroupTemplate()
	{
		return GroupTemplate.builder()
				.name(".") // TODO: come up with a better name
				.build();
	}
}
