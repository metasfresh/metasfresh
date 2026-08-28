/*
 * #%L
 * de.metas.business
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

package de.metas.shipping.api.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.impl.CreateShipperTransportationRequest;
import de.metas.handlingunits.impl.ShipperTransportationQuery;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.X_M_ShipperTransportation;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link ShipperTransportationDAO#create(CreateShipperTransportationRequest)} must derive
 * {@code M_ShipperTransportation.TransportDirection} from the request's SOTrx flag - never hardcode
 * {@code Outgoing} - because the DAO is also reached from purchase-receipt paths.
 */
class ShipperTransportationDAOTest
{
	private IShipperTransportationDAO shipperTransportationDAO;
	private ShipperId shipperId;
	private BPartnerLocationId bpartnerAndLocationId;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

		final I_M_Shipper shipper = newInstance(I_M_Shipper.class);
		shipper.setName("Shipper1");
		save(shipper);
		shipperId = ShipperId.ofRepoId(shipper.getM_Shipper_ID());

		final I_C_BP_Group bpGroup = newInstance(I_C_BP_Group.class);
		save(bpGroup);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName("ShipperTransportationDAOTest-BPartner");
		bpartner.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		save(bpartner);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setAddress("Address1");
		save(bpLocation);

		bpartnerAndLocationId = BPartnerLocationId.ofRepoId(bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
	}

	private CreateShipperTransportationRequest.CreateShipperTransportationRequestBuilder requestBuilder()
	{
		return CreateShipperTransportationRequest.builder()
				.orgId(OrgId.ofRepoId(0))
				.shipperId(shipperId)
				.shipperBPartnerAndLocationId(bpartnerAndLocationId)
				.shipDate(LocalDate.of(2026, 8, 27))
				.assignAnonymouslyPickedHUs(false);
	}

	@Test
	@DisplayName("a purchase receipt's transport order must be Incoming, never the sales-shipment Outgoing")
	void createDerivesIncomingDirectionForPurchaseReceipt()
	{
		final CreateShipperTransportationRequest request = requestBuilder()
				.isSOTrx(false) // purchase receipt
				.build();

		final ShipperTransportationId shipperTransportationId = shipperTransportationDAO.create(request);

		final I_M_ShipperTransportation transportOrder = load(shipperTransportationId, I_M_ShipperTransportation.class);
		assertThat(transportOrder.getTransportDirection())
				.as("a purchase receipt must not be labelled Outgoing")
				.isEqualTo(X_M_ShipperTransportation.TRANSPORTDIRECTION_Incoming);
	}

	@Test
	@DisplayName("a sales shipment's transport order must be Outgoing")
	void createDerivesOutgoingDirectionForSalesShipment()
	{
		final CreateShipperTransportationRequest request = requestBuilder()
				.isSOTrx(true) // sales shipment
				.build();

		final ShipperTransportationId shipperTransportationId = shipperTransportationDAO.create(request);

		final I_M_ShipperTransportation transportOrder = load(shipperTransportationId, I_M_ShipperTransportation.class);
		assertThat(transportOrder.getTransportDirection())
				.isEqualTo(X_M_ShipperTransportation.TRANSPORTDIRECTION_Outgoing);
	}

	@Test
	@DisplayName("getOrCreate must adopt an existing transport order whose direction matches the request")
	void getOrCreateAdoptsExistingOrder_whenDirectionMatches()
	{
		final CreateShipperTransportationRequest request = requestBuilder()
				.isSOTrx(true) // sales shipment -> Outgoing
				.build();

		final ShipperTransportationId existingId = shipperTransportationDAO.create(request);

		final ShipperTransportationId foundId = shipperTransportationDAO.getOrCreate(request);

		assertThat(foundId)
				.as("a same-direction transport order for the same shipper/location/date/org must be reused")
				.isEqualTo(existingId);
	}

	@Test
	@DisplayName("getOrCreate must NOT adopt an existing transport order whose direction differs from the request - e.g. an Incoming one created by a receipt path, while the current request is a sales shipment")
	void getOrCreateCreatesNewOrder_whenExistingDirectionDiffers()
	{
		final CreateShipperTransportationRequest incomingRequest = requestBuilder()
				.isSOTrx(false) // purchase receipt -> Incoming
				.build();
		final ShipperTransportationId incomingId = shipperTransportationDAO.create(incomingRequest);

		final CreateShipperTransportationRequest outgoingRequest = requestBuilder()
				.isSOTrx(true) // sales shipment -> Outgoing
				.build();

		final ShipperTransportationId foundId = shipperTransportationDAO.getOrCreate(outgoingRequest);

		assertThat(foundId)
				.as("an Incoming transport order must never be silently reused for an Outgoing shipment")
				.isNotEqualTo(incomingId);

		final I_M_ShipperTransportation createdOrder = load(foundId, I_M_ShipperTransportation.class);
		assertThat(createdOrder.getTransportDirection())
				.isEqualTo(X_M_ShipperTransportation.TRANSPORTDIRECTION_Outgoing);
	}

	@Test
	@DisplayName("a query with no explicit direction still matches transport orders of every direction - existing callers keep matching what they matched before")
	void getByQuery_matchesAnyDirection_whenTransportDirectionUnset()
	{
		final ShipperTransportationId incomingId = shipperTransportationDAO.create(requestBuilder().isSOTrx(false).build());
		final ShipperTransportationId outgoingId = shipperTransportationDAO.create(requestBuilder().isSOTrx(true).build());

		final ShipperTransportationQuery query = ShipperTransportationQuery.builder()
				.shipperId(shipperId)
				.shipperBPartnerAndLocationId(bpartnerAndLocationId)
				.orgId(OrgId.ofRepoId(0))
				.build();

		final Collection<I_M_ShipperTransportation> matches = shipperTransportationDAO.getByQuery(query);

		assertThat(matches)
				.extracting(I_M_ShipperTransportation::getM_ShipperTransportation_ID)
				.as("no transportDirection set on the query must not narrow the match by direction")
				.contains(incomingId.getRepoId(), outgoingId.getRepoId());
	}

	@Test
	@DisplayName("omitting isSOTrx must fail loudly - it must never fall back to a silent Incoming")
	void requestWithoutSOTrx_cannotBeBuilt()
	{
		final CreateShipperTransportationRequest.CreateShipperTransportationRequestBuilder builderWithoutSOTrx = requestBuilder();

		assertThatThrownBy(builderWithoutSOTrx::build)
				.as("a caller that forgets the transaction direction must not silently create an Incoming transport order")
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("isSOTrx");
	}
}
