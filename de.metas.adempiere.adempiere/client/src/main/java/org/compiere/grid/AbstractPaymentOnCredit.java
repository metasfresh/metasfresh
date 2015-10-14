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

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;

public abstract class AbstractPaymentOnCredit implements IVPaymentPanel
{
//
//	public AbstractPaymentOnCredit()
//	{
//		initUI();
//	}

	protected abstract void initUI();
	public abstract int getC_PaymentTerm_ID();
	public abstract void setC_PaymentTerm_ID(int C_PaymentTerm_ID);

	@Override
	public boolean isAllowProcessing()
	{
		return true;
	}

	@Override
	public abstract void updatePayment(ProcessingCtx pctx, MPayment payment);


	@Override
	public abstract int getC_CashBook_ID();

	@Override
	public abstract String getTargetTableName();

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		// Not needed. Property change support is available only for credit card payment.
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

	private BigDecimal amount = Env.ZERO;

	@Override
	public BigDecimal getAmount()
	{
		return amount;
	}

	@Override
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
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
	public boolean validate(boolean isInTrx)
	{
		return true;
	}

	@Override
	public void onActivate()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setFrom(I_C_Payment payment)
	{
	}

	@Override
	public void setFrom(I_C_CashLine cashline)
	{
	}

	protected KeyNamePair[] retrievePaymentTerms()
	{
		final String SQL = Env.getUserRolePermissions().addAccessSQL(
				"SELECT C_PaymentTerm_ID, Name FROM C_PaymentTerm WHERE IsActive='Y' ORDER BY Name",
				"C_PaymentTerm", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		return DB.getKeyNamePairs(SQL, false);
	}

}
