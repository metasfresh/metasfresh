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

package de.metas.serviceprovider.issue.importer.info;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
@ToString(exclude = "oAuthToken")
public class ImportIssuesRequest
{
	@NonNull
	String oAuthToken;

	@NonNull
	String repoId;

	@NonNull
	String repoOwner;

	@NonNull
	ExternalProjectType externalProjectType;

	@NonNull
	OrgId orgId;

	@Nullable
	ProjectId projectId;

	@Nullable
	LocalDate dateFrom;

	@Nullable
	ImmutableList<String> issueNoList;

	public boolean importByIds()
	{
		return !Check.isEmpty(issueNoList);
	}

}
