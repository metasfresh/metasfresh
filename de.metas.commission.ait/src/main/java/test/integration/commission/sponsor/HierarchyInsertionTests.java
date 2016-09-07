package test.integration.commission.sponsor;

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


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.TestClientUI;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MPayment;
import org.compiere.model.X_C_DocType;
import org.compiere.process.DocAction;
import org.compiere.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.helper.BPartnerHelper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.helper.OrderHelper.Order_InvoiceRule;
import de.metas.adempiere.ait.test.IntegrationTestRunner;
import de.metas.adempiere.ait.test.annotation.IntegrationTest;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.model.I_C_Order;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.interfaces.I_C_BPartner_Location;
import de.metas.commission.interfaces.I_C_Invoice;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.X_C_Sponsor_CondLine;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ISponsorDAO;
import de.metas.commission.util.CommissionConstants;
import de.metas.interfaces.I_C_OrderLine;
import test.integration.commission.helper.Helper;
import test.integration.swat.sales.scenario.SalesScenario;

/**
 * Creates a setup as follows:
 * <ul>
 * <li>HierarchyChange_SR1: Salesrep</li>
 * <ul>
 * <li>HierarchyChange_CUST3: customer with Order1; DateOrdered := now - 1d</li>
 * <li>HierarchyChange_CUST4: customer with Order2; DateOrdered := now - 1d</li>
 * </ul>
 * </ul>
 * 
 * Then another sales rep "HierarchyChange_SR2" is inserted between SR1 and CUST3. Therefore, the new hierachy will be like this:
 * 
 * <ul>
 * <li>HierarchyChange_SR1: Salesrep</li>
 * <ul>
 * <li>HierarchyChange_CUST3: customer</li>
 * <li>HierarchyChange_SR2: Salesrep</li>
 * <ul>
 * <li>HierarchyChange_CUST4: customer</li>
 * </ul>
 * </ul> </ul>
 * 
 * This should cause the following:
 * <ul>
 * <li>All commission instances for Order1 remain unaffected</li>
 * <li>for Order2, there are changes as follows:</li>
 * <ul>
 * <li>Exiting commission instances for SR1 their hierarchy levels increased by one.</li>
 * <li>SR1 doesn't receive 'Verkaufsvolumen' and 'Handelsspannen-Differenzprovision' and anymore.</li>
 * <li>SR1 now receives 'Ebenenbonus'.</li>
 * <li>SR2 receives 'Verkaufsvolumen' and 'Handelsspannen-Differenzprovision'</li>
 * </ul>
 * </ul>
 * 
 * 
 * There are different tests in which the insertion is made at different stages.
 * <ul>
 * <li>no suspension (reference)</li>
 * <libefore calculation (Berechnung)</li>
 * <li>before commission invoicing (Abrechnung)</li>
 * <li>before commission payment</li>
 * <li>after commission payment</li>
 * 
 * After the suspension has been made, commission calculations, invoices and payment are created (once more) to make sure that the instances were left in a consistent state. Also, the commission
 * payment is reversed and a new payment is made, to make sure that this also doesn't lead to inconsistencies.
 */
@RunWith(IntegrationTestRunner.class)
public class HierarchyInsertionTests extends AIntegrationTestDriver
{

	private I_C_BPartner sr1;
	private I_C_BPartner sr2;
	private I_C_BPartner cust3;
	private I_C_BPartner cust4;

	private I_C_Sponsor spSr1;
	private I_C_Sponsor spSr2;
	private I_C_Sponsor spCust3;
	private I_C_Sponsor spCust4;

	@IntegrationTest(
			desc = "Reference: no insertion is made",
			tasks = "02544")
	@Test
	public void noRetroactiveInsert()
	{
		setupHierarchy("HI0", "noretroactiveInsert");

		final Result initialResults = createOrders("noretroactiveInsert");
		getHelper().runProcess_WriteCommissionAccount();

		// Make a commission calculation
		makeCalculation(initialResults.dateOrdered);

		// Make the commission invoices
		makeCommissionInvoices(initialResults);

		payInvoice(initialResults, "noretroactiveInsert");

		voidCommissionPayments(initialResults);

		payInvoice(initialResults, "noretroactiveInsert");
	}

