/*
 * #%L
 * de.metas.shipper.gateway.commons
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

package de.metas.shipper.gateway.commons;

import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.StringUtils;
import de.metas.common.util.pair.IPair;
import de.metas.i18n.Language;
import de.metas.location.CountryCode;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.Address.AddressBuilder;
import de.metas.shipper.gateway.spi.model.ContactPerson;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public final class DeliveryOrderUtil
{

	public Address.AddressBuilder prepareAddressFromLocation(@NonNull final I_C_Location location)
	{
		final AddressBuilder addressBuilder = Address.builder();

		final IPair<String, String> splitStreetAndHouseNumber1 = StringUtils
				.splitStreetAndHouseNumberOrNull(location.getAddress1());
		if (splitStreetAndHouseNumber1 != null
				&& !Check.isEmpty(splitStreetAndHouseNumber1.getLeft())
				&& !Check.isEmpty(splitStreetAndHouseNumber1.getRight()))
		{
			addressBuilder.street1(splitStreetAndHouseNumber1.getLeft());
			addressBuilder.houseNo(splitStreetAndHouseNumber1.getRight());
		}
		else
		{
			addressBuilder.street1(location.getAddress1());
			addressBuilder.houseNo("0");
		}

		return addressBuilder
				.street2(StringUtils.trimBlankToNull(location.getAddress2()))
				.zipCode(location.getPostal())
				.city(location.getCity())
				.country(createShipperCountryCode(CountryId.ofRepoId(location.getC_Country_ID())));
	}

	public Address.AddressBuilder prepareAddressFromLocationBP(@NonNull final I_C_Location location, @NonNull final I_C_BPartner bpartner)
	{
		return prepareAddressFromLocation(location)
				.companyName1(bpartner.getName())
				.companyName2(bpartner.getName2())
				.bpartnerId(bpartner.getC_BPartner_ID());
	}

	public CountryCode createShipperCountryCode(final CountryId countryId)
	{
		final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
		return CountryCode.builder()
				.alpha2(countryDAO.retrieveCountryCode2ByCountryId(countryId))
				.alpha3(countryDAO.retrieveCountryCode3ByCountryId(countryId))
				.build();
	}

	public String getPOReferences(@NonNull final Collection<DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest.PackageInfo> packageInfos)
	{
		return packageInfos.stream()
				.map(DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest.PackageInfo::getPoReference)
				.map(StringUtils::trimBlankToNull)
				.filter(Objects::nonNull)
				.distinct()
				.collect(Collectors.joining(", "));
	}

	@NonNull
	public ContactPerson getContactPerson(@NonNull final I_C_BPartner bPartner,
										  @NonNull final I_C_BPartner_Location bPLocation,
										  @Nullable final User contact)
	{
		final String contactPersonName = contact != null ? contact.getName() : null;
		final String name = CoalesceUtil.firstNotEmptyTrimmedNotNull(contactPersonName, bPartner.getName());

		final String contactPersonPhoneNumber = contact != null ? contact.getPhone() : null;
		final String phoneNumber = CoalesceUtil.firstNotEmptyTrimmed(contactPersonPhoneNumber, bPLocation.getPhone(), bPLocation.getPhone2(), bPartner.getPhone2());

		final String contactPersonMail = contact != null ? contact.getEmailAddress() : null;
		final String emailAddress = CoalesceUtil.firstNotEmptyTrimmed(contactPersonMail, bPLocation.getEMail(), bPartner.getEMail());

		final Language bpLanguage = Language.asLanguage(bPartner.getAD_Language());
		return ContactPerson.builder()
				.name(name)
				.department(contact != null ? contact.getDepartment() : null)
				.emailAddress(emailAddress)
				.simplePhoneNumber(phoneNumber)
				.languageCode(bpLanguage != null ? bpLanguage.getLanguageCode() : null)
				.build();
	}

}
