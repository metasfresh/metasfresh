/*
 * #%L
 * de-metas-common-changelog
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

package de.metas.common.changelog;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.rest_api.JsonMetasfreshId;
import io.github.jsonSnapshot.SnapshotMatcher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class JsonChangeInfoTest
{
	private ObjectMapper mapper = new ObjectMapper();

	@BeforeAll
	static void beforeAll()
	{
		SnapshotMatcher.start();
	}

	@Test
	void test() throws IOException
	{
		final JsonChangeInfo jsonChangeInfo = JsonChangeInfo.builder()
				.createdBy(JsonMetasfreshId.of(10))
				.createdMillis(10L)
				.lastUpdatedBy(JsonMetasfreshId.of(20))
				.lastUpdatedMillis(20L)
				.changeLog(JsonChangeLogItem.builder()
						.fieldName("fieldName_30")
						.updatedBy(JsonMetasfreshId.of(30))
						.updatedMillis(30L)
						.oldValue("oldValue_30")
						.newValue("newValue_30")
						.build())
				.changeLog(JsonChangeLogItem.builder()
						.fieldName("fieldName_40")
						.updatedBy(JsonMetasfreshId.of(40))
						.updatedMillis(40L)
						.oldValue("oldValue_40")
						.newValue("newValue_40")
						.build())
				.build();

		final String valueAsString = mapper.writeValueAsString(jsonChangeInfo);

		final JsonChangeInfo result = mapper.readValue(valueAsString, JsonChangeInfo.class);
		assertThat(result).isEqualTo(jsonChangeInfo);

		expect(result).toMatchSnapshot();
	}
}
