package de.metas.material.dispo.reconcile;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

/**
 * How far one reconciliation key's stored projected ATP is from the target the
 * {@link AtpTargetCalculator} computes for it.
 * <p>
 * The {@code difference} is always {@code expectedAtp - storedAtp}, i.e. the delta that has to be applied
 * to the stored projection to bring it onto the target. It is computed here rather than by the callers, so
 * that the reconciliation (which writes that delta) and the divergence report (which only shows it) cannot
 * end up with opposite signs.
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AtpDivergence
{
	/** The target: physical stock plus the key's still-open contributions. */
	@NonNull BigDecimal expectedAtp;

	/** What the stored projection currently says. */
	@NonNull BigDecimal storedAtp;

	/** {@code expectedAtp - storedAtp}. */
	@NonNull BigDecimal difference;

	public static AtpDivergence of(
			@NonNull final BigDecimal expectedAtp,
			@NonNull final BigDecimal storedAtp)
	{
		return new AtpDivergence(expectedAtp, storedAtp, expectedAtp.subtract(storedAtp));
	}
}
