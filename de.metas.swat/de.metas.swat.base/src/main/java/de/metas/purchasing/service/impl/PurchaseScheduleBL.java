package de.metas.purchasing.service.impl;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.MRelation;
import org.adempiere.model.RelationTypeZoomProvider;
import org.adempiere.model.RelationTypeZoomProvidersFactory;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_PO;
import org.compiere.model.I_M_Replenish;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPO;
import org.compiere.model.MStorage;
import org.compiere.model.Query;
import org.compiere.model.X_M_Replenish;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.order.IOrderPA;
import de.metas.process.JavaProcess;
import de.metas.product.IProductPA;
import de.metas.purchasing.model.I_M_PurchaseSchedule;
import de.metas.purchasing.model.MMPurchaseSchedule;
import de.metas.purchasing.service.IPurchaseScheduleBL;

public final class PurchaseScheduleBL implements IPurchaseScheduleBL
{

	private static final Logger logger = LogManager.getLogger(PurchaseScheduleBL.class);

	/**
	 * 
	 * @param qtyToOrder
	 *            the qty that we would have to order anyways (because of reservedQty, i.e. open sales orders), even
	 *            without the replenish rule.
	 * @param productId
	 * @param warehouseId
	 * @param trxName
	 * @return
	 */
	private BigDecimal applyReplenishRule(
			final BigDecimal qtyToOrder,
			final I_M_PurchaseSchedule purchaseSchedule,
			final String trxName)
	{
		final int productId = purchaseSchedule.getM_Product_ID();
		final int warehouseId = purchaseSchedule.getM_Warehouse_ID();

		final IProductPA productPA = Services.get(IProductPA.class);
		final I_M_Replenish replenish = productPA.retrieveReplenish(productId, warehouseId, trxName);

		if (replenish == null)
		{
			return qtyToOrder;
		}

		if (!X_M_Replenish.REPLENISHTYPE_ReorderBelowMinimumLevel.equals(replenish.getReplenishType()))
		{
			logger.warn("Don't know how to handle replenish type '" + replenish.getReplenishType() + "'");
			return qtyToOrder;
		}

		final BigDecimal min = replenish.getLevel_Min();

		// qtyToOrder is negative if there is more on hand or ordered than
		// demanded, so qtyBase would be positive in that case
		final BigDecimal qtyBase = qtyToOrder.negate();

		if (qtyBase.compareTo(min) >= 0)
		{
			// we are not below the replenish minimum
			return qtyToOrder;
		}

		final BigDecimal max = replenish.getLevel_Max();
		return max.subtract(qtyBase);
	}

	/**
	 * 
	 * compute requirements according to completed requisitions.
	 * 
	 * @param productId
	 * @param warehouseId
	 * @param newReq
	 * @param trxName
	 * @return
	 */
	@SuppressWarnings("unused")
	private BigDecimal computeForRequisitions(
			final int productId,
			final int warehouseId,
			final I_M_Requisition newReq,
			final String trxName)
	{

		BigDecimal qtyDemanded = BigDecimal.ZERO;

		final IProductPA productPA = Services.get(IProductPA.class);
		for (final I_M_Requisition req : productPA.retrieveRequisitions(DocAction.STATUS_Completed, trxName))
		{
			if (warehouseId != req.getM_Warehouse_ID())
			{
				continue;
			}
			for (final I_M_RequisitionLine reqLine : productPA.retrieveRequisitionlines(req.getDocumentNo(), trxName))
			{
				if (reqLine.getM_Product_ID() != productId)
				{
					continue;
				}
				qtyDemanded = qtyDemanded.add(reqLine.getQty());
			}
		}
		if (newReq != null)
		{
			for (final I_M_RequisitionLine reqLine : productPA.retrieveRequisitionlines(newReq.getDocumentNo(), trxName))
			{
				if (reqLine.getM_Product_ID() != productId)
				{
					continue;
				}
				qtyDemanded = qtyDemanded.add(reqLine.getQty());
			}
		}
		return qtyDemanded;
	}

