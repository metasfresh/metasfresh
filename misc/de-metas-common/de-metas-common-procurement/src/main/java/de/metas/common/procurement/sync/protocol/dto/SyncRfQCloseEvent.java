/*
 * #%L
 * de-metas-common-procurement
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.procurement.sync.protocol.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/**
 * Event fired by metasfresh server to indicate that a given RfQ was closed.
 *
 * @author metas-dev <dev@metas-fresh.com>
 */
@Value
public class SyncRfQCloseEvent
{
	private String rfq_uuid;
	private boolean winnerKnown;
	private boolean winner;

	private List<SyncProductSupply> plannedSupplies;

	@Builder
	@JsonCreator
	private SyncRfQCloseEvent(
			@JsonProperty("rfq_uuid") final String rfq_uuid,
			@JsonProperty("winnerKnown") final boolean winnerKnown,
			@JsonProperty("winner") final boolean winner,
			@JsonProperty("plannedSupplies") @Singular final List<SyncProductSupply> plannedSupplies)
	{
		this.rfq_uuid = rfq_uuid;
		this.winnerKnown = winnerKnown;
		this.winner = winner;
		this.plannedSupplies = plannedSupplies;
	}

	@Override
	public String toString()
	{
		return "SyncRfQCloseEvent ["
				+ "rfq_uuid=" + rfq_uuid
				+ ", winnerKnown=" + winnerKnown
				+ ", winner=" + winner
				+ ", plannedSupplies=" + plannedSupplies
				+ "]";
	}
}
