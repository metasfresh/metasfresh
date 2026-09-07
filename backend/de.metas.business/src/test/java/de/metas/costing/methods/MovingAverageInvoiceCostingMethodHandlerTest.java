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
import lombok.NonNull;
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
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
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

		final CostAmountDetailed split = MovingAverageInvoiceCostingMethodHandler.computeMatchInvSplit(
				invoicedAmt, amtDifference, receiptQty, currentCost);

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

		final CostAmountDetailed split = MovingAverageInvoiceCostingMethodHandler.computeMatchInvSplit(
				invoicedAmt, amtDifference, receiptQty, currentCost);

		assertThat(split.getMainAmt().toBigDecimal()).isEqualTo("50");
		assertThat(split.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("75");
		assertThat(split.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("-25");
	}
}
