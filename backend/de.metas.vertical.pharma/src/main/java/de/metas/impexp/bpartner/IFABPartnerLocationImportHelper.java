package de.metas.impexp.bpartner;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_I_Pharma_BPartner;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
 * #%L
 * metasfresh-pharma
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

@UtilityClass
/* package */ class IFABPartnerLocationImportHelper
{
	final private Comparator<Address> addressComparator = Comparator.comparing(Address::getCity)
			.thenComparing(Address::getAddress1)
			.thenComparing(Address::getAddress2)
			.thenComparing(Address::getPostal)
			.thenComparing(Address::getPobox)
			.thenComparingInt(Address::getCounrytId);

	@Nullable public I_C_BPartner_Location importRecord(
			@NonNull final I_I_Pharma_BPartner importRecord,
			@NonNull final List<I_I_Pharma_BPartner> previousImportRecordsForSameBPartner)
	{
		// first, try to find an existent one
		I_C_BPartner_Location bpartnerLocation = fetchAndUpdateExistingBPLocation(importRecord, previousImportRecordsForSameBPartner);
		// if null, create a new one
		if (bpartnerLocation == null)
		{
			bpartnerLocation = createNewBPartnerLocation(importRecord);
		}
		importRecord.setC_BPartner_Location(bpartnerLocation);
		return bpartnerLocation;
	}

	@Nullable
	private I_C_BPartner_Location fetchAndUpdateExistingBPLocation(@NonNull final I_I_Pharma_BPartner importRecord,
			@NonNull final List<I_I_Pharma_BPartner> previousImportRecordsForSameBPartner)
	{
		I_C_BPartner_Location bpartnerLocation = importRecord.getC_BPartner_Location();

		final List<I_I_Pharma_BPartner> importRecordsWithEqualAddresses = getImportRecordsWithEqualAddresses(importRecord, previousImportRecordsForSameBPartner);

		final boolean previousImportRecordsHaveAnEqualAddress = !importRecordsWithEqualAddresses.isEmpty();

		if (previousImportRecordsHaveAnEqualAddress
				|| (bpartnerLocation != null && bpartnerLocation.getC_BPartner_Location_ID() > 0))// Update Location
		{
			if (previousImportRecordsHaveAnEqualAddress)
			{
				bpartnerLocation = importRecordsWithEqualAddresses.get(0).getC_BPartner_Location();
			}

			updateExistingBPartnerLocation(importRecord, bpartnerLocation);

			return bpartnerLocation;
		}

		return updateOldAddressIfExistsAndMatches(importRecord);
	}

	@Nullable
	private I_C_BPartner_Location updateOldAddressIfExistsAndMatches(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		final List<I_C_BPartner_Location> existentBPLocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(importRecord.getC_BPartner());
		if (existentBPLocations.isEmpty())
		{
			return null;
		}

		final Address importAddress = Address.builder()
				.counrytId(importRecord.getC_Country_ID())
				.city(importRecord.getb00ortzu())
				.address1(buildAddress1(importRecord))
				.address2(buildAddress2(importRecord))
				.postal(importRecord.getb00plzzu1())
				.pobox(buildPOBox(importRecord))
				.build();

		final List<Address> matchedAddreses = existentBPLocations.stream()
				.map(bpLocation -> Address.builder()
						.counrytId(importRecord.getC_Country_ID())
						.city(importRecord.getb00ortzu())
						.address1(buildAddress1(importRecord))
						.address2(buildAddress2(importRecord))
						.postal(importRecord.getb00plzzu1())
						.pobox(buildPOBox(importRecord))
						.bpLocationId(bpLocation.getC_BPartner_Location_ID())
						.build())
				.collect(ImmutableList.toImmutableList());

		final List<Address> filtered = matchedAddreses.stream()
				.filter(address -> isMatched(importAddress, address))
				.collect(ImmutableList.toImmutableList());

		if (filtered.size() > 0)
		{
			final I_C_BPartner_Location bpartnerLocation = Services.get(IBPartnerDAO.class).getBPartnerLocationByIdEvenInactive(BPartnerLocationId.ofRepoId(importRecord.getC_BPartner_ID(), matchedAddreses.get(0).getBpLocationId()));
			updateExistingBPartnerLocation(importRecord, bpartnerLocation);
			return bpartnerLocation;
		}

		return null;
	}

	private boolean isMatched(@NonNull final Address importAddress, @NonNull final Address address)
	{
		return addressComparator.compare(address, importAddress) == 0;
	}

	private List<I_I_Pharma_BPartner> getImportRecordsWithEqualAddresses(
			@NonNull final I_I_Pharma_BPartner importRecord,
			@NonNull final List<I_I_Pharma_BPartner> previousImportRecordsForSameBPartner)
	{
		return previousImportRecordsForSameBPartner.stream()
				.filter(createEqualAddressFilter(importRecord))
				.collect(Collectors.toList());
	}

	private Predicate<I_I_Pharma_BPartner> createEqualAddressFilter(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		return p -> p.getC_BPartner_Location_ID() > 0
				&& importRecord.getC_Country_ID() == p.getC_Country_ID()
				&& Objects.equals(importRecord.getb00ortzu(), p.getb00ortzu())
				&& Objects.equals(buildAddress1(importRecord), buildAddress1(p))
				&& Objects.equals(buildAddress2(importRecord), buildAddress2(p))
				&& Objects.equals(importRecord.getb00plzzu1(), p.getb00plzzu1())
				&& Objects.equals(buildPOBox(importRecord), buildPOBox(p));
	}

	@Value
	public static class Address
	{
		int counrytId;
		@Nullable String city;
		@Nullable String address1;
		@Nullable String address2;
		@Nullable String postal;
		@Nullable String pobox;
		int bpLocationId;

		@Builder
		public Address(int counrytId, String city, String address1, String address2, String postal, String pobox, int bpLocationId)
		{
			this.counrytId = counrytId;
			this.city = city == null ? "" : city;
			this.address1 = address1 == null ? "" : address1;
			this.address2 = address2 == null ? "" : address2;
			this.postal = postal == null ? "" : postal;
			this.pobox = pobox == null ? "" : pobox;
			this.bpLocationId = bpLocationId;
		}

	}

	@Nullable
	private I_C_BPartner_Location createNewBPartnerLocation(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		if (isCountryAndCityFilled(importRecord)
				|| isSomeBPartnerLocationDetailsFilled(importRecord))
		{
			final I_C_BPartner bpartner = importRecord.getC_BPartner();
			final I_C_BPartner_Location bpartnerLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, bpartner);
			bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
			
			if (isCountryAndCityFilled(importRecord))
			{
				updateExistingBPartnerLocation(importRecord, bpartnerLocation);
			}
			else
			{
				updatePhoneAndFax(importRecord, bpartnerLocation);
				updateEmails(importRecord, bpartnerLocation);
			}

			InterfaceWrapperHelper.save(bpartnerLocation);
			return bpartnerLocation;
		}

		return null;
	}

	private boolean isCountryAndCityFilled(final I_I_Pharma_BPartner importRecord)
	{
		return importRecord.getC_Country_ID() > 0
				&& !Check.isEmpty(importRecord.getb00ortzu(), true);
	}

	private boolean isSomeBPartnerLocationDetailsFilled(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		return (!Check.isEmpty(importRecord.getb00email())
				|| !Check.isEmpty(importRecord.getb00email2())
				|| !Check.isEmpty(importRecord.getb00tel1())
				|| !Check.isEmpty(importRecord.getb00tel2())
				|| !Check.isEmpty(importRecord.getb00fax1())
				|| !Check.isEmpty(importRecord.getb00fax2()));
	}

	private void updateExistingBPartnerLocation(@NonNull final I_I_Pharma_BPartner importRecord, @NonNull final I_C_BPartner_Location bpartnerLocation)
	{
		updateLocation(importRecord, bpartnerLocation);
		bpartnerLocation.setIsShipTo(true);
		updatePhoneAndFax(importRecord, bpartnerLocation);
		updateEmails(importRecord, bpartnerLocation);
		importRecord.setC_BPartner_Location(bpartnerLocation);
	}

	private void updateLocation(@NonNull final I_I_Pharma_BPartner importRecord, @NonNull final I_C_BPartner_Location bpartnerLocation)
	{
		I_C_Location location = bpartnerLocation.getC_Location();
		if (location == null)
		{
			location = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		}
		updateExistingLocation(importRecord, location);
		bpartnerLocation.setC_Location(location);
	}

	private void updateExistingLocation(@NonNull final I_I_Pharma_BPartner importRecord, @NonNull final I_C_Location location)
	{
		location.setAddress1(buildAddress1(importRecord));
		location.setAddress2(buildAddress2(importRecord));
		location.setPostal(importRecord.getb00plzzu1());
		location.setCity(importRecord.getb00ortzu());
		if (importRecord.getC_Country_ID() > 0)
		{
			location.setC_Country_ID(importRecord.getC_Country_ID());
		}
		location.setPOBox(buildPOBox(importRecord));
		InterfaceWrapperHelper.save(location);
	}

	@VisibleForTesting
	String buildAddress1(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		final StringBuilder sb = new StringBuilder();
		if (!Check.isEmpty(importRecord.getb00str()))
		{
			sb.append(importRecord.getb00str());
		}
		if (!Check.isEmpty(importRecord.getb00hnrv()))
		{
			if (sb.length() > 0)
			{
				sb.append(" ");
			}
			sb.append(importRecord.getb00hnrv());
		}
		if (!Check.isEmpty(importRecord.getb00hnrvz()))
		{
			if (sb.length() > 0)
			{
				sb.append(" ");
			}
			sb.append(importRecord.getb00hnrvz());
		}

		return sb.toString();
	}

	@VisibleForTesting
	String buildAddress2(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		final StringBuilder sb = new StringBuilder();
		if (!Check.isEmpty(importRecord.getb00hnrb()))
		{
			sb.append(importRecord.getb00hnrb());
		}
		if (!Check.isEmpty(importRecord.getb00hnrbz()))
		{
			if (sb.length() > 0)
			{
				sb.append(" ");
			}
			sb.append(importRecord.getb00hnrbz());
		}

		return sb.toString();
	}

	@VisibleForTesting
	String buildPOBox(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		final StringBuilder sb = new StringBuilder();
		if (!Check.isEmpty(importRecord.getb00plzpf1()))
		{
			sb.append(importRecord.getb00plzpf1());
		}
		if (!Check.isEmpty(importRecord.getb00ortpf()))
		{
			if (sb.length() > 0)
			{
				sb.append("\\n");
			}
			sb.append(importRecord.getb00plzpf1());
		}
		if (!Check.isEmpty(importRecord.getb00pf1()))
		{
			if (sb.length() > 0)
			{
				sb.append("\\n");
			}
			sb.append(importRecord.getb00pf1());
		}
		if (!Check.isEmpty(importRecord.getb00plzgk1()))
		{
			if (sb.length() > 0)
			{
				sb.append("\\n");
			}
			sb.append(importRecord.getb00plzgk1());
		}

		return sb.toString();
	}

	private void updatePhoneAndFax(@NonNull final I_I_Pharma_BPartner importRecord, @NonNull final I_C_BPartner_Location bpartnerLocation)
	{
		if (!Check.isEmpty(importRecord.getb00tel1()))
		{
			bpartnerLocation.setPhone(importRecord.getb00tel1());
		}
		if (!Check.isEmpty(importRecord.getb00tel2()))
		{
			bpartnerLocation.setPhone2(importRecord.getb00tel2());
		}
		if (!Check.isEmpty(importRecord.getb00fax1()))
		{
			bpartnerLocation.setFax(importRecord.getb00fax1());
		}
		if (!Check.isEmpty(importRecord.getb00fax2()))
		{
			bpartnerLocation.setFax2(importRecord.getb00fax2());
		}
	}

	private void updateEmails(@NonNull final I_I_Pharma_BPartner importRecord, @NonNull final I_C_BPartner_Location bpartnerLocation)
	{
		if (!Check.isEmpty(importRecord.getb00email()))
		{
			bpartnerLocation.setEMail(importRecord.getb00email());
		}
		if (!Check.isEmpty(importRecord.getb00email2()))
		{
			bpartnerLocation.setEMail2(importRecord.getb00email2());
		}
	}
}