	@Override
	public List<String> createPOs(final Properties ctx, final JavaProcess processLog, final String trxName)
	{

		final Map<ArrayKey, MOrder> key2PurchaseOrder = new HashMap<ArrayKey, MOrder>();
		final Map<String, MOrder> docNo2Order = new HashMap<String, MOrder>();

		final List<MMPurchaseSchedule> schedstoInclude = MMPurchaseSchedule.mkQuery(ctx, trxName).setIsIncludeInPO(true).list();

		for (final MMPurchaseSchedule purchaseSchedule : schedstoInclude)
		{
			//
			// 1. Decide if a new order needs to be created for 'purchaseSchedule' or if it can be handled in an
			// existing order.

			// Generate a grouping key, that is based on the properties of 'purchaseSchedule'.
			final ArrayKey key = mkPurchaseScheduleKey(purchaseSchedule);

			MOrder purchaseOrder = key2PurchaseOrder.get(key);

			final boolean existingOrder = purchaseOrder != null || purchaseSchedule.getC_OrderPO_ID() > 0;

			if (purchaseOrder == null)
			{
				final int existingOrderId = purchaseSchedule.getC_OrderPO_ID();
				logger.info(purchaseSchedule + " has C_OrderPO_ID=" + existingOrderId);

				if (existingOrderId == 0)
				{
					purchaseOrder = mkPurchaseOrder(purchaseSchedule, processLog);
					if (purchaseOrder == null)
					{
						continue;
					}
					purchaseSchedule.setC_OrderPO_ID(purchaseOrder.getC_Order_ID());
				}
				else
				{
					// load the order
					purchaseOrder = new MOrder(Env.getCtx(), existingOrderId, trxName);

					// assert that it really can be used
					final ArrayKey purchaseOrderKey = mkExistingOrderKey(purchaseOrder);
					if (!purchaseOrderKey.equals(key))
					{
						logger.warn("Preset order " + purchaseOrder + " can't be updated with " + purchaseSchedule);
						purchaseOrder = mkPurchaseOrder(purchaseSchedule, processLog);
						if (purchaseOrder == null)
						{
							continue;
						}
					}
					else
					{
						log("@Updated@: " + purchaseOrder.getC_DocTypeTarget().getName() + " " + purchaseOrder.getDocumentNo(), processLog);
					}
				}

				key2PurchaseOrder.put(key, purchaseOrder);

				final String docNo = purchaseOrder.getDocumentNo();
				docNo2Order.put(docNo, purchaseOrder);
			}

			MOrderLine purchaseOrderLinePO = null;
			if (existingOrder && !purchaseSchedule.isIndividualPOSchedule())
			{
				// See if there is an existing order line that we can "augment" using the qty of 'purchaseSchedule'.
				for (final MOrderLine existingLine : purchaseOrder.getLines(true, null))
				{
					if (existingLine.getM_Product_ID() == purchaseSchedule.getM_Product_ID()
							&& existingLine.getM_AttributeSetInstance_ID() == purchaseSchedule.getM_AttributeSetInstance_ID())
					{
						purchaseOrderLinePO = existingLine;
						logger.info(purchaseSchedule + " uses existing " + purchaseOrderLinePO);
						break;
					}
				}
			}
			if (purchaseOrderLinePO == null)
			{
				purchaseOrderLinePO = new MOrderLine(purchaseOrder);

				final MProduct product = (MProduct)purchaseSchedule.getM_Product();
				purchaseOrderLinePO.setProduct(product);
				purchaseOrderLinePO.setM_AttributeSetInstance_ID(purchaseSchedule.getM_AttributeSetInstance_ID());
			}

			final I_C_OrderLine purchaseOrderLine = InterfaceWrapperHelper.create(purchaseOrderLinePO, I_C_OrderLine.class);

			purchaseOrderLine.setIsIndividualPOSchedule(purchaseSchedule.isIndividualPOSchedule());

			// set the new order qty in the purchase order line
			final BigDecimal orderQty = purchaseOrderLine.getQtyOrdered().add(purchaseSchedule.getQty());

			purchaseOrderLine.setQtyEntered(orderQty);
			purchaseOrderLine.setQtyOrdered(orderQty);

			if (purchaseSchedule.getPricePO().signum() > 0)
			{
				purchaseOrderLine.setPriceActual(purchaseSchedule.getPricePO());
				purchaseOrderLine.setPriceEntered(purchaseSchedule.getPricePO());
				// 07090 not touching this code to add PriceUOM; it's legacy and won't be used in this form anyways
			}

			if (isDropship(ctx, purchaseSchedule, trxName))
			{
				purchaseOrderLine.setC_BPartner_Location_ID(purchaseSchedule.getDropShip_Location_ID());
				purchaseOrderLine.setC_BPartner_ID(purchaseSchedule.getDropShip_BPartner_ID());
				purchaseOrderLine.setAD_User_ID(purchaseSchedule.getDropShip_User_ID());
			}

			purchaseOrderLinePO.saveEx();

			final BigDecimal qtyLeft = purchaseSchedule.getQtyToOrder().subtract(purchaseSchedule.getQty());

			purchaseSchedule.setQtyToOrder(qtyLeft);
			purchaseSchedule.setQty(qtyLeft);
			purchaseSchedule.setIncludeInPurchase(false);
			purchaseSchedule.saveEx();

			// purchaseSchedule.addRelationToPOLine(purchaseOrderLine);
			final List<I_C_OrderLine> coveredSols = removeObsoleteSOLineRelations(ctx, purchaseSchedule, purchaseOrderLine.getQtyOrdered(), trxName);
			for (final I_C_OrderLine sol : coveredSols)
			{
				purchaseSchedule.addRelationSOLineToPOLine(sol, purchaseOrderLine);
			}
		}

		final ArrayList<String> result = new ArrayList<String>();

		boolean first = true;
		for (final String docNo : docNo2Order.keySet())
		{
			final MOrder order = docNo2Order.get(docNo);

			if (!order.processIt(DocAction.ACTION_Prepare))
			{
				throw new AdempiereException("Unable to prepare " + order + "; ProcessMsg=" + ((DocAction)order).getProcessMsg());
			}
			order.saveEx();

			if (first)
			{
				first = false;
			}
			else
			{
				result.add(", ");
			}
			result.add(docNo);
		}
		return result;
	}

