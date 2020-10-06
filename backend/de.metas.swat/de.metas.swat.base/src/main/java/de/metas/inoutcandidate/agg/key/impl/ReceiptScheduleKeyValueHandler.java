package de.metas.inoutcandidate.agg.key.impl;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.bpartner.BPartnerContactId;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.agg.key.IAggregationKeyValueHandler;

import java.util.ArrayList;
import java.util.List;

import static org.compiere.util.Util.ArrayKey.SEPARATOR;

/**
 * AggregationKey value handler for Invoice Candidates in Material Tracking
 *
 * @author al
 */
public class ReceiptScheduleKeyValueHandler implements IAggregationKeyValueHandler<I_M_ReceiptSchedule>
{
	@Override
	public List<Object> getValues(@NonNull final I_M_ReceiptSchedule rs)
	{
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

		final List<Object> values = new ArrayList<>();

		values.add(rs.getC_DocType_ID());
		values.add(receiptScheduleBL.getC_BPartner_Effective_ID(rs));
		values.add(receiptScheduleBL.getC_BPartner_Location_Effective_ID(rs));
		values.add(receiptScheduleBL.getWarehouseEffectiveId(rs).getRepoId());
		final BPartnerContactId bPartnerContactID = receiptScheduleBL.getBPartnerContactID(rs);
		if (bPartnerContactID != null)
		{
			values.add(bPartnerContactID);
		}
		values.add(rs.getAD_Org_ID());
		values.add(rs.getDateOrdered());
		values.add(rs.getC_Order_ID());

		if (Check.isNotBlank(rs.getExternalResourceURL()))
		{
			//remove any `org.compiere.util.Util.ArrayKey.SEPARATOR` that may be present in the URL
			final String externalResourceURL = rs.getExternalResourceURL().replaceAll(SEPARATOR, "");

			values.add(externalResourceURL);
		}

		return values;
	}
}
