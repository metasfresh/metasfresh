/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.activity;

import de.metas.activity.repository.ActivityRepository;
import de.metas.activity.repository.GetSingleActivityQuery;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ActivityService
{
	private final ActivityRepository activityRepository;

	public ActivityService(@NonNull final ActivityRepository activityRepository)
	{
		this.activityRepository = activityRepository;
	}

	@NonNull
	public ActivityId resolveExternalIdentifier(@NonNull final OrgId orgId, @NonNull final ExternalIdentifier activityIdentifier)
	{
		final GetSingleActivityQuery.GetSingleActivityQueryBuilder queryBuilder = GetSingleActivityQuery.builder()
				.orgId(orgId);

		switch (activityIdentifier.getType())
		{
			case METASFRESH_ID:
				queryBuilder
						.activityId(ActivityId.ofRepoId(activityIdentifier.asMetasfreshId().getValue()));
				break;
			case VALUE:
				queryBuilder
						.value(activityIdentifier.asValue());
				break;
			default:
				throw new InvalidIdentifierException(activityIdentifier.getRawValue());
		}

		return activityRepository.getIdByActivityQuery(queryBuilder.build())
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("C_Activity")
						.resourceIdentifier(activityIdentifier.getRawValue())
						.build());
	}
}
