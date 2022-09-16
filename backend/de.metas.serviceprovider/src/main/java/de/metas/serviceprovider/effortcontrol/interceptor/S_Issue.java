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

package de.metas.serviceprovider.effortcontrol.interceptor;

import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.effortcontrol.EffortChange;
import de.metas.serviceprovider.effortcontrol.EffortControlService;
import de.metas.serviceprovider.effortcontrol.EffortInfo;
import de.metas.serviceprovider.effortcontrol.EffortTarget;
import de.metas.serviceprovider.issue.Status;
import de.metas.serviceprovider.issue.agg.key.impl.IssueEffortKeyBuilder;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.time.HmmUtils;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;

@Interceptor(I_S_Issue.class)
@Component
public class S_Issue
{
	private final EffortControlService effortControlService;

	public S_Issue(final EffortControlService effortControlService)
	{
		this.effortControlService = effortControlService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_S_Issue.COLUMNNAME_AD_Org_ID,
					I_S_Issue.COLUMNNAME_C_Activity_ID,
					I_S_Issue.COLUMNNAME_C_Project_ID })
	public void addEffortControlKey(@NonNull final I_S_Issue record)
	{
		final ActivityId costCenterId = ActivityId.ofRepoIdOrNull(record.getC_Activity_ID());
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(record.getC_Project_ID());

		if (costCenterId == null || projectId == null)
		{
			record.setEffortAggregationKey(null);
			return;
		}

		final String issueEffortAggKey = new IssueEffortKeyBuilder().buildKey(record);

		record.setEffortAggregationKey(issueEffortAggKey);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_S_Issue.COLUMNNAME_AD_Org_ID,
					I_S_Issue.COLUMNNAME_IssueEffort,
					I_S_Issue.COLUMNNAME_BudgetedEffort,
					I_S_Issue.COLUMNNAME_InvoiceableEffort,
					I_S_Issue.COLUMNNAME_Status,
					I_S_Issue.COLUMNNAME_C_Activity_ID,
					I_S_Issue.COLUMNNAME_C_Project_ID
			})
	public void syncEffortControl(@NonNull final I_S_Issue record)
	{
		final I_S_Issue oldRecord = InterfaceWrapperHelper.createOld(record, I_S_Issue.class);

		final ActivityId costCenterId = ActivityId.ofRepoIdOrNull(record.getC_Activity_ID());
		final ActivityId oldCostCenterId = ActivityId.ofRepoIdOrNull(oldRecord.getC_Activity_ID());

		if (costCenterId == null && oldCostCenterId == null)
		{
			return;
		}

		final ProjectId projectId = ProjectId.ofRepoIdOrNull(record.getC_Project_ID());
		final ProjectId oldProjectId = ProjectId.ofRepoIdOrNull(oldRecord.getC_Project_ID());

		if (projectId == null && oldProjectId == null)
		{
			return;
		}

		final EffortInfo currentEffortInfo = buildEffortInfoFromRecord(record);
		final EffortInfo oldEffortInfo = buildEffortInfoFromRecord(oldRecord);

		if (currentEffortInfo == null && oldEffortInfo == null)
		{
			return;
		}

		final EffortChange effortChange = EffortChange.builder()
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.currentEffortValues(currentEffortInfo)
				.oldEffortValues(oldEffortInfo)
				.build();

		effortControlService.handleEffortChanges(effortChange);
	}

	@Nullable
	private EffortInfo buildEffortInfoFromRecord(@NonNull final I_S_Issue record)
	{
		final ActivityId costCenterId = ActivityId.ofRepoIdOrNull(record.getC_Activity_ID());
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(record.getC_Project_ID());

		if (costCenterId == null || projectId == null)
		{
			return null;
		}

		final EffortTarget effortTarget = EffortTarget.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.costCenterId(costCenterId)
				.projectId(projectId)
				.build();

		final Duration issueEffort = Duration.ofSeconds(HmmUtils.hmmToSeconds(record.getIssueEffort()));

		return EffortInfo.builder()
				.effortTarget(effortTarget)
				.issueStatus(Status.ofCode(record.getStatus()))
				.effortSum(issueEffort)
				//note: budget is spread across multiple effort issues but also kept at budget level
				.budget(record.isEffortIssue() ? record.getBudgetedEffort() : BigDecimal.ZERO)
				.invoiceableHours(record.isEffortIssue() ? BigDecimal.ZERO : record.getInvoiceableEffort())
				.build();
	}
}
