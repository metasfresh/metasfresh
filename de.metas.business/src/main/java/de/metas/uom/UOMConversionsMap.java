package de.metas.uom;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.NoUOMConversionException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

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

	private final ProductId productId;
	@Getter(AccessLevel.NONE)
	private final ImmutableMap<FromAndToUomIds, UOMConversion> conversions;

	@Builder
	private UOMConversionsMap(
			@Nullable final ProductId productId,
			@NonNull final List<UOMConversion> conversions)
	{
		this.productId = productId;
		this.conversions = Maps.uniqueIndex(conversions, conversion -> toFromAndToUomIds(conversion));
	}

	private UOMConversionsMap()
	{
		productId = null;
		conversions = ImmutableMap.of();
	}

	public BigDecimal getRate(@NonNull final UomId fromUomId, @NonNull final UomId toUomId)
	{
		final BigDecimal rate = getRateOrNull(fromUomId, toUomId);
		if (rate == null)
		{
			throw new NoUOMConversionException(productId, fromUomId, toUomId);
		}
		return rate;
	}

	public Optional<BigDecimal> getRateIfExists(@NonNull final UomId fromUomId, @NonNull final UomId toUomId)
	{
		final BigDecimal rate = getRateOrNull(fromUomId, toUomId);
		return Optional.ofNullable(rate);
	}

	private BigDecimal getRateOrNull(@NonNull final UomId fromUomId, @NonNull final UomId toUomId)
	{
		if (fromUomId.equals(toUomId))
		{
			return BigDecimal.ONE;
		}

		final FromAndToUomIds key = FromAndToUomIds.builder()
				.fromUomId(fromUomId)
				.toUomId(toUomId)
				.build();

		final UOMConversion directConversion = conversions.get(key);
		if (directConversion != null)
		{
			return directConversion.getMultiplyRate();
		}

		final UOMConversion invertedConversion = conversions.get(key.invert());
		if (invertedConversion != null)
		{
			return invertedConversion.getDivideRate();
		}

		return null;
	}

	public boolean isEmpty()
	{
		return conversions.isEmpty();
	}

	private static FromAndToUomIds toFromAndToUomIds(final UOMConversion conversion)
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
