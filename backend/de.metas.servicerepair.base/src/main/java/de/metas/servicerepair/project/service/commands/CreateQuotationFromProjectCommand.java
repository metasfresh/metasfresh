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

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import de.metas.servicerepair.project.service.commands.createQuotationFromProjectCommand.ProjectQuotationPricingInfo;
import de.metas.servicerepair.project.service.commands.createQuotationFromProjectCommand.QuotationAggregator;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CreateQuotationFromProjectCommand
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ServiceRepairProjectService projectService;

	private final ProjectId projectId;
	private final ProductId serviceProductId;

	@Builder
	private CreateQuotationFromProjectCommand(
			@NonNull final ServiceRepairProjectService projectService,
			@NonNull final ProjectId projectId,
			@NonNull final ProductId serviceProductId)
	{
		this.projectService = projectService;
		this.projectId = projectId;
		this.serviceProductId = serviceProductId;
	}

	public OrderId execute()
	{
		final ImmutableList<ServiceRepairProjectCostCollector> costCollectors = projectService.getByProjectIdButNotIncludedInCustomerQuotation(projectId)
				.stream()
				.filter(ServiceRepairProjectCostCollector::isNotIncludedInCustomerQuotation) // redundant but feels safe
				.collect(ImmutableList.toImmutableList());
		if (costCollectors.isEmpty())
		{
			throw new AdempiereException("Everything is already quoted");
		}

		final ServiceRepairProjectInfo fromProject = projectService.getById(projectId);
		final QuotationAggregator quotationAggregator = newQuotationAggregator(fromProject);

		final I_C_Order order = quotationAggregator
				.addAll(costCollectors)
				.createDraft();

		projectService.setCustomerQuotationToCostCollectors(quotationAggregator.getQuotationLineIdsIndexedByCostCollectorId());

		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	private QuotationAggregator newQuotationAggregator(@NonNull final ServiceRepairProjectInfo project)
	{
		return QuotationAggregator.builder()
				.orgDAO(orgDAO)
				.project(project)
				.pricingInfo(getPricingInfo(project))
				.serviceProductId(serviceProductId)
				.serviceProductUomId(productBL.getStockUOMId(serviceProductId))
				.quotationDocTypeId(getQuotationDocTypeId(project))
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

	private ProjectQuotationPricingInfo getPricingInfo(@NonNull final ServiceRepairProjectInfo project)
	{
		final PriceListVersionId priceListVersionId = project.getPriceListVersionId();
		if (priceListVersionId == null)
		{
			throw new AdempiereException("@NotFound@ @M_PriceList_Version_ID@")
					.setParameter("project", project);
		}
		final I_M_PriceList priceList = priceListDAO.getPriceListByPriceListVersionId(priceListVersionId);

		final OrgId orgId = project.getClientAndOrgId().getOrgId();
		final ZoneId orgTimeZone = orgDAO.getTimeZone(orgId);

		return ProjectQuotationPricingInfo.builder()
				.orgId(orgId)
				.orgTimeZone(orgTimeZone)
				.shipBPartnerId(project.getBpartnerId())
				.datePromised(extractDatePromised(project, orgTimeZone))
				.pricingSystemId(PricingSystemId.ofRepoId(priceList.getM_PricingSystem_ID()))
				.priceListId(PriceListId.ofRepoId(priceList.getM_PriceList_ID()))
				.priceListVersionId(priceListVersionId)
				.currencyId(CurrencyId.ofRepoId(priceList.getC_Currency_ID()))
				.countryId(CountryId.ofRepoId(priceList.getC_Country_ID()))
				.build();
	}

	private static ZonedDateTime extractDatePromised(
			@NonNull final ServiceRepairProjectInfo project,
			@NonNull final ZoneId timeZone)
	{
		final ZonedDateTime dateFinish = project.getDateFinish();
		return dateFinish != null
				? TimeUtil.convertToTimeZone(dateFinish, timeZone)
				: SystemTime.asZonedDateTimeAtEndOfDay(timeZone);
	}
}
