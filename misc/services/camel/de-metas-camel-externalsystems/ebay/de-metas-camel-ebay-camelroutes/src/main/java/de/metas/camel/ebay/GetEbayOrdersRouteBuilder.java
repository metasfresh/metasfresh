package de.metas.camel.ebay;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.common.bpartner.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import io.swagger.client.api.OrderApi;
import io.swagger.client.model.Order;
import io.swagger.client.model.OrderSearchPagedCollection;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Route to fetch ebay orders and put them as order line candidates into metasfresh.
 * 
 * 
 * @author Werner Gaulke
 *
 */
@Component
public class GetEbayOrdersRouteBuilder extends RouteBuilder{
	
	private static final String EXT_ID_PREFIX = "ebay";
	
	public static final String GET_ORDERS_ROUTE_ID = "Ebay-getOrders";
	public static final String PROCESS_ORDER_ROUTE_ID = "Ebay-processOrder";

	public static final String GET_ORDERS_PROCESSOR_ID = "GetEbayOrdersProcessorId";
	public static final String CREATE_ESR_QUERY_REQ_PROCESSOR_ID = "CreateBPartnerESRQueryProcessorId";
	public static final String CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID = "CreateBPartnerUpsertReqProcessorId";

	

	@Override
	public void configure() throws Exception {
		
		log.trace("Configure ebay order route");
		
		from("direct:Ebay-GetOrders")
			.routeId(GET_ORDERS_ROUTE_ID)
			.log("Route invoked")
			.process(exchange -> {
				
				log.trace("Preparing call to ebay order api");
				
				// get configs from route, configured in MF
				final var request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
				final var apiKey = request.getParameters().get(ExternalSystemConstants.PARAM_API_KEY);
				final var tenant = request.getParameters().get(ExternalSystemConstants.PARAM_TENANT);
				final var updatedAfter = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER);
				final var basePath = request.getParameters().get(ExternalSystemConstants.PARAM_BASE_PATH);

				
				log.trace("Executing call for tenant {}", tenant);
				
				
				final OrderApi api = new OrderApi();
				String fieldGroups = null;
			    String filter = null;
			    String limit = "20";
			    String offset = null;
			    String orderIds = null;
			    OrderSearchPagedCollection response = api.getOrders(fieldGroups, filter, limit, offset, orderIds);
				
			    List<Order> orders = response.getOrders();
				
				exchange.getIn().setHeader(ExternalSystemCamelConstants.HEADER_ORG_CODE, request.getOrgCode());
				exchange.getIn().setBody(orders);
			})
			.split(body())
			// create bpartners in metasfresh
			.process(exchange -> {
//
				
				log.trace("Processing single order");
				
				final var order = exchange.getIn().getBody(Order.class);
				final var orgCode = exchange.getIn().getHeader(ExternalSystemCamelConstants.HEADER_ORG_CODE, String.class);
				
				
				final BPartners bpartners = createPartientAndCareTaker(orgCode, order);

//				exchange.getIn().setHeader("patientBPartners", bpartners);
//				exchange.getIn().setBody(bpartners.toUpsertRequest());
			})
			//.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_CAMEL_URI + "}}") // no need to worry about metasfresh-URLs, API-Keys etc

