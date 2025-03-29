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

package de.metas.handlingunits.shipping;

import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOut;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class CreatePackagesForInOutRequest
{
	@NonNull
	I_M_InOut shipment;

	boolean processed;

	@Nullable
	List<PackageInfo> packageInfos;

	public static CreatePackagesForInOutRequest ofShipment(@NonNull final I_M_InOut shipment)
	{
		return CreatePackagesForInOutRequest.builder()
				.shipment(shipment)
				.processed(false)
				.packageInfos(null)
				.build();
	}

	public InOutId getShipmentId()
	{
		return InOutId.ofRepoId(shipment.getM_InOut_ID());
	}

	@Nullable
	public ShipperTransportationId getShipperTransportationId()
	{
		return ShipperTransportationId.ofRepoIdOrNull(shipment.getM_ShipperTransportation_ID());
	}
}
