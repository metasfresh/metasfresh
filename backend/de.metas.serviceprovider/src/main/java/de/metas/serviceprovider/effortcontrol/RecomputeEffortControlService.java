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

package de.metas.serviceprovider.effortcontrol;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.serviceprovider.effortcontrol.repository.CreateEffortControlRequest;
import de.metas.serviceprovider.effortcontrol.repository.EffortControl;
import de.metas.serviceprovider.effortcontrol.repository.EffortControlId;
import de.metas.serviceprovider.effortcontrol.repository.EffortControlQuery;
import de.metas.serviceprovider.effortcontrol.repository.EffortControlRepository;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueQuery;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class RecomputeEffortControlService
{
	private static final Logger logger = LogManager.getLogger(RecomputeEffortControlService.class);

	private final IssueRepository issueRepository;
	private final EffortControlRepository effortControlRepository;

	public RecomputeEffortControlService(
			@NonNull final IssueRepository issueRepository,
			@NonNull final EffortControlRepository effortControlRepository)
	{
		this.issueRepository = issueRepository;
		this.effortControlRepository = effortControlRepository;
	}

	public void recomputeAllOpenEffortControlRecords()
	{
		issueRepository.setAggregationKeyIfMissing();

		issueRepository.getIssuesWithOpenEffortStream()
				.forEach(this::handleIssueWithoutEffortControl);
	}

	public void recomputeEffortControl(@NonNull final EffortControlId effortControlId)
	{
		final EffortControl effortControl = effortControlRepository.getById(effortControlId);

		recomputeValuesFor(effortControl);
	}

	private void handleIssueWithoutEffortControl(@NonNull final IssueEntity effortIssue)
	{
		Check.assumeNotNull(effortIssue.getCostCenterActivityId(), "CostCenterId should not be empty at this stage!");
		Check.assumeNotNull(effortIssue.getProjectId(), "ProjectId should not be empty at this stage!");

		final Properties issueCtx = createIssueCtx(effortIssue);

		try (final IAutoCloseable contextRestorer = Env.switchContext(issueCtx))
		{
			final EffortControl effortControl = resolveEffortControlFromIssue(effortIssue);
			recomputeValuesFor(effortControl);
		}
	}

	private void recomputeValuesFor(@NonNull final EffortControl effortControl)
	{
		final IssueQuery query = IssueQuery.builder()
				.orgId(effortControl.getOrgId())
				.costCenterId(effortControl.getCostCenterId())
				.projectId(effortControl.getProjectId())
				.build();

		final List<IssueEntity> effortControlIssues = issueRepository.geyByQuery(query);

		final EffortCollection effortCollection = EffortCollection.of(effortControl, effortControlIssues);

		computeEffort(effortCollection);
	}

	private void computeEffort(@NonNull final EffortCollection effortCollection)
	{
		final EffortControl updatedEffortControl = effortCollection.computeEffortControl();

		if (EffortControl.equals(updatedEffortControl, effortCollection.getEffortControl()))
		{
			return;
		}

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("Updated effort control with id: {}", effortCollection.getEffortControl().getEffortControlId().getRepoId())
				.addLog("old effort control: {}", effortCollection.getEffortControl())
				.addLog("updated effort control:{}", updatedEffortControl);

		effortControlRepository.update(updatedEffortControl);
	}

	@NonNull
	private EffortControl resolveEffortControlFromIssue(@NonNull final IssueEntity issueEntity)
	{
		final EffortControlQuery query = EffortControlQuery.builder()
				.orgId(issueEntity.getOrgId())
				.costCenterId(issueEntity.getCostCenterActivityId())
				.projectId(issueEntity.getProjectId())
				.build();

		return effortControlRepository.getOptionalByQuery(query)
				.orElseGet(() -> {
					final CreateEffortControlRequest createEffortControlRequest = CreateEffortControlRequest.builder()
							.orgId(issueEntity.getOrgId())
							.costCenterId(issueEntity.getCostCenterActivityId())
							.projectId(issueEntity.getProjectId())
							.build();

					return effortControlRepository.save(createEffortControlRequest);
				});
	}

	@NonNull
	private static Properties createIssueCtx(@NonNull final IssueEntity issueEntity)
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setClientId(ctx, issueEntity.getClientId());
		Env.setOrgId(ctx, issueEntity.getOrgId());
		return ctx;
	}
}
