package org.adempiere.mm.attributes.countryattribute.impl;

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


import org.adempiere.mm.attributes.api.AttributeAction;
import org.adempiere.mm.attributes.api.impl.ModelAttributeSetInstanceListenerTestHelper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_InOutLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_C_InvoiceLine;

/**
 * Tests:
 * <ul>
 * <li> {@link OrderLineCountryModelAttributeSetInstanceListener}
 * <li> {@link InvoiceLineCountryModelAttributeSetInstanceListener}
 * <li> {@link InOutLineCountryModelAttributeSetInstanceListener}
 * </ul>
 * 
 * @author tsa
 *
 */
public class CountryModelAttributeSetInstanceListenerTest
{
	private ModelAttributeSetInstanceListenerTestHelper helper;
	private I_M_Attribute attr_Country;
	private I_C_Country country1;

	private static final String EXPECT_NoAttributeValue = null;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		helper = new ModelAttributeSetInstanceListenerTestHelper();
		helper.setAttributeAction(AttributeAction.GenerateNew);

		//
		// Create Country attribute
		{
			attr_Country = helper.createM_Attribute_TypeList("Country");
			attr_Country.setAD_JavaClass_ID(helper.createAD_JavaClass(CountryAttributeGenerator.class).getAD_JavaClass_ID());
			InterfaceWrapperHelper.save(attr_Country);

			helper.createM_AttributeUse(helper.product_attributeSet, attr_Country);

			helper.sysConfigBL.setValue(CountryAttributeDAO.SYSCONFIG_CountryAttribute, attr_Country.getM_Attribute_ID(), 0);
		}

		//
		// Create some test countries
		this.country1 = helper.createC_Country("Country1");
	}

	@Test
	public void test_PurchaseOrder()
	{
		final boolean isSOTrx = false;
		I_C_OrderLine orderLine = helper.createOrderLine(isSOTrx, country1);
		new OrderLineCountryModelAttributeSetInstanceListener().modelChanged(orderLine);
		helper.assertAttributeValue(country1.getCountryCode(), orderLine.getM_AttributeSetInstance(), attr_Country);
	}

	/**
	 * Expectation: {@link I_M_Attribute#isAttrDocumentRelevant()} is not affecting other documents than invoice
	 */
	@Test
	public void test_PurchaseOrder_NotAttrDocumentRelevantForInvoice()
	{
		// Set IsAttrDocumentRelevant(for invoice) = false
		helper.setIsAttrDocumentRelevantForInvoice(attr_Country, false);

		test_PurchaseOrder();
	}

	/**
	 * Expectation: ASI is not touched for Sales documents (06790)
	 */
	@Test
	public void test_SalesOrder()
	{
		helper.setIsAttrDocumentRelevantForInvoice(attr_Country, true);

		final boolean isSOTrx = true;
		I_C_OrderLine orderLine = helper.createOrderLine(isSOTrx, country1);
		new OrderLineCountryModelAttributeSetInstanceListener().modelChanged(orderLine);
		helper.assertAttributeValue(EXPECT_NoAttributeValue, orderLine.getM_AttributeSetInstance(), attr_Country);
	}

	@Test
	public void test_PurchaseInvoice()
	{
		helper.setIsAttrDocumentRelevantForInvoice(attr_Country, true);

		final boolean isSOTrx = false;
		I_C_InvoiceLine invoiceLine = helper.createInvoiceLine(isSOTrx, country1);
		new InvoiceLineCountryModelAttributeSetInstanceListener().modelChanged(invoiceLine);
		helper.assertAttributeValue(country1.getCountryCode(), invoiceLine.getM_AttributeSetInstance(), attr_Country);
	}

	/**
	 * Expectation: ASI is not touched because attribute is not document relevant
	 */
	@Test
	public void test_PurchaseInvoice_NotAttrDocumentRelevantForInvoice()
	{
		// Set IsAttrDocumentRelevant(for invoice) = false
		helper.setIsAttrDocumentRelevantForInvoice(attr_Country, false);

		final boolean isSOTrx = false;
		I_C_InvoiceLine invoiceLine = helper.createInvoiceLine(isSOTrx, country1);
		new InvoiceLineCountryModelAttributeSetInstanceListener().modelChanged(invoiceLine);
		helper.assertAttributeValue(EXPECT_NoAttributeValue, invoiceLine.getM_AttributeSetInstance(), attr_Country);
	}

	/**
	 * Expectation: ASI is not touched for Sales documents (06790)
	 */
	@Test
	public void test_SalesInvoices()
	{
		final boolean isSOTrx = true;
		I_C_InvoiceLine invoiceLine = helper.createInvoiceLine(isSOTrx, country1);
		new InvoiceLineCountryModelAttributeSetInstanceListener().modelChanged(invoiceLine);
		helper.assertAttributeValue(EXPECT_NoAttributeValue, invoiceLine.getM_AttributeSetInstance(), attr_Country);
	}

	@Test
	public void test_PurchaseInOut()
	{
		final boolean isSOTrx = false;
		I_M_InOutLine inOutLine = helper.createInOutLine(isSOTrx, country1);
		new InOutLineCountryModelAttributeSetInstanceListener().modelChanged(inOutLine);
		helper.assertAttributeValue(country1.getCountryCode(), inOutLine.getM_AttributeSetInstance(), attr_Country);
	}

	/**
	 * Expectation: ASI is not touched for Sales documents (06790)
	 */
	@Test
	public void test_SalesInOut()
	{
		final boolean isSOTrx = true;
		I_M_InOutLine inOutLine = helper.createInOutLine(isSOTrx, country1);
		new InOutLineCountryModelAttributeSetInstanceListener().modelChanged(inOutLine);
		helper.assertAttributeValue(EXPECT_NoAttributeValue, inOutLine.getM_AttributeSetInstance(), attr_Country);
	}
}
