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

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import de.metas.invoicecandidate.api.IInvoicingParams;
import lombok.ToString;

@ToString
public class PlainInvoicingParams implements IInvoicingParams
{
	private Boolean onlyApprovedForInvoicing = null;
	private Boolean consolidateApprovedICs = null;
	private Boolean ignoreInvoiceSchedule = null;
	private Boolean supplementMissingPaymentTermIds = null;

	private Boolean storeInvoicesInResult = null;
	private Boolean assumeOneInvoice = null;

	private LocalDate dateInvoiced;
	private boolean dateInvoicedSet = false;

	private LocalDate dateAcct;
	private boolean dateAcctSet = false;

	private String poReference;
	private boolean poReferenceSet = false;

	private BigDecimal check_NetAmtToInvoice = null;

	private boolean updateLocationAndContactForInvoice = false;

	private final IInvoicingParams defaults;

	public PlainInvoicingParams()
	{
		this(null);
	}

	/**
	 * @param defaults defaults to fallback in case a parameter is not set on this
	 *            level
	 */
	public PlainInvoicingParams(@Nullable final IInvoicingParams defaults)
	{
		this.defaults = defaults;
	}

	@Override
	public boolean isOnlyApprovedForInvoicing()
	{
		if (onlyApprovedForInvoicing != null)
		{
			return onlyApprovedForInvoicing;
		}
		else if (defaults != null)
		{
			return defaults.isOnlyApprovedForInvoicing();
		}
		else
		{
			return false;
		}
	}

	public void setOnlyApprovedForInvoicing(final boolean onlyApprovedForInvoicing)
	{
		this.onlyApprovedForInvoicing = onlyApprovedForInvoicing;
	}

	@Override
	public boolean isConsolidateApprovedICs()
	{
		if (consolidateApprovedICs != null)
		{
			return consolidateApprovedICs;
		}
		else if (defaults != null)
		{
			return defaults.isConsolidateApprovedICs();
		}
		else
		{
			return false;
		}
	}

	public void setConsolidateApprovedICs(final boolean consolidateApprovedICs)
	{
		this.consolidateApprovedICs = consolidateApprovedICs;
	}

	@Override
	public boolean isIgnoreInvoiceSchedule()
	{
		if (ignoreInvoiceSchedule != null)
		{
			return ignoreInvoiceSchedule;
		}
		else if (defaults != null)
		{
			return defaults.isIgnoreInvoiceSchedule();
		}
		else
		{
			return false;
		}
	}

	public void setIgnoreInvoiceSchedule(final boolean ignoreInvoiceSchedule)
	{
		this.ignoreInvoiceSchedule = ignoreInvoiceSchedule;
	}

	@Override
	public LocalDate getDateInvoiced()
	{
		if (dateInvoicedSet)
		{
			return dateInvoiced;
		}
		else if (defaults != null)
		{
			return defaults.getDateInvoiced();
		}
		else
		{
			return null;
		}
	}

	public void setDateInvoiced(final LocalDate dateInvoiced)
	{
		this.dateInvoiced = dateInvoiced;
		dateInvoicedSet = true;
	}

	@Override
	public LocalDate getDateAcct()
	{
		if (dateAcctSet)
		{
			return dateAcct;
		}
		else if (defaults != null)
		{
			return defaults.getDateAcct();
		}
		else
		{
			return null;
		}
	}

	public void setDateAcct(final LocalDate dateAcct)
	{
		this.dateAcct = dateAcct;
		dateAcctSet = true;
	}

	@Override
	public String getPOReference()
	{
		if (poReferenceSet)
		{
			return poReference;
		}
		else if (defaults != null)
		{
			return defaults.getPOReference();
		}
		else
		{
			return null;
		}
	}

	public void setPOReference(@Nullable final String poReference)
	{
		this.poReference = poReference;
		poReferenceSet = true;
	}

	public void setSupplementMissingPaymentTermIds(final boolean supplementMissingPaymentTermIds)
	{
		this.supplementMissingPaymentTermIds = supplementMissingPaymentTermIds;
	}

	@Override
	public boolean isSupplementMissingPaymentTermIds()
	{
		if (supplementMissingPaymentTermIds != null)
		{
			return supplementMissingPaymentTermIds;
		}
		else if (defaults != null)
		{
			return defaults.isSupplementMissingPaymentTermIds();
		}
		return false;
	}

	public void setCheck_NetAmtToInvoice(final BigDecimal check_NetAmtToInvoice)
	{
		this.check_NetAmtToInvoice = check_NetAmtToInvoice;
	}

	@Override
	public BigDecimal getCheck_NetAmtToInvoice()
	{
		if (check_NetAmtToInvoice != null)
		{
			return check_NetAmtToInvoice;
		}
		else if (defaults != null)
		{
			return defaults.getCheck_NetAmtToInvoice();
		}

		return null;
	}

	public PlainInvoicingParams setStoreInvoicesInResult(final boolean storeInvoicesInResult)
	{
		this.storeInvoicesInResult = storeInvoicesInResult;
		return this;
	}

	@Override
	public boolean isStoreInvoicesInResult()
	{
		if (storeInvoicesInResult != null)
		{
			return storeInvoicesInResult;
		}
		else if (defaults != null)
		{
			return defaults.isStoreInvoicesInResult();
		}
		else
		{
			return false;
		}
	}

	public PlainInvoicingParams setAssumeOneInvoice(final boolean assumeOneInvoice)
	{
		this.assumeOneInvoice = assumeOneInvoice;
		return this;
	}

	@Override
	public boolean isAssumeOneInvoice()
	{
		if (assumeOneInvoice != null)
		{
			return assumeOneInvoice;
		}
		else if (defaults != null)
		{
			return defaults.isAssumeOneInvoice();
		}
		else
		{
			return false;
		}
	}

	public boolean isUpdateLocationAndContactForInvoice()
	{
		return updateLocationAndContactForInvoice;
	}

	public void setUpdateLocationAndContactForInvoice(boolean updateLocationAndContactForInvoice)
	{
		this.updateLocationAndContactForInvoice = updateLocationAndContactForInvoice;
	}
}
