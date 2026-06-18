package de.metas.picking.workflow;

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
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.shipping.PackedHUShippingInfo;
import de.metas.handlingunits.shipping.PackedHUShippingInfoService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.product.PackageDimensions;
import de.metas.product.Product;
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
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

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

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
	 * Re-advises all packed top-level HUs for the given picking job / line.
	 * Covers LU, standalone-TU, and CU self-packed picks — any HU that was picked
	 * on the job (or on the specific line when {@code lineId} is non-null) is resolved
	 * to its top-level HU and re-advised.
	 * Skips shipment schedules whose carrier advising status is Manual.
	 * <p>
	 * Top-level resolution (getById → getTopLevelParentAsLUTUCUPair → getTopLevelHU) and
	 * deduplication by HuId must be kept in sync with
	 * {@link CarrierAdviseConsistencyService#assertConsistentForJob}.
	 */
	public void advise(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId)
	{
		final ImmutableSet<HuId> pickedHuIds = pickingJob.getPickedHuIds(lineId);
		if (pickedHuIds.isEmpty())
		{
			return;
		}

		// Resolve to top-level HUs and deduplicate by HuId
		// (two picked HUs sharing the same top-level LU can yield distinct I_M_HU instances)
		// Keep in sync with CarrierAdviseConsistencyService#assertConsistentForJob
		final ImmutableMap<HuId, I_M_HU> topLevelHUsById = pickedHuIds.stream()
				.map(handlingUnitsDAO::getById)
				.map(hu -> handlingUnitsBL.getTopLevelParentAsLUTUCUPair(hu).getTopLevelHU())
				.collect(ImmutableMap.toImmutableMap(
						hu -> HuId.ofRepoId(hu.getM_HU_ID()),
						hu -> hu,
						(existing, ignored) -> existing));

		for (final I_M_HU topLevelHU : topLevelHUsById.values())
		{
			final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById = huShipmentScheduleResolver.resolveSchedulesByIdForHU(topLevelHU);

			// Use the first schedule's order line as the price source (single-product HU enforced in buildRequestParcel).
			final ShipmentSchedule firstSchedule = schedulesById.values().stream().findFirst().orElse(null);
			final JsonDeliveryAdvisorRequestParcel parcel = buildRequestParcel(topLevelHU, firstSchedule);

			for (final ShipmentSchedule schedule : schedulesById.values())
			{
				if (schedule.getCarrierAdvisingStatus().isManual())
				{
					continue;
				}
				// executeSync (not execute): re-advise against the packed HU regardless of the schedule's
				// current advising status — at packing time it is typically already Completed from the
				// auto-advise at order completion, so the Requested-only execute() guard would no-op.
				CarrierAdviseCommand.ofPackedHU(schedule.getId(), parcel).executeSync();
			}
		}
	}

	// Carrier "final info" build path — HU-advise (1 of 3).
	// Field derivation MUST stay consistent across the three nShift build paths (change together):
	//   - HU-advise:        PackedHUCarrierAdviseService#buildRequestParcel
	//   - schedule-advise:  CarrierAdviseCommand#getJsonDeliveryAdvisorRequestParcel
	//   - delivery-order:   NShiftDraftDeliveryOrderCreator#createDeliveryOrderItem
	// Shared advise line-building: NShiftUtil#buildAdvisorLine.
	private JsonDeliveryAdvisorRequestParcel buildRequestParcel(
			@NonNull final I_M_HU topLevelHU,
			@Nullable final ShipmentSchedule schedule)
	{
		final PackedHUShippingInfo shippingInfo = packedHUShippingInfoService.of(topLevelHU);

		final List<IHUProductStorage> productStorages = handlingUnitsBL
				.getStorageFactory()
				.getProductStorages(topLevelHU);

		if (productStorages.isEmpty())
		{
			throw new AdempiereException("HU " + topLevelHU.getM_HU_ID() + " has no product storage");
		}
		if (productStorages.size() > 1)
		{
			throw new AdempiereException("Carrier advise for multi-product HUs is not supported. HU_ID=" + topLevelHU.getM_HU_ID());
		}
		final IHUProductStorage singleProductStorage = productStorages.get(0);

		final Product product = productRepository.getById(singleProductStorage.getProductId());
		final Quantity qty = singleProductStorage.getQtyInStockingUOM();
		final int numberOfItems = qty.intValueExact();

		final PackageDimensions dimensions = shippingInfo.getDimensions();
		final BigDecimal grossWeightKgBD = shippingInfo.getWeightInKg() != null
				? shippingInfo.getWeightInKg().toBigDecimal()
				: BigDecimal.ZERO;

		// Customs tariff — same source as NShiftDraftDeliveryOrderCreator#createDeliveryOrderItem
		final CustomsTariffId customsTariffId = product.getCustomsTariffId();
		final String customsTariff = customsTariffId != null ? customsTariffRepository.getById(customsTariffId).getValue() : null;

		// Unit price / total value from order line — same source as NShiftDraftDeliveryOrderCreator#createDeliveryOrderItem.
		// Null when no schedule or no order line is available (e.g. inventory-receipt picks).
		JsonMoney unitPrice = null;
		JsonMoney totalValue = null;
		BigDecimal totalWeightInKg = null;
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
			// Total weight in kg — nominal gross weight, same as NShiftDraftDeliveryOrderCreator#computeNominalGrossWeightInKg
			totalWeightInKg = grossWeightKgBD;
		}

		final JsonDeliveryAdvisorRequestItem item = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(numberOfItems)
				.productName(product.getName().getDefaultValue())
				.productValue(product.getValue())
				.countryOfOrigin(shippingInfo.getCountryOfOrigin())
				.customsTariff(customsTariff)
				.unitPrice(unitPrice)
				.totalValue(totalValue)
				.shippedQuantity(shippedQuantity)
				.totalWeightInKg(totalWeightInKg)
				.build();

		return JsonDeliveryAdvisorRequestParcel.builder()
				.grossWeightKg(grossWeightKgBD)
				.packageDimensions(JsonPackageDimensions.builder()
						.heightInCM(dimensions.getHeightInCM())
						.widthInCM(dimensions.getWidthInCM())
						.lengthInCM(dimensions.getLengthInCM())
						.build())
				.topLevelType(toTopLevelTypeWireString(shippingInfo.getTopLevelType()))
				.items(ImmutableList.of(item))
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
