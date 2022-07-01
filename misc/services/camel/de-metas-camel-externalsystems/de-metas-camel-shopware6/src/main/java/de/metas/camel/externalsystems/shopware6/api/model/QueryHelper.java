/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.api.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRequest;
import de.metas.camel.externalsystems.shopware6.order.query.PageAndLimit;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_CREATED_AT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_ORDER_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_ORDER_NUMBER;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_UPDATED_AT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.PARAMETERS_GTE;

@UtilityClass
public class QueryHelper
{
	@NonNull
	@VisibleForTesting
	public static JsonQuery buildEqualsJsonQuery(@NonNull final String key, @NonNull final String value)
	{
		return JsonQuery.builder()
				.field(key)
				.queryType(QueryType.EQUALS)
				.value(value)
				.build();
	}

	@NonNull
	public static MultiQueryRequest buildUpdatedAfterFilterQueryRequest(@NonNull final String updatedAfter)
	{
		return MultiQueryRequest.builder()
				.filter(buildUpdatedAfterJsonQueries(updatedAfter))
				.build();
	}

	@NonNull
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
					.filter(JsonQuery.builder()
									.queryType(QueryType.MULTI)
									.operatorType(OperatorType.AND)
									.jsonQueries(queries)
									.build())
					.build();

			return ImportOrdersRequest.builder()
					.shopware6QueryRequest(multiQueryRequest)
					.ignoreNextImportTimestamp(true)
					.build();
		}
		else
		{
			final String updatedAfterOverride = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE);

			final boolean ignoreNextImportTimestamp = updatedAfterOverride != null;

			final String updatedAfter = CoalesceUtil.coalesceNotNull(
					updatedAfterOverride,
					request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER),
					Instant.ofEpochSecond(0).toString());

			final MultiQueryRequest multiQueryRequest = buildUpdatedAfterFilterQueryRequest(updatedAfter);

			return ImportOrdersRequest.builder()
					.shopware6QueryRequest(multiQueryRequest)
					.ignoreNextImportTimestamp(ignoreNextImportTimestamp)
					.build();
		}
	}

	@NonNull
	public static MultiQueryRequest buildShopware6GetCustomersQueryRequest(@NonNull final String updatedAfter, final PageAndLimit pageAndLimitValues)
	{
		return MultiQueryRequest.builder()
				.filter(buildUpdatedAfterJsonQueries(updatedAfter))
				.page(pageAndLimitValues.getPageIndex())
				.limit(pageAndLimitValues.getLimit())
				.build();
	}

	@NonNull
	@VisibleForTesting
	public static JsonQuery buildUpdatedAfterJsonQueries(@NonNull final String updatedAfter)
	{
		final HashMap<String, String> parameters = new HashMap<>();
		parameters.put(PARAMETERS_GTE, updatedAfter);

		return JsonQuery.builder()
				.queryType(QueryType.MULTI)
				.operatorType(OperatorType.OR)
				.jsonQuery(JsonQuery.builder()
								   .field(FIELD_UPDATED_AT)
								   .queryType(QueryType.RANGE)
								   .parameters(parameters)
								   .build())
				.jsonQuery(JsonQuery.builder()
								   .field(FIELD_CREATED_AT)
								   .queryType(QueryType.RANGE)
								   .parameters(parameters)
								   .build())
				.build();
	}
}
