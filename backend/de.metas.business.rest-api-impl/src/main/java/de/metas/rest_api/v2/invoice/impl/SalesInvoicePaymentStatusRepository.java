package de.metas.rest_api.v2.invoice.impl;

import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.IDocument;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgIdNotFoundException;
import de.metas.organization.OrgQuery;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.invoice.SalesInvoicePayment;
import de.metas.rest_api.v2.invoice.SalesInvoicePaymentStatus;
import de.metas.rest_api.v2.invoice.SalesInvoicePaymentStatus.SalesInvoicePaymentStatusBuilder;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.isBlank;
import static de.metas.util.Check.isNotBlank;
import static java.math.BigDecimal.ZERO;

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
	private final CurrencyRepository currenciesRepo;

	public SalesInvoicePaymentStatusRepository(@NonNull final CurrencyRepository currenciesRepo)
	{
		this.currenciesRepo = currenciesRepo;
	}

	public ImmutableList<SalesInvoicePaymentStatus> getBy(@NonNull final PaymentStatusQuery query)
	{
		final OrgId orgId = retrieveOrgId(query.getOrgValue());

		final IQueryBuilder<I_C_Invoice> queryBuilder = createCommonQueryBuilder(orgId);

		if (isNotBlank(query.getInvoiceDocumentNoPrefix()))
		{
			queryBuilder.addCompareFilter(I_C_Invoice.COLUMN_DocumentNo, Operator.STRING_LIKE, query.getInvoiceDocumentNoPrefix() + "%");
		}
		if (query.getDateInvoicedFrom() != null)
		{
			queryBuilder.addCompareFilter(I_C_Invoice.COLUMN_DateInvoiced, Operator.GREATER_OR_EQUAL, query.getDateInvoicedFrom());
		}
		if (query.getDateInvoicedTo() != null)
		{
			queryBuilder.addCompareFilter(I_C_Invoice.COLUMN_DateInvoiced, Operator.LESS, query.getDateInvoicedTo());
		}

		final ImmutableList<I_C_Invoice> invoiceRecords = queryBuilder
				.create()
				.listImmutable(I_C_Invoice.class);

		return createResults(invoiceRecords);
	}

	private OrgId retrieveOrgId(@NonNull final String orgCode)
	{
		final OrgQuery orgQuery = OrgQuery
				.builder()
				.orgValue(orgCode)
				.failIfNotExists(false)
				.build();

		final OrgId orgId = Services.get(IOrgDAO.class)
				.retrieveOrgIdBy(orgQuery)
				.orElseGet(() -> retrieveOrgIdByBPartnerGLN(orgCode));
		if (orgId == null)
		{
			final String msg = StringUtils.formatMessage("Found no existing Org; Searched via both value and org-bpartner-location-GLN ='{}'", orgQuery.getOrgValue());
			throw new OrgIdNotFoundException(msg);
		}
		return orgId;
	}

	private OrgId retrieveOrgIdByBPartnerGLN(@NonNull final String locatorGln)
	{
		final I_C_BPartner bpartnerRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Location.COLUMN_GLN, locatorGln)
				.andCollect(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, I_C_BPartner.class)
				.addCompareFilter(I_C_BPartner.COLUMNNAME_AD_OrgBP_ID, Operator.GREATER, 0)
				.addOnlyActiveRecordsFilter()
				.create()
				.setRequiredAccess(Access.WRITE)
				.firstOnlyOrNull(I_C_BPartner.class);
		if (bpartnerRecord == null)
		{
			return null;
		}
		return OrgId.ofRepoId(bpartnerRecord.getAD_Org_ID());
	}

	private ImmutableList<SalesInvoicePaymentStatus> createResults(@NonNull final ImmutableList<I_C_Invoice> invoiceRecords)
	{
		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final ImmutableList.Builder<SalesInvoicePaymentStatus> result = ImmutableList.builder();

		// TODO get distinct C_Invoice_ID and load all payments at once
		for (final I_C_Invoice invoiceRecord : invoiceRecords)
		{
			if (invoiceBL.isCreditMemo(invoiceRecord))
			{
				continue;
			}

			final BigDecimal allocatedAmt = coalesce(allocationDAO.retrieveAllocatedAmt(invoiceRecord), ZERO);

			final BigDecimal openAmt = invoiceRecord.getGrandTotal().subtract(allocatedAmt);

			final SalesInvoicePaymentStatusBuilder statusBuilder = SalesInvoicePaymentStatus
					.builder()
					.invoiceId(MetasfreshId.of(invoiceRecord.getC_Invoice_ID()))
					.invoiceDocumentNumber(invoiceRecord.getDocumentNo())
					.openAmt(openAmt)
					.isPaid(openAmt.signum()<=0)
					.docStatus(invoiceRecord.getDocStatus())
					.currency(extractCurrencyCode(invoiceRecord).toThreeLetterCode());
			final List<I_C_Payment> paymentRecords = allocationDAO.retrieveInvoicePayments(invoiceRecord);

			for (final I_C_Payment paymentRecord : paymentRecords)
			{
				statusBuilder.payment(SalesInvoicePayment
						.builder()
						.paymentDate(TimeUtil.asLocalDate(paymentRecord.getDateTrx()))
						.paymentDocumentNumber(paymentRecord.getDocumentNo())
						.build());
			}
			result.add(statusBuilder.build());
		}
		return result.build();
	}

	private CurrencyCode extractCurrencyCode(final I_C_Invoice invoiceRecord)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID());
		return currenciesRepo.getCurrencyCodeById(currencyId);
	}

	private IQueryBuilder<I_C_Invoice> createCommonQueryBuilder(@NonNull final OrgId orgId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_AD_Org_ID, orgId)
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
		String invoiceDocumentNoPrefix;
		LocalDate dateInvoicedFrom;
		LocalDate dateInvoicedTo;

		@Builder
		private PaymentStatusQuery(
				@NonNull final String orgValue,
				@Nullable final String invoiceDocumentNoPrefix,
				@Nullable final LocalDate dateInvoicedFrom,
				@Nullable final LocalDate dateInvoicedTo)
		{
			this.orgValue = assumeNotEmpty(orgValue, "Parameter 'orgValue' may not be empty");

			this.invoiceDocumentNoPrefix = invoiceDocumentNoPrefix;
			this.dateInvoicedFrom = dateInvoicedFrom;
			this.dateInvoicedTo = dateInvoicedTo;
			if (isBlank(invoiceDocumentNoPrefix))
			{
				Check.assumeNotNull(dateInvoicedFrom, "If parameter 'invoiceDocumentNoPrefix' is empty, then both dateInvoicedFrom and dateInvoicedTo need to be set, but dateInvoicedFrom is null");
				Check.assumeNotNull(dateInvoicedTo, "If parameter 'invoiceDocumentNoPrefix' is empty, then both dateInvoicedFrom and dateInvoicedTo need to be set, but dateInvoicedTo is null");
			}
		}
	}
}
