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
import de.metas.externalreference.ExternalId;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.external.label.IssueLabel;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.external.project.ExternalProjectType;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.Status;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Value
@Builder(toBuilder = true)
public class ImportIssueInfo
{
	@Nullable
	ProjectId projectId;

	@NonNull
	ExternalProjectReferenceId externalProjectReferenceId;

	@NonNull
	String repositoryName;

	@NonNull
	OrgId orgId;

	@NonNull
	ExternalProjectType externalProjectType;

	@Nullable
	BigDecimal estimation;

	@Nullable
	BigDecimal budget;

	@Nullable
	BigDecimal roughEstimation;

	@NonNull
	UomId effortUomId;

	@NonNull
	String name;

	@Nullable
	String description;

	@Nullable
	Status status;

	@Nullable
	String deliveryPlatform;

	@Nullable
	LocalDate plannedUATDate;

	@Nullable
	UserId assigneeId;

	@NonNull
	ExternalId externalIssueId;

	@Nullable
	Integer externalIssueNo;

	@Nullable
	String externalIssueURL;

	@Nullable
	ImportMilestoneInfo milestone;

	@Nullable
	IssueId parentIssueId;

	@Nullable
	ExternalId externalParentIssueId;

	@NonNull
	@With
	ImmutableList<IssueLabel> issueLabels;

	@Nullable
	LocalDate deliveredDate;

	@NonNull
	Instant updatedAt;

	public String getSearchKey()
	{
		return externalIssueNo != null
				? externalIssueNo + " " + getName().trim()
				: getName().trim();
	}

	public boolean isEffortIssue()
	{
		return ExternalProjectType.EFFORT.equals(externalProjectType);
	}

	@NonNull
	public Optional<IssueLabel> getSingleLabel(@NonNull final Predicate<IssueLabel> filter)
	{
		final List<IssueLabel> matchingLabels = filterLabels(filter);

		if (!matchingLabels.isEmpty())
		{
			return Optional.of(CollectionUtils.singleElement(matchingLabels));
		}

		return Optional.empty();
	}

	@NonNull
	public List<IssueLabel> filterLabels(@NonNull final Predicate<IssueLabel> filter)
	{
		return issueLabels.stream()
				.filter(filter)
				.collect(ImmutableList.toImmutableList());
	}
}
