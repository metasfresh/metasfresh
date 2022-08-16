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
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

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
		final JsonWorkOrderProjectUpsertRequest woProjectRequest = new JsonWorkOrderProjectUpsertRequest();
		woProjectRequest.setBusinessPartnerId(JsonMetasfreshId.of(1));
		woProjectRequest.setProjectParentId(JsonMetasfreshId.of(2));
		woProjectRequest.setProjectTypeId(JsonMetasfreshId.of(3));
		woProjectRequest.setCurrencyId(JsonMetasfreshId.of(4));
		woProjectRequest.setPriceListVersionId(JsonMetasfreshId.of(5));
		woProjectRequest.setSalesRepId(JsonMetasfreshId.of(6));
		woProjectRequest.setIsActive(true);
		woProjectRequest.setDescription("Test WO Project Description");
		woProjectRequest.setDateContract(LocalDate.parse("2022-07-01"));
		woProjectRequest.setDateFinish(LocalDate.parse("2022-07-20"));
		woProjectRequest.setProjectReferenceExt("WOProjectTestRefExt");
		woProjectRequest.setName("Test WO Project");
		woProjectRequest.setValue("Test WO Project");
		woProjectRequest.setOrgCode("ORG");
		woProjectRequest.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
		woProjectRequest.setDateOfProvisionByBPartner(LocalDate.parse("2022-07-22"));
		woProjectRequest.setWoOwner("woOwner");
		woProjectRequest.setIdentifier("ext-252525");

		final JsonWorkOrderStepUpsertItemRequest woProjectStep = new JsonWorkOrderStepUpsertItemRequest();
		woProjectStep.setIdentifier("ext-1111");
		woProjectStep.setName("Test WO Project Step");
		woProjectStep.setDescription("Test WO Project Step Description");
		woProjectStep.setSeqNo(10);
		woProjectStep.setDateStart(LocalDate.parse("2022-07-03"));
		woProjectStep.setDateEnd(LocalDate.parse("2022-07-22"));
		woProjectStep.setWoPartialReportDate(LocalDate.parse("2022-07-10"));
		woProjectStep.setWoPlannedResourceDurationHours(3);
		woProjectStep.setDeliveryDate(LocalDate.parse("2022-08-05"));
		woProjectStep.setWOTargetStartDate(LocalDate.parse("2022-07-05"));
		woProjectStep.setWOTargetEndDate(LocalDate.parse("2022-07-31"));
		woProjectStep.setWOPlannedPersonDurationHours(20);
		woProjectStep.setWOStepStatus(JsonWOStepStatus.CANCELED);
		woProjectStep.setWOFindingsReleasedDate(LocalDate.parse("2022-08-03"));
		woProjectStep.setWOFindingsCreatedDate(LocalDate.parse("2022-08-01"));
		woProjectStep.setExternalId("1111");

		woProjectRequest.setSteps(ImmutableList.of(woProjectStep));

		final JsonWorkOrderResourceUpsertItemRequest woProjectResource = new JsonWorkOrderResourceUpsertItemRequest();
		woProjectResource.setResourceIdentifier("int-resourceIdentifier");
		woProjectResource.setAssignDateFrom(LocalDate.parse("2022-07-15"));
		woProjectResource.setAssignDateTo(LocalDate.parse("2022-07-16"));
		woProjectResource.setActive(true);
		woProjectResource.setAllDay(false);
		woProjectResource.setDuration(BigDecimal.TEN);
		woProjectResource.setDurationUnit(JsonDurationUnit.Month);
		woProjectResource.setTestFacilityGroupName("testFacilityGroupName");
		woProjectResource.setExternalId("1111");

		woProjectStep.setResources(ImmutableList.of(woProjectResource));

		final JsonWorkOrderObjectUnderTestUpsertItemRequest woObjectUnderTest = new JsonWorkOrderObjectUnderTestUpsertItemRequest();
		woObjectUnderTest.setNumberOfObjectsUnderTest(10);
		woObjectUnderTest.setWoDeliveryNote("woDeliveryNote");
		woObjectUnderTest.setWoManufacturer("woManufacturer");
		woObjectUnderTest.setWoObjectType("woObjectType");
		woObjectUnderTest.setWoObjectName("woObjectName");
		woObjectUnderTest.setWoObjectWhereabouts("woObjectWhereabouts");
		woObjectUnderTest.setExternalId("3333");
		woObjectUnderTest.setIdentifier("ext-3333");

		woProjectRequest.setObjectsUnderTest(ImmutableList.of(woObjectUnderTest));

		final String string = mapper.writeValueAsString(woProjectRequest);
		assertThat(string).isNotEmpty();

		final JsonWorkOrderProjectUpsertRequest result = mapper.readValue(string, JsonWorkOrderProjectUpsertRequest.class);

		assertThat(result).isEqualTo(woProjectRequest);
		expect(result).toMatchSnapshot();
	}
}
