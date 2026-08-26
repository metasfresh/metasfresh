/*
 * #%L
 * de.metas.vatid
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.vatid;

import de.metas.i18n.AdMessageKey;
import de.metas.tax.api.VATIdentifier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * Stateless helper for VAT-ID validation.
 *
 * <p>Whether the check runs at all is the caller's responsibility, resolved from
 * {@code VATaxIDConfig#isFormatCheckEnabled()} — not decided here.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VATaxIDValidationUtil
{
	private static final AdMessageKey MSG_VATaxID_Invalid_Format = AdMessageKey.of("VATaxID_Invalid_Format");

	/**
	 * Throws a user-validation {@link AdempiereException} if {@code vatId} is not a valid VAT-ID.
	 * Null, and values whose prefix is outside the supported country set, are accepted.
	 * The caller is responsible for checking whether the format check should run at all.
	 */
	public static void validate(@Nullable final VATIdentifier vatId)
	{
		final String vatIdString = VATIdentifier.toString(vatId);
		if (!EUVatIdValidator.isValid(vatIdString))
		{
			// AdempiereException(AdMessageKey, …) is already flagged userValidationError=true — no .markAsUserValidationError() needed.
			throw new AdempiereException(MSG_VATaxID_Invalid_Format, vatIdString);
		}
	}

	/**
	 * The non-throwing counterpart of {@link #validate(VATIdentifier)}, for a caller that must RECORD a
	 * verdict for a malformed value rather than abort on it. Same rule set: null, and values whose prefix is
	 * outside the supported country set... see {@link EUVatIdValidator#isValid(String)} for the exact
	 * acceptance rule (null / too-short accepted; a supported prefix must pass its structure and check digit;
	 * an unsupported prefix is rejected). The caller is responsible for checking whether the format check
	 * should run at all.
	 *
	 * @return {@code true} if {@code vatId} passes the offline format check.
	 */
	public static boolean isFormatValid(@Nullable final VATIdentifier vatId)
	{
		return EUVatIdValidator.isValid(VATIdentifier.toString(vatId));
	}
}
