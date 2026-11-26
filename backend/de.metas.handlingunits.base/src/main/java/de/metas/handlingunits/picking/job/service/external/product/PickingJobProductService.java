package de.metas.handlingunits.picking.job.service.external.product;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.gs1.GS1ProductCodesCollection;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.i18n.ITranslatableString;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.Product;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductLifeCycleAction;
import de.metas.product.ProductRepository;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickingJobProductService
{
	@NonNull private final ProductRepository productRepository;
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	public static PickingJobProductService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				PickingJobProductService.class,
				() -> new PickingJobProductService(ProductRepository.newInstanceForUnitTesting())
		);
	}

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

	public void assertPickAllowed(@NonNull final ProductId productId)
	{
		productBL.assertAllowed(productId, ProductLifeCycleAction.PICK);
	}

	/**
	 * @return {@code true} when the product opts into serial-no picking ({@code M_Product.IsSerialNoPicked='Y'}).
	 * <p>
	 * The checkbox alone drives the UI prompt. The picked HU's ability to <em>store</em> the {@code SerialNo} comes
	 * from the PI wiring ({@code M_HU_PI_Attribute} for {@code SerialNo}, active on the virtual PI version), NOT from
	 * the product's own attribute set — so the product attribute set is irrelevant here. The HU-storage check at pick
	 * time (in the pick command) remains the authoritative gate; this only decides whether to prompt.
	 * <p>
	 * The system-wide {@code SerialNo} attribute being defined is kept as a defensive guard: on an instance where the
	 * {@code SerialNo} attribute does not exist at all, the serial cannot be stored anywhere, so prompting would be
	 * pointless.
	 */
	public boolean isSerialNoPickingEnabled(@NonNull final ProductId productId)
	{
		final I_M_Product product = productBL.getById(productId);
		if (!product.isSerialNoPicked())
		{
			return false;
		}

		// Defensive guard only: if the SerialNo attribute is not defined system-wide, the serial can't be stored
		// anywhere, so don't prompt. The product's own attribute set is intentionally NOT consulted.
		return attributeDAO.retrieveActiveAttributeIdByValueOrNull(AttributeConstants.ATTR_SerialNo) != null;
	}

	public ITranslatableString getProductNameTrl(@NonNull final ProductId productId)
	{
		return productBL.getProductNameTrl(productId);
	}

	public boolean isValidEAN13Product(@NonNull final EAN13 ean13, @NonNull final ProductId expectedProductId, @Nullable final BPartnerId bpartnerId)
	{
		return productBL.isValidEAN13Product(ean13, expectedProductId, bpartnerId);
	}

	public ITranslatableString getUOMSymbolById(@NonNull final UomId uomId) {return uomDAO.getUOMSymbolById(uomId);}

	@NonNull
	public ImmutableMap<ProductId, Product> getByIdsAsMap(@NonNull final Set<ProductId> ids)
	{
		return productRepository.getByIdsAsMap(ids);
	}

	/**
	 * @return M_Product.GuaranteeDaysMin, falling back to product category when product value is 0.
	 *         Returns 0 if not configured on either.
	 */
	public int getGuaranteeDaysMin(@NonNull final ProductId productId)
	{
		return productDAO.getProductGuaranteeDaysMinFallbackProductCategory(productId);
	}
}
