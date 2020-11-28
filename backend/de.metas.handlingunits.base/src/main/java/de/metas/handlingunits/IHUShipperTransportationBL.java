package de.metas.handlingunits;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.impl.AddTrackingInfosForInOutWithoutHUReq;
import de.metas.handlingunits.impl.CreatePackagesForInOutRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Package;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

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

public interface IHUShipperTransportationBL extends ISingletonService
{
	/**
	 * Adds given list of HUs to shipper transportation, by creating the needed M_Packages.
	 * <p>
	 * This method adds only those HUs which are eligible (see {@link #isEligibleForAddingToShipperTransportation(I_M_HU)}).
	 */
	List<I_M_Package> addHUsToShipperTransportation(ShipperTransportationId shipperTransportationId, Collection<I_M_HU> hus);

	/**
	 * Adds given list of InOuts to ShipperTransportation, by creating the needed M_Packages.
	 * This method adds only those InOuts which have no HUs and which don't already have a ShipperTransportation.
	 * <p>
	 * It is likely that you should use {@link #addHUsToShipperTransportation(ShipperTransportationId, Collection)} instead of this method.
	 */
	@NonNull
	ImmutableList<I_M_Package> addInOutWithoutHUToShipperTransportation(
			@NonNull final ShipperTransportationId shipperTransportationId,
			@NonNull final ImmutableList<CreatePackagesForInOutRequest> requests);

	/**
	 * Generates Material Shipments from previously enqueued HUs to shipper transportation.
	 * <p>
	 * NOTE: this method will not checked if the HU was added to a shipper transportation but it will just check if it's locked.
	 *
	 * @param husQueryBuilder inital HUs query builder to be used; NOTE: this parameter will be changed in this method
	 */
	void generateShipments(Properties ctx, IHUQueryBuilder husQueryBuilder);

	/**
	 * Checks if given HU is suitable for adding to shipper transportation.
	 *
	 * @return true if HU is eligible for adding to shipper transportation.
	 */
	boolean isEligibleForAddingToShipperTransportation(@Nullable I_M_HU hu);

	/**
	 * @return shipping packages for HU, filtered by it's package partner and location
	 */
	List<I_M_ShippingPackage> getShippingPackagesForHU(I_M_HU hu);

	/**
	 * @return shipper transportation document of given HUs. It's expected to be the same among the passed parameters (otherwise will throw {@link AdempiereException}).
	 */
	@Nullable
	I_M_ShipperTransportation getCommonM_ShipperTransportationOrNull(Collection<I_M_HU> hus);

	@NonNull
	ShipperTransportationId addTrackingInfosForInOutWithoutHU(AddTrackingInfosForInOutWithoutHUReq req);

	@NonNull
	I_M_ShipperTransportation getById(ShipperTransportationId shipperTransportationId);

	void processShipperTransportation(@NonNull final ShipperTransportationId shipperTransportationId);
}
