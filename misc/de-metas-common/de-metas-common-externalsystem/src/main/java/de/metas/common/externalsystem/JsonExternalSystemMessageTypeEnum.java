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

/**
 * This enum corresponds with the ADempiere C_Print_Job_Instructions_Status list reference (AD_Reference_ID=540384). Please keep it in sync with the <code>STATUS_</code> constants in
 * /de.metas.printing/src/main/java-gen/de/metas/printing/model/X_C_Print_Job_Instructions.java
 * <p>
 * Note that this enum contains all values from the ADemnpiere class, even if currently only 'D' and 'E' are used.
 */
@AllArgsConstructor
@Getter
public enum JsonExternalSystemMessageTypeEnum
{
	/*
	* Message type for requesting metasfresh authorization
	* */
	REQUEST_AUTHORIZATION("RequestAuthorization"),
	/*
	 * Message type for replying with the authorization token
	 * */
	AUTHORIZATION_REPLY("AuthorizationReply");

	private final String code;

	@NonNull
	public static JsonExternalSystemMessageTypeEnum ofCode(@NonNull final String code)
	{
		return ofCodeOptional(code)
				.orElseThrow(() -> new RuntimeException("No JsonExternalSystemMessageTypeEnum could be found for code " + code + "!"));
	}

	@NonNull
	public static Optional<JsonExternalSystemMessageTypeEnum> ofCodeOptional(@Nullable final String code)
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
