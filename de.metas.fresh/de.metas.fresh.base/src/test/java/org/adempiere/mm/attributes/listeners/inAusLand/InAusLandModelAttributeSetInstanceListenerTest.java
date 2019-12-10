package org.adempiere.mm.attributes.listeners.inAusLand;

/*
 * #%L
 * de.metas.fresh.base
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


import org.adempiere.mm.attributes.api.impl.InAusLandAttributeBL;
import org.adempiere.mm.attributes.api.impl.InAusLandAttributeDAO;
import org.adempiere.mm.attributes.api.impl.ModelAttributeSetInstanceListenerTestHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOutLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.organization.OrgId;
import de.metas.user.UserRepository;
import de.metas.util.Services;

/**
 * Tests for
 * <ul>
 * <li> {@link OrderLineInAusLandModelAttributeSetInstanceListener}
 * <li> {@link InvoiceLineInAusLandModelAttributeSetInstanceListener}
 * <li> {@link InOutLineInAusLandModelAttributeSetInstanceListener}
 * </ul>
 *
 * @author tsa
 *
 */
public class InAusLandModelAttributeSetInstanceListenerTest
{
	private ModelAttributeSetInstanceListenerTestHelper helper;
	private I_M_Attribute attr_InAusLand;
	private I_C_Country country_InLand;
	private I_C_Country country_AusLand;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		helper = new ModelAttributeSetInstanceListenerTestHelper();

		country_InLand = helper.createC_Country("Switzerland", InAusLandAttributeBL.COUNTRY_ID_SCHWEIZ_ID);
		country_AusLand = helper.createC_Country("Romania", -1);

