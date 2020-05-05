/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.security.model.interceptor;

import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Document_Action_Access;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.ModelValidator;

@Interceptor(I_AD_Document_Action_Access.class)
public class AD_Document_Action_Access
{
	public static final transient AD_Document_Action_Access instance = new AD_Document_Action_Access();

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void afterNewChangedOrDeleted(final I_AD_Document_Action_Access documentActionAccess)
	{
		final IModelCacheInvalidationService modelCacheInvalidationService = Services.get(IModelCacheInvalidationService.class);
		//Calling with CHANGE as NEW only resets the local cache.
		modelCacheInvalidationService.invalidate(
				CacheInvalidateMultiRequest.allRecordsForTable(I_AD_Ref_List.Table_Name), ModelCacheInvalidationTiming.CHANGE);
	}
}
