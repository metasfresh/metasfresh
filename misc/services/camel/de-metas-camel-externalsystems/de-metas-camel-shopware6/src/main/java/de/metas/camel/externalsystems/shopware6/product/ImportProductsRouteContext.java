/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.product;

import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.product.JsonProduct;
import de.metas.camel.externalsystems.shopware6.currency.CurrencyInfoProvider;
import de.metas.camel.externalsystems.shopware6.tax.TaxCategoryProvider;
import de.metas.camel.externalsystems.shopware6.unit.UOMInfoProvider;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonUOM;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
import java.time.Instant;

@Data
@Builder
public class ImportProductsRouteContext
{
	@NonNull
	private final ShopwareClient shopwareClient;

	@NonNull
	private final JsonExternalSystemRequest externalSystemRequest;

	@NonNull
	private final String orgCode;

	@NonNull
	private final UOMInfoProvider shopwareUomInfoProvider;

	@NonNull
	private final CurrencyInfoProvider currencyInfoProvider;

	@NonNull
	private final ImmutableMap<String, JsonUOM> uomMappings;

	@NonNull
	private final TaxCategoryProvider taxCategoryProvider;

	@Nullable
	private final PriceListBasicInfo priceListBasicInfo;

	@NonNull
	@Setter(AccessLevel.NONE)
	private Instant nextImportStartingTimestamp;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonProduct jsonProduct;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonProduct parentJsonProduct;


	public void setNextImportStartingTimestamp(@NonNull final Instant candidate)
	{
		if (candidate.isAfter(nextImportStartingTimestamp))
		{
			nextImportStartingTimestamp = candidate;
		}
	}

	@Nullable
	public Integer getPInstanceId()
	{
		return JsonMetasfreshId.toValue(externalSystemRequest.getAdPInstanceId());
	}

	@NonNull
	public JsonProduct getJsonProduct()
	{
		if (jsonProduct == null)
		{
			throw new RuntimeException("getJsonProduct() called before assigning a product!");
		}

		return jsonProduct;
	}

	@Nullable
	public JsonProduct getParentJsonProduct()
	{
		return parentJsonProduct;
	}
}