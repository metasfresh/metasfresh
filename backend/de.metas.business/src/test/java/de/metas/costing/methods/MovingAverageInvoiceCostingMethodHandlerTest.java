package de.metas.costing.methods;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateRequest.CostDetailCreateRequestBuilder;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostSegment;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.money.CurrencyId;
import de.metas.order.costs.OrderCostService;
import de.metas.order.model.I_M_Product_Category;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_AcctSchema_GL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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
public class MovingAverageInvoiceCostingMethodHandlerTest
{
	private CostElementRepository costElementRepo;
	private CurrentCostsRepository currentCostsRepo;
	private MovingAverageInvoiceCostingMethodHandler handler;

	private OrgId orgId1;
	private CurrencyId euroCurrencyId;
	private I_C_UOM eachUOM;

	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);
	private CostElement costElement;
	private AcctSchemaId acctSchemaId;
	private ProductId productId;

	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId1 = AdempiereTestHelper.createOrgWithTimeZone(ZONE_ID);

		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);

		costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		final CostDetailRepository costDetailsRepo = new CostDetailRepository();
		final CostDetailService costDetailsService = new CostDetailService(costDetailsRepo, costElementRepo);
		final CostingMethodHandlerUtils handlerUtils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				currentCostsRepo,
				costDetailsService);

		handler = new MovingAverageInvoiceCostingMethodHandler(
				handlerUtils,
				MatchInvoiceService.newInstanceForUnitTesting(),
				OrderCostService.newInstanceForUnitTesting());

		euroCurrencyId = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();
		eachUOM = BusinessTestHelper.createUomEach();

		costElement = createMovingAverageInvoiceCostElement();
		acctSchemaId = createAcctSchema();

		productId = createProduct();
	}

	CostAmountAndQtyDetailed mainAmtAndQty(String amt, String qty)
	{
		return CostAmountAndQtyDetailed.of(CostAmount.of(new BigDecimal(amt), euroCurrencyId), Quantity.of(qty, eachUOM), CostAmountType.MAIN);
	}

	private CostElement createMovingAverageInvoiceCostElement()
	{
		final I_M_CostElement record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_CostElement.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(CostingMethod.MovingAverageInvoice.name());
		record.setCostElementType(CostElementType.Material.getCode());
		record.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());
		record.setIsCalculated(false);
		InterfaceWrapperHelper.saveRecord(record);

		final CostElementId costElementId = CostElementId.ofRepoId(record.getM_CostElement_ID());
		return costElementRepo.getById(costElementId);
	}

	private AcctSchemaId createAcctSchema()
	{
		final I_C_AcctSchema acctSchemaRecord = newInstance(I_C_AcctSchema.class);
		acctSchemaRecord.setName("Test AcctSchema");
		acctSchemaRecord.setC_Currency_ID(euroCurrencyId.getRepoId());
		acctSchemaRecord.setM_CostType_ID(costTypeId.getRepoId());
		acctSchemaRecord.setCostingLevel(CostingLevel.Client.getCode());
		acctSchemaRecord.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());
		acctSchemaRecord.setSeparator("-");
		acctSchemaRecord.setTaxCorrectionType(TaxCorrectionType.NONE.getCode());
		saveRecord(acctSchemaRecord);

		final I_C_AcctSchema_GL acctSchemaGL = newInstance(I_C_AcctSchema_GL.class);
		acctSchemaGL.setC_AcctSchema_ID(acctSchemaRecord.getC_AcctSchema_ID());
		acctSchemaGL.setIntercompanyDueFrom_Acct(1);
		acctSchemaGL.setIntercompanyDueTo_Acct(1);
		acctSchemaGL.setIncomeSummary_Acct(1);
		acctSchemaGL.setRetainedEarning_Acct(1);
		acctSchemaGL.setPPVOffset_Acct(1);
		acctSchemaGL.setCashRounding_Acct(1);
		saveRecord(acctSchemaGL);

		final I_C_AcctSchema_Default acctSchemaDefault = newInstance(I_C_AcctSchema_Default.class);
		acctSchemaDefault.setC_AcctSchema_ID(acctSchemaRecord.getC_AcctSchema_ID());
		acctSchemaDefault.setRealizedGain_Acct(1);
		acctSchemaDefault.setRealizedLoss_Acct(1);
		acctSchemaDefault.setUnrealizedGain_Acct(1);
		acctSchemaDefault.setUnrealizedLoss_Acct(1);
		saveRecord(acctSchemaDefault);

		return AcctSchemaId.ofRepoId(acctSchemaRecord.getC_AcctSchema_ID());
	}

	private ProductId createProduct()
	{
		final I_M_Product_Category productCategory = newInstanceOutOfTrx(I_M_Product_Category.class);
		saveRecord(productCategory);

		final I_M_Product_Category_Acct productCategoryAcct = newInstanceOutOfTrx(I_M_Product_Category_Acct.class);
		productCategoryAcct.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		productCategoryAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		saveRecord(productCategoryAcct);

		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		product.setValue("product");
		product.setName("product");
		product.setC_UOM_ID(eachUOM.getC_UOM_ID());
		product.setProductType(ProductType.Item.getCode());
		product.setIsStocked(true);
		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		saveRecord(product);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private static Instant instant(final String localDate)
	{
		return LocalDate.parse(localDate).atStartOfDay(ZONE_ID).toInstant();
	}

	private CostDetailCreateRequestBuilder costDetailCreateRequest()
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(ClientId.METASFRESH)
				.orgId(orgId1)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElement(costElement)
				.date(instant("2020-08-13"));
	}

	@NonNull
	private CurrentCost getCurrentCost(final OrgId orgId)
	{
		final CurrentCost currentCost = getCurrentCostOrNull(orgId);
		if (currentCost == null)
		{
			throw new AssertionError("No current costs found for " + orgId);
		}
		return currentCost;
	}

	private CurrentCost getCurrentCostOrNull(final OrgId orgId)
	{
		final CostSegment costSegment = costSegment(orgId);

		final ImmutableList<CurrentCost> currentCosts = currentCostsRepo.getByCostSegmentAndCostingMethod(costSegment, CostingMethod.MovingAverageInvoice);
		if (currentCosts.isEmpty())
		{
			return null;
		}
		else if (currentCosts.size() == 1)
		{
			return currentCosts.get(0);
		}
		else
		{
			throw new AdempiereException("Got multiple current costs for " + costSegment + ": " + currentCosts);
		}
	}

	private CostSegment costSegment(final OrgId orgId)
	{
		return CostSegment.builder()
				.costingLevel(CostingLevel.Client)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.clientId(ClientId.METASFRESH)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.build();
	}

	@Test
	public void inventoryWithNoQtyAndWithPrice_ExplicitCostPrice()
	{
		assertThat(getCurrentCostOrNull(orgId1)).isNull();

		// Initial inventory with Price=100 and Qty=0
		final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
						costDetailCreateRequest()
								.documentRef(CostingDocumentRef.ofInventoryLineId(1))
								.amt(CostAmount.of(0, euroCurrencyId))
								.explicitCostPrice(CostAmount.of(100, euroCurrencyId))
								.qty(Quantity.of(0, eachUOM))
								.build())
				.getSingleResult();

		assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0", "0"));

		final CurrentCost currentCost = getCurrentCost(orgId1);
		assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
		assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("100");
	}

	/**
	 * The customer costs certain products via year-end explicit-cost inventories WHILE there is
	 * stock on hand (currentQty > 0). MAI must adopt the explicit cost price even then — parity
	 * with {@link AveragePOCostingMethodHandler}.
	 */
	@Test
	public void initCostsAfterInitWithStock_ExplicitCost_WithQty()
	{
		assertThat(getCurrentCostOrNull(orgId1)).isNull();

		// Initial inventory with Price=10 and Qty=10
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(1))
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(10, euroCurrencyId))
									.qty(Quantity.of(10, eachUOM))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("100", "10"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}

		// Second explicit-cost inventory with Price=15 and Qty=10, WITH stock on hand
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(2))
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(15, euroCurrencyId))
									.qty(Quantity.of(10, eachUOM))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("150", "10"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("20");

			// The cost price is changed even if existing qty
			// because we decided that the responsibility of whom is setting the explicit cost price.
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15");
		}
	}

	/**
	 * Same as above but the second explicit-cost inventory carries no qty. Also adopts the explicit
	 * price with stock on hand.
	 */
	@Test
	public void initCostsAfterInitWithStock_ExplicitCost_NoQty()
	{
		assertThat(getCurrentCostOrNull(orgId1)).isNull();

		// Initial inventory with Price=10 and Qty=10
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(1))
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(10, euroCurrencyId))
									.qty(Quantity.of(10, eachUOM))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("100", "10"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}

		// Second explicit-cost inventory with Price=15 and Qty=0, WITH stock on hand
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(2))
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(15, euroCurrencyId))
									.qty(Quantity.of(0, eachUOM))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0", "0"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");

			// The cost price is changed even if existing qty
			// because we decided that the responsibility of whom is setting the explicit cost price.
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15");
		}
	}

	@Nested
	class ComputeMatchInvSplitRounded
	{
		/** Invoice 101.215 vs receipt 133.444 for a match fully in stock: both amounts are rounded to cents first, so the stock leg is -32.22 and no sold leg remains. */
		@Test
		void referenceCase_allInStock_legsOnCentGrid()
		{
			assertSplit("101.215", "133.444", "0.914", "2.413", "101.22", "-32.22", "0", true);
		}

		/** Mirror of the reference case: invoice above receipt, fully in stock; the stock leg is positive and no sold leg remains. */
		@Test
		void referenceCaseMirrored_invoiceAboveReceipt_allInStock()
		{
			assertSplit("133.444", "101.215", "0.914", "2.413", "133.44", "32.22", "0", true);
		}

		/** Two legs with a half-cent tie: the stock share 2585 x 2.309 / 9.4 = 634.975 rounds HALF_UP to 634.98, the sold share is 1950.02. */
		@Test
		void twoLegs_halfCentTie_roundsHalfUp()
		{
			assertSplit("2585.00", "0", "9.4", "2.309", "2585", "634.98", "1950.02", true);
		}

		/** Invoice below receipt and only part in stock: the stock share -2585 x 2.309 / 9.4 = -634.975 rounds away from zero to -634.98, the sold share is the rest and has the same sign. */
		@Test
		void twoLegs_negativeDifference_halfCentTie()
		{
			assertSplit("0", "2585.00", "9.4", "2.309", "0", "-634.98", "-1950.02", true);
		}

		/** Fully in stock, invoice 121.65 vs receipt 119.775 (119.78 rounded): stock leg 1.87. */
		@Test
		void allInStock_smallDifferenceA()
		{
			assertSplit("121.65", "119.775", "1.5", "4.0257", "121.65", "1.87", "0", true);
		}

		/** Fully in stock, invoice 39.45 vs receipt 35.805 (35.81 rounded): stock leg 3.64. */
		@Test
		void allInStock_smallDifferenceB()
		{
			assertSplit("39.45", "35.805", "1.5", "17.3634", "39.45", "3.64", "0", true);
		}

		/** Fully in stock, invoice 118.35 vs receipt 107.415 (107.42 rounded): stock leg 10.93. */
		@Test
		void allInStock_smallDifferenceC()
		{
			assertSplit("118.35", "107.415", "4.5", "17.3634", "118.35", "10.93", "0", true);
		}

		/** The stock share is computed from the booked (rounded) invoice amount 1601.00, not from 1601.004975. */
		@Test
		void twoLegs_stockShareOfBookedInvoiceAmount()
		{
			assertSplit("1601.004975", "0", "10", "6.937", "1601", "1110.61", "490.39", true);
		}

		/** Difference 66.67, qty 1000, 999.9 in stock: stock share 66.66, sold 0.01; the stock share never exceeds the difference. */
		@Test
		void noOvershoot_stockNeverExceedsDifference()
		{
			assertSplit("66.67", "0", "1000", "999.9", "66.67", "66.66", "0.01", true);
		}

		/** Credit memo (negative matched qty): the invoiced -50.005 is rounded to -50.01 before the split; stock -75, sold 24.99. */
		@Test
		void creditMemo_halfCent_onlyRounded()
		{
			assertSplit("-50.005", "0", "-10", "-15", "-50.01", "-75", "24.99", false);
		}

		/** Negative on-hand counts as nothing in stock: the whole rounded difference is the sold share. */
		@Test
		void negativeOnHand_centGrid()
		{
			assertSplit("50.005", "0", "10", "-4", "50.01", "0", "50.01", true);
		}

		/** Invoice 100.004 and receipt 100.00 are the same amount in cents: no legs. */
		@Test
		void differenceRoundsToZero()
		{
			assertSplit("100.004", "100.00", "5", "10", "100", "0", "0", true);
		}

		/** Zero matched qty: no stock share, the whole rounded difference is the sold share. */
		@Test
		void zeroMatchedQty()
		{
			assertSplit("10.005", "0", "0", "5", "10.01", "0", "10.01", true);
		}

		/**
		 * Asserts main / stock / sold, that main - stock - sold equals the receipt amount rounded to cents,
		 * and (if requested) that stock and sold share the same sign or are zero.
		 */
		private void assertSplit(
				final String invoicedAmt, final String receiptAmt, final String receiptQty, final String onHandQty,
				final String expectedMain, final String expectedStock, final String expectedSold,
				final boolean isSameSignExpected)
		{
			final CostAmountDetailed split = MovingAverageInvoiceCostingMethodHandler.prepareMatchInvSplitRounded()
					.invoicedAmt(CostAmount.of(new BigDecimal(invoicedAmt), euroCurrencyId))
					.receiptAmt(CostAmount.of(new BigDecimal(receiptAmt), euroCurrencyId))
					.receiptQty(Quantity.of(new BigDecimal(receiptQty), eachUOM))
					.currentCost(currentCostWithQty(onHandQty))
					.amountPrecision(CurrencyPrecision.ofInt(2))
					.build();

			final BigDecimal main = split.getMainAmt().toBigDecimal();
			final BigDecimal stock = split.getCostAdjustmentAmt().toBigDecimal();
			final BigDecimal sold = split.getAlreadyShippedAmt().toBigDecimal();
			assertThat(main).isEqualByComparingTo(expectedMain);
			assertThat(stock).isEqualByComparingTo(expectedStock);
			assertThat(sold).isEqualByComparingTo(expectedSold);
			assertThat(main.subtract(stock).subtract(sold))
					.isEqualByComparingTo(new BigDecimal(receiptAmt).setScale(2, RoundingMode.HALF_UP));
			if (isSameSignExpected)
			{
				assertThat(stock.signum() * sold.signum()).isGreaterThanOrEqualTo(0);
			}
		}
	}

	private CurrentCost currentCostWithQty(final String currentQty)
	{
		return CurrentCost.builder()
				.costSegment(costSegment(orgId1))
				.costElement(costElement)
				.currencyId(euroCurrencyId)
				.precision(CurrencyPrecision.ofInt(2))
				.uom(eachUOM)
				.ownCostPrice(BigDecimal.ZERO)
				.currentQty(new BigDecimal(currentQty))
				.build();
	}

	/**
	 * Match-invoice price-difference split with a negative on-hand qty (over-issued stock). Negative on-hand cannot
	 * adjust the on-hand cost price, so the whole price difference is period cost (already-shipped/COGS).
	 */
	@Test
	public void matchInvSplit_negativeOnHand_adjustsNothing_spillsWholeDifferenceToAlreadyShipped()
	{
		final CostAmount invoicedAmt = CostAmount.of(50, euroCurrencyId);
		final CostAmount amtDifference = CostAmount.of(50, euroCurrencyId);
		final Quantity receiptQty = Quantity.of(10, eachUOM);
		final CurrentCost currentCost = currentCostWithQty("-4");

		final CostAmountDetailed split = MovingAverageInvoiceCostingMethodHandler.prepareMatchInvSplit()
				.invoicedAmt(invoicedAmt)
				.amtDifference(amtDifference)
				.receiptQty(receiptQty)
				.currentCost(currentCost)
				.amountPrecision(CurrencyPrecision.ofInt(2))
				.build();

		assertThat(split.getMainAmt().toBigDecimal()).isEqualTo("50");
		assertThat(split.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("0");
		assertThat(split.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("50");
	}

	/**
	 * A credit-memo / material-return match carries a NEGATIVE matched qty. The on-hand clamp must NOT touch that
	 * flow: the split stays exactly what the un-clamped {@code getCurrentQty().min(receiptQty)} produced, so the
	 * clamp is a no-op for a negative matched qty. Here on-hand (-15) is more negative than the matched qty (-10):
	 * unclamped qtyStillInStock = min(-15,-10) = -15, priceDifference = 50/-10 = -5, costAdjustment = -5*-15 = 75,
	 * alreadyShipped = 50-75 = -25. (Clamping on-hand to 0 would have wrongly collapsed this to 50/0.)
	 */
	@Test
	public void matchInvSplit_negativeReceiptQty_creditMemo_clampIsNoOp()
	{
		final CostAmount invoicedAmt = CostAmount.of(50, euroCurrencyId);
		final CostAmount amtDifference = CostAmount.of(50, euroCurrencyId);
		final Quantity receiptQty = Quantity.of(-10, eachUOM);
		final CurrentCost currentCost = currentCostWithQty("-15");

		final CostAmountDetailed split = MovingAverageInvoiceCostingMethodHandler.prepareMatchInvSplit()
				.invoicedAmt(invoicedAmt)
				.amtDifference(amtDifference)
				.receiptQty(receiptQty)
				.currentCost(currentCost)
				.amountPrecision(CurrencyPrecision.ofInt(2))
				.build();

		assertThat(split.getMainAmt().toBigDecimal()).isEqualTo("50");
		assertThat(split.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("75");
		assertThat(split.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("-25");
	}

	// ---- M_CostDetail.Prev_* must hold the cost state BEFORE the inbound movement ----

	private void seedStock10At5()
	{
		handler.createOrUpdateCost(
				costDetailCreateRequest()
						.documentRef(CostingDocumentRef.ofInventoryLineId(1))
						.amt(CostAmount.of(0, euroCurrencyId))
						.explicitCostPrice(CostAmount.of(5, euroCurrencyId))
						.qty(Quantity.of(10, eachUOM))
						.build());
	}

	private int createInOutLineId()
	{
		final I_M_InOutLine line = newInstanceOutOfTrx(I_M_InOutLine.class);
		saveRecord(line);
		return line.getM_InOutLine_ID();
	}

	private I_M_CostDetail retrieveCostDetailByInOutLine(final int inoutLineId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_CostDetail.class)
				.addEqualsFilter(I_M_CostDetail.COLUMN_M_InOutLine_ID, inoutLineId)
				.create()
				.firstOnlyNotNull(I_M_CostDetail.class);
	}

	private static void assertPrev(final I_M_CostDetail cd, final String price, final String qty, final String cumAmt, final String cumQty)
	{
		assertThat(cd.getPrev_CurrentCostPrice()).isEqualByComparingTo(price);
		assertThat(cd.getPrev_CurrentQty()).isEqualByComparingTo(qty);
		assertThat(cd.getPrev_CumulatedAmt()).isEqualByComparingTo(cumAmt);
		assertThat(cd.getPrev_CumulatedQty()).isEqualByComparingTo(cumQty);
	}

	@Test
	public void materialReceipt_prevAmountsAreStateBeforeReceipt()
	{
		seedStock10At5();
		final int lineId = createInOutLineId();

		// receipt without order line => valued at current cost price 5 => 10 @ 5 = 50
		handler.createOrUpdateCost(
				costDetailCreateRequest()
						.documentRef(CostingDocumentRef.ofReceiptLineId(lineId))
						.amt(CostAmount.of(0, euroCurrencyId))
						.qty(Quantity.of(10, eachUOM))
						.build());

		assertThat(getCurrentCost(orgId1).getCurrentQty().toBigDecimal()).isEqualByComparingTo("20");
		assertPrev(retrieveCostDetailByInOutLine(lineId), "5", "10", "50", "10");
	}

	@Test
	public void materialReceiptReversal_prevAmountsAreStateBeforeReversal()
	{
		seedStock10At5();
		final int originalLineId = createInOutLineId();
		final int reversalLineId = createInOutLineId();

		handler.createOrUpdateCost(
				costDetailCreateRequest()
						.documentRef(CostingDocumentRef.ofReceiptLineId(reversalLineId))
						.initialDocumentRef(CostingDocumentRef.ofReceiptLineId(originalLineId))
						.amt(CostAmount.of(0, euroCurrencyId))
						.qty(Quantity.of(-4, eachUOM))
						.build());

		assertThat(getCurrentCost(orgId1).getCurrentQty().toBigDecimal()).isEqualByComparingTo("6");
		assertPrev(retrieveCostDetailByInOutLine(reversalLineId), "5", "10", "50", "10");
	}

	@Test
	public void customerReturn_inboundShipmentLine_prevAmountsAreStateBeforeReturn()
	{
		seedStock10At5();
		final int lineId = createInOutLineId();

		handler.createOrUpdateCost(
				costDetailCreateRequest()
						.documentRef(CostingDocumentRef.ofShipmentLineId(lineId))
						.amt(CostAmount.of(0, euroCurrencyId))
						.qty(Quantity.of(4, eachUOM))
						.build());

		assertThat(getCurrentCost(orgId1).getCurrentQty().toBigDecimal()).isEqualByComparingTo("14");
		assertPrev(retrieveCostDetailByInOutLine(lineId), "5", "10", "50", "10");
	}

	@Test
	public void receiptNonMaterialCosts_prevAmountsAreStateBeforeAddOn()
	{
		final CostElement addOnElement = createCostElement(CostElementType.Overhead);
		final int line1 = createInOutLineId();
		final int line2 = createInOutLineId();

		handler.createOrUpdateCost(
				costDetailCreateRequest()
						.costElement(addOnElement)
						.documentRef(CostingDocumentRef.ofReceiptLineId(line1))
						.amt(CostAmount.of(20, euroCurrencyId))
						.qty(Quantity.of(10, eachUOM))
						.build());
		handler.createOrUpdateCost(
				costDetailCreateRequest()
						.costElement(addOnElement)
						.documentRef(CostingDocumentRef.ofReceiptLineId(line2))
						.amt(CostAmount.of(30, euroCurrencyId))
						.qty(Quantity.of(10, eachUOM))
						.build());

		// first add-on: nothing before
		assertPrev(retrieveCostDetailByInOutLine(line1), "0", "0", "0", "0");
		// second add-on: state after the first (10 @ 2, cumulated 20)
		assertPrev(retrieveCostDetailByInOutLine(line2), "2", "10", "20", "10");
	}

	private CostElement createCostElement(final CostElementType type)
	{
		final I_M_CostElement record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_CostElement.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(type.name());
		record.setCostElementType(type.getCode());
		record.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());
		record.setIsCalculated(false);
		InterfaceWrapperHelper.saveRecord(record);
		return costElementRepo.getById(CostElementId.ofRepoId(record.getM_CostElement_ID()));
	}
}
