package de.metas.pricing.callout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class M_PricelistVersion_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(@NonNull final ICalloutRecord calloutRecord)
	{
		final IPriceListDAO pricelistDAO = Services.get(IPriceListDAO.class);

		final I_M_PriceList_Version version = calloutRecord.getModel(I_M_PriceList_Version.class);

		if (!Check.isEmpty(version.getName()))
		{
			// name was already set. Do nothing
			return;
		}

		final LocalDate today = SystemTime.asLocalDate();
		final String formattedDate = today.format(DateTimeFormatter.ISO_LOCAL_DATE);

		final I_M_PriceList pricelist = pricelistDAO.getById(version.getM_PriceList_ID());

		final String pricelsitName = pricelist.getName();

		final String plvName = new StringBuilder()
				.append(pricelsitName)
				.append(" ")
				.append(formattedDate)
				.toString();

		version.setName(plvName);
	}

}
