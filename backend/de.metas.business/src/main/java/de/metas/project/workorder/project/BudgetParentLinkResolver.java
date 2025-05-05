/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.project.workorder.project;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectQuery;
import de.metas.project.budget.BudgetProjectRepository;
import de.metas.util.Loggables;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ISysConfigBL;

import java.util.List;
import java.util.Optional;

@Value
@Builder
public class BudgetParentLinkResolver
{
	private final static String BUDGET_PARENT_EXTERNAL_ID_MIN_NR_OF_CHARS = "de.metas.project.workorder.project.budgetParentExternalIdMinNrOfChars";
	private final static String BUDGET_PARENT_EXTERNAL_ID_ENCLOSING_MARKER = "-";

	@NonNull ISysConfigBL sysConfigBL;
	@NonNull BudgetProjectRepository budgetProjectRepository;
	@NonNull String woProjectReferenceExt;
	@NonNull OrgId orgId;

	public BudgetParentLinkResolver(
			@NonNull final ISysConfigBL sysConfigBL,
			@NonNull final BudgetProjectRepository budgetProjectRepository,
			@NonNull final String woProjectReferenceExt,
			@NonNull final OrgId orgId)
	{
		this.sysConfigBL = sysConfigBL;
		this.budgetProjectRepository = budgetProjectRepository;
		this.woProjectReferenceExt = woProjectReferenceExt;
		this.orgId = orgId;
	}

	public Optional<ProjectId> resolve()
	{
		final String budgetExternalIdPattern = getBudgetExternalIdPattern().orElse(null);
		if (budgetExternalIdPattern == null)
		{
			Loggables.addLog("BudgetExternalId couldn't be computed for C_Project.C_Project_Reference_Ext={}", woProjectReferenceExt);
			return Optional.empty();
		}

		final List<ProjectId> budgetProjectsIds = getMatchingBudgetProjects(budgetExternalIdPattern);

		if (budgetProjectsIds.size() != 1)
		{
			Loggables.addLog("More than one/Or no budget project found for C_Project_Reference_Ext={}, budgetProjectIds={}",
							 woProjectReferenceExt, budgetProjectsIds);
			return Optional.empty();
		}
		return Optional.of(budgetProjectsIds.get(0));
	}

	@NonNull
	private Optional<String> getBudgetExternalIdPattern()
	{
		final int budgetExternalIdClosingMarkerIndex = woProjectReferenceExt.indexOf(BUDGET_PARENT_EXTERNAL_ID_ENCLOSING_MARKER);
		if (budgetExternalIdClosingMarkerIndex <= 0)
		{
			return Optional.empty();
		}

		final Integer budgetExternalIdMinChars = getBudgetExternalIdMinNrOfChars().orElse(null);
		if (budgetExternalIdMinChars == null)
		{
			return Optional.empty();
		}

		final String externalIdCandidatePart = woProjectReferenceExt.substring(0, budgetExternalIdClosingMarkerIndex - 1);
		if (budgetExternalIdMinChars >= externalIdCandidatePart.length())
		{
			return Optional.of(externalIdCandidatePart);
		}

		final int startIndex = externalIdCandidatePart.length() - budgetExternalIdMinChars;
		return Optional.of(externalIdCandidatePart.substring(startIndex, externalIdCandidatePart.length() - 1));
	}

	@NonNull
	private Optional<Integer> getBudgetExternalIdMinNrOfChars()
	{
		return Optional.of(sysConfigBL.getIntValue(BUDGET_PARENT_EXTERNAL_ID_MIN_NR_OF_CHARS, -1))
				.filter(nrOfChars -> nrOfChars > 0);
	}

	@NonNull
	private List<ProjectId> getMatchingBudgetProjects(@NonNull final String externalIdPattern)
	{
		return budgetProjectRepository.getBy(getBudgetQueryForPattern(externalIdPattern))
				.stream()
				.filter(budgetProject -> budgetProject.getExternalId() != null)
				.filter(budgetProject -> woProjectReferenceExt.contains(budgetProject.getExternalId().getValue() + BUDGET_PARENT_EXTERNAL_ID_ENCLOSING_MARKER))
				.map(BudgetProject::getProjectId)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private BudgetProjectQuery getBudgetQueryForPattern(@NonNull final String externalIdPattern)
	{
		return BudgetProjectQuery.builder()
				.orgId(orgId)
				.externalIdPattern(externalIdPattern)
				.build();
	}
}
