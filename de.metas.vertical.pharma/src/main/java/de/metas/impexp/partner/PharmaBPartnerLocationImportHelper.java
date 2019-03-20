package de.metas.impexp.partner;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

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

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
/* package */ class PharmaBPartnerLocationImportHelper
{
	final private Comparator<Address> addressComparator = Comparator.comparing(Address::getCity)
			.thenComparing(Address::getStreet1)
			.thenComparing(Address::getStreet2)
			.thenComparing(Address::getStreet3)
			.thenComparing(Address::getStreet4)
			.thenComparing(Address::getPostal)
			.thenComparing(Address::getPobox)
			.thenComparingInt(Address::getCounrytId);

	public I_C_BPartner_Location importRecord(
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
		return bpartnerLocation;
	}

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

		return updateOldAddressIfExistsAndMatches(importRecord, bpartnerLocation);
	}

	private I_C_BPartner_Location updateOldAddressIfExistsAndMatches(final I_I_Pharma_BPartner importRecord, I_C_BPartner_Location bpartnerLocation)
	{
		final List<I_C_BPartner_Location> existentBPLocations = Services.get(IBPartnerDAO.class).retrieveBPartnerLocations(importRecord.getC_BPartner());
		if (!existentBPLocations.isEmpty())
		{
			final Address importAddress = Address.builder()
					.counrytId(importRecord.getC_Country_ID())
					.city(importRecord.getb00ortzu())
					.street1(importRecord.getb00str())
					.street2(importRecord.getb00hnrv())
					.street3(importRecord.getb00hnrvz())
					.street4(importRecord.getb00hnrb() + " " + importRecord.getb00hnrbz())
					.postal(importRecord.getb00plzzu1())
					.pobox(buildPOBox(importRecord))
					.build();

			final List<Address> matchedAddreses = existentBPLocations.stream()
					.map(bpLocation -> Address.builder()
							.counrytId(importRecord.getC_Country_ID())
							.city(importRecord.getb00ortzu())
							.street1(importRecord.getb00str())
							.street2(importRecord.getb00hnrv())
							.street3(importRecord.getb00hnrvz())
							.street4(importRecord.getb00hnrb() + " " + importRecord.getb00hnrbz())
							.postal(importRecord.getb00plzzu1())
							.pobox(buildPOBox(importRecord))
							.bpLocationId(bpLocation.getC_BPartner_Location_ID())
							.build())
					.filter(address -> addressComparator.compare(address, importAddress) == 0)
					.collect(ImmutableList.toImmutableList());

			if (matchedAddreses.size() > 0)
			{
				bpartnerLocation = Services.get(IBPartnerDAO.class).getBPartnerLocationById(BPartnerLocationId.ofRepoId(importRecord.getC_BPartner_ID(), matchedAddreses.get(0).getBpLocationId()));
				updateExistingBPartnerLocation(importRecord,bpartnerLocation);
			}

		}
		return bpartnerLocation;
	}

	private List<I_I_Pharma_BPartner> getImportRecordsWithEqualAddresses(
			@NonNull final I_I_Pharma_BPartner importRecord,
			@NonNull final List<I_I_Pharma_BPartner> previousImportRecordsForSameBPartner)
	{
		final List<I_I_Pharma_BPartner> alreadyImportedBPAddresses = previousImportRecordsForSameBPartner.stream()
				.filter(createEqualAddressFilter(importRecord))
				.collect(Collectors.toList());

		return alreadyImportedBPAddresses;
	}

	private Predicate<I_I_Pharma_BPartner> createEqualAddressFilter(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		return p -> p.getC_BPartner_Location_ID() > 0
				&& importRecord.getC_Country_ID() == p.getC_Country_ID()
				&& Objects.equals(importRecord.getb00ortzu(), p.getb00ortzu())
				&& Objects.equals(importRecord.getb00str(), p.getb00str())
				&& Objects.equals(importRecord.getb00hnrv(), p.getb00hnrv())
				&& Objects.equals(importRecord.getb00hnrvz(), p.getb00hnrvz())
				&& Objects.equals(importRecord.getb00hnrb() + " " + importRecord.getb00hnrbz(), p.getb00hnrb() + " " + p.getb00hnrbz())
				&& Objects.equals(importRecord.getb00plzzu1(), p.getb00plzzu1());
	}

	@Builder
	@Value
	public class Address
	{
		final int counrytId;
		final String city;
		final String street1;
		final String street2;
		final String street3;
		final String street4;
		final String postal;
		final String pobox;;
		final int bpLocationId;

	}

	private I_C_BPartner_Location createNewBPartnerLocation(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		if (importRecord.getC_Country_ID() > 0
				&& !Check.isEmpty(importRecord.getb00ortzu(), true))
		{
			final I_C_BPartner bpartner = importRecord.getC_BPartner();
			final I_C_BPartner_Location bpartnerLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, bpartner);
			bpartnerLocation.setC_BPartner(bpartner);
			updateExistingBPartnerLocation(importRecord, bpartnerLocation);
			return bpartnerLocation;
		}

		return null;
	}

	private void updateExistingBPartnerLocation(@NonNull final I_I_Pharma_BPartner importRecord, @NonNull final I_C_BPartner_Location bpartnerLocation)
	{
		updateLocation(importRecord, bpartnerLocation);

		bpartnerLocation.setIsShipTo(true);

		updatePhoneAndFax(importRecord, bpartnerLocation);

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

	private static void updateExistingLocation(@NonNull final I_I_Pharma_BPartner importRecord, @NonNull final I_C_Location location)
	{
		location.setAddress1(importRecord.getb00str());
		location.setAddress2(importRecord.getb00hnrv());
		location.setAddress3(importRecord.getb00hnrvz());
		location.setAddress4(importRecord.getb00hnrb() + " " + importRecord.getb00hnrbz());
		location.setPostal(importRecord.getb00plzzu1());
		location.setCity(importRecord.getb00ortzu());
		location.setC_Country_ID(importRecord.getC_Country_ID());
		location.setPOBox(buildPOBox(importRecord));
		InterfaceWrapperHelper.save(location);
	}

	private String buildPOBox(@NonNull final I_I_Pharma_BPartner importRecord)
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

}
