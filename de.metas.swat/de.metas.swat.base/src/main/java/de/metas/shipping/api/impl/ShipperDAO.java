package de.metas.shipping.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Shipper;

import de.metas.shipping.api.IShipperDAO;
import de.metas.util.Check;
import de.metas.util.Services;

public class ShipperDAO implements IShipperDAO
{
	@Override
	public I_M_Shipper retrieveForShipperBPartner(final I_C_BPartner shipperBPartner)
	{
		Check.assumeNotNull(shipperBPartner, "shipperTransportation not null");

		final IQueryBuilder<I_M_Shipper> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_Shipper.class, shipperBPartner)
				.filter(new EqualsQueryFilter<I_M_Shipper>(I_M_Shipper.COLUMNNAME_C_BPartner_ID, shipperBPartner.getC_BPartner_ID()))
				.filter(new EqualsQueryFilter<I_M_Shipper>(I_M_Shipper.COLUMNNAME_AD_Client_ID, shipperBPartner.getAD_Client_ID()))
				.filter(new EqualsQueryFilter<I_M_Shipper>(I_M_Shipper.COLUMNNAME_IsActive, true));
		queryBuilder.orderBy().addColumn(de.metas.interfaces.I_M_Shipper.COLUMNNAME_IsDefault, false);

		final I_M_Shipper shipper = queryBuilder.create().first();
		if (shipper == null)
		{
			throw new AdempiereException("@NotFound@ @M_Shipper_ID@ (@C_BPartner_ID@: " + shipperBPartner.getValue() + ")");
		}

		return shipper;
		
	}
}
