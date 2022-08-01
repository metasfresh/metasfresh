/*
 * #%L
 * de.metas.business
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

package de.metas.project.workorder.data;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.util.lang.ExternalId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;

@Value
@Builder
public class WOProjectStep
{
	@Nullable
	@Getter
	WOProjectStepId woProjectStepId;

	@NonNull
	String name;

	@With
	@Nullable
	@Getter(AccessLevel.NONE)
	ProjectId projectId;

	@Nullable
	String description;

	@Nullable
	Integer seqNo;

	@Nullable
	Instant dateStart;

	@Nullable
	Instant dateEnd;

	@Nullable
	ExternalId externalId;

	@Nullable
	Instant woPartialReportDate;

	@Nullable
	Integer woPlannedResourceDurationHours;

	@Nullable
	Instant deliveryDate;

	@Nullable
	Instant woTargetStartDate;

	@Nullable
	Instant woTargetEndDate;

	@Nullable
	Integer woPlannedPersonDurationHours;

	@Nullable
	WOStepStatus woStepStatus;

	@Nullable
	Instant woFindingsReleasedDate;

	@Nullable
	Instant woFindingsCreatedDate;

	@NonNull
	List<WOProjectResource> projectResources;

	@Builder
	public WOProjectStep(
			@Nullable final WOProjectStepId woProjectStepId,
			@NonNull final String name,
			@Nullable final ProjectId projectId,
			@Nullable final String description,
			@Nullable final Integer seqNo,
			@Nullable final Instant dateStart,
			@Nullable final Instant dateEnd,
			@Nullable final ExternalId externalId,
			@Nullable final Instant woPartialReportDate,
			@Nullable final Integer woPlannedResourceDurationHours,
			@Nullable final Instant deliveryDate,
			@Nullable final Instant woTargetStartDate,
			@Nullable final Instant woTargetEndDate,
			@Nullable final Integer woPlannedPersonDurationHours,
			@Nullable final WOStepStatus woStepStatus,
			@Nullable final Instant woFindingsReleasedDate,
			@Nullable final Instant woFindingsCreatedDate,
			@Nullable final List<WOProjectResource> projectResources)
	{
		this.woProjectStepId = woProjectStepId;
		this.name = name;
		this.projectId = projectId;
		this.description = description;
		this.seqNo = seqNo;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.externalId = externalId;
		this.woPartialReportDate = woPartialReportDate;
		this.woPlannedResourceDurationHours = woPlannedResourceDurationHours;
		this.deliveryDate = deliveryDate;
		this.woTargetStartDate = woTargetStartDate;
		this.woTargetEndDate = woTargetEndDate;
		this.woPlannedPersonDurationHours = woPlannedPersonDurationHours;
		this.woStepStatus = woStepStatus;
		this.woFindingsReleasedDate = woFindingsReleasedDate;
		this.woFindingsCreatedDate = woFindingsCreatedDate;
		this.projectResources = CoalesceUtil.coalesce(projectResources, ImmutableList.of());
	}

	@NonNull
	public WOProjectStepId getWOProjectStepIdNonNull()
	{
		if (woProjectStepId == null)
		{
			throw new AdempiereException("WOProjectStepId cannot be null at this stage!");
		}
		return woProjectStepId;
	}

	@NonNull
	public Integer getSeqNoNonNull()
	{
		if (seqNo == null)
		{
			throw new AdempiereException("WOProjectStep SeqNo cannot be null at this stage!");
		}
		return seqNo;
	}

	@NonNull
	public ProjectId getProjectIdNonNull()
	{
		if (projectId == null)
		{
			throw new AdempiereException("ProjectId cannot be null at this stage!");
		}

		return projectId;
	}

	@NonNull
	public ExternalId getExternalIdNonNull()
	{
		if (this.externalId == null)
		{
			throw new AdempiereException("ExternalId cannot be null at this stage!");
		}

		return this.externalId;
	}
}