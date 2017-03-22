package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
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


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.MOrder;
import org.compiere.model.Query;
import org.compiere.model.X_C_Order;
import org.compiere.process.DocAction;
import org.junit.Assert;
import org.slf4j.Logger;

import de.metas.adempiere.ait.helper.ProductPriceVO.LineType;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.service.IOrderBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.interfaces.I_M_Shipper;
import de.metas.logging.LogManager;

public class OrderHelper
{
	private final Logger logger = LogManager.getLogger(getClass());

	public enum Order_InvoiceRule
	{
		IMMEDIATE
		{
			@Override
			public String toString()
			{
				return X_C_Order.INVOICERULE_Immediate;
			}
		},
		AFTER_DELIVERY
		{
			@Override
			public String toString()
			{
				return X_C_Order.INVOICERULE_AfterDelivery;
			}
		},
		AFTER_ORDER_DELIVERED
		{
			@Override
			public String toString()
			{
				return X_C_Order.INVOICERULE_AfterOrderDelivered;
			}
		}
	};

	public enum Order_DeliveryRule
	{
		AVAILIABILITY
		{
			@Override
			public String toString()
			{
				return X_C_Order.DELIVERYRULE_Availability;
			}
		},
		FORCE
		{
			@Override
			public String toString()
			{
				return X_C_Order.DELIVERYRULE_Force;
			}
		}
	}

	private final IHelper helper;
	private boolean gridTabLevel = true;

	private String pricingSystemValue = Helper.DEFAULT_PricingSystemValue;
	private String currencyCode;
	private String countryCode;
	private String bPartnerName = null;
	
	private boolean isSOTrx = true;
	private String docSubType;

	private Timestamp dateOrdered;
	private Timestamp datePromised;

	private String expectedCompleteStatus = null;
	// private ArrayList<ProductPriceVO> linesPPVO = new ArrayList<ProductPriceVO>();
	private ArrayList<OrderLineHelper> lines = new ArrayList<OrderLineHelper>();
	private Order_InvoiceRule invoiceRule = Order_InvoiceRule.IMMEDIATE;
	private Order_DeliveryRule deliveryRule = Order_DeliveryRule.FORCE;
	private String paymentRule = X_C_Order.PAYMENTRULE_Cash;
	private String paymentTermValue = null;
	private String freighCostRule = X_C_Order.FREIGHTCOSTRULE_FreightIncluded;

	//
	//
	private I_C_Order order;
	private GridWindowHelper gridWindowHelper;

	OrderHelper(final IHelper helper)
	{
		this.helper = helper;
		this.dateOrdered = helper.getNow();
		this.datePromised = helper.getNow();

		// not yet ported from 
//		final TestClientUI clientUI = (TestClientUI)Services.get(IClientUI.class);
//		clientUI.setYesNoAnswer(C_Order_Deprecated.AD_Message_SyncAddressChangeToExistingLines, Boolean.TRUE);
	}

	public I_C_Order quickCreateOrder(String pricingSystemValue, String productValue, String docSubType, int priceList, int priceActual)
	{
		return this.setPricingSystemValue(pricingSystemValue)
				.setDocSubType(docSubType)
				.addLine(productValue, priceList, priceActual)
				.setComplete(DocAction.STATUS_Completed)
				.createOrder(I_C_Order.class);
	}

	public OrderHelper setBPartnerName(final String bPartnerName)
	{
		this.bPartnerName = bPartnerName;
		return this;
	}

	public OrderHelper setPricingSystemValue(final String pricingSystemValue)
	{
		this.pricingSystemValue = pricingSystemValue;
		return this;
	}

	/**
	 * Returns the currency code used when creating the order and making sure that there is also pricing data. If no
	 * value has been set using {@link #setCurrencyCode(String)}, then the method delegates to
	 * {@link IHelper#getCurrencyCode()}.
	 * 
	 * @return
	 */
	public String getCurrencyCode()
	{
		if (currencyCode == null)
		{
			return helper.getCurrencyCode();
		}
		return currencyCode;
	}

	public OrderHelper setCurrencyCode(final String currencyCode)
	{
		this.currencyCode = currencyCode;
		return this;
	}

	/**
	 * Returns the country code used when creating the order and making sure that there is also pricing data. If no
	 * value has been set using {@link #setCountryCode(String)}, then the method delegates to
	 * {@link IHelper#getCountryCode()}.
	 * 
	 * @return
	 */
	public String getCountryCode()
	{
		if (countryCode == null)
		{
			return helper.getCountryCode();
		}
		return countryCode;
	}

