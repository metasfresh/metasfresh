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
import org.adempiere.model.InterfaceWrapperHelper;
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
		return toQueryBuilder(activityQuery)
				.create()
				.firstIdOnlyOptional(ActivityId::ofRepoIdOrNull);
	}

	@NonNull
	public ActivityId save(@NonNull final CreateActivityRequest request)
	{
		final I_C_Activity record = InterfaceWrapperHelper.newInstance(I_C_Activity.class);

		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setName(request.getName());
		record.setValue(request.getValue());

		InterfaceWrapperHelper.save(record);

		return ActivityId.ofRepoId(record.getC_Activity_ID());
	}

	@NonNull
	public Optional<Activity> getByActivityQuery(@NonNull final GetSingleActivityQuery activityQuery)
	{
		return toQueryBuilder(activityQuery)
				.create()
				.firstOptional(I_C_Activity.class)
				.map(ActivityRepository::fromRecord);
	}

	@NonNull
	public Activity getById(@NonNull final ActivityId activityId)
	{
		return fromRecord(InterfaceWrapperHelper.load(activityId, I_C_Activity.class));
	}

	@NonNull
	private IQueryBuilder<I_C_Activity> toQueryBuilder(@NonNull final GetSingleActivityQuery activityQuery)
	{
		final IQueryBuilder<I_C_Activity> activityQueryBuilder = queryBL.createQueryBuilder(I_C_Activity.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_Activity.COLUMNNAME_C_Activity_ID)
				.addInArrayFilter(I_C_Activity.COLUMNNAME_AD_Org_ID, OrgId.ANY, activityQuery.getOrgId());

		if (Check.isNotBlank(activityQuery.getValue()))
		{
			activityQueryBuilder.addEqualsFilter(I_C_Activity.COLUMNNAME_Value, activityQuery.getValue());
		}

		if (activityQuery.getActivityId() != null)
		{
			activityQueryBuilder.addEqualsFilter(I_C_Activity.COLUMNNAME_C_Activity_ID, activityQuery.getActivityId());
		}

		return activityQueryBuilder;
	}

	@NonNull
	private static Activity fromRecord(@NonNull final I_C_Activity record)
	{
		return Activity.builder()
				.activityId(ActivityId.ofRepoId(record.getC_Activity_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.value(record.getValue())
				.name(record.getName())
				.build();
	}
}
