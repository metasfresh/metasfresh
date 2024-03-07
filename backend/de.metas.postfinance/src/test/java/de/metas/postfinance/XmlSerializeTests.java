/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.metas.postfinance.customerregistration.model.XmlCustomerRegistrationMessage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class XmlSerializeTests
{
	@Test
	void checkSerializeDeserialize() throws IOException
	{
		final String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<CustomerRegistrationMessage>\n"
				+ "  <CustomerRegistration>\n"
				+ "    <SubscriptionType>1</SubscriptionType>\n"
				+ "    <BillerID>11100000000000010</BillerID>\n"
				+ "    <RecipientID>11100000000000001</RecipientID>\n"
				+ "    <RecipientType>RecipientType</RecipientType>\n"
				+ "    <Language>Language</Language>\n"
				+ "    <CustomerNameAddress>\n"
				+ "      <NamePrivate>\n"
				+ "        <GivenName>GivenName</GivenName>\n"
				+ "        <FamilyName>FamilyName</FamilyName>\n"
				+ "      </NamePrivate>\n"
				+ "      <Address>Address</Address>\n"
				+ "      <Zip>Zip</Zip>\n"
				+ "      <City>City</City>\n"
				+ "      <Country>Country</Country>\n"
				+ "    </CustomerNameAddress>\n"
				+ "    <Email>Email</Email>\n"
				+ "    <CustomerSubscriptionFormField>\n"
				+ "      <TechnicalID>TechnicalID</TechnicalID>\n"
				+ "      <Value>Value</Value>\n"
				+ "    </CustomerSubscriptionFormField>\n"
				+ "    <CustomerSubscriptionFormField>\n"
				+ "      <TechnicalID>TechnicalID</TechnicalID>\n"
				+ "      <Value>Value</Value>\n"
				+ "    </CustomerSubscriptionFormField>\n"
				+ "  </CustomerRegistration>\n"
				+ "  <CustomerRegistration>\n"
				+ "    <SubscriptionType>1</SubscriptionType>\n"
				+ "    <BillerID>11100000000000010</BillerID>\n"
				+ "    <RecipientID>11100000000000001</RecipientID>\n"
				+ "    <RecipientType>RecipientType</RecipientType>\n"
				+ "    <Language>Language</Language>\n"
				+ "    <CustomerNameAddress>\n"
				+ "      <NameCompany>\n"
				+ "        <CompanyName>CompanyName</CompanyName>\n"
				+ "      </NameCompany>\n"
				+ "      <Address>Address</Address>\n"
				+ "      <Zip>City</Zip>\n"
				+ "      <City>City</City>\n"
				+ "      <Country>Country</Country>\n"
				+ "    </CustomerNameAddress>\n"
				+ "    <Email>Email</Email>\n"
				+ "    <UID>UID</UID>\n"
				+ "    <CustomerSubscriptionFormField>\n"
				+ "      <TechnicalID>TechnicalID</TechnicalID>\n"
				+ "      <Value>Value</Value>\n"
				+ "    </CustomerSubscriptionFormField>\n"
				+ "  </CustomerRegistration>\n"
				+ "  <CustomerRegistration>\n"
				+ "    <SubscriptionType>2</SubscriptionType>\n"
				+ "    <BillerID>11100000000000010</BillerID>\n"
				+ "    <RecipientID>11100000000000001</RecipientID>\n"
				+ "    <RecipientType>RecipientType</RecipientType>\n"
				+ "    <Language>Language</Language>\n"
				+ "    <CustomerNameAddress>\n"
				+ "      <NamePrivate>\n"
				+ "        <GivenName>GivenName</GivenName>\n"
				+ "        <FamilyName>FamilyName</FamilyName>\n"
				+ "      </NamePrivate>\n"
				+ "      <Address>Address</Address>\n"
				+ "      <Zip>Zip</Zip>\n"
				+ "      <City>City</City>\n"
				+ "      <Country>Country</Country>\n"
				+ "    </CustomerNameAddress>\n"
				+ "    <Email>Email</Email>\n"
				+ "    <CreditAccount>CreditAccount</CreditAccount>\n"
				+ "    <CreditorReference>CreditorReference</CreditorReference>\n"
				+ "  </CustomerRegistration>\n"
				+ "  <CustomerRegistration>\n"
				+ "    <SubscriptionType>2</SubscriptionType>\n"
				+ "    <BillerID>11100000000000010</BillerID>\n"
				+ "    <RecipientID>11100000000000001</RecipientID>\n"
				+ "    <RecipientType>RecipientType</RecipientType>\n"
				+ "    <Language>Language</Language>\n"
				+ "    <CustomerNameAddress>\n"
				+ "      <NamePrivate>\n"
				+ "        <GivenName>GivenName</GivenName>\n"
				+ "        <FamilyName>FamilyName</FamilyName>\n"
				+ "      </NamePrivate>\n"
				+ "      <Address>Address</Address>\n"
				+ "      <Zip>Zip</Zip>\n"
				+ "      <City>City</City>\n"
				+ "      <Country>Country</Country>\n"
				+ "    </CustomerNameAddress>\n"
				+ "    <Email>Email</Email>\n"
				+ "    <CreditAccount>CreditAccount</CreditAccount>\n"
				+ "    <CreditorReference>CreditorReference</CreditorReference>\n"
				+ "  </CustomerRegistration>\n"
				+ "  <CustomerRegistration>\n"
				+ "    <SubscriptionType>3</SubscriptionType>\n"
				+ "    <BillerID>11100000000000010</BillerID>\n"
				+ "    <RecipientID>11100000000000001</RecipientID>\n"
				+ "    <RecipientType>RecipientType</RecipientType>\n"
				+ "    <Language>de</Language>\n"
				+ "    <CustomerNameAddress>\n"
				+ "      <NamePrivate>\n"
				+ "        <GivenName>GivenName</GivenName>\n"
				+ "        <FamilyName>FamilyName</FamilyName>\n"
				+ "      </NamePrivate>\n"
				+ "      <Address>Address</Address>\n"
				+ "      <Zip>Zip</Zip>\n"
				+ "      <City>City</City>\n"
				+ "      <Country>Country</Country>\n"
				+ "    </CustomerNameAddress>\n"
				+ "    <Email>Email</Email>\n"
				+ "  </CustomerRegistration>\n"
				+ "</CustomerRegistrationMessage>\n";

		assertOK(xmlString, XmlCustomerRegistrationMessage.class);
	}

	private <T> void assertOK(final String originalXml, final Class<T> valueType) throws IOException
	{
		final XmlMapper xmlMapper = ConfiguredXmlMapper.get();

		final T object = xmlMapper.readValue(originalXml, valueType);
		assertThat(object).isNotNull();

		// when
		final String xmlString = xmlMapper.writeValueAsString(object);
		assertThat(xmlString).isNotEmpty();

		// then
		assertThat(xmlString).isEqualToIgnoringNewLines(originalXml);
	}
}