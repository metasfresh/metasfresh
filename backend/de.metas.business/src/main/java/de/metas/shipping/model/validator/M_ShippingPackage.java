package de.metas.shipping.model.validator;

import de.metas.inout.model.I_M_InOut;
import de.metas.shipping.mpackage.PackageId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.cache.CacheMgt;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Services;

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

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void clearShipperTransportationLinkIfNoRemainingPackages(final I_M_ShippingPackage shippingPackage)
	{
		unlinkShipmentIfOrphaned(shippingPackage.getM_InOut_ID(), shippingPackage.getM_ShipperTransportation_ID());
	}

	/**
	 * The M_InOut to M_ShipperTransportation link is only ever SET, never cleared, anywhere else in the codebase.
	 * Call this whenever a M_ShippingPackage row that carried the link is removed or deactivated, so a shipment
	 * does not stay permanently linked to a transport order it no longer has any active package on.
	 */
	public static void unlinkShipmentIfOrphaned(final int inOutId, final int shipperTransportationId)
	{
		if (inOutId <= 0 || shipperTransportationId <= 0)
		{
			return;
		}

		final boolean stillLinked = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID, inOutId)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, shipperTransportationId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();

		if (stillLinked)
		{
			return;
		}

		final I_M_InOut shipment = InterfaceWrapperHelper.load(inOutId, I_M_InOut.class);
		if (shipment.getM_ShipperTransportation_ID() != shipperTransportationId)
		{
			// already relinked to something else (or already cleared) meanwhile - don't clobber
			return;
		}
		shipment.setM_ShipperTransportation_ID(0);
		InterfaceWrapperHelper.save(shipment);
	}
}
