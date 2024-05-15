package de.metas.handlingunits.pporder.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.impl.DocumentLUTUConfigurationManager;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueReceiptCandidatesProcessor;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.pporder.api.PPOrderIssueServiceProductRequest;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.manufacturing.generatedcomponents.ManufacturingComponentGeneratorService;
import de.metas.material.maturing.MaturingConfigLine;
import de.metas.material.maturing.MaturingConfigRepository;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.groups.WarehouseGroupAssignmentType;
import org.compiere.SpringContextHolder;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.dao.IPPOrderCandidateDAO;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class HUPPOrderBL implements IHUPPOrderBL
{
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUAttributesBL attributesBL = Services.get(IHUAttributesBL.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	private final SpringContextHolder.Lazy<HUQRCodesService> huqrCodesService = SpringContextHolder.lazyBean(HUQRCodesService.class);
	private final SpringContextHolder.Lazy<QRCodeConfigurationService> qrCodeConfigurationService = SpringContextHolder.lazyBean(QRCodeConfigurationService.class);
	private final SpringContextHolder.Lazy<MaturingConfigRepository> maturingConfigRepository = SpringContextHolder.lazyBean(MaturingConfigRepository.class);
	private final IPPOrderCandidateDAO ppOrderCandidateDAO = Services.get(IPPOrderCandidateDAO.class);

	@Override
	public I_PP_Order getById(@NonNull final PPOrderId ppOrderId)
	{
		return ppOrderDAO.getById(ppOrderId, I_PP_Order.class);
	}

	@Override
	public List<I_PP_Order> getByIds(@NonNull final Set<PPOrderId> ppOrderIds)
	{
		return ppOrderDAO.getByIds(ppOrderIds, I_PP_Order.class);
	}

	@Override
	public List<I_PP_Order> list(@NonNull final ManufacturingOrderQuery query)
	{
		return ppOrderDAO.streamManufacturingOrders(query)
				.map(ppOrder -> InterfaceWrapperHelper.create(ppOrder, I_PP_Order.class))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public ImmutableSet<PPOrderId> getManufacturingOrderIds(@NonNull final ManufacturingOrderQuery query)
	{
		return ppOrderDAO.getManufacturingOrderIds(query);
	}

	@Override
	public boolean anyMatch(@NonNull final ManufacturingOrderQuery query)
	{
		return ppOrderDAO.anyMatch(query);
	}

	@Override
	public IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(@NonNull final org.eevolution.model.I_PP_Order ppOrder)
	{
		final de.metas.handlingunits.model.I_PP_Order documentLine = InterfaceWrapperHelper.create(ppOrder, de.metas.handlingunits.model.I_PP_Order.class);
		return new DocumentLUTUConfigurationManager<>(documentLine, PPOrderDocumentLUTUConfigurationHandler.instance);
	}

	@Override
	public IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager(@NonNull final org.eevolution.model.I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final de.metas.handlingunits.model.I_PP_Order_BOMLine documentLine = InterfaceWrapperHelper.create(ppOrderBOMLine, de.metas.handlingunits.model.I_PP_Order_BOMLine.class);
		return new DocumentLUTUConfigurationManager<>(documentLine, PPOrderBOMLineDocumentLUTUConfigurationHandler.instance);
	}

	@Override
	public IAllocationSource createAllocationSourceForPPOrder(final I_PP_Order ppOrder)
	{
		final PPOrderProductStorage ppOrderProductStorage = new PPOrderProductStorage(ppOrder);
		return new GenericAllocationSourceDestination(
				ppOrderProductStorage,
				ppOrder // referenced model
		);
	}

	@Override
	public HUPPOrderIssueProducer createIssueProducer(@NonNull final PPOrderId ppOrderId)
	{
		return new HUPPOrderIssueProducer(ppOrderId);
	}

	@Override
	public IPPOrderReceiptHUProducer receivingMainProduct(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = getById(ppOrderId);
		return new CostCollectorCandidateFinishedGoodsHUProducer(ppOrder);
	}

	@Override
	public IPPOrderReceiptHUProducer receivingByOrCoProduct(@NonNull final PPOrderBOMLineId orderBOMLineId)
	{
		final I_PP_Order_BOMLine orderBOMLine = ppOrderBOMsRepo.getOrderBOMLineById(orderBOMLineId);
		return new CostCollectorCandidateCoProductHUProducer(orderBOMLine);
	}

	@Override
	public void issueServiceProduct(final PPOrderIssueServiceProductRequest request)
	{
		final ManufacturingComponentGeneratorService manufacturingComponentGeneratorService = SpringContextHolder.instance.getBean(ManufacturingComponentGeneratorService.class);

		PPOrderIssueServiceProductCommand.builder()
				.manufacturingComponentGeneratorService(manufacturingComponentGeneratorService)
				.request(request)
				.build()
				.execute();
	}

	@Override
	public IHUQueryBuilder createHUsAvailableToIssueQuery(@NonNull final I_PP_Order_BOMLine ppOrderBomLine)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(ppOrderBomLine.getM_Warehouse_ID());
		final Set<WarehouseId> issueFromWarehouseIds = warehouseDAO.getWarehouseIdsOfSameGroup(warehouseId, WarehouseGroupAssignmentType.MANUFACTURING);

		final IHUQueryBuilder huQueryBuilder = handlingUnitsDAO
				.createHUQueryBuilder()
				.addOnlyInWarehouseIds(issueFromWarehouseIds)
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.setExcludeReserved()
				.setOnlyTopLevelHUs()
				.setNotEmptyStorageOnly()
				.onlyNotLocked();

		if (!ppOrderBomLine.isAllowIssuingAnyProduct())
		{
			final AttributeSetInstanceId expectedASI = AttributeSetInstanceId.ofRepoIdOrNone(ppOrderBomLine.getM_AttributeSetInstance_ID());
			final ImmutableAttributeSet storageRelevantAttributeSet = attributeSetInstanceBL.getImmutableAttributeSetById(expectedASI)
					.filterOnlyStorageRelevantAttributes();
			huQueryBuilder.addOnlyWithAttributes(storageRelevantAttributeSet);

			huQueryBuilder.addOnlyWithProductId(ProductId.ofRepoId(ppOrderBomLine.getM_Product_ID()));
		}

		return huQueryBuilder;
	}

	private static final ImmutableMultimap<PPOrderPlanningStatus, PPOrderPlanningStatus> fromPlanningStatus2toPlanningStatusAllowed = ImmutableMultimap.<PPOrderPlanningStatus, PPOrderPlanningStatus>builder()
			.put(PPOrderPlanningStatus.PLANNING, PPOrderPlanningStatus.REVIEW)
			.put(PPOrderPlanningStatus.PLANNING, PPOrderPlanningStatus.COMPLETE)
			.put(PPOrderPlanningStatus.REVIEW, PPOrderPlanningStatus.PLANNING)
			.put(PPOrderPlanningStatus.REVIEW, PPOrderPlanningStatus.COMPLETE)
			// .put(PPOrderPlanningStatus.COMPLETE, PPOrderPlanningStatus.PLANNING) // don't allow this transition unless https://github.com/metasfresh/metasfresh/issues/2708 is done
			.build();

	@Override
	public boolean canChangePlanningStatus(final PPOrderPlanningStatus fromPlanningStatus, final PPOrderPlanningStatus toPlanningStatus)
	{
		return fromPlanningStatus2toPlanningStatusAllowed.get(fromPlanningStatus).contains(toPlanningStatus);
	}

	@Override
	public void processPlanning(@NonNull final PPOrderId ppOrderId, @NonNull final PPOrderPlanningStatus targetPlanningStatus)
	{
		trxManager.runInThreadInheritedTrx(() -> {
			final I_PP_Order ppOrder = getById(ppOrderId);
			processPlanning(ppOrder, targetPlanningStatus, false);
		});
	}

	@Override
	public void processPlanning(@NonNull final Set<PPOrderId> ppOrderIds, @NonNull final PPOrderPlanningStatus targetPlanningStatus)
	{
		if (ppOrderIds.isEmpty())
		{
			return;
		}

		for (final I_PP_Order ppOrder : getByIds(ppOrderIds))
		{
			processPlanning(ppOrder, targetPlanningStatus, false);
		}
	}

	@Override
	public void processPlanning(@NonNull final I_PP_Order ppOrder, @NonNull final PPOrderPlanningStatus targetPlanningStatus, boolean doNotCloseOrder)
	{
		trxManager.assertThreadInheritedTrxExists();

		final PPOrderPlanningStatus planningStatus = PPOrderPlanningStatus.ofCode(ppOrder.getPlanningStatus());
		if (PPOrderPlanningStatus.equals(planningStatus, targetPlanningStatus))
		{
			//throw new AdempiereException("Already " + targetPlanningStatus);
			return; // already processed
		}
		if (!canChangePlanningStatus(planningStatus, targetPlanningStatus))
		{
			throw new AdempiereException("Cannot change planning status from " + planningStatus + " to " + targetPlanningStatus);
		}

		if (PPOrderPlanningStatus.PLANNING.equals(targetPlanningStatus))
		{
			// nothing
		}
		else if (PPOrderPlanningStatus.REVIEW.equals(targetPlanningStatus))
		{
			// nothing
		}
		else if (PPOrderPlanningStatus.COMPLETE.equals(targetPlanningStatus))
		{
			HUPPOrderIssueReceiptCandidatesProcessor.newInstance()
					.setCandidatesToProcessByPPOrderId(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
					.process();

			if (!doNotCloseOrder)
			{
				ppOrderBL.closeOrder(ppOrder);
				InterfaceWrapperHelper.refresh(ppOrder);
			}
		}
		else
		{
			throw new AdempiereException("Unknown target planning status: " + targetPlanningStatus);
		}

		//
		// Update ppOrder's planning status
		ppOrder.setPlanningStatus(targetPlanningStatus.getCode());
		ppOrderDAO.save(ppOrder);
	}

	@Override
	public void addAssignedHandlingUnits(@NonNull final I_PP_Order ppOrder, @NonNull final Collection<I_M_HU> hus)
	{
		huAssignmentBL.addAssignedHandlingUnits(ppOrder, hus);
	}

	@Override
	public void addAssignedHandlingUnits(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine, @NonNull final Collection<I_M_HU> hus)
	{
		huAssignmentBL.addAssignedHandlingUnits(ppOrderBOMLine, hus);
	}

	private static ImmutableSet<TableRecordReference> toRecordRefs(final Set<PPOrderId> ppOrderIds)
	{
		return ppOrderIds.stream().map(PPOrderId::toRecordRef).collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public void closeOrder(@NonNull final PPOrderId ppOrderId)
	{
		ppOrderBL.closeOrder(ppOrderId);
	}

	@Override
	public Stream<I_PP_Order> streamManufacturingOrders(final ManufacturingOrderQuery query)
	{
		return ppOrderBL.streamManufacturingOrders(query)
				.map(ppOrder -> InterfaceWrapperHelper.create(ppOrder, I_PP_Order.class));
	}

	@Override
	public void save(final org.eevolution.model.I_PP_Order ppOrder)
	{
		ppOrderBL.save(ppOrder);
	}

	@Override
	public List<HuId> retrieveAvailableToIssue(final I_PP_Order_BOMLine ppOrderBomLine)
	{
		final IHUQueryBuilder huIdsToAvailableToIssueQuery = createHUsAvailableToIssueQuery(ppOrderBomLine);

		return huIdsToAvailableToIssueQuery.createQuery()
				.listIds()
				.stream()
				.map(HuId::ofRepoId)
				.filter(this::isEligibleHuToIssue)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public void issueAndReceiveMaturingHUs(@NonNull final I_PP_Order ppOrder)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		final I_M_HU huToBeIssued = getMaturingHU(ppOrder);

		createIssueProducer(ppOrderId)
				.considerIssueMethodForQtyToIssueCalculation(false) // issue exactly the HUs selected for maturing
				.createIssues(ImmutableList.of(huToBeIssued));

		receiveMaturedHU(ppOrder, huToBeIssued);

		ppOrderBL.closeOrder(ppOrderId);
	}

	public boolean isAtLeastOneCandidateMaturing(@NonNull final List<I_PP_Order_Candidate> candidates)
	{
		return candidates
				.stream()
				.anyMatch(I_PP_Order_Candidate::isMaturing);
	}

	private boolean isEligibleHuToIssue(final HuId huId)
	{
		if (SourceHUsService.get().isHuOrAnyParentSourceHu(huId) || !handlingUnitsBL.isHUHierarchyCleared(huId))
		{
			return false;
		}

		return !huStatusBL.isStatusIssued(huId);
	}

	private boolean isHUEligibleForMaturing(
			@NonNull final I_M_HU hu,
			@NonNull final ProductId maturedProductId)
	{
		if (!hu.isActive() || !hu.isReserved() || !hu.isLocked() || !huStatusBL.isStatusActiveOrIssued(hu))
		{
			return false;
		}

		final LocatorId locatorId = warehouseDAO.getLocatorIdByRepoId(hu.getM_Locator_ID());
		if (locatorId == null)
		{
			return false;
		}

		final IHUProductStorage productStorage = handlingUnitsBL.getStorageFactory()
				.getSingleHUProductStorage(hu);

		final List<MaturingConfigLine> maturingConfigLines = maturingConfigRepository.get().getByFromProductId(productStorage.getProductId());

		if (maturingConfigLines.isEmpty())
		{
			return false;
		}

		final MaturingConfigLine maturingConfigLine = CollectionUtils.singleElement(maturingConfigLines);

		return productPlanningDAO.find(IProductPlanningDAO.ProductPlanningQuery.builder()
											   .productId(maturedProductId)
											   .warehouseId(locatorId.getWarehouseId())
											   .maturingConfigLineId(maturingConfigLine.getId())
											   .build())
				.map(ProductPlanning::isMatured)
				.orElse(false);
	}

	@NonNull
	private I_PP_Order_Candidate getSingleMaturingCandidate(@NonNull final PPOrderId ppOrderId)
	{
		final ImmutableList<I_PP_Order_Candidate> maturingCandidates = ppOrderCandidateDAO.getByOrderId(ppOrderId);
		if (!isAtLeastOneCandidateMaturing(maturingCandidates))
		{
			throw new AdempiereException("No maturing candidates found for PPOrderId!")
					.appendParametersToMessage()
					.setParameter("PPOrderId", ppOrderId);
		}

		return CollectionUtils.singleElement(maturingCandidates);
	}

	@NonNull
	private I_M_HU getMaturingHU(@NonNull final I_PP_Order ppOrder)
	{
		final I_PP_Order_Candidate maturingCandidate = getSingleMaturingCandidate(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()));
		final HuId maturingHUId = HuId.ofRepoId(maturingCandidate.getIssue_HU_ID());

		final I_M_HU maturingHU = handlingUnitsBL.getById(maturingHUId);

		final ProductId maturedProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());

		if (isHUEligibleForMaturing(maturingHU, maturedProductId))
		{
			throw new AdempiereException("Issue HU is not eligible for maturing !");
		}

		return maturingHU;
	}

	private void receiveMaturedHU(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final I_M_HU huToBeIssued)
	{
		final LocatorId locatorId = LocatorId.ofRepoId(ppOrder.getM_Warehouse_ID(), ppOrder.getM_Locator_ID());

		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		final I_M_HU receivedHu = receivingMainProduct(ppOrderId)
				.locatorId(locatorId)
				.receiveVHU(Quantitys.of(ppOrder.getQtyOrdered(), UomId.ofRepoId(ppOrder.getC_UOM_ID())));

		attributesBL.transferAttributesForSingleProductHUs(huToBeIssued, receivedHu);
		attributesBL.updateHUAttribute(HuId.ofRepoId(receivedHu.getM_HU_ID()), AttributeConstants.ProductionDate, SystemTime.asTimestamp());

		final HUQRCode huqrCode = huqrCodesService.get().getQRCodeByHuId(HuId.ofRepoId(huToBeIssued.getM_HU_ID()));
		huqrCodesService.get().removeAssignment(huqrCode, ImmutableSet.of(HuId.ofRepoId(huToBeIssued.getM_HU_ID())));

		final boolean ensureSingleQrAssignment = qrCodeConfigurationService.get().isOneQrCodeForAggregatedHUsEnabledFor(receivedHu);
		huqrCodesService.get().assign(huqrCode, HuId.ofRepoId(receivedHu.getM_HU_ID()), ensureSingleQrAssignment);

		processPlanning(ppOrderId, PPOrderPlanningStatus.COMPLETE);
	}
}
