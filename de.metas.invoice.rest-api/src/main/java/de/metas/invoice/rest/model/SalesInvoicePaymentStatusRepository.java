package de.metas.invoice.rest.model;

import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.isEmpty;
import static org.compiere.util.TimeUtil.asTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.service.IOrgDAO;
import org.adempiere.service.IOrgDAO.OrgQuery;
import org.adempiere.service.OrgId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.engine.IDocument;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.invoice.rest-api
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

@Repository
public class SalesInvoicePaymentStatusRepository
{
	public ImmutableList<SalesInvoicePaymentStatus> getBy(@NonNull final PaymentStatusQuery query)
	{
		final OrgId orgId = retrieveOrgId(query.getOrgValue());

		final IQueryBuilder<I_C_Invoice> queryBuilder = createCommonQueryBuilder(orgId);

		if (!Check.isEmpty(query.getInvoiceDocumentNo(), true))
		{
			queryBuilder.addEqualsFilter(I_C_Invoice.COLUMN_DocumentNo, query.getInvoiceDocumentNo());
		}
		if (query.getDateInvoicedFrom() != null)
		{
			queryBuilder.addCompareFilter(I_C_Invoice.COLUMN_DateInvoiced, Operator.GREATER_OR_EQUAL, asTimestamp(query.getDateInvoicedFrom()));
		}
		if (query.getDateInvoicedTo() != null)
		{
			queryBuilder.addCompareFilter(I_C_Invoice.COLUMN_DateInvoiced, Operator.LESS, asTimestamp(query.getDateInvoicedTo()));
		}

		final ImmutableList<I_C_Invoice> invoiceRecords = queryBuilder
				.create()
				.listImmutable(I_C_Invoice.class);

		return createResults(invoiceRecords);
	}

	private OrgId retrieveOrgId(@NonNull final String orgCode)
	{
		final OrgQuery orgQuery = OrgQuery.builder().orgValue(orgCode)
				.failIfNotExists(true)
				.outOfTrx(true)
				.build();
		final OrgId orgId = Services.get(IOrgDAO.class)
				.retrieveOrgIdBy(orgQuery)
				.get(); // can use get because of .failIfNotExists(true)
		return orgId;
	}

	private ImmutableList<SalesInvoicePaymentStatus> createResults(@NonNull final ImmutableList<I_C_Invoice> invoiceRecords)
	{
		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final ImmutableList.Builder<SalesInvoicePaymentStatus> result = ImmutableList.builder();

		for (final I_C_Invoice invoiceRecord : invoiceRecords)
		{
			final BigDecimal openAmt = allocationDAO.retrieveOpenAmt(invoiceRecord, false/* creditMemoAdjusted */);

			final List<I_C_Payment> paymentrecords = allocationDAO.retrieveInvoicePayments(invoiceRecord);
			for (final I_C_Payment paymentRecord : paymentrecords)
			{
				if (invoiceBL.isCreditMemo(invoiceRecord))
				{
					continue;
				}

				final SalesInvoicePaymentStatus jsonSalesInvoicePaymentStatus = SalesInvoicePaymentStatus
						.builder()
						.invoiceDocumentNumber(invoiceRecord.getDocumentNo())
						.openAmt(openAmt)
						.currency(invoiceRecord.getC_Currency().getISO_Code())
						.paymentDate(TimeUtil.asZonedDateTime(paymentRecord.getDateTrx()))
						.paymentDocumentNumber(paymentRecord.getDocumentNo())
						.build();
				result.add(jsonSalesInvoicePaymentStatus);
			}
		}
		return result.build();
	}

	private IQueryBuilder<I_C_Invoice> createCommonQueryBuilder(@NonNull final OrgId orgId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMN_AD_Org_ID, orgId)
				.addEqualsFilter(I_C_Invoice.COLUMN_IsSOTrx, true)
				.addEqualsFilter(I_C_Invoice.COLUMN_DocStatus, IDocument.STATUS_Completed)
				.orderBy(I_C_Invoice.COLUMN_DateInvoiced)
				.orderBy(I_C_Invoice.COLUMN_C_Invoice_ID)
				.setOption(IQuery.OPTION_ReturnReadOnlyRecords, true);
	}

	@Value
	public static class PaymentStatusQuery
	{
		String orgValue;
		String invoiceDocumentNo;
		ZonedDateTime dateInvoicedFrom;
		ZonedDateTime dateInvoicedTo;

		@Builder
		private PaymentStatusQuery(
				@NonNull final String orgValue,
				@Nullable final String invoiceDocumentNo,
				@Nullable final ZonedDateTime dateInvoicedFrom,
				@Nullable final ZonedDateTime dateInvoicedTo)
		{
			this.orgValue = assumeNotEmpty(orgValue, "Parameter 'orgValue' may not be empty");

			this.invoiceDocumentNo = invoiceDocumentNo;
			this.dateInvoicedFrom = dateInvoicedFrom;
			this.dateInvoicedTo = dateInvoicedTo;

			if (isEmpty(invoiceDocumentNo, true))
			{
				Check.assumeNotNull(dateInvoicedFrom, "If parameter 'invoiceDocumentNo' is empty, then both dateInvoicedFrom and dateInvoicedTo need to be set, but dateInvoicedFrom is null");
				Check.assumeNotNull(dateInvoicedTo, "If parameter 'invoiceDocumentNo' is empty, then both dateInvoicedFrom and dateInvoicedTo need to be set, but dateInvoicedTo is null");
			}
		}
	}
}
