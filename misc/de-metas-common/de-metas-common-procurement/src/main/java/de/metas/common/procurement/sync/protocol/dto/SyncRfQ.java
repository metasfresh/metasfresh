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
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class SyncRfQ implements IConfirmableDTO
{
	String uuid;
	boolean deleted;
	long syncConfirmationId;

	LocalDate dateStart;
	LocalDate dateEnd;
	LocalDate dateClose;

	String bpartner_uuid;

	SyncProduct product;
	BigDecimal qtyRequested;
	String qtyCUInfo;

	String currencyCode;

	@JsonCreator
	@Builder(toBuilder = true)
	public SyncRfQ(
			@JsonProperty("uuid") final String uuid,
			@JsonProperty("deleted") final boolean deleted,
			@JsonProperty("syncConfirmationId") final long syncConfirmationId,
			@JsonProperty("dateStart") final LocalDate dateStart,
			@JsonProperty("dateEnd") final LocalDate dateEnd,
			@JsonProperty("dateClose") final LocalDate dateClose,
			@JsonProperty("bpartner_uuid") final String bpartner_uuid,
			@JsonProperty("product") final SyncProduct product,
			@JsonProperty("qtyRequested") final BigDecimal qtyRequested,
			@JsonProperty("qtyCUInfo") final String qtyCUInfo,
			@JsonProperty("currencyCode") final String currencyCode)
	{
		this.uuid = uuid;
		this.deleted = deleted;
		this.syncConfirmationId = syncConfirmationId;

		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.dateClose = dateClose;
		this.bpartner_uuid = bpartner_uuid;
		this.product = product;
		this.qtyRequested = qtyRequested;
		this.qtyCUInfo = qtyCUInfo;
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString()
	{
		return "SyncRfQ ["
				+ "dateStart=" + dateStart
				+ ", dateEnd=" + dateEnd
				+ ", dateClose=" + dateClose
				//
				+ ", bpartner_uuid=" + bpartner_uuid
				//
				+ ", product=" + product
				+ ", qtyRequested=" + qtyRequested + " " + qtyCUInfo
				//
				+ ", currencyCode=" + currencyCode
				+ "]";
	}

	@Override
	public IConfirmableDTO withNotDeleted()
	{
		return toBuilder().deleted(false).build();
	}
}
