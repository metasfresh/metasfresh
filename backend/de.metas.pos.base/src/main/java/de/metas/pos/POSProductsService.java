package de.metas.pos;

import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class POSProductsService
{
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final POSTerminalService posTerminalService;

	public POSProductsList getProducts(@NonNull final Instant evalDate)
	{
		return newProductsLoader().load(evalDate);
	}

	private POSProductsLoader newProductsLoader()
	{
		return POSProductsLoader.builder()
				.priceListDAO(priceListDAO)
				.productBL(productBL)
				.uomDAO(uomDAO)
				.posTerminal(posTerminalService.getPOSTerminal())
				.build();
	}
}
