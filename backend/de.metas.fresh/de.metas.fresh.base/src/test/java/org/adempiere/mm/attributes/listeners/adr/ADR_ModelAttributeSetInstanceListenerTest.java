package org.adempiere.mm.attributes.listeners.adr;

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


import org.adempiere.mm.attributes.api.AttributeAction;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.api.impl.ADRAttributeDAO;
import org.adempiere.mm.attributes.api.impl.ModelAttributeSetInstanceListenerTestHelper;
import org.adempiere.mm.attributes.spi.impl.ADRAttributeGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOutLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.edi.api.IEDIOLCandBL;
import de.metas.edi.api.impl.EDIOLCandBL;
import de.metas.fresh.model.I_C_BPartner;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;

/**
 * Tests:
 * <ul>
 * <li> {@link OrderLineADRModelAttributeSetInstanceListener}
 * <li> {@link InvoiceLineADRModelAttributeSetInstanceListener}
 * <li> {@link InOutLineADRModelAttributeSetInstanceListener}
 * <li> {@link OrderLineAllocADRModelAttributeSetInstanceListener}
 * </ul>
 * 
 * Test case:
 * <ul>
 * <li>have a product which has the ADR attribute in it's AttributeSet
 * <li>have a BPartner which has an ADR attribute configured
 * <li>create the document with document line (e.g. Order Line, Invoice Line, InOut Line)
 * <li>run the coresponding {@link IModelAttributeSetInstanceListener}
 * <li>check how the document line's ADR Attribute Instance was set
 * <li>
 * </ul>
 * 
 * @author tsa
 *
 */
public class ADR_ModelAttributeSetInstanceListenerTest
{
	/** Marker used when we expect no value to be set for ADR attribute instance */
	private static final String EXPECT_NoAttributeValue = null;
	/** NULL / does not matter */
	private final I_C_Country country_NULL = null;

	private ModelAttributeSetInstanceListenerTestHelper helper;
	private I_M_Attribute attr_ADR;

	/** Document's BPartner */
	private I_C_BPartner bpartner;
	/** {@link IEDIOLCandBL#isEDIInput(I_C_OLCand)} return value */
	private Boolean isEDIInput_ReturnValue = null;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		helper = new ModelAttributeSetInstanceListenerTestHelper();
		bpartner = InterfaceWrapperHelper.create(helper.bpartner, I_C_BPartner.class);

		helper.setAttributeAction(AttributeAction.GenerateNew);

		//
		// Setup ADR attribute
		{
			attr_ADR = helper.createM_Attribute_TypeList("ADR");
			attr_ADR.setAD_JavaClass_ID(helper.createAD_JavaClass(ADRAttributeGenerator.class).getAD_JavaClass_ID());
			InterfaceWrapperHelper.save(attr_ADR);
			helper.createM_AttributeUse(helper.product_attributeSet, attr_ADR);

			helper.sysConfigBL.setValue(ADRAttributeDAO.SYSCONFIG_ADRAttribute, attr_ADR.getM_Attribute_ID(), 0);

			helper.createAD_Ref_List_Items(I_C_BPartner.ADRZertifizierung_L_AD_Reference_ID
					, I_C_BPartner.ADRZertifizierung_L_GMAA
					, I_C_BPartner.ADRZertifizierung_L_GMAA_GMNF
					, I_C_BPartner.ADRZertifizierung_L_GMAA_GMNF_GMVD
					, I_C_BPartner.ADRZertifizierung_L_GMNF
					, I_C_BPartner.ADRZertifizierung_L_GMVD
					//
					);
		}

