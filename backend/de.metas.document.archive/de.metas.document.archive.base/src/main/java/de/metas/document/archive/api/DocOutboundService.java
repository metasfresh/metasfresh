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

import de.metas.document.IDocTypeDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.organization.IOrgDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_DocType;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

import static de.metas.util.Check.isEmpty;

@Service
public class DocOutboundService
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	public String computePdfFileName(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final StringJoiner fileNameParts = new StringJoiner("-");

		if (docOutboundLogRecord.getAD_Org_ID() > 0)
		{
			final I_AD_Org orgRecord = orgDAO.getById(docOutboundLogRecord.getAD_Org_ID());
			fileNameParts.add(orgRecord.getName());
		}

		if (docOutboundLogRecord.getC_DocType_ID() > 0)
		{
			final I_C_DocType docTypeRecord = docTypeDAO.getById(docOutboundLogRecord.getC_DocType_ID());
			final I_C_DocType docTypeRecordTrl = InterfaceWrapperHelper.translate(docTypeRecord, I_C_DocType.class);
			fileNameParts.add(docTypeRecordTrl.getName());
		}
		else
		{
			final I_AD_Table tableRecord = tableDAO.retrieveTable(AdTableId.ofRepoId(docOutboundLogRecord.getAD_Table_ID()));
			final I_AD_Table tableRecordTrl = InterfaceWrapperHelper.translate(tableRecord, I_AD_Table.class);
			fileNameParts.add(tableRecordTrl.getName());
		}

		if (!isEmpty(docOutboundLogRecord.getDocumentNo(), true))
		{
			fileNameParts.add(docOutboundLogRecord.getDocumentNo());
		}
		else
		{
			fileNameParts.add(Integer.toString(docOutboundLogRecord.getRecord_ID()));
		}

		final String pdfFileName = fileNameParts.toString();
		return pdfFileName + ".pdf";
	}
}
