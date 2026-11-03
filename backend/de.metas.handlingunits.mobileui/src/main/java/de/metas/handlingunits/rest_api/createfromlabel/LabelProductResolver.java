package de.metas.handlingunits.rest_api.createfromlabel;

import com.google.common.collect.ImmutableSet;
import de.metas.gs1.GTIN;
import de.metas.handlingunits.qrcodes.custom.CustomHUQRCode;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.gs1.GS1HUQRCode;
import de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.special.PickOnTheFlyQRCode;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.BARCODE_FITS_SEVERAL_PRODUCTS;
import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.LABEL_HAS_NO_PRODUCT;
import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.LABEL_NOT_RECOGNIZED;
import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.NOT_A_PRODUCT_LABEL;
import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.PRODUCT_NOT_FOUND_FOR_GTIN;
import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.PRODUCT_NOT_FOUND_FOR_PRODUCT_CODE;

/**
 * Turns a scanned code that is no existing HU into the product it identifies; shared by the proposal and the create
 * command, so both read the label the same way (the create command re-parses on the server and never trusts the client).
 * <p>
 * Every refusal is a {@link LabelRejectionException} (one plain-language message).
 */
class LabelProductResolver
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final ClientId clientId;

	@Builder
	private LabelProductResolver(
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final ClientId clientId)
	{
		this.huQRCodesService = huQRCodesService;
		this.clientId = clientId;
	}

	@NonNull
	public LabelProduct resolve(@NonNull final ScannedCode scannedCode)
	{
		final IHUQRCode code = parse(scannedCode);

		if (code instanceof PickOnTheFlyQRCode
				|| code instanceof HUQRCode // an HU QR code not (yet) assigned to an HU: the HU lookup found nothing
				|| code instanceof LMQRCode)
		{
			throw new LabelRejectionException(NOT_A_PRODUCT_LABEL, trunc(scannedCode));
		}
		else if (code instanceof CustomHUQRCode)
		{
			return resolveCustom(scannedCode, (CustomHUQRCode)code);
		}
		else if (code instanceof GS1HUQRCode)
		{
			final GTIN gtin = ((GS1HUQRCode)code).getGTIN()
					.orElseThrow(() -> new LabelRejectionException(LABEL_HAS_NO_PRODUCT, trunc(scannedCode)));
			return LabelProduct.builder().code(code).productId(resolveByGTIN(gtin)).build();
		}
		else if (code instanceof EAN13HUQRCode)
		{
			final EAN13HUQRCode ean13Code = (EAN13HUQRCode)code;
			final ProductId productId = productBL.getProductIdByEAN13(ean13Code.unbox(), null, clientId)
					.orElseThrow(() -> new LabelRejectionException(PRODUCT_NOT_FOUND_FOR_GTIN, ean13Code.getAsString()));
			return LabelProduct.builder().code(code).productId(productId).build();
		}
		else
		{
			throw new LabelRejectionException(LABEL_NOT_RECOGNIZED, trunc(scannedCode));
		}
	}

	private IHUQRCode parse(@NonNull final ScannedCode scannedCode)
	{
		try
		{
			return huQRCodesService.parse(scannedCode);
		}
		catch (final AdempiereException ex)
		{
			// A global (metasfresh) QR code of another type, e.g. a shelf label, never gets here (the HU lookup before refuses it);
			// should it, the existing message stays. Everything else the parser cannot read is "label not recognized".
			if (scannedCode.toGlobalQRCodeIfMatching().orNullIfError() != null)
			{
				throw ex;
			}
			throw new LabelRejectionException(LABEL_NOT_RECOGNIZED, trunc(scannedCode));
		}
	}

	private LabelProduct resolveCustom(@NonNull final ScannedCode scannedCode, @NonNull final CustomHUQRCode code)
	{
		final String productNo = code.getProductNo().orElse(null);
		if (productNo != null)
		{
			final ProductId productId = productBL.getProductIdByValue(productNo);
			if (productId == null)
			{
				throw new LabelRejectionException(PRODUCT_NOT_FOUND_FOR_PRODUCT_CODE, productNo);
			}
			return LabelProduct.builder().code(code).productId(productId).build();
		}

		final Optional<GTIN> gtin = code.getGTIN();
		if (!gtin.isPresent())
		{
			throw new LabelRejectionException(LABEL_HAS_NO_PRODUCT, trunc(scannedCode));
		}
		return LabelProduct.builder().code(code).productId(resolveByGTIN(gtin.get())).build();
	}

	/** exactly one active product with this GTIN, else a rejection (none: no product found; several: the barcode fits several products, never picks one) */
	@NonNull
	private ProductId resolveByGTIN(@NonNull final GTIN gtin)
	{
		final List<ProductId> productIds = productBL.getProductIdsByLabelGTIN(gtin, null, clientId);
		if (productIds.isEmpty())
		{
			throw new LabelRejectionException(PRODUCT_NOT_FOUND_FOR_GTIN, gtin.getAsString());
		}
		else if (productIds.size() > 1)
		{
			final String productValues = productBL.getProductValues(ImmutableSet.copyOf(productIds)).values().stream()
					.sorted()
					.collect(Collectors.joining(", "));
			throw new LabelRejectionException(BARCODE_FITS_SEVERAL_PRODUCTS, gtin.getAsString(), productValues);
		}
		return productIds.get(0);
	}

	private static String trunc(@NonNull final ScannedCode scannedCode)
	{
		return StringUtils.trunc(scannedCode.getAsString(), 40);
	}
}
