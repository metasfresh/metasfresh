package de.metas.camel.ebay.processor;

import static de.metas.camel.ebay.EbayConstants.EXTERNAL_ID_PREFIX;
import static de.metas.camel.ebay.EbayConstants.ROUTE_PROPERTY_ORG_CODE;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

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
	
	
	@Override
	public void process(final Exchange exchange) {
		final Order order = exchange.getIn().getBody(Order.class);
		
		if (order == null)
		{
			throw new RuntimeException("Empty body!");
		}

		final String orgCode = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_ORG_CODE, String.class);

		// create bPartner request items based on ebay field mapping.
		final String bPartnerIdentifier = EbayUtils.bPartnerIdentifier(order);

		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		final JsonRequestContact bpartnerContact = new JsonRequestContact();
		final JsonRequestLocation bpartnerLocation = new JsonRequestLocation();

		bpartner.setVatId(order.getBuyer().getTaxIdentifier().getTaxpayerId());

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
			// LOG error

		}

		// billing location
		final JsonRequestLocation billBPartnerLocation = new JsonRequestLocation();

		billBPartnerLocation.setCity(order.getBuyer().getTaxAddress().getCity());
		billBPartnerLocation.setCountryCode(order.getBuyer().getTaxAddress().getCountryCode());
		billBPartnerLocation.setPostal(order.getBuyer().getTaxAddress().getPostalCode());
		billBPartnerLocation.setDistrict(order.getBuyer().getTaxAddress().getStateOrProvince());

		// prepare usert requests.
		List<JsonRequestLocationUpsertItem> locationRequestItems = new ArrayList<>();
		locationRequestItems.add(JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(bPartnerIdentifier + "-location")
				.location(bpartnerLocation)
				.build());
		
		locationRequestItems.add(JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(bPartnerIdentifier + "-billlocation")
				.location(billBPartnerLocation)
				.build());

		JsonRequestContactUpsertItem contactItems = JsonRequestContactUpsertItem.builder()
				.contact(bpartnerContact)
				.contactIdentifier(bPartnerIdentifier + "-contact")
				.build();

		
		final JsonRequestLocationUpsert locations = JsonRequestLocationUpsert.builder().requestItems(locationRequestItems).build();
		final JsonRequestContactUpsert contacts = JsonRequestContactUpsert.builder().requestItem(contactItems).build();
		final JsonRequestComposite upsertComposite = JsonRequestComposite.builder()
				.bpartner(bpartner)
				.locations(locations)
				.contacts(contacts)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		JsonRequestBPartnerUpsertItem bpartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder().bpartnerComposite(upsertComposite).build();

		final JsonRequestBPartnerUpsert upsertBPartner = JsonRequestBPartnerUpsert.builder().requestItem(bpartnerUpsertItem).build();
		
		final BPUpsertCamelRequest bpUpsertCamelRequest = BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(upsertBPartner)
				.orgCode(orgCode).build();

		exchange.getIn().setBody(bpUpsertCamelRequest);
	}
}
