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

import de.metas.externalsystem.ExternalSystemConfigRepository;
import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointId;
import de.metas.util.Services;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit test for the re-send relevance gate
 * ({@link ExternalSystemScriptedExportConversionService#resendConfigIfRelevant}).
 *
 * <p>{@code isConfigMatchingRecord} runs the config's WhereClause as SQL (needs a live DB), so it is
 * stubbed on a Mockito spy here; the branch behaviour (DontSend-and-skip vs Pending+invoke) is the unit
 * under test. The end-to-end "epcis_has_events flips false → no send" path is covered by cucumber
 * {@code S30916_150}. The status writes go through a REAL in-memory {@link ExternalSystemExportStatusService}
 * so the produced status rows can be asserted.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ExternalSystemScriptedExportConversionServiceResendTest
{
	private ExternalSystemExportStatusRepository repo;
	private ExternalSystemScriptedExportConversionService service;

	private ExternalSystemScriptedExportConversionConfigId configId;
	private TableRecordReference sourceRecord;
	private int inoutId;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// repo and the repo inside exportStatusService share the same in-memory store
		repo = ExternalSystemExportStatusRepository.newInstanceForUnitTesting();
		final ExternalSystemExportStatusService exportStatusService = ExternalSystemExportStatusService.newInstanceForUnitTesting();

		final ExternalSystemScriptedExportConversionRepository scriptedRepo =
				mock(ExternalSystemScriptedExportConversionRepository.class);

		service = spy(new ExternalSystemScriptedExportConversionService(
				exportStatusService,
				scriptedRepo,
				mock(de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository.class),
				mock(de.metas.externalsystem.audit.ExternalSystemExportAuditRepo.class),
				mock(ExternalSystemConfigRepository.class)));

		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name);
		configId = ExternalSystemScriptedExportConversionConfigId.ofRepoId(101);
		inoutId = 5001;
		sourceRecord = TableRecordReference.of(tableId, inoutId);

		final ExternalSystemScriptedExportConversionConfig config = ExternalSystemScriptedExportConversionConfig.builder()
				.id(configId)
				.parentId(ExternalSystemParentConfigId.ofRepoId(1))
				.externalSystemEndpointId(ExternalSystemEndpointId.ofRepoId(1))
				.value("test")
				.scriptIdentifier("test.js")
				.tableAndClientId(AdTableAndClientId.of(AdTableId.ofRepoId(tableId), ClientId.ofRepoId(0)))
				.whereClause("1=1")
				.active(true)
				.isTriggerOnComplete(true)
				.build();
		doReturn(config).when(scriptedRepo).getById(configId);

		// invoke is heavy (ProcessInfo/adapter) — stub it so the branch decision is what's tested
		// (the return value is not asserted; only whether invoke was called)
		doReturn(ExternalSystemInvocationResult.error(new RuntimeException("stubbed invoke")))
				.when(service).executeInvokeScriptedExportConversionActionAndGetResult(any(), anyInt(), any());
	}

	@Test
	void resendConfigIfRelevant_nothingLeftToExport_recordsDontSend_andDoesNotInvoke()
	{
		// config's WhereClause no longer matches (e.g. every SSCC already in the EPCIS ledger)
		doReturn(false).when(service).isConfigMatchingRecord(any(), anyInt());

		final boolean sent = service.resendConfigIfRelevant(configId, sourceRecord, inoutId);

		assertThat(sent).isFalse();
		// adapter NOT invoked → no empty event sent
		verify(service, times(0)).executeInvokeScriptedExportConversionActionAndGetResult(any(), anyInt(), any());
		// the resend is recorded as terminal DontSend, not a Pending re-send attempt
		final Optional<ScriptedExportConversionStatus> latest = repo.getLatestByConfigAndRecord(configId, sourceRecord);
		assertThat(latest).isPresent();
		assertThat(latest.get().getStatus()).isEqualTo(ExternalSystemExportStatus.DontSend);
	}

	@Test
	void resendConfigIfRelevant_stillHasSomethingToExport_recordsPendingResend_andInvokes()
	{
		doReturn(true).when(service).isConfigMatchingRecord(any(), anyInt());

		final boolean sent = service.resendConfigIfRelevant(configId, sourceRecord, inoutId);

		assertThat(sent).isTrue();
		verify(service, times(1))
				.executeInvokeScriptedExportConversionActionAndGetResult(any(), anyInt(), any());
		final Optional<ScriptedExportConversionStatus> latest = repo.getLatestByConfigAndRecord(configId, sourceRecord);
		assertThat(latest).isPresent();
		assertThat(latest.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Pending);
		assertThat(latest.get().isResend()).isTrue();
	}
}