	@IntegrationTest(
			tasks = "02544")
	@Test
	public void retroactiveInsert()
	{
		setupHierarchy("HI1", "retroactiveInsert");

		final Result initialResults = createOrders("retroactiveInsert");

		addChatInfos(initialResults, "retroactiveInsert");

		//
		// now change the hierarchy
		insertSR2IntoHierarchy();

		getHelper().runProcess_WriteCommissionAccount();

		roughEvalInstancesAfterChange(initialResults);

		// Make the commission calculation
		makeCalculation(initialResults.dateOrdered);

		// Make the commission invoices
		makeCommissionInvoices(initialResults);

		payInvoice(initialResults, "retroactiveInsert");
		getHelper().assertAllFinished(initialResults.order1Ol, initialResults.order2Ol);

		voidCommissionPayments(initialResults);

		payInvoice(initialResults, "retroactiveInsert");
	}

	@IntegrationTest(
			tasks = "02544")
	@Test
	public void retroactiveInsertAfterCalc()
	{
		setupHierarchy("HI2", "retroactiveInsertAfterCalc");

		final Result initialResults = createOrders("retroactiveInsertAfterCalc");
		getHelper().runProcess_WriteCommissionAccount();

		// Make a commission calculation
		makeCalculation(initialResults.dateOrdered);

		//
		// now change the hierarchy
		insertSR2IntoHierarchy();

		getHelper().runProcess_WriteCommissionAccount();

		roughEvalInstancesAfterChange(initialResults);

		// Make another commission calculation
		makeCalculation(initialResults.dateOrdered);

		// Make the commission invoices
		makeCommissionInvoices(initialResults);

		payInvoice(initialResults, "retroactiveInsertAfterCalc");

		voidCommissionPayments(initialResults);

		payInvoice(initialResults, "retroactiveInsertAfterCalc");
	}

	@IntegrationTest(
			tasks = "02544")
	@Test
	public void retroactiveInsertAfterCalcAndInvoice()
	{
		setupHierarchy("HI3", "retroactiveInsertAfterCalcAndInvoice");

		final Result initialResults = createOrders("retroactiveInsertAfterCalcAndInvoice");
		getHelper().runProcess_WriteCommissionAccount();

		// Make a commission calculation
		makeCalculation(initialResults.dateOrdered);

		// Make the commission invoices
		makeCommissionInvoices(initialResults);

		//
		// now change the hierarchy
		insertSR2IntoHierarchy();

		getHelper().runProcess_WriteCommissionAccount();

		roughEvalInstancesAfterChange(initialResults);

		// Make another commission calculations
		makeCalculation(initialResults.dateOrdered);

		// Make another commission invoices
		makeCommissionInvoices(initialResults);

		payInvoice(initialResults, "retroactiveInsertAfterCalcAndInvoice");

		voidCommissionPayments(initialResults);

		payInvoice(initialResults, "retroactiveInsertAfterCalcAndInvoice");
	}

	@IntegrationTest(
			tasks = "02544")
	@Test
	public void retroactiveInsertAfterPay()
	{
		setupHierarchy("HI4", "retroactiveInsertAfterPay");

		final Result initialResults = createOrders("retroactiveInsertAfterPay");
		getHelper().runProcess_WriteCommissionAccount();

		// Make a commission calculation
		makeCalculation(initialResults.dateOrdered);

		// Make the commission invoices
		makeCommissionInvoices(initialResults);

		// Pay the invoices
		payInvoice(initialResults, "retroactiveInsertAfterPay");

		//
		// now change the hierarchy
		insertSR2IntoHierarchy();

		getHelper().runProcess_WriteCommissionAccount();

		roughEvalInstancesAfterChange(initialResults);

		// Make another commission calculations
		makeCalculation(initialResults.dateOrdered);

		// Make another commission invoices
		makeCommissionInvoices(initialResults);

		payInvoice(initialResults, "retroactiveInsertAfterPay");

		voidCommissionPayments(initialResults);

		payInvoice(initialResults, "retroactiveInsertAfterPay");
	}

	

	private void makeCalculation(final Timestamp date)
	{
		final int commissionconditionId = getHelper().getConfig().getCustomParamInt(Helper.C_ADV_COMMISSION_CONDITION_ID);

		final I_C_Calendar commissionCal =
				InterfaceWrapperHelper.create(getCtx(), commissionconditionId, I_C_AdvCommissionCondition.class, getTrxName()).getC_Calendar();

		getHelper().runProcess_CalculateCommission(commissionCal, date);

		getHelper().runProcess_WriteCommissionAccount();
	}

