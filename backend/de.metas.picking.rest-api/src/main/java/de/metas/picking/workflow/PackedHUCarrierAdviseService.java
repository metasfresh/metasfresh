package de.metas.picking.workflow;

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
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackedHUCarrierAdviseService
{
	@NonNull private final PackedHUShippingInfoService packedHUShippingInfoService;
	@NonNull private final ShipmentScheduleService shipmentScheduleService;
	@NonNull private final ProductRepository productRepository;

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);

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

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords =
				huShipmentScheduleDAO.retrieveQtyPickedNotDeliveredForTopLevelHU(topLevelHU);

		if (qtyPickedRecords.isEmpty())
		{
			throw new AdempiereException("No undelivered qty-picked records found for HU " + topLevelHU.getM_HU_ID());
		}

		for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : qtyPickedRecords)
		{
			final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(qtyPicked.getM_ShipmentSchedule_ID());
			final ShipmentSchedule schedule = shipmentScheduleService.getById(scheduleId);
			if (schedule.getCarrierAdvisingStatus().isManual())
			{
				continue;
			}
			CarrierAdviseCommand.ofPackedHU(scheduleId, item).execute();
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
		final IHUProductStorage singleProductStorage = productStorages.get(0);

		final Product product = productRepository.getById(singleProductStorage.getProductId());
		final int numberOfItems = singleProductStorage.getQtyInStockingUOM().intValueExact();

		final PackageDimensions dimensions = shippingInfo.getDimensions();
		final BigDecimal grossWeightKg = shippingInfo.getWeightInKg() != null
				? shippingInfo.getWeightInKg().toBigDecimal()
				: BigDecimal.ZERO;

		return JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(numberOfItems)
				.productName(product.getName().getDefaultValue())
				.productValue(product.getValue())
				.grossWeightKg(grossWeightKg)
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
