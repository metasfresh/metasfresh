package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import java.util.List;

import org.adempiere.util.Check;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderResponse;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientBase;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3Util;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Auftragsart;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellen;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellenResponse;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Liefervorgabe;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.ObjectFactory;
import lombok.Builder;
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

public class MSV3PurchaseOrderClient extends MSV3ClientBase
{
	private static final String URL_SUFFIX_PLACE_PURCHASE_ORDER = "/bestellen";

	private final ObjectFactory objectFactory;

	private final MSV3PurchaseOrderRepository purchaseOrderRepo;

	@Builder
	private MSV3PurchaseOrderClient(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config,
			@NonNull final MSV3PurchaseOrderRepository purchaseOrderRepo)
	{
		super(connectionFactory, config);

		this.purchaseOrderRepo = purchaseOrderRepo;
		this.objectFactory = new ObjectFactory();
	}

	public PurchaseOrderResponse placePurchaseOrder(@NonNull final PurchaseOrderRequest request)
	{

		final Bestellung bestellung = objectFactory.createBestellung();
		bestellung.setId(MSV3Util.createUniqueId());

		final MSV3PurchaseOrder msv3Order = purchaseOrderRepo.retrieveOrCreate(request.getOrderId());
		bestellung.setBestellSupportId(msv3Order.getSupportId());

		final BestellungAuftrag bestellungAuftrag = objectFactory.createBestellungAuftrag();
		bestellungAuftrag.setAuftragsart(Auftragsart.NORMAL);
		bestellungAuftrag.setAuftragskennung(Integer.toString(msv3Order.getSupportId()));
		bestellungAuftrag.setAuftragsSupportID(msv3Order.getSupportId());
		bestellungAuftrag.setGebindeId(Integer.toString(msv3Order.getSupportId()));
		bestellungAuftrag.setId(MSV3Util.createUniqueId());

		bestellung.getAuftraege().add(bestellungAuftrag);

		for (final ProductAndQuantity orderLine : request.getOrderLines())
		{
			final BestellungPosition bestellungPosition = objectFactory.createBestellungPosition();
			bestellungPosition.setLiefervorgabe(Liefervorgabe.NORMAL);

			bestellungPosition.setMenge(MSV3Util.extractMenge(orderLine));
			bestellungPosition.setPzn(MSV3Util.extractPZN(orderLine));

			bestellungAuftrag.getPositionen().add(bestellungPosition);
		}

		final Bestellen bestellen = objectFactory.createBestellen();
		bestellen.setClientSoftwareKennung(MSV3Util.CLIENT_SOFTWARE_IDENTIFIER.get());
		bestellen.setBestellung(bestellung);

		final BestellenResponse bestellenResponse = sendAndReceive(
				objectFactory.createBestellen(bestellen), BestellenResponse.class);

		final BestellungAntwort bestellungAntwort = bestellenResponse.getReturn();
		final List<BestellungAntwortAuftrag> auftraege = bestellungAntwort.getAuftraege();
		Check.errorIf(auftraege.size() != 1, "We send 1 BestellungAuftrag, but received {} BestellungAntwortAuftrag", auftraege.size());


		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUrlSuffix()
	{
		return URL_SUFFIX_PLACE_PURCHASE_ORDER;
	}
}
