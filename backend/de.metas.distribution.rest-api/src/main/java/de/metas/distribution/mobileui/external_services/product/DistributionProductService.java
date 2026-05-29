package de.metas.distribution.mobileui.external_services.product;

import de.metas.gs1.GTIN;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DistributionProductService
{
	private final IProductBL productBL = Services.get(IProductBL.class);

	public ProductInfo getProductInfo(@NonNull final ProductId productId)
	{
		return ProductInfo.builder()
				.productId(productId)
				.caption(productBL.getProductNameTrl(productId))
				.build();
	}

	public String getProductValueAndName(@NonNull ProductId productId)
	{
		return productBL.getProductValueAndName(productId);
	}

	public Optional<GTIN> getGTIN(@NonNull ProductId productId)
	{
		return productBL.getGTIN(productId);
	}

	public ProductId getProductIdByScannedProductCode(@NonNull final ScannedCode scannedProductCode)
	{
		ProductId productId = null;
		final GTIN gtin = GTIN.ofScannedCode(scannedProductCode).orElse(null);
		if (gtin != null)
		{
			productId = productBL.getProductIdByGTIN(gtin).orElse(null);
		}

		if (productId == null)
		{
			throw new AdempiereException("No product found for scanned product code: " + scannedProductCode);
		}

		return productId;
	}
}
