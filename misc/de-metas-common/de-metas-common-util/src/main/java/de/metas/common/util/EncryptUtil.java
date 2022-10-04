/*
 * #%L
 * de-metas-common-util
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.common.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

@UtilityClass
public class EncryptUtil
{
	private final static String HMAC_SHA_256_ALGORITHM = "HmacSHA256";

	@NonNull
	public static String encryptWithHmacSHA256(@NonNull final String payload, @NonNull final String secret) throws NoSuchAlgorithmException, InvalidKeyException
	{
		final Mac sha256_HMAC = Mac.getInstance(HMAC_SHA_256_ALGORITHM);

		final SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(UTF_8), HMAC_SHA_256_ALGORITHM);

		sha256_HMAC.init(secret_key);

		return Hex.encodeHexString(sha256_HMAC.doFinal(payload.getBytes(UTF_8)));
	}

	public static boolean equals(@NonNull final String signature1, @NonNull final String signature2)
	{
		try
		{
			return MessageDigest.isEqual(signature1.getBytes(UTF_8),
										 signature2.getBytes(UTF_8));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
