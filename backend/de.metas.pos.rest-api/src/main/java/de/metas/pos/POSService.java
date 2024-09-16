package de.metas.pos;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.IModelTranslationMap;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class POSService
{
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final POSConfigRepository configRepository;

	@NonNull
	public POSConfig getConfig() {return configRepository.getConfig();}

	public POSProductsList getProducts(@NonNull final Instant evalDate)
	{
		final POSConfig config = getConfig();
		final PriceListVersionId priceListVersionId = priceListDAO.retrievePriceListVersionId(config.getPriceListId(), evalDate.atZone(SystemTime.zoneId()));
		final ImmutableSet<ProductId> productIds = priceListDAO.retrieveProductPrices(priceListVersionId)
				.map(productPrice -> ProductId.ofRepoId(productPrice.getM_Product_ID()))
				.collect(ImmutableSet.toImmutableSet());

		return productBL.getByIdsInTrx(productIds)
				.stream()
				.map(POSService::toPOSProduct)
				.collect(POSProductsList.collect());
	}

	private static POSProduct toPOSProduct(final I_M_Product product)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(product);

		return POSProduct.builder()
				.id(ProductId.ofRepoId(product.getM_Product_ID()))
				.name(trls.getColumnTrl(I_M_Product.COLUMNNAME_Name, product.getName()))
				.build();
	}
}