	/**
	 * Iterates the given purchaseSchedule's related sales order lines and
	 * <ul>
	 * <li>removes the relation if the sales order line's QtyReserved is fully covered by the given qtyOrdered</li>
	 * <li>adds the sales order line to the result list, if it is partially covered</li>
	 * </ul>
	 * 
	 * @param ctx
	 * @param purchaseSchedule
	 * @param qtyOrdered
	 * @param trxName
	 * @return
	 */
	private List<I_C_OrderLine> removeObsoleteSOLineRelations(final Properties ctx, final MMPurchaseSchedule purchaseSchedule, final BigDecimal qtyOrdered, final String trxName)
	{
		if (qtyOrdered.signum() <= 0)
		{
			// nothing to do
			return Collections.emptyList();
		}
		// find out which sales order lines have now been dealt with and
		// * remove the relation between 'purchaseSchedule' and those lines
		// * add a relation between 'purchaseOrderLine' and those lines
		BigDecimal poLineQtyLeft = qtyOrdered;

		final List<I_C_OrderLine> removedSOLines = new ArrayList<I_C_OrderLine>();

		for (final org.compiere.model.I_C_OrderLine salesOrderLinePO : purchaseSchedule.retrieveSOls())
		{
			final I_C_OrderLine saleOrderLine = InterfaceWrapperHelper.create(salesOrderLinePO, I_C_OrderLine.class);

			if (poLineQtyLeft.compareTo(salesOrderLinePO.getQtyOrdered()) >= 0)
			{
				// salesOrderLine's QtyOrdered is completely covered by 'purchaseOrderLine'

				// delete relation between salesOrderLine and purchaseSchedule
				MMPurchaseSchedule.updateRelationsFromSOLines(ctx, saleOrderLine, purchaseSchedule, null, trxName);
			}
			else
			{
				// salesOrderLine's QtyOrdered is partially covered by 'purchaseOrderLine'
			}

			removedSOLines.add(saleOrderLine);

			poLineQtyLeft = poLineQtyLeft.subtract(salesOrderLinePO.getQtyOrdered());
			if (poLineQtyLeft.signum() <= 0)
			{
				// further salesOrderLines that are still related to 'purchaseSchedule' (if any) are not covered by
				// 'purchaseOrderLine'
				break;
			}
		}

		return removedSOLines;
	}

