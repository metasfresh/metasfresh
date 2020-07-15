package de.metas.payment.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_Fact_Acct;

import com.google.common.collect.ImmutableSet;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.api.PaymentQuery;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;

public abstract class AbstractPaymentDAO implements IPaymentDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_C_Payment getById(@NonNull final PaymentId paymentId)
	{
		return load(paymentId, I_C_Payment.class);
	}

	@Override
	public Optional<I_C_Payment> getByExternalOrderId(@NonNull final ExternalId externalId, @NonNull final OrgId orgId)
	{
		final I_C_Payment i_c_payment = queryBL
				.createQueryBuilder(I_C_Payment.class)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_ExternalOrderId, externalId.getValue())
				.addEqualsFilter(I_C_Payment.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.firstOnlyOrNull(I_C_Payment.class);

		return Optional.ofNullable(i_c_payment);
	}

	@Override
	public List<I_C_Payment> getByIds(@NonNull final Set<PaymentId> paymentIds)
	{
		return loadByRepoIdAwares(paymentIds, I_C_Payment.class);
	}

	@Override
	public BigDecimal getInvoiceOpenAmount(final I_C_Payment payment, final boolean creditMemoAdjusted)
	{
		final I_C_Invoice invoice = payment.getC_Invoice();
		Check.assumeNotNull(invoice, "Invoice available for {}", payment);

		// NOTE: we are not using C_InvoicePaySchedule_ID. It shall be a column in C_Payment

		return Services.get(IAllocationDAO.class).retrieveOpenAmt(invoice, creditMemoAdjusted);
	}

	@Override
	public List<I_C_PaySelectionLine> getProcessedLines(@NonNull final I_C_PaySelection paySelection)
	{
		return queryBL.createQueryBuilder(I_C_PaySelectionLine.class, paySelection)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, paySelection.getC_PaySelection_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_PaySelectionLine.class);
	}

	@Override
	public List<I_C_Payment> retrievePostedWithoutFactAcct(final Properties ctx, final Timestamp startTime)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final IQueryBuilder<I_C_Payment> queryBuilder = queryBL.createQueryBuilder(I_C_Payment.class, ctx, trxName)
				.addOnlyActiveRecordsFilter();

		queryBuilder
				.addEqualsFilter(I_C_Payment.COLUMNNAME_Posted, true) // Posted
				.addEqualsFilter(I_C_Payment.COLUMNNAME_Processed, true) // Processed
				.addInArrayOrAllFilter(I_C_Payment.COLUMN_DocStatus, DocStatus.completedOrClosedStatuses());

		// Only the documents created after the given start time
		if (startTime != null)
		{
			queryBuilder.addCompareFilter(I_C_Payment.COLUMNNAME_Created, Operator.GREATER_OR_EQUAL, startTime);
		}

		// Check if there are fact accounts created for each document
		final IQueryBuilder<I_Fact_Acct> subQueryBuilder = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_Payment.class));

		queryBuilder
				.addNotInSubQueryFilter(I_C_Payment.COLUMNNAME_C_Payment_ID, I_Fact_Acct.COLUMNNAME_Record_ID, subQueryBuilder.create()) // has no accounting
		;

		// Exclude the entries that don't have either PayAmt or OverUnderAmt. These entries will produce 0 in posting
		final ICompositeQueryFilter<I_C_Payment> nonZeroFilter = queryBL.createCompositeQueryFilter(I_C_Payment.class).setJoinOr()
				.addNotEqualsFilter(I_C_Payment.COLUMNNAME_PayAmt, BigDecimal.ZERO)
				.addNotEqualsFilter(I_C_Payment.COLUMNNAME_OverUnderAmt, BigDecimal.ZERO);

		queryBuilder.filter(nonZeroFilter);

		return queryBuilder
				.create()
				.list();

	}

	@Override
	public List<I_C_AllocationLine> retrieveAllocationLines(I_C_Payment payment)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_C_AllocationLine.class, ctx, trxName)
				.addEqualsFilter(I_C_AllocationLine.COLUMNNAME_C_Payment_ID, payment.getC_Payment_ID())
				.create()
				.list();
	}

	@Override
	public Stream<PaymentId> streamPaymentIdsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Payment.class)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.create()
				.listIds(PaymentId::ofRepoId)
				.stream();
	}

	/*
	 * TODO please consider the following improvement
	 * - create an AD_Table like `C_InvoiceOpenAmounts` that has the required values (`C_BPartner_ID`, `C_Currency_ID`, `InvoiceOpen`...) as columns
	 * - put this select-stuff into a DB function. that function shall return the `C_InvoiceOpenAmounts` as result (i.e. the metasfresh `C_InvoiceOpenAmounts` table is not a physical table in the DB; it's just what the DB-function returns)
	 * - create a model class `I_C_InvoiceOpenAmounts` for your AD_Table.
	 * - have the `PaymentDAO` implementation invoke the DB-function to get it's `I_C_InvoiceOpenAmounts` (=> you can do this with IQueryBL)
	 * - have the `PlainPaymentDAO ` implementation return "plain" instances of `I_C_InvoiceOpenAmounts`
	 * - that way, one can write a unit test where they first create one or two plain `I_C_InvoiceOpenAmounts`s and then query them in their test
	 */
	@Override
	public abstract void updateDiscountAndPayment(I_C_Payment payment, int c_Invoice_ID, I_C_DocType c_DocType);

	@Override
	public ImmutableSet<PaymentId> retrievePaymentIds(@NonNull final PaymentQuery query)
	{
		final IQueryBuilder<I_C_Payment> queryBuilder = queryBL.createQueryBuilder(I_C_Payment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Payment.COLUMNNAME_DocStatus, query.getDocStatus());

		if (query.getReconciled() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Payment.COLUMNNAME_IsReconciled, query.getReconciled());
		}
		if (query.getDirection() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Payment.COLUMNNAME_IsReceipt, query.getDirection().isReceipt());
		}
		if (query.getBpartnerId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_Payment.COLUMNNAME_C_BPartner_ID, query.getBpartnerId());
		}
		if (query.getPayAmt() != null)
		{
			queryBuilder
					.addEqualsFilter(I_C_Payment.COLUMNNAME_PayAmt, query.getPayAmt().toBigDecimal())
					.addEqualsFilter(I_C_Payment.COLUMNNAME_C_Currency_ID, query.getPayAmt().getCurrencyId());
		}
		if (!query.getExcludePaymentIds().isEmpty())
		{
			queryBuilder.addNotInArrayFilter(I_C_Payment.COLUMNNAME_C_Payment_ID, query.getExcludePaymentIds());
		}

		return queryBuilder
				.setLimit(query.getLimit())
				.create()
				.listIds(PaymentId::ofRepoId);
	}

	@Override
	public void save(final @NonNull I_C_Payment payment)
	{
		InterfaceWrapperHelper.save(payment);
	}
}
