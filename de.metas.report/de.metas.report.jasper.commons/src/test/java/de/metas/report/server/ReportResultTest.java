package de.metas.report.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * #%L
 * de.metas.report.jasper.commons
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

public class ReportResultTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void beforeEach()
	{
		jsonObjectMapper = new ObjectMapper();
	}

	@Test
	public void testSerializeDeserialize() throws Exception
	{
		testSerializeDeserialize(ReportResult.builder()
				.reportFilename("some_filename.xls")
				.outputType(OutputType.XLS)
				.reportContent(new byte[] { 1, 2, 3, 4, 5 })
				.build());
	}

	public void testSerializeDeserialize(final ReportResult obj) throws IOException
	{
		final String json = jsonObjectMapper.writeValueAsString(obj);
		final ReportResult objDeserialized = jsonObjectMapper.readValue(json, obj.getClass());
		assertThat(objDeserialized).isEqualTo(obj);
	}
}
