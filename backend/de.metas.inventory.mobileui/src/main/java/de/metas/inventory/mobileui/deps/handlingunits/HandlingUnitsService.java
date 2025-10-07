package de.metas.inventory.mobileui.deps.handlingunits;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HandlingUnitsService
{
	@NonNull final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final HUQRCodesService huQRCodesService;

	public I_M_HU getById(HuId huId) {return handlingUnitsBL.getById(huId);}

	public List<IHUProductStorage> getProductStorages(final I_M_HU hu) {return handlingUnitsBL.getStorageFactory().getProductStorages(hu);}

	public ImmutableAttributeSet getImmutableAttributeSet(final I_M_HU hu) {return handlingUnitsBL.getImmutableAttributeSet(hu);}

	public IHUQRCode parse(final @NonNull ScannedCode scannedCode)
	{
		return huQRCodesService.parse(scannedCode);
	}

	public HuId getHuIdByQRCode(final @NonNull HUQRCode huQRCode)
	{
		return huQRCodesService.getHuIdByQRCode(huQRCode);
	}
}
