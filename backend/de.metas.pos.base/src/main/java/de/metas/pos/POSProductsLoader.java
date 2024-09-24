package de.metas.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Builder
class POSProductsLoader
{
	@NonNull private final IPriceListDAO priceListDAO;
	@NonNull private final IProductBL productBL;
	@NonNull private final IUOMDAO uomDAO;
	@NonNull final POSTerminal posTerminal;

	private final HashMap<ProductId, I_M_Product> productsById = new HashMap<>();

	public POSProductsList load(@NonNull final Instant evalDate)
	{
		final PriceListVersionId priceListVersionId = priceListDAO.retrievePriceListVersionId(posTerminal.getPriceListId(), evalDate.atZone(SystemTime.zoneId()));

		final List<I_M_ProductPrice> productPrices = priceListDAO.retrieveProductPrices(priceListVersionId).collect(ImmutableList.toImmutableList());
		loadProductsById(extractProductIds(productPrices));

		return productPrices.stream()
				.map(this::toPOSProduct)
				.collect(POSProductsList.collect());

	}

	private static ImmutableSet<ProductId> extractProductIds(final List<I_M_ProductPrice> productPrices)
	{
		return productPrices.stream()
				.map(productPrice -> ProductId.ofRepoId(productPrice.getM_Product_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	private void loadProductsById(final Set<ProductId> productIds)
	{
		productBL.getByIdsInTrx(productIds).forEach(this::addToCache);
	}

	private void addToCache(final I_M_Product product)
	{
		productsById.put(ProductId.ofRepoId(product.getM_Product_ID()), product);
	}

	private I_M_Product getProductById(final ProductId productId)
	{
		return productsById.computeIfAbsent(productId, productBL::getByIdInTrx);
	}

	private POSProduct toPOSProduct(@NonNull final I_M_ProductPrice productPrice)
	{
		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(productPrice.getC_UOM_ID());

		return POSProduct.builder()
				.id(productId)
				.name(getProductName(productId))
				.price(extractPrice(productPrice))
				.currencySymbol(posTerminal.getCurrency().getSymbol())
				.uomId(uomId)
				.uomSymbol(getUOMSymbol(uomId))
				.taxCategoryId(TaxCategoryId.ofRepoId(productPrice.getC_TaxCategory_ID()))
				.build();
	}

	private ITranslatableString getProductName(final ProductId productId)
	{
		final I_M_Product product = getProductById(productId);
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(product);
		return trls.getColumnTrl(I_M_Product.COLUMNNAME_Name, product.getName());
	}

	private Amount extractPrice(final I_M_ProductPrice productPrice)
	{
		return Amount.of(productPrice.getPriceStd(), posTerminal.getCurrency().getCurrencyCode());
	}

	private String getUOMSymbol(final UomId uomId)
	{
		final I_C_UOM uom = uomDAO.getById(uomId);
		return StringUtils.trimBlankToOptional(uom.getUOMSymbol()).orElseGet(uom::getName);
	}

}
