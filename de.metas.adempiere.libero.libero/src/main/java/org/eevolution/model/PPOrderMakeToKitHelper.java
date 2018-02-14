package org.eevolution.model;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MClient;
import org.compiere.model.MStorage;
import org.compiere.model.X_C_Order;
import org.compiere.util.KeyNamePair;
import org.eevolution.api.ComponentIssueCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IReceiptCostCollectorCandidate;

import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.product.IProductBL;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Deprecated
public class PPOrderMakeToKitHelper
{
	/**
	 * Complete/Process the manufacturing order which is dealing with a Make-To-Kit BOM.
	 *
	 * NOTE: in this case we need a special was of completing it. After this method, the document can be automatically marked as closed.
	 */
	public static void complete(final MPPOrder ppOrder)
	{
		final Timestamp today = SystemTime.asTimestamp();

		// metas : cg task: 06004 - refactored a bit : start
		final Map<Integer, PPOrderBOMLineModel> issue = new HashMap<>();

		for (final I_PP_Order_BOMLine line : Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(ppOrder))
		{
			KeyNamePair id = null;

			if (X_PP_Order_BOMLine.ISSUEMETHOD_Backflush.equals(line.getIssueMethod()))
			{
				id = new KeyNamePair(line.getPP_Order_BOMLine_ID(), "Y"); // 0 - MPPOrderBOMLine ID
			}
			else
			{
				id = new KeyNamePair(line.getPP_Order_BOMLine_ID(), "N"); // 0 - MPPOrderBOMLine ID
			}

			// final boolean isCritical = line.isCritical(); //1 - Critical not used
			final I_M_Product product = line.getM_Product();
			final String value = product.getValue(); // 2 - Value
			final int M_Product_ID = product.getM_Product_ID(); // 3 - Product id
			final BigDecimal qtyToDeliver = line.getQtyRequiered(); // 4 - QtyToDeliver
			final BigDecimal qtyScrapComponent = BigDecimal.ZERO; // 5 - QtyScrapComponent

			final PPOrderBOMLineModel bomLineModel = new PPOrderBOMLineModel(id, line.isCritical(), value, M_Product_ID, qtyToDeliver, qtyScrapComponent);
			//
			final int i = issue.size();
			issue.put(i, bomLineModel);
		}

		// metas : cg task: 06004 - refactor a bit : end

		boolean forceIssue = false;
		final I_C_OrderLine oline = ppOrder.getC_OrderLine();
		final String orderDeliveryRule = oline.getC_Order().getDeliveryRule();
		if (X_C_Order.DELIVERYRULE_CompleteLine.equals(orderDeliveryRule) ||
				X_C_Order.DELIVERYRULE_CompleteOrder.equals(orderDeliveryRule))
		{
			final boolean isCompleteQtyDeliver = isQtyAvailable(ppOrder, issue, today);
			if (!isCompleteQtyDeliver)
			{
				throw new LiberoException("@NoQtyAvailable@");
			}
		}
		else if (X_C_Order.DELIVERYRULE_Availability.equals(orderDeliveryRule) ||
				X_C_Order.DELIVERYRULE_AfterReceipt.equals(orderDeliveryRule) ||
				X_C_Order.DELIVERYRULE_Manual.equals(orderDeliveryRule))
		{
			throw new LiberoException("@ActionNotSupported@");
		}
		else if (X_C_Order.DELIVERYRULE_Force.equals(orderDeliveryRule))
		{
			forceIssue = true;
		}

		for (int i = 0; i < issue.size(); i++)
		{
			final PPOrderBOMLineModel model = issue.get(i);

			int M_AttributeSetInstance_ID = 0;
			final KeyNamePair key = model.getKnp();
			// final String value = model.getValue(); not used
			final int M_Product_ID = model.getM_Product_ID();
			// final I_M_Product product = InterfaceWrapperHelper.create(getCtx(), I_M_Product.Table_Name, M_Product_ID, I_M_Product.class, get_TrxName()); // not used
			final BigDecimal qtyToDeliver = model.getQtyToDeliver();
			BigDecimal qtyScrapComponent = model.getQtyScrapComponent();

			int PP_Order_BOMLine_ID = key.getKey();
			if (PP_Order_BOMLine_ID > 0)
			{
				final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.create(ppOrder.getCtx(), PP_Order_BOMLine_ID, I_PP_Order_BOMLine.class, ppOrder.get_TrxName());
				// Validate if AttributeSet generate instance
				M_AttributeSetInstance_ID = orderBOMLine.getM_AttributeSetInstance_ID();
			}

			MStorage[] storages = getStorages(ppOrder.getCtx(),
					M_Product_ID,
					ppOrder.getM_Warehouse_ID(),
					M_AttributeSetInstance_ID,
					today, ppOrder.get_TrxName());

			createIssue(
					ppOrder,
					key.getKey(),
					today, qtyToDeliver,
					qtyScrapComponent,
					BigDecimal.ZERO,
					storages, forceIssue);
		}

		final BigDecimal qtyToReceive = Services.get(IPPOrderBL.class).getQtyOpen(ppOrder);

		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		final IReceiptCostCollectorCandidate candidate = ppCostCollectorBL.createReceiptCostCollectorCandidate()
				.PP_Order(ppOrder)
				.movementDate(today)
				.qtyToReceive(qtyToReceive)
				.qtyScrap(ppOrder.getQtyScrap())
				.qtyReject(ppOrder.getQtyReject())
				.M_Locator_ID(ppOrder.getM_Locator_ID())
				.M_Product(ppOrder.getM_Product())
				.C_UOM(ppOrder.getC_UOM())
				.M_AttributeSetInstance_ID(ppOrder.getM_AttributeSetInstance_ID())
				.build();

		ppCostCollectorBL.createReceipt(candidate);

		ppOrder.setQtyDelivered(qtyToReceive);
	}

