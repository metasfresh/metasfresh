package org.adempiere.pricing.exceptions;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.sql.Timestamp;
import java.text.DateFormat;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pricing.api.IPricingContext;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.MPriceList;
import org.compiere.model.MProductPricing;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

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

	@Deprecated
	public ProductNotOnPriceListException(final MProductPricing productPricing, final int documentLineNo)
	{
		super(buildMessage(productPricing, documentLineNo));
	}

	private static String buildMessage(final IPricingContext pricingCtx, final int documentLineNo)
	{
		return buildMessage(documentLineNo,
				pricingCtx.getM_Product_ID(),
				pricingCtx.getM_PriceList_ID(),
				pricingCtx.getPriceDate());
	}

	private static final String buildMessage(final MProductPricing pp, final int documentLineNo)
	{
		final int m_Product_ID = pp.getM_Product_ID();
		final int m_PriceList_ID = pp.getM_PriceList_ID();
		final Timestamp priceDate = pp.getPriceDate();

		return buildMessage(documentLineNo, m_Product_ID, m_PriceList_ID, priceDate);
	}

	protected static String buildMessage(final int documentLineNo, final int productId, final int m_PriceList_ID, final Timestamp priceDate)
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
		if (productId > 0)
		{
			final I_M_Product product = productId > 0 ? loadOutOfTrx(productId, I_M_Product.class) : null;
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("@M_Product_ID@:").append(product == null ? "?" : product.getValue() + "_" + product.getName());
		}
		if (m_PriceList_ID > 0)
		{
			final MPriceList pl = MPriceList.get(Env.getCtx(), m_PriceList_ID, null);
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("@M_PriceList_ID@:").append(pl == null ? "?" : pl.getName());
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
		final I_M_PriceList priceList = plv == null ? null : plv.getM_PriceList();
		sb.append("\n@M_PriceList_ID@: ").append(priceList == null ? "-" : priceList.getName());

		//
		// Pricing System
		final I_M_PricingSystem pricingSystem = priceList == null ? null : priceList.getM_PricingSystem();
		sb.append("\n@M_PricingSystem_ID@: ").append(pricingSystem == null ? "-" : pricingSystem.getName());

		return sb.toString();
	}
}
