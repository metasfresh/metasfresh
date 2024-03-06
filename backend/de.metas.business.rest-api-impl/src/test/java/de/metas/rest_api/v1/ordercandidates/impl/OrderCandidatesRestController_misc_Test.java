/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.v1.ordercandidates.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.CreatedUpdatedInfo;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryType;
import de.metas.common.ordercandidates.v1.response.JsonAttachment;
import de.metas.common.rest_api.v1.attachment.JsonAttachmentType;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.MimeType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.ZonedDateTime;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.assertj.core.api.Assertions.*;

public class OrderCandidatesRestController_misc_Test
{
	@BeforeAll
	public static void initStatic()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	public static void afterAll()
	{
		validateSnapshots();
	}

	/**
	 * Asserts that every {@link AttachmentEntryType} has a matching {@link JsonAttachmentType} and vice versa
	 */
	@Test
	void jsonAttachmentTypes()
	{
		for (final JsonAttachmentType jsonAttachmentEntryType : JsonAttachmentType.values())
		{
			final AttachmentEntryType attachmentEntryType = AttachmentEntryType.valueOf(jsonAttachmentEntryType.name());
			assertThat(attachmentEntryType.toString()).isEqualTo(jsonAttachmentEntryType.toString());
		}

		for (final AttachmentEntryType attachmentEntryType : AttachmentEntryType.values())
		{
			final JsonAttachmentType jsonAttachmentType = JsonAttachmentType.valueOf(attachmentEntryType.toString());
			assertThat(jsonAttachmentType.toString()).isEqualTo(attachmentEntryType.toString());
		}
	}

	@Test
	void toJsonAttachment_Type_URL() throws Exception
	{
		final AttachmentEntry attachmentEntry = AttachmentEntry
				.builder()
				.id(AttachmentEntryId.ofRepoId(10))
				.type(AttachmentEntryType.URL)
				.url(new URI("https://metasfresh.com"))
				.mimeType(MimeType.TYPE_TextPlain)
				.createdUpdatedInfo(CreatedUpdatedInfo.createNew(UserId.ofRepoId(10), ZonedDateTime.now()))
				.build();

		final JsonAttachment jsonAttachment = OrderCandidatesRestController.toJsonAttachment(
				"externalReference",
				"dataSourceName",
				attachmentEntry);

		assertThat(jsonAttachment.getType().name()).isEqualTo(AttachmentEntryType.URL.name());

		expect(jsonAttachment).toMatchSnapshot();
	}

	@Test
	void extractTags()
	{
		assertThat(invokeWith(ImmutableList.of())).isEmpty();

		assertThat(invokeWith(ImmutableList.of("n1"))).isEmpty();

		assertThat(invokeWith(ImmutableList.of("n1", "v1"))).containsEntry("n1", "v1");

		assertThat(invokeWith(ImmutableList.of("n1", "v1", "n2"))).containsEntry("n1", "v1");

		assertThat(invokeWith(ImmutableList.of("n1", "v1", "n2", "v2"))).containsEntry("n1", "v1").containsEntry("n2", "v2");
	}

	private ImmutableMap<String, String> invokeWith(final ImmutableList<String> of)
	{
		return OrderCandidatesRestController.extractTags(of);
	}
}
