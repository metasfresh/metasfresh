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

package de.metas.camel.externalsystems.shopware6.customer;

import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroup;
import de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteHelper;
import de.metas.camel.externalsystems.shopware6.product.PriceListBasicInfo;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMapping;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMappings;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Comparator;

@Data
public class ImportCustomersRouteContext
{
	@NonNull
	private final String orgCode;

	@NonNull
	private final ShopwareClient shopwareClient;

	@NonNull
	private final JsonExternalSystemRequest externalSystemRequest;

	@NonNull
	private final String updatedAfter;

	@Nullable
	private final PriceListBasicInfo priceListBasicInfo;

	private boolean skipNextImportTimestamp;

	@Nullable
	private final String shopwareIdJsonPath;

	@Nullable
	private final String metasfreshIdJsonPath;

	@Builder
	public ImportCustomersRouteContext(
			@NonNull final String orgCode,
			@NonNull final ShopwareClient shopwareClient,
			@NonNull final JsonExternalSystemRequest request,
			@NonNull final String updatedAfter,
			@Nullable final PriceListBasicInfo priceListBasicInfo,
			final boolean skipNextImportTimestamp)
	{
		this.orgCode = orgCode;
		this.shopwareClient = shopwareClient;
		this.externalSystemRequest = request;
		this.updatedAfter = updatedAfter;
		this.priceListBasicInfo = priceListBasicInfo;
		this.skipNextImportTimestamp = skipNextImportTimestamp;

		this.shopwareIdJsonPath = request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_SHOPWARE_ID);
		this.metasfreshIdJsonPath = request.getParameters().get(ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_METASFRESH_ID);
	}

	@Nullable
	public JsonExternalSystemShopware6ConfigMapping getMatchingShopware6Mapping(@Nullable final JsonCustomerGroup jsonCustomerGroup)
	{
		final JsonExternalSystemShopware6ConfigMappings shopware6ConfigMappingsComputed = GetOrdersRouteHelper
				.getSalesOrderMappingRules(externalSystemRequest)
				.orElse(null);

		if (shopware6ConfigMappingsComputed == null
				|| Check.isEmpty(shopware6ConfigMappingsComputed.getJsonExternalSystemShopware6ConfigMappingList())
				|| jsonCustomerGroup == null)
		{
			return null;
		}

		return shopware6ConfigMappingsComputed.getJsonExternalSystemShopware6ConfigMappingList()
				.stream()
				.filter(mapping -> mapping.isGroupMatching(jsonCustomerGroup.getName()))
				.min(Comparator.comparingInt(JsonExternalSystemShopware6ConfigMapping::getSeqNo))
				.orElse(null);
	}
}
