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
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

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
	/**
	 * The {@code M_HU_ID} of the underlying {@link de.metas.handlingunits.model.I_M_HU}-record.
	 * Note that in case of aggregated HUs one {@link HU} can have multiple child-HUs with the same id.
	 * That's because logically, the aggregated  {@link de.metas.handlingunits.model.I_M_HU} represents not one but {@code n} HUs.
	 */
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

	/**
	 * Also see the remark at {@link #id}.
	 */
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

	/**
	 * If this HU and all children have the same LotNo - empties are ignored! - that String is returned.
	 */
	@Nullable
	public String extractSingleLotNumber()
	{
		final Function<IAttributeSet, String> attrValueFunction = attrSet -> attrSet.getValueAsStringOrNull(AttributeConstants.ATTR_LotNumber);
		final HashSet<String> lotNumbers = new HashSet<>();
		for (final HU hu : allHUAsList())
		{
			final String lotNo = attrValueFunction.apply(hu.getAttributes());
			if (Check.isNotBlank(lotNo))
			{
				lotNumbers.add(lotNo);
			}
		}
		return CollectionUtils.singleElementOrNull(lotNumbers);
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
	 * Creates a "sparse" HU that only contains child-HUs, quantities and weights (if any!) for the given {@code reference}.
	 * ! Note that the HU's attribute-sets are <b>not</b> reduced by this method.
	 */
	@Nullable
	public HU retainReference(@NonNull final TableRecordReference reference)
	{
		return retainReference(reference, ImmutableList.of());
	}

	/**
	 * @param referencingModelsOfParentHU if an HU has no own ReferencingModels, then assume these
	 */
	@Nullable
	private HU retainReference(@NonNull final TableRecordReference reference,
							   @NonNull final ImmutableList<TableRecordReference> referencingModelsOfParentHU)
	{
		// If the current HU has no referencing models at all, we assume the parent's models.
		final ImmutableList<TableRecordReference> effectiveReferencingModels = getReferencingModels().isEmpty() ? referencingModelsOfParentHU : getReferencingModels();

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
			if (!effectiveReferencingModels.contains(reference))
			{
				return null;
			}

			// If this leaf didn't have any own referencing models, keep it like that.
			// If it contains the given 'reference', then make sure we retain only that one
			if (!getReferencingModels().isEmpty())
			{
				result.clearReferencingModels().referencingModel(reference);
			}
			newWeightNet = weightNet;
		}

		final HashMap<ProductId, Quantity> newProductQtysInStockUOM = new HashMap<>();
		final LinkedHashSet<TableRecordReference> newReferencingModels = new LinkedHashSet<>();

		for (final HU child : getChildHUs())
		{
			final HU retainedChild = child.retainReference(reference, effectiveReferencingModels);
			if (retainedChild != null)
			{
				result.childHU(retainedChild);
				newReferencingModels.addAll(retainedChild.getReferencingModels());

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
				.referencingModels(newReferencingModels)
				.weightNet(newWeightNet);

		final HU resultingHU = result.build();
		if (hasChildHUs && resultingHU.getChildHUs().isEmpty())
		{
			return null; // none of the child-HUs matched our predicate
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
