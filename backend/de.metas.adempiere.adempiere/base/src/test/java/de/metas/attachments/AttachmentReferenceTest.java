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

import de.metas.adempiere.model.I_M_Product;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.junit.Before;
import org.junit.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class AttachmentReferenceTest
{
	private AttachmentEntryId attachmentEntryId;
	private TableRecordReference bpartnerRef;
	private TableRecordReference productRef;
	private AttachmentReferenceId referenceId;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		attachmentEntryId = AttachmentEntryId.ofRepoId(100);
		referenceId = AttachmentReferenceId.ofRepoId(200);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		saveRecord(bpartner);
		bpartnerRef = TableRecordReference.of(bpartner);

		final I_M_Product product = newInstance(I_M_Product.class);
		saveRecord(product);
		productRef = TableRecordReference.of(product);
	}

	@Test
	public void builder_withAllFields()
	{
		final AttachmentReference reference = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.attachmentNameOverride("Custom Name")
				.build();

		assertThat(reference.getId()).isEqualTo(referenceId);
		assertThat(reference.getAttachmentEntryId()).isEqualTo(attachmentEntryId);
		assertThat(reference.getRecordRef()).isEqualTo(bpartnerRef);
		assertThat(reference.getAttachmentNameOverride()).isEqualTo("Custom Name");
	}

	@Test
	public void builder_withMinimalRequiredFields()
	{
		final AttachmentReference reference = AttachmentReference.builder()
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.build();

		assertThat(reference.getId()).isNull();
		assertThat(reference.getAttachmentEntryId()).isEqualTo(attachmentEntryId);
		assertThat(reference.getRecordRef()).isEqualTo(bpartnerRef);
		assertThat(reference.getAttachmentNameOverride()).isNull();
	}

	@Test
	public void builder_withoutNameOverride()
	{
		final AttachmentReference reference = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.build();

		assertThat(reference.getId()).isEqualTo(referenceId);
		assertThat(reference.getAttachmentEntryId()).isEqualTo(attachmentEntryId);
		assertThat(reference.getRecordRef()).isEqualTo(bpartnerRef);
		assertThat(reference.getAttachmentNameOverride()).isNull();
	}

	@Test
	public void builder_throwsException_whenAttachmentEntryIdIsNull()
	{
		assertThatNullPointerException()
				.isThrownBy(() -> AttachmentReference.builder()
						.id(referenceId)
						.recordRef(bpartnerRef)
						.build())
				.withMessageContaining("attachmentEntryId");
	}

	@Test
	public void builder_throwsException_whenRecordRefIsNull()
	{
		assertThatNullPointerException()
				.isThrownBy(() -> AttachmentReference.builder()
						.id(referenceId)
						.attachmentEntryId(attachmentEntryId)
						.build())
				.withMessageContaining("recordRef");
	}

	@Test
	public void withId_updatesId()
	{
		final AttachmentReference original = AttachmentReference.builder()
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.build();

		final AttachmentReference updated = original.withId(referenceId);

		assertThat(updated.getId()).isEqualTo(referenceId);
		assertThat(updated.getAttachmentEntryId()).isEqualTo(attachmentEntryId);
		assertThat(updated.getRecordRef()).isEqualTo(bpartnerRef);
		assertThat(updated.getAttachmentNameOverride()).isNull();
	}

	@Test
	public void withId_createsNewInstance()
	{
		final AttachmentReference original = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.build();

		final AttachmentReferenceId newId = AttachmentReferenceId.ofRepoId(300);
		final AttachmentReference updated = original.withId(newId);

		assertThat(original.getId()).isEqualTo(referenceId);
		assertThat(updated.getId()).isEqualTo(newId);
		assertThat(updated).isNotSameAs(original);
	}

	@Test
	public void equals_sameValues()
	{
		final AttachmentReference ref1 = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.attachmentNameOverride("Name")
				.build();

		final AttachmentReference ref2 = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.attachmentNameOverride("Name")
				.build();

		assertThat(ref1).isEqualTo(ref2);
		assertThat(ref1.hashCode()).isEqualTo(ref2.hashCode());
	}

	@Test
	public void equals_differentId()
	{
		final AttachmentReference ref1 = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.build();

		final AttachmentReference ref2 = AttachmentReference.builder()
				.id(AttachmentReferenceId.ofRepoId(300))
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.build();

		assertThat(ref1).isNotEqualTo(ref2);
	}

	@Test
	public void equals_differentAttachmentEntryId()
	{
		final AttachmentReference ref1 = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.build();

		final AttachmentReference ref2 = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(AttachmentEntryId.ofRepoId(400))
				.recordRef(bpartnerRef)
				.build();

		assertThat(ref1).isNotEqualTo(ref2);
	}

	@Test
	public void equals_differentRecordRef()
	{
		final AttachmentReference ref1 = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.build();

		final AttachmentReference ref2 = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(productRef)
				.build();

		assertThat(ref1).isNotEqualTo(ref2);
	}

	@Test
	public void equals_differentAttachmentNameOverride()
	{
		final AttachmentReference ref1 = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.attachmentNameOverride("Name1")
				.build();

		final AttachmentReference ref2 = AttachmentReference.builder()
				.id(referenceId)
				.attachmentEntryId(attachmentEntryId)
				.recordRef(bpartnerRef)
				.attachmentNameOverride("Name2")
				.build();

		assertThat(ref1).isNotEqualTo(ref2);
	}
}
