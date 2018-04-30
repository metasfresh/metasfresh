package de.metas.inoutcandidate.spi.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.uom.api.impl.UOMTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_ReceiptSchedule;

public class HUReceiptLineCandidatesBuilderTest
{
	// services
	private UOMTestHelper uomHelper;

	private IContextAware context;

	//
	// Master data
	private I_C_UOM uom;
	private I_M_ReceiptSchedule receiptSchedule;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx());

		//
		// Master data
		uomHelper = new UOMTestHelper(context.getCtx());
		uom = uomHelper.createUOM("UOM1", 2);
		receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class, context);
		receiptSchedule.setC_UOM(uom);
		InterfaceWrapperHelper.save(receiptSchedule);
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
		uom.setStdPrecision(0); // NOTE: Stuck/Each has precision=0

		//
		// Create HUReceiptLinePartCandidate
		//@formatter:off
		final QualityExpectations<Object> partsExpectations = QualityExpectations.newInstance()
				.receiptSchedule(receiptSchedule)
				.newQualityExpectation()
					.uom(uom)
					.qty("8")
					.qualityDiscountPercent("70")
					.qtyWithIssues("6") // = 5.6 rounded half up to 0 decimals
					.qtyWithoutIssues("2")
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
		uom.setStdPrecision(3); // NOTE: Kg has precision at least 3.

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
					.uom(uom).qty("2.500").qualityDiscountPercent("10");
			// Part: TU's NetWeightAdjusted part
			partsExpectations.newQualityExpectation()
					.attributesId(attributesId)
					.uom(uom).qty("0.125").qualityDiscountPercent("10");

			// // Aggregated HUReceiptLineCandidate expectation
			// aggregatedExpectations.newQualityExpectation()
			// .attributesId(attributesId)
			// .uom(uom)
			// .qty("2.625")
			// .qtyWithIssues("0.262") // = 2.625 * 10% = 0.2625 (rounded half down to 3 digits)
			// .qtyWithoutIssues("2.363") // = 2.625 - 0.262
			// .qualityDiscountPercent("10");
		}
		final HUReceiptLineCandidatesBuilder huReceiptLineCandidatesBuilder = partsExpectations.createHUReceiptLineCandidatesBuilder();

		//
		// Validate Candidates builder
		//@formatter:off
		aggregatedExpectations
			.aggregatedExpectation()
				.uom(uom)
				.qty("105")
				.qtyWithIssues("10.50")
				.qtyWithoutIssues("94.50")
				.qualityDiscountPercent("10")
				.endExpectation()
			.newQualityExpectation()
				.copyFrom(aggregatedExpectations.aggregatedExpectation())
				.endExpectation()
			.assertExpected(huReceiptLineCandidatesBuilder);
		;
		//@formatter:off
	}

	@Test
	public void test_10TUs_2TUsWithIssues_01()
	{
		uom.setStdPrecision(3); // NOTE: Kg has precision at least 3.

		final QualityExpectations<Object> partsExpectations = QualityExpectations.newInstance();
		//@formatter:off
		partsExpectations
			.receiptSchedule(receiptSchedule)
			// TU 1
			.newQualityExpectation().attributesId("TU01").uom(uom).qty(430).qualityDiscountPercent(5).endExpectation()
			.newQualityExpectation().attributesId("TU01").uom(uom).qty(510-430).qualityDiscountPercent(5).endExpectation()
			// TU 2
			.newQualityExpectation().attributesId("TU02").uom(uom).qty(430).qualityDiscountPercent(10).endExpectation()
			.newQualityExpectation().attributesId("TU02").uom(uom).qty(500-430).qualityDiscountPercent(10).endExpectation()
			// TU 3..10
			.newQualityExpectation().attributesId("TU03").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU04").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU05").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU06").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU07").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU08").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU09").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU10").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
		;
		//@formatter:on
		final HUReceiptLineCandidatesBuilder huReceiptLineCandidatesBuilder = partsExpectations.createHUReceiptLineCandidatesBuilder();

		final QualityExpectations<Object> aggregatedExpectations = QualityExpectations.newInstance();
		//@formatter:off
		aggregatedExpectations
			.receiptSchedule(receiptSchedule)
			// Aggregated values expectation
			.aggregatedExpectation()
				.uom(uom)
				.qty(4450) // = 510 + 500 + 8*430
				.qtyWithIssues("75.5") // = 510*5% + 500*10%
				.qtyWithoutIssues("4374.5") // = 4450 - 75.5
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
		uom.setStdPrecision(3); // NOTE: Kg has precision at least 3.

		final QualityExpectations<Object> partsExpectations = QualityExpectations.newInstance();
		//@formatter:off
		partsExpectations
			.receiptSchedule(receiptSchedule)
			// TU 1
			// => qtyWithIssues=21.75
			.newQualityExpectation().attributesId("TU01").uom(uom).qty(430).qualityDiscountPercent(5).endExpectation()
			.newQualityExpectation().attributesId("TU01").uom(uom).qty(435-430).qualityDiscountPercent(5).endExpectation()
			// TU 2
			// => qtyWithIssues=42.5
			.newQualityExpectation().attributesId("TU02").uom(uom).qty(430).qualityDiscountPercent(10).endExpectation()
			.newQualityExpectation().attributesId("TU02").uom(uom).qty(425-430).qualityDiscountPercent(10).endExpectation()
			// TU 3..10
			// => qtyWithIssues=0
			.newQualityExpectation().attributesId("TU03").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU04").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU05").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU06").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU07").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU08").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU09").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
			.newQualityExpectation().attributesId("TU10").uom(uom).qty(430).qualityDiscountPercent(0).endExpectation()
		;
		//@formatter:on
		final HUReceiptLineCandidatesBuilder huReceiptLineCandidatesBuilder = partsExpectations.createHUReceiptLineCandidatesBuilder();

		final QualityExpectations<Object> aggregatedExpectations = QualityExpectations.newInstance();
		//@formatter:off
		aggregatedExpectations
			.receiptSchedule(receiptSchedule)
			// Aggregated values expectation
			.aggregatedExpectation()
				.uom(uom)
				.qty(4300) // = (430 + 5) + (430 - 5) + 8*430
				.qtyWithIssues("64.25") // = (430 + 5)*5% + (430 - 5)*10%
				.qtyWithoutIssues("4235.75") // = 4300 - 64.25
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
