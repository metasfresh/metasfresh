/*
 * #%L
 * de-metas-camel-ebay-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.ebay.processor;

import static de.metas.camel.ebay.EbayConstants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.ebay.processor.ProcessorHelper.getPropertyOrThrowError;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.camel.ebay.EbayImportOrdersRouteContext;
import de.metas.camel.ebay.EbayUtils;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.ebay.api.model.Order;
import de.metas.camel.externalsystems.ebay.api.model.ShippingStep;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;

/**
 * Processor to create an UpsertRequest to create a new BPartner for an ebay
 * order.
 * 
 * TODO: refactor to use builders?
 * 
 * @author Werner Gaulke
 *
 */
public class CreateBPartnerUpsertReqForEbayOrderProcessor implements Processor {
	
	protected Logger log = LoggerFactory.getLogger(getClass());	
	
	@Override
	public void process(final Exchange exchange) {
		
		final EbayImportOrdersRouteContext importOrdersRouteContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, EbayImportOrdersRouteContext.class);
		log.debug("Create BPartner for ebay order {}", importOrdersRouteContext.getOrder().getOrderId());
		
		final Order order = importOrdersRouteContext.getOrder();
		final String orgCode = importOrdersRouteContext.getOrgCode();

		
		// First, create bPartner, contact and location and map ebay values
		final String bPartnerIdentifier = EbayUtils.bPartnerIdentifier(order);

		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		final JsonRequestContact bpartnerContact = new JsonRequestContact();
		final JsonRequestLocation bpartnerLocation = new JsonRequestLocation();
		
		if(order.getBuyer().getTaxIdentifier() != null ) {
			bpartner.setVatId(order.getBuyer().getTaxIdentifier().getTaxpayerId());
		}

		// shipping location and contact
		if (order.getFulfillmentStartInstructions() != null && order.getFulfillmentStartInstructions().size() >= 1
				&& order.getFulfillmentStartInstructions().get(0).getShippingStep() != null) {

			ShippingStep shipTo = order.getFulfillmentStartInstructions().get(0).getShippingStep();

			bpartnerLocation.setAddress1(shipTo.getShipTo().getContactAddress().getAddressLine1());
			bpartnerLocation.setAddress2(shipTo.getShipTo().getContactAddress().getAddressLine2());
			bpartnerLocation.setCity(shipTo.getShipTo().getContactAddress().getCity());
			bpartnerLocation.setCountryCode(shipTo.getShipTo().getContactAddress().getCountry());
			bpartnerLocation.setPostal(shipTo.getShipTo().getContactAddress().getPostalCode());

			bpartnerContact.setEmail(shipTo.getShipTo().getEmail());
			bpartnerContact.setName(shipTo.getShipTo().getFullName());
			bpartnerContact.setPhone(shipTo.getShipTo().getPrimaryPhone().getPhoneNumber());

		} else {
			log.error("No address to ship to for ebay order {}", order.getOrderId());
		}

		// billing location
		final JsonRequestLocation billBPartnerLocation = new JsonRequestLocation();
		billBPartnerLocation.setCity(order.getBuyer().getTaxAddress().getCity());
		billBPartnerLocation.setCountryCode(order.getBuyer().getTaxAddress().getCountryCode());
		billBPartnerLocation.setPostal(order.getBuyer().getTaxAddress().getPostalCode());
		billBPartnerLocation.setDistrict(order.getBuyer().getTaxAddress().getStateOrProvince());

		
		//Second, create upsert request for json items.
		List<JsonRequestLocationUpsertItem> locationUpsertItems = new ArrayList<>();
		locationUpsertItems.add(JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(bPartnerIdentifier + "-location")
				.location(bpartnerLocation)
				.build());
		
		locationUpsertItems.add(JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(bPartnerIdentifier + "-billlocation")
				.location(billBPartnerLocation)
				.build());

		JsonRequestContactUpsertItem contactUpsertItem = JsonRequestContactUpsertItem.builder()
				.contact(bpartnerContact)
				.contactIdentifier(bPartnerIdentifier + "-contact")
				.build();

		
		final JsonRequestLocationUpsert locations = JsonRequestLocationUpsert.builder().requestItems(locationUpsertItems).build();
		final JsonRequestContactUpsert contacts = JsonRequestContactUpsert.builder().requestItem(contactUpsertItem).build();

		
		//Third, create composite and finalise items 
		final JsonRequestComposite upsertComposite = JsonRequestComposite.builder()
				.bpartner(bpartner)
				.locations(locations)
				.contacts(contacts)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		JsonRequestBPartnerUpsertItem bpartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(upsertComposite).build();

		final JsonRequestBPartnerUpsert upsertBPartner = JsonRequestBPartnerUpsert.builder().requestItem(bpartnerUpsertItem).build();
		
		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(upsertBPartner)
				.orgCode(orgCode).build();

		
		//Finally to in upsert.
		exchange.getIn().setBody(bpUpsertCamelRequest);
	}
}
