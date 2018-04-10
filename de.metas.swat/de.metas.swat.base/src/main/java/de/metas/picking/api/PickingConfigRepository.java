package de.metas.picking.api;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.springframework.stereotype.Repository;

import de.metas.picking.model.I_M_Picking_Config;

/*
 * #%L
 * de.metas.swat.base
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

@Repository
public class PickingConfigRepository
{

	public static final String MSG_WEBUI_Picking_OverdeliveryNotAllowed = "M_Picking_Config_OverdeliveryNotAllowed";

	private final CCache<Integer, PickingConfig> pickingConfigCache = CCache.newCache(I_M_Picking_Config.Table_Name, 1, CCache.EXPIREMINUTES_Never);

	public PickingConfig getPickingConfig()
	{
		return pickingConfigCache.getOrLoad(0, this::createPickingConfig);
	}

	private PickingConfig createPickingConfig()
	{
		final I_M_Picking_Config pickingConfigPO = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyNotNull(I_M_Picking_Config.class);

		return PickingConfig.builder()
				.webuiPickingTerminalViewProfileId(pickingConfigPO.getWEBUI_PickingTerminal_ViewProfile())
				.allowOverDelivery(pickingConfigPO.isAllowOverdelivery())
				.autoProcess(pickingConfigPO.isAutoProcess())
				.build();
	}
}
