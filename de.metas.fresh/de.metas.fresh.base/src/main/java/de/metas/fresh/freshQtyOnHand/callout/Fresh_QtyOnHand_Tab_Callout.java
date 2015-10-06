package de.metas.fresh.freshQtyOnHand.callout;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.apache.commons.lang3.time.DateUtils;
import org.compiere.model.GridTab;

import de.metas.fresh.model.I_Fresh_QtyOnHand;

/**
 * The tab callout makes sure that, if a new <code>Fresh_QtyOnHand</code> record is created, then that record receives a <code>DateDoc</code> that was not yet "taken" by another
 * <code>Fresh_QtyOnHand</code>.<br>
 * It does so by using the record's current date (or "now"), and searching forward day-by-day, until it finds a date that was not yet taken.
 * <p>
 * Note: it also sets the description to <code>null</code>.
 * 
 * 
 * @author ts
 * @task 08329
 */
public class Fresh_QtyOnHand_Tab_Callout extends TabCalloutAdapter
{
	@Override
	public void onNew(final GridTab gridTab)
	{
		final I_Fresh_QtyOnHand freshqtyOnHand = InterfaceWrapperHelper.create(gridTab, I_Fresh_QtyOnHand.class);
		final Timestamp dateDoc = freshqtyOnHand.getDateDoc();

		Timestamp dateDocToUse = dateDoc == null ? SystemTime.asTimestamp() : dateDoc;

		boolean dateAlreadyUsed = isDateDocAlreadyUsed(freshqtyOnHand, dateDocToUse);

		while (dateAlreadyUsed)
		{
			dateDocToUse = new Timestamp(DateUtils.addDays(dateDocToUse, 1).getTime());
			dateAlreadyUsed = isDateDocAlreadyUsed(freshqtyOnHand, dateDocToUse);
		}

		freshqtyOnHand.setDateDoc(dateDocToUse);
		freshqtyOnHand.setDescription(null);
	}

	private boolean isDateDocAlreadyUsed(final I_Fresh_QtyOnHand freshqtyOnHand, final Timestamp dateDocToUse)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fresh_QtyOnHand.class)
				.setContext(freshqtyOnHand)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_Fresh_QtyOnHand.COLUMN_DateDoc, dateDocToUse)
				.create()
				.match();
	}
}
