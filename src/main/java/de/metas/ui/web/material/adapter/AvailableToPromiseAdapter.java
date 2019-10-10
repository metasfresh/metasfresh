package de.metas.ui.web.material.adapter;

import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseResult;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseResultGroup;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.AvailableToPromiseResultForWebuiBuilder;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group.GroupBuilder;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group.Type;
import de.metas.util.Services;
import lombok.NonNull;

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
	private final AvailableToPromiseRepository availableToPromiseRepository;

	public AvailableToPromiseAdapter(@NonNull final AvailableToPromiseRepository stockRepository)
	{
		this.availableToPromiseRepository = stockRepository;
	}

	@NonNull
	public AvailableToPromiseResultForWebui retrieveAvailableStock(@NonNull final AvailableToPromiseQuery query)
	{
		final AvailableToPromiseResult commonsAvailableStock = availableToPromiseRepository.retrieveAvailableStock(query);

		final AvailableToPromiseResultForWebuiBuilder clientResultBuilder = AvailableToPromiseResultForWebui.builder();

		final List<AvailableToPromiseResultGroup> commonsResultGroups = commonsAvailableStock.getResultGroups();
		for (final AvailableToPromiseResultGroup commonsResultGroup : commonsResultGroups)
		{
			final Group clientResultGroup = createClientResultGroup(commonsResultGroup);
			clientResultBuilder.group(clientResultGroup);
		}
		return clientResultBuilder.build();
	}

	private Group createClientResultGroup(@NonNull final AvailableToPromiseResultGroup commonsResultGroup)
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

	private Group createClientResultGroup0(final AvailableToPromiseResultGroup commonsResultGroup)
	{
		final GroupBuilder groupBuilder = Group.builder()
				.productId(ProductId.ofRepoId(commonsResultGroup.getProductId()));

		final Quantity quantity = Quantity.of(
				commonsResultGroup.getQty(),
				retrieveStockingUOM(commonsResultGroup.getProductId()));
		groupBuilder.qty(quantity);

		final AttributesKey attributesKey = commonsResultGroup.getStorageAttributesKey();
		final Type type = extractGroupType(attributesKey);
		groupBuilder.type(type);

		if (type == Type.ATTRIBUTE_SET)
		{
			groupBuilder.attributes(AttributesKeys.toImmutableAttributeSet(attributesKey));
		}

		return groupBuilder.build();
	}

	private I_C_UOM retrieveStockingUOM(final int productId)
	{
		return Services.get(IProductBL.class).getStockUOM(productId);
	}

	@VisibleForTesting
	static Group.Type extractGroupType(@NonNull final AttributesKey attributesKey)
	{
		if (AttributesKey.ALL.equals(attributesKey))
		{
			return Group.Type.ALL_STORAGE_KEYS;
		}
		else if (AttributesKey.OTHER.equals(attributesKey))
		{
			return Group.Type.OTHER_STORAGE_KEYS;
		}
		else
		{
			return Type.ATTRIBUTE_SET;
		}
	}

	public Set<AttributesKey> getPredefinedStorageAttributeKeys()
	{
		return availableToPromiseRepository.getPredefinedStorageAttributeKeys();
	}
}
