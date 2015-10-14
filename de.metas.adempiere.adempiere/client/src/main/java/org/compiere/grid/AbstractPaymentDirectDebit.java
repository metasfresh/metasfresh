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
import java.util.Properties;

import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;


public abstract class AbstractPaymentDirectDebit implements IVPaymentPanel
{

//	public AbstractPaymentDirectDebit()
//	{
//		initUI();
//	}
//	
	protected abstract void initUI();

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		// Not needed. Property change support is available only for credit card payment.
	}
	
	private BigDecimal amount = Env.ZERO;

	@Override
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	@Override
	public BigDecimal getAmount()
	{
		return amount;
	}
	

	@Override
	public int getC_CashBook_ID()
	{
		return -1;
	}
	
	private int currencyId = -1;
	
	@Override
	public void setC_Currency_ID(int C_Currency_ID)
	{
		this.currencyId = C_Currency_ID;
	}

	@Override
	public int getC_Currency_ID()
	{
		return currencyId;
	}
	
	public Properties getCtx()
	{
		return Env.getCtx();
	}

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

	private Timestamp date = TimeUtil.getDay(null); // today
	

	@Override
	public String getTargetTableName()
	{
		return I_C_Payment.Table_Name;
	}
	
	@Override
	public boolean isAllowProcessing()
	{
		return true;
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
	
	@Override
	public void onActivate()
	{
	}

	@Override
	public void setFrom(I_C_CashLine cashline)
	{
	}
	
	protected static KeyNamePair[] retrieveBankAccounts() 
	{
		final String SQL = "SELECT a.C_BP_BankAccount_ID, COALESCE(b.Name, ' ')||'_'||COALESCE(a.AccountNo, ' ') AS Acct "
				+ " FROM C_BP_BankAccount a "
				+ " LEFT OUTER JOIN C_Bank b ON (a.C_Bank_ID=b.C_Bank_ID) "
				+ " WHERE a.IsActive='Y' AND b.IsActive='Y' AND a.AD_Client_ID = ?";
		 return DB.getKeyNamePairs(SQL, false, Env.getAD_Client_ID(Env.getCtx()));
	}
	
	protected static KeyNamePair[] retrieveBPBankAccounts(int m_C_BPartner_ID) 
	{
		final String SQL = "SELECT a.C_BP_BankAccount_ID, COALESCE(b.Name, ' ')||'_'||COALESCE(a.AccountNo, ' ') AS Acct "
				+ "FROM C_BP_BankAccount a"
				+ " LEFT OUTER JOIN C_Bank b ON (a.C_Bank_ID=b.C_Bank_ID) "
				+ "WHERE C_BPartner_ID=?"
				+ "AND a.IsActive='Y' AND a.IsACH='Y'";
		return DB.getKeyNamePairs(SQL, false, m_C_BPartner_ID);
	}
}
