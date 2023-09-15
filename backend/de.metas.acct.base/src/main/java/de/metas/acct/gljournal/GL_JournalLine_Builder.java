package de.metas.acct.gljournal;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountBL;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.MAccount;
import org.compiere.model.X_GL_JournalLine;

import java.math.BigDecimal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class GL_JournalLine_Builder
{
	private final transient IAccountBL accountBL = Services.get(IAccountBL.class);

	private final I_GL_JournalLine glJournalLine;
	private final GL_Journal_Builder glJournalBuilder;

	GL_JournalLine_Builder(final GL_Journal_Builder glJournalBuilder)
	{
		super();
		Check.assumeNotNull(glJournalBuilder, "glJournalBuilder not null");
		this.glJournalBuilder = glJournalBuilder;
		final I_GL_Journal glJournal = glJournalBuilder.getGL_Journal();

		glJournalLine = InterfaceWrapperHelper.newInstance(I_GL_JournalLine.class, glJournal);
		glJournalLine.setAD_Org_ID(glJournal.getAD_Org_ID());
		glJournalLine.setGL_Journal(glJournal);
		glJournalLine.setDateAcct(glJournal.getDateAcct());
		glJournalLine.setC_Currency_ID(glJournal.getC_Currency_ID());
		glJournalLine.setC_ConversionType_ID(glJournal.getC_ConversionType_ID());
		glJournalLine.setType(X_GL_JournalLine.TYPE_Normal);

		glJournalLine.setIsAllowAccountDR(true);
		glJournalLine.setIsAllowAccountCR(true);
		glJournalLine.setIsSplitAcctTrx(false);
		// glJournalLine.setLine(Line); // will be set on save
		// glJournalLine.setGL_JournalLine_Group(GL_JournalLine_Group); // will be set on save

	}

	public I_GL_JournalLine build()
	{
		glJournalLine.setGL_Journal(getGL_Journal());
		InterfaceWrapperHelper.save(glJournalLine);
		return glJournalLine;
	}

	public GL_Journal_Builder endLine()
	{
		return glJournalBuilder;
	}

	private I_GL_Journal getGL_Journal()
	{
		return glJournalBuilder.getGL_Journal();
	}

	public GL_JournalLine_Builder setAccountDR(final I_C_ElementValue accountDR)
	{
		final I_C_ValidCombination vc = createValidCombination(accountDR);
		setAccountDR(vc);
		return this;
	}

	public GL_JournalLine_Builder setAccountDR(final I_C_ValidCombination vc)
	{
		glJournalLine.setAccount_DR(vc);
		return this;
	}

	public GL_JournalLine_Builder setAccountDR(final AccountId accountId)
	{
		glJournalLine.setAccount_DR_ID(accountId.getRepoId());
		return this;
	}

	public GL_JournalLine_Builder setAccountCR(final I_C_ElementValue accountCR)
	{
		final I_C_ValidCombination vc = createValidCombination(accountCR);
		setAccountCR(vc);
		return this;
	}

	public GL_JournalLine_Builder setAccountCR(final I_C_ValidCombination vc)
	{
		glJournalLine.setAccount_CR(vc);
		return this;
	}

	public GL_JournalLine_Builder setAccountCR(final AccountId accountId)
	{
		glJournalLine.setAccount_CR_ID(accountId.getRepoId());
		return this;
	}

	public GL_JournalLine_Builder setAmount(final BigDecimal amount)
	{
		glJournalLine.setAmtSourceDr(amount);
		glJournalLine.setAmtSourceCr(amount);
		// NOTE: AmtAcctDr/Cr will be set on before save
		return this;
	}

	private final I_C_ValidCombination createValidCombination(final I_C_ElementValue ev)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(getGL_Journal().getC_AcctSchema_ID());
		final AccountDimension dim = accountBL.createAccountDimension(ev, acctSchemaId);
		return MAccount.get(dim);
	}
}
