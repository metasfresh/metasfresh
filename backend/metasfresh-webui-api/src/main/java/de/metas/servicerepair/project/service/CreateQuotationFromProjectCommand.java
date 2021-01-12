/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.servicerepair.project.service;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.order.OrderFactory;
import de.metas.order.OrderId;
import de.metas.order.OrderLineBuilder;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.project.ProjectAndLineId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectLine;
import de.metas.project.service.HUProjectService;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;

class CreateQuotationFromProjectCommand
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final HUProjectService projectService;

	private final ProjectId projectId;

	@Builder
	private CreateQuotationFromProjectCommand(
			@NonNull final HUProjectService projectService,
			@NonNull final ProjectId projectId)
	{
		this.projectService = projectService;
		this.projectId = projectId;
	}

	public OrderId execute()
	{
		//checkIsServiceOrRepairProject(projectId).throwExceptionIfRejected();

		final I_C_Project fromProject = projectService.getById(projectId);

		final OrderFactory orderFactory = newOrderFactory(fromProject);

		final ArrayList<ProjectLineAndOrderLine> projectLineAndOrderLines = new ArrayList<>();
		for (final ProjectLine line : projectService.getLines(projectId))
		{
			if (line.getSalesOrderLineId() != null)
			{
				continue;
			}

			final ProductId productId = line.getProductId();
			final Quantity qty = line.getPlannedQty();
			final OrderLineBuilder orderLineBuilder = orderFactory.orderLineByProductAndUomOrCreate(productId, qty.getUomId());
			orderLineBuilder.addQty(qty);

			projectLineAndOrderLines.add(ProjectLineAndOrderLine.of(line.getId(), orderLineBuilder));
		}

		final I_C_Order order = orderFactory.createDraft();

		for (final ProjectLineAndOrderLine projectLineAndOrderLine : projectLineAndOrderLines)
		{
			projectService.linkToOrderLine(
					projectLineAndOrderLine.getProjectLineId(),
					projectLineAndOrderLine.getOrderLineBuilder().getCreatedOrderAndLineId());
		}

		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	private OrderFactory newOrderFactory(@NonNull final I_C_Project project)
	{
		final OrgId orgId = OrgId.ofRepoId(project.getAD_Org_ID());
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_SalesOrder)
				.docSubType(X_C_DocType.DOCSUBTYPE_Proposal)
				.adClientId(project.getAD_Client_ID())
				.adOrgId(orgId.getRepoId())
				.build());

		final OrderFactory orderFactory = OrderFactory.newSalesOrder()
				.docType(docTypeId)
				.orgId(orgId)
				.dateOrdered(CoalesceUtil.coalesceSuppliers(
						() -> TimeUtil.asLocalDate(project.getDateContract()),
						() -> SystemTime.asLocalDate()))
				.datePromised(CoalesceUtil.coalesceSuppliers(
						() -> TimeUtil.asZonedDateTime(project.getDateFinish()),
						() -> SystemTime.asZonedDateTimeAtEndOfDay(timeZone)))
				.shipBPartner(
						BPartnerId.ofRepoId(project.getC_BPartner_ID()),
						BPartnerLocationId.ofRepoIdOrNull(project.getC_BPartner_ID(), project.getC_BPartner_Location_ID()),
						BPartnerContactId.ofRepoIdOrNull(project.getC_BPartner_ID(), project.getAD_User_ID()))
				.salesRepId(project.getSalesRep_ID())
				.warehouseId(project.getM_Warehouse_ID())
				.paymentTermId(project.getC_PaymentTerm_ID())
				.campaignId(project.getC_Campaign_ID())
				.projectId(project.getC_Project_ID());

		getPricingSystemId(project).ifPresent(orderFactory::pricingSystemId);

		return orderFactory;
	}

	private Optional<PricingSystemId> getPricingSystemId(final I_C_Project project)
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoIdOrNull(project.getM_PriceList_Version_ID());
		if (priceListVersionId == null)
		{
			return Optional.empty();
		}

		final I_M_PriceList priceList = priceListDAO.getPriceListByPriceListVersionId(priceListVersionId);
		return Optional.of(PricingSystemId.ofRepoId(priceList.getM_PricingSystem_ID()));
	}

	@Value(staticConstructor = "of")
	private static class ProjectLineAndOrderLine
	{
		@NonNull ProjectAndLineId projectLineId;
		@NonNull OrderLineBuilder orderLineBuilder;
	}

}
