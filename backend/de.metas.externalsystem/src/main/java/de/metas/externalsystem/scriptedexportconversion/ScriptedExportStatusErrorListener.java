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
import de.metas.externalsystem.IExternalSystemInvocationErrorListener;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Error listener for Scripted Export Conversion.
 * Updates the {@code ExternalSystem_ScriptedExportConversion_Log} row to {@code Error}
 * when an external system invocation fails, and links the {@code AD_Issue} created for
 * this invocation.
 *
 * <p>This listener applies to <em>all</em> error contexts — it uses the {@code pInstanceId}
 * to look up the matching log row and is a no-op (no throw) when no matching row exists
 * (AC-10 safety: the pInstanceId may belong to a different external system invocation).
 *
 * <p>The {@link IExternalSystemInvocationErrorListener} SPI does not provide the
 * {@code AD_Issue_ID} directly. Instead, {@link ExternalSystemExportStatusService#markError}
 * resolves the most-recent {@code AD_Issue} stamped with the given {@code AD_PInstance_ID}
 * — created by {@code ExternalSystemService.createIssue()} before listeners are notified —
 * and links it to the log row automatically.
 */
@Component
@RequiredArgsConstructor
public class ScriptedExportStatusErrorListener implements IExternalSystemInvocationErrorListener
{
	private static final Logger logger = LogManager.getLogger(ScriptedExportStatusErrorListener.class);

	@NonNull private final ExternalSystemExportStatusService exportStatusService;

	/**
	 * Always returns {@code true} — this listener looks up by {@code pInstanceId} and is a no-op
	 * when no matching log row exists, so it is safe to be consulted for every error context.
	 */
	@Override
	public boolean applies(@NonNull final ExternalSystemErrorContext errorContext)
	{
		return true;
	}

	@Override
	public void onInvocationError(
			@NonNull final PInstanceId pInstanceId,
			@NonNull final ExternalSystemErrorContext errorContext,
			@NonNull final String errorMessage)
	{
		logger.debug("Handling invocation error for pInstanceId={}, errorContext={}", pInstanceId, errorContext);
		// adIssueId=0: markError will resolve the AD_Issue_ID by querying AD_Issue.AD_PInstance_ID
		exportStatusService.markError(pInstanceId, 0, errorMessage);
	}
}
