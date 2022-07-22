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

package de.metas.camel.externalsystems.ebay.processor.bpartner;

import static de.metas.camel.externalsystems.ebay.EbayConstants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.ebay.ProcessorHelper.getPropertyOrThrowError;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.ebay.EbayImportOrdersRouteContext;
import de.metas.camel.externalsystems.ebay.EbayUtils;
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
import de.metas.common.externalsystem.JsonExternalSystemEbayConfigMapping;
import de.metas.common.externalsystem.JsonExternalSystemEbayConfigMappings;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;

/**
 * Processor to create an UpsertRequest to create a new BPartner for an ebay
 * order.
 * 
 * @author Werner Gaulke
 *
 */
public class CreateBPartnerUpsertReqForEbayOrderProcessor implements Processor
{

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void process(final Exchange exchange)
	{

		final EbayImportOrdersRouteContext importOrdersRouteContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, EbayImportOrdersRouteContext.class);
		log.debug("Upsert BPartner for ebay order {}", importOrdersRouteContext.getOrder().getOrderId());
		
		//object to process
		final Order order = importOrdersRouteContext.getOrder();
		
		
		//determine customer group and get mapping.
		JsonExternalSystemEbayConfigMapping mapping = null;
		boolean isBusiness = false;
		if (order.getBuyer().getTaxIdentifier() != null) {
			mapping = getMatchingEbayMapping(importOrdersRouteContext.getEbayConfigMappings(), "business"); //business
			isBusiness = true;
		} else {
			mapping = getMatchingEbayMapping(importOrdersRouteContext.getEbayConfigMappings(), ""); //consumer
		}
		
		

		// prepare identifiers
		final String orgCode = importOrdersRouteContext.getOrgCode();

		final String bPartnerIdentifier = EbayUtils.bPartnerIdentifier(order);
		final String bPartnerShipToLocationIdentifier = bPartnerIdentifier + "-shipTo";
		final String bParnterBillLocationIdentifier = bPartnerIdentifier + "-billTo";
		final String bPartnerContactIdentifier = bPartnerIdentifier + "-contact";

		// add identifiers to context for reuse.
		importOrdersRouteContext.setShippingBPLocationExternalId(bPartnerShipToLocationIdentifier);
		if(isBusiness) {
			importOrdersRouteContext.setBillingBPLocationExternalId(bParnterBillLocationIdentifier);
		}

		// First, create bPartner, contact and location and map ebay values
		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		final JsonRequestContact bpartnerContact = new JsonRequestContact();
		final JsonRequestLocation bpartnerLocation = new JsonRequestLocation();
		final JsonRequestLocation billBPartnerLocation = new JsonRequestLocation();



		// get names / address for bpartner, contact and location
		if (order.getBuyer() != null
				&& order.getFulfillmentStartInstructions() != null
				&& order.getFulfillmentStartInstructions().size() >= 1
				&& order.getFulfillmentStartInstructions().get(0).getShippingStep() != null)
		{
			//get customer details
			ShippingStep shipTo = order.getFulfillmentStartInstructions().get(0).getShippingStep();

			//bpartner
			bpartner.setName(shipTo.getShipTo().getFullName());
			bpartner.setCompanyName(shipTo.getShipTo().getCompanyName());
			bpartner.setUrl("https://www.ebay.de/usr/"+ order.getBuyer().getUsername());
			bpartner.setCustomer(true);

			//contact
			bpartnerContact.setEmail(shipTo.getShipTo().getEmail());
			bpartnerContact.setName(shipTo.getShipTo().getFullName());
			bpartnerContact.setPhone(shipTo.getShipTo().getPrimaryPhone().getPhoneNumber());

			//shipping
			bpartnerLocation.setBpartnerName(shipTo.getShipTo().getFullName());
			bpartnerLocation.setAddress1(shipTo.getShipTo().getContactAddress().getAddressLine1());
			bpartnerLocation.setAddress2(shipTo.getShipTo().getContactAddress().getAddressLine2());
			bpartnerLocation.setCity(shipTo.getShipTo().getContactAddress().getCity());
			bpartnerLocation.setCountryCode(shipTo.getShipTo().getContactAddress().getCountryCode());
			bpartnerLocation.setPostal(shipTo.getShipTo().getContactAddress().getPostalCode());
			bpartnerLocation.setShipToDefault(true);

			// billing
			if (isBusiness) {
				bpartner.setVatId(order.getBuyer().getTaxIdentifier().getTaxpayerId());

				billBPartnerLocation.setCity(order.getBuyer().getTaxAddress().getCity());
				billBPartnerLocation.setCountryCode(order.getBuyer().getTaxAddress().getCountryCode());
				billBPartnerLocation.setPostal(order.getBuyer().getTaxAddress().getPostalCode());
				billBPartnerLocation.setDistrict(order.getBuyer().getTaxAddress().getStateOrProvince());
				billBPartnerLocation.setBillTo(true);
			} else {
				bpartnerLocation.setBillTo(true);
			}

		}
		else
		{
			log.error("No address to ship to for ebay order {}", order.getOrderId());
		}


		

