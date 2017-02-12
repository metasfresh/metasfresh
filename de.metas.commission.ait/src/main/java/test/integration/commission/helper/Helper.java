package test.integration.commission.helper;

/*
 * #%L
 * de.metas.commission.ait
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_Period;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPeriod;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.junit.Assert;

import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_Invoice;
import de.metas.commission.interfaces.I_M_ProductPrice;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.process.CalculateCommission;
import de.metas.commission.process.InvoiceCommission;
import de.metas.commission.process.WriteCommissionAccount;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionTypeBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.util.CommissionConstants;
import de.metas.interfaces.I_C_OrderLine;

public class Helper extends de.metas.adempiere.ait.helper.HelperDelegator
{

	public static final String C_ADV_COMMISSION_CONDITION_ID = "C_AdvCommissionCondition_ID";
	public static final String C_ADV_COM_SYSTEM_ID = "C_AdvComSystem_ID";

	public Helper(final IHelper parent)
	{
		super(parent);

		getConfig().setCustomParam(C_ADV_COM_SYSTEM_ID, getConfig().getPropertyAsInt(C_ADV_COM_SYSTEM_ID));
		getConfig().setCustomParam(C_ADV_COMMISSION_CONDITION_ID, getConfig().getPropertyAsInt(C_ADV_COMMISSION_CONDITION_ID));
	}

	/**
	 * Creates/Updates a {@link I_M_ProductPrice} with the given {@code price} as both price and commission point.
	 */
	@Override
	public I_M_ProductPrice setProductPrice(
			final org.compiere.model.I_M_PriceList pl,
			final String productValue,
			final BigDecimal price,
			final int C_TaxCategory_ID)
	{
		return setProductPrice(pl, productValue, price, price, C_TaxCategory_ID);
	}

	/**
	 * 
	 * @param pl
	 * @param productValue
	 * @param price
	 * @param commissionPoints
	 * @return
	 */
	public I_M_ProductPrice setProductPrice(
			final org.compiere.model.I_M_PriceList pl,
			final String productValue,
			final BigDecimal price,
			final BigDecimal commissionPoints,
			final int C_TaxCategory_ID)
	{
		final I_M_ProductPrice comPP =
				InterfaceWrapperHelper.create(
						super.setProductPrice(pl, productValue, price, C_TaxCategory_ID),
						de.metas.commission.interfaces.I_M_ProductPrice.class);
		
		comPP.setCommissionPoints(commissionPoints);
		InterfaceWrapperHelper.save(comPP);
		return comPP;
	}

	@Override
	public void createOrder_checkOrderLine(
			I_C_OrderLine orderLine,
			BigDecimal priceActualBD,
			de.metas.adempiere.model.I_M_ProductPrice pp)
	{
		super.createOrder_checkOrderLine(orderLine, priceActualBD, pp);

		de.metas.commission.interfaces.I_C_OrderLine comOrderLine = InterfaceWrapperHelper.create(orderLine,
				de.metas.commission.interfaces.I_C_OrderLine.class);
		de.metas.commission.interfaces.I_M_ProductPrice comPP = InterfaceWrapperHelper.create(pp,
				de.metas.commission.interfaces.I_M_ProductPrice.class);

		Assert.assertEquals("order line commision points not match - " + orderLine,
				comPP.getCommissionPoints(), comOrderLine.getCommissionPoints());
	}

	public void runProcess_WriteCommissionAccount()
	{
		runProcess_WriteCommissionAccount(getTrxName());
	}

	public void runProcess_WriteCommissionAccount(final String trxName)
	{
		mkProcessHelper()
				.setTrxName(trxName)
				.setProcessClass(WriteCommissionAccount.class)
				.run();

		// Asserting that the processing was successful
		assertAllFactsProcessed();
	}

	/**
	 * Runs the CalculateCommission process with the period of the given calendar and date.
	 * 
	 * @param calendar
	 * @param date
	 */
	public void runProcess_CalculateCommission(final I_C_Calendar calendar, final Timestamp date)
	{
		final MPeriod period = MPeriod.findByCalendar(getCtx(), date, calendar.getC_Calendar_ID(), getTrxName());

		mkProcessHelper()
				.setProcessClass(CalculateCommission.class)
				.setParameter(I_C_Period.COLUMNNAME_C_Year_ID, period.getC_Year_ID())
				.setParameter(I_C_Period.COLUMNNAME_C_Period_ID, period.getC_Period_ID())
				.run();
	}

	/**
	 * Runs the InvoiceCommission process with the period of the given calendar and date and with the given date as 'dateAcct'.
	 * 
	 * @param calendar
	 * @param date
	 */
	public void runProcess_InvoiceCommission(final I_C_Calendar calendar, final Timestamp date)
	{
		final MPeriod period = MPeriod.findByCalendar(getCtx(), date, calendar.getC_Calendar_ID(), getTrxName());

		mkProcessHelper()
				.setProcessClass(InvoiceCommission.class)
				.setParameter(I_C_Period.COLUMNNAME_C_Year_ID, period.getC_Year_ID())
				.setParameter(I_C_Period.COLUMNNAME_C_Period_ID, period.getC_Period_ID())
				.run();
	}

	public SponsorCondHelper mkSponsorCondHelper()
	{
		return new SponsorCondHelper(this);
	}

	/**
	 * Asserts that
	 * <ul>
	 * <li>there are C_AdvCommissionFactCand records referencing the given po
	 * <li>that at least one of them is still unprocessed
	 * <li>none of them has an error
	 * </ul>
	 * 
	 * @param referencedPO
	 */
	public void assertFactCandsUnprocessed(final PO referencedPO)
	{
		final List<MCAdvCommissionFactCand> candsForPO = MCAdvCommissionFactCand.retrieveForPO(referencedPO, null);
		assertFalse(candsForPO.isEmpty());

		boolean atLeastOneUnprocessed = false;

		for (final MCAdvCommissionFactCand cand : candsForPO)
		{
			if (!cand.isSubsequentProcessingDone())
			{
				atLeastOneUnprocessed = true;
			}

			assertFalse(cand.isError());
		}
		assertTrue(atLeastOneUnprocessed);
	}

	public void assertAllFactsProcessed()
	{
		final String wc = I_C_AdvCommissionFactCand.COLUMNNAME_IsSubsequentProcessingDone + "='N' OR " + I_C_AdvCommissionFactCand.COLUMNNAME_IsImmediateProcessingDone + "='N'";

		final List<I_C_AdvCommissionFactCand> unprocessed =
				new Query(getCtx(), I_C_AdvCommissionFactCand.Table_Name, wc, getTrxName())
						.setClient_ID()
						.setOnlyActiveRecords(true)
						.setOrderBy(I_C_AdvCommissionFactCand.COLUMNNAME_C_AdvCommissionFactCand_ID)
						.list(I_C_AdvCommissionFactCand.class);

		assertThat("No C_AdvCommissionFactCand records should be unprocessed", unprocessed.size(), equalTo(0));
	}

	/**
	 * Asserts that currently there is no unprocessed C_advCommissionFactCand record with Error=Y' that references the given po.
	 * 
	 * @param referencedPO
	 */
	public void assertNoFactCandsUnprocessedWithError(final PO referencedPO)
	{
		final List<MCAdvCommissionFactCand> candsForPO = MCAdvCommissionFactCand.retrieveForPO(referencedPO, null);
		for (final MCAdvCommissionFactCand cand : candsForPO)
		{
			if (!cand.isImmediateProcessingDone() || !cand.isSubsequentProcessingDone())
			{
				assertFalse(cand + " for " + referencedPO + " has Error=Y", cand.isError());
			}
		}
	}

	/**
	 * Asserts that there are C_advCommissionFactCand record referencing the given po and that they all have been successfully processed.
	 * 
	 * @param referencedPO
	 */
	public void assertFactCandsForPOProcessed(final Object referencedPOModel)
	{
		Assert.assertNotNull("referencedPOModel is null", referencedPOModel);

		PO referencedPO = InterfaceWrapperHelper.getPO(referencedPOModel);
		Assert.assertNotNull("No PO found for " + referencedPOModel, referencedPO);

		final List<MCAdvCommissionFactCand> candsForPO = MCAdvCommissionFactCand.retrieveForPO(referencedPO, null);

		for (final MCAdvCommissionFactCand cand : candsForPO)
		{
			assertTrue(cand + " ImmediateProcessingDone=Y", cand.isImmediateProcessingDone());
			assertTrue(cand + " SubsequentProcessingDone=N", cand.isSubsequentProcessingDone());
			assertFalse(cand + " Error=Y", cand.isError());
		}
	}

	/**
	 * Asserts that Points_Predicted != 0, while Points_ToCalculate, Points_Calculated and AmtToPay is zero.
	 * 
	 * @param instance
	 */
	public void assertSumPredictedOnly(final IAdvComInstance instance)
	{
		assertTrue(instance + " has Points_Predicted=" + instance.getPoints_Predicted(),
				instance.getPoints_Predicted().signum() != 0);
		assertTrue(instance + " has Points_ToCalculate=" + instance.getPoints_ToCalculate(),
				instance.getPoints_ToCalculate().signum() == 0);
		assertTrue(instance + " has Points_Calculated=" + instance.getPoints_Calculated(),
				instance.getPoints_Calculated().signum() == 0);
		assertTrue(instance + " has AmtToPay=" + instance.getAmtToPay(),
				instance.getAmtToPay().signum() == 0);
	}

	/**
	 * Asserts that Points_ToCalculate != 0, while Points_Predicted, Points_Calculated and AmtToPay is zero.
	 * 
	 * @param instance
	 */
	public void assertSumToCalculateOnly(final IAdvComInstance instance)
	{
		assertTrue(instance + " has Points_Predicted=" + instance.getPoints_Predicted(),
				instance.getPoints_Predicted().signum() == 0);
		assertTrue(instance + " has Points_ToCalculate=" + instance.getPoints_ToCalculate(),
				instance.getPoints_ToCalculate().signum() != 0);
		assertTrue(instance + " has Points_Calculated=" + instance.getPoints_Calculated(),
				instance.getPoints_Calculated().signum() == 0);
		assertTrue(instance + " has AmtToPay=" + instance.getAmtToPay(),
				instance.getAmtToPay().signum() == 0);
	}

	public void checkOrder(final IAdvComInstance instance)
	{
		assertEquals(instance + " doesn't refer to C_OrderLine", I_C_OrderLine.Table_Name, instance.getAD_Table().getTableName());

		final List<MCAdvCommissionFact> facts = MCAdvCommissionFact
				.mkQuery(getCtx(), getTrxName())
				.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
				.list();

		assertTrue("There should be at least fact for " + instance, facts.size() >= 1);

		assertEquals("1st fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(0).getStatus());
		assertTrue("1st fact of " + instance + " has wrong type", Check.isEmpty(facts.get(0).getFactType()));
		assertFalse("1st fact of " + instance + " is counter entry", facts.get(0).isCounterEntry());
		assertEquals("1st fact of " + instance + " has wrong table", facts.get(0).getAD_Table().getTableName(), I_C_OrderLine.Table_Name);

		final MOrderLine ol = (MOrderLine)facts.get(0).retrievePO();
		assertEquals("1st fact-PO of " + instance + " doesn't refer to instance trigger", ol.getC_OrderLine_ID(), instance.getRecord_ID());

	}

	public void checkPrepayOrderWithInsufficientPayment(final IAdvComInstance instance)
	{
		checkOrder(instance);

		final List<MCAdvCommissionFact> facts = MCAdvCommissionFact
				.mkQuery(getCtx(), getTrxName())
				.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
				.list();

		assertEquals("2nd fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(1).getStatus());
		assertEquals("2nd fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Zahlungszuordnung, facts.get(1).getFactType());
		assertFalse("2nd fact of " + instance + " is a counter entry", facts.get(1).isCounterEntry());
		assertEquals("2nd fact of " + instance + " has wrong table", facts.get(1).getAD_Table().getTableName(), I_C_AllocationLine.Table_Name);

	}

	public void checkPrepayOrderWithSufficientPayment(IAdvComInstance instance)
	{
		checkOrder(instance);

		final List<MCAdvCommissionFact> facts = MCAdvCommissionFact
				.mkQuery(getCtx(), getTrxName())
				.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
				.list();

		final MOrderLine ol = (MOrderLine)facts.get(0).retrievePO();

		assertEquals("2nd fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(1).getStatus());
		assertEquals("2nd fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Zahlungszuordnung, facts.get(1).getFactType());
		assertTrue("2nd fact of " + instance + " is not counter entry", facts.get(1).isCounterEntry());
		assertEquals("2nd fact of " + instance + " has wrong table", facts.get(1).getAD_Table().getTableName(), I_C_OrderLine.Table_Name);
		assertEquals("1st and 2nd fact of " + instance + " refer to different POs", ol.getC_OrderLine_ID(), facts.get(1).getRecord_ID());

		assertEquals("3rd fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_ZuBerechnen, facts.get(2).getStatus());
		assertEquals("3rd fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Zahlungszuordnung, facts.get(2).getFactType());
		assertFalse("3rd fact of " + instance + " is counter entry", facts.get(2).isCounterEntry());
		assertEquals("3rd fact of " + instance + " is no allocation line", facts.get(2).getAD_Table().getTableName(), I_C_AllocationLine.Table_Name);
	}

	public void checkOrderAndUnPaidInvoice(final IAdvComInstance instance)
	{
		checkOrder(instance);

		final List<MCAdvCommissionFact> facts = MCAdvCommissionFact
				.mkQuery(getCtx(), getTrxName())
				.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
				.list();

		final MOrderLine ol = (MOrderLine)facts.get(0).retrievePO();

		assertEquals("2nd fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(1).getStatus());
		assertEquals("2nd fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Umbuchung, facts.get(1).getFactType());
		assertTrue("2nd fact of " + instance + " is not counter entry", facts.get(1).isCounterEntry());
		assertEquals("2nd fact of " + instance + " has wrong table", facts.get(1).getAD_Table().getTableName(), I_C_OrderLine.Table_Name);
		assertEquals("1st and 2nd fact of " + instance + " refer to different POs", ol.getC_OrderLine_ID(), facts.get(1).getRecord_ID());

		assertEquals("3rd fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(2).getStatus());
		assertEquals("3rd fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Umbuchung, facts.get(2).getFactType());
		assertFalse("3rd fact of " + instance + " is counter entry", facts.get(2).isCounterEntry());
		assertEquals("3rd fact of " + instance + " has wrong table", facts.get(2).getAD_Table().getTableName(), I_C_InvoiceLine.Table_Name);

		final MInvoiceLine il = (MInvoiceLine)facts.get(2).retrievePO();
		assertTrue("ol from 1st and 2nd fact of " + instance + " is not referenced by il of 3rd fact", il.getC_OrderLine_ID() == ol.getC_OrderLine_ID());
	}

	public void checkOrderAndUnpaidInvoiceReversed(final IAdvComInstance instance)
	{
		checkOrderAndUnPaidInvoice(instance);

		final List<MCAdvCommissionFact> facts = MCAdvCommissionFact
				.mkQuery(getCtx(), getTrxName())
				.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
				.list();

		assertTrue("There should be at least six facts for " + instance, facts.size() >= 6);

		assertEquals("4th fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(3).getStatus());
		assertEquals("4th fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig, facts.get(3).getFactType());
		assertTrue("4th fact of " + instance + " is not counter entry", facts.get(3).isCounterEntry());
		assertEquals("4th fact of " + instance + " is no invoice line", facts.get(3).getAD_Table().getTableName(), I_C_InvoiceLine.Table_Name);

		final MInvoiceLine ilOrig = (MInvoiceLine)facts.get(3).retrievePO();

		assertEquals("5th fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(4).getStatus());
		assertEquals("5th fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig, facts.get(4).getFactType());
		assertFalse("5th fact of " + instance + " is counter entry", facts.get(4).isCounterEntry());
		assertEquals("5th fact of " + instance + " is no invoice line", facts.get(4).getAD_Table().getTableName(), I_C_InvoiceLine.Table_Name);

		final MInvoiceLine ilReversed = (MInvoiceLine)facts.get(4).retrievePO();

		assertEquals("6th fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(5).getStatus());
		assertEquals("6th fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig, facts.get(5).getFactType());
		assertFalse("6th fact of " + instance + " is  counter entry", facts.get(5).isCounterEntry());
		assertEquals("6th fact of " + instance + " is no order line ", facts.get(5).getAD_Table().getTableName(), I_C_OrderLine.Table_Name);

		assertTrue("1st and 6th fact or " + instance + " refer to differen order lines", facts.get(5).getRecord_ID() == facts.get(0).getRecord_ID());

		final MOrderLine ol = (MOrderLine)facts.get(5).retrievePO();
		assertTrue("Facts of " + instance + " refer to inconsistent POs", ilOrig.getC_OrderLine_ID() == ol.getC_OrderLine_ID());
		assertTrue("Facts of " + instance + " refer to inconsistent POs", ilReversed.getC_OrderLine_ID() == ol.getC_OrderLine_ID());
	}

	public void checkOrderAndPaidInvoice(IAdvComInstance instance)
	{
		checkOrderAndUnPaidInvoice(instance);

		final List<MCAdvCommissionFact> facts = MCAdvCommissionFact
				.mkQuery(getCtx(), getTrxName())
				.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
				.list();

		assertTrue("There should be at least five facts for " + instance, facts.size() >= 5);

		assertEquals("4th fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(3).getStatus());
		assertEquals("4th fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Zahlungszuordnung, facts.get(3).getFactType());
		assertTrue("4th fact of " + instance + " is not counter entry", facts.get(3).isCounterEntry());
		assertEquals("4th fact of " + instance + " is no invoice line", facts.get(3).getAD_Table().getTableName(), I_C_InvoiceLine.Table_Name);

		final MInvoiceLine il = (MInvoiceLine)facts.get(3).retrievePO();

		assertEquals("5th fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_ZuBerechnen, facts.get(4).getStatus());
		assertEquals("5th fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Zahlungszuordnung, facts.get(4).getFactType());
		assertFalse("5th fact of " + instance + " is counter entry", facts.get(4).isCounterEntry());
		assertEquals("5th fact of " + instance + " is no allocation line", facts.get(4).getAD_Table().getTableName(), I_C_AllocationLine.Table_Name);

		final MAllocationLine allocLine = (MAllocationLine)facts.get(4).retrievePO();

		assertEquals("4th and 5th fact of " + instance + " refer to different invoices", il.getC_Invoice_ID(), allocLine.getC_Invoice_ID());
	}

	public void checkOrderAndPaidInvoiceReversed(IAdvComInstance instance)
	{
		checkOrderAndPaidInvoice(instance);

		final List<MCAdvCommissionFact> facts = MCAdvCommissionFact
				.mkQuery(getCtx(), getTrxName())
				.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
				.list();

		assertTrue("There should be at least ten facts for " + instance, facts.size() >= 10);

		assertEquals("6th fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_ZuBerechnen, facts.get(5).getStatus());
		assertEquals("6th fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_ZahlungszuordnRueckg, facts.get(5).getFactType());
		assertTrue("6th fact of " + instance + " is not counter entry", facts.get(5).isCounterEntry());
		assertEquals("6th fact of " + instance + " is no invoice line", facts.get(5).getAD_Table().getTableName(), I_C_AllocationLine.Table_Name);

		final MAllocationLine allocLine = (MAllocationLine)facts.get(5).retrievePO();

		assertEquals("7th fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(6).getStatus());
		assertEquals("7th fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_ZahlungszuordnRueckg, facts.get(6).getFactType());
		assertFalse("7th fact of " + instance + " is counter entry", facts.get(6).isCounterEntry());
		assertEquals("7th fact of " + instance + " is no invoice line", facts.get(6).getAD_Table().getTableName(), I_C_InvoiceLine.Table_Name);

		final MInvoiceLine origIl = (MInvoiceLine)facts.get(6).retrievePO();
		assertEquals("6th and 7th fact of " + instance + " refer to different invoices", allocLine.getC_Invoice_ID(), origIl.getC_Invoice_ID());

		assertEquals("8th fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(7).getStatus());
		assertEquals("8th fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig, facts.get(7).getFactType());
		assertTrue("8th fact of " + instance + " is not counter entry", facts.get(7).isCounterEntry());
		assertEquals("8th fact of " + instance + " is no invoice line", facts.get(7).getAD_Table().getTableName(), I_C_InvoiceLine.Table_Name);

		assertEquals("7th and 8th fact of " + instance + " refer to different invoice lines", facts.get(6).getRecord_ID(), facts.get(7).getRecord_ID());

		assertEquals("9th fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(8).getStatus());
		assertEquals("9th fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig, facts.get(8).getFactType());
		assertFalse("9th fact of " + instance + " is counter entry", facts.get(8).isCounterEntry());
		assertEquals("9th fact of " + instance + " is no invoice line", facts.get(8).getAD_Table().getTableName(), I_C_InvoiceLine.Table_Name);

		final MInvoiceLine reverseIl = (MInvoiceLine)facts.get(8).retrievePO();
		// origIl should refer to the original invoice, reverseIl should refer to the reversal
		assertTrue("8th and 9th fact-PO of " + instance + " refer to the same invoice", origIl.getC_Invoice_ID() != reverseIl.getC_Invoice_ID());

		assertEquals("10th fact of " + instance + " has wrong status", X_C_AdvCommissionFact.STATUS_Prognostiziert, facts.get(9).getStatus());
		assertEquals("10th fact of " + instance + " has wrong type", X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig, facts.get(9).getFactType());
		assertFalse("10th fact of " + instance + " is counter entry", facts.get(9).isCounterEntry());
		assertEquals("10th fact of " + instance + " is no invoice line", facts.get(9).getAD_Table().getTableName(), I_C_OrderLine.Table_Name);

		final MOrderLine ol = (MOrderLine)facts.get(9).retrievePO();
		assertEquals("10th fact-PO of " + instance + " doesn't refer to instance trigger", ol.getC_OrderLine_ID(), instance.getRecord_ID());
		assertEquals("7th fact-PO of " + instance + " doesn't refer to instance trigger", ol.getC_OrderLine_ID(), origIl.getC_OrderLine_ID());
		assertEquals("9th fact-PO of " + instance + " doesn't refer to instance trigger", ol.getC_OrderLine_ID(), reverseIl.getC_OrderLine_ID());
	}

	public List<IAdvComInstance> retrieveInstances(final Object model)
	{
		Assert.assertNotNull("model is null", model);

		final PO po = InterfaceWrapperHelper.getPO(model);
		Assert.assertNotNull("No PO found for " + model, po);

		final List<IAdvComInstance> instances = new ArrayList<IAdvComInstance>();

		final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);

		for (final Object poLine : faBL.retrieveLines(po, true))
		{
			instances.addAll(Services.get(ICommissionInstanceDAO.class).retrieveAllFor(poLine, null));
		}
		return instances;
	}

	public boolean isCommissionCalculated(final IAdvComInstance inst)
	{
		final I_C_AdvComSystem_Type comSystemType = inst.getC_AdvComSystem_Type();

		final ICommissionType businessLogic = Services.get(ICommissionTypeBL.class).getBusinessLogic(comSystemType);
		final boolean commissionCanculated = businessLogic.isCommissionCalculated();
		return commissionCanculated;
	}

	public void assertNoneClosed(final List<IAdvComInstance> instances)
	{
		for (final IAdvComInstance inst : instances)
		{
			assertThat(inst + " expected to be *not* closed", inst.isClosed(), not(true));
		}
	}

	public void assertAllClosed(final List<IAdvComInstance> instances)
	{
		for (final IAdvComInstance inst : instances)
		{
			assertThat(inst + " expected to be closed", inst.isClosed(), is(true));
		}
	}

	public void assertAllFinished(final Object... instanceTriggerPOLineModels)
	{
		for (final Object poLineModel : instanceTriggerPOLineModels)
		{
			final PO poLine = InterfaceWrapperHelper.getPO(poLineModel);
			Check.assume(poLine instanceof MOrderLine || poLine instanceof MInvoiceLine,
					"Given instance trigger poLine model " + poLineModel + " is either an order line or an invoice line");

			for (final IAdvComInstance inst : Services.get(ICommissionInstanceDAO.class).retrieveAllFor(poLine, null))
			{
				assertThat(inst + " has wrong Points_Predicted", inst.getPoints_Predicted(), comparesEqualTo(BigDecimal.ZERO));
				assertThat(inst + " has wrong Points_ToCalculate", inst.getPoints_ToCalculate(), comparesEqualTo(BigDecimal.ZERO));
				assertThat(inst + " has wrong Points_Calculated", inst.getPoints_Calculated(), comparesEqualTo(BigDecimal.ZERO));
				assertThat(inst + " has wrong AmtToPay", inst.getAmtToPay(), comparesEqualTo(BigDecimal.ZERO));
			}
		}
	}

	/**
	 * For the given {@code poLineModel} this method retrieves all commission {@link I_C_Invoice}s that belong to commission instances with facts, referencing {@code poLineModel}. The invoices are
	 * inserted into the given map with there {@code C_Invoice_ID} as the key.
	 * 
	 * @param poLineModel
	 * @param commissionInvoices
	 */
	public void retrieveCommissionInvoices(final Object poLineModel, final Map<Integer, I_C_Invoice> commissionInvoices)
	{
		for (final IAdvComInstance inst : Services.get(ICommissionInstanceDAO.class).retrieveAllFor(InterfaceWrapperHelper.getPO(poLineModel), null))
		{
			final List<MCAdvCommissionFact> facts = MCAdvCommissionFact.mkQuery(getCtx(), getTrxName())
					.setAdvCommissionInstanceId(inst.getC_AdvCommissionInstance_ID())
					.setTableId(I_C_InvoiceLine.Table_ID)
					.setStatus(X_C_AdvCommissionFact.STATUS_Auszuzahlen, false)
					.list();

			for (final I_C_AdvCommissionFact fact : facts)
			{
				final I_C_InvoiceLine il = InterfaceWrapperHelper.create(MCAdvCommissionFact.retrievePO(fact), I_C_InvoiceLine.class);
				if (commissionInvoices.containsKey(il.getC_Invoice_ID()))
				{
					continue;
				}
				final I_C_Invoice invoice = InterfaceWrapperHelper.create(il.getC_Invoice(), I_C_Invoice.class);
				Check.assume(!invoice.isSOTrx(), "Commission invoice " + invoice + " has SOTrx='N'");

				final I_C_DocType docType = invoice.getC_DocType();
				Check.assume(
						Constants.DOCBASETYPE_AEInvoice.equals(docType.getDocBaseType()),
						"Commission invoice " + invoice + " has DocBaseType=" + Constants.DOCBASETYPE_AEInvoice);
				Check.assume(
						CommissionConstants.COMMISSON_INVOICE_DOCSUBTYPE_CALC.equals(docType.getDocSubType()),
						"Commission invoice " + invoice + " has DocSubType=" + CommissionConstants.COMMISSON_INVOICE_DOCSUBTYPE_CALC);

				commissionInvoices.put(il.getC_Invoice_ID(), InterfaceWrapperHelper.create(invoice, I_C_Invoice.class));
			}
		}
	}

	/**
	 * Creates outgoing payments for the commission invoices in {@code commissionInvoices}.
	 * 
	 * @param commissionInvoices
	 * @param info
	 * @return
	 */
	public List<I_C_Payment> payInvoice(final Map<Integer, I_C_Invoice> commissionInvoices, final String info)
	{
		final List<I_C_Payment> commissionPayments = new ArrayList<I_C_Payment>();

		for (final I_C_Invoice commissionInvoice : commissionInvoices.values())
		{
			InterfaceWrapperHelper.refresh(commissionInvoice);
			if (commissionInvoice.isPaid())
			{
				continue; // this is the second invocation of payInvoice() and 'commissionInvoice' has been created and
							// paid in the first run.
			}

			final I_C_Payment payment =
					mkPaymentHelper()
							.setC_Invoice(commissionInvoice)
							.createPayment();
			commissionPayments.add(payment);
			addChatInfoToPO(payment, info);
		}

		for (final I_C_Invoice commissionInvoice : commissionInvoices.values())
		{
			InterfaceWrapperHelper.refresh(commissionInvoice);
			assertTrue(commissionInvoice + " has IsPaid='Y'", commissionInvoice.isPaid());
		}

		runProcess_WriteCommissionAccount();
		return commissionPayments;
	}

}
