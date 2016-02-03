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
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Currency;
import org.compiere.model.MConversionRate;
import org.compiere.model.MPayment;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;

public abstract class AbstractPaymentCheck implements IVPaymentPanel
{
//	public AbstractPaymentCheck() 
//	{
//		initUI();
//	}
	
	public abstract String getAccountNo();
	
	public abstract int getC_BP_BankAccount_ID();
	
	protected abstract void loadBankAccounts();
	
	protected abstract void loadCurrencies();
	
	protected KeyNamePair[] retrieveCurrencies()
	{
		final String SQL = "SELECT C_Currency_ID, ISO_Code FROM C_Currency "
				+ "WHERE (IsEMUMember='Y' AND EMUEntryDate<now()) OR IsEuro='Y' "
				+ "ORDER BY 2";
		KeyNamePair[] knps = DB.getKeyNamePairs(SQL, false);
		return knps;
	}
	
	public abstract void setC_BP_BankAccount_ID(int C_BP_BankAccount_ID);
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		// Not needed. Property change support is available only for credit card payment.
	}

	@Override
	public int getC_CashBook_ID()
	{
		return -1;
	}

	public Properties getCtx()
	{
		return Env.getCtx();
	}
	
	
	private boolean enabled = true;
	
	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	@Override
	public boolean isEnabled()
	{
		return enabled;
	}
	
	private Timestamp date = TimeUtil.getDay(null); // today

	@Override
	public Timestamp getDate()
	{
		return date;
	}

	@Override
	public void setDate(Timestamp date)
	{
		this.date = date;
	}
	
	private static CCache<Integer, Hashtable<Integer, KeyNamePair>> s_cacheCurrencies = new CCache<Integer, Hashtable<Integer, KeyNamePair>>(I_C_Currency.Table_Name, 10, 0);
	
	public static Map<Integer, KeyNamePair> getCurrencies()
	{
		Hashtable<Integer, KeyNamePair> currencies = s_cacheCurrencies.get(0);
		if (currencies != null)
			return currencies;

		currencies = new Hashtable<Integer, KeyNamePair>(12); // Currenly only 10+1
		final String SQL = "SELECT C_Currency_ID, ISO_Code FROM C_Currency "
				+ "WHERE (IsEMUMember='Y' AND EMUEntryDate<now()) OR IsEuro='Y' "
				+ "ORDER BY 2";
		for (KeyNamePair knp : DB.getKeyNamePairs(SQL, false))
		{
			currencies.put(knp.getKey(), knp);
		}
		return currencies;
	} // loadCurrencies
	
	private IPayableDocument doc;
	

	public void setAmount()
	{
		BigDecimal amt = MConversionRate.convert(getCtx(),
				doc.getGrandTotal(), doc.getC_Currency_ID(), getC_Currency_ID(),
				doc.getAD_Client_ID(), doc.getAD_Org_ID());
		setAmount(amt);
	}
	
	public KeyNamePair[] retrieveBankAccounts()
	{
		final String SQL = Env.getUserRolePermissions().addAccessSQL(
				"SELECT C_BP_BankAccount_ID, Name || ' ' || AccountNo, IsDefault "
						+ "FROM C_BP_BankAccount ba"
						+ " INNER JOIN C_Bank b ON (ba.C_Bank_ID=b.C_Bank_ID) "
						+ "WHERE b.IsActive='Y'",
				"ba", IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		return DB.getKeyNamePairs(SQL, false);
	}
	
	@Override
	public void onActivate()
	{
		loadBankAccounts();
		loadCurrencies();
		if (!doc.isSOTrx())
		{
			setAmount(doc.getGrandTotal().negate());
		}
		else
		{
			setAmount(doc.getGrandTotal());
		}
	}

	@Override
	public boolean isAllowProcessing()
	{
		return true;
	}
	
	protected boolean isSOTrx() 
	{
		return doc.isSOTrx();
	}
	
	@Override
	public void setFrom(IPayableDocument doc)
	{
		this.doc = doc;
	}
	
	@Override
	public void setFrom(I_C_CashLine cashline)
	{
	}

	@Override
	public void updatePayment(ProcessingCtx pctx, MPayment payment)
	{
		payment.setBankCheck(this.getC_BP_BankAccount_ID(),isSOTrx(), this.getRoutingNo(),
				this.getAccountNo(), this.getCheckNo());
	}
	
	protected abstract String getRoutingNo();


	protected abstract String getCheckNo();


	protected abstract void initUI();
}
