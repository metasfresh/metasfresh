package de.metas.attachments;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.attachments.automaticlinksharing.RecordToReferenceProviderService;
import de.metas.attachments.automaticlinksharing.RecordToReferenceProviderService.ExpandResult;
import de.metas.attachments.listener.TableAttachmentListenerRepository;
import de.metas.attachments.listener.TableAttachmentListenerService;
import de.metas.attachments.migration.AttachmentMigrationService;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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
	private final AttachmentLogRepository attachmentLogRepository;
	private final AttachmentEntryFactory attachmentEntryFactory;
	private final AttachmentMigrationService attachmentMigrationService;
	private final RecordToReferenceProviderService attachmentHandlerRegistry;

	private static final AdMessageKey MSG_EXPECTED_ONE_ATTACHMENT_FOR_RECORD_REF = AdMessageKey.of("de.metas.attachments.ExpectedOneAttachmentForRecordRef");

	@VisibleForTesting
	public static AttachmentEntryService createInstanceForUnitTesting()
	{
		final AttachmentEntryFactory attachmentEntryFactory = new AttachmentEntryFactory();
		final TableAttachmentListenerService tableAttachmentListenerService = new TableAttachmentListenerService(new TableAttachmentListenerRepository());
		final AttachmentEntryRepository attachmentEntryRepository = new AttachmentEntryRepository(attachmentEntryFactory, tableAttachmentListenerService);
		final AttachmentLogRepository attachmentLogRepository = new AttachmentLogRepository();
		final AttachmentMigrationService attachmentMigrationService = new AttachmentMigrationService(attachmentEntryFactory);
		final RecordToReferenceProviderService attachmentHandlerRegistry = new RecordToReferenceProviderService(Optional.empty());

		return new AttachmentEntryService(
				attachmentEntryRepository,
				attachmentLogRepository,
				attachmentEntryFactory,
				attachmentMigrationService,
				attachmentHandlerRegistry);
	}

	/**
	 * Note: I didn't have spring initialize and inject those components,
	 * because I think only {@link AttachmentEntryService} should be used from outside, or obtained via spring context.
	 * But feel free to change this when useful.
	 */
	public AttachmentEntryService(
			@NonNull final AttachmentEntryRepository attachmentEntryRepository,
			@NonNull final AttachmentLogRepository attachmentLogRepository,
			@NonNull final AttachmentEntryFactory attachmentEntryFactory,
			@NonNull final AttachmentMigrationService attachmentMigrationService,
			@NonNull final RecordToReferenceProviderService attachmentHandlerRegistry)
	{
		this.attachmentEntryRepository = attachmentEntryRepository;
		this.attachmentLogRepository = attachmentLogRepository;
		this.attachmentEntryFactory = attachmentEntryFactory;
		this.attachmentMigrationService = attachmentMigrationService;
		this.attachmentHandlerRegistry = attachmentHandlerRegistry;
	}

	public AttachmentEntry createNewAttachment(
			@NonNull final Object referencedRecord,
			@NonNull final Path path)
	{
		return createNewAttachment(referencedRecord, path.toFile());
	}

	public AttachmentEntry createNewAttachment(
			@NonNull final Object referencedRecord,
			@NonNull final File file)
	{
		final TableRecordReference modelReference = TableRecordReference.of(referencedRecord);
		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.fromFile(file);

		return createNewAttachment(modelReference, request);
	}

	/**
	 * Convenience method
	 */
	public AttachmentEntry createNewAttachment(
			@NonNull final Object referencedRecord,
			@NonNull final String name,
			final byte[] bytes)
	{
		final TableRecordReference modelReference = TableRecordReference.of(referencedRecord);
		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.fromByteArray(name, bytes);

		return createNewAttachment(modelReference, request);
	}

	public AttachmentEntry createNewAttachment(
			@NonNull final Object referencedRecord,
			@NonNull final String name,
			@NonNull final URI uri)
	{
		final TableRecordReference modelReference = TableRecordReference.of(referencedRecord);
		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.fromURI(name, uri);

		return createNewAttachment(modelReference, request);
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

	/**
	 * @param referencedRecords may be a single model object, a single {@link TableRecordReference} or a collection of both.
	 */
	@SuppressWarnings("unchecked")
	public AttachmentEntry createNewAttachment(
			@NonNull final Object referencedRecords,
			@NonNull final AttachmentEntryCreateRequest attachmentEntryCreateRequest)
	{
		if (referencedRecords instanceof Collection)
		{
			return createNewAttachmentLinkAndLinkToAllReferencedRecords(
					(Collection<Object>)referencedRecords,
					attachmentEntryCreateRequest);
		}

		final AttachmentEntry newEntry = attachmentEntryFactory.createAndSaveEntry(attachmentEntryCreateRequest);
		final TableRecordReference tableRecordReference = TableRecordReference.of(referencedRecords);

		final ImmutableList<TableRecordReference> tableRecordReferenceAsList = ImmutableList.of(tableRecordReference);
		final Collection<AttachmentEntry> attachedEntries = createAttachmentLinks(
				ImmutableList.of(newEntry),
				tableRecordReferenceAsList);

		final ImmutableList<AttachmentEntry> attachedEntriesWithIds = attachmentEntryRepository.saveAll(attachedEntries);

		return CollectionUtils.singleElement(expandAndSave(tableRecordReferenceAsList, attachedEntriesWithIds));
	}

	private AttachmentEntry createNewAttachmentLinkAndLinkToAllReferencedRecords(
			@NonNull final Collection<Object> referencedRecords,
			@NonNull final AttachmentEntryCreateRequest attachmentEntryCreateRequest)
	{
		final ImmutableList<Object> referencedRecordsList = ImmutableList.copyOf(referencedRecords);

		Check.assumeNotEmpty(referencedRecordsList, "Parameter referencedRecords may not be empty");

		final Object firstRecord = referencedRecordsList.get(0);

		final AttachmentEntry entry = createNewAttachment(firstRecord, attachmentEntryCreateRequest);

		final List<Object> otherRecords = referencedRecordsList.subList(1, referencedRecords.size());
		final Collection<AttachmentEntry> entryWithReferencedRecords = createAttachmentLinks(ImmutableList.of(entry), otherRecords);

		return CollectionUtils.singleElement(entryWithReferencedRecords);
	}

	/**
	 * Note: the given objects may be "record" models or {@link TableRecordReference}s.
	 */
	public Collection<AttachmentEntry> createAttachmentLinks(
			@NonNull final Collection<AttachmentEntry> entries,
			@NonNull final Collection<?> referencedRecords)
	{
		final ImmutableList<AttachmentEntry> unsavedAttachmentsWithLinks = createAttachmentLinksDontSave(entries, referencedRecords);

		return expandAndSave(referencedRecords, unsavedAttachmentsWithLinks);
	}

	/**
	 * Link all given {@code referencedRecordsDest} with the attachments currently linked to the given {@code referencedRecordsSource}
	 * Note: the given objects may be "record" models {@code I_C_Order} or {@link TableRecordReference}s.
	 */
	public Collection<AttachmentEntry> shareAttachmentLinks(
			@NonNull final Collection<?> referencedRecordsSource,
			@NonNull final Collection<?> referencedRecordsDest)
	{
		final ImmutableList.Builder<AttachmentEntry> destAttachmentEntries = ImmutableList.builder();

		for (final Object referencedRecordSource : referencedRecordsSource)
		{
			final List<AttachmentEntry> sourceAttachmentEntries = //
					attachmentEntryRepository.getByReferencedRecord(TableRecordReference.of(referencedRecordSource));

			final ImmutableList<AttachmentEntry> destAttachmentEntriesForRecordSource = //
					createAttachmentLinksDontSave(sourceAttachmentEntries, referencedRecordsDest);

			destAttachmentEntries.addAll(destAttachmentEntriesForRecordSource);
		}

		return expandAndSave(referencedRecordsDest, destAttachmentEntries.build());
	}

	private Collection<AttachmentEntry> expandAndSave(
			@NonNull final Collection<?> originalReferencedRecords,
			@NonNull final ImmutableList<AttachmentEntry> attachmentEntriesToSave)
	{
		if (attachmentEntriesToSave.isEmpty())
		{
			return ImmutableList.of(); // no need to fire up the handler(s)
		}
		final ExpandResult additionalReferences = attachmentHandlerRegistry.expand(
				attachmentEntriesToSave,
				originalReferencedRecords);

		final ImmutableList<AttachmentEntry> destAttachmentEntriesWithAdditionalRefs = //
				createAttachmentLinksDontSave(
						attachmentEntriesToSave,
						additionalReferences.getAdditionalReferences());

		return attachmentEntryRepository.saveAll(destAttachmentEntriesWithAdditionalRefs);
	}

	private ImmutableList<AttachmentEntry> createAttachmentLinksDontSave(
			@NonNull final Collection<AttachmentEntry> entries,
			@NonNull final Collection<?> referencedRecords)
	{
		final ImmutableList.Builder<AttachmentEntry> result = ImmutableList.builder();

		for (final AttachmentEntry entry : entries)
		{
			AttachmentEntry updatedEntry = entry;
			for (final Object referencedRecord : referencedRecords)
			{
				updatedEntry = updatedEntry.withAdditionalLinkedRecord(TableRecordReference.of(referencedRecord));
			}
			result.add(updatedEntry);
		}
		return result.build();
	}

	@NonNull
	public AttachmentEntry handleAttachmentLinks(@NonNull final AttachmentLinksRequest attachmentLinksRequest)
	{
		AttachmentEntry updatedEntry = getById(attachmentLinksRequest.getAttachmentEntryId());

		if (!Check.isEmpty(attachmentLinksRequest.getLinksToAdd()))
		{
			updatedEntry = updatedEntry.withAdditionalLinkedRecords(attachmentLinksRequest.getLinksToAdd());
		}

		if (attachmentLinksRequest.getTagsToAdd() != null)
		{
			updatedEntry = updatedEntry.withAdditionalTag(attachmentLinksRequest.getTagsToAdd());
		}

		if (!Check.isEmpty(attachmentLinksRequest.getLinksToRemove()))
		{
			updatedEntry = updatedEntry.withRemovedLinkedRecords(attachmentLinksRequest.getLinksToRemove());
		}

		if (attachmentLinksRequest.getTagsToRemove() != null)
		{
			updatedEntry = updatedEntry.withoutTags(attachmentLinksRequest.getTagsToRemove());
		}

		final AttachmentEntry savedAttachmentEntry = attachmentEntryRepository.save(updatedEntry);

		if (savedAttachmentEntry.getLinkedRecords().isEmpty())
		{
			for (final TableRecordReference removedLinkRecord : Objects.requireNonNull(attachmentLinksRequest.getLinksToRemove()))
			{
				final AttachmentLog attachmentLog = AttachmentLog.builder()
						.attachmentEntry(savedAttachmentEntry)
						.recordRef(removedLinkRecord)
						.build();

				attachmentLogRepository.save(attachmentLog);
			}
			attachmentEntryRepository.delete(savedAttachmentEntry);
		}
		return savedAttachmentEntry;
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
			final AttachmentLog attachmentLog = AttachmentLog.builder()
					.attachmentEntry(attachment)
					.recordRef(tableRecordReference)
					.build();
			attachmentLogRepository.save(attachmentLog);
			attachmentEntryRepository.delete(withRemovedLinkedRecordAndId);
		}
		return withRemovedLinkedRecordAndId;
	}

	public List<AttachmentEntry> getByReferencedRecord(@NonNull final Object referencedRecord)
	{
		return getByQuery(AttachmentEntryQuery.builder().referencedRecord(referencedRecord).build());
	}

	public List<AttachmentEntry> getByQuery(@NonNull final AttachmentEntryQuery query)
	{
		final Comparator<AttachmentEntry> youngestFirst = (e1, e2) -> e2.getCreatedUpdatedInfo().getCreated().compareTo(e1.getCreatedUpdatedInfo().getCreated());
		final Comparator<AttachmentEntry> biggestIdFirst = (e1, e2) -> Integer.compare(AttachmentEntryId.getRepoId(e2.getId()), AttachmentEntryId.getRepoId(e1.getId()));
		return getByReferencedRecordMigrateIfNeeded(query.getReferencedRecord())
				.stream()
				.filter(e -> e.getTags().hasAllTagsSetToTrue(query.getTagsSetToTrue()))
				.filter(e -> e.getTags().hasAllTagsSetToAnyValue(query.getTagsSetToAnyValue()))
				.filter(e -> Check.isEmpty(query.getMimeType(), true) || Objects.equals(e.getMimeType(), query.getMimeType()))
				.sorted(youngestFirst.thenComparing(biggestIdFirst))
				.collect(ImmutableList.toImmutableList());
	}

	private List<AttachmentEntry> getByReferencedRecordMigrateIfNeeded(@NonNull final Object referencedRecord)
	{
		final TableRecordReference tableRecordReference = TableRecordReference.of(referencedRecord);
		final ImmutableList.Builder<AttachmentEntry> result = ImmutableList.builder();

		result.addAll(attachmentEntryRepository.getByReferencedRecord(tableRecordReference));

		if (attachmentMigrationService.isExistRecordsToMigrate())
		{
			final List<AttachmentEntry> migratedEntries = attachmentMigrationService.migrateAndGetByReferencedRecord(referencedRecord);
			final Collection<AttachmentEntry> migratedAndLinkedEntries = createAttachmentLinks(migratedEntries, ImmutableList.of(tableRecordReference));
			result.addAll(migratedAndLinkedEntries);
		}

		return result.build();
	}

	public AttachmentEntry getById(@NonNull final AttachmentEntryId id)
	{
		return attachmentEntryRepository.getById(id);
	}

	public byte[] retrieveData(@NonNull final AttachmentEntryId attachmentEntryId)
	{
		return attachmentEntryRepository.retrieveAttachmentEntryData(attachmentEntryId);
	}

	public AttachmentEntryDataResource retrieveDataResource(@NonNull final AttachmentEntryId attachmentEntryId)
	{
		return attachmentEntryRepository.retrieveAttachmentEntryDataResource(attachmentEntryId);
	}

	@Nullable
	public AttachmentEntry getByFilenameOrNull(
			@NonNull final Object referencedRecord,
			@NonNull final String fileName)
	{
		final TableRecordReference recordReference = TableRecordReference.of(referencedRecord);

		return attachmentEntryRepository
				.getByReferencedRecord(recordReference)
				.stream()
				.filter(entry -> fileName.equals(entry.getFilename()))
				.findFirst()
				.orElse(null);
	}

	public void updateData(
			@NonNull final AttachmentEntryId attachmentEntryId,
			final byte[] data)
	{
		attachmentEntryRepository.updateAttachmentEntryData(attachmentEntryId, data);
	}

	@NonNull
	public Optional<AttachmentEntry> getUniqueByReferenceRecord(@NonNull final TableRecordReference recordReference)
	{
		final List<AttachmentEntry> attachmentEntries = getByReferencedRecord(recordReference);

		if (attachmentEntries.size() > 1)
		{
			throw new AdempiereException(MSG_EXPECTED_ONE_ATTACHMENT_FOR_RECORD_REF,
					recordReference.getTableName(),
					recordReference.getRecord_ID());
		}

		return Optional.of(attachmentEntries)
				.filter(entries -> entries.size() == 1)
				.map(entries -> entries.get(0));
	}

	@NonNull
	public Stream<EmailAttachment> streamEmailAttachments(@NonNull final TableRecordReference recordRef, @Nullable final String tagName)
	{
		return attachmentEntryRepository.getByReferencedRecord(recordRef)
				.stream()
				.filter(attachmentEntry -> Check.isBlank(tagName) || attachmentEntry.getTags().hasTag(tagName))
				.map(attachmentEntry -> EmailAttachment.builder()
						.filename(attachmentEntry.getFilename())
						.attachmentDataSupplier(() -> retrieveData(attachmentEntry.getId()))
						.build());
	}

	/**
	 * Persist the given {@code attachmentEntry} as-is.
	 * Warning: e.g. tags or referenced records that are persisted before this method is called, but are not part of the given {@code attachmentEntry} are dropped.
	 */
	public void save(@NonNull final AttachmentEntry attachmentEntry)
	{
		attachmentEntryRepository.save(attachmentEntry);
	}

	@Value
	public static class AttachmentEntryQuery
	{
		List<String> tagsSetToTrue;

		List<String> tagsSetToAnyValue;

		Object referencedRecord;

		String mimeType;

		@Builder
		private AttachmentEntryQuery(
				@Singular("tagSetToTrue") final List<String> tagsSetToTrue,
				@Singular("tagSetToAnyValue") final List<String> tagsSetToAnyValue,
				@Nullable final String mimeType,
				@NonNull final Object referencedRecord)
		{
			this.referencedRecord = referencedRecord;

			this.mimeType = mimeType;

			this.tagsSetToTrue = tagsSetToTrue;
			this.tagsSetToAnyValue = tagsSetToAnyValue;
		}

	}
}
