package de.metas.shipping.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Optional;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_Shipper;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ITranslatableString;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;

public class ShipperDAO implements IShipperDAO
{
	@Override
	public I_M_Shipper getById(@NonNull final ShipperId id)
	{
		return loadOutOfTrx(id, I_M_Shipper.class);
	}

	@Override
	public ShipperId getShipperIdByShipperPartnerId(@NonNull final BPartnerId shipperPartnerId)
	{
		final ShipperId shipperId = Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_M_Shipper.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Shipper.COLUMNNAME_C_BPartner_ID, shipperPartnerId)
				.orderByDescending(I_M_Shipper.COLUMNNAME_IsDefault)
				.create()
				.firstId(ShipperId::ofRepoIdOrNull);
		if (shipperId == null)
		{
			throw new AdempiereException("@NotFound@ @M_Shipper_ID@ (@C_BPartner_ID@: " + shipperPartnerId + ")");
		}

		return shipperId;
	}

	@Override
	public ShipperId getDefault(final ClientId clientId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_Shipper.class)
				.addInArrayFilter(I_M_Shipper.COLUMNNAME_AD_Client_ID, clientId, ClientId.SYSTEM)
				.addEqualsFilter(I_M_Shipper.COLUMN_IsDefault, true)
				.orderBy(I_M_Shipper.COLUMNNAME_AD_Client_ID)
				.create()
				.firstIdOnly(ShipperId::ofRepoIdOrNull);
	}

	@Override
	public ITranslatableString getShipperName(@NonNull final ShipperId shipperId)
	{
		final I_M_Shipper shipper = getById(shipperId);
		return InterfaceWrapperHelper.getModelTranslationMap(shipper)
				.getColumnTrl(I_M_Shipper.COLUMNNAME_Name, shipper.getName());
	}

	@Override
	public Optional<ShipperId> getShipperIdByValue(final String value)
	{
		final ShipperId shipperId = Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_M_Shipper.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Shipper.COLUMNNAME_Value, value)
				.orderByDescending(I_M_Shipper.COLUMNNAME_IsDefault)
				.create()
				.firstIdOnly(ShipperId::ofRepoIdOrNull);

		return Optional.of(shipperId);
	}
}
