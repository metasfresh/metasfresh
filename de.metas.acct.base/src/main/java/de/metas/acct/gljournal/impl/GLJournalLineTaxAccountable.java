package de.metas.acct.gljournal.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.math.BigDecimal;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_GL_JournalLine;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.tax.ITaxAccountable;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Adapts {@link I_GL_JournalLine} to {@link ITaxAccountable}
 * 
 * @author tsa
 *
 */
/* package */class GLJournalLineTaxAccountable implements ITaxAccountable
{
	private final I_GL_JournalLine glJournalLine;
	private final boolean _accountSignDR;

	public GLJournalLineTaxAccountable(final I_GL_JournalLine glJournalLine, final boolean accountSignDR)
	{
		super();

		Check.assumeNotNull(glJournalLine, "glJournalLine not null");
		this.glJournalLine = glJournalLine;
		_accountSignDR = accountSignDR;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public final boolean isAccountSignDR()
	{
		return _accountSignDR;
	}

	@Override
	public final boolean isAccountSignCR()
	{
		return !_accountSignDR;
	}

	@Override
	public I_C_Tax getC_Tax()
	{
		return isAccountSignDR() ? glJournalLine.getDR_Tax() : glJournalLine.getCR_Tax();
	}

	@Override
	public int getC_Tax_ID()
	{
		return isAccountSignDR() ? glJournalLine.getDR_Tax_ID() : glJournalLine.getCR_Tax_ID();
	}

	@Override
	public void setC_Tax(final I_C_Tax tax)
	{
		if (isAccountSignDR())
		{
			glJournalLine.setDR_Tax(tax);
		}
		else
		{
			glJournalLine.setCR_Tax(tax);
		}
	}

	@Override
	public I_C_ValidCombination getTax_Acct()
	{
		return isAccountSignDR() ? glJournalLine.getDR_Tax_Acct() : glJournalLine.getCR_Tax_Acct();
	}

	@Override
	public void setTax_Acct(final I_C_ValidCombination taxAcct)
	{
		if (isAccountSignDR())
		{
			glJournalLine.setDR_Tax_Acct(taxAcct);
		}
		else
		{
			glJournalLine.setCR_Tax_Acct(taxAcct);
		}
	}

	@Override
	public BigDecimal getTaxAmt()
	{
		return isAccountSignDR() ? glJournalLine.getDR_TaxAmt() : glJournalLine.getCR_TaxAmt();

	}

	@Override
	public void setTaxAmt(final BigDecimal taxAmt)
	{
		if (isAccountSignDR())
		{
			glJournalLine.setDR_TaxAmt(taxAmt);
		}
		else
		{
			glJournalLine.setCR_TaxAmt(taxAmt);
		}
	}

	@Override
	public BigDecimal getTaxBaseAmt()
	{
		return isAccountSignDR() ? glJournalLine.getDR_TaxBaseAmt() : glJournalLine.getCR_TaxBaseAmt();

	}

	@Override
	public void setTaxBaseAmt(final BigDecimal taxBaseAmt)
	{
		if (isAccountSignDR())
		{
			glJournalLine.setDR_TaxBaseAmt(taxBaseAmt);
		}
		else
		{
			glJournalLine.setCR_TaxBaseAmt(taxBaseAmt);
		}
	}

	@Override
	public BigDecimal getTaxTotalAmt()
	{
		return isAccountSignDR() ? glJournalLine.getDR_TaxTotalAmt() : glJournalLine.getCR_TaxTotalAmt();

	}

	@Override
	public void setTaxTotalAmt(final BigDecimal totalAmt)
	{
		if (isAccountSignDR())
		{
			glJournalLine.setDR_TaxTotalAmt(totalAmt);
		}
		else
		{
			glJournalLine.setCR_TaxTotalAmt(totalAmt);
		}

		//
		// Update AmtSourceDr/Cr
		// NOTE: we are updating both sides because they shall be the SAME
		glJournalLine.setAmtSourceDr(totalAmt);
		glJournalLine.setAmtSourceCr(totalAmt);
	}

	@Override
	public I_C_ValidCombination getTaxBase_Acct()
	{
		return isAccountSignDR() ? glJournalLine.getAccount_DR() : glJournalLine.getAccount_CR();
	}

	@Override
	public I_C_ValidCombination getTaxTotal_Acct()
	{
		return isAccountSignDR() ? glJournalLine.getAccount_CR() : glJournalLine.getAccount_DR();
	}

	@Override
	public CurrencyPrecision getPrecision()
	{
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(glJournalLine.getC_Currency_ID());
		return currencyId != null
				? Services.get(ICurrencyDAO.class).getStdPrecision(currencyId)
				: CurrencyPrecision.TWO;
	}

	@Override
	public AcctSchemaId getAcctSchemaId()
	{
		return AcctSchemaId.ofRepoId(glJournalLine.getGL_Journal().getC_AcctSchema_ID());
	}
}
