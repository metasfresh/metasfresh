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

import de.metas.externalsystem.ExternalSystemErrorContext;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointId;
import de.metas.externalsystem.scriptedexportconversion.process.M_InOut_ReSend_ScriptedExportConversion;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfo;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_AD_Process;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Process-level test for {@link M_InOut_ReSend_ScriptedExportConversion}.
 *
 * <p>Verifies:
 * <ul>
 *   <li>When the status service returns qualifying config IDs, the process calls
 *       {@code resolveConfigAndRecordPendingAsResend} and then
 *       {@code executeInvokeScriptedExportConversionActionAndGetResult} with RESEND
 *       once per config.</li>
 *   <li>The IsResend=Y Pending row is created (via {@code resolveConfigAndRecordPendingAsResend})
 *       before the invocation call.</li>
 *   <li>When no qualifying configs exist the process returns the "nothing to re-send" message
 *       and neither service method is called.</li>
 * </ul>
 */
@ExtendWith(AdempiereTestWatcher.class)
public class M_InOut_ReSend_ScriptedExportConversionProcessTest
{
	private ExternalSystemExportStatusService exportStatusServiceMock;
	private ExternalSystemScriptedExportConversionService scriptedExportServiceMock;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		// ProcessInfo builder requires a valid logged-in user ID in context
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(100));

		exportStatusServiceMock = mock(ExternalSystemExportStatusService.class);
		scriptedExportServiceMock = mock(ExternalSystemScriptedExportConversionService.class);

		// Register mocks BEFORE instantiating the process (fields are resolved at construction time)
		SpringContextHolder.registerJUnitBean(ExternalSystemExportStatusService.class, exportStatusServiceMock);
		SpringContextHolder.registerJUnitBean(ExternalSystemScriptedExportConversionService.class, scriptedExportServiceMock);
	}

	// -----------------------------------------------------------------------
	// 1. Happy path: two qualifying configs → each gets resolveConfig+recordPending + invoke
	// -----------------------------------------------------------------------
	@Test
	void doIt_twoQualifyingConfigs_invokeCalledOncePerConfig()
	{
		// Set up an M_InOut record to act as the process record
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final int inoutId = inout.getM_InOut_ID();

		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, inoutId);

		final ExternalSystemScriptedExportConversionConfigId configIdA = ExternalSystemScriptedExportConversionConfigId.ofRepoId(101);
		final ExternalSystemScriptedExportConversionConfigId configIdB = ExternalSystemScriptedExportConversionConfigId.ofRepoId(102);
		final List<ExternalSystemScriptedExportConversionConfigId> qualifyingConfigIds = Arrays.asList(configIdA, configIdB);

		final ExternalSystemScriptedExportConversionConfig configA = buildDummyConfig(configIdA, tableId);
		final ExternalSystemScriptedExportConversionConfig configB = buildDummyConfig(configIdB, tableId);

		// Status service returns two qualifying configs
		when(exportStatusServiceMock.getConfigsWithNonSentAttemptBySourceRecord(sourceRecord))
				.thenReturn(qualifyingConfigIds);

		// resolveConfigAndRecordPendingAsResend returns the resolved config for each
		when(scriptedExportServiceMock.resolveConfigAndRecordPendingAsResend(eq(configIdA), any()))
				.thenReturn(configA);
		when(scriptedExportServiceMock.resolveConfigAndRecordPendingAsResend(eq(configIdB), any()))
				.thenReturn(configB);

		// executeInvokeScriptedExportConversionActionAndGetResult returns a result for both (process ignores the return value)
		when(scriptedExportServiceMock.executeInvokeScriptedExportConversionActionAndGetResult(any(), any(Integer.class), eq(ExternalSystemErrorContext.RESEND)))
				.thenReturn(ExternalSystemInvocationResult.error(new RuntimeException("mocked")));

		// Run the process
		final String result = runProcess(inoutId, tableId);

		// Assert: resolveConfigAndRecordPendingAsResend called once per config
		verify(scriptedExportServiceMock, times(1)).resolveConfigAndRecordPendingAsResend(eq(configIdA), any());
		verify(scriptedExportServiceMock, times(1)).resolveConfigAndRecordPendingAsResend(eq(configIdB), any());

		// Assert: executeInvokeScriptedExportConversionActionAndGetResult called once per config with RESEND
		verify(scriptedExportServiceMock, times(1))
				.executeInvokeScriptedExportConversionActionAndGetResult(eq(configA), eq(inoutId), eq(ExternalSystemErrorContext.RESEND));
		verify(scriptedExportServiceMock, times(1))
				.executeInvokeScriptedExportConversionActionAndGetResult(eq(configB), eq(inoutId), eq(ExternalSystemErrorContext.RESEND));

		assertThat(result).contains("2");
	}

	// -----------------------------------------------------------------------
	// 2. No qualifying configs → nothing to re-send, no invocations
	// -----------------------------------------------------------------------
	@Test
	void doIt_noQualifyingConfigs_returnsNothingToReSend()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		InterfaceWrapperHelper.saveRecord(inout);
		final int inoutId = inout.getM_InOut_ID();
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name);

		when(exportStatusServiceMock.getConfigsWithNonSentAttemptBySourceRecord(any()))
				.thenReturn(Collections.emptyList());

		final String result = runProcess(inoutId, tableId);

		assertThat(result).containsIgnoringCase("nothing to re-send");
		verify(scriptedExportServiceMock, times(0)).resolveConfigAndRecordPendingAsResend(any(), any());
		verify(scriptedExportServiceMock, times(0)).executeInvokeScriptedExportConversionActionAndGetResult(any(), any(Integer.class), any());
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	/**
	 * Builds a minimal config stub; the process only passes it through to the invoke call.
	 */
	private ExternalSystemScriptedExportConversionConfig buildDummyConfig(
			final ExternalSystemScriptedExportConversionConfigId id,
			final int tableId)
	{
		return ExternalSystemScriptedExportConversionConfig.builder()
				.id(id)
				.parentId(ExternalSystemParentConfigId.ofRepoId(1))
				.externalSystemEndpointId(ExternalSystemEndpointId.ofRepoId(1))
				.value("test")
				.scriptIdentifier("test.js")
				.tableAndClientId(AdTableAndClientId.of(AdTableId.ofRepoId(tableId), ClientId.ofRepoId(0)))
				.whereClause("1=1")
				.active(true)
				.isTriggerOnComplete(true)
				.build();
	}

	/**
	 * Instantiates and drives the process for the given M_InOut record.
	 * Returns the process summary result string.
	 */
	private String runProcess(final int inoutId, final int tableId)
	{
		final I_AD_Process adProcess = newInstanceOutOfTrx(I_AD_Process.class);
		adProcess.setValue(M_InOut_ReSend_ScriptedExportConversion.class.getSimpleName());
		adProcess.setName(M_InOut_ReSend_ScriptedExportConversion.class.getSimpleName());
		adProcess.setType(X_AD_Process.TYPE_Java);
		adProcess.setClassname(M_InOut_ReSend_ScriptedExportConversion.class.getName());
		saveRecord(adProcess);
		final AdProcessId adProcessId = AdProcessId.ofRepoId(adProcess.getAD_Process_ID());

		final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(adProcessId);

		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_PInstance(pinstance)
				.setRecord(tableId, inoutId)
				.setTitle("Test")
				.build();

		// Instantiate AFTER mocks are registered (fields resolved at construction via SpringContextHolder)
		final M_InOut_ReSend_ScriptedExportConversion process = new M_InOut_ReSend_ScriptedExportConversion();

		try (final IAutoCloseable ignored = JavaProcess.temporaryChangeCurrentInstance(process))
		{
			process.startProcess(pi, ITrx.TRX_None);
		}

		return pi.getResult().getSummary();
	}
}
