/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider;

import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.external.reference.ExternalServiceReferenceType;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.uom.UomId;
import de.metas.user.UserId;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public interface TestConstants
{
	String MOCK_EXTERNAL_PROJECT_OWNER = "externalProjectOwner";
	String MOCK_EXTERNAL_REFERENCE = "externalReference";
	ExternalServiceReferenceType MOCK_EXTERNAL_REFERENCE_TYPE = ExternalServiceReferenceType.ISSUE_ID;
	ExternalSystem MOCK_EXTERNAL_SYSTEM = ExternalSystem.GITHUB;
	ExternalSystem MOCK_EXTERNAL_SYSTEM_1 = ExternalSystem.EVERHOUR;
	ExternalProjectType MOCK_EXTERNAL_PROJECT_TYPE = ExternalProjectType.EFFORT;
	String MOCK_EXTERNAL_ID = "externalId";
	String MOCK_EXTERNAL_URL = "externalURL";
	Integer MOCK_EXTERNAL_ISSUE_NO = 1;
	String MOCK_PARENT_EXTERNAL_ID = "parentExternalId";
	Integer MOCK_PARENT_ISSUE_NO = 1234;

	String MOCK_NAME = "name";
	String MOCK_SEARCH_KEY = "searchKey";
	String MOCK_DESCRIPTION = "description";
	String MOCK_PARENT_ISSUE_URL = "https://github.com/" + MOCK_EXTERNAL_PROJECT_OWNER + "/" + MOCK_EXTERNAL_REFERENCE + "/issues/" + MOCK_PARENT_ISSUE_NO;
	String MOCK_ISSUE_URL =  "https://github.com/" + MOCK_EXTERNAL_PROJECT_OWNER + "/" + MOCK_EXTERNAL_REFERENCE + "/issues/" + MOCK_EXTERNAL_ISSUE_NO;
	String MOCK_DATE_ISO = "2020-03-16";
	Instant MOCK_INSTANT_FROM_DATE = LocalDate.parse(MOCK_DATE_ISO).atStartOfDay(ZoneOffset.UTC).toInstant();
	String MOCK_DATE_AND_TIME_ISO_8601 = "2020-03-16T14:37:53Z";
	Instant MOCK_INSTANT = Instant.parse(MOCK_DATE_AND_TIME_ISO_8601);
	String MOCK_AUTH_TOKEN = "authToken";
	String MOCK_VALUE = "value";
	String MOCK_BUD_6_LABEL = "bud:6";
	String MOCK_EST_4_25_LABEL = "est:4.25";
	long MOCK_BOOKED_SECONDS = 1800;
	String MOCK_HOURS_AND_MINUTES = "0:30";
	String MOCK_ERROR_MESSAGE = "errorMessage";
	String MOCK_JSON_VALUE = "mockedJSONValue";
	LocalDate MOCK_DATE_2020_03_01 = LocalDate.of(2020, 3, 1);
	LocalDate MOCK_DATE_2020_03_07 = LocalDate.of(2020, 3, 7);
	LocalDate MOCK_DATE_2020_03_08 = LocalDate.of(2020,3,8);
	LocalDate MOCK_DATE_2020_03_12 = LocalDate.of(2020, 3, 12);
	String MOCK_EFFORT_1_30 = "1:30";
	String MOCK_EFFORT_1_00 = "1:00";
	String MOCK_EFFORT_2_30 = "2:30";

	int MOCK_RECORD_ID = 1;
	ExternalProjectReferenceId MOCK_EXTERNAL_PROJECT_REFERENCE_ID_INACTIVE = ExternalProjectReferenceId.ofRepoId(2);
	ExternalProjectReferenceId MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE = ExternalProjectReferenceId.ofRepoId(1);
	MilestoneId MOCK_MILESTONE_ID = MilestoneId.ofRepoId(1);
	OrgId MOCK_ORG_ID = OrgId.ANY;
	ProjectId MOCK_PROJECT_ID = ProjectId.ofRepoId(1);
	UserId MOCK_USER_ID = UserId.ofRepoId(1);
	IssueId MOCK_ISSUE_ID = IssueId.ofRepoId(1);
	String MOCK_GH_TASK_ID = "gh:" + MOCK_ISSUE_ID.getRepoId();
	String MOCK_EV_TASK_ID = "ev:" + MOCK_ISSUE_ID.getRepoId();
}
