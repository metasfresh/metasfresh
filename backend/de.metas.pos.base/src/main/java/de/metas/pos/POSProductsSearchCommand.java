package de.metas.pos;

import de.metas.common.util.CoalesceUtil;
import de.metas.gs1.GS1Elements;
import de.metas.gs1.GS1Parser;
import de.metas.gs1.GTIN;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.InSetPredicate;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

class POSProductsSearchCommand
{
	@NonNull
	final Instant evalDate;
	@Nullable
	final String queryString;
	@NonNull
	private final IProductBL productBL;
	@NonNull
	private final POSProductsLoader loader;

	@Builder
	private POSProductsSearchCommand(
			@NonNull final IProductBL productBL,
			@NonNull final POSProductsLoader loader,
			@NonNull final Instant evalDate,
			@Nullable final String queryString)
	{
		this.productBL = productBL;
		this.loader = loader;
		this.evalDate = evalDate;
		this.queryString = StringUtils.trimBlankToNull(queryString);
	}

	public POSProductsSearchResult execute()
	{
		return CoalesceUtil.coalesceSuppliersNotNull(
				this::searchByGS1,
				this::searchByEAN13,
				this::searchByUPCorValue,
				this::searchByValueName,
				this::allProducts
		);
	}

	@Nullable
	private POSProductsSearchResult searchByGS1()
	{
		if (queryString == null)
		{
			return null;
		}

		final GS1Elements elements = GS1Parser.parseElementsOrNull(queryString);
		if (elements == null)
		{
			return null;
		}

		final GTIN gtin = elements.getGTIN().orElse(null);
		if (gtin == null)
		{
			return null;
		}

		final ProductId productId = productBL.getProductIdByGTIN(gtin, ClientId.METASFRESH).orElse(null);
		final POSProduct product = getPOSProduct(productId);
		if (product == null)
		{
			return null;
		}

		return POSProductsSearchResult.ofBarcodeMatchedProduct(
				product.withCatchWeight(elements.getWeightInKg().orElse(null))
		);
	}

	@Nullable
	private POSProductsSearchResult searchByEAN13()
	{
		if (queryString == null)
		{
			return null;
		}

		final EAN13HUQRCode ean13 = EAN13HUQRCode.fromString(queryString).orElse(null);
		if (ean13 == null)
		{
			return null;
		}

		final ProductId productId;
		if (ean13.isVariableWeight())
		{
			productId = productBL.getProductIdByValueStartsWith(ean13.getProductNo(), ClientId.METASFRESH).orElse(null);
		}

		else if (ean13.isInternalUseOrVariableMeasure())
		{
			productId = productBL.getProductIdByEAN13ProductCode(ean13.getProductNo(), ClientId.METASFRESH).orElse(null);
		}
		else
		{
			throw new AdempiereException("Unsupported EAN13 prefix: " + ean13.getPrefix());
		}
		final POSProduct product = getPOSProduct(productId);
		if (product == null)
		{
			return null;
		}

		return POSProductsSearchResult.ofBarcodeMatchedProduct(
				product.withCatchWeight(ean13.getWeightInKg().orElse(null))
		);
	}

	@Nullable
	private POSProductsSearchResult searchByUPCorValue()
	{
		if (queryString == null)
		{
			return null;
		}

		final ProductId productId = productBL.getProductIdByBarcode(queryString, ClientId.METASFRESH).orElse(null);
		final POSProduct product = getPOSProduct(productId);
		if (product == null)
		{
			return null;
		}

		return POSProductsSearchResult.ofBarcodeMatchedProduct(product);
	}

	@Nullable
	private POSProduct getPOSProduct(@Nullable final ProductId productId)
	{
		if (productId == null)
		{
			return null;
		}

		final List<POSProduct> products = loader.load(evalDate, InSetPredicate.only(productId));
		if (products.size() != 1)
		{
			return null;
		}

		return products.get(0);
	}

	@Nullable
	private POSProductsSearchResult searchByValueName()
	{
		if (queryString == null)
		{
			return null;
		}

		final Set<ProductId> productIds = productBL.getProductIdsMatchingQueryString(queryString, ClientId.METASFRESH, QueryLimit.ONE_HUNDRED);
		return POSProductsSearchResult.ofList(loader.load(evalDate, InSetPredicate.only(productIds)));
	}

	@NonNull
	private POSProductsSearchResult allProducts()
	{
		return POSProductsSearchResult.ofList(loader.load(evalDate, InSetPredicate.any()));
	}

}
