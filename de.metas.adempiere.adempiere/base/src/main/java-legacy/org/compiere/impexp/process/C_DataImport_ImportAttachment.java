package org.compiere.impexp.process;

import org.compiere.SpringContextHolder;
import org.compiere.impexp.DataImportConfigId;
import org.compiere.impexp.DataImportRequest;
import org.compiere.impexp.DataImportService;
import org.compiere.model.I_AD_AttachmentEntry;
import org.fusesource.hawtbuf.ByteArrayInputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;

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
	protected String doIt()
	{
		final String result = dataImportService.importData(DataImportRequest.builder()
				.data(getData())
				.dataImportConfigId(getDataImportConfigId())
				.clientId(getClientId())
				.orgId(getOrgId())
				.userId(getUserId())
				.build());

		deleteAttachmentEntry();

		return result;
	}

	private AttachmentEntryId getAttachmentEntryId()
	{
		return p_AD_AttachmentEntry_ID;
	}

	private DataImportConfigId getDataImportConfigId()
	{
		return DataImportConfigId.ofRepoId(getRecord_ID());
	}

	private Resource getData()
	{
		final byte[] data = attachmentEntryService.retrieveData(getAttachmentEntryId());
		return new InputStreamResource(new ByteArrayInputStream(data));
	}

	private void deleteAttachmentEntry()
	{
		final AttachmentEntry attachmentEntry = attachmentEntryService.getById(getAttachmentEntryId());
		attachmentEntryService.unattach(getDataImportConfigId().toRecordRef(), attachmentEntry);
	}

}
