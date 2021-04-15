/*
 * #%L
 * de-metas-camel-alberta-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.alberta.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.alberta.product.processor.AlbertaProductApi;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.GetProductsCamelRequest;
import de.metas.common.externalreference.JsonRequestExternalReferenceUpsert;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import io.swagger.client.model.Article;
import io.swagger.client.model.ArticleMapping;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ORG_CODE;
import static de.metas.camel.alberta.product.PushProductsRoute.PREPARE_ARTICLE_PROCESSOR_ID;
import static de.metas.camel.alberta.product.PushProductsRoute.PROCESS_PRODUCT_ROUTE_ID;
import static de.metas.camel.alberta.product.PushProductsRoute.PUSH_ARTICLE_PROCESSOR_ID;
import static de.metas.camel.alberta.product.PushProductsRoute.PUSH_PRODUCTS;
import static de.metas.camel.alberta.product.PushProductsRoute.RETRIEVE_PRODUCTS_PROCESSOR_ID;
import static de.metas.camel.alberta.product.PushProductsRouteConstants.ROUTE_PROPERTY_ALBERTA_PRODUCT_API;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_GET_PRODUCTS_ROUTE_ID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class PushProductsRouteTests extends CamelTestSupport
{
	private static final String MOCK_GET_PRODUCTS_REQUEST = "mock:getProductsRequest";
	private static final String MOCK_UPSERT_ARTICLE_REQUEST = "mock:upsertArticleRequest";
	private static final String MOCK_REPORT_ARTICLE_ID_IN_METASFRESH = "mock:reportArticleIdInMF";

	private static final String JSON_ARTICLE_MAPPINGS = "/de/metas/camel/alberta/product/ArticleMappings.json";
	private static final String JSON_MF_GET_PRODUCTS = "/de/metas/camel/alberta/product/GetProductsMetasfreshResponse.json";

	private static final String JSON_ALBERTA_ARTICLE = "/de/metas/camel/alberta/product/UpsertArticleRequest.json";
	private static final String JSON_INVOKE_PUSH_PRODUCTS_REQUEST = "/de/metas/camel/alberta/product/JsonExternalSystemRequest.json";
	private static final String JSON_MF_GET_PRODUCTS_REQUEST = "/de/metas/camel/alberta/product/GetProductsCamelRequest.json";
	private static final String JSON_MF_UPSERT_EXTERNAL_REF_REQUEST = "/de/metas/camel/alberta/product/JsonRequestExternalReferenceUpsert.json";

	private static final String MOCK_ORG_CODE = "orgCode";

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(PushProductsRouteTests.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new PushProductsRoute();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void happyFlow() throws Exception
	{
		final MockSuccessfullyCalledEndpoint sentExternalReferenceRequestEP = new MockSuccessfullyCalledEndpoint();
		final MockSuccessfullyCalledEndpoint logMessageEndpoint = new MockSuccessfullyCalledEndpoint();

		prepareRouteForTesting(sentExternalReferenceRequestEP, logMessageEndpoint);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		//validate get products request
		final InputStream getProductsRequest = this.getClass().getResourceAsStream(JSON_MF_GET_PRODUCTS_REQUEST);

		final MockEndpoint getProductsRequestMockEP = getMockEndpoint(MOCK_GET_PRODUCTS_REQUEST);
		getProductsRequestMockEP.expectedBodiesReceived(objectMapper.readValue(getProductsRequest, GetProductsCamelRequest.class));

		//validate jsonProduct to Alberta article transition
		final InputStream albertaArticleIS = this.getClass().getResourceAsStream(JSON_ALBERTA_ARTICLE);

		final MockEndpoint upsertArticleRequestMockEP = getMockEndpoint(MOCK_UPSERT_ARTICLE_REQUEST);
		upsertArticleRequestMockEP.expectedBodiesReceived(objectMapper.readValue(albertaArticleIS, UpsertArticleRequest.class));

		//validate the external reference upsert request
		final InputStream externalRefUpsertRequest = this.getClass().getResourceAsStream(JSON_MF_UPSERT_EXTERNAL_REF_REQUEST);

		final MockEndpoint externalRefUpsertRequestMockEndpoint = getMockEndpoint(MOCK_REPORT_ARTICLE_ID_IN_METASFRESH);
		externalRefUpsertRequestMockEndpoint.expectedBodiesReceived(objectMapper.readValue(externalRefUpsertRequest, JsonRequestExternalReferenceUpsert.class));

		//fire the route
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_INVOKE_PUSH_PRODUCTS_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		template.sendBody("direct:" + PUSH_PRODUCTS, invokeExternalSystemRequest);

		assertThat(sentExternalReferenceRequestEP.called).isEqualTo(1);
		assertThat(logMessageEndpoint.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			final MockSuccessfullyCalledEndpoint successfullySentExternalReferenceRequest,
			final MockSuccessfullyCalledEndpoint successfullyCalledLogMessageEndpoint) throws Exception
	{
		AdviceWith.adviceWith(context, PUSH_PRODUCTS,
										  advice -> {
											  advice.weaveById(RETRIEVE_PRODUCTS_PROCESSOR_ID)
													  .after()
													  .to(MOCK_GET_PRODUCTS_REQUEST);

											  advice.interceptSendToEndpoint("direct:" + MF_GET_PRODUCTS_ROUTE_ID)
													  .skipSendToOriginalEndpoint()
													  .process(new MockGetProductsFromMetasfreshProcessor());
										  });

		AdviceWith.adviceWith(context, PROCESS_PRODUCT_ROUTE_ID,
										  advice -> {
											  advice.weaveById(PREPARE_ARTICLE_PROCESSOR_ID)
													  .after()
													  .to(MOCK_UPSERT_ARTICLE_REQUEST);

											  advice.weaveById(PUSH_ARTICLE_PROCESSOR_ID)
													  .after()
													  .to(MOCK_REPORT_ARTICLE_ID_IN_METASFRESH);

											  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_EXTERNALREFERENCE_CAMEL_URI + "}}")
													  .skipSendToOriginalEndpoint()
													  .process(successfullySentExternalReferenceRequest);

											  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_LOG_MESSAGE_ROUTE_ID)
													  .skipSendToOriginalEndpoint()
													  .process(successfullyCalledLogMessageEndpoint);
										  });
	}

	private static class MockSuccessfullyCalledEndpoint implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockGetProductsFromMetasfreshProcessor implements Processor
	{
		@Override
		public void process(final Exchange exchange) throws IOException
		{
			// mock addArticle & updateArticle response
			final ObjectMapper mapper = new ObjectMapper();
			final InputStream jsonGetProductsResponse = PushProductsRouteTests.class.getResourceAsStream(JSON_MF_GET_PRODUCTS);

			// mock shopware client
			final AlbertaProductApi albertaProductApi = prepareAlbertaProductAPIClient(mapper);

			//set up the exchange
			exchange.getIn().setBody(jsonGetProductsResponse);
			exchange.setProperty(ROUTE_PROPERTY_ORG_CODE, MOCK_ORG_CODE);
			exchange.setProperty(ROUTE_PROPERTY_ALBERTA_PRODUCT_API, albertaProductApi);
		}

		@NonNull
		private AlbertaProductApi prepareAlbertaProductAPIClient(final ObjectMapper mapper) throws IOException
		{
			final AlbertaProductApi albertaProductApi = Mockito.mock(AlbertaProductApi.class);

			//1. mock updateProductFallbackAdd
			final InputStream mappingsIS = PushProductsRouteTests.class.getResourceAsStream(JSON_ARTICLE_MAPPINGS);
			final ArticleMapping articleMapping = mapper.readValue(mappingsIS, ArticleMapping.class);

			Mockito.when(albertaProductApi.updateProductFallbackAdd(any(Article.class)))
					.thenReturn(articleMapping);

			//2. mock addProductFallbackUpdate
			Mockito.when(albertaProductApi.addProductFallbackUpdate(any(Article.class)))
					.thenReturn(articleMapping);

			return albertaProductApi;
		}
	}
}
