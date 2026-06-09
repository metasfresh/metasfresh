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
import de.metas.externalsystem.IExternalSystemInvocationSuccessListener;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Success listener for Scripted Export Conversion.
 * Updates the {@code ExternalSystem_ScriptedExportConversion_Status} row to {@code Sent}
 * when an external system invocation succeeds, and stores the HTTP response code.
 *
 * <p>This listener applies to <em>all</em> success contexts — it uses the {@code pInstanceId}
 * to look up the matching log row and is a no-op (no throw) when no matching row exists
 * (AC-10 safety: the pInstanceId may belong to a different external system invocation).
 */
@Component
@RequiredArgsConstructor
public class ScriptedExportStatusSuccessListener implements IExternalSystemInvocationSuccessListener
{
	private static final Logger logger = LogManager.getLogger(ScriptedExportStatusSuccessListener.class);

	@NonNull private final ExternalSystemExportStatusService exportStatusService;

	/**
	 * Always returns {@code true} — this listener looks up by {@code pInstanceId} and is a no-op
	 * when no matching log row exists, so it is safe to be consulted for every success context.
	 */
	@Override
	public boolean applies(@NonNull final ExternalSystemErrorContext context)
	{
		return true;
	}

	@Override
	public void onInvocationSuccess(
			@NonNull final PInstanceId pInstanceId,
			@NonNull final ExternalSystemErrorContext context,
			final int httpResponseCode)
	{
		logger.debug("Handling invocation success for pInstanceId={}, context={}, httpResponseCode={}", pInstanceId, context, httpResponseCode);
		exportStatusService.markSent(pInstanceId, httpResponseCode);
	}
}
