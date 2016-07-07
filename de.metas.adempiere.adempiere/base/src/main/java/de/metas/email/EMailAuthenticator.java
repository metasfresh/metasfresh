package de.metas.email;

import java.io.Serializable;

import javax.annotation.concurrent.Immutable;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Username/Password EMail {@link Authenticator}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
@Immutable
public final class EMailAuthenticator extends Authenticator implements Serializable
{
	public static final EMailAuthenticator of(final String username, final String password)
	{
		return new EMailAuthenticator(username, password);
	}

	@JsonProperty("username")
	private final String username;
	@JsonProperty("password")
	private final String password;
	@JsonIgnore
	private transient PasswordAuthentication _passwordAuthenticator;

	/**
	 * Constructor
	 *
	 * @param username user name
	 * @param password user password
	 */
	@JsonCreator
	private EMailAuthenticator(@JsonProperty("username") final String username, @JsonProperty("password") final String password)
	{
		super();

		Check.assumeNotEmpty(username, "username not empty");
		this.username = username;
		Check.assumeNotEmpty(password, "password not empty");
		this.password = password;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("username", username)
				.add("password", "********")
				.toString();
	}

	@Override
	@JsonIgnore
	protected PasswordAuthentication getPasswordAuthentication()
	{
		if (_passwordAuthenticator == null)
		{
			_passwordAuthenticator = new PasswordAuthentication(username, password);

		}
		return _passwordAuthenticator;
	}
}
