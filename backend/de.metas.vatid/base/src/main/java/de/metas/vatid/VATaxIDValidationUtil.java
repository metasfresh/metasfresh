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
 * <p>{@link #SYSCONFIG_validateVATaxID} is the gate; reading it is the caller's responsibility (the
 * interceptors hold an {@code ISysConfigBL} instance field), so this helper itself stays free of any
 * service-locator call. {@link #validate(VATIdentifier)} performs the actual check via
 * {@link EUVatIdValidator#isValid(String)} and throws a user-validation error when the value is invalid.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VATaxIDValidationUtil
{
	/**
	 * Whether saving a malformed VAT-ID is rejected.
	 *
	 * <p>{@code Y} — the save-time format check is enforced: saving a {@code VATaxID} on
	 * {@code C_BPartner} or {@code C_BPartner_Location} whose format is wrong for its country prefix
	 * fails with a user error and the record is not stored. {@code N} — the check is skipped, so any
	 * value can be saved; useful for importing legacy data that would otherwise be unsavable.
	 *
	 * <p>Absent or unset counts as {@code Y}: the callers pass {@code true} as the default, and the
	 * shipped row is a single System-level {@code Y} with no organisation override. So the check is on
	 * unless someone deliberately turns it off.
	 *
	 * <p>Note this gates only the <em>save-time</em> check. It does not gate the format check inside
	 * {@code VATaxIDCheckService}, which the per-organisation {@code VATaxID_Config} governs instead.
	 */
	public static final String SYSCONFIG_validateVATaxID = "C_BPartner.validateVATaxID";
	private static final AdMessageKey MSG_VATaxID_Invalid_Format = AdMessageKey.of("VATaxID_Invalid_Format");

	/**
	 * Throws a user-validation {@link AdempiereException} if {@code vatId} is not a valid VAT-ID.
	 * Null, and values whose prefix is outside the supported country set, are accepted.
	 * The caller is responsible for checking the {@link #SYSCONFIG_validateVATaxID} gate first.
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
}
