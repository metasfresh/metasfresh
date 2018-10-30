package de.metas.product;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_ProductDescription;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class ProductDescriptionRepository
{

	public ProductDescription getById(final int productDescriptionRepositoryId)
	{
		Check.assumeGreaterThanZero(productDescriptionRepositoryId, "productDescriptionRepositoryId");
		final I_M_ProductDescription record = load(productDescriptionRepositoryId, I_M_ProductDescription.class);
		return toProductDescription(record);
	}

	private static ProductDescription toProductDescription(final I_M_ProductDescription record)
	{
		return ProductDescription.builder()
				.id(record.getM_ProductDescription_ID())
				.productId(record.getM_Product_ID())
				.bpartnerId(record.getC_BPartner_ID())
				.name(record.getName())
				.build();
	}
	
	public ProductDescription getByProductIdAndBPartner(@NonNull final ProductId productId, final BPartnerId bpartnerId)
	{
		final I_M_ProductDescription record =  Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductDescription.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_ProductDescription.COLUMN_M_Product_ID, productId)
				.addInArrayFilter(I_M_ProductDescription.COLUMN_C_BPartner_ID, bpartnerId, null)
				.orderBy(I_M_ProductDescription.COLUMN_C_BPartner_ID)
				.create()
				.first();
		
		return record != null ? toProductDescription(record) : null;
	}
}
