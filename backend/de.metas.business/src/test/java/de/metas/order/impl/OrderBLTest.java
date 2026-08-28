/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.order.impl;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.effective.BPartnerEffectiveBL;
import de.metas.order.InvoiceRule;
import de.metas.order.OrderId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.StringUtils;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

class OrderBLTest
{
	private OrderBL orderBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		BPartnerEffectiveBL.newInstanceForUnitTesting();
		orderBL = new OrderBL();
	}

	// bill-location - get Bill_Location_ID, maybe fallback to C_BPartner_Location_ID
	////////////////////////////////
	@Test
	void givenNoBillPartner_whenGetBillToLocationId_thenReturnAD_User_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);
		assertThat(orderBL.getBillToLocationId(order)).isEqualTo(BPartnerLocationAndCaptureId.ofRepoId(10, 20, 0));
	}

	@Test
	void givenSameBillPartnerWithoutLocation_whenGetBillToLocationId_thenReturnC_BPartner_Location_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);
		order.setBill_BPartner_ID(10);

		assertThat(orderBL.getBillToLocationId(order)).isEqualTo(BPartnerLocationAndCaptureId.ofRepoId(10, 20, 0));
	}

	@Test
	void givenDifferentBillPartnerWithoutLocation_whenGetBillToLocationId_thenReturnC_BPartner_Location_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);
		order.setBill_BPartner_ID(30); // Bill_BPartner_ID will be ignored! but in practice we never have a Bill_BPartner_ID without Bill_Location_ID

		assertThat(orderBL.getBillToLocationId(order)).isEqualTo(BPartnerLocationAndCaptureId.ofRepoId(10, 20, 0));
	}

	@Test
	void givenDifferentBillPartnerWithLocation_whenGetBillToLocationId_thenReturnBill_Location_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);
		order.setBill_BPartner_ID(30);
		order.setBill_Location_ID(40);

		assertThat(orderBL.getBillToLocationId(order)).isEqualTo(BPartnerLocationAndCaptureId.ofRepoId(30, 40, 0));
	}

	// bill-contact - get Bill_User_ID, maybe fallback to AD_User_ID
	////////////////////////////////
	@Test
	void givenNoBillPartner_whenGetBillToContactId_thenReturnAD_User_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setAD_User_ID(20);

		assertThat(orderBL.getBillToContactIdOrNull(order)).isEqualTo(BPartnerContactId.ofRepoId(10, 20));
	}

	@Test
	void givenNoBillPartnerAndNotContact_whenGetBillToContactId_thenReturnNull()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);

		assertThat(orderBL.getBillToContactIdOrNull(order)).isNull();
	}

	@Test
	void givenSameBillPartnerWithoutContact_whenGetBillToContactId_thenReturnAD_User_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setAD_User_ID(20);
		order.setBill_BPartner_ID(10);

		assertThat(orderBL.getBillToContactIdOrNull(order)).isEqualTo(BPartnerContactId.ofRepoId(10, 20));
	}

	@Test
	void givenDifferentBillPartnerWithoutContact_whenGetBillToContactId_thenReturnNull()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setAD_User_ID(20);
		order.setBill_BPartner_ID(30);

		assertThat(orderBL.getBillToContactIdOrNull(order)).isNull();
	}

	@Test
	void givenDifferentBillPartnerWithContact_whenGetBillToContactId_thenReturnBill_User_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setAD_User_ID(20);
		order.setBill_BPartner_ID(30);
		order.setBill_User_ID(40);

		assertThat(orderBL.getBillToContactIdOrNull(order)).isEqualTo(BPartnerContactId.ofRepoId(30, 40));
	}

	// setBPartner - InvoiceRule + IsAutoInvoice resolution via BP group
	////////////////////////////////

	@Nested
	class SetBPartner_InvoiceRuleAndIsAutoInvoice
	{
		/**
		 * Sales order, bp with null InvoiceRule/IsAutoInvoice, BP group has
		 * InvoiceRule=AfterDelivery and IsAutoInvoice=Y.
		 * After setBPartner the order must carry the group's values.
		 */
		@Test
		void bpHasNullInvoiceRule_bpGroupHasAfterDelivery_thenOrderGetsGroupValues()
		{
			// setup BP group with InvoiceRule=AfterDelivery, IsAutoInvoice=Y
			final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
			bpGroup.setInvoiceRule(InvoiceRule.AfterDelivery.getCode());
			bpGroup.setIsAutoInvoice(StringUtils.ofBoolean(true));
			saveRecord(bpGroup);

			// bp has no own InvoiceRule / IsAutoInvoice
			final I_C_BPartner bp = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			bp.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
			saveRecord(bp);

			final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
			order.setIsSOTrx(true);

			orderBL.setBPartner(order, bp);

			assertThat(order.getInvoiceRule())
					.as("order InvoiceRule must be AfterDelivery (from BP group)")
					.isEqualTo(InvoiceRule.AfterDelivery.getCode());
			assertThat(order.isAutoInvoice())
					.as("order IsAutoInvoice must be true (from BP group)")
					.isTrue();
		}

		/**
		 * Order has a distinct Bill_BPartner_ID. The bill partner's group has InvoiceRule=AfterDelivery + IsAutoInvoice=Y,
		 * while the passed (ship) bp's group has InvoiceRule=Immediate + IsAutoInvoice=N.
		 * After setBPartner the order must carry the BILL partner's effective values, not the ship bp's.
		 */
		@Test
		void separateBillBPartnerWithAfterDelivery_thenOrderGetsBillPartnerValues()
		{
			// ship bp group: InvoiceRule=Immediate, IsAutoInvoice=N
			final I_C_BP_Group shipGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
			shipGroup.setInvoiceRule(InvoiceRule.Immediate.getCode());
			shipGroup.setIsAutoInvoice(StringUtils.ofBoolean(false));
			saveRecord(shipGroup);

			final I_C_BPartner shipBp = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			shipBp.setC_BP_Group_ID(shipGroup.getC_BP_Group_ID());
			saveRecord(shipBp);

			// bill bp group: InvoiceRule=AfterDelivery, IsAutoInvoice=Y
			final I_C_BP_Group billGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
			billGroup.setInvoiceRule(InvoiceRule.AfterDelivery.getCode());
			billGroup.setIsAutoInvoice(StringUtils.ofBoolean(true));
			saveRecord(billGroup);

			final I_C_BPartner billBp = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			billBp.setC_BP_Group_ID(billGroup.getC_BP_Group_ID());
			saveRecord(billBp);

			final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
			order.setIsSOTrx(true);
			order.setBill_BPartner_ID(billBp.getC_BPartner_ID());

			orderBL.setBPartner(order, shipBp);

			assertThat(order.getInvoiceRule())
					.as("order InvoiceRule must come from the BILL partner (AfterDelivery), not the ship bp (Immediate)")
					.isEqualTo(InvoiceRule.AfterDelivery.getCode());
			assertThat(order.isAutoInvoice())
					.as("order IsAutoInvoice must come from the BILL partner (true), not the ship bp (false)")
					.isTrue();
		}

		/**
		 * bp with explicit InvoiceRule=Immediate — partner value wins over group.
		 */
		@Test
		void bpHasExplicitImmediateInvoiceRule_thenOrderGetsImmediateRule()
		{
			// setup BP group with InvoiceRule=AfterDelivery (lower-priority)
			final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
			bpGroup.setInvoiceRule(InvoiceRule.AfterDelivery.getCode());
			saveRecord(bpGroup);

			// bp has explicit InvoiceRule=Immediate
			final I_C_BPartner bp = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			bp.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
			bp.setInvoiceRule(InvoiceRule.Immediate.getCode());
			saveRecord(bp);

			final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
			order.setIsSOTrx(true);

			orderBL.setBPartner(order, bp);

			assertThat(order.getInvoiceRule())
					.as("order InvoiceRule must be Immediate (partner value wins)")
					.isEqualTo(InvoiceRule.Immediate.getCode());
			assertThat(order.isAutoInvoice())
					.as("order IsAutoInvoice must be false (no group value set)")
					.isFalse();
		}

		/**
		 * Purchase order: bp with null PO_InvoiceRule.
		 * BPartnerEffectiveBL.poInvoiceRule resolution has no group-chain (PO path reads only
		 * I_C_BPartner.PO_InvoiceRule, then falls back to the sysconfig default = AfterDelivery).
		 * The BP group's SO-side InvoiceRule field is intentionally irrelevant on the PO path.
		 * IsAutoInvoice must be false on the PO path (BPartnerEffective.isAutoInvoice(PURCHASE) always returns false).
		 */
		@Test
		void purchaseOrder_bpHasNullPoInvoiceRule_getsSystemDefault()
		{
			// BP group SO-side InvoiceRule=AfterDelivery — must have NO effect on the PO path
			final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
			bpGroup.setInvoiceRule(InvoiceRule.AfterDelivery.getCode());
			bpGroup.setIsAutoInvoice(StringUtils.ofBoolean(true)); // group has Y, but PO path must ignore it
			saveRecord(bpGroup);

			// bp has no own PO_InvoiceRule
			final I_C_BPartner bp = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			bp.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
			saveRecord(bp);

			final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
			order.setIsSOTrx(false); // purchase order

			orderBL.setBPartner(order, bp);

			assertThat(order.getInvoiceRule())
					.as("purchase order InvoiceRule must be AfterDelivery (sysconfig default, not from BP group SO field)")
					.isEqualTo(InvoiceRule.AfterDelivery.getCode());
			assertThat(order.isAutoInvoice())
					.as("purchase order IsAutoInvoice must be false (PO path always false)")
					.isFalse();
		}
	}

	// syncDatesFromTransportOrder - each of BLDate/ETA is copied from the transport order INDEPENDENTLY: a field
	// the transport order genuinely has no value for must not wipe an already-set value on the order. The two
	// dates travelling together on one call is convenient, not a reason to treat them as one all-or-nothing write.
	////////////////////////////////

	@Nested
	class SyncDatesFromTransportOrder
	{
		private I_M_ShipperTransportation transportOrder;
		private I_C_Order order;

		@BeforeEach
		void beforeEach()
		{
			transportOrder = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
			order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
			saveRecord(order);
		}

		@Test
		void bothDatesSetOnTransportOrder_thenBothApplied()
		{
			transportOrder.setBLDate(Timestamp.valueOf("2026-08-01 00:00:00"));
			transportOrder.setETA(Timestamp.valueOf("2026-08-05 00:00:00"));

			orderBL.syncDatesFromTransportOrder(OrderId.ofRepoId(order.getC_Order_ID()), transportOrder);

			final I_C_Order reloaded = InterfaceWrapperHelper.load(order.getC_Order_ID(), I_C_Order.class);
			assertThat(reloaded.getBLDate()).isEqualTo(Timestamp.valueOf("2026-08-01 00:00:00"));
			assertThat(reloaded.getETA()).isEqualTo(Timestamp.valueOf("2026-08-05 00:00:00"));
		}

		@Test
		void transportOrderHasNoETA_thenOrdersExistingETAIsNotWiped()
		{
			order.setETA(Timestamp.valueOf("2026-07-01 00:00:00"));
			saveRecord(order);

			transportOrder.setBLDate(Timestamp.valueOf("2026-08-01 00:00:00"));
			// transportOrder.ETA left unset (null)

			orderBL.syncDatesFromTransportOrder(OrderId.ofRepoId(order.getC_Order_ID()), transportOrder);

			final I_C_Order reloaded = InterfaceWrapperHelper.load(order.getC_Order_ID(), I_C_Order.class);
			assertThat(reloaded.getBLDate())
					.as("the transport order's BLDate is genuinely set, so it must still be applied")
					.isEqualTo(Timestamp.valueOf("2026-08-01 00:00:00"));
			assertThat(reloaded.getETA())
					.as("the transport order carries no ETA - the order's own, already-set ETA must survive untouched")
					.isEqualTo(Timestamp.valueOf("2026-07-01 00:00:00"));
		}

		@Test
		void transportOrderHasNoBLDate_thenOrdersExistingBLDateIsNotWiped()
		{
			order.setBLDate(Timestamp.valueOf("2026-06-01 00:00:00"));
			saveRecord(order);

			transportOrder.setETA(Timestamp.valueOf("2026-08-05 00:00:00"));
			// transportOrder.BLDate left unset (null)

			orderBL.syncDatesFromTransportOrder(OrderId.ofRepoId(order.getC_Order_ID()), transportOrder);

			final I_C_Order reloaded = InterfaceWrapperHelper.load(order.getC_Order_ID(), I_C_Order.class);
			assertThat(reloaded.getETA())
					.as("the transport order's ETA is genuinely set, so it must still be applied")
					.isEqualTo(Timestamp.valueOf("2026-08-05 00:00:00"));
			assertThat(reloaded.getBLDate())
					.as("the transport order carries no BLDate - the order's own, already-set BLDate must survive untouched")
					.isEqualTo(Timestamp.valueOf("2026-06-01 00:00:00"));
		}
	}
}
