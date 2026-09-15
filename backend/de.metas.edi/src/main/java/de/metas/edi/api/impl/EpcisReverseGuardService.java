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

import de.metas.externalsystem.scriptedexportconversion.ExternalSystemExportStatusService;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfigId;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionRepository;
import de.metas.inout.InOutId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Answers "may this shipment's EPCIS SSCCs already be at the receiver?" — true iff the shipment
 * either has a confirmed transmission (an active {@code EDI_EPCIS_Transmitted_SSCC} ledger row) OR
 * an in-flight EPCIS export (a scripted-export status row in {@code Enqueued}/{@code SendingStarted}).
 *
 * <p>The in-flight case closes the race the ledger alone leaves open: the ledger row is written only
 * on the asynchronous success callback, so between dispatch-to-receiver and that callback there is a
 * window in which no ledger row exists yet but the SSCC may already have been transmitted. Reversing/
 * reactivating/voiding in that window and re-completing would re-transmit the same physical SSCC.
 *
 * <p>Both checks consider ACTIVE rows only: deactivating the blocking row (the ledger row, or the
 * in-flight status row, via its WebUI tab) is the sanctioned way to release the shipment — matching
 * the exclusion in {@code get_epcis_events_json_fn}, which likewise ignores deactivated ledger rows.
 */
@Service
@RequiredArgsConstructor
public class EpcisReverseGuardService
{
	@NonNull private final EpcisTransmittedSsccRepository transmittedSsccRepository;
	@NonNull private final ExternalSystemExportStatusService exportStatusService;
	@NonNull private final ExternalSystemScriptedExportConversionRepository scriptedExportConversionRepository;
	@NonNull private final EpcisExportConfigMatcher epcisExportConfigMatcher;

	public boolean isEpcisTransmittedOrInFlight(@NonNull final InOutId inOutId)
	{
		// Two non-atomic reads (ledger, then in-flight status). A theoretical TOCTOU exists if the
		// async success-callback's commit (ledger write + status→Sent) lands exactly between them —
		// but that window is two local SELECTs, dwarfed by the external-system round-trip that gates
		// the callback, so it is negligible in practice and not worth a cross-module combined query.
		return transmittedSsccRepository.hasActiveTransmittedForInOut(inOutId)
				|| hasInflightEpcisExport(inOutId);
	}

	private boolean hasInflightEpcisExport(@NonNull final InOutId inOutId)
	{
		final TableRecordReference sourceRecord = TableRecordReference.of(I_M_InOut.Table_Name, inOutId.getRepoId());
		final List<ExternalSystemScriptedExportConversionConfigId> inflightConfigIds =
				exportStatusService.getInflightConfigsBySourceRecord(sourceRecord);
		return inflightConfigIds.stream()
				.map(scriptedExportConversionRepository::getById)
				.anyMatch(epcisExportConfigMatcher::isEpcisExportConfig);
	}
}
