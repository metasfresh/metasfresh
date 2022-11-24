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

package de.metas.audit.apirequest.request;

import de.metas.audit.apirequest.HttpMethod;
import de.metas.audit.apirequest.config.ApiAuditConfigId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

public class ApiRequestAuditRepositoryTest
{
	private ApiRequestAuditRepository apiRequestAuditRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		apiRequestAuditRepository = new ApiRequestAuditRepository();
	}

	@BeforeAll
	static void initStatic()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	public void save()
	{
		//given
		final ApiRequestAudit apiRequestAudit = ApiRequestAudit.builder()
				.orgId(OrgId.ofRepoId(1))
				.roleId(RoleId.SYSTEM)
				.userId(UserId.METASFRESH)
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(3))
				.status(Status.RECEIVED)
				.isErrorAcknowledged(false)
				.body("body")
				.method(HttpMethod.POST)
				.path("path")
				.remoteAddress("remoteAddress")
				.remoteHost("remoteHost")
				.time(Instant.ofEpochMilli(0))
				.httpHeaders("httpHeaders")
				.requestURI("request/uri")
				.build();

		//when
		final ApiRequestAudit result = apiRequestAuditRepository.save(apiRequestAudit);

		//then
		expect(result).toMatchSnapshot();
	}
}
