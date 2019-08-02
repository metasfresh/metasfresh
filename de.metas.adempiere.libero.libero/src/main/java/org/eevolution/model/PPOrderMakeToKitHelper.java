package org.eevolution.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MClient;
import org.compiere.model.MStorage;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;
import org.eevolution.api.ComponentIssueCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.ReceiptCostCollectorCandidate;

import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.order.DeliveryRule;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

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
	public static void complete(final I_PP_Order ppOrder)
	{
		final LocalDateTime today = SystemTime.asLocalDateTime();

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
		final DeliveryRule orderDeliveryRule = DeliveryRule.ofCode(oline.getC_Order().getDeliveryRule());
		if (DeliveryRule.COMPLETE_LINE.equals(orderDeliveryRule) ||
				DeliveryRule.COMPLETE_ORDER.equals(orderDeliveryRule))
		{
			final boolean isCompleteQtyDeliver = isQtyAvailable(ppOrder, issue, today);
			if (!isCompleteQtyDeliver)
			{
				throw new LiberoException("@NoQtyAvailable@");
			}
		}
		else if (DeliveryRule.AVAILABILITY.equals(orderDeliveryRule) ||
				DeliveryRule.AFTER_RECEIPT.equals(orderDeliveryRule) ||
				DeliveryRule.MANUAL.equals(orderDeliveryRule))
		{
			throw new LiberoException("@ActionNotSupported@");
		}
		else if (DeliveryRule.FORCE.equals(orderDeliveryRule))
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
				final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.create(Env.getCtx(), PP_Order_BOMLine_ID, I_PP_Order_BOMLine.class, ITrx.TRXNAME_ThreadInherited);
				// Validate if AttributeSet generate instance
				M_AttributeSetInstance_ID = orderBOMLine.getM_AttributeSetInstance_ID();
			}

			MStorage[] storages = getStorages(
					M_Product_ID,
					ppOrder.getM_Warehouse_ID(),
					M_AttributeSetInstance_ID,
					today);

			createIssue(
					ppOrder,
					key.getKey(),
					today,
					qtyToDeliver,
					qtyScrapComponent,
					BigDecimal.ZERO,
					storages,
					forceIssue);
		}

		IPPOrderBL ppOrderService = Services.get(IPPOrderBL.class);
		final Quantity qtyToReceive = ppOrderService.getQtyOpen(ppOrder);
		final Quantity qtyScrapped = ppOrderService.getQtyScrapped(ppOrder);
		final Quantity qtyRejected = ppOrderService.getQtyRejected(ppOrder);

		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		final ReceiptCostCollectorCandidate candidate = ReceiptCostCollectorCandidate.builder()
				.order(ppOrder)
				.movementDate(today)
				.qtyToReceive(qtyToReceive)
				.qtyScrap(qtyScrapped)
				.qtyReject(qtyRejected)
				.locatorId(Services.get(IWarehouseDAO.class).getLocatorIdByRepoIdOrNull(ppOrder.getM_Locator_ID()))
				.productId(ProductId.ofRepoId(ppOrder.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(ppOrder.getM_AttributeSetInstance_ID()))
				.build();

		ppCostCollectorBL.createReceipt(candidate);

		ppOrder.setQtyDelivered(qtyToReceive.toBigDecimal());
	}

	/**
	 * get if Component is Available
	 *
	 * @param MPPOrdrt Manufacturing order
	 * @param issues
	 * @param minGuaranteeDate Guarantee Date
	 * @return true when the qty available is enough
	 */
	private static boolean isQtyAvailable(I_PP_Order order, final Map<Integer, PPOrderBOMLineModel> issue, LocalDateTime minGuaranteeDate)
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

			if (M_Product_ID > 0 && Services.get(IProductBL.class).isStocked(ProductId.ofRepoIdOrNull(M_Product_ID)))
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
						final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.create(Env.getCtx(), PP_Order_BOMLine_ID, I_PP_Order_BOMLine.class, ITrx.TRXNAME_ThreadInherited);
						// Validate if AttributeSet generate instance
						M_AttributeSetInstance_ID = orderBOMLine.getM_AttributeSetInstance_ID();
					}
				}

				MStorage[] storages = getStorages(
						M_Product_ID,
						order.getM_Warehouse_ID(),
						M_AttributeSetInstance_ID,
						minGuaranteeDate);

				if (M_AttributeSetInstance_ID == 0)
				{
					BigDecimal toIssue = qtyToDeliver.add(qtyScrapComponent);
					for (MStorage storage : storages)
					{
						// TODO Selection of ASI
						if (storage.getQtyOnHand().signum() == 0)
						{
							continue;
						}
						BigDecimal issueActual = toIssue.min(storage.getQtyOnHand());
						toIssue = toIssue.subtract(issueActual);
						if (toIssue.signum() <= 0)
						{
							break;
						}
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
				{
					break;
				}

			}
		} // for each line

		return isCompleteQtyDeliver;
	}

	private static MStorage[] getStorages(
			int M_Product_ID,
			int M_Warehouse_ID,
			int M_ASI_ID,
			LocalDateTime minGuaranteeDate)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(M_Product_ID);
		if (product != null && Services.get(IProductBL.class).isStocked(product))
		{
			String MMPolicy = Services.get(IProductBL.class).getMMPolicy(product);

			final Properties ctx = Env.getCtx();
			final String trxName = ITrx.TRXNAME_ThreadInherited;
			// Validate if AttributeSet of product generated instance
			if (product.getM_AttributeSetInstance_ID() == 0)
			{
				return MStorage.getWarehouse(ctx,
						M_Warehouse_ID,
						M_Product_ID,
						M_ASI_ID,
						TimeUtil.asTimestamp(minGuaranteeDate),
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
						TimeUtil.asTimestamp(minGuaranteeDate),
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
			final LocalDateTime movementdate,
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
		final I_C_UOM uom = Services.get(IProductBL.class).getStockUOM(productId);

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
				final I_PP_Cost_Collector cc = ppCostCollectorBL.createIssue(ComponentIssueCreateRequest.builder()
						.orderBOMLine(orderBOMLine)
						.locatorId(Services.get(IWarehouseDAO.class).getLocatorIdByRepoIdOrNull(storage.getM_Locator_ID()))
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.movementDate(movementdate)
						.qtyIssue(Quantity.of(qtyIssue, uom))
						.qtyScrap(Quantity.of(qtyScrap, uom))
						.qtyReject(Quantity.of(qtyReject, uom))
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
					.locatorId(Services.get(IWarehouseDAO.class).getLocatorIdByRepoIdOrNull(orderBOMLine.getM_Locator_ID()))
					.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(orderBOMLine.getM_AttributeSetInstance_ID()))
					.movementDate(movementdate)
					.qtyIssue(Quantity.of(toIssue, uom))
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
