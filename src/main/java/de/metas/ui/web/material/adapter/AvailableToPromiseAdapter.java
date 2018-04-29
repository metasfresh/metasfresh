package de.metas.ui.web.material.adapter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.AvailableToPromiseResult.ResultGroup;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.AvailableToPromiseResultForWebuiBuilder;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group.GroupBuilder;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group.Type;
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
	private final AvailableToPromiseRepository stockRepository;

	public AvailableToPromiseAdapter(@NonNull final AvailableToPromiseRepository stockRepository)
	{
		this.stockRepository = stockRepository;
	}

	@NonNull
	public AvailableToPromiseResultForWebui retrieveAvailableStock(@NonNull final AvailableToPromiseQuery query)
	{
		final de.metas.material.dispo.commons.repository.AvailableToPromiseResult commonsAvailableStock = //
				stockRepository.retrieveAvailableStock(query);

		final AvailableToPromiseResultForWebuiBuilder clientResultBuilder = AvailableToPromiseResultForWebui.builder();

		final List<ResultGroup> commonsResultGroups = commonsAvailableStock.getResultGroups();
		for (final ResultGroup commonsResultGroup : commonsResultGroups)
		{
			final Group clientResultGroup = createClientResultGroup(commonsResultGroup);
			clientResultBuilder.group(clientResultGroup);
		}
		return clientResultBuilder.build();
	}

	private Group createClientResultGroup(@NonNull final ResultGroup commonsResultGroup)
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

	private Group createClientResultGroup0(final ResultGroup commonsResultGroup)
	{
		final GroupBuilder groupBuilder = Group.builder()
				.productId(commonsResultGroup.getProductId());

		final Quantity quantity = Quantity.of(
				commonsResultGroup.getQty(),
				retrieveStockingUOM(commonsResultGroup.getProductId()));
		groupBuilder.qty(quantity);

		final AttributesKey attributesKey = commonsResultGroup.getStorageAttributesKey();
		final Type type = extractType(attributesKey);
		groupBuilder.type(type);

		if (type == Type.ATTRIBUTE_SET)
		{
			final List<I_M_AttributeValue> attributevalues = extractAttributeSetFromStorageAttributesKey(attributesKey);
			groupBuilder.attributeValues(attributevalues);
		}

		return groupBuilder.build();
	}

	private I_C_UOM retrieveStockingUOM(final int productId)
	{
		final I_C_UOM uom = Services.get(IProductBL.class).getStockingUOM(load(productId, I_M_Product.class));
		return uom;
	}

	@VisibleForTesting
	Type extractType(@NonNull final AttributesKey attributesKey)
	{
		if (AttributesKey.ALL.equals(attributesKey))
		{
			return Type.ALL_STORAGE_KEYS;
		}
		else if (AttributesKey.OTHER.equals(attributesKey))
		{
			return Type.OTHER_STORAGE_KEYS;
		}
		else
		{
			return Type.ATTRIBUTE_SET;
		}
	}

	@VisibleForTesting
	List<I_M_AttributeValue> extractAttributeSetFromStorageAttributesKey(@NonNull final AttributesKey attributesKey)
	{
		try
		{
			final Collection<Integer> attributeValueIds = attributesKey.getAttributeValueIds();
			if (attributeValueIds.isEmpty())
			{
				return ImmutableList.of();
			}

			return Services.get(IQueryBL.class).createQueryBuilder(I_M_AttributeValue.class)
					.addInArrayFilter(I_M_AttributeValue.COLUMN_M_AttributeValue_ID, attributeValueIds)
					.create()
					.list(I_M_AttributeValue.class);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
					.setParameter("storageAttributesKey", attributesKey);
		}
	}
	
	public Set<AttributesKey> getPredefinedStorageAttributeKeys()
	{
		return stockRepository.getPredefinedStorageAttributeKeys();
	}
}
