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

package de.metas.serviceprovider.issue;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetail;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class IssueEntity
{
	@Nullable
	private IssueId issueId;

	@Nullable
	private ProjectId projectId;

	@NonNull
	private OrgId orgId;

	@Nullable
	private MilestoneId milestoneId;

	@NonNull
	private UomId effortUomId;

	@Nullable
	private BigDecimal estimatedEffort;

	@Nullable
	private BigDecimal budgetedEffort;

	@Nullable
	private String description;

	@NonNull
	private String name;

	@NonNull
	private String searchKey;

	@NonNull
	private IssueType type;

	private boolean processed;

	private boolean isEffortIssue;

	@Nullable
	private UserId assigneeId;

	@Nullable
	private String externalIssueNo;
	@Nullable
	private String externalIssueURL;

	@NonNull
	private ImmutableList<ExternalIssueDetail> externalIssueDetails;

	public void setEstimatedEffortIfNull(@Nullable final BigDecimal estimatedEffort)
	{
		if ( NumberUtils.asBigDecimal(this.estimatedEffort,BigDecimal.ZERO).equals(BigDecimal.ZERO) )
		{
			this.estimatedEffort = estimatedEffort;
		}
	}

	public void setBudgetedEffortIfNull(@Nullable final BigDecimal budgetedEffort)
	{
		if ( NumberUtils.asBigDecimal(this.budgetedEffort,BigDecimal.ZERO).equals(BigDecimal.ZERO) )
		{
			this.budgetedEffort = budgetedEffort;
		}
	}

	public void setAssigneeIdIfNull(@Nullable final UserId assigneeId)
	{
		if (this.assigneeId == null)
		{
			this.assigneeId = assigneeId;
		}
	}
}
