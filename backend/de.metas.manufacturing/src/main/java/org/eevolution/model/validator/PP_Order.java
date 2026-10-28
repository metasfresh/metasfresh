package org.eevolution.model.validator;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.order.IOrderBL;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderAndBOMLineId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMLineId;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Interceptor(I_PP_Order.class)
public class PP_Order
{
	private static final String DYNATTR_Voiding = PP_Order.class.getName() + "#voiding";

	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IPPOrderRoutingRepository ppOrderRoutingRepository = Services.get(IPPOrderRoutingRepository.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IPPOrderCostBL orderCostsService = Services.get(IPPOrderCostBL.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	private final PPOrderPojoConverter ppOrderConverter;
	private final PostMaterialEventService materialEventService;
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	private final IPPOrderBOMBL ppOrderBOMBL;
	private final ProductBOMVersionsDAO bomVersionsDAO;

	public PP_Order(
			@NonNull final PPOrderPojoConverter ppOrderConverter,
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final IPPOrderBOMBL ppOrderBOMBL,
			@NonNull final ProductBOMVersionsDAO bomVersionsDAO)
	{
		this.ppOrderConverter = ppOrderConverter;
		this.materialEventService = materialEventService;
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.ppOrderBOMBL = ppOrderBOMBL;
		this.bomVersionsDAO = bomVersionsDAO;
	}

	@Init
	public void registerCallouts()
	{
		final IProgramaticCalloutProvider calloutsRegistry = Services.get(IProgramaticCalloutProvider.class);
		calloutsRegistry.registerAnnotatedCallout(new org.eevolution.callout.PP_Order(documentNoBuilderFactory, bomVersionsDAO));

		final ITabCalloutFactory tabCalloutRegistry = Services.get(ITabCalloutFactory.class);
		tabCalloutRegistry.registerTabCalloutForTable(I_PP_Order.Table_Name, org.eevolution.callout.PP_Order_TabCallout.class);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(
			final I_PP_Order ppOrder,
			final ModelChangeType changeType)
	{
		//
		// If UOM not filled, get it from Product
		if (ppOrder.getC_UOM_ID() <= 0)
		{
			final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
			final UomId uomId = productBL.getStockUOMId(productId);
			ppOrder.setC_UOM_ID(uomId.getRepoId());
		}

		//
		// If DateFinishSchedule is not filled, use DatePromised
		if (ppOrder.getDateFinishSchedule() == null)
		{
			ppOrder.setDateFinishSchedule(ppOrder.getDatePromised());
		}

		//
		// If Warehouse changed or Locator was never set, set it now
		if (ppOrder.getM_Locator_ID() <= 0 || InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Warehouse_ID))
		{
			final WarehouseId warehouseId = WarehouseId.ofRepoId(ppOrder.getM_Warehouse_ID());
			final LocatorId locatorId = warehouseBL.getOrCreateDefaultLocatorId(warehouseId);
			ppOrder.setM_Locator_ID(locatorId.getRepoId());
		}

		//
		// Set QtyBatchSize and QtyBatchs
		ppOrderBL.updateQtyBatchs(ppOrder, false);

		//
		// Set BPartner if not set
		if (ppOrder.getC_OrderLine_ID() > 0 && ppOrder.getC_BPartner_ID() <= 0)
		{
			final int bpartnerId = ppOrder.getC_OrderLine().getC_BPartner_ID();
			ppOrder.setC_BPartner_ID(bpartnerId);
		}

		//
		// Set PreparationDate from linked Sales Order, if not set (08181)
		if (ppOrder.getPreparationDate() == null && !ppOrder.isProcessed() && ppOrder.getC_OrderLine_ID() > 0)
		{
			final Timestamp preparationDate = ppOrder.getC_OrderLine().getC_Order().getPreparationDate();
			ppOrder.setPreparationDate(preparationDate);
		}

		//
		// Set ASI from OrderLine if not set (08074)
		if (ppOrder.getM_AttributeSetInstance_ID() <= 0
				&& ppOrder.getC_OrderLine_ID() > 0
				&& ppOrder.getC_OrderLine().getM_AttributeSetInstance_ID() > 0)
		{
			final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(ppOrder.getC_OrderLine().getM_AttributeSetInstance_ID());
			final AttributeSetInstanceId asiIdCopy = asiBL.copy(asiId);
			ppOrder.setM_AttributeSetInstance_ID(asiIdCopy.getRepoId());
		}

		//
		// Set project from OrderLine if not set
		if ((changeType.isNew() || InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_C_OrderLine_ID))
				&& ppOrder.getC_OrderLine_ID() > 0)
		{
			final OrderLineId orderLineId = OrderLineId.ofRepoId(ppOrder.getC_OrderLine_ID());
			final ProjectId projectId = orderBL.getProjectIdOrNull(orderLineId);
			ppOrder.setC_Project_ID(ProjectId.toRepoId(projectId));
		}

		//
		// Warehouse/Locator changed => update Order BOM Lines
		if (InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Warehouse_ID)
				|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Locator_ID)
				|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_AD_Org_ID))
		{
			if (ppOrder.getPP_Order_ID() > 0)
			{
				ppOrderBL.updateBOMOrderLinesWarehouseAndLocator(ppOrder);
			}
		}

		//
		// DocTypeTarget:
		if (ppOrder.getC_DocTypeTarget_ID() <= 0)
		{
			throw new FillMandatoryException(I_PP_Order.COLUMNNAME_C_DocTypeTarget_ID);
		}

		//
		// DocType: OrderType
		if (changeType.isNew()
				|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_C_DocType_ID))
		{
			final I_C_DocType docType = DocTypeId.optionalOfRepoId(ppOrder.getC_DocType_ID())
					.map(docTypeBL::getById)
					.orElse(null);
			if (docType != null)
			{
				ppOrder.setOrderType(docType.getDocSubType());
				ppOrder.setDocBaseType(docType.getDocBaseType());
			}
			else
			{
				ppOrder.setOrderType(null);
				ppOrder.setDocBaseType(null);
			}
		}

		if (changeType.isNew() || InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_CanBeExportedFrom))
		{
			ppOrderBL.updateCanBeExportedAfter(ppOrder);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void createWorkFlowAndBom(final I_PP_Order ppOrderRecord)
	{
		createWorkflowAndBOM(ppOrderRecord);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = {
					I_PP_Order.COLUMNNAME_QtyEntered,
					I_PP_Order.COLUMNNAME_DateStartSchedule,
					I_PP_Order.COLUMNNAME_AD_Workflow_ID,
					I_PP_Order.COLUMNNAME_PP_Product_BOM_ID })
	public void updateAndPostEventOnQtyEnteredChange(final I_PP_Order ppOrderRecord)
	{
		if (ppOrderBL.isSomethingProcessed(ppOrderRecord))
		{
			throw new LiberoException("Cannot quantity is not allowed because there is something already processed on this order"); // TODO: trl
		}

		final PPOrderChangedEventFactory eventFactory = PPOrderChangedEventFactory.newWithPPOrderBeforeChange(ppOrderConverter, ppOrderRecord);

		final PPOrderId orderId = PPOrderId.ofRepoId(ppOrderRecord.getPP_Order_ID());

		// When voiding, QtyEntered is set to 0; the distribution supply is not moved then, but handled as before (deleted, or rejected if already processed).
		final boolean voiding = isVoiding(ppOrderRecord);
		InterfaceWrapperHelper.setDynAttribute(ppOrderRecord, DYNATTR_Voiding, null);

		// the move needs a trx: the records are re-pointed after the old BOM lines were deleted, which is FK-safe only because the FKs are deferred
		final ITrx trx = trxManager.get(InterfaceWrapperHelper.getTrxName(ppOrderRecord), OnTrxMissingPolicy.ReturnTrxNone);
		final boolean moveDistributionSupply = !voiding
				&& InterfaceWrapperHelper.isValueChanged(ppOrderRecord, I_PP_Order.COLUMNNAME_QtyEntered)
				&& !trxManager.isNull(trx); // OnTrxMissingPolicy.ReturnTrxNone returns ITrx.TRX_None, which is null
		if (moveDistributionSupply)
		{
			final DistributionSupplyMover distributionSupplyMover = new DistributionSupplyMover(orderId);
			distributionSupplyMover.collect();

			setMovingDistributionSupply(orderId, trx, true);
			try
			{
				deleteWorkflowAndBOM(orderId);
			}
			finally
			{
				setMovingDistributionSupply(orderId, trx, false);
			}
			createWorkflowAndBOM(ppOrderRecord);

			distributionSupplyMover.moveToRecreatedBOMLines();
		}
		else
		{
			deleteWorkflowAndBOM(orderId);
			createWorkflowAndBOM(ppOrderRecord);
		}

		final PPOrderChangedEvent event = eventFactory.inspectPPOrderAfterChange();

		materialEventService.enqueueEventAfterNextCommit(event);
	}

	/**
	 * Marks the PP_Order as being voided: voiding sets QtyEntered=0, which must not move the distribution supply to the re-created BOM lines.
	 */
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_VOID)
	public void markVoiding(@NonNull final I_PP_Order ppOrderRecord)
	{
		InterfaceWrapperHelper.setDynAttribute(ppOrderRecord, DYNATTR_Voiding, Boolean.TRUE);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_VOID)
	public void unmarkVoiding(@NonNull final I_PP_Order ppOrderRecord)
	{
		InterfaceWrapperHelper.setDynAttribute(ppOrderRecord, DYNATTR_Voiding, null);
	}

	private static boolean isVoiding(@NonNull final I_PP_Order ppOrderRecord)
	{
		return Boolean.TRUE.equals(InterfaceWrapperHelper.getDynAttribute(ppOrderRecord, DYNATTR_Voiding));
	}

	private static String movingDistributionSupplyTrxProperty(@NonNull final PPOrderId ppOrderId)
	{
		return PP_Order.class.getName() + "#movingDistributionSupply#" + ppOrderId.getRepoId();
	}

	private static void setMovingDistributionSupply(@NonNull final PPOrderId ppOrderId, @NonNull final ITrx trx, final boolean moving)
	{
		trx.setProperty(movingDistributionSupplyTrxProperty(ppOrderId), moving ? Boolean.TRUE : null);
	}

	/**
	 * @return true while {@link #updateAndPostEventOnQtyEnteredChange(I_PP_Order)} deletes the BOM lines of the given PP_Order in order to re-create them,
	 * i.e. the distribution supply of the deleted lines is not to be deleted, but re-pointed to the re-created lines. Always false without a transaction (e.g. the in-memory layer of plain JUnit tests).
	 */
	static boolean isMovingDistributionSupply(@NonNull final PPOrderId ppOrderId, @Nullable final String trxName)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.get(trxName, OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isNull(trx)) // OnTrxMissingPolicy.ReturnTrxNone returns ITrx.TRX_None, which is null
		{
			return false;
		}
		return Boolean.TRUE.equals(trx.getProperty(movingDistributionSupplyTrxProperty(ppOrderId)));
	}

	/**
	 * Re-points the distribution supply (MD_Candidate distribution details, DD_Order_Candidates, DD_Orders) of the BOM lines of a PP_Order
	 * to the BOM lines which are re-created for the same PP_Product_BOMLine, following the move pattern of {@code PPOrderCandidateListeners}.
	 */
	private final class DistributionSupplyMover
	{
		private final PPOrderId ppOrderId;
		private final List<OldBOMLine> oldBOMLines = new ArrayList<>();

		private final DDOrderCandidateRepository ddOrderCandidateRepository = SpringContextHolder.instance.getBean(DDOrderCandidateRepository.class);
		private final DDOrderLowLevelDAO ddOrderLowLevelDAO = SpringContextHolder.instance.getBean(DDOrderLowLevelDAO.class);
		private final CandidateRepositoryRetrieval candidateRepositoryRetrieval = SpringContextHolder.instance.getBean(CandidateRepositoryRetrieval.class);
		private final CandidateRepositoryWriteService candidateRepositoryWriteService = SpringContextHolder.instance.getBean(CandidateRepositoryWriteService.class);

		private DistributionSupplyMover(@NonNull final PPOrderId ppOrderId)
		{
			this.ppOrderId = ppOrderId;
		}

		private final class OldBOMLine
		{
			private final PPOrderBOMLineId bomLineId;
			@Nullable private final ProductBOMLineId productBOMLineId;
			private final ImmutableSet<CandidateId> mdCandidateIds;
			private final ImmutableSet<DDOrderCandidateId> ddOrderCandidateIds;
			private final ImmutableSet<DDOrderId> ddOrderIds;

			private OldBOMLine(@NonNull final PPOrderBOMLineId bomLineId, @Nullable final ProductBOMLineId productBOMLineId)
			{
				this.bomLineId = bomLineId;
				this.productBOMLineId = productBOMLineId;
				this.mdCandidateIds = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(
								CandidatesQuery.builder()
										.businessCase(CandidateBusinessCase.DISTRIBUTION)
										.distributionDetailsQuery(DistributionDetailsQuery.builder().ppOrderBOMLineId(bomLineId).build())
										.build())
						.stream()
						.map(Candidate::getId)
						.collect(ImmutableSet.toImmutableSet());
				this.ddOrderCandidateIds = ddOrderCandidateRepository.list(DDOrderCandidateQuery.builder().ppOrderBOMLineId(bomLineId).build())
						.stream()
						.map(DDOrderCandidate::getIdNotNull)
						.collect(ImmutableSet.toImmutableSet());
				// only the DD_Orders of this BOM line; a DD_Order without or with another BOM line (e.g. shared across PP_Orders) stays untouched
				this.ddOrderIds = ddOrderLowLevelDAO.getIdsByForwardPPOrderBOMLineId(bomLineId);
			}
		}

		/**
		 * To be called before the BOM lines are deleted.
		 */
		void collect()
		{
			for (final I_PP_Order_BOMLine line : ppOrderBOMDAO.retrieveOrderBOMLines(ppOrderId))
			{
				oldBOMLines.add(new OldBOMLine(
						PPOrderBOMLineId.ofRepoId(line.getPP_Order_BOMLine_ID()),
						ProductBOMLineId.ofRepoIdOrNull(line.getPP_Product_BOMLine_ID())));
			}
		}

		/**
		 * To be called after the BOM lines were re-created.
		 * <p>
		 * Several lines may share one PP_Product_BOMLine (e.g. the same phantom exploded twice); the n-th old line of a PP_Product_BOMLine
		 * is paired with the n-th new line of it. An old line without a counterpart is treated like a removed component.
		 */
		void moveToRecreatedBOMLines()
		{
			final Map<ProductBOMLineId, Deque<PPOrderBOMLineId>> newBOMLineIdsByProductBOMLine = new HashMap<>();
			for (final I_PP_Order_BOMLine line : ppOrderBOMDAO.retrieveOrderBOMLines(ppOrderId))
			{
				final ProductBOMLineId productBOMLineId = ProductBOMLineId.ofRepoIdOrNull(line.getPP_Product_BOMLine_ID());
				if (productBOMLineId != null)
				{
					newBOMLineIdsByProductBOMLine
							.computeIfAbsent(productBOMLineId, k -> new ArrayDeque<>())
							.add(PPOrderBOMLineId.ofRepoId(line.getPP_Order_BOMLine_ID()));
				}
			}

			for (final OldBOMLine oldBOMLine : oldBOMLines)
			{
				final Deque<PPOrderBOMLineId> candidates = oldBOMLine.productBOMLineId != null ? newBOMLineIdsByProductBOMLine.get(oldBOMLine.productBOMLineId) : null;
				final PPOrderBOMLineId newBOMLineId = candidates != null ? candidates.pollFirst() : null;
				if (newBOMLineId != null)
				{
					moveTo(oldBOMLine, newBOMLineId);
				}
				else
				{
					deleteDDOrderCandidates(oldBOMLine);
				}
			}
		}

		private void moveTo(final OldBOMLine oldBOMLine, final PPOrderBOMLineId newBOMLineId)
		{
			final PPOrderAndBOMLineId newPPOrderAndBOMLineId = PPOrderAndBOMLineId.of(ppOrderId, newBOMLineId);

			for (final CandidateId mdCandidateId : oldBOMLine.mdCandidateIds)
			{
				final Candidate candidate = candidateRepositoryRetrieval.retrieveById(mdCandidateId); // fails, naming the id, if it does not exist anymore
				final DistributionDetail distributionDetail = candidate.getBusinessCaseDetail(DistributionDetail.class)
						.orElseThrow(() -> new AdempiereException("No DistributionDetail found for " + candidate));
				candidateRepositoryWriteService.updateCandidateById(candidate.withBusinessCaseDetail(distributionDetail.toBuilder()
						.forwardPPOrderRef(PPOrderRef.withPPOrderAndBOMLineId(distributionDetail.getForwardPPOrderRef(), newPPOrderAndBOMLineId))
						.build()));
			}

			assertDDOrderCandidatesExist(oldBOMLine);
			for (final DDOrderCandidateId ddOrderCandidateId : oldBOMLine.ddOrderCandidateIds)
			{
				ddOrderCandidateRepository.updateByQuery(
						DDOrderCandidateQuery.builder().ddOrderCandidateId(ddOrderCandidateId).build(),
						candidate -> candidate.toBuilder()
								.forwardPPOrderRef(PPOrderRef.withPPOrderAndBOMLineId(candidate.getForwardPPOrderRef(), newPPOrderAndBOMLineId))
								.build());
			}

			final int updatedDDOrders = ddOrderLowLevelDAO.updateForwardPPOrderBOMLineByIds(oldBOMLine.ddOrderIds, newBOMLineId);
			if (updatedDDOrders != oldBOMLine.ddOrderIds.size())
			{
				throw new AdempiereException("Not all DD_Orders " + oldBOMLine.ddOrderIds + " of the old BOM line " + oldBOMLine.bomLineId + " exist anymore; cannot move them to the BOM line " + newBOMLineId);
			}
		}

		private void assertDDOrderCandidatesExist(final OldBOMLine oldBOMLine)
		{
			final Set<DDOrderCandidateId> existingIds = ddOrderCandidateRepository.getByIds(oldBOMLine.ddOrderCandidateIds)
					.stream()
					.map(DDOrderCandidate::getIdNotNull)
					.collect(ImmutableSet.toImmutableSet());
			final Set<DDOrderCandidateId> missingIds = Sets.difference(oldBOMLine.ddOrderCandidateIds, existingIds);
			if (!missingIds.isEmpty())
			{
				throw new AdempiereException("DD_Order_Candidates " + missingIds + " of the old BOM line " + oldBOMLine.bomLineId + " do not exist anymore");
			}
		}

		/**
		 * The BOM line has no successor (component removed from the BOM): same as before, unprocessed DD_Order_Candidates are deleted, a processed one rejects the change.
		 */
		private void deleteDDOrderCandidates(final OldBOMLine oldBOMLine)
		{
			for (final DDOrderCandidateId ddOrderCandidateId : oldBOMLine.ddOrderCandidateIds)
			{
				try
				{
					ddOrderCandidateRepository.delete(DDOrderCandidateQuery.builder().ddOrderCandidateId(ddOrderCandidateId).build());
				}
				catch (final AdempiereException e)
				{
					throw new AdempiereException(PP_Order_BOMLine.DD_ORDER_CANDIDATE_ALREADY_PROCESSED_CHANGES_NOT_ALLOWED, e);
				}
			}
		}
	}

	private void deleteWorkflowAndBOM(final PPOrderId orderId)
	{
		ppOrderRoutingRepository.deleteByOrderId(orderId);
		ppOrderBOMDAO.deleteByOrderId(orderId);
	}

	private void createWorkflowAndBOM(final I_PP_Order ppOrder)
	{
		ppOrderBL.createOrderRouting(ppOrder);
		ppOrderBOMBL.createOrderBOMAndLines(ppOrder);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_PP_Order ppOrder)
	{
		//
		// Delete depending records
		final String docStatus = ppOrder.getDocStatus();
		if (X_PP_Order.DOCSTATUS_Drafted.equals(docStatus)
				|| X_PP_Order.DOCSTATUS_InProgress.equals(docStatus))
		{
			final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());

			orderCostsService.deleteByOrderId(ppOrderId);
			deleteWorkflowAndBOM(ppOrderId);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = { I_PP_Order.COLUMNNAME_M_Product_ID, I_PP_Order.COLUMNNAME_PP_Product_BOM_ID })
	public void validateBOMAndProduct(@NonNull final I_PP_Order ppOrder)
	{
		final ProductBOMId bomId = ProductBOMId.ofRepoId(ppOrder.getPP_Product_BOM_ID());

		final ProductId productIdOfBOM = productBOMDAO.getBOMProductId(bomId);

		if (ppOrder.getM_Product_ID() != productIdOfBOM.getRepoId())
		{
			throw new AdempiereException(AdMessageKey.of("PP_Order_BOM_Doesnt_Match"))
					.markAsUserValidationError()
					.appendParametersToMessage()
					.setParameter("PP_Order.M_Product_ID", ppOrder.getM_Product_ID())
					.setParameter("PP_Order.PP_Product_BOM_ID.M_Product_ID", productIdOfBOM.getRepoId());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setSeqNo(final I_PP_Order ppOrder)
	{
		if (ppOrder.getSeqNo() <= 0)
		{
			ppOrder.setSeqNo(ppOrderDAO.getNextSeqNoPerDateStartSchedule(ppOrder).toInt());
		}
	}
}
