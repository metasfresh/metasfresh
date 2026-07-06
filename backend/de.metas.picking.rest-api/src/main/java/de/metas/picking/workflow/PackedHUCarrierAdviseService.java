package de.metas.picking.workflow;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import de.metas.common.delivery.v1.json.JsonTopLevelType;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestParcel;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.customstariff.CustomsTariffId;
import de.metas.customstariff.CustomsTariffRepository;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.shipping.PackedHUProductItem;
import de.metas.handlingunits.shipping.PackedHUShippingInfo;
import de.metas.handlingunits.shipping.PackedHUShippingInfoService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.product.IProductBL;
import de.metas.product.PackageDimensions;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.shipper.gateway.commons.CarrierAdviseCommand;
import de.metas.shipper.gateway.commons.model.CarrierProduct;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipping.CarrierProductId;
import de.metas.shipping.Shipper;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PackedHUCarrierAdviseService
{
	@NonNull private final PackedHUShippingInfoService packedHUShippingInfoService;
	@NonNull private final HUShipmentScheduleResolver huShipmentScheduleResolver;
	@NonNull private final ProductRepository productRepository;
	@NonNull private final CarrierProductRepository carrierProductRepository;
	@NonNull private final CustomsTariffRepository customsTariffRepository;
	@NonNull private final ShipperRepository shipperRepository;
	@NonNull private final ShipmentScheduleService shipmentScheduleService;
	@NonNull private final PickingJobRepository pickingJobRepository;

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	public CarrierAdviseTargetInfo resolveTargetInfo(@NonNull final I_M_HU topLevelHU)
	{
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById = huShipmentScheduleResolver.resolveSchedulesByIdForHU(topLevelHU);
		if (schedulesById.isEmpty())
		{
			return CarrierAdviseTargetInfo.NONE;
		}

		final ImmutableSet<ShipperId> shipperIds = schedulesById.values().stream()
				.map(ShipmentSchedule::getShipperId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		final Map<ShipperId, Shipper> shippersById = shipperRepository.getByIds(shipperIds);

		boolean anyAdviseEnabled = false;
		boolean allManual = true;
		final LinkedHashSet<String> productCaptionNames = new LinkedHashSet<>();

		for (final ShipmentSchedule schedule : schedulesById.values())
		{
			final ShipperId shipperId = schedule.getShipperId();
			if (shipperId == null)
			{
				continue;
			}

			final Shipper shipper = shippersById.get(shipperId);
			if (shipper == null || !shipper.isApiCarrierAdvise())
			{
				continue;
			}

			anyAdviseEnabled = true;

			if (!schedule.getCarrierAdvisingStatus().isManual())
			{
				allManual = false;
			}

			final CarrierProductId carrierProductId = schedule.getCarrierProductId();
			if (carrierProductId != null)
			{
				final CarrierProduct carrierProduct = carrierProductRepository.getCachedShipperProductById(carrierProductId);
				if (carrierProduct != null)
				{
					productCaptionNames.add(carrierProduct.getName());
				}
			}
		}

		if (!anyAdviseEnabled)
		{
			return CarrierAdviseTargetInfo.NONE;
		}

		final String productCaption = productCaptionNames.isEmpty() ? null : String.join(", ", productCaptionNames);

		return CarrierAdviseTargetInfo.builder()
				.available(true)
				.readOnly(allManual)
				.productCaption(productCaption)
				.build();
	}

	/**
	 * Builds the display info from a job-scoped carrier product — the picking-job line's (or header's)
	 * persisted {@code Carrier_Product_ID}, which is the job-scoped source of truth. Unlike
	 * {@link #resolveTargetInfo(I_M_HU)} (per-HU via the shipment schedule, which is NOT scoped to the
	 * picking job), this is what the mobile picking-job JSON converter should show for the current carrier.
	 */
	@NonNull
	public CarrierAdviseTargetInfo resolveTargetInfoFromCarrierProduct(
			@Nullable final CarrierProductId carrierProductId,
			final boolean readOnly)
	{
		if (carrierProductId == null)
		{
			return CarrierAdviseTargetInfo.NONE;
		}

		final CarrierProduct carrierProduct = carrierProductRepository.getCachedShipperProductById(carrierProductId);
		if (carrierProduct == null)
		{
			return CarrierAdviseTargetInfo.NONE;
		}

		return CarrierAdviseTargetInfo.builder()
				.available(true)
				.readOnly(readOnly)
				.productCaption(carrierProduct.getName())
				.build();
	}

	/**
	 * The HUs to (re-)advise. When the job/line has a current LU/TU pick target (the parcel being packed
	 * now), advise ONLY that target parcel — never the already-finished parcels: each top-level HU is its
	 * own Carrier_ShipmentOrder with its own carrier, so a finished parcel keeps its carrier and must not
	 * be re-touched (nor collapse divergent per-parcel carriers into the single header product). When there
	 * is NO LU/TU pick target (CU-direct), there is no single "current target" parcel — fall back to all
	 * picked HUs of the scope (unchanged behaviour; CU-direct scoping is a separately-tracked open decision).
	 */
	private ImmutableSet<HuId> resolveAdviseTargetHuIds(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId)
	{
		final HuId currentTargetHuId = pickingJob.getLuPickingTargetEffective(lineId)
				.filter(LUPickingTarget::isExistingLU)
				.map(LUPickingTarget::getLuId)
				.orElseGet(() -> pickingJob.getTuPickingTargetEffective(lineId)
						.filter(TUPickingTarget::isExistingTU)
						.map(TUPickingTarget::getTuId)
						.orElse(null));
		if (currentTargetHuId != null)
		{
			return ImmutableSet.of(currentTargetHuId);
		}
		return pickingJob.getPickedHuIds(lineId);
	}

	/**
	 * Re-advises the current pick-target parcel (or, CU-direct, all packed top-level HUs — see
	 * {@link #resolveAdviseTargetHuIds}), then persists the advised carrier product (and the read-only
	 * flag) onto the picking job header + its non-Manual lines, so the mobile preview and the
	 * {@link CarrierAdviseConsistencyService} checks read the same persisted state.
	 * Skips shipment schedules whose carrier advising status is Manual (a manually-set carrier product
	 * must never be overwritten, neither on the schedule nor on the picking job line).
	 * <p>
	 * Top-level resolution and deduplication by HuId is shared with
	 * {@link CarrierAdviseConsistencyService#assertConsistentForJob} via
	 * {@link IHandlingUnitsBL#getTopLevelHUsByHuIds(java.util.Collection)}.
	 *
	 * @return the (possibly unchanged) picking job after persisting the advised product onto header + lines.
	 */
	public PickingJob advise(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId)
	{
		final ImmutableSet<HuId> adviseHuIds = resolveAdviseTargetHuIds(pickingJob, lineId);
		if (adviseHuIds.isEmpty())
		{
			return pickingJob;
		}

		// (a picked HU can yield distinct I_M_HU instances; also normalises a CU-direct leaf to its top level)
		final ImmutableMap<HuId, I_M_HU> topLevelHUsById = handlingUnitsBL.getTopLevelHUsByHuIds(adviseHuIds);

		// the non-Manual schedules just re-advised (insertion order preserved for a stable header product pick)
		final LinkedHashSet<ShipmentScheduleId> advisedScheduleIds = new LinkedHashSet<>();
		// at least one schedule among the processed HUs is Manual → the whole job's carrier product is read-only
		boolean anyManual = false;

		for (final I_M_HU topLevelHU : topLevelHUsById.values())
		{
			final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById = huShipmentScheduleResolver.resolveSchedulesByIdForHU(topLevelHU);

			final JsonDeliveryAdvisorRequestParcel parcel = buildRequestParcel(topLevelHU, schedulesById);

			for (final ShipmentSchedule schedule : schedulesById.values())
			{
				if (schedule.getCarrierAdvisingStatus().isManual())
				{
					anyManual = true;
					continue;
				}
				adviseSchedule(schedule.getId(), parcel);
				advisedScheduleIds.add(schedule.getId());
			}
		}

		// adviseSchedule (executeSync) persisted the advised carrier onto each schedule; re-read them (the in-memory
		// schedules from resolveSchedulesByIdForHU are now stale) in ONE batch to learn the results — avoids a
		// per-schedule getById inside the loop.
		final Map<ShipmentScheduleId, AdvisedCarrier> advisedCarrierByScheduleId = new LinkedHashMap<>();
		if (!advisedScheduleIds.isEmpty())
		{
			for (final ShipmentSchedule advisedSchedule : shipmentScheduleService.getByIds(ImmutableSet.copyOf(advisedScheduleIds)))
			{
				advisedCarrierByScheduleId.put(advisedSchedule.getId(), AdvisedCarrier.builder()
						.carrierProductId(advisedSchedule.getCarrierProductId())
						.carrierGoodsTypeId(advisedSchedule.getCarrierGoodsTypeId())
						.carrierServices(ImmutableSet.copyOf(advisedSchedule.getCarrierServicesIfLoaded()))
						.build());
			}
		}

		return persistAdvisedProductOnJob(pickingJob, advisedCarrierByScheduleId, anyManual);
	}

	/**
	 * The carrier advice re-read from a just-advised shipment schedule: the carrier product the header carries,
	 * plus the goods-type + services that (with the product) are persisted onto the picking-job line.
	 */
	@Value
	@Builder
	private static class AdvisedCarrier
	{
		@Nullable CarrierProductId carrierProductId;
		@Nullable CarrierGoodsTypeId carrierGoodsTypeId;
		@NonNull ImmutableSet<CarrierServiceId> carrierServices;
	}

	/**
	 * Re-advises one non-Manual schedule against the packed-HU parcel.
	 * <p>
	 * executeSync (not execute): re-advise against the packed HU regardless of the schedule's
	 * current advising status — at packing time it is typically already Completed from the
	 * auto-advise at order completion, so the Requested-only execute() guard would no-op.
	 * <p>
	 * Extracted as a seam so {@link #advise(PickingJob, PickingJobLineId)} can be unit-tested without
	 * exercising the static {@link CarrierAdviseCommand} (which performs real DB + shipper-gateway work).
	 */
	@VisibleForTesting
	void adviseSchedule(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final JsonDeliveryAdvisorRequestParcel parcel)
	{
		CarrierAdviseCommand.ofPackedHU(shipmentScheduleId, parcel).executeSync();
	}

	/**
	 * Persists the advised carrier product onto the picking job:
	 * <ul>
	 *     <li>each non-Manual line (mapped to a just-advised schedule via its shipment-schedule id) gets the
	 *         advised product; Manual lines are left untouched;</li>
	 *     <li>the header gets the single distinct advised product (the job's current carrier target) — if the
	 *         advised schedules resolve to MORE than one distinct product, this is ambiguous and we abort
	 *         rather than guess which one the header should carry;</li>
	 *     <li>{@code carrierAdviseReadOnly = anyManual} on the header and on every touched line.</li>
	 * </ul>
	 */
	private PickingJob persistAdvisedProductOnJob(
			@NonNull final PickingJob pickingJob,
			@NonNull final Map<ShipmentScheduleId, AdvisedCarrier> advisedCarrierByScheduleId,
			final boolean anyManual)
	{
		if (advisedCarrierByScheduleId.isEmpty())
		{
			// No non-Manual schedule was advised. If every advise schedule was Manual, the carrier product is
			// manually controlled → still flag the header read-only (no product to set). Otherwise nothing to do.
			if (!anyManual)
			{
				return pickingJob;
			}
			final PickingJob jobWithReadOnly = pickingJob.withCarrierAdviseReadOnly(true);
			if (jobWithReadOnly != pickingJob)
			{
				pickingJobRepository.save(jobWithReadOnly);
			}
			return jobWithReadOnly;
		}

		// header carrier product = the single distinct advised product (the job's current carrier target).
		// More than one distinct product across the advised schedules has no single "current" target → abort.
		// (the header table has no goods-type/services columns — those live only on the line.)
		final ImmutableSet<CarrierProductId> distinctAdvisedProducts = advisedCarrierByScheduleId.values().stream()
				.map(AdvisedCarrier::getCarrierProductId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (distinctAdvisedProducts.size() > 1)
		{
			throw new AdempiereException("Cannot set a single carrier product on the picking job header: "
					+ "the advised schedules resolved to multiple distinct carrier products " + distinctAdvisedProducts);
		}
		final CarrierProductId headerProductId = distinctAdvisedProducts.stream().findFirst().orElse(null);

		// set the advised product + goods-type + services on each non-Manual line mapped to an advised schedule
		// (by shipment-schedule id); Manual lines never appear in advisedCarrierByScheduleId, so they are left untouched.
		final PickingJob jobWithLines = pickingJob.withChangedLines(line -> {
			final ShipmentScheduleId lineScheduleId = line.getScheduleId().getShipmentScheduleId();
			final AdvisedCarrier advisedCarrier = advisedCarrierByScheduleId.get(lineScheduleId);
			if (advisedCarrier == null)
			{
				return line;
			}
			return line.withCarrierAdvise(
					advisedCarrier.getCarrierProductId(),
					advisedCarrier.getCarrierGoodsTypeId(),
					advisedCarrier.getCarrierServices(),
					anyManual);
		});

		final PickingJob jobWithHeader = jobWithLines
				.withCarrierProductId(headerProductId)
				.withCarrierAdviseReadOnly(anyManual);

		pickingJobRepository.save(jobWithHeader);
		return jobWithHeader;
	}

	// HU-advise parcel envelope: parcel-level fields (weight/dims/topLevelType) from packedHUShippingInfoService.of(hu),
	// plus one item per contained product (buildRequestItem). The PARCEL gross weight is the real HU weight;
	// per-item weights are the nominal product weights (see buildRequestItem).
	@VisibleForTesting
	JsonDeliveryAdvisorRequestParcel buildRequestParcel(
			@NonNull final I_M_HU topLevelHU,
			@NonNull final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById)
	{
		final PackedHUShippingInfo shippingInfo = packedHUShippingInfoService.of(topLevelHU);

		final List<PackedHUProductItem> productItems = packedHUShippingInfoService.getProductItems(topLevelHU);
		if (productItems.isEmpty())
		{
			throw new AdempiereException("HU " + topLevelHU.getM_HU_ID() + " has no product storage");
		}

		// product → schedule lookup: each schedule resolves to its order line's product.
		// A product with no matching schedule keeps its value/price/qty fields null (still emitted with product/qty/weight/customs/CoO).
		final ImmutableMap<ProductId, ShipmentSchedule> scheduleByProductId = schedulesById.values().stream()
				.collect(ImmutableMap.toImmutableMap(
						ShipmentSchedule::getProductId,
						s -> s,
						(existing, ignored) -> existing));

		final PackageDimensions dimensions = shippingInfo.getDimensions();
		final BigDecimal grossWeightKgBD = shippingInfo.getWeightInKg() != null
				? shippingInfo.getWeightInKg().toBigDecimal()
				: BigDecimal.ZERO;

		final ImmutableList<JsonDeliveryAdvisorRequestItem> items = productItems.stream()
				.map(productItem -> buildRequestItem(
						productItem,
						scheduleByProductId.get(productItem.getProductId())))
				.collect(ImmutableList.toImmutableList());

		return JsonDeliveryAdvisorRequestParcel.builder()
				.grossWeightKg(grossWeightKgBD)
				.packageDimensions(JsonPackageDimensions.builder()
						.heightInCM(dimensions.getHeightInCM())
						.widthInCM(dimensions.getWidthInCM())
						.lengthInCM(dimensions.getLengthInCM())
						.build())
				.topLevelType(toTopLevelTypeWireString(shippingInfo.getTopLevelType()))
				.items(items)
				.build();
	}

	// Carrier "final info" build path — HU-advise item (1 of 3).
	// Field derivation MUST stay consistent across the three nShift build paths (change together):
	//   - HU-advise:        PackedHUCarrierAdviseService#buildRequestItem
	//   - schedule-advise:  CarrierAdviseCommand#getJsonDeliveryAdvisorRequestItem
	//   - delivery-order:   NShiftDraftDeliveryOrderCreator#createDeliveryOrderItem
	// numberOfItems is intentionally path-specific (NOT part of the consistency contract):
	// HU-advise = the product's packed qty; schedule-advise = 1 (no packed HU, a single CU baseline).
	private JsonDeliveryAdvisorRequestItem buildRequestItem(
			@NonNull final PackedHUProductItem productItem,
			@Nullable final ShipmentSchedule schedule)
	{
		final Product product = productRepository.getById(productItem.getProductId());
		final Quantity qty = productItem.getQty();
		final int numberOfItems = qty.intValueExact();

		// Customs tariff — same source as NShiftDraftDeliveryOrderCreator#createDeliveryOrderItem
		final CustomsTariffId customsTariffId = product.getCustomsTariffId();
		final String customsTariff = customsTariffId != null ? customsTariffRepository.getById(customsTariffId).getValue() : null;

		// Total weight in kg — nominal gross weight, same as NShiftDraftDeliveryOrderCreator#computeNominalGrossWeightInKg.
		// This is the per-product nominal weight (productBL.computeGrossWeight → kg), NOT the HU gross weight;
		// the HU gross weight stays on the parcel. Mirrors the nShift ship path for advise/ship parity.
		final BigDecimal totalWeightInKgBD = productBL.computeGrossWeight(product.getId(), qty)
				.map(weight -> uomConversionBL.convertToKilogram(weight, product.getId()))
				.map(Quantity::getAsBigDecimal)
				.orElse(BigDecimal.ZERO);

		// Unit price / total value from THIS product's order line — same source as NShiftDraftDeliveryOrderCreator#createDeliveryOrderItem.
		// Null when no schedule or no order line is available (e.g. inventory-receipt picks).
		JsonMoney unitPrice = null;
		JsonMoney totalValue = null;
		JsonQuantity shippedQuantity = null;
		if (schedule != null)
		{
			final OrderAndLineId orderAndLineId = schedule.getOrderAndLineId();
			if (orderAndLineId != null)
			{
				final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderAndLineId);
				final UomId targetUomId = CoalesceUtil.coalesceNotNull(
						UomId.ofRepoIdOrNull(orderLine.getPrice_UOM_ID()),
						qty.getUomId());
				final Quantity qtyConverted = uomConversionBL.convertQuantityTo(qty, product.getId(), targetUomId);
				final CurrencyId currencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());
				final CurrencyCode currencyCode = currencyDAO.getCurrencyCodeById(currencyId);
				final String currencyISOCode = currencyCode.toThreeLetterCode();
				final Money unitPriceMoney = Money.of(orderLine.getPriceEntered(), currencyId);
				unitPrice = JsonMoney.builder()
						.amount(unitPriceMoney.toBigDecimal())
						.currencyCode(currencyISOCode)
						.build();
				totalValue = JsonMoney.builder()
						.amount(unitPriceMoney.multiply(qtyConverted.toBigDecimal()).toBigDecimal())
						.currencyCode(currencyISOCode)
						.build();
				shippedQuantity = JsonQuantity.builder()
						.value(qtyConverted.toBigDecimal())
						.uomCode(qtyConverted.getX12DE355().getCode())
						.build();
			}
		}

		return JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(numberOfItems)
				.productName(product.getName().getDefaultValue())
				.productValue(product.getValue())
				.countryOfOrigin(productItem.getCountryOfOrigin())
				.customsTariff(customsTariff)
				.unitPrice(unitPrice)
				.totalValue(totalValue)
				.shippedQuantity(shippedQuantity)
				.totalWeightInKg(totalWeightInKgBD)
				.build();
	}

	private static String toTopLevelTypeWireString(@NonNull final HuUnitType huUnitType)
	{
		switch (huUnitType)
		{
			case LU:
				return JsonTopLevelType.LU.getCode();
			case TU:
				return JsonTopLevelType.TU.getCode();
			case VHU:
				return JsonTopLevelType.CU.getCode();
			default:
				throw new AdempiereException("Unexpected HuUnitType: " + huUnitType);
		}
	}
}
