package de.metas.banking.payment.impl;

/*
 * #%L
 * de.metas.banking.base
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
import java.util.List;

import org.adempiere.util.lang.ObjectUtils;

import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.IPaymentStringDataProvider;

public class PaymentString implements IPaymentString
{
	private final List<String> collectedErrors;

	private final String rawPaymentString;
	private final String postAccountNo;
	private final String innerAccountNo;
	private final BigDecimal amount;
	private final String referenceNoComplete;
	private final Timestamp paymentDate;
	private final Timestamp accountDate;
	private final String orgValue;

	private IPaymentStringDataProvider dataProvider = null;

	public PaymentString(
			final List<String> collectedErrors,
			final String rawPaymentString,
			final String postAccountNo,
			final String innerAccountNo,
			final BigDecimal amount,
			final String referenceNoComplete,
			final Timestamp paymentDate,
			final Timestamp accountDate,
			final String orgValue)
	{
		this.collectedErrors = collectedErrors;

		this.rawPaymentString = rawPaymentString;
		this.postAccountNo = postAccountNo;
		this.innerAccountNo = innerAccountNo;
		this.amount = amount;
		this.referenceNoComplete = referenceNoComplete;
		this.paymentDate = paymentDate;
		this.accountDate = accountDate;
		this.orgValue = orgValue;
	}

	@Override
	public void setDataProvider(final IPaymentStringDataProvider dataProvider)
	{
		this.dataProvider = dataProvider;
	}

	@Override
	public IPaymentStringDataProvider getDataProvider()
	{
		return dataProvider;
	}

	@Override
	public List<String> getCollectedErrors()
	{
		return collectedErrors;
	}

	@Override
	public String getPostAccountNo()
	{
		return postAccountNo;
	}

	@Override
	public String getInnerAccountNo()
	{
		return innerAccountNo;
	}

	@Override
	public BigDecimal getAmount()
	{
		return amount;
	}

	@Override
	public String getReferenceNoComplete()
	{
		return referenceNoComplete;
	}

	@Override
	public Timestamp getPaymentDate()
	{
		return paymentDate;
	}

	@Override
	public Timestamp getAccountDate()
	{
		return accountDate;
	}

	/**
	 * @task https://metasfresh.atlassian.net/browse/FRESH-318
	 */
	@Override
	public String getRawPaymentString()
	{
		return rawPaymentString;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
