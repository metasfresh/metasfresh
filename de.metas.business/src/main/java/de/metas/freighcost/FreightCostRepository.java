package de.metas.freighcost;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.I_M_FreightCost;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

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

@Repository
public class FreightCostRepository
{
	public boolean existsByProductId(final ProductId productId)
	{
		return !getByProductId(productId).isEmpty();
	}

	public List<FreightCost> getByProductId(@NonNull final ProductId productId)
	{
		// TODO caching

		return Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_M_FreightCost.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_FreightCost.COLUMNNAME_M_Product_ID, productId)
				.create()
				.stream()
				.map(record -> toFreightCost(record))
				.collect(ImmutableList.toImmutableList());

	}

	private static FreightCost toFreightCost(final I_M_FreightCost record)
	{
		return FreightCost.builder()
				.id(FreightCostId.ofRepoId(record.getM_FreightCost_ID()))
				.build();
	}

}
