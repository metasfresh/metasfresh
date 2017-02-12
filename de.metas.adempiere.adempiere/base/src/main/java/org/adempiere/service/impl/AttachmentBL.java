package org.adempiere.service.impl;

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


import java.io.File;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IAttachmentBL;
import org.adempiere.service.IAttachmentDAO;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;

public class AttachmentBL implements IAttachmentBL
{
	@Override
	public I_AD_Attachment getAttachment(final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final int recordId = InterfaceWrapperHelper.getId(model);

		I_AD_Attachment attachment = Services.get(IAttachmentDAO.class).retrieveAttachment(ctx, adTableId, recordId, trxName);
		if (attachment == null)
		{
			if (Adempiere.isUnitTestMode())
			{
				attachment = InterfaceWrapperHelper.newInstance(I_AD_Attachment.class, model);
				attachment.setAD_Table_ID(adTableId);
				attachment.setRecord_ID(recordId);
			}
			else
			{
				// FIXME: if we are not in test mode we need to use the old M class because attachment storage is handled there 
				attachment = new MAttachment(ctx, adTableId, recordId, trxName);
			}
			
			// Not saving here, we will save the attachment just when it's needed
			//attachment.saveEx();
		}

		return attachment;
	}

	@Override
	public void addEntry(final I_AD_Attachment attachment, final File file)
	{
		if (file == null)
		{
			return;
		}

		if (attachment.getAD_Attachment_ID() <= 0)
		{
			InterfaceWrapperHelper.save(attachment);
		}

		final MAttachment attachmentPO = InterfaceWrapperHelper.getPO(attachment);
		attachmentPO.addEntry(file);

		InterfaceWrapperHelper.save(attachment);
	}

	@Override
	public I_AD_Attachment createAttachment(final Object model, final File file)
	{
		final I_AD_Attachment attachment = getAttachment(model);
		addEntry(attachment, file);

		return attachment;
	}
	
	@Override
	public I_AD_Attachment createAttachment(final Object model, final String name, final byte[] data)
	{
		final I_AD_Attachment attachment = getAttachment(model);
		addEntry(attachment, name, data);
		return attachment;
	}


	@Override
	public void addEntry(final I_AD_Attachment attachment, final String name, final byte[] data)
	{
		if (data == null)
		{
			return;
		}

		if (attachment.getAD_Attachment_ID() <= 0)
		{
			InterfaceWrapperHelper.save(attachment);
		}

		final MAttachment attachmentPO = InterfaceWrapperHelper.getPO(attachment);
		attachmentPO.addEntry(new MAttachmentEntry(name, data));

		InterfaceWrapperHelper.save(attachment);
	}

	@Override
	public byte[] getEntryAsBytes(final I_AD_Attachment attachment, final String name)
	{
		final MAttachment attachmentPO = InterfaceWrapperHelper.getPO(attachment);
		for (final MAttachmentEntry entry : attachmentPO.getEntries())
		{
			if (name.equals(entry.getName()))
			{
				return entry.getData();
			}
		}

		return null;
	}
	
	@Override
	public byte[] getFirstEntryAsBytesOrNull(final I_AD_Attachment attachment)
	{
		final MAttachment attachmentPO = InterfaceWrapperHelper.getPO(attachment);
		for (final MAttachmentEntry entry : attachmentPO.getEntries())
		{

			return entry.getData();
		}

		return null;
	}
}
