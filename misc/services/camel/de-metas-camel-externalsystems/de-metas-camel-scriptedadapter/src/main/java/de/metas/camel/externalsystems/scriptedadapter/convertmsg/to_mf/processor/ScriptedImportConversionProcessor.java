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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.ScriptedImportedConversionToMfRequest;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

@RequiredArgsConstructor
public class ScriptedImportConversionProcessor implements Processor
{
	@NonNull private final JavaScriptExecutorService javaScriptExecutorService;
	@NonNull private final String scriptIdentifier;
	@NonNull private final JavaScriptRepo javaScriptRepo;

	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	@Override
	public void process(@NonNull final Exchange exchange) throws JsonProcessingException
	{
		final String request = exchange.getIn().getBody(String.class);
		if (Check.isEmpty(request))
		{
			exchange.getIn().setBody(null);
			return;
		}

		final String script = javaScriptRepo.get(scriptIdentifier);

		final String javaScriptResult = javaScriptExecutorService.executeScript(
				scriptIdentifier,
				script,
				request);

		final List<ScriptedImportedConversionToMfRequest> requests = mapper.readValue(javaScriptResult, new TypeReference<>() {});

		exchange.getIn().setBody(requests);
	}
}
