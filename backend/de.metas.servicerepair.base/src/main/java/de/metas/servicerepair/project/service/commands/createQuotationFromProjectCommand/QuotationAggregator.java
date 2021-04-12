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

import com.google.common.collect.ImmutableMap;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderFactory;
import de.metas.order.OrderLineBuilder;
import de.metas.organization.IOrgDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
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
import java.util.Map;
import java.util.Objects;

public class QuotationAggregator
{
	private final ServiceRepairProjectInfo project;
	private final ProductId serviceProductId;
	private final UomId serviceProductUomId;
	private final ProjectQuotationPricingInfo pricingInfo;
	private final DocTypeId quotationDocTypeId;

	private final ProjectQuotationPriceCalculator priceCalculator;

	private final ArrayList<ServiceRepairProjectCostCollector> costCollectorsToAggregate = new ArrayList<>();
	private ImmutableMap<ServiceRepairProjectCostCollectorId, OrderAndLineId> generatedQuotationLineIdsIndexedByCostCollectorId;

	@Builder
	private QuotationAggregator(
			@NonNull final IOrgDAO orgDAO,
			@NonNull final ServiceRepairProjectInfo project,
			@NonNull final ProjectQuotationPricingInfo pricingInfo,
			@NonNull final ProductId serviceProductId,
			@NonNull final UomId serviceProductUomId,
			@NonNull final DocTypeId quotationDocTypeId)
	{
		this.project = project;
		this.pricingInfo = pricingInfo;
		this.serviceProductId = serviceProductId;
		this.serviceProductUomId = serviceProductUomId;
		this.quotationDocTypeId = quotationDocTypeId;

		final IPricingBL pricingBL = Services.get(IPricingBL.class);
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

		// Make sure they are ordered by Task ID, so the quotation lines will be in same order as the repair tasks
		costCollectorsToAggregate.sort(Comparator.comparing(ServiceRepairProjectCostCollector::getTaskId));

		//
		// Create the empty quotation line aggregators (i.e. buckets)
		final LinkedHashMap<QuotationLineKey, QuotationLineAggregator> quotationLineAggregators = new LinkedHashMap<>();
		for (final ServiceRepairProjectCostCollector costCollector : costCollectorsToAggregate)
		{
			final QuotationLineKey key = extractQuotationLineKey(costCollector);
			quotationLineAggregators.computeIfAbsent(key, this::newQuotationLineAggregator);
		}

		//
		// Iterate (again!) all cost collectors and add each of them to each bucket
		for (final ServiceRepairProjectCostCollector costCollector : costCollectorsToAggregate)
		{
			final QuotationLineKey costCollectorKey = extractQuotationLineKey(costCollector);

			for (final Map.Entry<QuotationLineKey, QuotationLineAggregator> keyAndLineAggregator : quotationLineAggregators.entrySet())
			{
				final QuotationLineKey lineAggregatorKey = keyAndLineAggregator.getKey();
				final QuotationLineAggregator lineAggregator = keyAndLineAggregator.getValue();

				if (QuotationLineKey.equals(costCollectorKey, lineAggregatorKey))
				{
					lineAggregator.collectMatchingItem(costCollector);
				}
				else
				{
					lineAggregator.collectNotMatchingItem(costCollector);
				}
			}
		}

		final OrderFactory orderFactory = OrderFactory.newSalesOrder()
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

		for (final QuotationLineAggregator quotationLineAggregator : quotationLineAggregators.values())
		{
			final OrderLineBuilder orderLineBuilder = orderFactory.newOrderLine()
					.productId(quotationLineAggregator.getProductId())
					.asiId(quotationLineAggregator.getAsiId())
					.qty(quotationLineAggregator.getQty())
					.manualPrice(quotationLineAggregator.getManualPrice())
					.details(quotationLineAggregator.getDetails());

			quotationLineAggregator.setOrderLineBuilderUsed(orderLineBuilder);
		}

		final I_C_Order quotation = orderFactory.createDraft();

		generatedQuotationLineIdsIndexedByCostCollectorId = quotationLineAggregators.values()
				.stream()
				.flatMap(QuotationLineAggregator::streamQuotationLineIdsIndexedByCostCollectorId)
				.collect(GuavaCollectors.toImmutableMap());

		return quotation;
	}

	public final ImmutableMap<ServiceRepairProjectCostCollectorId, OrderAndLineId> getQuotationLineIdsIndexedByCostCollectorId()
	{
		Objects.requireNonNull(generatedQuotationLineIdsIndexedByCostCollectorId, "generatedQuotationLineIdsIndexedByCostCollectorId");
		return generatedQuotationLineIdsIndexedByCostCollectorId;
	}

	public QuotationAggregator addAll(@NonNull final List<ServiceRepairProjectCostCollector> costCollectors)
	{
		this.costCollectorsToAggregate.addAll(costCollectors);
		return this;
	}

	private QuotationLineKey extractQuotationLineKey(@NonNull final ServiceRepairProjectCostCollector costCollector)
	{
		final ServiceRepairProjectCostCollectorType type = costCollector.getType();
		if (type == ServiceRepairProjectCostCollectorType.RepairingConsumption)
		{
			return QuotationLineKey.builder()
					.type(ServiceRepairProjectCostCollectorType.RepairingConsumption)
					.productId(serviceProductId)
					.uomId(serviceProductUomId)
					.build();
		}
		else if (type == ServiceRepairProjectCostCollectorType.RepairedProductToReturn)
		{
			return QuotationLineKey.builder()
					.type(ServiceRepairProjectCostCollectorType.RepairedProductToReturn)
					.productId(costCollector.getProductId())
					.asiId(costCollector.getAsiId())
					.warrantyCase(costCollector.getWarrantyCase())
					.uomId(costCollector.getUomId())
					.singleCostCollectorId(costCollector.getId())
					.build();
		}
		else if (type == ServiceRepairProjectCostCollectorType.SparePartsOwnedByCustomer)
		{
			return QuotationLineKey.builder()
					.type(ServiceRepairProjectCostCollectorType.SparePartsOwnedByCustomer)
					.productId(costCollector.getProductId())
					.uomId(costCollector.getUomId())
					.build();
		}
		else if (type == ServiceRepairProjectCostCollectorType.SparePartsToBeInvoiced)
		{
			return QuotationLineKey.builder()
					.type(ServiceRepairProjectCostCollectorType.SparePartsToBeInvoiced)
					.productId(costCollector.getProductId())
					.uomId(costCollector.getUomId())
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown type: " + type);
		}
	}

	private QuotationLineAggregator newQuotationLineAggregator(@NonNull final QuotationLineKey key)
	{
		return QuotationLineAggregator.builder()
				.key(key)
				.priceCalculator(priceCalculator)
				.build();
	}
}
