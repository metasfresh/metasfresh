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
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Map;

public class QuotationAggregator
{
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);

	private final ServiceRepairProjectInfo project;
	private final ProductId serviceProductId;
	private final UomId serviceProductUomId;
	private final ProjectQuotationPricingInfo pricingInfo;
	private final DocTypeId quotationDocTypeId;

	private final ProjectQuotationPriceCalculator priceCalculator;

	private final LinkedHashMap<QuotationLineKey, QuotationLineAggregator> quotationLineAggregators = new LinkedHashMap<>();

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
		if (quotationLineAggregators.isEmpty())
		{
			throw new AdempiereException("Nothing to quote");
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
					.qty(quotationLineAggregator.getQty())
					.manualPrice(quotationLineAggregator.getManualPrice())
					.details(quotationLineAggregator.getDetails());

			quotationLineAggregator.setOrderLineBuilderUsed(orderLineBuilder);
		}

		return orderFactory.createDraft();
	}

	public final Map<ServiceRepairProjectCostCollectorId, OrderAndLineId> getQuotationLineIdsIndexedByCostCollectorId()
	{
		return quotationLineAggregators.values()
				.stream()
				.flatMap(QuotationLineAggregator::streamQuotationLineIdsIndexedByCostCollectorId)
				.collect(GuavaCollectors.toImmutableMap());
	}

	public void add(
			@NonNull final ServiceRepairProjectTask task,
			@NonNull final ServiceRepairProjectCostCollector costCollector)
	{
		quotationLineAggregators
				.computeIfAbsent(extractQuotationLineKey(task, costCollector), this::newQuotationLineAggregator)
				.add(costCollector);
	}

	private QuotationLineKey extractQuotationLineKey(
			@NonNull final ServiceRepairProjectTask task,
			@NonNull final ServiceRepairProjectCostCollector costCollector)
	{
		if (task.getType().isRepair())
		{
			if (task.isRepairInternalComponent(costCollector.getProductId()))
			{
				return QuotationLineKey.builder()
						.type(QuotationLineKeyType.INTERNALLY_CONSUMED_PRODUCTS)
						.productId(serviceProductId)
						.uomId(serviceProductUomId)
						.build();
			}
			else
			{
				return QuotationLineKey.builder()
						.type(QuotationLineKeyType.REPAIRED_PRODUCT_TO_RETURN)
						.productId(costCollector.getProductId())
						.uomId(costCollector.getUomId())
						.build();
			}
		}
		else // assume we we are dealing with spare parts
		{
			if (costCollector.getPartOwnership().isOwnedByCustomer())
			{
				return QuotationLineKey.builder()
						.type(QuotationLineKeyType.SPARE_PARTS_OWNED_BY_CUSTOMER)
						.productId(costCollector.getProductId())
						.uomId(costCollector.getUomId())
						.build();
			}
			else
			{
				return QuotationLineKey.builder()
						.type(QuotationLineKeyType.SPARE_PARTS_TO_BE_INVOICED)
						.productId(costCollector.getProductId())
						.uomId(costCollector.getUomId())
						.build();
			}
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
