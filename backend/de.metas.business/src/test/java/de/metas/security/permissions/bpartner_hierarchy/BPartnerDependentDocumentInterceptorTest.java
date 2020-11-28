package de.metas.security.permissions.bpartner_hierarchy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.request.RequestId;
import de.metas.user.UserId;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class BPartnerDependentDocumentInterceptorTest
{
	private BPartnerHierarchyTestHelper helper;

	@BeforeEach
	public void beforeEach()
	{
		helper = BPartnerHierarchyTestHelper.builder()
				.setupModelInterceptor(true)
				.setupBPartnerDependentDocumentEventDispatcher(true)
				.setupLoggedInUserId(true)
				.build();
	}

	@Test
	public void createNewChildBPartner_LinkedTo_ParentBPartner_OnCreationTime()
	{
		final UserId salesRepId = helper.createSalesRep("SalesRep1");

		//
		// Create BPartner1
		final BPartnerId bpartnerId1 = helper.createBPartnerId("BPartner1", null);
		helper.assertNoReadWritePermission(salesRepId, bpartnerId1);

		//
		// Assign BPartner1 to SalesRep1
		helper.bpHierarchyRecordAccessHandler.onBPartnerSalesRepChanged(BPartnerSalesRepChangedEvent.builder()
				.bpartnerId(bpartnerId1)
				.newSalesRepId(salesRepId)
				.changedBy(helper.loggedInUserId)
				.build());
		helper.assertReadWritePermission(salesRepId, bpartnerId1);

		//
		// Create BPartner2 which is linked to (parent) BPartner1 on creation time
		final BPartnerId bpartnerId2 = helper.createBPartnerId("BPartner2", bpartnerId1);
		// => assume model interceptor is triggered
		helper.assertReadWritePermission(salesRepId, bpartnerId1, bpartnerId2);

		//
		// Create a contact for BPartner2
		final BPartnerContactId bpartnerId2_contact1 = helper.createBPartnerContact(bpartnerId2, "BPartner2_Contact1");
		// => assume model interceptor is triggered
		helper.assertReadWritePermission(salesRepId, bpartnerId1, bpartnerId2, bpartnerId2_contact1);
	}

	@Test
	public void createNewChildBPartner_LinkedTo_ParentBPartner_Afterwards()
	{
		final UserId salesRepId = helper.createSalesRep("SalesRep1");

		//
		// Create BPartner1
		final BPartnerId bpartnerId1 = helper.createBPartnerId("BPartner1", null);
		helper.assertNoReadWritePermission(salesRepId, bpartnerId1);

		//
		// Assign BPartner1 to SalesRep1
		helper.bpHierarchyRecordAccessHandler.onBPartnerSalesRepChanged(BPartnerSalesRepChangedEvent.builder()
				.bpartnerId(bpartnerId1)
				.newSalesRepId(salesRepId)
				.changedBy(helper.loggedInUserId)
				.build());
		helper.assertReadWritePermission(salesRepId, bpartnerId1);

		//
		// Create BPartner2,
		// but don't link it to BPartner1 yet!
		final BPartnerId bpartnerId2 = helper.createBPartnerId("BPartner2", null);
		helper.assertNoReadWritePermission(salesRepId, bpartnerId2);

		//
		// Create a Contact for BPartner2
		final BPartnerContactId bpartnerId2_contact1 = helper.createBPartnerContact(bpartnerId2, "BPartner2_Contact1");
		helper.assertNoReadWritePermission(salesRepId, bpartnerId2_contact1);

		//
		// Create a Request for BPartner2
		final RequestId bpartner2_requestId = helper.createRequest(bpartnerId2);
		helper.assertNoReadWritePermission(salesRepId, bpartner2_requestId);

		//
		// Link BPartner2 to BPartner1
		{
			helper.prepareBPartnerUpdate()
					.bpartnerId(bpartnerId2)
					.newBPartnerSalesRepId(bpartnerId1)
					.update();
			// => assume model interceptor is triggered

			helper.assertReadWritePermission(salesRepId,
					bpartnerId1,
					bpartnerId2,
					bpartnerId2_contact1,
					bpartner2_requestId);
		}

	}

}
