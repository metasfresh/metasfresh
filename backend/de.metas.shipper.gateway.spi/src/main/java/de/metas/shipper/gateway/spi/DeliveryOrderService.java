package de.metas.shipper.gateway.spi;

import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import org.adempiere.util.lang.ITableRecordReference;

/*
 * #%L
 * de.metas.shipper.gateway.go
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface DeliveryOrderService
{
	String getShipperGatewayId();

	/**
	 * @return a reference to the internal {@code AD_Table_ID} and {@code Record_ID} of the record that backs the given {@code deliveryOrder}.
	 *         Note that the reference's {@code Record_ID} is coming from {@link DeliveryOrder#getId()}.
	 */
	ITableRecordReference toTableRecordReference(DeliveryOrder deliveryOrder);

	/**
	 * Assumes that there is a data record for the given {@code deliveryOrderRepoId}.
	 */
	DeliveryOrder getByRepoId(DeliveryOrderId deliveryOrderRepoId);

	/**
	 * Create or update the internal data record for the given {@code order}. The returned instance shall always have a {@code repoId > 0}.
	 */
	DeliveryOrder save(DeliveryOrder order);

}
