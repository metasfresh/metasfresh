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
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.uom.UomId;
import de.metas.user.UserId;

import java.time.Instant;

public interface TestConstants
{
	String MOCK_EXTERNAL_PROJECT_OWNER = "externalProjectOwner";
	String MOCK_EXTERNAL_REFERENCE = "externalReference";
	ExternalSystem MOCK_EXTERNAL_PROJECT = ExternalSystem.GITHUB;
	ExternalProjectType MOCK_EXTERNAL_PROJECT_TYPE = ExternalProjectType.EFFORT;

	String MOCK_NAME = "name";
	String MOCK_SEARCH_KEY = "searchKey";
	String MOCK_DESCRIPTION = "description";
	String MOCK_EXTERNAL_ID = "externalId";
	String MOCK_EXTERNAL_URL = "externalURL";
	String MOCK_EXTERNAL_ISSUE_NO = "externalIssueNo";
	String MOCK_DATE_ISO_8601 = "2020-03-16T14:37:53Z";
	Instant MOCK_INSTANT = Instant.parse(MOCK_DATE_ISO_8601);
	String MOCK_OAUTH_TOKEN = "oAuthToken";
	String MOCK_VALUE = "value";
	String MOCK_BUG_6_LABEL = "bud:6";
	String MOCK_EST_4_25_LABEL = "est:4.25";

	ExternalProjectReferenceId MOCK_EXTERNAL_PROJECT_REFERENCE_ID_INACTIVE = ExternalProjectReferenceId.ofRepoId(2);
	ExternalProjectReferenceId MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE = ExternalProjectReferenceId.ofRepoId(1);
	MilestoneId MOCK_MILESTONE_ID = MilestoneId.ofRepoId(1);
	OrgId MOCK_ORG_ID = OrgId.ANY;
	ProjectId MOCK_PROJECT_ID = ProjectId.ofRepoId(1);
	UserId MOCK_USER_ID = UserId.ofRepoId(1);
	UomId MOCK_UOM_ID = UomId.ofRepoId(1);
}
