package de.metas.handlingunits.picking.job.service.external.hu;

import de.metas.bpartner.BPartnerId;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.qrcodes.custom.CustomHUQRCode;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.gs1.GS1HUQRCode;
import de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.Objects;

@Builder
class PickFromHUQRCodeResolveCommand
{
	private final static AdMessageKey ERR_NotEnoughTUsFound = AdMessageKey.of("de.metas.handlingunits.picking.job.NOT_ENOUGH_TUS_ERROR_MSG");
	private final static AdMessageKey ERR_LMQ_LotNoNotFound = AdMessageKey.of("de.metas.handlingunits.picking.job.L_M_QR_CODE_ERROR_MSG");
	private final static AdMessageKey ERR_NoLotNoFoundForQRCode = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_EXTERNAL_LOT_ERROR_MSG");
	public final static AdMessageKey ERR_QR_ProductNotMatching = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");
	public final static AdMessageKey ERR_QR_HU_Destroyed = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_HU_DESTROYED_ERROR_MSG");

	// services
	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobHUService huService;

	// params
	@Nullable final IHUQRCode pickFromHUQRCode;
	@NonNull final ProductId productId;
	@NonNull final BPartnerId customerId;
	@NonNull final WarehouseId warehouseId;

	@NonNull
	public ExplainedOptional<HUInfo> execute()
	{
		if (pickFromHUQRCode == null)
		{
			return findFirstByWarehouseAndProduct(warehouseId, productId);
		}
		else if (pickFromHUQRCode instanceof HUQRCode)
		{
			final HUQRCode huQRCode = (HUQRCode)pickFromHUQRCode;
			final HuId huId = huService.getHuIdByQRCode(huQRCode);
			// Destroyed HUs have empty storage, so containsProduct below would give a misleading "product not matching" error.
			if (huService.isDestroyed(huId))
			{
				return ExplainedOptional.emptyBecause(ERR_QR_HU_Destroyed);
			}
			if (!huService.containsProduct(huId, productId))
			{
				return ExplainedOptional.emptyBecause(
						TranslatableStrings.adMessage(ERR_QR_ProductNotMatching, productService.getProductNameTrl(productId)));
			}

			return ExplainedOptional.of(HUInfo.ofHuIdAndQRCode(huId, huQRCode));
		}
		else if (pickFromHUQRCode instanceof LMQRCode)
		{
			final LMQRCode lmQRCode = (LMQRCode)pickFromHUQRCode;
			return findHUByExternalLotNo(lmQRCode);
		}
		else if (pickFromHUQRCode instanceof GS1HUQRCode)
		{
			final GS1HUQRCode gs1QRCode = (GS1HUQRCode)pickFromHUQRCode;
			final BooleanWithReason valid = validateQRCode_GTIN(gs1QRCode, productId);
			if (valid.isFalse())
			{
				return ExplainedOptional.emptyBecause(valid.getReason());
			}

			return findHUByExternalLotNo(gs1QRCode);
		}
		else if (pickFromHUQRCode instanceof EAN13HUQRCode)
		{
			final EAN13HUQRCode ean13QRCode = (EAN13HUQRCode)pickFromHUQRCode;
			final BooleanWithReason valid = validateQRCode_EAN13ProductNo(ean13QRCode, productId, customerId);
			if (valid.isFalse())
			{
				return ExplainedOptional.emptyBecause(valid.getReason());
			}

			return findFirstByWarehouseAndProduct(warehouseId, productId);
		}
		else if (pickFromHUQRCode instanceof CustomHUQRCode)
		{
			final CustomHUQRCode customQRCode = (CustomHUQRCode)pickFromHUQRCode;
			final BooleanWithReason valid = validateQRCode_ProductNo(customQRCode, productId);
			if (valid.isFalse())
			{
				return ExplainedOptional.emptyBecause(valid.getReason());
			}
			return findHUByQRCodeAttribute(pickFromHUQRCode, productId);
		}
		else
		{
			throw new AdempiereException("Unknown QR code type: " + pickFromHUQRCode); // TODO trl 
		}
	}

