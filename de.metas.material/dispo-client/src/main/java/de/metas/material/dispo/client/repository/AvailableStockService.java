package de.metas.material.dispo.client.repository;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.Collection;

import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.client.repository.AvailableStockResult.Group;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.MaterialQuery;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
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
public class AvailableStockService
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	public AvailableStockService(@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@NonNull
	public AvailableStockResult retrieveAvailableStock(@NonNull final MaterialQuery query)
	{
		final BigDecimal qtyValue = candidateRepositoryRetrieval.retrieveAvailableStock(query);

		final int productId = ListUtils.singleElement(query.getProductIds());

		final I_C_UOM uom = getStockingUOM(productId);
		final Quantity qty = Quantity.of(qtyValue, uom);

		return AvailableStockResult.builder()
				.group(Group.builder()
						.productId(productId)
						// .attributes(attributes) // TODO
						.qty(qty)
						.build())
				.build();
	}


	public I_C_UOM getStockingUOM(final int productId)
	{
		final I_C_UOM uom = Services.get(IProductBL.class).getStockingUOM(load(productId, I_M_Product.class));
		return uom;
	}

	public Collection<String> extractStorageAttributeKeysForDimensionSpec(int dimensionSpecId)
	{
		return null;

	}
}
