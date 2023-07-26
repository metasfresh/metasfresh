/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.impl;

import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ShipperTransportationRepository
{
	public ShipperTransportationId create(@NonNull final CreateShipperTransportationRequest request)
	{
		final I_M_ShipperTransportation shipperTransportation = newInstance(I_M_ShipperTransportation.class);

		shipperTransportation.setAD_Org_ID(request.getOrgId().getRepoId());
		shipperTransportation.setM_Shipper_ID(request.getShipperId().getRepoId());
		shipperTransportation.setShipper_BPartner_ID(request.getShipperBPartnerAndLocationId().getBpartnerId().getRepoId());
		shipperTransportation.setShipper_Location_ID(request.getShipperBPartnerAndLocationId().getRepoId());
		shipperTransportation.setDateDoc(TimeUtil.asTimestamp(request.getShipDate()));

		saveRecord(shipperTransportation);

		return ShipperTransportationId.ofRepoId(shipperTransportation.getM_ShipperTransportation_ID());
	}
}
