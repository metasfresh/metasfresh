/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.process.PInstanceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

/**
 * Value object mapping one row of {@code ExternalSystem_ScriptedExportConversion_Status}.
 *
 * <p>Grain: one row per (ExternalSystem_Config_ScriptedExportConversion_ID, AD_Table_ID, Record_ID).
 *
 * <p>TODO(R2.2): rename class to {@code ScriptedExportConversionStatus} and promote
 * {@code httpResponseCode}/{@code adIssueId} to typed wrappers (HttpStatus / AdIssueId).
 */
@Value
@Builder
@With
public class ExternalSystemExportStatusLogEntry
{
	/**
	 * DB primary key of the Status row ({@code ExternalSystem_ScriptedExportConversion_Status_ID}).
	 * 0 when not yet persisted.
	 */
	int logId;

	@Nullable PInstanceId pInstanceId;
	@NonNull ExternalSystemScriptedExportConversionConfigId configId;
	@NonNull TableRecordReference sourceRecord;
	@NonNull ExternalSystemExportStatus status;
	int httpResponseCode;
	int adIssueId;
	@Nullable String statusMessage;
	boolean isResend;
}
