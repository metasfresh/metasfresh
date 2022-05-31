/*
 * #%L
 * de.metas.business
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

package de.metas.activity.repository;

import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_Activity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ActivityRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<ActivityId> getIdByActivityQuery(@NonNull final GetSingleActivityQuery activityQuery)
	{
		final IQueryBuilder<I_C_Activity> activityQueryBuilder = queryBL.createQueryBuilder(I_C_Activity.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Activity.COLUMNNAME_AD_Org_ID, OrgId.ANY, activityQuery.getOrgId());

		if (Check.isNotBlank(activityQuery.getValue()))
		{
			activityQueryBuilder.addEqualsFilter(I_C_Activity.COLUMNNAME_Value, activityQuery.getValue());
		}

		if (activityQuery.getActivityId() != null)
		{
			activityQueryBuilder.addEqualsFilter(I_C_Activity.COLUMNNAME_C_Activity_ID, activityQuery.getActivityId());
		}

		return activityQueryBuilder
				.create()
				.firstIdOnlyOptional(ActivityId::ofRepoId);
	}
}
