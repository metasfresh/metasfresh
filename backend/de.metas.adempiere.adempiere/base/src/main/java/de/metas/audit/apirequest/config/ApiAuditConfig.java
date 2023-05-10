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

package de.metas.audit.apirequest.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.audit.apirequest.HttpMethod;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.organization.OrgId;
import de.metas.user.UserGroupId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE) // for snapshot testing
public class ApiAuditConfig
{
	@NonNull
	ApiAuditConfigId apiAuditConfigId;
	
	boolean active;

	@NonNull
	OrgId orgId;

	int seqNo;

	boolean isBypassAudit;

	boolean forceProcessedAsync;

	int keepRequestDays;
	int keepRequestBodyDays;
	int keepResponseDays;
	int keepResponseBodyDays;
	int keepErroredRequestDays;

	@Nullable
	HttpMethod method;

	@Nullable
	String pathPrefix;

	@Nullable
	NotificationTriggerType notifyUserInCharge;

	@Nullable
	UserGroupId userGroupInChargeId;

	/**
	 * If true, then still both {@link org.compiere.model.I_API_Request_Audit} and {@link org.compiere.model.I_API_Response_Audit} records are written for the request, but in an asynchrounous way, while the actual requests might already have been performed.
	 * This implies better performance for the caller, but:
	 * <li>no {@link org.compiere.model.I_API_Request_Audit_Log} records will be created</li>
	 * <li>Creating those audit reocrds might fail without the API caller noticing it</li>
	 */
	boolean performAuditAsync;

	/**
	 * If true, the API response shall be wrapped into a {@link JsonApiResponse}
	 * If false, it shall be communicated via a http response header.
	 * Note that if {@link #performAuditAsync} is {@code true}, then the API response is never wrapped.
	 */
	boolean wrapApiResponse;

	@NonNull
	@Builder.Default
	@JsonIgnore // not needed in snapshot-testing
	AntPathMatcher antPathMatcher = new AntPathMatcher();

	public boolean matchesRequest(@NonNull final String requestPath, @NonNull final String httpMethod)
	{
		final boolean isPathMatching = this.pathPrefix == null
				|| requestPath.contains(this.pathPrefix)
				|| antPathMatcher.match(pathPrefix, requestPath);

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
