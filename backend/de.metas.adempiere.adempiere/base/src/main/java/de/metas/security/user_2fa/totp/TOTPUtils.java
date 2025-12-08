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

import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;

/**
 * Implementation of TOTP: Time-based One-time Password Algorithm
 *
 * @author metasfresh
 * @author thoeger, <a href="https://github.com/taimos/totp">taimos</a>
 */
@UtilityClass
final class TOTPUtils
{
	public static boolean validate(@NonNull final SecretKey secretKey, @NonNull final OTP otp) {return validate(getStep(), secretKey, otp);}

	private static boolean validate(final long step, @NonNull final SecretKey secretKey, @NonNull final OTP otp)
	{
		return OTP.equals(computeOTP(step, secretKey), otp)
				|| OTP.equals(computeOTP(step - 1, secretKey), otp);
	}

	private static long getStep()
	{
		// 30 seconds StepSize (ID TOTP)
		return SystemTime.millis() / 30000;
	}

	private static OTP computeOTP(final long step, @NonNull final SecretKey secretKey)
	{
		String steps = Long.toHexString(step).toUpperCase();
		while (steps.length() < 16)
		{
			steps = "0" + steps;
		}

		// Get the HEX in a Byte[]
		final byte[] msg = hexStr2Bytes(steps);
		final byte[] k = hexStr2Bytes(secretKey.toHexString());

		final byte[] hash = hmac_sha1(k, msg);

		// put selected bytes into result int
		final int offset = hash[hash.length - 1] & 0xf;
		final int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);
		final int otp = binary % 1000000;

		String result = Integer.toString(otp);
		while (result.length() < 6)
		{
			result = "0" + result;
		}

		return OTP.ofString(result);
	}

	/**
	 * @return hex string converted to byte array
	 */
	private static byte[] hexStr2Bytes(final CharSequence hex)
	{
		// Adding one byte to get the right conversion
		// values starting with "0" can be converted
		final byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
		final byte[] ret = new byte[bArray.length - 1];

		// Copy all the REAL bytes, not the "first"
		System.arraycopy(bArray, 1, ret, 0, ret.length);
		return ret;
	}

	/**
	 * This method uses the JCE to provide the crypto algorithm. HMAC computes a Hashed Message Authentication Code with the crypto hash
	 * algorithm as a parameter.
	 *
	 * @param keyBytes the bytes to use for the HMAC key
	 * @param text     the message or text to be authenticated.
	 */
	private static byte[] hmac_sha1(final byte[] keyBytes, final byte[] text)
	{
		try
		{
			final Mac hmac = Mac.getInstance("HmacSHA1");
			final SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
			hmac.init(macKey);
			return hmac.doFinal(text);
		}
		catch (final GeneralSecurityException gse)
		{
			throw new UndeclaredThrowableException(gse);
		}
	}

}
