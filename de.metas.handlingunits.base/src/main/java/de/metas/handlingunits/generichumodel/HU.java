package de.metas.handlingunits.generichumodel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.util.lang.Mutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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
@Builder(toBuilder = true)
public class HU
{
	@NonNull
	HuId id;

	@NonNull
	HUType type;

	@NonNull
	Optional<PackagingCode> packagingCode;

	@NonNull
	@Singular
	ImmutableMap<ProductId, Quantity> productQuantities;

	@NonNull
	IAttributeSet attributes;

	@NonNull
	@Singular("childHU")
	ImmutableList<HU> childHUs;

	public <T> T extractSingleAttributeValue(
			@NonNull final Function<IAttributeSet, T> attrValueFunction,
			@NonNull final BinaryOperator<T> mergeFunction)
	{
		final Mutable<T> result = new Mutable<>();
		for (final HU hu : allHUAsList())
		{
			final T attrValue = attrValueFunction.apply(hu.getAttributes());
			result.setValue(mergeFunction.apply(result.getValue(), attrValue));
		}
		return result.getValue();
	}

	public List<HU> allHUAsList()
	{
		return recurseNext(this);
	}

	private List<HU> recurseNext(@NonNull final HU hu)
	{
		if (hu.getChildHUs().isEmpty())
		{
			return ImmutableList.of(hu);
		}

		final ImmutableList.Builder<HU> results = ImmutableList.builder();
		for (final HU child : hu.getChildHUs())
		{
			final List<HU> recurseNext = recurseNext(child);
			results.addAll(recurseNext);
		}
		return results.build();
	}

	public Optional<HU> retainProduct(@NonNull final ProductId productId)
	{
		if (!this.getProductQuantities().containsKey(productId))
		{
			return Optional.empty(); // we know that the M_HU datamodel is such that if we don't have a product here, the children won't have it either.
		}

		final Quantity quantity = this.getProductQuantities().get(productId);
		final HUBuilder result = this.toBuilder()
				.clearProductQuantities()
				.clearChildHUs()
				.productQuantity(productId, quantity);

		for (final HU child : getChildHUs())
		{
			child.retainProduct(productId).ifPresent(result::childHU);
		}
		return Optional.of(result.build());
	}

	/**
	 * Iterates all child-HUs' storage quantities for the given productId and returns the median (or something close).
	 * Goal: return a reasonably common quantity, and ignore possible outliers.
	 */
	public Quantity extractMedianCUQtyPerChildHU(@NonNull final ProductId productId)
	{
		final ImmutableList<BigDecimal> allQuantities = this.getChildHUs()
				.stream()
				.map(hu -> hu.getProductQuantities().get(productId).toBigDecimal())
				.sorted()
				.collect(ImmutableList.toImmutableList());

		final BigDecimal qtyCU = allQuantities.get(allQuantities.size() / 2);
		return Quantitys.create(qtyCU, productId);
	}
}
