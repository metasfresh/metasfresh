/*
 * #%L
 * de.metas.shipper.gateway.dhl
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.dhl;

import de.metas.location.CountryCode;
import de.metas.shipper.gateway.dhl.json.JsonDhlAddress;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DhlAddressMapperTest
{
	@Test
	public void testGetShipperAddress()
	{
		final Address address = Address.builder()
				.companyName1("Company A")
				.companyName2("Dept A")
				.companyDepartment("Dept X")
				.street1("Main Street")
				.street2("Suite 1")
				.houseNo("123A")
				.zipCode(" 12345")
				.city("Testville")
				.country(CountryCode.builder()
						.alpha2("XX")
						.alpha3("AFG")
						.build())
				.bpartnerId(1)
				.build();

		final JsonDhlAddress result = DhlAddressMapper.getShipperAddress(address);

		assertThat(result.getName1()).isEqualTo("Company A");
		assertThat(result.getName2()).isEqualTo("Dept A");
		assertThat(result.getAddressStreet()).isEqualTo("Main Street");
		assertThat(result.getAddressHouse()).isEqualTo("123A");
		assertThat(result.getPostalCode()).isEqualTo("12345");
		assertThat(result.getCity()).isEqualTo("Testville");
		assertThat(result.getCountry()).isEqualTo("AFG");
		assertThat(result.getAdditionalAddressInformation1()).isNull();
		assertThat(result.getEmail()).isNull();
		assertThat(result.getPhone()).isNull();
	}

	@Test
	public void testGetConsigneeAddress_withContactPerson()
	{
		final Address address = Address.builder()
				.companyName1("Company B")
				.companyName2("Dept B")
				.companyDepartment("Dept Y")
				.street1("Second Street")
				.street2("Apt 101")
				.houseNo("456B")
				.zipCode("SW1W 0NY ")
				.city("Exampletown")
				.country(CountryCode.builder()
						.alpha2("XX")
						.alpha3("AFG")
						.build())
				.bpartnerId(2)
				.build();

		final ContactPerson contactPerson = ContactPerson.builder()
				.emailAddress("contact@example.com")
				.simplePhoneNumber("555-1234")
				.build();

		final JsonDhlAddress result = DhlAddressMapper.getConsigneeAddress(address, contactPerson);

		assertThat(result.getName1()).isEqualTo("Company B");
		assertThat(result.getName2()).isEqualTo("Dept B");
		assertThat(result.getAddressStreet()).isEqualTo("Second Street");
		assertThat(result.getAddressHouse()).isEqualTo("456B");
		assertThat(result.getPostalCode()).isEqualTo("SW1W 0NY");
		assertThat(result.getCity()).isEqualTo("Exampletown");
		assertThat(result.getCountry()).isEqualTo("AFG");
		assertThat(result.getAdditionalAddressInformation1()).isEqualTo("Apt 101");
		assertThat(result.getEmail()).isEqualTo("contact@example.com");
		assertThat(result.getPhone()).isEqualTo("555-1234");
	}

	@Test
	public void testGetConsigneeAddress_withContactPersonAndInvalidEmail()
	{
		final Address address = Address.builder()
				.companyName1("Company B")
				.companyName2("Dept B")
				.companyDepartment("Dept Y")
				.street1("Second Street")
				.street2("Apt 101")
				.houseNo("456B")
				.zipCode("67890")
				.city("Exampletown")
				.country(CountryCode.builder()
						.alpha2("XX")
						.alpha3("AFG")
						.build())
				.bpartnerId(2)
				.build();

		final ContactPerson contactPerson = ContactPerson.builder()
				.emailAddress("co")
				.simplePhoneNumber("555-1234")
				.build();

		// when
		final RuntimeException thrown = Assertions.assertThrows(
				RuntimeException.class,
				() -> DhlAddressMapper.getConsigneeAddress(address, contactPerson));

		// then
		assertThat(thrown).hasMessage("Assumption failure: email has minimum three characters");
	}

	@Test
	public void testGetConsigneeAddress_withoutContactPerson()
	{
		final Address address = Address.builder()
				.companyName1("Company C")
				.companyName2(null)
				.companyDepartment(null)
				.street1("Third Street")
				.street2("Office 22")
				.houseNo("789C")
				.zipCode("11111")
				.city("MockCity")
				.country(CountryCode.builder()
						.alpha2("XX")
						.alpha3("AFG")
						.build())
				.bpartnerId(3)
				.build();

		final JsonDhlAddress result = DhlAddressMapper.getConsigneeAddress(address, null);

		assertThat(result.getName1()).isEqualTo("Company C");
		assertThat(result.getName2()).isNull();
		assertThat(result.getAdditionalAddressInformation1()).isEqualTo("Office 22");
		assertThat(result.getEmail()).isNull();
		assertThat(result.getPhone()).isNull();
	}

	@Test
	public void testGetConsigneeAddress_withOverlengthFields_shouldTruncate()
	{
		// 90 chars
		final String ninetyLongString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdef";
		// 80 chars
		final String eightyLongString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQR";
		// 62 chars
		final String sixtyTwoLongString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
		// 50 chars
		final String fiftyLongString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmn";
		// 40 chars
		final String fortyLongString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcd";
		// 20 chars
		final String twentyLongString = "ABCDEFGHIJKLMNOPQRST";
		// 10 chars
		final String tenLongString = "ABCDEFGHIJ";

		final Address address = Address.builder()
				.companyName1(sixtyTwoLongString)   // 62 chars, expect truncation to 50
				.companyName2(sixtyTwoLongString)
				.companyDepartment("Ignored")
				.street1(sixtyTwoLongString)
				.street2("This won't be used by shipper")
				.houseNo(sixtyTwoLongString)
				.zipCode(sixtyTwoLongString)
				.city(sixtyTwoLongString)
				.country(CountryCode.builder()
						.alpha2("XX")
						.alpha3("AFG")
						.build())
				.bpartnerId(42)
				.build();

		final ContactPerson contactPerson = ContactPerson.builder()
				.emailAddress(ninetyLongString)
				.simplePhoneNumber(sixtyTwoLongString)
				.build();

		final JsonDhlAddress result = DhlAddressMapper.getConsigneeAddress(address, contactPerson);

		assertThat(result.getName1()).isEqualTo(fiftyLongString);
		assertThat(result.getName2()).isEqualTo(fiftyLongString);
		assertThat(result.getAddressStreet()).isEqualTo(fiftyLongString);
		assertThat(result.getAddressHouse()).isEqualTo(tenLongString);
		assertThat(result.getPostalCode()).isEqualTo(tenLongString);
		assertThat(result.getCity()).isEqualTo(fortyLongString);
		assertThat(result.getPhone()).isEqualTo(twentyLongString);
		assertThat(result.getEmail()).isEqualTo(eightyLongString);
	}

	@Test
	public void testGetShipperAddress_withMissingPostalCode()
	{
		final Address address = Address.builder()
				.companyName1("Company A")
				.companyName2("Dept A")
				.companyDepartment("Dept X")
				.street1("Main Street")
				.street2("Suite 1")
				.houseNo("123A")
				.zipCode("12")
				.city("Testville")
				.country(CountryCode.builder()
						.alpha2("XX")
						.alpha3("AFG")
						.build())
				.bpartnerId(1)
				.build();

		// when
		final RuntimeException thrown = Assertions.assertThrows(
				RuntimeException.class,
				() -> DhlAddressMapper.getShipperAddress(address));

		// then
		assertThat(thrown).hasMessage("Assumption failure: postalCode has minimum three characters");
	}

	@Test
	public void testGetShipperAddress_withMissingPostalCode_CountryIRL()
	{
		final Address address = Address.builder()
				.companyName1("Company A")
				.companyName2("Dept A")
				.companyDepartment("Dept X")
				.street1("Main Street")
				.street2("Suite 1")
				.houseNo("123A")
				.zipCode("12")
				.city("Testville")
				.country(CountryCode.builder()
						.alpha2("XX")
						.alpha3("IRL")
						.build())
				.bpartnerId(1)
				.build();

		final JsonDhlAddress result = DhlAddressMapper.getShipperAddress(address);

		assertThat(result.getName1()).isEqualTo("Company A");
		assertThat(result.getName2()).isEqualTo("Dept A");
		assertThat(result.getAddressStreet()).isEqualTo("Main Street");
		assertThat(result.getAddressHouse()).isEqualTo("123A");
		assertThat(result.getPostalCode()).isEqualTo("12");
		assertThat(result.getCity()).isEqualTo("Testville");
		assertThat(result.getCountry()).isEqualTo("IRL");
		assertThat(result.getAdditionalAddressInformation1()).isNull();
		assertThat(result.getEmail()).isNull();
		assertThat(result.getPhone()).isNull();
	}

	@Test
	public void testGetShipperAddress_invalidPostalCode()
	{
		final Address address = Address.builder()
				.companyName1("Company A")
				.companyName2("Dept A")
				.companyDepartment("Dept X")
				.street1("Main Street")
				.street2("Suite 1")
				.houseNo("123A")
				.zipCode("12")
				.city("Testville")
				.country(CountryCode.builder()
						.alpha2("XX")
						.alpha3("DE")
						.build())
				.bpartnerId(1)
				.build();

		// when
		final RuntimeException thrown = Assertions.assertThrows(
				RuntimeException.class,
				() -> DhlAddressMapper.getShipperAddress(address));

		// then
		assertThat(thrown).hasMessage("Assumption failure: Invalid ISO alpha-3 country code: DE");
	}
}
