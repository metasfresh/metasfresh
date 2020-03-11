package de.metas.banking.model;

import org.compiere.model.X_C_BankStatementLine;

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

public enum BankStatementLineType implements ReferenceListAwareEnum
{
	Payment(X_C_BankStatementLine.LINETYPE_Payment), //
	PaySelection(X_C_BankStatementLine.LINETYPE_PaySelection), //
	ESR_Import(X_C_BankStatementLine.LINETYPE_ESR_Import) //
	;

	private static final ValuesIndex<BankStatementLineType> valuesIndex = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	BankStatementLineType(@NonNull final String code)
	{
		this.code = code;
	}

	public BankStatementLineType ofNullableCode(final String code)
	{
		return valuesIndex.ofNullableCode(code);
	}
}