	private MOrder mkPurchaseOrder(final MMPurchaseSchedule purchaseSchedule, final JavaProcess processLog)
	{
		// only need to check for warehouse and bPartner if we have no order yet
		if (purchaseSchedule.getC_BPartner_ID() == 0)
		{
			final String msg = "Skipping " + purchaseSchedule + " because it has no bPartner";
			log(msg, processLog);
			logger.warn(msg);
			return null;
		}

		if (purchaseSchedule.getM_Warehouse_ID() == 0)
		{
			final String msg = "Skipping " + purchaseSchedule + " because it has no warehouse";
			log(msg, processLog);
			logger.warn(msg);
			return null;
		}
		final Properties ctx = purchaseSchedule.getCtx();
		final String trxName = purchaseSchedule.get_TrxName();

		final MOrder purchaseOrder = new MOrder(ctx, 0, trxName);
		purchaseOrder.setIsSOTrx(false);
		purchaseOrder.setM_Warehouse_ID(purchaseSchedule.getM_Warehouse_ID());
		
		// Sales Rep
		// NOTE: we shall not set the SalesRep from context if is not set.
		// This is not a mandatory field, so leave it like it is.
		// purchaseOrder.setSalesRep_ID(Env.getAD_User_ID(Env.getCtx()));
		
		purchaseOrder.setDocAction(DocAction.ACTION_Prepare);
		purchaseOrder.setC_DocTypeTarget_ID();

		final MBPartner bPartner = (MBPartner)purchaseSchedule.getC_BPartner();
		purchaseOrder.setBPartner(bPartner);

		if (isDropship(ctx, purchaseSchedule, trxName))
		{
			purchaseOrder.setIsDropShip(true);
			purchaseOrder.setDropShip_BPartner_ID(purchaseSchedule.getDropShip_BPartner_ID());
			purchaseOrder.setDropShip_Location_ID(purchaseSchedule.getDropShip_Location_ID());
			purchaseOrder.setDropShip_User_ID(purchaseSchedule.getDropShip_User_ID());
		}
		purchaseOrder.saveEx();

		log("@Created@: " + purchaseOrder.getC_DocTypeTarget().getName() + " " + purchaseOrder.getDocumentNo(), processLog);

		assert mkPurchaseScheduleKey(purchaseSchedule).equals( mkExistingOrderKey(purchaseOrder)) : "Keys don't match: purchaseSchedule=" + purchaseSchedule + "; purchaseOrder=" + purchaseOrder;

		return purchaseOrder;
	}

	private static final void log(final String msg, final JavaProcess processLog)
	{
		if (processLog != null)
		{
			processLog.addLog(msg);
		}
	}

	private boolean isDropship(final Properties ctx, final I_M_PurchaseSchedule ps, final String trxName)
	{
		return isDropship(ctx, ps.getAD_Org_ID(), ps.getM_Warehouse_ID(), trxName);
	}

	private boolean isDropship(final Properties ctx, final int orgId, final int warehouseId, final String trxName)
	{
		return MOrgInfo.get(ctx, orgId, trxName).getDropShip_Warehouse_ID() == warehouseId;
	}

	private ArrayKey mkPurchaseScheduleKey(final MMPurchaseSchedule purchaseSchedule)
	{
		final List<Object> keyParams = new ArrayList<Object>();

		keyParams.add(purchaseSchedule.getC_BPartner_ID());
		keyParams.add(purchaseSchedule.getM_Warehouse_ID());
		keyParams.add(purchaseSchedule.getDropShip_Location_ID());
		keyParams.add(purchaseSchedule.getDropShip_BPartner_ID());
		keyParams.add(purchaseSchedule.getDropShip_User_ID());

		if (purchaseSchedule.isIndividualPOSchedule())
		{
			// Include C_Order_ID of the purchase schedule's ol into our key.
			// This makes sure that schedules with IsIndividualPOSchedule='Y' won't end up in the same purchase order,
			// if coming from different sales orders.
			keyParams.add(purchaseSchedule.getC_OrderLine_Individual().getC_Order_ID());
		}
		else
		{
			keyParams.add(0);
		}

		return Util.mkKey(keyParams.toArray());
	}

