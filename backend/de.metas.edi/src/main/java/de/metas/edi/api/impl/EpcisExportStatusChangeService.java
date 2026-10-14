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

import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemExportStatusService;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionRepository;
import de.metas.externalsystem.scriptedexportconversion.ScriptedExportConversionStatus;
import de.metas.inout.InOutId;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Backs the "Change EPCIS Export Status" WebUI process. Resolves a shipment's EPCIS scripted-export
 * config(s) and changes their export status by recording a new, process-instance-stamped attempt row
 * (who/when audit) — mirroring the {@code ChangeEDI_ExportStatus_*} pattern, adapted to the
 * per-attempt-row EPCIS status model. Sits next to {@link EpcisReverseGuardService} and reuses its exact
 * EPCIS-config resolution (latest-status rows filtered by {@link EpcisExportConfigMatcher}).
 */
@Service
@RequiredArgsConstructor
public class EpcisExportStatusChangeService
{
	@NonNull private final ExternalSystemExportStatusService exportStatusService;
	@NonNull private final ExternalSystemScriptedExportConversionRepository scriptedExportConversionRepository;
	@NonNull private final EpcisExportConfigMatcher epcisExportConfigMatcher;

	private static TableRecordReference toSourceRecord(@NonNull final InOutId inOutId)
	{
		return TableRecordReference.of(I_M_InOut.Table_Name, inOutId.getRepoId());
	}

	/** Latest EPCIS status rows (one per EPCIS config) for the shipment. Empty if the shipment has no EPCIS export. */
	@NonNull
	public List<ScriptedExportConversionStatus> getEpcisStatuses(@NonNull final InOutId inOutId)
	{
		return exportStatusService.getLatestStatusesBySourceRecord(toSourceRecord(inOutId)).stream()
				.filter(st -> epcisExportConfigMatcher.isEpcisExportConfig(scriptedExportConversionRepository.getById(st.getConfigId())))
				.collect(Collectors.toList());
	}

	/**
	 * The shipment's single current EPCIS export status (for the transition matrix / parameter lookup), or
	 * {@code null} if it has no EPCIS status row or its EPCIS configs are in mixed states.
	 */
	@Nullable
	public ExternalSystemExportStatus getFromStatus(@NonNull final InOutId inOutId)
	{
		final List<ExternalSystemExportStatus> distinct = getEpcisStatuses(inOutId).stream()
				.map(ScriptedExportConversionStatus::getStatus)
				.distinct()
				.collect(Collectors.toList());
		return distinct.size() == 1 ? distinct.get(0) : null;
	}

	/** Records a manual status change (a new, process-instance-stamped attempt row) for every EPCIS config of the shipment. */
	public void changeStatus(
			@NonNull final InOutId inOutId,
			@NonNull final ExternalSystemExportStatus targetStatus,
			@NonNull final PInstanceId pInstanceId)
	{
		final TableRecordReference sourceRecord = toSourceRecord(inOutId);
		for (final ScriptedExportConversionStatus st : getEpcisStatuses(inOutId))
		{
			exportStatusService.recordManualStatusChange(st.getConfigId(), sourceRecord, targetStatus, pInstanceId);
		}
	}
}
