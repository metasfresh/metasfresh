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

import de.metas.organization.OrgId;
import de.metas.user.UserGroupId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ApiAuditConfigTest
{

	@Test
	void matchesRequest_null_path_null_method()
	{
		final ApiAuditConfig apiAuditConfig = ApiAuditConfig.builder()
				.orgId(OrgId.ofRepoId(10))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(10))
				.build();
		assertThat(apiAuditConfig.matchesRequest("http://app:8282/api/v2/manufacturing/orders/report", "POST")).isTrue();
	}

	@Test
	void matchesRequest_manufacturing_path_null_method()
	{
		final ApiAuditConfig apiAuditConfig = ApiAuditConfig.builder()
				.orgId(OrgId.ofRepoId(10))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(10))
				.pathPrefix("orders")
				.build();
		assertThat(apiAuditConfig.matchesRequest("http://app:8282/api/v2/manufacturing/orders/report", "POST")).isTrue();
	}

	@Test
	void matchesRequest_manufacturing2_path_null_method()
	{
		final ApiAuditConfig apiAuditConfig = ApiAuditConfig.builder()
				.orgId(OrgId.ofRepoId(10))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(10))
				.pathPrefix("**/manufacturing/**/report/**")
				.build();
		assertThat(apiAuditConfig.matchesRequest("http://app:8282/api/v2/manufacturing/orders/report", "POST")).isTrue();
	}

	@Test
	void matchesRequest_manufacturing3_path_null_method()
	{
		final ApiAuditConfig apiAuditConfig = ApiAuditConfig.builder()
				.orgId(OrgId.ofRepoId(10))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(10))
				.pathPrefix("/manufacturing/orders/report")
				.build();
		assertThat(apiAuditConfig.matchesRequest("http://app:8282/api/v2/manufacturing/orders/report", "POST")).isTrue();
	}

	@Test
	void matchesRequest_error_path_null_method()
	{
		final ApiAuditConfig apiAuditConfig = ApiAuditConfig.builder()
				.orgId(OrgId.ofRepoId(10))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(10))
				.pathPrefix("**/externalsystem/**/externalstatus/**/error")
				.build();
		assertThat(apiAuditConfig.matchesRequest("http://app:8282/api/v2/externalsystem/externalstatus/{AD_PInstance_ID}/error", "POST")).isTrue();
		assertThat(apiAuditConfig.matchesRequest("http://app:8282/api/v2/externalsystem/{AD_PInstance_ID}/externalstatus/error", "POST")).isTrue();
	}

	private static ApiAuditConfig configWith(final NotificationTriggerType trigger)
	{
		return ApiAuditConfig.builder()
				.orgId(OrgId.ofRepoId(10))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(10))
				.notifyUserInCharge(trigger)
				.userGroupInChargeId(UserGroupId.ofRepoId(20))
				.build();
	}

	@Test
	void ofHttpStatus()
	{
		assertThat(ApiCallOutcome.ofHttpStatus(200)).isEqualTo(ApiCallOutcome.SUCCESS);
		assertThat(ApiCallOutcome.ofHttpStatus(201)).isEqualTo(ApiCallOutcome.SUCCESS);
		assertThat(ApiCallOutcome.ofHttpStatus(207)).isEqualTo(ApiCallOutcome.PARTIAL_ERROR);
		assertThat(ApiCallOutcome.ofHttpStatus(400)).isEqualTo(ApiCallOutcome.ERROR);
		assertThat(ApiCallOutcome.ofHttpStatus(500)).isEqualTo(ApiCallOutcome.ERROR);
	}

	@Test
	void notificationTriggerType_errorOr207_roundTrip()
	{
		assertThat(NotificationTriggerType.ofNullableCode("ERROR_OR_207")).isEqualTo(NotificationTriggerType.ERROR_OR_PARTIAL_ERROR);
	}

	@Test
	void getUserGroupToNotify_errorOrPartialError()
	{
		final ApiAuditConfig config = configWith(NotificationTriggerType.ERROR_OR_PARTIAL_ERROR);
		assertThat(config.getUserGroupToNotify(ApiCallOutcome.ERROR)).isPresent();
		assertThat(config.getUserGroupToNotify(ApiCallOutcome.PARTIAL_ERROR)).isPresent();
		assertThat(config.getUserGroupToNotify(ApiCallOutcome.SUCCESS)).isEmpty();
	}

	@Test
	void getUserGroupToNotify_onlyOnError_doesNotNotifyOnPartialError()
	{
		final ApiAuditConfig config = configWith(NotificationTriggerType.ONLY_ON_ERROR);
		assertThat(config.getUserGroupToNotify(ApiCallOutcome.ERROR)).isPresent();
		assertThat(config.getUserGroupToNotify(ApiCallOutcome.PARTIAL_ERROR)).isEmpty();
		assertThat(config.getUserGroupToNotify(ApiCallOutcome.SUCCESS)).isEmpty();
	}

	@Test
	void getUserGroupToNotify_always_and_never()
	{
		final ApiAuditConfig always = configWith(NotificationTriggerType.ALWAYS);
		for (final ApiCallOutcome outcome : ApiCallOutcome.values())
		{
			assertThat(always.getUserGroupToNotify(outcome)).isPresent();
			assertThat(configWith(NotificationTriggerType.NEVER).getUserGroupToNotify(outcome)).isEmpty();
		}
	}
}
