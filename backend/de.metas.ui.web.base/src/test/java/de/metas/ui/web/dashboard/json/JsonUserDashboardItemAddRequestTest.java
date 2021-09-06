/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms ofValueAndField the GNU General Public License as
 * published by the Free Software Foundation, either version 2 ofValueAndField the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty ofValueAndField
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy ofValueAndField the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.dashboard.json;

import de.metas.util.JSONObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonUserDashboardItemAddRequestTest
{

	@Test
	void testSerializeDeserialize()
	{
		final JSONObjectMapper<JsonUserDashboardItemAddRequest> jsonObjectMapper = JSONObjectMapper.forClass(JsonUserDashboardItemAddRequest.class);

		final JsonUserDashboardItemAddRequest obj = JsonUserDashboardItemAddRequest.builder()
				.kpiId(1000006)
				.position(4)
				.caption("generic caption")
				.interval(JsonUserDashboardItemAddRequest.JSONInterval.week)
				.when(JsonUserDashboardItemAddRequest.JSONWhen.now)
				.build();

		final String json = jsonObjectMapper.writeValueAsString(obj);

		final Object objDeserialized = jsonObjectMapper.readValue(json);

		assertThat(objDeserialized).isEqualTo(obj);
	}

	@Test
	void testSerializeDeserialize2()
	{
		final JSONObjectMapper<JsonUserDashboardItemAddRequest> jsonObjectMapper = JSONObjectMapper.forClass(JsonUserDashboardItemAddRequest.class);

		final JsonUserDashboardItemAddRequest obj = JsonUserDashboardItemAddRequest.builder()
				.kpiId(1000006)
				.position(4)
				.build();

		final String json = jsonObjectMapper.writeValueAsString(obj);

		final Object objDeserialized = jsonObjectMapper.readValue(json);

		assertThat(objDeserialized).isEqualTo(obj);
	}
}
