package de.metas.shipping.api;

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

import org.compiere.model.I_M_Package;

import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.ISingletonService;

public interface IShipperTransportationDAO extends ISingletonService
{
	List<I_M_ShippingPackage> retrieveShippingPackages(I_M_ShipperTransportation shipperTransportation);

	/**
	 * Retrieve all {@link I_M_ShippingPackage}s which are pointing to givem {@link I_M_Package}.
	 *
	 * @param mpackage
	 * @return
	 */
	List<I_M_ShippingPackage> retrieveShippingPackages(I_M_Package mpackage);

	<T extends I_M_ShipperTransportation> List<T> retrieveOpenShipperTransportations(Properties ctx, Class<T> clazz);

	ShipperTransportationId retrieveNextOpenShipperTransportationIdOrNull(ShipperId shipperId);
}
