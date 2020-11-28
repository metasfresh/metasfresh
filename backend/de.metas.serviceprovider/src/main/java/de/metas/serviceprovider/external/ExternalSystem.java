/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.serviceprovider.model.X_S_ExternalProjectReference.EXTERNALSYSTEM_Everhour;
import static de.metas.serviceprovider.model.X_S_ExternalProjectReference.EXTERNALSYSTEM_Github;

@AllArgsConstructor
@Getter
public enum ExternalSystem
{
	GITHUB(EXTERNALSYSTEM_Github),
	EVERHOUR(EXTERNALSYSTEM_Everhour);

	private final String value;

	@NonNull
	public static Optional<ExternalSystem> of(@NonNull final String code)
	{
		return Stream.of(values())
				.filter( type -> type.getValue().equals(code))
				.findFirst();
	}
}

