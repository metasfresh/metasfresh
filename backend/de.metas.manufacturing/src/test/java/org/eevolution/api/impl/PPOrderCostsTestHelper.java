/*
 * #%L
 * de.metas.adempiere.libero.libero
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

package org.eevolution.api.impl;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.CoalesceUtil;
import de.metas.costing.CostElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Cost;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.I_PP_Order_Workflow;
import org.eevolution.model.X_PP_Order_Workflow;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class PPOrderCostsTestHelper
{
	public final IUOMConversionDAO uomConversionDAO;

	public final ClientId clientId = ClientId.METASFRESH;

	public final I_C_UOM uomEach;
	public final UomId uomEachId;

	public final I_C_UOM uomKg;
	public final UomId uomKgId;

	public final I_C_UOM uomBag;
	public final UomId uomBagId;

	private final I_C_UOM uomSeconds;

	public final CurrencyId currencyId;

	private final AcctSchemaId acctSchemaId;
	private final CostTypeId costTypeId = CostTypeId.ofRepoId(1);
	public final CostElement costElement;

	public PPOrderCostsTestHelper()
	{
		uomConversionDAO = Services.get(IUOMConversionDAO.class);

		Env.setClientId(Env.getCtx(), clientId);

		AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(
				acctSchemaId = AcctSchemaTestHelper.newAcctSchema()
						.costingLevel(CostingLevel.Client)
						.costingMethod(CostingMethod.AveragePO)
						.costTypeId(costTypeId)
						.currencyId(currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR))
						.build());

		Services.registerService(IProductCostingBL.class, new MockedProductCostingBL(CostingLevel.Client, CostingMethod.AveragePO));

		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
		final CostElementRepository costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		SpringContextHolder.registerJUnitBean(ICurrentCostsRepository.class, new CurrentCostsRepository(costElementRepo));
		SpringContextHolder.registerJUnitBean(ICostElementRepository.class, costElementRepo);

		uomEach = BusinessTestHelper.createUOM("Ea", 0, 0);
		uomEachId = UomId.ofRepoId(uomEach.getC_UOM_ID());

		uomKg = BusinessTestHelper.createUOM("Kg", 2, 4);
		uomKgId = UomId.ofRepoId(uomKg.getC_UOM_ID());

		uomBag = BusinessTestHelper.createUOM("Bag", 0, 0);
		uomBagId = UomId.ofRepoId(uomBag.getC_UOM_ID());

		uomSeconds = BusinessTestHelper.createUOM("Second", X12DE355.ofTemporalUnit(ChronoUnit.SECONDS));

		costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
	}

	public AcctSchema getAcctSchema()
	{
		return Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);
	}

	@Builder(builderMethodName = "currentCost", builderClassName = "$CurrentCostBuilder")
	private void createCurrentCost(
			final ProductId productId,
			final I_C_UOM uom,
			final String currentCostPrice)
	{
		final I_M_Cost cost = InterfaceWrapperHelper.newInstance(I_M_Cost.class);
		cost.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		cost.setM_CostElement_ID(costElement.getId().getRepoId());
		cost.setM_CostType_ID(costTypeId.getRepoId());
		cost.setM_Product_ID(productId.getRepoId());
		cost.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		cost.setC_UOM_ID(uom.getC_UOM_ID());
		cost.setC_Currency_ID(currencyId.getRepoId());
		cost.setCurrentCostPrice(new BigDecimal(currentCostPrice));
		InterfaceWrapperHelper.saveRecord(cost);
	}

	private void createDummyRouting(final PPOrderId ppOrderId)
	{
		final I_PP_Order_Workflow ppOrderWorkflow = InterfaceWrapperHelper.newInstance(I_PP_Order_Workflow.class);
		ppOrderWorkflow.setPP_Order_ID(ppOrderId.getRepoId());
		ppOrderWorkflow.setDurationUnit(X_PP_Order_Workflow.DURATIONUNIT_Second);
		ppOrderWorkflow.setAD_Workflow_ID(1);
		InterfaceWrapperHelper.saveRecord(ppOrderWorkflow);

		{
			final I_PP_Order_Node ppOrderNode = InterfaceWrapperHelper.newInstance(I_PP_Order_Node.class);
			ppOrderNode.setPP_Order_ID(ppOrderWorkflow.getPP_Order_ID());
			ppOrderNode.setPP_Order_Workflow_ID(ppOrderWorkflow.getPP_Order_Workflow_ID());
			ppOrderNode.setAD_Workflow_ID(ppOrderWorkflow.getAD_Workflow_ID());
			ppOrderNode.setAD_WF_Node_ID(1);
			ppOrderNode.setValue("activity1");
			ppOrderNode.setS_Resource_ID(BusinessTestHelper.createManufacturingResource("workstation1", uomSeconds).getRepoId());
			ppOrderNode.setC_UOM_ID(uomSeconds.getC_UOM_ID());
			ppOrderNode.setPP_Activity_Type(PPRoutingActivityType.WorkReport.getCode());
			ppOrderNode.setName("Name");
			ppOrderNode.setDocStatus(PPOrderRoutingActivityStatus.NOT_STARTED.getDocStatus());
			ppOrderNode.setPP_Activity_Type(PPRoutingActivityType.WorkReport.getCode());
			ppOrderNode.setName("Name");
			InterfaceWrapperHelper.saveRecord(ppOrderNode);

			ppOrderWorkflow.setPP_Order_Node_ID((ppOrderNode.getPP_Order_Node_ID()));
			InterfaceWrapperHelper.saveRecord(ppOrderWorkflow);
		}
	}

	@NonNull
	@Builder(builderMethodName = "order", builderClassName = "$OrderBuilder")
	private I_PP_Order createOrder(
			@NonNull final ProductId finishedGoodsProductId,
			@NonNull final String finishedGoodsQty,
			@NonNull final I_C_UOM finishedGoodsUOM,
			//
			@NonNull final ProductId componentId,
			@NonNull final String componentQtyRequired,
			@NonNull final I_C_UOM componentUOM,
			//
			@Nullable final ProductId componentId2,
			@Nullable final String componentQtyRequired2,
			@Nullable final I_C_UOM componentUOM2,
			//
			@Nullable final ProductId coProductId,
			@Nullable final String coProductQtyRequired,
			@Nullable final I_C_UOM coProductUOM
	)
	{
		final @NonNull I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(finishedGoodsProductId.getRepoId());
		ppOrder.setC_UOM_ID(finishedGoodsUOM.getC_UOM_ID());
		ppOrder.setQtyOrdered(new BigDecimal(finishedGoodsQty));
		InterfaceWrapperHelper.saveRecord(ppOrder);
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());

		createDummyRouting(ppOrderId);

		orderBOMLine().ppOrderId(ppOrderId).productId(componentId).qtyRequired(componentQtyRequired).uom(componentUOM).build();

		if (componentId2 != null)
		{
			orderBOMLine().ppOrderId(ppOrderId).productId(componentId2).qtyRequired(componentQtyRequired2).uom(componentUOM2).build();
		}

		if (coProductId != null)
		{
			orderBOMLine().ppOrderId(ppOrderId).componentType(BOMComponentType.CoProduct).productId(coProductId).qtyRequired(coProductQtyRequired).uom(coProductUOM).build();
		}

		return ppOrder;
	}

	@Builder(builderMethodName = "orderBOMLine", builderClassName = "$OrderBOMLineBuilder")
	private void createOrderBOMLine(
			@NonNull final PPOrderId ppOrderId,
			@Nullable BOMComponentType componentType,
			@NonNull final ProductId productId,
			@NonNull final String qtyRequired,
			@NonNull final I_C_UOM uom)
	{
		final I_PP_Order_BOMLine bomLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class);
		bomLine.setPP_Order_ID(ppOrderId.getRepoId());
		bomLine.setComponentType(CoalesceUtil.coalesceNotNull(componentType, BOMComponentType.Component).getCode());
		bomLine.setM_Product_ID(productId.getRepoId());
		bomLine.setC_UOM_ID(uom.getC_UOM_ID());
		bomLine.setQtyRequiered(new BigDecimal(qtyRequired));
		InterfaceWrapperHelper.saveRecord(bomLine);
	}
}
