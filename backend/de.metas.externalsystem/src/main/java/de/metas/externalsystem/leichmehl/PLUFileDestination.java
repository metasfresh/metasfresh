/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.externalsystem.leichmehl;

import de.metas.externalsystem.model.X_ExternalSystem_Config_LeichMehl;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

@AllArgsConstructor
public enum PLUFileDestination implements ReferenceListAwareEnum
{
	DISK(X_ExternalSystem_Config_LeichMehl.PLUFILEDESTINATION_Disk),
	TCP(X_ExternalSystem_Config_LeichMehl.PLUFILEDESTINATION_TCP);

	@Getter
	private final String code;

	@NonNull
	public static PLUFileDestination ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@NonNull
	public static Optional<PLUFileDestination> ofCodeOptional(@Nullable final String code)
	{
		return index.optionalOfNullableCode(code);
	}
	
	private static final ReferenceListAwareEnums.ValuesIndex<PLUFileDestination> index = ReferenceListAwareEnums.index(values());
}