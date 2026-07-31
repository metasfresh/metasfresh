package de.metas.costing.impl;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.methods.CostAmountType;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.order.model.I_M_Product_Category;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.NonNull;
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
import java.time.ZoneId;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Covers {@code CostingService#getCostAsOf(CostSegmentAndElement, Instant)} — a point-in-time read of a cost element's
 * cost, reconstructed from the {@code Prev_*} chain of {@code M_CostDetail} rather than from the live {@code M_Cost} row.
 * <p>
 * The live {@code M_Cost} row only ever carries the LATEST state. For a cut-off that lies in the past (a cost-method
 * switch back-dated to a closed year-end), reading it yields the cost AFTER every movement booked since — not the cost
 * AT the cut-off. The first changing-costs detail dated strictly after the cut-off carries, in its {@code Prev_*}
 * columns, exactly the state the cost element was in immediately before that movement — i.e. the state at the cut-off.
 * <p>
 * The boundary is deliberately strict ({@code >}, not {@code >=}): a detail dated exactly ON the cut-off is a
 * pre-cut-off event and must not be read as a forward one.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class CostingServiceAsOfTest
{
	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");
	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);

	private static final Instant CUTOFF = Instant.parse("2025-12-31T00:00:00Z");
	private static final Instant BEFORE_CUTOFF = Instant.parse("2025-06-01T00:00:00Z");
	private static final Instant AFTER_CUTOFF = Instant.parse("2026-03-01T00:00:00Z");

	private CostElementRepository costElementRepo;
	private CurrentCostsRepository currentCostsRepo;
	private CostDetailRepository costDetailsRepo;
	private CostingService costingService;

	private CurrencyId euroCurrencyId;
	private I_C_UOM eachUOM;
	private AcctSchemaId acctSchemaId;
	private CostElementId costElementId;
	private ProductId productId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		AdempiereTestHelper.createOrgWithTimeZone(ZONE_ID);

		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);

		costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		costDetailsRepo = new CostDetailRepository();

		final CostDetailService costDetailsService = new CostDetailService(costDetailsRepo, costElementRepo);
		final CostingMethodHandlerUtils handlerUtils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				currentCostsRepo,
				costDetailsService);
		costingService = new CostingService(
				handlerUtils,
				costDetailsService,
				costElementRepo,
				currentCostsRepo,
				ImmutableList.of());

		euroCurrencyId = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();
		eachUOM = BusinessTestHelper.createUomEach();

		acctSchemaId = createAcctSchema();
		costElementId = createCostElement("SourceElement", CostingMethod.AveragePO);
		productId = createProduct("product");
	}

	private CostElementId createCostElement(@NonNull final String name, @NonNull final CostingMethod costingMethod)
	{
		final I_M_CostElement record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_CostElement.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(name);
		record.setCostElementType(CostElementType.Material.getCode());
		record.setCostingMethod(costingMethod.getCode());
		record.setIsCalculated(false);
		InterfaceWrapperHelper.saveRecord(record);

		return CostElementId.ofRepoId(record.getM_CostElement_ID());
	}

	private AcctSchemaId createAcctSchema()
	{
		final I_C_AcctSchema acctSchemaRecord = newInstance(I_C_AcctSchema.class);
		acctSchemaRecord.setName("Test AcctSchema");
		acctSchemaRecord.setC_Currency_ID(euroCurrencyId.getRepoId());
		acctSchemaRecord.setM_CostType_ID(costTypeId.getRepoId());
		acctSchemaRecord.setCostingLevel(CostingLevel.Client.getCode());
		acctSchemaRecord.setCostingMethod(CostingMethod.AveragePO.getCode());
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

	private ProductId createProduct(@NonNull final String value)
	{
		final I_M_Product_Category productCategory = newInstanceOutOfTrx(I_M_Product_Category.class);
		saveRecord(productCategory);

		final I_M_Product_Category_Acct productCategoryAcct = newInstanceOutOfTrx(I_M_Product_Category_Acct.class);
		productCategoryAcct.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		productCategoryAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		saveRecord(productCategoryAcct);

		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		product.setValue(value);
		product.setName(value);
		product.setC_UOM_ID(eachUOM.getC_UOM_ID());
		product.setProductType(ProductType.Item.getCode());
		product.setIsStocked(true);
		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		saveRecord(product);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private CostSegmentAndElement costSegmentAndElement()
	{
		return CostSegmentAndElement.builder()
				.costingLevel(CostingLevel.Client)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.ANY)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElementId(costElementId)
				.build();
	}

	/** Writes the LIVE {@code M_Cost} row — the latest state, which a naive read would return for any date. */
	private void seedLiveCurrentCost(
			@NonNull final String ownCostPrice,
			@NonNull final String qty)
	{
		final CostElement costElement = costElementRepo.getById(costElementId);

		final CurrentCost currentCost = CurrentCost.builder()
				.costSegment(costSegmentAndElement().toCostSegment())
				.costElement(costElement)
				.currencyId(euroCurrencyId)
				.precision(CurrencyPrecision.ofInt(2))
				.uom(eachUOM)
				.ownCostPrice(new BigDecimal(ownCostPrice))
				.componentsCostPrice(BigDecimal.ZERO)
				.currentQty(new BigDecimal(qty))
				.cumulatedAmt(new BigDecimal(ownCostPrice).multiply(new BigDecimal(qty)))
				.cumulatedQty(new BigDecimal(qty))
				.build();

		currentCostsRepo.save(currentCost);
	}

	/**
	 * Writes a changing-costs {@code M_CostDetail} whose {@code Prev_*} columns hold the given state — i.e. the state
	 * the cost element was in immediately BEFORE the movement this detail represents.
	 */
	private void createChangingCostsDetail(
			@NonNull final Instant dateAcct,
			@NonNull final String prevPrice,
			@NonNull final String prevQty,
			@NonNull final String prevCumulatedAmt,
			@NonNull final String prevCumulatedQty)
	{
		costDetailsRepo.create(CostDetail.builder()
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.ANY)
				.acctSchemaId(acctSchemaId)
				.costElementId(costElementId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.amtType(CostAmountType.MAIN)
				.amt(CostAmount.of("60.00", euroCurrencyId))
				.qty(Quantity.of("5", eachUOM))
				.changingCosts(true)
				.previousAmounts(CostDetailPreviousAmounts.builder()
						.costPrice(CostPrice.builder()
								.ownCostPrice(CostAmount.of(prevPrice, euroCurrencyId))
								.componentsCostPrice(CostAmount.zero(euroCurrencyId))
								.uomId(UomId.ofRepoId(eachUOM.getC_UOM_ID()))
								.build())
						.qty(Quantity.of(prevQty, eachUOM))
						.cumulatedAmt(CostAmount.of(prevCumulatedAmt, euroCurrencyId))
						.cumulatedQty(Quantity.of(prevCumulatedQty, eachUOM))
						.build())
				.documentRef(CostingDocumentRef.ofInventoryLineId(1))
				.dateAcct(dateAcct));
	}

	/**
	 * The whole point of the as-of read: the reconstruction from the first post-cut-off detail's {@code Prev_*} wins over
	 * the live {@code M_Cost} row, which by then already reflects the 2026 movement.
	 */
	@Test
	public void returnsPrevAmountsOfFirstDetailStrictlyAfterTheDate()
	{
		// The live row carries the LATER (post-cut-off) state.
		seedLiveCurrentCost("12", "80");

		// The 2026 movement, whose Prev_* preserve the state as of the cut-off.
		createChangingCostsDetail(AFTER_CUTOFF, "10", "100", "1000", "100");

		final Optional<CostDetailPreviousAmounts> costAsOf = costingService.getCostAsOf(costSegmentAndElement(), CUTOFF);

		assertThat(costAsOf).isPresent();
		final CostDetailPreviousAmounts amounts = costAsOf.get();
		assertThat(amounts.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("10");
		assertThat(amounts.getQty().toBigDecimal()).isEqualByComparingTo("100");
		assertThat(amounts.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("1000");
		assertThat(amounts.getCumulatedQty().toBigDecimal()).isEqualByComparingTo("100");
	}

	/**
	 * No movement after the cut-off means the live {@code M_Cost} row IS the state at the cut-off. Pins today's
	 * behaviour, which stays correct for a switch performed at the cut-off rather than back-dated.
	 */
	@Test
	public void fallsBackToLiveCurrentCostWhenNoDetailAfterTheDate()
	{
		seedLiveCurrentCost("12", "80");

		// The only detail is dated BEFORE the cut-off, so there is nothing to reconstruct from.
		createChangingCostsDetail(BEFORE_CUTOFF, "10", "100", "1000", "100");

		final Optional<CostDetailPreviousAmounts> costAsOf = costingService.getCostAsOf(costSegmentAndElement(), CUTOFF);

		assertThat(costAsOf).isPresent();
		final CostDetailPreviousAmounts amounts = costAsOf.get();
		assertThat(amounts.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("12");
		assertThat(amounts.getQty().toBigDecimal()).isEqualByComparingTo("80");
	}

	/**
	 * The strict {@code >} boundary. A detail dated exactly ON the cut-off — which is where the switch writes its own
	 * opening anchor — must not be read as a forward event; otherwise the switch would seed itself from its own
	 * anchor's {@code Prev_*} instead of from the source's cost at the cut-off.
	 */
	@Test
	public void treatsADetailOnTheDateItselfAsBeforeTheDate()
	{
		seedLiveCurrentCost("12", "80");

		// Dated exactly AT the cut-off: pre-cut-off, not post-cut-off.
		createChangingCostsDetail(CUTOFF, "10", "100", "1000", "100");

		final Optional<CostDetailPreviousAmounts> costAsOf = costingService.getCostAsOf(costSegmentAndElement(), CUTOFF);

		assertThat(costAsOf).isPresent();
		final CostDetailPreviousAmounts amounts = costAsOf.get();
		assertThat(amounts.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("12");
		assertThat(amounts.getQty().toBigDecimal()).isEqualByComparingTo("80");
	}
}
