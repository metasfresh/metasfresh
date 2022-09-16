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

import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.serviceprovider.costcenter.CostCenterService;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.model.I_S_Issue;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_S_Issue.class)
@Component
public class S_Issue
{
	private final CostCenterService costCenterService;

	public S_Issue(final CostCenterService costCenterService)
	{
		this.costCenterService = costCenterService;
	}

	/**
	 * Timing AFTER_* is needed as the issue will be loaded again via Repository during {@link CostCenterService#syncLabelsWithCostCenter(OrgId, IssueId, ActivityId)}
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_S_Issue.COLUMNNAME_C_Activity_ID })
	public void syncCostCenter(@NonNull final I_S_Issue record)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final IssueId issueId = IssueId.ofRepoId(record.getS_Issue_ID());
		final ActivityId costCenterId = ActivityId.ofRepoIdOrNull(record.getC_Activity_ID());

		costCenterService.syncLabelsWithCostCenter(orgId, issueId, costCenterId);
	}
}
