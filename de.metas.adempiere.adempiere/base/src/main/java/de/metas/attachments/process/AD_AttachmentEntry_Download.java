package de.metas.attachments.process;

import org.adempiere.util.Services;
import org.compiere.model.I_AD_AttachmentEntry;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.IAttachmentDAO;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class AD_AttachmentEntry_Download extends JavaProcess
{
	private final transient IAttachmentDAO attachmentDAO = Services.get(IAttachmentDAO.class);
	
	@Override
	protected String doIt()
	{
		final I_AD_AttachmentEntry entryRecord = getRecord(I_AD_AttachmentEntry.class);
		final AttachmentEntry entry = attachmentDAO.toAttachmentEntry(entryRecord);
		final byte[] data = attachmentDAO.retrieveData(entry);

		getResult().setReportData(data, entry.getFilename(), entry.getContentType());

		return MSG_OK;
	}

}
