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

import java.util.Date;

@Value
public class SyncWeeklySupply implements IConfirmableDTO
{
	String uuid;
	boolean deleted;
	long syncConfirmationId;

	String bpartner_uuid;
	String product_uuid;
	Date weekDay;
	String trend;
	int version;

	@Builder(toBuilder = true)
	@JsonCreator
	public SyncWeeklySupply(
			@JsonProperty("uuid") final String uuid,
			@JsonProperty("deleted") final boolean deleted,
			@JsonProperty("syncConfirmationId") final long syncConfirmationId,
			@JsonProperty("bpartner_uuid") final String bpartner_uuid,
			@JsonProperty("product_uuid") final String product_uuid,
			@JsonProperty("weekDay") final Date weekDay,
			@JsonProperty("trend") final String trend,
			@JsonProperty("version") final int version)
	{
		this.uuid = uuid;
		this.deleted = deleted;
		this.syncConfirmationId = syncConfirmationId;

		this.bpartner_uuid = bpartner_uuid;
		this.product_uuid = product_uuid;
		this.weekDay = weekDay;
		this.trend = trend;
		this.version = version;
	}

	@Override
	public String toString()
	{
		return "SyncWeeklySupply ["
				+ "bpartner_uuid=" + bpartner_uuid
				+ ", product_uuid=" + product_uuid
				+ ", weekDay=" + weekDay
				+ ", trend=" + trend
				+ ", uuid=" + getUuid()
				+ ", version=" + version
				+ "]";
	}

	@Override
	public IConfirmableDTO withNotDeleted()
	{
		return toBuilder().deleted(false).build();
	}
}
