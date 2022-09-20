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
import com.google.common.collect.ImmutableList;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

@Service
public class RecomputeEffortControlService
{
	private static final Logger logger = LogManager.getLogger(RecomputeEffortControlService.class);

	private final IssueRepository issueRepository;
	private final EffortControlRepository effortControlRepository;
	private final EffortControlService effortControlService;

	public RecomputeEffortControlService(
			@NonNull final IssueRepository issueRepository,
			@NonNull final EffortControlRepository effortControlRepository,
			@NonNull final EffortControlService effortControlService)
	{
		this.issueRepository = issueRepository;
		this.effortControlRepository = effortControlRepository;
		this.effortControlService = effortControlService;
	}

	public void recomputeAllOpenEffortControlRecords()
	{
		issueRepository.setAggregationKeyIfMissing();

		final Set<EffortTarget> processedTargets = new HashSet<>();

		issueRepository.streamIssuesWithOpenEffort()
				.forEach(issue -> {
					final EffortTarget effortTarget = issue.getEffortTarget().orElse(null);

					Check.assumeNotNull(effortTarget, "Issues without an effort target should not be loaded!");

					if (processedTargets.contains(effortTarget))
					{
						return;
					}

					final Properties issueCtx = createIssueCtx(issue);

					try (final IAutoCloseable tempContext = Env.switchContext(issueCtx))
					{
						recomputeEffortControlFor(effortTarget);
					}

					processedTargets.add(effortTarget);
				});
	}

	public void recomputeEffortControlFor(@NonNull final EffortControlId effortControlId)
	{
		final EffortControl effortControl = effortControlRepository.getById(effortControlId);

		recomputeValuesFor(effortControl);
	}

	private void recomputeEffortControlFor(@NonNull final EffortTarget effortTarget)
	{
		final EffortControl effortControl = resolveEffortControlForTarget(effortTarget);
		recomputeValuesFor(effortControl);
	}

	private void recomputeValuesFor(@NonNull final EffortControl effortControl)
	{
		final IssueQuery query = IssueQuery.builder()
				.orgId(effortControl.getOrgId())
				.costCenterId(effortControl.getCostCenterId())
				.projectId(effortControl.getProjectId())
				.build();

		final List<EffortInfo> effortInfos = issueRepository.geyByQuery(query)
				.stream()
				.map(effortControlService::getEffortInfo)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableList.toImmutableList());

		final EffortControlCalculator effortCalculator = EffortControlCalculator.of(effortControl, effortInfos);

		updateEffort(effortCalculator);
	}

	private void updateEffort(@NonNull final EffortControlCalculator effortCollection)
	{
		final EffortControl updatedEffortControl = effortCollection.calculate();

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
	private EffortControl resolveEffortControlForTarget(@NonNull final EffortTarget effortTarget)
	{
		final EffortControlQuery query = EffortControlQuery.builder()
				.orgId(effortTarget.getOrgId())
				.costCenterId(effortTarget.getCostCenterId())
				.projectId(effortTarget.getProjectId())
				.build();

		return effortControlRepository.getOptionalByQuery(query)
				.orElseGet(() -> {
					final CreateEffortControlRequest createEffortControlRequest = CreateEffortControlRequest.builder()
							.orgId(effortTarget.getOrgId())
							.costCenterId(effortTarget.getCostCenterId())
							.projectId(effortTarget.getProjectId())
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
