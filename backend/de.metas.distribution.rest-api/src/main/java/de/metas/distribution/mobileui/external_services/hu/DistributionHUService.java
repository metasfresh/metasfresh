package de.metas.distribution.mobileui.external_services.hu;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistributionHUService
{
	private static final AdMessageKey PRODUCT_DOES_NOT_MATCH = AdMessageKey.of("de.metas.distribution.workflows_api.ProductDoesNotMatch");

	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPIItemProductBL hupiItemProductBL = Services.get(IHUPIItemProductBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUReservationService huReservationService;

	@NonNull
	public HUInfo getHUInfoById(@NonNull HuId huId)
	{
		return HUInfo.builder()
				.id(huId)
				.qrCode(getQRCodeByHuId(huId))
				.build();
	}

	@NonNull
	public HUQRCode getQRCodeByHuId(@NonNull final HuId huId) {return huQRCodesService.getQRCodeByHuId(huId);}

	@NonNull
	public HUQRCode resolveHUQRCode(@NonNull final ScannedCode scannedCode)
	{
		final IHUQRCode parsedHUQRCode = huQRCodesService.parse(scannedCode);
		if (parsedHUQRCode instanceof HUQRCode)
		{
			return (HUQRCode)parsedHUQRCode;
		}
		else
		{
			throw new AdempiereException("Cannot convert " + scannedCode + " to actual HUQRCode")
					.setParameter("parsedHUQRCode", parsedHUQRCode)
					.setParameter("parsedHUQRCode type", parsedHUQRCode.getClass().getSimpleName());
		}
	}

	public HuId resolveHUId(@NonNull final ScannedCode scannedCode)
	{
		final HUQRCode huQRCode = resolveHUQRCode(scannedCode);
		return huQRCodesService.getHuIdByQRCode(huQRCode);
	}

	public PackToHUsProducer newPackToHUsProducer()
	{
		return PackToHUsProducer.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.huPIItemProductBL(hupiItemProductBL)
				.uomConversionBL(uomConversionBL)
				.inventoryService(inventoryService)
				.build();
	}

	public IAutoCloseable newContext()
	{
		return HUContextHolder.temporarySet(handlingUnitsBL.createMutableHUContextForProcessing());
	}

	public ProductId getSingleProductId(@NonNull final HUQRCode huQRCode)
	{
		final HuId huId = huQRCodesService.getHuIdByQRCode(huQRCode);
		return getSingleHUProductStorage(huId).getProductId();
	}

	public IHUProductStorage getSingleHUProductStorage(@NonNull final HuId huId)
	{
		return handlingUnitsBL.getSingleHUProductStorage(huId);
	}

	public Quantity getProductQuantity(@NonNull final HuId huId, @NonNull ProductId productId)
	{
		return getHUProductStorage(huId, productId)
				.map(IHUProductStorage::getQty)
				.filter(Quantity::isPositive)
				.orElseThrow(() -> new AdempiereException(PRODUCT_DOES_NOT_MATCH));
	}

	private Optional<IHUProductStorage> getHUProductStorage(final @NotNull HuId huId, final @NotNull ProductId productId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		return Optional.ofNullable(storageFactory.getStorage(hu).getProductStorageOrNull(productId));
	}

	public void assetHUContainsProduct(@NonNull final HUQRCode huQRCode, @NonNull final ProductId productId)
	{
		final HuId huId = huQRCodesService.getHuIdByQRCode(huQRCode);
		getProductQuantity(huId, productId); // shall throw exception if no qty found
	}

	public void deleteReservationsByDocumentRefs(final ImmutableSet<HUReservationDocRef> huReservationDocRefs)
	{
		huReservationService.deleteReservationsByDocumentRefs(huReservationDocRefs);
	}
}
