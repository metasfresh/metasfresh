package de.metas.email.mailboxes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.email.EMailAddress;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;

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
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@ToString(exclude = "password")
public class Mailbox
{
	private final static Logger logger = LogManager.getLogger(Mailbox.class);

	private static final int DEFAULT_SMTP_PORT = 25;
	private static final int DEFAULT_SMTPS_PORT = 587;

	@JsonProperty("smtpHost")
	String smtpHost;
	@JsonProperty("smtpPort")
	int smtpPort;

	@JsonProperty("email")
	EMailAddress email;

	@JsonProperty("username")
	String username;
	@JsonProperty("password")
	String password;
	@JsonProperty("smtpAuthorization")
	boolean smtpAuthorization;

	@JsonProperty("startTLS")
	boolean startTLS;

	@JsonProperty("sendEmailsFromServer")
	boolean sendEmailsFromServer;
	@JsonProperty("userToColumnName")
	String userToColumnName;

	@JsonCreator
	@Builder(toBuilder = true)
	private Mailbox(
			@JsonProperty("smtpHost") @NonNull final String smtpHost,
			@JsonProperty("smtpPort") final int smtpPort,
			@JsonProperty("email") final EMailAddress email,
			@JsonProperty("username") final String username,
			@JsonProperty("password") final String password,
			@JsonProperty("smtpAuthorization") final boolean smtpAuthorization,
			@JsonProperty("startTLS") final boolean startTLS,
			@JsonProperty("sendEmailsFromServer") final boolean sendEmailsFromServer,
			@JsonProperty("userToColumnName") final String userToColumnName)
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

		this.sendEmailsFromServer = sendEmailsFromServer;
		this.userToColumnName = !Check.isEmpty(userToColumnName, true) ? userToColumnName.trim() : null;
	}

	private static int getDefaultSMTPPort(final boolean startTLS)
	{
		return startTLS ? DEFAULT_SMTPS_PORT : DEFAULT_SMTP_PORT;
	}

	public Mailbox mergeFrom(@Nullable final UserEMailConfig userEmailConfig)
	{
		if (userEmailConfig == null
				|| userEmailConfig.getEmail() == null
				|| Check.isBlank(userEmailConfig.getUsername())
				|| Check.isBlank(userEmailConfig.getPassword()))
		{
			logger.debug("userEmailConfig can't be used because it has no SMTP-UserName, -Password or Mail-Address; userEmailConfig={}", userEmailConfig);
			return this;
		}

		return toBuilder()
				.email(userEmailConfig.getEmail())
				.username(userEmailConfig.getUsername())
				.password(userEmailConfig.getPassword())
				.build();
	}

	public Mailbox withSendEmailsFromServer(final boolean sendEmailsFromServer)
	{
		if (this.sendEmailsFromServer == sendEmailsFromServer)
		{
			return this;
		}

		return toBuilder().sendEmailsFromServer(sendEmailsFromServer).build();
	}

	public Mailbox withUserToColumnName(final String userToColumnName)
	{
		if (Objects.equals(this.userToColumnName, userToColumnName))
		{
			return this;
		}

		return toBuilder().userToColumnName(userToColumnName).build();
	}
}