	/**
	 * Helper method that creates a key for an existing order, similar to
	 * {@link #mkPurchaseScheduleKey(MMPurchaseSchedule)}. Used to enforce consistency when a preselected order is used
	 * 
	 * @param purchaseOrder
	 * @return
	 */
	private ArrayKey mkExistingOrderKey(final MOrder purchaseOrder)
	{
		final List<Object> keyParams = new ArrayList<Object>();

		keyParams.add(purchaseOrder.getC_BPartner_ID());
		keyParams.add(purchaseOrder.getM_Warehouse_ID());
		keyParams.add(purchaseOrder.getDropShip_Location_ID());
		keyParams.add(purchaseOrder.getDropShip_BPartner_ID());
		keyParams.add(purchaseOrder.getDropShip_User_ID());

		keyParams.add(0);

		return Util.mkKey(keyParams.toArray());
	}

	@Override
	public Collection<MMPurchaseSchedule> retrieveOrCreateForReq(
			final Properties ctx,
			final I_M_Requisition requisition,
			final String trxName)
	{
		final Collection<MMPurchaseSchedule> purchaseScheds = new ArrayList<MMPurchaseSchedule>();

		final IProductPA productPA = Services.get(IProductPA.class);

		for (final I_M_RequisitionLine reqLine : productPA.retrieveRequisitionlines(requisition.getDocumentNo(), trxName))
		{
			final MMPurchaseSchedule currentSched = retrieveOrCreateForReqLine(ctx, requisition, reqLine, trxName);
			purchaseScheds.add(currentSched);
		}
		return purchaseScheds;
	}

	/**
	 * Checks if there is an appropriate existing purchase schedule for the given requisition line. In not, a new one is
	 * created (and saved). In any case, a relation between the given <code>reqLine</code> and purchase schedules is
	 * added.
	 * 
	 * @param ctx
	 * @param requisition
	 * @param reqLine
	 * @param trxName
	 * @return
	 */
	private MMPurchaseSchedule retrieveOrCreateForReqLine(
			final Properties ctx,
			final I_M_Requisition requisition,
			final I_M_RequisitionLine reqLine,
			final String trxName)
	{
		final List<MMPurchaseSchedule> scheds = MMPurchaseSchedule.mkQuery(ctx, trxName)
				.setProductId(reqLine.getM_Product_ID())
				.setWarehouseId(requisition.getM_Warehouse_ID())
				.setIsIndividualPOSchedule(false)
				.setIsPricePONull(true)
				.list();

		final MMPurchaseSchedule schedForReqLine;

		if (scheds.isEmpty())
		{
			final MMPurchaseSchedule newSchedForReqLine = new MMPurchaseSchedule(ctx, 0, trxName);
			newSchedForReqLine.setM_Product_ID(reqLine.getM_Product_ID());
			newSchedForReqLine.setM_Warehouse_ID(requisition.getM_Warehouse_ID());
			newSchedForReqLine.setM_AttributeSetInstance_ID(reqLine.getM_AttributeSetInstance_ID());
			newSchedForReqLine.setQty(reqLine.getQty());

			setVendorIfNotAmbigous(ctx, newSchedForReqLine, trxName);
			newSchedForReqLine.saveEx();

			logger.info("Created new sched " + newSchedForReqLine + " for " + reqLine);
			schedForReqLine = newSchedForReqLine;
		}
		else
		{
			assert scheds.size() == 0 : "Expecting only one purchase sched for " + reqLine + "; scheds=" + scheds.toString();

			logger.info("Loaded existing sched " + scheds.get(0) + " for " + reqLine);
			schedForReqLine = scheds.get(0);
		}

		schedForReqLine.addRelationFromReqLine(reqLine);

		return schedForReqLine;
	}

