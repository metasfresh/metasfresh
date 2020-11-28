package de.metas.marketing.gateway.cleverreach.restapi.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.boot.json.JsonParserFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;

/*
 * #%L
 * marketing-cleverreach
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

public class ReceiverPostResonseJsonTest
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void deserializeToMaps() throws JsonParseException, JsonMappingException, IOException
	{
		final String serializedResponse = "[{\"status\":\"upsert success\",\"id\":\"6\"},\"invalid address 'test3-invalidmail';\",{\"status\":\"upsert success\",\"id\":\"7\"}]";

		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		final List<Object> resultList = JsonParserFactory.getJsonParser().parseList(serializedResponse);
		assertThat(resultList).hasSize(3);

		assertThat(resultList.get(0)).isInstanceOf(Map.class);
		final Map status1 = (Map)resultList.get(0);
		assertThat(status1).hasSize(2).containsEntry("id", "6").containsEntry("status", "upsert success");

		assertThat(resultList.get(1)).isInstanceOf(String.class);
		final String error2 = (String)resultList.get(1);
		assertThat(error2).isEqualTo("invalid address 'test3-invalidmail';");

		assertThat(resultList.get(2)).isInstanceOf(Map.class);
		final Map status3 = (Map)resultList.get(2);
		assertThat(status3).hasSize(2).containsEntry("id", "7").containsEntry("status", "upsert success");
	}

}
