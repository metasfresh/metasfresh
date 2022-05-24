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

package de.metas.audit.data;

import de.metas.audit.data.model.CreateDataExportAuditLogRequest;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.audit.data.model.DataExportAuditLog;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.process.PInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;

public class DataExportAuditLogRepositoryTest
{
	private DataExportAuditLogRepository dataExportAuditLogRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		dataExportAuditLogRepository = new DataExportAuditLogRepository();
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
		// given
		final CreateDataExportAuditLogRequest dataAuditLogRequest = CreateDataExportAuditLogRequest.builder()
				.dataExportAuditId(DataExportAuditId.ofRepoId(1))
				.externalSystemConfigId(ExternalSystemParentConfigId.ofRepoId(3))
				.action(Action.AssignedToParent)
				.adPInstanceId(PInstanceId.ofRepoId(4))
				.build();

		// when
		final DataExportAuditLog result = dataExportAuditLogRepository.createNew(dataAuditLogRequest);

		// then
		expect(result).toMatchSnapshot();
	}
}
