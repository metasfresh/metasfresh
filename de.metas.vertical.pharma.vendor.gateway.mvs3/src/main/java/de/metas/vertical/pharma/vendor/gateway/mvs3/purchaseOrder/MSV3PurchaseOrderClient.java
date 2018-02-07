package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreated.RemotePurchaseOrderCreatedBuilder;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreatedItem;
import de.metas.vendor.gateway.api.order.RemotePurchaseOrderCreatedItem.RemotePurchaseOrderCreatedItemBuilder;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ClientBase;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3Util;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3ClientException;
import de.metas.vertical.pharma.vendor.gateway.mvs3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung_Transaction;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Auftragsart;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellen;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellenResponse;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Liefervorgabe;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Msv3FaultInfo;
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

	@Builder
	private MSV3PurchaseOrderClient(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfig config)
	{
		super(connectionFactory, config);

		this.objectFactory = new ObjectFactory();
	}

	public RemotePurchaseOrderCreated placePurchaseOrder(@NonNull final PurchaseOrderRequest request)
	{
		final Bestellung bestellung = createBestellungWithOneAuftrag(request);

		final ImmutableMap<BestellungPosition, PurchaseOrderRequestItem> bestellungPosition2RequestItem = //
				Maps.uniqueIndex(request.getOrderLines(), item -> createBestellungPosition(item));

		final MSV3PurchaseOrderTransaction purchaseTransaction = //
				MSV3PurchaseOrderTransaction.builder()
						.orgId(request.getOrgId())
						.bestellung(bestellung)
						.build();

		bestellung.getAuftraege().get(0).getPositionen().addAll(bestellungPosition2RequestItem.keySet());

		performOrdering(bestellung, purchaseTransaction);

		final I_MSV3_Bestellung_Transaction purchaseTransactionRecord = purchaseTransaction.store();

		final RemotePurchaseOrderCreatedBuilder responseBuilder = RemotePurchaseOrderCreated.builder()
				.transactionRecordId(purchaseTransactionRecord.getMSV3_Bestellung_Transaction_ID())
				.transactionTableName(I_MSV3_Bestellung_Transaction.Table_Name)
				.exception(purchaseTransaction.getExceptionOrNull());

		final List<RemotePurchaseOrderCreatedItem> purchaseOrderResponseItems = //
				createResponseItems(bestellungPosition2RequestItem, purchaseTransaction);

		responseBuilder.purchaseOrderResponseItems(purchaseOrderResponseItems);

		return responseBuilder.build();
	}

	private List<RemotePurchaseOrderCreatedItem> createResponseItems(
			@NonNull final ImmutableMap<BestellungPosition, PurchaseOrderRequestItem> bestellungPosition2RequestItem,
			@NonNull final MSV3PurchaseOrderTransaction purchaseTransaction)
	{
		final Bestellung bestellung = purchaseTransaction.getBestellung();

		final List<RemotePurchaseOrderCreatedItem> purchaseOrderResponseItems = new ArrayList<>();
		final List<BestellungAntwortAuftrag> auftraege = purchaseTransaction.getBestellungAntwort().getAuftraege();
		for (int auftragIdx = 0; auftragIdx < auftraege.size(); auftragIdx++)
		{
			final BestellungAuftrag auftrag = bestellung.getAuftraege().get(auftragIdx);
			final BestellungAntwortAuftrag auftragAntwort = auftraege.get(auftragIdx);

			final List<BestellungPosition> positionen = auftrag.getPositionen();
			final List<BestellungAntwortPosition> antwortPositionen = auftragAntwort.getPositionen();

			for (int positionIdx = 0; positionIdx < antwortPositionen.size(); positionIdx++)
			{
				final BestellungPosition bestellungPosition = positionen.get(positionIdx);

				final RemotePurchaseOrderCreatedItemBuilder builder = RemotePurchaseOrderCreatedItem.builder()
						.remotePurchaseOrderId(auftragAntwort.getId())
						.correspondingRequestItem(bestellungPosition2RequestItem.get(bestellungPosition));

				final BestellungAntwortPosition bestellungAntwortPosition = antwortPositionen.get(positionIdx);
				final List<BestellungAnteil> anteile = bestellungAntwortPosition.getAnteile();
				for (final BestellungAnteil anteil : anteile)
				{
					if (anteil.getMenge() <= 0)
					{
						continue;
					}

					builder
							.confirmedDeliveryDate(MSV3Util.toDateOrNull(anteil.getLieferzeitpunkt()))
							.confirmedOrderQuantity(new BigDecimal(anteil.getMenge()));
					purchaseOrderResponseItems.add(builder.build());
				}
			}
		}
		return purchaseOrderResponseItems;
	}

	private Bestellung createBestellungWithOneAuftrag(@NonNull final PurchaseOrderRequest request)
	{
		final Bestellung bestellung = objectFactory.createBestellung();
		bestellung.setId(MSV3Util.createUniqueId());

		final int supportId = MSV3Util.retrieveNextSupportId();
		bestellung.setBestellSupportId(supportId);

		final BestellungAuftrag bestellungAuftrag = objectFactory.createBestellungAuftrag();
		bestellungAuftrag.setAuftragsart(Auftragsart.NORMAL);
		bestellungAuftrag.setAuftragskennung(Integer.toString(supportId));
		bestellungAuftrag.setAuftragsSupportID(supportId);

		bestellungAuftrag.setId(MSV3Util.createUniqueId());

		bestellung.getAuftraege().add(bestellungAuftrag);

		return bestellung;
	}

	private BestellungPosition createBestellungPosition(final PurchaseOrderRequestItem purchaseOrderRequestItem)
	{
		final BestellungPosition bestellungPosition = objectFactory.createBestellungPosition();
		bestellungPosition.setLiefervorgabe(Liefervorgabe.NORMAL);

		final ProductAndQuantity productAndQuantity = purchaseOrderRequestItem.getProductAndQuantity();
		bestellungPosition.setMenge(MSV3Util.extractMenge(productAndQuantity));
		bestellungPosition.setPzn(MSV3Util.extractPZN(productAndQuantity));

		return bestellungPosition;
	}

	private void performOrdering(
			@NonNull final Bestellung bestellung,
			@NonNull final MSV3PurchaseOrderTransaction purchaseTransaction)
	{
		try
		{
			final Bestellen bestellen = objectFactory.createBestellen();
			bestellen.setClientSoftwareKennung(MSV3Util.CLIENT_SOFTWARE_IDENTIFIER.get());
			bestellen.setBestellung(bestellung);

			final BestellenResponse bestellenResponse = sendAndReceive(
					objectFactory.createBestellen(bestellen), BestellenResponse.class);

			final BestellungAntwort bestellungAntwort = bestellenResponse.getReturn();

			purchaseTransaction.setBestellungAntwort(bestellungAntwort);

			final List<BestellungAntwortAuftrag> auftraege = bestellungAntwort.getAuftraege();
			Check.errorIf(auftraege.size() != 1,
					"We send 1 BestellungAuftrag, but received {} BestellungAntwortAuftrag", auftraege.size());

			final BestellungAntwortAuftrag bestellungAntwortAuftrag = auftraege.get(0);

			// bestellungAntwortAuftrag.getAuftragsart();
			final Msv3FaultInfo auftragsfehler = bestellungAntwortAuftrag.getAuftragsfehler();
			if (auftragsfehler != null)
			{
				purchaseTransaction.setFaultInfo(auftragsfehler);
			}
		}
		catch (final Msv3ClientException msv3ClientException)
		{
			purchaseTransaction.setFaultInfo(msv3ClientException.getMsv3FaultInfo());
		}
		catch (final Exception e)
		{
			purchaseTransaction.setOtherException(e);
		}
	}

	@Override
	public String getUrlSuffix()
	{
		return URL_SUFFIX_PLACE_PURCHASE_ORDER;
	}
}
