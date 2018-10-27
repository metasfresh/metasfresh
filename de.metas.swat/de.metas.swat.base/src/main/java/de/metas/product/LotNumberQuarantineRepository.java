package de.metas.product;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import de.metas.product.model.I_M_Product_LotNumber_Quarantine;
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
public class LotNumberQuarantineRepository
{

	public LotNumberQuarantine getById(final int lotNoQuarantineId)
	{
		Check.assumeGreaterThanZero(lotNoQuarantineId, "lotNoQuarantineId");
		final I_M_Product_LotNumber_Quarantine record = load(lotNoQuarantineId, I_M_Product_LotNumber_Quarantine.class);
		return toLotNumberQuarantine(record);
	}

	private static LotNumberQuarantine toLotNumberQuarantine(final I_M_Product_LotNumber_Quarantine record)
	{
		return LotNumberQuarantine.builder()
				.id(record.getM_Product_LotNumber_Quarantine_ID())
				.productId(record.getM_Product_ID())
				.lotNo(record.getLot())
				.description(record.getDescription())
				.build();
	}

	public LotNumberQuarantine getByProductIdAndLot(final ProductId productId, final String lotNo)
	{
		final I_M_Product_LotNumber_Quarantine record = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product_LotNumber_Quarantine.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addInArrayFilter(I_M_Product_LotNumber_Quarantine.COLUMN_M_Product_ID, productId, null)
				.addEqualsFilter(I_M_Product_LotNumber_Quarantine.COLUMNNAME_Lot, lotNo)
				.orderBy(I_M_Product_LotNumber_Quarantine.COLUMNNAME_M_Product_ID)
				.create()
				.first();

		return record != null ? toLotNumberQuarantine(record) : null;
	}

}