	@Override
	public Collection<MMPurchaseSchedule> retrieveOrCreateForSO(
			final Properties ctx,
			final I_C_Order order,
			final String trxName)
	{
		assert order.isSOTrx() : order;

		if (MOrder.DocSubType_Proposal.equals(order.getC_DocType().getDocSubType()))
		{
			// a proposal is a non-binding offer, so there is nothing to do for the given order
			return Collections.emptyList();
		}

		final Collection<MMPurchaseSchedule> purchaseScheds = new ArrayList<MMPurchaseSchedule>();

		final IOrderPA orderPA = Services.get(IOrderPA.class);

		for (final I_C_OrderLine ol : orderPA.retrieveOrderLines(order, I_C_OrderLine.class))
		{
			final I_M_Product product = ol.getM_Product();
			if (product == null || !product.isPurchased())
			{
				logger.debug("Skipping " + ol + ", because product '" + (product == null ? "<null>" : product) + "' is not purchased");
				continue;
			}

			final MMPurchaseSchedule currentSchedForOl = MMPurchaseSchedule.retrieveForSOLine(ctx, ol, trxName);

			final MMPurchaseSchedule newSchedForOl;
			if (ol.isIndividualPOSchedule())
			{
				// 'ol' needs its own purchase schedule.
				if (currentSchedForOl == null)
				{
					// 'ol' doesn't have a sched, so create one.
					newSchedForOl = MMPurchaseSchedule.createForSalesOL(ctx, ol, trxName);
					setVendorIfNotAmbigous(ctx, newSchedForOl, trxName);
				}
				else
				{
					if (!currentSchedForOl.isIndividualPOSchedule())
					{
						// 'ol' already has 'currentSchedForOl', but 'ol' its own purchase schedule,
						// and 'currentSchedForOl' could aggregate multiple ols.
						// So, once again, create a new sched.
						newSchedForOl = MMPurchaseSchedule.createForSalesOL(ctx, ol, trxName);
						setVendorIfNotAmbigous(ctx, newSchedForOl, trxName);
					}
					else
					{
						// Both 'ol' and 'currentSchedForOl' are fit for "IndividualPOSchedule"
						// That means, that 'currentSchedForOl' can't be related to another order line than 'ol',
						// so we can savely set newSchedForOl = currentSchedForOl
						newSchedForOl = currentSchedForOl;
					}
				}
			}
			else if (
			// 'ol' has no sched right now, or
			currentSchedForOl == null

			// 'currentSchedForOl' is individual, or
					|| currentSchedForOl.isIndividualPOSchedule()

					// 'ol' has a different product, warehouse or pricePO, or
					|| currentSchedForOl.getM_Product_ID() != ol.getM_Product_ID()
					|| currentSchedForOl.getM_AttributeSetInstance_ID() != ol.getM_AttributeSetInstance_ID()
					|| currentSchedForOl.getM_Warehouse_ID() != ol.getM_Warehouse_ID()

					// 'currentsched' is dropship and 'ol' has a different location or contact
					// (note: if we got this far, 'ol' must be dropship, too)
					|| (isDropship(ctx, currentSchedForOl, trxName)
							&& (currentSchedForOl.getDropShip_Location_ID() != ol.getC_BPartner_Location_ID()
									|| currentSchedForOl.getDropShip_User_ID() != ol.getAD_User_ID())
							))
			{

				// 'ol' has a different product, warehouse or pricePO and therefore can't be related to
				// 'currentSchedForOl' anymore.

				// see if there is an existing schedule that matches 'ol'
				final List<MMPurchaseSchedule> matchingScheds;
				if (isDropship(ctx, ol.getAD_Org_ID(), ol.getM_Warehouse_ID(), trxName))
				{
					matchingScheds = MMPurchaseSchedule.mkQuery(ctx, trxName)
							.setAsi(ol.getM_AttributeSetInstance_ID())
							.setProductId(ol.getM_Product_ID())
							.setWarehouseId(ol.getM_Warehouse_ID())
							.setIsIndividualPOSchedule(false)
							.setDropshipPartner(ol.getC_BPartner_ID())
							.setDropshipLocation(ol.getC_BPartner_Location_ID())
							.setDropshipUser(ol.getAD_User_ID())
							.list();
				}
				else
				{
					matchingScheds = MMPurchaseSchedule.mkQuery(ctx, trxName)
								.setAsi(ol.getM_AttributeSetInstance_ID())
								.setProductId(ol.getM_Product_ID())
								.setWarehouseId(ol.getM_Warehouse_ID())
								.setIsIndividualPOSchedule(false)
								.list();
				}
				if (matchingScheds.isEmpty())
				{
					newSchedForOl = MMPurchaseSchedule.createForSalesOL(ctx, ol, trxName);
					setVendorIfNotAmbigous(ctx, newSchedForOl, trxName);
				}
				else
				{
					newSchedForOl = matchingScheds.get(0);
				}
			}
			else
			{
				// 'ol' was related to 'currentSchedForOl' and this doesn't need to change
				newSchedForOl = currentSchedForOl;
			}

			MMPurchaseSchedule.updateRelationsFromSOLines(ctx, ol, currentSchedForOl, newSchedForOl, trxName);
			purchaseScheds.add(newSchedForOl);
			if (newSchedForOl != currentSchedForOl && currentSchedForOl != null)
			{
				// update values of 'currentSchedForOl', as ol have been "moved" to the 'newSchedForOl'
				updateStorageData(ctx, currentSchedForOl, trxName);
				updateQtyToOrder(currentSchedForOl);
				currentSchedForOl.saveEx();
			}
		}
		return purchaseScheds;
	}

