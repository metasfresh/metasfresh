package de.metas.manufacturing.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerId;
import de.metas.device.accessor.DeviceId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.manufacturing.job.model.FinishedGoodsReceive;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.HUInfo;
import de.metas.manufacturing.job.model.LocatorInfo;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.model.RawMaterialsIssue;
import de.metas.manufacturing.job.model.RawMaterialsIssueLine;
import de.metas.manufacturing.job.model.RawMaterialsIssueStep;
import de.metas.manufacturing.job.model.ReceivingTarget;
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ManufacturingJobLoaderAndSaver
{
	private final ManufacturingJobLoaderAndSaverSupportingServices supportingServices;

	private final HashMap<PPOrderId, I_PP_Order> ppOrders = new HashMap<>();
	private final HashMap<PPOrderId, PPOrderRouting> routings = new HashMap<>();
	private final HashMap<PPOrderId, ImmutableList<I_PP_Order_BOMLine>> bomLines = new HashMap<>();
	private final HashMap<PPOrderId, ImmutableListMultimap<PPOrderBOMLineId, PPOrderIssueSchedule>> issueSchedules = new HashMap<>();

	public ManufacturingJobLoaderAndSaver(@NonNull final ManufacturingJobLoaderAndSaverSupportingServices supportingServices)
	{
		this.supportingServices = supportingServices;
	}

	public ManufacturingJob load(@NonNull final I_PP_Order ppOrder)
	{
		addToCache(ppOrder);
		return load(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()));
	}

	public ManufacturingJob load(final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = getPPOrderRecordById(ppOrderId);
		final DocStatus ppOrderDocStatus = DocStatus.ofCode(ppOrder.getDocStatus());
		final PPOrderRouting routing = getRouting(ppOrderId);

		return ManufacturingJob.builder()
				.ppOrderId(ppOrderId)
				.documentNo(ppOrder.getDocumentNo())
				.customerId(BPartnerId.ofRepoIdOrNull(ppOrder.getC_BPartner_ID()))
				.datePromised(InstantAndOrgId.ofTimestamp(ppOrder.getDatePromised(), ppOrder.getAD_Org_ID()).toZonedDateTime(supportingServices::getTimeZone))
				.responsibleId(extractResponsibleId(ppOrder))
				.allowUserReporting(ppOrderDocStatus.isCompleted())
				//
				.warehouseId(WarehouseId.ofRepoId(ppOrder.getM_Warehouse_ID()))
				.currentScaleDeviceId(DeviceId.ofNullableString(ppOrder.getCurrentScaleDeviceId()))
				//
				.activities(routing.getActivities()
						.stream()
						.sorted(Comparator.comparing(activity -> activity.getCode().getAsString()))
						.map(this::toJobActivity)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	public static UserId extractResponsibleId(final I_PP_Order ppOrder)
	{
		return UserId.ofRepoIdOrNullIfSystem(ppOrder.getAD_User_Responsible_ID());
	}

	public void addToCache(@NonNull final I_PP_Order ppOrder)
	{
		ppOrders.put(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()), ppOrder);
	}

	public void addToCache(List<PPOrderIssueSchedule> schedules)
	{
		final PPOrderId ppOrderId = CollectionUtils.extractSingleElement(schedules, PPOrderIssueSchedule::getPpOrderId);
		final ImmutableListMultimap<PPOrderBOMLineId, PPOrderIssueSchedule> schedulesByBOMLineId = Multimaps.index(schedules, PPOrderIssueSchedule::getPpOrderBOMLineId);
		issueSchedules.put(ppOrderId, schedulesByBOMLineId);
	}

	private I_PP_Order getPPOrderRecordById(final PPOrderId ppOrderId)
	{
		return ppOrders.computeIfAbsent(ppOrderId, supportingServices::getPPOrderRecordById);
	}

	public PPOrderRouting getRouting(final PPOrderId ppOrderId)
	{
		return routings.computeIfAbsent(ppOrderId, supportingServices::getOrderRouting);
	}

	private ImmutableList<I_PP_Order_BOMLine> getBOMLines(final PPOrderId ppOrderId)
	{
		return bomLines.computeIfAbsent(ppOrderId, supportingServices::getOrderBOMLines);
	}

	private ImmutableListMultimap<PPOrderBOMLineId, PPOrderIssueSchedule> getIssueSchedules(final PPOrderId ppOrderId)
	{
		return issueSchedules.computeIfAbsent(ppOrderId, supportingServices::getIssueSchedules);
	}

	private ManufacturingJobActivity toJobActivity(@NonNull final PPOrderRoutingActivity from)
	{
		switch (from.getType())
		{
			case RawMaterialsIssue:
				return prepareJobActivity(from)
						.rawMaterialsIssue(toRawMaterialsIssue(from))
						.build();
			case MaterialReceipt:
				return prepareJobActivity(from)
						.finishedGoodsReceive(toFinishedGoodsReceive(from))
						.build();
			case WorkReport:
			case ActivityConfirmation:
			case GenerateHUQRCodes:
			case ScanScaleDevice:
			case CallExternalSystem:
			case RawMaterialsIssueAdjustment:
				return prepareJobActivity(from)
						.build();
			default:
				throw new AdempiereException("Unknown type: " + from);
		}
	}

	private static ManufacturingJobActivity.ManufacturingJobActivityBuilder prepareJobActivity(@NonNull final PPOrderRoutingActivity from)
	{
		final PPOrderRoutingActivityId ppOrderRoutingActivityId = Objects.requireNonNull(from.getId());

		return ManufacturingJobActivity.builder()
				.id(ManufacturingJobActivityId.of(ppOrderRoutingActivityId))
				.name(from.getName())
				.type(from.getType())
				.orderRoutingActivityId(from.getId())
				.routingActivityStatus(from.getStatus())
				.alwaysAvailableToUser(from.getAlwaysAvailableToUser())
				.userInstructions(from.getUserInstructions())
				.scannedQRCode(from.getScannedQRCode());
	}

	private RawMaterialsIssue toRawMaterialsIssue(final @NonNull PPOrderRoutingActivity from)
	{
		final PPOrderId ppOrderId = from.getOrderId();
		final PPOrderRouting routing = getRouting(ppOrderId);
		final ImmutableSet<ProductId> onlyProductIds = from.getId() != null ? routing.getProductIdsByActivityId(from.getId()) : ImmutableSet.of();

		return RawMaterialsIssue.builder()
				.lines(getBOMLines(ppOrderId)
						.stream()
						.filter(bomLine -> onlyProductIds.isEmpty() || onlyProductIds.contains(ProductId.ofRepoId(bomLine.getM_Product_ID())))
						.map(this::toRawMaterialsIssueLine)
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	private RawMaterialsIssueLine toRawMaterialsIssueLine(@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		final BOMComponentType bomComponentType = BOMComponentType.optionalOfNullableCode(orderBOMLine.getComponentType()).orElse(BOMComponentType.Component);
		if (!bomComponentType.isIssue())
		{
			return null;
		}

		final PPOrderId ppOrderId = PPOrderId.ofRepoId(orderBOMLine.getPP_Order_ID());
		final PPOrderBOMLineId ppOrderBOMLineId = PPOrderBOMLineId.ofRepoId(orderBOMLine.getPP_Order_BOMLine_ID());
		final ProductId productId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final OrderBOMLineQuantities quantities = supportingServices.getQuantities(orderBOMLine);
		final Quantity qtyToIssue = quantities.getQtyRequired();
		final boolean isWeightable = !orderBOMLine.isManualQtyInput() && qtyToIssue.isWeightable();

		return RawMaterialsIssueLine.builder()
				.productId(productId)
				.productName(supportingServices.getProductName(productId))
				.isWeightable(isWeightable)
				.qtyToIssue(qtyToIssue)
				.issuingToleranceSpec(quantities.getIssuingToleranceSpec())
				.steps(getIssueSchedules(ppOrderId)
						.get(ppOrderBOMLineId)
						.stream()
						.sorted(Comparator.comparing(PPOrderIssueSchedule::getSeqNo))
						.map(this::toRawMaterialsIssueStep)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private RawMaterialsIssueStep toRawMaterialsIssueStep(final PPOrderIssueSchedule schedule)
	{
		return RawMaterialsIssueStep.builder()
				.id(schedule.getId())
				.isAlternativeIssue(schedule.isAlternativeIssue())
				.productId(schedule.getProductId())
				.productName(supportingServices.getProductName(schedule.getProductId()))
				.qtyToIssue(schedule.getQtyToIssue())
				.issueFromLocator(LocatorInfo.builder()
						.id(schedule.getIssueFromLocatorId())
						.caption(supportingServices.getLocatorName(schedule.getIssueFromLocatorId()))
						.build())
				.issueFromHU(HUInfo.builder()
						.id(schedule.getIssueFromHUId())
						.barcode(supportingServices.getQRCodeByHuId(schedule.getIssueFromHUId()))
						.huCapacity(getHUCapacity(schedule))
						.build())
				.issued(schedule.getIssued())
				.build();
	}

	private Quantity getHUCapacity(@NonNull final PPOrderIssueSchedule schedule)
	{
		return supportingServices.getHUCapacity(schedule.getIssueFromHUId(), schedule.getProductId(), schedule.getQtyToIssue().getUOM());
	}

	private FinishedGoodsReceive toFinishedGoodsReceive(final @NonNull PPOrderRoutingActivity from)
	{
		final FinishedGoodsReceiveLine finishedGood = toFinishedGoodsReceiveLine(from.getOrderId());

		final Stream<FinishedGoodsReceiveLine> coProducts = getBOMLines(from.getOrderId())
				.stream()
				.map(this::toFinishedGoodsReceiveLine)
				.filter(Objects::nonNull);

		return FinishedGoodsReceive.builder()
				.linesById(Stream.concat(Stream.of(finishedGood), coProducts)
						.collect(ImmutableMap.toImmutableMap(FinishedGoodsReceiveLine::getId, line -> line)))
				.build();
	}

	@Nullable
	private FinishedGoodsReceiveLine toFinishedGoodsReceiveLine(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = getPPOrderRecordById(ppOrderId);
		final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final PPOrderQuantities orderQuantities = supportingServices.getQuantities(ppOrder);

		return FinishedGoodsReceiveLine.builder()
				.productId(productId)
				.productName(supportingServices.getProductName(productId))
				.attributes(supportingServices.getImmutableAttributeSet(AttributeSetInstanceId.ofRepoId(ppOrder.getM_AttributeSetInstance_ID())))
				.qtyToReceive(orderQuantities.getQtyRequiredToProduce())
				.qtyReceived(orderQuantities.getQtyReceived())
				.coProductBOMLineId(null)
				.receivingTarget(extractReceivingTarget(ppOrder))
				.build();

	}

	@Nullable
	public static ReceivingTarget extractReceivingTarget(final I_PP_Order ppOrder)
	{
		final HuId luId = HuId.ofRepoIdOrNull(ppOrder.getCurrent_Receiving_LU_HU_ID());
		if (luId == null)
		{
			return null;
		}

		return ReceivingTarget.builder()
				.luId(luId)
				// TODO .tuId()
				.tuPIItemProductId(HUPIItemProductId.ofRepoIdOrNone(ppOrder.getCurrent_Receiving_TU_PI_Item_Product_ID()))
				.build();
	}

	public static void updateRecordFromReceivingTarget(@NonNull final I_PP_Order record, @Nullable final ReceivingTarget from)
	{
		final HuId luId = from != null ? from.getLuId() : null;
		final HUPIItemProductId tuPIItemProductId = from != null ? from.getTuPIItemProductId() : null;

		record.setCurrent_Receiving_LU_HU_ID(HuId.toRepoId(luId));
		// TODO record.setCurrent_Receiving_TU_HU_ID(HuId.toRepoId(from.getTuId()));
		record.setCurrent_Receiving_TU_PI_Item_Product_ID(HUPIItemProductId.toRepoId(tuPIItemProductId));
	}

	@Nullable
	private FinishedGoodsReceiveLine toFinishedGoodsReceiveLine(@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		final BOMComponentType bomComponentType = BOMComponentType.optionalOfNullableCode(orderBOMLine.getComponentType()).orElse(BOMComponentType.Component);
		if (!bomComponentType.isByOrCoProduct())
		{
			return null;
		}

		final ProductId productId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final OrderBOMLineQuantities bomLineQuantities = supportingServices.getQuantities(orderBOMLine);

		return FinishedGoodsReceiveLine.builder()
				.productId(productId)
				.productName(supportingServices.getProductName(productId))
				.attributes(supportingServices.getImmutableAttributeSet(AttributeSetInstanceId.ofRepoId(orderBOMLine.getM_AttributeSetInstance_ID())))
				.qtyToReceive(bomLineQuantities.getQtyRequired().negate())
				.qtyReceived(bomLineQuantities.getQtyIssuedOrReceived().negate())
				.coProductBOMLineId(PPOrderBOMLineId.ofRepoId(orderBOMLine.getPP_Order_BOMLine_ID()))
				.receivingTarget(extractReceivingTarget(orderBOMLine))
				.build();
	}

	@Nullable
	public static ReceivingTarget extractReceivingTarget(@NonNull final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final HuId luId = HuId.ofRepoIdOrNull(ppOrderBOMLine.getCurrent_Receiving_LU_HU_ID());
		if (luId == null)
		{
			return null;
		}

		return ReceivingTarget.builder()
				.luId(luId)
				// TODO .tuId()
				.tuPIItemProductId(HUPIItemProductId.ofRepoIdOrNone(ppOrderBOMLine.getCurrent_Receiving_TU_PI_Item_Product_ID()))
				.build();
	}

	public static void updateRecordFromReceivingTarget(@NonNull final I_PP_Order_BOMLine record, @Nullable final ReceivingTarget from)
	{
		final HuId luId = from != null ? from.getLuId() : null;
		final HUPIItemProductId tuPIItemProductId = from != null ? from.getTuPIItemProductId() : null;

		record.setCurrent_Receiving_LU_HU_ID(HuId.toRepoId(luId));
		// TODO record.setCurrent_Receiving_TU_HU_ID(HuId.toRepoId(from.getTuId()));
		record.setCurrent_Receiving_TU_PI_Item_Product_ID(HUPIItemProductId.toRepoId(tuPIItemProductId));
	}

	public void saveActivityStatuses(final ManufacturingJob job)
	{
		final PPOrderId ppOrderId = job.getPpOrderId();
		final PPOrderRouting routing = getRouting(ppOrderId);
		final PPOrderRouting routingBeforeChange = routing.copy();

		for (ManufacturingJobActivity jobActivity : job.getActivities())
		{
			final PPOrderRoutingActivityId ppOrderRoutingActivityId = jobActivity.getId().toPPOrderRoutingActivityId(ppOrderId);
			routing.getActivityById(ppOrderRoutingActivityId).changeStatusTo(jobActivity.getRoutingActivityStatus());
		}

		if (!routing.equals(routingBeforeChange))
		{
			saveRouting(routing);
		}
	}

	public void saveRouting(final PPOrderRouting routing)
	{
		supportingServices.saveOrderRouting(routing);
	}

	public void saveHeader(@NonNull final ManufacturingJob job)
	{
		final I_PP_Order ppOrder = getPPOrderRecordById(job.getPpOrderId());
		ppOrder.setCurrentScaleDeviceId(job.getCurrentScaleDeviceId() != null ? job.getCurrentScaleDeviceId().getAsString() : null);
		InterfaceWrapperHelper.saveRecord(ppOrder);
	}
}
