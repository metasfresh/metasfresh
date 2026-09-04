/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.processor;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.RuntimeCamelException;

/**
 * Thrown when a scripted-import conversion script's output cannot be parsed into the expected
 * shape: a JSON array of items, each with a {@code camelServiceRouteID} and a {@code requestBody}.
 * <p>
 * Replaces the opaque Jackson deserialization error (e.g. {@code MismatchedInputException}) with an
 * actionable message naming the expected shape, so a script author immediately knows what to fix
 * (typically: the script returns the raw input instead of transforming it into the expected array).
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ScriptOutputFormatException extends RuntimeCamelException
{
	@NonNull String scriptIdentifier;

	@NonNull String scriptOutput;

	public ScriptOutputFormatException(
			@NonNull final String scriptIdentifier,
			@NonNull final String scriptOutput,
			@NonNull final Throwable cause)
	{
		super(buildMessage(scriptIdentifier, scriptOutput), cause);

		this.scriptIdentifier = scriptIdentifier;
		this.scriptOutput = scriptOutput;
	}

	private static String buildMessage(
			@NonNull final String scriptIdentifier,
			@NonNull final String scriptOutput)
	{
		return "Script '" + scriptIdentifier + "' did not produce the expected output shape. "
				+ "Expected a JSON array of items, each shaped like "
				+ "{\"camelServiceRouteID\": <string>, \"requestBody\": <string>} "
				+ "(the script must transform its input into this array, not just return it unchanged). "
				+ "Actual script output: " + scriptOutput;
	}
}
