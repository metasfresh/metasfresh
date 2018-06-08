package de.metas.material.cockpit.interceptor;

import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.compiere.util.CacheMgt;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Cockpit_DocumentDetail;
import de.metas.material.cockpit.model.I_MD_Stock;

/*
 * #%L
 * metasfresh-material-cockpit
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

public class ModuleInterceptor extends AbstractModuleInterceptor
{
	@Override
	protected void setupCaching(final IModelCacheService cachingService)
	{
		// changes in both MD_Cockpit and MD_Stock trigger the material cockpit view to be revalidated
		CacheMgt.get().enableRemoteCacheInvalidationForTableName(I_MD_Cockpit.Table_Name);
		CacheMgt.get().enableRemoteCacheInvalidationForTableName(I_MD_Stock.Table_Name);

		// currently as this window is opend via MD_Cockpit_DocumentDetail_Display,
		// changes MD_Cockpit_DocumentDetail don't cause and view change
		CacheMgt.get().enableRemoteCacheInvalidationForTableName(I_MD_Cockpit_DocumentDetail.Table_Name);
	}
}
