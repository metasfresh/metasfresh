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

import com.google.common.io.BaseEncoding;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.apache.commons.codec.binary.Hex;

import javax.annotation.Nullable;
import java.security.SecureRandom;
import java.util.Optional;

@EqualsAndHashCode
public class SecretKey
{
	private static final SecureRandom random = new SecureRandom();

	private final String string;

	private SecretKey(@NonNull final String string)
	{
		this.string = string;
	}

	public static SecretKey random()
	{
		final byte[] bytes = new byte[20];
		random.nextBytes(bytes);

		return new SecretKey(BaseEncoding.base32().encode(bytes));
	}

	public static SecretKey ofString(@NonNull String string)
	{
		return new SecretKey(string);
	}

	public static Optional<SecretKey> optionalOfString(@Nullable String string)
	{
		return StringUtils.trimBlankToOptional(string).map(SecretKey::new);
	}

	@Deprecated
	@Override
	public String toString() {return getAsString();}

	public String getAsString() {return string;}

	public boolean isValid(@NonNull final OTP otp)
	{
		return TOTPUtils.validate(this, otp);
	}

	public String toHexString()
	{
		final byte[] bytes = BaseEncoding.base32().decode(string);

		//return java.util.HexFormat.of().formatHex(bytes);
		return Hex.encodeHexString(bytes);

	}
}
