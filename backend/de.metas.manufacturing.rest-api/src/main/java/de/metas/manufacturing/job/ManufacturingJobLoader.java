package de.metas.manufacturing.job;

import com.google.common.collect.ImmutableList;
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

class ManufacturingJobLoader
{
	private final ManufacturingJobLoaderSupportingServices supportingServices;

	private final HashMap<PPOrderId, I_PP_Order> ppOrders = new HashMap<>();
	private final HashMap<PPOrderId, PPOrderRouting> routings = new HashMap<>();
	private final HashMap<PPOrderId, ImmutableList<I_PP_Order_BOMLine>> bomLines = new HashMap<>();

	ManufacturingJobLoader(@NonNull final ManufacturingJobLoaderSupportingServices supportingServices)
	{
		this.supportingServices = supportingServices;
	}

	public ManufacturingJob load(final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = getPPOrderRecordById(ppOrderId);
		final PPOrderRouting routing = getRouting(ppOrderId);

		return ManufacturingJob.builder()
				.ppOrderId(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
				.responsibleId(UserId.ofRepoId(ppOrder.getAD_User_Responsible_ID()))
				.activities(routing.getActivities()
						.stream()
						.map(this::toJobActivity)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public ManufacturingJobLoader addToCache(@NonNull final I_PP_Order ppOrder)
	{
		ppOrders.put(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()), ppOrder);
		return this;
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
				.code(from.getCode())
				.type(from.getType());
	}

	private ManufacturingJobActivity.RawMaterialsIssue toRawMaterialsIssue(final @NonNull PPOrderRoutingActivity from)
	{
		return ManufacturingJobActivity.RawMaterialsIssue.builder()
				.lines(getBOMLines(from.getOrderId())
						.stream()
						.map(this::toRawMaterialsIssueLine)
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	private ManufacturingJobActivity.RawMaterialsIssueLine toRawMaterialsIssueLine(@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		final BOMComponentType bomComponentType = BOMComponentType.ofCode(orderBOMLine.getComponentType());
		if (!bomComponentType.isIssue())
		{
			return null;
		}

		final ProductId productId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final OrderBOMLineQuantities bomLineQuantities = supportingServices.getQuantities(orderBOMLine);

		return ManufacturingJobActivity.RawMaterialsIssueLine.builder()
				.productId(productId)
				.productName(supportingServices.getProductName(productId))
				.qtyToIssue(bomLineQuantities.getQtyRequired())
				.qtyIssued(bomLineQuantities.getQtyIssuedOrReceived())
				.build();
	}

	private ManufacturingJobActivity.FinishedGoodsReceive toFinishedGoodsReceive(final @NonNull PPOrderRoutingActivity from)
	{
		final ManufacturingJobActivity.FinishedGoodsReceiveLine finishedGood = toFinishedGoodsReceiveLine(from.getOrderId());

		final Stream<ManufacturingJobActivity.FinishedGoodsReceiveLine> coProducts = getBOMLines(from.getOrderId())
				.stream()
				.map(this::toFinishedGoodsReceiveLine)
				.filter(Objects::nonNull);

		return ManufacturingJobActivity.FinishedGoodsReceive.builder()
				.lines(Stream.concat(Stream.of(finishedGood), coProducts)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	private ManufacturingJobActivity.FinishedGoodsReceiveLine toFinishedGoodsReceiveLine(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = getPPOrderRecordById(ppOrderId);
		final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final PPOrderQuantities orderQuantities = supportingServices.getQuantities(ppOrder);

		return ManufacturingJobActivity.FinishedGoodsReceiveLine.builder()
				.productId(productId)
				.productName(supportingServices.getProductName(productId))
				.qtyToReceive(orderQuantities.getQtyRequiredToProduce())
				.qtyReceived(orderQuantities.getQtyReceived())
				.isByOrCoProduct(true)
				.build();

	}

	@Nullable
	private ManufacturingJobActivity.FinishedGoodsReceiveLine toFinishedGoodsReceiveLine(@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		final BOMComponentType bomComponentType = BOMComponentType.ofCode(orderBOMLine.getComponentType());
		if (!bomComponentType.isByOrCoProduct())
		{
			return null;
		}

		final ProductId productId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final OrderBOMLineQuantities bomLineQuantities = supportingServices.getQuantities(orderBOMLine);

		return ManufacturingJobActivity.FinishedGoodsReceiveLine.builder()
				.productId(productId)
				.productName(supportingServices.getProductName(productId))
				.qtyToReceive(bomLineQuantities.getQtyRequired().negate())
				.qtyReceived(bomLineQuantities.getQtyIssuedOrReceived().negate())
				.isByOrCoProduct(true)
				.build();
	}

}
