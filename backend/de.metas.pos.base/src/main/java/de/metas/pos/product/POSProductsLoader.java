package de.metas.pos.product;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.Currency;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.pos.product.POSProduct.UomIdAndSymbol;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.InSetPredicate;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class POSProductsLoader
{
	// services
	@NonNull private static final Logger logger = LogManager.getLogger(POSProductsLoader.class);
	@NonNull private final IPriceListDAO priceListDAO;
	@NonNull private final IProductBL productBL;
	@NonNull private final IUOMDAO uomDAO;
	@NonNull private final POSProductCategoryAssignmentRepository categoryAssignmentRepository;

	// params
	@NonNull private final PriceListId priceListId;
	@NonNull private final Currency currency;

	private final HashMap<ProductId, I_M_Product> productsById = new HashMap<>();
	private final HashMap<ProductId, ImmutableSet<POSProductCategoryId>> categoryIdsByProductId = new HashMap<>();

	@Builder
	private POSProductsLoader(
			@NonNull final IPriceListDAO priceListDAO,
			@NonNull final IProductBL productBL,
			@NonNull final IUOMDAO uomDAO,
			@NonNull final POSProductCategoryAssignmentRepository categoryAssignmentRepository,
			@NonNull final PriceListId priceListId,
			@NonNull final Currency currency)
	{
		this.priceListDAO = priceListDAO;
		this.productBL = productBL;
		this.uomDAO = uomDAO;
		this.categoryAssignmentRepository = categoryAssignmentRepository;

		this.priceListId = priceListId;
		this.currency = currency;
	}

	public List<POSProduct> load(@NonNull final Instant evalDate, @NonNull final InSetPredicate<ProductId> productIds)
	{
		if (productIds.isNone())
		{
			return ImmutableList.of();
		}

		final PriceListVersionId priceListVersionId = priceListDAO.retrievePriceListVersionId(priceListId, evalDate.atZone(SystemTime.zoneId()));

		final List<I_M_ProductPrice> productPrices = priceListDAO.retrieveProductPrices(priceListVersionId)
				.filter(productPrice -> isProductPriceMatching(productPrice, productIds))
				.collect(ImmutableList.toImmutableList());

		loadProductsById(extractProductIds(productPrices));

		return productPrices.stream()
				.map(this::toPOSProductNoFail)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

	}

	private static boolean isProductPriceMatching(final I_M_ProductPrice productPrice, final @NonNull InSetPredicate<ProductId> productIds)
	{
		return productIds.test(ProductId.ofRepoId(productPrice.getM_Product_ID()));
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

		categoryAssignmentRepository.getProductCategoryIdsByProductIds(productIds)
				.asMap()
				.forEach((productId, categoryIds) -> categoryIdsByProductId.put(productId, ImmutableSet.copyOf(categoryIds)));
	}

	private void addToCache(final I_M_Product product)
	{
		productsById.put(ProductId.ofRepoId(product.getM_Product_ID()), product);
	}

	private I_M_Product getProductById(final ProductId productId)
	{
		return productsById.computeIfAbsent(productId, productBL::getByIdInTrx);
	}

	private ImmutableSet<POSProductCategoryId> getCategoryIds(@NonNull final ProductId productId)
	{
		return categoryIdsByProductId.computeIfAbsent(productId, categoryAssignmentRepository::getProductCategoryIdsByProductId);
	}

	@Nullable
	private POSProduct toPOSProductNoFail(@NonNull final I_M_ProductPrice productPrice)
	{
		try
		{
			return toPOSProduct(productPrice);
		}
		catch (Exception ex)
		{
			logger.warn("Failed converting {} to POSProduct. Skipped.", productPrice, ex);
			return null;
		}
	}

	private POSProduct toPOSProduct(@NonNull final I_M_ProductPrice productPrice)
	{
		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());

		final UomId uomId;
		final UomId catchWeightUomId;
		final InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.ofCode(productPrice.getInvoicableQtyBasedOn());
		switch (invoicableQtyBasedOn)
		{
			case NominalWeight:
				uomId = UomId.ofRepoId(productPrice.getC_UOM_ID());
				catchWeightUomId = null;
				break;
			case CatchWeight:
				uomId = getProductUomId(productId);
				catchWeightUomId = UomId.ofRepoId(productPrice.getC_UOM_ID());
				break;
			default:
				throw new AdempiereException("Unknown invoicableQtyBasedOn: " + invoicableQtyBasedOn);
		}

		return POSProduct.builder()
				.id(productId)
				.name(getProductName(productId))
				.price(extractPrice(productPrice))
				.currencySymbol(currency.getSymbol())
				.uom(toUomIdAndSymbol(uomId))
				.catchWeightUom(catchWeightUomId != null ? toUomIdAndSymbol(catchWeightUomId) : null)
				.taxCategoryId(TaxCategoryId.ofRepoId(productPrice.getC_TaxCategory_ID()))
				.categoryIds(getCategoryIds(productId))
				.build();
	}

	private ITranslatableString getProductName(final ProductId productId)
	{
		final I_M_Product product = getProductById(productId);
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(product);
		return trls.getColumnTrl(I_M_Product.COLUMNNAME_Name, product.getName());
	}

	private UomId getProductUomId(final ProductId productId)
	{
		final I_M_Product product = getProductById(productId);
		return UomId.ofRepoId(product.getC_UOM_ID());
	}

	private Amount extractPrice(final I_M_ProductPrice productPrice)
	{
		return Amount.of(productPrice.getPriceStd(), currency.getCurrencyCode());
	}

	private String getUOMSymbol(final UomId uomId)
	{
		final I_C_UOM uom = uomDAO.getById(uomId);
		return StringUtils.trimBlankToOptional(uom.getUOMSymbol()).orElseGet(uom::getName);
	}

	private UomIdAndSymbol toUomIdAndSymbol(final UomId uomId)
	{
		return UomIdAndSymbol.of(uomId, getUOMSymbol(uomId));
	}

}