	private ExplainedOptional<HUInfo> findHUByExternalLotNo(final IHUQRCode pickFromHUQRCode)
	{
		final String lotNumber = pickFromHUQRCode.getLotNumber().orElse(null);
		if (lotNumber == null)
		{
			return ExplainedOptional.emptyBecause(ERR_LMQ_LotNoNotFound);
		}

		final HuId huId = huService.getFirstHuIdByExternalLotNo(lotNumber).orElse(null);
		if (huId == null)
		{
			return ExplainedOptional.emptyBecause(ERR_NoLotNoFoundForQRCode);
		}

		return getHUInfoById(huId);
	}

	private ExplainedOptional<HUInfo> getHUInfoById(final HuId huId)
	{
		final HUQRCode huQRCode = huService.getQRCodeByHuId(huId);
		return ExplainedOptional.of(HUInfo.ofHuIdAndQRCode(huId, huQRCode));
	}

	private ExplainedOptional<HUInfo> findHUByQRCodeAttribute(
			@NonNull final IHUQRCode scannedQRCode,
			@NonNull final ProductId productId)
	{
		final HuId huId = huService.getFirstHUIdByQRCodeAttribute(scannedQRCode, productId).orElse(null);
		if (huId == null)
		{
			return ExplainedOptional.emptyBecause(ERR_NotEnoughTUsFound); // TODO introduce a better AD_Message
		}

		return getHUInfoById(huId);
	}

	private ExplainedOptional<HUInfo> findFirstByWarehouseAndProduct(@NonNull final WarehouseId warehouseId, @NonNull final ProductId productId)
	{
		final HuId huId = huService.getFirstHuIdByWarehouseAndProduct(warehouseId, productId).orElse(null);

		if (huId == null)
		{
			return ExplainedOptional.emptyBecause(ERR_NotEnoughTUsFound);// TODO introduce a better AD_Message
		}

		return getHUInfoById(huId);
	}

	private BooleanWithReason validateQRCode_GTIN(
			@NonNull final GS1HUQRCode pickFromHUQRCode,
			@NonNull final ProductId expectedProductId)
	{
		final GTIN gtin = pickFromHUQRCode.getGTIN().orElse(null);
		if (gtin != null)
		{
			final ProductId gs1ProductId = productService.getProductIdByGTINStrictlyNotNull(gtin, ClientId.METASFRESH);
			if (!ProductId.equals(expectedProductId, gs1ProductId))
			{
				return BooleanWithReason.falseBecause(ERR_QR_ProductNotMatching, productService.getProductNameTrl(expectedProductId));
			}
		}

		return BooleanWithReason.TRUE;
	}

	private BooleanWithReason validateQRCode_EAN13ProductNo(
			@NonNull final EAN13HUQRCode pickFromQRCode,
			@NonNull final ProductId expectedProductId,
			@NonNull final BPartnerId customerId)
	{
		final EAN13 ean13 = pickFromQRCode.unbox();
		if (!productService.isValidEAN13Product(ean13, expectedProductId, customerId))
		{
			return BooleanWithReason.falseBecause(ERR_QR_ProductNotMatching, productService.getProductNameTrl(expectedProductId));
		}

		return BooleanWithReason.TRUE;
	}

	private BooleanWithReason validateQRCode_ProductNo(
			@NonNull final CustomHUQRCode customQRCode,
			@NonNull final ProductId expectedProductId)
	{
		final String qrCodeProductNo = customQRCode.getProductNo().orElse(null);
		if (qrCodeProductNo == null) {return BooleanWithReason.TRUE;}

		final String expectedProductNo = productService.getProductValue(expectedProductId);
		if (!Objects.equals(qrCodeProductNo, expectedProductNo))
		{
			return BooleanWithReason.falseBecause(ERR_QR_ProductNotMatching, productService.getProductNameTrl(expectedProductId));
		}

		return BooleanWithReason.TRUE;
	}
}
