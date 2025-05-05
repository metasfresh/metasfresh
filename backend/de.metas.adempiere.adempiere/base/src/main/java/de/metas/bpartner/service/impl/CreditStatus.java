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

package de.metas.bpartner.service.impl;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_BPartner_Stats;

import javax.annotation.Nullable;

public enum CreditStatus  implements ReferenceListAwareEnum
{
	CreditStop(X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop),
	CreditHold(X_C_BPartner_Stats.SOCREDITSTATUS_CreditHold),
	CreditWatch(X_C_BPartner_Stats.SOCREDITSTATUS_CreditWatch),
	NoCreditCheck(X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck),
	CreditOK(X_C_BPartner_Stats.SOCREDITSTATUS_CreditOK),
	NurEineRechnung(X_C_BPartner_Stats.SOCREDITSTATUS_NurEineRechnung);

	private static final ReferenceListAwareEnums.ValuesIndex<CreditStatus> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	CreditStatus(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static CreditStatus ofNullableCode(@Nullable final String code)
	{
		return ofNullableCode(code, null);
	}

	@Nullable
	public static CreditStatus ofNullableCode(@Nullable final String code, @Nullable final CreditStatus fallbackValue)
	{
		return code != null ? ofCode(code) : fallbackValue;
	}

	public static CreditStatus ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final CreditStatus type)
	{
		return type != null ? type.getCode() : null;
	}
}
