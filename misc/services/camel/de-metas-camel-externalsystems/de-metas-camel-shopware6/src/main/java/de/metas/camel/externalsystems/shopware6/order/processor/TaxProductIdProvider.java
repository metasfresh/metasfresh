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

package de.metas.camel.externalsystems.shopware6.order.processor;

import com.google.common.collect.ImmutableMap;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Value
public class TaxProductIdProvider
{
	@NonNull
	@Getter(AccessLevel.NONE)
	ImmutableMap<JsonMetasfreshId, List<BigDecimal>> productId2VatRates;

	public static TaxProductIdProvider of(@NonNull final ImmutableMap<JsonMetasfreshId, List<BigDecimal>> productId2VatRates)
	{
		return new TaxProductIdProvider(productId2VatRates);
	}

	@NonNull
	public JsonMetasfreshId getProductIdByVatRate(@NonNull final BigDecimal vatRateCandidate)
	{
		return productId2VatRates.entrySet().stream()
				.filter(entry -> entry.getValue().stream().anyMatch(vatRate -> vatRate.equals(vatRateCandidate)))
				.findFirst()
				.map(Map.Entry::getKey)
				.orElseThrow(() -> new RuntimeException("No productId was found for the given VAT rate! vatRate =" + vatRateCandidate));
	}
}
