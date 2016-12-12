package de.metas.adempiere.callout;

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

import static org.compiere.model.I_C_Order.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_Shipper_ID;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.search.IGridTabRowBuilder;
import org.compiere.apps.search.IInfoWindowGridRowBuilders;
import org.compiere.apps.search.NullInfoWindowGridRowBuilders;
import org.compiere.apps.search.impl.InfoWindowGridRowBuilders;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;

/**
 * This callout's default behavior is determined by {@link ProductQtyOrderFastInputHandler}. To change the behavior, explicitly add further handlers using
 * {@link #addOrderFastInputHandler(IOrderFastInputHandler)}. These will take precedence over the default handler.
 * <p>
 * Also see
 * <ul>
 * <li>{@link C_OrderFastInputTabCallout}: this tabcallout makes sure that the quick-input fields are empty (and not "0"!) when a new order record is created (task 09232).
 * </ul>
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Geschwindigkeit_Erfassung_%282009_0027_G131%29'>(2009 0027 G131)</a>"
 */
public class OrderFastInput extends CalloutEngine
{
	public static final String OL_DONT_UPDATE_ORDER = "OrderFastInput_DontUpdateOrder_ID_";

	public static final String ZERO_WEIGHT_PRODUCT_ADDED = "OrderFastInput.ZeroWeightProductAdded";

	private static final CompositeOrderFastInputHandler handlers = new CompositeOrderFastInputHandler(new ProductQtyOrderFastInputHandler());

	static final Logger logger = LogManager.getLogger(OrderFastInput.class);

	public static void addOrderFastInputHandler(final IOrderFastInputHandler handler)
	{
		handlers.addHandler(handler);
	}

	public String cBPartnerId(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		if (!order.isSOTrx())
		{
			return NO_ERROR;
		}

		if (order.getC_BPartner_ID() > 0)
		{
			final ICalloutRecord calloutRecord = calloutField.getCalloutRecord();
			if (setShipperId(order, true) && !Check.isEmpty(order.getReceivedVia()) && calloutRecord.dataSave(false))
			{
				calloutRecord.dataRefresh();
			}
		}
		selectFocus(calloutField);
		return NO_ERROR;
	}