	private void setVendorIfNotAmbigous(final Properties ctx, final I_M_PurchaseSchedule sched, final String trxName)
	{
		final String wc =
				I_M_Product_PO.COLUMNNAME_M_Product_ID + "=? AND COALESCE(" + I_M_Product_PO.COLUMNNAME_C_BPartner_ID + ", 0) > 0";

		final List<MProductPO> productPOs =
				new Query(ctx, I_M_Product_PO.Table_Name, wc, trxName)
						.setParameters(sched.getM_Product_ID())
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.list();

		if (productPOs.size() != 1)
		{
			// nothing to do;
			return;
		}

		sched.setC_BPartner_ID(productPOs.get(0).getC_BPartner_ID());
	}

	private void updateQtyOnHand(
			final Properties ctx,
			final I_M_PurchaseSchedule ps,
			final String trxName)
	{
		BigDecimal qtyOnHand = BigDecimal.ZERO;

		if (ps.isIndividualPOSchedule() || isDropship(ctx, ps, trxName))
		{
			ps.setQtyOnHand(qtyOnHand);
			return;
		}

		final MStorage[] allStorages =
				MStorage.getWarehouse(ctx, ps.getM_Warehouse_ID(), ps.getM_Product_ID(), ps.getM_AttributeSetInstance_ID(), null, true, false, 0, trxName);

		for (final I_M_Storage currentStorage : allStorages)
		{
			qtyOnHand = qtyOnHand.add(currentStorage.getQtyOnHand());
		}
		ps.setQtyOnHand(qtyOnHand);
	}

	private void updateQtyOrderedAndQtyReserved(
			final Properties ctx,
			final I_M_PurchaseSchedule sched,
			final String trxName)
	{
		final RelationTypeZoomProvider relType = RelationTypeZoomProvidersFactory.instance.getZoomProviderBySourceTableNameAndInternalName(I_C_OrderLine.Table_Name, MMPurchaseSchedule.RELTYPE_SO_LINE_PO_LINE_INT_NAME);

		BigDecimal qtyReserved = BigDecimal.ZERO;
		BigDecimal qtyOrdered = BigDecimal.ZERO;

		for (final MOrderLine sol : MMPurchaseSchedule.retrieveSOls(ctx, sched, trxName))
		{
			// sol = sales order line
			assert sol.getM_Product_ID() == sched.getM_Product_ID() : "Expecting " + sol + " and " + sched + " to have the same M_Product_ID";
			assert sol.getM_AttributeSetInstance_ID() == sched.getM_AttributeSetInstance_ID() : "Expecting " + sol + " and " + sched + " to have the same M_AttributeSetInstance_ID";

			qtyReserved = qtyReserved.add(sol.getQtyReserved());

			// retrieve the purchase order lines that have already been created for 'sol'
			final List<I_C_OrderLine> pols = MRelation.retrieveDestinations(ctx, relType, sol, I_C_OrderLine.class, trxName);
			for (final I_C_OrderLine pol : pols)
			{
				qtyOrdered = qtyOrdered.add(pol.getQtyReserved());
			}
		}

		sched.setQtyReserved(qtyReserved);
		sched.setQtyOrdered(qtyOrdered);
	}

