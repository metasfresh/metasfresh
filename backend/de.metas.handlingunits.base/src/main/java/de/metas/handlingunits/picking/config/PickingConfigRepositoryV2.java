/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.handlingunits.picking.config;

import de.metas.cache.CCache;
import de.metas.picking.model.I_M_Picking_Config_V2;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

@Repository
public class PickingConfigRepositoryV2
{
	private final CCache<Integer, PickingConfigV2> pickingConfigCache = CCache.newCache(I_M_Picking_Config_V2.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	public PickingConfigV2 getPickingConfig()
	{
		return pickingConfigCache.getOrLoad(0, this::createPickingConfig);
	}

	private PickingConfigV2 createPickingConfig()
	{
		final I_M_Picking_Config_V2 record = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Config_V2.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyNotNull(I_M_Picking_Config_V2.class);

		return PickingConfigV2.builder()
				.pickingReviewRequired(record.isPickingReviewRequired())
				.considerAttributes(record.isConsiderAttributes())
				.reserveHUsOnJobStart(record.isReserveHUsOnPickingJobStart())
				.build();
	}

}
