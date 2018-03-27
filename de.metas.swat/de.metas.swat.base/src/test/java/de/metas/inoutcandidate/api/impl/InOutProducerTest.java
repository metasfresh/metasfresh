package de.metas.inoutcandidate.api.impl;

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

import org.adempiere.util.Services;
import org.junit.Assert;
import org.junit.Test;

import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

public class InOutProducerTest extends ReceiptScheduleTestBase
{
	protected InOutProducer inOutProducer;
	protected InOutGenerateResult receiptGenerateResult;

	@Override
	protected void setup()
	{
		receiptGenerateResult = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true

		final boolean complete = true;
		inOutProducer = new InOutProducer(receiptGenerateResult, complete);
	}

	/**
	 * Same receipt schedules - new receipt not required
	 */
	@Test
	public void isNewReceiptNotRequiredTest()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertFalse(isNewRequired);
	}

	/**
	 * different warehouse - new receipt required
	 */
	@Test
	public void isNewReceiptRequiredTestWarehouse()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertTrue(isNewRequired);
	}

	/**
	 * different bpartner - new receipt required
	 */
	@Test
	public void isNewReceiptRequiredTestBPartner()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertTrue(isNewRequired);
	}

	/**
	 * different date - new receipt required
	 */
	@Test
	public void isNewReceiptRequiredTestDate()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date2, product1_wh1, 10);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertTrue(isNewRequired);
	}

	/**
	 * different product - new receipt not required
	 */
	@Test
	public void isNewReceiptNotRequiredTestProduct()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product2_wh1, 10);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertFalse(isNewRequired);
	}

	/**
	 * different quantity - new receipt not required
	 */
	@Test
	public void isNewReceiptNotRequiredTestQuantity()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 20);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertFalse(isNewRequired);
	}

	/**
	 * different docType - new receipt required
	 */
	@Test
	public void isNewReceiptRequiredTestDocType()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		previousReceiptSchedule.setC_DocType_ID(123);
		receiptSchedule.setC_DocType_ID(100);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertTrue(isNewRequired);
	}

	/**
	 * different location - new receipt required
	 */
	@Test
	public void isNewReceiptRequiredTestBPartnerLocation()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		previousReceiptSchedule.setC_BPartner_Location_ID(12);
		receiptSchedule.setC_BPartner_Location_ID(13);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertTrue(isNewRequired);
	}

	/**
	 * different bpartner override - new receipt required
	 */
	@Test
	public void isNewReceiptRequiredTestBPartnerOverride()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		previousReceiptSchedule.setC_BPartner_Override_ID(111);
		receiptSchedule.setC_BPartner_Override_ID(123);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertTrue(isNewRequired);
	}

	/**
	 * different warehouse override - new receipt required
	 */
	@Test
	public void isNewReceiptRequiredTestWarehouseOverride()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		previousReceiptSchedule.setM_Warehouse_Override_ID(12);
		receiptSchedule.setM_Warehouse_Override_ID(14);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertTrue(isNewRequired);
	}

	/**
	 * different adUser Id - new receipt required
	 */
	@Test
	public void isNewReceiptRequiredTestADUserID()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		previousReceiptSchedule.setAD_User_ID(12);
		receiptSchedule.setAD_User_ID(14);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertTrue(isNewRequired);
	}

	/**
	 * different adUser override Id - new receipt required
	 */
	@Test
	public void isNewReceiptRequiredTestADUserOverrideID()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		previousReceiptSchedule.setAD_User_ID(12);
		receiptSchedule.setAD_User_ID(14);

		previousReceiptSchedule.setAD_User_Override_ID(11);
		receiptSchedule.setAD_User_Override_ID(10);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);

		Assert.assertTrue(isNewRequired);
	}

	/**
	 * different adOrg Id - new receipt required
	 */
	@Test
	public void isNewReceiptRequiredTestADOrgID()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		previousReceiptSchedule.setAD_Org_ID(12);
		receiptSchedule.setAD_Org_ID(13);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertTrue(isNewRequired);
	}

	/**
	 * same adOrg Id - new receipt not required
	 */
	@Test
	public void isNewReceiptNOTRequiredTestADOrgID()
	{
		final I_M_ReceiptSchedule previousReceiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10);
		previousReceiptSchedule.setAD_Org_ID(10);
		receiptSchedule.setAD_Org_ID(10);
		final boolean isNewRequired = inOutProducer.isNewReceiptRequired(previousReceiptSchedule, receiptSchedule);
		Assert.assertFalse(isNewRequired);
	}

}
