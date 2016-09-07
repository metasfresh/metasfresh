package de.metas.adempiere.ait.validator;

/*
 * #%L
 * de.metas.adempiere.ait
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

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.bpartner.service.IBPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import junit.framework.Assert;

public class BPOpenBalanceValidator implements ModelValidator
{
	private static class BPAmounts
	{
		public int bpartnerId;
		public BigDecimal totalOpenBalance;
		public BigDecimal actualLifeTimeValue;
	}

	private static final BPOpenBalanceValidator instance = new BPOpenBalanceValidator();

	public static BPOpenBalanceValidator get()
	{
		return instance;
	}

	private final Map<Integer, BPAmounts> map = new HashMap<Integer, BPOpenBalanceValidator.BPAmounts>();
	private int clientId = -1;
	private boolean isRegistered = false;

	private BPOpenBalanceValidator()
	{
	}

	public void register()
	{
		if (isRegistered)
			return;
		ModelValidationEngine.get().addModelValidator(this, null);
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
			this.clientId = client.getAD_Client_ID();

		engine.addDocValidate(I_C_Invoice.Table_Name, this);
		engine.addDocValidate(I_C_Payment.Table_Name, this);
		isRegistered = true;
	}

	@Override
	public int getAD_Client_ID()
	{
		return clientId;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		if (I_C_Invoice.Table_Name.equals(po.get_TableName()))
		{
			MInvoice invoice = (MInvoice)po;
			if (timing == TIMING_BEFORE_COMPLETE)
			{
				recordState(invoice.getC_BPartner());
			}
			else if (timing == TIMING_AFTER_COMPLETE)
			{
				afterInvoiceComplete(invoice);
			}
		}
		else if (I_C_Payment.Table_Name.equals(po.get_TableName()))
		{
			if (timing == TIMING_BEFORE_COMPLETE)
			{
				recordState(InterfaceWrapperHelper.create(po, I_C_Payment.class).getC_BPartner());
			}
			else if (timing == TIMING_AFTER_COMPLETE)
			{
				afterPaymentComplete(InterfaceWrapperHelper.create(po, I_C_Payment.class));
			}

		}
		return null;
	}

	private void recordState(I_C_BPartner bpartner)
	{
		updateFrom(bpartner);
	}

	private void afterInvoiceComplete(MInvoice invoice)
	{
		final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

		final I_C_BPartner bp = invoice.getC_BPartner();
		final IBPartnerStats stats = bpartnerStatsDAO.retrieveBPartnerStats(bp);
		BPAmounts bpAmt = getBPAmounts(bp);

		//
		// Check: BP amounts
		BigDecimal invoiceAmtAbs = invoice.getGrandTotal(true);
		if (!invoice.isSOTrx())
		{
			invoiceAmtAbs = invoiceAmtAbs.negate();
		}

		final BigDecimal totalOpenBalanceExpected = bpAmt.totalOpenBalance.add(invoiceAmtAbs);
		final BigDecimal actualLifeTimeValueExpected = bpAmt.actualLifeTimeValue.add(invoice.isSOTrx() ? invoiceAmtAbs : BigDecimal.ZERO);

		InterfaceWrapperHelper.refresh(bp);
		assertThat("BP open amount is not correct after invoice " + invoice, stats.getTotalOpenBalance(), comparesEqualTo(totalOpenBalanceExpected));
		assertThat("BP lifetime value is not correct after invoice " + invoice, stats.getActualLifeTimeValue(), comparesEqualTo(actualLifeTimeValueExpected));

		updateFrom(bpAmt, stats);
	}

	private void afterPaymentComplete(I_C_Payment payment)
	{
		final IBPartnerStats stats = Services.get(IBPartnerStatsDAO.class).retrieveBPartnerStats(payment.getC_BPartner());

		final I_C_BPartner bp = payment.getC_BPartner();
		BPAmounts bpAmt = getBPAmounts(bp);

		if (!payment.isPrepayment())
		{
			return;
		}

		BigDecimal payAmtAbs = payment.getPayAmt();
		if (!payment.isReceipt())
			payAmtAbs = payAmtAbs.negate();

		final BigDecimal totalOpenBalanceExpected = bpAmt.totalOpenBalance.subtract(payAmtAbs);
		InterfaceWrapperHelper.refresh(bp);
		assertThat("BP open amount is not correct after payment " + payment, stats.getTotalOpenBalance(), comparesEqualTo(totalOpenBalanceExpected));

		updateFrom(bpAmt, stats);
	}

	private void updateFrom(I_C_BPartner bp)
	{
		Assert.assertNotNull(bp);
		Assert.assertTrue(bp.getC_BPartner_ID() > 0);

		final IBPartnerStats stats = Services.get(IBPartnerStatsDAO.class).retrieveBPartnerStats(bp);
		BPAmounts bpAmt = map.get(bp.getC_BPartner_ID());
		if (bpAmt == null)
		{
			bpAmt = new BPAmounts();
			bpAmt.bpartnerId = bp.getC_BPartner_ID();
			map.put(bpAmt.bpartnerId, bpAmt);
		}
		updateFrom(bpAmt, stats);
	}

	private void updateFrom(final BPAmounts bpAmt, final IBPartnerStats stats)
	{
		bpAmt.totalOpenBalance = stats.getTotalOpenBalance();
		bpAmt.actualLifeTimeValue = stats.getActualLifeTimeValue();
	}

	private BPAmounts getBPAmounts(I_C_BPartner bp)
	{
		Assert.assertNotNull(bp);
		Assert.assertTrue(bp.getC_BPartner_ID() > 0);

		BPAmounts bpAmt = map.get(bp.getC_BPartner_ID());
		Assert.assertNotNull("No BPAmounts found for " + bp, bpAmt);
		return bpAmt;

	}
}
