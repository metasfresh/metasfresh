/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.log;

import de.metas.contracts.model.X_ModCntr_Log;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum LogEntryContractType implements ReferenceListAwareEnum
{
	INTERIM(X_ModCntr_Log.CONTRACTTYPE_Interim),
	MODULAR_CONTRACT(X_ModCntr_Log.CONTRACTTYPE_ModularContract);

	private static final ReferenceListAwareEnums.ValuesIndex<LogEntryContractType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	@NonNull
	public static LogEntryContractType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static LogEntryContractType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}
}
