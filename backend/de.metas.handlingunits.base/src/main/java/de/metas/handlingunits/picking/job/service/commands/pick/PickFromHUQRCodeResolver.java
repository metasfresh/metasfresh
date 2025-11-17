package de.metas.handlingunits.picking.job.service.commands.pick;

import de.metas.bpartner.BPartnerId;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13ProductCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.qrcodes.custom.CustomHUQRCode;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.gs1.GS1HUQRCode;
import de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.Objects;

@Builder
class PickFromHUQRCodeResolver
{
	private final static AdMessageKey ERR_NotEnoughTUsFound = AdMessageKey.of("de.metas.handlingunits.picking.job.NOT_ENOUGH_TUS_ERROR_MSG");
	private final static AdMessageKey ERR_LMQ_LotNoNotFound = AdMessageKey.of("de.metas.handlingunits.picking.job.L_M_QR_CODE_ERROR_MSG");
	private final static AdMessageKey ERR_NoLotNoFoundForQRCode = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_EXTERNAL_LOT_ERROR_MSG");
	private final static AdMessageKey ERR_QR_ProductNotMatching = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");

	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobHUService huService;

	@NonNull
	public HUInfo resolve(
			@Nullable final IHUQRCode pickFromHUQRCode,
			@NonNull final ProductId productId,
			@NonNull final BPartnerId customerId,
			@NonNull final WarehouseId warehouseId)
	{
		if (pickFromHUQRCode == null)
		{
			return findFirstByWarehouseAndProduct(warehouseId, productId);
		}
		else if (pickFromHUQRCode instanceof HUQRCode)
		{
			final HUQRCode huQRCode = (HUQRCode)pickFromHUQRCode;
			final HuId huId = huService.getHuIdByQRCode(huQRCode);
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
		final HuId huId = huService.getFirstHuIdByExternalLotNo(lotNumber)
				.orElseThrow(() -> new AdempiereException(ERR_NoLotNoFoundForQRCode)
						.appendParametersToMessage()
						.setParameter("LotNumber", lotNumber));
		return getHUInfoById(huId);
	}

	private HUInfo getHUInfoById(final HuId huId)
	{
		final HUQRCode huQRCode = huService.getQRCodeByHuId(huId);
		return HUInfo.ofHuIdAndQRCode(huId, huQRCode);
	}

	private HUInfo findHUByQRCodeAttribute(
			@NonNull final IHUQRCode scannedQRCode,
			@NonNull final ProductId productId)
	{
		final HuId huId = huService.getFirstHUIdByQRCodeAttribute(scannedQRCode, productId)
				.orElseThrow(() -> new AdempiereException(ERR_NotEnoughTUsFound) // TODO introduce a better AD_Message
						.setParameter("QRCode", scannedQRCode));

		return getHUInfoById(huId);
	}

	private HUInfo findFirstByWarehouseAndProduct(@NonNull final WarehouseId warehouseId, @NonNull final ProductId productId)
	{
		final HuId huId = huService.getFirstHuIdByWarehouseAndProduct(warehouseId, productId)
				.orElseThrow(() -> new AdempiereException(ERR_NotEnoughTUsFound)); // TODO introduce a better AD_Message

		return getHUInfoById(huId);
	}

	private void validateQRCode_GTIN(
			@NonNull final GS1HUQRCode pickFromHUQRCode,
			@NonNull final ProductId expectedProductId)
	{
		final GTIN gtin = pickFromHUQRCode.getGTIN().orElse(null);
		if (gtin != null)
		{
			final ProductId gs1ProductId = productService.getProductIdByGTINStrictlyNotNull(gtin, ClientId.METASFRESH);
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
		final String expectedProductNo = productService.getProductValue(expectedProductId);
		final EAN13 ean13 = pickFromQRCode.unbox();
		final EAN13ProductCode ean13ProductNo = ean13.getProductNo();
		if (!productService.isValidEAN13Product(ean13, expectedProductId, customerId))
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

		final String expectedProductNo = productService.getProductValue(expectedProductId);
		if (!Objects.equals(qrCodeProductNo, expectedProductNo))
		{
			throw new AdempiereException(ERR_QR_ProductNotMatching)
					.setParameter("qrCodeProductNo", qrCodeProductNo)
					.setParameter("expectedProductNo", expectedProductNo)
					.setParameter("expectedProductId", expectedProductId);
		}
	}
}
