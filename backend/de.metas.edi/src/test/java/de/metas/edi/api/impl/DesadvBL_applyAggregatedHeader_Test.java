package de.metas.edi.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link DesadvBL#applyAggregatedHeader} and the static helper methods.
 * Pure unit tests — no DB, no Spring.
 */
class DesadvBL_applyAggregatedHeader_Test
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	// --- helpers

	private static Timestamp ts(final int year, final int month, final int day)
	{
		return Timestamp.from(LocalDate.of(year, month, day).atStartOfDay().toInstant(ZoneOffset.UTC));
	}

	private I_EDI_Desadv newDesadv()
	{
		return InterfaceWrapperHelper.newInstance(I_EDI_Desadv.class);
	}

	private I_M_InOut newInOut(final int bPartnerId, final int bPartnerLocationId, final String deliveryViaRule, final Timestamp movementDate)
	{
		final I_M_InOut inOut = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		inOut.setC_BPartner_ID(bPartnerId);
		inOut.setC_BPartner_Location_ID(bPartnerLocationId);
		inOut.setDeliveryViaRule(deliveryViaRule);
		inOut.setMovementDate(movementDate);
		return inOut;
	}

	private I_C_Order newOrder(
			final String poReference,
			final String deliveryViaRule,
			final Timestamp dateOrdered,
			final int currencyId,
			final int handOverPartnerId,
			final int handOverLocationId,
			final int dropShipBPartnerId,
			final int dropShipLocationId)
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setPOReference(poReference);
		order.setDeliveryViaRule(deliveryViaRule);
		order.setDateOrdered(dateOrdered);
		order.setC_Currency_ID(currencyId);
		order.setHandOver_Partner_ID(handOverPartnerId);
		order.setHandOver_Location_ID(handOverLocationId);
		order.setDropShip_BPartner_ID(dropShipBPartnerId);
		order.setDropShip_Location_ID(dropShipLocationId);
		return order;
	}

	// ====================================================================
	// Test scenarios
	// ====================================================================

	@Test
	void singleOrder_allFieldsFromThatOrder()
	{
		final Timestamp dateOrdered = ts(2026, 1, 10);
		final I_C_Order orderA = newOrder("PO-ALPHA", "S", dateOrdered, 101, 10, 11, 20, 21);

		final I_M_InOut inOut = newInOut(99, 98, "P", ts(2026, 3, 22));

		final I_EDI_Desadv desadv = newDesadv();
		DesadvBL.applyAggregatedHeader(desadv, inOut, ImmutableList.of(orderA));

		assertThat(desadv.getPOReference()).isEqualTo("PO-ALPHA");
		assertThat(desadv.getDeliveryViaRule()).isEqualTo("S");
		assertThat(desadv.getDateOrdered()).isEqualTo(dateOrdered);
		assertThat(desadv.getHandOver_Partner_ID()).isEqualTo(10);
		assertThat(desadv.getDropShip_BPartner_ID()).isEqualTo(20);
		// MovementDate is always from InOut
		assertThat(desadv.getMovementDate()).isEqualTo(ts(2026, 3, 22));
		// BPartner from InOut
		assertThat(desadv.getC_BPartner_ID()).isEqualTo(99);
	}

	@Test
	void twoOrders_samePOReference_keepsIt()
	{
		final I_C_Order orderA = newOrder("PO-BRAVO", "S", ts(2026, 1, 10), 101, 0, 0, 0, 0);
		final I_C_Order orderB = newOrder("PO-BRAVO", "S", ts(2026, 1, 10), 101, 0, 0, 0, 0);

		final I_M_InOut inOut = newInOut(99, 98, "P", ts(2026, 3, 22));

		final I_EDI_Desadv desadv = newDesadv();
		DesadvBL.applyAggregatedHeader(desadv, inOut, ImmutableList.of(orderA, orderB));

		assertThat(desadv.getPOReference()).isEqualTo("PO-BRAVO");
	}

	@Test
	void twoOrders_differentPOReferences_clearsIt()
	{
		final I_C_Order orderA = newOrder("PO-CHARLIE", "S", ts(2026, 1, 10), 101, 0, 0, 0, 0);
		final I_C_Order orderB = newOrder("PO-DELTA", "S", ts(2026, 1, 10), 101, 0, 0, 0, 0);

		final I_M_InOut inOut = newInOut(99, 98, "P", ts(2026, 3, 22));

		final I_EDI_Desadv desadv = newDesadv();
		DesadvBL.applyAggregatedHeader(desadv, inOut, ImmutableList.of(orderA, orderB));

		assertThat(desadv.getPOReference()).isNullOrEmpty();
	}

	@Test
	void twoOrders_differentDatesOrdered_picksEarliest()
	{
		final Timestamp dateA = ts(2026, 2, 1);
		final Timestamp dateB = ts(2026, 1, 15);

		final I_C_Order orderA = newOrder("PO-ECHO", "S", dateA, 101, 0, 0, 0, 0);
		final I_C_Order orderB = newOrder("PO-ECHO", "S", dateB, 101, 0, 0, 0, 0);

		final I_M_InOut inOut = newInOut(99, 98, "P", ts(2026, 3, 22));

		final I_EDI_Desadv desadv = newDesadv();
		DesadvBL.applyAggregatedHeader(desadv, inOut, ImmutableList.of(orderA, orderB));

		assertThat(desadv.getDateOrdered()).isEqualTo(dateB);
	}

	@Test
	void twoOrders_differentHandOverPartners_fallsBackToInOutBPartner()
	{
		final I_C_Order orderA = newOrder("PO-FOXTROT", "S", ts(2026, 1, 10), 101, 30, 31, 0, 0);
		final I_C_Order orderB = newOrder("PO-FOXTROT", "S", ts(2026, 1, 10), 101, 40, 41, 0, 0);

		final I_M_InOut inOut = newInOut(99, 98, "P", ts(2026, 3, 22));

		final I_EDI_Desadv desadv = newDesadv();
		DesadvBL.applyAggregatedHeader(desadv, inOut, ImmutableList.of(orderA, orderB));

		assertThat(desadv.getHandOver_Partner_ID()).isEqualTo(99);  // falls back to inOut.C_BPartner_ID
		assertThat(desadv.getHandOver_Location_ID()).isEqualTo(98); // falls back to inOut.C_BPartner_Location_ID
	}

	@Test
	void twoOrders_sameHandOverPartner_keepsIt()
	{
		final I_C_Order orderA = newOrder("PO-GOLF", "S", ts(2026, 1, 10), 101, 50, 51, 0, 0);
		final I_C_Order orderB = newOrder("PO-GOLF", "S", ts(2026, 1, 10), 101, 50, 51, 0, 0);

		final I_M_InOut inOut = newInOut(99, 98, "P", ts(2026, 3, 22));

		final I_EDI_Desadv desadv = newDesadv();
		DesadvBL.applyAggregatedHeader(desadv, inOut, ImmutableList.of(orderA, orderB));

		assertThat(desadv.getHandOver_Partner_ID()).isEqualTo(50);
		assertThat(desadv.getHandOver_Location_ID()).isEqualTo(51);
	}

	@Test
	void movementDate_alwaysFromInOut()
	{
		final Timestamp orderDate = ts(2026, 1, 10);
		final Timestamp movementDate = ts(2026, 3, 22);

		final I_C_Order orderA = newOrder("PO-HOTEL", "S", orderDate, 101, 0, 0, 0, 0);

		final I_M_InOut inOut = newInOut(99, 98, "P", movementDate);

		final I_EDI_Desadv desadv = newDesadv();
		DesadvBL.applyAggregatedHeader(desadv, inOut, ImmutableList.of(orderA));

		assertThat(desadv.getMovementDate()).isEqualTo(movementDate);
		// DateOrdered should be from order, not movement date
		assertThat(desadv.getDateOrdered()).isEqualTo(orderDate);
	}
}
