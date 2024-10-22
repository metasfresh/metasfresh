package de.metas.pos.product;

import com.google.common.collect.ImmutableSet;
import de.metas.image.AdImage;
import de.metas.image.AdImageId;
import de.metas.image.AdImageRepository;
import de.metas.pos.product.POSProductsLoader.POSProductsLoaderBuilder;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
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
	@NonNull private final AdImageRepository adImageRepository;

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

	public AdImage getProductCategoryImage(@NonNull final POSProductCategoryId categoryId)
	{
		final POSProductCategory category = categoryRepository.getById(categoryId);
		final AdImageId imageId = category.getImageId();
		if (imageId == null)
		{
			throw new AdempiereException("Category " + categoryId + " has no image");
		}

		return adImageRepository.getById(imageId);
	}
}
