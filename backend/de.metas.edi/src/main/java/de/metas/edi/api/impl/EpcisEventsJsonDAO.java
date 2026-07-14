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
 * Reads the EPCIS events JSON payload for a shipment (via {@code "de.metas.edi".get_epcis_events_json_fn})
 * and extracts the physical SSCC18 values it carries — one per pallet ({@code pallets[].sscc}).
 *
 * <p>Used to determine, right after a successful EPCIS send, which physical SSCCs the sent
 * document actually carried, so that each one can be recorded in the
 * {@code EDI_EPCIS_Transmitted_SSCC} ledger (see {@link EpcisTransmittedSsccRepository}).
 * <p>
 * Repository Tables: (none — read-only via the {@code "de.metas.edi".get_epcis_events_json_fn} SQL function)
 * Repository Cluster: EpcisEventsJsonDAO
 */
@Repository
public class EpcisEventsJsonDAO
{
	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Returns the SSCC18 value of every pallet in the EPCIS events JSON for the given shipment.
	 * Returns an empty list when the function yields no pallets (including the {@code '{}'} shape
	 * returned for a shipment with no EPCIS-relevant events).
	 */
	@NonNull
	public ImmutableList<String> getPalletSscc18s(@NonNull final InOutId inOutId)
	{
		final String sql = "SELECT \"de.metas.edi\".get_epcis_events_json_fn(?)::text";
		final String json = DB.getSQLValueStringEx(ITrx.TRXNAME_None, sql, inOutId.getRepoId());

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

		final JsonNode pallets = root.path("pallets");
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
