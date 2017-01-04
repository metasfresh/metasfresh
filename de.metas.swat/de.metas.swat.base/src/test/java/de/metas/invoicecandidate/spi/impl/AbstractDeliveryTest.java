package de.metas.invoicecandidate.spi.impl;

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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

import de.metas.interfaces.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;
import mockit.Expectations;
import mockit.Mocked;

public abstract class AbstractDeliveryTest
{
	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@BeforeClass
	public final static void staticInit()
	{
		AdempiereTestHelper.get().init();
	}

	protected final Properties ctx = Env.getCtx();
	protected final String trxName = ITrx.TRXNAME_None;

	protected final C_OrderLine_Handler olHandler = new C_OrderLine_Handler();
	protected final I_C_ILCandHandler handler = InterfaceWrapperHelper.create(ctx, I_C_ILCandHandler.class, trxName);

	// task 07442
	private final I_AD_Org org = InterfaceWrapperHelper.create(ctx, I_AD_Org.class, trxName);
	private final I_M_Product product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, trxName);
	private final I_C_Activity activity = InterfaceWrapperHelper.create(ctx, I_C_Activity.class, trxName);
	@Mocked
	protected IProductAcctDAO productAcctDAO;
	@Mocked
	protected ITaxBL taxBL;
	// task 07442 end

	protected final I_C_Order order = InterfaceWrapperHelper.create(ctx, I_C_Order.class, trxName);
	protected final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(ctx, I_C_OrderLine.class, trxName);

	protected final I_M_InOut mInOut = InterfaceWrapperHelper.create(ctx, I_M_InOut.class, trxName);
	protected final I_M_InOutLine mInOutLine = InterfaceWrapperHelper.create(ctx, I_M_InOutLine.class, trxName);

	protected final I_C_BPartner bPartner = InterfaceWrapperHelper.create(ctx, I_C_BPartner.class, trxName);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		initHandlers();

		// initialize C_BPartner data
		{
			initC_BPartner();
		}
		// initialize C_Order data
		{
			initC_Order();
			initC_OrderLine();
		}

		// initialize M_InOut data
		{
			initM_InOut();
			initM_InOutLine();
		}

		Services.registerService(IProductAcctDAO.class, productAcctDAO);
		Services.registerService(ITaxBL.class, taxBL);

		new Expectations()
		{{
				productAcctDAO.retrieveActivityForAcct((IContextAware)any, org, product);
				minTimes = 0;
				result = activity;

				taxBL.getTax(
						ctx
						, order
						, -1 // taxCategoryId
						, orderLine.getM_Product_ID()
						, -1 // chargeId
						, order.getDatePromised()
						, order.getDatePromised()
						, order.getAD_Org_ID()
						, order.getM_Warehouse()
						, order.getBill_BPartner_ID()
						, order.getC_BPartner_Location_ID()
						, order.isSOTrx()
						, trxName);
				minTimes = 0;
				result = 3;
		}};
	}

	private void initC_BPartner()
	{
		// ...
		InterfaceWrapperHelper.save(bPartner);
	}

	private void initHandlers()
	{
		// current DB structure for OLHandler
		handler.setC_ILCandHandler_ID(540001);
		handler.setClassname(C_OrderLine_Handler.class.getName());
		handler.setName("Auftragszeilen");
		handler.setTableName(I_C_OrderLine.Table_Name);

		InterfaceWrapperHelper.save(handler);

		// configure olHandler
		olHandler.setHandlerRecord(handler);
	}

	private void initC_Order()
	{
		order.setAD_Org(org);
		order.setBill_BPartner_ID(bPartner.getC_BPartner_ID());
		InterfaceWrapperHelper.save(order);
	}

	private void initC_OrderLine()
	{
		orderLine.setAD_Org(org);
		orderLine.setM_Product(product);

		final BigDecimal qty = new BigDecimal(13);
		orderLine.setQtyEntered(qty);
		orderLine.setQtyOrdered(qty);
		orderLine.setQtyReserved(qty);

		// assume that the process (no direct access in decoupled mode) already set the orderLine qty
		orderLine.setQtyDelivered(qty);
		orderLine.setQtyInvoiced(qty);

		orderLine.setC_Order_ID(order.getC_Order_ID());

		InterfaceWrapperHelper.save(orderLine);
	}

	private void initM_InOut()
	{
		mInOut.setC_Order_ID(order.getC_Order_ID());

		mInOut.setDocStatus(DocAction.STATUS_Completed);
		mInOut.setDocAction(DocAction.ACTION_Close);

		InterfaceWrapperHelper.save(mInOut);
	}

	private void initM_InOutLine()
	{
		mInOutLine.setM_InOut_ID(mInOut.getM_InOut_ID());

		// link to C_OrderLine
		mInOutLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());

		// assume that it's true; our test case at the moment always has it true when dealing with qtyDelivered
		mInOutLine.setIsInvoiced(true);

		// set the orderLine's qty
		mInOutLine.setQtyEntered(orderLine.getQtyEntered());
		mInOutLine.setMovementQty(orderLine.getQtyEntered()); // TODO should use ReceiptSchedule for conversion

		InterfaceWrapperHelper.save(mInOutLine);
	}
}
