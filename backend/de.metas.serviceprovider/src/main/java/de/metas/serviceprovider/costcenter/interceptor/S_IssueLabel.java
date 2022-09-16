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

package de.metas.serviceprovider.costcenter.interceptor;

import de.metas.serviceprovider.costcenter.CostCenterService;
import de.metas.serviceprovider.external.label.IssueLabel;
import de.metas.serviceprovider.external.label.IssueLabelRepository;
import de.metas.serviceprovider.github.GithubImporterConstants;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.model.I_S_IssueLabel;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Interceptor(I_S_IssueLabel.class)
@Component
public class S_IssueLabel
{
	private final CostCenterService costCenterService;

	public S_IssueLabel(final CostCenterService costCenterService)
	{
		this.costCenterService = costCenterService;
	}

	/**
	 * Timing AFTER_* is needed so its latest (current) value it's available in case S_Issue.C_Activity_ID gets updated triggering another MI
	 * which ends up fetching the label (i.e. {@link S_Issue#syncCostCenter(I_S_Issue)})
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void addCostCenter(@NonNull final I_S_IssueLabel record)
	{
		final Matcher matcher = GithubImporterConstants.LabelType.COST_CENTER.getPattern().matcher(record.getLabel());

		if (!matcher.matches())
		{
			return;
		}

		final IssueLabel costCenterLabel = IssueLabelRepository.fromRecord(record);

		costCenterService.syncWithCostCenterLabel(IssueId.ofRepoId(record.getS_Issue_ID()), costCenterLabel);
	}

	/**
	 * Timing AFTER_* is needed so its removal is acknowledged in case S_Issue.C_Activity_ID gets updated triggering another MI
	 * which ends up fetching the issue's labels (i.e. {@link S_Issue#syncCostCenter(I_S_Issue)})
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void removeCostCenterFromIssue(@NonNull final I_S_IssueLabel record)
	{
		final Matcher matcher = GithubImporterConstants.LabelType.COST_CENTER.getPattern().matcher(record.getLabel());

		if (!matcher.matches())
		{
			return;
		}

		final IssueLabel costCenterLabel = IssueLabelRepository.fromRecord(record);

		costCenterService.removeCostCenter(IssueId.ofRepoId(record.getS_Issue_ID()), costCenterLabel);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void validateSingleCostCenterLabel(@NonNull final I_S_IssueLabel record)
	{
		final Matcher matcher = GithubImporterConstants.LabelType.COST_CENTER.getPattern().matcher(record.getLabel());

		if (!matcher.matches())
		{
			return;
		}

		costCenterService.validateNoCostCenterLabel(IssueId.ofRepoId(record.getS_Issue_ID()));
	}
}
