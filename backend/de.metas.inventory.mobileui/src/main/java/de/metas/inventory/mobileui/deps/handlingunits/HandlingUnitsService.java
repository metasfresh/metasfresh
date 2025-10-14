package de.metas.inventory.mobileui.deps.handlingunits;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HandlingUnitsService
{
	@NonNull final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	@NonNull private final HUQRCodesService huQRCodesService;

	public HULoadingCache newLoadingCache()
	{
		return HULoadingCache.builder()
				.huService(this)
				.build();
	}

	public I_M_HU getById(HuId huId) {return handlingUnitsBL.getById(huId);}

	public HUProductStorages getProductStorages(final I_M_HU hu)
	{
		final List<IHUProductStorage> productStorages = handlingUnitsBL.getStorageFactory().getProductStorages(hu);
		return new HUProductStorages(productStorages);
	}

	public Quantity getQty(final HuId huId, final ProductId productId)
	{
		final I_M_HU hu = getById(huId);
		return getProductStorages(hu).getQty(productId);
	}

	public ImmutableAttributeSet getImmutableAttributeSet(final I_M_HU hu) {return handlingUnitsBL.getImmutableAttributeSet(hu);}

	public IHUQRCode parse(final @NonNull ScannedCode scannedCode)
	{
		return huQRCodesService.parse(scannedCode);
	}

	public HuId getHuIdByQRCode(final @NonNull HUQRCode huQRCode)
	{
		return huQRCodesService.getHuIdByQRCode(huQRCode);
	}

	public @NonNull Attribute getAttribute(final AttributeCode attributeCode)
	{
		return attributeDAO.getAttributeByCode(attributeCode);
	}

	public String getDisplayName(final I_M_HU hu)
	{
		return handlingUnitsBL.getDisplayName(hu);
	}

}
