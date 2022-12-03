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
}