		//
		// Mock EDIOLCandBL
		final EDIOLCandBL ediOLCandBL = new EDIOLCandBL()
		{
			@Override
			public boolean isEDIInput(I_C_OLCand olCand)
			{
				Check.assumeNotNull(isEDIInput_ReturnValue, "isEDIInput_ReturnValue shall be set before");
				return isEDIInput_ReturnValue;
			};
		};
		Services.registerService(IEDIOLCandBL.class, ediOLCandBL);
	}

	private void setADR_Vendor(final String adrValue)
	{
		bpartner.setIsADRVendor(true);
		bpartner.setFresh_AdRVendorRegion(adrValue);
		InterfaceWrapperHelper.save(bpartner);
	}

	private void setADR_Customer(final String adrValue)
	{
		bpartner.setIsADRCustomer(true);
		bpartner.setFresh_AdRRegion(adrValue);
		InterfaceWrapperHelper.save(bpartner);
	}

	private final I_C_Order_Line_Alloc createC_Order_Line_Alloc(final boolean isSOTrx)
	{
		final I_C_OrderLine orderLine = helper.createOrderLine(isSOTrx, country_NULL);

		final I_C_OLCand olCand = InterfaceWrapperHelper.newInstance(I_C_OLCand.class, orderLine);
		InterfaceWrapperHelper.save(olCand);

		final I_C_Order_Line_Alloc alloc = InterfaceWrapperHelper.newInstance(I_C_Order_Line_Alloc.class, orderLine);
		alloc.setC_OrderLine(orderLine);
		alloc.setC_OLCand(olCand);
		InterfaceWrapperHelper.save(alloc);

		return alloc;
	}

	/**
	 * Expectation: always set the ADR attribute for purchase documents
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08642_ASI_on_shipment%2C_but_not_in_Invoice_%28109350210928%29
	 */
	@Test
	public void test_PurchaseOrder()
	{
		setADR_Vendor(I_C_BPartner.ADRZertifizierung_L_GMAA);
		final boolean isSOTrx = false;

		final I_C_OrderLine line = helper.createOrderLine(isSOTrx, country_NULL);
		new OrderLineADRModelAttributeSetInstanceListener().modelChanged(line);
		//
		final I_M_AttributeSetInstance asi = line.getM_AttributeSetInstance();
		helper.assertAttributeValue(I_C_BPartner.ADRZertifizierung_L_GMAA, asi, attr_ADR);
	}

	/**
	 * Expectation: ADR is not set because we don't deal with an ADR Vendor
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08642_ASI_on_shipment%2C_but_not_in_Invoice_%28109350210928%29
	 */
	@Test
	public void test_PurchaseOrder_NoADRVendor()
	{
		// NOTE: we are configuring the ADR on customer side, which shall not be relevant
		setADR_Customer(I_C_BPartner.ADRZertifizierung_L_GMAA);
		final boolean isSOTrx = false;

		final I_C_OrderLine line = helper.createOrderLine(isSOTrx, country_NULL);
		new OrderLineADRModelAttributeSetInstanceListener().modelChanged(line);
		//
		final I_M_AttributeSetInstance asi = line.getM_AttributeSetInstance();
		helper.assertAttributeValue(EXPECT_NoAttributeValue, asi, attr_ADR);
	}

	/**
	 * Expectation: don't set the ADR on sales documents
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08642_ASI_on_shipment%2C_but_not_in_Invoice_%28109350210928%29
	 */
	@Test
	public void test_SalesOrder()
	{
		setADR_Customer(I_C_BPartner.ADRZertifizierung_L_GMAA);
		final boolean isSOTrx = true;

		final I_C_OrderLine line = helper.createOrderLine(isSOTrx, country_NULL);
		new OrderLineADRModelAttributeSetInstanceListener().modelChanged(line);
		//
		final I_M_AttributeSetInstance asi = line.getM_AttributeSetInstance();
		helper.assertAttributeValue(EXPECT_NoAttributeValue, asi, attr_ADR);
	}

	/**
	 * Expectation: ADR attribute shall be copied even if it's from EDI or not
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08692_EDI_-_ADR_and_other_Attributes_from_PLV_not_in_Orderline_%28102526374063%29
	 */
	@Test
	public void test_Purchase_OrderLineAlloc_EDI()
	{
		setADR_Vendor(I_C_BPartner.ADRZertifizierung_L_GMAA);
		final boolean isSOTrx = false;
		this.isEDIInput_ReturnValue = true;

		final I_C_Order_Line_Alloc alloc = createC_Order_Line_Alloc(isSOTrx);
		new OrderLineAllocADRModelAttributeSetInstanceListener().modelChanged(alloc);
		//
		final I_M_AttributeSetInstance asi = alloc.getC_OrderLine().getM_AttributeSetInstance();
		helper.assertAttributeValue(I_C_BPartner.ADRZertifizierung_L_GMAA, asi, attr_ADR);
	}

	/**
	 * Expectation: ADR attribute shall be copied even if it's from EDI or not
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08692_EDI_-_ADR_and_other_Attributes_from_PLV_not_in_Orderline_%28102526374063%29
	 */
	@Test
	public void test_Purchase_OrderLineAlloc_NotEDI()
	{
		setADR_Vendor(I_C_BPartner.ADRZertifizierung_L_GMAA);
		final boolean isSOTrx = false;
		this.isEDIInput_ReturnValue = false;

		final I_C_Order_Line_Alloc alloc = createC_Order_Line_Alloc(isSOTrx);
		new OrderLineAllocADRModelAttributeSetInstanceListener().modelChanged(alloc);
		//
		final I_M_AttributeSetInstance asi = alloc.getC_OrderLine().getM_AttributeSetInstance();
		helper.assertAttributeValue(I_C_BPartner.ADRZertifizierung_L_GMAA, asi, attr_ADR);
	}

	/**
	 * Expectation: ADR attribute shall <b>not</b> be copied,even though the C_OLCand is from EDI
	 * 
	 * @task dewiki908/mediawiki/index.php/08803_ADR_from_Partner_versus_Pricelist
	 */
	@Test
	public void test_Sales_OrderLineAlloc_EDI()
	{
		setADR_Customer(I_C_BPartner.ADRZertifizierung_L_GMAA);
		final boolean isSOTrx = true;
		this.isEDIInput_ReturnValue = true;

		final I_C_Order_Line_Alloc alloc = createC_Order_Line_Alloc(isSOTrx);
		new OrderLineAllocADRModelAttributeSetInstanceListener().modelChanged(alloc);
		//
		final I_M_AttributeSetInstance asi = alloc.getC_OrderLine().getM_AttributeSetInstance();
		// helper.assertAttributeValue(I_C_BPartner.ADRZertifizierung_L_GMAA, asi, attr_ADR);
		helper.assertAttributeValue(EXPECT_NoAttributeValue, asi, attr_ADR);
	}

	/**
	 * Expectation: ADR attribute shall be copied only if the C_OLCand is from EDI
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08692_EDI_-_ADR_and_other_Attributes_from_PLV_not_in_Orderline_%28102526374063%29
	 */
	@Test
	public void test_Sales_OrderLineAlloc_NotEDI()
	{
		setADR_Customer(I_C_BPartner.ADRZertifizierung_L_GMAA);
		final boolean isSOTrx = true;
		this.isEDIInput_ReturnValue = false;

		final I_C_Order_Line_Alloc alloc = createC_Order_Line_Alloc(isSOTrx);
		new OrderLineAllocADRModelAttributeSetInstanceListener().modelChanged(alloc);
		//
		final I_M_AttributeSetInstance asi = alloc.getC_OrderLine().getM_AttributeSetInstance();
		helper.assertAttributeValue(EXPECT_NoAttributeValue, asi, attr_ADR);
	}

	/**
	 * Expectation: always set the ADR attribute for purchase documents, if ADR is relevant for for invoices
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08642_ASI_on_shipment%2C_but_not_in_Invoice_%28109350210928%29
	 */
	@Test
	public void test_PurchaseInvoice()
	{
		helper.setIsAttrDocumentRelevantForInvoice(attr_ADR, true);
		setADR_Vendor(I_C_BPartner.ADRZertifizierung_L_GMAA);
		final boolean isSOTrx = false;

		final I_C_InvoiceLine line = helper.createInvoiceLine(isSOTrx, country_NULL);
		new InvoiceLineADRModelAttributeSetInstanceListener().modelChanged(line);
		//
		final I_M_AttributeSetInstance asi = line.getM_AttributeSetInstance();
		helper.assertAttributeValue(I_C_BPartner.ADRZertifizierung_L_GMAA, asi, attr_ADR);
	}

	/**
	 * Expectation: always set the ADR attribute for purchase documents, if ADR is relevant for for invoices
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08642_ASI_on_shipment%2C_but_not_in_Invoice_%28109350210928%29
	 */
	@Test
	public void test_PurchaseInvoice_NotAttrDocumentRelevant()
	{
		helper.setIsAttrDocumentRelevantForInvoice(attr_ADR, false);

		setADR_Vendor(I_C_BPartner.ADRZertifizierung_L_GMAA);

		final boolean isSOTrx = false;

		final I_C_InvoiceLine line = helper.createInvoiceLine(isSOTrx, country_NULL);
		new InvoiceLineADRModelAttributeSetInstanceListener().modelChanged(line);
		//
		final I_M_AttributeSetInstance asi = line.getM_AttributeSetInstance();
		helper.assertAttributeValue(EXPECT_NoAttributeValue, asi, attr_ADR);
	}

	/**
	 * Expectation: don't set the ADR on sales documents, even if the ADR attribute is relevant for invoices
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08642_ASI_on_shipment%2C_but_not_in_Invoice_%28109350210928%29
	 */
	@Test
	public void test_SalesInvoice()
	{
		// we expect to not copy the attribute even if it's document relevant
		helper.setIsAttrDocumentRelevantForInvoice(attr_ADR, true);
		setADR_Customer(I_C_BPartner.ADRZertifizierung_L_GMAA);
		final boolean isSOTrx = true;

		final I_C_InvoiceLine line = helper.createInvoiceLine(isSOTrx, country_NULL);
		new InvoiceLineADRModelAttributeSetInstanceListener().modelChanged(line);
		//
		final I_M_AttributeSetInstance asi = line.getM_AttributeSetInstance();
		helper.assertAttributeValue(EXPECT_NoAttributeValue, asi, attr_ADR);
	}

	/**
	 * Test for Material Receipt.
	 * 
	 * Expectation: ADR attribute is copied
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08642_ASI_on_shipment%2C_but_not_in_Invoice_%28109350210928%29
	 */
	@Test
	public void test_PurchaseInOut()
	{
		helper.setIsAttrDocumentRelevantForInvoice(attr_ADR, false);
		setADR_Vendor(I_C_BPartner.ADRZertifizierung_L_GMAA);

		final boolean isSOTrx = false;

		final I_M_InOutLine line = helper.createInOutLine(isSOTrx, country_NULL);
		new InOutLineADRModelAttributeSetInstanceListener().modelChanged(line);
		//
		final I_M_AttributeSetInstance asi = line.getM_AttributeSetInstance();
		helper.assertAttributeValue(I_C_BPartner.ADRZertifizierung_L_GMAA, asi, attr_ADR);

	}

	/**
	 * Test for Material Shipment.
	 * 
	 * Expectation: ADR attribute not copied even if it's a document relevant
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08642_ASI_on_shipment%2C_but_not_in_Invoice_%28109350210928%29
	 */
	@Test
	public void test_SalesInOut()
	{
		helper.setIsAttrDocumentRelevantForInvoice(attr_ADR, true);
		setADR_Customer(I_C_BPartner.ADRZertifizierung_L_GMAA);
		final boolean isSOTrx = true;

		final I_M_InOutLine line = helper.createInOutLine(isSOTrx, country_NULL);
		new InOutLineADRModelAttributeSetInstanceListener().modelChanged(line);
		//
		final I_M_AttributeSetInstance asi = line.getM_AttributeSetInstance();
		helper.assertAttributeValue(EXPECT_NoAttributeValue, asi, attr_ADR);
	}
}