	/**
	 * Creates commission invoices for the date {@link Result#order2DateOrdered} and sets those invoices to {@link Result#commissionInvoices}.
	 * 
	 * @param date
	 * @param result
	 */
	private void makeCommissionInvoices(final Result result)
	{
		final int commissionconditionId = getHelper().getConfig().getCustomParamInt(Helper.C_ADV_COMMISSION_CONDITION_ID);

		final I_C_Calendar commissionCal =
				InterfaceWrapperHelper.create(getCtx(), commissionconditionId, I_C_AdvCommissionCondition.class, getTrxName()).getC_Calendar();

		getHelper().runProcess_InvoiceCommission(commissionCal, result.dateOrdered);

		getHelper().runProcess_WriteCommissionAccount();

		final Map<Integer, I_C_Invoice> commissionInvoices = new HashMap<Integer, I_C_Invoice>();

		getHelper().retrieveCommissionInvoices(result.order1Ol, commissionInvoices);
		getHelper().retrieveCommissionInvoices(result.order2Ol, commissionInvoices);

		result.commissionInvoices = commissionInvoices;
	}

	/**
	 * Creates outgoing payments for the commission invoices in {@link Result#commissionInvoices} and sets those payments to {@link Result#commissionPayments}.
	 * 
	 * @param result
	 */
	public void payInvoice(final Result result, final String info)
	{
		final Map<Integer, I_C_Invoice> commissionInvoices = result.commissionInvoices;
		
		final List<I_C_Payment> commissionPayments = getHelper().payInvoice(commissionInvoices, info);

		getHelper().assertAllFinished(result.order1Ol, result.order2Ol);

		result.commissionPayments = commissionPayments;
	}

	private void voidCommissionPayments(final Result result)
	{
		for (final I_C_Payment payment : result.commissionPayments)
		{
			final MPayment paymentPO = (MPayment)InterfaceWrapperHelper.getPO(payment);
			assertTrue(paymentPO.voidIt());
			paymentPO.saveEx();

			InterfaceWrapperHelper.refresh(payment);
		}

		getHelper().runProcess_WriteCommissionAccount();
	}

	private void addChatInfos(final Result initialResults, final String info)
	{
		for (final IAdvComInstance inst : initialResults.order1InstCust3Sr1BeforeChange)
		{
			getHelper().addChatInfoToPO(inst, info);
		}

		for (final IAdvComInstance inst : initialResults.order2InstCust4Sr1BeforeChange)
		{
			getHelper().addChatInfoToPO(inst, info);
		}

		getHelper().addChatInfoToPO(initialResults.order2Ol, info);
	}

