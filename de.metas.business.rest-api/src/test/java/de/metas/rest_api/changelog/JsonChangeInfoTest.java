package de.metas.rest_api.changelog;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.metas.rest_api.MetasfreshId;
import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.business.rest-api
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

class JsonChangeInfoTest
{

	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@Test
	void test()
	{
		final JsonChangeInfo jsonChangeInfo = JsonChangeInfo.builder()
				.createdBy(MetasfreshId.of(10))
				.createdMillis(10L)
				.lastUpdatedBy(MetasfreshId.of(20))
				.lastUpdatedMillis(20L)
				.changeLog(JsonChangeLogItem.builder()
						.fieldName("fieldName_30")
						.updatedBy(MetasfreshId.of(30))
						.updatedMillis(30L)
						.oldValue("oldValue_30")
						.newValue("newValue_30")
						.build())
				.changeLog(JsonChangeLogItem.builder()
						.fieldName("fieldName_40")
						.updatedBy(MetasfreshId.of(40))
						.updatedMillis(40L)
						.oldValue("oldValue_40")
						.newValue("newValue_40")
						.build())
				.build();

		final JSONObjectMapper<JsonChangeInfo> mapper = JSONObjectMapper.forClass(JsonChangeInfo.class);
		final String valueAsString = mapper.writeValueAsString(jsonChangeInfo);

		final JsonChangeInfo result = mapper.readValue(valueAsString);
		assertThat(result).isEqualTo(jsonChangeInfo);

		expect(result).toMatchSnapshot();
	}

}
