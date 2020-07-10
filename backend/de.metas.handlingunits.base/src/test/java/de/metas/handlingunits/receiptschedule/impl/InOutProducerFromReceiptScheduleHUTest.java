/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.receiptschedule.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.IProductAcctDAO;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.expectations.HUAttributeExpectation;
import de.metas.handlingunits.expectations.HUWeightsExpectation;
import de.metas.handlingunits.inout.impl.DistributeAndMoveReceiptCreator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters;
import de.metas.handlingunits.receiptschedule.IHUToReceiveValidator;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.product.IProductActivityProvider;
import de.metas.product.LotNumberQuarantineRepository;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test creation of material receipts ({@link I_M_InOut}s) from scheduled receipts ({@link I_M_ReceiptSchedule}s) and how line aggregations are made based on products, packing and ASIs.
 *
 * @author al
 */
public class InOutProducerFromReceiptScheduleHUTest extends AbstractRSAllocationWithWeightAttributeTest
{
	@Override
	protected void afterInitialize()
	{
		super.afterInitialize();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		Services.registerService(IProductActivityProvider.class, Services.get(IProductAcctDAO.class));

		final I_C_DocType docType = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_DocType.class);
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_MaterialReceipt);
		InterfaceWrapperHelper.save(docType);

		SpringContextHolder.registerJUnitBeans(IHUToReceiveValidator.class, ImmutableList.of());

		final LotNumberQuarantineRepository lotNumberQuarantineRepository = new LotNumberQuarantineRepository();
		SpringContextHolder.registerJUnitBean(new DistributeAndMoveReceiptCreator(lotNumberQuarantineRepository));
	}

	/**
	 * We're expecting just one receipt line per product and no quality issues
	 */
	@Test
	public void testNoASIChangeAndNoQualityIssues()
	{
		final List<I_M_HU> paloxes = createStandardHUsAndAssignThemToTheReceiptSchedule();

		//
		// Generate receipt
		final I_M_InOut receipt = generateReceiptFromReceiptSchedule(paloxes);

		final StockQtyAndUOMQty qtys_4300 = StockQtyAndUOMQtys.create(
				new BigDecimal("4300"), ProductId.ofRepoId(pTomato.getM_Product_ID()),
				new BigDecimal("4300"), UomId.ofRepoId(uomKg.getC_UOM_ID()));

		final StockQtyAndUOMQty qtys_10 = StockQtyAndUOMQtys.create(
				new BigDecimal("10"), ProductId.ofRepoId(pPaloxe.getM_Product_ID()),
				new BigDecimal("10"), UomId.ofRepoId(uomEach.getC_UOM_ID()));

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
				.stockQtyAndMaybeCatchQty(qtys_4300).noASIDescription()
				.inDispute(false)
				.endExpectation()
			.newInOutLineExpectation()
			.stockQtyAndMaybeCatchQty(qtys_10).noASIDescription()
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
		final List<I_M_HU> paloxes = createStandardHUsAndAssignThemToTheReceiptSchedule();
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

		final StockQtyAndUOMQty qtys_408_5 = StockQtyAndUOMQtys.create(
				new BigDecimal("408.5000"), ProductId.ofRepoId(pTomato.getM_Product_ID()),
				new BigDecimal("408.5000"), UomId.ofRepoId(uomKg.getC_UOM_ID()));

		final StockQtyAndUOMQty qtys_21_5 = StockQtyAndUOMQtys.create(
				new BigDecimal("21.5000"), ProductId.ofRepoId(pTomato.getM_Product_ID()),
				new BigDecimal("21.5000"), UomId.ofRepoId(uomKg.getC_UOM_ID()));

		final StockQtyAndUOMQty qtys_387 = StockQtyAndUOMQtys.create(
				new BigDecimal("387.0000"), ProductId.ofRepoId(pTomato.getM_Product_ID()),
				new BigDecimal("387.0000"), UomId.ofRepoId(uomKg.getC_UOM_ID()));

		final StockQtyAndUOMQty qtys_43 = StockQtyAndUOMQtys.create(
				new BigDecimal("43.0000"), ProductId.ofRepoId(pTomato.getM_Product_ID()),
				new BigDecimal("43.0000"), UomId.ofRepoId(uomKg.getC_UOM_ID()));

		final StockQtyAndUOMQty qtys_433_x_8 = StockQtyAndUOMQtys.create(
				new BigDecimal(430 * 8), ProductId.ofRepoId(pTomato.getM_Product_ID()),
				new BigDecimal(430 * 8), UomId.ofRepoId(uomKg.getC_UOM_ID()));

		final StockQtyAndUOMQty qtys_10 = StockQtyAndUOMQtys.create(
				new BigDecimal("10"), ProductId.ofRepoId(pPaloxe.getM_Product_ID()),
				new BigDecimal("10"), UomId.ofRepoId(uomEach.getC_UOM_ID()));

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
					.stockQtyAndMaybeCatchQty(qtys_408_5)
					.noASIDescription()
					.noQualityDiscountPercent().noQualityNote()
					.inDispute(false)
					.endExpectation()

				.newInOutLineExpectation()// line 2: Quality issue: 5% with issues
					.qtyEnteredTU(0) // TUs shall not be set for lines with issues
					.referencesPackagingMaterialLineIdx(0) // does *not* reference packaging iol, because it has no HUs
					//
					.packagingmaterialLine(false)  // is not a packaging material line
					.stockQtyAndMaybeCatchQty(qtys_21_5)
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
					.stockQtyAndMaybeCatchQty(qtys_387)
					.noASIDescription()
					.noQualityDiscountPercent().noQualityNote()
					.inDispute(false)
					.endExpectation()

				.newInOutLineExpectation()// line 4: Quality issue: 10% with issues
					.qtyEnteredTU(0)
					.referencesPackagingMaterialLineIdx(0) // does *not* reference packaging iol, because it has no HUs
					//
					.packagingmaterialLine(false)  // is not a packaging material line
					.stockQtyAndMaybeCatchQty(qtys_43)
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
					.stockQtyAndMaybeCatchQty(qtys_433_x_8)
					.noASIDescription()
					.noQualityDiscountPercent()
					.noQualityNote()
					.inDispute(false)
					.endExpectation()

				.newInOutLineExpectation() // line 6: packing materials line
					.referencesPackagingMaterialLineIdx(0) // is packaging material and does not reference another packing material line
					//
					.packagingmaterialLine(true)
					.stockQtyAndMaybeCatchQty(qtys_10).noASIDescription()
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
		final List<I_M_HU> paloxes = createStandardHUsAndAssignThemToTheReceiptSchedule();

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

		final StockQtyAndUOMQty qtys_4235_75 = StockQtyAndUOMQtys.create(
				new BigDecimal("4235.75") /* 4450(total) - QtyWithIssues */, ProductId.ofRepoId(pTomato.getM_Product_ID()),
				new BigDecimal("4235.75"), UomId.ofRepoId(uomKg.getC_UOM_ID()));

		final StockQtyAndUOMQty qtys_64_25 = StockQtyAndUOMQtys.create(
				new BigDecimal("64.25") /* 435*5% + 425*10% */, ProductId.ofRepoId(pTomato.getM_Product_ID()),
				new BigDecimal("64.25"), UomId.ofRepoId(uomKg.getC_UOM_ID()));

		final StockQtyAndUOMQty qtys_10 = StockQtyAndUOMQtys.create(
				new BigDecimal("10"), ProductId.ofRepoId(pPaloxe.getM_Product_ID()),
				new BigDecimal("10"), UomId.ofRepoId(uomEach.getC_UOM_ID()));

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
					.stockQtyAndMaybeCatchQty(qtys_4235_75)
					.noQualityDiscountPercent()
					.noASIDescription()
					.inDispute(false)
					.endExpectation()
				.newInOutLineExpectation()// Quality issue: NOK
					.qtyEnteredTU(0)
					.stockQtyAndMaybeCatchQty(qtys_64_25)
					.qualityDiscountPercent("1.49") // = 64.25 / 4235.75 * 100 = 1.4941860465
					.noASIDescription()
					.inDispute(true)
					.endExpectation()
				.newInOutLineExpectation() // Packing materials line
					.qtyEnteredTU(0)
					.stockQtyAndMaybeCatchQty(qtys_10)
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
		final List<I_M_HU> paloxes = createStandardHUsAndAssignThemToTheReceiptSchedule();

		final LocalDate lotNumberDate1 = LocalDate.of(2016, 01, 22);
		final LocalDate lotNumberDate2 = LocalDate.of(2016, 01, 23);

		//
		// Set attributes:
		{
			for (int i = 0; i < 10; i++)
			{
				final I_M_HU paloxe = paloxes.get(i);
				final LocalDate lotNumberDate = i <= 4 ? lotNumberDate1 : lotNumberDate2;
				final IAttributeStorage atributeStorage = attributeStorageFactory.getAttributeStorage(paloxe);
				atributeStorage.setValue(attr_LotNumberDate, lotNumberDate);
				atributeStorage.saveChangesIfNeeded();
				HUAttributeExpectation.newExpectation()
						.attribute(attr_LotNumberDate)
						.valueDate(lotNumberDate)
						.assertExpected("precondition: AS for paloxe", atributeStorage);
			}
			// FIXME: workaround to make sure our changes are pushed back to database
			helper.commitThreadInheritedTrx(huContext);
		}

		//
		// Generate receipt
		final I_M_InOut receipt = generateReceiptFromReceiptSchedule(paloxes);

		final StockQtyAndUOMQty qtys_2150 = StockQtyAndUOMQtys.create(
				new BigDecimal(4300 / 2), ProductId.ofRepoId(pTomato.getM_Product_ID()),
				new BigDecimal(4300 / 2), UomId.ofRepoId(uomKg.getC_UOM_ID()));

		final StockQtyAndUOMQty qtys_10 = StockQtyAndUOMQtys.create(
				new BigDecimal("10"), ProductId.ofRepoId(pPaloxe.getM_Product_ID()),
				new BigDecimal("10"), UomId.ofRepoId(uomEach.getC_UOM_ID()));

		//
		// Validate Receipt
		//@formatter:off
		InOutLineExpectations.newExpectations()
			.newInOutLineExpectation()
				.qtyEnteredTU(10 / 2)
				//
				.stockQtyAndMaybeCatchQty(qtys_2150)
				.inDispute(false)
				.attribute(attr_LotNumberDate, lotNumberDate1)
				.endExpectation()
			.newInOutLineExpectation()
				.qtyEnteredTU(10 / 2)
				//
				.stockQtyAndMaybeCatchQty(qtys_2150)
				.inDispute(false)
				.attribute(attr_LotNumberDate, lotNumberDate2)
				.endExpectation()
			.newInOutLineExpectation()
				.stockQtyAndMaybeCatchQty(qtys_10)
				.inDispute(false)
				.endExpectation()
			.assertExpected(receipt); // lines count expected: 3
		//@formatter:on
	}

	/**
	 * Create 10 Paloxes and assign them to "the" receipt schedule of this test's base class.
	 */
	private List<I_M_HU> createStandardHUsAndAssignThemToTheReceiptSchedule()
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
		final Set<HuId> selectedHuIds = selectedHUsToReceive.stream()
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final CreateReceiptsParameters parameters = CreateReceiptsParameters
				.builder()
				.ctx(ctx)
				.receiptSchedules(receiptSchedules)
				.selectedHuIds(selectedHuIds)
				.build();

		final InOutGenerateResult result = huReceiptScheduleBL.processReceiptSchedules(parameters);
		final I_M_InOut receipt = result.getInOuts().get(0);
		return receipt;
	}
}
