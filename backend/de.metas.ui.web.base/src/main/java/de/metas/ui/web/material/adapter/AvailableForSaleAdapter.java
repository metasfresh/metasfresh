/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.material.adapter;

import com.google.common.annotations.VisibleForTesting;
import de.metas.material.cockpit.availableforsales.AvailableForSalesLookupBucketResult;
import de.metas.material.cockpit.availableforsales.AvailableForSalesLookupResult;
import de.metas.material.cockpit.availableforsales.AvailableForSalesMultiQuery;
import de.metas.material.cockpit.availableforsales.AvailableForSalesRepository;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.ui.web.material.adapter.AvailabilityInfoResultForWebui.AvailabilityInfoResultForWebuiBuilder;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class AvailableForSaleAdapter
{
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final AvailableForSalesRepository availableForSalesRepository;

	public AvailableForSaleAdapter(@NonNull final AvailableForSalesRepository availableForSalesRepository)
	{
		this.availableForSalesRepository = availableForSalesRepository;
	}

	@VisibleForTesting
	static AvailabilityInfoResultForWebui.Group.Type extractGroupType(@NonNull final AttributesKeyPattern attributesKey)
	{
		if (AttributesKeyPattern.ALL.equals(attributesKey))
		{
			return AvailabilityInfoResultForWebui.Group.Type.ALL_STORAGE_KEYS;
		}
		else if (AttributesKeyPattern.OTHER.equals(attributesKey))
		{
			return AvailabilityInfoResultForWebui.Group.Type.OTHER_STORAGE_KEYS;
		}
		else
		{
			return AvailabilityInfoResultForWebui.Group.Type.ATTRIBUTE_SET;
		}
	}

	@NonNull
	public AvailabilityInfoResultForWebui retrieveAvailableStock(@NonNull final AvailableForSalesMultiQuery query)
	{
		final AvailableForSalesLookupResult commonsAvailableStock = availableForSalesRepository.retrieveAvailableStock(query);

		final AvailabilityInfoResultForWebuiBuilder clientResultBuilder = AvailabilityInfoResultForWebui.builder();

		for (final AvailableForSalesLookupBucketResult commonsResultGroup : commonsAvailableStock.getAvailableForSalesResults())
		{
			final AvailabilityInfoResultForWebui.Group clientResultGroup = createClientResultGroup(commonsResultGroup);
			clientResultBuilder.group(clientResultGroup);
		}
		return clientResultBuilder.build();
	}

	private AvailabilityInfoResultForWebui.Group createClientResultGroup(@NonNull final AvailableForSalesLookupBucketResult commonsResultGroup)
	{
		try
		{
			return createClientResultGroup0(commonsResultGroup);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
					.setParameter("commonsResultGroup", commonsResultGroup);
		}
	}

	private AvailabilityInfoResultForWebui.Group createClientResultGroup0(final AvailableForSalesLookupBucketResult commonsResultGroup)
	{
		final Quantity quantity = extractQuantity(commonsResultGroup);
		final AttributesKeyPattern attributesKey = AttributesKeyPatternsUtil.ofAttributeKey(commonsResultGroup.getStorageAttributesKey());
		final AvailabilityInfoResultForWebui.Group.Type type = extractGroupType(attributesKey);

		final ImmutableAttributeSet attributes = AvailabilityInfoResultForWebui.Group.Type.ATTRIBUTE_SET.equals(type)
				? AttributesKeys.toImmutableAttributeSet(commonsResultGroup.getStorageAttributesKey())
				: ImmutableAttributeSet.EMPTY;

		return AvailabilityInfoResultForWebui.Group.builder()
				.productId(commonsResultGroup.getProductId())
				.qty(quantity)
				.type(type)
				.attributes(attributes)
				.build();
	}

	private Quantity extractQuantity(final AvailableForSalesLookupBucketResult commonsResultGroup)
	{
		final AvailableForSalesLookupBucketResult.Quantities quantities = commonsResultGroup.getQuantities();
		final BigDecimal qty = quantities.getQtyOnHandStock().subtract(quantities.getQtyToBeShipped());
		final I_C_UOM uom = productsService.getStockUOM(commonsResultGroup.getProductId());
		return Quantity.of(qty, uom);
	}

	public Set<AttributesKeyPattern> getPredefinedStorageAttributeKeys()
	{
		return availableForSalesRepository.getPredefinedStorageAttributeKeys();
	}
}
