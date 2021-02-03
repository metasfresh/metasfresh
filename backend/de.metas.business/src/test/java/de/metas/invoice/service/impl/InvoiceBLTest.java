package de.metas.invoice.service.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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

public class InvoiceBLTest
{
	private static IInvoiceBL invoiceBL;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new CurrencyRepository());

		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_CH");

		invoiceBL = Services.get(IInvoiceBL.class);
	}

	private I_C_DocType createSalesOrderDocType()
	{
		final I_C_DocType orderDocType = docType(X_C_DocType.DOCBASETYPE_SalesOrder, null);
		final I_C_DocType invoiceDocType = docType(X_C_DocType.DOCBASETYPE_ARInvoice, null);
		orderDocType.setC_DocTypeInvoice_ID(invoiceDocType.getC_DocType_ID());
		InterfaceWrapperHelper.save(orderDocType);

		return orderDocType;
	}

	protected I_C_DocType docType(final String baseType, final String subType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(baseType);
		docType.setDocSubType(subType);
		saveRecord(docType);
		return docType;
	}

	public I_C_BPartner bpartner(final String bpValue)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setAD_Org_ID(OrgId.ANY.getRepoId());
		bpartner.setValue(bpValue);
		bpartner.setName(bpValue);
		saveRecord(bpartner);

		return bpartner;
	}

	public I_C_Order order(final String orderDocNo)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocumentNo(orderDocNo);
		final I_C_DocType orderDocType = createSalesOrderDocType();
		order.setC_DocType_ID(orderDocType.getC_DocType_ID());
		saveRecord(order);
		return order;
	}

	public I_C_OrderLine orderLine(final String orderLineDescription)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setDescription(orderLineDescription);

		// final PlainCurrencyBL currencyConversionBL = (PlainCurrencyBL)Services.get(ICurrencyBL.class);
		// orderLine.setC_Currency_ID(currencyConversionBL.getBaseCurrency(Env.getCtx()).getId().getRepoId());
		saveRecord(orderLine);

		return orderLine;
	}

	@Test
	public void createInvoiceFromOrder()
	{
		final I_C_BPartner bPartner = bpartner("1");
		final I_C_Order order = order("1");
		final I_C_OrderLine orderLine = orderLine("1");

		order.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		order.setGrandTotal(new BigDecimal("100.00"));
		saveRecord(order);

		orderLine.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setLineNetAmt(new BigDecimal("100.00"));
		saveRecord(orderLine);

		final I_C_Invoice invoice = invoiceBL.createInvoiceFromOrder(
				order,
				(DocTypeId)null,
				SystemTime.asLocalDate(),
				null);

		assertThat(invoice).isNotNull();
	}
}
