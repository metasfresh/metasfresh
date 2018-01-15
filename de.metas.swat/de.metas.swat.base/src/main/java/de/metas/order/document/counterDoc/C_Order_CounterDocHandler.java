package de.metas.order.document.counterDoc;

import javax.annotation.concurrent.Immutable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_DocTypeCounter;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MDocTypeCounter;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.slf4j.Logger;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.spi.CounterDocumentHandlerAdapter;
import de.metas.document.spi.ICounterDocHandler;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Immutable
public class C_Order_CounterDocHandler extends CounterDocumentHandlerAdapter
{
	private static final transient Logger logger = LogManager.getLogger(C_Order_CounterDocHandler.class);

	public static final ICounterDocHandler instance = new C_Order_CounterDocHandler();

	private C_Order_CounterDocHandler()
	{
	}

	/**
	 * Code taken from {@link org.compiere.model.MOrder}.
	 *
	 * @return <code>true</code> if <code>Ref_Order_ID() > 0</code>.
	 */
	@Override
	public boolean isCounterDocument(final IDocument document)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(document, I_C_Order.class);
		return order.getRef_Order_ID() > 0;
	}

	/**
	 * Creates a counter document for an order. The counter document is also processed, if there is a {@link I_C_DocTypeCounter} with a <code>DocAction</code> configured.
	 * <p>
	 * This implementation partially uses legacy code. I didn't yet get to refactor/remove/replace it all.
	 */
	@Override
	public IDocument createCounterDocument(final IDocument document)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(document, I_C_Order.class);
		final MOrder orderPO = LegacyAdapters.convertToPO(order);

		final I_C_DocType counterDocType = retrieveCounterDocTypeOrNull(document);

		final I_AD_Org counterOrg = retrieveCounterOrgOrNull(document);

		final de.metas.adempiere.model.I_C_Order counterOrder = InterfaceWrapperHelper.newInstance(de.metas.adempiere.model.I_C_Order.class, document.getCtx());
		final MOrder counterOrderPO = LegacyAdapters.convertToPO(counterOrder);

		counterOrder.setAD_Org(counterOrg); // 09700

		//
		Services.get(IOrderBL.class).setDocTypeTargetIdAndUpdateDescription(order, counterDocType.getC_DocType_ID());
		counterOrder.setIsSOTrx(counterDocType.isSOTrx());

		// the new order needs to figure out the pricing by itself
		counterOrder.setM_PricingSystem(null);
		counterOrder.setM_PriceList(null);

		counterOrder.setDateOrdered(order.getDateOrdered());
		counterOrder.setDateAcct(order.getDateAcct());
		counterOrder.setDatePromised(order.getDatePromised());

		counterOrder.setRef_Order_ID(order.getC_Order_ID());

		final I_C_BPartner counterBP = retrieveCounterPartnerOrNull(document);
		counterOrderPO.setBPartner(counterBP);

		final I_M_Warehouse counterWarehouse = Services.get(IWarehouseAdvisor.class).evaluateOrderWarehouse(counterOrder);
		counterOrder.setM_Warehouse(counterWarehouse);

		// References (should not be required)
		counterOrder.setSalesRep_ID(order.getSalesRep_ID());
		InterfaceWrapperHelper.save(counterOrder);

		// copy the order lines
		final boolean counter = true;
		final boolean copyASI = true;
		counterOrderPO.copyLinesFrom(orderPO, counter, copyASI);

		// Update copied lines
		final boolean requery = true;
		final MOrderLine[] counterLines = counterOrderPO.getLines(requery, null);
		for (int i = 0; i < counterLines.length; i++)
		{
			final MOrderLine counterLine = counterLines[i];
			counterLine.setOrder(counterOrderPO);	// copies header values (BP, etc.)
			counterLine.setPrice();
			counterLine.setTax();
			InterfaceWrapperHelper.save(counterLine);
		}
		logger.debug(counterOrder.toString());

		// Document Action
		final MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(document.getCtx(), order.getC_DocType_ID());
		if (counterDT != null)
		{
			if (counterDT.getDocAction() != null)
			{
				counterOrder.setDocAction(counterDT.getDocAction());
				Services.get(IDocumentBL.class).processEx(counterOrder, counterDT.getDocAction(),
						null); // not expecting a particular docStatus (e.g. for prepay orders, it might be "waiting to payment")
			}
		}
		return counterOrderPO;
	}

	@Override
	public String toString()
	{
		return "C_Order_CounterDocHandler[]";
	}

}
