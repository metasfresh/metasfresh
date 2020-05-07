package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.IHUPackageDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.shipping.interfaces.I_M_Package;

public class HUPackageDAO implements IHUPackageDAO
{
	@Override
	public List<I_M_Package_HU> retrievePackageHUs(final org.compiere.model.I_M_Package mpackage)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Package_HU.class, mpackage)
				.filter(new EqualsQueryFilter<I_M_Package_HU>(I_M_Package_HU.COLUMN_M_Package_ID, mpackage.getM_Package_ID()))
				.create()
				.list(I_M_Package_HU.class);
	}

	@Override
	public List<I_M_HU> retrieveHUs(final org.compiere.model.I_M_Package mpackage)
	{
		Check.assumeNotNull(mpackage, "mpackage not null");

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_M_Package_HU.class, mpackage)
				.addEqualsFilter(I_M_Package_HU.COLUMN_M_Package_ID, mpackage.getM_Package_ID())
				.addOnlyActiveRecordsFilter()
				.andCollect(I_M_Package_HU.COLUMN_M_HU_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_HU.class);
	}

	@Override
	public List<I_M_Package> retrievePackages(final Properties ctx, final Collection<Integer> packageIds)
	{
		if (packageIds == null || packageIds.isEmpty())
		{
			return Collections.emptyList();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Package.class, ctx, ITrx.TRXNAME_None)
				.addInArrayOrAllFilter(org.compiere.model.I_M_Package.COLUMNNAME_M_Package_ID, packageIds)
				.create()
				.list(I_M_Package.class);
	}

	@Override
	public boolean isHUAssignedToPackage(final I_M_HU hu)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Package_HU.class, hu)
				.addEqualsFilter(I_M_Package_HU.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.create()
				.match();
	}

	@Override
	public List<I_M_Package> retrievePackages(final I_M_HU hu, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(hu);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_M_Package_HU.class, ctx, trxName)
				.addEqualsFilter(I_M_Package_HU.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.addOnlyActiveRecordsFilter()
				.andCollect(I_M_Package_HU.COLUMN_M_Package_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_Package.class);
	}

	@Override
	public List<I_M_Package> retrievePackagesForShipment(final I_M_InOut shipment)
	{
		Check.assumeNotNull(shipment, "shipment not null");

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_M_Package.class, shipment)
				.addEqualsFilter(org.compiere.model.I_M_Package.COLUMNNAME_M_InOut_ID, shipment.getM_InOut_ID())
				.create()
				.list(I_M_Package.class);

	}

	@Override
	public I_M_Package retrievePackage(final I_M_HU hu)
	{
		final List<I_M_Package> mpackages = retrievePackages(hu, ITrx.TRXNAME_ThreadInherited);
		if (mpackages.isEmpty())
		{
			return null;
		}
		else if (mpackages.size() > 1)
		{
			Check.errorIf(true, HUException.class, "More than one package was found for HU."
					+ "\n@M_HU_ID@: {}"
					+ "\n@M_Package_ID@: {}", hu, mpackages);
			return mpackages.get(0); // in case the system is configured to just log
		}
		else
		{
			return mpackages.get(0);
		}
	}
}