	public String mShipperId(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}
		
		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		if (!order.isSOTrx())
		{
			return NO_ERROR;
		}
		// nothing to do right now...
		return NO_ERROR;
	}

	private boolean setShipperId(final I_C_Order order, final boolean force)
	{
		if (!force && order.getM_Shipper_ID() > 0)
		{
			return true;
		}

		if (order.getC_BPartner_ID() > 0)
		{
			// try to set the shipperId using BPartner
			final I_M_Shipper shipper = Services.get(IBPartnerDAO.class).retrieveShipper(order.getC_BPartner_ID(), ITrx.TRXNAME_None);
			if (shipper != null)
			{
				order.setM_Shipper(shipper);
				return true;
			}
		}
		return false;
	}

	public String mProduct(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final String msg = evalProductQtyInput(calloutField);
		selectFocus(calloutField);
		return msg;
	}

	public String qtyFastInput(final ICalloutField calloutField)
	{
		final String msg = evalProductQtyInput(calloutField);
		selectFocus(calloutField);
		return msg;
	}

	private IInfoWindowGridRowBuilders getOrderLineBuilders(final ICalloutField calloutField)
	{
		final I_C_Order order = calloutField.getModel(I_C_Order.class);

		//
		// First try: check if we already have builders from recently opened Info window
		final Properties ctx = calloutField.getCtx();
		final int windowNo = calloutField.getWindowNo();
		final IInfoWindowGridRowBuilders builders = InfoWindowGridRowBuilders.getFromContextOrNull(ctx, windowNo);
		if (builders != null)
		{
			logger.info("Got IInfoWindowGridRowBuilders from ctx for windowNo={}: {}", new Object[] { windowNo, builders });
			return builders;
		}

		//
		// Second try: Single product/qty case
		final int productId = order.getM_Product_ID();
		if (productId <= 0)
		{
			logger.info("Constructed NullInfoWindowGridRowBuilders, because order.getM_Product_ID()={}", productId);
			return NullInfoWindowGridRowBuilders.instance;
		}

		final GridTab gridTab = getGridTab(calloutField);
		if(gridTab == null)
		{
			return NullInfoWindowGridRowBuilders.instance;
		}
		
		final InfoWindowGridRowBuilders singletonBuilder = new InfoWindowGridRowBuilders();
		final IGridTabRowBuilder builder = handlers.createLineBuilderFromHeader(gridTab);
		builder.setSource(order);
		singletonBuilder.addGridTabRowBuilder(productId, builder);

		logger.info("Constructed singleton IInfoWindowGridRowBuilders: {}", singletonBuilder);

		return singletonBuilder;
	}

	public String evalProductQtyInput(final ICalloutField calloutField)
	{
		final I_C_Order order = calloutField.getModel(I_C_Order.class);

		final IInfoWindowGridRowBuilders orderLineBuilders = getOrderLineBuilders(calloutField);
		final Set<Integer> recordIds = orderLineBuilders.getRecordIds();
		if (recordIds.isEmpty())
		{
			return NO_ERROR; // nothing was selected
		}

		// metas kh: US840: show a warning if the new Product has a Weight <= 0
		// checkWeight(productIds, WindowNo);

		// clear the values in the order window
		// using invokeLater because at the time of this callout invocation we
		// are most probably in the mid of something that might prevent
		// changes to the actual swing component
		final boolean saveRecord = true;
		clearFieldsLater(calloutField, saveRecord);

		for (final int recordId : recordIds)
		{
			final IGridTabRowBuilder builder = orderLineBuilders.getGridTabRowBuilder(recordId);
			log.info("Calling addOrderLine for recordId=" + recordId + " and with builder=" + builder);

			addOrderLine(calloutField.getCtx(), order, builder::apply);
		}
		calloutField.getCalloutRecord().dataRefreshRecursively();

		// make sure that the freight amount is up to date
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final boolean fixPrice = X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(order.getFreightCostRule());
		if (!fixPrice)
		{
			orderBL.updateFreightAmt(calloutField.getCtx(), order, ITrx.TRXNAME_None);
		}

		return NO_ERROR;
	}

	private void clearFieldsLater(final ICalloutField calloutField, final boolean save)
	{
		Services.get(IClientUI.class).invokeLater(calloutField.getWindowNo(), new Runnable()
		{
			@Override
			public void run()
			{
				clearFields(calloutField, save);
			}
		});
	}

	public static I_C_OrderLine addOrderLine(final Properties ctx, final I_C_Order order, final Consumer<Object> orderLineCustomizer)
	{
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		final I_C_OrderLine ol = orderLineBL.createOrderLine(order);

		orderLineCustomizer.accept(ol); // note that we also get the M_Product_ID from the builder

		if (ol.getC_UOM_ID() <= 0 && ol.getM_Product_ID() > 0)
		{
			// the builders did provide a product, but no UOM, so we take the product's stocking UOM
			ol.setC_UOM_ID(ol.getM_Product().getC_UOM_ID());
		}

		// start: cg: 01717
		if (order.isSOTrx() && order.isDropShip())
		{
			final int C_BPartner_ID = order.getDropShip_BPartner_ID() > 0 ? order.getDropShip_BPartner_ID() : order.getC_BPartner_ID();
			ol.setC_BPartner_ID(C_BPartner_ID);

			final I_C_BPartner_Location bpLocation = Services.get(IOrderBL.class).getShipToLocation(order);
			ol.setC_BPartner_Location_ID(bpLocation != null ? bpLocation.getC_BPartner_Location_ID() : -1);

			final int AD_User_ID = order.getDropShip_User_ID() > 0 ? order.getDropShip_User_ID() : order.getAD_User_ID();
			ol.setAD_User_ID(AD_User_ID);
		}
		// end: cg: 01717

		// set the prices before saveEx, because otherwise, priceEntered is
		// reset and that way IOrderLineBL.setPrices can't tell whether it
		// should use priceEntered or a computed price.
		ol.setPriceEntered(BigDecimal.ZERO);
		orderLineBL.setPricesIfNotIgnored(ctx, ol,
				true,  // usePriceUOM = true
				ITrx.TRXNAME_None);

		// set OL_DONT_UPDATE_ORDER to inform the ol's model validator not to update the order
		final String dontUpdateOrderLock = OL_DONT_UPDATE_ORDER + order.getC_Order_ID();
		Env.setContext(ctx, dontUpdateOrderLock, true);
		try
		{
			InterfaceWrapperHelper.save(ol);
		}
		finally
		{
			Env.removeContext(ctx, dontUpdateOrderLock);
		}

		return ol;
	}

	/**
	 * Checks if the Weight of the given Product is zero or less and shows a warning if so.
	 * 
	 * @param product
	 * @param WindowNo
	 */
	void checkWeight(final Set<Integer> productIds, final int windowNo)
	{
		if (productIds == null || productIds.isEmpty())
		{
			return;
		}

		final StringBuilder invalidProducts = new StringBuilder();

		for (final Integer productId : productIds)
		{
			if (productId == null || productId <= 0)
			{
				continue;
			}

			final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);

			//
			// 07074: Switch off weight check for all Products which are not "Artikel" (type = item)
			if (!X_M_Product.PRODUCTTYPE_Item.equals(product.getProductType()))
			{
				continue;
			}

			if (product.getWeight().signum() <= 0)
			{
				if (invalidProducts.length() > 0)
				{
					invalidProducts.append(", ");
				}
				invalidProducts.append(product.getName());
			}
		}

		if (invalidProducts.length() > 0)
		{
			final IClientUI factory = Services.get(IClientUI.class);
			factory.warn(windowNo, ZERO_WEIGHT_PRODUCT_ADDED, invalidProducts.toString());
		}
	}

	private void selectFocus(final ICalloutField calloutField)
	{
		final I_C_Order order = calloutField.getModel(I_C_Order.class);

		final Integer bPartnerId = order.getC_BPartner_ID();
		final GridTab mTab = getGridTab(calloutField);
		if (mTab == null)
		{
			return;
		}
		
		if (bPartnerId <= 0 && mTab.getField(COLUMNNAME_C_BPartner_ID).isDisplayed(true))
		{
			mTab.getField(COLUMNNAME_C_BPartner_ID).requestFocus();
			return;
		}

		// received via is not so important anymore, so don't request focus on it
		// final String receivedVia = order.getReceivedVia();
		// if (Util.isEmpty(receivedVia)) {
		// mTab.getField(RECEIVED_VIA).requestFocus();
		// return;
		// }

		final Integer shipperId = order.getM_Shipper_ID();
		if (shipperId <= 0 && mTab.getField(COLUMNNAME_M_Shipper_ID).isDisplayed(true))
		{
			mTab.getField(COLUMNNAME_M_Shipper_ID).requestFocus();
			return;
		}

		//
		// Ask handlers to request focus
		handlers.requestFocus(mTab);
	}

	public static void clearFields(final ICalloutField calloutField, final boolean save)
	{
		clearFields(calloutField.getCalloutRecord(), save);
	}

	@Deprecated
	public static void clearFields(final ICalloutRecord calloutRecord, final boolean save)
	{
		final GridTab gridTab = GridTab.fromCalloutRecordOrNull(calloutRecord);
		if(gridTab == null)
		{
			return;
		}
		
		handlers.clearFields(gridTab);
		if (save)
		{
			gridTab.dataSave(true);
		}
	}

	@Deprecated
	private final static GridTab getGridTab(final ICalloutField calloutField)
	{
		final ICalloutRecord calloutRecord = calloutField.getCalloutRecord();
		return GridTab.fromCalloutRecordOrNull(calloutRecord);
	}
}
