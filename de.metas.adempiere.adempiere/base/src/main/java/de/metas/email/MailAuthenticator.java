package de.metas.email;

import java.io.Serializable;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.adempiere.util.Check;

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
 * Simple username/password based email {@link Authenticator}.
 * <p>
 * This is basically a reimplementation of the class of <code>org.compiere.util.EMailAuthenticator</code> which was authored (according to the javadoc) by Joerg Janke
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MailAuthenticator extends Authenticator implements Serializable
{

	private static final long serialVersionUID = -1453447115062744524L;

	/**
	 *
	 * @param username may not be <code>null</code> or empty.
	 * @param password may not be <code>null</code> or empty.
	 *
	 * @return
	 */
	public static final MailAuthenticator of(final String username, final String password)
	{
		return new MailAuthenticator(username, password);
	}

	private final PasswordAuthentication passwordAuthenticator;

	/**
	 *
	 * @param username
	 * @param password
	 */
	private MailAuthenticator(final String username, final String password)
	{
		Check.assumeNotEmpty(username, "username not empty");
		Check.assumeNotEmpty(password, "password not empty");
		passwordAuthenticator = new PasswordAuthentication(username, password);
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return passwordAuthenticator;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("username", passwordAuthenticator.getUserName())
				.add("password", "********")
				.toString();
	}

}
