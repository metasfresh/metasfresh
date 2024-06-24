package de.metas.product.allergen;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAllergensService
{
	private final ProductAllergensRepository productAllergensRepository;
	private final AllergenRepository allergenRepository;

	public ProductAllergensService(
			@NonNull final ProductAllergensRepository productAllergensRepository,
			@NonNull final AllergenRepository AllergenRepository)
	{
		this.productAllergensRepository = productAllergensRepository;
		this.allergenRepository = AllergenRepository;
	}

	public List<Allergen> getAllergensByProductId(@NonNull final ProductId productId)
	{
		final ImmutableSet<AllergenId> AllergenIds = productAllergensRepository.getByProductId(productId).getAllergenIds();
		return allergenRepository.getByIds(AllergenIds);
	}
}
