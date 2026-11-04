package de.metas.handlingunits.rest_api.createfromlabel;

import com.google.common.collect.ImmutableSet;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.model.ProductLabelCodes;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.AdMessageKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.BARCODE_FITS_SEVERAL_PRODUCTS;
import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.LABEL_HAS_NO_PRODUCT;
import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.LABEL_NOT_RECOGNIZED;
import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.NOT_A_PRODUCT_LABEL;
import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.PRODUCT_NOT_FOUND_FOR_GTIN;
import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.PRODUCT_NOT_FOUND_FOR_PRODUCT_CODE;

/**
 * Turns a scanned code that is no existing HU into the {@link LabelValues} of the product it identifies; shared by the proposal and the
 * create command, so both read the label the same way (the create command re-parses on the server and never trusts the client).
 * <p>
 * What a label's weight means for the product (a design default):
 * <ul>
 * <li>product stocked in kg: the label weight is the quantity</li>
 * <li>catch-weight product: quantity 1, the label weight becomes the CU's net weight</li>
 * <li>any other product: the weight is not used</li>
 * <li>no weight on the label: quantity 1</li>
 * </ul>
 * Every refusal is a {@link LabelRejectionException} (one plain-language message).
 * <p>
 * Stateless: the services only; the per-request client is the argument of {@link #resolve(ScannedCode, ClientId)}.
 */
@RequiredArgsConstructor
public class LabelValuesService
{
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final IProductBL productBL;
	@NonNull private final IUOMDAO uomDAO;

	@NonNull
	public LabelValues resolve(@NonNull final ScannedCode scannedCode, @NonNull final ClientId clientId)
	{
		return resolve(parse(scannedCode), scannedCode, clientId);
	}

	/**
	 * The code decides what it says about its product ({@link IHUQRCode#getProductLabelCodes()}); this method never asks what kind of code it is.
	 * The first code present decides, there is no fallback to the next one.
	 */
	@NonNull
	LabelValues resolve(@NonNull final IHUQRCode code, @NonNull final ScannedCode scannedCode, @NonNull final ClientId clientId)
	{
		final I_M_Product product = productBL.getById(resolveProductId(code, scannedCode, clientId));
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		final UomId stockUomId = productBL.getStockUOMId(productId);
		final X12DE355 stockUom = uomDAO.getX12DE355ById(stockUomId);

		final LabelValues.LabelValuesBuilder result = LabelValues.builder()
				.productId(productId)
				.productValue(product.getValue())
				.productName(product.getName())
				.lotNo(code.getLotNumber().orElse(null))
				// a label's use-by date (e.g. GS1 AI 17) is stored as the best-before date (working default)
				.bestBeforeDate(code.getBestBeforeDate().orElseGet(() -> code.getExpirationDate().orElse(null)))
				.productionDate(code.getProductionDate().orElse(null))
				.stockUomId(stockUomId)
				.stockUom(stockUom);

		final BigDecimal labelWeightKg = code.getWeightInKg().orElse(null);
		if (labelWeightKg != null)
		{
			if (X12DE355.KILOGRAM.equals(stockUom))
			{
				result.qty(labelWeightKg);
			}
			else if (productBL.getCatchUOMId(productId).isPresent())
			{
				result.weightKgForCU(labelWeightKg);
			}
			else
			{
				result.isWeightIgnored(true);
			}
		}
		return result.build();
	}

	@NonNull
	private ProductId resolveProductId(@NonNull final IHUQRCode code, @NonNull final ScannedCode scannedCode, @NonNull final ClientId clientId)
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
			productId = resolveByProductNo(labelCodes.getProductNo(), clientId);
		}
		else if (labelCodes.getGtin() != null)
		{
			productId = resolveByGTIN(labelCodes.getGtin(), clientId);
		}
		else
		{
			final EAN13 ean13 = Objects.requireNonNull(labelCodes.getEan13());
			productId = resolveByGTIN(ean13.toGTIN(), clientId);
		}
		return productId;
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

	/**
	 * exactly one active product of the client with this product number, else a rejection (none: no product found; several, one per organisation:
	 * the code fits several products, never picks one; the articles are named by name, as their numbers are the same)
	 */
	@NonNull
	private ProductId resolveByProductNo(@NonNull final String productNo, @NonNull final ClientId clientId)
	{
		return exactlyOne(
				productBL.getProductIdsByValue(productNo, clientId),
				PRODUCT_NOT_FOUND_FOR_PRODUCT_CODE,
				productNo,
				productIds -> productBL.getProductNames(productIds).values());
	}

	/** exactly one active product with this GTIN, else a rejection (none: no product found; several: the barcode fits several products, never picks one) */
	@NonNull
	private ProductId resolveByGTIN(@NonNull final GTIN gtin, @NonNull final ClientId clientId)
	{
		return exactlyOne(
				productBL.getProductIdsByGTIN(gtin, clientId),
				PRODUCT_NOT_FOUND_FOR_GTIN,
				gtin.getAsString(),
				productIds -> productBL.getProductValues(productIds).values());
	}

	/** @param namesOfSeveral what names the articles in the "fits several products" message (article names when their numbers are the same, else numbers) */
	@NonNull
	private static ProductId exactlyOne(
			@NonNull final ImmutableSet<ProductId> productIds,
			@NonNull final AdMessageKey notFoundMessageKey,
			@NonNull final String code,
			@NonNull final Function<ImmutableSet<ProductId>, Collection<String>> namesOfSeveral)
	{
		if (productIds.isEmpty())
		{
			throw new LabelRejectionException(notFoundMessageKey, code);
		}
		else if (productIds.size() > 1)
		{
			final String names = namesOfSeveral.apply(productIds).stream().sorted().collect(Collectors.joining(", "));
			throw new LabelRejectionException(BARCODE_FITS_SEVERAL_PRODUCTS, code, names);
		}
		return productIds.iterator().next();
	}

	private static String trunc(@NonNull final ScannedCode scannedCode)
	{
		return StringUtils.trunc(scannedCode.getAsString(), 40);
	}
}
