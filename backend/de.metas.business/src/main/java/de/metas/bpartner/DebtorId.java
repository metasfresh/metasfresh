package de.metas.bpartner;

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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Typed value object wrapping the integer debtor number from {@code C_BPartner.DebtorId}.
 * This is a plain business-account number, not a table-row foreign key.
 * <p>
 * The convention {@code 0} (or any non-positive value) means "not set"; use
 * {@link #ofNullableNo(Integer)} to normalise incoming integers from DB columns.
 */
@Value
public class DebtorId
{
	/**
	 * Creates a {@link DebtorId} from a positive integer.
	 *
	 * @throws IllegalArgumentException if {@code no} is {@code <= 0}
	 */
	@JsonCreator
	public static DebtorId ofNo(final int no)
	{
		return new DebtorId(no);
	}

	/**
	 * Returns {@code null} when {@code no} is {@code null} or {@code <= 0} (the "≤0 = unset"
	 * normalisation), or a {@link DebtorId} otherwise.
	 * <p>
	 * This is NOT a {@code RepoIdAware}-style {@code ofRepoIdOrNull} — it encapsulates the
	 * business-account-number convention that non-positive values mean "not set".
	 */
	@Nullable
	public static DebtorId ofNullableNo(@Nullable final Integer no)
	{
		return no != null && no > 0 ? new DebtorId(no) : null;
	}

	/** Returns the wrapped number as a plain {@code int}. */
	@JsonValue
	public int toInt()
	{
		return no;
	}

	/**
	 * Converts a (possibly {@code null}) {@link DebtorId} to a nullable {@code Integer}.
	 * Useful when copying a value-type back to a raw DB/model integer column.
	 */
	@Nullable
	public static Integer toIntOrNull(@Nullable final DebtorId debtorId)
	{
		return debtorId != null ? debtorId.no : null;
	}

	int no;

	private DebtorId(final int no)
	{
		this.no = Check.assumeGreaterThanZero(no, "DebtorId");
	}
}
