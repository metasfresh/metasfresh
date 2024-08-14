package de.metas.product.hazard_symbol;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductHazardSymbolService
{
	private final ProductHazardSymbolRepository productHazardSymbolRepository;
	private final HazardSymbolRepository hazardSymbolRepository;

	public ProductHazardSymbolService(
			@NonNull final ProductHazardSymbolRepository productHazardSymbolRepository,
			@NonNull final HazardSymbolRepository hazardSymbolRepository)
	{
		this.productHazardSymbolRepository = productHazardSymbolRepository;
		this.hazardSymbolRepository = hazardSymbolRepository;
	}

	public List<HazardSymbol> getHazardSymbolsByProductId(@NonNull final ProductId productId)
	{
		final ImmutableSet<HazardSymbolId> hazardSymbolIds = productHazardSymbolRepository.getByProductId(productId).getHazardSymbolIds();
		return hazardSymbolRepository.getByIds(hazardSymbolIds);
	}
}
