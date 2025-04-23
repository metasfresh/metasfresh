package de.metas.handlingunits.generichumodel;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

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
	OrgId orgId;

	@NonNull
	HUType type;

	@Nullable
	PackagingCode packagingCode;

	@NonNull
	@Singular
	Map<BPartnerId, String> packagingGTINs;

	@NonNull
	@Singular("productQtyInStockUOM")
	ImmutableMap<ProductId, Quantity> productQtysInStockUOM;

	@Nullable
	Quantity weightNet;

	@NonNull
	IAttributeSet attributes;

	@NonNull
	@Singular("referencingModel")
	ImmutableList<TableRecordReference> referencingModels;

	@NonNull
	@Singular("childHU")
	ImmutableList<HU> childHUs;

	@Nullable
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

		results.add(hu);

		for (final HU child : hu.getChildHUs())
		{
			final List<HU> recurseNext = recurseNext(child);
			results.addAll(recurseNext);
		}
		return results.build();
	}

	/**
	 * Creates a "sparse" HU that only contains child-HUs, quantities and weights (if any!) for the given {@code productId}.
	 */
	@Nullable
	public HU retainReference(@NonNull final TableRecordReference reference)
	{
		final Predicate<HU> prediate = hu -> hu.getReferencingModels().contains(reference);
		final HUBuilder result = this.toBuilder();

		final boolean hasChildHUs = !getChildHUs().isEmpty();

		Quantity newWeightNet;
		if (hasChildHUs)
		{
			// we will rebuild this from the child-HUs
			result.clearProductQtysInStockUOM()
					.clearReferencingModels()
					.clearChildHUs();
			newWeightNet = toZeroOrNull(weightNet);
		}
		else
		{   // we are a leaf
			if (!prediate.test(this))
			{
				return null;
			}
			
			result.clearReferencingModels()
					.referencingModel(reference);
			newWeightNet = weightNet;
		}

		final HashMap<ProductId, Quantity> newProductQtysInStockUOM = new HashMap<>();

		for (final HU child : getChildHUs())
		{
			final HU retainedChild = child.retainReference(reference);
			if (retainedChild != null)
			{
				result.childHU(retainedChild);
				result.referencingModels(retainedChild.getReferencingModels());

				retainedChild.getProductQtysInStockUOM().forEach(
						(productId, quantity) -> newProductQtysInStockUOM.merge(productId, quantity, Quantity::add));

				if (newWeightNet == null && retainedChild.getWeightNet() != null)
				{
					newWeightNet = retainedChild.getWeightNet();
				}
				else if (newWeightNet != null && retainedChild.getWeightNet() != null)
				{
					newWeightNet = Quantitys.add(null, newWeightNet, retainedChild.getWeightNet());
				}
			}
		}
		result.productQtysInStockUOM(newProductQtysInStockUOM)
				.weightNet(newWeightNet);

		final HU resultingHU = result.build();
		if (hasChildHUs && resultingHU.getChildHUs().isEmpty())
		{
			return null; // none of the child-HUs match our predicate
		}
		return resultingHU;
	}

	@Nullable
	private Quantity toZeroOrNull(@Nullable final Quantity weightNet)
	{
		return weightNet != null ? weightNet.toZero() : null;
	}

	/**
	 * Iterates all child-HUs' storage quantities for the given productId and returns the median (or something close).
	 * Goal: return a reasonably common quantity, and ignore possible outliers.
	 * <p>
	 * If this HU has no children, then return this HU's quantity!
	 */
	public Quantity extractMedianCUQtyPerChildHU(@NonNull final ProductId productId)
	{
		if (getChildHUs().isEmpty())
		{
			return getProductQtysInStockUOM().get(productId);
		}

		final ImmutableList<BigDecimal> allQuantities = this.getChildHUs()
				.stream()
				.map(hu -> hu.getProductQtysInStockUOM().get(productId).toBigDecimal())
				.sorted()
				.collect(ImmutableList.toImmutableList());

		final BigDecimal qtyCU = allQuantities.get(allQuantities.size() / 2);
		return Quantitys.of(qtyCU, productId);
	}

	@Nullable
	public String getPackagingGTIN(@NonNull final BPartnerId bpartnerId)
	{
		final String gtin = packagingGTINs.get(bpartnerId);
		if (Check.isNotBlank(gtin))
		{
			return gtin;
		}
		return packagingGTINs.get(BPartnerId.NONE);
	}

	@VisibleForTesting
	public ImmutableMap<BPartnerId, String> getAllPackaginGTINs()
	{
		return ImmutableMap.copyOf(packagingGTINs);
	}
}
