/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import de.metas.adempiere.form.IClientUI;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.document.location.DocumentLocation;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.location.LocationId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.LegacyUOMConversionUtils;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.SpringContextHolder;
import org.compiere.util.DisplayType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/**
 * Shipment/Receipt Callouts
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com www.e-evolution.com [ 1867464 ]
 * http://sourceforge
 * .net/tracker/index.php?func=detail&aid=1867464&group_id
 * =176962&atid=879332
 * @author kh http://dewiki908/mediawiki/index.php/Fehlerliste_Integrationstest#B062
 * @version $Id: CalloutInOut.java,v 1.7 2006/07/30 00:51:05 jjanke Exp $
 */
// metas: synched with rev. 10203
public class CalloutInOut extends CalloutEngine
{

	public static final String MSG_SERIALNO_QTY_ONE = "CalloutInOut.QtySerNoMustBeOne";

	private static final String CTXNAME_M_Product_ID = "M_Product_ID";
	private static final String CTXNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
	private static final String CTXNAME_M_Locator_ID = "M_Locator_ID";
	private static final String CTXNAME_UOMConversion = "UOMConversion";
	//
	private static final String CTXNAME_C_BPartner_ID = "C_BPartner_ID";
	private static final String CTXNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * C_Order - Order Defaults.
	 */
	public String order(final ICalloutField calloutField)
	{
		final I_M_InOut inout = calloutField.getModel(I_M_InOut.class);

		final I_C_Order order = inout.getC_Order();
		if (order == null || order.getC_Order_ID() <= 0)
		{
			return NO_ERROR;
		}

		// No Callout Active to fire dependent values
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		// Get Details
		inout.setDateOrdered(order.getDateOrdered());
		inout.setPOReference(order.getPOReference());
		inout.setAD_Org_ID(order.getAD_Org_ID());
		inout.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		inout.setC_Activity_ID(order.getC_Activity_ID());
		inout.setC_Campaign_ID(order.getC_Campaign_ID());
		inout.setC_Project_ID(order.getC_Project_ID());
		inout.setUser1_ID(order.getUser1_ID());
		inout.setUser2_ID(order.getUser2_ID());

		// Warehouse (05251 begin: we need to use the advisor)
		final WarehouseId warehouseId = Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(order);
		Check.assumeNotNull(warehouseId, "IWarehouseAdvisor finds a ware house for {}", order);
		inout.setM_Warehouse_ID(warehouseId.getRepoId());

		//
		inout.setDeliveryRule(order.getDeliveryRule());
		inout.setSalesRep_ID(order.getSalesRep_ID());
		inout.setDeliveryViaRule(order.getDeliveryViaRule());
		inout.setM_Shipper_ID(order.getM_Shipper_ID());
		inout.setFreightCostRule(order.getFreightCostRule());
		inout.setFreightAmt(order.getFreightAmt());

		InOutDocumentLocationAdapterFactory.locationAdapter(inout).setFrom(order);

		return NO_ERROR;
	} // order

	/**
	 * M_RMA - RMA Defaults.
	 */
	public String rma(final ICalloutField calloutField)
	{
		final I_M_InOut inout = calloutField.getModel(I_M_InOut.class);
		final I_M_RMA rma = inout.getM_RMA();
		if (rma == null)
		{
			return NO_ERROR;
		}
		// No Callout Active to fire dependent values
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		// Get Details
		final I_M_InOut originalReceipt = rma.getInOut();
		if (originalReceipt != null)
		{
			inout.setDateOrdered(originalReceipt.getDateOrdered());
			inout.setPOReference(originalReceipt.getPOReference());
			inout.setAD_Org_ID(originalReceipt.getAD_Org_ID());
			inout.setAD_OrgTrx_ID(originalReceipt.getAD_OrgTrx_ID());
			inout.setC_Activity_ID(originalReceipt.getC_Activity_ID());
			inout.setC_Campaign_ID(originalReceipt.getC_Campaign_ID());
			inout.setC_Project_ID(originalReceipt.getC_Project_ID());
			inout.setUser1_ID(originalReceipt.getUser1_ID());
			inout.setUser2_ID(originalReceipt.getUser2_ID());
			inout.setM_Warehouse_ID(originalReceipt.getM_Warehouse_ID());
			//
			inout.setDeliveryRule(originalReceipt.getDeliveryRule());
			inout.setDeliveryViaRule(originalReceipt.getDeliveryViaRule());
			inout.setM_Shipper_ID(originalReceipt.getM_Shipper_ID());
			inout.setFreightCostRule(originalReceipt.getFreightCostRule());
			inout.setFreightAmt(originalReceipt.getFreightAmt());

			InOutDocumentLocationAdapterFactory.locationAdapter(inout).setFrom(originalReceipt);
		}

		return NO_ERROR;
	} // rma

