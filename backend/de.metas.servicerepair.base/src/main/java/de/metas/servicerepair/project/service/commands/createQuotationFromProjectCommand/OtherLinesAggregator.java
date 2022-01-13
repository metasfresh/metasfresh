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
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import lombok.Builder;
import lombok.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

class OtherLinesAggregator implements QuotationLinesGroupAggregator
{
	private final ProjectQuotationPriceCalculator priceCalculator;

	private final LinkedHashMap<QuotationLineKey, QuotationLineAggregator> lineAggregators = new LinkedHashMap<>();

	@Builder
	private OtherLinesAggregator(
			@NonNull final ProjectQuotationPriceCalculator priceCalculator)
	{
		this.priceCalculator = priceCalculator;
	}

	@Override
	public void add(final @NonNull ServiceRepairProjectCostCollector costCollector)
	{
		final QuotationLineKey lineKey = QuotationLineAggregator.extractKey(costCollector);
		final QuotationLineAggregator lineAggregator = lineAggregators.computeIfAbsent(lineKey, this::newLineAggregator);
		lineAggregator.add(costCollector);
	}

	private QuotationLineAggregator newLineAggregator(@NonNull final QuotationLineKey lineKey)
	{
		return QuotationLineAggregator.builder()
				.priceCalculator(priceCalculator)
				.key(lineKey)
				.build();
	}

	@Override
	public void createOrderLines(@NonNull final OrderFactory orderFactory)
	{
		lineAggregators.values()
				.forEach(lineAggregator -> lineAggregator.createOrderLines(orderFactory));
	}

	@Override
	public Stream<Map.Entry<ServiceRepairProjectCostCollectorId, OrderAndLineId>> streamQuotationLineIdsIndexedByCostCollectorId()
	{
		return lineAggregators.values()
				.stream()
				.flatMap(QuotationLineAggregator::streamQuotationLineIdsIndexedByCostCollectorId);
	}

	@Override
	public GroupTemplate getGroupTemplate()
	{
		return null;
	}
}
