package de.metas.inventory.mobileui.deps.products;

import de.metas.gs1.ean13.EAN13;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.impl.AddAttributesRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

	public boolean isValidEAN13Product(final @NonNull EAN13 ean13, @NonNull final ProductId lineProductId)
	{
		return productBL.isValidEAN13Product(ean13, lineProductId);
	}

	public ProductsLoadingCache newProductsLoadingCache()
	{
		return ProductsLoadingCache.builder()
				.productBL(productBL)
				.build();
	}

	public ASILoadingCache newASILoadingCache()
	{
		return ASILoadingCache.builder()
				.attributeSetInstanceBL(attributeSetInstanceBL)
				.build();
	}

	public AttributeSetInstanceId createASI(@NonNull ProductId productId, @NonNull final Map<AttributeCode, String> attributes)
	{
		if (attributes.isEmpty())
		{
			return AttributeSetInstanceId.NONE;
		}

		return attributeSetInstanceBL.addAttributes(
				AddAttributesRequest.builder()
						.productId(productId)
						.attributeInstanceBasicInfos(attributes.entrySet()
								.stream()
								.map(entry -> CreateAttributeInstanceReq.builder()
										.attributeCode(entry.getKey())
										.value(entry.getValue())
										.build())
								.collect(Collectors.toList()))
						.build()
		);
	}

}
