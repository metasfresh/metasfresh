package de.metas.invoicecandidate.api.impl;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.currency.ICurrencyBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateQuery;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;

public class PlainInvoiceCandDAO extends InvoiceCandDAO
{
	private static final transient Logger logger = LogManager.getLogger(PlainInvoiceCandDAO.class);

	private final POJOLookupMap db = POJOLookupMap.get();

	/**
	 * Comparator to order a list of order candidates the same way it would be returned by {@link InvoiceCandDAO#fetchInvalidInvoiceCandidates(Properties, int, String)}. Please keep in sync if the
	 * orderBy clause of that method.
	 */
	public static final Comparator<I_C_Invoice_Candidate> INVALID_CANDIDATES_ORDERING = Services.get(IQueryBL.class)
			.createQueryOrderByBuilder(I_C_Invoice_Candidate.class)
			.addColumn(I_C_Invoice_Candidate.COLUMN_IsManual)
			.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID)
			.createQueryOrderBy()
			.getComparator(I_C_Invoice_Candidate.class);

	@Override
	public BigDecimal retrieveInvoicableAmount(final Properties ctx, final IInvoiceCandidateQuery query, final int targetCurrencyId, final int adClientId, final int adOrgId,
			final String amountColumnName, final String trxName)
	{
		// Conversion date to be used on currency conversion
		final Timestamp dateConv = SystemTime.asTimestamp();

		BigDecimal totalAmt = BigDecimal.ZERO;
		for (final I_C_Invoice_Candidate ic : retrieveCandidates(query))
		{
			final BigDecimal netAmtToInvoice = (BigDecimal)POJOWrapper.getWrapper(ic).getValue(amountColumnName, BigDecimal.class);
			if (netAmtToInvoice == null)
			{
				continue;
			}

			final BigDecimal netAmtToInvoiceConv = Services.get(ICurrencyBL.class).convert(ctx,
					netAmtToInvoice,
					ic.getC_Currency_ID(), // CurFrom_ID,
					targetCurrencyId, // CurTo_ID,
					dateConv,
					ic.getC_ConversionType_ID(),
					adClientId, adOrgId);

			totalAmt = totalAmt.add(netAmtToInvoiceConv);

			logger.debug("netAmtToInvoice=" + netAmtToInvoice + ", netAmtToInvoiceConv=" + netAmtToInvoiceConv + " => total=" + totalAmt + " (ic=" + ic + ")");
		}

		return totalAmt;
	}

	private List<I_C_Invoice_Candidate> retrieveCandidates(final IInvoiceCandidateQuery query)
	{
		return db.getRecords(I_C_Invoice_Candidate.class, new IQueryFilter<I_C_Invoice_Candidate>()
		{

			@Override
			public boolean accept(final I_C_Invoice_Candidate pojo)
			{
				if (query.getBill_BPartner_ID() > 0 && query.getBill_BPartner_ID() != pojo.getBill_BPartner_ID())
				{
					return false;
				}

				if (query.getDateToInvoice() != null)
				{
					final Timestamp queryDateToInvoice = TimeUtil.getDay(query.getDateToInvoice());
					final Timestamp pojoDateToInvoice = Services.get(IInvoiceCandBL.class).getDateToInvoice(pojo);
					if (!queryDateToInvoice.equals(pojoDateToInvoice))
					{
						return false;
					}
				}

				if (query.getExcludeC_Invoice_Candidate_ID() > 0 && pojo.getC_Invoice_Candidate_ID() == query.getExcludeC_Invoice_Candidate_ID())
				{
					return false;
				}

				// either the candidate is *not* manual, or its ID is less or equal than MaxManualC_Invoice_Candidate_ID
				if (query.getMaxManualC_Invoice_Candidate_ID() > 0
						&& pojo.isManual()
						&& pojo.getC_Invoice_Candidate_ID() > query.getMaxManualC_Invoice_Candidate_ID())
				{
					return false;
				}

				if (query.getHeaderAggregationKey() != null && !query.getHeaderAggregationKey().equals(pojo.getHeaderAggregationKey()))
				{
					return false;
				}

				if (query.getProcessed() != null && query.getProcessed() != pojo.isProcessed())
				{
					return false;
				}

				return true;
			}
		});
	}
}
