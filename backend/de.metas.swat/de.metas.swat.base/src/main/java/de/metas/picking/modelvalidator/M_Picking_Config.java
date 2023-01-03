package de.metas.picking.modelvalidator;

import de.metas.cache.CacheMgt;
import de.metas.common.util.WindowConstants;
import de.metas.picking.model.I_M_Picking_Config;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Window;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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

@Interceptor(I_M_Picking_Config.class)
@Component
public class M_Picking_Config
{
	@Init
	public void onInit()
	{
		CacheMgt.get().enableRemoteCacheInvalidationForTableName(I_M_Picking_Config.Table_Name);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_Picking_Config.COLUMNNAME_WEBUI_PickingTerminal_ViewProfile)
	public void resetCacheAfterViewProfileChange(final I_M_Picking_Config pickingConfig)
	{
		CacheMgt.get().reset(I_AD_Window.Table_Name, WindowConstants.PACKAGEABLE_VIEW_AD_WINDOW_ID);
	}
}
