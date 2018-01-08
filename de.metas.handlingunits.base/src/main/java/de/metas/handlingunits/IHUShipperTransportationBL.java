package de.metas.handlingunits;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Package;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;

public interface IHUShipperTransportationBL extends ISingletonService
{
	/**
	 * Adds given list of HUs to shipper transportation.
	 *
	 * This method adds only those HUs which are eligible (see {@link #isEligibleForAddingToShipperTransportation(I_M_HU)}).
	 *
	 * @param shipperTransportationId
	 * @param hus
	 */
	List<I_M_Package> addHUsToShipperTransportation(int shipperTransportationId, Collection<I_M_HU> hus);

	/**
	 * Generates Material Shipments from previously enqueued HUs to shipper transportation.
	 *
	 * NOTE: this method will not checked if the HU was added to a shipper transportation but it will just check if it's locked.
	 *
	 * @param ctx
	 * @param husQueryBuilder inital HUs query builder to be used; NOTE: this parameter will be changed in this method
	 */
	void generateShipments(Properties ctx, IHUQueryBuilder husQueryBuilder);

	/**
	 * Checks if given HU is suitable for adding to shipper transportation.
	 *
	 * @param hu
	 * @return true if HU is eligible for adding to shipper transportation.
	 */
	boolean isEligibleForAddingToShipperTransportation(I_M_HU hu);

	/**
	 * @param hu
	 * @return shipping packages for HU, filtered by it's package partner and location
	 */
	List<I_M_ShippingPackage> getShippingPackagesForHU(I_M_HU hu);

	/**
	 * @param hus
	 * @return shipper transportation document of given HUs. It's expected to be the same among the passed parameters (otherwise will throw {@link AdempiereException}).
	 */
	I_M_ShipperTransportation getCommonM_ShipperTransportationOrNull(Collection<I_M_HU> hus);
}
