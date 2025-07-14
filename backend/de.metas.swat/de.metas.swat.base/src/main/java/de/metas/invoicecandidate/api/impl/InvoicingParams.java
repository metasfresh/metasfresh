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

import de.metas.invoicecandidate.api.IInvoicingParams;
import lombok.NonNull;
import org.adempiere.util.api.IParams;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Invoicing Enqueueing & generating parameters (wrapped from {@link IParams}).
 */
public class InvoicingParams implements IInvoicingParams
{
	private final IParams params;

	public InvoicingParams(@NonNull final IParams params)
	{
		this.params = params;
	}

	@Override
	public boolean isOnlyApprovedForInvoicing()
	{
		return params.getParameterAsBool(PARA_OnlyApprovedForInvoicing);
	}

	@Override
	public boolean isConsolidateApprovedICs()
	{
		return params.getParameterAsBool(PARA_IsConsolidateApprovedICs);
	}

	@Override
	public boolean isIgnoreInvoiceSchedule()
	{
		return params.getParameterAsBool(PARA_IgnoreInvoiceSchedule);
	}

	@Override
	public boolean isUpdateLocationAndContactForInvoice()
	{
		return params.getParameterAsBool(PARA_IsUpdateLocationAndContactForInvoice);
	}

	@Override
	public LocalDate getDateInvoiced()
	{
		return params.getParameterAsLocalDate(PARA_DateInvoiced);
	}

	@Override
	public LocalDate getDateAcct()
	{
		return params.getParameterAsLocalDate(PARA_DateAcct);
	}

	@Override
	public String getPOReference()
	{
		return params.getParameterAsString(PARA_POReference);
	}

	@Override
	public boolean isSupplementMissingPaymentTermIds()
	{
		return params.getParameterAsBool(PARA_SupplementMissingPaymentTermIds);
	}

	@Override
	public BigDecimal getCheck_NetAmtToInvoice()
	{
		return params.getParameterAsBigDecimal(PARA_Check_NetAmtToInvoice);
	}

	/**
	 * Always returns {@code false}.
	 */
	@Override
	public boolean isAssumeOneInvoice()
	{
		return false;
	}

	@Override
	public boolean isCompleteInvoices()
	{
		return params.getParameterAsBoolean(PARA_IsCompleteInvoices, true /*true for backwards-compatibility*/);
	}
	
	/**
	 * Always returns {@code false}.
	 */
	@Override
	public boolean isStoreInvoicesInResult()
	{
		return false;
	}
}
