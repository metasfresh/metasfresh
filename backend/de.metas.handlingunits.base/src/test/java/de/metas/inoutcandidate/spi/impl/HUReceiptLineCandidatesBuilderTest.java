package de.metas.inoutcandidate.spi.impl;

import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;

import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import de.metas.uom.impl.UOMTestHelper;

public class HUReceiptLineCandidatesBuilderTest
{
	// services
	private UOMTestHelper uomHelper;

	private IContextAware context;

	//
	// Master data
	private I_C_UOM uomRecord;

	private I_M_ReceiptSchedule receiptSchedule;

	private ProductId productId;

	@Before
	public void init()
	{
		// the code under tests includes WeightableFactory which assumes that some weight-related I_AD_Attributes to exist. that's why we use HUTestHelper to set them up.
		new HUTestHelper();
		context = PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx());

		//
		// Master data
		uomHelper = new UOMTestHelper(context.getCtx());
		uomRecord = uomHelper.createUOM("UOM1", 2);

		final I_C_UOM stockUOMRecord = uomHelper.createUOM("StockUOM1", 2);
		final I_M_Product productRecord = BusinessTestHelper.createProduct("TestProduct", stockUOMRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_C_BPartner bpPartnerRecord = BusinessTestHelper.createBPartner("TestVendor");

		final I_M_Warehouse warehouseRecord = BusinessTestHelper.createWarehouse("TestWarehouse");

		// we need to be able to convert between stocking-uom and the receiptSchedule's UOM
		// we use a trivial conversion because we don'T want to complicate the tests (and we don't want to test uom-conversion in here)
		uomHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(productId)
				.fromUomId(UomId.ofRepoId(uomRecord.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(stockUOMRecord.getC_UOM_ID()))
				.fromToMultiplier(ONE)
				.build());

		receiptSchedule = newInstance(I_M_ReceiptSchedule.class, context);
		receiptSchedule.setC_UOM_ID(uomRecord.getC_UOM_ID());
		receiptSchedule.setM_Product_ID(productRecord.getM_Product_ID());
		receiptSchedule.setC_BPartner_ID(bpPartnerRecord.getC_BPartner_ID());
		receiptSchedule.setM_Warehouse_ID(warehouseRecord.getM_Warehouse_ID());
		saveRecord(receiptSchedule);
	}

