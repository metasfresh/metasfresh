/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.model;

import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.shipping.CarrierProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Carrier_Product_Service_Alloc;
import org.springframework.stereotype.Repository;

@Repository
public class CarrierProductServiceAllocRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public boolean exists(@NonNull final CarrierProductId carrierProductId, @NonNull final CarrierServiceId serviceId)
	{
		return queryBL.createQueryBuilder(I_Carrier_Product_Service_Alloc.class)
				.addEqualsFilter(I_Carrier_Product_Service_Alloc.COLUMNNAME_Carrier_Product_ID, carrierProductId)
				.addEqualsFilter(I_Carrier_Product_Service_Alloc.COLUMNNAME_Carrier_Service_ID, serviceId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch(); // at most one active row per pair — enforced by carrier_product_service_alloc_active_uidx
	}

	public void save(@NonNull final CarrierProductId carrierProductId, @NonNull final CarrierServiceId serviceId)
	{
		final I_Carrier_Product_Service_Alloc record = InterfaceWrapperHelper.newInstance(I_Carrier_Product_Service_Alloc.class);
		record.setCarrier_Product_ID(carrierProductId.getRepoId());
		record.setCarrier_Service_ID(serviceId.getRepoId());
		InterfaceWrapperHelper.saveRecord(record);
	}
}
