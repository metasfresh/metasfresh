/*
 * #%L
 * de-metas-camel-shopware6
 * %%
<<<<<<< HEAD
 * Copyright (C) 2021 metas GmbH
=======
 * Copyright (C) 2022 metas GmbH
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
package de.metas.camel.externalsystems.shopware6.order;
=======
package de.metas.camel.externalsystems.shopware6.order.query;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.shopware6.api.model.JsonQuery;
<<<<<<< HEAD
import de.metas.camel.externalsystems.shopware6.api.model.MultiJsonFilter;
import de.metas.camel.externalsystems.shopware6.api.model.MultiQueryRequest;
=======
import de.metas.camel.externalsystems.shopware6.api.model.MultiQueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.OperatorType;
import de.metas.camel.externalsystems.shopware6.api.model.QueryType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Instant;
<<<<<<< HEAD
import java.util.HashMap;
import java.util.List;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_CREATED_AT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_ORDER_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_ORDER_NUMBER;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_UPDATED_AT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.PARAMETERS_DATE_GTE;
=======
import java.util.List;
import java.util.stream.Stream;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_ORDER_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_ORDER_NUMBER;
import static de.metas.camel.externalsystems.shopware6.api.model.QueryHelper.buildEqualsJsonQuery;
import static de.metas.camel.externalsystems.shopware6.api.model.QueryHelper.buildUpdatedAfterJsonQueries;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

@UtilityClass
public class OrderQueryHelper
{
	@NonNull
	@VisibleForTesting
<<<<<<< HEAD
	public static JsonQuery buildEqualsJsonQuery(@NonNull final String key, @NonNull final String value)
	{
		return JsonQuery.builder()
				.field(key)
				.queryType(JsonQuery.QueryType.EQUALS)
				.value(value)
				.build();
	}

	@NonNull
	@VisibleForTesting
	public static MultiQueryRequest buildUpdatedAfterQueryRequest(@NonNull final String updatedAfter)
	{
		final HashMap<String, String> parameters = new HashMap<>();
		parameters.put(PARAMETERS_DATE_GTE, updatedAfter);

		return MultiQueryRequest.builder()
				.isQueryByDate(Boolean.TRUE)
				.filter(MultiJsonFilter.builder()
								.operatorType(MultiJsonFilter.OperatorType.OR)
								.jsonQuery(JsonQuery.builder()
												   .field(FIELD_UPDATED_AT)
												   .queryType(JsonQuery.QueryType.RANGE)
												   .parameters(parameters)
												   .build())
								.jsonQuery(JsonQuery.builder()
												   .field(FIELD_CREATED_AT)
												   .queryType(JsonQuery.QueryType.RANGE)
												   .parameters(parameters)
												   .build())
								.build())
=======
	public static MultiQueryRequest buildUpdatedAfterQueryRequest(@NonNull final String updatedAfter, @NonNull final PageAndLimit pageAndLimitValues)
	{
		return MultiQueryRequest.builder()
				.filter(buildUpdatedAfterJsonQueries(updatedAfter))
				.limit(pageAndLimitValues.getLimit())
				.page(pageAndLimitValues.getPageIndex())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.build();
	}

	@NonNull
<<<<<<< HEAD
	@VisibleForTesting
	public ImportOrdersRequest buildShopware6QueryRequest(@NonNull final JsonExternalSystemRequest request)
	{
		final ImmutableList.Builder<JsonQuery> jsonQueries = ImmutableList.builder();

		final String orderId = request.getParameters().get(ExternalSystemConstants.PARAM_ORDER_ID);
		if (Check.isNotBlank(orderId))
		{
			jsonQueries.add(buildEqualsJsonQuery(FIELD_ORDER_ID, orderId));
		}

		final String orderNo = request.getParameters().get(ExternalSystemConstants.PARAM_ORDER_NO);
		if (Check.isNotBlank(orderNo))
		{
			jsonQueries.add(buildEqualsJsonQuery(FIELD_ORDER_NUMBER, orderNo));
		}

		final List<JsonQuery> queries = jsonQueries.build();

		if (!Check.isEmpty(queries))
		{
			final MultiQueryRequest multiQueryRequest = MultiQueryRequest.builder()
					.filter(MultiJsonFilter.builder()
									.operatorType(MultiJsonFilter.OperatorType.AND)
									.jsonQueries(queries)
									.build())
					.build();

			return ImportOrdersRequest.builder()
					.shopware6QueryRequest(multiQueryRequest)
					.ignoreNextImportTimestamp(true)
=======
	public MultiQueryRequest buildShopware6QueryRequest(@NonNull final JsonExternalSystemRequest request, @NonNull final PageAndLimit pageAndLimitValues)
	{
		final List<JsonQuery> queries = buildLookUpSpecificOrderQuery(request);

		if (!Check.isEmpty(queries))
		{
			return MultiQueryRequest.builder()
					.filter(JsonQuery.builder()
									.queryType(QueryType.MULTI)
									.operatorType(OperatorType.AND)
									.jsonQueries(queries)
									.build())
					.limit(pageAndLimitValues.getLimit())
					.page(pageAndLimitValues.getPageIndex())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					.build();
		}
		else
		{
			final String updatedAfterOverride = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE);

<<<<<<< HEAD
			final boolean ignoreNextImportTimestamp = updatedAfterOverride != null;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			final String updatedAfter = CoalesceUtil.coalesceNotNull(
					updatedAfterOverride,
					request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER),
					Instant.ofEpochSecond(0).toString());

<<<<<<< HEAD
			final MultiQueryRequest multiQueryRequest = buildUpdatedAfterQueryRequest(updatedAfter);

			return ImportOrdersRequest.builder()
					.shopware6QueryRequest(multiQueryRequest)
					.ignoreNextImportTimestamp(ignoreNextImportTimestamp)
					.build();
		}
	}
=======
			return buildUpdatedAfterQueryRequest(updatedAfter, pageAndLimitValues);
		}
	}

	public boolean shouldIgnoreNextImportTimestamp(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		final String orderId = externalSystemRequest.getParameters().get(ExternalSystemConstants.PARAM_ORDER_ID);
		final String orderNo = externalSystemRequest.getParameters().get(ExternalSystemConstants.PARAM_ORDER_NO);
		final String updatedAfterOverride = externalSystemRequest.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE);

		return Stream.of(orderId, orderNo, updatedAfterOverride)
				.anyMatch(Check::isNotBlank);
	}

	@NonNull
	private ImmutableList<JsonQuery> buildLookUpSpecificOrderQuery(@NonNull final JsonExternalSystemRequest request)
	{
		final ImmutableList.Builder<JsonQuery> getSpecificOrderQuery = ImmutableList.builder();

		final String orderId = request.getParameters().get(ExternalSystemConstants.PARAM_ORDER_ID);
		if (Check.isNotBlank(orderId))
		{
			getSpecificOrderQuery.add(buildEqualsJsonQuery(FIELD_ORDER_ID, orderId));
		}

		final String orderNo = request.getParameters().get(ExternalSystemConstants.PARAM_ORDER_NO);
		if (Check.isNotBlank(orderNo))
		{
			getSpecificOrderQuery.add(buildEqualsJsonQuery(FIELD_ORDER_NUMBER, orderNo));
		}

		return getSpecificOrderQuery.build();
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
