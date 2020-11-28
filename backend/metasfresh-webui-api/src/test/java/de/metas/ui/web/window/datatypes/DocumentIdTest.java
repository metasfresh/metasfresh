package de.metas.ui.web.window.datatypes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DocumentIdTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void init()
	{
		jsonObjectMapper = new ObjectMapper();
	}

	@Test
	public void toJson_Int() throws JsonProcessingException
	{
		final DocumentId documentId = DocumentId.of(12345);
		final String json = jsonObjectMapper.writeValueAsString(documentId);
		assertThat(json).isEqualTo("\"12345\"");
	}

	@Test
	public void toJson_String() throws JsonProcessingException
	{
		final DocumentId documentId = DocumentId.of("12345string");
		final String json = jsonObjectMapper.writeValueAsString(documentId);
		assertThat(json).isEqualTo("\"12345string\"");
	}

	@Test
	public void fromJson_Int() throws Exception
	{
		final DocumentId documentId = jsonObjectMapper.readValue("12345", DocumentId.class);
		assertThat(documentId).isEqualTo(DocumentId.of(12345));
	}

	@Test
	public void fromJson_String() throws Exception
	{
		final DocumentId documentId = jsonObjectMapper.readValue("\"12345\"", DocumentId.class);
		assertThat(documentId).isEqualTo(DocumentId.of(12345));
	}

	@Test
	public void fromJson_StrictString() throws Exception
	{
		final DocumentId documentId = jsonObjectMapper.readValue("\"12345string\"", DocumentId.class);
		assertThat(documentId).isEqualTo(DocumentId.of("12345string"));
	}
}
