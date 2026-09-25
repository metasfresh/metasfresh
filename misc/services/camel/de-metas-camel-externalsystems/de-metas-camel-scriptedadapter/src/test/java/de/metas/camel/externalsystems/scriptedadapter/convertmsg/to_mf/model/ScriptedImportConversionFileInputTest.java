/*
 * #%L
 * de-metas-camel-scriptedadapter
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScriptedImportConversionFileInputTest
{
	private final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	@Test
	void roundTrip_preservesValues() throws Exception
	{
		final ScriptedImportConversionFileInput input = ScriptedImportConversionFileInput.builder()
				.fileName("packzettel.pdf")
				.fileBase64("dGVzdC1jb250ZW50")
				.attachmentFileName("packzettel_attachment.pdf")
				.build();

		final String json = objectMapper.writeValueAsString(input);
		final ScriptedImportConversionFileInput deserialized = objectMapper.readValue(json, ScriptedImportConversionFileInput.class);

		assertThat(deserialized.getFileName()).isEqualTo("packzettel.pdf");
		assertThat(deserialized.getFileBase64()).isEqualTo("dGVzdC1jb250ZW50");
		assertThat(deserialized.getAttachmentFileName()).isEqualTo("packzettel_attachment.pdf");
	}

	@Test
	void serializedJson_usesExactFieldNames() throws Exception
	{
		final ScriptedImportConversionFileInput input = ScriptedImportConversionFileInput.builder()
				.fileName("packzettel.pdf")
				.fileBase64("dGVzdC1jb250ZW50")
				.attachmentFileName("packzettel_attachment.pdf")
				.build();

		final String json = objectMapper.writeValueAsString(input);
		final JsonNode node = objectMapper.readTree(json);

		assertThat(node.has("fileName")).isTrue();
		assertThat(node.has("fileBase64")).isTrue();
		assertThat(node.has("attachmentFileName")).isTrue();

		assertThat(node.get("fileName").asText()).isEqualTo("packzettel.pdf");
		assertThat(node.get("fileBase64").asText()).isEqualTo("dGVzdC1jb250ZW50");
		assertThat(node.get("attachmentFileName").asText()).isEqualTo("packzettel_attachment.pdf");

		// exactly these three fields — a renamed field would leave the old key absent and an extra key present
		assertThat(node.fieldNames()).toIterable().containsExactlyInAnyOrder(
				"fileName", "fileBase64", "attachmentFileName");
	}

	@Test
	void nullableFields_canBeOmitted() throws Exception
	{
		final ScriptedImportConversionFileInput input = ScriptedImportConversionFileInput.builder()
				.fileName("packzettel.pdf")
				.fileBase64("dGVzdC1jb250ZW50")
				.build();

		final String json = objectMapper.writeValueAsString(input);

		// the key must be ABSENT, not present-with-null — a round-tripped null value alone would also
		// pass when the DTO serializes an explicit "attachmentFileName":null, which is the defect this guards
		final JsonNode node = objectMapper.readTree(json);
		assertThat(node.has("attachmentFileName")).isFalse();

		final ScriptedImportConversionFileInput deserialized = objectMapper.readValue(json, ScriptedImportConversionFileInput.class);

		assertThat(deserialized.getFileName()).isEqualTo("packzettel.pdf");
		assertThat(deserialized.getFileBase64()).isEqualTo("dGVzdC1jb250ZW50");
		assertThat(deserialized.getAttachmentFileName()).isNull();
	}
}
