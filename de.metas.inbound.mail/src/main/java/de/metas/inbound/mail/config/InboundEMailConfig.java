package de.metas.inbound.mail.config;

import org.adempiere.service.ClientId;

import de.metas.organization.OrgId;
import de.metas.request.RequestTypeId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.inbound.mail
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

@Value
@ToString(exclude = "password")
public class InboundEMailConfig
{
	InboundEMailConfigId id;
	InboundEMailProtocol protocol;
	String host;
	int port;
	String folder;
	String username;
	String password;
	boolean debugProtocol;

	ClientId adClientId;
	OrgId orgId;
	RequestTypeId requestTypeId;

	@Builder
	private InboundEMailConfig(
			final InboundEMailConfigId id,
			@NonNull final InboundEMailProtocol protocol,
			@NonNull final String host,
			final int port,
			@NonNull final String folder,
			@NonNull final String username,
			@NonNull final String password,
			final boolean debugProtocol,
			//
			@NonNull final ClientId adClientId,
			@NonNull final OrgId orgId,
			final RequestTypeId requestTypeId)
	{
		Check.assumeGreaterThanZero(port, "port");

		this.id = id != null ? id : InboundEMailConfigId.random();

		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.folder = folder;
		this.username = username;
		this.password = password;
		this.debugProtocol = debugProtocol;

		this.adClientId = adClientId;
		this.orgId = orgId;

		this.requestTypeId = requestTypeId;
	}

	public String getUrl()
	{
		return protocol.getProtocolString() + "://" + normalizeUsername(username) + ":" + password + "@" + host + ":" + port + "/" + folder;
	}

	private static final String normalizeUsername(final String username)
	{
		return username.replace("@", "%40");
	}

	public boolean isCreateRequest()
	{
		return getRequestTypeId() != null;
	}
}
