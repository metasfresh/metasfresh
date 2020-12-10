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
public class SyncProductSupply implements IConfirmableDTO
{
	String uuid;
	boolean deleted;
	long syncConfirmationId;

	String bpartner_uuid;
	String product_uuid;
	String contractLine_uuid;
	BigDecimal qty;
	Date day;
	boolean weekPlanning;
	int version;

	@Builder(toBuilder = true)
	@JsonCreator
	public SyncProductSupply(
			@JsonProperty("uuid") final String uuid,
			@JsonProperty("deleted") final boolean deleted,
			@JsonProperty("syncConfirmationId") final long syncConfirmationId,
			@JsonProperty("bpartner_uuid") final String bpartner_uuid,
			@JsonProperty("product_uuid") final String product_uuid,
			@JsonProperty("contractLine_uuid") final String contractLine_uuid,
			@JsonProperty("qty") final BigDecimal qty,
			@JsonProperty("day") final Date day,
			@JsonProperty("weekPlanning") final boolean weekPlanning,
			@JsonProperty("version") final int version)
	{
		this.uuid = uuid;
		this.deleted = deleted;
		this.syncConfirmationId = syncConfirmationId;

		this.bpartner_uuid = bpartner_uuid;
		this.product_uuid = product_uuid;
		this.contractLine_uuid = contractLine_uuid;
		this.qty = qty;
		this.day = day;
		this.weekPlanning = weekPlanning;
		this.version = version;
	}

	@Override
	public String toString()
	{
		return "SyncProductSupply ["
				+ "bpartner_uuid=" + bpartner_uuid
				+ ", product_uuid=" + product_uuid
				+ ", contractLine_uuid=" + contractLine_uuid
				+ ", qty=" + qty
				+ ", day=" + day
				+ ", weekPlanning=" + weekPlanning
				+ ", version=" + version
				+ ", uuid=" + getUuid()
				+ "]";
	}

	@Override
	public IConfirmableDTO withNotDeleted()
	{
		return toBuilder().deleted(false).build();
	}
}
