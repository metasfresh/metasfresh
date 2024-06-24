package de.metas.ui.web.material.adapter;

import com.google.common.annotations.VisibleForTesting;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseResult;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseResultGroup;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.ui.web.material.adapter.AvailabilityInfoResultForWebui.AvailabilityInfoResultForWebuiBuilder;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/*
 * #%L
 * metasfresh-material-dispo-client
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
public class AvailableToPromiseAdapter
{
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final AvailableToPromiseRepository availableToPromiseRepository;

	public AvailableToPromiseAdapter(@NonNull final AvailableToPromiseRepository stockRepository)
	{
		this.availableToPromiseRepository = stockRepository;
	}

	@VisibleForTesting
	static AvailabilityInfoResultForWebui.Group.Type extractGroupType(@NonNull final AttributesKey attributesKey)
	{
		if (AttributesKey.ALL.equals(attributesKey))
		{
			return AvailabilityInfoResultForWebui.Group.Type.ALL_STORAGE_KEYS;
		}
		else if (AttributesKey.OTHER.equals(attributesKey))
		{
			return AvailabilityInfoResultForWebui.Group.Type.OTHER_STORAGE_KEYS;
		}
		else
		{
			return AvailabilityInfoResultForWebui.Group.Type.ATTRIBUTE_SET;
		}
	}

	@NonNull
	public AvailabilityInfoResultForWebui retrieveAvailableStock(@NonNull final AvailableToPromiseQuery query)
	{
		final AvailableToPromiseResult commonsAvailableStock = availableToPromiseRepository.retrieveAvailableStock(query);

		final AvailabilityInfoResultForWebuiBuilder clientResultBuilder = AvailabilityInfoResultForWebui.builder();

		final List<AvailableToPromiseResultGroup> commonsResultGroups = commonsAvailableStock.getResultGroups();
		for (final AvailableToPromiseResultGroup commonsResultGroup : commonsResultGroups)
		{
			final AvailabilityInfoResultForWebui.Group clientResultGroup = createClientResultGroup(commonsResultGroup);
			clientResultBuilder.group(clientResultGroup);
		}
		return clientResultBuilder.build();
	}

	private AvailabilityInfoResultForWebui.Group createClientResultGroup(@NonNull final AvailableToPromiseResultGroup commonsResultGroup)
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

	private Quantity extractQuantity(final AvailableToPromiseResultGroup commonsResultGroup)
	{
		final BigDecimal qty = commonsResultGroup.getQty();
		final I_C_UOM uom = productsService.getStockUOM(commonsResultGroup.getProductId());
		return Quantity.of(qty, uom);
	}

	private AvailabilityInfoResultForWebui.Group createClientResultGroup0(final AvailableToPromiseResultGroup commonsResultGroup)
	{
		final Quantity quantity = extractQuantity(commonsResultGroup);

		final AttributesKey attributesKey = commonsResultGroup.getStorageAttributesKey();
		final AvailabilityInfoResultForWebui.Group.Type type = extractGroupType(attributesKey);

		final ImmutableAttributeSet attributes = AvailabilityInfoResultForWebui.Group.Type.ATTRIBUTE_SET.equals(type)
				? AttributesKeys.toImmutableAttributeSet(attributesKey)
				: ImmutableAttributeSet.EMPTY;

		return AvailabilityInfoResultForWebui.Group.builder()
				.productId(commonsResultGroup.getProductId())
				.qty(quantity)
				.type(type)
				.attributes(attributes)
				.build();
	}

	public Set<AttributesKeyPattern> getPredefinedStorageAttributeKeys()
	{
		return availableToPromiseRepository.getPredefinedStorageAttributeKeys();
	}
}
