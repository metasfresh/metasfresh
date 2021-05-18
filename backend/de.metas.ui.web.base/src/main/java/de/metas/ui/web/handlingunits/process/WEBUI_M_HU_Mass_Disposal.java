package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateRequest;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.process.Param;
import de.metas.product.acct.api.ActivityId;
import org.compiere.util.Env;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Create Internal Use Inventory and destroy given HUs.
 *
 * @author metas-dev <dev@metasfresh.com>
 * Task initial task https://github.com/metasfresh/metasfresh-webui-api/issues/396
 */
public class WEBUI_M_HU_Mass_Disposal extends WEBUI_M_HU_InternalUse_Template
{
	@Param(parameterName = I_M_Inventory.COLUMNNAME_C_Activity_ID)
	@Nullable
	private ActivityId p_C_Activity_ID;

	@Param(parameterName = I_M_Inventory.COLUMNNAME_Description)
	@Nullable
	private String p_Description;

	@Param(parameterName = "IsComplete")
	private boolean p_IsCompleteInventory;

	@Override
	protected HUInternalUseInventoryCreateRequest createHUInternalUseInventoryCreateRequest()
	{
		return HUInternalUseInventoryCreateRequest.builder()
				.hus(getHUsToInternalUse())
				.movementDate(Env.getZonedDateTime(getCtx()))
				.activityId(p_C_Activity_ID)
				.description(p_Description)
				.completeInventory(p_IsCompleteInventory)
				.moveEmptiesToEmptiesWarehouse(false)
				.sendNotifications(true)
				.build();
	}
}
