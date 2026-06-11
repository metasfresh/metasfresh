package de.metas.picking.workflow;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipping.PackedHUShippingInfo;
import de.metas.handlingunits.shipping.PackedHUShippingInfoService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.product.PackageDimensions;
import de.metas.product.Product;
import de.metas.product.ProductRepository;
import de.metas.shipper.gateway.commons.CarrierAdviseCommand;
import de.metas.shipper.gateway.commons.model.CarrierProduct;
import de.metas.shipper.gateway.commons.model.CarrierProductRepository;
import de.metas.shipping.CarrierProductId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Shipper;
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
	@NonNull private final ShipmentScheduleService shipmentScheduleService;
	@NonNull private final ProductRepository productRepository;
	@NonNull private final CarrierProductRepository carrierProductRepository;

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

	public CarrierAdviseTargetInfo resolveTargetInfo(@NonNull final I_M_HU topLevelHU)
	{
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById = resolveSchedulesByIdForHU(topLevelHU);
		if (schedulesById.isEmpty())
		{
			return CarrierAdviseTargetInfo.NONE;
		}

		final ImmutableSet<ShipperId> shipperIds = schedulesById.values().stream()
				.map(ShipmentSchedule::getShipperId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		final Map<ShipperId, I_M_Shipper> shippersById = shipperDAO.getByIds(shipperIds);

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

			final I_M_Shipper shipper = shippersById.get(shipperId);
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

	private ImmutableMap<ShipmentScheduleId, ShipmentSchedule> resolveSchedulesByIdForHU(@NonNull final I_M_HU topLevelHU)
	{
		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords =
				huShipmentScheduleDAO.retrieveQtyPickedNotDeliveredForTopLevelHU(topLevelHU);
		if (qtyPickedRecords.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSet<ShipmentScheduleId> scheduleIds = qtyPickedRecords.stream()
				.map(r -> ShipmentScheduleId.ofRepoId(r.getM_ShipmentSchedule_ID()))
				.collect(ImmutableSet.toImmutableSet());

		return shipmentScheduleService.getByIds(scheduleIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(ShipmentSchedule::getId, s -> s));
	}

	/**
	 * Re-advises the currently open packed LU/TU target for the given picking job line.
	 * Skips shipment schedules whose carrier advising status is Manual.
	 *
	 * @throws AdempiereException if no existing LU target is set on the job line
	 */
	public void advise(
			@NonNull final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId)
	{
		final LUPickingTarget luTarget = pickingJob.getLuPickingTarget(lineId)
				.orElseThrow(() -> new AdempiereException("No LU picking target set"));

		if (!luTarget.isExistingLU())
		{
			throw new AdempiereException("Carrier advise requires an existing (packed) LU, not a new one");
		}

		final I_M_HU topLevelHU = handlingUnitsDAO.getById(luTarget.getLuIdNotNull());

		final JsonDeliveryAdvisorRequestItem item = buildRequestItem(topLevelHU);

		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById = resolveSchedulesByIdForHU(topLevelHU);
		if (schedulesById.isEmpty())
		{
			throw new AdempiereException("No undelivered qty-picked records found for HU " + topLevelHU.getM_HU_ID());
		}

		for (final ShipmentSchedule schedule : schedulesById.values())
		{
			if (schedule.getCarrierAdvisingStatus().isManual())
			{
				continue;
			}
			CarrierAdviseCommand.ofPackedHU(schedule.getId(), item).execute();
		}
	}

	private JsonDeliveryAdvisorRequestItem buildRequestItem(@NonNull final I_M_HU topLevelHU)
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
		final int numberOfItems = singleProductStorage.getQtyInStockingUOM().intValueExact();

		final PackageDimensions dimensions = shippingInfo.getDimensions();
		final BigDecimal grossWeightKgBD = shippingInfo.getWeightInKg() != null
				? shippingInfo.getWeightInKg().toBigDecimal()
				: BigDecimal.ZERO;

		return JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(numberOfItems)
				.productName(product.getName().getDefaultValue())
				.productValue(product.getValue())
				.grossWeightKg(grossWeightKgBD)
				.packageDimensions(JsonPackageDimensions.builder()
						.heightInCM(dimensions.getHeightInCM())
						.widthInCM(dimensions.getWidthInCM())
						.lengthInCM(dimensions.getLengthInCM())
						.build())
				.topLevelType(shippingInfo.getTopLevelType())
				.countryOfOrigin(shippingInfo.getCountryOfOrigin())
				.build();
	}
}
