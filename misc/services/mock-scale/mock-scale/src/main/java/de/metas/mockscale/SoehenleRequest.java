/*
 * #%L
 * mock-scale
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.mockscale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum SoehenleRequest
{
	GET_INSTANT_GROSS_WEIGHT("<A>", "response.soehenle.get-instantGrossWeight"),
	GET_STABLE_GROSS_WEIGHT("<H>", "response.soehenle.get-stableGrossWeight");

	private final String command;
	private final String mockResponsePropertyName;

	@NonNull
	public static Optional<SoehenleRequest> ofCommandOptional(@NonNull final String command)
	{
		return Arrays.stream(values())
				.filter(cmd -> cmd.getCommand().equals(command))
				.findFirst();
	}
}
