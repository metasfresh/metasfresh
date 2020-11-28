package de.metas.pricing.rules;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import de.metas.common.util.time.SystemTime;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Calculate Price using Price List Version
 *
 * @author tsa
 *
 */
public class PriceListVersion extends AbstractPriceListBasedRule
{
	private static final Logger logger = LogManager.getLogger(PriceListVersion.class);

	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final I_M_PriceList_Version ctxPriceListVersion = getPriceListVersionEffective(pricingCtx);
		if (ctxPriceListVersion == null)
		{
			return;
		}

		final ZoneId timeZone = orgDAO.getTimeZone(pricingCtx.getOrgId());
		final I_M_ProductPrice productPrice = getProductPriceOrNull(pricingCtx.getProductId(),
				ctxPriceListVersion,
				TimeUtil.asZonedDateTime(pricingCtx.getPriceDate(), timeZone));

		if (productPrice == null)
		{
			logger.trace("Not found (PLV)");
			return;
		}

		final PriceListVersionId resultPriceListVersionId = PriceListVersionId.ofRepoId(productPrice.getM_PriceList_Version_ID());
		final I_M_PriceList_Version resultPriceListVersion = getOrLoadPriceListVersion(resultPriceListVersionId, ctxPriceListVersion);
		final I_M_PriceList priceList = priceListsRepo.getById(resultPriceListVersion.getM_PriceList_ID());

		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());
		final ProductCategoryId productCategoryId = productsRepo.retrieveProductCategoryByProductId(productId);

		result.setPriceStd(productPrice.getPriceStd());
		result.setPriceList(productPrice.getPriceList());
		result.setPriceLimit(productPrice.getPriceLimit());
		result.setCurrencyId(CurrencyId.ofRepoId(priceList.getC_Currency_ID()));
		result.setProductId(productId);
		result.setProductCategoryId(productCategoryId);
		result.setPriceEditable(productPrice.isPriceEditable());
		result.setDiscountEditable(productPrice.isDiscountEditable());
		result.setEnforcePriceLimit(extractEnforcePriceLimit(priceList));
		result.setTaxIncluded(priceList.isTaxIncluded());
		result.setTaxCategoryId(TaxCategoryId.ofRepoId(productPrice.getC_TaxCategory_ID()));
		result.setPriceListVersionId(resultPriceListVersionId);
		result.setPriceUomId(getProductPriceUomId(productPrice)); // 06942 : use product price uom all the time
		result.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.fromRecordString(productPrice.getInvoicableQtyBasedOn()));
		result.setCalculated(true);

		//
		// Override with calculated BOM price if suitable
		BOMPriceCalculator.builder()
				.bomProductId(productId)
				.asiAware(pricingCtx.getAttributeSetInstanceAware().orElse(null))
				.priceListVersion(resultPriceListVersion)
				.calculate()
				.ifPresent(bomPrices -> updatePricingResultFromBOMPrices(result, bomPrices));
	}

	private BooleanWithReason extractEnforcePriceLimit(final I_M_PriceList priceList)
	{
		final ITranslatableString reason = TranslatableStrings.builder()
				.appendADElement("M_PriceList_ID")
				.append(": ")
				.append(priceList.getName())
				.build();

		return priceList.isEnforcePriceLimit()
				? BooleanWithReason.trueBecause(reason)
				: BooleanWithReason.falseBecause(reason);
	}

	private I_M_ProductPrice getProductPriceOrNull(final ProductId productId,
			final I_M_PriceList_Version ctxPriceListVersion,
			final ZonedDateTime promisedDate)
	{
		return ProductPrices.iterateAllPriceListVersionsAndFindProductPrice(
				ctxPriceListVersion,
				priceListVersion -> ProductPrices.retrieveMainProductPriceOrNull(priceListVersion, productId),
				promisedDate);
	}

	private I_M_PriceList_Version getOrLoadPriceListVersion(
			@NonNull final PriceListVersionId priceListVersionId,
			final I_M_PriceList_Version existingPriceListVersion)
	{
		if (existingPriceListVersion != null
				&& existingPriceListVersion.getM_PriceList_Version_ID() == priceListVersionId.getRepoId())
		{
			return existingPriceListVersion;
		}

		return priceListsRepo.getPriceListVersionById(priceListVersionId);
	}

	private I_M_PriceList_Version getPriceListVersionEffective(final IPricingContext pricingCtx)
	{
		final I_M_PriceList_Version contextPLV = pricingCtx.getM_PriceList_Version();
		if (contextPLV != null)
		{
			return contextPLV.isActive() ? contextPLV : null;
		}

		final I_M_PriceList_Version plv = priceListsRepo.retrievePriceListVersionOrNull(
				pricingCtx.getPriceListId(),
				TimeUtil.asZonedDateTime(pricingCtx.getPriceDate(), SystemTime.zoneId()),
				(Boolean)null // processed
		);

		return plv != null && plv.isActive() ? plv : null;
	}

	private UomId getProductPriceUomId(final I_M_ProductPrice productPrice)
	{
		final UomId productPriceUomId = UomId.ofRepoIdOrNull(productPrice.getC_UOM_ID());
		if (productPriceUomId != null)
		{
			return productPriceUomId;
		}

		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());
		return productsService.getStockUOMId(productId);
	}

	private static void updatePricingResultFromBOMPrices(final IPricingResult to, final BOMPrices from)
	{
		to.setPriceStd(from.getPriceStd());
		to.setPriceList(from.getPriceList());
		to.setPriceLimit(from.getPriceLimit());
	}
}
