/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.audit.config;

import de.metas.audit.HttpMethod;
import de.metas.organization.OrgId;
import de.metas.user.UserGroupId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
public class ApiAuditConfig
{
	@NonNull
	ApiAuditConfigId apiAuditConfigId;

	@NonNull
	OrgId orgId;

	int seqNo;

	boolean isInvokerWaitsForResponse;

	int keepRequestDays;
	int keepRequestBodyDays;
	int keepResponseDays;
	int keepResponseBodyDays;

	@Nullable
	HttpMethod method;

	@Nullable
	String pathPrefix;

	@Nullable
	NotificationTriggerType notifyUserInCharge;

	@Nullable
	UserGroupId userGroupInChargeId;

	public boolean matchesRequest(@NonNull final String requestPath, @NonNull final String httpMethod)
	{
		final boolean isPathMatching = this.pathPrefix == null || requestPath.contains(this.pathPrefix);
		final boolean isMethodMatching = this.method == null || httpMethod.equalsIgnoreCase(method.getCode());

		return isMethodMatching && isPathMatching;
	}

	@NonNull
	public Optional<UserGroupId> getUserGroupToNotify(final boolean isError)
	{
		if (this.userGroupInChargeId == null
				|| this.notifyUserInCharge == null
				|| this.notifyUserInCharge.equals(NotificationTriggerType.NEVER)
				|| (NotificationTriggerType.ONLY_ON_ERROR.equals(this.notifyUserInCharge)
				&& !isError))
		{

			return Optional.empty();
		}

		return Optional.of(userGroupInChargeId);
	}
}
