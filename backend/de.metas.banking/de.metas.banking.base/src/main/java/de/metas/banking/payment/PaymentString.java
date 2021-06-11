package de.metas.banking.payment;

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

import javax.annotation.Nullable;

import org.adempiere.util.lang.ObjectUtils;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class PaymentString 
{
	@Nullable 
	private final List<String> collectedErrors;

	@NonNull
	private final String rawPaymentString;
	@Nullable
	private final String postAccountNo;
	@Nullable
	private final String innerAccountNo;
	@Nullable
	private final BigDecimal amount;
	@NonNull
	private final String referenceNoComplete;
	private final Timestamp paymentDate;
	private final Timestamp accountDate;
	private final String orgValue;
	
	@Nullable 
	private final String IBAN;

	private IPaymentStringDataProvider dataProvider;


	public void setDataProvider(final IPaymentStringDataProvider dataProvider)
	{
		this.dataProvider = dataProvider;
	}

	public IPaymentStringDataProvider getDataProvider()
	{
		return dataProvider;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
