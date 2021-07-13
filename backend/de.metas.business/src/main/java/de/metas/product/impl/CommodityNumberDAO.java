/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.product.impl;

import com.google.common.collect.ImmutableList;
import de.metas.fresh.model.I_M_CommodityNumber;
import de.metas.product.CommodityNumberId;
import de.metas.product.ICommodityNumberDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;

import java.util.List;
import java.util.Set;

public class CommodityNumberDAO implements ICommodityNumberDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@NonNull
	public List<I_M_CommodityNumber> getByIds(@NonNull final Set<CommodityNumberId> commodityNumberIds)
	{
		if (commodityNumberIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return
				queryBL
				.createQueryBuilder(I_M_CommodityNumber.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_CommodityNumber.COLUMN_M_CommodityNumber_ID, commodityNumberIds)
				.create()
				.list();
	}
}
