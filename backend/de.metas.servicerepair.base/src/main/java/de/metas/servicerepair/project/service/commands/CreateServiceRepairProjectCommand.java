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

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.exceptions.PriceListNotFoundException;
import de.metas.pricing.exceptions.PriceListVersionNotFoundException;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.service.CreateProjectRequest;
import de.metas.quantity.Quantity;
import de.metas.request.RequestId;
import de.metas.request.api.IRequestBL;
import de.metas.servicerepair.customerreturns.RepairCustomerReturnsService;
import de.metas.servicerepair.customerreturns.SparePartsReturnCalculation;
import de.metas.servicerepair.project.repository.requests.CreateRepairProjectTaskRequest;
import de.metas.servicerepair.project.repository.requests.CreateSparePartsProjectTaskRequest;
import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_R_Request;
import org.compiere.util.TimeUtil;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class CreateServiceRepairProjectCommand
{
	private final IRequestBL requestBL = Services.get(IRequestBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final RepairCustomerReturnsService customerReturnsService;
	private final ServiceRepairProjectService projectService;

	private final RequestId requestId;

	@Builder
	private CreateServiceRepairProjectCommand(
			@NonNull final RepairCustomerReturnsService customerReturnsService,
			@NonNull final ServiceRepairProjectService projectService,
			@NonNull final RequestId requestId)
	{
		this.customerReturnsService = customerReturnsService;
		this.projectService = projectService;
		this.requestId = requestId;
	}

	public ProjectId execute()
	{
		final I_R_Request request = requestBL.getById(requestId);
		final OrgId orgId = OrgId.ofRepoId(request.getAD_Org_ID());

		final InOutId customerReturnId = InOutId.ofRepoId(request.getM_InOut_ID());
		final I_M_InOut customerReturn = inoutBL.getById(customerReturnId);
		final BPartnerLocationId bpartnerAndLocationId = BPartnerLocationId.ofRepoId(customerReturn.getC_BPartner_ID(), customerReturn.getC_BPartner_Location_ID());
		final BPartnerContactId contactId = BPartnerContactId.ofRepoIdOrNull(customerReturn.getC_BPartner_ID(), customerReturn.getAD_User_ID());
		final ZonedDateTime customerReturnDate = TimeUtil.asZonedDateTime(customerReturn.getMovementDate(), orgDAO.getTimeZone(orgId));
		final PricingInfo pricingInfo = getPricingInfo(bpartnerAndLocationId, customerReturnDate);

		final ProjectId projectId = projectService.createProjectHeader(CreateProjectRequest.builder()
				.orgId(orgId)
				.projectCategory(ProjectCategory.ServiceOrRepair)
				.bpartnerAndLocationId(bpartnerAndLocationId)
				.contactId(contactId)
				.priceListVersionId(pricingInfo.getPriceListVersionId())
				.currencyId(pricingInfo.getCurrencyId())
				.warehouseId(WarehouseId.ofRepoId(customerReturn.getM_Warehouse_ID()))
				.build());

		final SparePartsReturnCalculation sparePartsCalculation = customerReturnsService.getSparePartsCalculation(customerReturnId);

		//
		// Repair Tasks
		for (final SparePartsReturnCalculation.FinishedGoodToRepair productToRepair : sparePartsCalculation.getFinishedGoods())
		{
			projectService.createProjectTask(CreateRepairProjectTaskRequest.builder()
					.projectId(projectId)
					.orgId(orgId)
					.customerReturnLineId(productToRepair.getCustomerReturnLineId())
					.productId(productToRepair.getProductId())
					.asiId(productToRepair.getAsiId())
					.warrantyCase(productToRepair.getWarrantyCase())
					.qtyRequired(productToRepair.getQty())
					.repairVhuId(productToRepair.getRepairVhuId())
					.build());
		}

		//
		// Spare Parts Tasks
		for (final ProductId sparePartId : sparePartsCalculation.getAllSparePartIds())
		{
			Quantity qtyRequiredGross = sparePartsCalculation.computeQtyOfSparePartsRequiredGross(sparePartId, uomConversionBL).orElse(null);
			final ArrayList<CreateSparePartsProjectTaskRequest.AlreadyReturnedQty> alreadyReturnedQtys = new ArrayList<>();

			UomId uomId = null;
			for (final SparePartsReturnCalculation.SparePart sparePart : sparePartsCalculation.getSpareParts(sparePartId))
			{
				final Quantity qtyAlreadyReturned;
				if (uomId == null)
				{
					if (qtyRequiredGross == null)
					{
						qtyAlreadyReturned = sparePart.getQty();
						qtyRequiredGross = qtyAlreadyReturned.toZero();
						uomId = qtyAlreadyReturned.getUomId();
					}
					else
					{
						uomId = qtyRequiredGross.getUomId();
						qtyAlreadyReturned = sparePart.getQty(uomId, uomConversionBL);
					}
				}
				else
				{
					qtyAlreadyReturned = sparePart.getQty(uomId, uomConversionBL);
				}

				alreadyReturnedQtys.add(CreateSparePartsProjectTaskRequest.AlreadyReturnedQty.builder()
						.qty(qtyAlreadyReturned)
						.sparePartsVhuId(sparePart.getSparePartsVhuId())
						.build());
			}

			if (qtyRequiredGross == null && alreadyReturnedQtys.isEmpty())
			{
				// shall not happen
				continue;
			}

			projectService.createProjectTask(CreateSparePartsProjectTaskRequest.builder()
					.projectId(projectId)
					.orgId(orgId)
					.productId(sparePartId)
					.qtyRequired(qtyRequiredGross)
					.alreadyReturnedQtys(alreadyReturnedQtys)
					.build());
		}

		request.setC_Project_ID(projectId.getRepoId());
		requestBL.save(request);

		customerReturn.setC_Project_ID(projectId.getRepoId());
		inoutBL.save(customerReturn);

		return projectId;
	}

	@Value
	@Builder
	private static class PricingInfo
	{
		@NonNull PriceListVersionId priceListVersionId;
		@NonNull CurrencyId currencyId;
	}

	private PricingInfo getPricingInfo(
			@NonNull final BPartnerLocationId bpartnerAndLocationId,
			@NonNull final ZonedDateTime customerReturnDate)
	{
		final PricingSystemId pricingSystemId = bpartnerDAO.retrievePricingSystemIdOrNull(bpartnerAndLocationId.getBpartnerId(), SOTrx.SALES);
		if (pricingSystemId == null)
		{
			throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@")
					.setParameter("C_BPartner_ID", bpartnerDAO.getBPartnerNameById(bpartnerAndLocationId.getBpartnerId()))
					.setParameter("SOTrx", SOTrx.SALES);
		}

		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, bpartnerAndLocationId, SOTrx.SALES);
		if (priceListId == null)
		{
			throw new PriceListNotFoundException(
					priceListDAO.getPricingSystemName(pricingSystemId),
					SOTrx.SALES);
		}

		final I_M_PriceList_Version priceListVersion = priceListDAO.retrievePriceListVersionOrNull(priceListId, customerReturnDate, null);
		if (priceListVersion == null)
		{
			throw new PriceListVersionNotFoundException(priceListId, customerReturnDate);
		}

		return PricingInfo.builder()
				.priceListVersionId(PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID()))
				.currencyId(priceListDAO.getCurrencyId(priceListId))
				.build();
	}

}
