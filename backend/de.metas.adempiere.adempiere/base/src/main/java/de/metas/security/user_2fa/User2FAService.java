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

import de.metas.common.util.CoalesceUtil;
import de.metas.security.user_2fa.totp.OTP;
import de.metas.security.user_2fa.totp.SecretKey;
import de.metas.security.user_2fa.totp.TOTPInfo;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class User2FAService
{
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IClientDAO clientDAO = Services.get(IClientDAO.class);

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
		return extractSecretKey(userDAO.getById(userId));
	}

	private static Optional<SecretKey> extractSecretKey(final I_AD_User user)
	{
		return SecretKey.optionalOfString(user.getSecretKey_2FA());
	}

	public TOTPInfo enable(@NonNull final UserId userId, final boolean regenerateSecretKey)
	{
		final I_AD_User user = userDAO.getById(userId);
		final String account = CoalesceUtil.firstNotBlank(user::getEMail, user::getLogin, user::getName);

		final String companyName = clientDAO.getClientNameById(ClientId.ofRepoId(user.getAD_Client_ID()));

		SecretKey secretKey = extractSecretKey(user).orElse(null);
		if (secretKey == null || regenerateSecretKey)
		{
			secretKey = SecretKey.random();
		}

		user.setSecretKey_2FA(secretKey.getAsString());
		userDAO.save(user);

		return TOTPInfo.builder()
				.userId(userId)
				.secretKey(secretKey)
				.account(account)
				.issuer(companyName)
				.build();
	}

	public void disable(@NonNull final UserId userId)
	{
		final I_AD_User user = userDAO.getById(userId);
		user.setSecretKey_2FA(null);
		userDAO.save(user);
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
