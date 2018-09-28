package de.metas.attachments;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.X_AD_AttachmentEntry;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

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

@Service
public class AttachmentEntryService
{
	private final AttachmentEntryRepository attachmentEntryRepository;
	private final AttachmentEntryFactory attachmentEntryFactory;

	public AttachmentEntryService(
			@NonNull final AttachmentEntryRepository attachmentEntryRepository,
			@NonNull final AttachmentEntryFactory attachmentEntryFactory)
	{
		this.attachmentEntryRepository = attachmentEntryRepository;
		this.attachmentEntryFactory = attachmentEntryFactory;
	}

//	public List<AttachmentEntry> createNewAttachments(
//			@NonNull final Object referencedRecord,
//			@NonNull final Collection<File> files)
//	{
//		if (files.isEmpty())
//		{
//			return ImmutableList.of();
//		}
//
//		final List<AttachmentEntry> entries = files
//				.stream()
//				.map(file -> createNewAttachment(referencedRecord, file))
//				.collect(ImmutableList.toImmutableList());
//
//		return entries;
//	}

	public AttachmentEntry createNewAttachment(
			@NonNull final Object referencedRecord,
			@NonNull final File file)
	{
		final ImmutableSet<TableRecordReference> modelReferences = ImmutableSet.of(TableRecordReference.of(referencedRecord));
		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.fromFile(file);

		return createNewAttachment(modelReferences, request);
	}

	/** Convenience method */
	public AttachmentEntry createNewAttachment(
			@NonNull final Object referencedRecord,
			@NonNull final String name,
			@NonNull final byte[] bytes)
	{
		final ImmutableSet<TableRecordReference> modelReferences = ImmutableSet.of(TableRecordReference.of(referencedRecord));
		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.fromByteArray(name, bytes);

		return createNewAttachment(modelReferences, request);
	}

	public AttachmentEntry createNewAttachment(
			@NonNull final Object referencedRecord,
			@NonNull final String name,
			@NonNull final URI uri)
	{
		final ImmutableSet<TableRecordReference> modelReferences = ImmutableSet.of(TableRecordReference.of(referencedRecord));
		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.fromURI(name, uri);

		return createNewAttachment(modelReferences, request);
	}

	public List<AttachmentEntry> createNewAttachments(
			@NonNull final Object referencedRecord,
			@NonNull final Collection<AttachmentEntryCreateRequest> attachmentEntryCreateRequests)
	{
		final ImmutableList.Builder<AttachmentEntry> result = ImmutableList.builder();
		for (final AttachmentEntryCreateRequest attachmentEntryCreateRequest : attachmentEntryCreateRequests)
		{
			result.add(createNewAttachment(referencedRecord, attachmentEntryCreateRequest));
		}
		return result.build();
	}

	public AttachmentEntry createNewAttachment(
			@NonNull final Object referencedRecord,
			@NonNull final AttachmentEntryCreateRequest attachmentEntryCreateRequest)
	{
		final AttachmentEntry newEntry = attachmentEntryFactory.createEntry(attachmentEntryCreateRequest);
		final TableRecordReference tableRecordReference = TableRecordReference.of(referencedRecord);

		final Collection<AttachmentEntry> attachedEntries = linkAttachmentsToModels(ImmutableList.of(newEntry), ImmutableList.of(tableRecordReference));
		final Collection<AttachmentEntry> attachedEntriesWithIds = attachmentEntryRepository.saveAll(attachedEntries);

		return CollectionUtils.singleElement(attachedEntriesWithIds);
	}

	public Collection<AttachmentEntry> linkAttachmentsToModels(
			@NonNull final Collection<AttachmentEntry> entries,
			@NonNull final Collection<? extends ITableRecordReference> modelRefs)
	{
		final ImmutableList.Builder<AttachmentEntry> result = ImmutableList.builder();

		for (final AttachmentEntry entry : entries)
		{
			AttachmentEntry updatedEntry = entry;
			for (final ITableRecordReference modelRef : modelRefs)
			{
				updatedEntry = updatedEntry.withAdditionalLinkedRecord(modelRef);
			}
			result.add(updatedEntry);
		}
		return attachmentEntryRepository.saveAll(result.build());
	}

	public AttachmentEntry unattach(
			@NonNull final Object referencedRecord,
			@NonNull final AttachmentEntry attachment)
	{
		final TableRecordReference tableRecordReference = TableRecordReference.of(referencedRecord);
		final AttachmentEntry withRemovedLinkedRecord = attachment.withRemovedLinkedRecord(tableRecordReference);
		final AttachmentEntry withRemovedLinkedRecordAndId = attachmentEntryRepository.save(withRemovedLinkedRecord);

		if (withRemovedLinkedRecordAndId.getLinkedRecords().isEmpty())
		{
			attachmentEntryRepository.delete(withRemovedLinkedRecordAndId);
		}
		return withRemovedLinkedRecordAndId;
	}

	public List<AttachmentEntry> getEntries(@NonNull final Object referencedRecord)
	{
		final TableRecordReference tableRecordReference = TableRecordReference.of(referencedRecord);
		return attachmentEntryRepository.getByRecordReferences(tableRecordReference);
	}

	public AttachmentEntry getById(@NonNull final AttachmentEntryId id)
	{
		return attachmentEntryRepository.getById(id);
	}

	public byte[] retrieveData(@NonNull final AttachmentEntryId attachmentEntryId)
	{
		final I_AD_AttachmentEntry record = load(attachmentEntryId, I_AD_AttachmentEntry.class);
		return record.getBinaryData();
	}

	public AttachmentEntry getByFilenameOrNull(
			@NonNull final Object referencedRecord,
			@NonNull final String fileName)
	{
		final TableRecordReference recordReference = TableRecordReference.of(referencedRecord);

		return attachmentEntryRepository
				.getByRecordReferences(recordReference)
				.stream()
				.filter(entry -> fileName.equals(entry.getFilename()))
				.findFirst()
				.orElse(null);
	}

	public void updateData(
			@NonNull final AttachmentEntryId attachmentEntryId,
			@NonNull final byte[] data)
	{
		final I_AD_AttachmentEntry entryRecord = load(attachmentEntryId, I_AD_AttachmentEntry.class);
		if (X_AD_AttachmentEntry.TYPE_Data.equals(entryRecord.getType()))
		{
			throw new AdempiereException("Only entries of type Data support attaching data").setParameter("entryRecord", entryRecord);
		}

		entryRecord.setBinaryData(data);
		save(entryRecord);
	}
}
