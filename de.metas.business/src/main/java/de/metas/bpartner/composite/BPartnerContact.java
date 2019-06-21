package de.metas.bpartner.composite;

import javax.annotation.Nullable;

import org.adempiere.ad.table.RecordChangeLog;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import de.metas.bpartner.BPartnerContactId;
import de.metas.util.rest.ExternalId;
import lombok.Builder;
import lombok.Data;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Data
public class BPartnerContact
{
	private BPartnerContactId id;

	private ExternalId externalId;

	private String name;

	private String lastName;

	private String firstName;

	@JsonInclude(Include.NON_NULL)
	private String email;

	@JsonInclude(Include.NON_NULL)
	private String phone;

	private final RecordChangeLog changeLog;

	@Builder(toBuilder = true)
	private BPartnerContact(
			@Nullable final BPartnerContactId id,
			@Nullable final ExternalId externalId,
			final String name,
			final String firstName,
			final String lastName,
			final String email,
			final String phone,
			@Nullable final RecordChangeLog changeLog)
	{
		this.id = id;
		this.externalId = externalId;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;

		this.changeLog = changeLog;
	}

	public BPartnerContact deepCopy()
	{
		return toBuilder().build();
	}
}