	/**
	 * Test for use case:
	 * <ul>
	 * <li>make a bestellung with Batavia (stuck) on 1 IFCO 6416 x 8
	 * <li>complete order
	 * <li>go to wareneingang pos and set quality discount to 70% (which means 5.6 are with issue)
	 * <li>auswahl, ok
	 * <li>go to inout: quality discount is set to 75 and 6 stk are with issues
	 * </ul>
	 */
	@Test
	public void test_07847_01()
	{
		uomRecord.setStdPrecision(0); // NOTE: Stuck/Each has precision=0

		final StockQtyAndUOMQty qty_8 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("8.00"), productId);
		final StockQtyAndUOMQty qty_5_6 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("5.6"), productId);
		final StockQtyAndUOMQty qty_2_4 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("2.4"), productId);

		//
		// Create HUReceiptLinePartCandidate
		//@formatter:off
		final QualityExpectations<Object> partsExpectations = QualityExpectations.newInstance()
				.receiptSchedule(receiptSchedule)
				.newQualityExpectation()
					.uom(uomRecord)
					.qty(qty_8)
					.qualityDiscountPercent("70")
					.qtyWithIssues(qty_5_6)
					.qtyWithoutIssues(qty_2_4)
					.endExpectation();
		//@formatter:on
		final HUReceiptLineCandidatesBuilder huReceiptLineCandidatesBuilder = partsExpectations.createHUReceiptLineCandidatesBuilder();

		//
		//
		//@formatter:off
		QualityExpectations.newInstance()
				.receiptSchedule(receiptSchedule)
				.aggregatedExpectation()
					.copyFrom(partsExpectations.qualityExpectation(0))
					.endExpectation()
				.newQualityExpectation()
					.copyFrom(partsExpectations.qualityExpectation(0))
					.endExpectation()
				.assertExpected(huReceiptLineCandidatesBuilder)
		;
		//@formatter:on
	}

	/**
	 * Test for use case:
	 * <ul>
	 * <li>make a bestellung with Batavia Ind (kg) - no uom conversion on 40 G2 x 2.5, complete order
	 * <li>go to wareneingang pos, set the net weight to 105 and set quality discount to 10%
	 * <li>auswahl, ok
	 * <li>go to inout: quality discount is set to 9.98 and 10.48 kg are with issue
	 * </ul>
	 */
	@Test
	public void test_07847_02()
	{
		uomRecord.setStdPrecision(3); // NOTE: Kg has precision at least 3.

		final StockQtyAndUOMQty qty_2_500 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("2.500"), productId);
		final StockQtyAndUOMQty qty_0_125 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("0.125"), productId);
		final StockQtyAndUOMQty qty_105 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("105"), productId);
		final StockQtyAndUOMQty qty_10_50 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("10.50"), productId);
		final StockQtyAndUOMQty qty_94_50 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("94.50"), productId);

		final QualityExpectations<Object> partsExpectations = QualityExpectations.newInstance()
				.receiptSchedule(receiptSchedule);
		final QualityExpectations<Object> aggregatedExpectations = QualityExpectations.newInstance()
				.receiptSchedule(receiptSchedule);

		for (int i = 1; i <= 40; i++)
		{
			final String attributesId = "TU" + i;
			// Part: TU
			partsExpectations.newQualityExpectation()
					.attributesId(attributesId)
					.uom(uomRecord).qty(qty_2_500).qualityDiscountPercent("10");
			// Part: TU's NetWeightAdjusted part
			partsExpectations.newQualityExpectation()
					.attributesId(attributesId)
					.uom(uomRecord).qty(qty_0_125).qualityDiscountPercent("10");

			// // Aggregated HUReceiptLineCandidate expectation
			// aggregatedExpectations.newQualityExpectation()
			// .attributesId(attributesId)
			// .uom(uom)
			// .qty("2.625")
			// .qtyWithIssues("0.262") // = 2.625 * 10% = 0.2625 (rounded half down to 3 digits)
			// .qtyWithoutIssues("2.363") // = 2.625 - 0.262
			// .qualityDiscountPercent("10");
		}

		// invoke the method under test
		final HUReceiptLineCandidatesBuilder huReceiptLineCandidatesBuilder = partsExpectations.createHUReceiptLineCandidatesBuilder();

		//
		// Validate Candidates builder
		//@formatter:off
		aggregatedExpectations
			.aggregatedExpectation()
				.uom(uomRecord)
				.qty(qty_105)
				.qtyWithIssues(qty_10_50)
				.qtyWithoutIssues(qty_94_50)
				.qualityDiscountPercent("10")
				.endExpectation()
			.newQualityExpectation()
				.copyFrom(aggregatedExpectations.aggregatedExpectation())
				.endExpectation()
			.assertExpected(huReceiptLineCandidatesBuilder);

		//@formatter:off
	}

	@Test
	public void test_10TUs_2TUsWithIssues_01()
	{
		uomRecord.setStdPrecision(3); // NOTE: Kg has precision at least 3.

		final StockQtyAndUOMQty qty_430 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal(430), productId);
		final StockQtyAndUOMQty qty_80 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal(510-430), productId);
		final StockQtyAndUOMQty qty_70 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal(500-430), productId);

		final StockQtyAndUOMQty qty_4450 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal(4450), productId); // = 510 + 500 + 8*430
		final StockQtyAndUOMQty qty_75_5 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("75.5"), productId);  // = 510*5% + 500*10%
		final StockQtyAndUOMQty qty_4374_5 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("4374.5"), productId);  // = 4450 - 75.5

		final QualityExpectations<Object> partsExpectations = QualityExpectations.newInstance();
		//@formatter:off
		partsExpectations
			.receiptSchedule(receiptSchedule)
			// TU 1
			.newQualityExpectation().attributesId("TU01").uom(uomRecord).qty(qty_430).qualityDiscountPercent(5).endExpectation()
			.newQualityExpectation().attributesId("TU01").uom(uomRecord).qty(qty_80).qualityDiscountPercent(5).endExpectation()
			// TU 2
			.newQualityExpectation().attributesId("TU02").uom(uomRecord).qty(qty_430).qualityDiscountPercent(10).endExpectation()
			.newQualityExpectation().attributesId("TU02").uom(uomRecord).qty(qty_70).qualityDiscountPercent(10).endExpectation()
			// TU 3..10
			.newQualityExpectation().attributesId("TU03").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU04").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU05").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU06").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU07").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU08").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU09").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU10").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
		;
		//@formatter:on

		// invoke the method under test
		final HUReceiptLineCandidatesBuilder huReceiptLineCandidatesBuilder = partsExpectations.createHUReceiptLineCandidatesBuilder();

		final QualityExpectations<Object> aggregatedExpectations = QualityExpectations.newInstance();
		//@formatter:off
		aggregatedExpectations
			.receiptSchedule(receiptSchedule)
			// Aggregated values expectation
			.aggregatedExpectation()
				.uom(uomRecord)
				.qty(qty_4450) // = 510 + 500 + (8*430)
				.qtyWithIssues(qty_75_5) // = 510*5% + 500*10%
				.qtyWithoutIssues(qty_4374_5) // = 4450 - 75.5
				.qualityDiscountPercent("1.70") // = 75.5 / 4450 * 100
				.endExpectation()
			// ReceiptLine Candidates expectations
			.newQualityExpectation()
				.copyFrom(aggregatedExpectations.aggregatedExpectation())
				.endExpectation()
			.assertExpected(huReceiptLineCandidatesBuilder)
		;
		//@formatter:on
	}

	@Test
	public void test_10TUs_2TUsWithIssues_02()
	{
		uomRecord.setStdPrecision(3); // NOTE: Kg has precision at least 3.

		final StockQtyAndUOMQty qty_430 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal(430), productId);
		final StockQtyAndUOMQty qty_5 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal(435 - 430), productId);
		final StockQtyAndUOMQty qty_minus_5 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal(425 - 430), productId);

		final StockQtyAndUOMQty qty_4300 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal(4300), productId); // = (430 + 5) + (430 - 5) + 8*430
		final StockQtyAndUOMQty qty_64_25 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("64.25"), productId);  // = (430 + 5)*5% + (430 - 5)*10%
		final StockQtyAndUOMQty qty_4235_75 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("4235.75"), productId); // = 4300 - 64.25

		final QualityExpectations<Object> partsExpectations = QualityExpectations.newInstance();
		//@formatter:off
		partsExpectations
			.receiptSchedule(receiptSchedule)
			// TU 1
			// => qtyWithIssues=21.75
			.newQualityExpectation().attributesId("TU01").uom(uomRecord).qty(qty_430).qualityDiscountPercent(5).endExpectation()
			.newQualityExpectation().attributesId("TU01").uom(uomRecord).qty(qty_5).qualityDiscountPercent(5).endExpectation()
			// TU 2
			// => qtyWithIssues=42.5
			.newQualityExpectation().attributesId("TU02").uom(uomRecord).qty(qty_430).qualityDiscountPercent(10).endExpectation()
			.newQualityExpectation().attributesId("TU02").uom(uomRecord).qty(qty_minus_5).qualityDiscountPercent(10).endExpectation()
			// TU 3..10
			// => qtyWithIssues=0
			.newQualityExpectation().attributesId("TU03").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU04").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU05").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU06").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU07").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU08").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU09").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU10").uom(uomRecord).qty(qty_430).qualityDiscountPercent(0).endExpectation()
		;
		//@formatter:on
		final HUReceiptLineCandidatesBuilder huReceiptLineCandidatesBuilder = partsExpectations.createHUReceiptLineCandidatesBuilder();

		final QualityExpectations<Object> aggregatedExpectations = QualityExpectations.newInstance();
		//@formatter:off
		aggregatedExpectations
			.receiptSchedule(receiptSchedule)
			// Aggregated values expectation
			.aggregatedExpectation()
				.uom(uomRecord)
				.qty(qty_4300) // = (430 + 5) + (430 - 5) + 8*430
				.qtyWithIssues(qty_64_25) // = (430 + 5)*5% + (430 - 5)*10%
				.qtyWithoutIssues(qty_4235_75) // = 4300 - 64.25
				.qualityDiscountPercent("1.49") // = 64.25/4300 * 100= 1.4941860465
				.endExpectation()
			// ReceiptLine Candidates expectations
			.newQualityExpectation()
				.copyFrom(aggregatedExpectations.aggregatedExpectation())
				.endExpectation()
			.assertExpected(huReceiptLineCandidatesBuilder)
		;
		//@formatter:on
	}

}