	/**
	 * InOut - DocType. - sets MovementType - gets DocNo
	 */
	public String docType(final ICalloutField calloutField)
	{
		final I_M_InOut inout = calloutField.getModel(I_M_InOut.class);
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(loadOutOfTrx(inout.getC_DocType_ID(), I_C_DocType.class))
				.setOldDocumentNo(inout.getDocumentNo())
				.setDocumentModel(inout)
				.buildOrNull();
		if (documentNoInfo == null)
		{
			return NO_ERROR;
		}

		// Set Movement Type
		final String DocBaseType = documentNoInfo.getDocBaseType();
		final boolean isSOTrx = documentNoInfo.isSOTrx(); // BF [2708789] Read IsSOTrx from C_DocType
		// solve 1648131 bug vpj-cd e-evolution
		if (X_C_DocType.DOCBASETYPE_MaterialDelivery.equals(DocBaseType))
		{
			if (isSOTrx)
			{
				inout.setMovementType(X_M_InOut.MOVEMENTTYPE_CustomerShipment);
			}
			else
			{
				inout.setMovementType(X_M_InOut.MOVEMENTTYPE_VendorReturns);
			}
		}
		else if (X_C_DocType.DOCBASETYPE_MaterialReceipt.equals(DocBaseType))
		{
			if (isSOTrx)
			{
				inout.setMovementType(X_M_InOut.MOVEMENTTYPE_CustomerReturns);
			}
			else
			{
				inout.setMovementType(X_M_InOut.MOVEMENTTYPE_VendorReceipts);
			}
		}

		inout.setIsSOTrx(isSOTrx);

		// DocumentNo
		if (documentNoInfo.isDocNoControlled())
		{
			inout.setDocumentNo(documentNoInfo.getDocumentNo());
		}

		return NO_ERROR;
	} // docType

	/**
	 * M_InOut - Defaults for BPartner. - Location - Contact
	 */
	public String bpartner(final ICalloutField calloutField)
	{
		final I_M_InOut inout = calloutField.getModel(I_M_InOut.class);
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(inout.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return NO_ERROR;
		}

		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final I_C_BPartner bpartner = bpartnerBL.getById(bpartnerId);
		if (bpartner == null)
		{
			return NO_ERROR;
		}

		final boolean isSOTrx = inout.isSOTrx();

		//
		// BPartner Location (i.e. ShipTo)
		final I_C_BPartner_Location shipToLocation = suggestShipToLocation(calloutField, bpartner);

		//
		// BPartner Contact
		BPartnerContactId bpContactId = InOutDocumentLocationAdapterFactory.locationAdapter(inout).getBPartnerContactId().orElse(null);
		if (!isSOTrx)
		{
			I_AD_User contact = null;
			if (shipToLocation != null)
			{
				contact = bpartnerBL.retrieveUserForLoc(shipToLocation);
			}
			if (contact == null)
			{
				contact = bpartnerBL.retrieveShipContact(bpartner);
			}
			if (contact != null)
			{
				bpContactId = BPartnerContactId.ofRepoId(contact.getC_BPartner_ID(), contact.getAD_User_ID());
			}
		}

		InOutDocumentLocationAdapterFactory.locationAdapter(inout)
				.setFrom(DocumentLocation.builder()
								 .bpartnerId(bpartnerId)
								 .bpartnerLocationId(shipToLocation != null
															 ? BPartnerLocationId.ofRepoId(shipToLocation.getC_BPartner_ID(), shipToLocation.getC_BPartner_Location_ID())
															 : null)
								 .locationId(shipToLocation != null
													 ? LocationId.ofRepoId(shipToLocation.getC_Location_ID())
													 : null)
								 .bpartnerAddress(shipToLocation != null ? shipToLocation.getAddress() : null)
								 .contactId(bpContactId)
								 .build());

		//
		// Check SO credit available
		if (isSOTrx)
		{
			final BPartnerStats bpartnerStats = Services.get(IBPartnerStatsDAO.class).getCreateBPartnerStats(bpartner);
			final BigDecimal soCreditUsed = bpartnerStats.getSOCreditUsed();
			if (soCreditUsed.signum() < 0)
			{
				calloutField.fireDataStatusEEvent("CreditLimitOver", DisplayType.getNumberFormat(DisplayType.Amount).format(soCreditUsed), false);
			}
		}
		return NO_ERROR;
	} // bpartner

