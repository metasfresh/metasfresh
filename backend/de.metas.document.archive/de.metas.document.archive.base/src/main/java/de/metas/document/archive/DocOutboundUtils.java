package de.metas.document.archive;

import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Properties;

import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocumentBL;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public class DocOutboundUtils
{
	public I_C_Doc_Outbound_Log_Line createOutboundLogLineRecord(@NonNull final I_C_Doc_Outbound_Log docOutboundLog)
	{
		Check.assume(!isNew(docOutboundLog), "The given docOutboundLog needs to be saved; docOutboundLog={}", docOutboundLog);

		final Properties ctx = getCtx(docOutboundLog);

		final I_C_Doc_Outbound_Log_Line docOutboundLogLineRecord = newInstance(I_C_Doc_Outbound_Log_Line.class);

		docOutboundLogLineRecord.setC_Doc_Outbound_Log_ID(docOutboundLog.getC_Doc_Outbound_Log_ID());
		docOutboundLogLineRecord.setAD_Org_ID(docOutboundLog.getAD_Org_ID());
		docOutboundLogLineRecord.setAD_Table_ID(docOutboundLog.getAD_Table_ID());
		docOutboundLogLineRecord.setRecord_ID(docOutboundLog.getRecord_ID());

		final IDocumentBL documentBL = Services.get(IDocumentBL.class);

		final String documentNo = documentBL.getDocumentNo(ctx, docOutboundLog.getAD_Table_ID(), docOutboundLog.getRecord_ID());
		docOutboundLogLineRecord.setDocumentNo(documentNo);

		final TableRecordReference reference = TableRecordReference.ofReferenced(docOutboundLog);
		final DocStatus docStatus = documentBL.getDocStatusOrNull(reference);
		docOutboundLogLineRecord.setDocStatus(DocStatus.toCodeOrNull(docStatus));

		DocTypeId doctypeID = Services.get(IArchiveBL.class).getOverrideDocTypeId(ArchiveId.ofRepoId(docOutboundLog.getAD_Archive_ID()));

		if (doctypeID == null)
		{
			doctypeID = DocTypeId.ofRepoId(documentBL.getC_DocType_ID(ctx, docOutboundLog.getAD_Table_ID(), docOutboundLog.getRecord_ID()));
		}

		docOutboundLogLineRecord.setC_DocType_ID(doctypeID.getRepoId());

		return docOutboundLogLineRecord;
	}
}
