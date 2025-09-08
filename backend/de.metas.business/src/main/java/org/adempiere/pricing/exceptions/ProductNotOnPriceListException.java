package org.adempiere.pricing.exceptions;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.text.DateFormat;
import java.time.LocalDate;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.util.DisplayType;

import de.metas.pricing.IPricingContext;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

@SuppressWarnings("serial")
public class ProductNotOnPriceListException extends AdempiereException
{
	public static final String AD_Message = "ProductNotOnPriceList";

	/**
	 *
	 * @param pricingCtx
	 * @param documentLineNo ignored if <=0
	 */
	public ProductNotOnPriceListException(final IPricingContext pricingCtx, final int documentLineNo)
	{
		super(buildMessage(pricingCtx, documentLineNo));
	}

	public ProductNotOnPriceListException(final I_M_PriceList_Version plv, final int productId)
	{
		super(buildMessage(plv, productId));
	}

	private static String buildMessage(final IPricingContext pricingCtx, final int documentLineNo)
	{
		return buildMessage(documentLineNo,
				pricingCtx.getProductId(),
				pricingCtx.getPriceListId(),
				pricingCtx.getPriceDate());
	}

	protected static String buildMessage(final int documentLineNo, final ProductId productId, final PriceListId priceListId, final LocalDate priceDate)
	{
		final StringBuilder sb = new StringBuilder();
		if (documentLineNo > 0)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("@Line@:").append(documentLineNo);
		}
		if (productId != null)
		{
			final String productName = Services.get(IProductBL.class).getProductValueAndName(productId);
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("@M_Product_ID@:").append(productName);
		}
		if (priceListId != null)
		{
			final String priceListName = Services.get(IPriceListDAO.class).getPriceListName(priceListId);
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("@M_PriceList_ID@:").append(priceListName);
		}
		if (priceDate != null)
		{
			final DateFormat df = DisplayType.getDateFormat(DisplayType.Date);
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("@Date@:").append(df.format(priceDate));
		}
		//
		sb.insert(0, "@" + AD_Message + "@ - ");
		return sb.toString();
	}

	private static String buildMessage(final I_M_PriceList_Version plv, final int productId)
	{
		final StringBuilder sb = new StringBuilder("@NotFound@ @M_ProductPrice_ID@");

		//
		// Product
		final I_M_Product product = productId > 0 ? loadOutOfTrx(productId, I_M_Product.class) : null;
		sb.append("\n@M_Product_ID@: ").append(product == null ? "<" + productId + ">" : product.getName());

		//
		// Price List Version
		sb.append("\n@M_PriceList_Version_ID@: ").append(plv == null ? "-" : plv.getName());

		//
		// Price List
		final I_M_PriceList priceList = plv == null ? null : Services.get(IPriceListDAO.class).getById(PriceListId.ofRepoId(plv.getM_PriceList_ID()));
		sb.append("\n@M_PriceList_ID@: ").append(priceList == null ? "-" : priceList.getName());

		//
		// Pricing System
		final PricingSystemId pricingSystemId = priceList != null
				? PricingSystemId.ofRepoIdOrNull(priceList.getM_PricingSystem_ID())
				: null;
		final String pricingSystemName = pricingSystemId != null
				? Services.get(IPriceListDAO.class).getPricingSystemName(pricingSystemId)
				: "-";
		sb.append("\n@M_PricingSystem_ID@: ").append(pricingSystemName);

		return sb.toString();
	}
}
