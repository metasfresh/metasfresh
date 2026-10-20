package de.metas.handlingunits.pporder.api.impl;

import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Best-before date a manufacturing receipt sets on the received HUs.
 */
public class PPOrderReceiptHUProducer_BestBeforeDate_Test
{
	// 2026-10-19 in UTC, but already 2026-10-20 in the order's org time zone
	private static final ZonedDateTime MOVEMENT_DATE = ZonedDateTime.parse("2026-10-19T12:00:00Z");
	private static final LocalDate RECEIPT_DAY_IN_ORG_TIME_ZONE = LocalDate.of(2026, 10, 20);

	private OrgId orgId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		orgId = AdempiereTestHelper.createOrgWithTimeZone(ZoneId.of("Pacific/Auckland"));
	}

	@Test
	public void noBestBeforeDateGiven_receiptDayPlusBestBeforeDays()
	{
		final AbstractPPOrderReceiptHUProducer producer = newProducer(10);
		producer.defaultBestBeforeDateFromProduct();

		assertThat(producer.getBestBeforeDateToSet(null)).isEqualTo(RECEIPT_DAY_IN_ORG_TIME_ZONE.plusDays(10));
	}

	@Test
	public void defaultComputedOncePerReceipt()
	{
		final AbstractPPOrderReceiptHUProducer producer = newProducer(10);
		producer.defaultBestBeforeDateFromProduct();
		assertThat(producer.getBestBeforeDateToSet(null)).isEqualTo(RECEIPT_DAY_IN_ORG_TIME_ZONE.plusDays(10));

		producer.movementDate(MOVEMENT_DATE.plusDays(5));

		assertThat(producer.getBestBeforeDateToSet(null)).isEqualTo(RECEIPT_DAY_IN_ORG_TIME_ZONE.plusDays(10));
	}

	@Test
	public void bestBeforeDateGiven_kept()
	{
		final AbstractPPOrderReceiptHUProducer producer = newProducer(10);
		producer.defaultBestBeforeDateFromProduct().bestBeforeDate(LocalDate.of(2026, 12, 24));

		assertThat(producer.getBestBeforeDateToSet(null)).isEqualTo(LocalDate.of(2026, 12, 24));
	}

	@Test
	public void bestBeforeDaysNotSet_none()
	{
		final AbstractPPOrderReceiptHUProducer producer = newProducer(0);
		producer.defaultBestBeforeDateFromProduct();

		assertThat(producer.getBestBeforeDateToSet(null)).isNull();
	}

	@Test
	public void huAlreadyHasBestBeforeDate_notOverwritten()
	{
		final AbstractPPOrderReceiptHUProducer producer = newProducer(10);
		producer.defaultBestBeforeDateFromProduct();

		assertThat(producer.getBestBeforeDateToSet(LocalDate.of(2026, 11, 1))).isNull();
	}

	@Test
	public void defaultNotRequested_none()
	{
		assertThat(newProducer(10).getBestBeforeDateToSet(null)).isNull();
	}

	@NonNull
	private AbstractPPOrderReceiptHUProducer newProducer(final int bestBeforeDays)
	{
		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		product.setValue("product");
		product.setName("product");
		product.setBestBeforeDays(bestBeforeDays);
		saveRecord(product);

		final PPOrderId ppOrderId = HUPPOrderBL_LotNumberFromSequence_Test.createPPOrder(orgId);
		final I_PP_Order ppOrder = load(ppOrderId, I_PP_Order.class);
		ppOrder.setM_Product_ID(product.getM_Product_ID());
		saveRecord(ppOrder);

		final AbstractPPOrderReceiptHUProducer producer = new CostCollectorCandidateFinishedGoodsHUProducer(ppOrder);
		producer.movementDate(MOVEMENT_DATE);
		return producer;
	}
}
