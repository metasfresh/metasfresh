package de.metas.product.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;

import de.metas.product.IProductLotNumberLockDAO;
import de.metas.product.model.I_M_Product_LotNumber_Lock;

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

public class ProductLotNumberLockDAO implements IProductLotNumberLockDAO
{

	@Override
	public I_M_Product_LotNumber_Lock retrieveLotNumberLock(final int productId, final String lotNo)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product_LotNumber_Lock.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Product_LotNumber_Lock.COLUMN_M_Product_ID, productId)
				.addEqualsFilter(I_M_Product_LotNumber_Lock.COLUMNNAME_Lot, lotNo)
				.orderBy(I_M_Product_LotNumber_Lock.COLUMN_M_Product_ID)
				.create()
				.first();

	}

}
