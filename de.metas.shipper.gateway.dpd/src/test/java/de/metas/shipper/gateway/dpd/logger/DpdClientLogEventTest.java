/*
 * #%L
 * de.metas.shipper.gateway.dpd
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.dpd.logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DpdClientLogEventTest
{

	private final DpdClientLogEvent logEvent = DpdClientLogEvent.builder().build();

	@Test
	void testRegexPdfIsNotRemoved_LoginRequest()
	{
		final String originalString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns4:getAuth xmlns:ns2=\"http://dpd.com/common/service/types/Authentication/2.0\" xmlns:ns4=\"http://dpd.com/common/service/types/LoginService/2.0\" xmlns:ns3=\"http://dpd.com/common/service/types/ShipmentService/3.2\"><delisId>sandboxdpd</delisId><password>xMmshh1</password><messageLanguage>en_EN</messageLanguage></ns4:getAuth>";
		final String expectedString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns4:getAuth xmlns:ns2=\"http://dpd.com/common/service/types/Authentication/2.0\" xmlns:ns4=\"http://dpd.com/common/service/types/LoginService/2.0\" xmlns:ns3=\"http://dpd.com/common/service/types/ShipmentService/3.2\"><delisId>sandboxdpd</delisId><password>xMmshh1</password><messageLanguage>en_EN</messageLanguage></ns4:getAuth>";

		assertEquals(expectedString, logEvent.cleanupPdfData(originalString));
	}

	@Test
	void testRegexPdfIsNotRemoved_LoginResponse()
	{
		final String originalString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns4:getAuthResponse xmlns:ns2=\"http://dpd.com/common/service/types/Authentication/2.0\" xmlns:ns4=\"http://dpd.com/common/service/types/LoginService/2.0\" xmlns:ns3=\"http://dpd.com/common/service/types/ShipmentService/3.2\"><return><delisId>sandboxdpd</delisId><customerUid>sandboxdpd</customerUid><authToken>NDU3NzcxMTM5NTYzNzk4NzQxOQRRMTU2MDczNDMxOTYzOQRR</authToken><depot>0998</depot></return></ns4:getAuthResponse>";
		final String expectedString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns4:getAuthResponse xmlns:ns2=\"http://dpd.com/common/service/types/Authentication/2.0\" xmlns:ns4=\"http://dpd.com/common/service/types/LoginService/2.0\" xmlns:ns3=\"http://dpd.com/common/service/types/ShipmentService/3.2\"><return><delisId>sandboxdpd</delisId><customerUid>sandboxdpd</customerUid><authToken>NDU3NzcxMTM5NTYzNzk4NzQxOQRRMTU2MDczNDMxOTYzOQRR</authToken><depot>0998</depot></return></ns4:getAuthResponse>";

		assertEquals(expectedString, logEvent.cleanupPdfData(originalString));
	}

	@Test
	void testRegexPdfIsNotRemoved_StoreOrderRequest()
	{
		final String originalString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns3:storeOrders xmlns:ns2=\"http://dpd.com/common/service/types/Authentication/2.0\" xmlns:ns4=\"http://dpd.com/common/service/types/LoginService/2.0\" xmlns:ns3=\"http://dpd.com/common/service/types/ShipmentService/3.2\"><printOptions><printerLanguage>PDF</printerLanguage><paperFormat>A6</paperFormat></printOptions><order><generalShipmentData><identificationNumber>100003</identificationNumber><sendingDepot>0998</sendingDepot><product>E12</product><sender><name1>TheBestPessimist Inc.</name1><name2>The Second Shipper Company Name</name2><street>Eduard-Otto-Straße</street><houseNo>10</houseNo><country>DE</country><zipCode>53129</zipCode><city>Bonn</city></sender><recipient><name1>Berlin National Library?????</name1><name2></name2><street>Unter den Linden</street><houseNo>8</houseNo><country>DE</country><zipCode>10117</zipCode><city>Berlin</city><phone>+10-012-345689</phone><email>cristian.pasat@metasfresh.com</email></recipient></generalShipmentData><parcels><volume>10010010</volume><weight>100</weight><content>the epic package 11 description</content></parcels><parcels><volume>10010010</volume><weight>100</weight><content>the epic package 22 description</content></parcels><parcels><volume>10010010</volume><weight>100</weight><content>the epic package 33 description</content></parcels><parcels><volume>10010010</volume><weight>100</weight><content>the epic package 44 description</content></parcels><parcels><volume>10010010</volume><weight>100</weight><content>the epic package 55 description</content></parcels><productAndServiceData><orderType>consignment</orderType><pickup><quantity>5</quantity><date>20191123</date><day>6</day><fromTime1>1234</fromTime1><toTime1>2109</toTime1><collectionRequestAddress><name1>TheBestPessimist Inc.</name1><name2>The Second Shipper Company Name</name2><street>Eduard-Otto-Straße</street><houseNo>10</houseNo><country>DE</country><zipCode>53129</zipCode><city>Bonn</city></collectionRequestAddress></pickup></productAndServiceData></order></ns3:storeOrders>";
		final String expectedString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns3:storeOrders xmlns:ns2=\"http://dpd.com/common/service/types/Authentication/2.0\" xmlns:ns4=\"http://dpd.com/common/service/types/LoginService/2.0\" xmlns:ns3=\"http://dpd.com/common/service/types/ShipmentService/3.2\"><printOptions><printerLanguage>PDF</printerLanguage><paperFormat>A6</paperFormat></printOptions><order><generalShipmentData><identificationNumber>100003</identificationNumber><sendingDepot>0998</sendingDepot><product>E12</product><sender><name1>TheBestPessimist Inc.</name1><name2>The Second Shipper Company Name</name2><street>Eduard-Otto-Straße</street><houseNo>10</houseNo><country>DE</country><zipCode>53129</zipCode><city>Bonn</city></sender><recipient><name1>Berlin National Library?????</name1><name2></name2><street>Unter den Linden</street><houseNo>8</houseNo><country>DE</country><zipCode>10117</zipCode><city>Berlin</city><phone>+10-012-345689</phone><email>cristian.pasat@metasfresh.com</email></recipient></generalShipmentData><parcels><volume>10010010</volume><weight>100</weight><content>the epic package 11 description</content></parcels><parcels><volume>10010010</volume><weight>100</weight><content>the epic package 22 description</content></parcels><parcels><volume>10010010</volume><weight>100</weight><content>the epic package 33 description</content></parcels><parcels><volume>10010010</volume><weight>100</weight><content>the epic package 44 description</content></parcels><parcels><volume>10010010</volume><weight>100</weight><content>the epic package 55 description</content></parcels><productAndServiceData><orderType>consignment</orderType><pickup><quantity>5</quantity><date>20191123</date><day>6</day><fromTime1>1234</fromTime1><toTime1>2109</toTime1><collectionRequestAddress><name1>TheBestPessimist Inc.</name1><name2>The Second Shipper Company Name</name2><street>Eduard-Otto-Straße</street><houseNo>10</houseNo><country>DE</country><zipCode>53129</zipCode><city>Bonn</city></collectionRequestAddress></pickup></productAndServiceData></order></ns3:storeOrders>";

		assertEquals(expectedString, logEvent.cleanupPdfData(originalString));
	}

	@Test
	void testRegexPdfNotRemoved_StoreOrderResponse()
	{
		final String originalString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns3:storeOrdersResponse xmlns:ns2=\"http://dpd.com/common/service/types/Authentication/2.0\" xmlns:ns4=\"http://dpd.com/common/service/types/LoginService/2.0\" xmlns:ns3=\"http://dpd.com/common/service/types/ShipmentService/3.2\"><orderResult><parcellabelsPDF>JVBERi0xLjQKJeLjz9MKMyAwIG9iago8PC9EZWNvZGVQYXJtczw8L0NvbG9ycyAzL1ByZWRpY3RvciAxNS9CaXRzUGVyQ29tcG9uZW50IDgvQ29sdW1ucyAxNDg+Pi9UeXBlL1hPYmplY3QvQ29sb3JTcGFjZS9EZXZpY2VSR0IvU3VidHlwZS9JbWFnZS9CaXRzUGVyQ29tcG9uZW50IDgvV2lkdGggMTQ4L0xlbmd0aCAxNjA1L0hlaWdodCAxNDgvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeNrtncGO20AMQ/P/P90CPRVYxBb56GRHoE9GN3XG87wyRaro60+PY49Xt6DwehRejwi817/j5/n1539+8t113n3y3aFe83rN764//5Pr9UyuYwL7/5qFtxbe9dLnUOc/Jds3wa9CvX6A5p/3Hr6buy68VfDU7Zsvd7KJpJyS9aR+6pXl+fcW3nZ4Pf/N54W3C97kF3YuCiZ/a15MJjI9JZqeuL4nYW5ePYW3Cl62TSYvdiJk5g/Q/O7m96heIdAqFN7BNDdmODdhYjJiNDE1YjA4ZjFlZDhmM2E1ZTk3YzQ+PGU2NjNmZjA1NzEzMzdlMDk4MDJkMDdhODFlZjFiMzYzPl0vSW5mbyA1NyAwIFIvU2l6ZSA1OD4+CnN0YXJ0eHJlZgoxMzE5ODQKJSVFT0YK</parcellabelsPDF><shipmentResponses><identificationNumber>100003</identificationNumber><mpsId>EXP0998505274844120191122</mpsId><parcelInformation><parcelLabelNumber>09985052748441</parcelLabelNumber></parcelInformation><parcelInformation><parcelLabelNumber>09985052748442</parcelLabelNumber></parcelInformation><parcelInformation><parcelLabelNumber>09985052748443</parcelLabelNumber></parcelInformation><parcelInformation><parcelLabelNumber>09985052748444</parcelLabelNumber></parcelInformation><parcelInformation><parcelLabelNumber>09985052748445</parcelLabelNumber></parcelInformation></shipmentResponses></orderResult></ns3:storeOrdersResponse>";
		final String expectedString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns3:storeOrdersResponse xmlns:ns2=\"http://dpd.com/common/service/types/Authentication/2.0\" xmlns:ns4=\"http://dpd.com/common/service/types/LoginService/2.0\" xmlns:ns3=\"http://dpd.com/common/service/types/ShipmentService/3.2\"><orderResult><parcellabelsPDF>PDF TEXT REMOVED!</parcellabelsPDF><shipmentResponses><identificationNumber>100003</identificationNumber><mpsId>EXP0998505274844120191122</mpsId><parcelInformation><parcelLabelNumber>09985052748441</parcelLabelNumber></parcelInformation><parcelInformation><parcelLabelNumber>09985052748442</parcelLabelNumber></parcelInformation><parcelInformation><parcelLabelNumber>09985052748443</parcelLabelNumber></parcelInformation><parcelInformation><parcelLabelNumber>09985052748444</parcelLabelNumber></parcelInformation><parcelInformation><parcelLabelNumber>09985052748445</parcelLabelNumber></parcelInformation></shipmentResponses></orderResult></ns3:storeOrdersResponse>";

		assertEquals(expectedString, logEvent.cleanupPdfData(originalString));
	}

}
