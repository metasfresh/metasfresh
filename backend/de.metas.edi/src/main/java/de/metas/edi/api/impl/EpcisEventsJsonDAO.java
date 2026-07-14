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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.common.util.Check;
import de.metas.edi.process.export.json.M_InOut_EPCIS_Export_JSON;
import de.metas.inout.InOutId;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Extracts the physical SSCC18 values (one per pallet, {@code pallets[].sscc}) of the EPCIS events
 * payload for a shipment, so a successful EPCIS send can record each transmitted SSCC in the
 * {@code EDI_EPCIS_Transmitted_SSCC} ledger (see {@link EpcisTransmittedSsccRepository}).
 *
 * <p>Primary source is the <b>actually-sent</b> payload: the EPCIS outbound-export process
 * ({@link M_InOut_EPCIS_Export_JSON}) attaches the JSON it produced to the shipment (an
 * {@code AD_AttachmentEntry} named {@code <process>_<M_InOut_ID>.json}), so at the success callback
 * we read that attachment rather than recomputing — the SSCCs we record are exactly what left the
 * system. A defensive fallback recomputes via {@code "de.metas.edi".get_epcis_events_json_fn} only
 * when no such attachment exists (it shouldn't in the scripted-export send path — the process
 * always attaches when not called via API), so the exactly-once ledger is still written.
 * <p>
 * Repository Tables: (none — reads AD_AttachmentEntry via AttachmentEntryService; falls back to the
 * {@code "de.metas.edi".get_epcis_events_json_fn} SQL function)
 * Repository Cluster: EpcisEventsJsonDAO
 */
@Repository
public class EpcisEventsJsonDAO
{
	private static final Logger logger = LogManager.getLogger(EpcisEventsJsonDAO.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	// AttachmentEntryService is a Spring bean; obtained here (not a constructor param) to match how the
	// EPCIS export process itself resolves it, keeping this @Repository constructor-arg-free.
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	/**
	 * The SSCC18 of every pallet the shipment's EPCIS payload carried. Read from the payload the
	 * export actually sent (the shipment's EPCIS-export attachment); recomputed from the SQL function
	 * only as a fallback when that attachment is absent. Empty when there are no EPCIS pallets.
	 */
	@NonNull
	public ImmutableList<String> getPalletSscc18s(@NonNull final InOutId inOutId)
	{
		final Optional<String> sentJson = readSentEpcisPayload(inOutId);
		if (sentJson.isPresent())
		{
			return extractSscc18s(sentJson.get(), inOutId);
		}

		logger.warn("M_InOut_ID={} has no {} attachment - falling back to recomputing the EPCIS events JSON"
				+ " so the transmission ledger is still written", inOutId.getRepoId(), M_InOut_EPCIS_Export_JSON.class.getSimpleName());
		return getPalletSscc18sFromFunction(inOutId);
	}

	/**
	 * The JSON the EPCIS export actually attached to the shipment, if present — the latest attachment
	 * whose filename is the EPCIS export process output ({@code <process>_<M_InOut_ID>.json}).
	 * {@code getByReferencedRecord} returns youngest-first, so the first match is the most recent send.
	 */
	@NonNull
	private Optional<String> readSentEpcisPayload(@NonNull final InOutId inOutId)
	{
		final I_M_InOut inOut = InterfaceWrapperHelper.load(inOutId.getRepoId(), I_M_InOut.class);
		final String epcisFilenamePrefix = M_InOut_EPCIS_Export_JSON.class.getSimpleName();

		return attachmentEntryService.getByReferencedRecord(inOut)
				.stream()
				.filter(entry -> entry.getFilename() != null && entry.getFilename().startsWith(epcisFilenamePrefix))
				.findFirst()
				.map(this::retrieveAttachmentAsString);
	}

	@NonNull
	private String retrieveAttachmentAsString(@NonNull final AttachmentEntry entry)
	{
		return new String(attachmentEntryService.retrieveData(entry.getId()), StandardCharsets.UTF_8);
	}

	/**
	 * Extracts {@code pallets[].sscc} from an EPCIS events JSON. Handles both the raw function output
	 * ({@code {"pallets":[...]}}) and the attached/sent envelope ({@code {"embedded_json":{"pallets":[...]}}}).
	 */
	@NonNull
	private ImmutableList<String> extractSscc18s(@NonNull final String json, @NonNull final InOutId inOutId)
	{
		if (Check.isBlank(json))
		{
			return ImmutableList.of();
		}

		final JsonNode root;
		try
		{
			root = objectMapper.readTree(json);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed to parse EPCIS events JSON for M_InOut_ID=" + inOutId.getRepoId(), e);
		}

		// the sent/attached payload wraps the function output as {"embedded_json": {...}}; unwrap if present
		final JsonNode eventsNode = root.has("embedded_json") ? root.path("embedded_json") : root;

		final JsonNode pallets = eventsNode.path("pallets");
		if (!pallets.isArray())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<String> result = ImmutableList.builder();
		for (final JsonNode pallet : pallets)
		{
			final String sscc18 = pallet.path("sscc").asText(null);
			if (Check.isNotBlank(sscc18))
			{
				result.add(sscc18);
			}
		}
		return result.build();
	}

	/**
	 * Fallback: recompute the EPCIS events JSON via the SQL function. Used only when the sent payload
	 * attachment is missing (see {@link #getPalletSscc18s}).
	 */
	@NonNull
	private ImmutableList<String> getPalletSscc18sFromFunction(@NonNull final InOutId inOutId)
	{
		final String sql = "SELECT \"de.metas.edi\".get_epcis_events_json_fn(?)::text";
		final String json = DB.getSQLValueStringEx(ITrx.TRXNAME_None, sql, inOutId.getRepoId());
		return extractSscc18s(json, inOutId);
	}
}
