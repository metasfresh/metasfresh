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

package de.metas.camel.externalsystems.scriptedadapter;

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

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;

/**
 * Executes javascript.
 * I was unable to get camel's own javascript component to work for me.
 * That's why we have this class now.
 */
public class JavaScriptExecutorService
{
	/**
	 * @param scriptIdentifier just needed for additional context in case of an error.
	 * @param script           a javascript with a  {@code function transform(messageFromMetasfresh)} that takes one string parameter and returns a string as output.
	 *                         See `from_metasfresh_template.js` for a template.
	 * @return the string return-value of the script
	 */
	public String executeScript(
			@NonNull final String scriptIdentifier,
			@NonNull final String script,
			@NonNull final String messageFromMetasfresh)
	{
		try
		{
			return executeScript(
					script,
					ImmutableMap.of(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, messageFromMetasfresh));
		}
		catch (final PolyglotException | IllegalStateException | IllegalArgumentException e)
		{
			throw new JavaScriptExecutorException(
					scriptIdentifier, script, messageFromMetasfresh,
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
			// 2. Evaluate the script string to define functions (like 'transform').
			// We don't need the result of this eval directly, as it's just the function definition.
			context.eval("js", script);

			// 3. Get the bindings for the context.
			final Value contextBindings = context.getBindings("js");

			// 4. Populate the context's bindings with the variables from our input map.
			// This makes variables like 'messageFromMetasfresh' globally available in the JS context.
			for (final Map.Entry<String, Object> entry : bindings.entrySet())
			{
				contextBindings.putMember(entry.getKey(), entry.getValue());
			}

			// 5. Get a reference to the 'transform' function from the evaluated script.
			final Value transformFunction = contextBindings.getMember("transform");

			// 6. Check if the 'transform' function exists and is executable.
			if (transformFunction == null || !transformFunction.canExecute())
			{
				throw new IllegalStateException("JavaScript script must define a 'transform' function that is executable.");
			}

			// 7. Get the input parameter value from the bindings.
			final String messageFromMetasfreshInput = (String)bindings.get(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT);
			if (messageFromMetasfreshInput == null)
			{
				throw new IllegalArgumentException("Missing required input parameter: " + PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT);
			}

			// 8. Invoke the 'transform' function, passing the 'messageFromMetasfresh' parameter.
			final Value result = transformFunction.execute(messageFromMetasfreshInput);

			// 9. Convert the result to a Java type and return it.
			return result.as(String.class);
		}
	}
}