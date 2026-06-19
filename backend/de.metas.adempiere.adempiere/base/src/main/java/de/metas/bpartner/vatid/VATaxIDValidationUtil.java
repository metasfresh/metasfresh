/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.vatid;

import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import javax.annotation.Nullable;

/**
 * Stateless helper that validates the structural format of a VAT-ID value before it is persisted.
 *
 * <p>Validation is guarded by the {@code C_BPartner.validateVATaxID} system-configuration entry
 * (default {@code Y}).  When the entry is {@code N}, every value passes regardless of format.
 *
 * <p>Validation is delegated to {@link EUVatIdValidator#isValid(String)}.
 */
public final class VATaxIDValidationUtil
{
	private static final AdMessageKey MSG_VATaxID_Invalid_Format = AdMessageKey.of("VATaxID_Invalid_Format");
	private static final String SYSCONFIG_validateVATaxID = "C_BPartner.validateVATaxID";

	private VATaxIDValidationUtil()
	{
	}

	/**
	 * Validates the given VAT-ID value when the system-config gate is enabled.
	 *
	 * @param vatId value to validate (null and blank are always accepted)
	 * @throws AdempiereException (user-validation error) when validation is enabled and the format is invalid
	 */
	public static void validateIfEnabled(@Nullable final String vatId)
	{
		final boolean enabled = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_validateVATaxID, true);
		if (enabled && !EUVatIdValidator.isValid(vatId))
		{
			throw new AdempiereException(MSG_VATaxID_Invalid_Format, vatId).markAsUserValidationError();
		}
	}
}
