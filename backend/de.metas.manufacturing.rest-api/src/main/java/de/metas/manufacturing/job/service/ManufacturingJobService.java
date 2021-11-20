package de.metas.manufacturing.job.service;

import de.metas.dao.ValueRestriction;
import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleProcessRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.manufacturing.job.model.CurrentReceivingHU;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLineId;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.model.ManufacturingJobReference;
import de.metas.manufacturing.job.service.commands.create_job.ManufacturingJobCreateCommand;
import de.metas.manufacturing.workflows_api.activity_handlers.json.JsonAggregateToLU;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ManufacturingJobService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUPPOrderBL ppOrderBL;
	private final IPPOrderBOMBL ppOrderBOMBL;
	private final PPOrderIssueScheduleService ppOrderIssueScheduleService;
	private final HUReservationService huReservationService;
	private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;

	public ManufacturingJobService(
			final PPOrderIssueScheduleService ppOrderIssueScheduleService,
			final HUReservationService huReservationService)
	{
		this.ppOrderIssueScheduleService = ppOrderIssueScheduleService;
		this.huReservationService = huReservationService;

		this.loadingAndSavingSupportServices = ManufacturingJobLoaderAndSaverSupportingServices.builder()
				.orgDAO(Services.get(IOrgDAO.class))
				.warehouseBL(Services.get(IWarehouseBL.class))
				.productBL(Services.get(IProductBL.class))
				.ppOrderBL(ppOrderBL = Services.get(IHUPPOrderBL.class))
				.ppOrderBOMBL(ppOrderBOMBL = Services.get(IPPOrderBOMBL.class))
				.ppOrderRoutingRepository(Services.get(IPPOrderRoutingRepository.class))
				.ppOrderIssueScheduleService(ppOrderIssueScheduleService)
				.build();
	}

	public ManufacturingJob getJobById(final PPOrderId ppOrderId) {return newLoader().load(ppOrderId);}

	@NonNull
	private ManufacturingJobLoaderAndSaver newLoader() {return new ManufacturingJobLoaderAndSaver(loadingAndSavingSupportServices);}

	@NonNull
	private ManufacturingJobLoaderAndSaver newSaver() {return new ManufacturingJobLoaderAndSaver(loadingAndSavingSupportServices);}

	public Stream<ManufacturingJobReference> streamJobReferencesForUser(
			final @NonNull UserId responsibleId,
			final @NonNull QueryLimit suggestedLimit)
	{
		final ArrayList<ManufacturingJobReference> result = new ArrayList<>();

		//
		// Already started jobs
		streamAlreadyStartedJobs(responsibleId)
				.forEach(result::add);

		//
		// New possible jobs
		if (!suggestedLimit.isLimitHitOrExceeded(result))
		{
			streamJobCandidatesToCreate()
					.limit(suggestedLimit.minusSizeOf(result).toIntOr(Integer.MAX_VALUE))
					.forEach(result::add);
		}

		return result.stream();
	}

	private Stream<ManufacturingJobReference> streamAlreadyStartedJobs(@NonNull final UserId responsibleId)
	{
		return ppOrderBL.streamManufacturingOrders(ManufacturingOrderQuery.builder()
						.onlyCompleted(true)
						.responsibleId(ValueRestriction.equalsTo(responsibleId))
						.build())
				.map(ppOrder -> toManufacturingJobReference(ppOrder, true));
	}

	private Stream<ManufacturingJobReference> streamJobCandidatesToCreate()
	{
		return ppOrderBL.streamManufacturingOrders(ManufacturingOrderQuery.builder()
						.onlyCompleted(true)
						.responsibleId(ValueRestriction.isNull())
						.build())
				.map(ppOrder -> toManufacturingJobReference(ppOrder, false));
	}

	private ManufacturingJobReference toManufacturingJobReference(final I_PP_Order ppOrder, final boolean isJobStarted)
	{
		return ManufacturingJobReference.builder()
				.ppOrderId(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
				.documentNo(ppOrder.getDocumentNo())
				.datePromised(InstantAndOrgId.ofTimestamp(ppOrder.getDatePromised(), ppOrder.getAD_Org_ID()).toZonedDateTime(orgDAO::getTimeZone))
				.productName(loadingAndSavingSupportServices.getProductName(ProductId.ofRepoId(ppOrder.getM_Product_ID())))
				.qtyRequiredToProduce(loadingAndSavingSupportServices.getQuantities(ppOrder).getQtyRequiredToProduce())
				.isJobStarted(isJobStarted)
				.build();
	}

	public ManufacturingJob createJob(final PPOrderId ppOrderId, final UserId responsibleId)
	{
		return ManufacturingJobCreateCommand.builder()
				.trxManager(trxManager)
				.ppOrderBL(ppOrderBL)
				.huReservationService(huReservationService)
				.ppOrderIssueScheduleService(ppOrderIssueScheduleService)
				.loadingSupportServices(loadingAndSavingSupportServices)
				//
				.ppOrderId(ppOrderId)
				.responsibleId(responsibleId)
				.build()
				.execute();
	}

	public void abortJob(@NonNull final PPOrderId ppOrderId, @NonNull final UserId responsibleId)
	{
		unassignFromResponsible(ppOrderId, responsibleId);
	}

	private void unassignFromResponsible(final @NonNull PPOrderId ppOrderId, final @NonNull UserId responsibleId)
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);
		final UserId currentResponsibleId = ManufacturingJobLoaderAndSaver.extractResponsibleId(ppOrder);

		//noinspection StatementWithEmptyBody
		if (currentResponsibleId == null)
		{
			// already unassigned, do nothing
		}
		else if (UserId.equals(currentResponsibleId, responsibleId))
		{
			ppOrder.setAD_User_Responsible_ID(-1);
			ppOrderBL.save(ppOrder);
		}
		else
		{
			throw new AdempiereException("Cannot unassign " + ppOrder.getDocumentNo()
					+ " because its assigned to a different responsible than the one we thought (expected: " + responsibleId + ", actual: " + currentResponsibleId + ")");
		}
	}

	public ManufacturingJob withActivityCompleted(ManufacturingJob job, ManufacturingJobActivityId jobActivityId)
	{
		final PPOrderId ppOrderId = job.getPpOrderId();
		final ManufacturingJobActivity jobActivity = job.getActivityById(jobActivityId);
		final PPOrderRoutingActivityId orderRoutingActivityId = jobActivity.getOrderRoutingActivityId();

		boolean jobNeedsReload = changeRoutingActivityStatusToCompleted(ppOrderId, orderRoutingActivityId);

		if (job.isLastActivity(jobActivityId))
		{
			ppOrderBL.closeOrder(ppOrderId);
			jobNeedsReload = true;
		}

		//
		return jobNeedsReload ? getJobById(ppOrderId) : job;

	}

	private boolean changeRoutingActivityStatusToCompleted(final PPOrderId ppOrderId, final PPOrderRoutingActivityId orderRoutingActivityId)
	{
		final ManufacturingJobLoaderAndSaver saver = newSaver();
		final PPOrderRouting orderRouting = saver.getRouting(ppOrderId);
		final PPOrderRouting orderRoutingBeforeChange = orderRouting.copy();
		orderRouting.completeActivity(orderRoutingActivityId);

		boolean jobNeedsReload = false;
		if (!orderRouting.equals(orderRoutingBeforeChange))
		{
			saver.saveRouting(orderRouting);
			jobNeedsReload = true;
		}

		return jobNeedsReload;
	}

	private void saveActivityStatuses(@NonNull final ManufacturingJob job)
	{
		newSaver().saveActivityStatuses(job);
	}

	public ManufacturingJob issueRawMaterials(@NonNull final ManufacturingJob job, @NonNull final PPOrderIssueScheduleProcessRequest request)
	{
		final ManufacturingJob changedJob = job.withChangedRawMaterialsIssueStep(request.getIssueScheduleId(), step -> {
			step.assertNotIssued();
			final PPOrderIssueSchedule issueSchedule = ppOrderIssueScheduleService.issue(request);
			return step.withIssued(issueSchedule.getIssued());
		});

		saveActivityStatuses(changedJob);

		return changedJob;
	}

	public ManufacturingJob receiveGoodsAndAggregateToLU(
			@NonNull final ManufacturingJob job,
			@NonNull final FinishedGoodsReceiveLineId lineId,
			@NonNull JsonAggregateToLU aggregateToLU,
			@NonNull final BigDecimal qtyToReceiveBD,
			@NonNull final ZonedDateTime date)
	{
		final PPOrderId ppOrderId = job.getPpOrderId();

		final ManufacturingJob changedJob = job.withChangedReceiveLine(lineId, line -> {
			final CurrentReceivingHU currentReceivingHU = trxManager.callInThreadInheritedTrx(() -> receiveGoodsAndAggregateToLU(
					ppOrderId,
					line.getCoProductBOMLineId(),
					aggregateToLU,
					qtyToReceiveBD,
					date));

			return line.withCurrentReceivingHU(currentReceivingHU);
		});

		saveActivityStatuses(changedJob);

		return changedJob;
	}

	private CurrentReceivingHU receiveGoodsAndAggregateToLU(
			final @NonNull PPOrderId ppOrderId,
			final @Nullable PPOrderBOMLineId coProductBOMLineId,
			final @NonNull JsonAggregateToLU aggregateToLU,
			final @NonNull BigDecimal qtyToReceiveBD,
			final @NonNull ZonedDateTime date)
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);

		final I_PP_Order_BOMLine coProductLine;
		final LocatorId locatorId;
		final UomId uomId;
		final IPPOrderReceiptHUProducer huProducer;
		if (coProductBOMLineId != null)
		{
			coProductLine = ppOrderBOMBL.getOrderBOMLineById(coProductBOMLineId);
			locatorId = LocatorId.ofRepoId(coProductLine.getM_Warehouse_ID(), coProductLine.getM_Locator_ID());
			uomId = UomId.ofRepoId(coProductLine.getC_UOM_ID());
			huProducer = ppOrderBL.receivingByOrCoProduct(coProductBOMLineId);
		}
		else
		{
			coProductLine = null;
			locatorId = LocatorId.ofRepoId(ppOrder.getM_Warehouse_ID(), ppOrder.getM_Locator_ID());
			uomId = UomId.ofRepoId(ppOrder.getC_UOM_ID());
			huProducer = ppOrderBL.receivingMainProduct(ppOrderId);
		}

		final HUPIItemProductId tuPIItemProductId = aggregateToLU.getTUPIItemProductId();
		final Quantity qtyToReceive = Quantitys.create(qtyToReceiveBD, uomId);

		final List<I_M_HU> tusOrVhus = huProducer
				.movementDate(date)
				.locatorId(locatorId)
				.receiveTUs(qtyToReceive, tuPIItemProductId);

		final HuId luId = aggregateTUsToLU(tusOrVhus, aggregateToLU);

		//
		// Remember current receiving LU.
		// We will need it later too.
		if (coProductLine != null)
		{
			coProductLine.setCurrent_Receiving_LU_HU_ID(luId.getRepoId());
			coProductLine.setCurrent_Receiving_TU_PI_Item_Product_ID(tuPIItemProductId.getRepoId());
			ppOrderBOMBL.save(coProductLine);
		}
		else
		{
			ppOrder.setCurrent_Receiving_LU_HU_ID(luId.getRepoId());
			ppOrder.setCurrent_Receiving_TU_PI_Item_Product_ID(tuPIItemProductId.getRepoId());
			ppOrderBL.save(ppOrder);
		}

		return CurrentReceivingHU.builder()
				.tuPIItemProductId(tuPIItemProductId)
				.aggregateToLUId(luId)
				.build();
	}

	private HuId aggregateTUsToLU(
			final @NonNull List<I_M_HU> tusOrVhus,
			final @NonNull JsonAggregateToLU aggregateToLU)
	{
		Check.assumeNotEmpty(tusOrVhus, "at least one TU shall be received from manufacturing order");

		I_M_HU lu = null;
		I_M_HU_PI_Item luPIItem = null;
		if (aggregateToLU.getExistingLU() != null)
		{
			lu = handlingUnitsDAO.getById(HUBarcode.ofBarcodeString(aggregateToLU.getExistingLU().getHuBarcode()).toHuId());
		}
		else
		{
			if (aggregateToLU.getNewLU() == null)
			{
				throw new AdempiereException("LU packing materials spec needs to be provided when no actual LU is specified.");
			}
			luPIItem = handlingUnitsDAO.getPackingInstructionItemById(HuPackingInstructionsItemId.ofRepoId(aggregateToLU.getNewLU().getLuPIItemId()));
		}

		for (final I_M_HU tu : tusOrVhus)
		{
			if (lu == null)
			{
				final List<I_M_HU> createdLUs = HUTransformService.newInstance()
						.tuToNewLUs(
								tu,
								QtyTU.ONE.toBigDecimal(),
								Objects.requireNonNull(luPIItem),
								true);
				lu = CollectionUtils.singleElement(createdLUs);
			}
			else
			{
				HUTransformService.newInstance().tuToExistingLU(tu, QtyTU.ONE.toBigDecimal(), lu);
			}
		}

		if (lu == null)
		{
			// shall not happen
			throw new AdempiereException("No LU was created");
		}

		return HuId.ofRepoId(lu.getM_HU_ID());
	}
}
