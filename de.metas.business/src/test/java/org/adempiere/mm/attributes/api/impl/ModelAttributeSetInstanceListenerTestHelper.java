package org.adempiere.mm.attributes.api.impl;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeAction;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.junit.Assert;
import org.junit.Ignore;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_M_Product;
import de.metas.organization.OrgId;
import de.metas.util.Services;

/**
 * Base context and helpers for all {@link IModelAttributeSetInstanceListener}s which are about updating the document line's ASI.
 *
 * @author tsa
 *
 */

@Ignore
public class ModelAttributeSetInstanceListenerTestHelper extends AttributesTestHelper
{
	// services
	public final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	public final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public final I_M_Product product;
	public final I_M_AttributeSet product_attributeSet;

	public final I_C_BPartner bpartner;

	public ModelAttributeSetInstanceListenerTestHelper()
	{
		super();

		product_attributeSet = createM_AttributeSet();
		product = createM_Product("Product", product_attributeSet);

		this.bpartner = InterfaceWrapperHelper.create(ctx, I_C_BPartner.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(bpartner);

		setAttributeAction(AttributeAction.Error);
	}

	public void setAttributeAction(AttributeAction attributeAction)
	{
		sysConfigBL.setValue(AttributesBL.SYSCONFIG_AttributeAction, attributeAction.getCode(), ClientId.SYSTEM, OrgId.ANY);
	}

	public void setIsAttrDocumentRelevantForInvoice(final I_M_Attribute attribute, final boolean isAttrDocumentRelevant)
	{
		attribute.setIsAttrDocumentRelevant(isAttrDocumentRelevant);
		InterfaceWrapperHelper.save(attribute);
	}

	public I_M_Product createM_Product(final String name, final I_M_AttributeSet as)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product.setValue(name);
		product.setName(name);
		product.setM_AttributeSet(as);
		InterfaceWrapperHelper.save(product);
		return product;
	}

	private I_C_BPartner_Location createC_BPartner_Location(final I_C_Country country)
	{
		final I_C_Location location = InterfaceWrapperHelper.create(ctx, I_C_Location.class, ITrx.TRXNAME_None);
		if (country != null)
		{
			location.setC_Country_ID(country.getC_Country_ID());
		}
		InterfaceWrapperHelper.save(location);

		final I_C_BPartner_Location bpl = InterfaceWrapperHelper.create(ctx, I_C_BPartner_Location.class, ITrx.TRXNAME_None);
		bpl.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpl.setC_Location_ID(location.getC_Location_ID());
		InterfaceWrapperHelper.save(bpl);
		return bpl;
	}

	public final I_C_InvoiceLine createInvoiceLine(final boolean isSOTrx, final I_C_Country bplCountry)
	{
		final I_C_BPartner_Location bpl = createC_BPartner_Location(bplCountry);

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice.setIsSOTrx(isSOTrx);
		invoice.setC_BPartner_ID(bpl.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(bpl.getC_BPartner_Location_ID());
		InterfaceWrapperHelper.save(invoice);

		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class, invoice);
		invoiceLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceLine.setM_Product_ID(product.getM_Product_ID());
		InterfaceWrapperHelper.save(invoiceLine);
		return invoiceLine;
	}

	public final I_C_OrderLine createOrderLine(final boolean isSOTrx, final I_C_Country bplCountry)
	{
		final I_C_BPartner_Location bpl = createC_BPartner_Location(bplCountry);

		final I_C_Order order = InterfaceWrapperHelper.create(ctx, I_C_Order.class, ITrx.TRXNAME_None);
		order.setIsSOTrx(isSOTrx);
		order.setC_BPartner_ID(bpl.getC_BPartner_ID());
		order.setC_BPartner_Location_ID(bpl.getC_BPartner_Location_ID());
		InterfaceWrapperHelper.save(order);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class, order);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Product_ID(product.getM_Product_ID());
		InterfaceWrapperHelper.save(orderLine);
		return orderLine;
	}

	public final I_M_InOutLine createInOutLine(final boolean isSOTrx, final I_C_Country bplCountry)
	{
		final I_C_BPartner_Location bpl = createC_BPartner_Location(bplCountry);

		final I_M_InOut inout = InterfaceWrapperHelper.create(ctx, I_M_InOut.class, ITrx.TRXNAME_None);
		inout.setIsSOTrx(isSOTrx);
		inout.setC_BPartner_ID(bpl.getC_BPartner_ID());
		inout.setC_BPartner_Location_ID(bpl.getC_BPartner_Location_ID());
		InterfaceWrapperHelper.save(inout);

		final I_M_InOutLine inoutLine = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class, inout);
		inoutLine.setM_InOut(inout);
		inoutLine.setM_Product_ID(product.getM_Product_ID());
		InterfaceWrapperHelper.save(inoutLine);
		return inoutLine;
	}

	public void assertAttributeValue(
			final String expectedAttributeValue,
			@Nullable final I_M_AttributeSetInstance asi,
			final I_M_Attribute attribute)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(asi == null ? -1 : asi.getM_AttributeSetInstance_ID());
		final I_M_AttributeInstance ai = attributeDAO.retrieveAttributeInstance(asiId, AttributeId.ofRepoId(attribute.getM_Attribute_ID()));
		if (expectedAttributeValue == null)
		{
			Assert.assertNull("No AI expected", ai);
		}
		else
		{
			Assert.assertNotNull("AI expected", ai);
			Assert.assertEquals("Invalid attribute value", expectedAttributeValue, ai.getValue());
		}
	}

	public I_C_Country createC_Country(final String countryCode)
	{
		final int countryId = -1;
		return createC_Country(countryCode, countryId);
	}

	public I_C_Country createC_Country(final String countryCode, final int countryId)
	{
		final I_C_Country country = InterfaceWrapperHelper.create(ctx, I_C_Country.class, ITrx.TRXNAME_None);
		country.setName(countryCode);
		country.setCountryCode(countryCode);
		if (countryId > 0)
		{
			country.setC_Country_ID(countryId);
		}
		InterfaceWrapperHelper.save(country);
		return country;

	}
}
