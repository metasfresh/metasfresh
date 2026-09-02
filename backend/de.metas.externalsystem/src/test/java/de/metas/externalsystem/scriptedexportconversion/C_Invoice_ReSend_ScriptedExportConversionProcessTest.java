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

import de.metas.externalsystem.scriptedexportconversion.process.C_Invoice_ReSend_ScriptedExportConversion;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfo;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_AD_Process;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Collections;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Process-level test for {@link C_Invoice_ReSend_ScriptedExportConversion}.
 *
 * <p>The process is thin glue: per selected C_Invoice it resolves the config IDs for the requested mode
 * ({@code IsOnlyNotSentSuccessfully=Y} → {@code getResendableConfigsBySourceRecord}; {@code =N} →
 * {@code getMatchingConfigIdsBySourceRecord}) and delegates the whole per-config re-send decision
 * (relevance gate → DontSend-or-Pending+invoke) to
 * {@link ExternalSystemScriptedExportConversionService#resendConfigIfRelevant}. So this test verifies
 * the mode selection + delegation + counting; the gate branch itself is unit-tested in
 * {@code ExternalSystemScriptedExportConversionServiceResendTest}.
 * <ul>
 *   <li>Each config is delegated once; the {@code @Processed@ #N} count reflects only the configs for
 *       which the service reported a send ({@code true}).</li>
 *   <li>A config the service suppressed ({@code false} — nothing left to export) is excluded from the count.</li>
 *   <li>No configs → {@code @Processed@ #0}, no delegation.</li>
 * </ul>
 */
@ExtendWith(AdempiereTestWatcher.class)
public class C_Invoice_ReSend_ScriptedExportConversionProcessTest
{
	private ExternalSystemScriptedExportConversionService scriptedExportServiceMock;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		// ProcessInfo builder requires a valid logged-in user ID in context
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(100));

		scriptedExportServiceMock = mock(ExternalSystemScriptedExportConversionService.class);

		// Register mock BEFORE instantiating the process (field is resolved at construction time)
		SpringContextHolder.registerJUnitBean(ExternalSystemScriptedExportConversionService.class, scriptedExportServiceMock);
	}

	// -----------------------------------------------------------------------
	// 1. Two matching configs, both sent → delegated once each, count = 2
	// -----------------------------------------------------------------------
	@Test
	void doIt_twoConfigs_bothSent_delegatedOncePerConfig()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		InterfaceWrapperHelper.saveRecord(invoice);
		final int invoiceId = invoice.getC_Invoice_ID();
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId);

		final ExternalSystemScriptedExportConversionConfigId configIdA = ExternalSystemScriptedExportConversionConfigId.ofRepoId(101);
		final ExternalSystemScriptedExportConversionConfigId configIdB = ExternalSystemScriptedExportConversionConfigId.ofRepoId(102);

		// isOnlyNotSentSuccessfully=false → all matching configs
		when(scriptedExportServiceMock.getMatchingConfigIdsBySourceRecord(sourceRecord))
				.thenReturn(Arrays.asList(configIdA, configIdB));

		// both still have something to export → service reports a send
		when(scriptedExportServiceMock.resendConfigIfRelevant(eq(configIdA), any(TableRecordReference.class), eq(invoiceId))).thenReturn(true);
		when(scriptedExportServiceMock.resendConfigIfRelevant(eq(configIdB), any(TableRecordReference.class), eq(invoiceId))).thenReturn(true);

		final String result = runProcess(invoiceId, tableId, false);

		verify(scriptedExportServiceMock, times(1)).resendConfigIfRelevant(eq(configIdA), any(TableRecordReference.class), eq(invoiceId));
		verify(scriptedExportServiceMock, times(1)).resendConfigIfRelevant(eq(configIdB), any(TableRecordReference.class), eq(invoiceId));
		assertThat(result).contains("2");
	}

	// -----------------------------------------------------------------------
	// 2. No configs → zero-count result, no delegation
	// -----------------------------------------------------------------------
	@Test
	void doIt_noConfigs_returnsZeroCount()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		InterfaceWrapperHelper.saveRecord(invoice);
		final int invoiceId = invoice.getC_Invoice_ID();
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name);

		when(scriptedExportServiceMock.getMatchingConfigIdsBySourceRecord(any()))
				.thenReturn(Collections.emptyList());

		final String result = runProcess(invoiceId, tableId, false);

		assertThat(result).contains("#0");
		verify(scriptedExportServiceMock, times(0)).resendConfigIfRelevant(any(), any(), anyInt());
	}

	// -----------------------------------------------------------------------
	// 3. Gate suppresses one config (service returns false) → excluded from the count
	// -----------------------------------------------------------------------
	@Test
	void doIt_serviceSuppressesOneConfig_countsOnlySent()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		InterfaceWrapperHelper.saveRecord(invoice);
		final int invoiceId = invoice.getC_Invoice_ID();
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId);

		final ExternalSystemScriptedExportConversionConfigId configIdSent = ExternalSystemScriptedExportConversionConfigId.ofRepoId(201);
		final ExternalSystemScriptedExportConversionConfigId configIdSuppressed = ExternalSystemScriptedExportConversionConfigId.ofRepoId(202);

		when(scriptedExportServiceMock.getMatchingConfigIdsBySourceRecord(sourceRecord))
				.thenReturn(Arrays.asList(configIdSent, configIdSuppressed));

		// one config sends, the other is suppressed (nothing left to export)
		when(scriptedExportServiceMock.resendConfigIfRelevant(eq(configIdSent), any(TableRecordReference.class), eq(invoiceId))).thenReturn(true);
		when(scriptedExportServiceMock.resendConfigIfRelevant(eq(configIdSuppressed), any(TableRecordReference.class), eq(invoiceId))).thenReturn(false);

		final String result = runProcess(invoiceId, tableId, false);

		// both delegated once, but only the sent one counts
		verify(scriptedExportServiceMock, times(1)).resendConfigIfRelevant(eq(configIdSent), any(TableRecordReference.class), eq(invoiceId));
		verify(scriptedExportServiceMock, times(1)).resendConfigIfRelevant(eq(configIdSuppressed), any(TableRecordReference.class), eq(invoiceId));
		assertThat(result).contains("1");
	}

	// -----------------------------------------------------------------------
	// 4. isOnlyNotSentSuccessfully=true → uses getResendableConfigsBySourceRecord (not the all-matching path)
	// -----------------------------------------------------------------------
	@Test
	void doIt_isOnlyNotSentSuccessfully_usesResendableConfigs()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		InterfaceWrapperHelper.saveRecord(invoice);
		final int invoiceId = invoice.getC_Invoice_ID();
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name);
		final TableRecordReference sourceRecord = TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId);

		final ExternalSystemScriptedExportConversionConfigId configIdA = ExternalSystemScriptedExportConversionConfigId.ofRepoId(201);

		when(scriptedExportServiceMock.getResendableConfigsBySourceRecord(sourceRecord))
				.thenReturn(Collections.singletonList(configIdA));
		when(scriptedExportServiceMock.resendConfigIfRelevant(eq(configIdA), any(TableRecordReference.class), eq(invoiceId))).thenReturn(true);

		final String result = runProcess(invoiceId, tableId, true /*isOnlyNotSentSuccessfully*/);

		// Verify the filtered path was used, NOT the all-matching path
		verify(scriptedExportServiceMock, times(1)).getResendableConfigsBySourceRecord(sourceRecord);
		verify(scriptedExportServiceMock, times(0)).getMatchingConfigIdsBySourceRecord(any());
		verify(scriptedExportServiceMock, times(1)).resendConfigIfRelevant(eq(configIdA), any(TableRecordReference.class), eq(invoiceId));
		assertThat(result).contains("#1");
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	/**
	 * Instantiates and drives the process for the given C_Invoice record.
	 *
	 * @param isOnlyNotSentSuccessfully value for the {@code IsOnlyNotSentSuccessfully} process parameter
	 * @return the process summary result string
	 */
	private String runProcess(final int invoiceId, final int tableId, final boolean isOnlyNotSentSuccessfully)
	{
		final I_AD_Process adProcess = newInstanceOutOfTrx(I_AD_Process.class);
		adProcess.setValue(C_Invoice_ReSend_ScriptedExportConversion.class.getSimpleName());
		adProcess.setName(C_Invoice_ReSend_ScriptedExportConversion.class.getSimpleName());
		adProcess.setType(X_AD_Process.TYPE_Java);
		adProcess.setClassname(C_Invoice_ReSend_ScriptedExportConversion.class.getName());
		saveRecord(adProcess);
		final AdProcessId adProcessId = AdProcessId.ofRepoId(adProcess.getAD_Process_ID());

		final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).createAD_PInstance(adProcessId);

		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_PInstance(pinstance)
				.setRecord(tableId, invoiceId)
				.setTitle("Test")
				.addParameter("IsOnlyNotSentSuccessfully", isOnlyNotSentSuccessfully)
				.build();

		// Instantiate AFTER mocks are registered (fields resolved at construction via SpringContextHolder)
		final C_Invoice_ReSend_ScriptedExportConversion process = new C_Invoice_ReSend_ScriptedExportConversion();

		try (final IAutoCloseable ignored = JavaProcess.temporaryChangeCurrentInstance(process))
		{
			process.startProcess(pi, ITrx.TRX_None);
		}

		return pi.getResult().getSummary();
	}
}
