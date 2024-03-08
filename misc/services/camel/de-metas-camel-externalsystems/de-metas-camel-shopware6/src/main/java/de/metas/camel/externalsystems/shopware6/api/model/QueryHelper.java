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
import de.metas.camel.externalsystems.shopware6.order.query.PageAndLimit;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.HashMap;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_PRODUCT_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_CREATED_AT;
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
	public static MultiQueryRequest buildShopware6GetCustomersQueryRequest(@NonNull final String updatedAfter, final PageAndLimit pageAndLimitValues)
	{
		return MultiQueryRequest.builder()
				.filter(buildUpdatedAfterJsonQueries(updatedAfter))
				.page(pageAndLimitValues.getPageIndex())
				.limit(pageAndLimitValues.getLimit())
				.build();
	}

	@NonNull
	public static MultiQueryRequest buildQueryParentProductRequest(@NonNull final String parentId)
	{
		return MultiQueryRequest.builder()
				.filter(JsonQuery.builder()
								.field(FIELD_PRODUCT_ID)
								.queryType(QueryType.EQUALS)
								.value(parentId)
								.build())
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
