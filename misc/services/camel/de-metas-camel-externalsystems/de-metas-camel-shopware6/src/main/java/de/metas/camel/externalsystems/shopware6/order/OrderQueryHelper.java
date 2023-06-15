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

package de.metas.camel.externalsystems.shopware6.order.query;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.shopware6.api.model.JsonQuery;
import de.metas.camel.externalsystems.shopware6.api.model.MultiQueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.OperatorType;
import de.metas.camel.externalsystems.shopware6.api.model.QueryType;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_ORDER_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_ORDER_NUMBER;
import static de.metas.camel.externalsystems.shopware6.api.model.QueryHelper.buildEqualsJsonQuery;
import static de.metas.camel.externalsystems.shopware6.api.model.QueryHelper.buildUpdatedAfterJsonQueries;

@UtilityClass
public class OrderQueryHelper
{
	@NonNull
	@VisibleForTesting
	public static MultiQueryRequest buildUpdatedAfterQueryRequest(@NonNull final String updatedAfter, @NonNull final PageAndLimit pageAndLimitValues)
	{
		return MultiQueryRequest.builder()
				.filter(buildUpdatedAfterJsonQueries(updatedAfter))
				.limit(pageAndLimitValues.getLimit())
				.page(pageAndLimitValues.getPageIndex())
				.build();
	}

	@NonNull
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
					.build();
		}
		else
		{
			final String updatedAfterOverride = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE);

			final String updatedAfter = CoalesceUtil.coalesceNotNull(
					updatedAfterOverride,
					request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER),
					Instant.ofEpochSecond(0).toString());

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
}
