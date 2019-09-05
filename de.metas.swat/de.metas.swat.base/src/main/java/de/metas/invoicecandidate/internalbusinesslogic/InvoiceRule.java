package de.metas.invoicecandidate.internalbusinesslogic;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import lombok.Getter;

/*
 * #%L
 * de.metas.swat.base
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

public enum InvoiceRule
{
	/** Please keep in sync with {@link X_C_Invoice_Candidate#INVOICERULE_AfterDelivery}. */
	AfterDelivery(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery),

	/** Please keep in sync with {@link X_C_Invoice_Candidate#INVOICERULE_AfterOrderDelivered}. */
	AfterOrderDelivered(X_C_Invoice_Candidate.INVOICERULE_AfterOrderDelivered),

	/** Please keep in sync with {@link X_C_Invoice_Candidate#INVOICERULE_CustomerScheduleAfterDelivery}. */
	CustomerScheduleAfterDelivery(X_C_Invoice_Candidate.INVOICERULE_CustomerScheduleAfterDelivery),

	/** Please keep in sync with {@link X_C_Invoice_Candidate#INVOICERULE_Immediate}. */
	Immediate(X_C_Invoice_Candidate.INVOICERULE_Immediate);

	public static InvoiceRule fromRecordString(@Nullable final String invoiceRule)
	{
		if (X_C_Invoice_Candidate.INVOICERULE_AfterDelivery.equals(invoiceRule))
		{
			return AfterDelivery;
		}
		else if (X_C_Invoice_Candidate.INVOICERULE_AfterOrderDelivered.equals(invoiceRule))
		{
			return AfterOrderDelivered;
		}
		else if (X_C_Invoice_Candidate.INVOICERULE_CustomerScheduleAfterDelivery.equals(invoiceRule))
		{
			return CustomerScheduleAfterDelivery;
		}
		else if (X_C_Invoice_Candidate.INVOICERULE_Immediate.equals(invoiceRule))
		{
			return Immediate;
		}
		throw new AdempiereException("Unsupported invoiceRule value: " + invoiceRule);
	}

	@Getter
	private final String recordString;

	private InvoiceRule(String recordString)
	{
		this.recordString = recordString;
	}

}
