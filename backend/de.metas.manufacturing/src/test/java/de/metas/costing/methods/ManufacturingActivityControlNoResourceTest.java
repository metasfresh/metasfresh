package de.metas.costing.methods;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.workflow.WFDurationUnit;
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
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
 * Covers the ActivityControl cost-collector posting path for the two production populations that
 * broke at Intercheese (me03#29817):
 * <ul>
 *     <li>the {@code S_Resource_ID = ResourceId.NO_RESOURCE} (540011) sentinel collision, which made
 *     {@code createCost} return {@code null} and NPE the pipeline, and</li>
 *     <li>a real, product-linked resource reaching {@code createActivityControl}, which used to
 *     {@code throw new AdempiereException("Computing activity costs is not yet supported")}.</li>
 * </ul>
 * Both must be graceful no-ops returning {@link CostDetailCreateResultsList#EMPTY}, for every
 * costing method.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ManufacturingActivityControlNoResourceTest
{
	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");
	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);

	private CostingMethodHandlerUtils handlerUtils;
	private CurrentCostsRepository currentCostsRepo;
	private CostDetailService costDetailsService;

	private OrgId orgId1;
	private CurrencyId euroCurrencyId;
	private I_C_UOM eachUOM;
	private AcctSchemaId acctSchemaId;
	private CostElement standardMaterialCostElement;
	private ProductId placeholderProductId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId1 = AdempiereTestHelper.createOrgWithTimeZone(ZONE_ID);

		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);

		final CostElementRepository costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		final CostDetailRepository costDetailsRepo = new CostDetailRepository();
		costDetailsService = new CostDetailService(costDetailsRepo, costElementRepo);
		handlerUtils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				currentCostsRepo,
				costDetailsService);

		euroCurrencyId = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();
		eachUOM = BusinessTestHelper.createUomEach();

		standardMaterialCostElement = createStandardMaterialCostElement(costElementRepo);
		acctSchemaId = createAcctSchema();

		// CostDetailCreateRequest.productId is @NonNull; the ActivityControl branch under
		// test never posts against it (it resolves the real product from the resource instead),
		// so any valid product id is a safe placeholder here.
		placeholderProductId = BusinessTestHelper.createProductId("placeholder", eachUOM);
	}

	private ManufacturingStandardCostingMethodHandler newStandardHandler()
	{
		return new ManufacturingStandardCostingMethodHandler(
				currentCostsRepo,
				costDetailsService,
				handlerUtils,
				new StandardCostingMethodHandler(handlerUtils));
	}

	private ManufacturingAveragePOCostingMethodHandler newAveragePOHandler()
	{
		// The delegate is only consulted by recalculateCostDetailAmountAndUpdateCurrentCost, which
		// these tests never call; a mock keeps us clear of AveragePOCostingMethodHandler's heavy
		// (MatchInvoiceService/OrderCostService) constructor.
		return new ManufacturingAveragePOCostingMethodHandler(
				handlerUtils,
				Mockito.mock(AveragePOCostingMethodHandler.class));
	}

	private ManufacturingLastPOCostingMethodHandler newLastPOHandler()
	{
		return new ManufacturingLastPOCostingMethodHandler(handlerUtils);
	}

	private ManufacturingMovingAverageInvoiceCostingMethodHandler newMovingAverageHandler()
	{
		return new ManufacturingMovingAverageInvoiceCostingMethodHandler(
				handlerUtils,
				Mockito.mock(MovingAverageInvoiceCostingMethodHandler.class));
	}

	private static CostElement createStandardMaterialCostElement(final CostElementRepository costElementRepo)
	{
		final I_M_CostElement record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_CostElement.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName("Standard Material");
		record.setCostElementType(CostElementType.Material.getCode());
		record.setCostingMethod(CostingMethod.StandardCosting.getCode());
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
		acctSchemaRecord.setCostingMethod(CostingMethod.StandardCosting.getCode());
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

	private static Instant instant(final String localDate)
	{
		return LocalDate.parse(localDate).atStartOfDay(ZONE_ID).toInstant();
	}

	/**
	 * Builds a resource whose id is the {@link ResourceId#NO_RESOURCE} sentinel (540011). No
	 * {@code S_Resource} row is created on purpose: the sentinel is what the handler tests
	 * {@code isNoResource()} against, exactly as the Intercheese collectors carried it.
	 */
	private static int noResourceSentinelId()
	{
		return ResourceId.NO_RESOURCE.getRepoId();
	}

	private ResourceId createResourceWithProduct()
	{
		final I_S_Resource resource = newInstance(I_S_Resource.class);
		resource.setName("resource-with-product");
		resource.setIsManufacturingResource(true);
		saveRecord(resource);
		final ResourceId resourceId = ResourceId.ofRepoId(resource.getS_Resource_ID());

		// link a cost product to the resource (ProductDAO#getProductIdByResourceId resolves via M_Product.S_Resource_ID)
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue("resource-product");
		product.setName("resource-product");
		product.setC_UOM_ID(eachUOM.getC_UOM_ID());
		product.setS_Resource_ID(resourceId.getRepoId());
		saveRecord(product);

		return resourceId;
	}

	private static PPCostCollectorId createActivityControlCostCollector(final int resourceRepoId)
	{
		final I_PP_Cost_Collector cc = newInstance(I_PP_Cost_Collector.class);
		cc.setCostCollectorType(CostCollectorType.ActivityControl.getCode());
		cc.setS_Resource_ID(resourceRepoId);
		// AveragePO/LastPO/MovingAverage createCost eagerly resolves PPOrderId.ofRepoId(getPP_Order_ID())
		// before dispatching; the ActivityControl branch never fetches the order row, so a positive
		// dummy id is enough (a real cost collector always belongs to a PP_Order).
		cc.setPP_Order_ID(1);
		cc.setMovementQty(BigDecimal.ONE);
		// needed by IPPCostCollectorBL#getTotalDurationReported on the product-linked path
		cc.setDurationUnit(WFDurationUnit.Minute.getCode());
		cc.setSetupTimeReal(BigDecimal.ZERO);
		cc.setDurationReal(BigDecimal.ZERO);
		saveRecord(cc);

		return PPCostCollectorId.ofRepoId(cc.getPP_Cost_Collector_ID());
	}

	private CostDetailCreateRequest request(final PPCostCollectorId costCollectorId)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(ClientId.METASFRESH)
				.orgId(orgId1)
				.productId(placeholderProductId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElement(standardMaterialCostElement)
				.documentRef(CostingDocumentRef.ofCostCollectorId(costCollectorId))
				.qty(Quantity.of(1, eachUOM))
				.amt(CostAmount.zero(euroCurrencyId))
				.date(instant("2026-07-22"))
				.build();
	}

	@Test
	public void standard_activityControl_noResourceSentinel_postsNoCost()
	{
		final PPCostCollectorId costCollectorId = createActivityControlCostCollector(noResourceSentinelId());

		final CostDetailCreateResultsList result = newStandardHandler().createOrUpdateCost(request(costCollectorId));

		assertThat(result).isEqualTo(CostDetailCreateResultsList.EMPTY);
	}

	@Test
	public void averagePO_activityControl_noResourceSentinel_postsNoCost()
	{
		final PPCostCollectorId costCollectorId = createActivityControlCostCollector(noResourceSentinelId());

		final CostDetailCreateResultsList result = newAveragePOHandler().createOrUpdateCost(request(costCollectorId));

		assertThat(result).isEqualTo(CostDetailCreateResultsList.EMPTY);
	}

	@Test
	public void averagePO_activityControl_resourceWithProduct_postsNoCost()
	{
		final ResourceId resourceId = createResourceWithProduct();
		final PPCostCollectorId costCollectorId = createActivityControlCostCollector(resourceId.getRepoId());

		final CostDetailCreateResultsList result = newAveragePOHandler().createOrUpdateCost(request(costCollectorId));

		assertThat(result).isEqualTo(CostDetailCreateResultsList.EMPTY);
	}

	@Test
	public void lastPO_activityControl_resourceWithProduct_postsNoCost()
	{
		final ResourceId resourceId = createResourceWithProduct();
		final PPCostCollectorId costCollectorId = createActivityControlCostCollector(resourceId.getRepoId());

		final CostDetailCreateResultsList result = newLastPOHandler().createOrUpdateCost(request(costCollectorId));

		assertThat(result).isEqualTo(CostDetailCreateResultsList.EMPTY);
	}

	@Test
	public void movingAverage_activityControl_resourceWithProduct_postsNoCost()
	{
		final ResourceId resourceId = createResourceWithProduct();
		final PPCostCollectorId costCollectorId = createActivityControlCostCollector(resourceId.getRepoId());

		final CostDetailCreateResultsList result = newMovingAverageHandler().createOrUpdateCost(request(costCollectorId));

		assertThat(result).isEqualTo(CostDetailCreateResultsList.EMPTY);
	}
}
