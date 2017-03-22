package org.eevolution.mrp.spi.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.drp.api.IDistributionNetworkDAO;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alternative;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_DD_Order;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPSourceEvent;

import de.metas.adempiere.service.IBPartnerOrgBL;

public class DDOrderMRPSupplyProducer extends AbstractMRPSupplyProducer
{
	public static final String ERR_DRP_010_InTransitWarehouseNotFound = "DRP-010";
	public static final String ERR_DRP_060_NoSourceOfSupply = "DRP-060";

	private final IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);

	public DDOrderMRPSupplyProducer()
	{
		super();

		addSourceColumnNames(I_DD_Order.class,
				I_DD_Order.COLUMN_PP_Plant_ID,
				I_DD_Order.COLUMN_DocStatus);
		addSourceColumnNames(I_DD_OrderLine.class,
				I_DD_OrderLine.COLUMN_AD_Org_ID,
				I_DD_OrderLine.COLUMN_M_Product_ID,
				I_DD_OrderLine.COLUMN_C_UOM_ID,
				I_DD_OrderLine.COLUMN_DatePromised,
				I_DD_OrderLine.COLUMN_QtyOrdered,
				I_DD_OrderLine.COLUMN_QtyDelivered,
				I_DD_OrderLine.COLUMN_QtyInTransit,
				I_DD_OrderLine.COLUMN_M_Locator_ID,
				I_DD_OrderLine.COLUMN_M_LocatorTo_ID,
				I_DD_OrderLine.COLUMN_C_OrderLineSO_ID,
				I_DD_OrderLine.COLUMN_PP_Plant_From_ID);
		addSourceColumnNames(I_DD_OrderLine_Alternative.class,
				I_DD_OrderLine_Alternative.COLUMN_M_Product_ID,
				I_DD_OrderLine_Alternative.COLUMN_QtyOrdered,
				I_DD_OrderLine_Alternative.COLUMN_QtyDelivered,
				I_DD_OrderLine_Alternative.COLUMN_QtyInTransit);

	}

	@Override
	public Class<?> getDocumentClass()
	{
		return I_DD_Order.class;
	}

	@Override
	public boolean applies(final IMRPContext mrpContext, final IMutable<String> notAppliesReason)
	{
		if (!mrpContext.isRequireDRP())
		{
			notAppliesReason.setValue("DRP not enabled");
			return false;
		}

		// Check distribution network
		final I_PP_Product_Planning productPlanning = mrpContext.getProductPlanning();
		if (productPlanning.getDD_NetworkDistribution_ID() <= 0)
		{
			notAppliesReason.setValue("No distribution network configured in product data planning: " + productPlanning);
			return false;
		}

		// If nothing else is preventing as, consider that we can do DRP
		return true;
	}

	@Override
	public void onRecordChange(final IMRPSourceEvent event)
	{
		final Object model = event.getModel();

		if (event.isChange() && InterfaceWrapperHelper.isInstanceOf(model, I_DD_Order.class))
		{
			// Do nothing if the DD Order was just created because there is nothing we can do
			if (event.isNew())
			{
				return;
			}

			final I_DD_Order ddOrder = InterfaceWrapperHelper.create(model, I_DD_Order.class);

			//
			// Create/update MRP records
			createUpdateMRPRecords(ddOrder);
		}
		else if (event.isChange() && InterfaceWrapperHelper.isInstanceOf(model, I_DD_OrderLine.class))
		{
			final I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.create(model, I_DD_OrderLine.class);
			createUpdateMRPRecords(ddOrderLine);
		}
		else if (event.isChange() && InterfaceWrapperHelper.isInstanceOf(model, I_DD_OrderLine_Alternative.class))
		{
			final I_DD_OrderLine_Alternative ddOrderLineAlt = InterfaceWrapperHelper.create(model, I_DD_OrderLine_Alternative.class);
			createUpdateMRPRecords(ddOrderLineAlt);
		}
	}

	@Override
	protected void onRecordDeleted(final IMRPSourceEvent event)
	{
		super.onRecordDeleted(event);

		//
		// Delete MRP alternatives, if any
		final Object model = event.getModel();
		if (InterfaceWrapperHelper.isInstanceOf(model, I_DD_OrderLine_Alternative.class))
		{
			final I_DD_OrderLine_Alternative ddOrderLineAlt = InterfaceWrapperHelper.create(model, I_DD_OrderLine_Alternative.class);
			deleteMRPAlternatives(ddOrderLineAlt);
		}
	}

	@Override
	public void onDocumentChange(final Object model, final DocTimingType timing)
	{
		// nothing
	}

	private final I_PP_MRP retrieveMRPRecord(final I_DD_OrderLine ddOrderLine, final String typeMRP)
	{
		// Optimization: in case the DD_OrderLine was just created, there is no point to query because there won't be any result for sure
		if (InterfaceWrapperHelper.isJustCreated(ddOrderLine))
		{
			return null;
		}

		final I_PP_MRP mrp = mrpDAO.retrieveQueryBuilder(ddOrderLine, typeMRP, X_PP_MRP.ORDERTYPE_DistributionOrder)
				.setSkipIfMRPExcluded(false)
				.firstOnly();
		return mrp;
	}

	/**
	 * Create/Update MRP record based in Distribution Order.
	 * 
	 * Actally it is calling {@link #createUpdateMRPRecords(I_DD_OrderLine)} for each line.
	 * 
	 * @param I_DD_Order Distribution Order
	 */
	private void createUpdateMRPRecords(final I_DD_Order ddOrder)
	{
		final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);
		final List<I_DD_OrderLine> ddOrderLines = ddOrderDAO.retrieveLines(ddOrder);
		for (final I_DD_OrderLine ddOrderLine : ddOrderLines)
		{
			createUpdateMRPRecords(ddOrderLine);
		}
	}

	/**
	 * Create/Update MRP record based in Distribution Order Line
	 * 
	 * @param I_DD_OrderLine Distribution Order Line
	 */
	private void createUpdateMRPRecords(final I_DD_OrderLine ddOrderLine)
	{
		//
		// MRP Supply record (moving from InTransit to Destination Locator)
		createUpdateMRPRecord(ddOrderLine, X_PP_MRP.TYPEMRP_Supply);

		//
		// MRP Demand record (moving from Source Locator to InTransit)
		final I_PP_MRP mrpDemand = createUpdateMRPRecord(ddOrderLine, X_PP_MRP.TYPEMRP_Demand);
		notifyAffectedMRPSegment(mrpDemand); // notify that we changed this segment because it's in another warehouse then the warehouse on which we are planning now
	}

	private I_PP_MRP createUpdateMRPRecord(final I_DD_OrderLine ddOrderLine, final String typeMRP)
	{
		final I_DD_Order ddOrder = ddOrderLine.getDD_Order();
		final String docStatus = ddOrder.getDocStatus();
		final BigDecimal qtyTarget = ddOrderLine.getQtyOrdered();

		final int ppPlantId;
		BigDecimal qty;
		final I_M_Locator locator;
		final int attributeSetInstanceId;
		if (X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			// Supply goes on Destination Locator
			locator = ddOrderLine.getM_LocatorTo();

			// Qty (supply) on target warehouse
			qty = ddOrderBL.getQtyToReceive(ddOrderLine);

			// Supply plant (taken from DD Order header)
			ppPlantId = ddOrder.getPP_Plant_ID();

			attributeSetInstanceId = ddOrderLine.getM_AttributeSetInstanceTo_ID();
		}
		else if (X_PP_MRP.TYPEMRP_Demand.equals(typeMRP))
		{
			// Demand goes on Source Locator
			locator = ddOrderLine.getM_Locator();

			// Qty (demand) on source warehouse
			qty = ddOrderBL.getQtyToShip(ddOrderLine);

			// Demand plant (taken from line)
			ppPlantId = ddOrderLine.getPP_Plant_From_ID();

			attributeSetInstanceId = ddOrderLine.getM_AttributeSetInstance_ID();
		}
		else
		{
			throw new IllegalArgumentException("Unknown TypeMRP: " + typeMRP);
		}

		//
		// In case we moved more then it was required, we shall consider the MRP quantity as ZERO because there are no more movements to plan
		if (qty.signum() < 0)
		{
			qty = BigDecimal.ZERO;
		}

		//
		// Get/Create MRP Record
		I_PP_MRP mrp = retrieveMRPRecord(ddOrderLine, typeMRP);
		final boolean isNewMRPRecord = mrp == null;
		if (isNewMRPRecord)
		{
			mrp = mrpBL.createMRP(ddOrderLine);
		}

		//
		// Header
		mrp.setTypeMRP(typeMRP);
		mrp.setDescription(ddOrderLine.getDescription());

		//
		// Dimension (Org, Warehouse, Plant)
		mrp.setAD_Org_ID(locator.getAD_Org_ID());
		mrp.setM_Warehouse_ID(locator.getM_Warehouse_ID());
		mrp.setS_Resource_ID(ppPlantId);

		//
		// Document Reference
		mrp.setOrderType(X_PP_MRP.ORDERTYPE_DistributionOrder);
		mrp.setDD_Order_ID(ddOrderLine.getDD_Order_ID());
		mrp.setDD_OrderLine(ddOrderLine);
		mrp.setDocStatus(docStatus);
		mrp.setC_BPartner_ID(ddOrderLine.getC_BPartner_ID());
		mrp.setC_OrderLineSO_ID(ddOrderLine.getC_OrderLineSO_ID());

		//
		// Dates
		final Timestamp datePromised = ddOrderLine.getDatePromised();
		mrp.setDateOrdered(ddOrderLine.getDateOrdered());
		mrp.setDatePromised(datePromised);
		if (mrp.getDateFinishSchedule() == null)
		{
			// If DateFinishSchedule was not set, we assume DatePromised
			mrp.setDateFinishSchedule(datePromised);
		}
		if (mrp.getDateStartSchedule() == null)
		{
			// If DateStartSchedule was not set, we assume DateFinishSchedule
			// => leadtime = 0 because we don't have this information here
			mrp.setDateStartSchedule(mrp.getDateFinishSchedule());
		}

		//
		// Product & Qty
		mrp.setM_Product_ID(ddOrderLine.getM_Product_ID());
		mrp.setM_AttributeSetInstance_ID(attributeSetInstanceId);
		mrpBL.setQty(mrp, qtyTarget, qty, ddOrderLine.getC_UOM());

		InterfaceWrapperHelper.save(mrp);
		return mrp;
	}

	private void createUpdateMRPRecords(final I_DD_OrderLine_Alternative ddOrderLineAlt)
	{
		final I_DD_OrderLine ddOrderLine = ddOrderLineAlt.getDD_OrderLine();
		final I_PP_MRP mrpDemand = retrieveMRPRecord(ddOrderLine, X_PP_MRP.TYPEMRP_Demand);
		if (mrpDemand != null)
		{
			createUpdateMRPRecord(ddOrderLineAlt, mrpDemand);
		}

		final I_PP_MRP mrpSupply = retrieveMRPRecord(ddOrderLine, X_PP_MRP.TYPEMRP_Supply);
		if (mrpSupply != null)
		{
			createUpdateMRPRecord(ddOrderLineAlt, mrpSupply);
		}
	}

	private void createUpdateMRPRecord(final I_DD_OrderLine_Alternative ddOrderLineAlt, final I_PP_MRP ddOrderLineMRPRecord)
	{
		Check.assumeNotNull(ddOrderLineAlt, "ddOrderLineAlt not null");
		Check.assumeNotNull(ddOrderLineMRPRecord, "ddOrderLineMRPRecord not null");

		//
		// Get existing MRP Alternative
		I_PP_MRP_Alternative mrpAlternative = retrieveMRPAlternative(ddOrderLineAlt, ddOrderLineMRPRecord);

		//
		// If no MRP alternative found (or it was deleted)
		// we need to create a new one
		if (mrpAlternative == null)
		{
			mrpAlternative = mrpBL.createMRPAlternative(ddOrderLineMRPRecord);
			mrpAlternative.setPP_MRP(ddOrderLineMRPRecord);
			mrpAlternative.setDD_OrderLine_Alternative(ddOrderLineAlt);
		}

		//
		// Get MRP Quantity
		final String typeMRP = ddOrderLineMRPRecord.getTypeMRP();
		final BigDecimal qty;
		if (X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			qty = ddOrderBL.getQtyToReceive(ddOrderLineAlt);
		}
		else
		// if (X_PP_MRP.TYPEMRP_Demand.equals(typeMRP))
		{
			qty = ddOrderBL.getQtyToShip(ddOrderLineAlt);
		}

		//
		// Update MRP alternative record
		mrpAlternative.setAD_Org_ID(ddOrderLineAlt.getAD_Org_ID());
		mrpAlternative.setM_Product_ID(ddOrderLineAlt.getM_Product_ID());
		mrpAlternative.setQty(qty);
		mrpAlternative.setIsActive(true);
		InterfaceWrapperHelper.save(mrpAlternative);
	}

	@Override
	public void createSupply(final IMRPCreateSupplyRequest request)
	{
		final IMRPContext mrpContext = request.getMRPContext();
		final IMRPExecutor executor = request.getMRPExecutor();

		final Properties ctx = mrpContext.getCtx();
		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();
		final I_AD_Org org = mrpContext.getAD_Org();
		final I_S_Resource plant = mrpContext.getPlant();
		final Timestamp supplyDateFinishSchedule = TimeUtil.asTimestamp(request.getDemandDate());

		// QtyToSupply: qty for which we need to produce the supply
		final BigDecimal qtyToSupply = request.getQtyToSupply();

		// TODO vpj-cd I need to create logic for DRP-040 Shipment Due Action Notice
		// Indicates that a shipment for a Order Distribution is due.
		// Action should be taken at the source warehouse to ensure that the order is received on time.

		// TODO vpj-cd I need to create logic for DRP-050 Shipment Pas Due Action Notice
		// Indicates that a shipment for a Order Distribution is past due. You should either delay the orders created the requirement for the product
		// or expedite them when the product does arrive.

		if (productPlanningData.getDD_NetworkDistribution_ID() <= 0)
		{
			// Indicates that the Product Planning Data for this product does not specify a valid network distribution.
			executor.newMRPNote(mrpContext, ERR_DRP_060_NoSourceOfSupply)
					.collect();
			//
			return;
		}

		final I_DD_NetworkDistribution network = productPlanningData.getDD_NetworkDistribution();
		final List<I_DD_NetworkDistributionLine> networkLines = Services.get(IDistributionNetworkDAO.class)
				.retrieveNetworkLinesByTargetWarehouse(network, productPlanningData.getM_Warehouse_ID());
		if (networkLines.isEmpty())
		{
			// No network lines were found for our target warehouse
			final I_M_Warehouse warehouseTo = productPlanningData.getM_Warehouse();
			executor.newMRPNote(mrpContext, ERR_DRP_060_NoSourceOfSupply)
					.setComment("@NotFound@ @DD_NetworkDistributionLine_ID@")
					.addParameter(I_DD_NetworkDistribution.COLUMNNAME_DD_NetworkDistribution_ID, network == null ? "?" : network.getName())
					.addParameter("M_Warehouse_Dest_ID", warehouseTo == null ? "?" : warehouseTo.getName())
					.collect();
			//
			return;
		}

		int M_Shipper_ID = -1;
		I_DD_Order order = null;

		BigDecimal qtyToSupplyRemaining = qtyToSupply;
		for (final I_DD_NetworkDistributionLine networkLine : networkLines)
		{
			//
			// Check: if we created DD Orders for all qty that needed to be supplied, stop here
			if (qtyToSupplyRemaining.signum() <= 0)
			{
				break;
			}

			// get supply source warehouse and locator
			final I_M_Warehouse warehouseFrom = networkLine.getM_WarehouseSource();
			final I_M_Locator locatorFrom = Services.get(IWarehouseBL.class).getDefaultLocator(warehouseFrom);

			// get supply target warehouse and locator
			final I_M_Warehouse warehouseTo = networkLine.getM_Warehouse();
			final I_M_Locator locatorTo = Services.get(IWarehouseBL.class).getDefaultLocator(warehouseTo);

			if (locatorFrom == null || locatorTo == null)
			{
				executor.newMRPNote(mrpContext, "DRP-001")// FIXME: DRP-001 error code does not exist
						.addParameter(I_DD_NetworkDistribution.COLUMNNAME_DD_NetworkDistribution_ID, network == null ? "?" : network.getName())
						.addParameter(I_DD_NetworkDistributionLine.COLUMNNAME_M_WarehouseSource_ID, warehouseFrom.getName())
						.addParameter(I_DD_NetworkDistributionLine.COLUMNNAME_M_Warehouse_ID, warehouseTo.getName())
						.setComment("No locators found for source or target warehouse")
						.collect();
				//
				continue;
			}

			//
			// Get the warehouse in transit
			final I_M_Warehouse warehouseInTrasit = retrieveInTransitWarehouse(ctx, warehouseFrom.getAD_Org_ID());
			if (warehouseInTrasit == null)
			{
				// DRP-010: Do not exist Transit Warehouse to this Organization
				executor.newMRPNote(mrpContext, ERR_DRP_010_InTransitWarehouseNotFound)
						.addParameter(I_AD_Org.COLUMNNAME_AD_Org_ID, org.getName())
						.collect();
				//
				continue;
			}

			//
			// DRP-030: Do not exist Shipper for Create Distribution Order
			if (networkLine.getM_Shipper_ID() <= 0)
			{
				executor.newMRPNote(mrpContext, "DRP-030")
						.addParameter(I_DD_NetworkDistribution.COLUMNNAME_DD_NetworkDistribution_ID, network == null ? "?" : network.getName())
						.addParameter(I_DD_NetworkDistributionLine.COLUMNNAME_DD_NetworkDistributionLine_ID, networkLine)
						.collect();
				//
				continue;
			}

			if (M_Shipper_ID != networkLine.getM_Shipper_ID())
			{
				// Org Must be linked to BPartner
				final I_AD_Org locatorToOrg = locatorTo.getAD_Org();
				final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
				final I_C_BPartner orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(locatorToOrg);
				if (orgBPartner == null)
				{
					// DRP-020: Target Org has no BP linked to it
					executor.newMRPNote(mrpContext, "DRP-020")
							.addParameter(I_AD_Org.COLUMNNAME_AD_Org_ID, locatorToOrg.getName())
							.collect();
					//
					continue;
				}

				final I_C_BPartner_Location orgBPLocation = bpartnerOrgBL.retrieveOrgBPLocation(mrpContext.getCtx(), locatorToOrg.getAD_Org_ID(), ITrx.TRXNAME_None);

				//
				// Try found some DD_Order with Shipper , Business Partner and Doc Status = Draft
				// Consolidate the demand in a single order for each Shipper , Business Partner , DemandDateStartSchedule
				order = getDDOrderFromCache(org, plant, warehouseInTrasit, networkLine.getM_Shipper_ID(), orgBPartner.getC_BPartner_ID(), supplyDateFinishSchedule);
				if (order == null)
				{
					order = InterfaceWrapperHelper.newInstance(I_DD_Order.class, mrpContext);
					order.setMRP_Generated(true);
					order.setMRP_AllowCleanup(true);
					order.setAD_Org_ID(warehouseTo.getAD_Org_ID());
					order.setPP_Plant(plant);
					order.setC_BPartner(orgBPartner);
					order.setC_BPartner_Location(orgBPLocation);
					// order.setAD_User_ID(productPlanningData.getPlanner_ID()); // FIXME: improve performances/cache and retrive Primary BP's User
					order.setSalesRep_ID(productPlanningData.getPlanner_ID());

					final int docTypeDO_ID = getC_DocType_ID(mrpContext, X_C_DocType.DOCBASETYPE_DistributionOrder);
					order.setC_DocType_ID(docTypeDO_ID);
					order.setM_Warehouse(warehouseInTrasit);
					order.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
					order.setDocAction(X_DD_Order.DOCACTION_Complete);
					order.setDateOrdered(mrpContext.getDateAsTimestamp());
					order.setDatePromised(supplyDateFinishSchedule);
					order.setM_Shipper_ID(networkLine.getM_Shipper_ID());
					order.setIsInDispute(false);
					order.setIsInTransit(false);

					InterfaceWrapperHelper.save(order);

					executor.addGeneratedSupplyDocument(order);
					addToCache(order);
				}
				M_Shipper_ID = networkLine.getM_Shipper_ID();
			}

			//
			// Crate DD order line
			final BigDecimal qtyToMove = calculateQtyToMove(qtyToSupplyRemaining, networkLine.getPercent());
			createDD_OrderLine(mrpContext, order, networkLine, locatorFrom, locatorTo, qtyToMove, supplyDateFinishSchedule, request);

			qtyToSupplyRemaining = qtyToSupplyRemaining.subtract(qtyToMove);
		}

		//
		// Check: remaining qtyToSupply shall be ZERO
		if (qtyToSupplyRemaining.signum() != 0)
		{
			// TODO: introduce DRP-XXX notice
			throw new LiberoException("Cannot create DD Order for required Qty To Supply."
					+ "\nQtyToSupply: " + qtyToSupply
					+ "\nQtyToSupply (remaining): " + qtyToSupplyRemaining
					+ "\n@DD_NetworkDistribution_ID@: " + network
					+ "\n@DD_NetworkDistributionLine_ID@: " + networkLines
					+ "\nMRPContext: " + mrpContext);
		}
	}

	private I_DD_OrderLine createDD_OrderLine(final IMRPContext mrpContext,
			final I_DD_Order order,
			final I_DD_NetworkDistributionLine networkLine,
			final I_M_Locator locatorFrom,
			final I_M_Locator locatorTo,
			final BigDecimal qtyToMove,
			final Timestamp supplyDateFinishSchedule,
			final IMRPCreateSupplyRequest request)
	{
		final I_M_Product product = mrpContext.getM_Product();
		final int bpartnerId = request.getMRPDemandBPartnerId();
		final int orderLineSOId = request.getMRPDemandOrderLineSOId();
		
		//
		// Create DD Order Line
		final I_DD_OrderLine ddOrderline = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, order);
		ddOrderline.setAD_Org_ID(order.getAD_Org_ID());
		ddOrderline.setDD_Order(order);
		ddOrderline.setC_BPartner_ID(bpartnerId);
		ddOrderline.setC_OrderLineSO_ID(orderLineSOId);

		//
		// Locator From/To
		ddOrderline.setM_Locator(locatorFrom);
		ddOrderline.setM_LocatorTo(locatorTo);

		//
		// Product, UOM, Qty
		// NOTE: we assume qtyToMove is in "mrpContext.getC_UOM()" which shall be the Product's stocking UOM
		ddOrderline.setM_Product(product);
		ddOrderline.setC_UOM(mrpContext.getC_UOM());
		ddOrderline.setQtyEntered(qtyToMove);
		ddOrderline.setQtyOrdered(qtyToMove);
		ddOrderline.setTargetQty(qtyToMove);

		//
		// Dates
		ddOrderline.setDateOrdered(order.getDateOrdered());
		ddOrderline.setDatePromised(order.getDatePromised());

		//
		// Other flags
		ddOrderline.setIsInvoiced(false);
		ddOrderline.setDD_AllowPush(networkLine.isDD_AllowPush());
		ddOrderline.setIsKeepTargetPlant(networkLine.isKeepTargetPlant());

		//
		// Save DD Order Line
		InterfaceWrapperHelper.save(ddOrderline);

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
				createDD_OrderLine_Alternative(mrpContext, ddOrderline, mrpAlternative);
			}
		}

		//
		// Set Correct Planning Dates
		final int durationDays = calculateDurationDays(mrpContext, networkLine);
		final Timestamp supplyDateStartSchedule = TimeUtil.addDays(supplyDateFinishSchedule, 0 - durationDays);
		final List<I_PP_MRP> mrpList = mrpDAO.retrieveMRPRecords(ddOrderline);
		for (final I_PP_MRP mrp : mrpList)
		{
			mrp.setDateStartSchedule(supplyDateStartSchedule);
			mrp.setDateFinishSchedule(supplyDateFinishSchedule);
			InterfaceWrapperHelper.save(mrp);
		}

		return ddOrderline;
	}

	private void createDD_OrderLine_Alternative(IMRPContext mrpContext, I_DD_OrderLine ddOrderLine, I_PP_MRP_Alternative mrpAlternative)
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

		InterfaceWrapperHelper.save(ddOrderLineAlt);
	}

	private int calculateDurationDays(final IMRPContext mrpContext, final I_DD_NetworkDistributionLine networkLine)
	{
		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();

		//
		// Leadtime
		final int leadtimeDays = productPlanningData.getDeliveryTime_Promised().intValueExact();
		Check.assume(leadtimeDays >= 0, LiberoException.class, "leadtimeDays >= 0");

		//
		// Transfer time
		int transferTime = networkLine.getTransfertTime().intValueExact();
		if (transferTime <= 0)
		{
			transferTime = productPlanningData.getTransfertTime().intValueExact();
		}
		Check.assume(transferTime >= 0, LiberoException.class, "transferTime >= 0");

		final int durationTotalDays = leadtimeDays + transferTime;
		return durationTotalDays;
	}

	private I_DD_Order getDDOrderFromCache(final I_AD_Org org,
			final I_S_Resource plant,
			final I_M_Warehouse warehouseInTrasit,
			final int shipperId,
			final int bpartnerId,
			final Timestamp demandDateStartSchedule)
	{
		// TODO implement
		return null;
	}

	private void addToCache(final I_DD_Order order)
	{
		// TODO: implement
		// DD_Order_ID = order.getDD_Order_ID();
		// String key = network_line.getM_Shipper_ID() + "#" + C_BPartner_ID + "#" + DemandDateStartSchedule + "DR";
		// dd_order_id_cache.put(key, DD_Order_ID);
	}

	private I_M_Warehouse retrieveInTransitWarehouse(final Properties ctx, final int adOrgId)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
		return warehouseDAO.retrieveWarehouseInTransitForOrg(ctx, adOrgId);
	}

	@Override
	public void cleanup(final IMRPContext mrpContext, final IMRPExecutor executor)
	{
		// If DRP module is not activated, then skip the cleanup
		if (!mrpContext.isRequireDRP())
		{
			return;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		//
		// Delete generated distribution orders
		// (i.e. Distribution Order with Draft Status)
		final ICompositeQueryFilter<I_DD_Order> filters = queryBL.createCompositeQueryFilter(I_DD_Order.class);
		filters.addEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Drafted);

		//
		// Only those which were generated by MRP
		filters.addEqualsFilter(I_DD_Order.COLUMN_MRP_Generated, true);
		// Only those which are allowed to be deleted by MRP cleanup
		filters.addEqualsFilter(I_DD_Order.COLUMN_MRP_AllowCleanup, true);

		//
		// Only for our AD_Client_ID
		filters.addEqualsFilter(I_DD_Order.COLUMNNAME_AD_Client_ID, mrpContext.getAD_Client_ID());
		//
		// Only for our AD_Org_ID
		filters.addEqualsFilter(I_DD_Order.COLUMNNAME_AD_Org_ID, mrpContext.getAD_Org().getAD_Org_ID());
		//
		// Only those DD Orders which are from our Plant or does not have a plant at all
		filters.addInArrayOrAllFilter(I_DD_Order.COLUMNNAME_PP_Plant_ID, null, mrpContext.getPlant().getS_Resource_ID());

		//
		// Only those which have a line with Destination Warehouse same as our warehouse
		final int targetWarehouseId = mrpContext.getM_Warehouse().getM_Warehouse_ID();
		filters.addFilter(Services.get(IDDOrderDAO.class).getDDOrdersForTargetWarehouseQueryFilter(targetWarehouseId));

		//
		// If we are running in an constrained MRP Context, filter only those documents
		if (mrpContext.getEnforced_PP_MRP_Demand_ID() > 0)
		{
			final IQuery<I_PP_MRP> mrpQuery = createMRPQueryBuilderForCleanup(mrpContext, executor)
					.createQueryBuilder()
					.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Supply)
					.create();

			filters.addInSubQueryFilter(I_DD_Order.COLUMN_DD_Order_ID, I_PP_MRP.COLUMN_DD_Order_ID, mrpQuery);
		}

		deletePO(mrpContext, executor, I_DD_Order.class, filters);
	}

	/* package */final BigDecimal calculateQtyToMove(final BigDecimal qtyToMoveRequested, final BigDecimal networkLineTransferPercent)
	{
		Check.assumeNotNull(qtyToMoveRequested, "qtyToMoveRequested not null");
		Check.assumeNotNull(networkLineTransferPercent, "networkLineTransferPercent not null");

		if (networkLineTransferPercent.signum() == 0)
		{
			return BigDecimal.ZERO;
		}
		else if (networkLineTransferPercent.signum() < 0)
		{
			throw new LiberoException("NetworkLine's TransferPercent shall not be negative"
					+ "\nQtyToMove: " + qtyToMoveRequested
					+ "\nTransfer Percent: " + networkLineTransferPercent);
		}
		else if (networkLineTransferPercent.compareTo(Env.ONEHUNDRED) == 0)
		{
			return qtyToMoveRequested;
		}
		final BigDecimal networkLineTransferPercentMultiplier = networkLineTransferPercent.divide(Env.ONEHUNDRED, 4, RoundingMode.HALF_UP);

		final BigDecimal qtyToMove = qtyToMoveRequested.multiply(networkLineTransferPercentMultiplier);
		return qtyToMove;
	}

	/**
	 * Delete MRP Alternative records for given {@link I_DD_OrderLine_Alternative}.
	 * 
	 * @param ddOrderLineAlt
	 */
	private void deleteMRPAlternatives(final I_DD_OrderLine_Alternative ddOrderLineAlt)
	{
		Check.assumeNotNull(ddOrderLineAlt, "ddOrderLineAlt not null");
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_PP_MRP_Alternative> queryBuilder = queryBL.createQueryBuilder(I_PP_MRP_Alternative.class, ddOrderLineAlt)
				.addEqualsFilter(I_PP_MRP_Alternative.COLUMN_DD_OrderLine_Alternative_ID, ddOrderLineAlt.getDD_OrderLine_Alternative_ID());

		queryBuilder.create().deleteDirectly();
	}

	/**
	 * Retrieves MRP Alternative record for given {@link I_DD_OrderLine_Alternative}.
	 * 
	 * @param ddOrderLineAlt
	 * @param mrp
	 * @return MRP Alternative or <code>null</code>
	 */
	private I_PP_MRP_Alternative retrieveMRPAlternative(final I_DD_OrderLine_Alternative ddOrderLineAlt, final I_PP_MRP mrp)
	{
		// Optimization: in case the DD_OrderLine was just created, there is no point to query because there won't be any result for sure
		if (InterfaceWrapperHelper.isJustCreated(ddOrderLineAlt))
		{
			return null;
		}

		Check.assumeNotNull(ddOrderLineAlt, "ddOrderLineAlt not null");
		Check.assumeNotNull(mrp, "mrp not null");

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_PP_MRP_Alternative> queryBuilder = queryBL.createQueryBuilder(I_PP_MRP_Alternative.class, ddOrderLineAlt)
				.addEqualsFilter(I_PP_MRP_Alternative.COLUMN_DD_OrderLine_Alternative_ID, ddOrderLineAlt.getDD_OrderLine_Alternative_ID())
				.addEqualsFilter(I_PP_MRP_Alternative.COLUMN_PP_MRP_ID, mrp.getPP_MRP_ID());

		return queryBuilder.create().firstOnly(I_PP_MRP_Alternative.class);
	}

	/**
	 * If this DD Order's MRP demand record was fully allocated from QOH then complete forward DD Orders
	 * 
	 * @task http://dewiki908/mediawiki/index.php/07961_Handelsware_DD_Order_automatisieren_%28101259925191%29
	 */
	@Override
	public void onQtyOnHandReservation(final IMRPContext mrpContext,
			final IMRPExecutor mrpExecutor,
			final IMRPDemandToSupplyAllocation mrpDemandToSupplyAllocation)
	{
		final I_PP_MRP mrpDemand = mrpDemandToSupplyAllocation.getMRPDemand();
		final int ddOrderId = mrpDemand.getDD_Order_ID();
		if (ddOrderId <= 0)
		{
			return;
		}

		if (mrpBL.isReleased(mrpDemand))
		{
			return;
		}

		if (!mrpDemandToSupplyAllocation.isMRPDemandFullyAllocated())
		{
			return;
		}

		//
		// Restrictions
		final int ppPlantId = mrpDemand.getS_Resource_ID();
		if (ppPlantId <= 0)
		{
			// shall not happen
			return;
		}

		final DocumentsToCompleteAfterMRPExecution scheduler = DocumentsToCompleteAfterMRPExecution.getCreate(mrpExecutor);
		scheduler.enqueueDDOrderForMRPDemand(mrpDemand);
	}
}