	/**
	 * get if Component is Available
	 *
	 * @param MPPOrdrt Manufacturing order
	 * @param issues
	 * @param minGuaranteeDate Guarantee Date
	 * @return true when the qty available is enough
	 */
	private static boolean isQtyAvailable(MPPOrder order, final Map<Integer, PPOrderBOMLineModel> issue, Timestamp minGuaranteeDate)
	{
		boolean isCompleteQtyDeliver = false;
		for (int i = 0; i < issue.size(); i++)
		{
			final PPOrderBOMLineModel bomLineModel = issue.get(i);
			final KeyNamePair key = bomLineModel.getKnp();
			boolean isSelected = key.getName().equals("Y");
			if (key == null || !isSelected)
			{
				continue;
			}

			final String value = bomLineModel.getValue();
			final int M_Product_ID = bomLineModel.getM_Product_ID();
			final BigDecimal qtyToDeliver = bomLineModel.getQtyToDeliver();
			final BigDecimal qtyScrapComponent = bomLineModel.getQtyScrapComponent();

			if (M_Product_ID > 0 && Services.get(IProductBL.class).isStocked(M_Product_ID))
			{
				int M_AttributeSetInstance_ID = 0;
				if (value == null && isSelected)
				{
					M_AttributeSetInstance_ID = key.getKey();
				}
				else if (value != null && isSelected)
				{
					int PP_Order_BOMLine_ID = key.getKey();
					if (PP_Order_BOMLine_ID > 0)
					{
						final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.create(order.getCtx(), PP_Order_BOMLine_ID, I_PP_Order_BOMLine.class, order.get_TrxName());
						// Validate if AttributeSet generate instance
						M_AttributeSetInstance_ID = orderBOMLine.getM_AttributeSetInstance_ID();
					}
				}

				MStorage[] storages = getStorages(order.getCtx(),
						M_Product_ID,
						order.getM_Warehouse_ID(),
						M_AttributeSetInstance_ID,
						minGuaranteeDate, order.get_TrxName());

				if (M_AttributeSetInstance_ID == 0)
				{
					BigDecimal toIssue = qtyToDeliver.add(qtyScrapComponent);
					for (MStorage storage : storages)
					{
						// TODO Selection of ASI
						if (storage.getQtyOnHand().signum() == 0)
							continue;
						BigDecimal issueActual = toIssue.min(storage.getQtyOnHand());
						toIssue = toIssue.subtract(issueActual);
						if (toIssue.signum() <= 0)
							break;
					}
				}
				else
				{
					BigDecimal qtydelivered = qtyToDeliver;
					qtydelivered.setScale(4, BigDecimal.ROUND_HALF_UP);
					qtydelivered = BigDecimal.ZERO;
				}

				BigDecimal onHand = BigDecimal.ZERO;
				for (MStorage storage : storages)
				{
					onHand = onHand.add(storage.getQtyOnHand());
				}

				isCompleteQtyDeliver = onHand.compareTo(qtyToDeliver.add(qtyScrapComponent)) >= 0;
				if (!isCompleteQtyDeliver)
					break;

			}
		} // for each line

		return isCompleteQtyDeliver;
	}

