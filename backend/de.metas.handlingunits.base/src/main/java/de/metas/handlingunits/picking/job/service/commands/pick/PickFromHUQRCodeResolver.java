package de.metas.handlingunits.picking.job.service.commands.pick;

import de.metas.bpartner.BPartnerId;
import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13ProductCode;
import de.metas.gs1.GTIN;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.qrcodes.custom.CustomHUQRCode;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.gs1.GS1HUQRCode;
import de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.AdMessageKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;

import java.util.Objects;

@Builder
class PickFromHUQRCodeResolver
{
	private final static AdMessageKey ERR_NotEnoughTUsFound = AdMessageKey.of("de.metas.handlingunits.picking.job.NOT_ENOUGH_TUS_ERROR_MSG");
	private final static AdMessageKey ERR_LMQ_LotNoNotFound = AdMessageKey.of("de.metas.handlingunits.picking.job.L_M_QR_CODE_ERROR_MSG");
	private final static AdMessageKey ERR_NoLotNoFoundForQRCode = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_EXTERNAL_LOT_ERROR_MSG");
	private final static AdMessageKey ERR_QR_ProductNotMatching = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");

	@NonNull private final IProductBL productBL;
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final IWarehouseBL warehouseBL;

	@NonNull
	public HUInfo resolve(
			@NonNull final IHUQRCode pickFromHUQRCode,
			@NonNull final ProductId productId,
			@NonNull final BPartnerId customerId,
			@NonNull final WarehouseId warehouseId)
	{
		if (pickFromHUQRCode instanceof HUQRCode)
		{
			final HUQRCode huQRCode = (HUQRCode)pickFromHUQRCode;
			final HuId huId = huQRCodesService.getHuIdByQRCode(huQRCode);
			return HUInfo.ofHuIdAndQRCode(huId, huQRCode);
		}
		else if (pickFromHUQRCode instanceof LMQRCode)
		{
			final LMQRCode lmQRCode = (LMQRCode)pickFromHUQRCode;
			return findHUByExternalLotNo(lmQRCode);
		}
		else if (pickFromHUQRCode instanceof GS1HUQRCode)
		{
			final GS1HUQRCode gs1QRCode = (GS1HUQRCode)pickFromHUQRCode;
			validateQRCode_GTIN(gs1QRCode, productId);
			return findHUByExternalLotNo(gs1QRCode);
		}
		else if (pickFromHUQRCode instanceof EAN13HUQRCode)
		{
			final EAN13HUQRCode ean13QRCode = (EAN13HUQRCode)pickFromHUQRCode;
			validateQRCode_EAN13ProductNo(ean13QRCode, productId, customerId);
			return findFirstByWarehouseAndProduct(warehouseId, productId);
		}
		else if (pickFromHUQRCode instanceof CustomHUQRCode)
		{
			final CustomHUQRCode customQRCode = (CustomHUQRCode)pickFromHUQRCode;
			validateQRCode_ProductNo(customQRCode, productId);
			return findHUByQRCodeAttribute(pickFromHUQRCode, productId);
		}
		else
		{
			throw new AdempiereException("Unknown QR code type: " + pickFromHUQRCode); // TODO trl 
		}
	}

	private HUInfo findHUByExternalLotNo(final IHUQRCode pickFromHUQRCode)
	{
		final String lotNumber = pickFromHUQRCode.getLotNumber()
				.orElseThrow(() -> new AdempiereException(ERR_LMQ_LotNoNotFound)
						.setParameter("pickFromHUQRCode", pickFromHUQRCode));
		final HuId huId = handlingUnitsBL.getFirstHuIdByExternalLotNo(lotNumber)
				.orElseThrow(() -> new AdempiereException(ERR_NoLotNoFoundForQRCode)
						.appendParametersToMessage()
						.setParameter("LotNumber", lotNumber));
		return getHUInfoById(huId);
	}

	private HUInfo getHUInfoById(final HuId huId)
	{
		final HUQRCode huQRCode = huQRCodesService.getQRCodeByHuId(huId);
		return HUInfo.ofHuIdAndQRCode(huId, huQRCode);
	}

	private HUInfo findHUByQRCodeAttribute(
			@NonNull final IHUQRCode scannedQRCode,
			@NonNull final ProductId productId)
	{
		final HuId huId = handlingUnitsBL.createHUQueryBuilder()
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.setOnlyTopLevelHUs()
				.addOnlyWithProductId(productId)
				.addOnlyWithAttribute(HUAttributeConstants.ATTR_QRCode, scannedQRCode.getAsString())
				.setExcludeReserved()
				.firstId()
				.orElseThrow(() -> new AdempiereException(ERR_NotEnoughTUsFound) // TODO introduce a better AD_Message
						.setParameter("QRCode", scannedQRCode));

		return getHUInfoById(huId);
	}

	private HUInfo findFirstByWarehouseAndProduct(@NonNull final WarehouseId warehouseId, @NonNull final ProductId productId)
	{
		final HuId huId = handlingUnitsBL.createHUQueryBuilder()
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.setOnlyTopLevelHUs()
				.addOnlyWithProductId(productId)
				.addOnlyInLocatorIds(warehouseBL.getLocatorIdsOfTheSamePickingGroup(warehouseId))
				.setExcludeReserved()
				.firstId()
				.orElseThrow(() -> new AdempiereException(ERR_NotEnoughTUsFound)) // TODO introduce a better AD_Message
				;

		return getHUInfoById(huId);
	}

	private void validateQRCode_GTIN(
			@NonNull final GS1HUQRCode pickFromHUQRCode,
			@NonNull final ProductId expectedProductId)
	{
		final GTIN gtin = pickFromHUQRCode.getGTIN().orElse(null);
		if (gtin != null)
		{
			final ProductId gs1ProductId = productBL.getProductIdByGTINNotNull(gtin, ClientId.METASFRESH);
			if (!ProductId.equals(expectedProductId, gs1ProductId))
			{
				throw new AdempiereException(ERR_QR_ProductNotMatching)
						.setParameter("GTIN", gtin)
						.setParameter("expected", expectedProductId)
						.setParameter("actual", gs1ProductId);
			}
		}
	}

	private void validateQRCode_EAN13ProductNo(
			@NonNull final EAN13HUQRCode pickFromQRCode,
			@NonNull final ProductId expectedProductId,
			@NonNull final BPartnerId customerId)
	{
		final String expectedProductNo = productBL.getProductValue(expectedProductId);
		final EAN13 ean13 = pickFromQRCode.unbox();
		final EAN13ProductCode ean13ProductNo = ean13.getProductNo();
		if (!productBL.isValidEAN13Product(ean13, expectedProductId, customerId))
		{
			throw new AdempiereException(ERR_QR_ProductNotMatching)
					.setParameter("ean13ProductNo", ean13ProductNo)
					.setParameter("expectedProductNo", expectedProductNo)
					.setParameter("expectedProductId", expectedProductId);
		}
	}

	private void validateQRCode_ProductNo(
			@NonNull final CustomHUQRCode customQRCode,
			@NonNull final ProductId expectedProductId)
	{
		final String qrCodeProductNo = customQRCode.getProductNo().orElse(null);
		if (qrCodeProductNo == null) {return;}

		final String expectedProductNo = productBL.getProductValue(expectedProductId);
		if (!Objects.equals(qrCodeProductNo, expectedProductNo))
		{
			throw new AdempiereException(ERR_QR_ProductNotMatching)
					.setParameter("qrCodeProductNo", qrCodeProductNo)
					.setParameter("expectedProductNo", expectedProductNo)
					.setParameter("expectedProductId", expectedProductId);
		}
	}
}
