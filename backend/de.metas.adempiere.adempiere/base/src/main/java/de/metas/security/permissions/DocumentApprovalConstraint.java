package de.metas.security.permissions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.math.BigDecimal;

@Immutable
public final class DocumentApprovalConstraint extends Constraint
{
	public static final DocumentApprovalConstraint of(
			final boolean canApproveOwnDoc,
			@NonNull final BigDecimal amtApproval,
			@Nullable final CurrencyId currencyId)
	{
		return new DocumentApprovalConstraint(canApproveOwnDoc, amtApproval, currencyId);
	}

	public static final DocumentApprovalConstraint DEFAULT = new DocumentApprovalConstraint(false, BigDecimal.ZERO, null);

	private final boolean canApproveOwnDoc;
	private final BigDecimal amtApproval;
	private final CurrencyId currencyId;

	private DocumentApprovalConstraint(
			boolean canApproveOwnDoc,
			@NonNull BigDecimal amtApproval,
			@Nullable CurrencyId currencyId)
	{
		this.canApproveOwnDoc = canApproveOwnDoc;
		this.amtApproval = amtApproval;
		this.currencyId = currencyId;
	}

	@Override
	public String toString()
	{
		// NOTE: we are making it translateable friendly because it's displayed in Prefereces->Info->Rollen
		final StringBuilder sb = new StringBuilder()
				.append("DocumentApproval[")
				.append("@IsCanApproveOwnDoc@: @" + (canApproveOwnDoc ? "Y" : "N") + "@");

		if (!canApproveOwnDoc)
		{
			sb.append(", @AmtApproval@: " + amtApproval);
			sb.append(", @C_Currency_ID@: " + currencyId);
		}

		sb.append("]");

		return sb.toString();
	}

	@Override
	public boolean isInheritable()
	{
		return false;
	}

	public boolean canApproveOwnDoc()
	{
		return canApproveOwnDoc;
	}

	@NonNull
	public Money getAmtApproval(@NonNull final CurrencyId fallbackCurrencyId)
	{
		return currencyId != null
				? Money.of(amtApproval, currencyId)
				: Money.of(amtApproval, fallbackCurrencyId);
	}
}