			// create bpartner-relations in metasfresh
			.process(exchange -> {

//				final var orgCode = exchange.getIn().getHeader(ExternalSystemCamelConstants.HEADER_ORG_CODE, String.class);
//				final var bPartners = exchange.getIn().getHeader("patientBPartners", BPartners.class);
//
//				final var relationsUpsertBuilder = JsonRequestBPRelationsUpsert.builder()
//						.orgCode(orgCode);
//
//				final var relatesTo = ImmutableList.<JsonRequestBPRelationTarget>builder();
//				for (final var careTaker : bPartners.getCareTakers())
//				{
//					final var locationIdentifier = careTaker.getBpartnerComposite().getLocationsNotNull()
//							.getRequestItems().get(0).getLocationIdentifier();
//					
//					relatesTo.add(JsonRequestBPRelationTarget.builder()
//							.name("name") // TODO do we really need this to be mandatory?
//							.role(JsonBPRelationRole.CareTaker)
//							.targetBpartnerIdentifier(careTaker.getBpartnerIdentifier())
//							.targetLocationIdentifier(locationIdentifier)
//							.build());
//				}
//				relationsUpsertBuilder.relatesTo(relatesTo.build());
//
//				final var bpRelationsCamelRequest = BPRelationsCamelRequest.builder()
//						.bpartnerIdentifier(bPartners.getPatient().getBpartnerIdentifier())
//						.jsonRequestBPRelationsUpsert(relationsUpsertBuilder.build())
//						.build();
//				exchange.getIn().setBody(bpRelationsCamelRequest);
			});
			//.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPRELATION_CAMEL_URI + "}}")
		
	}
	
	
	
	private BPartners createPartientAndCareTaker(@NonNull final String orgCode, @NonNull final Order order)
	{
		final var result = BPartners.builder();
		
		//result.customer(createPatientBPartner(orgCode, order));

		return result.build();
	}
	
	private JsonRequestBPartnerUpsertItem createPatientBPartner(final String orgCode, final Order patient)
	{
		/*
		final var bPartner = new JsonRequestBPartner();
		bPartner.setName(patient.getFirstName() + " " + patient.getLastName());
		//bPartner.setMemo(patient.getComment()); TODO
		bPartner.setCustomer(true);

		final var hasExtraBillToAddress = patient.getBillingAddress() != null;
		final var hasExtraShipToAddress = patient.getDeliveryAddress() != null;

		final var locationUpsertBuilder = JsonRequestLocationUpsert.builder();

		// "normal" address
		{
			final var location = new JsonRequestLocation();
			location.setAddress1(patient.getAddress());
			location.setAddress2(patient.getAdditionalAddress());
			location.setAddress3(patient.getAdditionalAddress2());
			location.setPostal(patient.getPostalCode());
			location.setCity(patient.getCity());
			location.setBillToDefault(!hasExtraBillToAddress);
			location.setBillTo(!hasExtraBillToAddress);
			location.setShipToDefault(!hasExtraShipToAddress);
			location.setShipTo(!hasExtraShipToAddress);

			locationUpsertBuilder.requestItem(JsonRequestLocationUpsertItem.builder()
					.locationIdentifier("ext-" + patient.getId().toString())
					.location(location).build());
		}
		if (hasExtraBillToAddress)
		{
			final var billingAddress = patient.getBillingAddress();
			final var location = new JsonRequestLocation();
			location.setAddress1(billingAddress.getAddress());
			location.setPostal(billingAddress.getPostalCode());
			location.setCity(billingAddress.getCity());
			location.setBpartnerName((billingAddress.getTitle() + " " + billingAddress.getName()).trim());
			location.setBillToDefault(true);
			location.setBillTo(true);

			locationUpsertBuilder.requestItem(JsonRequestLocationUpsertItem.builder()
					.locationIdentifier("ext-billingAddress_" + patient.getId().toString())
					.location(location).build());
		}
		if (hasExtraShipToAddress)
		{
			final var deliveryAddress = patient.getDeliveryAddress();
			final var location = new JsonRequestLocation();
			location.setAddress1(deliveryAddress.getAddress());
			location.setPostal(deliveryAddress.getPostalCode());
			location.setCity(deliveryAddress.getCity());
			location.setBpartnerName((deliveryAddress.getTitle() + " " + deliveryAddress.getName()).trim());
			location.setBillToDefault(true);
			location.setBillTo(true);

			locationUpsertBuilder.requestItem(JsonRequestLocationUpsertItem.builder()
					.locationIdentifier("ext-deliveryAddress_" + patient.getId().toString())
					.location(location).build());
		}

		final var contact = new JsonRequestContact();
		// contact.setTitle(patient.getTitle()); TODO
		contact.setFirstName(patient.getFirstName());
		contact.setLastName(patient.getLastName());
		//contact.setBirthDay(patient.getBirthday()); TODO
		contact.setEmail(patient.getEmail());
		contact.setFax(patient.getFax());
		contact.setPhone(patient.getPhone());
		contact.setMobilePhone(patient.getMobilePhone());
		contact.setDefaultContact(true);

		// patient.getGender(); TODO needed?
		 * *
		 

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier("ext-" + patient.getId().toString())
				.bpartnerComposite(JsonRequestComposite.builder()
						.orgCode(orgCode)
						.bpartner(bPartner)
						.locations(locationUpsertBuilder.build())
						.contacts(JsonRequestContactUpsert.builder().requestItem(JsonRequestContactUpsertItem.builder()
								.contactIdentifier("ext-" + patient.getId().toString())
								.contact(contact)
								.build())
								.build())
						.build())
				.build();
				
		*/
		return null;
	}
	
	
	@Builder
	@Value
	private static class BPartners
	{
		@NonNull
		JsonRequestBPartnerUpsertItem billingCustomer;
		
		@NonNull
		JsonRequestBPartnerUpsertItem shippingCustomer;


		public JsonRequestBPartnerUpsert toUpsertRequest()
		{
			return JsonRequestBPartnerUpsert.builder().requestItem(billingCustomer).requestItem(shippingCustomer).build();
		}
	}

}
