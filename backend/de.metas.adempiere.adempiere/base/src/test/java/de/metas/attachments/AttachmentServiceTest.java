package de.metas.attachments;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.metas.attachments.AttachmentTags.TAGNAME_SEND_VIA_EMAIL;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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

public class AttachmentServiceTest
{
	private AttachmentService attachmentService;
	private AttachmentReferenceRepository attachmentReferenceRepository;
	private I_C_BPartner bpartnerRecord;
	private I_M_Product productRecord;
	private AttachmentEntry bpartnerAttachmentEntry1;
	private AttachmentEntry bpartnerAttachmentEntry2;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, 10); // will be in the attachment-entry's CreatedUpdatedInfo

		bpartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bpartnerRecord);

		productRecord = newInstance(I_M_Product.class);
		saveRecord(productRecord);

		attachmentService = AttachmentService.createInstanceForUnitTesting();

		attachmentReferenceRepository = new AttachmentReferenceRepository();
		
		bpartnerAttachmentEntry1 = attachmentService.createNewAttachment(bpartnerRecord, "bPartnerAttachment1", "bPartnerAttachment1.data".getBytes());
		bpartnerAttachmentEntry2 = attachmentService.createNewAttachment(bpartnerRecord, "bPartnerAttachment2", "bPartnerAttachment2.data".getBytes());
	}

	@Test
	public void linkEntriesToModel()
	{
		// invoke the method under test
		attachmentService.createAttachmentLinks(ImmutableList.of(bpartnerAttachmentEntry1), TableRecordReference.ofCollection(ImmutableList.of(productRecord)));

		// assert that bpartnerRecord's attachments are unchanged
		final List<AttachmentEntry> bpartnerRecordEntries = attachmentService.getByReferencedRecord(bpartnerRecord);
		assertThat(bpartnerRecordEntries).hasSize(2);
		
		assertThat(bpartnerRecordEntries.get(1)).isEqualTo(bpartnerAttachmentEntry1);
		assertThat(bpartnerRecordEntries.get(0)).isEqualTo(bpartnerAttachmentEntry2);

		final List<AttachmentEntry> productRecordEntries = attachmentService.getByReferencedRecord(productRecord);
		assertThat(productRecordEntries).hasSize(1);
		
		// we need to compare them without linked records because productRecordEntries.get(0) has the product and bpartnerAttachmentEntry1 has the bpartner
		assertThat(productRecordEntries.get(0)).isEqualTo(bpartnerAttachmentEntry1);
	}

	@Test
	public void getEntries()
	{
		attachmentService.createAttachmentLinks(ImmutableList.of(bpartnerAttachmentEntry1), TableRecordReference.ofCollection(ImmutableList.of(productRecord)));

		attachmentService.createNewAttachment(bpartnerRecord, "bPartnerAttachment3", "bPartnerAttachment3.data".getBytes());

		// invoke the method under test
		final List<AttachmentEntry> productRecordEntries = attachmentService.getByReferencedRecord(productRecord);

		// the entries to productRecord shall not be changed by the addition of an entry for bpartnerRecord
		assertThat(productRecordEntries).hasSize(1);

		// we need to compare them without linked records because productRecordEntries.get(0) has the product and bpartnerAttachmentEntry1 has the bpartner
		assertThat(productRecordEntries.get(0)).isEqualTo(bpartnerAttachmentEntry1);
	}

	/**
	 * Unattached an entry that wasn't attached in the first place
	 */
	@Test
	public void deleteEntryForModel_Noop()
	{
		final I_M_Product productRecord2 = newInstance(I_M_Product.class);
		saveRecord(productRecord2);

		final AttachmentEntry entry = attachmentService.createNewAttachment(productRecord2, "productRecord2", "productRecord2.data".getBytes());
		assertThat(attachmentService.getByReferencedRecord(productRecord2)).hasSize(1);
		attachmentService.createAttachmentLinks(ImmutableList.of(entry), TableRecordReference.ofCollection(ImmutableList.of(productRecord2)));
		assertThat(attachmentService.getByReferencedRecord(productRecord2)).hasSize(1);
		
		// invoke the method under test - shall not do anything??
		final AttachmentEntry entryWithRemovedLink = attachmentService.unattach(TableRecordReference.of(productRecord), entry);

		assertThat(attachmentService.getByReferencedRecord(productRecord)).isEmpty();

		final List<AttachmentEntry> entriesOfProductRecord2 = attachmentService.getByReferencedRecord(productRecord2);
		assertThat(entriesOfProductRecord2).hasSize(1);
		assertThat(entriesOfProductRecord2.get(0)).isEqualTo(entryWithRemovedLink);
	}

	@Test
	public void deleteEntryForModel()
	{
		// given
		final I_M_Product productRecord2 = newInstance(I_M_Product.class);
		saveRecord(productRecord2);

		final AttachmentEntry entry = attachmentService.createNewAttachment(productRecord, "product", "product.data".getBytes());

		attachmentService.createAttachmentLinks(ImmutableList.of(entry), TableRecordReference.ofCollection(ImmutableList.of(productRecord2)));

		// when
		final AttachmentEntry entryWithRemovedLink = attachmentService.unattach(TableRecordReference.of(productRecord), entry);
		
		// then
		final ImmutableList<AttachmentReference> byEntryId = attachmentReferenceRepository.getByEntryId(entryWithRemovedLink.getIdNonNull());
		assertThat(byEntryId).hasSize(1);
		assertThat(byEntryId.get(0).getRecordRef()).isEqualTo(TableRecordReference.of(productRecord2));
		
		assertThat(attachmentService.getByReferencedRecord(productRecord)).isEmpty();

		final List<AttachmentEntry> entriesOfProductRecord2 = attachmentService.getByReferencedRecord(productRecord2);
		assertThat(entriesOfProductRecord2).hasSize(1);
		assertThat(entriesOfProductRecord2.get(0)).isEqualTo(entryWithRemovedLink);
	}
	
	@Test
	public void createNewAttachment_with_tags()
	{
		createNewAttachment_with_tags_performTest();
	}

	private AttachmentEntry createNewAttachment_with_tags_performTest()
	{
		final AttachmentEntryCreateRequest requestWithTags = AttachmentEntryCreateRequest
				.builderFromByteArray(
						"bPartnerAttachment_with_tags",
						"bPartnerAttachment_with_tags.data".getBytes())
				.tags(AttachmentTags.builder()
						.tag("tag1Name", "tag1Value")
						.tag("tag2Name", "tag2Value")
						.build())
				.build();

		// invoke the method under test
		final AttachmentEntry newEntry = attachmentService.createNewAttachment(bpartnerRecord, requestWithTags);

		assertThat(newEntry.getTags().getTagValueOrNull("tag1Name")).isEqualTo("tag1Value");
		assertThat(newEntry.getTags().getTagValueOrNull("tag2Name")).isEqualTo("tag2Value");

		return newEntry;
	}

	@Test
	public void save_with_additional_tags()
	{
		final AttachmentEntry attachmentEntry = createNewAttachment_with_tags_performTest();

		assertThat(attachmentEntry.getTags().getTagValueOrNull("tag1Name")).isEqualTo("tag1Value");
		assertThat(attachmentEntry.getTags().getTagValueOrNull("tag2Name")).isEqualTo("tag2Value");
		assertThat(attachmentEntry.getTags().getTagValueOrNull("tag3Name")).isEqualTo(null);


		final AttachmentEntry attachmentEntryWithAdditionalTag = attachmentEntry.toBuilder()
				.tags(AttachmentTags.builder()
						.tag("tag3Name", "tag3Value")
						.tag("tag2Name", "tag2Value")
						.build())
				.build();

		// invoke the method under test
		attachmentService.save(attachmentEntryWithAdditionalTag);

		final AttachmentEntry result = attachmentService.getById(attachmentEntryWithAdditionalTag.getIdNonNull());

		assertThat(result.getTags().getTagValueOrNull("tag1Name")).isEqualTo(null);
		assertThat(result.getTags().getTagValueOrNull("tag2Name")).isEqualTo("tag2Value");
		assertThat(result.getTags().getTagValueOrNull("tag3Name")).isEqualTo("tag3Value");
	}

	@Test
	public void handleAttachmentLinks()
	{
		final AttachmentEntry bPartnerLinkedAttachmentEntry = createNewAttachment_with_tags_performTest();

		final AttachmentTags tagsToAdd = AttachmentTags.builder()
				.tag("tag3Name", "tag3Value")
				.tag("tag4Name", "tag4Value")
				.build();

		final AttachmentTags tagsToRemove = AttachmentTags.builder()
				.tag("tag1Name", "tag1Value")
				.tag("tag2Name", "tag2Value")
				.build();

		final TableRecordReference tableRecordReferenceToRemove = TableRecordReference.of(bpartnerRecord);
		final TableRecordReference tableRecordReferenceToAdd = TableRecordReference.of(productRecord);

		final AttachmentLinksRequest attachmentLinksRequest = AttachmentLinksRequest.builder()
				.attachmentEntryId(bPartnerLinkedAttachmentEntry.getIdNonNull())
				.tagsToAdd(tagsToAdd)
				.tagsToRemove(tagsToRemove)
				.linksToAdd(ImmutableList.of(tableRecordReferenceToAdd))
				.linksToRemove(ImmutableList.of(tableRecordReferenceToRemove))
				.build();

		final AttachmentEntry result = attachmentService.handleAttachmentLinks(attachmentLinksRequest);

		assertThat(result.getTags().getTagValueOrNull("tag1Name")).isEqualTo(null);
		assertThat(result.getTags().getTagValueOrNull("tag2Name")).isEqualTo(null);
		assertThat(result.getTags().getTagValueOrNull("tag3Name")).isEqualTo("tag3Value");
		assertThat(result.getTags().getTagValueOrNull("tag4Name")).isEqualTo("tag4Value");
	}

	@Test
	public void streamEmailAttachments_test()
	{
		// given
		final AttachmentEntry attachment = createNewAttachment_with_tags_performTest().toBuilder()
				.tags(AttachmentTags.builder()
							  .tag(TAGNAME_SEND_VIA_EMAIL, "tag3Value")
							  .build())
				.build();

		attachmentService.save(attachment);
		final List<AttachmentEntry> attachmentsForRecordRef = attachmentService.getByReferencedRecord(bpartnerRecord);
		assertThat(attachmentsForRecordRef.size()).isEqualTo(3);

		// when
		final List<EmailAttachment> filteredAttachments = attachmentService.streamEmailAttachments(TableRecordReference.of(bpartnerRecord), TAGNAME_SEND_VIA_EMAIL)
				.collect(ImmutableList.toImmutableList());

		// then
		assertThat(filteredAttachments).isNotEmpty();
		assertThat(filteredAttachments.size()).isEqualTo(1);

		final EmailAttachment attachmentEntry = filteredAttachments.get(0);
		assertThat(attachmentEntry.getFilename()).isEqualTo("bPartnerAttachment_with_tags");
		assertThat(attachmentEntry.getAttachmentDataSupplier().get()).isEqualTo("bPartnerAttachment_with_tags.data".getBytes());
	}
}
