package de.metas.product;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.uom.UomId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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
public class ProductRepository
{
	public Product getById(@NonNull final ProductId id)
	{
		final I_M_Product productRecord = loadOutOfTrx(id.getRepoId(), I_M_Product.class);
		return ofRecord(productRecord);
	}

	public Product ofRecord(@NonNull final I_M_Product productRecord)
	{
		final int manufacturerId = productRecord.getManufacturer_ID();

		return Product.builder()
				.id(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.uomId(UomId.ofRepoId(productRecord.getC_UOM_ID()))
				.manufacturerId(manufacturerId > 0 ? BPartnerId.ofRepoId(manufacturerId) : null)
				.build();
	}
}
