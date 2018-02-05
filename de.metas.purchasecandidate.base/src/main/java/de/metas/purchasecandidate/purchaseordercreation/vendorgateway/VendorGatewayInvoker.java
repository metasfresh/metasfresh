package de.metas.purchasecandidate.purchaseordercreation.vendorgateway;

import java.util.Optional;

import org.compiere.Adempiere;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.vendor.gateway.api.VendorGatewayRegistry;
import de.metas.vendor.gateway.api.VendorGatewayService;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface VendorGatewayInvoker
{
	void addCandidate(PurchaseCandidate candidate);

	/**
	 * The {@code C_Order_ID} of the purchase order we just created.
	 */
	VendorGatewayStatus createAndComplete(int purchaseOrderId);

	public enum VendorGatewayStatus
	{
		NO_GATEWAY_SERVICE,

		SERVICE_ORDER_CREATED,

		SERVICE_ORDER_NEEDS_ATENTION,
	}

	public static VendorGatewayInvoker createForVendorId(final int vendorBPartnerId)
	{
		final VendorGatewayRegistry vendorGatewayRegistry = Adempiere.getBean(VendorGatewayRegistry.class);
		final Optional<VendorGatewayService> vendorGatewayService = vendorGatewayRegistry
				.getSingleVendorGatewayService(vendorBPartnerId);

		if (vendorGatewayService.isPresent())
		{
			return RealVendorGatewayInvoker.builder()
					.vendorBPartnerId(vendorBPartnerId)
					.vendorGatewayService(vendorGatewayService.get())
					.build();
		}
		return new NullVendorGatewayInvoker();
	}
}
