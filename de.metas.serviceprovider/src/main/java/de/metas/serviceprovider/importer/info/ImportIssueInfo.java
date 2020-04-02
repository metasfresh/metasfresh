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

package de.metas.serviceprovider.importer.info;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetail;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.milestone.Milestone;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class ImportIssueInfo
{
	@Nullable
	ProjectId projectId;

	@NonNull
	OrgId orgId;

	@NonNull
	ExternalProjectType externalProjectType;

	@Nullable
	BigDecimal estimation;

	@Nullable
	BigDecimal budget;

	@NonNull
	UomId effortUomId;

	@NonNull
	String name;

	@Nullable
	String description;

	boolean processed;

	@Nullable
	UserId assigneeId;

	@NonNull
	String externalIssueId;

	@Nullable
	String externalIssueNo;

	@Nullable
	String externalIssueURL;

	@Nullable
	Milestone milestone;

	@NonNull
	ImmutableList<ExternalIssueDetail> externalIssueDetails;

	public String getSearchKey()
	{
		return StringUtils.nullToEmpty(getExternalIssueNo()).concat(" ").concat(getName()).trim();
	}
}
