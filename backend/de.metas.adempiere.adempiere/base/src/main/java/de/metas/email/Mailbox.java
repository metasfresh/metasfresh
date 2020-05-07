package de.metas.email;

import java.io.Serializable;

import org.adempiere.util.Check;
import org.compiere.Adempiere;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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
@SuppressWarnings("serial")
public final class Mailbox implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	@JsonProperty("smtpHost")
	private final String smtpHost;
	@JsonProperty("email")
	private final String email;
	@JsonProperty("username")
	private final String username;
	@JsonProperty("password")
	private final String password;
	@JsonProperty("smtpAuthorization")
	private final boolean smtpAuthorization;
	@JsonProperty("sendFromServer")
	private final boolean sendFromServer;
	@JsonProperty("adClientId")
	private final int adClientId;
	@JsonProperty("adUserId")
	private final int adUserId;
	@JsonProperty("columnUserTo")
	private final String colummnUserTo;

	@JsonCreator
	private Mailbox(
			@JsonProperty("smtpHost") final String smtpHost //
			, @JsonProperty("email") final String email //
			, @JsonProperty("username") final String username //
			, @JsonProperty("password") final String password //
			, @JsonProperty("smtpAuthorization") final boolean smtpAuthorization //
			, @JsonProperty("sendFromServer") final boolean sendFromServer //
			, @JsonProperty("adClientId") final int AD_Client_ID //
			, @JsonProperty("adUserId") final int AD_User_ID //
			, @JsonProperty("columnUserTo") final String columnUserTo //
	)
	{
		this(builder()
				.setSmtpHost(smtpHost)
				.setEmail(email)
				.setUsername(username)
				.setPassword(password)
				.setSmtpAuthorization(smtpAuthorization)
				.setSendFromServer(sendFromServer)
				.setAD_Client_ID(AD_Client_ID)
				.setAD_User_ID(AD_User_ID)
				.setColumnUserTo(columnUserTo));
	}

	private Mailbox(final Builder builder)
	{
		super();
		smtpHost = builder.smtpHost;
		email = builder.email;
		username = builder.username;
		password = builder.password;
		smtpAuthorization = builder.smtpAuthorization;
		sendFromServer = builder.sendFromServer;
		adClientId = builder.AD_Client_ID;
		adUserId = builder.AD_User_ID;
		colummnUserTo = builder.columnUserTo;
	}

	@Override
	public String toString()
	{
		final String passwordStr = Adempiere.isUnitTestMode() ? password : password == null ? "(empty)" : "********";
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("smtpHost", smtpHost)
				.add("email", email)
				.add("smtpAuthorization", smtpAuthorization)
				.add("username", username)
				.add("password", passwordStr)
				.add("sendFromServer", sendFromServer)
				.add("AD_Client_ID", adClientId)
				.add("AD_User_ID", adUserId)
				.add("colummnUserTo", colummnUserTo)
				.toString();
	}

	public String getSmtpHost()
	{
		return smtpHost;
	}

	public String getEmail()
	{
		return email;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public boolean isSmtpAuthorization()
	{
		return smtpAuthorization;
	}

	public boolean isSendFromServer()
	{
		return sendFromServer;
	}

	@JsonIgnore
	public int getAD_Client_ID()
	{
		return adClientId;
	}

	@JsonIgnore
	public int getAD_User_ID()
	{
		return adUserId;
	}

	public String getColumnUserTo()
	{
		return colummnUserTo;
	}

	public static final class Builder
	{
		private String smtpHost;
		private String email;
		private String username;
		private String password;
		private boolean smtpAuthorization;
		private boolean sendFromServer;
		private int AD_Client_ID;
		private int AD_User_ID = -1;
		private String columnUserTo;

		private Builder()
		{
			super();
		}

		public Mailbox build()
		{
			return new Mailbox(this);
		}

		public Builder setSmtpHost(final String smtpHost)
		{
			this.smtpHost = smtpHost;
			return this;
		}

		public Builder setEmail(final String email)
		{
			this.email = email;
			return this;
		}

		public Builder setUsername(final String username)
		{
			this.username = Check.isEmpty(username) ? null : username;
			return this;
		}

		public Builder setPassword(final String password)
		{
			this.password = password;
			return this;
		}

		public Builder setSmtpAuthorization(final boolean isSmtpAuthorization)
		{
			this.smtpAuthorization = isSmtpAuthorization;
			return this;
		}

		public Builder setSendFromServer(final boolean isSendFromServer)
		{
			this.sendFromServer = isSendFromServer;
			return this;
		}

		public Builder setAD_Client_ID(final int adClientId)
		{
			AD_Client_ID = adClientId;
			return this;
		}

		public Builder setAD_User_ID(final int adUserId)
		{
			AD_User_ID = adUserId;
			return this;
		}

		public Builder setColumnUserTo(final String columnUserTo)
		{
			this.columnUserTo = columnUserTo;
			return this;
		}

		public Builder setAllFrom(final Mailbox mailbox)
		{
			setSmtpHost(mailbox.getSmtpHost());
			setEmail(mailbox.getEmail());
			setUsername(mailbox.getUsername());
			setPassword(mailbox.getPassword());
			setSmtpAuthorization(mailbox.isSmtpAuthorization());
			setSendFromServer(mailbox.isSendFromServer());
			setAD_Client_ID(mailbox.getAD_Client_ID());
			setAD_User_ID(mailbox.getAD_User_ID());
			setColumnUserTo(mailbox.getColumnUserTo());
			return this;
		}

	}
}
