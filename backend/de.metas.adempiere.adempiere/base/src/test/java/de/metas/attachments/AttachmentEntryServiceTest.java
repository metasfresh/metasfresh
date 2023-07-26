package de.metas.attachments;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

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

public class AttachmentEntryServiceTest
{
	private AttachmentEntryService attachmentEntryService;
	private I_C_BPartner bpartnerRecord;
	private I_M_Product productRecord;
	private AttachmentEntry bpartnerAttachmentEntry1;
	private AttachmentEntry bpartnerAttachmentEntry2;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		bpartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bpartnerRecord);

		productRecord = newInstance(I_M_Product.class);
		saveRecord(productRecord);

		attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();

		bpartnerAttachmentEntry1 = attachmentEntryService.createNewAttachment(bpartnerRecord, "bPartnerAttachment1", "bPartnerAttachment1.data".getBytes());
		bpartnerAttachmentEntry2 = attachmentEntryService.createNewAttachment(bpartnerRecord, "bPartnerAttachment2", "bPartnerAttachment2.data".getBytes());
	}

	@Test
	public void linkEntriesToModel()
	{
		// invoke the method under test
		attachmentEntryService.createAttachmentLinks(ImmutableList.of(bpartnerAttachmentEntry1), TableRecordReference.ofCollection(ImmutableList.of(productRecord)));

		// assert that bpartnerRecord's attachments are unchanged
		final List<AttachmentEntry> bpartnerRecordEntries = attachmentEntryService.getByReferencedRecord(bpartnerRecord);
		assertThat(bpartnerRecordEntries).hasSize(2);
		// we need to compare them without linked records because productRecordEntries.get(0) now also has the product
		assertThat(bpartnerRecordEntries.get(0).withoutLinkedRecords()).isEqualTo(bpartnerAttachmentEntry1.withoutLinkedRecords());
		assertThat(bpartnerRecordEntries.get(1)).isEqualTo(bpartnerAttachmentEntry2);

		final List<AttachmentEntry> productRecordEntries = attachmentEntryService.getByReferencedRecord(productRecord);
		assertThat(productRecordEntries).hasSize(1);
		// we need to compare them without linked records because productRecordEntries.get(0) has the product and bpartnerAttachmentEntry1 has the bpartner
		assertThat(productRecordEntries.get(0).withoutLinkedRecords()).isEqualTo(bpartnerAttachmentEntry1.withoutLinkedRecords());
	}

	@Test
	public void getEntries()
	{
		attachmentEntryService.createAttachmentLinks(ImmutableList.of(bpartnerAttachmentEntry1), TableRecordReference.ofCollection(ImmutableList.of(productRecord)));

		attachmentEntryService.createNewAttachment(bpartnerRecord, "bPartnerAttachment3", "bPartnerAttachment3.data".getBytes());

		// invoke the method under test
		final List<AttachmentEntry> productRecordEntries = attachmentEntryService.getByReferencedRecord(productRecord);

		// the entries to productRecord shall not be changed by the addition uf an entry for bpartnerRecord
		assertThat(productRecordEntries).hasSize(1);
		// we need to compare them without linked records because productRecordEntries.get(0) has the product and bpartnerAttachmentEntry1 has the bpartner
		assertThat(productRecordEntries.get(0).withoutLinkedRecords()).isEqualTo(bpartnerAttachmentEntry1.withoutLinkedRecords());
	}

	@Test
	public void deleteEntryForModel()
	{
		final I_M_Product productRecord2 = newInstance(I_M_Product.class);
		saveRecord(productRecord2);

		final AttachmentEntry entry = attachmentEntryService.createNewAttachment(productRecord2, "productRecord2", "productRecord2.data".getBytes());

		attachmentEntryService.createAttachmentLinks(ImmutableList.of(entry), TableRecordReference.ofCollection(ImmutableList.of(productRecord2)));

		// invoke the method under test
		attachmentEntryService.unattach(TableRecordReference.of(productRecord), entry);

		assertThat(attachmentEntryService.getByReferencedRecord(productRecord)).isEmpty();

		final List<AttachmentEntry> entriesOfProductRecord2 = attachmentEntryService.getByReferencedRecord(productRecord2);
		assertThat(entriesOfProductRecord2).hasSize(1);
		assertThat(entriesOfProductRecord2.get(0)).isEqualTo(entry);
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
						"bPartnerAttachment_eith_tags",
						"bPartnerAttachment_with_tags.data".getBytes())
				.tags(AttachmentTags.builder()
						.tag("tag1Name", "tag1Value")
						.tag("tag2Name", "tag2Value")
						.build())
				.build();

		// invoke the method under test
		final AttachmentEntry newEntry = attachmentEntryService.createNewAttachment(bpartnerRecord, requestWithTags);

		assertThat(newEntry.getTags().getTagValueOrNull("tag1Name")).isEqualTo("tag1Value");
		assertThat(newEntry.getTags().getTagValueOrNull("tag2Name")).isEqualTo("tag2Value");

		return newEntry;
	}

	@Test
	public void save_with_additional_tags()
	{
		final AttachmentEntry attachmentEntry = createNewAttachment_with_tags_performTest();

		final AttachmentEntry attachmentEntryWithAdditionalTag = attachmentEntry.toBuilder()
				.tags(AttachmentTags.builder()
						.tag("tag3Name", "tag3Value")
						.tag("tag2Name", "tag2Value")
						.build())
				.build();

		// invoke the method under test
		attachmentEntryService.save(attachmentEntryWithAdditionalTag);

		final AttachmentEntry result = attachmentEntryService.getById(attachmentEntryWithAdditionalTag.getId());

		assertThat(result.getTags().getTagValueOrNull("tag1Name")).isEqualTo(null);
		assertThat(result.getTags().getTagValueOrNull("tag2Name")).isEqualTo("tag2Value");
		assertThat(result.getTags().getTagValueOrNull("tag3Name")).isEqualTo("tag3Value");

		assertThat(attachmentEntryWithAdditionalTag.getLinkedRecords()).isEqualTo(attachmentEntry.getLinkedRecords());
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
				.attachmentEntryId(bPartnerLinkedAttachmentEntry.getId())
				.tagsToAdd(tagsToAdd)
				.tagsToRemove(tagsToRemove)
				.linksToAdd(ImmutableList.of(tableRecordReferenceToAdd))
				.linksToRemove(ImmutableList.of(tableRecordReferenceToRemove))
				.build();

		final AttachmentEntry result = attachmentEntryService.handleAttachmentLinks(attachmentLinksRequest);

		assertThat(result.getTags().getTagValueOrNull("tag1Name")).isEqualTo(null);
		assertThat(result.getTags().getTagValueOrNull("tag2Name")).isEqualTo(null);
		assertThat(result.getTags().getTagValueOrNull("tag3Name")).isEqualTo("tag3Value");
		assertThat(result.getTags().getTagValueOrNull("tag4Name")).isEqualTo("tag4Value");

		assertThat(result.getLinkedRecords()).doesNotContain(tableRecordReferenceToRemove);
		assertThat(result.getLinkedRecords()).contains(tableRecordReferenceToAdd);
	}
}
