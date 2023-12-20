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

package de.metas.costing.methods;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostAmountAndQty;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostSegment;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.costing.ShipmentCosts;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.money.CurrencyId;
import de.metas.money.MoneyService;
import de.metas.order.costs.OrderCostService;
import de.metas.order.model.I_M_Product_Category;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.quantity.Quantity;
import de.metas.shippingnotification.ShippingNotificationLineId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class MovingAverageInvoiceCostingMethodHandlerTest
{
	private MoneyService moneyService;
	private IUOMDAO uomDAO;

	private CostElementRepository costElementRepo;
	private CurrentCostsRepository currentCostsRepo;
	private MovingAverageInvoiceCostingMethodHandler handler;

	private OrgId orgId1;
	private CurrencyId euroCurrencyId;
	private I_C_UOM eachUOM;

	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);
	private CostElement costElement;
	private AcctSchemaId acctSchemaId;
	private AcctSchema acctSchema;
	private ProductId productId;

	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId1 = AdempiereTestHelper.createOrgWithTimeZone(ZONE_ID);

		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);

		final CurrencyRepository currenciesRepo = new CurrencyRepository();
		moneyService = new MoneyService(currenciesRepo);
		uomDAO = Services.get(IUOMDAO.class);
		costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		final CostDetailRepository costDetailsRepo = new CostDetailRepository();
		final CostDetailService costDetailsService = new CostDetailService(costDetailsRepo, costElementRepo);
		final CostingMethodHandlerUtils handlerUtils = new CostingMethodHandlerUtils(
				currenciesRepo,
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
		acctSchema = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);

		productId = createProduct();
	}

	CostAmountAndQtyDetailed mainAmtAndQty(String amt, String qty)
	{
		return CostAmountAndQtyDetailed.builder().main(amtAndQty(amt, qty)).build();
	}

	CostAmountAndQty amtAndQty(String amt, String qty)
	{
		return CostAmountAndQty.of(costAmount(amt), qty(qty));
	}

	CostAmount costAmount(@NonNull final String str)
	{
		final Splitter splitter = Splitter.on(" ").trimResults().omitEmptyStrings();
		try
		{
			final List<String> parts = splitter.splitToList(str);
			return CostAmount.of(parts.get(0), currencyId(parts.get(1)));
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed converting `" + str + "` to CostAmount", ex);
		}
	}

	private CurrencyId currencyId(final String currencyCodeStr)
	{
		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(currencyCodeStr);
		return moneyService.getCurrencyIdByCurrencyCode(currencyCode);
	}

	Quantity qty(@NonNull final String str)
	{
		final Splitter splitter = Splitter.on(" ").trimResults().omitEmptyStrings();
		try
		{
			final List<String> parts = splitter.splitToList(str);
			return Quantity.of(parts.get(0), uom(parts.get(1)));
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed converting `" + str + "` to Quantity", ex);
		}
	}

	I_C_UOM uom(@NonNull final String uomSymbol)
	{
		return uomDAO.getBySymbol(uomSymbol)
				.or(() -> uomDAO.getByX12DE355IfExists(X12DE355.ofCode(uomSymbol)))
				.orElseThrow(() -> new AdempiereException("No UOM found for `" + uomSymbol + "`"));
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

	private CostDetailCreateRequest.CostDetailCreateRequestBuilder costDetailCreateRequest()
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
	public void inventoryWithQtyAndPrice_NotExplicitCostPrice()
	{
		assertThat(getCurrentCostOrNull(orgId1)).isNull();

		// Initial inventory with Price=10 and Qty=0
		final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
						costDetailCreateRequest()
								.documentRef(CostingDocumentRef.ofInventoryLineId(1))
								.amt(costAmount("100 EUR"))
								.explicitCostPrice(null)
								.qty(qty("10 Ea"))
								.build())
				.getSingleResult();

		assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "10 Ea"));

		final CurrentCost currentCost = getCurrentCost(orgId1);
		assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
		assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("0");
	}

	@Test
	public void inventoryNoQtyAndWithPrice_NoExplicitCostPrice()
	{
		assertThat(getCurrentCostOrNull(orgId1)).isNull();

		// Initial inventory with Price=10 and Qty=0
		final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
						costDetailCreateRequest()
								.documentRef(CostingDocumentRef.ofInventoryLineId(1))
								.amt(costAmount("100 EUR"))
								.qty(qty("0 Ea"))
								.build())
				.getSingleResult();

		assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "0 Ea"));

		final CurrentCost currentCost = getCurrentCost(orgId1);
		assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
		assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("0");
	}

	@Test
	public void inventoryWithNoQtyAndWithPrice_ExplicitCostPrice()
	{
		assertThat(getCurrentCostOrNull(orgId1)).isNull();

		// Initial inventory with Price=10 and Qty=0
		final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
						costDetailCreateRequest()
								.documentRef(CostingDocumentRef.ofInventoryLineId(1))
								.amt(costAmount("0 EUR"))
								.explicitCostPrice(costAmount("100 EUR"))
								.qty(qty("0 Ea"))
								.build())
				.getSingleResult();

		assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "0 Ea"));

		final CurrentCost currentCost = getCurrentCost(orgId1);
		assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
		assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("100");
	}

	@Test
	public void initCostsTwice()
	{
		assertThat(getCurrentCostOrNull(orgId1)).isNull();

		// Initial inventory with Price=10 and Qty=0
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(1))
									.amt(costAmount("0 EUR"))
									.explicitCostPrice(costAmount("10 EUR"))
									.qty(qty("0 Ea"))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "0 Ea"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}

		// Initial inventory with Price=15 and Qty=0
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(2))
									.amt(costAmount("0 EUR"))
									.explicitCostPrice(costAmount("15 EUR"))
									.qty(qty("0 Ea"))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "0 Ea"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15");
		}
	}

	@Test
	public void initCostsAfterInitWithStock_ExplicitCost_NoQty()
	{
		assertThat(getCurrentCostOrNull(orgId1)).isNull();

		// Initial inventory with Price=10 and Qty=0
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(1))
									.amt(costAmount("0 EUR"))
									.explicitCostPrice(costAmount("10 EUR"))
									.qty(qty("10 Ea"))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("100 EUR", "10 Ea"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}

		// Initial inventory with Price=15 and Qty=0
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(2))
									.amt(costAmount("0 EUR"))
									.explicitCostPrice(costAmount("15 EUR"))
									.qty(qty("0 Ea"))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "0 Ea"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
			// The cost price was not changed because of the existing qty
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}
	}

	@Test
	public void initCostsAfterInitWithStock_ExplicitCost_WithQty()
	{
		assertThat(getCurrentCostOrNull(orgId1)).isNull();

		// Initial inventory with Price=10 and Qty=0
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(1))
									.amt(costAmount("0 EUR"))
									.explicitCostPrice(costAmount("10 EUR"))
									.qty(qty("10 Ea"))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("100 EUR", "10 Ea"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}

		// Initial inventory with Price=15 and Qty=10
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(2))
									.amt(costAmount("0 EUR"))
									.explicitCostPrice(costAmount("15 EUR"))
									.qty(qty("10 Ea"))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("150 EUR", "10 Ea"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("20");
			// The cost price was not changed because of the existing qty
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("12.5");
		}
	}

	@Test
	public void initCost_Then_Init_Qty()
	{
		assertThat(getCurrentCostOrNull(orgId1)).isNull();

		// Initial inventory with Price=10 and Qty=0
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(1))
									.amt(costAmount("0 EUR"))
									.explicitCostPrice(costAmount("10 EUR"))
									.qty(qty("0 Ea"))
									.build())
					.getSingleResult();

			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "0 Ea"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}

		// Initial inventory with Price=0 and Qty=10
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(2))
									.amt(costAmount("0 EUR"))
									.qty(qty("10 Ea"))
									.build())
					.getSingleResult();

			// The amount is the current explicit cost multiplied by the existing qty
			assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("100 EUR", "10 Ea"));

			final CurrentCost currentCost = getCurrentCost(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
			// The cost price was not changed because it was not explicit
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}
	}

	@Nested
	public class scenarios
	{
		@Test
		@DisplayName("case 1: cost init, shipment, receipt, shipment reversal")
		public void case1()
		{
			assertThat(getCurrentCostOrNull(orgId1)).isNull();

			// Initial inventory with Price=10 and Qty=0
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofInventoryLineId(1))
										.amt(costAmount("0 EUR"))
										.explicitCostPrice(costAmount("10 EUR"))
										.qty(qty("0 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "0 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
			}

			// Shipment
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(costAmount("0 EUR")) // to be calculated
										.qty(qty("-10 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("-100 EUR", "-10 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
			}

			// Receipt
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofMatchPOId(1))
										.amt(costAmount("150 EUR"))
										.qty(qty("10 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("150 EUR", "10 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15");
			}

			// Shipment reversal
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(2))
										.initialDocumentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(costAmount("100 EUR"))
										.qty(qty("10 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("100 EUR", "10 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("20");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("12.5"); // (10x15 + 10x10) / (10 + 10)
			}
		}

		@Test
		@DisplayName("case 2: cost init, shipment, shipment reversal, receipt")
		public void case2()
		{
			assertThat(getCurrentCostOrNull(orgId1)).isNull();

			// Initial inventory with Price=10 and Qty=0
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofInventoryLineId(1))
										.amt(costAmount("0 EUR"))
										.explicitCostPrice(costAmount("10 EUR"))
										.qty(qty("0 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "0 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
			}

			// Shipment
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(costAmount("0 EUR")) // to be calculated
										.qty(qty("-10 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("-100 EUR", "-10 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
			}

			// Shipment reversal
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(2))
										.initialDocumentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(costAmount("100 EUR"))
										.qty(qty("10 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("100 EUR", "10 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
			}

			// Receipt
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofMatchPOId(1))
										.amt(costAmount("150 EUR"))
										.qty(qty("10 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("150 EUR", "10 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("20");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("12.5"); // (10x15 + 10x10) / (10 + 10)
			}
		}

		@Test
		@DisplayName("case 3: shipment, receipt, shipment reversal")
		public void case3()
		{
			assertThat(getCurrentCostOrNull(orgId1)).isNull();

			// Shipment
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(costAmount("0 EUR")) // to be calculated
										.qty(qty("-100 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "-100 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("0");
			}

			// Receipt
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofMatchPOId(1))
										.amt(costAmount("150 EUR"))
										.qty(qty("10 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("150 EUR", "10 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15");
			}

			// Shipment reversal
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(2))
										.initialDocumentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(costAmount("0 EUR"))
										.qty(qty("100 Ea"))
										.build())
						.getSingleResult();

				assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("0 EUR", "100 Ea"));

				final CurrentCost currentCost = getCurrentCost(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("110");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("1.3636"); // (10x15 + 100x0) / (10 + 100) = 150 / 110 = 1.3636
			}
		}

		@Nested
		public class shipment_with_shipping_notification
		{
			CostAmountAndQty amtAndQtyNotified;

			void commonScenario()
			{
				assertThat(getCurrentCostOrNull(orgId1)).isNull();

				// Initial inventory with Price=10 and Qty=100
				{
					handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(1))
									.amt(costAmount("0 EUR")) // to be calculated based on explicit cost price
									.explicitCostPrice(costAmount("10 EUR"))
									.qty(qty("100 Ea"))
									.build());

					final CurrentCost currentCost = getCurrentCost(orgId1);
					assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("100");
					assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
				}

				// Shipment notification
				{
					final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
									costDetailCreateRequest()
											.documentRef(CostingDocumentRef.ofShippingNotificationLineId(ShippingNotificationLineId.ofRepoId(1)))
											.amt(costAmount("0 EUR")) // to be calculated
											.qty(qty("-20 Ea"))
											.build())
							.getSingleResult();

					assertThat(costDetailResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("-200 EUR", "-20 Ea"));
					amtAndQtyNotified = costDetailResult.getAmtAndQty(CostAmountType.MAIN).negate();

					final CurrentCost currentCost = getCurrentCost(orgId1);
					assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("80");
					assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
				}

				// Receipt
				{
					final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
									costDetailCreateRequest()
											.documentRef(CostingDocumentRef.ofMatchPOId(1))
											.amt(costAmount("1600 EUR"))
											.qty(qty("80 Ea"))
											.build())
							.getSingleResult();

					assertThat(costDetailResult.getAmtAndQty()).usingRecursiveComparison().isEqualTo(mainAmtAndQty("1600 EUR", "80 Ea"));

					final CurrentCost currentCost = getCurrentCost(orgId1);
					assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("160");
					assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15"); // (80x10 + 1600) / (80 + 80)
					assertThat(currentCost.getCumulatedAmt().toBigDecimal()).isEqualTo("2400"); // 160 x 15
				}
			}

			void print(final ShipmentCosts shipmentCosts)
			{
				System.out.println("shippedButNotNotified: " + shipmentCosts.getShippedButNotNotified());
				System.out.println("   shippedAndNotified: " + shipmentCosts.getShippedAndNotified());
				System.out.println("notifiedButNotShipped: " + shipmentCosts.getNotifiedButNotShipped());
			}

			void print(final CurrentCost currentCost)
			{
				System.out.println("          Current Qty: " + currentCost.getCurrentQty());
				System.out.println("    Current CostPrice: " + currentCost.getCostPrice());
				System.out.println("Current Cumulated Amt: " + currentCost.getCumulatedAmt());
			}

			@Test
			void shipped_less_than_notified()
			{
				commonScenario();

				// Shipment
				{
					final CostDetailCreateResultsList results = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofShipmentLineId(1))
									.amt(costAmount("0 EUR")) // to be calculated
									.qty(qty("-17 Ea"))
									.externallyOwned(amtAndQtyNotified)
									.build());

					final ShipmentCosts shipmentCosts = ShipmentCosts.extractAccountableFrom(results, acctSchema);
					print(shipmentCosts);
					assertThat(shipmentCosts).usingRecursiveComparison().isEqualTo(ShipmentCosts.builder()
							.shippedAndNotified(amtAndQty("170 EUR", "17 Ea"))
							.shippedButNotNotified(amtAndQty("0 EUR", "0 Ea"))
							.notifiedButNotShipped(amtAndQty("30 EUR", "3 Ea"))
							.build());

					final CurrentCost currentCost = getCurrentCost(orgId1);
					print(currentCost);
					assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("163"); // 100 - 20 + 80 + 3
					assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("14.908"); // (160 x 15 + 30) / (160 + 3)
					assertThat(currentCost.getCumulatedAmt().toBigDecimal()).isEqualTo("2430");
				}
			}

			@Test
			void shipped_as_notified()
			{
				commonScenario();

				final CurrentCost currentCosts_beforeShipment = getCurrentCost(orgId1);

				// Shipment
				{
					final CostDetailCreateResultsList results = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofShipmentLineId(1))
									.amt(costAmount("0 EUR")) // to be calculated
									.qty(qty("-20 Ea"))
									.externallyOwned(amtAndQtyNotified)
									.build());

					final ShipmentCosts shipmentCosts = ShipmentCosts.extractAccountableFrom(results, acctSchema);
					print(shipmentCosts);
					assertThat(shipmentCosts).usingRecursiveComparison().isEqualTo(ShipmentCosts.builder()
							.shippedAndNotified(amtAndQty("200 EUR", "20 Ea"))
							.build());

					final CurrentCost currentCost = getCurrentCost(orgId1);
					print(currentCost);
					assertThat(currentCost).usingRecursiveComparison().isEqualTo(currentCosts_beforeShipment);
				}
			}

			@Test
			void shipped_more_than_notified()
			{
				commonScenario();

				// Shipment
				{
					final CostDetailCreateResultsList results = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofShipmentLineId(1))
									.amt(costAmount("0 EUR")) // to be calculated
									.qty(qty("-30 Ea"))
									.externallyOwned(amtAndQtyNotified)
									.build());

					final ShipmentCosts shipmentCosts = ShipmentCosts.extractAccountableFrom(results, acctSchema);
					print(shipmentCosts);
					assertThat(shipmentCosts).usingRecursiveComparison().isEqualTo(ShipmentCosts.builder()
							.shippedAndNotified(amtAndQty("200 EUR", "20 Ea"))
							.shippedButNotNotified(amtAndQty("150 EUR", "10 Ea"))
							.build());

					final CurrentCost currentCost = getCurrentCost(orgId1);
					print(currentCost);
					assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("150"); // 100 - 20 + 80 - 10
					assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15");
					assertThat(currentCost.getCumulatedAmt().toBigDecimal()).isEqualTo("2250"); // 150 x 15
				}
			}
		}
	}

	@Nested
	public class createMovementCosts
	{
		@Nested
		public class sameSegment
		{
			@Test
			public void noInitialCostPrice_noInitialQty()
			{
				assertThat(getCurrentCostOrNull(orgId1)).isNull();

				final MoveCostsResult result = handler.createMovementCosts(MoveCostsRequest.builder()
						.acctSchemaId(acctSchemaId)
						.clientId(ClientId.METASFRESH)
						.costElement(costElement)
						.date(instant("2020-08-14"))
						.productId(productId)
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.qtyToMove(qty("100 Ea"))
						//
						.outboundOrgId(orgId1)
						.outboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
						//
						.inboundOrgId(orgId1)
						.inboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
						//
						.build());

				assertThat(result.getOutboundAmountToPost(acctSchema).toBigDecimal()).isEqualTo("0");
				assertThat(result.getInboundAmountToPost(acctSchema).toBigDecimal()).isEqualTo("0");
			}

			@Test
			public void withInitialCostPrice_noInitialQty()
			{
				assertThat(getCurrentCostOrNull(orgId1)).isNull();

				// Initial inventory with Price=10 and Qty=0
				handler.createOrUpdateCost(
						costDetailCreateRequest()
								.documentRef(CostingDocumentRef.ofInventoryLineId(1))
								.amt(costAmount("13 EUR"))
								.qty(qty("0 Ea"))
								.build());

				final MoveCostsResult result = handler.createMovementCosts(MoveCostsRequest.builder()
						.acctSchemaId(acctSchemaId)
						.clientId(ClientId.METASFRESH)
						.costElement(costElement)
						.date(instant("2020-08-14"))
						.productId(productId)
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.qtyToMove(qty("100 Ea"))
						//
						.outboundOrgId(orgId1)
						.outboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
						//
						.inboundOrgId(orgId1)
						.inboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
						//
						.build());

				assertThat(result.getOutboundAmountToPost(acctSchema).toBigDecimal()).isEqualTo("0");
				assertThat(result.getInboundAmountToPost(acctSchema).toBigDecimal()).isEqualTo("0");
			}

			@Test
			public void withInitialCostPrice_noInitialQty_ExplicitCostPrice()
			{
				assertThat(getCurrentCostOrNull(orgId1)).isNull();

				// Initial inventory with Price=10 and Qty=0
				handler.createOrUpdateCost(
						costDetailCreateRequest()
								.documentRef(CostingDocumentRef.ofInventoryLineId(1))
								.amt(costAmount("0 EUR"))
								.explicitCostPrice(costAmount("13 EUR"))
								.qty(qty("0 Ea"))
								.build());

				final MoveCostsResult result = handler.createMovementCosts(MoveCostsRequest.builder()
						.acctSchemaId(acctSchemaId)
						.clientId(ClientId.METASFRESH)
						.costElement(costElement)
						.date(instant("2020-08-14"))
						.productId(productId)
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.qtyToMove(qty("100 Ea"))
						//
						.outboundOrgId(orgId1)
						.outboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
						//
						.inboundOrgId(orgId1)
						.inboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
						//
						.build());

				assertThat(result.getOutboundAmountToPost(acctSchema).toBigDecimal()).isEqualTo("-1300");
				assertThat(result.getInboundAmountToPost(acctSchema).toBigDecimal()).isEqualTo("+1300");
			}

		}

	}

}