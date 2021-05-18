package de.metas.ui.web.handlingunits.process;

import de.metas.document.DocTypeId;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateRequest;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.process.Param;
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
public class WEBUI_M_HU_MoveToGarbage extends WEBUI_M_HU_InternalUse_Template
{
	@Param(parameterName = "C_DocType_ID")
	@Nullable
	private DocTypeId p_internalUseInventoryDocTypeId;

	@Param(parameterName = I_M_Inventory.COLUMNNAME_Description)
	@Nullable
	private String p_description;

	protected HUInternalUseInventoryCreateRequest createHUInternalUseInventoryCreateRequest()
	{
		return HUInternalUseInventoryCreateRequest.builder()
				.hus(getHUsToInternalUse())
				.movementDate(Env.getZonedDateTime(getCtx()))
				.internalUseInventoryDocTypeId(p_internalUseInventoryDocTypeId)
				.description(p_description)
				.completeInventory(true)
				.moveEmptiesToEmptiesWarehouse(true)
				.sendNotifications(true)
				.build();
	}
}
