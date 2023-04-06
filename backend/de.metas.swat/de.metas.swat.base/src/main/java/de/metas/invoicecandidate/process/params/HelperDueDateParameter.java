/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoicecandidate.process.params;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.organization.IOrgDAO;
import de.metas.payment.paymentterm.BaseLineType;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTerm;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

public class HelperDueDateParameter
{
	private static final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	private static final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	final public static LocalDate computeOverrideDueDate(@NonNull final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder, @Nullable LocalDate dateInvoiced)
	{
		if (countPaymentTerms(icQueryBuilder) > 1)
		{
			return null;
		}

		final int paymentTermId =  icQueryBuilder
				.setLimit(QueryLimit.ONE)
				.create()
				.first()
				.getC_PaymentTerm_ID();

		final PaymentTerm paymentTerm = paymentTermRepository.getById(PaymentTermId.ofRepoId(paymentTermId));
		final Timestamp baseLineDate;
		if (dateInvoiced !=null && paymentTerm.isBaseLineTypeInvoiceDate())
		{
			baseLineDate = TimeUtil.asTimestamp(dateInvoiced);
		}
		else
		{
			baseLineDate = retrieveEarliestBaseLineDate(paymentTerm, icQueryBuilder);
			if (baseLineDate == null)
			{
				return null;
			}
		}

		final Timestamp dueDate = paymentTerm.computeDueDate(baseLineDate);
		final ZoneId zoneId = orgDAO.getTimeZone(paymentTerm.getOrgId());

		return TimeUtil.asLocalDate(dueDate,zoneId);
	}

	static private int countPaymentTerms(@NonNull final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder)
	{
		return icQueryBuilder
				.create()
				.listDistinct(I_C_Invoice_Candidate.COLUMNNAME_C_PaymentTerm_ID)
				.size();

	}

	static private Timestamp retrieveEarliestBaseLineDate(@NonNull final PaymentTerm paymentTerm , @NonNull final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder)
	{
		final BaseLineType baseLineType = paymentTerm.getBaseLineType();

		final Timestamp  earliestDate;

		switch (baseLineType)
		{

			case AfterDelivery:
				earliestDate = icQueryBuilder.orderBy(I_C_Invoice_Candidate.COLUMNNAME_DeliveryDate)
						.create()
						.first()
						.getDeliveryDate();
				break;
			case AfterBillOfLanding:
				earliestDate = icQueryBuilder.orderBy(I_C_Invoice_Candidate.COLUMNNAME_ActualLoadingDate)
						.create()
						.first()
						.getActualLoadingDate();
				break;
			case InvoiceDate:
				earliestDate = icQueryBuilder.orderBy(I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Effective)
						.create()
						.first()
						.getDateToInvoice_Effective();
				break;
			default:
				throw new AdempiereException("Unknown base line type for payment term " + paymentTerm);
		}

		return earliestDate;
	}
}
