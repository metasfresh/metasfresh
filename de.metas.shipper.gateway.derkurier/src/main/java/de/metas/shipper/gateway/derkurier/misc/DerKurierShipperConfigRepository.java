package de.metas.shipper.gateway.derkurier.misc;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.springframework.stereotype.Repository;

import de.metas.shipper.gateway.derkurier.model.I_DerKurier_Shipper_Config;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class DerKurierShipperConfigRepository
{
	public DerKurierShipperConfig retrieveConfigForShipperId(final int shipperId)
	{
		final I_DerKurier_Shipper_Config shipperConfigRecord = Services.get(IQueryBL.class).createQueryBuilder(I_DerKurier_Shipper_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DerKurier_Shipper_Config.COLUMN_M_Shipper_ID, shipperId)
				.create()
				.firstOnly(I_DerKurier_Shipper_Config.class);

		final DerKurierShipperConfig shipperConfig = DerKurierShipperConfig.builder()
				.restApiBaseUrl(shipperConfigRecord.getAPIServerBaseURL())
				.customerNumber(shipperConfigRecord.getDK_CustomerNumber()).build();

		return shipperConfig;
	}

}
