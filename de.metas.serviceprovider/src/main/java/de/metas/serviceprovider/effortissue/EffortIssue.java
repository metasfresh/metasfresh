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

package de.metas.serviceprovider.effortissue;

import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetail;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class EffortIssue
{
	@NonNull
	private OrgId orgId;

	private EffortIssueId effortIssueId;

	private UserId assigneeId;

	@NonNull
	private String name;

	private String description;

	private MilestoneId milestoneId;

	private BigDecimal estimatedEffort;

	private BigDecimal budgetedEffort;

	@NonNull
	private UomId effortUomId;

	private boolean processed;

	private String externalIssueId;

	private String externalIssueNo;

	private String externalIssueURL;

	private List<ExternalIssueDetail> externalIssueDetailList;

	public void setEstimatedEffortIfNull(final BigDecimal estimatedEffort)
	{
		if ( NumberUtils.asBigDecimal(this.estimatedEffort,BigDecimal.ZERO).equals(BigDecimal.ZERO) )
		{
			this.estimatedEffort = estimatedEffort;
		}
	}

	public void setBudgetedEffortIfNull(final BigDecimal budgetedEffort)
	{
		if ( NumberUtils.asBigDecimal(this.budgetedEffort,BigDecimal.ZERO).equals(BigDecimal.ZERO) )
		{
			this.budgetedEffort = budgetedEffort;
		}
	}

	public void setMilestoneIdIfNull(final MilestoneId milestoneId)
	{
		if (this.milestoneId == null)
		{
			this.milestoneId = milestoneId;
		}
	}

	public void setDescriptionIfNull(final String description)
	{
		if (Check.isEmpty(this.description))
		{
			this.description = description;
		}
	}

	public void setAssigneeIdIfNull(final UserId assigneeId)
	{
		if (this.assigneeId == null)
		{
			this.assigneeId = assigneeId;
		}
	}
}
