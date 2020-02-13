package de.metas.ui.web.window.datatypes.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class JSONDocumentPathTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void init()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@Test
	public void testSerializeDeserialize()
	{
		// Root document
		testSerializeDeserialize(JSONDocumentPath.builder()
				.windowId(WindowId.fromJson("windowId"))
				.documentId(DocumentId.of("documentId"))
				.build());

		// Included document
		testSerializeDeserialize(JSONDocumentPath.builder()
				.windowId(WindowId.fromJson("windowId"))
				.documentId(DocumentId.of("documentId"))
				.tabId(DetailId.fromPrefixAndId("tabId", 1))
				.rowId(DocumentId.of("rowId"))
				.build());

		// View Row
		testSerializeDeserialize(JSONDocumentPath.builder()
				.viewId(ViewId.ofParts(WindowId.fromJson("windowId"), "viewId"))
				.rowId(DocumentId.of("rowId"))
				.build());
	}

	private void testSerializeDeserialize(final JSONDocumentPath path)
	{
		final JSONDocumentPath pathDeserialized = fromJson(toJson(path));
		assertThat(pathDeserialized).isEqualTo(path);
	}

	private String toJson(final JSONDocumentPath path)
	{
		try
		{
			return jsonObjectMapper.writeValueAsString(path);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed serializing " + path, e);
		}
	}

	private JSONDocumentPath fromJson(final String json)
	{
		try
		{
			return jsonObjectMapper.readValue(json, JSONDocumentPath.class);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed deserializing:\n" + json, e);
		}
	}
}