	public OrderHelper setCountryCode(final String countryCode)
	{
		this.countryCode = countryCode;
		return this;
	}

	public OrderHelper setDocSubType(final String docSubType)
	{
		this.docSubType = docSubType;
		if (docSubType != null)
		{
			// assuming is sales transaction
			this.isSOTrx = true;
		}
		return this;
	}

	public OrderHelper setDateOrdered(Timestamp dateOrdered)
	{
		this.dateOrdered = dateOrdered;
		return this;
	}

	public OrderHelper setDatePromised(Timestamp datePromised)
	{
		this.datePromised = datePromised;
		return this;
	}

	private Integer dropshipLocationId;

	public OrderHelper setDropShipLocation_ID(int dropshipId)
	{
		this.dropshipLocationId = dropshipId;
		return this;
	}

	public OrderHelper setIsSOTrx(boolean isSOTrx)
	{
		this.isSOTrx = isSOTrx;
		return this;
	}

	public boolean isSOTrx()
	{
		return isSOTrx;
	}

	/**
	 * Method calls {@link #addLine(Class)} with {@link OrderLineHelper}.class as parameter. Feel free to override it in
	 * subclasses of this {@link OrderHelper}.
	 * 
	 * @return
	 */
	public OrderLineHelper addLine()
	{
		return addLine(OrderLineHelper.class);
	}

	public OrderHelper addLine(
			final String productValue,
			final BigDecimal qty,
			final int priceList,
			final int priceActual,
			final boolean useFastInput)
	{
		return addLine()
				.setProductValue(productValue)
				.setQty(qty)
				.setPriceList(new BigDecimal(priceList))
				.setPriceActual(new BigDecimal(priceActual))
				.setUseFastInput(useFastInput)
				.endLine();
	}

	public OrderHelper addLine(
			final String productValue,
			final BigDecimal qty,
			final int priceList,
			final int priceActual)
	{
		return addLine(productValue, qty, priceList, priceActual, true);
	}

