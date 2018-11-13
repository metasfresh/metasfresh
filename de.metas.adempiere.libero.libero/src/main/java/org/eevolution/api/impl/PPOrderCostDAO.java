package org.eevolution.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.eevolution.api.IPPOrderCostDAO;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.model.I_PP_Order_Cost;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegment;
import de.metas.costing.IProductCostingBL;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

public class PPOrderCostDAO implements IPPOrderCostDAO
{
	@Override
	public PPOrderCosts getByOrderId(@NonNull final PPOrderId orderId)
	{
		final ImmutableList<PPOrderCost> costs = retrieveAllOrderCostsQuery(orderId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::toPPOrderCost)
				.collect(ImmutableList.toImmutableList());

		return PPOrderCosts.builder()
				.orderId(orderId)
				.costs(costs)
				.build();
	}

	@Override
	public void save(@NonNull final PPOrderCosts orderCosts)
	{
		final PPOrderId orderId = orderCosts.getOrderId();
		deleteByOrderId(orderId);

		orderCosts.forEach(cost -> createRecordAndSave(orderId, cost));
	}

	@Override
	public void deleteByOrderId(@NonNull final PPOrderId ppOrderId)
	{
		retrieveAllOrderCostsQuery(ppOrderId)
				.create()
				.delete();
	}

	private IQueryBuilder<I_PP_Order_Cost> retrieveAllOrderCostsQuery(@NonNull final PPOrderId ppOrderId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_Cost.class)
				.addEqualsFilter(I_PP_Order_Cost.COLUMNNAME_PP_Order_ID, ppOrderId)
		// .addOnlyActiveRecordsFilter() // NOTE: we need to retrieve ALL costs
		;
	}

	private I_PP_Order_Cost createRecordAndSave(final PPOrderId orderId, final PPOrderCost from)
	{
		final I_PP_Order_Cost record = InterfaceWrapperHelper.newInstance(I_PP_Order_Cost.class);
		record.setPP_Order_ID(orderId.getRepoId());
		updateRecord(record, from);
		saveRecord(record);
		return record;
	}

	private void updateRecord(@NonNull final I_PP_Order_Cost record, @NonNull final PPOrderCost from)
	{
		final CostSegment costSegment = from.getCostSegment();
		record.setAD_Org_ID(costSegment.getOrgId().getRepoId());
		record.setC_AcctSchema_ID(costSegment.getAcctSchemaId().getRepoId());
		record.setM_CostType_ID(costSegment.getCostTypeId().getRepoId());
		record.setM_Product_ID(costSegment.getProductId().getRepoId());
		record.setM_AttributeSetInstance_ID(costSegment.getAttributeSetInstanceId().getRepoId());

		final CostElementId costElementId = from.getCostElementId();
		record.setM_CostElement_ID(costElementId.getRepoId());

		final CostAmount amount = from.getAmount();
		record.setCurrentCostPrice(amount.getValue());
		// ppOrderCost.setCurrentCostPriceLL(cost.getCurrentCostPriceLL());
		// ppOrderCost.setCumulatedAmt(cost.getCumulatedAmt()); // TODO: delete it
		// ppOrderCost.setCumulatedQty(cost.getCumulatedQty()); // TODO: delete it
	}

	private PPOrderCost toPPOrderCost(final I_PP_Order_Cost record)
	{
		final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
		final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);

		final AcctSchema acctSchema = acctSchemasRepo.getById(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()));
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());

		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(productCostingBL.getCostingLevel(productId, acctSchema))
				.acctSchemaId(acctSchema.getId())
				.costTypeId(acctSchema.getCosting().getCostTypeId())
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.build();

		final CostElementId costElementId = CostElementId.ofRepoId(record.getM_CostElement_ID());

		final CostAmount amount = CostAmount.of(record.getCurrentCostPrice(), acctSchema.getCurrencyId());

		return PPOrderCost.builder()
				.costSegment(costSegment)
				.costElementId(costElementId)
				.amount(amount)
				.build();
	}
}
