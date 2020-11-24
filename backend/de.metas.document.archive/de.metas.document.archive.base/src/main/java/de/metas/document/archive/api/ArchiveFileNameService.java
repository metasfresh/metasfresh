/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.document.archive.api;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import org.compiere.model.I_AD_Archive;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_DocType;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.StringJoiner;

@Service
public class ArchiveFileNameService
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	public String computePdfFileName(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final ComputeFileNameRequest request = ComputeFileNameRequest.builder()
				.docTypeId(DocTypeId.ofRepoIdOrNull(docOutboundLogRecord.getC_DocType_ID()))
				.documentNo(docOutboundLogRecord.getDocumentNo())
				.orgId(OrgId.ofRepoIdOrNull(docOutboundLogRecord.getAD_Org_ID()))
				.recordReference(TableRecordReference.ofReferenced(docOutboundLogRecord))
				.build();

		return computePdfFileName(request);
	}

	public String computePdfFileName(@NonNull final I_AD_Archive archiveRecord)
	{
		final TableRecordReference recordReference = TableRecordReference.ofReferenced(archiveRecord);
		final Object referencedRecord = TableRecordReference.ofReferenced(archiveRecord).getModel(Object.class);
		final IDocument documentNorNull = Services.get(IDocumentBL.class).getDocumentOrNull(referencedRecord);

		final ComputeFileNameRequest.ComputeFileNameRequestBuilder request = ComputeFileNameRequest.builder();

		if (documentNorNull != null)
		{
			request.documentNo(documentNorNull.getDocumentNo());

			final I_C_DocType docType = Services.get(IDocumentBL.class).getDocTypeOrNull(referencedRecord);
			if (docType != null)
			{
				request.docTypeId(DocTypeId.ofRepoIdOrNull(docType.getC_DocType_ID()));
			}

		}

		request.orgId(OrgId.ofRepoIdOrNull(archiveRecord.getAD_Org_ID()))
				.recordReference(recordReference);

		return computePdfFileName(request.build());
	}

	public String computePdfFileName(@NonNull final ComputeFileNameRequest computeFileNameRequest)
	{
		final StringJoiner fileNameParts = new StringJoiner("-");

		if (computeFileNameRequest.getOrgId() != null && computeFileNameRequest.getOrgId().isRegular())
		{
			final I_AD_Org orgRecord = orgDAO.getById(computeFileNameRequest.getOrgId());
			fileNameParts.add(orgRecord.getName());
		}

		if (computeFileNameRequest.getDocTypeId() != null)
		{
			final I_C_DocType docTypeRecord = docTypeDAO.getById(computeFileNameRequest.getDocTypeId());
			final I_C_DocType docTypeRecordTrl = InterfaceWrapperHelper.translate(docTypeRecord, I_C_DocType.class);
			fileNameParts.add(docTypeRecordTrl.getName());
		}
		else
		{
			final I_AD_Table tableRecord = tableDAO.retrieveTable(computeFileNameRequest.getRecordReference().getAdTableId());
			final I_AD_Table tableRecordTrl = InterfaceWrapperHelper.translate(tableRecord, I_AD_Table.class);
			fileNameParts.add(tableRecordTrl.getName());
		}

		if (Check.isNotBlank(computeFileNameRequest.getDocumentNo()))
		{
			fileNameParts.add(computeFileNameRequest.getDocumentNo());
		}
		else
		{
			fileNameParts.add(Integer.toString(computeFileNameRequest.getRecordReference().getRecord_ID()));
		}

		final String pdfFileName = fileNameParts.toString();
		return pdfFileName + ".pdf";
	}

	@Value
	@Builder
	public static class ComputeFileNameRequest
	{
		@Nullable
		OrgId orgId;

		@Nullable
		DocTypeId docTypeId;

		@NonNull TableRecordReference recordReference;

		@Nullable
		String documentNo;
	}
}



