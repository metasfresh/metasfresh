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

package de.metas.serviceprovider.issue.activity;

import de.metas.activity.repository.ActivityRepository;
import de.metas.activity.repository.CreateActivityRequest;
import de.metas.activity.repository.GetSingleActivityQuery;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CostCenterActivityService
{
	private final ActivityRepository activityRepository;

	public CostCenterActivityService(@NonNull final ActivityRepository activityRepository)
	{
		this.activityRepository = activityRepository;
	}

	@NonNull
	public ActivityId getOrCreateCostCenter(@NonNull final CreateActivityRequest createActivityRequest)
	{
		return getByQuery(createActivityRequest.getOrgId(), createActivityRequest.getValue())
				.orElseGet(() -> activityRepository.save(createActivityRequest));
	}

	@NonNull
	private Optional<ActivityId> getByQuery(@NonNull final OrgId orgId, @NonNull final String value)
	{
		final GetSingleActivityQuery query = GetSingleActivityQuery.builder()
				.orgId(orgId)
				.value(value)
				.build();

		return activityRepository.getIdByActivityQuery(query);
	}
}
