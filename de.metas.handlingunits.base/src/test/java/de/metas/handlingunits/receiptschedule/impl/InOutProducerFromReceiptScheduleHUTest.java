package de.metas.handlingunits.receiptschedule.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.compiere.util.TimeUtil;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.expectations.HUAttributeExpectation;
import de.metas.handlingunits.expectations.HUWeightsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.InOutGenerateResult;

/**
 * Test creation of material receipts ({@link I_M_InOut}s) from scheduled receipts ({@link I_M_ReceiptSchedule}s) and how line aggregations are made based on products, packing and ASIs.
 *
 * @author al
 */
public class InOutProducerFromReceiptScheduleHUTest extends AbstractRSAllocationWithWeightAttributeTest
{
	/**
	 * We're expecting just one receipt line per product and no quality issues
	 */
	@Test
	public void testNoASIChangeAndNoQualityIssues()
	{
		final List<I_M_HU> paloxes = createStandardHUsAndAssignThemToReceiptSchedule();

		//
		// Generate receipt
		final I_M_InOut receipt = generateReceiptFromReceiptSchedule(paloxes);

		//
		// Validate Receipt
		//@formatter:off
		InOutLineExpectations.newExpectations()
			.newInOutLineExpectation()
				.qtyEnteredTU(10)
				.newHUAssignmentExpectation().hu(paloxes.get(0)).luHU(null).tuHU(null).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(0)).luHU(null).tuHU(paloxes.get(0)).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(1)).luHU(null).tuHU(null).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(1)).luHU(null).tuHU(paloxes.get(1)).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(2)).luHU(null).tuHU(null).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(2)).luHU(null).tuHU(paloxes.get(2)).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(3)).luHU(null).tuHU(null).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(3)).luHU(null).tuHU(paloxes.get(3)).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(4)).luHU(null).tuHU(null).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(4)).luHU(null).tuHU(paloxes.get(4)).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(5)).luHU(null).tuHU(null).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(5)).luHU(null).tuHU(paloxes.get(5)).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(6)).luHU(null).tuHU(null).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(6)).luHU(null).tuHU(paloxes.get(6)).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(7)).luHU(null).tuHU(null).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(7)).luHU(null).tuHU(paloxes.get(7)).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(8)).luHU(null).tuHU(null).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(8)).luHU(null).tuHU(paloxes.get(8)).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(9)).luHU(null).tuHU(null).endExpectation()
				.newHUAssignmentExpectation().hu(paloxes.get(9)).luHU(null).tuHU(paloxes.get(9)).endExpectation()
				//
				.product(pTomato).movementQty("4300").noASIDescription()
				.inDispute(false)
				.endExpectation()
			.newInOutLineExpectation()
				.product(pPaloxe).movementQty("10").noASIDescription()
				.inDispute(false)
				.endExpectation()
			.assertExpected(receipt); // lines count expected: 2
		//@formatter:on
	}

	/**
	 * We're expecting different receipt lines per different ASI on handling units
	 */
	@Test
	public void testDifferentASIAndQualityIssues()
	{
		final List<I_M_HU> paloxes = createStandardHUsAndAssignThemToReceiptSchedule();
		assertThat(paloxes.size(), is(10)); // guard

		//
		// Setup paloxe attribute structure
		{
			{
				final I_M_HU paloxe1 = paloxes.get(0);
				final IAttributeStorage as1 = attributeStorageFactory.getAttributeStorage(paloxe1);
				Assert.assertTrue("precondition: CountryMadeIn.UseInASI", as1.getAttributeValue(helper.attr_CountryMadeIn).isUseInASI()); // else we will get different aggregates
				as1.setValue(helper.attr_CountryMadeIn, "DE");
				as1.setValue(helper.attr_FragileSticker, "Y");
				as1.setValue(helper.attr_QualityDiscountPercent, "5");
				as1.setValue(helper.attr_QualityNotice, HUTestHelper.QUALITYNOTICE_Test1);
				as1.saveChangesIfNeeded();
			}
			{
				final I_M_HU paloxe2 = paloxes.get(1);
				final IAttributeStorage as2 = attributeStorageFactory.getAttributeStorage(paloxe2);
				Assert.assertTrue("precondition: CountryMadeIn.UseInASI", as2.getAttributeValue(helper.attr_CountryMadeIn).isUseInASI()); // else we will get different aggregates
				as2.setValue(helper.attr_CountryMadeIn, "RO");
				as2.setValue(helper.attr_FragileSticker, "N");
				as2.setValue(helper.attr_QualityDiscountPercent, "10");
				as2.setValue(helper.attr_QualityNotice, HUTestHelper.QUALITYNOTICE_Test2);
				as2.saveChangesIfNeeded();
			}

			helper.commitThreadInheritedTrx(huContext);
		}

		//
		// Generate Receipt
		final I_M_InOut receipt = generateReceiptFromReceiptSchedule(paloxes);

		//
		// Validate generated receipt
		// @formatter:off
		InOutLineExpectations.newExpectations()
				.newInOutLineExpectation()// line 1: Quality issue: 95% OK
					.qtyEnteredTU(1)
					.newHUAssignmentExpectation().hu(paloxes.get(0)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(0)).luHU(null).tuHU(paloxes.get(0)).endExpectation()
					.referencesPackagingMaterialLineIdx(5) // does reference the 6th iol
					//
					.packagingmaterialLine(false)  // is not a packaging material line
					.product(pTomato).movementQty("408.5000")
					.noASIDescription()
					.noQualityDiscountPercent().noQualityNote()
					.inDispute(false)
					.endExpectation()

				.newInOutLineExpectation()// line 2: Quality issue: 5% with issues
					.qtyEnteredTU(0) // TUs shall not be set for lines with issues
					.referencesPackagingMaterialLineIdx(0) // does *not* reference packaging iol, because it has no HUs
					//
					.packagingmaterialLine(false)  // is not a packaging material line
					.product(pTomato).movementQty("21.5000")
					.noASIDescription()
					.qualityDiscountPercent("5").qualityNote(HUTestHelper.QUALITYNOTICE_Test1)
					.inDispute(true)
					.endExpectation()

				.newInOutLineExpectation()// line 3: Quality issue: 90% OK
					.qtyEnteredTU(1)
					.newHUAssignmentExpectation().hu(paloxes.get(1)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(1)).luHU(null).tuHU(paloxes.get(1)).endExpectation()
					.referencesPackagingMaterialLineIdx(5) // does reference the 6th iol
					//
					.packagingmaterialLine(false)  // is not a packaging material line
					.product(pTomato).movementQty("387.0000")
					.noASIDescription()
					.noQualityDiscountPercent().noQualityNote()
					.inDispute(false)
					.endExpectation()

				.newInOutLineExpectation()// line 4: Quality issue: 10% with issues
					.qtyEnteredTU(0)
					.referencesPackagingMaterialLineIdx(0) // does *not* reference packaging iol, because it has no HUs
					//
					.packagingmaterialLine(false)  // is not a packaging material line
					.product(pTomato).movementQty("43.0000")
					.noASIDescription()
					.qualityDiscountPercent("10").qualityNote(HUTestHelper.QUALITYNOTICE_Test2)
					.inDispute(true)
					.endExpectation()

				.newInOutLineExpectation()// line 5: Rest of the quantity
					.qtyEnteredTU(10 -1 -1)
					.newHUAssignmentExpectation().hu(paloxes.get(2)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(2)).luHU(null).tuHU(paloxes.get(2)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(3)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(3)).luHU(null).tuHU(paloxes.get(3)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(4)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(4)).luHU(null).tuHU(paloxes.get(4)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(5)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(5)).luHU(null).tuHU(paloxes.get(5)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(6)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(6)).luHU(null).tuHU(paloxes.get(6)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(7)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(7)).luHU(null).tuHU(paloxes.get(7)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(8)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(8)).luHU(null).tuHU(paloxes.get(8)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(9)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(9)).luHU(null).tuHU(paloxes.get(9)).endExpectation()
					.referencesPackagingMaterialLineIdx(5) // does reference the 6th iol
					//
					.packagingmaterialLine(false)  // is not a packaging material line
					.product(pTomato)
					.movementQty(430 * 8)
					.noASIDescription()
					.noQualityDiscountPercent()
					.noQualityNote()
					.inDispute(false)
					.endExpectation()
					
				.newInOutLineExpectation() // line 6: packing materials line
					.referencesPackagingMaterialLineIdx(0) // is packaging material and does not reference another packing material line
					//
					.packagingmaterialLine(true)
					.product(pPaloxe).movementQty("10").noASIDescription()
					.noQualityDiscountPercent().noQualityNote()
					.endExpectation()
				//
				.assertExpected(receipt);
				//
		// @formatter:on
	}

	/**
	 * We're expecting no different lines because of ASIs, but different lines because of Quality Issues.<br>
	 * Using TU test case; also considering weight. Will also assert that the {@link HUReceiptScheduleWeightNetAdjuster} works.
	 */
	@Test
	public void testSameASIAndQualityIssues_TU()
	{
		final List<I_M_HU> paloxes = createStandardHUsAndAssignThemToReceiptSchedule();

		//
		// Setup paloxe attribute structure
		{
			{
				final I_M_HU paloxe1 = paloxes.get(0);
				final IAttributeStorage as1 = attributeStorageFactory.getAttributeStorage(paloxe1);
				as1.setValue(attr_WeightGross, "510");
				as1.setValue(attr_QualityDiscountPercent, "5");
				as1.saveChangesIfNeeded();
				HUWeightsExpectation.newExpectation()
						.gross("510").net("435").tare("75").tareAdjust("0")
						.assertExpected("precondition: AS for paloxe1", as1);
			}
			{
				final I_M_HU paloxe2 = paloxes.get(1);
				final IAttributeStorage as2 = attributeStorageFactory.getAttributeStorage(paloxe2);
				as2.setValue(attr_WeightGross, "500");
				as2.setValue(attr_QualityDiscountPercent, "10");
				as2.saveChangesIfNeeded();
				HUWeightsExpectation.newExpectation()
						.gross("500").net("425").tare("75").tareAdjust("0")
						.assertExpected("precondition: AS for paloxe2", as2);
			}
			// NOTE: paloxes from index 3 to 9 stay as they are
			// FIXME: workaround to make sure our changes are pushed back to database
			helper.commitThreadInheritedTrx(huContext);
		}

		//
		// Process receipt schedules and get the resulting Receipt document
		final I_M_InOut receipt = generateReceiptFromReceiptSchedule(paloxes);

		//
		// Validate generated receipt
		// @formatter:off
		InOutLineExpectations.newExpectations()
				.newInOutLineExpectation()// Quality issue: OK
					.qtyEnteredTU(10)
					.newHUAssignmentExpectation().hu(paloxes.get(0)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(0)).luHU(null).tuHU(paloxes.get(0)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(1)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(1)).luHU(null).tuHU(paloxes.get(1)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(2)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(2)).luHU(null).tuHU(paloxes.get(2)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(3)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(3)).luHU(null).tuHU(paloxes.get(3)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(4)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(4)).luHU(null).tuHU(paloxes.get(4)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(5)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(5)).luHU(null).tuHU(paloxes.get(5)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(6)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(6)).luHU(null).tuHU(paloxes.get(6)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(7)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(7)).luHU(null).tuHU(paloxes.get(7)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(8)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(8)).luHU(null).tuHU(paloxes.get(8)).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(9)).luHU(null).tuHU(null).endExpectation()
					.newHUAssignmentExpectation().hu(paloxes.get(9)).luHU(null).tuHU(paloxes.get(9)).endExpectation()
					//
					.product(pTomato)
					.movementQty("4235.75") // = 4450(total) - QtyWithIssues
					.noQualityDiscountPercent()
					.noASIDescription()
					.inDispute(false)
					.endExpectation()
				.newInOutLineExpectation()// Quality issue: NOK
					.qtyEnteredTU(0)
					.product(pTomato)
					.movementQty("64.25") // = 435*5% + 425*10%
					.qualityDiscountPercent("1.49") // = 64.25 / 4235.75 * 100 = 1.4941860465
					.noASIDescription()
					.inDispute(true)
					.endExpectation()
				.newInOutLineExpectation() // Packing materials line
					.qtyEnteredTU(0)
					.product(pPaloxe)
					.movementQty("10")
					.noQualityDiscountPercent()
					.noASIDescription()
					.inDispute(false)
					.endExpectation()
				//
				.assertExpected(receipt);
				//
		// @formatter:on
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/09670_Tageslot_Einlagerung_%28100236982974%29
	 */
	@Test
	public void test_HU_LotNumberDate_propagated()
	{
		final List<I_M_HU> paloxes = createStandardHUsAndAssignThemToReceiptSchedule();

		final Date lotNumberDate1 = TimeUtil.getDay(2016, 01, 22);
		final Date lotNumberDate2 = TimeUtil.getDay(2016, 01, 23);

		//
		// Set attributes:
		{
			for (int i = 0; i < 10; i++)
			{
				final I_M_HU paloxe = paloxes.get(i);
				final Date lotNumberDate = i <= 4 ? lotNumberDate1 : lotNumberDate2;
				final IAttributeStorage as = attributeStorageFactory.getAttributeStorage(paloxe);
				as.setValue(attr_LotNumberDate, lotNumberDate);
				as.saveChangesIfNeeded();
				HUAttributeExpectation.newExpectation()
						.attribute(attr_LotNumberDate).valueDate(lotNumberDate)
						.assertExpected("precondition: AS for paloxe", as);
			}
			// FIXME: workaround to make sure our changes are pushed back to database
			helper.commitThreadInheritedTrx(huContext);
		}

		//
		// Generate receipt
		final I_M_InOut receipt = generateReceiptFromReceiptSchedule(paloxes);

		//
		// Validate Receipt
		//@formatter:off
		InOutLineExpectations.newExpectations()
			.newInOutLineExpectation()
				.qtyEnteredTU(10 / 2)
				//
				.product(pTomato).movementQty(4300 / 2)
				.inDispute(false)
				.attribute(attr_LotNumberDate, lotNumberDate1)
				.endExpectation()
			.newInOutLineExpectation()
				.qtyEnteredTU(10 / 2)
				//
				.product(pTomato).movementQty(4300 / 2)
				.inDispute(false)
				.attribute(attr_LotNumberDate, lotNumberDate2)
				.endExpectation()
			.newInOutLineExpectation()
				.product(pPaloxe).movementQty(10)
				.inDispute(false)
				.endExpectation()
			.assertExpected(receipt); // lines count expected: 3
		//@formatter:on
	}

	/**
	 * i.e. Create 10 Paloxes and assign them to receipt schedule
	 *
	 * @return created HUs
	 */
	private List<I_M_HU> createStandardHUsAndAssignThemToReceiptSchedule()
	{
		final BigDecimal qtyOrdered = receiptSchedule.getQtyOrdered();
		Assert.assertThat("precondition: QtyOrdered", qtyOrdered, Matchers.comparesEqualTo(new BigDecimal("4300")));

		final List<I_M_HU> paloxes = createIncomingTradingUnits(
				materialItemTomato_430, // Paloxe x 430 kg
				materialItemProductTomato_430,
				qtyOrdered,
				weightGrossPaloxe // GrossWeight
		);
		final int paloxesCount = paloxes.size();
		Assert.assertEquals("Invalid amount of paloxes created", 10, paloxesCount);
		for (int i = 0; i < paloxesCount; i++)
		{
			final I_M_HU paloxe = paloxes.get(i);
			POJOWrapper.setInstanceName(paloxe, "Paloxe-" + (i + 1) + "/" + paloxesCount);
			POJOWrapper.save(paloxe);
		}

		initReceiptScheduleAllocations(paloxes);
		newTUWeightsExpectations()
				.setDefaultTUExpectation(standardPaloxeWeightExpectation)
				.assertExpected(paloxes);

		return paloxes;
	}

	private I_M_InOut generateReceiptFromReceiptSchedule(final Collection<I_M_HU> selectedHUsToReceive)
	{
		final Properties ctx = huContext.getCtx();
		final List<I_M_ReceiptSchedule> receiptSchedules = Collections.singletonList(receiptSchedule);
		final Set<I_M_HU> selectedHUsSet = new HashSet<I_M_HU>(selectedHUsToReceive);
		final InOutGenerateResult result = huReceiptScheduleBL.processReceiptSchedules(ctx,
				receiptSchedules,
				selectedHUsSet,
				true // storeReceipts
		);
		final I_M_InOut receipt = result.getInOuts().get(0);
		return receipt;
	}
}
