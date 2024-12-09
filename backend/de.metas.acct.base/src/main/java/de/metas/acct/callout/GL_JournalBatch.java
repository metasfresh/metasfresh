package de.metas.acct.callout;

<<<<<<< HEAD
import java.sql.Timestamp;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_GL_JournalBatch;

import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.util.Services;
=======
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_GL_JournalBatch;

import java.sql.Timestamp;
import java.util.Optional;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Callout(I_GL_JournalBatch.class)
public class GL_JournalBatch
{
<<<<<<< HEAD
=======
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
				.setNewDocType(glJournalBatch.getC_DocType())
=======
				.setNewDocType(getDocType(glJournalBatch).orElse(null))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.setOldDocumentNo(glJournalBatch.getDocumentNo())
				.setDocumentModel(glJournalBatch)
				.buildOrNull();
		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			glJournalBatch.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}

<<<<<<< HEAD
=======
	private Optional<I_C_DocType> getDocType(final I_GL_JournalBatch glJournalBatch)
	{
		return DocTypeId.optionalOfRepoId(glJournalBatch.getC_DocType_ID())
				.map(docTypeBL::getById);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	// Old/missing callouts
	// "GL_JournalBatch";"C_Period_ID";"org.compiere.model.CalloutGLJournal.period"
	// "GL_JournalBatch";"DateAcct";"org.compiere.model.CalloutGLJournal.period"

}
