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
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Attachment_MultiRef;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

public class AttachmentReferenceRepositoryTest
{
	private AttachmentReferenceRepository attachmentReferenceRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		attachmentReferenceRepository = new AttachmentReferenceRepository();
	}

	@Test
	public void getById()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef = createOrderRecordReference();

		final I_AD_Attachment_MultiRef multiRefRecord = createMultiRefRecord(attachmentEntryId, recordRef, "testFileName.txt");
		final AttachmentReferenceId id = AttachmentReferenceId.ofRepoId(multiRefRecord.getAD_Attachment_MultiRef_ID());

		//when
		final AttachmentReference result = attachmentReferenceRepository.getById(id);

		//then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(id);
		assertThat(result.getAttachmentEntryId()).isEqualTo(attachmentEntryId);
		assertThat(result.getRecordRef()).isEqualTo(recordRef);
		assertThat(result.getAttachmentNameOverride()).isEqualTo("testFileName.txt");
	}

	@Test
	public void getByReferencedRecord()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef = createOrderRecordReference();

		createMultiRefRecord(attachmentEntryId, recordRef, "testFileName.txt");

		//when
		final ImmutableList<AttachmentReference> result = attachmentReferenceRepository.getByReferencedRecord(recordRef);

		//then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getAttachmentEntryId()).isEqualTo(attachmentEntryId);
		assertThat(result.get(0).getRecordRef()).isEqualTo(recordRef);
	}

	@Test
	public void getByReferencedRecords_multipleRecords()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef1 = createOrderRecordReference();
		final TableRecordReference recordRef2 = createOrderLineRecordReference();

		createMultiRefRecord(attachmentEntryId, recordRef1, null);
		createMultiRefRecord(attachmentEntryId, recordRef2, null);

		//when
		final ImmutableList<AttachmentReference> result = attachmentReferenceRepository.getByReferencedRecords(
				ImmutableList.of(recordRef1, recordRef2)
		);

		//then
		assertThat(result).hasSize(2);
		assertThat(result).extracting(AttachmentReference::getRecordRef)
				.containsExactlyInAnyOrder(recordRef1, recordRef2);
	}

	@Test
	public void save_newReference()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef = createOrderRecordReference();

		final AttachmentReference attachmentReference = AttachmentReference.builder()
				.attachmentEntryId(attachmentEntryId)
				.recordRef(recordRef)
				.attachmentNameOverride("override.txt")
				.build();

		//when
		final AttachmentReference savedReference = attachmentReferenceRepository.save(attachmentReference);

		//then
		assertThat(savedReference.getId()).isNotNull();
		assertThat(savedReference.getAttachmentEntryId()).isEqualTo(attachmentEntryId);
		assertThat(savedReference.getRecordRef()).isEqualTo(recordRef);
		assertThat(savedReference.getAttachmentNameOverride()).isEqualTo("override.txt");

		// Verify it was actually saved
		final AttachmentReference reloaded = attachmentReferenceRepository.getById(savedReference.getId());
		assertThat(reloaded).isEqualTo(savedReference);
	}

	@Test
	public void save_existingReferenceById()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef = createOrderRecordReference();

		final I_AD_Attachment_MultiRef multiRefRecord = createMultiRefRecord(attachmentEntryId, recordRef, "original.txt");
		final AttachmentReferenceId id = AttachmentReferenceId.ofRepoId(multiRefRecord.getAD_Attachment_MultiRef_ID());

		final AttachmentReference attachmentReference = AttachmentReference.builder()
				.id(id)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(recordRef)
				.attachmentNameOverride("updated.txt")
				.build();

		//when
		final AttachmentReference updatedReference = attachmentReferenceRepository.save(attachmentReference);

		//then
		assertThat(updatedReference.getId()).isEqualTo(id);
		assertThat(updatedReference.getAttachmentNameOverride()).isEqualTo("updated.txt");

		// Verify the update
		final AttachmentReference reloaded = attachmentReferenceRepository.getById(id);
		assertThat(reloaded.getAttachmentNameOverride()).isEqualTo("updated.txt");
	}

	@Test
	public void save_existingReferenceByEntryAndRecord()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef = createOrderRecordReference();

		createMultiRefRecord(attachmentEntryId, recordRef, "original.txt");

		final AttachmentReference attachmentReference = AttachmentReference.builder()
				.attachmentEntryId(attachmentEntryId)
				.recordRef(recordRef)
				.attachmentNameOverride("updated.txt")
				.build();

		//when
		final AttachmentReference updatedReference = attachmentReferenceRepository.save(attachmentReference);

		//then
		assertThat(updatedReference.getId()).isNotNull();
		assertThat(updatedReference.getAttachmentNameOverride()).isEqualTo("updated.txt");

		// Verify only one record exists
		final ImmutableList<AttachmentReference> all = attachmentReferenceRepository.getByReferencedRecord(recordRef);
		assertThat(all).hasSize(1);
	}

	@Test
	public void saveAll()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef1 = createOrderRecordReference();
		final TableRecordReference recordRef2 = createOrderLineRecordReference();

		final ImmutableList<AttachmentReference> references = ImmutableList.of(
				AttachmentReference.builder()
						.attachmentEntryId(attachmentEntryId)
						.recordRef(recordRef1)
						.build(),
				AttachmentReference.builder()
						.attachmentEntryId(attachmentEntryId)
						.recordRef(recordRef2)
						.build()
		);

		//when
		final ImmutableList<AttachmentReference> savedReferences = attachmentReferenceRepository.saveAll(references);

		//then
		assertThat(savedReferences).hasSize(2);
		assertThat(savedReferences).allMatch(ref -> ref.getId() != null);
	}

	@Test
	public void delete_byAttachmentEntryId()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef1 = createOrderRecordReference();
		final TableRecordReference recordRef2 = createOrderLineRecordReference();

		createMultiRefRecord(attachmentEntryId, recordRef1, null);
		createMultiRefRecord(attachmentEntryId, recordRef2, null);

		//when
		attachmentReferenceRepository.delete(attachmentEntryId);

		//then
		final ImmutableList<AttachmentReference> result = attachmentReferenceRepository.getByEntryId(attachmentEntryId);
		assertThat(result).isEmpty();
	}

	@Test
	public void delete_byAttachmentReference_withId()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef = createOrderRecordReference();

		final I_AD_Attachment_MultiRef multiRefRecord = createMultiRefRecord(attachmentEntryId, recordRef, null);
		final AttachmentReferenceId id = AttachmentReferenceId.ofRepoId(multiRefRecord.getAD_Attachment_MultiRef_ID());

		final AttachmentReference attachmentReference = AttachmentReference.builder()
				.id(id)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(recordRef)
				.build();

		//when
		attachmentReferenceRepository.delete(attachmentReference);

		//then
		final ImmutableList<AttachmentReference> result = attachmentReferenceRepository.getByEntryId(attachmentEntryId);
		assertThat(result).isEmpty();
	}

	@Test
	public void delete_byAttachmentReference_withoutId()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef = createOrderRecordReference();

		createMultiRefRecord(attachmentEntryId, recordRef, null);

		final AttachmentReference attachmentReference = AttachmentReference.builder()
				.attachmentEntryId(attachmentEntryId)
				.recordRef(recordRef)
				.build();

		//when
		attachmentReferenceRepository.delete(attachmentReference);

		//then
		final ImmutableList<AttachmentReference> result = attachmentReferenceRepository.getByEntryId(attachmentEntryId);
		assertThat(result).isEmpty();
	}

	@Test
	public void getByEntryId()
	{
		//given
		final AttachmentEntryId attachmentEntryId1 = createAttachmentEntry();
		final AttachmentEntryId attachmentEntryId2 = createAttachmentEntry();
		final TableRecordReference recordRef1 = createOrderRecordReference();
		final TableRecordReference recordRef2 = createOrderLineRecordReference();

		createMultiRefRecord(attachmentEntryId1, recordRef1, null);
		createMultiRefRecord(attachmentEntryId1, recordRef2, null);
		createMultiRefRecord(attachmentEntryId2, recordRef1, null);

		//when
		final ImmutableList<AttachmentReference> result = attachmentReferenceRepository.getByEntryId(attachmentEntryId1);

		//then
		assertThat(result).hasSize(2);
		assertThat(result).allMatch(ref -> ref.getAttachmentEntryId().equals(attachmentEntryId1));
	}

	@Test
	public void deleteBy()
	{
		//given
		final AttachmentEntryId attachmentEntryId = createAttachmentEntry();
		final TableRecordReference recordRef1 = createOrderRecordReference();
		final TableRecordReference recordRef2 = createOrderLineRecordReference();

		createMultiRefRecord(attachmentEntryId, recordRef1, null);
		createMultiRefRecord(attachmentEntryId, recordRef2, null);

		//when
		attachmentReferenceRepository.deleteBy(attachmentEntryId, recordRef1);

		//then
		final ImmutableList<AttachmentReference> remaining = attachmentReferenceRepository.getByEntryId(attachmentEntryId);
		assertThat(remaining).hasSize(1);
		assertThat(remaining.get(0).getRecordRef()).isEqualTo(recordRef2);
	}

	// Helper methods

	@NonNull
	private AttachmentEntryId createAttachmentEntry()
	{
		final org.compiere.model.I_AD_AttachmentEntry entry = InterfaceWrapperHelper.newInstance(org.compiere.model.I_AD_AttachmentEntry.class);
		entry.setFileName("test.txt");
		entry.setType("D");
		InterfaceWrapperHelper.saveRecord(entry);
		return AttachmentEntryId.ofRepoId(entry.getAD_AttachmentEntry_ID());
	}

	@NonNull
	private I_AD_Attachment_MultiRef createMultiRefRecord(
			@NonNull final AttachmentEntryId attachmentEntryId,
			@NonNull final TableRecordReference recordRef,
			@Nullable final String fileNameOverride)
	{
		final I_AD_Attachment_MultiRef multiRefRecord = InterfaceWrapperHelper.newInstance(I_AD_Attachment_MultiRef.class);
		multiRefRecord.setAD_AttachmentEntry_ID(attachmentEntryId.getRepoId());
		multiRefRecord.setAD_Table_ID(recordRef.getAD_Table_ID());
		multiRefRecord.setRecord_ID(recordRef.getRecord_ID());
		multiRefRecord.setFileName_Override(fileNameOverride);
		InterfaceWrapperHelper.saveRecord(multiRefRecord);
		return multiRefRecord;
	}

	@NonNull
	private TableRecordReference createOrderRecordReference()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		InterfaceWrapperHelper.saveRecord(order);
		return TableRecordReference.of(I_C_Order.Table_Name, order.getC_Order_ID());
	}

	@NonNull
	private TableRecordReference createOrderLineRecordReference()
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		InterfaceWrapperHelper.saveRecord(orderLine);
		return TableRecordReference.of(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID());
	}
}
