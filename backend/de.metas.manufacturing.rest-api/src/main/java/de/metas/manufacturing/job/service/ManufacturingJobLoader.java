package de.metas.manufacturing.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.manufacturing.job.model.CurrentReceivingHU;
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
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
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

public class ManufacturingJobLoader
{
	private final ManufacturingJobLoaderSupportingServices supportingServices;

	private final HashMap<PPOrderId, I_PP_Order> ppOrders = new HashMap<>();
	private final HashMap<PPOrderId, PPOrderRouting> routings = new HashMap<>();
	private final HashMap<PPOrderId, ImmutableList<I_PP_Order_BOMLine>> bomLines = new HashMap<>();
	private final HashMap<PPOrderId, ImmutableListMultimap<PPOrderBOMLineId, PPOrderIssueSchedule>> issueSchedules = new HashMap<>();

	public ManufacturingJobLoader(@NonNull final ManufacturingJobLoaderSupportingServices supportingServices)
	{
		this.supportingServices = supportingServices;
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

	private PPOrderRouting getRouting(final PPOrderId ppOrderId)
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
				.id(ManufacturingJobActivityId.ofRepoId(ppOrderRoutingActivityId.getRepoId()))
				.name(from.getName())
				.type(from.getType())
				.orderRoutingActivityId(from.getId())
				.status(from.getStatus());
	}

	private RawMaterialsIssue toRawMaterialsIssue(final @NonNull PPOrderRoutingActivity from)
	{
		return RawMaterialsIssue.builder()
				.lines(getBOMLines(from.getOrderId())
						.stream()
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
		final OrderBOMLineQuantities bomLineQuantities = supportingServices.getQuantities(orderBOMLine);

		return RawMaterialsIssueLine.builder()
				.productId(productId)
				.productName(supportingServices.getProductName(productId))
				.qtyToIssue(bomLineQuantities.getQtyRequired())
				//.qtyIssued(bomLineQuantities.getQtyIssuedOrReceived())
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
						.barcode(HUBarcode.ofHuId(schedule.getIssueFromHUId()))
						.build())
				.issued(schedule.getIssued())
				.build();
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
				.qtyToReceive(orderQuantities.getQtyRequiredToProduce())
				.qtyReceived(orderQuantities.getQtyReceived())
				.coProductBOMLineId(null)
				.currentReceivingHU(extractCurrentReceivingHU(ppOrder))
				.build();

	}

	private static CurrentReceivingHU extractCurrentReceivingHU(final I_PP_Order ppOrder)
	{
		final HuId luId = HuId.ofRepoIdOrNull(ppOrder.getCurrent_Receiving_LU_HU_ID());
		if (luId != null)
		{
			return CurrentReceivingHU.builder()
					.aggregateToLUId(luId)
					.tuPIItemProductId(HUPIItemProductId.ofRepoIdOrNone(ppOrder.getCurrent_Receiving_TU_PI_Item_Product_ID()))
					.build();
		}
		else
		{
			return null;
		}
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
				.qtyToReceive(bomLineQuantities.getQtyRequired().negate())
				.qtyReceived(bomLineQuantities.getQtyIssuedOrReceived().negate())
				.coProductBOMLineId(PPOrderBOMLineId.ofRepoId(orderBOMLine.getPP_Order_BOMLine_ID()))
				.currentReceivingHU(extractCurrentReceivingHU(orderBOMLine))
				.build();
	}

	private static CurrentReceivingHU extractCurrentReceivingHU(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final HuId luId = HuId.ofRepoIdOrNull(ppOrderBOMLine.getCurrent_Receiving_LU_HU_ID());
		if (luId != null)
		{
			return CurrentReceivingHU.builder()
					.aggregateToLUId(luId)
					.tuPIItemProductId(HUPIItemProductId.ofRepoIdOrNone(ppOrderBOMLine.getCurrent_Receiving_TU_PI_Item_Product_ID()))
					.build();
		}
		else
		{
			return null;
		}
	}

}
