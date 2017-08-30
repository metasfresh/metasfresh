package de.metas.attachments.impl;

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
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.activation.DataSource;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.compiere.util.Util;

import com.google.common.collect.ImmutableList;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.IAttachmentBL;
import de.metas.attachments.IAttachmentDAO;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

public class AttachmentBL implements IAttachmentBL
{
	@Override
	@NonNull
	public I_AD_Attachment getAttachment(final Object model)
	{
		Check.assumeNotNull(model, "Parameter model is not null");

		if (InterfaceWrapperHelper.isInstanceOf(model, I_AD_Attachment.class))
		{
			return InterfaceWrapperHelper.create(model, I_AD_Attachment.class);
		}

		final Properties ctx;
		final String trxName;
		final int adTableId;
		final int recordId;

		if (model instanceof ITableRecordReference)
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
			attachment = InterfaceWrapperHelper.newInstance(I_AD_Attachment.class, PlainContextAware.newWithTrxName(ctx, trxName));
			attachment.setTitle("."); // fill with something because it's mandatory
			attachment.setAD_Table_ID(adTableId);
			attachment.setRecord_ID(recordId);

			// Not saving here, we will save the attachment just when it's needed
			// attachment.saveEx();
		}

		return attachment;
	}

	private int getAttachmentId(final Object model)
	{
		final I_AD_Attachment attachment = getAttachment(model);
		return attachment.getAD_Attachment_ID();
	}

	@Override
	public void deleteEntryForModel(final Object model, final int id)
	{
		final I_AD_Attachment attachment = getAttachment(model);
		if (attachment == null)
		{
			return;
		}

		deleteEntryById(attachment, id);
	}

	@Override
	public void deleteEntryById(final Object model, final int attachmentEntryId)
	{
		final int attachmentId = getAttachmentId(model);
		Services.get(IAttachmentDAO.class).deleteAttachmentEntryById(attachmentId, attachmentEntryId);
	}

	@Override
	public AttachmentEntry addEntry(@NonNull final Object model, @NonNull final File file)
	{
		return addEntry(model, AttachmentEntryCreateRequest.fromFile(file));
	}

	private AttachmentEntry addEntry(@NonNull final Object model, @NonNull final AttachmentEntryCreateRequest request)
	{
		final String filename = request.getFilename();
		final String contentType = request.getContentType();
		final byte[] data = request.getData();

		//
		// Make sure the attachment is saved
		final I_AD_Attachment attachment = getAttachment(model);
		if (attachment.getAD_Attachment_ID() <= 0)
		{
			InterfaceWrapperHelper.save(attachment);
		}

		//
		// Create entry
		final I_AD_AttachmentEntry entryRecord = InterfaceWrapperHelper.newInstance(I_AD_AttachmentEntry.class);
		entryRecord.setAD_Attachment_ID(attachment.getAD_Attachment_ID());
		entryRecord.setAD_Table_ID(attachment.getAD_Table_ID());
		entryRecord.setRecord_ID(attachment.getRecord_ID());
		//
		entryRecord.setFileName(filename);
		entryRecord.setBinaryData(data);
		entryRecord.setContentType(contentType);
		InterfaceWrapperHelper.save(entryRecord);

		//
		final AttachmentEntry entry = Services.get(IAttachmentDAO.class).toAttachmentEntry(entryRecord);
		return entry;
	}

	@Override
	public List<AttachmentEntry> addEntriesFromFiles(@NonNull final Object model, @NonNull final Collection<File> files)
	{
		if (files.isEmpty())
		{
			return ImmutableList.of();
		}

		final I_AD_Attachment attachment = getAttachment(model);

		final List<AttachmentEntry> entries = files.stream()
				.map(file -> addEntry(attachment, file))
				.collect(ImmutableList.toImmutableList());

		return entries;
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
	public AttachmentEntry addEntry(
			@NonNull final Object model, 
			@NonNull final String name, 
			@NonNull final byte[] data)
	{
		return addEntry(model, AttachmentEntryCreateRequest.builder()
				.filename(name)
				.contentType(MimeType.getMimeType(name))
				.data(data)
				.build());
	}

	@Override
	public List<AttachmentEntry> addEntriesFromDataSources(@NonNull final Object model, final Collection<DataSource> dataSources)
	{
		if (dataSources == null || dataSources.isEmpty())
		{
			return ImmutableList.of();
		}
		
		final I_AD_Attachment attachment = getAttachment(model);

		final List<AttachmentEntry> entries = dataSources.stream()
				.map(dataSource -> AttachmentEntryCreateRequest.fromDataSource(dataSource))
				.map(request -> addEntry(attachment, request))
				.collect(ImmutableList.toImmutableList());

		InterfaceWrapperHelper.save(attachment);

		return entries;
	}

	@Override
	public byte[] getEntryByFilenameAsBytes(final Object model, final String filename)
	{
		final AttachmentEntry entry = getEntryByFilename(model, filename);
		return entry != null ? entry.getData() : null;
	}

	@Override
	public byte[] getEntryByIdAsBytes(final Object model, final int id)
	{
		final AttachmentEntry entry = getEntryById(model, id);
		if (entry == null)
		{
			return null;
		}

		return entry.getData();
	}

	@Override
	public AttachmentEntry getEntryById(@NonNull final Object model, final int attachmentEntryId)
	{
		if (attachmentEntryId <= 0)
		{
			return null;
		}

		final I_AD_Attachment attachment = getAttachment(model);
		final int attachmentId = attachment.getAD_Attachment_ID();
		if (attachmentId <= 0)
		{
			return null;
		}
		return Services.get(IAttachmentDAO.class).retrieveAttachmentEntryById(attachmentId, attachmentEntryId);
	}

	@Override
	public AttachmentEntry getEntryByFilename(final Object model, final String filename)
	{
		final int attachmentId = getAttachmentId(model);
		if(attachmentId <= 0)
		{
			return null;
		}
		return Services.get(IAttachmentDAO.class).retrieveAttachmentEntryByFilename(attachmentId, filename);
	}

	@Override
	public byte[] getFirstEntryAsBytesOrNull(final Object model)
	{
		final int attachmentId = getAttachmentId(model);
		if(attachmentId <= 0)
		{
			return null;
		}
		return Services.get(IAttachmentDAO.class).retrieveFirstAttachmentEntryAsBytes(attachmentId);
	}

	@Override
	public AttachmentEntry getFirstEntry(final Object model)
	{
		final I_AD_Attachment attachment = getAttachment(model);
		final int attachmentId = attachment.getAD_Attachment_ID();
		if (attachmentId <= 0)
		{
			return null;
		}

		return Services.get(IAttachmentDAO.class).retrieveFirstAttachmentEntry(attachmentId);
	}

	@Override
	public List<AttachmentEntry> getEntries(@NonNull final Object model)
	{
		final I_AD_Attachment attachment = getAttachment(model);
		return Services.get(IAttachmentDAO.class).retrieveAttachmentEntries(attachment);
	}

	@Override
	public boolean hasEntries(@NonNull final Object model)
	{
		final I_AD_Attachment attachment = getAttachment(model);
		final int attachmentId = attachment.getAD_Attachment_ID();
		if (attachmentId <= 0)
		{
			return false;
		}

		return Services.get(IAttachmentDAO.class).hasAttachmentEntries(attachmentId);
	}

	@Override
	public void deleteAttachment(@NonNull final Object model)
	{
		final I_AD_Attachment attachment = getAttachment(model);
		final int attachmentId = attachment.getAD_Attachment_ID();
		if (attachmentId <= 0)
		{
			return;
		}

		// TODO: delete entries first
		InterfaceWrapperHelper.delete(attachment);
	}

	@Override
	public void setAttachmentText(@NonNull final Object model, final String textMsg)
	{
		final I_AD_Attachment attachment = getAttachment(model);
		attachment.setTextMsg(textMsg);
		InterfaceWrapperHelper.save(attachment);
	}

	@Value
	@Builder
	private static final class AttachmentEntryCreateRequest
	{
		public static AttachmentEntryCreateRequest fromDataSource(final DataSource dataSource)
		{
			final String filename = dataSource.getName();
			final String contentType = dataSource.getContentType();
			final byte[] data;
			try
			{
				data = Util.readBytes(dataSource.getInputStream());
			}
			catch (final IOException e)
			{
				throw new AdempiereException("Failed reading data from " + dataSource);
			}

			return builder()
					.filename(filename)
					.contentType(contentType)
					.data(data)
					.build();
		}

		public static AttachmentEntryCreateRequest fromFile(final File file)
		{
			final String filename = file.getName();
			final String contentType = MimeType.getMimeType(filename);
			final byte[] data = Util.readBytes(file);

			return builder()
					.filename(filename)
					.contentType(contentType)
					.data(data)
					.build();
		}

		private final String filename;
		private final String contentType;
		private final byte[] data;
	}
}
