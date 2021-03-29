/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts;

import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

public enum FlatrateTermStatus implements ReferenceListAwareEnum
{
	Running(X_C_Flatrate_Term.CONTRACTSTATUS_Running),
	DeliveryPause(X_C_Flatrate_Term.CONTRACTSTATUS_DeliveryPause),
	Quit(X_C_Flatrate_Term.CONTRACTSTATUS_Quit),
	Info(X_C_Flatrate_Term.CONTRACTSTATUS_Info),
	Waiting(X_C_Flatrate_Term.CONTRACTSTATUS_Waiting),
	EndingContract(X_C_Flatrate_Term.CONTRACTSTATUS_EndingContract),
	Voided(X_C_Flatrate_Term.CONTRACTSTATUS_Voided);

	@Getter
	private final String code;

	FlatrateTermStatus(final String code)
	{
		this.code = code;
	}

	public static FlatrateTermStatus ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static FlatrateTermStatus ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<FlatrateTermStatus> index = ReferenceListAwareEnums.index(values());

	public static final int AD_Reference_ID = X_C_Flatrate_Term.CONTRACTSTATUS_AD_Reference_ID;

}
