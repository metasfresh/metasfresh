package de.metas.attachments;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.FileUtil;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.I_AD_Attachment_MultiRef;
import org.compiere.model.X_AD_AttachmentEntry;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

@RequiredArgsConstructor
@Repository
class AttachmentEntryRepository
{
	private final AttachmentEntryFactory attachmentEntryFactory;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AttachmentEntry getById(@NonNull final AttachmentEntryId id)
	{
		final I_AD_AttachmentEntry attachmentEntryRecord = retrieveAttachmentEntryRecordOutOfTrx(id);
		return forRecord(attachmentEntryRecord);
	}

	public ImmutableList<AttachmentEntry> getByIds(@NonNull final ImmutableSet<AttachmentEntryId> entryIds)
	{
		final ImmutableList<I_AD_AttachmentEntry> iAdAttachmentEntryRecords = InterfaceWrapperHelper.loadByRepoIdAwares(entryIds, I_AD_AttachmentEntry.class);
		return CollectionUtils.map(iAdAttachmentEntryRecords, this::forRecord);
	}
	
	private I_AD_AttachmentEntry retrieveAttachmentEntryRecordInTrx(@NonNull final AttachmentEntryId attachmentEntryId)
	{
		return load(attachmentEntryId, I_AD_AttachmentEntry.class);
	}

	private I_AD_AttachmentEntry retrieveAttachmentEntryRecordOutOfTrx(@NonNull final AttachmentEntryId attachmentEntryId)
	{
		return loadOutOfTrx(attachmentEntryId, I_AD_AttachmentEntry.class);
	}

	public byte[] retrieveAttachmentEntryData(@NonNull final AttachmentEntryId attachmentEntryId)
	{
		final I_AD_AttachmentEntry record = retrieveAttachmentEntryRecordInTrx(attachmentEntryId);
		return getBinaryData(record);
	}

	public AttachmentEntryDataResource retrieveAttachmentEntryDataResource(@NonNull final AttachmentEntryId attachmentEntryId)
	{
		final I_AD_AttachmentEntry record = retrieveAttachmentEntryRecordInTrx(attachmentEntryId);

		return AttachmentEntryDataResource.builder()
				.source(getBinaryData(record))
				.filename(record.getFileName())
				.description(record.getDescription())
				.build();
	}

	public void updateAttachmentEntryData(
			@NonNull final AttachmentEntryId attachmentEntryId,
			final byte[] data)
	{
		final I_AD_AttachmentEntry entryRecord = retrieveAttachmentEntryRecordInTrx(attachmentEntryId);
		final boolean entryHasTypeData = X_AD_AttachmentEntry.TYPE_Data.equals(entryRecord.getType());
		if (!entryHasTypeData)
		{
			throw new AdempiereException("Only entries of type Data support attaching binary data").setParameter("entryRecord", entryRecord);
		}

		entryRecord.setBinaryData(data);
		saveRecord(entryRecord);
	}

	private AttachmentEntry forRecord(@NonNull final I_AD_AttachmentEntry attachmentEntryRecord)
	{
		return attachmentEntryFactory.toAttachmentEntry(attachmentEntryRecord);
	}

	public ImmutableList<AttachmentEntry> saveAll(@NonNull final Collection<AttachmentEntry> attachmentEntries)
	{
		final ImmutableList.Builder<AttachmentEntry> result = ImmutableList.builder();
		for (final AttachmentEntry attachmentEntry : attachmentEntries)
		{
			result.add(save(attachmentEntry));
		}
		return result.build();
	}

	public AttachmentEntry save(@NonNull final AttachmentEntry attachmentEntry)
	{
		final I_AD_AttachmentEntry attachmentEntryRecord;
		if (attachmentEntry.getId() != null)
		{
			attachmentEntryRecord = retrieveAttachmentEntryRecordInTrx(attachmentEntry.getId());
		}
		else
		{
			attachmentEntryRecord = newInstance(I_AD_AttachmentEntry.class);
		}

		attachmentEntryFactory.syncToRecord(attachmentEntry, attachmentEntryRecord);
		saveRecord(attachmentEntryRecord); // needed in case the record was new, because we need an ID for it

		return forRecord(attachmentEntryRecord);
	}

	private void deleteAllAttachmentMultiRefs(@NonNull final AttachmentEntryId attachmententryId)
	{
		queryBL.createQueryBuilder(I_AD_Attachment_MultiRef.class)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMN_AD_AttachmentEntry_ID, attachmententryId)
				.create()
				.delete();
	}

	public void deleteById(@NonNull final AttachmentEntry attachmentEntry)
	{
		final AttachmentEntryId id = attachmentEntry.getId();
		deleteById(id);
	}

	public void deleteById(@Nullable final AttachmentEntryId id)
	{
		if (id == null)
		{
			return;
		}
		deleteAllAttachmentMultiRefs(id);

		queryBL.createQueryBuilder(I_AD_AttachmentEntry.class)
				.addEqualsFilter(I_AD_AttachmentEntry.COLUMN_AD_AttachmentEntry_ID, id)
				.create()
				.delete();
	}

	private static byte[] getBinaryDataFromLocalFileURL(@NonNull final URI uri)
	{
		try
		{
			final URL url = uri.toURL();

			final Path filePath = FileUtil.getFilePath(url);

			return Files.readAllBytes(filePath);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Could not get binary data from url " + uri, e);
		}
	}

	private static byte[] getBinaryData(@NonNull final I_AD_AttachmentEntry record)
	{
		final AttachmentEntryType type = AttachmentEntryType.ofCode(record.getType());

		if (AttachmentEntryType.LocalFileURL.equals(type))
		{
			Check.assumeNotNull(record.getURL(), "AD_AttachmentEntry.URL cannot be null for type = {}, AD_AttachmentEntry_ID = {}",
					AttachmentEntryType.LocalFileURL.getCode(), record.getAD_AttachmentEntry_ID());

			return getBinaryDataFromLocalFileURL(URI.create(record.getURL()));
		}

		return record.getBinaryData();
	}
}
