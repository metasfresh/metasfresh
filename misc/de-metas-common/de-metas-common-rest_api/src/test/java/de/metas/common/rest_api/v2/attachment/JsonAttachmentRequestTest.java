/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2.attachment;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class JsonAttachmentRequestTest
{
	private final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonExternalReferenceTarget referenceTarget = JsonExternalReferenceTarget.builder()
				.externalReferenceType("type")
				.externalReferenceIdentifier("identifier")
				.build();

		final ImmutableList<JsonExternalReferenceTarget> targets = ImmutableList.of(referenceTarget);

		final JsonTag tag = JsonTag.builder()
				.name("name")
				.value("value")
				.build();

		final ImmutableList<JsonTag> tags = ImmutableList.of(tag);

		final JsonAttachment attachment = JsonAttachment.builder()
				.fileName("fileName")
				.mimeType("mimeType")
				.tags(tags)
				.data("data")
				.build();

		final JsonAttachmentRequest attachmentRequest = JsonAttachmentRequest.builder()
				.orgCode("orgCode")
				.targets(targets)
				.attachment(attachment)
				.build();

		final String string = mapper.writeValueAsString(attachmentRequest);

		final JsonAttachmentRequest result = mapper.readValue(string, JsonAttachmentRequest.class);

		assertThat(result).isEqualTo(attachmentRequest);
	}
}
