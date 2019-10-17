package de.metas.pricing.exceptions;

import java.time.LocalDate;

import org.adempiere.exceptions.AdempiereException;

import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;

@SuppressWarnings("serial")
public class PriceListVersionNotFoundException extends AdempiereException
{
	public PriceListVersionNotFoundException(final PriceListId priceListId, final LocalDate date)
	{
		super(buildMessage(toPriceListName(priceListId), date));
	}

	private static String buildMessage(final String priceListName, final LocalDate date)
	{
		final StringBuilder sb = new StringBuilder("@NotFound@: @M_PriceList_Version_ID@");
		sb.append("\n@M_PriceList_ID@: ").append(priceListName != null ? priceListName : "-");
		sb.append("\n@Date@: ").append(date != null ? date : "-");
		return sb.toString();
	}

	private static String toPriceListName(final PriceListId priceListId)
	{
		if (priceListId == null)
		{
			return null;
		}

		return Services.get(IPriceListDAO.class).getPriceListName(priceListId);
	}
}
