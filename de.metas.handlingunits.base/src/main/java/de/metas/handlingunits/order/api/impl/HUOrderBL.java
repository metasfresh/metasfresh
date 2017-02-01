package de.metas.handlingunits.order.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;
import java.util.function.Consumer;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductQuery;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.order.api.IHUOrderBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.product.IProductDAO;

public class HUOrderBL implements IHUOrderBL
{
	private static final transient Logger logger = LogManager.getLogger(HUOrderBL.class);

	@Override
	public void updateOrderLine(final de.metas.interfaces.I_C_OrderLine olPO, final String columnName)
	{
		//
		// services
		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);

		final de.metas.handlingunits.model.I_C_OrderLine ol = InterfaceWrapperHelper.create(olPO, de.metas.handlingunits.model.I_C_OrderLine.class);

		if (olPO.getM_Product_ID() <= 0)
		{
			return; // No product selected. Nothing to do.
		}
		if (ol.isManualQtyItemCapacity())
		{
			return; // fiddling with the order line is not our business. Nothing to do
		}

		final String huUnitType = X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;

		I_M_HU_PI_Item_Product pip = ol.getM_HU_PI_Item_Product();
		final boolean isCounterDoc = ol.getRef_OrderLine_ID() > 0;

		// in case the order line already has a packing instruction, it should be kept as is, excepting the lines of counter documents
		if (pip != null && !isCounterDoc)
		{
			// nothing to do. Keep the old packing instructions
		}

