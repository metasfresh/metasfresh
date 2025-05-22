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

 import de.metas.shipper.gateway.dhl.json.JsonDhlAddress;
 import de.metas.shipper.gateway.spi.model.Address;
 import de.metas.shipper.gateway.spi.model.ContactPerson;
 import de.metas.util.Check;
 import de.metas.util.Loggables;
 import de.metas.util.StringUtils;
 import lombok.NonNull;
 import lombok.experimental.UtilityClass;

 import javax.annotation.Nullable;
 import java.util.Locale;
 import java.util.function.BiConsumer;

 import static de.metas.shipper.gateway.dhl.DhlConstants.IRL_COUNTRY;

 @UtilityClass
 public class DhlAddressMapper
 {
	 private static final BiConsumer<String, String> ON_TRUNC = (origStr, truncatedStr) -> Loggables.addLog("Truncated {} to {} because it was too long", origStr, truncatedStr);

	 @NonNull
	 public static JsonDhlAddress getShipperAddress(@NonNull final Address address)
	 {
		 final JsonDhlAddress.JsonDhlAddressBuilder addressBuilder = JsonDhlAddress.builder();
		 mapCommonAddressFields(addressBuilder, address);

		 return addressBuilder.build();
	 }

	 @NonNull
	 public static JsonDhlAddress getConsigneeAddress(@NonNull final Address address, @Nullable final ContactPerson deliveryContact)
	 {
		 final JsonDhlAddress.JsonDhlAddressBuilder addressBuilder = JsonDhlAddress.builder();
		 mapCommonAddressFields(addressBuilder, address);
		 addressBuilder.additionalAddressInformation1(StringUtils.trunc(address.getStreet2(), 50, ON_TRUNC));

		 if (deliveryContact != null)
		 {
			 final String email = deliveryContact.getEmailAddress();
			 if (Check.isNotBlank(email))
			 {
				 Check.assume(email.length() > 2, "email has minimum three characters");
				 addressBuilder.email(StringUtils.trunc(deliveryContact.getEmailAddress(), 80, ON_TRUNC));
			 }

			 addressBuilder.phone(StringUtils.trunc(deliveryContact.getPhoneAsStringOrNull(), 20, ON_TRUNC));
		 }

		 return addressBuilder.build();
	 }

	 private static boolean isValidAlpha3CountryCode(@NonNull final String code)
	 {
		 if (code.length() != 3)
		 {
			 return false;
		 }

		 for (final String iso : Locale.getISOCountries())
		 {
			 final Locale locale = new Locale("", iso);
			 if (locale.getISO3Country().equalsIgnoreCase(code))
			 {
				 return true;
			 }
		 }
		 return false;
	 }

	 private static void mapCommonAddressFields(@NonNull final JsonDhlAddress.JsonDhlAddressBuilder addressBuilder, @NonNull final Address address)
	 {
		 final String country = address.getCountry().getAlpha3();
		 final String postalCode = StringUtils.trunc(address.getZipCode(), 10, ON_TRUNC);

		 // Validate ISO 3166-1 alpha-3 country code
		 Check.assume(isValidAlpha3CountryCode(country), "Invalid ISO alpha-3 country code: " + country);

		 if (!IRL_COUNTRY.equals(country))
		 {
			 Check.assumeNotNull(postalCode, "postalCode is not NULL");
			 Check.assume(postalCode.length() > 2, "postalCode has minimum three characters");
		 }

		 addressBuilder.name1(StringUtils.trunc(address.getCompanyName1(), 50, ON_TRUNC))
				 .name2(StringUtils.trunc(address.getCompanyName2(), 50, ON_TRUNC))
				 .addressStreet(StringUtils.trunc(address.getStreet1(), 50, ON_TRUNC))
				 .addressHouse(StringUtils.trunc(address.getHouseNo(), 10, ON_TRUNC))
				 .postalCode(postalCode)
				 .city(StringUtils.trunc(address.getCity(), 40, ON_TRUNC))
				 .country(country);
	 }
 }