	private void setupHierarchy(final String bPartnerPrefix, final String testMethod)
	{
		final BPartnerHelper bpartnerHelper = getHelper().mkBPartnerHelper();

		sr1 = bpartnerHelper.getC_BPartnerByName(bPartnerPrefix + "_SR1_(*)", I_C_BPartner.class);
		final I_C_BPartner_Location sr1Location = InterfaceWrapperHelper.create(MBPartnerLocation.getForBPartner(getCtx(), sr1.getC_BPartner_ID())[0], I_C_BPartner_Location.class);
		sr1Location.setIsCommissionTo(true);
		InterfaceWrapperHelper.save(sr1Location);

		sr2 = bpartnerHelper.getC_BPartnerByName(bPartnerPrefix + "_SR2_(*)", I_C_BPartner.class);
		final I_C_BPartner_Location sr2Location = InterfaceWrapperHelper.create(MBPartnerLocation.getForBPartner(getCtx(), sr2.getC_BPartner_ID())[0], I_C_BPartner_Location.class);
		sr2Location.setIsCommissionTo(true);
		InterfaceWrapperHelper.save(sr2Location);

		cust3 = bpartnerHelper.getC_BPartnerByName(bPartnerPrefix + "_CUST3_(*)", I_C_BPartner.class);
		cust4 = bpartnerHelper.getC_BPartnerByName(bPartnerPrefix + "_CUST4_(*)", I_C_BPartner.class);

		spSr1 = Services.get(ISponsorDAO.class).retrieveForBPartner(sr1, true);
		spSr2 = Services.get(ISponsorDAO.class).retrieveForBPartner(sr2, true);
		spCust3 = Services.get(ISponsorDAO.class).retrieveForBPartner(cust3, true);
		spCust4 = Services.get(ISponsorDAO.class).retrieveForBPartner(cust4, true);

		getHelper().addChatInfoToPO(sr1, testMethod);
		getHelper().addChatInfoToPO(sr2, testMethod);
		getHelper().addChatInfoToPO(cust3, testMethod);
		getHelper().addChatInfoToPO(cust4, testMethod);

		getHelper().addChatInfoToPO(spSr1, testMethod);
		getHelper().addChatInfoToPO(spSr2, testMethod);
		getHelper().addChatInfoToPO(spCust3, testMethod);
		getHelper().addChatInfoToPO(spCust4, testMethod);

		final int comSystedmId = getHelper().getConfig().getCustomParamInt(Helper.C_ADV_COM_SYSTEM_ID);
		final int commissionconditionId = getHelper().getConfig().getCustomParamInt(Helper.C_ADV_COMMISSION_CONDITION_ID);

		getHelper().mkSponsorCondHelper()
				.setC_Sponsor_ID(spSr1.getC_Sponsor_ID())
				.addLine()
				.setSponsorSalesRepType(X_C_Sponsor_CondLine.SPONSORSALESREPTYPE_VP)
				.setDateFrom(MiscUtils.toTimeStamp("2011-06-01"))
				.setDateTo(CommissionConstants.VALID_RANGE_MAX)
				.setC_AdvComSystem_ID(comSystedmId)
				.setC_AdvCommissionCondition_ID(commissionconditionId)
				.setC_BPartner_ID(sr1.getC_BPartner_ID())
				.addLineDone()
				.create()
				.complete();
		// TODO: teo: Assert Employee=N, SalesRep=Y

		// Link Customer(3) to SalesRep(2)
		getHelper().mkSponsorCondHelper()
				.setC_Sponsor_ID(spCust3.getC_Sponsor_ID())
				.addLine()
				.setSponsorSalesRepType(X_C_Sponsor_CondLine.SPONSORSALESREPTYPE_Hierarchie)
				.setDateFrom(MiscUtils.toTimeStamp("2011-06-01"))
				.setDateTo(CommissionConstants.VALID_RANGE_MAX)
				.setC_AdvComSystem_ID(comSystedmId)
				.setC_Sponsor_Parent_ID(spSr1.getC_Sponsor_ID())
				.addLineDone()
				.create()
				.complete();

		// Link Customer(4) to SalesRep(2)
		getHelper().mkSponsorCondHelper()
				.setC_Sponsor_ID(spCust4.getC_Sponsor_ID())
				.addLine()
				.setSponsorSalesRepType(X_C_Sponsor_CondLine.SPONSORSALESREPTYPE_Hierarchie)
				.setDateFrom(MiscUtils.toTimeStamp("2011-06-01"))
				.setDateTo(CommissionConstants.VALID_RANGE_MAX)
				.setC_AdvComSystem_ID(comSystedmId)
				.setC_Sponsor_Parent_ID(spSr1.getC_Sponsor_ID())
				.addLineDone()
				.create()
				.complete();
	}

	private void insertSR2IntoHierarchy()
	{
		final int comSystedmId = getHelper().getConfig().getCustomParamInt(Helper.C_ADV_COM_SYSTEM_ID);
		final int commissionconditionId = getHelper().getConfig().getCustomParamInt(Helper.C_ADV_COMMISSION_CONDITION_ID);

		//
		// add SR2 as child to SR1. This by itself is not critical
		getHelper().mkSponsorCondHelper()
				.setC_Sponsor_ID(spSr2.getC_Sponsor_ID())
				.addLine()
				.setSponsorSalesRepType(X_C_Sponsor_CondLine.SPONSORSALESREPTYPE_VP)
				.setDateFrom(MiscUtils.toTimeStamp("2011-06-01"))
				.setDateTo(CommissionConstants.VALID_RANGE_MAX)
				.setC_AdvComSystem_ID(comSystedmId)
				.setC_AdvCommissionCondition_ID(commissionconditionId)
				.setC_BPartner_ID(sr2.getC_BPartner_ID())
				.addLineDone()
				.addLine()
				.setSponsorSalesRepType(X_C_Sponsor_CondLine.SPONSORSALESREPTYPE_Hierarchie)
				.setDateFrom(MiscUtils.toTimeStamp("2011-06-01"))
				.setDateTo(CommissionConstants.VALID_RANGE_MAX)
				.setC_AdvComSystem_ID(comSystedmId)
				.setC_Sponsor_Parent_ID(spSr1.getC_Sponsor_ID())
				.addLineDone()
				.create()
				.complete();
		// TODO: teo: Assert Employee=N, SalesRep=Y

		final TestClientUI clientUI = (TestClientUI)Services.get(IClientUI.class);
		clientUI.setYesNoAnswer(true);

		//
		// now *move* CUST4 from SR1 to SR2
		getHelper().mkSponsorCondHelper()
				.setC_Sponsor_ID(spCust4.getC_Sponsor_ID())
				.addLine()
				.setSponsorSalesRepType(X_C_Sponsor_CondLine.SPONSORSALESREPTYPE_Hierarchie)
				.setDateFrom(MiscUtils.toTimeStamp("2011-06-01"))
				.setDateTo(CommissionConstants.VALID_RANGE_MAX)
				.setC_AdvComSystem_ID(comSystedmId)
				.setC_Sponsor_Parent_ID(spSr2.getC_Sponsor_ID())
				.addLineDone()
				.create()
				.complete();

		clientUI.assertAskDialogWasShown();
	}

