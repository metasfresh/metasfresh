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

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystemInvocationContext;
import de.metas.externalsystem.IExternalSystemInvocationSuccessListener;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemExportStatusService;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionRepository;
import de.metas.externalsystem.scriptedexportconversion.ScriptedExportConversionStatus;
import de.metas.inout.InOutId;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


/**
 * On a successful scripted-export invocation whose config is the EPCIS outbound export, records
 * every physical SSCC18 the sent shipment carried in the {@code EDI_EPCIS_Transmitted_SSCC}
 * ledger — making the next send idempotent, since {@code get_epcis_events_json_fn} excludes any
 * SSCC already present there.
 *
 * <p>This listener applies to <em>all</em> invocation contexts (the dispatch context is
 * {@code UNKNOWN} for this success path — see {@code ExternalSystemService.handleExportSuccess}):
 * it dispatches by {@code pInstanceId} and gates to EPCIS internally, in {@link #onInvocationSuccess},
 * by comparing the resolved config's outbound-data process against the EPCIS export process. It is a
 * no-op for any other scripted export (no matching status row, a non-EPCIS config, or a shipment with
 * no EPCIS-relevant pallets).
 */
@Component
@RequiredArgsConstructor
public class EpcisTransmittedSsccSuccessListener implements IExternalSystemInvocationSuccessListener
{
	private static final Logger logger = LogManager.getLogger(EpcisTransmittedSsccSuccessListener.class);

	@NonNull private final ExternalSystemExportStatusService exportStatusService;
	@NonNull private final ExternalSystemScriptedExportConversionRepository scriptedExportConversionRepository;
	@NonNull private final EpcisEventsJsonDAO epcisEventsJsonDAO;
	@NonNull private final EpcisTransmittedSsccRepository transmittedSsccRepository;
	@NonNull private final EpcisExportProcess epcisExportProcess;

	/**
	 * Always returns {@code true} — this listener dispatches by {@code pInstanceId} and gates to
	 * EPCIS internally in {@link #onInvocationSuccess}, making it safe to consult for every
	 * invocation context.
	 */
	@Override
	public boolean applies(@NonNull final ExternalSystemInvocationContext context)
	{
		return true;
	}

	@Override
	public void onInvocationSuccess(
			@NonNull final PInstanceId pInstanceId,
			@NonNull final ExternalSystemInvocationContext context,
			@NonNull final HttpStatus httpStatus)
	{
		final ScriptedExportConversionStatus status = exportStatusService.getLatestByPInstanceId(pInstanceId).orElse(null);
		if (status == null)
		{
			logger.debug("No scripted-export status row found for pInstanceId={} - not a scripted export, skipping", pInstanceId);
			return;
		}

		final ExternalSystemScriptedExportConversionConfig config = scriptedExportConversionRepository.getById(status.getConfigId());
		if (!epcisExportProcess.isEpcisExportConfig(config))
		{
			logger.debug("configId={} is not the EPCIS outbound export - skipping", status.getConfigId());
			return;
		}

		final TableRecordReference sourceRecord = status.getSourceRecord();
		if (!sourceRecord.tableNameEqualsTo(I_M_InOut.Table_Name))
		{
			logger.warn("EPCIS config configId={} fired for a non-M_InOut sourceRecord={} - skipping", status.getConfigId(), sourceRecord);
			return;
		}

		final InOutId inOutId = InOutId.ofRepoId(sourceRecord.getRecord_ID());
		final ImmutableList<String> sscc18s = epcisEventsJsonDAO.getPalletSscc18s(inOutId);
		if (sscc18s.isEmpty())
		{
			logger.debug("M_InOut_ID={} carries no EPCIS pallets - nothing to record in the transmission ledger", inOutId);
			return;
		}

		for (final String sscc18 : sscc18s)
		{
			transmittedSsccRepository.recordTransmittedIfAbsent(config.getId(), inOutId, sscc18);
		}
	}
}
