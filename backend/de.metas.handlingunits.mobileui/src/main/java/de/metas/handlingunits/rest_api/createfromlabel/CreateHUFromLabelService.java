package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.IProductBL;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/** HU Manager "create HU from a scanned label": the preview (writes nothing) and OK (one single-HU inventory, one CU). */
@Service
@RequiredArgsConstructor
public class CreateHUFromLabelService
{
	// services of the commands (resolved once here and handed to each command)
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final HUQtyService huQtyService;
	@NonNull private final LabelProductResolver labelProductResolver;
	@NonNull private final LabelTargetLocatorResolver labelTargetLocatorResolver;

	/** @return the command with the services set; the caller sets the scanned code, client, org, user and language, then {@code build().execute()} */
	@NonNull
	public ProposeHUFromScannedCodeCommand.ProposeHUFromScannedCodeCommandBuilder propose()
	{
		return ProposeHUFromScannedCodeCommand.builder()
				.productBL(productBL)
				.attributeDAO(attributeDAO)
				.warehouseDAO(warehouseDAO)
				.labelProductResolver(labelProductResolver)
				.labelTargetLocatorResolver(labelTargetLocatorResolver);
	}

	/** @return the new HU, with its HU QR code created (so the standard actions, Move etc., work on it) */
	@NonNull
	public CreatedHU create(@NonNull final JsonCreateHUFromScannedCodeRequest request, @NonNull final ClientId clientId, @NonNull final String adLanguage, @NonNull final ZonedDateTime date)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			final HuId huId = CreateHUFromScannedCodeCommand.builder()
					.handlingUnitsBL(handlingUnitsBL)
					.uomConversionBL(uomConversionBL)
					.warehouseDAO(warehouseDAO)
					.attributeDAO(attributeDAO)
					.huQtyService(huQtyService)
					.labelProductResolver(labelProductResolver)
					.clientId(clientId)
					.adLanguage(adLanguage)
					.scannedCode(request.getScannedCode())
					.qty(request.getQty())
					.locatorId(LocatorQRCode.ofScannedCode(request.getLocatorQRCode()).getLocatorId())
					.date(date)
					.build()
					.execute();
			final HUQRCode qrCode = huQRCodesService.getQRCodeByHuId(huId); // creates the QR code if missing, like the HU lookup by value does
			return new CreatedHU(huId, qrCode);
		});
	}

	@Value
	public static class CreatedHU
	{
		@NonNull HuId huId;
		@NonNull HUQRCode qrCode;
	}
}
