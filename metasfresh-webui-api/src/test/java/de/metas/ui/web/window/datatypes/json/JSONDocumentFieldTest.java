package de.metas.ui.web.window.datatypes.json;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JSONDocumentFieldTest
{
	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = new ObjectMapper();
	}

	@Test
	public void test_deserialize_PreliminaryTest() throws Exception
	{
		final JSONLayoutWidgetType jsonWidgetType = null; // N/A
		final JSONDocumentField field = new JSONDocumentField("field1", jsonWidgetType);
		
		final String json = jsonObjectMapper.writeValueAsString(field);
		
		final JSONDocumentField field2 = jsonObjectMapper.readValue(json, JSONDocumentField.class);
		Assert.assertEquals("Field name", field.getField(), field2.getField());
	}
}
