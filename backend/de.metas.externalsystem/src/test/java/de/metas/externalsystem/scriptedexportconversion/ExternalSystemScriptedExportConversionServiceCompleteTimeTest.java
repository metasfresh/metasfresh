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

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.util.Services;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
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
 * TDD tests for R2.3: at complete-time, ALL trigger-on-complete configs for the record's table
 * must have an eligibility status recorded — Pending for matching configs, DontSend for non-matching ones.
 *
 * <p>The DB-SQL matching ({@code isConfigMatchingRecord}) is sealed behind the inner method
 * {@link ExternalSystemScriptedExportConversionService#recordCompleteTimeEligibilityStatusesOnly}
 * which receives pre-partitioned lists and is unit-testable without a live DB.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ExternalSystemScriptedExportConversionServiceCompleteTimeTest
{
	private ExternalSystemExportStatusRepository repo;
	private ExternalSystemExportStatusService exportStatusService;
	private ExternalSystemScriptedExportConversionService service;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		repo = ExternalSystemExportStatusRepository.newInstanceForUnitTesting();
		exportStatusService = ExternalSystemExportStatusService.newInstanceForUnitTesting();

		// Service is constructed with the real exportStatusService; repo and endpoint dependencies
		// are mocked because recordCompleteTimeEligibilityStatusesOnly does not call them.
		service = new ExternalSystemScriptedExportConversionService(
				exportStatusService,
				Mockito.mock(ExternalSystemScriptedExportConversionRepository.class),
				Mockito.mock(de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository.class));
	}

	// -----------------------------------------------------------------------
	// Helper factories
	// -----------------------------------------------------------------------

	private int getM_InOutTableId()
	{
		return Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name);
	}

	private ExternalSystemScriptedExportConversionConfigId newConfigId()
	{
		final I_ExternalSystem_Config_ScriptedExportConversion cfg =
				InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_ScriptedExportConversion.class);
		cfg.setAD_Table_ID(getM_InOutTableId());
		cfg.setExternalSystemValue("test");
		cfg.setScriptIdentifier("test.js");
		cfg.setWhereClause("1=1");
		cfg.setIsTriggerOnComplete(true);
		cfg.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(cfg);
		return ExternalSystemScriptedExportConversionConfigId.ofRepoId(cfg.getExternalSystem_Config_ScriptedExportConversion_ID());
	}

	private ExternalSystemScriptedExportConversionConfig buildConfig(
			AdTableId tableId,
			ClientId clientId,
			ExternalSystemScriptedExportConversionConfigId configId)
	{
		return ExternalSystemScriptedExportConversionConfig.builder()
				.id(configId)
				.parentId(ExternalSystemParentConfigId.ofRepoId(1))
				.externalSystemEndpointId(ExternalSystemEndpointId.ofRepoId(1))
				.value("test")
				.scriptIdentifier("test.js")
				.tableAndClientId(AdTableAndClientId.of(tableId, clientId))
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
	// R2.3 core: matching → Pending; non-matching → DontSend
	// -----------------------------------------------------------------------

	/**
	 * Given one matching and one non-matching config,
	 * {@code recordCompleteTimeEligibilityStatusesOnly} must write Pending for the matching config
	 * and DontSend for the non-matching config.
	 */
	@Test
	void recordCompleteTimeEligibilityStatusesOnly_writesPendingForMatchAndDontSendForNonMatch()
	{
		// Arrange
		final int tableId = getM_InOutTableId();
		final ClientId clientId = ClientId.ofRepoId(1);
		final AdTableId adTableId = AdTableId.ofRepoId(tableId);

		final ExternalSystemScriptedExportConversionConfigId matchingConfigId = newConfigId();
		final ExternalSystemScriptedExportConversionConfigId nonMatchingConfigId = newConfigId();

		final ExternalSystemScriptedExportConversionConfig matchingConfig = buildConfig(adTableId, clientId, matchingConfigId);
		final ExternalSystemScriptedExportConversionConfig nonMatchingConfig = buildConfig(adTableId, clientId, nonMatchingConfigId);

		final I_M_InOut inout = newInOut();
		final int recordId = inout.getM_InOut_ID();

		// Act — call the pure inner method directly, bypassing DB matching
		service.recordCompleteTimeEligibilityStatusesOnly(
				ImmutableList.of(matchingConfig),
				ImmutableList.of(nonMatchingConfig),
				recordId);

		// Assert matching config → Pending
		final Optional<ScriptedExportConversionStatus> pendingEntry =
				repo.getLatestByConfigAndRecord(matchingConfigId, TableRecordReference.of(I_M_InOut.Table_Name, recordId));
		assertThat(pendingEntry).as("matching config should have a status row").isPresent();
		assertThat(pendingEntry.get().getStatus())
				.as("matching config WhereClause match → Pending")
				.isEqualTo(ExternalSystemExportStatus.Pending);

		// Assert non-matching config → DontSend
		final Optional<ScriptedExportConversionStatus> dontSendEntry =
				repo.getLatestByConfigAndRecord(nonMatchingConfigId, TableRecordReference.of(I_M_InOut.Table_Name, recordId));
		assertThat(dontSendEntry).as("non-matching config should have a status row").isPresent();
		assertThat(dontSendEntry.get().getStatus())
				.as("non-matching config WhereClause miss → DontSend")
				.isEqualTo(ExternalSystemExportStatus.DontSend);
	}

	/**
	 * When all configs match the record (no non-matching), only Pending rows must be written.
	 */
	@Test
	void recordCompleteTimeEligibilityStatusesOnly_onlyMatchingConfigs_allPending()
	{
		final int tableId = getM_InOutTableId();
		final ClientId clientId = ClientId.ofRepoId(1);
		final AdTableId adTableId = AdTableId.ofRepoId(tableId);

		final ExternalSystemScriptedExportConversionConfigId configId1 = newConfigId();
		final ExternalSystemScriptedExportConversionConfigId configId2 = newConfigId();

		final ExternalSystemScriptedExportConversionConfig config1 = buildConfig(adTableId, clientId, configId1);
		final ExternalSystemScriptedExportConversionConfig config2 = buildConfig(adTableId, clientId, configId2);

		final I_M_InOut inout = newInOut();
		final int recordId = inout.getM_InOut_ID();

		service.recordCompleteTimeEligibilityStatusesOnly(
				ImmutableList.of(config1, config2),
				ImmutableList.of(),
				recordId);

		final Optional<ScriptedExportConversionStatus> entry1 =
				repo.getLatestByConfigAndRecord(configId1, TableRecordReference.of(I_M_InOut.Table_Name, recordId));
		assertThat(entry1).isPresent();
		assertThat(entry1.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Pending);

		final Optional<ScriptedExportConversionStatus> entry2 =
				repo.getLatestByConfigAndRecord(configId2, TableRecordReference.of(I_M_InOut.Table_Name, recordId));
		assertThat(entry2).isPresent();
		assertThat(entry2.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Pending);
	}

	/**
	 * When no config matches the record, all configs receive DontSend.
	 */
	@Test
	void recordCompleteTimeEligibilityStatusesOnly_noMatchingConfigs_allDontSend()
	{
		final int tableId = getM_InOutTableId();
		final ClientId clientId = ClientId.ofRepoId(1);
		final AdTableId adTableId = AdTableId.ofRepoId(tableId);

		final ExternalSystemScriptedExportConversionConfigId configId1 = newConfigId();
		final ExternalSystemScriptedExportConversionConfigId configId2 = newConfigId();

		final ExternalSystemScriptedExportConversionConfig config1 = buildConfig(adTableId, clientId, configId1);
		final ExternalSystemScriptedExportConversionConfig config2 = buildConfig(adTableId, clientId, configId2);

		final I_M_InOut inout = newInOut();
		final int recordId = inout.getM_InOut_ID();

		service.recordCompleteTimeEligibilityStatusesOnly(
				ImmutableList.of(),
				ImmutableList.of(config1, config2),
				recordId);

		final Optional<ScriptedExportConversionStatus> entry1 =
				repo.getLatestByConfigAndRecord(configId1, TableRecordReference.of(I_M_InOut.Table_Name, recordId));
		assertThat(entry1).isPresent();
		assertThat(entry1.get().getStatus()).isEqualTo(ExternalSystemExportStatus.DontSend);

		final Optional<ScriptedExportConversionStatus> entry2 =
				repo.getLatestByConfigAndRecord(configId2, TableRecordReference.of(I_M_InOut.Table_Name, recordId));
		assertThat(entry2).isPresent();
		assertThat(entry2.get().getStatus()).isEqualTo(ExternalSystemExportStatus.DontSend);
	}

	/**
	 * Status-write failure for one config must NOT abort the whole loop — the remaining config
	 * must still receive its status row.
	 * (Mirrors the corpus-#6 pattern: status-write failure must never abort document completion.)
	 *
	 * <p>We simulate the failure via a Mockito spy on the exportStatusService: the spy throws on
	 * the first {@code recordDontSend} call (for the "bad" config) but delegates normally for the
	 * second call (the "good" config). The try/catch guard in the production code must swallow the
	 * first failure so the second call still runs.
	 */
	@Test
	void recordCompleteTimeEligibilityStatusesOnly_statusWriteFailure_doesNotAbortLoop()
	{
		final int tableId = getM_InOutTableId();
		final ClientId clientId = ClientId.ofRepoId(1);
		final AdTableId adTableId = AdTableId.ofRepoId(tableId);

		final ExternalSystemScriptedExportConversionConfigId badConfigId = newConfigId();
		final ExternalSystemScriptedExportConversionConfigId goodConfigId = newConfigId();

		final ExternalSystemScriptedExportConversionConfig badConfig = buildConfig(adTableId, clientId, badConfigId);
		final ExternalSystemScriptedExportConversionConfig goodConfig = buildConfig(adTableId, clientId, goodConfigId);

		final I_M_InOut inout = newInOut();
		final int recordId = inout.getM_InOut_ID();

		// Spy on the export-status service: throw on the first recordDontSend call, delegate on subsequent calls
		final ExternalSystemExportStatusService spyExportStatusService = Mockito.spy(exportStatusService);
		Mockito.doThrow(new RuntimeException("simulated DontSend write failure"))
				.doCallRealMethod()
				.when(spyExportStatusService)
				.recordDontSend(Mockito.any(), Mockito.any());

		// Build a new service instance wired with the spy
		final ExternalSystemScriptedExportConversionService serviceWithSpy = new ExternalSystemScriptedExportConversionService(
				spyExportStatusService,
				Mockito.mock(ExternalSystemScriptedExportConversionRepository.class),
				Mockito.mock(de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository.class));

		// Must not throw even though the first config causes a status-write failure
		serviceWithSpy.recordCompleteTimeEligibilityStatusesOnly(
				ImmutableList.of(),
				ImmutableList.of(badConfig, goodConfig),
				recordId);

		// goodConfig must still have its DontSend row (badConfig's row will be absent due to the thrown exception)
		final Optional<ScriptedExportConversionStatus> goodEntry =
				repo.getLatestByConfigAndRecord(goodConfigId, TableRecordReference.of(I_M_InOut.Table_Name, recordId));
		assertThat(goodEntry).as("good config must still be processed despite bad config failure").isPresent();
		assertThat(goodEntry.get().getStatus()).isEqualTo(ExternalSystemExportStatus.DontSend);
	}
}
