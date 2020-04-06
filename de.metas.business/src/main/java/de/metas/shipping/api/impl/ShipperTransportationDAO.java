package de.metas.shipping.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Package;

import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.X_M_ShipperTransportation;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class ShipperTransportationDAO implements IShipperTransportationDAO
{

	@Override
	public I_M_ShipperTransportation getById(@NonNull final ShipperTransportationId shipperItransportationId)
	{
		final I_M_ShipperTransportation shipperTransportation = load(shipperItransportationId.getRepoId(), I_M_ShipperTransportation.class);
		if (shipperTransportation == null)
		{
			throw new AdempiereException("@NotFound@: " + shipperItransportationId);
		}
		return shipperTransportation;
	}

	@Override
	public List<I_M_ShippingPackage> retrieveShippingPackages(@NonNull final ShipperTransportationId shipperTransportationId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShippingPackage.class)
				.filter(new EqualsQueryFilter<I_M_ShippingPackage>(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, shipperTransportationId))
				.create()
				.list(I_M_ShippingPackage.class);
	}

	@Override
	public <T extends I_M_ShipperTransportation> List<T> retrieveOpenShipperTransportations(final Properties ctx, final Class<T> clazz)
	{
		final IQueryBuilder<T> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(clazz, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_DocStatus, X_M_ShipperTransportation.DOCSTATUS_Drafted) // Drafts
		;

		queryBuilder.orderBy()
				.addColumn(I_M_ShipperTransportation.COLUMNNAME_DocumentNo);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public ShipperTransportationId retrieveNextOpenShipperTransportationIdOrNull(final ShipperId shipperId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipperTransportation.class)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_DocStatus, X_M_ShipperTransportation.DOCSTATUS_Drafted) // Drafts
				.addEqualsFilter(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID, shipperId)
				.orderBy(I_M_ShipperTransportation.COLUMNNAME_DateToBeFetched)

				.create()
				.firstId(ShipperTransportationId::ofRepoIdOrNull);
	}

	@Override
	public List<I_M_ShippingPackage> retrieveShippingPackages(final I_M_Package mpackage)
	{
		Check.assumeNotNull(mpackage, "mpackage not null");

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShippingPackage.class, mpackage)
				.filter(new EqualsQueryFilter<I_M_ShippingPackage>(I_M_ShippingPackage.COLUMNNAME_M_Package_ID, mpackage.getM_Package_ID()))
				.create()
				.list(I_M_ShippingPackage.class);
	}

	@Override
	public I_M_ShipperTransportation retrieve(@NonNull final ShipperTransportationId shipperTransportationId)
	{
		return load(shipperTransportationId, I_M_ShipperTransportation.class);
	}
}