	/**
	 * Note: <code>purchaseSchedule</code> is not saved by this method.
	 * 
	 * @param purchaseSchedule
	 */
	@Override
	public void updateQtyToOrder(final MMPurchaseSchedule purchaseSchedule)
	{
		final Properties ctx = purchaseSchedule.getCtx();
		final String trxName = purchaseSchedule.get_TrxName();

		if (purchaseSchedule.isIndividualPOSchedule() || isDropship(ctx, purchaseSchedule, trxName))
		{
			// this is a special purchaseSchedule, so we want exactly the reserved qty (for this sched!) to be ordered.
			final BigDecimal qtyToOrder = purchaseSchedule.getQtyReserved().subtract(purchaseSchedule.getQtyOrdered());
			if (qtyToOrder.signum() >= 0)
			{
				purchaseSchedule.setQtyToOrder(qtyToOrder);
				purchaseSchedule.setQty(qtyToOrder);
			}
			else
			{
				purchaseSchedule.setQtyToOrder(BigDecimal.ZERO);
				purchaseSchedule.setQty(BigDecimal.ZERO);
			}
		}
		else
		{
			// 'qtyOnHand' is the qty that we still have in the warehouse
			final BigDecimal qtyOnHand = purchaseSchedule.getQtyOnHand();

			// From qtyAlreadySold (i.e. what our customers ordered from us), we subtract our 'qtyOrdered' and
			// 'qtyOnHand'.
			// Note: if there is more on hand or ordered than demanded, then 'qtyToOrderBeforeReplenish' is negative
			final BigDecimal overallQtyToOrderBeforeReplenish = purchaseSchedule.getQtyReserved().subtract(purchaseSchedule.getQtyOrdered()).subtract(qtyOnHand);

			BigDecimal overallQtyToOrder = applyReplenishRule(overallQtyToOrderBeforeReplenish, purchaseSchedule, trxName);
			if (overallQtyToOrder.signum() <= 0)
			{
				overallQtyToOrder = BigDecimal.ZERO;
			}

			purchaseSchedule.setQtyToOrder(overallQtyToOrder);
			purchaseSchedule.setQty(overallQtyToOrder);
		}

		removeObsoleteSOLineRelations(ctx, purchaseSchedule, purchaseSchedule.getQtyOrdered(), trxName);
	}

	@Override
	public void updateQtyToOrder(
			final Properties ctx,
			final int productId,
			final int warehouseId,
			final String trxName)
	{
		for (final MMPurchaseSchedule purchaseSchedule : MMPurchaseSchedule.retrieveFor(ctx, productId, warehouseId, trxName))
		{
			updateQtyToOrder(purchaseSchedule);
			purchaseSchedule.saveEx();
		}
	}

	/**
	 * Updates QtyOrdered, QtyReserved and QtyOnHand of the given purchaseSchedule.
	 */

	@Override
	public void updateStorageData(
			final Properties ctx,
			final I_M_PurchaseSchedule purchaseSchedule,
			final String trxName)
	{
		// update qty onhand from storage
		updateQtyOnHand(ctx, purchaseSchedule, trxName);

		// update qty ordered from ols (find ols using relation)
		updateQtyOrderedAndQtyReserved(ctx, purchaseSchedule, trxName);
	}

	@Override
	public void updateStorageData(
			final Properties ctx,
			final int productId,
			final int warehouseId,
			final String trxName)
	{
		//
		// update the qtyReserved, qtyOrdered, qtyOnHand of all related
		// purchaseSchedules by adding up the respective values of all storages
		// with the same product and warehouse.
		for (final MMPurchaseSchedule purchaseSchedule : MMPurchaseSchedule.retrieveFor(ctx, productId, warehouseId, trxName))
		{
			updateStorageData(ctx, purchaseSchedule, trxName);
		}
	}
}
