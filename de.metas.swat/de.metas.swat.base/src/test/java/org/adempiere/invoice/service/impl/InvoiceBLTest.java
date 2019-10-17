package org.adempiere.invoice.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.invoice.service.IInvoiceBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.document.DocTypeId;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

public class InvoiceBLTest extends AbstractICTestSupport
{
	private static IInvoiceBL invoiceBL;
	private static final POJOLookupMap db = POJOLookupMap.get();

	@Test
	public void generateInvoiceTest()
	{
		final I_C_BPartner bPartner = bpartner("1");
		final I_C_Order order = order("1");
		final I_C_OrderLine orderLine = orderLine("1");

		order.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		order.setGrandTotal(new BigDecimal("100.00"));
		db.save(order);

		orderLine.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setLineNetAmt(new BigDecimal("100.00"));
		db.save(orderLine);

		final I_C_Invoice invoice = invoiceBL.createInvoiceFromOrder(
				order,
				(DocTypeId)null,
				SystemTime.asLocalDate(),
				null);

		Assert.assertNotNull(invoice);
	}

	@Before
	public void init()
	{
		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_CH");

		invoiceBL = Services.get(IInvoiceBL.class);
	}
}
