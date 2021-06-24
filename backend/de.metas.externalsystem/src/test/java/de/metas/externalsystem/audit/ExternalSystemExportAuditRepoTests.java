/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.audit;

import de.metas.dlm.model.I_AD_Table;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.process.PInstanceId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

public class ExternalSystemExportAuditRepoTests
{
	private ExternalSystemExportAuditRepo externalSystemExportAuditRepo;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		externalSystemExportAuditRepo = new ExternalSystemExportAuditRepo();
	}

	@Test
	public void createESExportAuditTest()
	{
		//given
		final I_AD_Table testTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		testTable.setName("TestTable");
		testTable.setTableName("TestTable");
		InterfaceWrapperHelper.saveRecord(testTable);

		final CreateExportAuditRequest createExportAuditRequest = CreateExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(testTable.getAD_Table_ID(), 100))
				.pInstanceId(PInstanceId.ofRepoId(1))
				.exportTime(Instant.now())
				.exportUserId(UserId.ofRepoId(1))
				.exportRoleId(RoleId.ofRepoId(2))
				.exportParameters("ExportParameters")
				.externalSystemType(ExternalSystemType.Shopware6)
				.build();

		//when
		final ExternalSystemExportAudit audit = externalSystemExportAuditRepo.createESExportAudit(createExportAuditRequest);

		//then
		assertThat(audit).isNotNull();
		assertThat(audit.getExportParameters()).isEqualTo(createExportAuditRequest.getExportParameters());
		assertThat(audit.getExportTime()).isEqualTo(createExportAuditRequest.getExportTime());
		assertThat(audit.getExportRoleId()).isEqualTo(createExportAuditRequest.getExportRoleId());
		assertThat(audit.getExportUserId()).isEqualTo(createExportAuditRequest.getExportUserId());
		assertThat(audit.getExternalSystemType()).isEqualTo(createExportAuditRequest.getExternalSystemType());
		assertThat(audit.getPInstanceId()).isEqualTo(createExportAuditRequest.getPInstanceId());
		assertThat(audit.getTableRecordReference()).isEqualTo(createExportAuditRequest.getTableRecordReference());
	}
}