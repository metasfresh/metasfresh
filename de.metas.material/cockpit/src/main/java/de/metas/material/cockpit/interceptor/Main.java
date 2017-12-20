package de.metas.material.cockpit.interceptor;

import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.compiere.Adempiere;
import org.compiere.util.CacheMgt;

import de.metas.material.cockpit.MaterialCockpitEventListener;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.event.MaterialEventService;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

public class Main extends AbstractModuleInterceptor
{
	/**
	 * Registers the {@link MaterialCockpitEventListener} that keeps the {@code MD_Cockpit} table up to date.
	 */
	@Override
	protected void onAfterInit()
	{
		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
		final MaterialCockpitEventListener cockpitListener = Adempiere.getBean(MaterialCockpitEventListener.class);

		materialEventService.registerListener(cockpitListener);
		materialEventService.subscribeToEventBus();
	}

	@Override
	protected void setupCaching(@NonNull final IModelCacheService cachingService)
	{
		// needed to update the webui-view on data changes
		CacheMgt.get().enableRemoteCacheInvalidationForTableName(I_MD_Cockpit.Table_Name);
	}
}
