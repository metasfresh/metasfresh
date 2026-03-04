/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api.impl;

import de.metas.externalsystem.ExternalSystemErrorContext;
import de.metas.externalsystem.IExternalSystemInvocationErrorListener;
import de.metas.externalsystem.scriptedexportconversion.exportlog.ExternalSystemScriptedAdapterExportLogId;
import de.metas.externalsystem.scriptedexportconversion.exportlog.ExternalSystemScriptedAdapterExportLogRepository;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScriptedAdapterExportErrorListener implements IExternalSystemInvocationErrorListener
{
	@NonNull private static final Logger logger = LogManager.getLogger(ScriptedAdapterExportErrorListener.class);
	@NonNull private final ExternalSystemScriptedAdapterExportLogRepository exportLogRepository;

	@Override
	public boolean applies(@NonNull final ExternalSystemErrorContext errorContext)
	{
		return errorContext.isScriptedAdapterExport();
	}

	@Override
	public void onInvocationError(
			@NonNull final PInstanceId pInstanceId,
			@NonNull final ExternalSystemErrorContext errorContext,
			@NonNull final String errorMessage)
	{
		if (!applies(errorContext))
		{
			logger.debug("Error context '{}' does not match ScriptedAdapterExport, skipping", errorContext.getCode());
			return;
		}

		for (final ExternalSystemScriptedAdapterExportLogId logId : exportLogRepository.getByPInstanceId(pInstanceId))
		{
			logger.info("Marking export log {} as Error: {}", logId, errorMessage);
			exportLogRepository.markError(logId, errorMessage);
		}
	}
}
