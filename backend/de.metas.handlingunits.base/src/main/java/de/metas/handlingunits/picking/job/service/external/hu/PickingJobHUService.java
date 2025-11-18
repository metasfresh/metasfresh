package de.metas.handlingunits.picking.job.service.external.hu;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.ReservedHUsPolicy;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.service.external.warehouse.PickingJobWarehouseService;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsSupplier;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.handlingunits.report.labels.HULabelPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickingJobHUService
{
	@NonNull final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	@NonNull private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	@NonNull private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	@NonNull private final MobileUIPickingUserProfileService configService;
	@NonNull private final PickingJobWarehouseService warehouseService;
	@NonNull @Getter private final HUQRCodesService huQRCodesService;
	@NonNull private final HULabelService huLabelService;
	@NonNull private final HUReservationService huReservationService;
	@NonNull private final InventoryService inventoryService;

	public IAutoCloseable temporarySetNewHContextForProcessing()
	{
		return HUContextHolder.temporarySet(createMutableHUContextForProcessing());
	}

	public IMutableHUContext createMutableHUContextForProcessing()
	{
		return handlingUnitsBL.createMutableHUContextForProcessing();
	}

	public I_M_HU getById(@NonNull final HuId huId) {return handlingUnitsBL.getById(huId);}

	public List<I_M_HU> getByIds(@NonNull final Collection<HuId> huIds) {return handlingUnitsBL.getByIds(huIds);}

	public Optional<HuId> getFirstHuIdByExternalLotNo(final String externalLotNo) {return handlingUnitsBL.getFirstHuIdByExternalLotNo(externalLotNo);}

	public Optional<HuId> getFirstHUIdByQRCodeAttribute(
			@NonNull final IHUQRCode scannedQRCode,
			@NonNull final ProductId productId)
	{
		return handlingUnitsBL.createHUQueryBuilder()
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.setOnlyTopLevelHUs()
				.addOnlyWithProductId(productId)
				.addOnlyWithAttribute(HUAttributeConstants.ATTR_QRCode, scannedQRCode.getAsString())
				.setExcludeReserved()
				.firstId();
	}

	public Optional<HuId> getFirstHuIdByWarehouseAndProduct(@NonNull final WarehouseId warehouseId, @NonNull final ProductId productId)
	{
		return handlingUnitsBL.createHUQueryBuilder()
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.setOnlyTopLevelHUs()
				.addOnlyWithProductId(productId)
				.addOnlyInLocatorIds(warehouseService.getLocatorIdsOfTheSamePickingGroup(warehouseId))
				.setExcludeReserved()
				.firstId();
	}

	public LocatorId getLocatorId(@NonNull final HuId huId) {return handlingUnitsBL.getLocatorId(huId);}

	@Nullable
	public IAttributeValue getAttributeValue(@NonNull final I_M_HU hu, @NonNull final AttributeCode attributeCode)
	{
		return huAttributesBL.getAttributeValue(hu, attributeCode);
	}

	public boolean isLoadingUnit(final I_M_HU hu) {return handlingUnitsBL.isLoadingUnit(hu);}

	public boolean isTransportUnit(final I_M_HU hu) {return handlingUnitsBL.isTransportUnit(hu);}

	public boolean isVirtual(final I_M_HU hu) {return handlingUnitsBL.isVirtual(hu);}

	public boolean isDestroyed(final HuId huId) {return handlingUnitsBL.isDestroyed(huId);}

	public boolean isDestroyedOrEmptyStorage(@NonNull final I_M_HU hu) {return handlingUnitsBL.isDestroyedOrEmptyStorage(hu);}

	public void setHUStatusPicked(@NonNull final Collection<I_M_HU> hus) {handlingUnitsBL.setHUStatus(hus, X_M_HU.HUSTATUS_Picked);}

	public void setHUStatusActive(@NonNull final I_M_HU topLevelHU)
	{
		handlingUnitsBL.setHUStatus(topLevelHU, PlainContextAware.newWithThreadInheritedTrx(), X_M_HU.HUSTATUS_Active);
	}

	public HuPackingInstructionsIdAndCaption getEffectivePackingInstructionsIdAndCaption(@NonNull final I_M_HU hu) {return handlingUnitsBL.getEffectivePackingInstructionsIdAndCaption(hu);}

	public I_M_HU_PI getPI(@NonNull final HuPackingInstructionsId id) {return handlingUnitsBL.getPI(id);}

	public Set<HuPackingInstructionsIdAndCaption> getLUPIs(
			@NonNull final ImmutableSet<HuPackingInstructionsItemId> tuPIItemIds,
			@Nullable final BPartnerId bpartnerId)
	{
		return handlingUnitsBL.getLUPIs(tuPIItemIds, bpartnerId);
	}

	public I_M_HU_PI retrievePIDefaultForPicking() {return handlingUnitsBL.retrievePIDefaultForPicking();}

	public ImmutableSet<HuPackingInstructionsIdAndCaption> retrievePIInfo(@NonNull final Collection<HuPackingInstructionsItemId> piItemIds)
	{
		return handlingUnitsBL.retrievePIInfo(piItemIds);
	}

	public List<I_M_HU_PI_Item_Product> getPIItemProducts(@NonNull final Set<ProductId> productIdSet, @Nullable final BPartnerId partnerId)
	{
		return huPIItemProductDAO.retrieveForProducts(productIdSet, partnerId);
	}

	public HUPIItemProduct getPackingInfo(@NonNull final HUPIItemProductId huPIItemProductId)
	{
		return huPIItemProductBL.getById(huPIItemProductId);
	}

	public IHUQRCode parse(final ScannedCode pickFromQRCode) {return huQRCodesService.parse(pickFromQRCode);}

	public HUQRCode getQRCodeByHuId(@NonNull final HuId huId) {return huQRCodesService.getQRCodeByHuId(huId);}

	public List<HUQRCode> getOrCreateQRCodesByHuId(@NonNull final HuId huId) {return huQRCodesService.getOrCreateQRCodesByHuId(huId);}

	public HuId getHuIdByQRCode(final HUQRCode huQRCode) {return huQRCodesService.getHuIdByQRCode(huQRCode);}

	public HuId createInventoryForMissingQty(@NonNull final CreateVirtualInventoryWithQtyReq req) {return inventoryService.createInventoryForMissingQty(req);}

	public PickFromHUsSupplier newPickFromHUsSupplier()
	{
		return PickFromHUsSupplier.builder()
				.huReservationService(huReservationService)
				.considerAttributes(configService.isConsiderAttributes())
				.build();
	}

	public PackToHUsProducer newPackToHUsProducer(@NonNull final PickingJobId pickingJobId)
	{
		return PackToHUsProducer.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.huPIItemProductBL(huPIItemProductBL)
				.uomConversionBL(uomConversionBL)
				.inventoryService(inventoryService)
				.contextPickingJobId(pickingJobId)
				.build();

	}

	public void printLULabels(@NonNull final Collection<HuId> luIds)
	{
		final List<I_M_HU> lus = getByIds(luIds);
		if (lus.isEmpty())
		{
			return;
		}

		huLabelService.print(HULabelPrintRequest.builder()
				.sourceDocType(HULabelSourceDocType.Picking)
				.hus(HUToReportWrapper.ofList(lus))
				.onlyIfAutoPrint(true)
				.failOnMissingLabelConfig(false)
				.build());
	}

	public HuId extractTopLevelCUIfNeeded(
			@NonNull final HuId pickFromHUId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToPick)
	{
		final I_M_HU pickFromHU = handlingUnitsBL.getById(pickFromHUId);

		// Not a top level CU
		if (!handlingUnitsBL.isTopLevel(pickFromHU) || !handlingUnitsBL.isVirtual(pickFromHU))
		{
			return pickFromHUId;
		}

		final Quantity storageQty = handlingUnitsBL.getStorageFactory()
				.getStorage(pickFromHU)
				.getProductStorage(productId)
				.getQty(qtyToPick.getUOM());

		// Nothing to split
		if (storageQty.compareTo(qtyToPick) <= 0)
		{
			return pickFromHUId;
		}

		final I_M_HU extractedCU = HUTransformService.newInstance()
				.huToNewSingleCU(HUTransformService.HUsToNewCUsRequest.builder()
						.sourceHU(pickFromHU)
						.productId(productId)
						.qtyCU(qtyToPick)
						//.keepNewCUsUnderSameParent(true) // not needed, our HU is top level anyways
						.reservedVHUsPolicy(ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED)
						.build());

		return HuId.ofRepoId(extractedCU.getM_HU_ID());
	}

	public void reservePickFromHUs(final PickingJob pickingJob)
	{
		for (final PickingJobLine line : pickingJob.getLines())
		{
			for (final PickingJobStep step : line.getSteps())
			{
				reservePickFromHU(step, pickingJob.getCustomerId());
			}
		}
	}

	private void reservePickFromHU(@NonNull final PickingJobStep step, @Nullable final BPartnerId customerId)
	{
		huReservationService.makeReservation(
						ReserveHUsRequest.builder()
								.customerId(customerId)
								.documentRef(HUReservationDocRef.ofPickingJobStepId(step.getId()))
								.productId(step.getProductId())
								.qtyToReserve(step.getQtyToPick())
								.huId(step.getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHUId())
								.build())
				.orElseThrow(() -> new AdempiereException("Cannot reserve HU for " + step)); // shall not happen
	}

	public void releaseAllReservations(@NonNull final PickingJob pickingJob)
	{
		final ImmutableSet<HUReservationDocRef> reservationDocRefs = pickingJob
				.getLines().stream()
				.flatMap(line -> line.getSteps().stream())
				.map(step -> HUReservationDocRef.ofPickingJobStepId(step.getId()))
				.collect(ImmutableSet.toImmutableSet());

		huReservationService.deleteReservationsByDocumentRefs(reservationDocRefs);
	}

	@Nullable
	public ProductAvailableStocks newAvailableStocksProvider(@NonNull final Workplace workplace)
	{
		final Set<LocatorId> pickFromLocatorIds = warehouseService.getPickFromLocatorIds(workplace);
		if (pickFromLocatorIds.isEmpty())
		{
			return null;
		}

		return ProductAvailableStocks.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.pickFromLocatorIds(pickFromLocatorIds)
				.build();
	}

}
