package de.metas.handlingunits.rest_api.createfromlabel;

import com.google.common.collect.ImmutableSet;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.model.ProductLabelCodes;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import java.util.Objects;
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
public class LabelProductResolver
{
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final IProductBL productBL;

	/** stateless: the services only; the per-request client is the argument of {@link #resolve(ScannedCode, ClientId)} */
	public LabelProductResolver(
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final IProductBL productBL)
	{
		this.huQRCodesService = huQRCodesService;
		this.productBL = productBL;
	}

	@NonNull
	public LabelProduct resolve(@NonNull final ScannedCode scannedCode, @NonNull final ClientId clientId)
	{
		return resolve(parse(scannedCode), scannedCode, clientId);
	}

	/**
	 * The code decides what it says about its product ({@link IHUQRCode#getProductLabelCodes()}); this method never asks what kind of code it is.
	 * The first code present decides, there is no fallback to the next one.
	 */
	@NonNull
	LabelProduct resolve(@NonNull final IHUQRCode code, @NonNull final ScannedCode scannedCode, @NonNull final ClientId clientId)
	{
		final ProductLabelCodes labelCodes = code.getProductLabelCodes()
				.orElseThrow(() -> new LabelRejectionException(NOT_A_PRODUCT_LABEL, trunc(scannedCode)));
		if (labelCodes.isEmpty())
		{
			throw new LabelRejectionException(LABEL_HAS_NO_PRODUCT, trunc(scannedCode));
		}

		final ProductId productId;
		if (labelCodes.getProductNo() != null)
		{
			productId = productBL.getProductIdByValue(labelCodes.getProductNo());
			if (productId == null)
			{
				throw new LabelRejectionException(PRODUCT_NOT_FOUND_FOR_PRODUCT_CODE, labelCodes.getProductNo());
			}
		}
		else if (labelCodes.getGTIN() != null)
		{
			productId = resolveByGTIN(labelCodes.getGTIN(), clientId);
		}
		else
		{
			final EAN13 ean13 = Objects.requireNonNull(labelCodes.getEAN13());
			productId = productBL.getProductIdByEAN13(ean13, null, clientId)
					.orElseThrow(() -> new LabelRejectionException(PRODUCT_NOT_FOUND_FOR_GTIN, ean13.getAsString()));
		}
		return LabelProduct.builder().code(code).productId(productId).build();
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

	/** exactly one active product with this GTIN, else a rejection (none: no product found; several: the barcode fits several products, never picks one) */
	@NonNull
	private ProductId resolveByGTIN(@NonNull final GTIN gtin, @NonNull final ClientId clientId)
	{
		final ImmutableSet<ProductId> productIds = productBL.getProductIdsByGTIN(gtin, clientId);
		if (productIds.isEmpty())
		{
			throw new LabelRejectionException(PRODUCT_NOT_FOUND_FOR_GTIN, gtin.getAsString());
		}
		else if (productIds.size() > 1)
		{
			final String productValues = productBL.getProductValues(productIds).values().stream()
					.sorted()
					.collect(Collectors.joining(", "));
			throw new LabelRejectionException(BARCODE_FITS_SEVERAL_PRODUCTS, gtin.getAsString(), productValues);
		}
		return productIds.iterator().next();
	}

	private static String trunc(@NonNull final ScannedCode scannedCode)
	{
		return StringUtils.trunc(scannedCode.getAsString(), 40);
	}
}
