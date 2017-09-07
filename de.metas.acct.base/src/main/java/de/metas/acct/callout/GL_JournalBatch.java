package de.metas.acct.callout;

import java.sql.Timestamp;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.util.Services;
import org.compiere.model.I_GL_JournalBatch;

import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.documentNo.impl.IDocumentNoInfo;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Callout(I_GL_JournalBatch.class)
public class GL_JournalBatch
{
	@CalloutMethod(columnNames = I_GL_JournalBatch.COLUMNNAME_DateDoc)
	public void onDateDoc(final I_GL_JournalBatch glJournalBatch)
	{
		final Timestamp dateDoc = glJournalBatch.getDateDoc();
		if (dateDoc == null)
		{
			return;
		}

		glJournalBatch.setDateAcct(dateDoc);
	}

	@CalloutMethod(columnNames = I_GL_JournalBatch.COLUMNNAME_C_DocType_ID)
	public void onC_DocType_ID(final I_GL_JournalBatch glJournalBatch)
	{
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(glJournalBatch.getC_DocType())
				.setOldDocumentNo(glJournalBatch.getDocumentNo())
				.setDocumentModel(glJournalBatch)
				.buildOrNull();
		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			glJournalBatch.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

	// Old/missing callouts
	// "GL_JournalBatch";"C_Period_ID";"org.compiere.model.CalloutGLJournal.period"
	// "GL_JournalBatch";"DateAcct";"org.compiere.model.CalloutGLJournal.period"

}