	private void roughEvalInstancesAfterChange(final Result result)
	{
		final List<IAdvComInstance> order1Sr1AfterChange = Services.get(ICommissionInstanceDAO.class).retrieveFor(InterfaceWrapperHelper.getPO(result.order2Ol), spCust4, spSr1);
		// existing 'Handelsspannenprovision' and 'Verkaufsvolumen' have been corrected (level=2 -> 0%) and there is one additional instance
		// 'Ebenenbonus'
		assertThat(result.order1InstCust3Sr1BeforeChange.size(), equalTo(2));
		assertThat(order1Sr1AfterChange.size(), equalTo(3));

		final List<IAdvComInstance> order2InstCust4Sr2AfterChange = Services.get(ICommissionInstanceDAO.class).retrieveFor(InterfaceWrapperHelper.getPO(result.order2Ol), spCust4, spSr2);
		// There are two new instances for 'Handelsspannenprovision' and 'Verkaufsvolumen'
		assertThat(order2InstCust4Sr2AfterChange.size(), equalTo(2));
		getHelper().assertNoneClosed(order2InstCust4Sr2AfterChange);
	}

	@Override
	public IHelper newHelper()
	{
		return new Helper(new de.metas.adempiere.ait.helper.Helper());
	}

	@Override
	public Helper getHelper()
	{
		return (Helper)super.getHelper();
	}

