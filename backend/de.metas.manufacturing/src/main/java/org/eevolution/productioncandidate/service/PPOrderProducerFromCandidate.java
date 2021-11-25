/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.productioncandidate.service;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.planning.ProductPlanningId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.agg.key.impl.PPOrderCandidateHeaderAggregationKeyBuilder;
import org.eevolution.productioncandidate.async.OrderGenerateResult;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PPOrderProducerFromCandidate implements ITrxItemChunkProcessor<I_PP_Order_Candidate, OrderGenerateResult>
{
	private final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IPPOrderBL ppOrderService = Services.get(IPPOrderBL.class);

	private ITrxItemExceptionHandler trxItemExceptionHandler = FailTrxItemExceptionHandler.instance;

	private final PPOrderCandidatePojoConverter ppOrderCandidateConverter = SpringContextHolder.instance.getBean(PPOrderCandidatePojoConverter.class);
	private final PPOrderCandidateDAO ppOrderCandidatesDAO = SpringContextHolder.instance.getBean(PPOrderCandidateDAO.class);

	private final IAggregationKeyBuilder<I_PP_Order_Candidate> ppOrderCandidateKeyBuilder;
	private final OrderGenerateResult result;

	@Nullable
	private PPOrderCreateRequest currentPPOrderRequest;

	@Nullable
	private List<I_PP_Order_Candidate> currentCandidates;

	public PPOrderProducerFromCandidate(@NonNull final OrderGenerateResult result)
	{
		this.result = result;

		ppOrderCandidateKeyBuilder = new PPOrderCandidateHeaderAggregationKeyBuilder();
	}

	/**
	 * Last processed item
	 */
	private I_PP_Order_Candidate lastItem;

	@NonNull
	public OrderGenerateResult createOrders(final List<I_PP_Order_Candidate> candidates)
	{
		return trxItemProcessorExecutorService
				.<I_PP_Order_Candidate, OrderGenerateResult>createExecutor()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setProcessor(this)
				.setExceptionHandler(trxItemExceptionHandler)
				.process(candidates);
	}

	@Override
	public boolean isSameChunk(final I_PP_Order_Candidate item)
	{
		if (lastItem == null)
		{
			return false;
		}

		return ppOrderCandidateKeyBuilder.isSame(item, lastItem);
	}

	@Override
	public void newChunk(final I_PP_Order_Candidate candidate)
	{
		currentPPOrderRequest = createRequest(candidate);
		currentCandidates = new ArrayList<>();
	}

	@Override
	public void completeChunk()
	{
		processCurrentRequest();
		resetCurrentOrder();
	}

	@Override
	public OrderGenerateResult getResult()
	{
		return result;
	}

	@Override
	public void cancelChunk()
	{
		resetCurrentOrder();
	}

	@Override
	public void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx) {}

	@Override
	public void process(@NonNull final I_PP_Order_Candidate item)
	{
		lastItem = item;
		if (currentCandidates != null)
		{
			currentCandidates.add(item);
		}
		else
		{
			currentCandidates = new ArrayList<>();
		}
	}

	public PPOrderProducerFromCandidate setTrxItemExceptionHandler(@NonNull final ITrxItemExceptionHandler trxItemExceptionHandler)
	{
		this.trxItemExceptionHandler = trxItemExceptionHandler;
		return this;
	}

	@NonNull
	private PPOrderCreateRequest createRequest(@NonNull final I_PP_Order_Candidate candidateRecord)
	{
		final PPOrderCandidate ppOrderCandidatePojo = ppOrderCandidateConverter.toPPOrderCandidate(candidateRecord);

		final ProductId productId = ProductId.ofRepoId(ppOrderCandidatePojo.getPpOrderData().getProductDescriptor().getProductId());

		final I_C_UOM uom = productBL.getStockUOM(productId);
		final Quantity qtyRequired = Quantity.of(candidateRecord.getQtyToProcess(), uom);

		return PPOrderCreateRequest.builder()
				.clientAndOrgId(ppOrderCandidatePojo.getPpOrderData().getClientAndOrgId())
				.productPlanningId(ProductPlanningId.ofRepoId(ppOrderCandidatePojo.getPpOrderData().getProductPlanningId()))
				.materialDispoGroupId(ppOrderCandidatePojo.getPpOrderData().getMaterialDispoGroupId())
				.plantId(ppOrderCandidatePojo.getPpOrderData().getPlantId())
				.warehouseId(ppOrderCandidatePojo.getPpOrderData().getWarehouseId())
				//
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(ppOrderCandidatePojo.getPpOrderData().getProductDescriptor().getAttributeSetInstanceId()))
				.qtyRequired(qtyRequired)
				//
				.dateOrdered(SystemTime.asInstant())
				.datePromised(ppOrderCandidatePojo.getPpOrderData().getDatePromised())
				.dateStartSchedule(ppOrderCandidatePojo.getPpOrderData().getDateStartSchedule())
				//
				.salesOrderLineId(OrderLineId.ofRepoIdOrNull(ppOrderCandidatePojo.getPpOrderData().getOrderLineId()))
				//
				.build();
	}

	private void processCurrentRequest()
	{
		Check.assumeNotNull(currentPPOrderRequest, "currentPPOrderRequest cannot be null");
		Check.assumeNotNull(currentCandidates, "currentCandidates cannot be null");

		final I_C_UOM currentUOM = currentPPOrderRequest.getQtyRequired().getUOM();

		final BigDecimal ppOrderQtyRequiredSum = currentCandidates.stream()
				.peek(candidate -> {
					if (candidate.getC_UOM_ID() != currentUOM.getC_UOM_ID())
					{
						//dev-note: can never happen atm due to aggregationKey
						throw new AdempiereException("PP_Order_Candidate.C_UOM_ID != currentPPOrderRequest.C_UOM_ID!")
								.setParameter("PP_Order_Candidate.PP_Order_Candidate_ID", candidate.getPP_Order_Candidate_ID())
								.setParameter("PP_Order_Candidate.C_UOM_ID", candidate.getC_UOM_ID())
								.setParameter("currentPPOrderRequest.C_UOM_ID", currentUOM.getC_UOM_ID());
					}
				})
				.map(I_PP_Order_Candidate::getQtyToProcess)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		final Quantity ppOrderQtyRequired = Quantity.of(ppOrderQtyRequiredSum, currentUOM);

		final PPOrderCreateRequest createRequest = currentPPOrderRequest.toBuilder()
				.qtyRequired(ppOrderQtyRequired)
				.build();

		final I_PP_Order ppOrder = ppOrderService.createOrder(createRequest);

		for (final I_PP_Order_Candidate ppOrderCandidateRecord : currentCandidates)
		{
			ppOrderCandidatesDAO.createProductionOrderAllocation(ppOrderCandidateRecord, ppOrder);

			markCandidateAsProcessed(ppOrderCandidateRecord);
		}

		result.addOrder(ppOrder);

		final List<Integer> currentCandidatesId = currentCandidates
				.stream()
				.map(I_PP_Order_Candidate::getPP_Order_Candidate_ID)
				.collect(ImmutableList.toImmutableList());

		Loggables.addLog("PP_Order {0} created from PP_Order_Candidates Ids: {1}", ppOrder, currentCandidatesId);
	}

	private void resetCurrentOrder()
	{
		currentPPOrderRequest = null;
		currentCandidates = null;
	}

	private void markCandidateAsProcessed(@NonNull final I_PP_Order_Candidate candidateRecord)
	{
		candidateRecord.setProcessed(true);
		ppOrderCandidatesDAO.save(candidateRecord);
	}
}