package de.metas.attachments;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.attachments.automaticlinksharing.RecordToReferenceProviderService;
import de.metas.attachments.automaticlinksharing.RecordToReferenceProviderService.ExpandResult;
import de.metas.attachments.listener.TableAttachmentListenerRepository;
import de.metas.attachments.listener.TableAttachmentListenerService;
import de.metas.attachments.migration.AttachmentMigrationService;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

@RequiredArgsConstructor
@Service
public class AttachmentService
{
	private final AttachmentEntryRepository attachmentEntryRepository;
	private final AttachmentReferenceRepository attachmentReferenceRepository;
	private final AttachmentLogRepository attachmentLogRepository;
	private final AttachmentEntryFactory attachmentEntryFactory;
	private final AttachmentMigrationService attachmentMigrationService;
	private final RecordToReferenceProviderService attachmentHandlerRegistry;
	private final TableAttachmentListenerService tableAttachmentListenerService;

	@VisibleForTesting
	public static AttachmentService createInstanceForUnitTesting()
	{
		final TableAttachmentListenerService tableAttachmentListenerService = new TableAttachmentListenerService(new TableAttachmentListenerRepository());
		final AttachmentEntryFactory attachmentEntryFactory = new AttachmentEntryFactory();
		final AttachmentEntryRepository attachmentEntryRepository = new AttachmentEntryRepository(attachmentEntryFactory);
		final AttachmentReferenceRepository attachmentReferenceRepository = new AttachmentReferenceRepository();
		final AttachmentLogRepository attachmentLogRepository = new AttachmentLogRepository();
		final AttachmentMigrationService attachmentMigrationService = new AttachmentMigrationService(attachmentEntryFactory);
		final RecordToReferenceProviderService attachmentHandlerRegistry = new RecordToReferenceProviderService(Optional.empty());

		return new AttachmentService(
				attachmentEntryRepository,
				attachmentReferenceRepository,
				attachmentLogRepository,
				attachmentEntryFactory,
				attachmentMigrationService,
				attachmentHandlerRegistry,
				tableAttachmentListenerService);
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

	public void createNewAttachments(
			@NonNull final Object referencedRecord,
			@NonNull final Collection<AttachmentEntryCreateRequest> attachmentEntryCreateRequests)
	{
		for (final AttachmentEntryCreateRequest attachmentEntryCreateRequest : attachmentEntryCreateRequests)
		{
			createNewAttachment(referencedRecord, attachmentEntryCreateRequest);
		}
	}

	/**
	 * @param referencedRecords maybe a single model object, or a single {@link TableRecordReference} or a collection of both.
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
		createAttachmentLinks(ImmutableList.of(newEntry), tableRecordReferenceAsList);

		return newEntry;
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
		createAttachmentLinks(ImmutableList.of(entry), otherRecords);

		return entry;
	}

	/**
	 * Note: the given objects may be "record" models or {@link TableRecordReference}s.
	 */
	public void createAttachmentLinks(
			@NonNull final ImmutableList<AttachmentEntry> entries,
			@NonNull final Collection<?> referencedRecords)
	{
		final ImmutableList<AttachmentReference> unsavedAttachmentReferences = createAttachmentLinksDontSave(
				CollectionUtils.map(entries, AttachmentEntry::getId),
				referencedRecords);

		final ImmutableMultimap<AttachmentEntry, AttachmentReference> multimap = getMultimap(unsavedAttachmentReferences);
		for (final AttachmentEntry attachmentEntry : multimap.keySet())
		{
			expandAndSave(attachmentEntry, ImmutableList.copyOf(multimap.get(attachmentEntry)));
		}
	}

	/**
	 * Link all given {@code referencedRecordsDest} with the attachments currently linked to the given {@code referencedRecordsSource}
	 * Note: the given objects may be "record" models {@code I_C_Order} or {@link TableRecordReference}s.
	 */
	public void shareAttachmentLinks(
			@NonNull final Collection<?> referencedRecordsSource,
			@NonNull final Collection<?> referencedRecordsDest)
	{
		final ImmutableList.Builder<AttachmentReference> destAttachmentReferences = ImmutableList.builder();

		for (final Object referencedRecordSource : referencedRecordsSource)
		{
			final ImmutableList<AttachmentReference> sourceAttachmentReferences = //
					attachmentReferenceRepository.getByReferencedRecord(TableRecordReference.of(referencedRecordSource));

			final ImmutableList<AttachmentReference> destAttachmentEntriesForRecordSource = //
					createAttachmentLinksDontSave(
							CollectionUtils.map(sourceAttachmentReferences, AttachmentReference::getAttachmentEntryId),
							referencedRecordsDest);

			destAttachmentReferences.addAll(destAttachmentEntriesForRecordSource);
		}

		final ImmutableMultimap<AttachmentEntry, AttachmentReference> multimap = getMultimap(destAttachmentReferences.build());
		for (final AttachmentEntry attachmentEntry : multimap.keySet())
		{
			expandAndSave(attachmentEntry, ImmutableList.copyOf(multimap.get(attachmentEntry)));
		}
	}

	private ImmutableListMultimap<AttachmentEntry, AttachmentReference> getMultimap(
			@NonNull final ImmutableList<AttachmentReference> attachmentReferences)
	{
		final ImmutableMap<AttachmentEntryId, AttachmentEntry> attachmentEntries = retrieveEntriesForReferences(attachmentReferences);

		return attachmentReferences.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						ref -> attachmentEntries.get(ref.getAttachmentEntryId()),
						ref -> ref));
	}

	private void expandAndSave(
			@NonNull final AttachmentEntry newEntry,
			@NonNull final ImmutableList<AttachmentReference> attachmentReferencesToSave)
	{
		if (attachmentReferencesToSave.isEmpty())
		{
			return; // no need to fire up the handler(s)
		}

		final ArrayList<AttachmentReference> referencesToSave = new ArrayList<>(attachmentReferencesToSave);

		final AttachmentEntryWithReferences entryWithRefs = AttachmentEntryWithReferences.builder()
				.entry(newEntry)
				.references(attachmentReferencesToSave)
				.build();

		final ExpandResult expandResult = attachmentHandlerRegistry.expand(entryWithRefs);

		final ImmutableList<AttachmentReference> additionalRefs = //
				createAttachmentLinksDontSave(
						CollectionUtils.map(attachmentReferencesToSave, AttachmentReference::getAttachmentEntryId),
						expandResult.getAdditionalReferences());

		referencesToSave.addAll(additionalRefs);

		attachmentReferenceRepository.saveAll(referencesToSave);
	}

	private ImmutableList<AttachmentReference> createAttachmentLinksDontSave(
			@NonNull final List<AttachmentEntryId> sourceAttachmentReferences,
			@NonNull final Collection<?> referencedRecords)
	{
		final ImmutableList.Builder<AttachmentReference> result = ImmutableList.builder();

		for (final AttachmentEntryId entryId : sourceAttachmentReferences)
		{
			for (final Object referencedRecord : referencedRecords)
			{
				result.add(
						AttachmentReference.builder()
								.attachmentEntryId(entryId)
								.recordRef(TableRecordReference.of(referencedRecord))
								.build());
			}
		}
		return result.build();
	}

	@Nullable
	public AttachmentEntry handleAttachmentLinks(@NonNull final AttachmentLinksRequest attachmentLinksRequest)
	{
		AttachmentEntry entry = getById(attachmentLinksRequest.getAttachmentEntryId());

		if (!Check.isEmpty(attachmentLinksRequest.getLinksToAdd()))
		{
			final ImmutableList<AttachmentReference> attachmentReferences = attachmentReferenceRepository.getByEntryId(entry.getIdNonNull());
			final AttachmentEntryWithReferences attachmentBeforeLinking = AttachmentEntryWithReferences.builder().entry(entry).references(attachmentReferences).build();
			tableAttachmentListenerService.fireBeforeRecordLinked(attachmentBeforeLinking);
			for (final TableRecordReference linkToAdd : attachmentLinksRequest.getLinksToAdd())
			{
				final AttachmentReference attachmentReference = AttachmentReference.builder()
						.attachmentEntryId(attachmentLinksRequest.getAttachmentEntryId())
						.recordRef(linkToAdd)
						.build();

				attachmentReferenceRepository.save(attachmentReference);
				tableAttachmentListenerService.fireAfterRecordLinked(entry, attachmentReference);
			}
		}

		if (!Check.isEmpty(attachmentLinksRequest.getLinksToRemove()))
		{
			AttachmentEntry lastUnattachResult;
			for (final TableRecordReference linkToRemove : attachmentLinksRequest.getLinksToRemove())
			{
				lastUnattachResult = unattach(linkToRemove, entry);
				if (lastUnattachResult.getId() == null)
				{
					// the attachment is deleted. no point processing the tag requests
					return lastUnattachResult;
				}
			}
		}

		boolean changed = false;
		if (attachmentLinksRequest.getTagsToAdd() != null)
		{
			entry = entry.withAdditionalTag(attachmentLinksRequest.getTagsToAdd());
			changed = true;
		}
		if (attachmentLinksRequest.getTagsToRemove() != null)
		{
			entry = entry.withoutTags(attachmentLinksRequest.getTagsToRemove());
			changed = true;
		}
		if (!changed)
		{
			return entry;

		}
		return attachmentEntryRepository.save(entry);
	}

	@Nullable
	private AttachmentEntryId deleteIfNoReferenceLeft(@NonNull final AttachmentEntryId attachmentEntryId)
	{
		final ImmutableList<AttachmentReference> remainingReferences = attachmentReferenceRepository.getByEntryId(attachmentEntryId);
		if (remainingReferences.isEmpty())
		{
			attachmentEntryRepository.deleteById(attachmentEntryId);
			return null;
		}
		return attachmentEntryId;
	}

	public AttachmentEntry unattach(
			@NonNull final Object referencedRecord,
			@NonNull final AttachmentEntry attachment)
	{
		synchronized (attachment.getIdNonNull().toString().intern())
		{
			final TableRecordReference tableRecordReference = TableRecordReference.of(referencedRecord);
			attachmentReferenceRepository.deleteBy(attachment.getIdNonNull(), tableRecordReference);

			final AttachmentEntryId attachmentEntryId = deleteIfNoReferenceLeft(attachment.getIdNonNull());

			final AttachmentLog attachmentLog = AttachmentLog.builder()
					.attachmentEntry(attachment)
					.recordRef(tableRecordReference)
					.build();
			attachmentLogRepository.save(attachmentLog);

			return attachment.withId(attachmentEntryId);
		}
	}

	public List<AttachmentEntry> getByReferencedRecord(@NonNull final Object referencedRecord)
	{
		return getByQuery(AttachmentEntryQuery.builder().referencedRecord(referencedRecord).build());
	}

	public ImmutableList<AttachmentEntry> getByQuery(@NonNull final AttachmentEntryQuery query)
	{
		final Comparator<AttachmentEntry> youngestFirst = (e1, e2) -> {
			final ZonedDateTime created1 = e1.getCreatedUpdatedInfo() != null ? e1.getCreatedUpdatedInfo().getCreated() : ZonedDateTime.now();
			final ZonedDateTime created2 = e2.getCreatedUpdatedInfo() != null ? e2.getCreatedUpdatedInfo().getCreated() : ZonedDateTime.now();
			return created2.compareTo(created1);
		};
		final Comparator<AttachmentEntry> biggestIdFirst = (e1, e2) -> Integer.compare(AttachmentEntryId.toRepoId(e2.getIdNonNull()), AttachmentEntryId.toRepoId(e1.getIdNonNull()));
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
		final TableRecordReference recordReference = TableRecordReference.of(referencedRecord);
		final ImmutableList.Builder<AttachmentEntry> result = ImmutableList.builder();

		final ImmutableList<AttachmentReference> references = attachmentReferenceRepository.getByReferencedRecord(recordReference);

		final ImmutableCollection<AttachmentEntry> entries = retrieveEntriesForReferences(references).values();
		result.addAll(entries);

		if (attachmentMigrationService.isExistRecordsToMigrate())
		{
			final ImmutableList<AttachmentEntry> migratedEntries = attachmentMigrationService.migrateAndGetByReferencedRecord(referencedRecord);

			createAttachmentLinks(migratedEntries, ImmutableList.of(recordReference));
			result.addAll(migratedEntries);
		}

		return result.build();
	}

	private ImmutableMap<AttachmentEntryId, AttachmentEntry> retrieveEntriesForReferences(@NonNull final ImmutableList<AttachmentReference> references)
	{
		final ImmutableSet<AttachmentEntryId> entryIds = references.stream()
				.map(AttachmentReference::getAttachmentEntryId)
				.collect(ImmutableSet.toImmutableSet());

		return attachmentEntryRepository.getByIds(entryIds);
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

		return attachmentReferenceRepository.getByReferencedRecord(recordReference)
				.stream()
				.map(ref -> attachmentEntryRepository.getById(ref.getAttachmentEntryId()))
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
	public Stream<EmailAttachment> streamEmailAttachments(@NonNull final TableRecordReference recordReference, @Nullable final String tagName)
	{
		return attachmentReferenceRepository.getByReferencedRecord(recordReference)
				.stream()
				.map(ref -> attachmentEntryRepository.getById(ref.getAttachmentEntryId()))
				.filter(attachmentEntry -> Check.isBlank(tagName) || attachmentEntry.getTags().hasTag(tagName))
				.map(attachmentEntry -> EmailAttachment.builder()
						.filename(attachmentEntry.getFilename())
						.attachmentDataSupplier(() -> retrieveData(attachmentEntry.getIdNonNull()))
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

	public ImmutableList<AttachmentReference> retrieveAttachmentReferences(@NonNull final AttachmentEntry attachmentEntry)
	{
		return attachmentReferenceRepository.getByEntryId(attachmentEntry.getIdNonNull());
	}

	public ImmutableList<AttachmentEntryWithReferences> getEntriesAndReferencesFor(@NonNull final Collection<TableRecordReference> referencedRecords)
	{
		final ImmutableList<AttachmentReference> byReferencedRecords = attachmentReferenceRepository.getByReferencedRecords(referencedRecords);
		final ImmutableMultimap<AttachmentEntry, AttachmentReference> multimap = getMultimap(byReferencedRecords);

		final ImmutableList.Builder<AttachmentEntryWithReferences> result = ImmutableList.builder();

		for (final AttachmentEntry attachmentEntry : multimap.keySet())
		{
			final AttachmentEntryWithReferences attachmentEntryWithReferences = AttachmentEntryWithReferences.builder().entry(attachmentEntry).references(ImmutableList.copyOf(multimap.get(attachmentEntry))).build();
			result.add(attachmentEntryWithReferences);
		}
		return result.build();
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
