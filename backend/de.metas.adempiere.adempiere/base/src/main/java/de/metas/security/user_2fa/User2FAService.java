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

package de.metas.security.user_2fa;

import de.metas.security.user_2fa.totp.OTP;
import de.metas.security.user_2fa.totp.SecretKey;
import de.metas.user.UserId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class User2FAService
{
	public boolean isEnabled(@NonNull final UserId userId)
	{
		return getSecretKey(userId).isPresent();
	}

	public boolean isDisabled(@NonNull final UserId userId)
	{
		return !isEnabled(userId);
	}

	private Optional<SecretKey> getSecretKey(final @NonNull UserId userId)
	{
		// TODO copy from master
		// return extractSecretKey(userDAO.getById(userId));
		return Optional.empty();
	}

	public boolean isValidOTP(@NonNull final UserId userId, @NonNull final OTP otp)
	{
		final SecretKey secretKey = getSecretKey(userId).orElse(null);
		if (secretKey == null)
		{
			// 2FA is not enabled for this user
			return false;
		}

		return secretKey.isValid(otp);
	}
}