		// Second, create upsert request for location and contact.
		final List<JsonRequestLocationUpsertItem> locationUpsertItems = new ArrayList<>();
		locationUpsertItems.add(JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(bPartnerShipToLocationIdentifier)
				.location(bpartnerLocation)
				.build());

		if(isBusiness) {
			locationUpsertItems.add(JsonRequestLocationUpsertItem.builder()
											.locationIdentifier(bParnterBillLocationIdentifier)
											.location(billBPartnerLocation)
											.build());
		}

		
		final JsonRequestLocationUpsert locations = JsonRequestLocationUpsert.builder()
				.requestItems(locationUpsertItems)
				.syncAdvise( mapping != null ? mapping.getBPartnerLocationSyncAdvice() : SyncAdvise.CREATE_OR_MERGE)
				.build();

		
		final JsonRequestContactUpsertItem contactUpsertItem = JsonRequestContactUpsertItem.builder()
				.contact(bpartnerContact)
				.contactIdentifier(bPartnerContactIdentifier)
				.build();

		final JsonRequestContactUpsert contacts = JsonRequestContactUpsert.builder()
				.syncAdvise( mapping != null ? mapping.getBPartnerLocationSyncAdvice() : SyncAdvise.CREATE_OR_MERGE)
				.requestItem(contactUpsertItem)
				.build();

		// Third, create composite and finalise items
		final JsonRequestComposite upsertComposite = JsonRequestComposite.builder()
				.bpartner(bpartner)
				.locations(locations)
				.contacts(contacts)
				.syncAdvise( mapping != null ? mapping.getBPartnerSyncAdvice() : SyncAdvise.CREATE_OR_MERGE)
				.build();

		final JsonRequestBPartnerUpsertItem bpartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.bpartnerComposite(upsertComposite)
				.build();

		final JsonRequestBPartnerUpsert upsertBPartner = JsonRequestBPartnerUpsert.builder().requestItem(bpartnerUpsertItem).build();

		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(upsertBPartner)
				.orgCode(orgCode).build();

		// Finally to in upsert.
		exchange.getIn().setBody(bpUpsertCamelRequest);
	}
	
	
	@Nullable
	private JsonExternalSystemEbayConfigMapping getMatchingEbayMapping(
			@Nullable final JsonExternalSystemEbayConfigMappings ebayConfigMappings,
			@Nullable final String customerGroup)
	{
		if (ebayConfigMappings == null
				|| Check.isEmpty(ebayConfigMappings.getJsonExternalSystemEbayConfigMappingList())
				|| customerGroup == null)
		{
			return null;
		}

		return ebayConfigMappings.getJsonExternalSystemEbayConfigMappingList()
				.stream()
				.filter(mapping -> mapping.isGroupMatching(customerGroup))
				.min(Comparator.comparingInt(JsonExternalSystemEbayConfigMapping::getSeqNo))
				.orElse(null);
	}
	
}
