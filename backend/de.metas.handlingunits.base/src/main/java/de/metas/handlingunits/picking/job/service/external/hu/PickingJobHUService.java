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
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.ReservedHUsPolicy;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.grai.DummyGRAIProvider;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.grai.HUGraiSnapshotsCollection;
import de.metas.handlingunits.grai.HUPIGraiRepository;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
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
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
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
	public static PickingJobHUService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				PickingJobHUService.class,
				() -> new PickingJobHUService(
						MobileUIPickingUserProfileService.newInstanceForUnitTesting(),
						new PickingJobWarehouseService(new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository())),
						PickingJobProductService.newInstanceForUnitTesting(),
						HUQRCodesService.newInstanceForUnitTesting(),
						HULabelService.newInstanceForUnitTesting(),
						new HUReservationService(new HUReservationRepository()),
						InventoryService.newInstanceForUnitTesting(),
						new HUGraiService(new HUPIGraiRepository())));
	}

	private static final AdMessageKey MSG_GRAI_ATTRIBUTE_NOT_SUPPORTED_BY_TU_TYPE = AdMessageKey.of("de.metas.handlingunits.picking.GRAIAttributeNotSupportedByTUType");

	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	@NonNull private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	@NonNull private final IHUPIAttributesDAO huPIAttributesDAO = Services.get(IHUPIAttributesDAO.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	@NonNull private final MobileUIPickingUserProfileService configService;
	@NonNull private final PickingJobWarehouseService warehouseService;
	@NonNull private final PickingJobProductService productService;
	@NonNull @Getter private final HUQRCodesService huQRCodesService;
	@NonNull private final HULabelService huLabelService;
	@NonNull private final HUReservationService huReservationService;
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUGraiService huGraiService;

	@NonNull
	public HUGraiSnapshotsCollection getGraiSnapshots(@NonNull final Set<HuId> huIds)
	{
		return huGraiService.getSnapshots(huIds);
	}

	public void generateMissingGRAIs(@NonNull final HUGraiSnapshotsCollection snapshots, @NonNull final DummyGRAIProvider nextGraiProvider)
	{
		huGraiService.generateMissingGRAIs(snapshots, nextGraiProvider);
	}

	public void setGrais(@NonNull final HuId huId, @NonNull final GRAISet graiSet) {huGraiService.setGrais(huId, graiSet);}

	/**
	 * Returns the TU packing instruction configured for the given GRAI (matched by company-prefix and asset-type).
	 *
	 * @throws AdempiereException keyed on {@code de.metas.handlingunits.picking.GRAINoMatchingTUType}
	 *                            when no active GRAI-to-TU mapping exists for the given GRAI.
	 */
	@NonNull
	public HuPackingInstructionsId resolveHuPackingInstructionsId(@NonNull final GRAI grai)
	{
		return huGraiService.resolveHuPackingInstructionsId(grai);
	}

	/**
	 * Fail-loud guard for the GRAI-scan flow: the resolved TU type's <i>current</i> PI version must declare the
	 * {@code GRAI} HU-attribute slot. Without it, a materialised TU built from this type has no slot to store the
	 * scanned GRAI; the GRAI would be silently dropped and only surface as a confusing GRAI_COUNT_MISMATCH at pick
	 * completion. Throwing here surfaces the misconfiguration immediately at scan time.
	 *
	 * @param tuPIId the resolved TU packing-instruction id.
	 * @param tuPI   the resolved TU packing instruction (used only for the error message caption).
	 * @throws AdempiereException (keyed {@code GRAIAttributeNotSupportedByTUType}) if the GRAI attribute is not
	 *         defined in the system, or the TU type's current PI version does not declare the GRAI slot.
	 */
	public void assertTUTypeSupportsGraiAttribute(
			@NonNull final HuPackingInstructionsId tuPIId,
			@NonNull final I_M_HU_PI tuPI)
	{
		// Two ways the scanned GRAI cannot be honoured, both reported with the same message:
		//  - the GRAI M_Attribute is not defined in this system at all (graiAttributeId == null), or
		//  - it exists but this TU type's current PI version does not declare the slot.
		final AttributeId graiAttributeId = attributeDAO.retrieveActiveAttributeIdByValueOrNull(AttributeConstants.ATTR_GRAI);
		if (graiAttributeId != null)
		{
			final HuPackingInstructionsVersionId tuPIVersionId = retrievePICurrentVersionId(tuPIId);
			if (huPIAttributesDAO.retrievePIAttributes(tuPIVersionId).hasActiveAttribute(graiAttributeId))
			{
				return;
			}
		}

		throw new AdempiereException(MSG_GRAI_ATTRIBUTE_NOT_SUPPORTED_BY_TU_TYPE, tuPI.getName());
	}

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

	public Optional<IAttributeValue> getAttributeValueIfExists(@NonNull final I_M_HU hu, @NonNull final AttributeCode attributeCode)
	{
		return huAttributesBL.getAttributeValueIfExists(hu, attributeCode);
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

	public HuPackingInstructionsVersionId retrievePICurrentVersionId(@NonNull final HuPackingInstructionsId piId) {return handlingUnitsBL.retrievePICurrentVersionId(piId);}

	public I_M_HU_PI_Version retrievePICurrentVersion(@NonNull final HuPackingInstructionsId piId) {return handlingUnitsBL.retrievePICurrentVersion(piId);}

	public I_M_HU_PI_Item retrievePIItemMaterial(@NonNull final I_M_HU_PI_Version version) {return handlingUnitsBL.retrievePIItemMaterial(version);}

	public Optional<I_M_HU_PI_Item> retrieveFirstPIItem(
			@NonNull final HuPackingInstructionsId piId,
			@NonNull final HuPackingInstructionsId includedPIId,
			@Nullable final BPartnerId bpartnerId)
	{
		return handlingUnitsBL.retrieveFirstPIItem(piId, includedPIId, bpartnerId);
	}

	public List<I_M_HU_PI_Item_Product> retrievePIMaterialItemProducts(@NonNull final I_M_HU_PI_Item itemDef) {return huPIItemProductBL.retrievePIMaterialItemProducts(itemDef);}

	public HuPackingInstructionsId getPackingInstructionsId(@NonNull final I_M_HU hu) {return handlingUnitsBL.getPackingInstructionsId(hu);}

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
		return huPIItemProductBL.retrieveForProducts(productIdSet, partnerId);
	}

	public HUPIItemProduct getPackingInfo(@NonNull final HUPIItemProductId huPIItemProductId)
	{
		return huPIItemProductBL.getById(huPIItemProductId);
	}

	public IHUQRCode parsePickFromScannedCode(final ScannedCode pickFromScannedCode) {return huQRCodesService.parse(pickFromScannedCode);}

	public ExplainedOptional<HUInfo> resolvePickFromHUQRCode(
			@Nullable final IHUQRCode pickFromHUQRCode,
			@NonNull final ProductId productId,
			@NonNull final BPartnerId customerId,
			@NonNull final WarehouseId warehouseId)
	{
		return PickFromHUQRCodeResolveCommand.builder()
				.huService(this)
				.productService(productService)
				.pickFromHUQRCode(pickFromHUQRCode)
				.productId(productId)
				.customerId(customerId)
				.warehouseId(warehouseId)
				//
				.build().execute();
	}

	public HUQRCode getQRCodeByHuId(@NonNull final HuId huId) {return huQRCodesService.getQRCodeByHuId(huId);}

	public List<HUQRCode> getOrCreateQRCodesByHuId(@NonNull final HuId huId) {return huQRCodesService.getOrCreateQRCodesByHuId(huId);}

	public HuId getHuIdByQRCode(final HUQRCode huQRCode) {return huQRCodesService.getHuIdByQRCode(huQRCode);}

	public Optional<HuId> getHuIdByQRCodeIfExists(final HUQRCode huQRCode) {return huQRCodesService.getHuIdByQRCodeIfExists(huQRCode);}

	public Optional<HuId> getHuIdByQRCodeIncludingInactiveIfExists(final HUQRCode huQRCode) {return huQRCodesService.getHuIdByQRCodeIncludingInactiveIfExists(huQRCode);}

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
		return extractTopLevelCUIfNeeded(pickFromHUId, productId, qtyToPick, ImmutableSet.of());
	}

	public HuId extractTopLevelCUIfNeeded(
			@NonNull final HuId pickFromHUId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToPick,
			@NonNull final ImmutableSet<HuId> allowedReservedVhuIds)
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

		final I_M_HU extractedCU = HUTransformService.builder()
				.allowedReservedVhuIds(allowedReservedVhuIds)
				.build()
				.huToNewSingleCU(HUTransformService.HUsToNewCUsRequest.builder()
						.sourceHU(pickFromHU)
						.productId(productId)
						.qtyCU(qtyToPick)
						//.keepNewCUsUnderSameParent(true) // not needed, our HU is top level anyways
						.reservedVHUsPolicy(allowedReservedVhuIds.isEmpty()
								? ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED
								: ReservedHUsPolicy.onlyNotReservedExceptVhuIds(allowedReservedVhuIds))
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

	public ImmutableSet<HuId> getVHUIdsByDocumentRef(@NonNull final HUReservationDocRef documentRef)
	{
		return huReservationService.getVHUIdsByDocumentRef(documentRef);
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

	public boolean containsProduct(@NonNull final HuId huId, @NonNull ProductId productId)
	{
		return getHUProductStorage(huId, productId)
				.map(IHUProductStorage::getQty)
				.map(Quantity::isPositive)
				.orElse(false);
	}

	private Optional<IHUProductStorage> getHUProductStorage(final @NonNull HuId huId, final @NonNull ProductId productId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		return Optional.ofNullable(storageFactory.getStorage(hu).getProductStorageOrNull(productId));
	}
}
