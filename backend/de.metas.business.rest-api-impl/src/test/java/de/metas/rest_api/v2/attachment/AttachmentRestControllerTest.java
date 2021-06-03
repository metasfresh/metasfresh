/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.attachment;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryService;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentResponse;
import de.metas.common.rest_api.v2.attachment.JsonExternalReferenceTarget;
import de.metas.common.rest_api.v2.attachment.JsonTag;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_AttachmentEntry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(AdempiereTestWatcher.class)
public class AttachmentRestControllerTest
{
	private IQueryBL queryBL;
	private AttachmentRestController attachmentRestController;

	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		queryBL = Services.get(IQueryBL.class);

		final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();

		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();

		final ExternalReferenceRestControllerService externalReferenceRestControllerService = Mockito.mock(ExternalReferenceRestControllerService.class);

		final AttachmentRestService attachmentRestService = new AttachmentRestService(attachmentEntryService, externalReferenceRestControllerService, externalReferenceTypes);

		attachmentRestController = new AttachmentRestController(attachmentRestService);
	}

	@Test
	void createAttachment()
	{
		final JsonExternalReferenceTarget referenceTarget = JsonExternalReferenceTarget.builder()
				.externalReferenceType("BPartner")
				.externalReferenceIdentifier("111111")
				.build();

		final ImmutableList.Builder<JsonExternalReferenceTarget> targets = ImmutableList.builder();
		targets.add(referenceTarget);

		final JsonTag tag = JsonTag.builder()
				.name("name")
				.value("value")
				.build();

		final ImmutableList.Builder<JsonTag> tags = ImmutableList.builder();
		tags.add(tag);

		final JsonAttachment attachment = JsonAttachment.builder()
				.fileName("test.txt")
				.mimeType("text/plain")
				.tags(tags.build())
				.data("dGhpcyBpcyBhIGR1bW15IHRlc3QgZmlsZQ==")
				.build();

		final JsonAttachmentRequest attachmentRequest = JsonAttachmentRequest.builder()
				.orgCode("orgCode")
				.targets(targets.build())
				.attachment(attachment)
				.build();

		final ResponseEntity<JsonAttachmentResponse> responseEntity = attachmentRestController.createAttachment(attachmentRequest);

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonAttachmentResponse resultBody = responseEntity.getBody();

		expect(resultBody).toMatchSnapshot();

		assertThat(resultBody).isNotNull();

		final String attachmentId = resultBody.getAttachmentId();
		final String fileName = resultBody.getFilename();

		final I_AD_AttachmentEntry entry = queryBL.createQueryBuilder(I_AD_AttachmentEntry.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_AttachmentEntry.COLUMN_AD_AttachmentEntry_ID, Integer.parseInt(attachmentId))
				.create()
				.first(I_AD_AttachmentEntry.class);

		assertThat(entry).isNotNull();

		assertThat(entry.getFileName()).isEqualTo(fileName);
	}
}
