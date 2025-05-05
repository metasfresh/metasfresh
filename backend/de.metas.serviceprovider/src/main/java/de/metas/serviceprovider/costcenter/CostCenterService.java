/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.costcenter;

import com.google.common.collect.ImmutableList;
import de.metas.activity.repository.Activity;
import de.metas.activity.repository.ActivityRepository;
import de.metas.activity.repository.CreateActivityRequest;
import de.metas.activity.repository.GetSingleActivityQuery;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.serviceprovider.external.label.IssueLabel;
import de.metas.serviceprovider.external.label.IssueLabelService;
import de.metas.serviceprovider.external.label.LabelCollection;
import de.metas.serviceprovider.github.GithubImporterConstants;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class CostCenterService
{
	private final IssueRepository issueRepository;
	private final IssueLabelService issueLabelService;
	private final ActivityRepository activityRepository;

	public CostCenterService(
			@NonNull final IssueRepository issueRepository,
			@NonNull final IssueLabelService issueLabelService,
			@NonNull final ActivityRepository activityRepository)
	{
		this.issueRepository = issueRepository;
		this.issueLabelService = issueLabelService;
		this.activityRepository = activityRepository;
	}

	public void syncLabelsWithCostCenter(
			@NonNull final OrgId orgId,
			@NonNull final IssueId issueId,
			@Nullable final ActivityId costCenterId)
	{
		final LabelCollection labelCollection = issueLabelService.getByIssueId(issueId);

		if (costCenterId == null)
		{
			deleteCostCenterLabel(labelCollection);
			return;
		}

		addCostCenterLabel(costCenterId, orgId, labelCollection);
	}

	public void syncWithCostCenterLabel(@NonNull final IssueId issueId, @NonNull final IssueLabel costCenterLabel)
	{
		final IssueEntity issueEntity = issueRepository.getById(issueId);

		final ActivityId costCenterId = computeCostCenterId(issueEntity, costCenterLabel);

		if (ActivityId.equals(issueEntity.getCostCenterActivityId(), costCenterId))
		{
			return;
		}

		final IssueEntity issueWithSyncedCostCenter = issueEntity.toBuilder()
				.costCenterActivityId(costCenterId)
				.build();

		issueRepository.save(issueWithSyncedCostCenter);
	}

	public void removeCostCenter(@NonNull final IssueId issueId, @NonNull final IssueLabel removedCostCenterLabel)
	{
		final ActivityId activityIdToRemove = resolveCostCenterId(removedCostCenterLabel).orElse(null);

		if (activityIdToRemove == null)
		{
			return;
		}

		final IssueEntity issueEntity = issueRepository.getById(issueId);

		if (!ActivityId.equals(issueEntity.getCostCenterActivityId(), activityIdToRemove))
		{
			return;
		}

		final IssueEntity issueWithoutCostCenter = issueEntity.toBuilder()
				.costCenterActivityId(null)
				.build();

		issueRepository.save(issueWithoutCostCenter);
	}

	public void validateNoCostCenterLabel(@NonNull final IssueId issueId)
	{
		final LabelCollection labelCollection = issueLabelService.getByIssueId(issueId);

		final boolean costCenterAlreadySet = labelCollection
				.streamByType(ImmutableList.of(GithubImporterConstants.LabelType.COST_CENTER))
				.findFirst()
				.isPresent();

		if (costCenterAlreadySet)
		{
			throw new AdempiereException("S_Issue already has a cost center label applied!")
					.appendParametersToMessage()
					.setParameter("issueId", labelCollection.getIssueId())
					.markAsUserValidationError();
		}
	}

	@NonNull
	private ActivityId computeCostCenterId(@NonNull final IssueEntity issueEntity, @NonNull final IssueLabel costCenterLabel)
	{
		return resolveCostCenterId(costCenterLabel)
				.orElseGet(() -> {
					final CreateActivityRequest request = CreateActivityRequest.builder()
							.orgId(costCenterLabel.getOrgId())
							.value(costCenterLabel.getValueForType(GithubImporterConstants.LabelType.COST_CENTER))
							.name(issueEntity.getName())
							.build();

					return activityRepository.save(request);
				});
	}

	@NonNull
	private Optional<ActivityId> resolveCostCenterId(@NonNull final IssueLabel costCenterLabel)
	{
		final String costCenterValue = costCenterLabel.getValueForType(GithubImporterConstants.LabelType.COST_CENTER);

		final GetSingleActivityQuery query = GetSingleActivityQuery.builder()
				.orgId(costCenterLabel.getOrgId())
				.value(costCenterValue)
				.build();

		return activityRepository.getIdByActivityQuery(query);
	}

	private void deleteCostCenterLabel(@NonNull final LabelCollection labelCollection)
	{
		final ImmutableList<IssueLabel> labelsWithoutCostCenter = labelCollection.filter(label -> !label.matchesType(GithubImporterConstants.LabelType.COST_CENTER));

		issueLabelService.persistLabels(labelCollection.getIssueId(), labelsWithoutCostCenter);
	}

	private void addCostCenterLabel(
			@NonNull final ActivityId costCenterActivityId,
			@NonNull final OrgId orgId,
			@NonNull final LabelCollection labelCollection)
	{
		final Activity costCenterActivity = activityRepository.getById(costCenterActivityId);

		final IssueLabel costCenterLabel = IssueLabel.builder()
				.value(GithubImporterConstants.LabelType.COST_CENTER.wrapValue(costCenterActivity.getValue()))
				.orgId(orgId)
				.build();

		final boolean isCostCenterLabelAlreadyUpToDate = labelCollection.hasLabel(costCenterLabel);

		if (isCostCenterLabelAlreadyUpToDate)
		{
			return;
		}

		final LabelCollection upToDateCollection = labelCollection.putLabelByType(costCenterLabel, GithubImporterConstants.LabelType.COST_CENTER);

		issueLabelService.persistLabels(labelCollection.getIssueId(), upToDateCollection.getIssueLabelList());
	}
}