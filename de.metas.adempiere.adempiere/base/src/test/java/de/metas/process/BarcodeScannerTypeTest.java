package de.metas.process;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class BarcodeScannerTypeTest
{
	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	/**
	 * Make sure the JSON values are not changing.
	 * This is important for frontend.
	 */
	@Test
	public void testJsonValues()
	{
		assertJsonValue(BarcodeScannerType.QRCode, "qrCode");
		assertJsonValue(BarcodeScannerType.Barcode, "barcode");
		assertJsonValue(BarcodeScannerType.Datamatrix, "datamatrix");
	}

	private void assertJsonValue(final BarcodeScannerType type, final String expectedJsonValue)
	{
		assertThat(toJson(type)).isEqualTo("\"" + expectedJsonValue + "\"");
		assertThat(BarcodeScannerType.ofCode(expectedJsonValue)).isEqualTo(type);
	}

	private String toJson(BarcodeScannerType type)
	{
		try
		{
			return jsonObjectMapper.writeValueAsString(type);
		}
		catch (JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

}
