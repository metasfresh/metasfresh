package de.metas.shipper.gateway.dpd;

import com.dpd.common.service.types.shipmentservice._3.Address;
import com.dpd.common.service.types.shipmentservice._3.GeneralShipmentData;
import com.dpd.common.service.types.shipmentservice._3.Parcel;
import com.dpd.common.service.types.shipmentservice._3.Pickup;
import com.dpd.common.service.types.shipmentservice._3.PrintOptions;
import com.dpd.common.service.types.shipmentservice._3.ProductAndServiceData;
import com.dpd.common.service.types.shipmentservice._3.ShipmentResponse;
import com.dpd.common.service.types.shipmentservice._3.ShipmentServiceData;
import com.dpd.common.service.types.shipmentservice._3.StoreOrders;
import com.dpd.common.service.types.shipmentservice._3.StoreOrdersResponse;
import com.dpd.common.service.types.shipmentservice._3.StoreOrdersResponseType;
import com.dpd.common.ws.authentication.v2_0.types.Authentication;
import com.dpd.common.ws.authentication.v2_0.types.ObjectFactory;
import com.dpd.common.ws.loginservice.v2_0.types.GetAuth;
import com.dpd.common.ws.loginservice.v2_0.types.GetAuthResponse;
import com.dpd.common.ws.loginservice.v2_0.types.Login;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Test1
{
	private static final String DELIS_ID = "sandboxdpd";
	private static final String DELIS_PASSWORD = "a";
	private static final String MESSAGE_LANGUAGE = "en_EN";

	public static final String LOGIN_SERVICE_API_URL = "https://public-ws-stage.dpd.com/services/LoginService/V2_0/";
	public static final String SHIPMENT_SERVICE_API_URL = "https://public-ws-stage.dpd.com/services/ShipmentService/V3_2/";

	private WebServiceTemplate webServiceTemplate;
	private com.dpd.common.ws.loginservice.v2_0.types.ObjectFactory loginServiceOF;
	private com.dpd.common.service.types.shipmentservice._3.ObjectFactory shipmentServiceOF;

	@BeforeEach
	private void beforeEach()
	{
		webServiceTemplate = createWebServiceTemplate();
		loginServiceOF = new com.dpd.common.ws.loginservice.v2_0.types.ObjectFactory();
		shipmentServiceOF = new com.dpd.common.service.types.shipmentservice._3.ObjectFactory();
	}

	@Test
	void _1_testLogin() throws IOException
	{

		final GetAuth getAuthValue = loginServiceOF.createGetAuth();
		getAuthValue.setDelisId(DELIS_ID);
		getAuthValue.setPassword(DELIS_PASSWORD);
		getAuthValue.setMessageLanguage(MESSAGE_LANGUAGE);

		final JAXBElement<GetAuth> getAuth = loginServiceOF.createGetAuth(getAuthValue);

		System.out.println(elementToString(getAuth, webServiceTemplate.getMarshaller()));
		//noinspection unchecked
		final JAXBElement<GetAuthResponse> authenticationElement = (JAXBElement<GetAuthResponse>)webServiceTemplate.marshalSendAndReceive(LOGIN_SERVICE_API_URL, getAuth);
		System.out.println(elementToString(authenticationElement, webServiceTemplate.getMarshaller()));

		final Login authentication = authenticationElement.getValue().getReturn();

		assertTrue(StringUtils.isNotBlank(authentication.getAuthToken()));
		assertTrue(StringUtils.isNotBlank(authentication.getDepot()));
		assertEquals(authentication.getDelisId(), DELIS_ID);
		assertEquals(authentication.getCustomerUid(), DELIS_ID);
	}

	@Test
	void _2_createOrder()
	{
		final Login login;
		{
			// Login
			final GetAuth getAuthValue = loginServiceOF.createGetAuth();
			getAuthValue.setDelisId(DELIS_ID);
			getAuthValue.setPassword(DELIS_PASSWORD);
			getAuthValue.setMessageLanguage(MESSAGE_LANGUAGE);

			final JAXBElement<GetAuth> getAuth = loginServiceOF.createGetAuth(getAuthValue);
			final JAXBElement<GetAuthResponse> authenticationElement = (JAXBElement<GetAuthResponse>)webServiceTemplate.marshalSendAndReceive(LOGIN_SERVICE_API_URL, getAuth);
			login = authenticationElement.getValue().getReturn();
		}

		final StoreOrders storeOrders;
		{
			// Create the Delivery Order
			storeOrders = shipmentServiceOF.createStoreOrders();
			{
				// Print Options
				final PrintOptions printOptions = shipmentServiceOF.createPrintOptions();
				printOptions.setPaperFormat("A4");
				printOptions.setPrinterLanguage("PDF");
				storeOrders.setPrintOptions(printOptions);
			}
			{
				// ShipmentOrder 1
				final ShipmentServiceData shipmentServiceData = shipmentServiceOF.createShipmentServiceData();
				storeOrders.getOrder().add(shipmentServiceData);

				{
					// General Shipment Data
					final GeneralShipmentData generalShipmentData = shipmentServiceOF.createGeneralShipmentData();
					shipmentServiceData.setGeneralShipmentData(generalShipmentData);
					generalShipmentData.setMpsCustomerReferenceNumber1("Article 123"); // what is this? optional?
					generalShipmentData.setMpsCustomerReferenceNumber2("Order 456"); // what is this? optional?
					generalShipmentData.setIdentificationNumber("Article 456"); // what is this? optional?
					generalShipmentData.setSendingDepot("0112"); // mandatory? (taken from login)
					generalShipmentData.setProduct("CL"); // this is the DPD product
					generalShipmentData.setMpsWeight(Long.valueOf(500)); // what UOM? optional?

					{
						// Sender
						// the sender is hard linked with the location provided to dpd when the account was created.
						// 		it is not connected though with the pickup address! as in the pickup section there's the "CollectionRequestAddress".
						final Address sender = shipmentServiceOF.createAddress();
						generalShipmentData.setSender(sender);
						sender.setName1("metasfresh");
						sender.setName2("TheBestPessimist");
						sender.setStreet("Schröderstr.");
						sender.setHouseNo("66");
						sender.setState(null);
						sender.setCountry("DE");
						sender.setZipCode("10115");
						sender.setCity("Berlin, Mitte");
						sender.setCustomerNumber("");
						sender.setContact("");
						sender.setPhone("");
					}
					{
						// Recipient
						Address recipient = shipmentServiceOF.createAddress();
						generalShipmentData.setRecipient(recipient);
						recipient.setName1("DPD Deutschland GmbH");
						recipient.setName2("");
						recipient.setStreet("Wailandtstr.");
						recipient.setHouseNo("1");
						recipient.setState(null);
						recipient.setCountry("DE");
						recipient.setZipCode("63741");
						recipient.setCity("Aschaffenburg");
						recipient.setCustomerNumber("");
						recipient.setContact("");
						recipient.setPhone("");
					}
				}

				{
					// Parcel 1
					final Parcel parcel = shipmentServiceOF.createParcel();
					shipmentServiceData.getParcels().add(parcel);
					parcel.setCustomerReferenceNumber1("Article 123");    // what is this? optional?
					parcel.setCustomerReferenceNumber2("Order 456");    // what is this? optional?
					parcel.setWeight(500);    // what is this? optional? what uom?
					parcel.setContent("Smartphones");    // what is this? optional?
				}

				{
					// ProductAndService Data
					final ProductAndServiceData productAndServiceData = shipmentServiceOF.createProductAndServiceData();
					shipmentServiceData.setProductAndServiceData(productAndServiceData);
					{
						// Shipper Product
						productAndServiceData.setOrderType("consignment"); // this is somehow related to CL
					}
					{
						// Pickup
						final Pickup pickup = shipmentServiceOF.createPickup();
						productAndServiceData.setPickup(pickup);
						pickup.setTour(0);
						pickup.setQuantity(1);
						pickup.setDate(20191029);
						pickup.setDay(2);
						pickup.setFromTime1(1300);
						pickup.setToTime1(1700);
						pickup.setExtraPickup(true);

						// Collection Request Address
						final Address pickupAddress = shipmentServiceOF.createAddress();
						pickup.setCollectionRequestAddress(pickupAddress);
						pickupAddress.setName1("metasfresh");
						pickupAddress.setName2("TheBestPessimist");
						pickupAddress.setStreet("Schröderstr.");
						pickupAddress.setHouseNo("66");
						pickupAddress.setState(null);
						pickupAddress.setCountry("DE");
						pickupAddress.setZipCode("10115");
						pickupAddress.setCity("Berlin, Mitte");
						pickupAddress.setCustomerNumber("");
						pickupAddress.setContact("");
						pickupAddress.setPhone("");
					}
				}
			}
		}
		{
			// Call the Shipment API
			final JAXBElement<StoreOrders> storeOrdersElement = shipmentServiceOF.createStoreOrders(storeOrders);
			final JAXBElement<StoreOrdersResponse> storeOrdersResponseElement =
					(JAXBElement<StoreOrdersResponse>)webServiceTemplate.marshalSendAndReceive(SHIPMENT_SERVICE_API_URL, storeOrdersElement, new SoapHeaderWithAuth(login));

			final StoreOrdersResponseType orderResult = storeOrdersResponseElement.getValue().getOrderResult();
			assertTrue(orderResult.getParcellabelsPDF().length > 2);
			assertTrue(orderResult.getShipmentResponses().size() == 1);
			final ShipmentResponse shipmentResponse = orderResult.getShipmentResponses().get(0);
			assertEquals("Article 456", shipmentResponse.getIdentificationNumber());
			assertTrue(shipmentResponse.getMpsId().startsWith("MPS"));
		}
	}

	@NonNull
	private WebServiceTemplate createWebServiceTemplate()
	{
		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(
				"com.dpd.common"
		);

		final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		//		webServiceTemplate.setDefaultUri(apiUrl);
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		return webServiceTemplate;
	}

	private static StringResult elementToString(final Object element, final Marshaller marshaller) throws IOException
	{
		final StringResult result = new StringResult();
		marshaller.marshal(element, result);
		return result;
	}

	private static class SoapHeaderWithAuth implements WebServiceMessageCallback
	{
		private final Login login;

		private SoapHeaderWithAuth(final Login login)
		{
			this.login = login;
		}

		// thx to https://www.devglan.com/spring-mvc/custom-header-in-spring-soap-request
		// 		for the SoapHeader reference
		@Override
		public void doWithMessage(final WebServiceMessage message)
		{
			try
			{
				final Authentication authentication = new ObjectFactory().createAuthentication();
				authentication.setDelisId(login.getDelisId());
				authentication.setAuthToken(login.getAuthToken());
				authentication.setMessageLanguage(MESSAGE_LANGUAGE);

				// add the authentication to header
				final SoapMessage soapMessage = (SoapMessage)message;
				final SoapHeader header = soapMessage.getSoapHeader();

				final JAXBContext context = JAXBContext.newInstance(authentication.getClass());
				final javax.xml.bind.Marshaller marshaller = context.createMarshaller();
				marshaller.marshal(authentication, header.getResult());
			}
			catch (final JAXBException e)
			{
				throw new AdempiereException("Error while setting soap header");
			}
		}
	}
}
