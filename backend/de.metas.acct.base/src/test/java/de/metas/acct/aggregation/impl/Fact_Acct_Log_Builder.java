package de.metas.acct.aggregation.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Period;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.acct.model.I_Fact_Acct_Log;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class Fact_Acct_Log_Builder
{
	public static final Fact_Acct_Log_Builder newBuilder()
	{
		return new Fact_Acct_Log_Builder();
	}

	private Properties ctx;
	private String trxName = ITrx.TRXNAME_None;
	private I_C_Period period;
	private Date dateAcct;
	private Integer C_AcctSchema_ID;
	private Integer C_ElementValue_ID;
	private String postingType;
	private String action;
	private BigDecimal amtAcctDr = BigDecimal.ZERO;
	private BigDecimal amtAcctCr = BigDecimal.ZERO;
	private BigDecimal qty = BigDecimal.ZERO;

	private Fact_Acct_Log_Builder()
	{
		super();
	}

	public I_Fact_Acct_Log build()
	{
		final I_Fact_Acct_Log log = InterfaceWrapperHelper.create(ctx, I_Fact_Acct_Log.class, trxName);
		//
		log.setAD_Org_ID(Env.getAD_Org_ID(ctx));
		log.setC_AcctSchema_ID(C_AcctSchema_ID);
		log.setC_ElementValue_ID(C_ElementValue_ID);
		log.setC_Period(period);
		log.setDateAcct(TimeUtil.asTimestamp(dateAcct));
		//
		log.setAction(action);
		log.setPostingType(postingType);
		//
		log.setAmtAcctDr(amtAcctDr);
		log.setAmtAcctCr(amtAcctCr);
		log.setQty(qty);
		//
		InterfaceWrapperHelper.save(log);
		return log;
	}

	public Fact_Acct_Log_Builder setCtx(final Properties ctx)
	{
		this.ctx = ctx;
		return this;
	}

	public Fact_Acct_Log_Builder setTrxName(final String trxName)
	{
		this.trxName = trxName;
		return this;
	}

	public Fact_Acct_Log_Builder setC_Period(final I_C_Period period)
	{
		this.period = period;
		return this;
	}

	public Fact_Acct_Log_Builder setDateAcct(Date dateAcct)
	{
		this.dateAcct = dateAcct;
		return this;
	}

	public Fact_Acct_Log_Builder setDateAcct(int year, int month, int day)
	{
		return setDateAcct(TimeUtil.getDay(year, month, day));
	}

	public Fact_Acct_Log_Builder setC_AcctSchema_ID(final int C_AcctSchema_ID)
	{
		this.C_AcctSchema_ID = C_AcctSchema_ID;
		return this;
	}

	public Fact_Acct_Log_Builder setC_ElementValue_ID(final int C_ElementValue_ID)
	{
		this.C_ElementValue_ID = C_ElementValue_ID;
		return this;
	}

	public Fact_Acct_Log_Builder setAction(final String action)
	{
		this.action = action;
		return this;
	}

	public Fact_Acct_Log_Builder setAmtAcctDr(final BigDecimal amtAcctDr)
	{
		this.amtAcctDr = amtAcctDr;
		return this;
	}

	public Fact_Acct_Log_Builder setAmtAcctDr(final int amtAcctDr)
	{
		return setAmtAcctDr(BigDecimal.valueOf(amtAcctDr));
	}

	public Fact_Acct_Log_Builder setAmtAcctCr(final BigDecimal amtAcctCr)
	{
		this.amtAcctCr = amtAcctCr;
		return this;
	}

	public Fact_Acct_Log_Builder setAmtAcctCr(final int amtAcctCr)
	{
		return setAmtAcctCr(BigDecimal.valueOf(amtAcctCr));
	}

	public Fact_Acct_Log_Builder setQty(final BigDecimal qty)
	{
		this.qty = qty;
		return this;
	}

	public Fact_Acct_Log_Builder setQty(final int qty)
	{
		return setQty(BigDecimal.valueOf(qty));
	}

	public Fact_Acct_Log_Builder setPostingType(String postingType)
	{
		this.postingType = postingType;
		return this;
	}
}
