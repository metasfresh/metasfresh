package de.metas.handlingunits.picking.job.service.external.product;

import de.metas.bpartner.BPartnerId;
import de.metas.gs1.GS1ProductCodesCollection;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.i18n.ITranslatableString;
import de.metas.product.IProductBL;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class PickingJobProductService
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull
	public ProductInfo getById(@NonNull final ProductId productId)
	{
		final I_M_Product product = productBL.getById(productId);
		final GS1ProductCodesCollection gs1ProductCodes = productBL.getGS1ProductCodesCollection(product);

		return ProductInfo.builder()
				.productId(productId)
				.productNo(product.getValue())
				.gs1ProductCodes(gs1ProductCodes)
				.productCategoryId(ProductCategoryId.ofRepoId(product.getM_Product_Category_ID()))
				.name(InterfaceWrapperHelper.getModelTranslationMap(product).getColumnTrl(I_M_Product.COLUMNNAME_Name, product.getName()))
				.build();
	}

	public ProductId getProductIdByGTINStrictlyNotNull(@NonNull final GTIN gtin, @NonNull final ClientId clientId)
	{
		return productBL.getProductIdByGTINStrictlyNotNull(gtin, clientId);
	}

	public String getProductValue(@NonNull final ProductId productId)
	{
		return productBL.getProductValue(productId);
	}

	public boolean isValidEAN13Product(@NonNull final EAN13 ean13, @NonNull final ProductId expectedProductId, @Nullable final BPartnerId bpartnerId)
	{
		return productBL.isValidEAN13Product(ean13, expectedProductId, bpartnerId);
	}

	public ITranslatableString getUOMSymbolById(@NonNull final UomId uomId) {return uomDAO.getUOMSymbolById(uomId);}
}
