package de.metas.email;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Mailbox configuration.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
@ToString(exclude = "password")
public final class Mailbox
{
	private static final int DEFAULT_SMTP_PORT = 25;
	private static final int DEFAULT_SMTPS_PORT = 587;

	@JsonProperty("smtpHost")
	private final String smtpHost;
	@JsonProperty("smtpPort")
	private final int smtpPort;
	@JsonProperty("email")
	private final String email;
	@JsonProperty("username")
	private final String username;
	@JsonProperty("password")
	private final String password;
	@JsonProperty("smtpAuthorization")
	private final boolean smtpAuthorization;
	@JsonProperty("startTLS")
	private final boolean startTLS;
	@JsonProperty("sendFromServer")
	private final boolean sendFromServer;
	@JsonProperty("adClientId")
	private final int adClientId;
	@JsonProperty("adUserId")
	private final int adUserId;
	@JsonProperty("columnUserTo")
	private final String columnUserTo;

	@JsonCreator
	@Builder(toBuilder = true)
	private Mailbox(
			@JsonProperty("smtpHost") @NonNull final String smtpHost,
			@JsonProperty("smtpPort") final int smtpPort,
			@JsonProperty("email") final String email,
			@JsonProperty("username") final String username,
			@JsonProperty("password") final String password,
			@JsonProperty("smtpAuthorization") final boolean smtpAuthorization,
			@JsonProperty("startTLS") final boolean startTLS,
			@JsonProperty("sendFromServer") final boolean sendFromServer,
			@JsonProperty("adClientId") final int adClientId,
			@JsonProperty("adUserId") final int adUserId,
			@JsonProperty("columnUserTo") final String columnUserTo)
	{
		Check.assumeNotEmpty(smtpHost, "smtpHost is not empty");

		this.smtpHost = smtpHost.trim();
		this.smtpPort = smtpPort > 0 ? smtpPort : getDefaultSMTPPort(startTLS);
		this.email = email;

		this.smtpAuthorization = smtpAuthorization;
		if (smtpAuthorization)
		{
			Check.assumeNotEmpty(username, "username is not empty");
			this.username = username;
			this.password = password;
		}
		else
		{
			this.username = null;
			this.password = null;
		}

		this.startTLS = startTLS;
		
		this.sendFromServer = sendFromServer;
		this.adClientId = adClientId;
		this.adUserId = adUserId;
		this.columnUserTo = columnUserTo;
	}

	private static int getDefaultSMTPPort(final boolean startTLS)
	{
		return startTLS ? DEFAULT_SMTPS_PORT : DEFAULT_SMTP_PORT;
	}
}
