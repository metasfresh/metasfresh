package de.metas.pos;

import de.metas.pos.POSProductsLoader.POSProductsLoaderBuilder;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.util.InSetPredicate;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class POSProductsService
{
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final POSTerminalService posTerminalService;

	public POSProductsList getProducts(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final Instant evalDate,
			@Nullable final String queryString)
	{
		final POSTerminal posTerminal = posTerminalService.getPOSTerminalById(posTerminalId);
		final POSProductsLoader loader = newProductsLoader()
				.priceListId(posTerminal.getPriceListId())
				.currency(posTerminal.getCurrency())
				.build();

		final String queryStringNorm = StringUtils.trimBlankToNull(queryString);
		if (queryStringNorm == null)
		{
			return POSProductsList.ofList(loader.load(evalDate, InSetPredicate.any()));
		}

		//
		// Try searching by barcode first
		{
			final ProductId productId = productBL.getProductIdByBarcode(queryStringNorm, ClientId.METASFRESH).orElse(null);
			if (productId != null)
			{
				final List<POSProduct> products = loader.load(evalDate, InSetPredicate.only(productId));
				if (products.size() == 1)
				{
					return POSProductsList.ofBarcodeMatchedProduct(products.get(0));
				}
			}
		}

		//
		// Search by product value/name
		{
			final Set<ProductId> productIds = productBL.getProductIdsMatchingQueryString(queryStringNorm, ClientId.METASFRESH, QueryLimit.ONE_HUNDRED);
			return POSProductsList.ofList(loader.load(evalDate, InSetPredicate.only(productIds)));
		}
	}

	private POSProductsLoaderBuilder newProductsLoader()
	{
		return POSProductsLoader.builder()
				.priceListDAO(priceListDAO)
				.productBL(productBL)
				.uomDAO(uomDAO);
	}
}
