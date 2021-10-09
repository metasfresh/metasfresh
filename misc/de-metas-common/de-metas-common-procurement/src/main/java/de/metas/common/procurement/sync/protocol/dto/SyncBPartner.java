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

@Value
public class SyncBPartner implements IConfirmableDTO
{
	String uuid;
	boolean deleted;
	long syncConfirmationId;

	String name;
	List<SyncUser> users;

	boolean syncContracts;
	List<SyncContract> contracts;

	List<SyncRfQ> rfqs;


	@Builder(toBuilder = true)
	@JsonCreator
	private SyncBPartner(
			@JsonProperty("uuid") final String uuid,
			@JsonProperty("deleted") final boolean deleted,
			@JsonProperty("syncConfirmationId") final long syncConfirmationId,
			@JsonProperty("name") final String name,
			@JsonProperty("users") @Singular final List<SyncUser> users,
			@JsonProperty("syncContracts") final Boolean syncContracts,
			@JsonProperty("contracts") @Singular final List<SyncContract> contracts,
			@JsonProperty("rfqs") @Singular final List<SyncRfQ> rfqs)
	{
		this.uuid = uuid;
		this.deleted = deleted;
		this.syncConfirmationId = syncConfirmationId;
		this.name = name;
		this.users = users;
		this.syncContracts = syncContracts != null ? syncContracts : false;
		this.contracts = contracts;
		this.rfqs = rfqs;
	}

	@Override
	public String toString()
	{
		return "SyncBPartner [name=" + name
				+ ", users=" + users
				+ ", syncContracts=" + syncContracts
				+ ", contracts=" + contracts
				+ ", rfqs=" + rfqs
				+ "]";
	}

	@Override
	public IConfirmableDTO withNotDeleted()
	{
		return toBuilder().deleted(false).build();
	}
}
