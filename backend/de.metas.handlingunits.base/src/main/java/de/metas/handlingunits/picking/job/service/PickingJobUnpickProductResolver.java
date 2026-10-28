package de.metas.handlingunits.picking.job.service;

import de.metas.gs1.GTIN;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobUnpickResolveResult;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.qrcodes.custom.CustomHUQRCode;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.gs1.GS1HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PickingJobUnpickProductResolver
{
	@NonNull private final PickingJobHUService huService;
	@NonNull private final PickingJobProductService productService;

	@NonNull
	public PickingJobUnpickResolveResult resolve(
			@NonNull final PickingJob pickingJob,
			@NonNull final ScannedCode scannedCode)
	{
		final IHUQRCode parsedQRCode = huService.parsePickFromScannedCode(scannedCode);
		final ProductId matchedProductId = resolveProductInJob(parsedQRCode, pickingJob);
		final Quantity packedQty = pickingJob.getPackedQty(matchedProductId);
		final ITranslatableString productNameTrl = productService.getProductNameTrl(matchedProductId);

		return PickingJobUnpickResolveResult.builder()
				.productId(matchedProductId)
				.productName(productNameTrl)
				.packedQty(packedQty)
				.build();
	}

	@NonNull
	private ProductId resolveProductInJob(
			@NonNull final IHUQRCode parsedQRCode,
			@NonNull final PickingJob pickingJob)
	{
		if (parsedQRCode instanceof GS1HUQRCode)
		{
			final GS1HUQRCode gs1QRCode = (GS1HUQRCode)parsedQRCode;
			final GTIN gtin = gs1QRCode.getGTIN().orElse(null);
			if (gtin != null)
			{
				final ProductId productId = productService.getProductIdByGTINStrictlyNotNull(gtin, ClientId.METASFRESH);
				if (pickingJob.getProductIds().contains(productId))
				{
					return productId;
				}
			}
		}
		else if (parsedQRCode instanceof EAN13HUQRCode)
		{
			final EAN13HUQRCode ean13QRCode = (EAN13HUQRCode)parsedQRCode;
			final de.metas.gs1.ean13.EAN13 ean13 = ean13QRCode.unbox();
			for (final ProductId productId : pickingJob.getProductIds())
			{
				if (productService.isValidEAN13Product(ean13, productId, pickingJob.getCustomerId()))
				{
					return productId;
				}
			}
		}
		else if (parsedQRCode instanceof CustomHUQRCode)
		{
			final CustomHUQRCode customQRCode = (CustomHUQRCode)parsedQRCode;
			final String scannedProductNo = customQRCode.getProductNo().orElse(null);
			if (scannedProductNo != null)
			{
				for (final ProductId productId : pickingJob.getProductIds())
				{
					if (scannedProductNo.equals(productService.getProductValue(productId)))
					{
						return productId;
					}
				}
			}
		}
		else if (parsedQRCode instanceof HUQRCode)
		{
			throw new AdempiereException("Scanning an HU QR code is not supported for partial-unpick resolve; scan a product barcode (GTIN/EAN/product code) instead");
		}

		throw new AdempiereException("Cannot find a product matching the scanned code in this picking job");
	}
}
