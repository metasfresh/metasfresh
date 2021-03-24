package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class BPartnerDAO_retrieveBPartnerLocationTests
{
	private BPartnerDAO bpartnerDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		bpartnerDAO = new BPartnerDAO();
	}

	/** verifies that also with {@code applyTypeStrictly(false)}, if we have the desired type, it is returned. */
	@Test
	void billTo_notStrict_alsoShipTo()
	{
		final BPartnerId bpartnerId1 = createBPartnerWithName("BPartner 1");

		final BPLocationBuilder builder = new BPLocationBuilder(bpartnerId1)
				.billTo(false)
				.shipTo(true);
		builder.createRecord();

		final I_C_BPartner_Location billLocationRecord1 = builder
				.billTo(true)
				.shipTo(false)
				.createRecord();

		final BPartnerLocationQuery query = BPartnerLocationQuery.builder()
				.bpartnerId(bpartnerId1)
				.type(Type.BILL_TO)
				.applyTypeStrictly(false)
				.build();
		final BPartnerLocationId bpartnerLocationId = bpartnerDAO.retrieveBPartnerLocationId(query);
		assertThat(bpartnerLocationId).isNotNull();
		assertThat(bpartnerLocationId.getRepoId()).isEqualTo(billLocationRecord1.getC_BPartner_Location_ID());
	}

	/** verifies that with {@code applyTypeStrictly(false)}, if we don't have a location with the desired type, then one with another type is returned */
	@Test
	void billTo_notStrict_onlyShipTo()
	{
		final BPartnerId bpartnerId1 = createBPartnerWithName("BPartner 1");

		final BPLocationBuilder builder = new BPLocationBuilder(bpartnerId1)
				.billTo(false)
				.shipTo(true);
		final I_C_BPartner_Location shipLocationRecord1 = builder.createRecord();

		builder.createRecord(); // create another shipToLocation record

		final BPartnerLocationQuery query = BPartnerLocationQuery.builder()
				.bpartnerId(bpartnerId1)
				.type(Type.BILL_TO)
				.applyTypeStrictly(false)
				.build();
		final BPartnerLocationId bpartnerLocationId = bpartnerDAO.retrieveBPartnerLocationId(query);
		assertThat(bpartnerLocationId).isNotNull();
		assertThat(bpartnerLocationId.getRepoId()).isEqualTo(shipLocationRecord1.getC_BPartner_Location_ID());
	}



	/** verifies if there is a matching location in relation and returns that one , if found */
	@Test
	void billTo_withBPRelation()
	{
		final BPartnerId bpartnerId = createBPartnerWithName("BPartner 1");

		final BPLocationBuilder builder = new BPLocationBuilder(bpartnerId)
				.billTo(false)
				.shipTo(true);

		final I_C_BPartner_Location  deliveryLocationRecord = builder.createRecord();
		final BPartnerLocationId deliveryLocationId = BPartnerLocationId.ofRepoId(bpartnerId, deliveryLocationRecord.getC_BPartner_Location_ID());

		final I_C_BPartner_Location billLocationRecord1 = builder
				.billTo(true)
				.shipTo(false)
				.createRecord();
		final BPartnerLocationId billLocationRecord1Id = BPartnerLocationId.ofRepoId(bpartnerId, billLocationRecord1.getC_BPartner_Location_ID());

		// create a default billto address
		builder
		.billTo(true)
		.billToDefault(true)
		.shipTo(false)
		.createRecord();

		// create BPRelation
		createBillBPRelation(deliveryLocationId, billLocationRecord1Id);

		final BPartnerLocationQuery query = BPartnerLocationQuery.builder()
				.bpartnerId(bpartnerId)
				.type(Type.BILL_TO)
				.relationBPartnerLocationId(deliveryLocationId)
				.bpartnerId(bpartnerId)
				.build();


		final BPartnerLocationId bpartnerLocationId = bpartnerDAO.retrieveBPartnerLocationId(query);
		assertThat(bpartnerLocationId).isNotNull();
		assertThat(bpartnerLocationId.getRepoId()).isEqualTo(billLocationRecord1.getC_BPartner_Location_ID());
	}


	@Test
	void billTo_withoutBPRelation()
	{
		final BPartnerId bpartnerId = createBPartnerWithName("BPartner 1");

		final BPLocationBuilder builder = new BPLocationBuilder(bpartnerId)
				.billTo(false)
				.shipTo(true);

		final I_C_BPartner_Location  deliveryLocationRecord = builder.createRecord();
		final BPartnerLocationId deliveryLocationId = BPartnerLocationId.ofRepoId(bpartnerId, deliveryLocationRecord.getC_BPartner_Location_ID());

		final I_C_BPartner_Location billLocationRecord1 = builder
				.billTo(true)
				.shipTo(false)
				.createRecord();
		BPartnerLocationId.ofRepoId(bpartnerId, billLocationRecord1.getC_BPartner_Location_ID());

		// create a default billto address
		final I_C_BPartner_Location billLocationRecord2 = builder
				.billTo(true)
				.billToDefault(true)
				.shipTo(false)
				.createRecord();

		final BPartnerLocationQuery query = BPartnerLocationQuery.builder()
				.bpartnerId(bpartnerId)
				.type(Type.BILL_TO)
				.relationBPartnerLocationId(deliveryLocationId)
				.bpartnerId(bpartnerId)
				.build();


		final BPartnerLocationId bpartnerLocationId = bpartnerDAO.retrieveBPartnerLocationId(query);
		assertThat(bpartnerLocationId).isNotNull();
		assertThat(bpartnerLocationId.getRepoId()).isNotEqualTo(billLocationRecord1.getC_BPartner_Location_ID());
		assertThat(bpartnerLocationId.getRepoId()).isEqualTo(billLocationRecord2.getC_BPartner_Location_ID());
	}


	@Test
	void bpartnerLocationByIdEvenInactive()
	{
		final BPartnerId bpartnerId = createBPartnerWithName("BPartner");

		final I_C_BPartner_Location locationRecord = new BPLocationBuilder(bpartnerId)
				.active(false)
				.createRecord();

		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, locationRecord.getC_BPartner_Location_ID());

		final I_C_BPartner_Location expectedlocationRecord = bpartnerDAO.getBPartnerLocationByIdEvenInactive(bpartnerLocationId);
		assertThat(expectedlocationRecord).isNotNull();
		assertThat(expectedlocationRecord.getC_BPartner_Location_ID()).isEqualTo(locationRecord.getC_BPartner_Location_ID());
	}



	@Test
	void bpartnerLocationCountryIdWithIactiveLocation()
	{
		final BPartnerId bpartnerId = createBPartnerWithName("BPartner");

		final I_C_BPartner_Location locationRecord = new BPLocationBuilder(bpartnerId)
				.active(false)
				.createRecord();

		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, locationRecord.getC_BPartner_Location_ID());

		assertAll(() ->bpartnerDAO.getBPartnerLocationCountryId(bpartnerLocationId));
	}


	private void createBillBPRelation(final BPartnerLocationId deliveryLocationId, final BPartnerLocationId billLocationRecord1Id)
	{
		BPRelation.builder().billTo(true)
		.bpartnerId(deliveryLocationId.getBpartnerId())
		.bpLocationId(deliveryLocationId)
		.relBPartnerId(billLocationRecord1Id.getBpartnerId())
		.relBPLocationId(billLocationRecord1Id)
		.name("testName")
		.build()
		.createRecord();
	}


	private BPartnerId createBPartnerWithName(final String name)
	{
		final I_C_BPartner record = newInstance(I_C_BPartner.class);
		record.setName(name);
		saveRecord(record);

		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}
}
