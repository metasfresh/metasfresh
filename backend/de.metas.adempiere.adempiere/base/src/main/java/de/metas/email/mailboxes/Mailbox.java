package de.metas.email.mailboxes;

<<<<<<< HEAD
import java.util.Objects;

import javax.annotation.Nullable;

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

=======
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.email.EMailAddress;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
@ToString(exclude = "password")
public final class Mailbox
{
	private final static transient Logger logger = LogManager.getLogger(Mailbox.class);
=======
public class Mailbox
{
	private final static Logger logger = LogManager.getLogger(Mailbox.class);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	private static final int DEFAULT_SMTP_PORT = 25;
	private static final int DEFAULT_SMTPS_PORT = 587;

<<<<<<< HEAD
	@JsonProperty("smtpHost")
	private final String smtpHost;
	@JsonProperty("smtpPort")
	private final int smtpPort;

	@JsonProperty("email")
	private final EMailAddress email;

	@JsonProperty("username")
	private final String username;
	@JsonProperty("password")
	private final String password;
	@JsonProperty("smtpAuthorization")
	private final boolean smtpAuthorization;

	@JsonProperty("startTLS")
	private final boolean startTLS;

	@JsonProperty("sendEmailsFromServer")
	private final boolean sendEmailsFromServer;
	@JsonProperty("userToColumnName")
	private final String userToColumnName;

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
=======
	@NonNull MailboxType type;
	@NonNull EMailAddress email;
	@Nullable String userToColumnName;
	@Nullable SMTPConfig smtpConfig;
	@Nullable MicrosoftGraphConfig microsoftGraphConfig;

	@Jacksonized
	@Builder(toBuilder = true)
	private Mailbox(
			@NonNull final MailboxType type,
			@NonNull final EMailAddress email,
			@Nullable final String userToColumnName,
			@Nullable final SMTPConfig smtpConfig,
			@Nullable final MicrosoftGraphConfig microsoftGraphConfig)
	{
		this.type = type;
		this.email = email;
		this.userToColumnName = StringUtils.trimBlankToNull(userToColumnName);

		switch (type)
		{
			case SMTP:
			{
				this.smtpConfig = Check.assumeNotNull(smtpConfig, "smtpConfig cannot be null");
				this.microsoftGraphConfig = null;
				break;
			}
			case MICROSOFT_GRAPH:
			{
				this.smtpConfig = null;
				this.microsoftGraphConfig = Check.assumeNotNull(microsoftGraphConfig, "microsoftGraphConfig cannot be null");
				break;
			}
			default:
			{
				throw new AdempiereException("Unknown type: " + type);
			}
		}
	}

	public ExplainedOptional<Mailbox> mergeFrom(@Nullable final UserEMailConfig userEmailConfig)
	{
		if (userEmailConfig == null)
		{
			logger.debug("userEmailConfig can't be used because it has no user config");
			return ExplainedOptional.of(this);
		}

		final EMailAddress userEmail = userEmailConfig.getEmail();
		if (userEmail == null)
		{
			logger.debug("userEmailConfig can't be used because it has no Mail-Address: {}", userEmailConfig);
			return ExplainedOptional.emptyBecause("userEmailConfig can't be used because it has no Mail-Address: " + userEmailConfig);
		}

		switch (type)
		{
			case SMTP:
			{
				final SMTPConfig smtpConfig = getSmtpConfigNotNull();

				if (smtpConfig.isSmtpAuthorization()
						&& (Check.isBlank(userEmailConfig.getUsername()) || Check.isBlank(userEmailConfig.getPassword())))
				{
					return ExplainedOptional.emptyBecause("userEmailConfig can't be used because it has no SMTP-UserName or SMTP-Password: " + userEmailConfig);
				}

				return ExplainedOptional.of(
						toBuilder()
								.email(userEmail)
								.smtpConfig(smtpConfig.mergeFrom(userEmailConfig))
								.build()
				);
			}
			case MICROSOFT_GRAPH:
			{
				final MicrosoftGraphConfig microsoftGraphConfig = getMicrosoftGraphConfigNotNull();
				return ExplainedOptional.of(
						toBuilder()
								.microsoftGraphConfig(microsoftGraphConfig.withDefaultReplyTo(userEmail))
								.build()
				);
			}
			default:
				throw new AdempiereException("Unknown type: " + type);
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public Mailbox withUserToColumnName(final String userToColumnName)
	{
<<<<<<< HEAD
		if (Objects.equals(this.userToColumnName, userToColumnName))
		{
			return this;
		}

		return toBuilder().userToColumnName(userToColumnName).build();
	}
=======
		final String userToColumnNameNorm = StringUtils.trimBlankToNull(userToColumnName);
		if (Objects.equals(this.userToColumnName, userToColumnNameNorm))
		{
			return this;
		}
		else
		{
			return toBuilder().userToColumnName(userToColumnName).build();
		}
	}

	public SMTPConfig getSmtpConfigNotNull() {return Check.assumeNotNull(smtpConfig, "smtpConfig cannot be null");}

	public MicrosoftGraphConfig getMicrosoftGraphConfigNotNull() {return Check.assumeNotNull(microsoftGraphConfig, "microsoftGraphConfig cannot be null");}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
