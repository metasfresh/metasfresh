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

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.effortcontrol.EffortChange;
import de.metas.serviceprovider.effortcontrol.EffortControlService;
import de.metas.serviceprovider.effortcontrol.EffortInfo;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.Status;
import de.metas.serviceprovider.issue.agg.key.impl.IssueEffortKeyBuilder;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Set;

@Interceptor(I_S_Issue.class)
@Component
public class S_Issue
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	private final EffortControlService effortControlService;

	public S_Issue(@NonNull final EffortControlService effortControlService)
	{
		this.effortControlService = effortControlService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_S_Issue.COLUMNNAME_AD_Org_ID,
					I_S_Issue.COLUMNNAME_C_Activity_ID,
					I_S_Issue.COLUMNNAME_C_Project_ID })
	public void setEffortAggregationKey(@NonNull final I_S_Issue record)
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

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					I_S_Issue.COLUMNNAME_AD_Org_ID,
					I_S_Issue.COLUMNNAME_IssueEffort,
					I_S_Issue.COLUMNNAME_BudgetedEffort,
					I_S_Issue.COLUMNNAME_InvoiceableEffort,
					I_S_Issue.COLUMNNAME_Status,
					I_S_Issue.COLUMNNAME_C_Activity_ID,
					I_S_Issue.COLUMNNAME_C_Project_ID
			})
	public void syncEffortControl(@NonNull final I_S_Issue record, @NonNull final ModelChangeType timing)
	{
		final I_S_Issue oldRecord = InterfaceWrapperHelper.createOld(record, I_S_Issue.class);

		final EffortInfo currentEffortInfo = buildEffortInfoFromRecord(record);
		final EffortInfo oldEffortInfo = timing.isNew()
				? null
				: buildEffortInfoFromRecord(oldRecord);

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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_S_Issue.COLUMNNAME_AD_Org_ID,
					I_S_Issue.COLUMNNAME_C_Activity_ID,
					I_S_Issue.COLUMNNAME_C_Project_ID })
	public void forbidUpdatesOnInvoicedIssue(@NonNull final I_S_Issue record)
	{
		final Status status = Status.ofCode(record.getStatus());

		if (status.equals(Status.INVOICED))
		{
			throw new AdempiereException("OrgId, ActivityId & ProjectId cannot be changed for an INVOICED issue record!")
					.appendParametersToMessage()
					.setParameter("S_Issue_ID", record.getS_Issue_ID())
					.setParameter("AD_Org_ID", record.getAD_Org_ID())
					.setParameter("C_Project_ID", record.getC_Project_ID())
					.setParameter("C_Activity_ID", record.getC_Activity_ID());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_S_Issue.COLUMNNAME_Status })
	public void forbidStatusChangeOnIssueWithIC(@NonNull final I_S_Issue record)
	{
		final Status status = Status.ofCode(record.getStatus());

		if (status.equals(Status.INVOICED))
		{
			return;
		}

		final Set<InvoiceCandidateId> existingCandidateIds = invoiceCandDAO.retrieveReferencingIds(TableRecordReference.of(record));

		if (existingCandidateIds.isEmpty())
		{
			return;
		}

		throw new AdempiereException("There are existing invoice candidates for the given issue. Status cannot be changed from INVOICED!")
				.appendParametersToMessage()
				.setParameter("S_Issue_ID", record.getS_Issue_ID())
				.setParameter("InvoiceCandidateIds", existingCandidateIds);
	}

	@Nullable
	private EffortInfo buildEffortInfoFromRecord(@NonNull final I_S_Issue record)
	{
		final IssueEntity issue = IssueRepository.ofRecord(record);
		return effortControlService.getEffortInfo(issue).orElse(null);
	}
}
