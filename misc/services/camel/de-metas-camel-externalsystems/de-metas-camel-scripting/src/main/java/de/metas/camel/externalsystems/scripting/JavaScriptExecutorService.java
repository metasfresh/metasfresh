/*
 * #%L
 * de-metas-camel-scripting
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

package de.metas.camel.externalsystems.scripting;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SandboxPolicy;
import org.graalvm.polyglot.Value;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static de.metas.camel.externalsystems.scripting.ScriptedAdapterConvertMsgFromMFRouteBuilder.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;

/**
 * Executes javascript.
 * I was unable to get camel's own javascript component to work for me.
 * That's why we have this class now.
 */
public class JavaScriptExecutorService
{
	/**
	 * @param script a javascript that takes one parameter named `messageFromMetasfresh` and converts it to an output-string.
	 *
	 * @return the string return-value of the script
	 */
	public String executeScript(
			@NonNull final String scriptIdentifier,
			@NonNull final String script,
			@NonNull final String input)
	{
		try
		{
			return executeScript(
					script,
					ImmutableMap.of(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, input));
		}
		catch (final PolyglotException e)
		{
			throw new JavaScriptExecutorException(
					scriptIdentifier, script, input,
					e.getMessage(), e);
		}
	}

	/**
	 * Executes a given JavaScript script with provided input variables.
	 *
	 * @param script   The JavaScript code to execute.
	 * @param bindings A map of variable names to Java objects that will be available in the script's scope.
	 * @return The result of the script execution, converted to a string.
	 * @throws RuntimeException if there is an error during script execution.
	 */
	@VisibleForTesting
	String executeScript(@NonNull final String script,
						 @NonNull final Map<String, Object> bindings) throws RuntimeException
	{
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		final ByteArrayOutputStream byteArrayErrorStream = new ByteArrayOutputStream();

		// 1. Create a new polyglot context with access to the JavaScript language ('js').
		try (final Context context = Context.newBuilder("js")
				// Allow the script to access members of any Java object passed into the bindings.
				.sandbox(SandboxPolicy.CONSTRAINED)
				.allowHostAccess(HostAccess.UNTRUSTED)
				.out(byteArrayOutputStream)
				.err(byteArrayErrorStream)
				.build())
		{
			// 2. Get the bindings for the context, which is where we'll put our variables.
			final Value contextBindings = context.getBindings("js");

			// 3. Populate the context's bindings with the variables from our input map.
			for (final Map.Entry<String, Object> entry : bindings.entrySet())
			{
				contextBindings.putMember(entry.getKey(), entry.getValue());
			}

			// 4. Execute the script and get the result.
			final Value result = context.eval("js", script);

			// 5. Convert the result to a Java type and return it.
			// This will handle primitives, maps, lists, etc.
			return result.as(String.class);
		}
	}
}
