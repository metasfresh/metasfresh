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
import de.metas.common.util.Check;
import de.metas.inout.InOutId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

/**
 * Extracts the physical SSCC18 values (one per pallet, {@code pallets[].sscc}) of the EPCIS events
 * payload for a shipment, so a successful EPCIS send can record each transmitted SSCC in the
 * {@code EDI_EPCIS_Transmitted_SSCC} ledger (see {@link EpcisTransmittedSsccRepository}).
 *
 * <p>Two entry points, both pure of any business decision (the "which source" decision lives in the
 * service-layer caller, {@code EpcisTransmittedSsccSuccessListener}):
 * <ul>
 *   <li>{@link #extractPalletSscc18s(String)} — parse an already-obtained EPCIS events JSON string
 *       (e.g. the payload the export actually attached to the shipment);</li>
 *   <li>{@link #getPalletSscc18sFromFunction(InOutId)} — recompute the JSON via the
 *       {@code "de.metas.edi".get_epcis_events_json_fn} SQL function and parse it.</li>
 * </ul>
 * <p>
 * Repository Tables: (none — read-only via the {@code "de.metas.edi".get_epcis_events_json_fn} SQL function)
 * Repository Cluster: EpcisEventsJsonDAO
 */
@Repository
public class EpcisEventsJsonDAO
{
	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Recomputes the EPCIS events JSON for the shipment via the SQL function and returns the SSCC18 of
	 * every pallet. Empty when the function yields no pallets (including the {@code '{}'} shape).
	 */
	@NonNull
	public ImmutableList<String> getPalletSscc18sFromFunction(@NonNull final InOutId inOutId)
	{
		final String sql = "SELECT \"de.metas.edi\".get_epcis_events_json_fn(?)::text";
		final String json = DB.getSQLValueStringEx(ITrx.TRXNAME_None, sql, inOutId.getRepoId());
		return extractPalletSscc18s(json, inOutId);
	}

	/**
	 * Extracts {@code pallets[].sscc} from an EPCIS events JSON string. Handles both the raw function
	 * output ({@code {"pallets":[...]}}) and the attached/sent envelope
	 * ({@code {"embedded_json":{"pallets":[...]}}}). Empty when there are no pallets.
	 *
	 * @param inOutId the shipment the JSON belongs to — used only to give a parse-failure a
	 *                diagnosable message; never queried.
	 */
	@NonNull
	public ImmutableList<String> extractPalletSscc18s(@NonNull final String json, @NonNull final InOutId inOutId)
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
}
