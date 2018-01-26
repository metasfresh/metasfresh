package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import org.springframework.ws.client.core.WebServiceTemplate;

import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderResponse;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3Util;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellen;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.ObjectFactory;
import lombok.NonNull;

/*
 * #%L
 * de.metas.vendor.gateway.mvs3
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

public class MSV3PurchaseOrderClient
{
	private static final String URL_SUFFIX_PLACE_PURCHASE_ORDER = "/bestellen";

	private static final int MSV3_MAX_QUANTITY_99999 = 99999;

	private final MSV3ClientConfig config;
	private final WebServiceTemplate webServiceTemplate;
	private final ObjectFactory objectFactory;

	public MSV3PurchaseOrderClient(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3PurchaseOrderRepository purchaseOrderRepo,
			@NonNull final MSV3ClientConfig config)
	{
		this.config = config;
		objectFactory = new ObjectFactory();
		webServiceTemplate = connectionFactory.createWebServiceTemplate(config);
	}

	public PurchaseOrderResponse placePurchaseOrder(@NonNull final PurchaseOrderRequest request)
	{
		final Bestellen bestellen = objectFactory.createBestellen();
		bestellen.setClientSoftwareKennung(MSV3Util.CLIENT_SOFTWARE_IDENTIFIER.get());

		final Bestellung bestellung = objectFactory.createBestellung();
		bestellung.setId(MSV3Util.createUniqueId());

		// TODO Auto-generated method stub
		return null;
	}

}
