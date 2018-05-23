package de.metas.pricing.exceptions;

import java.util.Date;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;

@SuppressWarnings("serial")
public class ProductNotOnPriceListException extends AdempiereException
{
	public static final String AD_Message = "ProductNotOnPriceList";

	public ProductNotOnPriceListException(final IPricingContext pricingCtx)
	{
		this(pricingCtx, /* documentLineNo */-1);
	}

	public ProductNotOnPriceListException(final IPricingContext pricingCtx, final int documentLineNo)
	{
		super(buildMessage(pricingCtx, documentLineNo));
		setParameter("pricingCtx", pricingCtx);
		if (documentLineNo > 0)
		{
			setParameter("documentLineNo", documentLineNo);
		}
	}

	private static ITranslatableString buildMessage(final IPricingContext pricingCtx, final int documentLineNo)
	{
		final TranslatableStringBuilder sb = TranslatableStringBuilder.newInstance();

		if (documentLineNo > 0)
		{
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("Line").append(": ").append(documentLineNo);
		}

		final int productId = pricingCtx.getM_Product_ID();
		if (productId > 0)
		{
			final String productName = Services.get(IProductBL.class).getProductValueAndName(productId);
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("M_Product_ID").append(": ").append(productName);
		}

		final int pricingSystemId = pricingCtx.getM_PricingSystem_ID();
		final int priceListId = pricingCtx.getM_PriceList_ID();
		if (priceListId > 0)
		{
			final String priceListName = Services.get(IPriceListDAO.class).getPriceListName(priceListId);
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("M_PriceList_ID").append(": ").append(priceListName);
		}
		else if (pricingSystemId > 0)
		{
			final String pricingSystemName = Services.get(IPriceListDAO.class).getPricingSystemName(pricingSystemId);
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("M_PricingSystem_ID").append(": ").append(pricingSystemName);
		}

		final BPartnerId bpartnerId = pricingCtx.getBPartnerId();
		if (bpartnerId != null)
		{
			String bpartnerName = Services.get(IBPartnerBL.class).getBPartnerValueAndName(bpartnerId);
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("C_BPartner_ID").append(": ").append(bpartnerName);
		}

		final int countryId = pricingCtx.getC_Country_ID();
		if (countryId > 0)
		{
			final ITranslatableString countryName = Services.get(ICountryDAO.class).getCountryNameById(countryId);
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("C_Country_ID").append(": ").append(countryName);
		}

		final Date priceDate = pricingCtx.getPriceDate();
		if (priceDate != null)
		{
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("Date").append(": ").appendDate(priceDate);
		}

		//
		sb.insertFirstADMessage(AD_Message);
		return sb.build();
	}
}
