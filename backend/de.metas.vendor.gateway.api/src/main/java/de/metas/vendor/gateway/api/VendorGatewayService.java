package de.metas.vendor.gateway.api;

import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.order.LocalPurchaseOrderForRemoteOrderCreated;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated;

/*
 * #%L
 * de.metas.vendor.gateway.api
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

/**
 * Hint: obtain your instance(s) for a given vendor (if any!) via {@link VendorGatewayRegistry#getVendorGatewayServices(int)}.
 *
 */
public interface VendorGatewayService
{
	boolean isProvidedForVendor(int vendorId);

	AvailabilityResponse retrieveAvailability(AvailabilityRequest request);

	/**
	 * <b>IMPORTANT: </b> shall not throw an exception. If an exception occurs, it shall be included in the return value.
	 */
	RemotePurchaseOrderCreated placePurchaseOrder(PurchaseOrderRequest request);

	void associateLocalWithRemotePurchaseOrderId(LocalPurchaseOrderForRemoteOrderCreated localPurchaseOrderForRemoteOrderCreated);
}
