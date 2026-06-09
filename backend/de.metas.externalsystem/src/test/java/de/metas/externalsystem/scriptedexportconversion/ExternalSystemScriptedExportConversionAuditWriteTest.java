/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.externalsystem.scriptedexportconversion;

import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemTestHelper;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.audit.ExternalSystemExportAudit;
import de.metas.externalsystem.audit.ExternalSystemExportAuditRepo;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointId;
import de.metas.process.PInstanceId;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that {@link ExternalSystemScriptedExportConversionService#writeExportAudit}
 * writes exactly one {@code ExternalSystem_ExportAudit} row carrying the expected
 * pinstance, record reference, and external-system type.
 *
 * <p>The full DB-driven send→audit integration is covered by the cucumber suite;
 * this test exercises only the extracted seam method.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ExternalSystemScriptedExportConversionAuditWriteTest
{
	private ExternalSystemExportAuditRepo auditRepo;
	private ExternalSystemScriptedExportConversionService service;

	private static final ExternalSystemType SCRIPTED_TYPE = ExternalSystemType.ScriptedExportConversion;
	private static final ExternalSystemParentConfigId PARENT_ID = ExternalSystemParentConfigId.ofRepoId(1);

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		ExternalSystemTestHelper.createExternalSystemIfNotExists(SCRIPTED_TYPE);

		auditRepo = ExternalSystemExportAuditRepo.newInstanceForUnitTesting();

		// Mock ExternalSystemConfigRepo so that getParentTypeById(PARENT_ID) returns SCRIPTED_TYPE
		final ExternalSystemConfigRepo configRepoMock = Mockito.mock(ExternalSystemConfigRepo.class);
		Mockito.when(configRepoMock.getParentTypeById(PARENT_ID))
				.thenReturn(SCRIPTED_TYPE.getValue());

		service = new ExternalSystemScriptedExportConversionService(
				ExternalSystemExportStatusService.newInstanceForUnitTesting(),
				Mockito.mock(ExternalSystemScriptedExportConversionRepository.class),
				Mockito.mock(de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository.class),
				auditRepo,
				configRepoMock);
	}

	// -----------------------------------------------------------------------
	// Helper factories
	// -----------------------------------------------------------------------

	private ExternalSystemScriptedExportConversionConfig buildConfig(
			final ExternalSystemParentConfigId parentId,
			final AdTableId tableId)
	{
		return ExternalSystemScriptedExportConversionConfig.builder()
				.id(ExternalSystemScriptedExportConversionConfigId.ofRepoId(1))
				.parentId(parentId)
				.externalSystemEndpointId(ExternalSystemEndpointId.ofRepoId(1))
				.value("test")
				.scriptIdentifier("test.js")
				.tableAndClientId(AdTableAndClientId.of(tableId, ClientId.ofRepoId(1)))
				.whereClause("1=1")
				.active(true)
				.isTriggerOnComplete(true)
				.build();
	}

	private I_M_InOut newInOut()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		return inout;
	}

	// -----------------------------------------------------------------------
	// Core assertion: writeExportAudit persists a row with the right pinstance
	// -----------------------------------------------------------------------

	/**
	 * Given a source record and a pInstanceId,
	 * {@code writeExportAudit} must persist exactly one audit row
	 * that carries the given pinstance and whose table-record reference
	 * matches the source record.
	 *
	 * <p>This is the {@code _Status.AD_PInstance_ID → ExportAudit} correlation assertion:
	 * both the status row and the audit row share the same pInstanceId.
	 */
	@Test
	void writeExportAudit_persistsRowWithExpectedPinstanceAndRecord()
	{
		// Arrange
		final I_M_InOut inout = newInOut();
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, inout.getM_InOut_ID());
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(42);

		final AdTableId tableId = AdTableId.ofRepoId(sourceRecord.getAD_Table_ID());
		final ExternalSystemScriptedExportConversionConfig config = buildConfig(PARENT_ID, tableId);

		// Act
		service.writeExportAudit(config, sourceRecord, pInstanceId);

		// Assert — the row is present and carries the expected pinstance
		final Optional<ExternalSystemExportAudit> auditOpt =
				auditRepo.getMostRecentByTableReferenceAndSystem(sourceRecord, SCRIPTED_TYPE);

		assertThat(auditOpt)
				.as("an audit row must have been written for the source record")
				.isPresent();

		final ExternalSystemExportAudit audit = auditOpt.get();
		assertThat(audit.getPInstanceId())
				.as("audit row must carry the pinstance passed at enqueue — _Status and ExportAudit both share this id")
				.isEqualTo(pInstanceId);

		assertThat(audit.getTableRecordReference())
				.as("audit row must reference the source record")
				.isEqualTo(sourceRecord);

		assertThat(audit.getExternalSystemType())
				.as("audit row must carry the config's external-system type")
				.isEqualTo(SCRIPTED_TYPE);
	}
}
