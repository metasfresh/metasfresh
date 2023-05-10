/*
 * #%L
 * de-metas-camel-externalsystems-common
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

package de.metas.camel.externalsystems.common.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.common.rest_api.v2.JsonError;
import de.metas.common.rest_api.v2.JsonErrorItem;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

public class ErrorProcessorTest
{

	@Test
	public void givenHttpOperationFailedException_fromV2Endpoint_whenProcessHttpErrorEncounteredResponse_thenReturnRemoteError() throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final CamelContext ctx = new DefaultCamelContext();

		//given
		final JsonApiResponse errorJsonApiResponse = getErrorJsonApiResponse();
		final HttpOperationFailedException httpOperationFailedException = createException(objectMapper.writeValueAsString(errorJsonApiResponse));
		final StackTraceElement[] stackTraceElements = getStackTrace();
		httpOperationFailedException.setStackTrace(stackTraceElements);

		final Exchange ex = new DefaultExchange(ctx);

		ex.setProperty(Exchange.EXCEPTION_CAUGHT, httpOperationFailedException);

		//when
		final JsonError jsonError = ErrorProcessor.processMetasfreshHttpError(ex);

		//then
		assertThat(jsonError.getRequestId()).isEqualTo(errorJsonApiResponse.getRequestId());
		assertThat(jsonError.getErrors().size()).isEqualTo(2);
		assertThat(jsonError.getErrors().get(0)).isEqualTo(((JsonError)errorJsonApiResponse.getEndpointResponse()).getErrors().get(0));

		assertThat(jsonError.getErrors().get(1).getSourceClassName()).isEqualTo(stackTraceElements[0].getClassName());
		assertThat(jsonError.getErrors().get(1).getSourceMethodName()).isEqualTo(stackTraceElements[0].getMethodName());
	}

	@Test
	public void givenHttpOperationFailedException_fromV2Endpoint_withUnknownPayload_whenProcessHttpErrorEncounteredResponse_thenReturnStacktrace() throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final CamelContext ctx = new DefaultCamelContext();

		//given
		final JsonApiResponse nonErrorApiRequest = getNonErrorApiResponse();
		final HttpOperationFailedException httpOperationFailedException = createException(objectMapper.writeValueAsString(nonErrorApiRequest));
		final StackTraceElement[] stackTraceElements = getStackTrace();
		httpOperationFailedException.setStackTrace(stackTraceElements);

		final Exchange ex = new DefaultExchange(ctx);

		ex.setProperty(Exchange.EXCEPTION_CAUGHT, httpOperationFailedException);

		//when
		final JsonError jsonError = ErrorProcessor.processMetasfreshHttpError(ex);

		//then
		assertThat(jsonError.getRequestId()).isNull();
		assertThat(jsonError.getErrors().size()).isEqualTo(1);
		assertThat(jsonError.getErrors().get(0).getSourceClassName()).isEqualTo(stackTraceElements[0].getClassName());
		assertThat(jsonError.getErrors().get(0).getSourceMethodName()).isEqualTo(stackTraceElements[0].getMethodName());
	}

	@Test
	public void givenHttpOperationFailedException_fromV1Endpoint_whenProcessHttpErrorEncounteredResponse_thenReturnRemoteError() throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final CamelContext ctx = new DefaultCamelContext();

		//given
		final JsonError nonWrappedJsonError = (JsonError)getErrorJsonApiResponse().getEndpointResponse();
		final HttpOperationFailedException httpOperationFailedException = createException(objectMapper.writeValueAsString(nonWrappedJsonError));
		final StackTraceElement[] stackTraceElements = getStackTrace();
		httpOperationFailedException.setStackTrace(stackTraceElements);

		final Exchange ex = new DefaultExchange(ctx);

		ex.setProperty(Exchange.EXCEPTION_CAUGHT, httpOperationFailedException);

		//when
		final JsonError jsonError = ErrorProcessor.processMetasfreshHttpError(ex);

		//then
		assertThat(jsonError.getRequestId()).isNull();
		assertThat(jsonError.getErrors().size()).isEqualTo(2);
		assertThat(jsonError.getErrors().get(0)).isEqualTo(nonWrappedJsonError.getErrors().get(0));

		assertThat(jsonError.getErrors().get(1).getSourceClassName()).isEqualTo(stackTraceElements[0].getClassName());
		assertThat(jsonError.getErrors().get(1).getSourceMethodName()).isEqualTo(stackTraceElements[0].getMethodName());
	}

	@Test
	public void givenHttpOperationFailedException_withNoResponseBody_whenProcessHttpErrorEncounteredResponse_thenReturnStacktrace()
	{
		final CamelContext ctx = new DefaultCamelContext();

		//given
		final HttpOperationFailedException httpOperationFailedException = createException(null);
		final StackTraceElement[] stackTraceElements = getStackTrace();
		httpOperationFailedException.setStackTrace(stackTraceElements);

		final Exchange ex = new DefaultExchange(ctx);

		ex.setProperty(Exchange.EXCEPTION_CAUGHT, httpOperationFailedException);

		//when
		final JsonError jsonError = ErrorProcessor.processMetasfreshHttpError(ex);

		//then
		assertThat(jsonError.getRequestId()).isNull();
		assertThat(jsonError.getErrors().size()).isEqualTo(1);
		assertThat(jsonError.getErrors().get(0).getSourceClassName()).isEqualTo(stackTraceElements[0].getClassName());
		assertThat(jsonError.getErrors().get(0).getSourceMethodName()).isEqualTo(stackTraceElements[0].getMethodName());
	}

	private HttpOperationFailedException createException(@Nullable final String responseBody)
	{
		return new HttpOperationFailedException("url",
												500,
												"Error",
												"location",
												new HashMap<>(),
												responseBody);
	}

	private JsonApiResponse getErrorJsonApiResponse()
	{
		return JsonApiResponse.builder()
				.requestId(JsonMetasfreshId.of(1))
				.endpointResponse(JsonError.ofSingleItem(JsonErrorItem.builder()
																 .message("Remote error message!")
																 .build()))
				.build();
	}

	private JsonApiResponse getNonErrorApiResponse()
	{
		return JsonApiResponse.builder()
				.requestId(JsonMetasfreshId.of(1))
				.endpointResponse("Anything but an error")
				.build();
	}

	private StackTraceElement[] getStackTrace()
	{
		return new StackTraceElement[]{new StackTraceElement("declaringClass", "methodName", "fileName", 1)};
	}
}
