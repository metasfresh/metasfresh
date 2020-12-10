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
import java.util.Date;

@Value
public class SyncRfQQtyChangeEvent implements IConfirmableDTO
{
	String uuid;
	boolean deleted;
	long syncConfirmationId;

	String rfq_uuid;
	Date day;
	String product_uuid;
	BigDecimal qty;

	@Builder(toBuilder = true)
	@JsonCreator
	private SyncRfQQtyChangeEvent(
			@JsonProperty("uuid") final String uuid,
			@JsonProperty("deleted") final boolean deleted,
			@JsonProperty("syncConfirmationId") final long syncConfirmationId,
			@JsonProperty("rfq_uuid") final String rfq_uuid,
			@JsonProperty("day") final Date day,
			@JsonProperty("product_uuid") final String product_uuid,
			@JsonProperty("qty") final BigDecimal qty)
	{
		this.uuid = uuid;
		this.deleted = deleted;
		this.syncConfirmationId = syncConfirmationId;

		this.rfq_uuid = rfq_uuid;
		this.day = day;
		this.product_uuid = product_uuid;
		this.qty = qty;
	}

	@Override
	public IConfirmableDTO withNotDeleted()
	{
		return toBuilder().deleted(false).build();
	}
}
