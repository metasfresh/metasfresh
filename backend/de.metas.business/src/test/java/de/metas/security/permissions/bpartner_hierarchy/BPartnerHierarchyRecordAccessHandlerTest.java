package de.metas.security.permissions.bpartner_hierarchy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.request.RequestId;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.RecordAccess;
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

@ExtendWith(AdempiereTestWatcher.class)
public class BPartnerHierarchyRecordAccessHandlerTest
{
	private BPartnerHierarchyTestHelper helper;

	@BeforeEach
	public void beforeEach()
	{
		helper = BPartnerHierarchyTestHelper.builder()
				.setupModelInterceptor(false)
				.setupBPartnerDependentDocumentEventDispatcher(false)
				.setupLoggedInUserId(false)
				.build();
	}

	@Test
	public void bpartnerHierarchyFeatureIsEnabled()
	{
		assertThat(helper.bpHierarchyRecordAccessHandler.isEnabled()).isTrue();
	}

	@Test
	public void onBPartnerSalesRepChanged_checkPropagation()
	{
		final UserId salesRepId = helper.createSalesRep("SalesRep1");

		final BPartnerId rootBPartnerId = helper.createBPartnerId("BPartner", null);
		final BPartnerId bpartnerId = helper.createBPartnerId("BPartner", rootBPartnerId);
		final BPartnerContactId contact1 = helper.createBPartnerContact(bpartnerId, "Contact 1");
		final BPartnerContactId contact2 = helper.createBPartnerContact(bpartnerId, "Contact 2");
		final RequestId requestId = helper.createRequest(bpartnerId);
		final OrderId orderId = helper.createOrder(bpartnerId);
		final InvoiceId invoiceId = helper.createInvoice(bpartnerId);
		final InOutId inoutId = helper.createInOut(bpartnerId);
		final PaymentId paymentId = helper.createPayment(bpartnerId);

		final TableRecordReference[] allRecordRefs = new TableRecordReference[] {
				TableRecordReference.of(I_C_BPartner.Table_Name, rootBPartnerId),
				TableRecordReference.of(I_C_BPartner.Table_Name, bpartnerId),
				TableRecordReference.of(I_AD_User.Table_Name, contact1),
				TableRecordReference.of(I_AD_User.Table_Name, contact2),
				TableRecordReference.of(I_R_Request.Table_Name, requestId),
				TableRecordReference.of(I_C_Order.Table_Name, orderId),
				TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId),
				TableRecordReference.of(I_M_InOut.Table_Name, inoutId),
				TableRecordReference.of(I_C_Payment.Table_Name, paymentId),
		};
		helper.assertNoReadWritePermission(salesRepId, allRecordRefs);

		helper.bpHierarchyRecordAccessHandler.onBPartnerSalesRepChanged(BPartnerSalesRepChangedEvent.builder()
				.bpartnerId(rootBPartnerId)
				.newSalesRepId(salesRepId)
				.changedBy(UserId.ofRepoId(222))
				.build());
		helper.assertReadWritePermission(salesRepId, allRecordRefs);

		//
		// Make sure all access records have the right CreatedBy
		for (final RecordAccess access : helper.getAllRecordAccess(allRecordRefs))
		{
			assertThat(access.getCreatedBy()).isEqualTo(UserId.ofRepoId(222));
		}

		//
		// Make sure the Parent_ID and Root_ID are correctly set
		for (final Access permission : Arrays.asList(Access.READ, Access.WRITE))
		{
			final RecordAccess rootBPartner_access = helper.getRecordAccess(I_C_BPartner.Table_Name, rootBPartnerId, permission);
			assertThat(rootBPartner_access.getRootId()).isEqualTo(rootBPartner_access.getId());
			assertThat(rootBPartner_access.getParentId()).isNull();

			final RecordAccess childBPartner_access = helper.getRecordAccess(I_C_BPartner.Table_Name, bpartnerId, permission);
			assertThat(childBPartner_access.getRootId()).isEqualTo(rootBPartner_access.getId());
			assertThat(childBPartner_access.getParentId()).isEqualTo(rootBPartner_access.getId());

			// i=0 => rootBPartner
			// i=1 => childBPartner
			for (int i = 2; i < allRecordRefs.length; i++)
			{
				final TableRecordReference recordRef = allRecordRefs[i];
				final RecordAccess record_access = helper.getRecordAccess(recordRef, permission);
				assertThat(record_access.getRootId()).isEqualTo(rootBPartner_access.getId());
				assertThat(record_access.getParentId()).isEqualTo(childBPartner_access.getId());
			}
		}

		helper.bpHierarchyRecordAccessHandler.onBPartnerSalesRepChanged(BPartnerSalesRepChangedEvent.builder()
				.bpartnerId(rootBPartnerId)
				.oldSalesRepId(salesRepId)
				.newSalesRepId(null)
				.changedBy(UserId.ofRepoId(111))
				.build());
		helper.assertNoReadWritePermission(salesRepId, allRecordRefs);
	}
}
