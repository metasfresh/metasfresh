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
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.text.TokenizedStringBuilder;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.X_GL_Journal;

import de.metas.acct.gljournal.IGLJournalLineBL;
import de.metas.acct.gljournal.IGLJournalLineDAO;
import de.metas.acct.gljournal.IGLJournalLineGroup;
import de.metas.acct.tax.ITaxAccountable;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.IMsgBL;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.Services;

public class GLJournalLineBL implements IGLJournalLineBL
{
	@Override
	public void setAmtSourcePrecision(final I_GL_JournalLine line)
	{
		final CurrencyPrecision precision = getPrecision(line);

		final BigDecimal amtSourceDr = precision.roundIfNeeded(line.getAmtSourceDr());
		line.setAmtSourceDr(amtSourceDr);

		final BigDecimal amtSourceCr = precision.roundIfNeeded(line.getAmtSourceCr());
		line.setAmtSourceCr(amtSourceCr);
	}

	@Override
	public void setAmtAcct(final I_GL_JournalLine glJournalLine)
	{
		// Acct Amts
		final CurrencyPrecision precision = getPrecision(glJournalLine);
		final BigDecimal rate = glJournalLine.getCurrencyRate();

		// AmtAcctDr
		{
			BigDecimal amtAcctDr = rate.multiply(glJournalLine.getAmtSourceDr());
			amtAcctDr = precision.roundIfNeeded(amtAcctDr);
			glJournalLine.setAmtAcctDr(amtAcctDr);
		}

		// AmtAcctCr
		{
			BigDecimal amtAcctCr = rate.multiply(glJournalLine.getAmtSourceCr());
			amtAcctCr = precision.roundIfNeeded(amtAcctCr);
			glJournalLine.setAmtAcctCr(amtAcctCr);
		}

	}

	@Override
	public CurrencyPrecision getPrecision(final I_GL_JournalLine glJournalLine)
	{
		Check.assumeNotNull(glJournalLine, "glJournalLine not null");

		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(glJournalLine.getC_Currency_ID());
		if (currencyId != null)
		{
			return Services.get(ICurrencyDAO.class).getStdPrecision(currencyId);
		}
		else
		{
			return ICurrencyDAO.DEFAULT_PRECISION;
		}
	}	// getPrecision

	@Override
	public void setGroupNoAndFlags(final I_GL_JournalLine glJournalLine)
	{
		// Services
		final IGLJournalLineDAO glJournalLineDAO = Services.get(IGLJournalLineDAO.class);

		final I_GL_Journal glJournal = glJournalLine.getGL_Journal();

		final int groupNo;
		final boolean allowAccountDr;
		final boolean allowAccountCr;
		final boolean isSplitAcctTrx;

		final IGLJournalLineGroup group = glJournalLineDAO.retrieveFirstUnballancedJournalLineGroup(glJournal);
		
		//
		// Case: all previous groups are balanced, so we will start a new group here
		if (group == null)
		{
			final int lastGroupNo = glJournalLineDAO.retrieveLastGroupNo(glJournal);
			groupNo = (lastGroupNo <= 0 ? 0 : lastGroupNo) + 10;
			allowAccountDr = true;
			allowAccountCr = true;
			
			// Split transaction: no, it's not a split transaction because we are just starting a new group.
			// User would be able to enable it if he wants
			isSplitAcctTrx = false;
		}
		//
		// Case: we found an unbalanced group, so this new journal line shall be part of that.
		else
		{
			final BigDecimal balance = group.getBalance();
			groupNo = group.getGroupNo();
			allowAccountDr = balance.signum() <= 0;
			allowAccountCr = balance.signum() >= 0;
			
			// Split transaction: yes, it is a split transaction because it's part of an existing group.
			// TODO: user shall NOT be able to change this flag because it's already settled
			isSplitAcctTrx = true;
		}

		glJournalLine.setGL_JournalLine_Group(groupNo);
		glJournalLine.setIsSplitAcctTrx(isSplitAcctTrx);
		glJournalLine.setIsAllowAccountDR(allowAccountDr);
		glJournalLine.setIsAllowAccountCR(allowAccountCr);
	}

