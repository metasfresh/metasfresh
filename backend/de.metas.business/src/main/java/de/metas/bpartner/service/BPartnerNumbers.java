package de.metas.bpartner.service;

/*
 * #%L
 * de.metas.business
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

import de.metas.bpartner.CreditorId;
import de.metas.bpartner.DebtorId;
import de.metas.bpartner.service.BPartnerNumberContext.Kind;
import de.metas.interfaces.I_C_BPartner;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.OptionalInt;

/**
 * The debtor and/or creditor number(s) generated for a single business partner in one save cycle.
 * <p>
 * A partner can be both a customer and a vendor, so both may be present at once; either may be
 * absent (that role does not apply, or no number-generation is configured for it) — hence both
 * fields are {@link Nullable}.
 * <p>
 * The value stays in its typed {@link DebtorId}/{@link CreditorId} form here; it is unwrapped to a
 * raw {@code int} only at {@link #getNo(Kind)}, i.e. at the {@code C_BPartner} model-column boundary.
 */
@Value(staticConstructor = "of")
public class BPartnerNumbers
{
	public static BPartnerNumbers NONE = BPartnerNumbers.of(null, null);

	@Nullable DebtorId debtorId;
	@Nullable CreditorId creditorId;

	/**
	 * The generated number for the given {@code kind} as a raw {@code int} (the "get by kind"
	 * accessor), or {@link OptionalInt#empty()} when none was generated for that role.
	 */
	public OptionalInt getNo(@NonNull final Kind kind)
	{
		switch (kind)
		{
			case DEBTOR:
				return debtorId != null ? OptionalInt.of(debtorId.toInt()) : OptionalInt.empty();
			case CREDITOR:
				return creditorId != null ? OptionalInt.of(creditorId.toInt()) : OptionalInt.empty();
			default:
				throw new IllegalArgumentException("Unsupported kind: " + kind);
		}
	}

	/**
	 * Writes the generated number(s) onto the record — debtor and/or creditor. The typed value is
	 * unwrapped to the raw {@code int} model column here, i.e. at the model boundary; a role with no
	 * generated number is left untouched.
	 */
	public void applyTo(@NonNull final I_C_BPartner bpartner)
	{
		getNo(Kind.DEBTOR).ifPresent(bpartner::setDebtorId);
		getNo(Kind.CREDITOR).ifPresent(bpartner::setCreditorId);
	}
}
