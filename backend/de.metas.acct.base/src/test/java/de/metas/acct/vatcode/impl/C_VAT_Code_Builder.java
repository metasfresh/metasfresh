package de.metas.acct.vatcode.impl;

import de.metas.acct.model.I_C_VAT_Code;
import de.metas.acct.vatcode.VATCode;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Tax;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.util.Date;

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

public class C_VAT_Code_Builder
{
	public static final C_VAT_Code_Builder newBuilder()
	{
		return new C_VAT_Code_Builder();
	}

	private Integer C_AcctSchema_ID;
	private I_C_Tax tax;
	private String isSOTrx;
	private Date validFrom;
	private Date validTo;
	private VATCode code;

	private C_VAT_Code_Builder()
	{
		super();
	}

	public I_C_VAT_Code build()
	{
		final I_C_VAT_Code vatCode = InterfaceWrapperHelper.create(Env.getCtx(), I_C_VAT_Code.class, ITrx.TRXNAME_None);
		vatCode.setC_AcctSchema_ID(C_AcctSchema_ID);
		vatCode.setC_Tax_ID(tax != null ? tax.getC_Tax_ID() : -1);
		vatCode.setIsSOTrx(isSOTrx);
		vatCode.setValidFrom(TimeUtil.asTimestamp(validFrom));
		vatCode.setValidTo(TimeUtil.asTimestamp(validTo));
		vatCode.setVATCode(code == null ? null : code.getCode());
		vatCode.setC_VAT_Code_ID(code == null ? null : code.getVatCodeId().getRepoId());
		InterfaceWrapperHelper.save(vatCode);
		return vatCode;
	}

	public C_VAT_Code_Builder setC_AcctSchema_ID(final int C_AcctSchema_ID)
	{
		this.C_AcctSchema_ID = C_AcctSchema_ID;
		return this;
	}

	public C_VAT_Code_Builder setC_Tax(final I_C_Tax tax)
	{
		this.tax = tax;
		return this;
	}

	public C_VAT_Code_Builder setIsSOTrx(final Boolean isSOTrx)
	{
		this.isSOTrx = isSOTrx == null ? null : DisplayType.toBooleanString(isSOTrx);
		return this;
	}

	public C_VAT_Code_Builder setValidFrom(final Date validFrom)
	{
		this.validFrom = validFrom;
		return this;
	}

	public C_VAT_Code_Builder setValidTo(final Date validTo)
	{
		this.validTo = validTo;
		return this;
	}

	public C_VAT_Code_Builder setVATCode(final VATCode code)
	{
		this.code = code;
		return this;
	}
}
