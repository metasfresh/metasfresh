package org.compiere.grid;

/*
 * #%L
 * ADempiere ERP - Desktop Client
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

import org.compiere.grid.VPayment.ProcessingCtx;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.ValueNamePair;

public abstract class AbstractCreditCardPanel implements PropertyChangeListener, IVPaymentPanel
{
	
	protected boolean isCreditCardTypesLoaded = false;
	@Override
	public abstract BigDecimal getAmount();

	@Override
	public abstract void setAmount(BigDecimal amount);

	@Override
	public int getC_CashBook_ID()
	{
		return -1;
	}

	private Timestamp date = TimeUtil.getDay(null); // today
	private int currencyId = -1;

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
	public String getTargetTableName()
	{
		return I_C_Payment.Table_Name;
	}

	@Override
	public boolean isAllowProcessing()
	{
		// we are processing credit card payments trough Online button
		return false;
	}

	protected ValueNamePair[] retrieveCreditCardTypes()
	{
		ValueNamePair[] ccs = new ValueNamePair[0];
			MPayment payment = new MPayment(getCtx(), 0, null);
			if (doc != null)
			{
				// payment.setAD_Client_ID(doc.getAD_Client_ID());
				payment.setAD_Org_ID(doc.getAD_Org_ID());
			}
			payment.setPayAmt(getAmount());
			payment.setC_Currency_ID(getC_Currency_ID());
			ccs = payment.getCreditCards();
		return ccs;
	}

	protected ValueNamePair[] retrieveCreditCardTypes(MPayment payment)
	{
		ValueNamePair[] ccs = new ValueNamePair[0];
		// metas: only load credit cards, if it is an available payment rule
		if (isEnabled())
		{
			ccs = payment.getCreditCards();
		}
		return ccs;
	}

	protected IPayableDocument doc;
	private boolean enabled;

	@Override
	public boolean isEnabled()
	{
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
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

	private boolean readonly = true;

	@Override
	public boolean isReadOnly()
	{
		return readonly;
	}

	@Override
	public void setReadOnly(boolean readOnly)
	{
		this.readonly = readOnly;
	}
	
	public abstract String getCCType();
	public abstract String getNumber();
	public abstract String getExp();
	public abstract String getName();
	public abstract String getCardCode();
	
	@Override
	public void updatePayment(ProcessingCtx pctx, MPayment payment)
	{
		payment.setCreditCard(MPayment.TRXTYPE_Sales, this.getCCType(), this.getNumber(), this.getCardCode(), this.getExp());
		payment.setA_Name(this.getName());
		payment.setCreditCardVV(this.getCardCode()); // metas
		payment.setPaymentProcessor();
		pctx.isOnline = true;
	}

}
