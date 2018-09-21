package de.metas.product;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import de.metas.product.model.I_M_Product_LotNumber_Lock;
import de.metas.util.Check;
import de.metas.util.Services;

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
public class LotNumberLockRepository
{

	public LotNumberLock getById(final int lotNoLockId)
	{
		Check.assumeGreaterThanZero(lotNoLockId, "lotNoLockId");
		final I_M_Product_LotNumber_Lock record = load(lotNoLockId, I_M_Product_LotNumber_Lock.class);
		return toLotNumberLock(record);
	}

	private static LotNumberLock toLotNumberLock(final I_M_Product_LotNumber_Lock record)
	{
		return LotNumberLock.builder()
				.id(record.getM_Product_LotNumber_Lock_ID())
				.productId(record.getM_Product_ID())
				.lotNo(record.getLot())
				.description(record.getDescription())
				.build();
	}

	public LotNumberLock getByProductIdAndLot(final int productId, final String lotNo)
	{
		final I_M_Product_LotNumber_Lock record = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product_LotNumber_Lock.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addInArrayFilter(I_M_Product_LotNumber_Lock.COLUMN_M_Product_ID, productId, null)
				.addEqualsFilter(I_M_Product_LotNumber_Lock.COLUMNNAME_Lot, lotNo)
				.orderBy(I_M_Product_LotNumber_Lock.COLUMNNAME_M_Product_ID)
				.create()
				.first();

		return record != null ? toLotNumberLock(record) : null;
	}

}
