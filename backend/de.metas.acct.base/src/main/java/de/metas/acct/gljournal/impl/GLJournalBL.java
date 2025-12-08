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

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.gljournal.IGLJournalBL;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.MPeriod;
import org.compiere.model.X_GL_Journal;

import java.util.List;
import java.util.Properties;

public class GLJournalBL implements IGLJournalBL
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private static final AdMessageKey MSG_HeaderAndLinePeriodMismatchError = AdMessageKey.of("GL_Journal.HeaderAndLinePeriodMismatch");

	@Override
	public boolean isComplete(final I_GL_Journal glJournal)
	{
		final String ds = glJournal.getDocStatus();
		return X_GL_Journal.DOCSTATUS_Completed.equals(ds)
				|| X_GL_Journal.DOCSTATUS_Closed.equals(ds)
				|| X_GL_Journal.DOCSTATUS_Reversed.equals(ds);
	}

	@Override
	public void unpost(final I_GL_Journal glJournal)
	{
		// Make sure the period is open
		final Properties ctx = InterfaceWrapperHelper.getCtx(glJournal);
		MPeriod.testPeriodOpen(ctx, glJournal.getDateAcct(), glJournal.getC_DocType_ID(), glJournal.getAD_Org_ID());

		Services.get(IFactAcctDAO.class).deleteForDocumentModel(glJournal);

		glJournal.setPosted(false);
		InterfaceWrapperHelper.save(glJournal);
	}

	@Override
	public DocTypeId getDocTypeGLJournal(@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.adClientId(clientId.getRepoId())
				.adOrgId(orgId.getRepoId())
				.docBaseType(DocBaseType.GLJournal)
				.build();

		return docTypeDAO
				.getDocTypeId(docTypeQuery);
	}

	@Override
	public void assertSamePeriod(
			@NonNull final I_GL_Journal journal,
			@NonNull final I_GL_JournalLine line)
	{
		assertSamePeriod(journal, ImmutableList.of(line));
	}

	@Override
	public void assertSamePeriod(
			@NonNull final I_GL_Journal journal,
			@NonNull final List<I_GL_JournalLine> lines)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(journal);
		final int headerPeriodId = MPeriod.getOrFail(ctx, journal.getDateAcct(), journal.getAD_Org_ID()).getC_Period_ID();

		for (final I_GL_JournalLine line : lines)
		{
			final int linePeriodId = MPeriod.getOrFail(ctx, line.getDateAcct(), line.getAD_Org_ID()).getC_Period_ID();
			if (headerPeriodId != linePeriodId)
			{
				throw new AdempiereException(MSG_HeaderAndLinePeriodMismatchError, line.getLine());
			}
		}
	}

}
