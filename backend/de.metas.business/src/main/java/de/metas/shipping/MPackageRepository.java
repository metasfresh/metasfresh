package de.metas.shipping;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.shipping.mpackage.PackageId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Package;
import org.springframework.stereotype.Repository;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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
 * Repository Tables: M_Package
 * Repository Cluster: MPackageRepository (sole owner of M_Package). A caller that needs a package alongside
 * records of another aggregate - {@code M_ShippingPackage}, say - composes the two rather than creating the
 * package itself.
 */
@Repository
public class MPackageRepository
{
	/**
	 * Creates one {@code M_Package}. Every field of the request is optional, so a caller passes what it knows and
	 * leaves the rest empty.
	 */
	public PackageId create(@NonNull final MPackageCreateRequest request)
	{
		final I_M_Package mpackage = newInstance(I_M_Package.class);
		mpackage.setM_Shipper_ID(ShipperId.toRepoId(request.getShipperId()));
		mpackage.setShipDate(request.getShipDate());
		mpackage.setC_BPartner_ID(BPartnerId.toRepoId(request.getBpartnerId()));
		mpackage.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(request.getBpartnerLocationId()));
		save(mpackage);

		return PackageId.ofRepoId(mpackage.getM_Package_ID());
	}

	public I_M_Package getById(final PackageId mPackageId)
	{
		final I_M_Package mPackage = load(mPackageId.getRepoId(), I_M_Package.class);
		if (mPackage == null)
		{
			throw new AdempiereException("@NotFound@: " + mPackageId);
		}
		return mPackage;
	}

	public void closeMPackage(PackageId mPackageId)
	{
		final I_M_Package mPackage = getById(mPackageId);

		mPackage.setIsClosed(true);

		save(mPackage);
	}
}
