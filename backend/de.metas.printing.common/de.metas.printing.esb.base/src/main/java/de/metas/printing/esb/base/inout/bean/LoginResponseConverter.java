package de.metas.printing.esb.base.inout.bean;

/*
 * #%L
 * de.metas.printing.esb.base
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


import java.math.BigInteger;

import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.base.jaxb.generated.PRTLoginRequestType;
import de.metas.printing.esb.base.util.collections.Converter;

public class LoginResponseConverter implements Converter<LoginResponse, PRTLoginRequestType>
{
	public static final transient LoginResponseConverter instance = new LoginResponseConverter();

	@Override
	public LoginResponse convert(final PRTLoginRequestType xmlLoginResponse)
	{
		final LoginResponse loginResponse = new LoginResponse();
		loginResponse.setUsername(xmlLoginResponse.getUserName());
		loginResponse.setSessionId(toString(xmlLoginResponse.getADSessionID(), null));
		loginResponse.setHostKey(xmlLoginResponse.getHostKey());
		return loginResponse;
	}

	private static final String toString(final BigInteger value, final String defaultValue)
	{
		return value == null ? defaultValue : String.valueOf(value.intValue());
	}

}
