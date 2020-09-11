package org.eevolution.mrp.spi.impl.ddorder;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.ASICopy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alternative;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_DD_Order;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDAO;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DDOrderUtil;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-mrp
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Service
public class DDOrderProducer
{
	private final IDDOrderDAO ddOrdersRepo = Services.get(IDDOrderDAO.class);
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);
	private final IMRPBL mrpBL = Services.get(IMRPBL.class);
	private final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);

	public static final ModelDynAttributeAccessor<I_DD_Order, MaterialDispoGroupId> ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID = //
			new ModelDynAttributeAccessor<>(I_DD_Order.class.getName(), "DDOrderRequestedEvent_GroupId", MaterialDispoGroupId.class);

	public I_DD_Order createDDOrder(
			@NonNull final DDOrder pojo,
			@NonNull final Date dateOrdered)
	{
		return createDDOrder(pojo, dateOrdered, null);
	}

	public I_DD_Order createDDOrder(
			@NonNull final DDOrder pojo,
			@NonNull final IMRPCreateSupplyRequest request)
	{
		return createDDOrder(pojo,
				request.getMrpContext().getDate(),
				request);
	}

	/**
	 *
	 * @param ddOrder
	 * @param request may be {@code null}. If not-null, then the method also does some {@link I_PP_MRP} related stuff that is likely to become obsolete soon.
	 * @return
	 */
	private I_DD_Order createDDOrder(
			@NonNull final DDOrder ddOrder,
			@NonNull final Date dateOrdered,
			@Nullable final IMRPCreateSupplyRequest request)
	{
		final ProductPlanningId productPlanningId = ProductPlanningId.ofRepoId(ddOrder.getProductPlanningId());
		final I_PP_Product_Planning productPlanning = productPlanningsRepo.getById(productPlanningId);

		final BPartnerLocationId orgBPartnerLocationId = DDOrderUtil.retrieveOrgBPartnerLocationId(ddOrder.getOrgId());

		final I_DD_Order ddOrderRecord = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID.setValue(ddOrderRecord, ddOrder.getMaterialDispoGroupId());

		ddOrderRecord.setAD_Org_ID(ddOrder.getOrgId().getRepoId());
		ddOrderRecord.setMRP_Generated(true);
		ddOrderRecord.setMRP_AllowCleanup(true);
		ddOrderRecord.setPP_Plant_ID(ddOrder.getPlantId());
		ddOrderRecord.setC_BPartner_ID(orgBPartnerLocationId != null ? orgBPartnerLocationId.getBpartnerId().getRepoId() : -1);
		ddOrderRecord.setC_BPartner_Location_ID(orgBPartnerLocationId != null ? orgBPartnerLocationId.getRepoId() : -1);
		ddOrderRecord.setAD_User_ID(productPlanning.getPlanner_ID()); // FIXME: improve performances/cache and retrive Primary BP's User
		ddOrderRecord.setSalesRep_ID(productPlanning.getPlanner_ID());

		ddOrderRecord.setC_DocType_ID(getDocTypeId(ddOrder.getOrgId()).getRepoId());

		final WarehouseId inTransitWarehouseId = DDOrderUtil.retrieveInTransitWarehouseId(ddOrder.getOrgId());
		ddOrderRecord.setM_Warehouse_ID(inTransitWarehouseId.getRepoId());

		ddOrderRecord.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrderRecord.setDocAction(X_DD_Order.DOCACTION_Complete);
		ddOrderRecord.setDateOrdered(new Timestamp(dateOrdered.getTime()));
		ddOrderRecord.setDatePromised(TimeUtil.asTimestamp(ddOrder.getDatePromised()));
		ddOrderRecord.setM_Shipper_ID(ddOrder.getShipperId());
		ddOrderRecord.setIsInDispute(false);
		ddOrderRecord.setIsInTransit(false);

		ddOrderRecord.setPP_Product_Planning_ID(productPlanning.getPP_Product_Planning_ID());

		ddOrdersRepo.save(ddOrderRecord);

		for (final DDOrderLine linePojo : ddOrder.getLines())
		{
			// Create DD Order Line
			final I_DD_OrderLine ddOrderline = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, ddOrderRecord);
			ddOrderline.setAD_Org_ID(ddOrder.getOrgId().getRepoId());
			ddOrderline.setDD_Order(ddOrderRecord);
			ddOrderline.setC_OrderLineSO_ID(linePojo.getSalesOrderLineId());
			ddOrderline.setC_BPartner_ID(linePojo.getBPartnerId());

			if (linePojo.getSalesOrderLineId() > 0)
			{
				ddOrderline.setC_BPartner_ID(ddOrderline.getC_OrderLineSO().getC_BPartner_ID());
			}

			final I_DD_NetworkDistributionLine networkDistributionLine = InterfaceWrapperHelper.create(Env.getCtx(), linePojo.getNetworkDistributionLineId(), I_DD_NetworkDistributionLine.class, ITrx.TRXNAME_ThreadInherited);

			ddOrderline.setDD_NetworkDistributionLine_ID(networkDistributionLine.getDD_NetworkDistributionLine_ID());

			// get supply source warehouse and locator
			final I_M_Warehouse warehouseFrom = networkDistributionLine.getM_WarehouseSource();
			final I_M_Locator locatorFrom = warehouseBL.getDefaultLocator(warehouseFrom);

			// get supply target warehouse and locator
			final I_M_Warehouse warehouseTo = networkDistributionLine.getM_Warehouse();
			final I_M_Locator locatorTo = warehouseBL.getDefaultLocator(warehouseTo);

			//
			// Locator From/To
			ddOrderline.setM_Locator_ID(locatorFrom.getM_Locator_ID());
			ddOrderline.setM_LocatorTo_ID(locatorTo.getM_Locator_ID());

			//
			// Product, UOM, Qty
			// NOTE: we assume qtyToMove is in "mrpContext.getC_UOM()" which shall be the Product's stocking UOM
			final ProductDescriptor productDescriptor = linePojo.getProductDescriptor();
			final I_M_Product product = load(productDescriptor.getProductId(), I_M_Product.class);

			final I_M_AttributeSetInstance asi = load(productDescriptor.getAttributeSetInstanceId(), I_M_AttributeSetInstance.class);
			final ASICopy asiCopy = ASICopy.newInstance(asi);

			ddOrderline.setM_Product_ID(product.getM_Product_ID());
			ddOrderline.setC_UOM_ID(product.getC_UOM_ID());
			ddOrderline.setQtyEntered(linePojo.getQty());
			ddOrderline.setQtyOrdered(linePojo.getQty());
			ddOrderline.setTargetQty(linePojo.getQty());
			ddOrderline.setM_AttributeSetInstance(asiCopy.copy());
			ddOrderline.setM_AttributeSetInstanceTo(asiCopy.copy());

			//
			// Dates
			ddOrderline.setDateOrdered(ddOrderRecord.getDateOrdered());
			ddOrderline.setDatePromised(ddOrderRecord.getDatePromised());

			//
			// Other flags
			ddOrderline.setIsInvoiced(false);
			ddOrderline.setDD_AllowPush(networkDistributionLine.isDD_AllowPush());
			ddOrderline.setIsKeepTargetPlant(networkDistributionLine.isKeepTargetPlant());

			//
			// Save DD Order Line
			ddOrdersRepo.save(ddOrderline);

			if (request != null)
			{
				//
				// Create DD_OrderLine_Alternatives
				// NOTE: demand MRP line is available only in lot-for-lot order policy
				// TODO: i think we shall get all MRP demands, retrieve their alternatives, aggregate them and create DD_OrderLine_Alternatives
				final I_PP_MRP parentMRPDemand = request.getMRPDemandRecordOrNull();
				if (parentMRPDemand != null)
				{
					final List<I_PP_MRP_Alternative> mrpAlternatives = mrpDAO.retrieveMRPAlternativesQuery(parentMRPDemand).create().list();
					for (final I_PP_MRP_Alternative mrpAlternative : mrpAlternatives)
					{
						createDD_OrderLine_Alternative(ddOrderline, mrpAlternative);
					}
				}
				final Timestamp supplyDateFinishSchedule = TimeUtil.asTimestamp(request.getDemandDate());

				//
				// Set Correct Planning Dates
				final Timestamp supplyDateStartSchedule = TimeUtil.addDays(supplyDateFinishSchedule, 0 - linePojo.getDurationDays());

				final List<I_PP_MRP> mrpList = mrpDAO.retrieveMRPRecords(ddOrderline);
				for (final I_PP_MRP mrp : mrpList)
				{
					mrp.setDateStartSchedule(supplyDateStartSchedule);
					mrp.setDateFinishSchedule(supplyDateFinishSchedule);
					mrpDAO.save(mrp);
				}
			}
		}

		return ddOrderRecord;
	}

	private void createDD_OrderLine_Alternative(
			final I_DD_OrderLine ddOrderLine,
			final I_PP_MRP_Alternative mrpAlternative)
	{
		final I_DD_OrderLine_Alternative ddOrderLineAlt = InterfaceWrapperHelper.newInstance(I_DD_OrderLine_Alternative.class, ddOrderLine);
		ddOrderLineAlt.setAD_Org_ID(mrpAlternative.getAD_Org_ID());
		ddOrderLineAlt.setDD_OrderLine(ddOrderLine);

		final I_M_Product product = mrpAlternative.getM_Product();
		final I_C_UOM uom = mrpBL.getC_UOM(mrpAlternative);
		final BigDecimal qty = mrpAlternative.getQty();

		ddOrderLineAlt.setM_Product(product);
		ddOrderLineAlt.setM_AttributeSetInstance(ddOrderLine.getM_AttributeSetInstance());
		ddOrderLineAlt.setC_UOM(uom);
		ddOrderLineAlt.setQtyOrdered(qty);
		ddOrderLineAlt.setQtyDelivered(BigDecimal.ZERO);
		ddOrderLineAlt.setQtyInTransit(BigDecimal.ZERO);

		ddOrdersRepo.save(ddOrderLineAlt);
	}

	private DocTypeId getDocTypeId(final OrgId orgId)
	{
		final ClientId clientId = orgDAO.getClientIdByOrgId(orgId);

		return docTypesRepo.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
				.adClientId(clientId.getRepoId())
				.adOrgId(orgId.getRepoId())
				.build());
	}
}
