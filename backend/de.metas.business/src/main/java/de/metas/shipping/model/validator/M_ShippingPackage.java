package de.metas.shipping.model.validator;

import de.metas.shipping.mpackage.PackageId;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.cache.CacheMgt;
import de.metas.shipping.MPackageRepository;
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
	final MPackageRepository packageRepo = SpringContextHolder.instance.getBean(MPackageRepository.class);

	@Init
	public void setupCaching()
	{
		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_M_ShippingPackage.Table_Name);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_ShippingPackage.COLUMNNAME_C_Order_ID)
	public void closePackageOnOrderDelete(final I_M_ShippingPackage shippingPackage)
	{
		final int orderRecordId = shippingPackage.getC_Order_ID();

		if (orderRecordId > 0)
		{
			// nothing to do
			return;
		}

		final PackageId packageId = PackageId.ofRepoId(shippingPackage.getM_Package_ID());
		packageRepo.closeMPackage(packageId);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void closePackageOnDelete(final I_M_ShippingPackage shippingPackage)
	{
		final int orderRecordId = shippingPackage.getC_Order_ID();

		if (orderRecordId <= 0)
		{
			// nothing to do
			return;
		}

		final PackageId mPackageId = PackageId.ofRepoId(shippingPackage.getM_Package_ID());
		packageRepo.closeMPackage(mPackageId);
	}
}
