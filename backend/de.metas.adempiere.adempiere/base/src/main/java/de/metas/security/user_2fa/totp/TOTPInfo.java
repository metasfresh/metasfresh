/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.security.user_2fa.totp;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Value
@Builder
public class TOTPInfo
{
	@NonNull UserId userId;
	@NonNull SecretKey secretKey;
	@NonNull String account;
	@NonNull String issuer;

	public String toQRCodeString()
	{
		return toURL();
	}

	private String toURL()
	{
		try
		{
			return "otpauth://totp/"
					+ URLEncoder.encode(issuer + ":" + account, StandardCharsets.UTF_8.name()).replace("+", "%20")
					+ "?secret=" + URLEncoder.encode(secretKey.getAsString(), StandardCharsets.UTF_8.name()).replace("+", "%20")
					+ "&issuer=" + URLEncoder.encode(issuer, StandardCharsets.UTF_8.name()).replace("+", "%20");
		}
		catch (UnsupportedEncodingException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

}
