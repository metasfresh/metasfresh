package de.metas.pricing.rules;

import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.Env;

import de.metas.money.CurrencyId;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;

/**
 * Calculate Price using Price List Version
 *
 * @author tsa
 *
 */
public class PriceListVersion extends AbstractPriceListBasedRule
{
	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final ProductId productId = pricingCtx.getProductId();

		final I_M_PriceList_Version plv = getPriceListVersionEffective(pricingCtx);
		if (plv == null || !plv.isActive())
		{
			return;
		}

		// task 09688: in order to make the material tracking module (and others!) testable decoupled, incl. price list versions and stuff,
		// we get rid of the hardcoded SQL. For the time beeing it's still here (commented), so we can see how it used to be.
		// !!IMPORTANT!! with this change of implementation, we loose the bomPriceList calculation.
		// Should bomPricing be needed in future, please consider adding a dedicated pricing rule
		final I_M_ProductPrice productPrice = ProductPrices.retrieveMainProductPriceOrNull(plv, productId);
		if (productPrice == null)
		{
			log.trace("Not found (PLV)");
			return;
		}

		final I_M_PriceList priceList = plv.getM_PriceList();
		
		final ProductCategoryId productCategoryId = Services.get(IProductDAO.class).retrieveProductCategoryByProductId(productId);

		result.setPriceStd(productPrice.getPriceStd());
		result.setPriceList(productPrice.getPriceList());
		result.setPriceLimit(productPrice.getPriceLimit());
		result.setCurrencyId(CurrencyId.ofRepoId(priceList.getC_Currency_ID()));
		result.setProductCategoryId(productCategoryId);
		result.setPriceEditable(productPrice.isPriceEditable());
		result.setDiscountEditable(productPrice.isDiscountEditable());
		result.setEnforcePriceLimit(priceList.isEnforcePriceLimit());
		result.setTaxIncluded(priceList.isTaxIncluded());
		result.setC_TaxCategory_ID(productPrice.getC_TaxCategory_ID());
		result.setPriceListVersionId(PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID())); // make sure that the result doesn't lack this important info, even if it was already known from the context!
		result.setCalculated(true);

		// 06942 : use product price uom all the time
		if (productPrice.getC_UOM_ID() <= 0)
		{
			final int uomId = Services.get(IProductBL.class).getStockingUOMId(productId);
			result.setPrice_UOM_ID(uomId);
		}
		else
		{
			result.setPrice_UOM_ID(productPrice.getC_UOM_ID());
		}
	}

	private I_M_PriceList_Version getPriceListVersionEffective(final IPricingContext pricingCtx)
	{
		final I_M_PriceList_Version plv = pricingCtx.getM_PriceList_Version();
		if(plv != null)
		{
			return plv;
		}
		
		return Services.get(IPriceListDAO.class).retrievePriceListVersionOrNull(Env.getCtx(),
				pricingCtx.getPriceListId(),
				pricingCtx.getPriceDate(),
				(Boolean)null // processed
		);

	}
}
