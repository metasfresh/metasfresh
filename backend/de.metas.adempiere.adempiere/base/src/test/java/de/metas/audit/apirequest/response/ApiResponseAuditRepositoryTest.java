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

package de.metas.audit.apirequest.response;

import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.organization.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

public class ApiResponseAuditRepositoryTest
{
	private ApiResponseAuditRepository apiResponseAuditRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		apiResponseAuditRepository = new ApiResponseAuditRepository();
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
		final ApiResponseAudit apiResponseAudit = ApiResponseAudit.builder()
				.orgId(OrgId.ofRepoId(1))
				.apiRequestAuditId(ApiRequestAuditId.ofRepoId(2))
				.httpCode("200")
				.body("body")
				.time(Instant.ofEpochMilli(0))
				.build();

		//when
		final ApiResponseAudit responseAudit = apiResponseAuditRepository.save(apiResponseAudit);

		//then
		expect(responseAudit).toMatchSnapshot();
	}
}
