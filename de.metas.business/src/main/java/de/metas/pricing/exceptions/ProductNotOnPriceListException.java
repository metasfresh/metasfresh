package de.metas.pricing.exceptions;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
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
			@Nullable final IPricingContext pricingCtx,
			final int documentLineNo,
			final ProductId productId)
	{
		final TranslatableStringBuilder sb = TranslatableStrings.builder();

		if (documentLineNo > 0)
		{
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("Line").append(": ").append(documentLineNo);
		}

		ProductId productIdEffective = null;

		if (productId != null)
		{
			productIdEffective = productId;

		}
		else if (pricingCtx != null)
		{
			productIdEffective = pricingCtx.getProductId();
		}

		if (productIdEffective != null)
		{
			final String productName = Services.get(IProductBL.class).getProductValueAndName(productIdEffective);
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("M_Product_ID").append(": ").append(productName);
		}

		final PricingSystemId pricingSystemId = pricingCtx == null ? null : pricingCtx.getPricingSystemId();
		final PriceListId priceListId = pricingCtx == null ? null : pricingCtx.getPriceListId();
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

		final BPartnerId bpartnerId = pricingCtx == null ? null : pricingCtx.getBPartnerId();
		if (bpartnerId != null)
		{
			String bpartnerName = Services.get(IBPartnerBL.class).getBPartnerValueAndName(bpartnerId);
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("C_BPartner_ID").append(": ").append(bpartnerName);
		}

		final CountryId countryId = pricingCtx == null ? null : pricingCtx.getCountryId();
		if (countryId != null)
		{
			final ITranslatableString countryName = Services.get(ICountryDAO.class).getCountryNameById(countryId);
			if (!sb.isEmpty())
			{
				sb.append(", ");
			}
			sb.appendADElement("C_Country_ID").append(": ").append(countryName);
		}

		final LocalDate priceDate = pricingCtx == null ? null : pricingCtx.getPriceDate();
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
