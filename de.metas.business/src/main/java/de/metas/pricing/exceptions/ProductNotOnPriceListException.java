package de.metas.pricing.exceptions;

import java.util.Date;

import org.adempiere.exceptions.AdempiereException;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;

@SuppressWarnings("serial")
public class ProductNotOnPriceListException extends AdempiereException
{
	public static final String AD_Message = "ProductNotOnPriceList";

	public ProductNotOnPriceListException(final IPricingContext pricingCtx)
	{
		this(pricingCtx,
				-1, // documentLineNo
				(ProductId)null);
	}

	public ProductNotOnPriceListException(final IPricingContext pricingCtx, final int documentLineNo)
	{
		this(pricingCtx,
				documentLineNo,
				(ProductId)null);
	}

	@Builder
	private ProductNotOnPriceListException(
			final IPricingContext pricingCtx,
			final int documentLineNo,
			final ProductId productId)
	{
		super(buildMessage(pricingCtx, documentLineNo, productId));

		setParameter("pricingCtx", pricingCtx);

		if (documentLineNo > 0)
		{
			setParameter("documentLineNo", documentLineNo);
		}
	}

	private static ITranslatableString buildMessage(
			final IPricingContext pricingCtx,
			final int documentLineNo,
			final ProductId productId)
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

		final ProductId productIdEffective = productId != null ? productId : pricingCtx.getProductId();
		if (productIdEffective != null)
		{
			final String productName = Services.get(IProductBL.class).getProductValueAndName(productIdEffective);
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("M_Product_ID").append(": ").append(productName);
		}

		final PricingSystemId pricingSystemId = pricingCtx.getPricingSystemId();
		final PriceListId priceListId = pricingCtx.getPriceListId();
		if (priceListId != null)
		{
			final String priceListName = Services.get(IPriceListDAO.class).getPriceListName(priceListId);
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("M_PriceList_ID").append(": ").append(priceListName);
		}
		else if (pricingSystemId != null)
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
		sb.insertFirst(" - ");
		sb.insertFirstADMessage(AD_Message);
		return sb.build();
	}
}
