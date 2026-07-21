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

import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.ScriptedImportedConversionToMfRequest;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ScriptedImportConversionProcessorTest
{
	private static final String SCRIPT_IDENTIFIER = "mock-script-identifier";
	private static final String SCRIPT = "mock:script.js";

	private final JavaScriptRepo javaScriptRepo = Mockito.mock(JavaScriptRepo.class);
	private final JavaScriptExecutorService javaScriptExecutorService = Mockito.mock(JavaScriptExecutorService.class);

	private final ScriptedImportConversionProcessor processor =
			new ScriptedImportConversionProcessor(javaScriptExecutorService, SCRIPT_IDENTIFIER, javaScriptRepo);

	private static Exchange newExchange(final String body)
	{
		final CamelContext camelContext = new DefaultCamelContext();
		final Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(body);
		return exchange;
	}

	@Test
	void validArrayOutput_isDeserializedAndSetAsBody() throws Exception
	{
		Mockito.when(javaScriptRepo.get(SCRIPT_IDENTIFIER)).thenReturn(SCRIPT);

		final String request = "{\"orderId\":\"1\"}";
		final String scriptOutput = "[{\"camelServiceRouteID\":\"Route1\",\"requestBody\":\"{}\"}]";

		Mockito.when(javaScriptExecutorService.executeScript(SCRIPT_IDENTIFIER, SCRIPT, request))
				.thenReturn(scriptOutput);

		final Exchange exchange = newExchange(request);

		processor.process(exchange);

		@SuppressWarnings("unchecked")
		final List<ScriptedImportedConversionToMfRequest> body = exchange.getIn().getBody(List.class);

		assertThat(body).hasSize(1);
		assertThat(body.get(0).getCamelServiceRouteID()).isEqualTo("Route1");
		assertThat(body.get(0).getRequestBody()).isEqualTo("{}");
	}

	@Test
	void nonArrayOutput_failsWithClearActionableMessage_notOpaqueJacksonError() throws Exception
	{
		Mockito.when(javaScriptRepo.get(SCRIPT_IDENTIFIER)).thenReturn(SCRIPT);

		final String request = "{\"orderId\":\"1\"}";
		// the real-world bug this guards against: a script that just echoes the raw input object
		// back instead of transforming it into the expected item array.
		final String scriptOutput = "{\"orderId\":\"1\"}";

		Mockito.when(javaScriptExecutorService.executeScript(SCRIPT_IDENTIFIER, SCRIPT, request))
				.thenReturn(scriptOutput);

		final Exchange exchange = newExchange(request);

		assertThatThrownBy(() -> processor.process(exchange))
				.hasMessageContaining("camelServiceRouteID")
				.hasMessageContaining("requestBody")
				.hasMessageContaining("array")
				.hasMessageContaining(SCRIPT_IDENTIFIER)
				.hasMessageNotContaining("MismatchedInputException");
	}
}
