package de.metas.uom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.common.util.CoalesceUtil;
import de.metas.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.NoUOMConversionException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class UOMConversionsMap
{
	public static final UOMConversionsMap EMPTY = new UOMConversionsMap();

	ProductId productId;
	
	@Getter(AccessLevel.NONE)
	ImmutableMap<FromAndToUomIds, UOMConversionRate> rates;

	/**
	 * {@code true} if this map is about a productId *and* contains at least one mapping besides the trivial times-one-mapping to and from the product's own stocking-UOM.
	 */
	boolean hasRatesForNonStockingUOMs;
	
	@Builder
	private UOMConversionsMap(
			@Nullable final ProductId productId,
			@Nullable final Boolean hasRatesForNonStockingUOMs,
			@NonNull @Singular final List<UOMConversionRate> rates)
	{
		this.productId = productId;
		this.hasRatesForNonStockingUOMs = CoalesceUtil.coalesceNotNull(hasRatesForNonStockingUOMs, false);
		this.rates = Maps.uniqueIndex(rates, UOMConversionsMap::toFromAndToUomIds);
	}

	private UOMConversionsMap()
	{
		productId = null;
		hasRatesForNonStockingUOMs = false;
		rates = ImmutableMap.of();
	}

	public UOMConversionRate getRate(@NonNull final UomId fromUomId, @NonNull final UomId toUomId)
	{
		final UOMConversionRate rate = getRateOrNull(fromUomId, toUomId);
		if (rate == null)
		{
			throw new NoUOMConversionException(productId, fromUomId, toUomId);
		}
		return rate;
	}

	public Optional<UOMConversionRate> getRateIfExists(@NonNull final UomId fromUomId, @NonNull final UomId toUomId)
	{
		return Optional.ofNullable(getRateOrNull(fromUomId, toUomId));
	}

	@Nullable
	private UOMConversionRate getRateOrNull(@NonNull final UomId fromUomId, @NonNull final UomId toUomId)
	{
		if (fromUomId.equals(toUomId))
		{
			return UOMConversionRate.one(fromUomId);
		}

		final FromAndToUomIds key = FromAndToUomIds.builder()
				.fromUomId(fromUomId)
				.toUomId(toUomId)
				.build();

		final UOMConversionRate directRate = rates.get(key);
		if (directRate != null)
		{
			return directRate;
		}

		final UOMConversionRate invertedRate = rates.get(key.invert());
		if (invertedRate != null)
		{
			return invertedRate.invert();
		}

		return null;
	}

	public boolean isEmpty()
	{
		return rates.isEmpty();
	}

	public ImmutableSet<UomId> getCatchUomIds()
	{
		if (rates.isEmpty())
		{
			return ImmutableSet.of();
		}
		
		return rates.values()
				.stream()
				.filter(UOMConversionRate::isCatchUOMForProduct)
				.map(UOMConversionRate::getToUomId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static FromAndToUomIds toFromAndToUomIds(final UOMConversionRate conversion)
	{
		return FromAndToUomIds.builder()
				.fromUomId(conversion.getFromUomId())
				.toUomId(conversion.getToUomId())
				.build();
	}

	@Value
	@Builder
	private static class FromAndToUomIds
	{
		@NonNull
		UomId fromUomId;
		@NonNull
		UomId toUomId;

		public FromAndToUomIds invert()
		{
			return builder().fromUomId(toUomId).toUomId(fromUomId).build();
		}
	}
}
