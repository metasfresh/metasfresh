package de.metas.distribution.service.external;

import de.metas.gs1.GTIN;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DistributionProductService
{
	private final IProductBL productBL = Services.get(IProductBL.class);

	public String getProductValueAndName(@NonNull ProductId productId)
	{
		return productBL.getProductValueAndName(productId);
	}

	public Optional<GTIN> getGTIN(@NonNull ProductId productId)
	{
		return productBL.getGTIN(productId);
	}

}
