/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl;

import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import org.adempiere.util.lang.ITableRecordReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DhlDeliveryOrderRepository implements DeliveryOrderRepository
{
	private static final Logger logger = LoggerFactory.getLogger(DhlDeliveryOrderRepository.class);

	@Override public String getShipperGatewayId()
	{
		return DhlConstants.SHIPPER_GATEWAY_ID;
	}

	@Override public ITableRecordReference toTableRecordReference(final DeliveryOrder deliveryOrder)
	{
		return null;
	}

	@Override public DeliveryOrder getByRepoId(final DeliveryOrderId deliveryOrderRepoId)
	{
		return null;
	}

	@Override public DeliveryOrder save(final DeliveryOrder order)
	{
		return null;
	}
}
