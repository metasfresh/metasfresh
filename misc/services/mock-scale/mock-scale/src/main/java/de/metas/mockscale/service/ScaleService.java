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

package de.metas.mockscale.service;

import de.metas.mockscale.SoehenleRequest;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.util.annotation.NonNull;

import java.util.Optional;

@Service
public class ScaleService
{
	@NonNull
	private final Environment env;

	public ScaleService(@NonNull final Environment env)
	{
		this.env = env;
	}

	@NonNull
	public String handleCommand(@NonNull final String command)
	{
		final Optional<SoehenleRequest> soehenleRequest = SoehenleRequest.ofCommandOptional(command);

		final String response;
		if (soehenleRequest.isPresent())
		{
			response = env.getProperty(soehenleRequest.get().getMockResponsePropertyName());
		}
		else
		{
			throw new RuntimeException("Unrecognized command!");
		}

		return Optional.ofNullable(response)
				.orElseThrow(() -> new RuntimeException("No response available for command: " + command));
	}
}
