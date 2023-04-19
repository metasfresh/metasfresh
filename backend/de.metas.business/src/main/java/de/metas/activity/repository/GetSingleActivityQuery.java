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
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class GetSingleActivityQuery
{
	@NonNull
	OrgId orgId;

	@Nullable
	String value;

	@Nullable
	ActivityId activityId;

	@Builder
	public GetSingleActivityQuery(
			@NonNull final OrgId orgId,
			@Nullable final String value,
			@Nullable final ActivityId activityId)
	{
		if (activityId == null && Check.isBlank(value))
		{
			throw new AdempiereException("Querying only by org is not allowed!");
		}

		this.orgId = orgId;
		this.value = value;
		this.activityId = activityId;
	}
}