/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.order.location.adapter;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.greeting.GreetingRepository;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.user.UserRepository;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@SuppressWarnings("ConstantConditions")
class OrderMainLocationAdapterTest
{
	private IDocumentLocationBL documentLocationBL;

	private CountryId countryDE;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		documentLocationBL = new DocumentLocationBL(new BPartnerBL(new UserRepository()));
		SpringContextHolder.registerJUnitBean(new GreetingRepository());

		countryDE = createCountry("DE", "@A1@ @CO@");
	}

	public CountryId createCountry(@NonNull final String countryCode, @Nullable String addressFormat)
	{
		final I_C_Country record = newInstance(I_C_Country.class);
		record.setCountryCode(countryCode);
		record.setName(countryCode);
		record.setDisplaySequence(addressFormat);
		record.setDisplaySequenceLocal(addressFormat);
		POJOWrapper.setInstanceName(record, countryCode);
		saveRecord(record);
		return CountryId.ofRepoId(record.getC_Country_ID());
	}

	@Builder(builderMethodName = "documentLocation", builderClassName = "$DocumentLocation")
	private DocumentLocation createDocumentLocation(
			@NonNull final String bpName,
			@NonNull final String address1)
	{
		final I_C_BPartner bpartner = BusinessTestHelper.createBPartner(bpName);

		final I_AD_User user = newInstance(I_AD_User.class);
		user.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		saveRecord(user);

		final DocumentLocation documentLocation = DocumentLocation.builder()
				.bpartnerId(BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()))
				.contactId(BPartnerContactId.ofRepoId(user.getC_BPartner_ID(), user.getAD_User_ID()))
				.build();

		return withNewBPartnerLocation(documentLocation, address1);
	}

	private DocumentLocation withNewBPartnerLocation(
			@NonNull final DocumentLocation documentLocation,
			@NonNull final String address1)
	{
		final LocationId locationId = createLocation(address1);

		final I_C_BPartner_Location bpl = newInstance(I_C_BPartner_Location.class);
		bpl.setC_BPartner_ID(documentLocation.getBpartnerId().getRepoId());
		bpl.setC_Location_ID(locationId.getRepoId());
		saveRecord(bpl);

		return documentLocation.toBuilder()
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpl.getC_BPartner_ID(), bpl.getC_BPartner_Location_ID()))
				.locationId(locationId)
				.bpartnerAddress(null) // needs calculation
				.build();
	}

	private LocationId createLocation(final String address1)
	{
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(countryDE.getRepoId());
		location.setAddress1(address1);
		saveRecord(location);
		return LocationId.ofRepoId(location.getC_Location_ID());
	}

	private OrderMainLocationAdapter adapter(@NonNull final I_C_Order order)
	{
		return new OrderMainLocationAdapter(order);
	}

	@Nested
	class updateCapturedLocationAndRenderedAddressIfNeeded
	{
		@Nested
		class newRecord
		{
			@Test
			void withoutLocationIdSet()
			{
				final DocumentLocation documentLocation = documentLocation().bpName("bp1").address1("addr1").build();

				final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
				order.setC_BPartner_ID(documentLocation.getBpartnerId().getRepoId());
				order.setC_BPartner_Location_ID(documentLocation.getBpartnerLocationId().getRepoId());
				order.setC_BPartner_Location_Value_ID(-1);
				order.setBPartnerAddress("we expect this to be set");
				order.setAD_User_ID(documentLocation.getContactId().getRepoId());

				adapter(order).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);

				Assertions.assertThat(adapter(order).toDocumentLocation())
						.usingRecursiveComparison()
						.isEqualTo(documentLocation.toBuilder()
										   .bpartnerAddress("addr1\nDE")
										   .build());
			}

			@Test
			void withLocationIdSet()
			{
				final DocumentLocation documentLocation = documentLocation().bpName("bp1").address1("addr1").build();
				final LocationId addr2 = createLocation("addr2");

				final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
				order.setC_BPartner_ID(documentLocation.getBpartnerId().getRepoId());
				order.setC_BPartner_Location_ID(documentLocation.getBpartnerLocationId().getRepoId());
				order.setC_BPartner_Location_Value_ID(addr2.getRepoId());
				order.setBPartnerAddress("we expect this to be set");
				order.setAD_User_ID(documentLocation.getContactId().getRepoId());

				adapter(order).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);

				Assertions.assertThat(adapter(order).toDocumentLocation())
						.usingRecursiveComparison()
						.isEqualTo(documentLocation.toBuilder()
										   .locationId(addr2)
										   .bpartnerAddress("addr2\nDE")
										   .build());
			}
		}

		@Nested
		class existingRecord
		{
			I_C_Order createExistingOrder(DocumentLocation documentLocation)
			{
				final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
				adapter(order).setFrom(documentLocation);
				InterfaceWrapperHelper.saveRecord(order);
				return order;
			}

			@Test
			void changeC_BPartner_Location_ID()
			{
				final DocumentLocation location = documentLocation().bpName("bp1").address1("addr1").build();
				final I_C_Order order = createExistingOrder(location);
				order.setBPartnerAddress("we expect this to be set");

				final DocumentLocation newLocation = withNewBPartnerLocation(location, "addr2");
				order.setC_BPartner_Location_ID(newLocation.getBpartnerLocationId().getRepoId());
				adapter(order).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);

				Assertions.assertThat(adapter(order).toDocumentLocation())
						.usingRecursiveComparison()
						.isEqualTo(newLocation.toBuilder()
										   .bpartnerAddress("addr2\nDE")
										   .build());
			}

			@Test
			void changeC_BPartner_Location_Value_ID()
			{
				final DocumentLocation location = documentLocation().bpName("bp1").address1("addr1").build();
				final I_C_Order order = createExistingOrder(location);
				order.setBPartnerAddress("we expect this to be set");

				final LocationId newLocationId = createLocation("addr2");
				order.setC_BPartner_Location_Value_ID(newLocationId.getRepoId());
				adapter(order).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);

				Assertions.assertThat(adapter(order).toDocumentLocation())
						.usingRecursiveComparison()
						.isEqualTo(location.toBuilder()
										   .locationId(newLocationId)
										   .bpartnerAddress("addr2\nDE")
										   .build());
			}

			@Test
			void changeBPartnerAddress()
			{
				final DocumentLocation location = documentLocation().bpName("bp1").address1("addr1").build();
				final I_C_Order order = createExistingOrder(location);
				order.setBPartnerAddress("expect this value to be preserved");

				adapter(order).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);

				Assertions.assertThat(adapter(order).toDocumentLocation())
						.usingRecursiveComparison()
						.isEqualTo(location.toBuilder()
										   .bpartnerAddress("expect this value to be preserved")
										   .build());
			}

		}
	}
}