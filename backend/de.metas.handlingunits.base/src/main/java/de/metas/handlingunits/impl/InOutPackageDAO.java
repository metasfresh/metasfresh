/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.impl;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IInOutPackageDAO;
import de.metas.inout.IInOutDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

public class InOutPackageDAO implements IInOutPackageDAO
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	@Override
	@NonNull
	public ImmutableList<I_M_Package> createM_Packages(@NonNull final List<CreatePackagesRequest> packagesRequestList)
	{
		return packagesRequestList.stream().map(this::createM_Package).collect(ImmutableList.toImmutableList());
	}

	private I_M_Package createM_Package(@NonNull final CreatePackagesRequest createPackageRequest)
	{
		final I_M_InOut inOut = inOutDAO.getById(createPackageRequest.getInOutId());

		final I_M_Package mPackage = newInstance(I_M_Package.class);
		mPackage.setM_Shipper_ID(createPackageRequest.getShipperId().getRepoId());
		mPackage.setAD_Org_ID(inOut.getAD_Org_ID());
		mPackage.setProcessed(createPackageRequest.isProcessed());
		mPackage.setShipDate(null);
		mPackage.setC_BPartner_ID(inOut.getC_BPartner_ID());
		mPackage.setC_BPartner_Location_ID(inOut.getC_BPartner_Location_ID());
		mPackage.setM_InOut_ID(inOut.getM_InOut_ID());
		mPackage.setTrackingInfo(createPackageRequest.getTrackingCode());
		mPackage.setPackageWeight(createPackageRequest.getWeight());
		mPackage.setTrackingURL(createPackageRequest.getTrackingURL());

		InterfaceWrapperHelper.save(mPackage);

		return mPackage;
	}
}
