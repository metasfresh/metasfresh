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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.activation.DataSource;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IAttachmentBL;
import org.adempiere.service.IAttachmentDAO;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

public class AttachmentBL implements IAttachmentBL
{
	@Override
	public I_AD_Attachment getAttachment(final Object model)
	{
		Check.assumeNotNull(model, "Parameter model is not null");

		final Properties ctx;
		final String trxName;
		final int adTableId;
		final int recordId;

		if(model instanceof ITableRecordReference)
		{
			final ITableRecordReference recordRef = (ITableRecordReference)model;
			ctx = Env.getCtx();
			trxName = ITrx.TRXNAME_ThreadInherited;
			adTableId = recordRef.getAD_Table_ID();
			recordId = recordRef.getRecord_ID();
		}
		else
		{
			ctx = InterfaceWrapperHelper.getCtx(model);
			trxName = InterfaceWrapperHelper.getTrxName(model);
	
			final String tableName = InterfaceWrapperHelper.getModelTableName(model);
			adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
			recordId = InterfaceWrapperHelper.getId(model);
		}

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
			// attachment.saveEx();
		}

		return attachment;
	}

	@Override
	public List<MAttachmentEntry> getEntiresForModel(final Object model)
	{
		final MAttachment attachment = LegacyAdapters.convertToPO(getAttachment(model));
		return attachment == null ? ImmutableList.of() : attachment.getEntriesAsList();
	}

	@Override
	public MAttachmentEntry getEntryForModelById(final Object model, final int id)
	{
		final MAttachment attachment = LegacyAdapters.convertToPO(getAttachment(model));
		return attachment == null ? null : attachment.getEntryById(id);
	}

	@Override
	public void deleteEntryForModel(final Object model, final int id)
	{
		final MAttachment attachment = LegacyAdapters.convertToPO(getAttachment(model));
		if (attachment == null)
		{
			return;
		}

		attachment.deleteEntryById(id);
		InterfaceWrapperHelper.save(attachment);
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
		attachmentPO.addEntry(name, data);

		InterfaceWrapperHelper.save(attachment);
	}

	@Override
	public void addEntries(final I_AD_Attachment attachment, final Collection<DataSource> dataSources)
	{
		Check.assumeNotNull(attachment, "Parameter attachment is not null");
		if (dataSources == null || dataSources.isEmpty())
		{
			return;
		}

		if (attachment.getAD_Attachment_ID() <= 0)
		{
			InterfaceWrapperHelper.save(attachment);
		}

		final MAttachment attachmentPO = InterfaceWrapperHelper.getPO(attachment);
		dataSources.forEach(attachmentPO::addEntry);
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
