package de.metas.shipper.gateway.dpd;

import com.dpd.common.service.types.shipmentservice._3.GeneralShipmentData;
import com.dpd.common.service.types.shipmentservice._3.PrintOptions;
import com.dpd.common.service.types.shipmentservice._3.ShipmentServiceData;
import com.dpd.common.service.types.shipmentservice._3.StoreOrders;
import com.dpd.common.ws.loginservice.v2_0.types.GetAuth;
import com.dpd.common.ws.loginservice.v2_0.types.GetAuthResponse;
import com.dpd.common.ws.loginservice.v2_0.types.Login;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.xml.transform.StringResult;

import javax.xml.bind.JAXBElement;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Test1
{

	private static final String DELIS_ID = "sandboxdpd";
	private static final String DELIS_PASSWORD = "xMmshh1";
	private static final String MESSAGE_LANGUAGE = "en_EN";
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
		final JAXBElement<GetAuthResponse> authenticationElement = (JAXBElement<GetAuthResponse>)webServiceTemplate.marshalSendAndReceive(getAuth);
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
		final StoreOrders storeOrders = shipmentServiceOF.createStoreOrders();

		//
		final PrintOptions printOptions = shipmentServiceOF.createPrintOptions();
		printOptions.setPaperFormat("A4");
		printOptions.setPrinterLanguage("PDF");
		storeOrders.setPrintOptions(printOptions);

		// ShipmentOrder 1
		final ShipmentServiceData shipmentServiceData = shipmentServiceOF.createShipmentServiceData();
		// General Shipment Data
		final GeneralShipmentData generalShipmentData = shipmentServiceOF.createGeneralShipmentData();
		//		generalShipmentData.set
		shipmentServiceData.setGeneralShipmentData(generalShipmentData);
		////
		//		shipmentServiceData.setProductAndServiceData();
		//
		//		shipmentServiceData.getParcels().add()

	}

	@NonNull
	private WebServiceTemplate createWebServiceTemplate()
	{
		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(
				"com.dpd.common"
		);

		final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setDefaultUri("https://public-ws-stage.dpd.com/services/LoginService/V2_0/");
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
}

