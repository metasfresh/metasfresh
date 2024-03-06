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

import com.google.common.collect.ImmutableList;
import de.metas.audit.apirequest.HttpMethod;
import de.metas.organization.OrgId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_API_Audit_Config;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.assertj.core.api.Assertions.*;

public class ApiAuditConfigRepositoryTest
{
	private ApiAuditConfigRepository apiAuditConfigRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		apiAuditConfigRepository = new ApiAuditConfigRepository();
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
	public void getActiveConfigsByOrgId()
	{
		//given
		final OrgId targetOrgId = OrgId.ofRepoId(1);
		final OrgId otherOrgId = OrgId.ofRepoId(2);

		createMockAuditConfig(targetOrgId, true);
		createMockAuditConfig(targetOrgId, false);
		createMockAuditConfig(otherOrgId, true);

		//when
		final ImmutableList<ApiAuditConfig> configs = apiAuditConfigRepository.getActiveConfigsByOrgId(targetOrgId);

		//then
		assertThat(configs.size()).isEqualTo(1);
		expect(configs).toMatchSnapshot();
	}

	private void createMockAuditConfig(final OrgId orgId, final boolean isActive)
	{
		final I_API_Audit_Config config = InterfaceWrapperHelper.newInstance(I_API_Audit_Config.class);

		config.setAD_Org_ID(orgId.getRepoId());
		config.setAD_UserGroup_InCharge_ID(1);
		config.setIsActive(isActive);
		config.setIsForceProcessedAsync(true);
		config.setKeepRequestBodyDays(100);
		config.setKeepRequestDays(101);
		config.setKeepResponseBodyDays(102);
		config.setKeepResponseDays(103);
		config.setKeepErroredRequestDays(104);
		config.setMethod(HttpMethod.PUT.getCode());
		config.setNotifyUserInCharge(NotificationTriggerType.ALWAYS.getCode());
		config.setPathPrefix("pathPrefix");
		config.setSeqNo(10);
		config.setIsSynchronousAuditLoggingEnabled(true);
		config.setIsWrapApiResponse(true);

		InterfaceWrapperHelper.saveRecord(config);

	}
}
