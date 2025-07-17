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

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaScriptProcessorTests
{
	@Test
	public void executeScript_WithJSON()
	{
		// 1. Instantiate the service
		final JavaScriptExecutorService jsService = new JavaScriptExecutorService();

		// 2. Define the input data (e.g., your JSON string)
		final String jsonInput = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

		// 3. Create a map for variables to pass to the script
		final Map<String, Object> bindings = ImmutableMap.of(
				"requestBody", jsonInput,
				"someOtherValue", 123);

		// 4. Define the JavaScript to be executed
		final String script = """
				    // The 'requestBody' variable is available here because we passed it in the bindings
				    var inputData = JSON.parse(requestBody);
				
				    var result = {
				        processed: true,
				        originalName: inputData.name,
				        ageInMonths: inputData.age * 12,
				        anotherVal: someOtherValue // 'someOtherValue' is also available
				    };
				
				    // Return the result as a new JSON string
				    JSON.stringify(result);
				""";

		// 5. Execute the script
		final Object result = jsService.executeScript(script, bindings);

		// 6. Print the result
		assertThat(result).isEqualTo("{\"processed\":true,\"originalName\":\"John\",\"ageInMonths\":360,\"anotherVal\":123}");
	}

	@Test
	public void executeScript_Simple()
	{
		final JavaScriptExecutorService jsService = new JavaScriptExecutorService();
		final Object numberResult = jsService.executeScript("10 * 20", ImmutableMap.of());
		assertThat(numberResult).isEqualTo(200);
	}
}