		attr_InAusLand = helper.createM_Attribute_TypeList("In/Aus Land");
		helper.createM_AttributeValue(attr_InAusLand, InAusLandAttributeBL.ATTRIBUTEVALUE_INLAND);
		helper.createM_AttributeValue(attr_InAusLand, InAusLandAttributeBL.ATTRIBUTEVALUE_AUSLAND);
		helper.createM_AttributeUse(helper.product_attributeSet, attr_InAusLand);
		helper.sysConfigBL.setValue(InAusLandAttributeDAO.SYSCONFIG_InAusLandAttribute, attr_InAusLand.getM_Attribute_ID(), ClientId.SYSTEM, OrgId.ANY);
	}

	@Test
	public void test_PurchaseOrder_InLand()
	{
		final String expectedAttributeValue = InAusLandAttributeBL.ATTRIBUTEVALUE_INLAND;
		final boolean isSOTrx = false;
		final I_C_Country country = country_InLand;

		testOrder(expectedAttributeValue, isSOTrx, country);
	}

	@Test
	public void test_PurchaseOrder_AusLand()
	{
		final String expectedAttributeValue = InAusLandAttributeBL.ATTRIBUTEVALUE_AUSLAND;
		final boolean isSOTrx = false;
		final I_C_Country country = country_AusLand;

		testOrder(expectedAttributeValue, isSOTrx, country);
	}

	/**
	 * Expectation: ASI is not touched for Sales documents (06790)
	 */
	@Test
	public void test_SalesOrders()
	{
		final String expectedAttributeValue = null;
		final boolean isSOTrx = true;

		testOrder(expectedAttributeValue, isSOTrx, country_InLand);
		testOrder(expectedAttributeValue, isSOTrx, country_AusLand);
	}

	private void testOrder(final String expectedAttributeValue, final boolean isSOTrx, final I_C_Country country)
	{
		final I_C_OrderLine model = helper.createOrderLine(isSOTrx, country);
		new OrderLineInAusLandModelAttributeSetInstanceListener().modelChanged(model);

		final I_M_AttributeSetInstance asi = model.getM_AttributeSetInstance();
		helper.assertAttributeValue(expectedAttributeValue, asi, attr_InAusLand);
	}

	@Test
	public void test_PurchaseInvoice_InLand()
	{
		helper.setIsAttrDocumentRelevantForInvoice(attr_InAusLand, true);

		final String expectedAttributeValue = InAusLandAttributeBL.ATTRIBUTEVALUE_INLAND;
		final boolean isSOTrx = false;
		final I_C_Country country = country_InLand;

		testInvoice(expectedAttributeValue, isSOTrx, country);
	}

	@Test
	public void test_PurchaseInvoice_AusLand()
	{
		helper.setIsAttrDocumentRelevantForInvoice(attr_InAusLand, true);

		final String expectedAttributeValue = InAusLandAttributeBL.ATTRIBUTEVALUE_AUSLAND;
		final boolean isSOTrx = false;
		final I_C_Country country = country_AusLand;

		testInvoice(expectedAttributeValue, isSOTrx, country);
	}

	/**
	 * Expectation: ASI is not touched because attribute is not document relevant
	 */
	@Test
	public void test_PurchaseInvoice_AusLand_NotAttrDocumentRelevant()
	{
		helper.setIsAttrDocumentRelevantForInvoice(attr_InAusLand, false);

		final String expectedAttributeValue = null;
		final boolean isSOTrx = false;

		testInvoice(expectedAttributeValue, isSOTrx, country_InLand);
		testInvoice(expectedAttributeValue, isSOTrx, country_AusLand);
	}

	/**
	 * Expectation: ASI is not touched for Sales documents (06790)
	 */
	@Test
	public void test_SalesInvoices()
	{
		helper.setIsAttrDocumentRelevantForInvoice(attr_InAusLand, true);

		final String expectedAttributeValue = null;
		final boolean isSOTrx = true;

		testInvoice(expectedAttributeValue, isSOTrx, country_InLand);
		testInvoice(expectedAttributeValue, isSOTrx, country_AusLand);
	}

	private void testInvoice(final String expectedAttributeValue, final boolean isSOTrx, final I_C_Country country)
	{
		final I_C_InvoiceLine model = helper.createInvoiceLine(isSOTrx, country);
		new InvoiceLineInAusLandModelAttributeSetInstanceListener().modelChanged(model);

		final I_M_AttributeSetInstance asi = model.getM_AttributeSetInstance();
		helper.assertAttributeValue(expectedAttributeValue, asi, attr_InAusLand);
	}

	@Test
	public void test_PurchaseInOut_InLand()
	{
		final String expectedAttributeValue = InAusLandAttributeBL.ATTRIBUTEVALUE_INLAND;
		final boolean isSOTrx = false;
		final I_C_Country country = country_InLand;

		testInOut(expectedAttributeValue, isSOTrx, country);
	}

	@Test
	public void test_PurchaseInOut_AusLand()
	{
		final String expectedAttributeValue = InAusLandAttributeBL.ATTRIBUTEVALUE_AUSLAND;
		final boolean isSOTrx = false;
		final I_C_Country country = country_AusLand;

		testInOut(expectedAttributeValue, isSOTrx, country);
	}

	/**
	 * Expectation: ASI is not touched for Sales documents (06790)
	 */
	@Test
	public void test_SalesInOuts()
	{
		final String expectedAttributeValue = null;
		final boolean isSOTrx = true;

		testInOut(expectedAttributeValue, isSOTrx, country_InLand);
		testInOut(expectedAttributeValue, isSOTrx, country_AusLand);
	}

	private void testInOut(final String expectedAttributeValue, final boolean isSOTrx, final I_C_Country country)
	{
		final I_M_InOutLine model = helper.createInOutLine(isSOTrx, country);
		new InOutLineInAusLandModelAttributeSetInstanceListener().modelChanged(model);

		final I_M_AttributeSetInstance asi = model.getM_AttributeSetInstance();
		helper.assertAttributeValue(expectedAttributeValue, asi, attr_InAusLand);
	}

}
