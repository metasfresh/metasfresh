package de.metas.pos;

import de.metas.pos.POSProductsLoader.POSProductsLoaderBuilder;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.util.InSetPredicate;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

	public POSProductsSearchResult getProducts(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final Instant evalDate,
			@Nullable final String queryString)
	{
		final POSTerminal posTerminal = posTerminalService.getPOSTerminalById(posTerminalId);

		return POSProductsSearchCommand.builder()
				.productBL(productBL)
				.loader(newProductsLoader()
						.priceListId(posTerminal.getPriceListId())
						.currency(posTerminal.getCurrency())
						.build())
				.evalDate(evalDate)
				.queryString(queryString)
				.build()
				.execute();
	}

	/**
	 * Resolves the till's current price (and its price UOM — see {@link POSProduct#getPriceUom()}) for a fixed
	 * set of products, so a caller that already knows WHICH products it needs (e.g. the POS return endpoint,
	 * given the client's product+qty lines) does not have to go through the free-text/barcode search this
	 * service otherwise offers.
	 */
	@NonNull
	public List<POSProduct> getProductsByIds(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final Instant evalDate,
			@NonNull final Set<ProductId> productIds)
	{
		final POSTerminal posTerminal = posTerminalService.getPOSTerminalById(posTerminalId);

		return newProductsLoader()
				.priceListId(posTerminal.getPriceListId())
				.currency(posTerminal.getCurrency())
				.build()
				.load(evalDate, InSetPredicate.only(productIds));
	}

	private POSProductsLoaderBuilder newProductsLoader()
	{
		return POSProductsLoader.builder()
				.priceListDAO(priceListDAO)
				.productBL(productBL)
				.uomDAO(uomDAO);
	}
}
