package de.metas.pos.product;

import com.google.common.collect.ImmutableSet;
import de.metas.pos.product.POSProductsLoader.POSProductsLoaderBuilder;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class POSProductsService
{
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final POSProductCategoryRepository categoryRepository;
	@NonNull private final POSProductCategoryAssignmentRepository categoryAssignmentRepository;

	public POSProductsSearchResult getProducts(@NonNull POSProductsSearchRequest request)
	{
		return POSProductsSearchCommand.builder()
				.productBL(productBL)
				.loader(newProductsLoader()
						.priceListId(request.getPriceListId())
						.currency(request.getCurrency())
						.build())
				.evalDate(request.getEvalDate())
				.queryString(request.getQueryString())
				.build()
				.execute();
	}

	private POSProductsLoaderBuilder newProductsLoader()
	{
		return POSProductsLoader.builder()
				.priceListDAO(priceListDAO)
				.productBL(productBL)
				.uomDAO(uomDAO)
				.categoryAssignmentRepository(categoryAssignmentRepository);
	}

	public ImmutableSet<POSProductCategory> getActiveCategoriesByIds(final Collection<POSProductCategoryId> ids)
	{
		return categoryRepository.getActiveByIds(ids);
	}
}
