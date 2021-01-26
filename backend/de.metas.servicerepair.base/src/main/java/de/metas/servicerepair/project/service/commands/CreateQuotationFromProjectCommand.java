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

package de.metas.servicerepair.project.service.commands;

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderFactory;
import de.metas.order.OrderId;
import de.metas.order.OrderLineBuilder;
import de.metas.organization.IOrgDAO;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.servicerepair.project.model.PartOwnership;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.X_C_DocType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class CreateQuotationFromProjectCommand
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ServiceRepairProjectService projectService;

	private final ProjectId projectId;

	@Builder
	private CreateQuotationFromProjectCommand(
			@NonNull final ServiceRepairProjectService projectService,
			@NonNull final ProjectId projectId)
	{
		this.projectService = projectService;
		this.projectId = projectId;
	}

	public OrderId execute()
	{
		final ServiceRepairProjectInfo fromProject = projectService.getById(projectId);

		final QuotationAggregator quotationAggregator = newQuotationAggregator(fromProject);

		for (final ServiceRepairProjectCostCollector costCollector : projectService.getCostCollectorsByProjectButNotInProposal(projectId))
		{
			if (costCollector.getCustomerQuotationLineId() != null)
			{
				continue;
			}

			quotationAggregator.add(costCollector);
		}

		final I_C_Order order = quotationAggregator.createDraft();

		projectService.setCustomerQuotationToCostCollectors(quotationAggregator.getQuotationLineIdsIndexedByCostCollectorId());

		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	private static QuotationLineKey extractQuotationLineKey(final ServiceRepairProjectCostCollector costCollector)
	{
		return QuotationLineKey.builder()
				.productId(costCollector.getProductId())
				.uomId(costCollector.getUomId())
				.partOwnership(costCollector.getPartOwnership())
				.build();
	}

	private QuotationAggregator newQuotationAggregator(@NonNull final ServiceRepairProjectInfo project)
	{
		final OrderFactory orderFactory = OrderFactory.newSalesOrder()
				.docType(getQuotationDocTypeId(project))
				.orgId(project.getClientAndOrgId().getOrgId())
				.dateOrdered(extractDateOrdered(project))
				.datePromised(extractDatePromised(project))
				.shipBPartner(
						project.getBpartnerId(),
						project.getBpartnerLocationId(),
						project.getBpartnerContactId())
				.salesRepId(project.getSalesRepId())
				.warehouseId(project.getWarehouseId())
				.paymentTermId(project.getPaymentTermId())
				.campaignId(project.getCampaignId())
				.projectId(project.getProjectId());

		getPricingSystemId(project).ifPresent(orderFactory::pricingSystemId);

		return QuotationAggregator.builder()
				.orderFactory(orderFactory)
				.build();
	}

	private DocTypeId getQuotationDocTypeId(@NonNull final ServiceRepairProjectInfo project)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_SalesOrder)
				.docSubType(X_C_DocType.DOCSUBTYPE_Proposal)
				.adClientId(project.getClientAndOrgId().getClientId().getRepoId())
				.adOrgId(project.getClientAndOrgId().getOrgId().getRepoId())
				.build());
	}

	private ZonedDateTime extractDatePromised(@NonNull final ServiceRepairProjectInfo project)
	{
		final ZonedDateTime dateFinish = project.getDateFinish();
		if (dateFinish != null)
		{
			return dateFinish;
		}

		final ZoneId timeZone = orgDAO.getTimeZone(project.getClientAndOrgId().getOrgId());
		return SystemTime.asZonedDateTimeAtEndOfDay(timeZone);
	}

	private static LocalDate extractDateOrdered(@NonNull final ServiceRepairProjectInfo project)
	{
		final LocalDate dateContract = project.getDateContract();
		return dateContract != null ? dateContract : SystemTime.asLocalDate();
	}

	private Optional<PricingSystemId> getPricingSystemId(@NonNull final ServiceRepairProjectInfo project)
	{
		final PriceListVersionId priceListVersionId = project.getPriceListVersionId();
		if (priceListVersionId == null)
		{
			return Optional.empty();
		}

		final I_M_PriceList priceList = priceListDAO.getPriceListByPriceListVersionId(priceListVersionId);
		return Optional.of(PricingSystemId.ofRepoId(priceList.getM_PricingSystem_ID()));
	}

	@Value
	@Builder
	private static class QuotationLineKey
	{
		@NonNull ProductId productId;
		@NonNull UomId uomId;
		@NonNull PartOwnership partOwnership;
	}

	private static class QuotationAggregator
	{
		private final OrderFactory orderFactory;
		private final LinkedHashMap<QuotationLineKey, QuotationLineAggregator> quotationLineAggregators = new LinkedHashMap<>();

		@Builder
		private QuotationAggregator(@NonNull final OrderFactory orderFactory)
		{
			this.orderFactory = orderFactory;
		}

		public I_C_Order createDraft()
		{
			return orderFactory.createDraft();
		}

		public final Map<ServiceRepairProjectCostCollectorId, OrderAndLineId> getQuotationLineIdsIndexedByCostCollectorId()
		{
			return quotationLineAggregators.values()
					.stream()
					.flatMap(QuotationLineAggregator::streamQuotationLineIdsIndexedByCostCollectorId)
					.collect(GuavaCollectors.toImmutableMap());
		}

		public void add(@NonNull final ServiceRepairProjectCostCollector costCollector)
		{
			final QuotationLineAggregator quotationLineAggregator = quotationLineAggregators.computeIfAbsent(
					extractQuotationLineKey(costCollector),
					key -> QuotationLineAggregator.builder().orderFactory(orderFactory).key(key).build());

			quotationLineAggregator.add(costCollector);
		}
	}

	private static class QuotationLineAggregator
	{
		private final OrderLineBuilder orderLineBuilder;
		private final HashSet<ServiceRepairProjectCostCollectorId> costCollectorIds = new HashSet<>();

		@Builder
		private QuotationLineAggregator(
				@NonNull final OrderFactory orderFactory,
				@NonNull final QuotationLineKey key)
		{
			orderLineBuilder = orderFactory.newOrderLine().productId(key.getProductId());

			if (key.getPartOwnership().isOwnedByCustomer())
			{
				orderLineBuilder.manualPrice(BigDecimal.ZERO);
			}
		}

		public void add(@NonNull final ServiceRepairProjectCostCollector costCollector)
		{
			final Quantity qty = costCollector.getQtyReservedOrConsumed();
			orderLineBuilder.addQty(qty);

			costCollectorIds.add(costCollector.getId());
		}

		public Stream<Map.Entry<ServiceRepairProjectCostCollectorId, OrderAndLineId>> streamQuotationLineIdsIndexedByCostCollectorId()
		{
			final OrderAndLineId quotationLineId = getCustomerQuotationLineId();
			return costCollectorIds.stream().map(costCollectorId -> GuavaCollectors.entry(costCollectorId, quotationLineId));
		}

		public OrderAndLineId getCustomerQuotationLineId()
		{
			return orderLineBuilder.getCreatedOrderAndLineId();
		}
	}

}
