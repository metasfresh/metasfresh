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
import de.metas.externalsystem.ExternalSystemInvocationContext;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointId;
import de.metas.process.PInstanceId;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

/**
 * Pins the error-context the auto-trigger export path propagates.
 *
 * <p>{@link ExternalSystemScriptedExportConversionService#executeInvokeScriptedExportConversionAction}
 * is the path used by the AFTER_COMPLETE interceptor and by external archive-listener callers
 * (e.g. the DocuWare invoice export, me03 30104). It must delegate with a <b>non-null</b>
 * {@link ExternalSystemInvocationContext} so that a downstream failure reaches the error listener:
 * {@code ExternalSystemService.createIssue} only notifies the export-status error listener when the
 * error item carries a non-null {@code errorContext}, and only then does the status row flip to
 * {@code Error} + get its {@code AD_Issue} linked. Passing {@code null} silently disables error-status
 * tracking for every auto-trigger export. {@link ExternalSystemInvocationContext#UNKNOWN} is the neutral,
 * always-applicable context (the EDI and Re-send paths already pass {@code EDI} / {@code RESEND}).
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ExternalSystemScriptedExportConversionServiceErrorContextTest
{
	private ExternalSystemScriptedExportConversionService service;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		service = new ExternalSystemScriptedExportConversionService(
				ExternalSystemExportStatusService.newInstanceForUnitTesting(),
				Mockito.mock(ExternalSystemScriptedExportConversionRepository.class),
				Mockito.mock(de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository.class),
				Mockito.mock(de.metas.externalsystem.audit.ExternalSystemExportAuditRepo.class),
				Mockito.mock(ExternalSystemConfigRepository.class));
	}

	private ExternalSystemScriptedExportConversionConfig buildConfig()
	{
		return ExternalSystemScriptedExportConversionConfig.builder()
				.id(ExternalSystemScriptedExportConversionConfigId.ofRepoId(1))
				.parentId(ExternalSystemParentConfigId.ofRepoId(1))
				.externalSystemEndpointId(ExternalSystemEndpointId.ofRepoId(1))
				.value("test")
				.scriptIdentifier("test.js")
				.tableAndClientId(AdTableAndClientId.of(AdTableId.ofRepoId(540), ClientId.ofRepoId(1)))
				.whereClause("1=1")
				.active(true)
				.isTriggerOnComplete(true)
				.build();
	}

	@Test
	void executeInvokeScriptedExportConversionAction_propagatesUnknownErrorContext_notNull()
	{
		final ExternalSystemScriptedExportConversionService spy = Mockito.spy(service);
		final ExternalSystemScriptedExportConversionConfig config = buildConfig();
		final int recordId = 100;

		// Stub the heavy "execute the process" method so the test stays a pure unit test;
		// any() matches the null arg too, so the stub is hit in both the pre-fix and post-fix cases.
		Mockito.doReturn(ExternalSystemInvocationResult.success(PInstanceId.ofRepoId(1)))
				.when(spy)
				.executeInvokeScriptedExportConversionActionAndGetResult(
						Mockito.any(), Mockito.anyInt(), Mockito.any());

		spy.executeInvokeScriptedExportConversionAction(config, recordId);

		// The auto-trigger path must pass UNKNOWN, not null.
		Mockito.verify(spy).executeInvokeScriptedExportConversionActionAndGetResult(
				config, recordId, ExternalSystemInvocationContext.UNKNOWN);
	}
}
