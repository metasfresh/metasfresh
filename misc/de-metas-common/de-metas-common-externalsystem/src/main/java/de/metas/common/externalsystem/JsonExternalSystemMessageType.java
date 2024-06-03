package de.metas.common.externalsystem;

/*
 * #%L
 * de.metas.printing.esb.api
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.common.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum JsonExternalSystemMessageType
{
	/**
	 * Message type for requesting metasfresh authorization
	 */
	REQUEST_AUTHORIZATION("RequestAuthorization"),

	/**
	 * Message type for replying with the metasfresh authorization
	 */
	AUTHORIZATION_REPLY("AuthorizationReply");

	private final String code;

	@NonNull
	public static JsonExternalSystemMessageType ofCode(@NonNull final String code)
	{
		return ofCodeOptional(code)
				.orElseThrow(() -> new RuntimeException("No JsonExternalSystemMessageType could be found for code " + code + "!"));
	}

	@NonNull
	public static Optional<JsonExternalSystemMessageType> ofCodeOptional(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return Optional.empty();
		}

		return Arrays.stream(values())
				.filter(value -> value.getCode().equals(code))
				.findFirst();
	}
}
