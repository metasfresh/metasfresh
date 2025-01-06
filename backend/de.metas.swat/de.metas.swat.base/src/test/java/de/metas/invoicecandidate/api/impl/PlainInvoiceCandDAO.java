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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.ICurrencyBL;
import de.metas.invoicecandidate.api.InvoiceCandRecomputeTag;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.service.ClientId;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PlainInvoiceCandDAO extends InvoiceCandDAO
{
	private static final Logger logger = LogManager.getLogger(PlainInvoiceCandDAO.class);

	/**
	 * Comparator to order a list of order candidates the same way it would be returned by {@link InvoiceCandDAO#fetchInvalidInvoiceCandidates(Properties, InvoiceCandRecomputeTag, String)}. Please keep in sync if the
	 * orderBy clause of that method.
	 */
	public static final Comparator<I_C_Invoice_Candidate> INVALID_CANDIDATES_ORDERING = Services.get(IQueryBL.class)
			.createQueryOrderByBuilder(I_C_Invoice_Candidate.class)
			.addColumn(I_C_Invoice_Candidate.COLUMN_IsManual)
			.addColumn(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID)
			.createQueryOrderBy()
			.getComparator(I_C_Invoice_Candidate.class);

	@Override
	public BigDecimal retrieveInvoicableAmount(
			final Properties ctx,
			final @NonNull InvoiceCandidateQuery query,
			final @NonNull CurrencyId targetCurrencyId,
			final int adClientId,
			final String amountColumnName,
			final String trxName)
	{
		final List<I_C_Invoice_Candidate> records = getByQuery(InvoiceCandidateMultiQuery.builder().query(query).build());

		BigDecimal totalAmt = BigDecimal.ZERO;
		for (final I_C_Invoice_Candidate ic : records)
		{
			final BigDecimal netAmtToInvoice = (BigDecimal)POJOWrapper.getWrapper(ic).getValue(amountColumnName, BigDecimal.class);
			if (netAmtToInvoice == null)
			{
				continue;
			}

			final BigDecimal netAmtToInvoiceConv = Services.get(ICurrencyBL.class).convert(
					netAmtToInvoice,
					CurrencyId.ofRepoId(ic.getC_Currency_ID()), // CurFrom_ID,
					targetCurrencyId, // CurTo_ID,
					SystemTime.asInstant(),
					CurrencyConversionTypeId.ofRepoIdOrNull(ic.getC_ConversionType_ID()),
					ClientId.ofRepoId(adClientId),
					query.getOrgIdNotNull());

			totalAmt = totalAmt.add(netAmtToInvoiceConv);

			logger.debug("netAmtToInvoice=" + netAmtToInvoice + ", netAmtToInvoiceConv=" + netAmtToInvoiceConv + " -> total=" + totalAmt + " (ic=" + ic + ")");
		}

		return totalAmt;
	}



	@Override
	public Set<String> retrieveOrderDocumentNosForIncompleteGroupsFromSelection(final PInstanceId adPInstanceId)
	{
		// not implemented
		return ImmutableSet.of();
	}

}
