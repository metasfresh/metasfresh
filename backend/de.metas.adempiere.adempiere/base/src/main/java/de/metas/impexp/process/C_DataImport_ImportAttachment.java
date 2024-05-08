package de.metas.impexp.process;

import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_AttachmentEntry;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryDataResource;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.impexp.DataImportRequest;
import de.metas.impexp.DataImportResult;
import de.metas.impexp.DataImportService;
import de.metas.impexp.InsertIntoImportTableResult;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class C_DataImport_ImportAttachment extends JavaProcess implements IProcessPrecondition
{
	private final transient AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final transient DataImportService dataImportService = SpringContextHolder.instance.getBean(DataImportService.class);

	@Param(parameterName = I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID, mandatory = true)
	private AttachmentEntryId p_AD_AttachmentEntry_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx // dataImportService comes with its own trx-management
	protected String doIt()
	{
		final AttachmentEntryDataResource data = attachmentEntryService.retrieveDataResource(getAttachmentEntryId());

		final DataImportResult result = dataImportService.importDataFromResource(DataImportRequest.builder()
				.data(data)
				.dataImportConfigId(getDataImportConfigId())
				.clientId(getClientId())
				.orgId(getOrgId())
				.userId(getUserId())
				.additionalParameters(getParameterAsIParams())
				.build());

		deleteAttachmentEntry();

		return toSummaryString(result);
	}

	private String toSummaryString(final DataImportResult importResult)
	{
		final StringBuilder result = new StringBuilder();
		result.append("@IsImportScheduled@");

		final InsertIntoImportTableResult insertIntoImportTable = importResult.getInsertIntoImportTable();
		if (insertIntoImportTable != null)
		{
			result.append("#").append(insertIntoImportTable.getCountValidRows())
					.append(", @IsError@ #").append(insertIntoImportTable.getErrors().size());
		}

		result.append(" (took " + importResult.getDuration() + ")");
		return result.toString();
	}

	private AttachmentEntryId getAttachmentEntryId()
	{
		return p_AD_AttachmentEntry_ID;
	}

	private DataImportConfigId getDataImportConfigId()
	{
		return DataImportConfigId.ofRepoId(getRecord_ID());
	}

	private void deleteAttachmentEntry()
	{
		final AttachmentEntry attachmentEntry = attachmentEntryService.getById(getAttachmentEntryId());
		attachmentEntryService.unattach(getDataImportConfigId().toRecordRef(), attachmentEntry);
	}

}
