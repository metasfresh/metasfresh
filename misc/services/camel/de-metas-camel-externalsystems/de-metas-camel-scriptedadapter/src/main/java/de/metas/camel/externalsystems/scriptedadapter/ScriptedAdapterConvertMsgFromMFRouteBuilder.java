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
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpMethods;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * Invokes a given script on a given request-body and forwards the result to an an API-request.
 */
@RequiredArgsConstructor
@Component
public class ScriptedAdapterConvertMsgFromMFRouteBuilder extends RouteBuilder
{
	public static final String Scripting_ROUTE_ID = "ScriptedAdapter-ConvertMsgFromMF";

	public static final String PROPERTY_SCRIPTING_REPO_BASE_DIR = "metasfresh.scriptedadapter.repo.baseDir";

	public static final String PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT = "messageFromMetasfresh";
	public static final String PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER = "scriptIdentifier";
	public static final String PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_EP = "outboundHttpEP";
	public static final String PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_TOKEN = "outboundHttpToken";
	public static final String PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_METHOD = "outboundHttpMethod";
	
	@VisibleForTesting
	static final String SCRIPTEDADAPTER_OUTBOUND_HTTP_EP_ID = "scriptedAdapterOutboundHttpEPId";

	private JavaScriptRepo javaScriptRepo;

	private final JavaScriptExecutorService javaScriptExecutorService = new JavaScriptExecutorService();

	@NonNull
	private final ProcessLogger processLogger;

	@Override
	public void configure()
	{
		CamelRouteUtil.setupProperties(getContext());

		javaScriptRepo = new JavaScriptRepo(getContext().resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));

		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(Scripting_ROUTE_ID))
				.routeId(Scripting_ROUTE_ID)
				.log("Route invoked!")
				.process(this::buildAndSetContext)
				.process(this::executeJavaScript)
				.process(this::prepareHttpRequest)
				//dev-note: the actual path is set in this.prepareHttpRequest()
				.to("https://placeholder").id(SCRIPTEDADAPTER_OUTBOUND_HTTP_EP_ID)
			;
		//@formatter:on
	}

	private void buildAndSetContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final Map<String, String> parameters = request.getParameters();

		final MsgFromMfContext msgFromMfContext = MsgFromMfContext.builder()
				.scriptingRequestBody(parameters.get(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT))
				.scriptIdentifier(parameters.get(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER))
				.outboundHttpEP(parameters.get(PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_EP))
				.outboundHttpToken(parameters.get(PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_TOKEN))
				.outboundHttpMethod(parameters.get(PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_METHOD))
				.build();
		exchange.getIn().setBody(msgFromMfContext);
	}

	private void executeJavaScript(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = exchange.getIn().getBody(MsgFromMfContext.class);

		final String script = javaScriptRepo.get(msgFromMfContext.getScriptIdentifier());
		msgFromMfContext.setScript(script);

		final String javaScriptResult = javaScriptExecutorService.executeScript(
				msgFromMfContext.getScriptIdentifier(),
				msgFromMfContext.getScript(),
				msgFromMfContext.getScriptingRequestBody());

		msgFromMfContext.setScriptReturnValue(javaScriptResult);
	}

	private void prepareHttpRequest(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = exchange.getIn().getBody(MsgFromMfContext.class);
		
		exchange.getIn().removeHeaders("CamelHttp*");
		exchange.getIn().setHeader(AUTHORIZATION, msgFromMfContext.getOutboundHttpToken());
		exchange.getIn().setHeader(Exchange.HTTP_URI, msgFromMfContext.getOutboundHttpEP());
		exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.valueOf(msgFromMfContext.getOutboundHttpMethod()));
		exchange.getIn().setBody(msgFromMfContext.getScriptReturnValue());
	}
}