	public static I_C_BPartner_Location suggestShipToLocation(final ICalloutField calloutField, final I_C_BPartner bpartner)
	{
		final int infoWindow_bpartnerId = calloutField.getTabInfoContextAsInt(CTXNAME_C_BPartner_ID);
		int infoWindow_bpartnerLocationId = -1;
		if (bpartner.getC_BPartner_ID() == infoWindow_bpartnerId)
		{
			infoWindow_bpartnerLocationId = calloutField.getTabInfoContextAsInt(CTXNAME_C_BPartner_Location_ID);
		}

		final List<I_C_BPartner_Location> shipToLocations = Services.get(IBPartnerDAO.class).retrieveBPartnerShipToLocations(bpartner);
		if (shipToLocations.isEmpty())
		{
			return null;
		}

		if (infoWindow_bpartnerLocationId > 0)
		{
			for (final I_C_BPartner_Location shipToLocation : shipToLocations)
			{
				if (shipToLocation.getC_BPartner_Location_ID() == infoWindow_bpartnerLocationId)
				{
					return shipToLocation;
				}
			}
		}

		return shipToLocations.get(0);
	}

	/**
	 * M_Warehouse. Set Organization and Default Locator
	 */
	public String warehouse(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}
		final I_M_InOut inout = calloutField.getModel(I_M_InOut.class);
		final I_M_Warehouse warehouse = load(inout.getM_Warehouse_ID(), I_M_Warehouse.class);
		if (warehouse == null)
		{
			return NO_ERROR;
		}

		inout.setAD_Org_ID(warehouse.getAD_Org_ID());

