package de.metas.distribution.ddorder.material_dispo;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DDOrderUtil;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.mm.attributes.api.ASICopy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
	private final DDOrderLowLevelService ddOrderLowLevelService;
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);

	public static final ModelDynAttributeAccessor<I_DD_Order, MaterialDispoGroupId> ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID = //
			new ModelDynAttributeAccessor<>(I_DD_Order.class.getName(), "DDOrderRequestedEvent_GroupId", MaterialDispoGroupId.class);

	public static final ModelDynAttributeAccessor<I_DD_Order, String> ATTR_DDORDER_REQUESTED_EVENT_TRACE_ID = //
			new ModelDynAttributeAccessor<>(I_DD_Order.class.getName(), "DDOrderRequestedEvent_TraceId", String.class);

	public DDOrderProducer(final DDOrderLowLevelService ddOrderLowLevelService) {this.ddOrderLowLevelService = ddOrderLowLevelService;}

	public ImmutableList<I_DD_Order> createDDOrders(
			@NonNull final DDOrder ddOrder,
			@NonNull final Date dateOrdered,
			@Nullable final String traceId)
	{
		final Map<FromToWarehouse, I_DD_Order> warehouses2ddOrder = new HashMap<>();
		
		for (final DDOrderLine linePojo : ddOrder.getLines())
		{
			final I_DD_NetworkDistributionLine networkDistributionLineRecord = load(linePojo.getNetworkDistributionLineId(), I_DD_NetworkDistributionLine.class);
			final FromToWarehouse fromToWarehouseKey = FromToWarehouse.create(linePojo);

			final I_DD_Order ddOrderRecord = warehouses2ddOrder.computeIfAbsent(
					fromToWarehouseKey, 
					key -> createDDOrderRecord(key, ddOrder, dateOrdered, traceId));

			createDDOrderLine(ddOrder, linePojo, networkDistributionLineRecord, fromToWarehouseKey, ddOrderRecord);
		}

		return ImmutableList.copyOf(warehouses2ddOrder.values());
	}
	
	private I_DD_Order createDDOrderRecord(
			@NonNull final FromToWarehouse fromToWarehouse, 
			@NonNull final DDOrder ddOrder,
			@NonNull final Date dateOrdered,
			@Nullable final String ddOrderRequestedEventTrace)
	{
		final ProductPlanningId productPlanningId = ProductPlanningId.ofRepoId(ddOrder.getProductPlanningId());
		final ProductPlanning productPlanning = productPlanningsRepo.getById(productPlanningId);

		final BPartnerLocationId orgBPartnerLocationId = DDOrderUtil.retrieveOrgBPartnerLocationId(ddOrder.getOrgId());
		
		final I_DD_Order ddOrderRecord = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID.setValue(ddOrderRecord, ddOrder.getMaterialDispoGroupId());
		ATTR_DDORDER_REQUESTED_EVENT_TRACE_ID.setValue(ddOrderRecord, ddOrderRequestedEventTrace);

		ddOrderRecord.setAD_Org_ID(ddOrder.getOrgId().getRepoId());
		ddOrderRecord.setMRP_Generated(true);
		ddOrderRecord.setMRP_AllowCleanup(true);
		ddOrderRecord.setPP_Plant_ID(ddOrder.getPlantId());
		ddOrderRecord.setC_BPartner_ID(orgBPartnerLocationId != null ? orgBPartnerLocationId.getBpartnerId().getRepoId() : -1);
		ddOrderRecord.setC_BPartner_Location_ID(orgBPartnerLocationId != null ? orgBPartnerLocationId.getRepoId() : -1);
		ddOrderRecord.setAD_User_ID(UserId.toRepoId(productPlanning.getPlannerId())); // FIXME: improve performances/cache and retrive Primary BP's User
		ddOrderRecord.setSalesRep_ID(UserId.toRepoId(productPlanning.getPlannerId()));

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
		ddOrderRecord.setIsSimulated(ddOrder.isSimulated());

		if (ddOrder.isSimulated())
		{
			ddOrderRecord.setProcessed(true);
		}

		ddOrderRecord.setM_Warehouse_From_ID(fromToWarehouse.getWarehouseFromId().getRepoId());
		ddOrderRecord.setM_Warehouse_To_ID(fromToWarehouse.getWarehouseToId().getRepoId());

		ddOrderRecord.setPP_Product_Planning_ID(ProductPlanningId.toRepoId(productPlanning.getId()));

		ddOrderLowLevelService.save(ddOrderRecord);
		
		return ddOrderRecord;
	}

	@Value
	private static class FromToWarehouse
	{
		public static FromToWarehouse create(@NonNull final DDOrderLine linePojo)
		{
			final I_DD_NetworkDistributionLine networkDistributionLine = InterfaceWrapperHelper.load(linePojo.getNetworkDistributionLineId(), I_DD_NetworkDistributionLine.class);
			final WarehouseId warehouseFromId = WarehouseId.ofRepoId(networkDistributionLine.getM_WarehouseSource_ID());
			final WarehouseId warehouseToId = WarehouseId.ofRepoId(networkDistributionLine.getM_Warehouse_ID());
			
			return new FromToWarehouse(warehouseFromId, warehouseToId);
		}
		
		@NonNull
		WarehouseId warehouseFromId;

		@NonNull
		WarehouseId warehouseToId;
	}

	private void createDDOrderLine(
			@NonNull final DDOrder ddOrder,
			@NonNull final DDOrderLine linePojo,
			@NonNull final I_DD_NetworkDistributionLine networkDistributionLineRecord,
			@NonNull final DDOrderProducer.FromToWarehouse fromToWarehouse,
			@NonNull final I_DD_Order ddOrderRecord)
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

		ddOrderline.setDD_NetworkDistributionLine_ID(networkDistributionLineRecord.getDD_NetworkDistributionLine_ID());

		// get supply source warehouse and locator
		final LocatorId locatorFromId = warehouseBL.getDefaultLocatorId(fromToWarehouse.getWarehouseFromId());

		// get supply target warehouse and locator
		final LocatorId locatorToId = warehouseBL.getDefaultLocatorId(fromToWarehouse.getWarehouseToId());

		//
		// Locator From/To
		ddOrderline.setM_Locator_ID(locatorFromId.getRepoId());
		ddOrderline.setM_LocatorTo_ID(locatorToId.getRepoId());
		
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
		ddOrderline.setDD_AllowPush(networkDistributionLineRecord.isDD_AllowPush());
		ddOrderline.setIsKeepTargetPlant(networkDistributionLineRecord.isKeepTargetPlant());

		//
		// Save DD Order Line
			ddOrderLowLevelService.save(ddOrderline);
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
