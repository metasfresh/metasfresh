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
				"messageFromMetasfresh", jsonInput,
				"someOtherValue", 123);

		// 4. Define the JavaScript to be executed
		final String script = """
				    function transform(messageFromMetasfresh) {
						var inputData = JSON.parse(messageFromMetasfresh);
					
						var result = {
							processed: true,
							originalName: inputData.name,
							ageInMonths: inputData.age * 12,
						};
					
						// Return the result as a new JSON string
						return JSON.stringify(result);
				    }
				""";

		// 5. Execute the script
		final String result = jsService.executeScript(script, bindings);

		// 6. Validate the result
		assertThat(result).isEqualTo("{\"processed\":true,\"originalName\":\"John\",\"ageInMonths\":360}");
	}
}
