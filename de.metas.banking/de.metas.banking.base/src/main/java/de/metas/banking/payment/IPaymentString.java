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

/**
 * Contains relevant chunks extracted from a payment string
 *
 * @author al
 */
public interface IPaymentString
{
	/**
	 * @return errors collected while the payment text was parsed
	 */
	List<String> getCollectedErrors();

	/**
	 * Sets data provider for this payment string
	 *
	 * @param dataProvider
	 */
	void setDataProvider(IPaymentStringDataProvider dataProvider);

	/**
	 * @return dataProvider implementation which masks BL / DAO implementations
	 */
	IPaymentStringDataProvider getDataProvider();

	String getPostAccountNo();

	String getInnerAccountNo();

	BigDecimal getAmount();

	String getReferenceNoComplete();

	Timestamp getPaymentDate();

	Timestamp getAccountDate();

	/**
	 *
	 * @return the original, raw and unparsed payment string this instance is based on.
	 */
	String getRawPaymentString();
}