	private Result createOrders(final String testMethod)
	{
		final Timestamp dateOrdered = TimeUtil.addDays(getHelper().getToday(), -1);

		getHelper().addInventory(IHelper.DEFAULT_ProductValue, 2);

		final I_C_Order order1 = getHelper().mkOrderHelper()
				.setDocSubType(X_C_DocType.DOCSUBTYPE_StandardOrder)
				.setBPartnerName(cust3.getValue())
				.setInvoiceRule(Order_InvoiceRule.AFTER_DELIVERY)
				.setDateOrdered(dateOrdered)
				.setComplete(DocAction.STATUS_Completed)
				.addLine(IHelper.DEFAULT_ProductValue, 10, 10)
				.createOrder();
		fireTestEvent(EventType.ORDER_STANDARD_COMPLETE_AFTER, order1);
		final SalesScenario salesScenario = new SalesScenario(this);
		salesScenario.standartOrderWithProduct(order1);

		final I_C_Order order2 = getHelper().mkOrderHelper()
				.setDocSubType(X_C_DocType.DOCSUBTYPE_StandardOrder)
				.setBPartnerName(cust4.getValue())
				.setInvoiceRule(Order_InvoiceRule.AFTER_DELIVERY)
				.setDateOrdered(dateOrdered)
				.setComplete(DocAction.STATUS_Completed)
				.addLine(IHelper.DEFAULT_ProductValue, 10, 10)
				.createOrder();
		fireTestEvent(EventType.ORDER_STANDARD_COMPLETE_AFTER, order1);
		salesScenario.standartOrderWithProduct(order2);

		getHelper().addChatInfoToPO(order1, testMethod);
		getHelper().addChatInfoToPO(order2, testMethod);

		final int[] orderIds = { order1.getC_Order_ID(), order2.getC_Order_ID() };

		getHelper().runProcess_InvoiceGenerate(orderIds);

		final List<de.metas.adempiere.model.I_C_Invoice> invoices = getHelper().retrieveInvoicesForOrders(orderIds, getTrxName());
		assertThat("Expected 2 invoices", invoices.size(), equalTo(2));

		getHelper().addChatInfoToPO(invoices.get(0), testMethod);
		getHelper().addChatInfoToPO(invoices.get(1), testMethod);

		final I_C_Payment payment1 = getHelper().mkPaymentHelper()
				.setC_Invoice(invoices.get(0))
				.createPayment();

		final I_C_Payment payment2 = getHelper().mkPaymentHelper()
				.setC_Invoice(invoices.get(1))
				.createPayment();

		getHelper().addChatInfoToPO(payment1, testMethod);
		getHelper().addChatInfoToPO(payment2, testMethod);

		getHelper().runProcess_WriteCommissionAccount();

		final List<I_C_OrderLine> order1Ols = getHelper().retrieveLines(order1);
		assertThat(order1Ols.size(), equalTo(1));

		final I_C_OrderLine order1Ol = order1Ols.get(0);
		final List<IAdvComInstance> order1InstCust3Sr1BeforeChange = Services.get(ICommissionInstanceDAO.class).retrieveFor(InterfaceWrapperHelper.getPO(order1Ol), spCust3, spSr1);
		assertThat(order1InstCust3Sr1BeforeChange.size(), equalTo(2)); // 'Handelsspannenprovision + Verkaufsvolumen'
		getHelper().assertNoneClosed(order1InstCust3Sr1BeforeChange);

		getHelper().addChatInfoToPO(order1InstCust3Sr1BeforeChange.get(0), testMethod);

		final List<IAdvComInstance> order1InstCust4Sr1BeforeChange = Services.get(ICommissionInstanceDAO.class).retrieveFor(InterfaceWrapperHelper.getPO(order1Ol), spCust4, spSr1);
		assertThat(order1InstCust4Sr1BeforeChange.size(), equalTo(0)); // cust4 has nothing to do with order1

		final List<I_C_OrderLine> order2Ols = getHelper().retrieveLines(order2);
		assertThat(order2Ols.size(), equalTo(1));

		final I_C_OrderLine order2Ol = order2Ols.get(0);
		final List<IAdvComInstance> order2InstCust3Sr1BeforeChange = Services.get(ICommissionInstanceDAO.class).retrieveFor(InterfaceWrapperHelper.getPO(order2Ol), spCust3, spSr1);
		assertThat(order2InstCust3Sr1BeforeChange.size(), equalTo(0)); // cust3 has nothing to do with order2

		final List<IAdvComInstance> order2InstCust4Sr1BeforeChange = Services.get(ICommissionInstanceDAO.class).retrieveFor(InterfaceWrapperHelper.getPO(order2Ol), spCust4, spSr1);
		assertThat(order2InstCust4Sr1BeforeChange.size(), equalTo(2)); // 'Handelsspannenprovision + Verkaufsvolumen'

		getHelper().addChatInfoToPO(order2InstCust4Sr1BeforeChange.get(0), testMethod);

		getHelper().assertNoneClosed(order2InstCust4Sr1BeforeChange);

		return new Result(
				order1Ol,
				order2Ol,
				dateOrdered,
				order1InstCust3Sr1BeforeChange,
				order2InstCust4Sr1BeforeChange);
	}

	private static class Result
	{
		final I_C_OrderLine order1Ol;
		final I_C_OrderLine order2Ol;

		final Timestamp dateOrdered;

		final List<IAdvComInstance> order1InstCust3Sr1BeforeChange;

		final List<IAdvComInstance> order2InstCust4Sr1BeforeChange;

		List<I_C_Payment> commissionPayments;

		/**
		 * Maps C_Invoice_ID => C_Invoice
		 */
		Map<Integer, I_C_Invoice> commissionInvoices;

		public Result(
				I_C_OrderLine order1Ol,
				I_C_OrderLine order2Ol,
				Timestamp dateOrdered,
				List<IAdvComInstance> order1InstCust3Sr1BeforeChange,
				List<IAdvComInstance> order2InstCust4Sr1BeforeChange)
		{
			super();
			this.order1Ol = order1Ol;
			this.order2Ol = order2Ol;
			this.dateOrdered = dateOrdered;
			this.order1InstCust3Sr1BeforeChange = order1InstCust3Sr1BeforeChange;
			this.order2InstCust4Sr1BeforeChange = order2InstCust4Sr1BeforeChange;
		}

	}

}
