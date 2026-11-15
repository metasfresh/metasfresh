/*
 * #%L
 * de.metas.shipper.gateway.dhl
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.product.PackageDimensions;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.DeliveryOrderKey;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.X_AD_OrgInfo;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * In-memory records and the request needed to run {@link DhlDraftDeliveryOrderCreator#createDraftDeliveryOrder(CreateDraftDeliveryOrderRequest)}.
 * Needs an initialized {@code AdempiereTestHelper}.
 */
@UtilityClass
class DhlDraftDeliveryOrderTestFixture
{
	static I_C_Country createCountry(@NonNull final String alpha2)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode(alpha2);
		saveRecord(country);
		return country;
	}

	static I_C_BPartner createBPartner(@NonNull final String name, @Nullable final String name2)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName(name);
		bpartner.setName2(name2);
		return bpartner;
	}

	static I_C_BPartner_Location createBPLocation(
			@NonNull final I_C_BPartner bpartner,
			@NonNull final I_C_Country country,
			@NonNull final String address1,
			@Nullable final String address2,
			@NonNull final String postal,
			@NonNull final String city,
			@Nullable final String email)
	{
		saveRecord(bpartner);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1(address1);
		location.setAddress2(address2);
		location.setPostal(postal);
		location.setCity(city);
		location.setC_Country_ID(country.getC_Country_ID());
		saveRecord(location);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setC_Location_ID(location.getC_Location_ID());
		bpLocation.setEMail(email);
		saveRecord(bpLocation);
		return bpLocation;
	}

	/**
	 * @return the org id; its linked partner is {@code orgBPartner} with {@code orgBPLocation} as the org location
	 */
	static int createOrgWithLinkedBPartner(@NonNull final I_C_BPartner orgBPartner, @NonNull final I_C_Country country, @NonNull final String address1, @Nullable final String address2, @NonNull final String postal, @NonNull final String city)
	{
		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setName("Shipper Org");
		saveRecord(org);

		orgBPartner.setAD_OrgBP_ID(org.getAD_Org_ID());
		final I_C_BPartner_Location orgBPLocation = createBPLocation(orgBPartner, country, address1, address2, postal, city, null);

		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(orgBPartner.getC_BPartner_ID());
		orgInfo.setOrgBP_Location_ID(orgBPLocation.getC_BPartner_Location_ID());
		orgInfo.setStoreCreditCardData(X_AD_OrgInfo.STORECREDITCARDDATA_Letzte4Stellen);
		saveRecord(orgInfo);

		return org.getAD_Org_ID();
	}

	static DeliveryOrder createDraftDeliveryOrder(
			@NonNull final DhlDraftDeliveryOrderCreator draftDeliveryOrderCreator,
			final int fromOrgId,
			@NonNull final I_C_BPartner_Location deliverToBPLocation,
			@Nullable final UserId deliverToContactId,
			@NonNull final LocalDate pickupDate,
			@NonNull final PackageId packageId,
			@Nullable final BigDecimal weightInKg,
			@NonNull final PackageDimensions packageDimensions)
	{
		final DeliveryOrderKey deliveryOrderKey = DeliveryOrderKey.builder()
				.shipperId(ShipperId.ofRepoId(1))
				.shipperTransportationId(ShipperTransportationId.ofRepoId(1))
				.fromOrgId(fromOrgId)
				.deliverToBPartnerId(deliverToBPLocation.getC_BPartner_ID())
				.deliverToBPartnerLocationId(deliverToBPLocation.getC_BPartner_Location_ID())
				.deliverToContactId(deliverToContactId)
				.pickupDate(pickupDate)
				.timeFrom(LocalTime.of(8, 0))
				.timeTo(LocalTime.of(17, 0))
				.build();

		final CreateDraftDeliveryOrderRequest.PackageInfo packageInfo = CreateDraftDeliveryOrderRequest.PackageInfo.builder()
				.packageId(packageId)
				.weightInKg(weightInKg)
				.packageDimension(packageDimensions)
				.build();

		return draftDeliveryOrderCreator.createDraftDeliveryOrder(CreateDraftDeliveryOrderRequest.builder()
				.deliveryOrderKey(deliveryOrderKey)
				.packageInfos(ImmutableSet.of(packageInfo))
				.build());
	}
}
