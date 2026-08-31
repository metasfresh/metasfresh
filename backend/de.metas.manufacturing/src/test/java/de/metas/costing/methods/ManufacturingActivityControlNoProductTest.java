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
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
 * Covers the ActivityControl cost-collector posting path when the reported {@code S_Resource}
 * has no linked {@code M_Product} (me03#29817): posting shall be a graceful no-op
 * ({@link CostDetailCreateResultsList#EMPTY}), not an {@link org.adempiere.exceptions.AdempiereException}.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ManufacturingActivityControlNoProductTest
{
	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");
	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);

	private ManufacturingStandardCostingMethodHandler handler;

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
		final CurrentCostsRepository currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		final CostDetailRepository costDetailsRepo = new CostDetailRepository();
		final CostDetailService costDetailsService = new CostDetailService(costDetailsRepo, costElementRepo);
		final CostingMethodHandlerUtils handlerUtils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				currentCostsRepo,
				costDetailsService);

		handler = new ManufacturingStandardCostingMethodHandler(
				currentCostsRepo,
				costDetailsService,
				handlerUtils,
				new StandardCostingMethodHandler(handlerUtils));

		euroCurrencyId = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();
		eachUOM = BusinessTestHelper.createUomEach();

		standardMaterialCostElement = createStandardMaterialCostElement(costElementRepo);
		acctSchemaId = createAcctSchema();

		// CostDetailCreateRequest.productId is @NonNull; the ActivityControl branch under
		// test never reads it (it resolves the real product from the resource instead), so
		// any valid product id is a safe placeholder here.
		placeholderProductId = BusinessTestHelper.createProductId("placeholder", eachUOM);
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

	private static ResourceId createResourceWithoutProduct()
	{
		final I_S_Resource resource = newInstance(I_S_Resource.class);
		resource.setName("resource-no-product");
		resource.setIsManufacturingResource(true);
		saveRecord(resource);

		// deliberately NOT creating/linking any M_Product to this resource
		return ResourceId.ofRepoId(resource.getS_Resource_ID());
	}

	private static PPCostCollectorId createActivityControlCostCollector(final ResourceId resourceId)
	{
		final I_PP_Cost_Collector cc = newInstance(I_PP_Cost_Collector.class);
		cc.setCostCollectorType(CostCollectorType.ActivityControl.getCode());
		cc.setS_Resource_ID(resourceId.getRepoId());
		cc.setMovementQty(BigDecimal.ONE);
		saveRecord(cc);

		return PPCostCollectorId.ofRepoId(cc.getPP_Cost_Collector_ID());
	}

	@Test
	public void activityControl_resourceWithoutProduct_postsNoCost()
	{
		final ResourceId resourceId = createResourceWithoutProduct();
		final PPCostCollectorId costCollectorId = createActivityControlCostCollector(resourceId);

		final CostDetailCreateResultsList result = handler.createOrUpdateCost(
				CostDetailCreateRequest.builder()
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
						.build());

		// no cost posted, no exception thrown; CostDetailCreateResultsList has no isEmpty()
		// accessor, so equality against the EMPTY singleton is the equivalent check (it is
		// returned by every empty-producing factory method, see ofList/ofNullable).
		assertThat(result).isEqualTo(CostDetailCreateResultsList.EMPTY);
	}
}