		else if (InterfaceWrapperHelper.isNew(ol))
		{
			pip = getOrderLinePIIPForNewOrderLine(ol, huUnitType);
		}
		else
		{
			// if the partner or product has changed, we need to search for the proper combination
			if (isProductChanged(olPO, columnName) || isBPartnerChanged(olPO, columnName))
			{
				final boolean allowInfiniteCapacity = true;
				pip = hupiItemProductDAO.retrieveMaterialItemProduct(olPO.getM_Product(), olPO.getC_BPartner(), olPO.getDateOrdered(), huUnitType, allowInfiniteCapacity);
			}
			// use the existing pip
			else if (ol.getM_HU_PI_Item_Product_ID() > 0 && (isQtyChanged(olPO, columnName) || isM_HU_PI_Item_ProductChanged(olPO, columnName)))
			{
				pip = ol.getM_HU_PI_Item_Product();
			}
			// the pip was reset
			else
			{
				pip = null;
			}
		}

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		if (pip == null)
		{
			ol.setPackDescription("");
			ol.setQtyItemCapacity(null);
			//
			// if no pip it should be VirtualPI
			final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
			final I_M_HU_PI_Item_Product pipVirtual = hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(ctx);
			ol.setM_HU_PI_Item_Product(pipVirtual);

			// 05825 : Update Prices and set prices
			orderLineBL.updatePrices(ol);
			final String trxName = InterfaceWrapperHelper.getTrxName(ol);
			orderLineBL.setPricesIfNotIgnored(ctx,
					ol,
					InterfaceWrapperHelper.isNew(ol),                       // usePriceUOM
					trxName);
		}
		// If is not null, set all related items
		else
		{
			ol.setM_HU_PI_Item_Product_ID(pip.getM_HU_PI_Item_Product_ID());
			//
			BigDecimal qtyCap = BigDecimal.ZERO;
			if (!Services.get(IHUCapacityBL.class).isInfiniteCapacity(pip))
			{
				final I_M_Product product = ol.getM_Product();
				qtyCap = Services.get(IHUCapacityBL.class).getCapacity(pip, product, pip.getC_UOM()).getCapacity();
				Check.assume(qtyCap.signum() != 0, "Zero capacity for M_HU_PI_Item_Product {}", pip.getM_HU_PI_Item_Product_ID());
			}
			final String description = pip.getDescription();
			final StringBuilder packDescription = new StringBuilder();
			packDescription.append(Check.isEmpty(description, true) ? "" : description);
			ol.setPackDescription(packDescription.toString());

			// 05131 : Changed column from virtual to real.
			if (!ol.isManualQtyItemCapacity())
			{
				ol.setQtyItemCapacity(qtyCap);
			}
			// 05825 : Update Prices and set prices
			orderLineBL.updatePrices(ol);
			final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
			final String trxName = InterfaceWrapperHelper.getTrxName(ol);
			orderLineBL.setPricesIfNotIgnored(ctx, ol,
					InterfaceWrapperHelper.isNew(ol),                       // usePriceUOM
					trxName);
		}
	}

	private I_M_HU_PI_Item_Product getOrderLinePIIPForNewOrderLine(
			final de.metas.handlingunits.model.I_C_OrderLine ol,
			final String huUnitType)
	{
		if (ol.getM_HU_PI_Item_Product_ID() <= 0)
		{
			// pip = Services.get(IHUPIItemProductDAO.class).retrieveMaterialItemProduct(olPO.getM_Product(), olPO.getC_BPartner(), olPO.getDateOrdered(), huUnitType);
			// 06730 : Removed functionality for now.
			return null;
		}

		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
		final IHUPIItemProductBL hupiItemProductBL = Services.get(IHUPIItemProductBL.class);

		final I_M_HU_PI_Item_Product olPip = ol.getM_HU_PI_Item_Product();

		final boolean allowInfiniteCapacity = true;

		I_M_HU_PI_Item_Product newPIIP;

		// FRESH-351: maybe the order line was copied with *all* its columns and then the M_Product_ID was changed
		// -> this happens when creating a counter doc, see MOrder.copyLineFrom(...)

		// check if the ol has a packaging order line whose product is inconsistent with the ol's PIIP
		final boolean packagingProductMightBeInconsistent = true
				// here we just avoid NPEs and check if the ol actually has a packaging line with a product
				&& ol.getC_PackingMaterial_OrderLine_ID() > 0
				&& ol.getC_PackingMaterial_OrderLine().getM_Product_ID() > 0;

		final boolean inconsistentProduct =
		// a virtual piip or one that allows any product can't be inconsistent with the ol's current procudt
		!olPip.isAllowAnyProduct() && !hupiItemProductBL.isVirtualHUPIItemProduct(olPip)
				&& olPip.getM_Product_ID() != ol.getM_Product_ID();

		if (packagingProductMightBeInconsistent)
		{
			// the packing material product that will be used for packaging the products in the line
			final I_M_Product packagingProduct;

			final boolean isCounterDoc = ol.getRef_OrderLine_ID() > 0;

			if (ol.getC_PackingMaterial_OrderLine() == null || !isCounterDoc)
			{
				packagingProduct = null;
			}
			else
			{
				packagingProduct = ol.getC_PackingMaterial_OrderLine().getM_Product();
			}

			final IProductDAO productDAO = Services.get(IProductDAO.class);

			// the pm product that will have to fit the piip.
			// In case this product is null, the PIIPs which allow any product are also eligible.
			final I_M_Product pmProductToUse;

			if (isCounterDoc)
			{
				pmProductToUse = productDAO.retrieveMappedProductOrNull(packagingProduct, ol.getAD_Org());
			}

			else
			{
				pmProductToUse = null;
			}

			// FRESH-573: In case the packing material to use was not found in case of a counter document, always use VIrtual PI
			// Do not fallback to general packing instructions because it would be confusing.
			if (pmProductToUse == null && isCounterDoc)
			{
				newPIIP = null;
			}

			else
			{
				newPIIP = hupiItemProductDAO.retrieveMaterialItemProduct(
						ol.getM_Product(),
						ol.getC_BPartner(),
						ol.getDateOrdered(),
						huUnitType,
						allowInfiniteCapacity,
						// FRESH-386:
						// TODO: add listener etc infractucture to make this work nicely
						// ol.getC_PackingMaterial_OrderLine().getM_Product()
						pmProductToUse);
			}

			if (newPIIP != null && !isCounterDoc)
			{
				if (newPIIP.getM_HU_PI_Item_ID() != olPip.getM_HU_PI_Item_ID())
				{
					logger.debug("C_OrderLine={} has M_Product_ID={} and C_PackingMaterial_OrderLine={} with (packaging-)M_Product_ID={},"
							+ " but the ol's current M_HU_PI_Item_Product={} references a different packaging-M_Product;"
							+ " => going to change the ol's M_HU_PI_Item_Product to {}!",
							ol, ol.getM_Product_ID(), ol.getC_PackingMaterial_OrderLine(), ol.getC_PackingMaterial_OrderLine().getM_Product_ID(),
							olPip,
							newPIIP);
				}
			}
		}
		else if (inconsistentProduct)
		{
			newPIIP = hupiItemProductDAO.retrieveMaterialItemProduct(
					ol.getM_Product(),
					ol.getC_BPartner(),
					ol.getDateOrdered(),
					huUnitType,
					allowInfiniteCapacity);

			logger.debug("C_OrderLine={} has M_Product_ID={}, but the ol's current M_HU_PI_Item_Product={} has M_Product_ID={}; => going to change the ol's M_HU_PI_Item_Product to {}!",
					ol, ol.getM_Product_ID(), olPip, olPip.getM_Product_ID(), newPIIP);
		}
		else
		{
			newPIIP = olPip; // go with the order line's already set pip, because it is consistent with the order line's product.
		}

		return newPIIP;
	}

	private boolean isM_HU_PI_Item_ProductChanged(final I_C_OrderLine orderLine, final String columnName)
	{
		return de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID.equals(columnName) //
				|| InterfaceWrapperHelper.isPOValueChanged(orderLine, de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID);
	}

	private boolean isQtyChanged(final I_C_OrderLine orderLine, final String columnName)
	{
		return org.compiere.model.I_C_OrderLine.COLUMNNAME_QtyEntered.equals(columnName)
				|| InterfaceWrapperHelper.isPOValueChanged(orderLine, org.compiere.model.I_C_OrderLine.COLUMNNAME_QtyEntered);
	}

	private boolean isProductChanged(final I_C_OrderLine orderLine, final String columnName)
	{
		return org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID.equals(columnName)
				|| InterfaceWrapperHelper.isPOValueChanged(orderLine, org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID);
	}

	private boolean isBPartnerChanged(final I_C_OrderLine orderLine, final String columnName)
	{
		return org.compiere.model.I_C_OrderLine.COLUMNNAME_C_BPartner_ID.equals(columnName)
				|| InterfaceWrapperHelper.isPOValueChanged(orderLine, org.compiere.model.I_C_OrderLine.COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public boolean updateQtyCU(final I_C_Order order)
	{
		final I_M_HU_PI_Item_Product huPIP = order.getM_HU_PI_Item_Product();

		if (huPIP == null)
		{
			// nothing to do: the M_HU_PI_Item_Product was not yet set.
			return false;
		}

		if (huPIP.isInfiniteCapacity())
		{
			// nothing to do. The qty CU shall remain as it was manually set by the user
			return false;
		}

		final BigDecimal qtyTU = order.getQty_FastInput_TU();

		Check.assume(qtyTU.signum() >= 0, "Qty TU must be positive");

		// In case the Qty TU is set to 0, Qty CU shall be also set to 0
		if (qtyTU.compareTo(BigDecimal.ZERO) == 0)
		{
			order.setQty_FastInput(BigDecimal.ZERO);
		}
		else
		{
			final BigDecimal capacity = huPIP.getQty();

			order.setQty_FastInput(capacity.multiply(qtyTU));
		}

		return true;
	}

	@Override
	public boolean updateQtyTU(final I_C_Order order)
	{
		final I_M_HU_PI_Item_Product huPIP = order.getM_HU_PI_Item_Product();

		if (huPIP == null)
		{
			// nothing to do: the M_HU_PI_Item_Product was not yet set.
			return false;
		}

		if (huPIP.isInfiniteCapacity())
		{
			// nothing to do. The qty CU shall remain as it was manually set by the user
			return false;
		}

		final BigDecimal qtyCU = order.getQty_FastInput();

		Check.assume(qtyCU.signum() >= 0, "Qty CU must be positive");

		if (qtyCU.compareTo(BigDecimal.ZERO) == 0)
		{
			order.setQty_FastInput_TU(BigDecimal.ZERO);
		}
		else
		{
			final BigDecimal capacity = huPIP.getQty();

			order.setQty_FastInput_TU(qtyCU.divide(capacity, 0, BigDecimal.ROUND_UP));
		}

		// qty TU was modified
		return true;
	}

	@Override
	public void updateQtys(final I_C_Order order, final String columnname)
	{
		final I_M_HU_PI_Item_Product huPIP = order.getM_HU_PI_Item_Product();

		if (huPIP == null)
		{
			// nothing to do: the M_HU_PI_Item_Product was not yet set.
			return;
		}

		if (huPIP.isInfiniteCapacity())
		{
			// nothing to do. The qty CU shall remain as it was manually set by the user
			return;
		}
		else if (I_C_Order.COLUMNNAME_M_HU_PI_Item_Product_ID.equals(columnname))
		{
			final BigDecimal qtyCU = order.getQty_FastInput();
			if (qtyCU != null && qtyCU.signum() > 0)
			{
				updateQtyTU(order);
			}
			else
			{
				final BigDecimal qtyTU = order.getQty_FastInput_TU();
				if (qtyTU != null && qtyTU.signum() > 0)
				{
					updateQtyCU(order);
				}
			}

			return;
		}
		else if (I_C_Order.COLUMNNAME_Qty_FastInput_TU.equals(columnname))
		{

			updateQtyCU(order);
			return;
		}
		else if (de.metas.adempiere.model.I_C_Order.COLUMNNAME_Qty_FastInput.equals(columnname))
		{
			updateQtyTU(order);
			return;
		}
	}

	@Override
	public boolean hasTUs(final I_C_Order order)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final int bpartnerId = order.getC_BPartner_ID();
		final int productId = order.getM_Product_ID();
		final Date date = order.getDateOrdered();

		return hasTUs(ctx, bpartnerId, productId, date);
	}

	@Override
	public boolean hasTUs(final Properties ctx, final int bpartnerId, final int productId, final Date date)
	{
		final IHUPIItemProductDAO piItemProductDAO = Services.get(IHUPIItemProductDAO.class);

		final IHUPIItemProductQuery queryVO = piItemProductDAO.createHUPIItemProductQuery();
		queryVO.setC_BPartner_ID(bpartnerId);
		queryVO.setM_Product_ID(productId);
		queryVO.setDate(date);
		queryVO.setAllowAnyProduct(false);
		queryVO.setHU_UnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		return piItemProductDAO.matches(ctx, queryVO, ITrx.TRXNAME_None);
	}

	@Override
	public void findM_HU_PI_Item_Product(final org.compiere.model.I_C_Order order, final I_M_Product product, final Consumer<I_M_HU_PI_Item_Product> pipConsumer)
	{
		//
		// services
		final IHUDocumentHandlerFactory huDocumentHandlerFactory = Services.get(IHUDocumentHandlerFactory.class);
		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);


		Check.assumeNotNull(order, "Order cannot be null");

		if (order.getC_BPartner() == null || order.getDateOrdered() == null)
		{
			// in case order's C_BPartner_ID or DateOrdered are null
			// (i.e. when we just hit New to create a new order), there is no point to search for M_HU_PI_Item_Product record.
			// Please assume M_HU_PI_Item_Product is null immediately

			return;
		}

		//
		// Try fetching the PIP from pricing
		final IHUDocumentHandler handler = huDocumentHandlerFactory.createHandler(I_C_Order.Table_Name);
		if (null != handler && product != null && product.getM_Product_ID() > 0)
		{
			final I_M_HU_PI_Item_Product overridePip = handler.getM_HU_PI_ItemProductFor(order, product);
			// If we have a default price and it has an M_HU_PI_Item_Product, suggest it in quick entry.
			if (null != overridePip && overridePip.getM_HU_PI_Item_Product_ID() > 0)
			{
				if (overridePip.isAllowAnyProduct())
				{
					pipConsumer.accept(null);
				}
				else
				{
					pipConsumer.accept(overridePip);
				}
				return;
			}
		}

		//
		// Try fetching best matching PIP
		final I_M_HU_PI_Item_Product pip = hupiItemProductDAO.retrieveMaterialItemProduct(product, order.getC_BPartner(), order.getDateOrdered(),
				X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit,
				true); // allowInfiniteCapacity = true

		if (pip == null)
		{
			// nothing to do, product is not included in any Transport Units
			return;
		}

		else if (pip.isAllowAnyProduct())
		{
			return;
		}
		else
		{
			pipConsumer.accept(pip);
		}
	}

}
