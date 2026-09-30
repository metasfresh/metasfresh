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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.edi.process.export.json.M_InOut_EPCIS_Export_JSON;
import de.metas.externalsystem.ExternalSystemInvocationContext;
import de.metas.externalsystem.IExternalSystemInvocationSuccessListener;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemExportStatusService;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionRepository;
import de.metas.externalsystem.scriptedexportconversion.ScriptedExportConversionStatus;
import de.metas.inout.InOutId;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Optional;

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
	@NonNull private final EpcisEventsJsonParser epcisEventsJsonParser;
	@NonNull private final EpcisTransmittedSsccRepository transmittedSsccRepository;
	@NonNull private final EpcisExportConfigMatcher epcisExportConfigMatcher;
	@NonNull private final AttachmentEntryService attachmentEntryService;

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
		// Log to both the slf4j logger and the current ILoggable sink (process / async-workpackage log),
		// so support can see on the shipment's process log why (or whether) a transmission was recorded.
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final ScriptedExportConversionStatus status = exportStatusService.getLatestByPInstanceId(pInstanceId).orElse(null);
		if (status == null)
		{
			loggable.addLog("EPCIS ledger: no scripted-export status row for pInstanceId={} - not a scripted export, skipping", pInstanceId);
			return;
		}

		final ExternalSystemScriptedExportConversionConfig config = scriptedExportConversionRepository.getById(status.getConfigId());
		if (!epcisExportConfigMatcher.isEpcisExportConfig(config))
		{
			loggable.addLog("EPCIS ledger: configId={} is not the EPCIS outbound export - skipping", status.getConfigId());
			return;
		}

		final TableRecordReference sourceRecord = status.getSourceRecord();
		if (!sourceRecord.tableNameEqualsTo(I_M_InOut.Table_Name))
		{
			Loggables.withLogger(logger, Level.WARN)
					.addLog("EPCIS ledger: EPCIS config configId={} fired for a non-M_InOut sourceRecord={} - skipping", status.getConfigId(), sourceRecord);
			return;
		}

		final InOutId inOutId = InOutId.ofRepoId(sourceRecord.getRecord_ID());
		final ImmutableList<String> sscc18s = getTransmittedSscc18s(sourceRecord, inOutId);
		if (sscc18s.isEmpty())
		{
			loggable.addLog("EPCIS ledger: M_InOut_ID={} carries no EPCIS pallets - nothing to record", inOutId);
			return;
		}

		for (final String sscc18 : sscc18s)
		{
			transmittedSsccRepository.recordTransmittedIfAbsent(config.getId(), inOutId, sscc18);
		}
		loggable.addLog("EPCIS ledger: recorded {} transmitted SSCC(s) {} for M_InOut_ID={} (config={})",
				sscc18s.size(), sscc18s, inOutId, config.getId());
	}

	/**
	 * The physical SSCC18s that were sent — read from the payload the EPCIS export attached to the
	 * shipment ({@link M_InOut_EPCIS_Export_JSON} attaches its output as {@code <process>_<M_InOut_ID>.json}).
	 * On this success path the export always attached the payload, so a missing attachment means
	 * nothing was actually sent — we then record nothing rather than fabricate a ledger entry.
	 */
	@NonNull
	private ImmutableList<String> getTransmittedSscc18s(
			@NonNull final TableRecordReference sourceRecord,
			@NonNull final InOutId inOutId)
	{
		final String epcisFilenamePrefix = M_InOut_EPCIS_Export_JSON.class.getSimpleName();
		// The LATEST EPCIS export attachment is this send's payload — pick it explicitly by the highest
		// AttachmentEntry id (a later send has a higher id) rather than relying on the query's ordering.
		final Optional<AttachmentEntry> sentPayload = attachmentEntryService.getByReferencedRecord(sourceRecord)
				.stream()
				.filter(entry -> entry.getFilename() != null && entry.getFilename().startsWith(epcisFilenamePrefix))
				.max(Comparator.comparingInt(entry -> AttachmentEntryId.getRepoId(entry.getId())));

		if (!sentPayload.isPresent())
		{
			// Impossible on the success path (the export attaches its payload unconditionally): if there
			// is no attachment, nothing was sent, so writing a ledger row would claim a transmission that
			// never happened. Log it loudly and record nothing.
			Loggables.withLogger(logger, Level.WARN).addLog(
					"EPCIS ledger: success callback for M_InOut_ID={} but no {} attachment - nothing was sent, recording nothing",
					inOutId.getRepoId(), epcisFilenamePrefix);
			return ImmutableList.of();
		}

		final String json = new String(attachmentEntryService.retrieveData(sentPayload.get().getId()), StandardCharsets.UTF_8);
		return epcisEventsJsonParser.extractPalletSscc18s(json, inOutId);
	}
}