	/**
	 * Returns an order line helper with the given class. By using this method, one can add module-custom order line
	 * helpers to create order lines with additional columns
	 * 
	 * For an example, check out the <code>ContractsOrderLineHelper</code> in the de.metas.contracts-ait project
	 * 
	 * @param clazz
	 * @return
	 */
	public <T extends OrderLineHelper> T addLine(final Class<T> clazz)
	{
		try
		{
			final Constructor<T> constructor = clazz.getConstructor(OrderHelper.class, IHelper.class, LineType.class);
			final T line = constructor.newInstance(this, helper, LineType.Product);

			lines.add(line);
			return line;
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	public OrderHelper addLine(
			final String productValue,
			final int priceList,
			final int priceActual)
	{
		return addLine(productValue, BigDecimal.ONE, priceList, priceActual);
	}

	/**
	 * Tells this helper to complete the order after creation.
	 * 
	 * @param expectedCompleteStatus
	 *            the expected docstatus after the completion ('WP' or 'CO'). If <code>null</code>, the order is not
	 *            completed.
	 * @return
	 */
	public OrderHelper setComplete(final String expectedCompleteStatus)
	{
		this.expectedCompleteStatus = expectedCompleteStatus;
		return this;
	}

	public I_C_Order createOrder()
	{
		return createOrder(I_C_Order.class);
	}
	
	public <T extends I_C_Order> T createOrder(Class<T> clazz)
	{
		Assert.assertNull("Builder already run", order);

		final Properties ctx = helper.getCtx();

		if (gridTabLevel)
		{
			gridWindowHelper = helper.mkGridWindowHelper();
			order = gridWindowHelper
					.openTabForTable(I_C_Order.Table_Name, isSOTrx)
					.newRecord()
					.getGridTabInterface(clazz);
		}
		else
		{
			order = InterfaceWrapperHelper.create(ctx, clazz, helper.getTrxName());
			order.setIsSOTrx(isSOTrx);
		}

		configureOrder();
		saveOrder();

		if (docSubType != null)
		{
			assertThat(order.getC_DocTypeTarget().getDocSubType(), equalTo(docSubType));
		}

		for (final OrderLineHelper line : lines)
		{
			line.createLine(order);
		}

		if (expectedCompleteStatus != null)
		{
			this.process(DocAction.ACTION_Complete, expectedCompleteStatus);

			if (docSubType != null)
				assertThat(order.getC_DocType().getDocSubType(), equalTo(docSubType));
		}

		System.out.println("Order: " + order + ", NetAmt=" + order.getTotalLines());

		return InterfaceWrapperHelper.create(order, clazz);
	}

	public OrderHelper process(String docAction, String expectedDocStatus)
	{
		if (gridTabLevel)
		{
			final int bkpOrderId = order.getC_Order_ID();

			// this resets the selected order ID to some arbitrary order in the grid tab
			gridWindowHelper.selectTab(I_C_Order.Table_Name);
			gridWindowHelper.selectRecordById(bkpOrderId);

			order.setDocAction(docAction);
			gridWindowHelper.runProcess(I_C_Order.COLUMNNAME_DocAction);

			gridWindowHelper.refresh();
			if (expectedDocStatus != null)
				assertThat(order.getDocStatus(), equalTo(expectedDocStatus));
		}
		else
		{
			helper.process(order, docAction, expectedDocStatus);
		}

		orderLines = null;

		return this;
	}

	public OrderHelper createOrderNow()
	{
		createOrder(I_C_Order.class);
		return this;
	}

	public I_C_Order getC_Order()
	{
		return order;
	}

	private List<I_C_OrderLine> orderLines = null;

	public List<I_C_OrderLine> getC_OrderLines(boolean requery)
	{
		Assert.assertNotNull("Order was not created yet", order);

		if (orderLines != null && !requery)
			return orderLines;

		Properties ctx = InterfaceWrapperHelper.getCtx(order);
		String trxName = InterfaceWrapperHelper.getTrxName(order);
		orderLines = new Query(ctx, I_C_OrderLine.Table_Name, I_C_OrderLine.COLUMNNAME_C_Order_ID + "=?", trxName)
				.setParameters(order.getC_Order_ID())
				.setOrderBy(I_C_OrderLine.COLUMNNAME_Line)
				.list(I_C_OrderLine.class);
		orderLines = Collections.unmodifiableList(orderLines);
		return orderLines;
	}

	private void saveOrder()
	{
		if (gridTabLevel)
		{
			gridWindowHelper
					.validateLookups()
					.save();
		}
		else
		{
			InterfaceWrapperHelper.save(order);
		}
		assertTrue("C_Order_ID shall be set", order.getC_Order_ID() > 0);
	}

	public MOrder getOrderPO(I_C_Order order)
	{
		MOrder orderPO = InterfaceWrapperHelper.getPO(order);
		if (orderPO == null)
		{
			orderPO = new MOrder(helper.getCtx(), order.getC_Order_ID(), helper.getTrxName());
		}
		return orderPO;
	}

	private void configureOrder()
	{
		final Properties ctx = helper.getCtx();
		final String trxName = helper.getTrxName();

		final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrencyByISOCode(ctx, getCurrencyCode());
		final I_M_PricingSystem ps = helper.getM_PricingSystem(pricingSystemValue);
		
		// setting these two first, because some callouts assume that e.g. the correct ps is selected prior to the bpartner
		order.setM_PricingSystem_ID(ps.getM_PricingSystem_ID());
		order.setC_Currency_ID(currency.getC_Currency_ID());
		
		// Make sure we a created the PriceSystem/PriceList/Version
		final I_M_PriceList priceList = helper.getM_PriceList(pricingSystemValue, getCurrencyCode(), getCountryCode(), isSOTrx);

		if (docSubType != null)
		{
			Services.get(IOrderBL.class).setDocTypeTargetId(order, docSubType);
		}
		else
		{
			Services.get(IOrderBL.class).setDocTypeTargetId(order);
		}
		Assert.assertTrue("C_DocTypeTarget_ID was not set/found", order.getC_DocTypeTarget_ID() > 0);

		final I_C_BPartner bp;
		if (bPartnerName == null)
		{
			bp = helper.mkBPartnerHelper().getC_BPartner(helper.getConfig());
		}
		else
		{
			bp = helper.mkBPartnerHelper().getC_BPartnerByName(bPartnerName);
		}
		order.setC_BPartner_ID(bp.getC_BPartner_ID());
		
		order.setDateOrdered(dateOrdered);
		order.setDatePromised(datePromised);
		order.setDateAcct(dateOrdered);

		// metas-ts: Warehouse is not mandatory anymore, plus the returned value might not be valid for the order's DocType
		// order.setM_Warehouse_ID(helper.getM_Warehouse().getM_Warehouse_ID()); 
		
		order.setInvoiceRule(invoiceRule.toString());
		order.setDeliveryRule(MOrder.DELIVERYRULE_Force);
		order.setFreightCostRule(MOrder.FREIGHTCOSTRULE_Calculated);
		order.setDescription("Generated by " + this.toString() + " on " + (new Date()));
		order.setPaymentRule(paymentRule);
		if (paymentTermValue != null)
			order.setC_Payment_ID(helper.getC_PaymentTerm(paymentTermValue).getC_PaymentTerm_ID());

		// TODO: Purchase order settings (shall be set it by ADempiere it self)
		if (!order.isSOTrx())
		{
			if (order.getM_PriceList_ID() <= 0)
			{
				logger.warn("Setting pricelist on purchase order because is not set by default");
				order.setM_PriceList_ID(priceList.getM_PriceList_ID());
			}

			if (order.getC_PaymentTerm_ID() <= 0)
			{
				logger.warn("Setting payment term on purchase order because is not set by default");
				order.setC_Payment_ID(helper.getC_PaymentTerm().getC_PaymentTerm_ID()); // TODO: need to check wher is
																						// reseted because at save the
																						// field is empty
			}
		} 
		else 
		{
			order.setFreightCostRule(freighCostRule);
			order.setDeliveryRule(deliveryRule.toString());
		}
		// Shipper:
		final I_M_Shipper shipper = InterfaceWrapperHelper.create(Services.get(IBPartnerDAO.class).retrieveShipper(order.getC_BPartner_ID(), trxName), I_M_Shipper.class);
		if (shipper != null)
		{
			order.setM_Shipper_ID(shipper.getM_Shipper_ID());
		}

		if (dropshipLocationId != null)
		{
			order.setIsDropShip(true);
			order.setDropShip_Location_ID(dropshipLocationId);
		}
	}

	public OrderHelper setInvoiceRule(Order_InvoiceRule invoiceRule)
	{
		this.invoiceRule = invoiceRule;
		return this;
	}

	public OrderHelper setDeliveryRule(Order_DeliveryRule deliveryRule)
	{
		this.deliveryRule = deliveryRule;
		return this;
	}

	public OrderHelper setPaymentRule(String paymentRule)
	{
		this.paymentRule = paymentRule;
		return this;
	}

	public OrderHelper setFreighCostRule(String freighCostRule)
	{
		this.freighCostRule = freighCostRule;
		return this;
	}

	public OrderHelper setPaymentTerm(String paymentTermValue)
	{
		this.paymentTermValue = paymentTermValue;
		return this;
	}

	/**
	 * Enable GridTab level mode. If enabled, document will be created using only GridTab
	 * 
	 * @param gridTabLevel
	 * @return
	 */
	public OrderHelper setGridTabLevel(boolean gridTabLevel)
	{
		this.gridTabLevel = gridTabLevel;
		return this;
	}

	public int retrieveLastOrderLineId(I_C_Order order, boolean failIfNotFound)
	{
		final int newOrderLineId =
				new Query(helper.getCtx(), I_C_OrderLine.Table_Name, I_C_OrderLine.COLUMNNAME_C_Order_ID + "=?", helper.getTrxName())
						.setParameters(order.getC_Order_ID())
						.setOrderBy(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + " DESC")
						.firstId();
		if (newOrderLineId <= 0 && failIfNotFound)
		{
			assertTrue("Expecting order line to be generated for " + order, newOrderLineId > 0);
		}
		return newOrderLineId;
	}

	public OrderHelper assertLinesNo(int expectedLinesNo)
	{
		Assert.assertEquals("Order Lines count not match", expectedLinesNo, getC_OrderLines(false).size());
		return this;
	}

	public OrderHelper assertPriceQtyByIndex(int index, BigDecimal expectedQty, BigDecimal expectedPriceActual)
	{
		final I_C_OrderLine orderLine = getC_OrderLines(false).get(index);
		if (expectedQty != null)
			assertThat("Qty not match for " + orderLine, orderLine.getQtyOrdered(), comparesEqualTo(expectedQty));
		if (expectedPriceActual != null)
			assertThat("PriceActual not match for " + orderLine, orderLine.getPriceActual(), comparesEqualTo(expectedPriceActual));
		return this;
	}

	@Override
	public String toString()
	{
		return getClass().getCanonicalName() + "[" + "gridTabLevel=" + gridTabLevel + "]";
	}

	public String getPricingSystemValue()
	{
		return pricingSystemValue;
	}

	public boolean isGridTabLevel()
	{
		return gridTabLevel;
	}

	public GridWindowHelper getGridWindowHelper()
	{
		return gridWindowHelper;
	}

}
