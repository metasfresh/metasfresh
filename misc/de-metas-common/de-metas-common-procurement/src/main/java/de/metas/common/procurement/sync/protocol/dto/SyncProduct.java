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

import java.util.Map;

@Value
public class SyncProduct implements IConfirmableDTO
{
	String uuid;
	boolean deleted;
	long syncConfirmationId;

	String name;
	String packingInfo;
	boolean shared;

	/**
	 * adLanguage to translated product name map
	 */
	Map<String, String> nameTrls;

	@Builder(toBuilder = true)
	@JsonCreator
	public SyncProduct(
			@JsonProperty("uuid") final String uuid,
			@JsonProperty("deleted") final boolean deleted,
			@JsonProperty("syncConfirmationId") final long syncConfirmationId,
			@JsonProperty("name") final String name,
			@JsonProperty("packingInfo") final String packingInfo,
			@JsonProperty("shared") final boolean shared,
			@Singular @JsonProperty("namesTrl") final Map<String, String> nameTrls)
	{
		this.uuid = uuid;
		this.deleted = deleted;
		this.syncConfirmationId = syncConfirmationId;

		this.name = name;
		this.packingInfo = packingInfo;
		this.shared = shared;
		this.nameTrls = nameTrls;
	}

	@Override
	public String toString()
	{
		return "SyncProduct [name=" + name + ", packingInfo=" + packingInfo + ", shared=" + shared + ", nameTrls=" + nameTrls + "]";
	}

	public SyncProduct copy()
	{
		return toBuilder().build();
	}

	@Override
	public IConfirmableDTO withNotDeleted()
	{
		return toBuilder().deleted(false).build();
	}
}
