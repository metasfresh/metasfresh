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

package de.metas.serviceprovider.effortcontrol.repository;

import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.model.I_S_EffortControl;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class EffortControlRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public EffortControl getById(@NonNull final EffortControlId effortControlId)
	{
		final I_S_EffortControl record = InterfaceWrapperHelper.load(effortControlId, I_S_EffortControl.class);

		return fromRecord(record);
	}

	@NonNull
	public Optional<EffortControl> getOptionalByQuery(@NonNull final EffortControlQuery query)
	{
		return queryBL.createQueryBuilder(I_S_EffortControl.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_EffortControl.COLUMNNAME_AD_Org_ID, query.getOrgId())
				.addEqualsFilter(I_S_EffortControl.COLUMNNAME_C_Activity_ID, query.getCostCenterId())
				.addEqualsFilter(I_S_EffortControl.COLUMNNAME_C_Project_ID, query.getProjectId())
				.create()
				.firstOnlyOptional()
				.map(EffortControlRepository::fromRecord);
	}

	@NonNull
	public EffortControl update(@NonNull final EffortControl effortControl)
	{
		final I_S_EffortControl updatedRecord = InterfaceWrapperHelper.load(effortControl.getEffortControlId(), I_S_EffortControl.class);

		updatedRecord.setPendingEffortSum(effortControl.getPendingEffortSum().getHmm());
		updatedRecord.setPendingEffortSumSeconds(BigDecimal.valueOf(effortControl.getPendingEffortSum().getSeconds()));

		updatedRecord.setEffortSum(effortControl.getEffortSum().getHmm());
		updatedRecord.setEffortSumSeconds(BigDecimal.valueOf(effortControl.getEffortSum().getSeconds()));

		updatedRecord.setBudget(effortControl.getBudget());
		updatedRecord.setInvoiceableHours(effortControl.getInvoiceableHours());

		updatedRecord.setIsOverBudget(effortControl.getIsOverBudget());

		InterfaceWrapperHelper.save(updatedRecord);

		return fromRecord(updatedRecord);
	}

	@NonNull
	public EffortControl save(@NonNull final CreateEffortControlRequest request)
	{
		final I_S_EffortControl record = InterfaceWrapperHelper.newInstance(I_S_EffortControl.class);

		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setC_Activity_ID(request.getCostCenterId().getRepoId());
		record.setC_Project_ID(request.getProjectId().getRepoId());

		InterfaceWrapperHelper.save(record);

		return fromRecord(record);
	}

	@NonNull
	public static EffortControl fromRecord(@NonNull final I_S_EffortControl record)
	{
		return EffortControl.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.effortControlId(EffortControlId.ofRepoId(record.getS_EffortControl_ID()))
				.costCenterId(ActivityId.ofRepoId(record.getC_Activity_ID()))
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.pendingEffortSum(Effort.ofNullable(record.getPendingEffortSum()))
				.effortSum(Effort.ofNullable(record.getEffortSum()))
				.budget(record.getBudget())
				.invoiceableHours(record.getInvoiceableHours())
				.build();
	}
}
