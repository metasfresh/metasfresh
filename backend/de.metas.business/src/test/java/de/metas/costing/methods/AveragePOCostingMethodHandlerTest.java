package de.metas.costing.methods;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.TaxCorrectionType;
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
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.order.model.I_M_Product_Category;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
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

import java.time.LocalDate;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.business
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

@ExtendWith(AdempiereTestWatcher.class)
public class AveragePOCostingMethodHandlerTest
{
	private CostElementRepository costElementRepo;
	private CurrentCostsRepository currentCostsRepo;
	private AveragePOCostingMethodHandler handler;

	private OrgId orgId1;
	private CurrencyId euroCurrencyId;
	private I_C_UOM eachUOM;

	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);
	private CostElement costElement;
	private AcctSchemaId acctSchemaId;
	private AcctSchema acctSchema;
	private ProductId productId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId1 = BusinessTestHelper.createOrgWithTimeZone();
		// orgId2 = BusinessTestHelper.createOrgWithTimeZone();

		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);

		costElementRepo = new CostElementRepository();
		currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		final CostDetailRepository costDetailsRepo = new CostDetailRepository();
		final CostDetailService costDetailsService = new CostDetailService(costDetailsRepo, costElementRepo);
		final CostingMethodHandlerUtils handlerUtils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				currentCostsRepo,
				costDetailsService);

		handler = new AveragePOCostingMethodHandler(handlerUtils);

		euroCurrencyId = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();
		eachUOM = BusinessTestHelper.createUomEach();

		costElement = createAveragePOCostElement();
		acctSchemaId = createAcctSchema();
		acctSchema = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);

		productId = createProduct();
	}

	private CostElement createAveragePOCostElement()
	{
		final I_M_CostElement record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_CostElement.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(CostingMethod.AveragePO.name());
		record.setCostElementType(CostElementType.Material.getCode());
		record.setCostingMethod(CostingMethod.AveragePO.getCode());
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
		saveRecord(acctSchemaGL);

		final I_C_AcctSchema_Default acctSchemaDefault = newInstance(I_C_AcctSchema_Default.class);
		acctSchemaDefault.setC_AcctSchema_ID(acctSchemaRecord.getC_AcctSchema_ID());
		acctSchemaDefault.setRealizedGain_Acct(1);
		acctSchemaDefault.setRealizedLoss_Acct(1);
		acctSchemaDefault.setUnrealizedGain_Acct(1);
		acctSchemaDefault.setUnrealizedLoss_Acct(1);
		saveRecord(acctSchemaDefault);

		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(acctSchemaRecord.getC_AcctSchema_ID());
		return acctSchemaId;
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

	private CostDetailCreateRequestBuilder costDetailCreateRequest()
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.ofRepoId(1))
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElement(costElement)
				.date(LocalDate.parse("2020-08-13"));
	}

	private CurrentCost getCurrentCostOrNull(final OrgId orgId)
	{
		final CostSegment costSegment = costSegment(orgId);

		final ImmutableList<CurrentCost> currentCosts = currentCostsRepo.getByCostSegmentAndCostingMethod(costSegment, CostingMethod.AveragePO);
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
								.amt(CostAmount.of(100, euroCurrencyId))
								.explicitCostPrice(null)
								.qty(Quantity.of(10, eachUOM))
								.build())
				.get();

		assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
		assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("10");

		final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
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
								.amt(CostAmount.of(100, euroCurrencyId))
								.qty(Quantity.of(0, eachUOM))
								.build())
				.get();

		assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
		assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("0");

		final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
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
								.amt(CostAmount.of(0, euroCurrencyId))
								.explicitCostPrice(CostAmount.of(100, euroCurrencyId))
								.qty(Quantity.of(0, eachUOM))
								.build())
				.get();

		assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
		assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("0");

		final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
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
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(10, euroCurrencyId))
									.qty(Quantity.of(0, eachUOM))
									.build())
					.get();

			assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
			assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("0");

			final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}

		// Initial inventory with Price=15 and Qty=0
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(2))
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(15, euroCurrencyId))
									.qty(Quantity.of(0, eachUOM))
									.build())
					.get();

			assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
			assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("0");

			final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
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
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(10, euroCurrencyId))
									.qty(Quantity.of(10, eachUOM))
									.build())
					.get();

			assertThat(costDetailResult.getAmt().getValue()).isEqualTo("100");
			assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("10");

			final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}

		// Initial inventory with Price=15 and Qty=0
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(2))
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(15, euroCurrencyId))
									.qty(Quantity.of(0, eachUOM))
									.build())
					.get();

			assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
			assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("0");

			final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");

			// The cost price is changed even if existing qty
			// because we decided that the responsibility of whom is setting the explicit cost price.
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15");
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
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(10, euroCurrencyId))
									.qty(Quantity.of(10, eachUOM))
									.build())
					.get();

			assertThat(costDetailResult.getAmt().getValue()).isEqualTo("100");
			assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("10");

			final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}

		// Initial inventory with Price=15 and Qty=10
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(2))
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(15, euroCurrencyId))
									.qty(Quantity.of(10, eachUOM))
									.build())
					.get();

			assertThat(costDetailResult.getAmt().getValue()).isEqualTo("150");
			assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("10");

			final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("20");

			// The cost price is changed even if existing qty
			// because we decided that the responsibility of whom is setting the explicit cost price.
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15");
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
									.amt(CostAmount.of(0, euroCurrencyId))
									.explicitCostPrice(CostAmount.of(10, euroCurrencyId))
									.qty(Quantity.of(0, eachUOM))
									.build())
					.get();

			assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
			assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("0");

			final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
		}

		// Initial inventory with Price=0 and Qty=10
		{
			final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
							costDetailCreateRequest()
									.documentRef(CostingDocumentRef.ofInventoryLineId(2))
									.amt(CostAmount.of(0, euroCurrencyId))
									.qty(Quantity.of(10, eachUOM))
									.build())
					.get();

			// The amount is the current explicit cost multiplied by the existing qty
			assertThat(costDetailResult.getAmt().getValue()).isEqualTo("100");
			assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("10");

			final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
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
										.amt(CostAmount.of(0, euroCurrencyId))
										.explicitCostPrice(CostAmount.of(10, euroCurrencyId))
										.qty(Quantity.of(0, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("0");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
			}

			// Shipment
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(CostAmount.of(0, euroCurrencyId)) // to be calculated
										.qty(Quantity.of(-10, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("-100");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("-10");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
			}

			// Receipt
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofMatchPOId(1))
										.amt(CostAmount.of(10 * 15, euroCurrencyId))
										.qty(Quantity.of(10, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("150");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("10");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15");
			}

			// Shipment reversal
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(2))
										.initialDocumentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(CostAmount.of(+100, euroCurrencyId))
										.qty(Quantity.of(+10, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("100");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("10");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
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
										.amt(CostAmount.of(0, euroCurrencyId))
										.explicitCostPrice(CostAmount.of(10, euroCurrencyId))
										.qty(Quantity.of(0, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("0");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
			}

			// Shipment
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(CostAmount.of(0, euroCurrencyId)) // to be calculated
										.qty(Quantity.of(-10, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("-100");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("-10");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
			}

			// Shipment reversal
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(2))
										.initialDocumentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(CostAmount.of(+100, euroCurrencyId))
										.qty(Quantity.of(+10, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("100");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("10");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("10");
			}

			// Receipt
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofMatchPOId(1))
										.amt(CostAmount.of(10 * 15, euroCurrencyId))
										.qty(Quantity.of(10, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("150");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("10");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
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
										.amt(CostAmount.of(0, euroCurrencyId)) // to be calculated
										.qty(Quantity.of(-100, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("-100");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("0");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("0");
			}

			// Receipt
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofMatchPOId(1))
										.amt(CostAmount.of(10 * 15, euroCurrencyId))
										.qty(Quantity.of(10, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("150");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("10");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("10");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("15");
			}

			// Shipment reversal
			{
				final CostDetailCreateResult costDetailResult = handler.createOrUpdateCost(
								costDetailCreateRequest()
										.documentRef(CostingDocumentRef.ofShipmentLineId(2))
										.initialDocumentRef(CostingDocumentRef.ofShipmentLineId(1))
										.amt(CostAmount.of(0, euroCurrencyId))
										.qty(Quantity.of(+100, eachUOM))
										.build())
						.get();

				assertThat(costDetailResult.getAmt().getValue()).isEqualTo("0");
				assertThat(costDetailResult.getQty().toBigDecimal()).isEqualTo("100");

				final CurrentCost currentCost = getCurrentCostOrNull(orgId1);
				assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualTo("110");
				assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("1.3636"); // (10x15 + 100x0) / (10 + 100) = 150 / 110 = 1.3636
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
																				   .date(LocalDate.parse("2020-08-14"))
																				   .productId(productId)
																				   .attributeSetInstanceId(AttributeSetInstanceId.NONE)
																				   .qtyToMove(Quantity.of(100, eachUOM))
																				   //
																				   .outboundOrgId(orgId1)
																				   .outboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
																				   //
																				   .inboundOrgId(orgId1)
																				   .inboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
																				   //
																				   .build());

				assertThat(result.getOutboundAmountToPost(acctSchema).getValue()).isEqualTo("0");
				assertThat(result.getInboundAmountToPost(acctSchema).getValue()).isEqualTo("0");
			}

			@Test
			public void withInitialCostPrice_noInitialQty()
			{
				assertThat(getCurrentCostOrNull(orgId1)).isNull();

				// Initial inventory with Price=10 and Qty=0
				handler.createOrUpdateCost(
						costDetailCreateRequest()
								.documentRef(CostingDocumentRef.ofInventoryLineId(1))
								.amt(CostAmount.of(13, euroCurrencyId))
								.qty(Quantity.of(0, eachUOM))
								.build());

				final MoveCostsResult result = handler.createMovementCosts(MoveCostsRequest.builder()
																				   .acctSchemaId(acctSchemaId)
																				   .clientId(ClientId.METASFRESH)
																				   .costElement(costElement)
																				   .date(LocalDate.parse("2020-08-14"))
																				   .productId(productId)
																				   .attributeSetInstanceId(AttributeSetInstanceId.NONE)
																				   .qtyToMove(Quantity.of(100, eachUOM))
																				   //
																				   .outboundOrgId(orgId1)
																				   .outboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
																				   //
																				   .inboundOrgId(orgId1)
																				   .inboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
																				   //
																				   .build());

				assertThat(result.getOutboundAmountToPost(acctSchema).getValue()).isEqualTo("0");
				assertThat(result.getInboundAmountToPost(acctSchema).getValue()).isEqualTo("0");
			}

			@Test
			public void withInitialCostPrice_noInitialQty_ExplicitCostPrice()
			{
				assertThat(getCurrentCostOrNull(orgId1)).isNull();

				// Initial inventory with Price=10 and Qty=0
				handler.createOrUpdateCost(
						costDetailCreateRequest()
								.documentRef(CostingDocumentRef.ofInventoryLineId(1))
								.amt(CostAmount.of(0, euroCurrencyId))
								.explicitCostPrice(CostAmount.of(13, euroCurrencyId))
								.qty(Quantity.of(0, eachUOM))
								.build());

				final MoveCostsResult result = handler.createMovementCosts(MoveCostsRequest.builder()
																				   .acctSchemaId(acctSchemaId)
																				   .clientId(ClientId.METASFRESH)
																				   .costElement(costElement)
																				   .date(LocalDate.parse("2020-08-14"))
																				   .productId(productId)
																				   .attributeSetInstanceId(AttributeSetInstanceId.NONE)
																				   .qtyToMove(Quantity.of(100, eachUOM))
																				   //
																				   .outboundOrgId(orgId1)
																				   .outboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
																				   //
																				   .inboundOrgId(orgId1)
																				   .inboundDocumentRef(CostingDocumentRef.ofOutboundMovementLineId(1))
																				   //
																				   .build());

				assertThat(result.getOutboundAmountToPost(acctSchema).getValue()).isEqualTo("-1300");
				assertThat(result.getInboundAmountToPost(acctSchema).getValue()).isEqualTo("+1300");
			}

		}

	}
}
