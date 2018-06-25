package de.metas.invoicecandidate.agg.key.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.aggregation.api.AbstractAggregationKeyBuilder;
import de.metas.aggregation.api.IAggregationKey;
import de.metas.aggregation.api.impl.AggregationKey;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.pricing.service.IPriceListDAO;

/**
 * AggregationKey value handler for Invoice Candidates in Material Tracking
 *
 * @author al
 */
public final class ICHeaderAggregationKeyBuilder_OLD extends AbstractAggregationKeyBuilder<I_C_Invoice_Candidate>
{
	public static final transient ICHeaderAggregationKeyBuilder_OLD instance = new ICHeaderAggregationKeyBuilder_OLD();

	private static final List<String> columnNames = Arrays.asList(
			I_C_Invoice_Candidate.COLUMNNAME_C_DocTypeInvoice_ID,
			I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID,
			I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID,
			I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_ID,
			I_C_Invoice_Candidate.COLUMNNAME_Bill_User_ID,
			I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID,
			I_C_Invoice_Candidate.COLUMNNAME_IsSOTrx,
			I_C_Invoice_Candidate.COLUMNNAME_DescriptionHeader, // 04258: header and footer added
			I_C_Invoice_Candidate.COLUMNNAME_DescriptionBottom,
			I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID,

			// task 08451
			I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded,
			I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded_Override,

			I_C_Invoice_Candidate.COLUMNNAME_POReference // task 07879
	);

	private ICHeaderAggregationKeyBuilder_OLD()
	{
		super();
	}

	@Override
	public String getTableName()
	{
		return I_C_Invoice_Candidate.Table_Name;
	}

	@Override
	public List<String> getDependsOnColumnNames()
	{
		return columnNames;
	}

	@Override
	public IAggregationKey buildAggregationKey(I_C_Invoice_Candidate model)
	{
		final List<Object> keyValues = getValues(model);
		final ArrayKey key = Util.mkKey(keyValues.toArray());
		final int aggregationId = -1;
		return new AggregationKey(key, aggregationId);
	}

	private final List<Object> getValues(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final List<Object> values = new ArrayList<>();

		final int invoiceDocTypeId = ic.getC_DocTypeInvoice_ID();
		final int currencyId = ic.getC_Currency_ID();

		values.add(invoiceDocTypeId <= 0 ? 0 : invoiceDocTypeId);
		values.add(ic.getAD_Org_ID());

		values.add(ic.getBill_BPartner_ID());
		values.add(ic.getBill_Location_ID());

		values.add(currencyId <= 0 ? 0 : currencyId);

		// Dates
		// 08511 workaround - we don't add dates in header aggregation key
		values.add(null); // ic.getDateInvoiced());
		values.add(null); // ic.getDateAcct()); // task 08437

		// IsSOTrx
		values.add(ic.isSOTrx());

		// Pricing System
		final int pricingSystemId = IPriceListDAO.M_PricingSystem_ID_None; // 08511 workaround
		values.add(pricingSystemId);
		
		values.add(invoiceCandBL.isTaxIncluded(ic)); // task 08451

		final Boolean compact = true;
		values.add(compact ? toHashcode(ic.getDescriptionHeader()) : ic.getDescriptionHeader());
		values.add(compact ? toHashcode(ic.getDescriptionBottom()) : ic.getDescriptionBottom());

		return values;
	}

	private static final int toHashcode(final String s)
	{
		if (Check.isEmpty(s, true))
		{
			return 0;
		}

		return s.hashCode();
	}
}
