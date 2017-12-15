package de.metas.shipping.model.validator;

import org.adempiere.ad.dao.cache.CacheInvalidateRequest;
import org.adempiere.ad.dao.cache.IModelCacheInvalidationService;
import org.adempiere.ad.dao.cache.ModelCacheInvalidateRequestFactory;
import org.adempiere.ad.dao.cache.ModelCacheInvalidationTiming;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.CacheMgt;
import org.springframework.stereotype.Component;

import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;

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

@Interceptor(I_M_ShippingPackage.class)
@Component
public class M_ShippingPackage
{
	@Init
	public void setupCaching()
	{
		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_ShippingPackage.Table_Name);

		final IModelCacheInvalidationService cacheInvalidationService = Services.get(IModelCacheInvalidationService.class);
		cacheInvalidationService.register(I_M_ShippingPackage.Table_Name, new ShippingPackageModelCacheInvalidateRequestFactory());
	}

	private static final class ShippingPackageModelCacheInvalidateRequestFactory implements ModelCacheInvalidateRequestFactory
	{
		@Override
		public CacheInvalidateRequest createRequestFromModel(Object model, ModelCacheInvalidationTiming timing)
		{
			final I_M_ShippingPackage shippingPackage = InterfaceWrapperHelper.create(model, I_M_ShippingPackage.class);

			final int shipperTransportationId = shippingPackage.getM_ShipperTransportation_ID();
			if (shipperTransportationId > 0)
			{
				return CacheInvalidateRequest.builder()
						.rootRecord(I_M_ShipperTransportation.Table_Name, shipperTransportationId)
						.childRecord(I_M_ShippingPackage.Table_Name, shippingPackage.getM_ShippingPackage_ID())
						.build();
			}
			else
			{
				return CacheInvalidateRequest.rootRecord(I_M_ShipperTransportation.Table_Name, shipperTransportationId);
			}
		}
	}

}
