package de.metas.manufacturing.job.service.commands.issue_what_was_received;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewCUsRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer.ProcessIssueCandidatesPolicy;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleCreateRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.RawMaterialsIssueLine;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.material.planning.pporder.DraftPPOrderQuantities;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.RawMaterialsIssueStrategy;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class IssueWhatWasReceivedCommand
{
	private static final AdMessageKey NOTHING_WAS_RECEIVED_YET = AdMessageKey.of("de.metas.manufacturing.NOTHING_WAS_RECEIVED_YET");

	// Services
	@NonNull private static final Logger logger = LogManager.getLogger(IssueWhatWasReceivedCommand.class);
	@NonNull private final ILoggable warn = Loggables.withLogger(logger, Level.WARN);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	@NonNull private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	@NonNull private final IHUPPOrderQtyBL huPPOrderQtyBL = Services.get(IHUPPOrderQtyBL.class);
	@NonNull private final IHandlingUnitsDAO huDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final PPOrderIssueScheduleService issueScheduleService;
	@NonNull private final ManufacturingJobService jobService;
	@NonNull private final PPOrderSourceHUService ppOrderSourceHUService;
	@NonNull private final SourceHUsService sourceHUsService;

	//
	// Params
	@NonNull private final PPOrderId ppOrderId;
	@NonNull private final RawMaterialsIssueStrategy issueStrategy;

	//
	// State
	@Nullable private ManufacturingJob _job; // lazy
	@Nullable private ImmutableMap<PPOrderBOMLineId, I_PP_Order_BOMLine> _bomLineRecordsById; // lazy
	@Nullable private DraftPPOrderQuantities _draftPPOrderQuantities; // lazy
	@Nullable private SourceHUsCollectionProvider _sourceHUsCollectionProvider; // lazy

	@Builder
	private IssueWhatWasReceivedCommand(
			@NonNull final PPOrderIssueScheduleService issueScheduleService,
			@NonNull final ManufacturingJobService jobService,
			@NonNull final PPOrderSourceHUService ppOrderSourceHUService,
			@NonNull final SourceHUsService sourceHUsService,
			//
			@Nullable final RawMaterialsIssueStrategy issueStrategy,
			@Nullable final PPOrderId ppOrderId,
			@Nullable final ManufacturingJob job)
	{
		this.issueScheduleService = issueScheduleService;
		this.jobService = jobService;
		this.ppOrderSourceHUService = ppOrderSourceHUService;
		this.sourceHUsService = sourceHUsService;
		this.issueStrategy = issueStrategy != null ? issueStrategy : RawMaterialsIssueStrategy.DEFAULT;

		if (job != null)
		{
			Check.assume(ppOrderId == null || PPOrderId.equals(job.getPpOrderId(), ppOrderId), "ppOrderId shall match job: {}", ppOrderId, job);
			this.ppOrderId = job.getPpOrderId();
			this._job = job;
		}
		else if (ppOrderId != null)
		{
			this.ppOrderId = ppOrderId;
			this._job = null; // to be loaded
		}
		else
		{
			throw new AdempiereException("Either ppOrderId or job shall be given");
		}
	}

	@NonNull
	public ManufacturingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	@NonNull
	private ManufacturingJob execute0()
	{
		final ManufacturingJob job = getJob();

		final ImmutableList<RawMaterialsIssueLine> lines = job.streamRawMaterialsIssueLines()
				.filter(RawMaterialsIssueLine::isIssueOnlyForReceived)
				.collect(ImmutableList.toImmutableList());
		if (lines.isEmpty())
		{
			// no lines to auto-issue what was received
			return job;
		}

		lines.forEach(this::issueForWhatWasReceived);

		return jobService.recomputeQtyToIssueForSteps(ppOrderId);
	}

	private SourceHUsCollectionProvider getSourceHUsCollectionProvider()
	{
		if (_sourceHUsCollectionProvider == null)
		{
			_sourceHUsCollectionProvider = SourceHUsCollectionProvider.builder()
					.handlingUnitsDAO(huDAO)
					.ppOrderBOMBL(ppOrderBOMBL)
					.ppOrderSourceHUService(ppOrderSourceHUService)
					.sourceHUsService(sourceHUsService)
					//
					.ppOrderId(ppOrderId)
					.issueStrategy(issueStrategy)
					.manufacturingWarehouseId(getJob().getWarehouseId())
					//
					.build();
		}
		return _sourceHUsCollectionProvider;
	}

	private ManufacturingJob getJob()
	{
		if (_job == null)
		{
			_job = jobService.getJobById(ppOrderId);
		}
		return _job;
	}

	private DraftPPOrderQuantities getDraftPPOrderQuantities()
	{
		if (_draftPPOrderQuantities == null)
		{
			_draftPPOrderQuantities = huPPOrderQtyBL.getPPOrderQuantities(ppOrderId, false);
		}
		return _draftPPOrderQuantities;
	}

	private void issueForWhatWasReceived(@NonNull final RawMaterialsIssueLine rawMaterialsIssueLine)
	{
		if (rawMaterialsIssueLine.getQtyToIssue().signum() <= 0)
		{
			logger.debug("Skipping issue line due to qtyToIssue <= 0: {}", rawMaterialsIssueLine);
			return;
		}

		// issue exact qty for what was received, don't cap it as rawMaterialsIssueLine.getQtyToIssue() because if, due to packing, a finished product may be delivered in greater qty than ordered
		// ex: product A packed in x10 TUs, QtyOrdered=3, QtyReceived=10. In that case, issue for the whole received qty (10), not just for the ordered qty.
		final Quantity quantityToIssueForWhatWasReceived = computeQtyToIssueBasedOnFinishedGoodReceipt(rawMaterialsIssueLine);
		if (quantityToIssueForWhatWasReceived.signum() <= 0)
		{
			logger.debug("Skipping issue line due to calculated qtyToIssue <= 0: {}, quantityToIssueForWhatWasReceived={}", rawMaterialsIssueLine, quantityToIssueForWhatWasReceived);
			return;
		}

		final List<I_M_HU> extractedCUs = splitOutCUsFromSourceHUs(rawMaterialsIssueLine.getProductId(), quantityToIssueForWhatWasReceived);
		if (extractedCUs.isEmpty())
		{
			warn.addLog("Skipping issue line as there are no source HUs matching the product: {}", rawMaterialsIssueLine);
			return;
		}

		huPPOrderBL.createIssueProducer(ppOrderId)
				.considerIssueMethodForQtyToIssueCalculation(false) // issue exactly the CUs we split
				.processCandidates(ProcessIssueCandidatesPolicy.ALWAYS)
				.createIssues(extractedCUs)
				.forEach(this::createIssueSchedule);
	}

	private Quantity computeQtyToIssueBasedOnFinishedGoodReceipt(@NonNull final RawMaterialsIssueLine rawMaterialsIssueLine)
	{
		return ppOrderBOMBL.computeQtyToIssueBasedOnFinishedGoodReceipt(
				getBomLine(rawMaterialsIssueLine),
				rawMaterialsIssueLine.getQtyToIssue().getUOM(),
				getDraftPPOrderQuantities()
		);
	}

	@NonNull
	private I_PP_Order_BOMLine getBomLine(@NonNull final RawMaterialsIssueLine issueLine)
	{
		if (_bomLineRecordsById == null)
		{
			_bomLineRecordsById = Maps.uniqueIndex(ppOrderBOMBL.getOrderBOMLines(ppOrderId), IssueWhatWasReceivedCommand::extractOrderBOMLineId);
		}

		final I_PP_Order_BOMLine bomLineRecord = _bomLineRecordsById.get(issueLine.getOrderBOMLineId());
		if (bomLineRecord == null)
		{
			throw new AdempiereException("No PP_Order_BOMLine found for " + issueLine);
		}

		return bomLineRecord;
	}

	@NonNull
	private static PPOrderBOMLineId extractOrderBOMLineId(@NonNull final I_PP_Order_BOMLine record)
	{
		return PPOrderBOMLineId.ofRepoId(record.getPP_Order_BOMLine_ID());
	}

	private List<I_M_HU> splitOutCUsFromSourceHUs(@NonNull final ProductId productId, @NonNull final Quantity qtyCU)
	{
		final SourceHUsCollection sourceHUsCollection = getSourceHUsCollectionProvider().getByProductId(productId);
		if (sourceHUsCollection.isEmpty())
		{
			return ImmutableList.of();
		}

		return HUTransformService.builder()
				.emptyHUListener(
						EmptyHUListener.doBeforeDestroyed(hu -> sourceHUsCollection
										.getSourceHU(HuId.ofRepoId(hu.getM_HU_ID()))
										.ifPresent(sourceHUsService::snapshotSourceHU),
								"Create snapshot of source-HU before it is destroyed")
				)
				.build()
				.husToNewCUs(
						HUsToNewCUsRequest.builder()
								.sourceHUs(sourceHUsCollection.getHusThatAreFlaggedAsSource())
								.productId(productId)
								.qtyCU(qtyCU)
								.build()
				)
				.getNewCUs();
	}

	private void createIssueSchedule(final I_PP_Order_Qty ppOrderQtyRecord)
	{
		final Quantity qtyIssued = Quantitys.of(ppOrderQtyRecord.getQty(), UomId.ofRepoId(ppOrderQtyRecord.getC_UOM_ID()));

		issueScheduleService.createSchedule(
				PPOrderIssueScheduleCreateRequest.builder()
						.ppOrderId(ppOrderId)
						.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoId(ppOrderQtyRecord.getPP_Order_BOMLine_ID()))
						.seqNo(SeqNo.ofInt(0))
						.productId(ProductId.ofRepoId(ppOrderQtyRecord.getM_Product_ID()))
						.qtyToIssue(qtyIssued)
						.issueFromHUId(HuId.ofRepoId(ppOrderQtyRecord.getM_HU_ID()))
						.issueFromLocatorId(warehouseDAO.getLocatorIdByRepoId(ppOrderQtyRecord.getM_Locator_ID()))
						.isAlternativeIssue(true)
						.qtyIssued(qtyIssued)
						.build()
		);
	}
}
