package de.metas.banking.payment;

import javax.annotation.Nullable;

import org.compiere.model.X_C_PaySelection;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public enum PaySelectionTrxType implements ReferenceListAwareEnum
{
	DIRECT_DEBIT(X_C_PaySelection.PAYSELECTIONTRXTYPE_DirectDebit), //
	CREDIT_TRANSFER(X_C_PaySelection.PAYSELECTIONTRXTYPE_CreditTransfer) //
	;

	@Getter
	private final String code;

	PaySelectionTrxType(final String code)
	{
		this.code = code;
	}

	public static PaySelectionTrxType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static PaySelectionTrxType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	private static final ValuesIndex<PaySelectionTrxType> index = ReferenceListAwareEnums.index(values());
}
