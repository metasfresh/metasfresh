package de.metas.freighcost;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.I_M_FreightCostShipper;
import org.springframework.stereotype.Repository;

import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class FreightCostShipperRepository
{
	public FreightCostShipper getByFreightCostAndShipper(
			@NonNull final FreightCostId freightCostId,
			@NonNull final ShipperId shipperId)
	{
		final I_M_FreightCostShipper record = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_FreightCostShipper.class)
				.addEqualsFilter(I_M_FreightCostShipper.COLUMNNAME_M_FreightCost_ID, freightCostId)
				.addEqualsFilter(I_M_FreightCostShipper.COLUMNNAME_M_Shipper_ID, shipperId)
				.create()
				.firstOnly(I_M_FreightCostShipper.class);
		return record != null ? toFreightCostShipper(record) : null;
	}

	private static FreightCostShipper toFreightCostShipper(@NonNull final I_M_FreightCostShipper record)
	{
		return FreightCostShipper.builder()
				.build();
	}

}
