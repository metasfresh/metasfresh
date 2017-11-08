package de.metas.attachments.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.X_AD_AttachmentEntry;
import org.compiere.util.MimeType;
import org.slf4j.Logger;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryType;
import de.metas.attachments.IAttachmentDAO;
import de.metas.logging.LogManager;
import lombok.NonNull;

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

public class AttachmentDAO implements IAttachmentDAO
{
	private static final transient Logger logger = LogManager.getLogger(AttachmentDAO.class);

	@Override
	public I_AD_Attachment retrieveAttachment(final Properties ctx, final int adTableId, final int recordId, final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Attachment.class, ctx, trxName)
				.addEqualsFilter(I_AD_Attachment.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_AD_Attachment.COLUMNNAME_Record_ID, recordId)
				.create()
				.firstOnly(I_AD_Attachment.class);
	}

	@Override
	public List<AttachmentEntry> retrieveAttachmentEntries(@NonNull final I_AD_Attachment attachment)
	{
		// Convert legacy LOB data to entries and return it
		if (attachment.getBinaryData() != null)
		{
			return convertAttachmentLOBToEntries(attachment);
		}
		// Standard case: retrieve entries from AD_AttachmentEntry table
		else
		{
			final int attachmentId = attachment.getAD_Attachment_ID();
			return Services.get(IQueryBL.class).createQueryBuilder(I_AD_AttachmentEntry.class)
					.addEqualsFilter(I_AD_AttachmentEntry.COLUMNNAME_AD_Attachment_ID, attachmentId)
					.orderBy().addColumn(I_AD_AttachmentEntry.COLUMN_AD_AttachmentEntry_ID).endOrderBy()
					.create()
					.stream(I_AD_AttachmentEntry.class)
					.map(this::toAttachmentEntryOrNullIfError)
					.filter(entry -> entry != null)
					.collect(ImmutableList.toImmutableList());
		}
	}

	@Override
	public AttachmentEntry retrieveAttachmentEntryById(final int attachmentId, final int attachmentEntryId)
	{
		final I_AD_AttachmentEntry entryRecord = InterfaceWrapperHelper.load(attachmentEntryId, I_AD_AttachmentEntry.class);

		// Make sure the attachmentId is matching
		// NOTE: because some BLs are not aware of attachmentId but only about attachmentEntryId, we are validating only when provided
		if (attachmentId > 0 && entryRecord.getAD_Attachment_ID() != attachmentId)
		{
			throw new AdempiereException("Attachment entry is not matching the attachmentId"); // shall not happen
		}
		return toAttachmentEntry(entryRecord);
	}

	@Override
	public AttachmentEntry retrieveAttachmentEntryByFilename(final int attachmentId, final String filename)
	{
		final I_AD_AttachmentEntry entryRecord = Services.get(IQueryBL.class).createQueryBuilder(I_AD_AttachmentEntry.class)
				.addEqualsFilter(I_AD_AttachmentEntry.COLUMNNAME_AD_Attachment_ID, attachmentId)
				.addEqualsFilter(I_AD_AttachmentEntry.COLUMNNAME_FileName, filename)
				.orderBy().addColumn(I_AD_AttachmentEntry.COLUMN_AD_AttachmentEntry_ID).endOrderBy()
				.create()
				.first(I_AD_AttachmentEntry.class);

		if (entryRecord == null)
		{
			return null;
		}
		else
		{
			return toAttachmentEntry(entryRecord);
		}
	}

	@Override
	public byte[] retrieveFirstAttachmentEntryAsBytes(final int attachmentId)
	{
		final AttachmentEntry entry = retrieveFirstAttachmentEntry(attachmentId);
		if (entry == null)
		{
			return null;
		}
		return retrieveData(entry);
	}

	@Override
	public AttachmentEntry retrieveFirstAttachmentEntry(final int attachmentId)
	{
		final I_AD_AttachmentEntry entryRecord = Services.get(IQueryBL.class).createQueryBuilder(I_AD_AttachmentEntry.class)
				.addEqualsFilter(I_AD_AttachmentEntry.COLUMNNAME_AD_Attachment_ID, attachmentId)
				.orderBy().addColumn(I_AD_AttachmentEntry.COLUMN_AD_AttachmentEntry_ID).endOrderBy()
				.create()
				.first(I_AD_AttachmentEntry.class);

		if (entryRecord == null)
		{
			return null;
		}
		else
		{
			return toAttachmentEntry(entryRecord);
		}
	}

	@Override
	public AttachmentEntry toAttachmentEntry(@NonNull final I_AD_AttachmentEntry entryRecord)
	{
		return AttachmentEntry.builder()
				.id(entryRecord.getAD_AttachmentEntry_ID())
				.name(entryRecord.getFileName())
				.type(toAttachmentEntryTypeFromADRefListValue(entryRecord.getType()))
				.filename(entryRecord.getFileName())
				.contentType(entryRecord.getContentType())
				.url(extractURI(entryRecord))
				.build();
	}

	private AttachmentEntry toAttachmentEntryOrNullIfError(@NonNull final I_AD_AttachmentEntry entryRecord)
	{
		try
		{
			return toAttachmentEntry(entryRecord);
		}
		catch (Exception ex)
		{
			logger.warn("Cannot convert {} to attachment entry. Returning null", entryRecord, ex);
			return null;
		}
	}

	private static final URI extractURI(final I_AD_AttachmentEntry entryRecord)
	{
		final String url = entryRecord.getURL();
		if (Check.isEmpty(url, true))
		{
			return null;
		}

		try
		{
			return new URI(url);
		}
		catch (URISyntaxException ex)
		{
			throw new AdempiereException("Invalid URL: " + url, ex)
					.setParameter("entryRecord", entryRecord);
		}
	}

	private static final AttachmentEntryType toAttachmentEntryTypeFromADRefListValue(final String adRefListValue)
	{
		final AttachmentEntryType type = adRefListValue2attachmentEntryType.get(adRefListValue);
		if (type == null)
		{
			throw new IllegalArgumentException("No " + AttachmentEntryType.class + " found for " + adRefListValue);
		}
		return type;
	}

	private static final BiMap<String, AttachmentEntryType> adRefListValue2attachmentEntryType = ImmutableBiMap.<String, AttachmentEntryType> builder()
			.put(X_AD_AttachmentEntry.TYPE_Data, AttachmentEntryType.Data)
			.put(X_AD_AttachmentEntry.TYPE_URL, AttachmentEntryType.URL)
			.build();

	@Override
	public void saveAttachmentEntryData(final AttachmentEntry entry, final byte[] data)
	{
		final I_AD_AttachmentEntry entryRecord = InterfaceWrapperHelper.load(entry.getId(), I_AD_AttachmentEntry.class);
		if (X_AD_AttachmentEntry.TYPE_Data.equals(entryRecord.getType()))
		{
			throw new AdempiereException("Only entries of type Data support attaching data").setParameter("entry", entry);
		}

		entryRecord.setBinaryData(data);
		InterfaceWrapperHelper.save(entryRecord);
	}

	@Override
	public boolean deleteAttachmentEntryById(final int attachmentId, final int attachmentEntryId)
	{
		final int deleteCount = Services.get(IQueryBL.class).createQueryBuilder(I_AD_AttachmentEntry.class)
				.addEqualsFilter(I_AD_AttachmentEntry.COLUMNNAME_AD_Attachment_ID, attachmentId)
				.addEqualsFilter(I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID, attachmentEntryId)
				.create()
				.delete();
		return deleteCount > 0;
	}

	@Override
	public boolean hasAttachmentEntries(final int attachmentId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_AttachmentEntry.class)
				.addEqualsFilter(I_AD_AttachmentEntry.COLUMNNAME_AD_Attachment_ID, attachmentId)
				.create()
				.match();
	}

	private List<AttachmentEntry> convertAttachmentLOBToEntries(final I_AD_Attachment attachment)
	{
		final byte[] data = attachment.getBinaryData();
		if (data == null)
		{
			// already converted
			return new ArrayList<>();
		}

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(() -> {
			attachment.setBinaryData(null);
			InterfaceWrapperHelper.save(attachment, ITrx.TRXNAME_ThreadInherited);

			final List<AttachmentEntry> entries = new ArrayList<>();
			if (data.length == 0)
			{
				return entries;
			}

			final String INDEX_Filename = ".index";
			try
			{
				final ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(data));
				ZipEntry zipEntry = zip.getNextEntry();
				while (zipEntry != null)
				{
					final String name = zipEntry.getName();

					final ByteArrayOutputStream out = new ByteArrayOutputStream();
					final byte[] buffer = new byte[2048];
					int length = zip.read(buffer);
					while (length != -1)
					{
						out.write(buffer, 0, length);
						length = zip.read(buffer);
					}
					final byte[] dataEntry = out.toByteArray();

					if (INDEX_Filename.equals(name))
					{
						// nothing
					}
					else
					{
						final String filename = new File(name).getName();
						final String contentType = MimeType.getMimeType(filename);

						final I_AD_AttachmentEntry entryRecord = InterfaceWrapperHelper.newInstance(I_AD_AttachmentEntry.class);
						entryRecord.setAD_Attachment_ID(attachment.getAD_Attachment_ID());
						entryRecord.setAD_Table_ID(attachment.getAD_Table_ID());
						entryRecord.setRecord_ID(attachment.getRecord_ID());

						entryRecord.setFileName(filename);
						entryRecord.setBinaryData(dataEntry);
						entryRecord.setContentType(contentType);
						InterfaceWrapperHelper.save(entryRecord);

						final AttachmentEntry entry = toAttachmentEntry(entryRecord);
						entries.add(entry);
					}

					//
					zipEntry = zip.getNextEntry();
				}

				return entries;
			}
			catch (Exception ex)
			{
				throw new AdempiereException("Failed convering legacy LOB attachments to entries", ex);
			}
		});
	}

	@Override
	public byte[] retrieveData(@NonNull final AttachmentEntry entry)
	{
		final int entryId = entry.getId();
		if (entryId <= 0)
		{
			return null;
		}

		final I_AD_AttachmentEntry entryRecord = InterfaceWrapperHelper.load(entryId, I_AD_AttachmentEntry.class);
		if (entryRecord == null)
		{
			return null;
		}

		return entryRecord.getBinaryData();
	}
}