	@Override
	public void assertAccountValid(final I_C_ValidCombination account, final I_GL_JournalLine glJournalLine)
	{
		Check.assumeNotNull(account, "account not null");
		Check.assume(account.getC_ValidCombination_ID() > 0, "account exists");

		final I_C_ElementValue accountEV = account.getAccount();
		Check.assumeNotNull(accountEV, "accountEV not null for {}", account);

		Check.assumeNotNull(glJournalLine, "line not null");
		final int lineNo = glJournalLine.getLine();

		final I_GL_Journal glJournal = glJournalLine.getGL_Journal();
		Check.assumeNotNull(glJournal, "glJournal not null");
		final String postingType = glJournal.getPostingType();
		Check.assumeNotNull(postingType, "postingType not null");

		// bcahya, BF [2789319] No check of Actual, Budget, Statistical attribute
		if (!accountEV.isActive())
		{
			throw new AdempiereException("@InActiveAccount@ - @Line@=" + lineNo + " - " + accountEV);
		}

		// Michael Judd (mjudd) BUG: [ 2678088 ] Allow posting to system accounts for non-actual postings
		if (accountEV.isDocControlled() &&
				(postingType.equals(X_GL_Journal.POSTINGTYPE_Actual)) ||
				postingType.equals(X_GL_Journal.POSTINGTYPE_Commitment) ||
				postingType.equals(X_GL_Journal.POSTINGTYPE_Reservation))
		{
			throw new AdempiereException("@DocControlledError@ - @Line@=" + lineNo + " - " + accountEV);
		}
		//

		// bcahya, BF [2789319] No check of Actual, Budget, Statistical attribute
		if (postingType.equals(X_GL_Journal.POSTINGTYPE_Actual) && !accountEV.isPostActual())
		{
			throw new AdempiereException("@PostingTypeActualError@ - @Line@=" + lineNo + " - " + accountEV);
		}

		if (postingType.equals(X_GL_Journal.POSTINGTYPE_Budget) && !accountEV.isPostBudget())
		{
			throw new AdempiereException("@PostingTypeBudgetError@ - @Line@=" + lineNo + " - " + accountEV);
		}

		if (postingType.equals(X_GL_Journal.POSTINGTYPE_Statistical) && !accountEV.isPostStatistical())
		{
			throw new AdempiereException("@PostingTypeStatisticalError@ - @Line@=" + lineNo + " - " + accountEV);
		}
		// end BF [2789319] No check of Actual, Budget, Statistical attribute
	}

	@Override
	public final ITaxAccountable asTaxAccountable(final I_GL_JournalLine glJournalLine, final boolean accountSignDR)
	{
		return new GLJournalLineTaxAccountable(glJournalLine, accountSignDR);
	}

	@Override
	public final ITaxAccountable asTaxAccountable(final I_GL_JournalLine glJournalLine)
	{
		Check.assumeNotNull(glJournalLine, "glJournalLine not null");

		if (glJournalLine.isDR_AutoTaxAccount())
		{
			return asTaxAccountable(glJournalLine, true);
		}
		else if (glJournalLine.isCR_AutoTaxAccount())
		{
			return asTaxAccountable(glJournalLine, false);
		}
		else
		{
			throw new AdempiereException("Journal line " + glJournalLine + " is not about tax accounting");
		}
	}

	@Override
	public String getDocumentNo(final I_GL_JournalLine glJournalLine)
	{
		Check.assumeNotNull(glJournalLine, "glJournalLine not null");

		final I_GL_Journal glJournal = glJournalLine.getGL_Journal();
		final I_GL_JournalBatch glJournalBatch = glJournal == null ? null : glJournal.getGL_JournalBatch();

		final TokenizedStringBuilder summary = new TokenizedStringBuilder(" / ")
				.setAutoAppendSeparator(true);

		if (glJournalBatch != null)
		{
			summary.append(glJournalBatch.getDocumentNo());
		}
		if (glJournal != null)
		{
			summary.append(glJournal.getDocumentNo());
		}
		
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(glJournalLine);
		summary.append(msgBL.parseTranslation(ctx, "@Line@ " + glJournalLine.getLine()));

		return summary.toString();
	}
	
	@Override
	public void checkValidSplitAcctTrxFlag(final I_GL_JournalLine glJournalLine)
	{
		//
		// Case: Split accounting transaction
		if (glJournalLine.isSplitAcctTrx())
		{
			// nothing to validate here
		}
		//
		// Case: not a split accouting transaction
		else
		{
			// In this case user shall be allowed to edit both DR and CR sides
			if(!glJournalLine.isAllowAccountDR() || !glJournalLine.isAllowAccountCR())
			{
				throw new AdempiereException("@Invalid@ @" + I_GL_JournalLine.COLUMNNAME_IsSplitAcctTrx + "@");
			}
		}
	}
}
