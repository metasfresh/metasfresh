package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.handlingunits.picking.config.mobileui.PickAttribute;
import de.metas.handlingunits.pporder.api.impl.HUPPOrderBL_LotNumberFromSequence_Test;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.eevolution.api.PPOrderId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class FinishedGoodsReceiptDefaultsTest
{
	private static final ImmutableSet<PickAttribute> ALL_EDITABLE = ImmutableSet.of(PickAttribute.BestBeforeDate, PickAttribute.LotNo);

	private PPOrderId ppOrderId;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IDocumentNoBuilderFactory.class, DocumentNoBuilderFactory.newInstanceForUnitTesting());
		Services.get(ISysConfigBL.class).setValue("de.metas.document.seqNo.DateSequenceProvider.dateFormat", "yy-DDD", ClientId.SYSTEM, OrgId.ANY);

		// 2026-10-19 in UTC, but already 2026-10-20 01:00 in the order's org time zone
		SystemTime.setFixedTimeSource(ZonedDateTime.parse("2026-10-19T12:00:00Z"));
		ppOrderId = HUPPOrderBL_LotNumberFromSequence_Test.createPPOrderBOM(
				HUPPOrderBL_LotNumberFromSequence_Test.createPPOrder(AdempiereTestHelper.createOrgWithTimeZone(ZoneId.of("Pacific/Auckland"))),
				HUPPOrderBL_LotNumberFromSequence_Test.createDateLotSequence());
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	@Test
	void receiptDayIsTodayInOrgTimeZone()
	{
		final FinishedGoodsReceiptDefaults defaults = FinishedGoodsReceiptDefaults.of(ppOrderId, ALL_EDITABLE);

		assertThat(defaults.getBestBeforeDate(createProductWithBestBeforeDays(10))).isEqualTo(LocalDate.of(2026, 10, 30));
		assertThat(defaults.getLotNo()).isEqualTo("26-293");
	}

	@Test
	void bestBeforeDaysNotSet_noBestBeforeDate()
	{
		final FinishedGoodsReceiptDefaults defaults = FinishedGoodsReceiptDefaults.of(ppOrderId, ALL_EDITABLE);

		assertThat(defaults.getBestBeforeDate(createProductWithBestBeforeDays(0))).isNull();
	}

	/**
	 * A lot number that is not editable is still shown (read-only), so its preview is computed anyway.
	 */
	@Test
	void attributesNotEditable_noBestBeforeDefault_butLotPreview()
	{
		final FinishedGoodsReceiptDefaults defaults = FinishedGoodsReceiptDefaults.of(ppOrderId, ImmutableSet.of());

		assertThat(defaults.getBestBeforeDate(createProductWithBestBeforeDays(10))).isNull();
		assertThat(defaults.getLotNo()).isEqualTo("26-293");
	}

	private static ProductId createProductWithBestBeforeDays(final int bestBeforeDays)
	{
		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		product.setValue("product");
		product.setName("product");
		product.setBestBeforeDays(bestBeforeDays);
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}
}
