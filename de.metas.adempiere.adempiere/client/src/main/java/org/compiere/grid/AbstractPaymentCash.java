package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.model.I_C_Bank;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MConversionRate;
import org.compiere.model.MPayment;
import org.compiere.model.MSysConfig;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

public abstract class AbstractPaymentCash implements IVPaymentPanel
{
	protected final String PROP_Loaded = getClass().getName() + ".Loaded";
	
	private IPayableDocument doc;
//
//	public AbstractPaymentCash()
//	{
//		// initUI(); // delayed initialization: see setFrom(IPayableDocument)
//	}

	public Properties getCtx()
	{
		return Env.getCtx();
	}

	protected KeyNamePair[] retrieveCashBooks()
	{
		final String SQL = Env.getUserRolePermissions().addAccessSQL(
				"SELECT C_CashBook_ID, Name, AD_Org_ID FROM C_CashBook WHERE IsActive='Y'",
				"C_CashBook", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		KeyNamePair[] knps = DB.getKeyNamePairs(SQL, false);
		return knps;
	}

	protected KeyNamePair[] retrieveCurrencies()
	{
		final String SQL = "SELECT C_Currency_ID, ISO_Code FROM C_Currency "
				+ "WHERE (IsEMUMember='Y' AND EMUEntryDate<now()) OR IsEuro='Y' "
				+ "ORDER BY 2";
		KeyNamePair[] knps = DB.getKeyNamePairs(SQL, false);
		return knps;
	}
	
	protected KeyNamePair[] retrieveCashBankAccounts()
	{
		final String SQL = Env.getUserRolePermissions().addAccessSQL(
				"SELECT ba.C_BP_BankAccount_ID, b.Name || ' ' || ba.AccountNo, ba.IsDefault "
						+ "FROM C_BP_BankAccount ba"
						+ " INNER JOIN C_Bank b ON (ba.C_Bank_ID=b.C_Bank_ID) "
						+ "WHERE b.IsActive='Y'"
						+ " AND b." + I_C_Bank.COLUMNNAME_IsCashBank + "='Y'",
				"ba", IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		KeyNamePair[] knps = DB.getKeyNamePairs(SQL, false);
		return knps;
	}

	public boolean isCashAsPayment()
	{
		return MSysConfig.getBooleanValue("CASH_AS_PAYMENT", true, doc.getAD_Client_ID());
	}

	public void setAmount()
	{
		BigDecimal amt = MConversionRate.convert(getCtx(),
				doc.getGrandTotal(), doc.getC_Currency_ID(), getC_Currency_ID(), doc.getAD_Client_ID(), doc.getAD_Org_ID());
		setAmount(amt);
	}

	protected abstract void initUI();

	protected abstract void loadCashBooks();

	protected abstract void loadCashBankAccounts();

	public abstract void setAmount(BigDecimal amount);

	public abstract int getC_BP_BankAccount_ID();

	public abstract void setC_BP_BankAccount_ID(int C_BP_BankAccount_ID);

	public abstract void setC_CashBook_ID(int c_CashBook_ID);

	@Override
	public void setFrom(I_C_Payment payment)
	{
		if (payment == null)
			return;
		setC_BP_BankAccount_ID(payment.getC_BP_BankAccount_ID());
		setAmount(payment.getPayAmt());
	}

	@Override
	public void setFrom(I_C_CashLine cashline)
	{
		if (cashline == null)
			return;

		setC_CashBook_ID(cashline.getC_Cash().getC_CashBook_ID());
		setAmount(cashline.getAmount());
		setDate(cashline.getC_Cash().getStatementDate());
	}

	@Override
	public void setFrom(IPayableDocument doc)
	{
		boolean init = this.doc == null;
		this.doc = doc;
		if (init)
		{
			initUI();
		}
	}

	public void onActivate()
	{
		loadCashBooks();
		loadCashBankAccounts();
		if (!doc.isSOTrx())
		{
			setAmount(doc.getGrandTotal().negate());
		}
		else
		{
			setAmount(doc.getGrandTotal());
		}
	}

	private boolean enabled = true;

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = true;
	}

	@Override
	public boolean isEnabled()
	{
		return enabled;
	}

	@Override
	public boolean validate(boolean isInTrx)
	{
		if (isCashAsPayment())
		{
			if (getC_BP_BankAccount_ID() <= 0)
				throw new FillMandatoryException("C_BP_BankAccount_ID");
		}
		else
		{
			if (getC_CashBook_ID() <= 0)
				throw new FillMandatoryException("C_CashBook_ID");
		}
		return true;
	}

	@Override
	public boolean isAllowProcessing()
	{
		return true;
	}

	@Override
	public void updatePayment(ProcessingCtx pctx, MPayment payment)
	{
		if (!isCashAsPayment())
		{
			throw new IllegalStateException("CashAsPayment is not activated");
		}

		payment.setBankCash(getC_BP_BankAccount_ID(), doc.isSOTrx(), MPayment.TENDERTYPE_Cash);
	}

	@Override
	public String getTargetTableName()
	{
		return isCashAsPayment() ? I_C_Payment.Table_Name : I_C_CashLine.Table_Name;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		// Not needed. Property change support is available only for credit card payment. 
	}

	private boolean readonly = true;

	@Override
	public void setReadOnly(boolean readOnly)
	{
		this.readonly = readOnly;
	}

	@Override
	public boolean isReadOnly()
	{
		return readonly;
	}

}
