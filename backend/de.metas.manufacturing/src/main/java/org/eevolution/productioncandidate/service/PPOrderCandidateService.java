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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ProductPlanningService;
import de.metas.material.planning.pporder.ComputeQtyRequiredRequest;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMLineId;
import org.eevolution.model.I_PP_OrderLine_Candidate;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.productioncandidate.async.OrderGenerateResult;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.dao.PPMaturingCandidateV;
import org.eevolution.productioncandidate.model.dao.PPMaturingCandidatesViewRepo;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;
import org.eevolution.productioncandidate.service.produce.PPOrderAllocatorService;
import org.eevolution.productioncandidate.service.produce.PPOrderProducerFromCandidate;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PPOrderCandidateService
{
	private final IProductBOMDAO productBOMsRepo = Services.get(IProductBOMDAO.class);
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	private final IPPOrderBL ppOrderService = Services.get(IPPOrderBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ProductPlanningService productPlanningService;
	private final PPOrderCandidateDAO ppOrderCandidateDAO;
	private final PPOrderAllocatorService ppOrderAllocatorBuilderService;
	private final PPMaturingCandidatesViewRepo ppMaturingCandidatesViewRepo;

	private static final Logger logger = LogManager.getLogger(PPOrderCandidateService.class);

	public PPOrderCandidateService(
			@NonNull final ProductPlanningService productPlanningService,
			@NonNull final PPOrderCandidateDAO ppOrderCandidateDAO,
			@NonNull final PPOrderAllocatorService ppOrderAllocatorBuilderService,
			@NonNull final PPMaturingCandidatesViewRepo ppMaturingCandidatesViewRepo)
	{
		this.productPlanningService = productPlanningService;
		this.ppOrderCandidateDAO = ppOrderCandidateDAO;
		this.ppMaturingCandidatesViewRepo = ppMaturingCandidatesViewRepo;
		this.ppOrderAllocatorBuilderService = ppOrderAllocatorBuilderService;
	}

	@NonNull
	public I_PP_Order_Candidate createUpdateCandidate(@NonNull final PPOrderCandidateCreateUpdateRequest ppOrderCandidateCreateUpdateRequest)
	{
		return CreateUpdateOrderCandidateCommand.builder()
				.request(ppOrderCandidateCreateUpdateRequest)
				.build()
				.execute();
	}

	@NonNull
	public OrderGenerateResult processCandidates(@NonNull final PPOrderCandidateProcessRequest ppOrderCandidateProcessRequest)
	{
		return createPPOrderProducerFromCandidate().createOrders(ppOrderCandidateProcessRequest);
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
		ppOrderCandidateDAO.closeCandidate(PPOrderCandidateId.ofRepoId(ppOrderCandidate.getPP_Order_Candidate_ID()));
	}

	public void syncLines(@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord)
	{
		final Date dateStartSchedule = TimeUtil.asDate(ppOrderCandidateRecord.getDateStartSchedule());

		Check.assumeNotNull(dateStartSchedule, "PP_Order_Candidate.DateStartSchedule is never null!");

		final I_PP_Product_BOM productBOM = getVerifiedBOMProduct(ppOrderCandidateRecord, dateStartSchedule);

		final List<I_PP_Product_BOMLine> productBOMLines = productBOMsRepo.retrieveLines(productBOM);

		final PPOrderCandidateId ppOrderCandidateId = PPOrderCandidateId.ofRepoId(ppOrderCandidateRecord.getPP_Order_Candidate_ID());

		final Map<I_PP_OrderLine_Candidate, Optional<I_PP_Product_BOMLine>> orderLineCandidate2BOMLine = getOrderLine2BOMLine(ppOrderCandidateId, productBOMLines);

		final List<I_PP_Product_BOMLine> unhandledBOMLines = getUnhandledBOMLines(orderLineCandidate2BOMLine, productBOMLines);

		for (final Map.Entry<I_PP_OrderLine_Candidate, Optional<I_PP_Product_BOMLine>> orderLineCandidate2BOMLineEntry : orderLineCandidate2BOMLine.entrySet())
		{
			final boolean isOrderLineOutdated = !orderLineCandidate2BOMLineEntry.getValue().isPresent();
			if (isOrderLineOutdated)
			{
				handleOutdatedLine(orderLineCandidate2BOMLineEntry.getKey());
			}
			else
			{
				handleUpdatedLine(ppOrderCandidateRecord, orderLineCandidate2BOMLineEntry.getKey(), orderLineCandidate2BOMLineEntry.getValue().get());
			}
		}

		for (final I_PP_Product_BOMLine productBOMLine : unhandledBOMLines)
		{
			createOrderLineCandidate(ppOrderCandidateRecord, productBOMLine);
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

		final ProductPlanning productPlanningRecord = productPlanningDAO.getById(productPlanningId);

		final int durationDays = productPlanningService.calculateDurationDays(productPlanningRecord, ppOrderCandidateRecord.getQtyEntered());
		final Instant dateStartSchedule = ppOrderCandidateRecord.getDateStartSchedule().toInstant();

		return Optional.of(dateStartSchedule.plus(durationDays, ChronoUnit.DAYS));
	}

	public static boolean canAssignBOMVersion(@NonNull final I_PP_Order_Candidate ppOrderCandidate, @NonNull final I_PP_Product_BOM bomVersion)
	{
		return ppOrderCandidate.getDateStartSchedule().compareTo(bomVersion.getValidFrom()) >= 0
				&& ppOrderCandidate.getQtyProcessed().signum() == 0;
	}

	private void createOrderLineCandidate(
			@NonNull final I_PP_Order_Candidate orderCandidateRecord,
			@NonNull final I_PP_Product_BOMLine bomLine)
	{
		final I_PP_OrderLine_Candidate orderLineCandidate = InterfaceWrapperHelper.newInstance(I_PP_OrderLine_Candidate.class);

		orderLineCandidate.setPP_Order_Candidate_ID(orderCandidateRecord.getPP_Order_Candidate_ID());

		setBOMLineRelatedFields(orderLineCandidate, bomLine);

		setQtyEntered(orderCandidateRecord, orderLineCandidate, bomLine);

		ppOrderCandidateDAO.saveLine(orderLineCandidate);
	}

	private void setBOMLineRelatedFields(
			@NonNull final I_PP_OrderLine_Candidate orderLineCandidate,
			@NonNull final I_PP_Product_BOMLine bomLine)
	{
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
	}

	private void setQtyEntered(
			@NonNull final I_PP_Order_Candidate orderCandidateRecord,
			@NonNull final I_PP_OrderLine_Candidate orderLineCandidate,
			@NonNull final I_PP_Product_BOMLine bomLine)
	{
		final Quantity finishedGoodQty = Quantitys.create(orderCandidateRecord.getQtyToProcess(), UomId.ofRepoId(orderCandidateRecord.getC_UOM_ID()));

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
	}

	@NonNull
	private Map<I_PP_OrderLine_Candidate, Optional<I_PP_Product_BOMLine>> getOrderLine2BOMLine(
			@NonNull final PPOrderCandidateId ppOrderCandidateId,
			@NonNull final List<I_PP_Product_BOMLine> productBOMLines)
	{
		final ImmutableList<I_PP_OrderLine_Candidate> orderCandidateLines = ppOrderCandidateDAO.getLinesByCandidateId(ppOrderCandidateId);

		final Map<I_PP_OrderLine_Candidate, Optional<I_PP_Product_BOMLine>> orderLine2BOMLine = new HashMap<>();

		for (final I_PP_OrderLine_Candidate orderLineCandidate : orderCandidateLines)
		{
			for (final I_PP_Product_BOMLine productBOMLine : productBOMLines)
			{
				if (isBOMLineMatchingOrderLine(orderLineCandidate, productBOMLine))
				{
					orderLine2BOMLine.put(orderLineCandidate, Optional.of(productBOMLine));
					break;
				}
			}

			orderLine2BOMLine.putIfAbsent(orderLineCandidate, Optional.empty());
		}

		return ImmutableMap.copyOf(orderLine2BOMLine);
	}

	@NonNull
	private List<I_PP_Product_BOMLine> getUnhandledBOMLines(
			@NonNull final Map<I_PP_OrderLine_Candidate, Optional<I_PP_Product_BOMLine>> orderLine2BomLine,
			@NonNull final List<I_PP_Product_BOMLine> bomLines)
	{
		final ImmutableSet<Integer> handledBOMLineIds = orderLine2BomLine.values()
				.stream()
				.filter(Optional::isPresent)
				.map(bomLine -> bomLine.get().getPP_Product_BOMLine_ID())
				.collect(ImmutableSet.toImmutableSet());

		return bomLines.stream()
				.filter(bomLine -> !handledBOMLineIds.contains(bomLine.getPP_Product_BOMLine_ID()))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private I_PP_Product_BOM getVerifiedBOMProduct(
			@NonNull final I_PP_Order_Candidate ppOrderCandidate,
			@NonNull final Date dateStartSchedule)
	{
		final I_PP_Product_BOM productBOM = productBOMsRepo.getById(ProductBOMId.ofRepoId(ppOrderCandidate.getPP_Product_BOM_ID()));

		final ProductId orderCandidateProductId = ProductId.ofRepoId(ppOrderCandidate.getM_Product_ID());

		return PPOrderUtil.verifyProductBOMAndReturnIt(
				orderCandidateProductId,
				dateStartSchedule,
				productBOM);
	}

	private boolean isBOMLineMatchingOrderLine(
			@NonNull final I_PP_OrderLine_Candidate orderLineCandidate,
			@NonNull final I_PP_Product_BOMLine productBOMLine)
	{
		final AttributeSetInstanceId orderLineCandidateASIId = AttributeSetInstanceId.ofRepoIdOrNull(orderLineCandidate.getM_AttributeSetInstance_ID());
		final AttributeSetInstanceId productBOMLineASIId = AttributeSetInstanceId.ofRepoIdOrNull(productBOMLine.getM_AttributeSetInstance_ID());

		return Objects.equals(orderLineCandidate.getComponentType(), productBOMLine.getComponentType())
				&& orderLineCandidate.getM_Product_ID() == productBOMLine.getM_Product_ID()
				&& attributesRepo.nullSafeASIEquals(orderLineCandidateASIId, productBOMLineASIId);
	}

	@NonNull
	private PPOrderProducerFromCandidate createPPOrderProducerFromCandidate()
	{
		return PPOrderProducerFromCandidate.builder()
				.ppOrderAllocatorBuilderService(ppOrderAllocatorBuilderService)
				.ppOrderService(ppOrderService)
				.trxManager(trxManager)
				.ppOrderCandidatesDAO(ppOrderCandidateDAO)
				.productPlanningsRepo(productPlanningDAO)
				.createEachPPOrderInOwnTrx(true)
				.build();
	}

	private void handleOutdatedLine(@NonNull final I_PP_OrderLine_Candidate orderCandidateLine)
	{
		orderCandidateLine.setQtyEntered(BigDecimal.ZERO);

		ppOrderCandidateDAO.saveLine(orderCandidateLine);
	}

	private void handleUpdatedLine(
			@NonNull final I_PP_Order_Candidate ppOrderCandidateRecord,
			@NonNull final I_PP_OrderLine_Candidate existingOrderCandidateLine,
			@NonNull final I_PP_Product_BOMLine newBOMLine)
	{
		syncOrderLineWithBOMLine(existingOrderCandidateLine, ppOrderCandidateRecord, newBOMLine);
	}

	private void syncOrderLineWithBOMLine(
			@NonNull final I_PP_OrderLine_Candidate orderCandidateLine,
			@NonNull final I_PP_Order_Candidate ppOrderCandidate,
			@NonNull final I_PP_Product_BOMLine productBOMLine)
	{
		setBOMLineRelatedFields(orderCandidateLine, productBOMLine);

		setQtyEntered(ppOrderCandidate, orderCandidateLine, productBOMLine);

		ppOrderCandidateDAO.saveLine(orderCandidateLine);
	}

	public RecomputeMaturingCandidatesResult recomputeMaturingCandidates()
	{
		final int deletedCandidates = ppMaturingCandidatesViewRepo.deleteStaleCandidates();
		final Map<CrudOperationResult, Long> resultMap = ppMaturingCandidatesViewRepo.streamValidCandidates()
				.map(this::createUpdateCandidate)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		return RecomputeMaturingCandidatesResult.builder()
				.deleted(deletedCandidates)
				.created(resultMap.get(CrudOperationResult.CREATED) != null ? resultMap.get(CrudOperationResult.CREATED) : 0)
				.updated(resultMap.get(CrudOperationResult.UPDATED) != null ? resultMap.get(CrudOperationResult.UPDATED) : 0)
				.ignored(resultMap.get(CrudOperationResult.NO_OP) != null ? resultMap.get(CrudOperationResult.NO_OP) : 0)
				.build();
	}

	private CrudOperationResult createUpdateCandidate(final @NonNull PPMaturingCandidateV ppMaturingCandidatesV)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			try
			{
				return createUpdateCandidate0(ppMaturingCandidatesV);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed to create/update a Maturing candidate for: {}", ppMaturingCandidatesV, ex);
			}
			return CrudOperationResult.NO_OP;
		});
	}

	@NonNull
	private CrudOperationResult createUpdateCandidate0(final @NonNull PPMaturingCandidateV ppMaturingCandidatesV)
	{
		final ProductPlanningId productPlanningId = ppMaturingCandidatesV.getProductPlanningId();

		final ProductPlanning productPlanning = productPlanningDAO.getById(productPlanningId);
		Check.assume(productPlanning.isMatured(), "PP_Product_Planning_ID: {} is not matured", productPlanningId);

		createUpdateCandidate(PPOrderCandidateCreateUpdateRequest.builder()
				.clientAndOrgId(ppMaturingCandidatesV.getClientAndOrgId())
				.ppOrderCandidateId(ppMaturingCandidatesV.getPpOrderCandidateId())
				.maturingConfigId(ppMaturingCandidatesV.getMaturingConfigId())
				.maturingConfigLineId(ppMaturingCandidatesV.getMaturingConfigLineId())
				.productId(ppMaturingCandidatesV.getProductId())
				.warehouseId(ppMaturingCandidatesV.getWarehouseId())
				.productPlanningId(productPlanningId)
				.plantId(productPlanning.getPlantId())
				.qtyRequired(getQtyRequired(ppMaturingCandidatesV))
				.datePromised(ppMaturingCandidatesV.getDateStartSchedule())
				.dateStartSchedule(ppMaturingCandidatesV.getDateStartSchedule())
				.isMaturing(true)
				.simulated(false)
				.attributeSetInstanceId(ppMaturingCandidatesV.getAttributeSetInstanceId())
				.issueHuId(ppMaturingCandidatesV.getIssueHuId())
				.build());

		return ppMaturingCandidatesV.getPpOrderCandidateId() == null ? CrudOperationResult.CREATED : CrudOperationResult.UPDATED;
	}

	@NonNull
	private Quantity getQtyRequired(final @NonNull PPMaturingCandidateV ppMaturingCandidatesV)
	{
		final ProductBOMLineId productBOMLineId = productBOMsRepo.getLatestBOMByVersion(ppMaturingCandidatesV.getProductBOMVersionsId())
				.flatMap(bom -> productBOMsRepo.getBomLineByProductId(bom, ppMaturingCandidatesV.getIssueProductId()))
				.orElseThrow(() -> new AdempiereException("Cannot identify current BOM line for ProductBOMVersionsId=" + ppMaturingCandidatesV.getProductBOMVersionsId()));
		final ComputeQtyRequiredRequest computeQtyRequiredRequest = ComputeQtyRequiredRequest.builder()
				.issuedQty(ppMaturingCandidatesV.getQtyRequired())
				.productBOMLineId(productBOMLineId)
				.build();
		return orderBOMBL.getQtyRequired(computeQtyRequiredRequest);
	}

	public void deleteLines(@NonNull final PPOrderCandidateId ppOrderCandidateId)
	{
		ppOrderCandidateDAO.deleteLines(ppOrderCandidateId);
	}

	public void setWorkstationId(@NonNull final ImmutableSet<PPOrderCandidateId> ppOrderCandidateIds, @Nullable final ResourceId workstationId)
	{
		final ImmutableList<I_PP_Order_Candidate> candidates = ppOrderCandidateDAO.getByIds(ppOrderCandidateIds);
		for (final I_PP_Order_Candidate candidate : candidates)
		{
			if (candidate.isProcessed() || candidate.isClosed() || !candidate.isActive())
			{
				continue;
			}

			candidate.setWorkStation_ID(ResourceId.toRepoId(workstationId));
			ppOrderCandidateDAO.save(candidate);
		}
	}
}