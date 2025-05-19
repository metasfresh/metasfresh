package de.metas.printing.esb.api;

/*
 * #%L
 * de.metas.printing.esb.api
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.Serial;
import java.io.Serializable;

public class LoginResponse implements Serializable
{
	@Serial
	private static final long serialVersionUID = 6515589439074746845L;

	private String username;
	private String sessionId;
	private String hostKey = null;

	@Override
	public String toString()
	{
		return "LoginResponse [username=" + username
				+ ", sessionId=" + sessionId
				+ ", hostKey=" + hostKey
				+ "]";
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getHostKey()
	{
		return hostKey;
	}

	public void setHostKey(String hostKey)
	{
		this.hostKey = hostKey;
	}

}
