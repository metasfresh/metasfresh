/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.acct.aggregation.legacy.impl;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.model.I_Fact_Acct_Log;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Period;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Fact_Acct_Log_Builder
{
	public static Fact_Acct_Log_Builder newBuilder()
	{
		return new Fact_Acct_Log_Builder();
	}

	private I_C_Period period;
	private LocalDate dateAcct;
	private AcctSchemaId C_AcctSchema_ID;
	private ElementValueId C_ElementValue_ID;
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
		final I_Fact_Acct_Log log = InterfaceWrapperHelper.newInstance(I_Fact_Acct_Log.class);
		//
		log.setAD_Org_ID(Env.getAD_Org_ID(Env.getCtx()));
		log.setC_AcctSchema_ID(C_AcctSchema_ID.getRepoId());
		log.setC_ElementValue_ID(C_ElementValue_ID.getRepoId());
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

	public Fact_Acct_Log_Builder setC_Period(final I_C_Period period)
	{
		this.period = period;
		return this;
	}

	public Fact_Acct_Log_Builder setDateAcct(final LocalDate dateAcct)
	{
		this.dateAcct = dateAcct;
		return this;
	}

	public Fact_Acct_Log_Builder setDateAcct(final String dateAcct)
	{
		return setDateAcct(LocalDate.parse(dateAcct));
	}

	public Fact_Acct_Log_Builder setC_AcctSchema_ID(final AcctSchemaId C_AcctSchema_ID)
	{
		this.C_AcctSchema_ID = C_AcctSchema_ID;
		return this;
	}

	public Fact_Acct_Log_Builder setC_ElementValue_ID(final ElementValueId C_ElementValue_ID)
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

	public Fact_Acct_Log_Builder setPostingType(final String postingType)
	{
		this.postingType = postingType;
		return this;
	}
}