		final I_M_Locator locator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);
		calloutField.putContext(CTXNAME_M_Locator_ID, locator == null ? -1 : locator.getM_Locator_ID());

		return NO_ERROR;
	} // warehouse

	/**
	 * M_InOutLine.C_OrderLine_ID
	 */
	public String orderLine(final ICalloutField calloutField)
	{
		final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);
		final I_M_InOutLine inoutLine = calloutField.getModel(I_M_InOutLine.class);

		final int C_OrderLine_ID = inoutLine.getC_OrderLine_ID();
		if (C_OrderLine_ID <= 0)
		{
			return NO_ERROR;
		}

		// Get Details
		final I_C_OrderLine ol = inoutLine.getC_OrderLine();
		if (ol != null && ol.getC_OrderLine_ID() > 0)
		{
			if (ol.getC_Charge_ID() > 0 && ol.getM_Product_ID() <= 0)
			{
				inoutLine.setC_Charge_ID(ol.getC_Charge_ID());
			}
			else
			{
				inoutLine.setM_Product_ID(ol.getM_Product_ID());
				inoutLine.setM_AttributeSetInstance_ID(ol.getM_AttributeSetInstance_ID());
			}
			//
			inoutLine.setC_UOM_ID(ol.getC_UOM_ID());

			final BigDecimal MovementQty = ol.getQtyOrdered().subtract(ol.getQtyDelivered());
			inoutLine.setMovementQty(MovementQty);

			BigDecimal QtyEntered = MovementQty;
			if (ol.getQtyEntered().compareTo(ol.getQtyOrdered()) != 0)
			{
				QtyEntered = QtyEntered.multiply(ol.getQtyEntered()).divide(ol.getQtyOrdered(), 12, BigDecimal.ROUND_HALF_UP);
			}
			inoutLine.setQtyEntered(QtyEntered);

			//
			// Dimensions
			inoutLine.setC_ProjectPhase_ID(ol.getC_ProjectPhase_ID());
			inoutLine.setC_ProjectTask_ID(ol.getC_ProjectTask_ID());
			inoutLine.setAD_OrgTrx_ID(ol.getAD_OrgTrx_ID());

			final Dimension orderLineDimensions = dimensionService.getFromRecord(ol);
			dimensionService.updateRecord(inoutLine, orderLineDimensions);
		}

		return NO_ERROR;
	} // orderLine

	/**
	 * M_InOutLine.M_RMALine_ID
	 */
	public String rmaLine(final ICalloutField calloutField)
	{
		final I_M_InOutLine inoutLine = calloutField.getModel(I_M_InOutLine.class);

		final int M_RMALine_id = inoutLine.getM_RMALine_ID();
		if (M_RMALine_id <= 0)
		{
			return NO_ERROR;
		}

		// Get Details
		final I_M_RMALine rl = inoutLine.getM_RMALine();
		if (rl != null && rl.getM_RMALine_ID() > 0)
		{
			final I_M_InOutLine originalInOutLine = rl.getM_InOutLine();

			final int productId = originalInOutLine == null ? -1 : originalInOutLine.getM_Product_ID();
			final int asiId = originalInOutLine == null ? -1 : originalInOutLine.getM_AttributeSetInstance_ID();
			if (rl.getC_Charge_ID() > 0 && productId <= 0)
			{
				inoutLine.setC_Charge_ID(rl.getC_Charge_ID());
			}
			else
			{
				inoutLine.setM_Product_ID(productId);
				inoutLine.setM_AttributeSetInstance_ID(asiId);
			}

			//
			final int uomId = originalInOutLine == null ? -1 : originalInOutLine.getC_UOM_ID();
			inoutLine.setC_UOM_ID(uomId);

			final BigDecimal movementQty = rl.getQty().subtract(rl.getQtyDelivered());
			inoutLine.setMovementQty(movementQty);

			final BigDecimal QtyEntered = movementQty;
			inoutLine.setQtyEntered(QtyEntered);

			//
			// Dimensions
			inoutLine.setC_Activity_ID(originalInOutLine == null ? -1 : originalInOutLine.getC_Activity_ID());
			inoutLine.setC_Campaign_ID(originalInOutLine == null ? -1 : originalInOutLine.getC_Campaign_ID());
			inoutLine.setC_Project_ID(originalInOutLine == null ? -1 : originalInOutLine.getC_Project_ID());
			inoutLine.setC_ProjectPhase_ID(originalInOutLine == null ? -1 : originalInOutLine.getC_ProjectPhase_ID());
			inoutLine.setC_ProjectTask_ID(originalInOutLine == null ? -1 : originalInOutLine.getC_ProjectTask_ID());
			inoutLine.setAD_OrgTrx_ID(originalInOutLine == null ? -1 : originalInOutLine.getAD_OrgTrx_ID());
			inoutLine.setUser1_ID(originalInOutLine == null ? -1 : originalInOutLine.getUser1_ID());
			inoutLine.setUser2_ID(originalInOutLine == null ? -1 : originalInOutLine.getUser2_ID());
		}
		return NO_ERROR;
	} // rmaLine

	/**
	 * M_InOutLine - Default UOM/Locator for Product.
	 */
	public String product(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final I_M_InOutLine inoutLine = calloutField.getModel(I_M_InOutLine.class);

		final int M_Product_ID = inoutLine.getM_Product_ID();
		if (M_Product_ID <= 0)
		{
			return NO_ERROR;
		}

		//
		// Set ASI.
		// Fetch selected locator, if any.
		final int selected_productId = calloutField.getTabInfoContextAsInt(CTXNAME_M_Product_ID);
		final int selected_asiId = calloutField.getTabInfoContextAsInt(CTXNAME_M_AttributeSetInstance_ID);
		int selected_locatorId = -1;
		if (selected_productId == M_Product_ID && selected_asiId > 0)
		{
			inoutLine.setM_AttributeSetInstance_ID(selected_asiId);
			selected_locatorId = calloutField.getTabInfoContextAsInt(CTXNAME_M_Locator_ID);
		}
		else
		{
			inoutLine.setM_AttributeSetInstance(null);
		}

		//
		// Set UOM/Qty
		final UomId productUOM = Services.get(IProductBL.class).getStockUOMId(inoutLine.getM_Product_ID());
		inoutLine.setC_UOM_ID(productUOM.getRepoId());

		//
		final I_M_InOut inout = inoutLine.getM_InOut();
		if (inout.isSOTrx())
		{
			return NO_ERROR;
		}

		//
		// Locator
		updateLocator(inoutLine, selected_locatorId);

		//
		final BigDecimal QtyEntered = inoutLine.getQtyEntered();
		inoutLine.setMovementQty(QtyEntered);

		return NO_ERROR;
	} // product

	private static final void updateLocator(final I_M_InOutLine inoutLine, final int suggestedLocatorId)
	{
		final I_M_InOut inout = inoutLine.getM_InOut();
		final int allowedWarehouseRecordId = inout.getM_Warehouse_ID();

		//
		// Validate suggested locator and set it if valid
		int locatorIdToSet = suggestedLocatorId;
		if (locatorIdToSet > 0)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(inoutLine);
			final I_M_Locator locatorToSet = InterfaceWrapperHelper.create(ctx, locatorIdToSet, I_M_Locator.class, ITrx.TRXNAME_None);
			if (locatorToSet == null || allowedWarehouseRecordId != locatorToSet.getM_Warehouse_ID())
			{
				locatorIdToSet = -1;
			}
		}
		// Set given locator, if valid
		if (locatorIdToSet > 0)
		{
			inoutLine.setM_Locator_ID(locatorIdToSet);
			return;
		}

		//
		// Validate current locator and if OK, do nothing
		{
			final int locatorRepoId = inoutLine.getM_Locator_ID();
			if (locatorRepoId > 0)
			{
				final I_M_Locator currentLocator = loadOutOfTrx(locatorRepoId, I_M_Locator.class);
				if (allowedWarehouseRecordId == currentLocator.getM_Warehouse_ID())
				{
					return;
				}
			}
		}

		//
		// Set product's default locator, if valid
		final I_M_Product product = loadOutOfTrx(inoutLine.getM_Product_ID(), I_M_Product.class);

		int productLocatorRecordId = product.getM_Locator_ID();

		if (productLocatorRecordId > 0)
		{
			final WarehouseId allowedWarehouseId = WarehouseId.ofRepoId(allowedWarehouseRecordId);

			final LocatorId locatorId = LocatorId.ofRepoIdOrNull(allowedWarehouseId, productLocatorRecordId);

			if (locatorId != null)
			{
				inoutLine.setM_Locator_ID(productLocatorRecordId);
				return;
			}
		}

		//
		// Set warehouse's default locator
		final WarehouseId allowedWarehouseId = WarehouseId.ofRepoIdOrNull(inout.getM_Warehouse_ID());
		if (allowedWarehouseId != null)  // shall never be null
		{
			final LocatorId defaultLocatorId = Services.get(IWarehouseBL.class).getDefaultLocatorId(allowedWarehouseId);
			inoutLine.setM_Locator_ID(defaultLocatorId.getRepoId());
		}

		//
		// Fallback: don't set the locator because it's not valid
	}

	/**
	 * InOut Line - Quantity. - called from C_UOM_ID, QtyEntered, MovementQty -
	 */
	public String qty(final ICalloutField calloutField)
	{
		if (isCalloutActive() || calloutField.getValue() == null)
		{
			return NO_ERROR;
		}

		final I_M_InOutLine inoutLine = calloutField.getModel(I_M_InOutLine.class);

		final int M_Product_ID = inoutLine.getM_Product_ID();

		// No Product
		if (M_Product_ID <= 0)
		{
			final BigDecimal QtyEntered = inoutLine.getQtyEntered();
			inoutLine.setMovementQty(QtyEntered);
		}
		// UOM Changed - convert from Entered -> Product
		else if (I_M_InOutLine.COLUMNNAME_C_UOM_ID.equals(calloutField.getColumnName()) && inoutLine.getC_UOM_ID() > 0)
		{
			final int C_UOM_To_ID = inoutLine.getC_UOM_ID();
			BigDecimal QtyEntered = inoutLine.getQtyEntered();
			final BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(calloutField.getCtx(), C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM={}; QtyEntered={} -> {}", C_UOM_To_ID, QtyEntered, QtyEntered1);
				QtyEntered = QtyEntered1;
				inoutLine.setQtyEntered(QtyEntered);
			}

			BigDecimal MovementQty = LegacyUOMConversionUtils.convertToProductUOM(calloutField.getCtx(), M_Product_ID, C_UOM_To_ID, QtyEntered);
			if (MovementQty == null)
			{
				MovementQty = QtyEntered;
			}

			final boolean conversion = QtyEntered.compareTo(MovementQty) != 0;
			log.debug("UOM={}, QtyEntered={} -> conversion={} MovementQty={}", C_UOM_To_ID, QtyEntered, conversion, MovementQty);
			calloutField.putContext(CTXNAME_UOMConversion, conversion);
			inoutLine.setMovementQty(MovementQty);
		}
		// No UOM defined
		else if (inoutLine.getC_UOM_ID() <= 0)
		{
			final BigDecimal QtyEntered = inoutLine.getQtyEntered();
			inoutLine.setMovementQty(QtyEntered);
		}
		// QtyEntered changed - calculate MovementQty
		else if (I_M_InOutLine.COLUMNNAME_QtyEntered.equals(calloutField.getColumnName()))
		{
			final int C_UOM_To_ID = inoutLine.getC_UOM_ID();
			BigDecimal QtyEntered = inoutLine.getQtyEntered();

			// metas: make sure that MovementQty must be 1 for a product with a serial number.
			final I_M_AttributeSetInstance attributeSetInstance = inoutLine.getM_AttributeSetInstance();
			if (attributeSetInstance != null && attributeSetInstance.getM_AttributeSetInstance_ID() > 0)
			{
				final String serNo = attributeSetInstance.getSerNo();
				if (!Check.isEmpty(serNo, true)
						&& QtyEntered.compareTo(BigDecimal.ONE) > 0)
				{
					Services.get(IClientUI.class).info(calloutField.getWindowNo(), MSG_SERIALNO_QTY_ONE);
					QtyEntered = BigDecimal.ONE;
					inoutLine.setQtyEntered(QtyEntered);
				}
			}
			// metas end

			final BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(calloutField.getCtx(), C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.debug("Corrected QtyEntered Scale UOM={}; QtyEntered={} -> {}", C_UOM_To_ID, QtyEntered, QtyEntered1);
				QtyEntered = QtyEntered1;
				inoutLine.setQtyEntered(QtyEntered);
			}

			BigDecimal MovementQty = LegacyUOMConversionUtils.convertToProductUOM(calloutField.getCtx(), M_Product_ID, C_UOM_To_ID, QtyEntered);
			if (MovementQty == null)
			{
				MovementQty = QtyEntered;
			}

			final boolean conversion = QtyEntered.compareTo(MovementQty) != 0;
			log.debug("UOM={}, QtyEntered={} -> conversion={} MovementQty={}", C_UOM_To_ID, QtyEntered, conversion, MovementQty);
			calloutField.putContext(CTXNAME_UOMConversion, conversion);
			inoutLine.setMovementQty(MovementQty);
		}
		// MovementQty changed - calculate QtyEntered (should not happen)
		else if (I_M_InOutLine.COLUMNNAME_MovementQty.equals(calloutField.getColumnName()))
		{
			final int C_UOM_To_ID = inoutLine.getC_UOM_ID();
			BigDecimal MovementQty = inoutLine.getMovementQty();
			BigDecimal QtyEntered;

			final ProductId productId = ProductId.ofRepoIdOrNull(inoutLine.getM_Product_ID());
			if (productId != null && C_UOM_To_ID > 0)
			{
				final I_M_Product product = loadOutOfTrx(productId, I_M_Product.class);
				final UOMPrecision precision = Services.get(IProductBL.class).getUOMPrecision(product);
				final BigDecimal MovementQty1 = precision.round(MovementQty);
				if (MovementQty.compareTo(MovementQty1) != 0)
				{
					log.debug("Corrected MovementQty {}->{}", MovementQty, MovementQty1);
					MovementQty = MovementQty1;
					inoutLine.setMovementQty(MovementQty);
				}

				QtyEntered = LegacyUOMConversionUtils.convertFromProductUOM(calloutField.getCtx(), M_Product_ID, C_UOM_To_ID, MovementQty);
				if (QtyEntered == null)
				{
					QtyEntered = MovementQty;
				}
			}
			else
			{
				QtyEntered = MovementQty;
			}

			final boolean conversion = MovementQty.compareTo(QtyEntered) != 0;
			log.debug("UOM={}, MovementQty={} -> conversion={}, QtyEntered={}", C_UOM_To_ID, MovementQty, conversion, QtyEntered);
			calloutField.putContext(CTXNAME_UOMConversion, conversion);
			inoutLine.setQtyEntered(QtyEntered);
		}

		//
		return NO_ERROR;
	} // qty

	/**
	 * M_InOutLine - ASI.
	 *
	 * @return error message or ""
	 */
	public String asi(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final I_M_InOutLine inoutLine = calloutField.getModel(I_M_InOutLine.class);
		final int asiId = inoutLine.getM_AttributeSetInstance_ID();
		if (asiId <= 0)
		{
			return NO_ERROR;
		}

		//
		final I_M_InOut inout = inoutLine.getM_InOut();
		final int M_Product_ID = inoutLine.getM_Product_ID();
		final int M_Warehouse_ID = inout.getM_Warehouse_ID();
		final int M_Locator_ID = inoutLine.getM_Locator_ID(); // TODO: Env.getContextAsInt(ctx, WindowNo, CTXNAME_M_Locator_ID);
		log.debug("M_Product_ID={}, M_ASI_ID={} - M_Warehouse_ID={}, M_Locator_ID={}", M_Product_ID, asiId, M_Warehouse_ID, M_Locator_ID);

		//
		// Check Selection
		final int selected_asiId = calloutField.getTabInfoContextAsInt(CTXNAME_M_AttributeSetInstance_ID);
		if (selected_asiId == asiId)
		{
			final int selectedM_Locator_ID = calloutField.getTabInfoContextAsInt(CTXNAME_M_Locator_ID);
			if (selectedM_Locator_ID > 0)
			{
				log.debug("Selected M_Locator_ID={}", selectedM_Locator_ID);
				inoutLine.setM_Locator_ID(selectedM_Locator_ID);
			}
		}

		//
		// metas: make sure that MovementQty must be 1 for a product with a serial number.
		final I_M_AttributeSetInstance attributeSetInstance = inoutLine.getM_AttributeSetInstance();
		if (attributeSetInstance != null)
		{
			final BigDecimal qtyEntered = inoutLine.getQtyEntered();
			final String serNo = attributeSetInstance.getSerNo();
			if (!Check.isEmpty(serNo, true)
					&& (qtyEntered == null || qtyEntered.compareTo(BigDecimal.ONE) != 0))
			{
				final int windowNo = calloutField.getWindowNo();
				Services.get(IClientUI.class).info(windowNo, MSG_SERIALNO_QTY_ONE);
				inoutLine.setQtyEntered(BigDecimal.ONE);
			}
		}
		// metas end

		return NO_ERROR;
	} // asi

} // CalloutInOut
