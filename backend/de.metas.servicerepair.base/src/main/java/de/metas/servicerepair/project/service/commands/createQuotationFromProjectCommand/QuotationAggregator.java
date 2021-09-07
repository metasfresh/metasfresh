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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.order.OrderFactory;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.pricing.service.IPricingBL;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class QuotationAggregator
{
	// services
	private final OrderGroupRepository orderGroupRepository;

	private final ServiceRepairProjectInfo project;
	private final ImmutableMap<ServiceRepairProjectTaskId, ServiceRepairProjectTask> tasksById;
	private final ProjectQuotationPricingInfo pricingInfo;
	private final DocTypeId quotationDocTypeId;

	private final ProjectQuotationPriceCalculator priceCalculator;

	private final ArrayList<ServiceRepairProjectCostCollector> costCollectorsToAggregate = new ArrayList<>();
	private final AtomicInteger nextRepairingGroupIndex = new AtomicInteger(1);
	private QuotationLineIdsByCostCollectorIdIndex generatedQuotationLineIdsIndexedByCostCollectorId;

	@Builder
	private QuotationAggregator(
			@NonNull final IPricingBL pricingBL,
			@NonNull final OrderGroupRepository orderGroupRepository,
			//
			@NonNull final ServiceRepairProjectInfo project,
			@NonNull final List<ServiceRepairProjectTask> tasks,
			@NonNull final ProjectQuotationPricingInfo pricingInfo,
			@NonNull final DocTypeId quotationDocTypeId)
	{
		this.orderGroupRepository = orderGroupRepository;

		this.project = project;
		this.tasksById = Maps.uniqueIndex(tasks, ServiceRepairProjectTask::getId);
		this.pricingInfo = pricingInfo;
		this.quotationDocTypeId = quotationDocTypeId;

		this.priceCalculator = ProjectQuotationPriceCalculator.builder()
				.pricingBL(pricingBL)
				.pricingInfo(pricingInfo)
				.build();
	}

	private static LocalDate extractDateOrdered(
			@NonNull final ServiceRepairProjectInfo project,
			@NonNull final ZoneId timeZone)
	{
		final LocalDate dateContract = project.getDateContract();
		return dateContract != null ? dateContract : SystemTime.asLocalDate(timeZone);
	}

	public I_C_Order createDraft()
	{
		if (costCollectorsToAggregate.isEmpty())
		{
			throw new AdempiereException("No cost collectors to aggregate");
		}

		//
		// Make sure they are ordered by Task ID, so the quotation lines will be in same ordering as the repair tasks
		costCollectorsToAggregate.sort(Comparator.comparing(ServiceRepairProjectCostCollector::getTaskId));

		//
		// Create lines groups
		final LinkedHashMap<QuotationLinesGroupKey, QuotationLinesGroupAggregator> linesGroups = new LinkedHashMap<>();
		for (final ServiceRepairProjectCostCollector costCollector : costCollectorsToAggregate)
		{
			linesGroups
					.computeIfAbsent(
							QuotationLinesGroupAggregator.extractKey(costCollector),
							this::createGroupAggregator)
					.add(costCollector);
		}

		//
		// Create the quotation
		final OrderFactory orderFactory = newOrderFactory();
		for (final QuotationLinesGroupAggregator groupAggregator : linesGroups.values())
		{
			groupAggregator.createOrderLines(orderFactory);
		}
		final I_C_Order quotation = orderFactory.createDraft();

		//
		// Group the order lines
		linesGroups.values().forEach(this::groupOrderLinesIfNeeded);
		orderGroupRepository.renumberOrderLinesForOrderId(OrderId.ofRepoId(quotation.getC_Order_ID()));

		//
		//
		generatedQuotationLineIdsIndexedByCostCollectorId = QuotationLineIdsByCostCollectorIdIndex.of(
				linesGroups.values()
						.stream()
						.flatMap(QuotationLinesGroupAggregator::streamQuotationLineIdsIndexedByCostCollectorId)
						.distinct()
						.collect(GuavaCollectors.toImmutableListMultimap()));

		//
		return quotation;
	}

	private void groupOrderLinesIfNeeded(@NonNull final QuotationLinesGroupAggregator linesGroup)
	{
		final GroupTemplate groupTemplate = linesGroup.getGroupTemplate();
		if (groupTemplate == null)
		{
			return;
		}

		final ImmutableList<OrderLineId> orderLineIds = getOrderLineIds(linesGroup);
		if (orderLineIds.isEmpty())
		{
			return;
		}

		orderGroupRepository.prepareNewGroup()
				.groupTemplate(groupTemplate)
				.createGroup(orderLineIds);
	}

	private static ImmutableList<OrderLineId> getOrderLineIds(@NonNull final QuotationLinesGroupAggregator linesGroup)
	{
		return linesGroup.streamQuotationLineIdsIndexedByCostCollectorId()
				.map(entry -> entry.getValue().getOrderLineId())
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	private QuotationLinesGroupAggregator createGroupAggregator(@NonNull final QuotationLinesGroupKey key)
	{
		if (key.getType() == QuotationLinesGroupKey.Type.REPAIRED_PRODUCT)
		{
			final ServiceRepairProjectTask task = tasksById.get(key.getTaskId());
			if (task == null)
			{
				// shall not happen
				throw new AdempiereException("No task found for " + key);
			}

			final int groupIndex = nextRepairingGroupIndex.getAndIncrement();

			return RepairedProductAggregator.builder()
					.priceCalculator(priceCalculator)
					.key(key)
					.groupCaption(String.valueOf(groupIndex))
					.repairOrderSummary(task.getRepairOrderSummary())
					.repairServicePerformedId(task.getRepairServicePerformedId())
					.build();
		}
		else if (key.getType() == QuotationLinesGroupKey.Type.OTHERS)
		{
			return OtherLinesAggregator.builder()
					.priceCalculator(priceCalculator)
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown key type: " + key);
		}
	}
	
	private OrderFactory newOrderFactory()
	{
		return OrderFactory.newSalesOrder()
				.docType(quotationDocTypeId)
				.orgId(pricingInfo.getOrgId())
				.dateOrdered(extractDateOrdered(project, pricingInfo.getOrgTimeZone()))
				.datePromised(pricingInfo.getDatePromised())
				.shipBPartner(project.getBpartnerId(), project.getBpartnerLocationId(), project.getBpartnerContactId())
				.salesRepId(project.getSalesRepId())
				.pricingSystemId(pricingInfo.getPricingSystemId())
				.warehouseId(project.getWarehouseId())
				.paymentTermId(project.getPaymentTermId())
				.campaignId(project.getCampaignId())
				.projectId(project.getProjectId());
	}

	
	public final QuotationLineIdsByCostCollectorIdIndex getQuotationLineIdsIndexedByCostCollectorId()
	{
		Objects.requireNonNull(generatedQuotationLineIdsIndexedByCostCollectorId, "generatedQuotationLineIdsIndexedByCostCollectorId");
		return generatedQuotationLineIdsIndexedByCostCollectorId;
	}

	public QuotationAggregator addAll(@NonNull final List<ServiceRepairProjectCostCollector> costCollectors)
	{
		this.costCollectorsToAggregate.addAll(costCollectors);
		return this;
	}
}
