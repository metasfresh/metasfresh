/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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
package de.metas.attachments;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Attachment_MultiRef;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@RequiredArgsConstructor
@Repository
class AttachmentReferenceRepository
{
	final CCache<TableRecordReference, ImmutableList<AttachmentReference>> cache = CCache.newLRUCache(I_AD_Attachment_MultiRef.Table_Name + "#by#ReferencedRecord", 1000, 60);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AttachmentReference getById(@NonNull final AttachmentReferenceId id)
	{
		final I_AD_Attachment_MultiRef attachmentEntryRecord = load(id, I_AD_Attachment_MultiRef.class);
		return forRecord(attachmentEntryRecord);
	}

	public ImmutableList<AttachmentReference> getByReferencedRecord(@NonNull final TableRecordReference referencedRecord)
	{
		return getByReferencedRecords(ImmutableList.of(referencedRecord));
	}

	public ImmutableList<AttachmentReference> getByReferencedRecords(@NonNull final Collection<TableRecordReference> referencedRecords)
	{
		final Collection<ImmutableList<AttachmentReference>> allOrLoad = cache.getAllOrLoad(referencedRecords, this::getByReferencedRecords0);
		return allOrLoad
				.stream()
				.flatMap(ImmutableList::stream)
				.collect(ImmutableList.toImmutableList());

	}

	private ImmutableMap<TableRecordReference, ImmutableList<AttachmentReference>> getByReferencedRecords0(@NonNull final Collection<TableRecordReference> referencedRecords)
	{
		final IQueryBuilder<I_AD_Attachment_MultiRef> queryBuilder = queryBL.createQueryBuilder(I_AD_Attachment_MultiRef.class)
				.setJoinOr();
		for (final TableRecordReference referencedRecord : referencedRecords)
		{
			final ICompositeQueryFilter<I_AD_Attachment_MultiRef> filter = queryBL
					.createCompositeQueryFilter(I_AD_Attachment_MultiRef.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMN_Record_ID, referencedRecord.getRecord_ID())
					.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMNNAME_AD_Table_ID, referencedRecord.getAD_Table_ID());

			queryBuilder.filter(filter);
		}

		return queryBuilder.create()
				.stream()
				.map(this::forRecord)
				.collect(ImmutableMap.toImmutableMap(
						AttachmentReference::getRecordRef,
						ImmutableList::of,
						(list1, list2) -> ImmutableList.<AttachmentReference>builder().addAll(list1).addAll(list2).build()));
	}

	private AttachmentReference forRecord(@NonNull final I_AD_Attachment_MultiRef multiRefRecord)
	{
		final AttachmentEntryId attachmentEntryId = AttachmentEntryId.ofRepoId(multiRefRecord.getAD_AttachmentEntry_ID());
		final AttachmentReferenceId id = AttachmentReferenceId.ofRepoIdOrNull(multiRefRecord.getAD_Attachment_MultiRef_ID());

		return AttachmentReference.builder()
				.id(id)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(TableRecordReference.of(multiRefRecord.getAD_Table_ID(), multiRefRecord.getRecord_ID()))
				.attachmentNameOverride(multiRefRecord.getFileName_Override())
				.build();
	}

	public ImmutableList<AttachmentReference> saveAll(@NonNull final Collection<AttachmentReference> attachmentReferences)
	{
		return attachmentReferences.stream()
				.map(this::save)
				.collect(ImmutableList.toImmutableList());
	}

	public AttachmentReference save(@NonNull final AttachmentReference attachmentReference)
	{
		I_AD_Attachment_MultiRef record = null;
		if (attachmentReference.getId() != null)
		{
			record = load(attachmentReference.getId(), I_AD_Attachment_MultiRef.class);
		}
		if (record == null)
		{
			record = getBy(attachmentReference.getAttachmentEntryId(), attachmentReference.getRecordRef());
		}
		if (record == null)
		{
			record = newInstance(I_AD_Attachment_MultiRef.class);
		}

		final TableRecordReference tableRecordRef = attachmentReference.getRecordRef();
		record.setAD_Table_ID(tableRecordRef.getAD_Table_ID());
		record.setRecord_ID(tableRecordRef.getRecord_ID());

		record.setAD_AttachmentEntry_ID(attachmentReference.getAttachmentEntryId().getRepoId());

		record.setFileName_Override(attachmentReference.getAttachmentNameOverride());

		saveRecord(record);

		return attachmentReference.withId(AttachmentReferenceId.ofRepoId(record.getAD_Attachment_MultiRef_ID()));
	}

	public void delete(@NonNull final AttachmentEntryId attachmentEntryId)
	{
		queryBL
				.createQueryBuilder(I_AD_Attachment_MultiRef.class)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMN_AD_AttachmentEntry_ID, attachmentEntryId)
				.create()
				.delete();
	}

	public void delete(@NonNull final AttachmentReference attachmentReference)
	{
		if (attachmentReference.getId() == null)
		{
			deleteBy(attachmentReference.getAttachmentEntryId(), attachmentReference.getRecordRef());
			return;
		}
		queryBL.createQueryBuilder(I_AD_Attachment_MultiRef.class)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMN_AD_Attachment_MultiRef_ID, attachmentReference.getId())
				.create()
				.delete();
	}

	public ImmutableList<AttachmentReference> getByEntryId(@NonNull final AttachmentEntryId attachmentEntryId)
	{
		return queryBL
				.createQueryBuilder(I_AD_Attachment_MultiRef.class)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMN_AD_AttachmentEntry_ID, attachmentEntryId)
				.create()
				.stream()
				.map(this::forRecord)
				.collect(ImmutableList.toImmutableList());

	}

	public void deleteBy(@NonNull final AttachmentEntryId attachmentEntryId, @NonNull final TableRecordReference tableRecordReference)
	{
		queryBL
				.createQueryBuilder(I_AD_Attachment_MultiRef.class)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMN_AD_AttachmentEntry_ID, attachmentEntryId)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMN_Record_ID, tableRecordReference.getRecord_ID())
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMNNAME_AD_Table_ID, tableRecordReference.getAD_Table_ID())
				.create()
				.delete();
	}

	private I_AD_Attachment_MultiRef getBy(@NonNull final AttachmentEntryId attachmentEntryId,
										   @NonNull final TableRecordReference tableRecordReference)
	{
		return queryBL.createQueryBuilder(I_AD_Attachment_MultiRef.class)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMN_AD_AttachmentEntry_ID, attachmentEntryId)
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMN_Record_ID, tableRecordReference.getRecord_ID())
				.addEqualsFilter(I_AD_Attachment_MultiRef.COLUMNNAME_AD_Table_ID, tableRecordReference.getAD_Table_ID())
				.create()
				.firstOnly(I_AD_Attachment_MultiRef.class);
	}
}