	private static MStorage[] getStorages(
			Properties ctx,
			int M_Product_ID,
			int M_Warehouse_ID,
			int M_ASI_ID,
			Timestamp minGuaranteeDate, String trxName)
	{
		final I_M_Product product = loadOutOfTrx(M_Product_ID, I_M_Product.class);
		if (product != null && Services.get(IProductBL.class).isStocked(product))
		{
			String MMPolicy = Services.get(IProductBL.class).getMMPolicy(product);

			// Validate if AttributeSet of product generated instance
			if (product.getM_AttributeSetInstance_ID() == 0)
			{
				return MStorage.getWarehouse(ctx,
						M_Warehouse_ID,
						M_Product_ID,
						M_ASI_ID,
						minGuaranteeDate,
						MClient.MMPOLICY_FiFo.equals(MMPolicy), // FiFo
						true, // positiveOnly
						0, // M_Locator_ID
						trxName);
			}
			else
			{
				// TODO: vpj-cd Create logic to get storage that matched with attribure set that not create instances
				return MStorage.getWarehouse(ctx,
						M_Warehouse_ID,
						M_Product_ID,
						0,
						minGuaranteeDate,
						MClient.MMPOLICY_FiFo.equals(MMPolicy), // FiFo
						true, // positiveOnly
						0, // M_Locator_ID
						trxName);
			}

		}
		else
		{
			return new MStorage[0];
		}
	}

	/**
	 * Create Issue
	 *
	 * @param PP_OrderBOMLine_ID
	 * @param movementdate
	 * @param qty
	 * @param qtyScrap
	 * @param qtyReject
	 * @param storages
	 * @param force Issue
	 *
	 * @deprecated Please use {@link IPPCostCollectorBL#createIssue(I_PP_Order_BOMLine, int, int, java.util.Date, BigDecimal, BigDecimal, BigDecimal, org.compiere.model.I_C_UOM)}.
	 */
	@Deprecated
	private static String createIssue(
			final I_PP_Order order,
			final int PP_Order_BOMLine_ID,
			final Timestamp movementdate,
			final BigDecimal qty,
			final BigDecimal qtyScrap,
			final BigDecimal qtyReject,
			final MStorage[] storages,
			final boolean forceIssue)
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		final StringBuilder sb = new StringBuilder();
		sb.append("\n");

		if (qty.signum() == 0)
		{
			return sb.toString();
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final String trxName = InterfaceWrapperHelper.getTrxName(order);
		final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.create(ctx, PP_Order_BOMLine_ID, I_PP_Order_BOMLine.class, trxName);

		final int productId = orderBOMLine.getM_Product_ID();
		final I_C_UOM uom = Services.get(IProductBL.class).getStockingUOM(productId);

		BigDecimal toIssue = qty.add(qtyScrap);
		for (final I_M_Storage storage : storages)
		{
			// TODO Selection of ASI

			final BigDecimal qtyOnHand = storage.getQtyOnHand();
			if (qtyOnHand.signum() <= 0)
			{
				continue;
			}

			final BigDecimal qtyIssue = toIssue.min(qtyOnHand);
			// create record for negative and positive transaction
			if (qtyIssue.signum() != 0 || qtyScrap.signum() != 0 || qtyReject.signum() != 0)
			{
				String costCollectorType = X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue;
				// Method Variance
				if (orderBOMLine.getQtyBatch().signum() == 0
						&& orderBOMLine.getQtyBOM().signum() == 0)
				{
					costCollectorType = X_PP_Cost_Collector.COSTCOLLECTORTYPE_MethodChangeVariance;
				}
				else if (PPOrderUtil.isComponentTypeOneOf(orderBOMLine, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product))
				{
					costCollectorType = X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance;
				}
				//
				final I_PP_Cost_Collector cc = ppCostCollectorBL.createIssue(ComponentIssueCreateRequest.builder()
						.orderBOMLine(orderBOMLine)
						.locatorId(storage.getM_Locator_ID())
						.attributeSetInstanceId(0)
						.movementDate(movementdate)
						.qtyUOM(uom)
						.qtyIssue(qtyIssue)
						.qtyScrap(qtyScrap)
						.qtyReject(qtyReject)
						.build());

				sb.append(cc.getDocumentNo());
				sb.append("\n");

			}

			toIssue = toIssue.subtract(qtyIssue);
			if (toIssue.signum() == 0)
			{
				break;
			}
		}
		if (forceIssue && toIssue.signum() != 0)
		{
			final I_PP_Cost_Collector cc = ppCostCollectorBL.createIssue(ComponentIssueCreateRequest.builder()
					.orderBOMLine(orderBOMLine)
					.locatorId(orderBOMLine.getM_Locator_ID())
					.attributeSetInstanceId(orderBOMLine.getM_AttributeSetInstance_ID())
					.movementDate(movementdate)
					.qtyUOM(uom)
					.qtyIssue(toIssue)
					.build());

			sb.append(cc.getDocumentNo());
			sb.append("\n");

			toIssue = BigDecimal.ZERO;
		}

		//
		if (toIssue.signum() != 0)
		{
			// should not happen because we validate Qty On Hand on start of this process
			throw new LiberoException("Should not happen toIssue=" + toIssue);
		}

		return sb.toString();
	}

}
