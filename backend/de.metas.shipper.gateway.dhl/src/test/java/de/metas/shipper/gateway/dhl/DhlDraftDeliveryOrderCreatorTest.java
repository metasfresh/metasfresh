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
import de.metas.shipper.gateway.dhl.model.DhlClientConfigRepository;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.CreateDraftDeliveryOrderRequest;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator.DeliveryOrderKey;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.X_AD_OrgInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Recipient e-mail precedence of {@link DhlDraftDeliveryOrderCreator#createDraftDeliveryOrder(CreateDraftDeliveryOrderRequest)}:
 * the shipment's contact, then the delivery location, then the partner's active default contact.
 * {@code C_BPartner.EMail} is a virtual column that a project may redefine, so it must never be the source.
 */
class DhlDraftDeliveryOrderCreatorTest
{
	private static final String BPARTNER_VIRTUAL_COLUMN_EMAIL = "-";

	private DhlDraftDeliveryOrderCreator draftDeliveryOrderCreator;
	private I_C_Country countryDE;
	private int fromOrgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		draftDeliveryOrderCreator = new DhlDraftDeliveryOrderCreator(DhlClientConfigRepository.newInstanceForUnitTesting(), new UserRepository());

		countryDE = DhlDraftDeliveryOrderTestFixture.createCountry("DE");

		fromOrgId = createOrgWithLinkedBPartner();
	}

	@Test
	void defaultContactEmail_notTheBPartnerVirtualColumn()
	{
		final I_C_BPartner_Location deliverToBPLocation = createDeliverToBPartnerAndLocation(null);
		createDefaultContact(deliverToBPLocation.getC_BPartner_ID(), "default.contact@example.com");

		final DeliveryOrder deliveryOrder = createDraftDeliveryOrder(deliverToBPLocation, null);

		assertThat(deliveryOrder.getDeliveryContact().getEmailAddress()).isEqualTo("default.contact@example.com");
	}

	@Test
	void locationEmail_winsOverDefaultContact()
	{
		final I_C_BPartner_Location deliverToBPLocation = createDeliverToBPartnerAndLocation("location@example.com");
		createDefaultContact(deliverToBPLocation.getC_BPartner_ID(), "default.contact@example.com");

		final DeliveryOrder deliveryOrder = createDraftDeliveryOrder(deliverToBPLocation, null);

		assertThat(deliveryOrder.getDeliveryContact().getEmailAddress()).isEqualTo("location@example.com");
	}

	@Test
	void shipmentContactEmail_winsOverLocation()
	{
		final I_C_BPartner_Location deliverToBPLocation = createDeliverToBPartnerAndLocation("location@example.com");
		createDefaultContact(deliverToBPLocation.getC_BPartner_ID(), "default.contact@example.com");
		final UserId shipmentContactId = createContact(deliverToBPLocation.getC_BPartner_ID(), "shipment.contact@example.com", false);

		final DeliveryOrder deliveryOrder = createDraftDeliveryOrder(deliverToBPLocation, shipmentContactId);

		assertThat(deliveryOrder.getDeliveryContact().getEmailAddress()).isEqualTo("shipment.contact@example.com");
	}

	private DeliveryOrder createDraftDeliveryOrder(@NonNull final I_C_BPartner_Location deliverToBPLocation, @Nullable final UserId deliverToContactId)
	{
		return DhlDraftDeliveryOrderTestFixture.createDraftDeliveryOrder(
				draftDeliveryOrderCreator,
				fromOrgId,
				deliverToBPLocation,
				deliverToContactId,
				LocalDate.parse("2026-11-09"),
				PackageId.ofRepoId(1),
				null,
				PackageDimensions.builder().lengthInCM(10).widthInCM(10).heightInCM(10).build());
	}

	private int createOrgWithLinkedBPartner()
	{
		return DhlDraftDeliveryOrderTestFixture.createOrgWithLinkedBPartner(
				DhlDraftDeliveryOrderTestFixture.createBPartner("Shipper", null),
				countryDE, "Musterstrasse 1", null, "53113", "Bonn");
	}

	/**
	 * The partner's {@code EMail} is set to {@code "-"}, as a project that redefines the virtual column returns it.
	 */
	private I_C_BPartner_Location createDeliverToBPartnerAndLocation(@Nullable final String locationEmail)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName("Receiver");
		bpartner.setEMail(BPARTNER_VIRTUAL_COLUMN_EMAIL);
		saveRecord(bpartner);

		return DhlDraftDeliveryOrderTestFixture.createBPLocation(bpartner, countryDE, "Musterstrasse 1", null, "53113", "Bonn", locationEmail);
	}

	private void createDefaultContact(final int bpartnerId, @NonNull final String email)
	{
		createContact(bpartnerId, email, true);
	}

	private UserId createContact(final int bpartnerId, @NonNull final String email, final boolean isDefaultContact)
	{
		final I_AD_User contact = newInstance(I_AD_User.class);
		contact.setName(email);
		contact.setC_BPartner_ID(bpartnerId);
		contact.setEMail(email);
		contact.setIsDefaultContact(isDefaultContact);
		saveRecord(contact);
		return UserId.ofRepoId(contact.getAD_User_ID());
	}
}
