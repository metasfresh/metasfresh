/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.common.rest_api.v2.project.workorder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.*;

public class JsonWorkOrderProjectRequestTest
{

	private final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	@BeforeClass	
	public static void beforeAll()
	{
		start();
	}

	@Test
	public void serializeDeserialize() throws IOException
	{

		final JsonWorkOrderProjectRequest woProjectRequest = new JsonWorkOrderProjectRequest();
		woProjectRequest.setProjectId(JsonMetasfreshId.of(123456));
		woProjectRequest.setbPartnerId(JsonMetasfreshId.of(123456));
		woProjectRequest.setProjectParentId(JsonMetasfreshId.of(123456));
		woProjectRequest.setProjectTypeId(JsonMetasfreshId.of(123456));
		woProjectRequest.setCurrencyId(JsonMetasfreshId.of(123456));
		woProjectRequest.setPriceListVersionId(JsonMetasfreshId.of(123456));
		woProjectRequest.setSalesRepId(JsonMetasfreshId.of(123456));
		woProjectRequest.setIsActive(true);
		woProjectRequest.setDescription("Test WO Project Description");
		woProjectRequest.setDateContract(LocalDate.now());
		woProjectRequest.setDateFinish(LocalDate.now());
		woProjectRequest.setProjectReferenceExt("WOProjectTestRefExt");
		woProjectRequest.setName("Test WO Project");
		woProjectRequest.setValue("Test WO Project");
		woProjectRequest.setOrgCode("ORG");
		woProjectRequest.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);

		final List<JsonWorkOrderStepRequest> woProjectStepRequestList = new ArrayList<>();

		final JsonWorkOrderStepRequest woProjectStepRequest = new JsonWorkOrderStepRequest();
		woProjectStepRequest.setStepId(JsonMetasfreshId.of(123456));
		woProjectStepRequest.setSeqNo(123456);
		woProjectStepRequest.setName("Test WO Project Step");
		woProjectStepRequest.setDescription("Test WO Project Step Description");
		woProjectStepRequest.setDateEnd(LocalDate.now());
		woProjectStepRequest.setDateStart(LocalDate.now());
		woProjectStepRequest.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);

		woProjectStepRequestList.add(woProjectStepRequest);

		woProjectRequest.setSteps(woProjectStepRequestList);

		final String string = mapper.writeValueAsString(woProjectRequest);
		assertThat(string).isNotEmpty();

		final JsonWorkOrderProjectRequest result = mapper.readValue(string, JsonWorkOrderProjectRequest.class);

		assertThat(result).isEqualTo(woProjectRequest);
		expect(result).toMatchSnapshot();
	}
}
