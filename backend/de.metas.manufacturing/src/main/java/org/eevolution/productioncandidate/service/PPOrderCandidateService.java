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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.EmptyUtil;
import de.metas.logging.LogManager;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ProductPlanningService;
import de.metas.material.planning.pporder.ComputeQtyRequiredRequest;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMLineId;
import org.eevolution.model.I_PP_OrderLine_Candidate;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.productioncandidate.agg.key.impl.PPOrderCandidateHeaderAggregationKeyBuilder;
import org.eevolution.productioncandidate.async.OrderGenerateResult;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PPOrderCandidateService
{
	private static final Logger logger = LogManager.getLogger(PPOrderCandidateService.class);

	private final IProductBOMDAO productBOMsRepo = Services.get(IProductBOMDAO.class);
	private final IProductBOMBL productBOMsBL = Services.get(IProductBOMBL.class);
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	private final ProductPlanningService productPlanningService;
	private final PPOrderCandidateDAO ppOrderCandidateDAO;

	public PPOrderCandidateService(
			@NonNull final ProductPlanningService productPlanningService,
			@NonNull final PPOrderCandidateDAO ppOrderCandidateDAO)
	{
		this.productPlanningService = productPlanningService;
		this.ppOrderCandidateDAO = ppOrderCandidateDAO;
	}

	@NonNull
	public I_PP_Order_Candidate createCandidateWithLines(@NonNull final PPOrderCandidateCreateRequest ppOrderCandidateCreateRequest)
	{
		final I_PP_Order_Candidate ppOrderCandidateRecord = CreateOrderCandidateCommand.builder()
				.request(ppOrderCandidateCreateRequest)
				.build()
				.execute();

		createPPOrderCandidateLines(ppOrderCandidateRecord);

		return ppOrderCandidateRecord;
	}

	@NonNull
	public OrderGenerateResult processCandidates(@NonNull final Stream<I_PP_Order_Candidate> orderCandidates)
	{
		final ImmutableList<I_PP_Order_Candidate> sortedCandidates = orderCandidates
				.sorted(Comparator.comparing(this::generateHeaderAggregationKey))
				.collect(ImmutableList.toImmutableList());

		return createPPOrderProducerFromCandidate()
				.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance)
				.createOrders(sortedCandidates);
	}

	@NonNull
	public Iterator<I_PP_Order_Candidate> retrieveOCForSelection(@NonNull final PInstanceId pinstanceId)
	{
		return ppOrderCandidateDAO.retrieveOCForSelection(pinstanceId);
	}

	public void reopenCandidate(@NonNull final I_PP_Order_Candidate ppOrderCandidate)
	{
		if (ppOrderCandidate.isClosed())
		{
			throw new AdempiereException("Cannot reopen a closed candidate!")
					.appendParametersToMessage()
					.setParameter("PPOrderCandidateId", ppOrderCandidate.getPP_Order_Candidate_ID())
					.setParameter("isClosed", ppOrderCandidate.isClosed());
		}

		if (!ppOrderCandidate.isProcessed())
		{
			return;
		}

		ppOrderCandidate.setProcessed(false);

		ppOrderCandidateDAO.save(ppOrderCandidate);
	}

	public void closeCandidate(@NonNull final I_PP_Order_Candidate ppOrderCandidate)
	{
		ppOrderCandidate.setIsClosed(true);
		ppOrderCandidate.setProcessed(true);
		ppOrderCandidate.setQtyEntered(ppOrderCandidate.getQtyProcessed());

		ppOrderCandidateDAO.save(ppOrderCandidate);
	}

	public void syncLinesWithRequiredQty(@NonNull final I_PP_Order_Candidate ppOrderCandidate)
	{
		final PPOrderCandidateId ppOrderCandidateId = PPOrderCandidateId.ofRepoId(ppOrderCandidate.getPP_Order_Candidate_ID());

		final ImmutableList<I_PP_OrderLine_Candidate> orderCandidateLines = ppOrderCandidateDAO.getLinesByCandidateId(ppOrderCandidateId);

		if (EmptyUtil.isEmpty(orderCandidateLines))
		{
			createPPOrderCandidateLines(ppOrderCandidate);
		}
		else
		{
			final Quantity finishedGoodQty = Quantitys.create(ppOrderCandidate.getQtyEntered(), UomId.ofRepoId(ppOrderCandidate.getC_UOM_ID()));

			orderCandidateLines.forEach(ppOrderCandidateLine -> {
				final ComputeQtyRequiredRequest request = ComputeQtyRequiredRequest.builder()
						.finishedGoodQty(finishedGoodQty)
						.productBOMLineId(ProductBOMLineId.ofRepoId(ppOrderCandidateLine.getPP_Product_BOMLine_ID()))
						.build();

				final Quantity qtyRequired = orderBOMBL.getQtyRequired(request);

				ppOrderCandidateLine.setQtyEntered(qtyRequired.toBigDecimal());
				ppOrderCandidateDAO.saveLine(ppOrderCandidateLine);
			});
		}
	}

	@NonNull
	public Optional<Instant> recalculateDatePromised(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		final ProductPlanningId productPlanningId = ProductPlanningId.ofRepoIdOrNull(ppOrderCandidateRecord.getPP_Product_Planning_ID());

		if (productPlanningId == null)
		{
			return Optional.empty();
		}

		final I_PP_Product_Planning productPlanningRecord = productPlanningDAO.getById(productPlanningId);

		final int durationDays = productPlanningService.calculateDurationDays(productPlanningRecord, ppOrderCandidateRecord.getQtyEntered());
		final Instant dateStartSchedule = ppOrderCandidateRecord.getDateStartSchedule().toInstant();

		return Optional.of(dateStartSchedule.plus(durationDays, ChronoUnit.DAYS));
	}

	private void createPPOrderCandidateLines(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		final Date dateStartSchedule = TimeUtil.asDate(ppOrderCandidateRecord.getDateStartSchedule());

		Check.assumeNotNull(dateStartSchedule, "PP_Order_Candidate.DateStartSchedule is never null!");

		final I_PP_Product_BOM productBOM = productBOMsRepo.getById(ProductBOMId.ofRepoId(ppOrderCandidateRecord.getPP_Product_BOM_ID()));

		final ProductId orderCandidateProductId = ProductId.ofRepoId(ppOrderCandidateRecord.getM_Product_ID());

		final I_PP_Product_BOM verifiedProductBOM = PPOrderUtil.verifyProductBOMAndReturnIt(
				orderCandidateProductId,
				dateStartSchedule,
				productBOM);

		final List<I_PP_Product_BOMLine> productBOMLines = productBOMsRepo.retrieveLines(verifiedProductBOM);

		if (EmptyUtil.isEmpty(productBOMLines))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ProductBOM has no lines! - PP_Product_BOM_ID: {}", verifiedProductBOM.getPP_Product_BOM_ID());
			return;
		}

		for (final I_PP_Product_BOMLine productBOMLine : productBOMLines)
		{
			if (!productBOMsBL.isValidFromTo(productBOMLine, dateStartSchedule))
			{
				Loggables.withLogger(logger, Level.DEBUG).addLog("BOM Line skipped - {}", productBOMLine);
				continue;
			}

			createOrderLineCandidate(ppOrderCandidateRecord, productBOMLine);
		}
	}

	@NonNull
	private I_PP_OrderLine_Candidate createOrderLineCandidate(
			@NonNull final I_PP_Order_Candidate orderCandidateRecord,
			@NonNull final I_PP_Product_BOMLine bomLine)
	{
		final I_PP_OrderLine_Candidate orderLineCandidate = InterfaceWrapperHelper.newInstance(I_PP_OrderLine_Candidate.class);

		orderLineCandidate.setPP_Order_Candidate_ID(orderCandidateRecord.getPP_Order_Candidate_ID());

		orderLineCandidate.setPP_Product_BOMLine_ID(bomLine.getPP_Product_BOMLine_ID());
		orderLineCandidate.setComponentType(bomLine.getComponentType());
		orderLineCandidate.setM_Product_ID(bomLine.getM_Product_ID());

		orderLineCandidate.setDescription(bomLine.getDescription());

		if (bomLine.getM_AttributeSetInstance_ID() > 0)
		{
			final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(bomLine.getM_AttributeSetInstance_ID());
			final AttributeSetInstanceId asiCopy = attributesRepo.copyASI(asiId);
			orderLineCandidate.setM_AttributeSetInstance_ID(asiCopy.getRepoId());
		}

		final Quantity finishedGoodQty = Quantitys.create(orderCandidateRecord.getQtyEntered(), UomId.ofRepoId(orderCandidateRecord.getC_UOM_ID()));

		final ComputeQtyRequiredRequest request = ComputeQtyRequiredRequest.builder()
				.finishedGoodQty(finishedGoodQty)
				.productBOMLineId(ProductBOMLineId.ofRepoId(bomLine.getPP_Product_BOMLine_ID()))
				.build();

		final Quantity qtyRequired = Optional.ofNullable(orderBOMBL.getQtyRequired(request))
				.orElseThrow(() -> new AdempiereException("Couldn't calculate qtyRequired for bom line!")
						.appendParametersToMessage()
						.setParameter("PP_Product_BOMLine_ID", bomLine.getPP_Product_BOMLine_ID())
						.setParameter("PP_Order_Candidate_ID", orderCandidateRecord.getPP_Order_Candidate_ID()));

		orderLineCandidate.setQtyEntered(qtyRequired.toBigDecimal());
		orderLineCandidate.setC_UOM_ID(qtyRequired.getUOM().getC_UOM_ID());

		ppOrderCandidateDAO.saveLine(orderLineCandidate);

		return orderLineCandidate;
	}

	@NonNull
	private PPOrderProducerFromCandidate createPPOrderProducerFromCandidate()
	{
		return new PPOrderProducerFromCandidate(new OrderGenerateResult());
	}

	@NonNull
	private PPOrderCandidateHeaderAggregationKeyBuilder mkPPOrderCandidateHeaderAggregationKeyBuilder()
	{
		return new PPOrderCandidateHeaderAggregationKeyBuilder();
	}

	@NonNull
	private String generateHeaderAggregationKey(@NonNull final I_PP_Order_Candidate orderCandidateRecord)
	{
		final PPOrderCandidateHeaderAggregationKeyBuilder orderCandidateKeyBuilder = mkPPOrderCandidateHeaderAggregationKeyBuilder();
		return orderCandidateKeyBuilder.buildKey(orderCandidateRecord);
	}
}